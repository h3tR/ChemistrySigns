package chem_signs.diamond_sign;

import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.model.pipeline.UnpackedBakedQuad;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DiamondSignBakedModel implements IBakedModel {

    public static final ModelResourceLocation BAKED_MODEL = new ModelResourceLocation("chem_signs:diamond_sign");

    //Texture must be 32x32
    private final TextureAtlasSprite sprite;
    private final VertexFormat format;
    public DiamondSignBakedModel(TextureAtlasSprite sprite, VertexFormat format) {
        this.format = format;
        this.sprite = sprite;
    }

    //following 2 methods are adapted from https://www.mcjty.eu/docs/1.12/rendering/block-baked
    private void putVertex(UnpackedBakedQuad.Builder builder, Vec3d normal, double x, double y, double z, float u, float v) {
        for (int e = 0; e < format.getElementCount(); e++) {
            switch (format.getElement(e).getUsage()) {
                case POSITION:
                    builder.put(e, (float)x, (float)y, (float)z, 1.0f);
                    break;
                case COLOR:
                    builder.put(e, 1.0f, 1.0f, 1.0f, 1.0f);
                    break;
                case UV:
                    if (format.getElement(e).getIndex() == 0) {
                        u = sprite.getInterpolatedU(u/2);
                        v = sprite.getInterpolatedV(v/2);
                        builder.put(e, u, v, 0f, 1f);
                        break;
                    }
                case NORMAL:
                    builder.put(e, (float) normal.x, (float) normal.y, (float) normal.z, 0f);
                    break;
                default:
                    builder.put(e);
                    break;
            }
        }
    }

    private BakedQuad createQuad(EnumFacing dir,Vec3d v1, Vec3d v2, Vec3d v3, Vec3d v4, Vec2f uv1, Vec2f uv2) {
        UnpackedBakedQuad.Builder builder = new UnpackedBakedQuad.Builder(format);
        builder.setTexture(sprite);
        //I know these values kinda weird, I got them by trial and error and they work
        switch (dir) {
            case NORTH: {
                v1 = v1.rotateYaw((float) (-Math.PI)).add(1,0,1);
                v2 = v2.rotateYaw((float) (-Math.PI)).add(1,0,1);
                v3 = v3.rotateYaw((float) (-Math.PI)).add(1,0,1);
                v4 = v4.rotateYaw((float) (-Math.PI)).add(1,0,1);
            }
            case SOUTH: {
                v1 = v1.rotateYaw((float) (Math.PI/2)).add(0,0,1);
                v2 = v2.rotateYaw((float) (Math.PI/2)).add(0,0,1);
                v3 = v3.rotateYaw((float) (Math.PI/2)).add(0,0,1);
                v4 = v4.rotateYaw((float) (Math.PI/2)).add(0,0,1);
            }
            case WEST: {
                v1 = v1.rotateYaw((float) (Math.PI)).add(1,0,1);
                v2 = v2.rotateYaw((float) (Math.PI)).add(1,0,1);
                v3 = v3.rotateYaw((float) (Math.PI)).add(1,0,1);
                v4 = v4.rotateYaw((float) (Math.PI)).add(1,0,1);
            }
        }

        Vec3d normal = v3.subtract(v2).crossProduct(v1.subtract(v2)).normalize();

        putVertex(builder, normal, v1.x, v1.y, v1.z, uv1.x, uv1.y);
        putVertex(builder, normal, v2.x, v2.y, v2.z, uv1.x, uv2.y);
        putVertex(builder, normal, v3.x, v3.y, v3.z, uv2.x, uv2.y);
        putVertex(builder, normal, v4.x, v4.y, v4.z, uv2.x, uv1.y);
        return builder.build();
    }


    @Override
    public List<BakedQuad> getQuads(@Nullable IBlockState state, @Nullable EnumFacing side, long rand) {

        if (side != null) {
            return Collections.emptyList();
        }

        EnumFacing direction = state.getValue(BlockHorizontal.FACING);

        List<BakedQuad> quads = new ArrayList<>();
        //Default quads are for east direction
        quads.add(createQuad(direction,new Vec3d(0,.5,1),new Vec3d(0,1,.5),new Vec3d(0,.5,0),new Vec3d(0,0,.5), new Vec2f(16,0),new Vec2f(32,16)));
        quads.add(createQuad(direction,new Vec3d(1/16d,.5,1),new Vec3d(1/16d,0,.5),new Vec3d(1/16d,.5,0),new Vec3d(1/16d,1,.5), new Vec2f(0,0),new Vec2f(16,16)));
        quads.add(createQuad(direction,new Vec3d(0,0,.5),new Vec3d(0,.5,0),new Vec3d(1/16d,.5,0),new Vec3d(1/16d,0,.5), new Vec2f(0,16),new Vec2f(16,17)));
        quads.add(createQuad(direction,new Vec3d(0,.5,0),new Vec3d(0,1,.5),new Vec3d(1/16d,1,.5),new Vec3d(1/16d,.5,0), new Vec2f(0,17),new Vec2f(16,18)));
        quads.add(createQuad(direction,new Vec3d(0,1,.5),new Vec3d(0,.5,1),new Vec3d(1/16d,.5,1),new Vec3d(1/16d,1,.5), new Vec2f(0,18),new Vec2f(16,19)));
        quads.add(createQuad(direction,new Vec3d(0,.5,1),new Vec3d(0,0,.5),new Vec3d(1/16d,0,.5),new Vec3d(1/16d,.5,1), new Vec2f(0,19),new Vec2f(16,20)));
        return quads;
    }

    @Override
    public boolean isAmbientOcclusion() {
        return false;
    }

    @Override
    public boolean isGui3d() {
        return false;
    }

    @Override
    public boolean isBuiltInRenderer() {
        return false;
    }

    @Override
    public TextureAtlasSprite getParticleTexture() {
        return sprite;
    }

    @Override
    public ItemOverrideList getOverrides() {
        return ItemOverrideList.NONE;
    }
}
