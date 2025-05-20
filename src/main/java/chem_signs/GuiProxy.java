package chem_signs;

import chem_signs.ghs.GHSContentSelectorGUI;
import chem_signs.ghs.GHSTileEntity;
import chem_signs.nfpa704.NFPA704ContentSelectorGUI;
import chem_signs.nfpa704.NFPA704TileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import javax.annotation.Nullable;

public class GuiProxy implements IGuiHandler {

        @Nullable
        @Override
        public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
            return null;
        }

        @Override
        public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
            BlockPos pos = new BlockPos(x, y, z);
            TileEntity te = world.getTileEntity(pos);
            if (te instanceof NFPA704TileEntity) {
                NFPA704TileEntity nfpa704TileEntity = (NFPA704TileEntity) te;
                return new NFPA704ContentSelectorGUI(nfpa704TileEntity);
            } else if (te instanceof GHSTileEntity) {
                GHSTileEntity ghsTileEntity = (GHSTileEntity) te;
                return new GHSContentSelectorGUI(ghsTileEntity);
            }
            return null;
        }

}
