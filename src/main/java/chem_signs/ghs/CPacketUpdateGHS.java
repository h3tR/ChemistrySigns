package chem_signs.ghs;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CPacketUpdateGHS implements IMessage {
    private int symbol = 0;
    private BlockPos blockPos;

    public CPacketUpdateGHS() {}

    @Override
    public void fromBytes(ByteBuf buf) {
        symbol = buf.readInt();
        blockPos = BlockPos.fromLong(buf.readLong());
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(symbol);
        buf.writeLong(blockPos.toLong());

    }

    public CPacketUpdateGHS(int symbol, BlockPos teBlockPos) {
        this.symbol = symbol;
        blockPos = teBlockPos;
    }

    public static class Handler implements IMessageHandler<CPacketUpdateGHS, IMessage> {
        @Override
        public IMessage onMessage(CPacketUpdateGHS message, MessageContext ctx) {
            FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
            return null;
        }

        private void handle(CPacketUpdateGHS message, MessageContext ctx) {
            EntityPlayerMP playerEntity = ctx.getServerHandler().player;
            World world = playerEntity.getEntityWorld();
            if (world.isBlockLoaded(message.blockPos) && world.getTileEntity(message.blockPos) instanceof GHSTileEntity) {
                GHSTileEntity te = (GHSTileEntity)world.getTileEntity(message.blockPos);
                te.setSymbol(sanitizeSymbol(message.symbol));
            }
        }

        private int sanitizeSymbol(int symbol) {
            if(symbol <= 8 && symbol >= 0) return symbol;
            return 0;
        }
    }
}
