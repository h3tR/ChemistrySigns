package chem_signs.ghs;

import chem_signs.Tags;
import chem_signs.net.PacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GHSContentSelectorGUI extends GuiScreen {

    private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(Tags.MOD_ID,"textures/blocks/ghs/sign.png");
    private static final int BACKGROUND_SIZE = 180;
    private static final ResourceLocation SYMBOLS = new ResourceLocation(Tags.MOD_ID, "textures/blocks/ghs/symbols.png");
    private static final int SYMBOL_SIZE = 90;



    private final GHSTileEntity te;
    private int symbol;
    public GHSContentSelectorGUI(GHSTileEntity te) {
        this.te = te;
        this.symbol = te.getSymbol();
    }

    
    
    @Override
    public void initGui() {
        int centerX = this.width / 2;
        int centerY = this.height / 2;
        //ugly code ew


        this.addButton(new GuiButton(0,centerX + BACKGROUND_SIZE / 2 - BACKGROUND_SIZE / 8, centerY,10,10,"->"){
            @Override
            public void mouseReleased(int mouseX, int mouseY) {
                if(symbol<8)
                    symbol+=1;
            }

            @Override
            public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
                if (this.visible)
                {
                    FontRenderer fontrenderer = mc.fontRenderer;
                    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                    this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
                    GlStateManager.enableBlend();
                    GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
                    GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
                    this.mouseDragged(mc, mouseX, mouseY);
                    int j = 0x808080;

                    if (packedFGColour != 0) {
                        j = packedFGColour;
                    } else if (!this.enabled) {
                        j = 10526880;
                    } else if (this.hovered) {
                        j = 0x000000;
                    }

                    this.drawCenteredString(fontrenderer, this.displayString, this.x , this.y - 3, j);
                }
            }
        });

        this.addButton(new GuiButton(1,centerX - BACKGROUND_SIZE / 2 + BACKGROUND_SIZE / 8, centerY,10,10,"<-"){
            @Override
            public void mouseReleased(int mouseX, int mouseY) {
                if(symbol>0)
                    symbol-=1;
            }

            @Override
            public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
                if (this.visible)
                {
                    FontRenderer fontrenderer = mc.fontRenderer;
                    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                    this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
                    GlStateManager.enableBlend();
                    GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
                    GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
                    this.mouseDragged(mc, mouseX, mouseY);
                    int j = 0x808080;

                    if (packedFGColour != 0) {
                        j = packedFGColour;
                    } else if (!this.enabled) {
                        j = 10526880;
                    } else if (this.hovered) {
                        j = 0x000000;
                    }

                    this.drawCenteredString(fontrenderer, this.displayString, this.x , this.y - 3, j);
                }
            }
        });

        this.addButton(new GuiButton(2, this.width / 2 - 100, this.height * 3 / 4, I18n.format("gui.done")){
            @Override
            public void mouseReleased(int mouseX, int mouseY) {
                GHSContentSelectorGUI.this.mc.displayGuiScreen(null);
            }
        });
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRenderer, I18n.format("sign.edit"), this.width / 2, 20, 16777215);
        GlStateManager.pushMatrix();

        GlStateManager.enableBlend();
        GlStateManager.disableLighting();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

        drawSignBackground();
        
        this.mc.getTextureManager().bindTexture(SYMBOLS);

        drawSymbol();

        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
    
    private void drawSignBackground() {
        GlStateManager.pushMatrix();

        this.mc.getTextureManager().bindTexture(BACKGROUND_TEXTURE);
        int centerX = this.width / 2;
        int centerY = this.height / 2;

        Tessellator tess = Tessellator.getInstance();
        BufferBuilder buffer = tess.getBuffer();

        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        buffer.pos(centerX , centerY + BACKGROUND_SIZE / 2, 0).tex(0, .5).endVertex(); // Bottom-left
        buffer.pos(centerX + BACKGROUND_SIZE / 2, centerY, 0).tex(.5, .5).endVertex(); // Bottom-right
        buffer.pos(centerX, centerY - BACKGROUND_SIZE / 2, 0).tex(.5, 0).endVertex(); // Top-right
        buffer.pos(centerX - BACKGROUND_SIZE / 2, centerY , 0).tex(0, 0).endVertex(); // Top-left
        tess.draw();
        GlStateManager.popMatrix();

    }
    
    private void drawSymbol() {
        GlStateManager.pushMatrix();

        Tessellator tess = Tessellator.getInstance();
        BufferBuilder buffer = tess.getBuffer();

        int centerX = this.width / 2;
        int centerY = this.height / 2;

        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        buffer.pos(centerX - SYMBOL_SIZE / 2,centerY + SYMBOL_SIZE / 2, 0.1).tex(1/9F * symbol, 1).endVertex();  // Top-left
        buffer.pos(centerX + SYMBOL_SIZE / 2, centerY + SYMBOL_SIZE / 2, 0.1).tex(1/9F + 1/9F * symbol, 1).endVertex();   // Top-right
        buffer.pos(centerX + SYMBOL_SIZE / 2, centerY - SYMBOL_SIZE / 2, 0.1).tex(1/9F + 1/9F * symbol, 0).endVertex();  // Bottom-right
        buffer.pos(centerX - SYMBOL_SIZE / 2, centerY - SYMBOL_SIZE / 2, 0.1).tex(1/9F * symbol, 0).endVertex(); // Bottom-left
        tess.draw();
        GlStateManager.popMatrix();

    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
        PacketHandler.INSTANCE.sendToServer(new CPacketUpdateGHS(symbol,te.getPos()));
        te.setSymbol(symbol);
    }
}
