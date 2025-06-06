package chem_signs.diamond_sign;

import com.google.common.collect.ImmutableSet;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.TRSRTransformation;

import java.util.Collection;
import java.util.function.Function;

public class DiamondSignModel implements IModel {
    private final ResourceLocation texture;

    public DiamondSignModel(ResourceLocation texture) {
        this.texture = texture;
    }

    @Override
    public IBakedModel bake(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
        return new DiamondSignBakedModel(bakedTextureGetter.apply(texture), format);
    }

    @Override
    public Collection<ResourceLocation> getTextures() {
        return ImmutableSet.of(texture);
    }

    @Override
    public IModelState getDefaultState() {
        return TRSRTransformation.identity();
    }
}
