package kamkeel.plugin.TileEntity;

import kamkeel.plugin.Blocks.BlockColor;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityBlockColorData extends TileEntity
{
    public float[][] palette;
    private int rerenderTimer;
    private int rerenderDelay;

    public TileEntityBlockColorData() {
        this.palette = new float[16][3];
        this.rerenderTimer = 0;
        this.rerenderDelay = 20;
        for (int i = 0; i < 16; ++i) {
            for (int j = 0; j < 3; ++j) {
                if (this.palette[i][j] == 0.0f) {
                    this.palette[i][j] = BlockColor.initColor[i][j];
                }
            }
        }
    }

    @Override
    public void readFromNBT(final NBTTagCompound par1NBTTagCompound) {
        super.readFromNBT(par1NBTTagCompound);
        for (int i = 0; i < 16; ++i) {
            for (int j = 0; j < 3; ++j) {
                if (par1NBTTagCompound.hasKey("col" + i + "_" + j)) {
                    if (par1NBTTagCompound.getTag("col" + i + "_" + j) instanceof NBTTagFloat) {
                        this.palette[i][j] = par1NBTTagCompound.getFloat("col" + i + "_" + j);
                    }
                }
                else {
                    this.palette[i][j] = BlockColor.initColor[i][j];
                }
            }
        }
    }

    @Override
    public NBTTagCompound writeToNBT(final NBTTagCompound par1NBTTagCompound) {
        super.writeToNBT(par1NBTTagCompound);
        for (int i = 0; i < 16; ++i) {
            for (int j = 0; j < 3; ++j) {
                par1NBTTagCompound.setFloat("col" + i + "_" + j, this.palette[i][j]);
            }
        }
        return par1NBTTagCompound;
    }

    public void setColor(final int metadata, final float r, final float g, final float b) {
        if (this.world.isRemote) {
            return;
        }
        if (this.palette[metadata][0] == r && this.palette[metadata][1] == g && this.palette[metadata][2] == b) {
            return;
        }
        this.palette[metadata][0] = r;
        this.palette[metadata][1] = g;
        this.palette[metadata][2] = b;
        boolean notDefault = false;
        for (int i = 0; i < 16 && !notDefault; ++i) {
            for (int j = 0; j < 3 && !notDefault; ++j) {
                if (this.palette[i][j] != BlockColor.initColor[i][j]) {
                    notDefault = true;
                    break;
                }
            }
        }
        if (notDefault) {
            this.world.notifyBlockUpdate(this.pos, this.world.getBlockState(this.pos), this.world.getBlockState(this.pos), 3);
        }
        else {
            this.world.setBlockToAir(this.pos);
        }
        this.markDirty();
    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        final NBTTagCompound t = new NBTTagCompound();
        this.writeToNBT(t);
        return new SPacketUpdateTileEntity(this.pos, 4, t);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onDataPacket(final NetworkManager net, final SPacketUpdateTileEntity pkt) {
        this.readFromNBT(pkt.getNbtCompound());
        if (this.world.isRemote && this.rerenderTimer == 0) {
            this.world.markBlockRangeForRenderUpdate(new BlockPos(this.pos.getX(), 0, this.pos.getZ()), new BlockPos(this.pos.getX() + 16, 255, this.pos.getZ() + 16));
            this.rerenderTimer = this.rerenderDelay;
            this.rerenderDelay *= (int)1.1;
        }
    }

}
