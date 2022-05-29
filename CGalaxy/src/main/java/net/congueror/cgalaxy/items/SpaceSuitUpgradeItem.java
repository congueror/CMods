package net.congueror.cgalaxy.items;

import net.minecraft.world.item.Item;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Supplier;

public class SpaceSuitUpgradeItem extends Item {

    private final int defense;
    private final float toughness;
    private final float knockbackResistance;
    private final Supplier<SpaceSuitUpgradeItem>[] previousTiers;

    @SafeVarargs
    public SpaceSuitUpgradeItem(Properties properties, int defense, float toughness, float knockbackResistance, Supplier<SpaceSuitUpgradeItem>... previousTier) {
        super(properties.stacksTo(4));
        this.defense = defense;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
        this.previousTiers = previousTier;
    }

    public int getDefense() {
        return defense;
    }

    public float getToughness() {
        return toughness;
    }

    public float getKnockbackResistance() {
        return knockbackResistance;
    }

    public SpaceSuitUpgradeItem[] getPreviousTiers() {
        return Arrays.stream(previousTiers)
                .filter(itemSupplier -> itemSupplier.get() != null)
                .map(Supplier::get)
                .toArray(SpaceSuitUpgradeItem[]::new);
    }

    public static boolean isApplicable(SpaceSuitUpgradeItem suitUpgradeItem, SpaceSuitUpgradeItem upgradeToApply) {
        if (upgradeToApply.getPreviousTiers().length != 0) {
            for (SpaceSuitUpgradeItem item : upgradeToApply.getPreviousTiers()) {
                if (Objects.equals(suitUpgradeItem, item)) {
                    return true;
                }
            }
        } else {
            return suitUpgradeItem == null;
        }
        return false;
    }
}
