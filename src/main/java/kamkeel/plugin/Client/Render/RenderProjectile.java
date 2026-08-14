package kamkeel.plugin.Client.Render;

import kamkeel.plugin.Entity.EntityProjectile;
import kamkeel.plugin.Items.ItemVariantTexture;
import kamkeel.plugin.Items.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderProjectile extends Render<EntityProjectile>
{

	public boolean renderWithColor = true;
	private static final ResourceLocation field_110780_a = new ResourceLocation("textures/entity/arrow.png");

    public RenderProjectile(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    public void doRender(EntityProjectile par1EntityProjectile, double par2, double par4, double par6, float par8, float par9)
    {
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)par2, (float)par4, (float)par6);
        GlStateManager.enableRescaleNormal();
        float f = par1EntityProjectile.getRenderScale();
        ItemStack item = par1EntityProjectile.getItemDisplay();
        if (item == null || item.isEmpty()) {
            GlStateManager.popMatrix();
            return;
        }
        GlStateManager.scale(f, f, f);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();

        if (par1EntityProjectile.isArrow()) { //If it's the special case we are rendering an arrow

        	this.bindTexture(field_110780_a);
            GlStateManager.rotate(par1EntityProjectile.prevRotationYaw + (par1EntityProjectile.rotationYaw - par1EntityProjectile.prevRotationYaw) * par9 - 90.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(par1EntityProjectile.prevRotationPitch + (par1EntityProjectile.rotationPitch - par1EntityProjectile.prevRotationPitch) * par9, 0.0F, 0.0F, 1.0F);
            byte b0 = 0;
            float f2 = 0.0F;
            float f3 = 0.5F;
            float f4 = (float)(0 + b0 * 10) / 32.0F;
            float f5 = (float)(5 + b0 * 10) / 32.0F;
            float f6 = 0.0F;
            float f7 = 0.15625F;
            float f8 = (float)(5 + b0 * 10) / 32.0F;
            float f9 = (float)(10 + b0 * 10) / 32.0F;
            float f10 = 0.05625F;
            GlStateManager.enableRescaleNormal();
            float f11 = (float)par1EntityProjectile.arrowShake - par9;

            if (f11 > 0.0F)
            {
                float f12 = -MathHelper.sin(f11 * 3.0F) * f11;
                GlStateManager.rotate(f12, 0.0F, 0.0F, 1.0F);
            }

            GlStateManager.rotate(45.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.scale(f10, f10, f10);
            GlStateManager.translate(-4.0F, 0.0F, 0.0F);
            GlStateManager.glNormal3f(f10, 0.0F, 0.0F);
            buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
            buffer.pos(-7.0D, -2.0D, -2.0D).tex((double)f6, (double)f8).endVertex();
            buffer.pos(-7.0D, -2.0D, 2.0D).tex((double)f7, (double)f8).endVertex();
            buffer.pos(-7.0D, 2.0D, 2.0D).tex((double)f7, (double)f9).endVertex();
            buffer.pos(-7.0D, 2.0D, -2.0D).tex((double)f6, (double)f9).endVertex();
            tessellator.draw();
            GlStateManager.glNormal3f(-f10, 0.0F, 0.0F);
            buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
            buffer.pos(-7.0D, 2.0D, -2.0D).tex((double)f6, (double)f8).endVertex();
            buffer.pos(-7.0D, 2.0D, 2.0D).tex((double)f7, (double)f8).endVertex();
            buffer.pos(-7.0D, -2.0D, 2.0D).tex((double)f7, (double)f9).endVertex();
            buffer.pos(-7.0D, -2.0D, -2.0D).tex((double)f6, (double)f9).endVertex();
            tessellator.draw();

            for (int i = 0; i < 4; ++i)
            {
                GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
                GlStateManager.glNormal3f(0.0F, 0.0F, f10);
                buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
                buffer.pos(-8.0D, -2.0D, 0.0D).tex((double)f2, (double)f4).endVertex();
                buffer.pos(8.0D, -2.0D, 0.0D).tex((double)f3, (double)f4).endVertex();
                buffer.pos(8.0D, 2.0D, 0.0D).tex((double)f3, (double)f5).endVertex();
                buffer.pos(-8.0D, 2.0D, 0.0D).tex((double)f2, (double)f5).endVertex();
                tessellator.draw();
            }
        }

        else if (par1EntityProjectile.is3D()) {
        	GlStateManager.rotate(par1EntityProjectile.prevRotationYaw + (par1EntityProjectile.rotationYaw - par1EntityProjectile.prevRotationYaw) * par9 - 90.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(par1EntityProjectile.prevRotationPitch + (par1EntityProjectile.rotationPitch - par1EntityProjectile.prevRotationPitch) * par9 - 180, 0.0F, 0.0F, 1.0F);

             //Render a block or 3D item
             GlStateManager.translate(-0.6f, -0.6f, 0);
             RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();
             GlStateManager.pushMatrix();
             renderItem.renderItem(item, net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType.FIXED);
             GlStateManager.popMatrix();
        }
        else //If the render is a sprite
        {
	        TextureAtlasSprite icon = getSprite(item);

	        this.bindTexture(net.minecraft.client.renderer.texture.TextureMap.LOCATION_BLOCKS_TEXTURE);
	        this.renderSprite(buffer, tessellator, icon);
        }
        GlStateManager.disableRescaleNormal();
        GlStateManager.popMatrix();
    }

    private TextureAtlasSprite getSprite(ItemStack item) {
        Item it = item.getItem();
        String tex = ModItems.ITEM_TEXTURES.get(it);
        if (it instanceof ItemVariantTexture) {
            tex = ((ItemVariantTexture) it).getTextureName(item.getItemDamage());
        }
        if (tex != null) {
            tex = ModItems.resolveTexturePath(tex);
            TextureAtlasSprite sprite = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(tex);
            if (sprite != null) {
                return sprite;
            }
        }
        return Minecraft.getMinecraft().getTextureMapBlocks().getMissingSprite();
    }

    private void renderSprite(BufferBuilder buffer, Tessellator tessellator, TextureAtlasSprite par2Icon)
    {
        float f = par2Icon.getMinU();
        float f1 = par2Icon.getMaxU();
        float f2 = par2Icon.getMinV();
        float f3 = par2Icon.getMaxV();
        float f4 = 1.0F;
        float f5 = 0.5F;
        float f6 = 0.25F;
        GlStateManager.rotate(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(-this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        GlStateManager.glNormal3f(0.0F, 1.0F, 0.0F);
        buffer.pos((double)(0.0F - f5), (double)(0.0F - f6), 0.0D).tex((double)f, (double)f3).endVertex();
        buffer.pos((double)(f4 - f5), (double)(0.0F - f6), 0.0D).tex((double)f1, (double)f3).endVertex();
        buffer.pos((double)(f4 - f5), (double)(f4 - f6), 0.0D).tex((double)f1, (double)f2).endVertex();
        buffer.pos((double)(0.0F - f5), (double)(f4 - f6), 0.0D).tex((double)f, (double)f2).endVertex();
        tessellator.draw();
    }

	@Override
	protected ResourceLocation getEntityTexture(EntityProjectile par1Entity) {
        return par1Entity.isArrow() ? field_110780_a : net.minecraft.client.renderer.texture.TextureMap.LOCATION_BLOCKS_TEXTURE;
	}
}
