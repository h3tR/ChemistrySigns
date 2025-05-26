package chem_signs.nfpa704;

import chem_signs.ChemistrySigns;
import chem_signs.Items;
import chem_signs.compat.GTCompat;
import chem_signs.diamond_sign.DiamondSignBlock;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.Random;

public class NFPA704Block extends DiamondSignBlock implements ITileEntityProvider {

    public static final int GUI_ID = 1;

    public NFPA704Block() {
        setRegistryName("nfpa704");
        setTranslationKey("chem_signs.block.nfpa704");
        setHarvestLevel("pickaxe",0);
        setCreativeTab(CreativeTabs.DECORATIONS);
        setHardness(.1F);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Items.nfpa704;
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        super.initModel();
        ClientRegistry.bindTileEntitySpecialRenderer(NFPA704TileEntity.class, new NFPA704SpecialRenderer());
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
        if (placer instanceof EntityPlayer ){
            ((EntityPlayer)placer).openGui(ChemistrySigns.instance, GUI_ID, worldIn, pos.getX(), pos.getY(), pos.getZ());
        }
    }


    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {

        if(Loader.isModLoaded("gregtech") && GTCompat.UseScrewdriver(playerIn,hand)){
            playerIn.openGui(ChemistrySigns.instance, GUI_ID, worldIn, pos.getX(), pos.getY(), pos.getZ());
            return true;
        }

        return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new NFPA704TileEntity();
    }


}
