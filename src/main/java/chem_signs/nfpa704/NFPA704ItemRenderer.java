package chem_signs.nfpa704;

import chem_signs.Tags;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class NFPA704ItemRenderer extends TileEntityItemStackRenderer {

    private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(Tags.MOD_ID,"textures/blocks/nfpa704/sign.png");
    //TODO: adapt this to DiamondSign... also turn this into a bakedmodel
    @Override
    public void renderByItem(ItemStack itemStackIn) {
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableLighting();
        GlStateManager.disableCull();
        GlStateManager.color(1f, 1f, 1f, 1f);
        Minecraft.getMinecraft().getTextureManager().bindTexture(BACKGROUND_TEXTURE);

        GlStateManager.translate(0.5F, 0.5F, 0.5F);

        GlStateManager.scale(1.0F, 1.0F, 1.0F);

        Tessellator tess = Tessellator.getInstance();
        BufferBuilder buffer = tess.getBuffer();

        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        //front
        buffer.pos(0 , .5, 0).tex(.5, 0).endVertex();
        buffer.pos(-.5, 0, 0).tex(0, 0).endVertex();
        buffer.pos(0, -.5, 0).tex(0, .5).endVertex();
        buffer.pos(.5, 0 , 0).tex(.5, .5).endVertex();

        //top left
        buffer.pos(0 , .5, -1/16F).tex(1, 0).endVertex();
        buffer.pos(-.5, 0, -1/16F).tex(.5, 0).endVertex();
        buffer.pos(-.5, 0, 0).tex(0, 0).endVertex();
        buffer.pos(0 , .5, 0).tex(.5, 0).endVertex();
        //bottom left
        buffer.pos(-.5, 0, -1/16F).tex(1, 0).endVertex();
        buffer.pos(0, -.5, -1/16F).tex(1, .5).endVertex();
        buffer.pos(0, -.5, 0).tex(.5, .5).endVertex();
        buffer.pos(-.5, 0, 0).tex(.5, 0).endVertex();
        //bottom right
        buffer.pos(0, -.5, -1/16F).tex(1, 0).endVertex();
        buffer.pos(.5, 0 , -1/16F).tex(1, .5).endVertex();
        buffer.pos(.5, 0 , 0).tex(.5, .5).endVertex();
        buffer.pos(0, -.5, 0).tex(.5, 0).endVertex();
        //top right
        buffer.pos(.5, 0 , -1/16F).tex(1, 0).endVertex();
        buffer.pos(0 , .5, -1/16F).tex(1, .5).endVertex();
        buffer.pos(0 , .5, 0).tex(.5, .5).endVertex();
        buffer.pos(.5, 0 , 0).tex(.5, 0).endVertex();

        //back
        buffer.pos(0 , .5, -1/16F).tex(1, 0).endVertex();
        buffer.pos(.5, 0 , -1/16F).tex(1, .5).endVertex();
        buffer.pos(0, -.5, -1/16F).tex(.5, .5).endVertex();
        buffer.pos(-.5, 0, -1/16F).tex(.5, 0).endVertex();

        tess.draw();

        GlStateManager.enableLighting();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }
}
