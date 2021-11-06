package net.congueror.cgalaxy.item;

import net.congueror.cgalaxy.blocks.launch_pad.LaunchPadBlock;
import net.congueror.cgalaxy.entity.AbstractRocket;
import net.congueror.cgalaxy.init.CGBlockInit;
import net.congueror.clib.api.objects.items.CLItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.IItemRenderProperties;

import javax.annotation.Nonnull;
import java.util.function.Consumer;

public abstract class AbstractRocketItem extends CLItem {

    public AbstractRocketItem(Properties properties) {
        super(properties);
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
