package chem_signs;

import chem_signs.ghs.GHSItemBlock;
import chem_signs.nfpa704.NFPA704ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class Items {
    @GameRegistry.ObjectHolder("chem_signs:nfpa704")
    public static NFPA704ItemBlock nfpa704 = (NFPA704ItemBlock) (new NFPA704ItemBlock(Blocks.nfpa704).setRegistryName(Blocks.nfpa704.getRegistryName()));

    @GameRegistry.ObjectHolder("chem_signs:ghs")
    public static GHSItemBlock ghs = (GHSItemBlock) (new GHSItemBlock(Blocks.ghs).setRegistryName(Blocks.ghs.getRegistryName()));

}
