package chem_signs.ghs;


import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.FMLLaunchHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GHSItemBlock extends ItemBlock {
    public GHSItemBlock(Block block) {
        super(block);
        if (FMLLaunchHandler.side().isClient())
            setTEISR();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean hasCustomEntity(ItemStack stack) {
        return true;
    }


    @SideOnly(Side.CLIENT)
    public void setTEISR() {
        this.setTileEntityItemStackRenderer(new GHSItemRenderer());
    }
}
