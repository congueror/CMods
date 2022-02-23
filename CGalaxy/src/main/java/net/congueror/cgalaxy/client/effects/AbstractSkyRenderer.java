package net.congueror.cgalaxy.client.effects;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexBuffer;
import net.congueror.clib.util.RenderingHelper;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraftforge.client.ISkyRenderHandler;

public abstract class AbstractSkyRenderer implements ISkyRenderHandler {

    VertexBuffer starVBO;

    public AbstractSkyRenderer() {
        generateStars();
    }

    /**
     * Fork of {@link net.minecraft.client.renderer.LevelRenderer}#createStars().
     */
    private void generateStars() {
        Tesselator tessellator = Tesselator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuilder();
        RenderSystem.setShader(GameRenderer::getPositionShader);

        if (this.starVBO != null) {
            this.starVBO.close();
        }

        this.starVBO = new VertexBuffer();
        RenderingHelper.renderStars(bufferbuilder, 3000);
        bufferbuilder.end();
        this.starVBO.upload(bufferbuilder);
    }
}
