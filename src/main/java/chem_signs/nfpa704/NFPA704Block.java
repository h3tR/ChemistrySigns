package chem_signs.nfpa704;

import chem_signs.ChemistrySigns;
import chem_signs.Items;
import chem_signs.diamond_sign.DiamondSignBlock;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
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
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.Random;

public class NFPA704Block extends DiamondSignBlock implements ITileEntityProvider {

    public static final int GUI_ID = 1;

    public NFPA704Block() {
        super(Material.IRON);
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

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new NFPA704TileEntity();
    }

    public static class GuiProxy implements IGuiHandler {

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
            }
            return null;
        }
    }
}
