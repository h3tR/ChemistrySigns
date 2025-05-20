package chem_signs.diamond_sign;

import chem_signs.Tags;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.IModel;

import java.util.HashMap;
import java.util.Map;

public class DiamondSignBakedModelLoader implements ICustomModelLoader {

    private static final Map<String, DiamondSignModel> models = new HashMap<>();

    static {
        DiamondSignBakedModelLoader.addModel(new ResourceLocation(Tags.MOD_ID,"nfpa704"),new ResourceLocation(Tags.MOD_ID,"blocks/nfpa704/sign"));
        DiamondSignBakedModelLoader.addModel(new ResourceLocation(Tags.MOD_ID,"ghs"),new ResourceLocation(Tags.MOD_ID,"blocks/ghs/sign"));
    }

    @Override
    public void onResourceManagerReload(IResourceManager resourceManager) {

    }

    @Override
    public boolean accepts(ResourceLocation modelLocation) {
        if(!modelLocation.getNamespace().equals(Tags.MOD_ID)) return false;

        return models.containsKey(modelLocation.getPath());
    }

    @Override
    public IModel loadModel(ResourceLocation modelLocation) {
        return models.get(modelLocation.getPath());
    }

    public static void addModel(ResourceLocation modelLocation, ResourceLocation textureLocation) {
        models.put(modelLocation.getPath(), new DiamondSignModel(textureLocation));
    }
}
