package net.congueror.cgalaxy.entity.villagers;

import com.google.common.collect.ImmutableMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.congueror.cgalaxy.CGalaxy;
import net.congueror.cgalaxy.util.events.AddVillagerProfessionsEvent;
import net.congueror.cgalaxy.init.CGBlockInit;
import net.congueror.cgalaxy.init.CGEnchantmentInit;
import net.congueror.cgalaxy.init.CGItemInit;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.MinecraftForge;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;

public class LunarVillagerProfession implements IProfession {

    public static final ArrayList<LunarVillagerProfession> professions = registerProfessions();
    public final String name;
    public final Int2ObjectMap<VillagerTrades.ItemListing[]> trades;
    public final ResourceLocation clothesTexture;
    public final ResourceLocation professionClothesTexture;

    public static void init() {}

    public LunarVillagerProfession(String name, Int2ObjectMap<VillagerTrades.ItemListing[]> trades, ResourceLocation clothesTexture, ResourceLocation professionClothesTexture) {
        this.name = name;
        this.trades = trades;
        this.clothesTexture = clothesTexture;
        this.professionClothesTexture = professionClothesTexture;
    }

    public static ArrayList<LunarVillagerProfession> registerProfessions() {
        ArrayList<LunarVillagerProfession> profs = new ArrayList<>();
        profs.add(new LunarVillagerProfession("cartographer", LunarVillagerTrades.toIntMap(ImmutableMap.of(
                0, new VillagerTrades.ItemListing[] {
                        new LunarVillagerTrades.SapphiresToItems(Items.COMPASS, 10, 1, 5, 1),
                },
                1, new VillagerTrades.ItemListing[] {
                        new LunarVillagerTrades.ItemsToSapphires(Items.COMPASS, 10, 1, 10, 1),
                },
                2, new VillagerTrades.ItemListing[] {
                        new LunarVillagerTrades.SapphiresToItems(makeSouthPoleCompass(), 29, 5, 6),
                }
        )), new ResourceLocation(CGalaxy.MODID, "textures/entity/lunar_villager_clothes.png"),
                new ResourceLocation(CGalaxy.MODID, "textures/entity/lunar_cartographer.png")));

        profs.add(new LunarVillagerProfession("farmer", LunarVillagerTrades.toIntMap(ImmutableMap.of(
                0, new VillagerTrades.ItemListing[] {
                        new LunarVillagerTrades.ItemsToSapphires(CGBlockInit.LUNAR_CARROTS.get().asItem(), 33, 1000, 1),
                        new LunarVillagerTrades.ItemsToSapphires(CGBlockInit.LUNAR_CARROTS.get().asItem(), 23, 1000, 1),
                        new LunarVillagerTrades.ItemsToSapphires(CGBlockInit.LUNAR_CARROTS.get().asItem(), 20, 1000, 1),
                        new LunarVillagerTrades.ItemsToSapphires(CGBlockInit.LUNAR_CARROTS.get().asItem(), 30, 1000, 1),
                },
                1, new VillagerTrades.ItemListing[] {
                        new LunarVillagerTrades.SapphiresToItems(CGBlockInit.LUNAR_CARROTS.get(), 1, 12, 100, 1),
                        new LunarVillagerTrades.SapphiresToItems(CGBlockInit.LUNAR_CARROTS.get(), 1, 17, 100, 1),
                        new LunarVillagerTrades.SapphiresToItems(CGBlockInit.LUNAR_CARROTS.get(), 1, 19, 100, 1),
                        new LunarVillagerTrades.SapphiresToItems(CGBlockInit.LUNAR_CARROTS.get(), 1, 9, 100, 1),
                },
                2, new VillagerTrades.ItemListing[] {
                        new LunarVillagerTrades.SapphiresToItems(CGBlockInit.MOON_REGOLITH.get(), 1, 41, 5, 1),
                        new LunarVillagerTrades.SapphiresToItems(CGBlockInit.MOON_REGOLITH.get(), 1, 53, 5, 1),
                        new LunarVillagerTrades.SapphiresToItems(CGBlockInit.MOON_REGOLITH.get(), 1, 39, 5, 1),
                        new LunarVillagerTrades.SapphiresToItems(CGBlockInit.MOON_REGOLITH.get(), 1, 64, 5, 1),
                },
                3, new VillagerTrades.ItemListing[] {
                        new LunarVillagerTrades.SapphiresToItems(CGItemInit.DIAMOND_APPLE.get(), 12, 1, 5, 1),
                        new LunarVillagerTrades.SapphiresToItems(CGItemInit.DIAMOND_APPLE.get(), 17, 1, 5, 1),
                        new LunarVillagerTrades.SapphiresToItems(CGItemInit.DIAMOND_APPLE.get(), 14, 1, 5, 1),
                },
                4, new VillagerTrades.ItemListing[] {
                        new LunarVillagerTrades.SapphiresToItems(CGBlockInit.LUNAC_CROP.get(), 128, 1, 5, 1),
                }
        )), CGalaxy.location("textures/entity/lunar_villager_clothes.png"),
                CGalaxy.location("textures/entity/lunar_farmer.png")));

        AddVillagerProfessionsEvent event = new AddVillagerProfessionsEvent(profs);
        MinecraftForge.EVENT_BUS.post(event);
        profs.addAll(event.getAddedProfessions());
        if (profs.stream().map(lunarVillagerProfession -> lunarVillagerProfession.name).anyMatch(s -> Collections.frequency(profs, s) > 1)) {
            throw new IllegalArgumentException("Duplicate lunar villager professions present!");
        }
        return profs;
    }

    @Nullable
    public static LunarVillagerProfession getProfessionFromString(String name) {
        return professions.stream().filter(lunarVillagerProfession -> lunarVillagerProfession.name.equals(name)).findFirst().orElse(null);
    }

    private static ItemStack makeSouthPoleCompass() {
        ItemStack stack = new ItemStack(Items.COMPASS, 1);
        stack.setHoverName(new TranslatableComponent("item.minecraft.compass"));
        stack.enchant(CGEnchantmentInit.POLARIZED.get(), 1);
        return stack;
    }
}
