package net.congueror.clib.util.registry.material_builders;

import net.congueror.clib.blocks.generic.CLBuddingBlock;
import net.congueror.clib.util.registry.ResourceBuilder;
import net.minecraft.data.recipes.FinishedRecipe;
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

        public Builder(String modid, String name, CreativeModeTab tab, DeferredRegister<Block> blockRegister, DeferredRegister<Item> itemRegister) {
            super(modid, name, tab, blockRegister, itemRegister);
            addShard();
            addDust();
            addCrystalBlock();
            addSmallBud();
            addMediumBud();
            addLargeBud();
            addCluster();
            addBudding();
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
