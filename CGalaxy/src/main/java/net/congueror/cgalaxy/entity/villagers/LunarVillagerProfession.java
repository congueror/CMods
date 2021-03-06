package net.congueror.cgalaxy.entity.villagers;

import com.google.common.collect.ImmutableMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.congueror.cgalaxy.CGalaxy;
import net.congueror.cgalaxy.api.events.AddVillagerProfessionsEvent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.MinecraftForge;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;

public class LunarVillagerProfession {

    public static final ArrayList<LunarVillagerProfession> professions = registerProfessions();
    public final String name;
    public final Int2ObjectMap<VillagerTrades.ItemListing[]> trades;
    public final ResourceLocation clothesTexture;
    public final ResourceLocation professionClothesTexture;

    public LunarVillagerProfession(String name, Int2ObjectMap<VillagerTrades.ItemListing[]> trades, ResourceLocation clothesTexture, ResourceLocation professionClothesTexture) {
        this.name = name;
        this.trades = trades;
        this.clothesTexture = clothesTexture;
        this.professionClothesTexture = professionClothesTexture;
    }

    public static ArrayList<LunarVillagerProfession> registerProfessions() {//TODO: More Professions, More Trades
        ArrayList<LunarVillagerProfession> profs = new ArrayList<>();
        profs.add(new LunarVillagerProfession("cartographer", LunarVillagerTrades.toIntMap(ImmutableMap.of(
                1, new VillagerTrades.ItemListing[]{
                        new LunarVillagerTrades.ItemsToEmeralds(Items.WHEAT, 20, 16, 2),
                        new LunarVillagerTrades.ItemsToEmeralds(Items.ANDESITE_STAIRS, 1, 1, 1)},
                2, new VillagerTrades.ItemListing[]{
                        new LunarVillagerTrades.ItemsToEmeralds(Items.ACACIA_BOAT, 1, 1, 1)}
        )), new ResourceLocation(CGalaxy.MODID, "textures/entity/lunar_villager_clothes.png")
                , new ResourceLocation(CGalaxy.MODID, "textures/entity/lunar_cartographer.png")));

        MinecraftForge.EVENT_BUS.post(new AddVillagerProfessionsEvent(profs));
        if (profs.stream().map(lunarVillagerProfession -> lunarVillagerProfession.name).anyMatch(s -> Collections.frequency(profs, s) > 1)) {
            throw new IllegalArgumentException("Duplicate lunar villager professions present!");
        }
        return profs;
    }

    @Nullable
    public static LunarVillagerProfession getProfessionFromString(String name) {
        return professions.stream().filter(lunarVillagerProfession -> lunarVillagerProfession.name.equals(name)).findFirst().orElse(null);
    }
}
