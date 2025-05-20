package chem_signs.nfpa704;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class NFPA704TileEntity extends TileEntity {

    private int[] signData = {0,0,0,-1};

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.signData = compound.getIntArray("signData");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setIntArray("signData", this.signData);
        return super.writeToNBT(compound);
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        return writeToNBT(new NBTTagCompound());
    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        NBTTagCompound nbtTag = new NBTTagCompound();
        this.writeToNBT(nbtTag);
        return new SPacketUpdateTileEntity(getPos(), 1, nbtTag);
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
        this.readFromNBT(packet.getNbtCompound());
    }


    public int getFireHazardCode(){
        return this.signData[0];
    }
    public int getHealthHazardCode(){
        return this.signData[1];
    }
    public int getReactivityCode(){
        return this.signData[2];
    }
    public int getSpecificHazardCode(){
        return this.signData[3];
    }

    public void setRawSignData(int[] signData){
        this.signData = signData;
    }

}
