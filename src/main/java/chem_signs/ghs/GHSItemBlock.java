package chem_signs.ghs;


import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GHSItemBlock extends ItemBlock {
    public GHSItemBlock(Block block) {
        super(block);
        this.setTileEntityItemStackRenderer(new GHSItemRenderer());
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean hasCustomEntity(ItemStack stack) {
        return true;
    }

}
