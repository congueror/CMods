package net.congueror.clib.api.recipe;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class FluidIngredient implements Predicate<FluidStack> {

    public static final FluidIngredient EMPTY = new FluidIngredient();

    @Nullable
    private final Tag.Named<Fluid> tag;
    @Nullable
    private final Fluid fluid;
    private final int amount;

    public FluidIngredient(Tag.Named<Fluid> tag, Fluid fluid, int amount) {
        this.tag = tag;
        this.fluid = fluid;
        this.amount = amount;
    }

    public FluidIngredient(Fluid fluid, int amount) {
        this(null, fluid, amount);
    }

    public FluidIngredient(Tag.Named<Fluid> tag, int amount) {
        this(tag, null, amount);
    }

    public FluidIngredient() {
        this(null, null, 1000);
    }

    /**
     * Checks whether the given {@link FluidStack} matches the ingredient.
     * @return true if the {@link FluidStack} matches the recipe's ingredient.
     */
    @Override
    public boolean test(FluidStack fluidStack) {
        return (tag != null && fluidStack.getFluid().is(tag)) || (fluid != null && fluidStack.getFluid().equals(fluid)) && fluidStack.getAmount() >= amount;
    }

    public List<FluidStack> getFluidStacks() {
        if (fluid != null) {
            return Collections.singletonList(new FluidStack(fluid, amount));
        } else if (tag != null) {
            return tag.getValues().stream().map(fluid1 -> new FluidStack(fluid1, amount)).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    public static FluidIngredient read(FriendlyByteBuf buf) {
        ResourceLocation id = buf.readResourceLocation();
        int amount = buf.readVarInt();
        boolean isTag = buf.readBoolean();
        return isTag ? new FluidIngredient(FluidTags.bind(id.toString()), amount) : new FluidIngredient(ForgeRegistries.FLUIDS.getValue(id), amount);
    }

    public void write(FriendlyByteBuf buf) {
        boolean isTag = (tag != null);
        buf.writeBoolean(isTag);
        if (isTag) {
            buf.writeResourceLocation(tag.getName());
        } else if (fluid != null) {
            buf.writeResourceLocation(fluid.getRegistryName());
        } else {
            buf.writeResourceLocation(new ResourceLocation("null"));
        }
        buf.writeVarInt(amount);
    }

    public static FluidIngredient deserialize(JsonElement json) {
        if (json != null && json.isJsonObject()) {
            JsonObject obj = json.getAsJsonObject();
            int amount = GsonHelper.getAsInt(obj, "amount", 100);
            if (obj.has("fluid")) {
                ResourceLocation fluidId = new ResourceLocation(GsonHelper.getAsString(obj, "fluid"));
                return new FluidIngredient(ForgeRegistries.FLUIDS.getValue(fluidId), amount);
            }
            if (obj.has("tag")) {
                ResourceLocation tagId = new ResourceLocation(GsonHelper.getAsString(obj, "tag"));
                return new FluidIngredient(FluidTags.bind(tagId.toString()), amount);
            }
            throw new JsonSyntaxException("Must have either \"fluid\" or \"tag\"");
        }
        throw new JsonSyntaxException("ingredient must not be null");
    }
}
