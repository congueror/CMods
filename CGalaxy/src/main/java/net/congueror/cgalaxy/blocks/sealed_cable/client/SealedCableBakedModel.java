package net.congueror.cgalaxy.blocks.sealed_cable.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.data.EmptyModelData;
import net.minecraftforge.client.model.data.IDynamicBakedModel;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.client.model.data.ModelProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SealedCableBakedModel implements IDynamicBakedModel {
    public static final ModelProperty<BlockState> BLOCK_PROPERTY = new ModelProperty<>();

    private BakedModel model;
    private List<BakedQuad> cachedQuads;

    public SealedCableBakedModel() {
        this.model = Minecraft.getInstance().getBlockRenderer().getBlockModel(Blocks.STONE.defaultBlockState());
    }

    /**
     * Whenever a chunk where our block is in needs to be rerendered this method is called to return the quads (polygons)
     * for our model. Typically this will be called seven times: one time for every direction and one time in general.
     * If you have a block that is solid at one of the six sides it can be a good idea to render that face only for that
     * direction. That way Minecraft knows that it can get rid of that face when another solid block is adjacent to that.
     * All faces or quads that are generated for side == null are not going to be culled away like that
     * @param state the blockstate for our block
     * @param side the six directions or null for quads that are not at a specific direction
     * @param rand random generator that you can use to add variations to your model (usually for textures)
     * @param extraData this represents the data that is given to use from our block entity
     * @return a list of quads
     */
    @NotNull
    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @NotNull Random rand, @NotNull IModelData extraData) {
        Minecraft mc = Minecraft.getInstance();
        BlockState camo = extraData.getData(BLOCK_PROPERTY);

        if (camo == null || camo.is(Blocks.AIR)) {
            if (this.cachedQuads == null) {
                this.cachedQuads = addQuads(this.model, state, rand);
            }
            return this.cachedQuads;
        }

        this.model = mc.getBlockRenderer().getBlockModel(camo);
        return addQuads(this.model, camo, rand);
    }

    private static List<BakedQuad> addQuads(BakedModel model, BlockState state, Random random){
        List<BakedQuad> quads = new ArrayList<>();
        for(Direction direction : Direction.values())
            quads.addAll(model.getQuads(state, direction, random, EmptyModelData.INSTANCE));
        quads.addAll(model.getQuads(state, null, random, EmptyModelData.INSTANCE));
        return quads;
    }

    @NotNull
    @Override
    public IModelData getModelData(@NotNull BlockAndTintGetter level, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull IModelData modelData) {
        return level.getBlockEntity(pos) == null ? EmptyModelData.INSTANCE : level.getBlockEntity(pos).getModelData();
    }

    @Override
    public boolean useAmbientOcclusion() {
        return this.model.useAmbientOcclusion();
    }

    @Override
    public boolean isGui3d() {
        return this.model.isGui3d();
    }

    @Override
    public boolean usesBlockLight() {
        return this.model.usesBlockLight();
    }

    @Override
    public boolean isCustomRenderer() {
        return false;
    }

    @Override
    public TextureAtlasSprite getParticleIcon() {
        return this.model.getParticleIcon();
    }

    @Override
    public ItemOverrides getOverrides() {
        return ItemOverrides.EMPTY;
    }
}
