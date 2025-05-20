package chem_signs.ghs;

import chem_signs.ChemistrySigns;
import chem_signs.Items;
import chem_signs.diamond_sign.DiamondSignBlock;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.Random;

public class GHSBlock extends DiamondSignBlock implements ITileEntityProvider {

    public static final int GUI_ID = 2;


    public GHSBlock() {
        setRegistryName("ghs");
        setTranslationKey("chem_signs.block.ghs");
        setHarvestLevel("pickaxe",0);
        setCreativeTab(CreativeTabs.DECORATIONS);
        setHardness(.1F);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Items.ghs;
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        super.initModel();
        ClientRegistry.bindTileEntitySpecialRenderer(GHSTileEntity.class, new GHSSpecialRenderer());
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
        if (placer instanceof EntityPlayer ){
            ((EntityPlayer)placer).openGui(ChemistrySigns.instance, GUI_ID, worldIn, pos.getX(), pos.getY(), pos.getZ());
        }
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new GHSTileEntity();
    }

}
