package chem_signs.ghs;

import chem_signs.diamond_sign.DiamondSignBlock;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.Random;

public class GHSBlock extends DiamondSignBlock implements ITileEntityProvider {

    public GHSBlock() {
        super(Material.IRON);
        setRegistryName("ghs");
        setTranslationKey("chem_signs.block.ghs");
        setHarvestLevel("pickaxe",0);
        setCreativeTab(CreativeTabs.DECORATIONS);
        setHardness(.1F);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return null; //Items.ghs;
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        super.initModel();
       //TODO ClientRegistry.bindTileEntitySpecialRenderer(GHSTileEntity.class, new NFPA704SpecialRenderer());
    }


    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new GHSTileEntity();
    }
}
