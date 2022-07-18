package net.congueror.clib.util.registry.builders.material_builders;

import net.congueror.clib.blocks.generic.CLBuddingBlock;
import net.congueror.clib.util.registry.builders.ResourceBuilder;
import net.congueror.clib.util.registry.builders.BlockBuilder;
import net.congueror.clib.util.registry.builders.ItemBuilder;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.AmethystClusterBlock;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Consumer;

import static net.congueror.clib.util.registry.data.RecipeDataProvider.*;

public record GeodeGemObject(RegistryObject<Item> shard,
                             RegistryObject<Item> dust,
                             RegistryObject<Block> block,
                             RegistryObject<AmethystClusterBlock> small_bud,
                             RegistryObject<AmethystClusterBlock> medium_bud,
                             RegistryObject<AmethystClusterBlock> large_bud,
                             RegistryObject<AmethystClusterBlock> cluster,
                             RegistryObject<CLBuddingBlock> budding) implements ResourceBuilder.ResourceObject {

    @Override
    public void generateRecipes(Consumer<FinishedRecipe> c) {
        Block block = block().get();
        Item shard = shard().get();
        Item dust = dust().get();

        shaped2x2Recipe(c, block, 1, getTag("forge:shards/", shard));
        dustRecipes(c, dust, shard, getTag("forge:shards/", shard));
    }

    public static class Builder extends ResourceBuilder<Builder, GeodeGemObject> {

        protected ItemBuilder<Item> shard;
        protected ItemBuilder<Item> dust;
        protected BlockBuilder<Block, BlockItem> crystal_block;
        protected BlockBuilder<AmethystClusterBlock, BlockItem> small_bud;
        protected BlockBuilder<AmethystClusterBlock, BlockItem> medium_bud;
        protected BlockBuilder<AmethystClusterBlock, BlockItem> large_bud;
        protected BlockBuilder<AmethystClusterBlock, BlockItem> cluster;
        protected BlockBuilder<CLBuddingBlock, BlockItem> budding;

        public Builder(String modid, String name, CreativeModeTab tab, DeferredRegister<Block> blockRegister, DeferredRegister<Item> itemRegister) {
            super(modid, name, tab, blockRegister, itemRegister);
            shard = addShard();
            dust = addDust();
            crystal_block = addCrystalBlock();
            small_bud = addSmallBud();
            medium_bud = addMediumBud();
            large_bud = addLargeBud();
            cluster = addCluster(shard);
            budding = addBudding(small_bud, medium_bud, large_bud, cluster);
        }

        public Builder withCrystalBlockTranslation(String translation, String locale) {
            crystal_block.withTranslation(translation, locale);
            return this;
        }

        public GeodeGemObject buildObject() {
            return new GeodeGemObject(
                    shard.build(),
                    dust.build(),
                    crystal_block.build(),
                    small_bud.build(),
                    medium_bud.build(),
                    large_bud.build(),
                    cluster.build(),
                    budding.build());
        }
    }
}
