package net.congueror.clib.blocks.solar_generator;

import com.google.gson.JsonObject;
import net.congueror.clib.api.recipe.ItemRecipe;
import net.congueror.clib.init.CLRecipeSerializerInit;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistryEntry;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class SolarGeneratorRecipe implements ItemRecipe<Container> {
    final ResourceLocation id;
    float dayIntensity;
    float nightIntensity;
    String dimension;
    String texture;

    public SolarGeneratorRecipe(ResourceLocation id) {
        this.id = id;
    }

    public float getDayIntensity() {
        return dayIntensity;
    }

    public float getNightIntensity() {
        return nightIntensity;
    }

    public ResourceKey<Level> getDimension() {
        return ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(dimension));
    }

    public String getTexture() {
        return texture;
    }

    @Override
    public int getProcessTime() {
        return 1;
    }

    @Override
    public boolean matches(@NotNull Container container, Level level) {
        return level.dimension().equals(getDimension());
    }

    @NotNull
    @Override
    public ItemStack getResultItem() {
        return ItemStack.EMPTY;
    }

    @Override
    public @NotNull ResourceLocation getId() {
        return id;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return CLRecipeSerializerInit.SOLAR_ENERGY.get();
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return CLRecipeSerializerInit.Types.SOLAR_ENERGY;
    }

    public static class Serializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<SolarGeneratorRecipe> {

        @Override
        public @NotNull SolarGeneratorRecipe fromJson(@NotNull ResourceLocation id, @NotNull JsonObject serializedRecipe) {
            SolarGeneratorRecipe recipe = new SolarGeneratorRecipe(id);
            recipe.dimension = GsonHelper.getAsString(serializedRecipe, "dimension", Level.OVERWORLD.location().toString());
            recipe.dayIntensity = GsonHelper.getAsFloat(serializedRecipe, "dayIntensity", 1.0f);
            recipe.nightIntensity = GsonHelper.getAsFloat(serializedRecipe, "nightIntensity", 0.0f);
            recipe.texture = GsonHelper.getAsString(serializedRecipe, "modTexture", "clib:textures/gui/overworld.png");
            return recipe;
        }

        @Nullable
        @Override
        public SolarGeneratorRecipe fromNetwork(@NotNull ResourceLocation id, FriendlyByteBuf buf) {
            SolarGeneratorRecipe recipe = new SolarGeneratorRecipe(id);
            recipe.dimension = buf.readUtf();
            recipe.dayIntensity = buf.readFloat();
            recipe.nightIntensity = buf.readFloat();
            recipe.texture = buf.readUtf();
            return recipe;
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, SolarGeneratorRecipe recipe) {
            buf.writeUtf(recipe.dimension);
            buf.writeFloat(recipe.dayIntensity);
            buf.writeFloat(recipe.nightIntensity);
            buf.writeUtf(recipe.texture);
        }
    }
}
