package chem_signs.nfpa704;

import chem_signs.Tags;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec2f;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class NFPA704SpecialRenderer extends TileEntitySpecialRenderer<NFPA704TileEntity> {

    private static final ResourceLocation GLYPHS = new ResourceLocation(Tags.MOD_ID, "textures/blocks/nfpa704/glyphs.png");
    private static final ResourceLocation SPECIAL_NOTICE = new ResourceLocation(Tags.MOD_ID, "textures/blocks/nfpa704/special_notice.png");
    private static final float GLYPH_SIZE = 1/2F/8*5F;
    private static final Vec2f GLYPH_DIMENSIONS = new Vec2f(.25F,.35F);
    private static final Vec2f SPECIAL_NOTICE_DIMENSIONS = new Vec2f(.44F,.32F);

    @Override
    public void render(NFPA704TileEntity te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        GlStateManager.pushAttrib();
        GlStateManager.pushMatrix();

        // Translate to the location of our tile entity
        int k = te.getBlockMetadata();
        float f2 = 0.0F;

        if (k == 2)
        {
            f2 = 180.0F;
        }

        if (k == 4)
        {
            f2 = 90.0F;
        }

        if (k == 5)
        {
            f2 = -90.0F;
        }

        GlStateManager.translate((float)x + .5F, (float)y + 0.5F, (float)z +.5F);
        GlStateManager.rotate(-f2, 0.0F, 1.0F, 0.0F);

        Tessellator tess = Tessellator.getInstance();
        Minecraft.getMinecraft().getTextureManager().bindTexture(GLYPHS);
        GlStateManager.disableLighting();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GlStateManager.color(1f,1f,1f, 1f);

        //Fire Hazard
        GlStateManager.pushMatrix();
        GlStateManager.translate(0, 0.25/8*7, -7/16F+.001);
        renderGlyph(te.getFireHazardCode(),tess);
        GlStateManager.popMatrix();

        //Health Hazard
        GlStateManager.pushMatrix();
        GlStateManager.translate(-0.25/8*7, 0, -7/16F+.001);
        renderGlyph(te.getHealthHazardCode(),tess);

        GlStateManager.popMatrix();

        //Reactivity
        GlStateManager.pushMatrix();
        GlStateManager.translate(0.25/8*7, 0, -7/16F+.001 );

        renderGlyph(te.getReactivityCode(),tess);
        GlStateManager.popMatrix();

        if (te.getSpecificHazardCode() != -1) {
            Minecraft.getMinecraft().getTextureManager().bindTexture(SPECIAL_NOTICE);
            GlStateManager.translate(0, -0.25/8*7, -7/16F+.001 );
            renderSpecialNotice(te.getSpecificHazardCode(),tess);
        }

        GlStateManager.enableLighting();
        GlStateManager.disableBlend();

        GlStateManager.popMatrix();
        GlStateManager.popAttrib();
    }

    private void renderSpecialNotice(int index, Tessellator tess) {

        BufferBuilder buffer = tess.getBuffer();
        GlStateManager.scale(GLYPH_SIZE, -GLYPH_SIZE, GLYPH_SIZE);
        GlStateManager.glNormal3f(0.0F, 0.0F, -GLYPH_SIZE);
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        buffer.pos(-SPECIAL_NOTICE_DIMENSIONS.x, SPECIAL_NOTICE_DIMENSIONS.y, 0.01).tex(1/4F*index, 1).endVertex();  // Top-left
        buffer.pos(SPECIAL_NOTICE_DIMENSIONS.x, SPECIAL_NOTICE_DIMENSIONS.y, 0.01).tex(1/4F + 1/4F*index, 1).endVertex();   // Top-right
        buffer.pos(SPECIAL_NOTICE_DIMENSIONS.x, -SPECIAL_NOTICE_DIMENSIONS.y, 0.01).tex(1/4F + 1/4F*index, 0).endVertex();  // Bottom-right
        buffer.pos(-SPECIAL_NOTICE_DIMENSIONS.x, -SPECIAL_NOTICE_DIMENSIONS.y, 0.01).tex(1/4F*index, 0).endVertex(); // Bottom-left
        tess.draw();

    }

    private void renderGlyph(int index, Tessellator tess) {

        BufferBuilder buffer = tess.getBuffer();
        GlStateManager.scale(GLYPH_SIZE, -GLYPH_SIZE, GLYPH_SIZE);
        GlStateManager.glNormal3f(0.0F, 0.0F, -GLYPH_SIZE);
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        buffer.pos(-GLYPH_DIMENSIONS.x, GLYPH_DIMENSIONS.y, 0.01).tex(1/5F*index, 1).endVertex();  // Top-left
        buffer.pos(GLYPH_DIMENSIONS.x, GLYPH_DIMENSIONS.y, 0.01).tex(1/5F + 1/5F*index, 1).endVertex();   // Top-right
        buffer.pos(GLYPH_DIMENSIONS.x, -GLYPH_DIMENSIONS.y, 0.01).tex(1/5F + 1/5F*index, 0).endVertex();  // Bottom-right
        buffer.pos(-GLYPH_DIMENSIONS.x, -GLYPH_DIMENSIONS.y, 0.01).tex(1/5F*index, 0).endVertex(); // Bottom-left
        tess.draw();

    }

}
