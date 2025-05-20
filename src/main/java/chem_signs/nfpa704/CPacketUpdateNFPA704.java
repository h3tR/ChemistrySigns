package chem_signs.nfpa704;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CPacketUpdateNFPA704 implements IMessage {
    private int[] signData = {0,0,0,-1};
    private BlockPos blockPos;

    public CPacketUpdateNFPA704() {}

    @Override
    public void fromBytes(ByteBuf buf) {
        signData[3] = buf.readInt();
        signData[2] = buf.readInt();
        signData[1] = buf.readInt();
        signData[0] = buf.readInt();
        blockPos = BlockPos.fromLong(buf.readLong());
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(signData[3]);
        buf.writeInt(signData[2]);
        buf.writeInt(signData[1]);
        buf.writeInt(signData[0]);
        buf.writeLong(blockPos.toLong());

    }

    public CPacketUpdateNFPA704(int[] signData, BlockPos teBlockPos) {
        this.signData = signData;
        blockPos = teBlockPos;
    }

    public static class Handler implements IMessageHandler<CPacketUpdateNFPA704, IMessage> {
        @Override
        public IMessage onMessage(CPacketUpdateNFPA704 message, MessageContext ctx) {
            FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
            return null;
        }

        private void handle(CPacketUpdateNFPA704 message, MessageContext ctx) {
            EntityPlayerMP playerEntity = ctx.getServerHandler().player;
            World world = playerEntity.getEntityWorld();
            if (world.isBlockLoaded(message.blockPos) && world.getTileEntity(message.blockPos) instanceof NFPA704TileEntity) {
                NFPA704TileEntity te = (NFPA704TileEntity)world.getTileEntity(message.blockPos);
                te.setRawSignData(sanitizeSignData(message.signData));
            }
        }

        private int[] sanitizeSignData(int[] signData) {
            int[] sanitizedSignData = new int[]{0,0,0,-1};
            if(signData.length != 4) return sanitizedSignData;
            for (int i = 0; i < 3; i++) {
                if(signData[i] <= 4 && signData[i] >= 0) sanitizedSignData[i] = signData[i];
            }
            if(signData[3] <= 3 && signData[3] >= -1) sanitizedSignData[3] = signData[3];
            return sanitizedSignData;
        }
    }
}
