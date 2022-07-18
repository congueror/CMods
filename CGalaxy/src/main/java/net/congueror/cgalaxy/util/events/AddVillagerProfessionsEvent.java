package net.congueror.cgalaxy.util.events;

import net.congueror.cgalaxy.entity.villagers.LunarVillagerProfession;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.npc.Villager;
import net.minecraftforge.eventbus.api.Event;

import java.util.ArrayList;

public class AddVillagerProfessionsEvent extends Event {
    private final ArrayList<LunarVillagerProfession> villagerProfessions;
    private final ArrayList<LunarVillagerProfession> addedProfessions = new ArrayList<>();
    private EntityType<? extends Villager> villager;

    public AddVillagerProfessionsEvent(ArrayList<LunarVillagerProfession> villagerProfessions) {
        this.villagerProfessions = villagerProfessions;
    }

    public ArrayList<LunarVillagerProfession> getCGalaxyProfessions() {
        return villagerProfessions;
    }

    public ArrayList<LunarVillagerProfession> getAddedProfessions() {
        return addedProfessions;
    }

    public EntityType<? extends Villager> getVillager() {
        return villager;
    }

    public void addProfession(EntityType<? extends Villager> villager, LunarVillagerProfession profession) {
        this.villager = villager;
        addedProfessions.add(profession);
    }
}
