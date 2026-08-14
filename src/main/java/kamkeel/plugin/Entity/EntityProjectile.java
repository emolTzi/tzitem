package kamkeel.plugin.Entity;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.play.server.SPacketExplosion;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import kamkeel.plugin.Enum.Items.EnumParticleType;
import kamkeel.plugin.Enum.Items.EnumPotionType;
import kamkeel.plugin.Util.IProjectileCallback;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;


public class EntityProjectile extends EntityThrowable {

    private static final DataParameter<ItemStack> THROWN_ITEM = EntityDataManager.createKey(EntityProjectile.class, DataSerializers.ITEM_STACK);
    private static final DataParameter<String> PARTICLE = EntityDataManager.createKey(EntityProjectile.class, DataSerializers.STRING);
    private static final DataParameter<Integer> SIZE = EntityDataManager.createKey(EntityProjectile.class, DataSerializers.VARINT);
    private static final DataParameter<Byte> GLOWS = EntityDataManager.createKey(EntityProjectile.class, DataSerializers.BYTE);
    private static final DataParameter<Integer> VELOCITY = EntityDataManager.createKey(EntityProjectile.class, DataSerializers.VARINT);
    private static final DataParameter<Byte> GRAVITY = EntityDataManager.createKey(EntityProjectile.class, DataSerializers.BYTE);
    private static final DataParameter<Byte> IS_ARROW = EntityDataManager.createKey(EntityProjectile.class, DataSerializers.BYTE);
    private static final DataParameter<Byte> RENDER_3D = EntityDataManager.createKey(EntityProjectile.class, DataSerializers.BYTE);
    private static final DataParameter<Byte> ROTATING = EntityDataManager.createKey(EntityProjectile.class, DataSerializers.BYTE);
    private static final DataParameter<Byte> STICKS = EntityDataManager.createKey(EntityProjectile.class, DataSerializers.BYTE);
    private static final DataParameter<Byte> FAKE_EXPLOSION = EntityDataManager.createKey(EntityProjectile.class, DataSerializers.BYTE);

	private int xTile = -1;
    private int yTile = -1;
    private int zTile = -1;
    private Block inTile;
    protected boolean inGround = false;
    private int inData = 0;
    public int throwableShake = 0;
    public int arrowShake = 0;

    public boolean canBePickedUp = false;
    public boolean destroyedOnEntityHit = true;

    /**
     * Is the entity that throws this 'thing' (snowball, ender pearl, eye of ender or potion)
     */
    private EntityLivingBase thrower;
    public EntityItem entityitem;
    private String throwerName = null;
    private int ticksInGround;
    public int ticksInAir = 0;

    private double accelerationX;
    private double accelerationY;
    private double accelerationZ;

    /**
     * Properties settable by GUI
     */

    public float damage = 5;
    public int punch = 0;
    public boolean accelerate = false;
    public boolean explosive = false;
    public boolean explosiveDamage = true;
    public int explosiveRadius = 0;
    public EnumPotionType effect = EnumPotionType.None;
    public int duration = 5;
    public int amplify = 0;

    public IProjectileCallback callback;
    public ItemStack callbackItem;


    public EntityProjectile(World par1World)
    {
        super(par1World);
        this.setSize(0.25F, 0.25F);
    }

    @Override
    protected void entityInit() {
        this.dataManager.register(THROWN_ITEM, ItemStack.EMPTY);
        this.dataManager.register(PARTICLE, String.valueOf(""));//particle
        this.dataManager.register(SIZE, Integer.valueOf(5));//size
        this.dataManager.register(GLOWS, Byte.valueOf((byte)0));//glows
        this.dataManager.register(VELOCITY, Integer.valueOf(10));//velocity
        this.dataManager.register(GRAVITY, Byte.valueOf((byte)0));//gravity
        this.dataManager.register(IS_ARROW, Byte.valueOf((byte)0));//Arrow
        this.dataManager.register(RENDER_3D, Byte.valueOf((byte)0));//3D
        this.dataManager.register(ROTATING, Byte.valueOf((byte)0));//Rotating
        this.dataManager.register(STICKS, Byte.valueOf((byte)0));//Sticks
        this.dataManager.register(FAKE_EXPLOSION, Byte.valueOf((byte)0));//FakeExplosion
    }

    @SideOnly(Side.CLIENT)

    /**
     * Checks if the entity is in range to render by using the past in distance and comparing it to its average edge
     * length * 64 * renderDistanceWeight Args: distance
     */
    @Override
    public boolean isInRangeToRenderDist(double par1)
    {
        double d1 = this.getEntityBoundingBox().getAverageEdgeLength() * 4.0D;
        d1 *= 64.0D;
        return par1 < d1 * d1;
    }

    public EntityProjectile(World par1World, EntityLivingBase par2EntityLiving, ItemStack item, boolean isNPC)
    {
        super(par1World);
        this.thrower = par2EntityLiving;
        if(this.thrower != null)
        	this.throwerName = this.thrower.getUniqueID().toString();
        setThrownItem(item);
        this.dataManager.set(IS_ARROW, Byte.valueOf((byte) ((this.getItem() == Items.ARROW) ? 1 : 0)));
        this.setSize(this.dataManager.get(SIZE) / 10 , this.dataManager.get(SIZE) / 10);
        this.setLocationAndAngles(par2EntityLiving.posX, par2EntityLiving.posY + (double)par2EntityLiving.getEyeHeight(), par2EntityLiving.posZ, par2EntityLiving.rotationYaw, par2EntityLiving.rotationPitch);
        this.posX -= (double)(MathHelper.cos(this.rotationYaw / 180.0F * (float)Math.PI) * 0.1F);
        this.posY -= 0.1f;
        this.posZ -= (double)(MathHelper.sin(this.rotationYaw / 180.0F * (float)Math.PI) * 0.1F);
        this.setPosition(this.posX, this.posY, this.posZ);

    }

    public void setThrownItem(ItemStack item){
        dataManager.set(THROWN_ITEM, item);
    }

    /**
     * Par: X, Y, Z, Angle, Accuracy
     */
    public void setThrowableHeading(double par1, double par3, double par5, float par7, float par8)
    {
        float f2 = MathHelper.sqrt(par1 * par1 + par3 * par3 + par5 * par5);
        float f3 = MathHelper.sqrt(par1 * par1 + par5 * par5);
        float yaw = (float)(Math.atan2(par1, par5) * 180.0D / Math.PI);
        float pitch = this.hasGravity() ? par7 : (float)(Math.atan2(par3, (double)f3) * 180.0D / Math.PI);
        this.prevRotationYaw = this.rotationYaw = yaw;
        this.prevRotationPitch = this.rotationPitch = pitch;
        this.motionX = (double)(MathHelper.sin(yaw / 180.0F * (float)Math.PI) * MathHelper.cos(pitch / 180.0F * (float)Math.PI));
        this.motionZ = (double)(MathHelper.cos(yaw / 180.0F * (float)Math.PI) * MathHelper.cos(pitch / 180.0F * (float)Math.PI));
        this.motionY = (double)(MathHelper.sin((pitch + 1.0F) / 180.0F * (float)Math.PI));
        this.motionX += this.rand.nextGaussian() * 0.007499999832361937D * (double)par8;
        this.motionZ += this.rand.nextGaussian() * 0.007499999832361937D * (double)par8;
        this.motionY += this.rand.nextGaussian() * 0.007499999832361937D * (double)par8;
        this.motionX *= this.getSpeed();
        this.motionZ *= this.getSpeed();
        this.motionY *= this.getSpeed();
        this.accelerationX = par1 / f2 * 0.1D;
        this.accelerationY = par3 / f2 * 0.1D;
        this.accelerationZ = par5 / f2 * 0.1D;
        this.ticksInGround = 0;
    }

    /**
     * get an angle for firing at coordinates XYZ
     * Par: X Distance, Y Distance, Z Distance, Horizontial Distance
     */
    public float getAngleForXYZ(double varX, double varY, double varZ, double horiDist, boolean arc) {
    	float g = this.getGravityVelocity();
    	float var1 = this.getSpeed() * this.getSpeed();
    	double var2 = (g * horiDist);
    	double var3 = ((g * horiDist * horiDist) + (2 * varY * var1));
    	double var4 = (var1 * var1) - (g * var3);
    	if (var4 < 0) return 30.0F;
    	float var6 = arc ? var1 + MathHelper.sqrt(var4) : var1 - MathHelper.sqrt(var4);
    	float var7 = (float) (Math.atan2(var6 , var2) * 180.0D / Math.PI);
    	return var7;
    }

    public void shoot(float speed){
        double varX = (double)(-MathHelper.sin(this.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float)Math.PI));
        double varZ = (double)(MathHelper.cos(this.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float)Math.PI));
        double varY = (double)(-MathHelper.sin(this.rotationPitch / 180.0F * (float)Math.PI));
        this.setThrowableHeading(varX, varY, varZ, -rotationPitch, speed);
    }

    @Override
    public void onUpdate()
    {
        super.onEntityUpdate();
        if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F)
        {
            float f = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
            this.prevRotationYaw = this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);
            this.prevRotationPitch = this.rotationPitch = (float)(Math.atan2(this.motionY, (double)f) * 180.0D / Math.PI);
            if (this.isRotating()) {
            	this.rotationPitch -= 20;
            }
        }
        if (this.effect == EnumPotionType.Fire && !this.inGround)
        	this.setFire(1);


        BlockPos tilePos = new BlockPos(this.xTile, this.yTile, this.zTile);
        IBlockState blockState = this.world.getBlockState(tilePos);
        Block block = blockState.getBlock();

        if ((this.isArrow() || this.sticksToWalls()) && block != Blocks.AIR)
        {
            AxisAlignedBB axisalignedbb = blockState.getCollisionBoundingBox(this.world, tilePos);

            if (axisalignedbb != null && axisalignedbb.contains(new Vec3d(this.posX, this.posY, this.posZ)))
            {
                this.inGround = true;
            }
        }

        if (this.arrowShake > 0)
        {
            --this.arrowShake;
        }

        if (this.inGround)
        {
            int j = blockState.getBlock().getMetaFromState(blockState);
            if (block == this.inTile && j == this.inData)
            {
                ++this.ticksInGround;

                if (this.ticksInGround == 1200)
                {
                    this.setDead();
                }
            }
            else
            {
                this.inGround = false;
                this.motionX *= (double)(this.rand.nextFloat() * 0.2F);
                this.motionY *= (double)(this.rand.nextFloat() * 0.2F);
                this.motionZ *= (double)(this.rand.nextFloat() * 0.2F);
                this.ticksInGround = 0;
                this.ticksInAir = 0;
            }
        }
        else
        {
            ++this.ticksInAir;

            if (this.ticksInAir == 1200)
            {
                this.setDead();
            }
	        Vec3d vec3 = new Vec3d(this.posX, this.posY, this.posZ);
	        Vec3d vec31 = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
	        RayTraceResult movingobjectposition = this.world.rayTraceBlocks(vec3, vec31, false, true, false);//rayTraceBlocks
	        vec3 = new Vec3d(this.posX, this.posY, this.posZ);
	        vec31 = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);

	        if (movingobjectposition != null)
	        {
	            vec31 = new Vec3d(movingobjectposition.hitVec.x, movingobjectposition.hitVec.y, movingobjectposition.hitVec.z);
	        }
	        if (!this.world.isRemote)
	        {
	            Entity entity = null;
	            List list = this.world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().offset(this.motionX, this.motionY, this.motionZ).expand(1.0D, 1.0D, 1.0D));
	            double d0 = 0.0D;
	            EntityLivingBase entityliving = this.getThrower();

	            for (int k = 0; k < list.size(); ++k)
	            {
	                Entity entity1 = (Entity)list.get(k);

	                if (entity1.canBeCollidedWith() && (!entity1.isEntityEqual(this.thrower) || this.ticksInAir >= 25))
	                {
	                    float f = 0.3F;
	                    AxisAlignedBB axisalignedbb = entity1.getEntityBoundingBox().expand((double)f, (double)f, (double)f);
	                    RayTraceResult movingobjectposition1 = axisalignedbb.calculateIntercept(vec3, vec31);

	                    if (movingobjectposition1 != null)
	                    {
	                        double d1 = vec3.distanceTo(movingobjectposition1.hitVec);

	                        if (d1 < d0 || d0 == 0.0D)
	                        {
	                            entity = entity1;
	                            d0 = d1;
	                        }
	                    }
	                }
	            }

	            if (entity != null)
	            {
	                movingobjectposition = new RayTraceResult(entity);
	            }
	        }

	        if (movingobjectposition != null)
	        {
	            if (movingobjectposition.typeOfHit == RayTraceResult.Type.BLOCK && this.world.getBlockState(movingobjectposition.getBlockPos()).getBlock() == Blocks.PORTAL)
	            {
	                this.setPortal(new BlockPos(this));
	            }
	            else
	            {
	            	this.dataManager.set(ROTATING, Byte.valueOf((byte)0));
	            	this.onImpact(movingobjectposition);
	            }
	        }

	        this.posX += this.motionX;
	        this.posY += this.motionY;
	        this.posZ += this.motionZ;
	        float f1 = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
	        this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);

	        for (this.rotationPitch = (float)(Math.atan2(this.motionY, (double)f1) * 180.0D / Math.PI); this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F)
	        {
	            ;
	        }

	        while (this.rotationPitch - this.prevRotationPitch >= 180.0F)
	        {
	            this.prevRotationPitch += 360.0F;
	        }

	        while (this.rotationYaw - this.prevRotationYaw < -180.0F)
	        {
	            this.prevRotationYaw -= 360.0F;
	        }

	        while (this.rotationYaw - this.prevRotationYaw >= 180.0F)
	        {
	            this.prevRotationYaw += 360.0F;
	        }

	        float f = this.isArrow() ? 0.0F : 225.0F;
	        this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) + f * 0.2F;
	        this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F;
	        if (this.isRotating()) {
	        	int spin = isBlock()? 10 : 20;
	        	this.rotationPitch -= (this.ticksInAir % 15) * spin * getSpeed();
	        }
	        float f2 = this.getMotionFactor();
	        float f3 = this.getGravityVelocity();

	        if (this.isInWater())
	        {
	        	if(world.isRemote){
		            for (int k = 0; k < 4; ++k)
		            {
		                float f4 = 0.25F;
		                this.world.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX - this.motionX * (double)f4, this.posY - this.motionY * (double)f4, this.posZ - this.motionZ * (double)f4, this.motionX, this.motionY, this.motionZ);
		            }
	        	}

	            f2 = 0.8F;
	        }

	        this.motionX *= (double)f2;
	        this.motionY *= (double)f2;
	        this.motionZ *= (double)f2;

	        if (hasGravity())
	        	this.motionY -= (double)f3;

	        if (accelerate)
	        {
	        	this.motionX += this.accelerationX;
	            this.motionY += this.accelerationY;
	            this.motionZ += this.accelerationZ;
	        }

	        if (world.isRemote && !this.dataManager.get(PARTICLE).equals("")){
	        	EnumParticleTypes particle = getParticleType(this.dataManager.get(PARTICLE));
	        	if (particle != null) {
	        		this.world.spawnParticle(particle, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
	        	}
	        }
	        this.setPosition(this.posX, this.posY, this.posZ);
	        this.doBlockCollisions();
        }
    }

    private static EnumParticleTypes getParticleType(String name) {
        switch (name) {
            case "smoke": return EnumParticleTypes.SMOKE_NORMAL;
            case "portal": return EnumParticleTypes.PORTAL;
            case "reddust": return EnumParticleTypes.REDSTONE;
            case "magicCrit": return EnumParticleTypes.CRIT_MAGIC;
            case "largesmoke": return EnumParticleTypes.SMOKE_LARGE;
            case "witchMagic": return EnumParticleTypes.SPELL_WITCH;
            case "enchantmenttable": return EnumParticleTypes.ENCHANTMENT_TABLE;
            case "crit": return EnumParticleTypes.CRIT;
            default: return null;
        }
    }

    public boolean isBlock(){
    	ItemStack item = this.getItemDisplay();
    	if(item == null)
    		return false;
    	return item.getItem() instanceof ItemBlock;
    }

    private Item getItem(){
    	ItemStack item = this.getItemDisplay();
    	if(item == null)
    		return null;
    	return item.getItem();
    }

    protected float getMotionFactor()
    {
        return accelerate ? 0.95F : 1.0F;
    }

    /**
     * Called when this EntityThrowable hits a block or entity.
     */
    @Override
    protected void onImpact(RayTraceResult movingobjectposition)
    {
    	if (movingobjectposition.entityHit != null)
        {
        	if(callback != null && callbackItem != null && movingobjectposition.entityHit instanceof EntityLivingBase && callback.onImpact(this, (EntityLivingBase)movingobjectposition.entityHit, callbackItem)){
        		return;
        	}
    		float damage = this.damage;
    		if(damage == 0)
    			damage = 0.001f;

            if (movingobjectposition.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), damage))
            {
	            if (movingobjectposition.entityHit instanceof EntityLivingBase && (this.isArrow() || this.sticksToWalls()))
	            {
	            	EntityLivingBase entityliving = (EntityLivingBase)movingobjectposition.entityHit;

	                if (!this.world.isRemote)
	                {
	                    entityliving.setArrowCountInEntity(entityliving.getArrowCountInEntity() + 1);
	                }

	                if (destroyedOnEntityHit && !(movingobjectposition.entityHit instanceof EntityEnderman))
                    {
                        this.setDead();
                    }
	            }

	            if (this.isBlock())
	    		{
	        		this.world.playEvent(2001, new BlockPos(movingobjectposition.entityHit.posX, movingobjectposition.entityHit.posY, movingobjectposition.entityHit.posZ), Item.getIdFromItem(getItem()));
	    		}
	            else if (!this.isArrow() && !this.sticksToWalls())
	    		{
			        for (int i = 0; i < 8; ++i)
			        {
			        	this.world.spawnParticle(EnumParticleTypes.ITEM_CRACK, this.posX, this.posY, this.posZ, this.rand.nextGaussian() * 0.15D, this.rand.nextGaussian() * 0.2D, this.rand.nextGaussian() * 0.15D, Item.getIdFromItem(getItem()));
			        }
	    		}

	            if (this.punch > 0)
	            {
	                float f3 = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);

	                if (f3 > 0.0F)
	                {
	                    movingobjectposition.entityHit.addVelocity(this.motionX * (double)this.punch * 0.6000000238418579D / (double)f3, 0.1D, this.motionZ * (double)this.punch * 0.6000000238418579D / (double)f3);
	                }
	            }

	            if (this.effect != EnumPotionType.None && movingobjectposition.entityHit instanceof EntityLivingBase)
	            {
	            	if (this.effect != EnumPotionType.Fire)
	            	{
	            		Potion p = this.getPotion(effect);
	            		((EntityLivingBase)movingobjectposition.entityHit).addPotionEffect(new PotionEffect(p, this.duration * 20, this.amplify));
	            	}
	            	else
	            	{
	            		movingobjectposition.entityHit.setFire(duration);
	            	}
	            }
            }
            else if (this.hasGravity() && (this.isArrow() || this.sticksToWalls()))
            {
            	this.motionX *= -0.10000000149011612D;
                this.motionY *= -0.10000000149011612D;
                this.motionZ *= -0.10000000149011612D;
                this.rotationYaw += 180.0F;
                this.prevRotationYaw += 180.0F;
                this.ticksInAir = 0;
            }
        }
    	else
    	{
    		if (this.isArrow() || this.sticksToWalls()) {
	        	this.xTile = movingobjectposition.getBlockPos().getX();
	            this.yTile = movingobjectposition.getBlockPos().getY();
	            this.zTile = movingobjectposition.getBlockPos().getZ();
	            this.inTile = this.world.getBlockState(movingobjectposition.getBlockPos()).getBlock();
	            this.inData = this.world.getBlockState(movingobjectposition.getBlockPos()).getBlock().getMetaFromState(this.world.getBlockState(movingobjectposition.getBlockPos()));
	            this.motionX = (double)((float)(movingobjectposition.hitVec.x - this.posX));
	            this.motionY = (double)((float)(movingobjectposition.hitVec.y - this.posY));
	            this.motionZ = (double)((float)(movingobjectposition.hitVec.z - this.posZ));
	            float f2 = MathHelper.sqrt(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
	            this.posX -= this.motionX / (double)f2 * 0.05000000074505806D;
	            this.posY -= this.motionY / (double)f2 * 0.05000000074505806D;
	            this.posZ -= this.motionZ / (double)f2 * 0.05000000074505806D;
	            this.inGround = true;
	            if (this.isArrow())
	            	this.playSound(SoundEvents.ENTITY_ARROW_HIT, 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
	            else
	            	this.playSound(SoundEvents.ENTITY_ITEM_BREAK, 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
	            this.arrowShake = 7;

	            if (!this.hasGravity()) {
	            		this.dataManager.set(GRAVITY, Byte.valueOf((byte) 1));
	            	}

	            if (this.inTile != null)
	            {//onEntityCollidedWithBlock
	            	this.world.getBlockState(movingobjectposition.getBlockPos()).getBlock().onEntityCollidedWithBlock(this.world, movingobjectposition.getBlockPos(), this.world.getBlockState(movingobjectposition.getBlockPos()), this);
	            }
    		}
    		else
    		{
	            if (this.isBlock())
	    		{
	        		this.world.playEvent(2001, new BlockPos(MathHelper.floor(posX), MathHelper.floor(posY), MathHelper.floor(posZ)), Item.getIdFromItem(getItem()));
	    		}
	            else
	    		{
			        for (int i = 0; i < 8; ++i)
			        {
			        	this.world.spawnParticle(EnumParticleTypes.ITEM_CRACK, this.posX, this.posY, this.posZ, this.rand.nextGaussian() * 0.15D, this.rand.nextGaussian() * 0.2D, this.rand.nextGaussian() * 0.15D, Item.getIdFromItem(getItem()));
			        }
	    		}
        	}
        }


    	if (explosive){
    		if (this.explosiveRadius != 0 || this.effect == EnumPotionType.None){
    			boolean terraindamage = this.world.getGameRules().getBoolean("mobGriefing") && explosiveDamage;
    	        Explosion explosion = new Explosion(world, this, posX, posY, posZ, explosiveRadius, this.effect == EnumPotionType.Fire, terraindamage);
    	        if(terraindamage)
        	        explosion.doExplosionA();
    	        explosion.doExplosionB(world.isRemote);
	            if(!world.isRemote){
	            	Iterator iterator = world.playerEntities.iterator();

	                while (iterator.hasNext()){
	                    EntityPlayer entityplayer = (EntityPlayer)iterator.next();
	                    if (entityplayer.getDistanceSq(posX, posY, posZ) < 4096.0D){
	                        ((EntityPlayerMP)entityplayer).connection.sendPacket(new SPacketExplosion(posX, posY, posZ, explosiveRadius, explosion.getAffectedBlockPositions(), (Vec3d)explosion.getPlayerKnockbackMap().get(entityplayer)));
	                    }
	                }
	            }
    			if (this.explosiveRadius != 0 && (this.isArrow() || this.sticksToWalls()))
    				this.setDead();
    		}
    		else if (this.effect == EnumPotionType.Fire){
    			BlockPos pos = movingobjectposition.getBlockPos().offset(movingobjectposition.sideHit);

                if (this.world.isAirBlock(pos))
                {
                    this.world.setBlockState(pos, Blocks.FIRE.getDefaultState());
                }
    		}
    		else
    		{
    			AxisAlignedBB axisalignedbb = this.getEntityBoundingBox().expand(4.0D, 2.0D, 4.0D);
    			List list1 = this.world.getEntitiesWithinAABB(EntityLivingBase.class, axisalignedbb);

    			if (list1 != null && !list1.isEmpty())
    			{
    				Iterator iterator = list1.iterator();

                    while (iterator.hasNext())
                    {
                        EntityLivingBase entitylivingbase = (EntityLivingBase)iterator.next();
                        double d0 = this.getDistanceSq(entitylivingbase);

                        if (d0 < 16.0D)
                        {
                            double d1 = 1.0D - Math.sqrt(d0) / 4.0D;

                            if (entitylivingbase == movingobjectposition.entityHit)
                            {
                                d1 = 1.0D;
                            }

                            Potion potion = this.getPotion(effect);

                            if (potion.isInstant())
                            {
                                potion.affectEntity(this.getThrower(), this, entitylivingbase, this.amplify, d1);
                            }
                            else
                            {
                                int j = (int)(d1 * (double)this.duration + 0.5D);

                                if (j > 20)
                                {
                                    entitylivingbase.addPotionEffect(new PotionEffect(potion, j, this.amplify));
                                }
                            }
                        }
                    }
                }
    			this.world.playEvent(2002, new BlockPos((int)Math.round(this.posX), (int)Math.round(this.posY), (int)Math.round(this.posZ)), this.getPotionColor(this.effect));
    		}
    	}

        if (!this.world.isRemote && !this.isArrow() && !this.sticksToWalls())
        {
            this.setDead();
        }
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
    {
        par1NBTTagCompound.setShort("xTile", (short)this.xTile);
        par1NBTTagCompound.setShort("yTile", (short)this.yTile);
        par1NBTTagCompound.setShort("zTile", (short)this.zTile);
        par1NBTTagCompound.setByte("inTile", (byte) Block.getIdFromBlock(this.inTile));
        par1NBTTagCompound.setByte("inData", (byte)this.inData);
        par1NBTTagCompound.setByte("shake", (byte)this.throwableShake);
        par1NBTTagCompound.setByte("inGround", (byte)(this.inGround ? 1 : 0));
        par1NBTTagCompound.setByte("isArrow", (byte)(this.isArrow() ? 1 : 0));
        par1NBTTagCompound.setTag("direction", this.newDoubleNBTList(new double[] {this.motionX, this.motionY, this.motionZ}));
        par1NBTTagCompound.setBoolean("canBePickedUp", canBePickedUp);

        if ((this.throwerName == null || this.throwerName.length() == 0) && this.thrower != null && this.thrower instanceof EntityPlayer)
        {
            this.throwerName = this.thrower.getUniqueID().toString();
        }

        par1NBTTagCompound.setString("ownerName", this.throwerName == null ? "" : this.throwerName);
        if (this.getItemDisplay() != null)
        {
            par1NBTTagCompound.setTag("Item", this.getItemDisplay().writeToNBT(new NBTTagCompound()));
        }

        par1NBTTagCompound.setFloat("damagev2", damage);
		par1NBTTagCompound.setInteger("punch", punch);
		par1NBTTagCompound.setInteger("size", this.dataManager.get(SIZE));
		par1NBTTagCompound.setInteger("velocity", this.dataManager.get(VELOCITY));
		par1NBTTagCompound.setInteger("explosiveRadius", explosiveRadius);
		par1NBTTagCompound.setInteger("effectDuration", duration);
		par1NBTTagCompound.setBoolean("gravity", this.hasGravity());
		par1NBTTagCompound.setBoolean("accelerate", this.accelerate);
		par1NBTTagCompound.setByte("glows", this.dataManager.get(GLOWS));
		par1NBTTagCompound.setBoolean("explosive", explosive);
		par1NBTTagCompound.setInteger("PotionEffect", effect.ordinal());
		par1NBTTagCompound.setString("trail", this.dataManager.get(PARTICLE));
		par1NBTTagCompound.setByte("Render3D", this.dataManager.get(RENDER_3D));
		par1NBTTagCompound.setByte("Spins", this.dataManager.get(ROTATING));
		par1NBTTagCompound.setByte("Sticks", this.dataManager.get(STICKS));
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    @Override
    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        this.xTile = par1NBTTagCompound.getShort("xTile");
        this.yTile = par1NBTTagCompound.getShort("yTile");
        this.zTile = par1NBTTagCompound.getShort("zTile");
        this.inTile = Block.getBlockById(par1NBTTagCompound.getByte("inTile") & 255);
        this.inData = par1NBTTagCompound.getByte("inData") & 255;
        this.throwableShake = par1NBTTagCompound.getByte("shake") & 255;
        this.inGround = par1NBTTagCompound.getByte("inGround") == 1;
        this.dataManager.set(IS_ARROW, par1NBTTagCompound.getByte("isArrow"));
        this.throwerName = par1NBTTagCompound.getString("ownerName");
        this.canBePickedUp = par1NBTTagCompound.getBoolean("canBePickedUp");

        this.damage = par1NBTTagCompound.getFloat("damagev2");
    	this.punch = par1NBTTagCompound.getInteger("punch");
    	this.explosiveRadius = par1NBTTagCompound.getInteger("explosiveRadius");
    	this.duration = par1NBTTagCompound.getInteger("effectDuration");
    	this.accelerate = par1NBTTagCompound.getBoolean("accelerate");
    	this.explosive = par1NBTTagCompound.getBoolean("explosive");
    	this.effect = EnumPotionType.values()[par1NBTTagCompound.getInteger("PotionEffect") % EnumPotionType.values().length];
        this.dataManager.set(PARTICLE, par1NBTTagCompound.getString("trail"));
		this.dataManager.set(SIZE, Integer.valueOf(par1NBTTagCompound.getInteger("size")));
		this.dataManager.set(GLOWS, Byte.valueOf((byte) (par1NBTTagCompound.getBoolean("glows") ? 1 : 0)));
		this.dataManager.set(VELOCITY, Integer.valueOf(par1NBTTagCompound.getInteger("velocity")));
		this.dataManager.set(GRAVITY, Byte.valueOf((byte) (par1NBTTagCompound.getBoolean("gravity") ? 1 : 0)));
		this.dataManager.set(RENDER_3D, Byte.valueOf((byte) (par1NBTTagCompound.getBoolean("Render3D") ? 1 : 0)));
		this.dataManager.set(ROTATING, Byte.valueOf((byte) (par1NBTTagCompound.getBoolean("Spins") ? 1 : 0)));
		this.dataManager.set(STICKS, Byte.valueOf((byte) (par1NBTTagCompound.getBoolean("Sticks") ? 1 : 0)));

        if (this.throwerName != null && this.throwerName.length() == 0)
        {
            this.throwerName = null;
        }
        if (par1NBTTagCompound.hasKey("direction"))
        {
            NBTTagList nbttaglist = par1NBTTagCompound.getTagList("direction",6);
            this.motionX = nbttaglist.getDoubleAt(0);
            this.motionY = nbttaglist.getDoubleAt(1);
            this.motionZ = nbttaglist.getDoubleAt(2);
        }

        NBTTagCompound var2 = par1NBTTagCompound.getCompoundTag("Item");
        ItemStack item = new ItemStack(var2);

        if (item.isEmpty())
            this.setDead();
        else
        	dataManager.set(THROWN_ITEM, item);
    }

    @Override
	public EntityLivingBase getThrower()
    {
    	if(throwerName == null || throwerName.isEmpty())
    		return null;
		try{
	    	UUID uuid = UUID.fromString(throwerName);
	        if (this.thrower == null && uuid != null)
	            this.thrower = this.world.getPlayerEntityByUUID(uuid);
		}
		catch(IllegalArgumentException ex){

		}

        return this.thrower;
    }

	private Potion getPotion(EnumPotionType p) {
		switch(p)
		{
		case Poison : return net.minecraft.init.MobEffects.POISON;
		case Hunger : return net.minecraft.init.MobEffects.HUNGER;
		case Weakness : return net.minecraft.init.MobEffects.WEAKNESS;
		case Slowness : return net.minecraft.init.MobEffects.SLOWNESS;
		case Nausea : return net.minecraft.init.MobEffects.NAUSEA;
		case Blindness : return net.minecraft.init.MobEffects.BLINDNESS;
		case Wither : return net.minecraft.init.MobEffects.WITHER;
		default : return null;
		}
	}

	private int getPotionColor(EnumPotionType p) {
		switch(p)
		{
		case Poison : return 32660;
		case Hunger : return 32660;
		case Weakness : return 32696;
		case Slowness : return 32698;
		case Nausea : return 32732;
		case Blindness : return 15; // 1.7.10 Potion.blindness.id
		case Wither : return 32732;
		default : return 0;
		}
	}

	public void setParticleEffect(EnumParticleType type){
		this.dataManager.set(PARTICLE, type.particleName);
	}

	public void setHasGravity(boolean bo){
		this.dataManager.set(GRAVITY, Byte.valueOf((byte) (bo ? 1 : 0)));
	}
	public void setIs3D(boolean bo){
		this.dataManager.set(RENDER_3D, Byte.valueOf((byte) (bo ? 1 : 0)));
	}
	public void setStickInWall(boolean bo){
		this.dataManager.set(STICKS, Byte.valueOf((byte) (bo ? 1 : 0)));
	}

	public ItemStack getItemDisplay() {
		return dataManager.get(THROWN_ITEM);
	}

	public float getRenderScale() {
		return dataManager.get(SIZE) / 10.0F;
	}

	@Override
	public float getBrightness()
    {
        return this.dataManager.get(GLOWS) == 1 ? 1.0F : super.getBrightness();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public int getBrightnessForRender()
    {
        return this.dataManager.get(GLOWS) == 1 ? 15728880 : super.getBrightnessForRender();
    }

    public boolean hasGravity() {
    	return this.dataManager.get(GRAVITY) == 1;
    }

    public void setSpeed(int speed) {
    	this.dataManager.set(VELOCITY, speed);
    }

    public float getSpeed() {
    	return this.dataManager.get(VELOCITY) / 10.0F;
    }

    public boolean isArrow() {
    	return this.dataManager.get(IS_ARROW) == 1;
    }

	public void setRotating(boolean bo) {
		dataManager.set(ROTATING, Byte.valueOf((byte) (bo ? 1 : 0)));
	}

    public boolean isRotating() {
    	return this.dataManager.get(ROTATING) == 1;
    }

    public boolean glows() {
    	return this.dataManager.get(GLOWS) == 1;
    }

    public boolean is3D() {
    	return this.dataManager.get(RENDER_3D) == 1 || isBlock();
    }

    public boolean sticksToWalls() {
    	return this.is3D() && this.dataManager.get(STICKS) == 1;
    }

    @Override
    public void onCollideWithPlayer(EntityPlayer par1EntityPlayer)
    {
        if (this.world.isRemote || !canBePickedUp || !this.inGround || this.arrowShake > 0)
        	return;
        if (par1EntityPlayer.inventory.addItemStackToInventory(getItemDisplay()))
        {
        	inGround = false;
            this.playSound(SoundEvents.ENTITY_ITEM_PICKUP, 0.2F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
            par1EntityPlayer.onItemPickup(this, 1);
            this.setDead();
        }

    }

    @Override
    protected boolean canTriggerWalking()
    {
        return false;
    }

    @Override
    public net.minecraft.util.text.ITextComponent getDisplayName()
    {
    	if(getItemDisplay() != null)
    		return new TextComponentTranslation(getItemDisplay().getDisplayName());
    	return super.getDisplayName();
    }
}
