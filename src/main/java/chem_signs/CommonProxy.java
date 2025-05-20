package chem_signs;

import chem_signs.net.PacketHandler;
import chem_signs.nfpa704.NFPA704Block;
import chem_signs.nfpa704.NFPA704ItemBlock;
import chem_signs.nfpa704.NFPA704TileEntity;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod.EventBusSubscriber
public class CommonProxy {


    public void preInit(FMLPreInitializationEvent event) {
        PacketHandler.registerMessages(Tags.MOD_ID);

    }

    public void init(FMLInitializationEvent event) {
        NetworkRegistry.INSTANCE.registerGuiHandler(ChemistrySigns.instance, new NFPA704Block.GuiProxy());

        GameRegistry.registerTileEntity(NFPA704TileEntity.class, Tags.MOD_ID + "_nfpa704");
      //  GameRegistry.registerTileEntity(GHSTileEntity.class, Tags.MOD_ID + "_ghs");
    }

    public void postInit(FMLPostInitializationEvent event) {
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        event.getRegistry().register(new NFPA704Block());
        //event.getRegistry().register(new GHSBlock());
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(new NFPA704ItemBlock(Blocks.nfpa704).setRegistryName(Blocks.nfpa704.getRegistryName()));
        //event.getRegistry().register(new ItemBlock(Blocks.ghs).setRegistryName(Blocks.ghs.getRegistryName()));

    }

}
