package chem_signs;

import chem_signs.ghs.GHSBlock;
import chem_signs.nfpa704.NFPA704Block;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Blocks {
    @GameRegistry.ObjectHolder("chem_signs:nfpa704")
    public static NFPA704Block nfpa704 = new NFPA704Block();

    @GameRegistry.ObjectHolder("chem_signs:ghs")
    public static GHSBlock ghs = new GHSBlock();

    @SideOnly(Side.CLIENT)
    public static void initModels() {
        nfpa704.initModel();
        ghs.initModel();
    }

}
