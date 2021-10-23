package net.congueror.cgalaxy.api.events;

import net.congueror.cgalaxy.entity.villagers.LunarVillagerProfession;
import net.minecraftforge.eventbus.api.Event;

import java.util.ArrayList;

public class AddVillagerProfessionsEvent extends Event {//TODO: Docs
    public final ArrayList<LunarVillagerProfession> villagerProfessions;

    public AddVillagerProfessionsEvent(ArrayList<LunarVillagerProfession> villagerProfessions) {
        this.villagerProfessions = villagerProfessions;
    }
}
