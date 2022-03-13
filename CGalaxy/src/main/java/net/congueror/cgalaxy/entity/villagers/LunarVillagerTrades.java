package net.congueror.cgalaxy.entity.villagers;

import com.google.common.collect.ImmutableMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.congueror.cgalaxy.init.CGItemInit;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

public class LunarVillagerTrades {
    public static Int2ObjectMap<VillagerTrades.ItemListing[]> toIntMap(ImmutableMap<Integer, VillagerTrades.ItemListing[]> pMap) {
        return new Int2ObjectOpenHashMap<>(pMap);
    }

    public static class ItemsToSapphires implements VillagerTrades.ItemListing {
        private final Item item;
        private final int cost;
        private final int sapphireAmount;
        private final int maxUses;
        private final int villagerXp;
        private final float priceMultiplier;

        public ItemsToSapphires(Item item, int cost, int maxUses, int villagerXp) {
            this(item, cost, 1, maxUses, villagerXp);
        }

        public ItemsToSapphires(ItemLike pItem, int pCost, int sapphireAmount, int pMaxUses, int pVillagerXp) {
            this.item = pItem.asItem();
            this.cost = pCost;
            this.sapphireAmount = sapphireAmount;
            this.maxUses = pMaxUses;
            this.villagerXp = pVillagerXp;
            this.priceMultiplier = 0.05F;
        }

        @Override
        public MerchantOffer getOffer(@Nonnull Entity pTrader, @Nonnull Random pRand) {
            ItemStack itemstack = new ItemStack(this.item, this.cost);
            return new MerchantOffer(itemstack, new ItemStack(CGItemInit.ASTRAL_SAPPHIRE.get()), this.maxUses, this.villagerXp, this.priceMultiplier);
        }
    }

    public static class ItemsAndEmeraldsToItems implements VillagerTrades.ItemListing {
        private final ItemStack fromItem;
        private final int fromCount;
        private final int emeraldCost;
        private final ItemStack toItem;
        private final int toCount;
        private final int maxUses;
        private final int villagerXp;
        private final float priceMultiplier;

        public ItemsAndEmeraldsToItems(ItemLike pForItem, int pFromCount, Item pToItem, int pToCount, int pMaxUses, int pVillagerXp) {
            this(pForItem, pFromCount, 1, pToItem, pToCount, pMaxUses, pVillagerXp);
        }

        public ItemsAndEmeraldsToItems(ItemLike pForItem, int pFromCount, int pEmeraldCost, Item pToItem, int pToCount, int pMaxUses, int pVillagerXp) {
            this.fromItem = new ItemStack(pForItem);
            this.fromCount = pFromCount;
            this.emeraldCost = pEmeraldCost;
            this.toItem = new ItemStack(pToItem);
            this.toCount = pToCount;
            this.maxUses = pMaxUses;
            this.villagerXp = pVillagerXp;
            this.priceMultiplier = 0.05F;
        }

        @Nullable
        @Override
        public MerchantOffer getOffer(@Nonnull Entity pTrader, @Nonnull Random pRand) {
            return new MerchantOffer(new ItemStack(Items.EMERALD, this.emeraldCost), new ItemStack(this.fromItem.getItem(), this.fromCount), new ItemStack(this.toItem.getItem(), this.toCount), this.maxUses, this.villagerXp, this.priceMultiplier);
        }
    }

    public static class SapphiresToItems implements VillagerTrades.ItemListing {
        private final ItemStack itemStack;
        private final int sapphireCost;
        private final int maxUses;
        private final int villagerXp;
        private final float priceMultiplier;

        public SapphiresToItems(Block pBlock, int sapphireCost, int pNumberOfItems, int pMaxUses, int pVillagerXp) {
            this(new ItemStack(pBlock, pNumberOfItems), sapphireCost, pMaxUses, pVillagerXp);
        }

        public SapphiresToItems(Item pItem, int sapphireCost, int pNumberOfItems, int pVillagerXp) {
            this(new ItemStack(pItem, pNumberOfItems), sapphireCost, 12, pVillagerXp);
        }

        public SapphiresToItems(Item pItem, int sapphireCost, int pNumberOfItems, int pMaxUses, int pVillagerXp) {
            this(new ItemStack(pItem, pNumberOfItems), sapphireCost, pMaxUses, pVillagerXp);
        }

        public SapphiresToItems(ItemStack pItemStack, int sapphireCost, int pMaxUses, int pVillagerXp) {
            this(pItemStack, sapphireCost, pMaxUses, pVillagerXp, 0.05F);
        }

        public SapphiresToItems(ItemStack pItemStack, int sapphireCost, int pMaxUses, int pVillagerXp, float pPriceMultiplier) {
            this.itemStack = pItemStack;
            this.sapphireCost = sapphireCost;
            this.maxUses = pMaxUses;
            this.villagerXp = pVillagerXp;
            this.priceMultiplier = pPriceMultiplier;
        }

        @Override
        public MerchantOffer getOffer(@Nonnull Entity pTrader, @Nonnull Random pRand) {
            if (sapphireCost > 64) {
                return new MerchantOffer(new ItemStack(CGItemInit.ASTRAL_SAPPHIRE.get(), 64),
                        new ItemStack(CGItemInit.ASTRAL_SAPPHIRE.get(), this.sapphireCost - 64),
                        this.itemStack, this.maxUses, this.villagerXp, this.priceMultiplier);
            }
            return new MerchantOffer(new ItemStack(CGItemInit.ASTRAL_SAPPHIRE.get(), this.sapphireCost),
                    this.itemStack, this.maxUses, this.villagerXp, this.priceMultiplier);
        }
    }
}
