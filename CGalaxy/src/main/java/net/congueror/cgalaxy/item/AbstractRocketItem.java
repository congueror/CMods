package net.congueror.cgalaxy.item;

import net.congueror.cgalaxy.blocks.launch_pad.LaunchPadBlock;
import net.congueror.cgalaxy.entity.AbstractRocket;
import net.congueror.cgalaxy.init.CGBlockInit;
import net.congueror.clib.api.objects.items.CLItem;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.IItemRenderProperties;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Consumer;

public abstract class AbstractRocketItem extends CLItem {

    public AbstractRocketItem(Properties properties) {
        super(properties);
    }

    public int getFuel(ItemStack stack) {
        if (stack.getItem() instanceof AbstractRocketItem) {
            return stack.getOrCreateTag().getInt("Fuel");
        } else {
            return 0;
        }
    }

    /**
     * Client only
     */
    public int getCapacity() {
        return newRocketEntity(Minecraft.getInstance().level, 1000).getCapacity();
    }

    @Nonnull
    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        Level level = pContext.getLevel();
        if (level.isClientSide) {
            return InteractionResult.PASS;
        }
        BlockPos pos = pContext.getClickedPos();
        BlockState state = level.getBlockState(pos);
        Player player = pContext.getPlayer();
        if (state.is(CGBlockInit.LAUNCH_PAD.get()) && player != null) {
            LaunchPadBlock launchPad = (LaunchPadBlock) (state.getBlock());
            ItemStack stack = pContext.getItemInHand();
            float rot = 0.0f;
            switch (player.getDirection()) {
                case EAST -> rot = 90.0f;
                case NORTH -> rot = 0.0f;
                case WEST -> rot = -90.0f;
                case SOUTH -> rot = 180.0f;
            }
            AbstractRocket entity = newRocketEntity(level, 0);
            int fuel = stack.getOrCreateTag().getInt("Fuel");
            if (launchPad.spawnRocket(level, pos, entity, fuel, rot)) {
                stack.shrink(1);
                return InteractionResult.CONSUME;
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    public void fillItemCategory(@Nonnull CreativeModeTab pCategory, @Nonnull NonNullList<ItemStack> pItems) {
        ItemStack stack = new ItemStack(this);
        stack.getOrCreateTag().putInt("Fuel", 1000);
        if (this.allowdedIn(pCategory)) {
            pItems.add(new ItemStack(this));
            pItems.add(stack);
        }
    }

    @Override
    public boolean isBarVisible(@NotNull ItemStack stack) {
        return getFuel(stack) != getCapacity();
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        AbstractRocketItem item = (AbstractRocketItem) stack.getItem();
        return Math.round(13.0F - (item.getCapacity() - item.getFuel(stack)) * 13.0F / (float)item.getCapacity());
    }

    @Override
    public int getBarColor(ItemStack p_150901_) {
        return 0xFFE5E5C0;
    }

    @Override
    public void appendHoverText(@Nonnull ItemStack pStack, @Nullable net.minecraft.world.level.Level pLevel, java.util.List<Component> pTooltipComponents, @Nonnull TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(new TranslatableComponent("key.cgalaxy.fuel").append(": ").withStyle(ChatFormatting.AQUA).append(getFuel(pStack) + "/" + getCapacity()).withStyle(ChatFormatting.GREEN));
    }

    @Override
    public void initializeClient(@Nonnull Consumer<IItemRenderProperties> consumer) {
        Minecraft mc = Minecraft.getInstance();
        if (mc != null)
            consumer.accept(new IItemRenderProperties() {
                @Override
                public BlockEntityWithoutLevelRenderer getItemStackRenderer() {
                    return newBlockEntityWithoutLevelRenderer(mc.getBlockEntityRenderDispatcher(), mc.getEntityModels());
                }
            });
    }

    public abstract AbstractRocket newRocketEntity(Level level, int fuel);

    public abstract BlockEntityWithoutLevelRenderer newBlockEntityWithoutLevelRenderer(BlockEntityRenderDispatcher dispatcher, EntityModelSet modelSet);
}
