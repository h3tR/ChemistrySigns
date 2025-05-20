package chem_signs.nfpa704;

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

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class NFPA704ContentSelectorGUI extends GuiScreen {

    private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(Tags.MOD_ID,"textures/blocks/nfpa704/sign.png");
    private static final int BACKGROUND_SIZE = 180;
    private static final ResourceLocation GLYPHS = new ResourceLocation(Tags.MOD_ID, "textures/blocks/nfpa704/glyphs.png");
    private static final int GLYPH_HEIGHT = 21;
    private static final int GLYPH_WIDTH = GLYPH_HEIGHT/7*5;

    private static final ResourceLocation SPECIAL_NOTICE = new ResourceLocation(Tags.MOD_ID, "textures/blocks/nfpa704/special_notice.png");
    private static final int SPECIAL_NOTICE_WIDTH = 22;
    private static final int SPECIAL_NOTICE_HEIGHT = SPECIAL_NOTICE_WIDTH/11*8;

    private static final int DEFAULT_OFFSET = (int) (BACKGROUND_SIZE/2/8*3.5F);

    private final NFPA704TileEntity te;
    private final List<GuiSelector> selectors = new ArrayList<>();
    private final int[] signData;
    public NFPA704ContentSelectorGUI(NFPA704TileEntity te) {
        this.te = te;
        this.signData = new int[]{te.getFireHazardCode(), te.getHealthHazardCode(), te.getReactivityCode(), te.getSpecificHazardCode()};
    }

    
    
    @Override
    public void initGui() {
        int centerX = this.width / 2;
        int centerY = this.height / 2;
        //ugly code ew
        selectors.add(new GuiSelector(0,centerX, centerY - DEFAULT_OFFSET,(change)->{
            if(change>0 && signData[0]>=4) return;
            else if(change<0 && signData[0]<=0) return;
            signData[0] += change;
        },"+","-"));
        selectors.add(new GuiSelector(2,centerX - DEFAULT_OFFSET, centerY,(change)->{
            if(change>0 && signData[1]>=4) return;
            else if(change<0 && signData[1]<=0) return;
            signData[1] += change;
        },"+","-"));
        selectors.add(new GuiSelector(4,centerX + DEFAULT_OFFSET, centerY,(change)->{
            if(change>0 && signData[2]>=4) return;
            else if(change<0 && signData[2]<=0) return;
            signData[2] += change;
        },"+","-"));
        selectors.add(new GuiSelector(6,centerX, centerY + DEFAULT_OFFSET,(change)->{
            if(change>0 && signData[3]>=3) return;
            else if(change<0 && signData[3]<=-1) return;
            signData[3] += change;
        },"->","<-"));
        for(GuiSelector selector : selectors) {
            this.addButton(selector.next);
            this.addButton(selector.prev);
        }

        this.addButton(new GuiButton(8, this.width / 2 - 100, this.height * 3 / 4, I18n.format("gui.done")){
            @Override
            public void mouseReleased(int mouseX, int mouseY) {
                NFPA704ContentSelectorGUI.this.mc.displayGuiScreen(null);
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
        
        this.mc.getTextureManager().bindTexture(GLYPHS);

        int centerX = this.width / 2;
        int centerY = this.height / 2;
        drawGlyph(signData[0],centerX, centerY - DEFAULT_OFFSET);
        drawGlyph(signData[1],centerX - DEFAULT_OFFSET, centerY);
        drawGlyph(signData[2],centerX + DEFAULT_OFFSET, centerY);

        if(signData[3]!=-1)
            drawSpecialNotice();

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
        buffer.pos(centerX , centerY + (double) BACKGROUND_SIZE / 2, 0).tex(0, .5).endVertex(); // Bottom-left
        buffer.pos(centerX + (double) BACKGROUND_SIZE / 2, centerY, 0).tex(.5, .5).endVertex(); // Bottom-right
        buffer.pos(centerX, centerY - (double) BACKGROUND_SIZE / 2, 0).tex(.5, 0).endVertex(); // Top-right
        buffer.pos(centerX - (double) BACKGROUND_SIZE / 2, centerY , 0).tex(0, 0).endVertex(); // Top-left
        tess.draw();
        GlStateManager.popMatrix();

    }
    
    private void drawGlyph(int glyph, int x, int y) {
        GlStateManager.pushMatrix();

        Tessellator tess = Tessellator.getInstance();
        BufferBuilder buffer = tess.getBuffer();

        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        buffer.pos(x-GLYPH_WIDTH, y+GLYPH_HEIGHT, 0.1).tex(.2F*glyph, 1).endVertex();  // Top-left
        buffer.pos(x+GLYPH_WIDTH, y+GLYPH_HEIGHT, 0.1).tex(.2F + .2F*glyph, 1).endVertex();   // Top-right
        buffer.pos(x+GLYPH_WIDTH, y-GLYPH_HEIGHT, 0.1).tex(.2F + .2F*glyph, 0).endVertex();  // Bottom-right
        buffer.pos(x-GLYPH_WIDTH, y-GLYPH_HEIGHT, 0.1).tex(.2F*glyph, 0).endVertex(); // Bottom-left
        tess.draw();
        GlStateManager.popMatrix();

    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
        PacketHandler.INSTANCE.sendToServer(new CPacketUpdateNFPA704(signData,te.getPos()));
        te.setRawSignData(signData);
    }

    private void drawSpecialNotice() {
        GlStateManager.pushMatrix();

        this.mc.getTextureManager().bindTexture(SPECIAL_NOTICE);

        Tessellator tess = Tessellator.getInstance();
        BufferBuilder buffer = tess.getBuffer();

        int x = this.width / 2;
        int y = this.height / 2 + DEFAULT_OFFSET;

        int index = signData[3];

        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        buffer.pos(x-SPECIAL_NOTICE_WIDTH, y+SPECIAL_NOTICE_HEIGHT, 0.1).tex(.25F*index, 1).endVertex();  // Top-left
        buffer.pos(x+SPECIAL_NOTICE_WIDTH, y+SPECIAL_NOTICE_HEIGHT, 0.1).tex(.25F + .25F*index, 1).endVertex();   // Top-right
        buffer.pos(x+SPECIAL_NOTICE_WIDTH, y-SPECIAL_NOTICE_HEIGHT, 0.1).tex(.25F + .25F*index, 0).endVertex();  // Bottom-right
        buffer.pos(x-SPECIAL_NOTICE_WIDTH, y-SPECIAL_NOTICE_HEIGHT, 0.1).tex(.25F*index, 0).endVertex(); // Bottom-left
        tess.draw();
        GlStateManager.popMatrix();

    }

    private static class GuiSelector{
        public GuiButton next;
        public GuiButton prev;

        private GuiSelector(int nextid, int x, int y, Consumer<Integer> changeValue, String nextText, String prevText) {
            this.next = new GuiButton(nextid++,x+2+DEFAULT_OFFSET/8*7,y,10,10,nextText){
                @Override
                public void mouseReleased(int mouseX, int mouseY) {
                    changeValue.accept(1);
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

                        this.drawCenteredString(fontrenderer, this.displayString, this.x , this.y-3, j);
                    }
                }
            };
            this.prev = new GuiButton(nextid,x-2-DEFAULT_OFFSET/8*7,y,10,10,prevText){
                @Override
                public void mouseReleased(int mouseX, int mouseY) {
                    changeValue.accept(-1);
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

                        this.drawCenteredString(fontrenderer, this.displayString, this.x , this.y-3, j);
                    }
                }
            };
        }
    }
}
