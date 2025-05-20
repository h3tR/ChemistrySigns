package chem_signs.nfpa704;


import net.minecraft.item.ItemBlock;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class NFPA704ItemBlock extends ItemBlock {
    public NFPA704ItemBlock(Block block) {
        super(block);
        this.setTileEntityItemStackRenderer(new NFPA704ItemRenderer());
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean hasCustomEntity(ItemStack stack) {
        return true;
    }

}
