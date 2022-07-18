package net.congueror.clib.compat.jei;

import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.congueror.clib.CLib;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractJeiCategory<T> implements IRecipeCategory<T> {
    protected static final ResourceLocation TEXTURE = new ResourceLocation(CLib.MODID, "textures/gui/jei_gui.png");
    protected final RecipeType<T> recipeType;
    protected final IGuiHelper helper;
    protected boolean isGenerator;
    protected int fePerTick;

    protected final IDrawableStatic arrow;
    protected final IDrawableAnimated arrow_anim;
    protected final IDrawableStatic arrow_vertical;
    protected final IDrawableAnimated arrow_vertical_anim;
    protected final IDrawableStatic energy_slot;
    protected final IDrawableStatic energy_glass;
    protected final IDrawableAnimated energy;
    protected final IDrawableStatic tank;
    protected final IDrawableStatic slot;

    protected final String localized;

    public AbstractJeiCategory(RecipeType<T> recipeType, IGuiHelper helper, String translationKey, int processTime, int energyCapacity, int fePerTick, boolean isGenerator) {
        this.recipeType = recipeType;
        this.helper = helper;
        this.isGenerator = isGenerator;
        this.fePerTick = fePerTick;
        int energySpeed = energyCapacity / fePerTick;

        arrow = helper.drawableBuilder(TEXTURE, 213, 60, 24, 17).build();
        arrow_anim = helper.drawableBuilder(TEXTURE, 190, 60, 24, 17).buildAnimated(processTime, IDrawableAnimated.StartDirection.LEFT, false);
        arrow_vertical = helper.drawableBuilder(TEXTURE, 222, 30, 17, 30).build();
        arrow_vertical_anim = helper.drawableBuilder(TEXTURE, 222, 0, 17, 30).buildAnimated(processTime, IDrawableAnimated.StartDirection.BOTTOM, false);
        energy_slot = helper.drawableBuilder(TEXTURE, 172, 18, 18, 62).build();
        energy_glass = helper.drawableBuilder(TEXTURE, 206, 0, 16, 60).build();
        energy = helper.drawableBuilder(TEXTURE, 190, 0, 16, 60).buildAnimated(energySpeed, isGenerator ? IDrawableAnimated.StartDirection.BOTTOM : IDrawableAnimated.StartDirection.TOP, !isGenerator);
        tank = helper.drawableBuilder(TEXTURE, 172, 80, 18, 52).build();
        slot = helper.drawableBuilder(TEXTURE, 172, 0, 18, 18).build();

        localized = I18n.get(translationKey);
    }

    @Override
    public @NotNull RecipeType<T> getRecipeType() {
        return recipeType;
    }

    @Override
    public ResourceLocation getUid() {
        return recipeType.getUid();
    }

    @Override
    public Class<? extends T> getRecipeClass() {
        return recipeType.getRecipeClass();
    }

    public IDrawable createIcon(ItemLike item) {
        return helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(item));
    }

    @Override
    public Component getTitle() {
        return new TextComponent(localized);
    }

    @Override
    public IDrawable getBackground() {
        return helper.createDrawable(TEXTURE, 0, 0, 172, 63);
    }

    @Override
    public void draw(T recipe, IRecipeSlotsView recipeSlotsView, PoseStack stack, double mouseX, double mouseY) {
        energy_slot.draw(stack, 150, 1);
        energy.draw(stack, 151, 2);
        energy_glass.draw(stack, 151, 2);
    }

    @Override
    public List<Component> getTooltipStrings(T recipe, IRecipeSlotsView recipeSlotsView, double mouseX, double mouseY) {
        List<Component> list = new ArrayList<>();
        if (mouseX >= 151 && mouseX <= 167 && mouseY >= 2 && mouseY <= 62) {
            if (isGenerator) {
                list.add(new TranslatableComponent("key.clib.energy_generation").append(": " + fePerTick + "FE/t"));
            } else {
                list.add(new TranslatableComponent("key.clib.energy_usage").append(": " + fePerTick + "FE/t"));
            }
        }
        return list;
    }
}
