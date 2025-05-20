package chem_signs.ghs;

import chem_signs.Tags;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class GHSSpecialRenderer extends TileEntitySpecialRenderer<GHSTileEntity> {

    private static final ResourceLocation SYMBOLS = new ResourceLocation(Tags.MOD_ID, "textures/blocks/ghs/symbols.png");
    private static final float SYMBOL_SIZE = 2/4F;

    @Override
    public void render(GHSTileEntity te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
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
        Minecraft.getMinecraft().getTextureManager().bindTexture(SYMBOLS);
        GlStateManager.disableLighting();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GlStateManager.color(1f,1f,1f, 1f);

        GlStateManager.pushMatrix();
        GlStateManager.translate(0, 0, -7/16F+.001);

        int symbol = te.getSymbol();

        BufferBuilder buffer = tess.getBuffer();
        GlStateManager.scale(SYMBOL_SIZE, -SYMBOL_SIZE, SYMBOL_SIZE);
        GlStateManager.glNormal3f(0.0F, 0.0F, -SYMBOL_SIZE);
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        buffer.pos(-SYMBOL_SIZE, SYMBOL_SIZE, 0.01).tex(1/9F*symbol, 1).endVertex();  // Top-left
        buffer.pos(SYMBOL_SIZE, SYMBOL_SIZE, 0.01).tex(1/9F + 1/9F*symbol, 1).endVertex();   // Top-right
        buffer.pos(SYMBOL_SIZE, -SYMBOL_SIZE, 0.01).tex(1/9F + 1/9F*symbol, 0).endVertex();  // Bottom-right
        buffer.pos(-SYMBOL_SIZE, -SYMBOL_SIZE, 0.01).tex(1/9F*symbol, 0).endVertex(); // Bottom-left
        tess.draw();
        
        
        GlStateManager.popMatrix();
        
        GlStateManager.enableLighting();
        GlStateManager.disableBlend();

        GlStateManager.popMatrix();
        GlStateManager.popAttrib();
    }
}
