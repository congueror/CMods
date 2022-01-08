package net.congueror.cgalaxy.api.events;

import net.minecraftforge.eventbus.api.Event;

public class OxygenCheckEvent extends Event {//TODO

    boolean hasOxygen;

    public boolean hasOxygen() {
        return hasOxygen;
    }

    public void hasOxygen(boolean hasOxygen) {
        this.hasOxygen = hasOxygen;
    }
}
