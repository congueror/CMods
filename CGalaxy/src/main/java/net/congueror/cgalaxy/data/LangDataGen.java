package net.congueror.cgalaxy.data;

import net.congueror.cgalaxy.CGalaxy;
import net.congueror.clib.api.data.LangDataProvider;
import net.minecraft.data.DataGenerator;

public class LangDataGen extends LangDataProvider {
    public LangDataGen(DataGenerator gen, String locale) {
        super(gen, CGalaxy.MODID, locale);
    }

    @Override
    protected void addTranslations() {
        super.addTranslations();
        add("chat.cgalaxy.launch_off", " is launching off to space!");
        add("key.cgalaxy.empty", "Empty");
        add("tooltip.cgalaxy.screen.energy_percent", "Energy");
        add("tooltip.cgalaxy.screen.energy_usage", "Usage");
        add("key.cgalaxy.error_incomplete_pad", "The Launch Pad must be 3x3!");
        add("key.cgalaxy.error_missing_rocket", "No viable rocket detected!");
        add("key.cgalaxy.idle_rocket_full", "Rocket is full.");
        add("key.cgalaxy.fuel_remaining", "Remaining Fuel");
        add("gui.cgalaxy.galaxy_map", "Galaxy Map");
        add("gui.cgalaxy.space_suit", "Space Suit Menu");

        add("death.attack.noOxygen0", "%1$s thought he could breath in space.");
        add("death.attack.noOxygen1", "No one heard %1$s scream.");
        add("death.attack.noOxygen2", "%1$s forgot his oxygen gear.");
        add("death.attack.noOxygen3", "%1$s suffocated in the vacuum of space.");
        add("death.attack.noHeat0", "%1$s got too hot.");
        add("death.attack.noHeat1", "%1$s forgot his heat protection.");
        add("death.attack.noCold0", "%1$s got too cool for space.");
        add("death.attack.noCold1", "%1$s forgot his cold protection.");
        add("death.attack.noRadiation0", "%1$s got sick of the radiation.");
        add("death.attack.noRadiation1", "%1$s forgot his radiation protection.");

        add("text.cgalaxy.insufficient_fuel", "Insufficient Fuel!");
        add("text.cgalaxy.about_to_launch", "You are about to go to space. Are you sure you wish to proceed?");
        add("text.cgalaxy.about_to_launch1", "Press launch to continue.");

        add("gui.cgalaxy.milky_way", "Milky Way");
        add("gui.cgalaxy.milky_way.diameter", "Diameter: 105,700 LY");
        add("gui.cgalaxy.milky_way.age", "Age: 13.51B Y");
        add("gui.cgalaxy.milky_way.stars", "Stars: 250B");
        add("gui.cgalaxy.hover_tip1", "Hover over a location on map");
        add("gui.cgalaxy.hover_tip2", "and left click to select it");

        add("gui.cgalaxy.solar_system", "Solar System");
        add("gui.cgalaxy.solar_system.diameter", "Diameter: 284.46B km");
        add("gui.cgalaxy.solar_system.age", "Age: 4.6B Y");
        add("gui.cgalaxy.solar_system.bodies", "Celestial Bodies: 166");
        add("gui.cgalaxy.solar_system.planets", "Planets: 8");

        add("gui.cgalaxy.mercury", "Mercury");
        add("gui.cgalaxy.mercury.type", "Rocky Planet");
        add("gui.cgalaxy.mercury.diameter", "Diameter: 4,879.4 km");
        add("gui.cgalaxy.mercury.atmosphere", "Atmosphere: Negligible");
        add("gui.cgalaxy.mercury.moons", "Moons: 0");
        add("gui.cgalaxy.mercury.gravity", "Gravity: 3.7 m/s\u00b2");
        add("gui.cgalaxy.mercury.tier", "Tier: 5");

        add("gui.cgalaxy.venus", "Venus");
        add("gui.cgalaxy.venus.type", "Volcanic Planet");
        add("gui.cgalaxy.venus.diameter", "Diameter: 12,104 km");
        add("gui.cgalaxy.venus.atmosphere", "Atmosphere: Cataclysmic Greenhouse Effect");
        add("gui.cgalaxy.venus.moons", "Moons: 0");
        add("gui.cgalaxy.venus.gravity", "Gravity: 8.87 m/s\u00b2");
        add("gui.cgalaxy.venus.tier", "Tier: 4");

        add("gui.cgalaxy.earth", "Earth");
        add("gui.cgalaxy.earth.type", "Habitable Planet");
        add("gui.cgalaxy.earth.diameter", "Diameter: 12,742 km");
        add("gui.cgalaxy.earth.atmosphere", "Atmosphere: Breathable");
        add("gui.cgalaxy.earth.moons", "Moons: 1");
        add("gui.cgalaxy.earth.gravity", "Gravity: 9.81 m/s\u00b2");
        add("gui.cgalaxy.earth.tier", "Tier: 0");

        add("gui.cgalaxy.moon", "Moon");
        add("gui.cgalaxy.moon.type", "Rocky Moon");
        add("gui.cgalaxy.moon.diameter", "Diameter: 3,474.2 km");
        add("gui.cgalaxy.moon.atmosphere", "Atmosphere: None");
        add("gui.cgalaxy.moon.moons", "Submoons: 0");
        add("gui.cgalaxy.moon.gravity", "Gravity: 1.62 m/s\u00b2");
        add("gui.cgalaxy.moon.tier", "Tier: 1");

        add("gui.cgalaxy.mars", "Mars");
        add("gui.cgalaxy.mars.type", "Rocky Red Planet");
        add("gui.cgalaxy.mars.diameter", "Diameter: 6,779 km");
        add("gui.cgalaxy.mars.atmosphere", "Atmosphere: Very Thin");
        add("gui.cgalaxy.mars.moons", "Moons: 2");
        add("gui.cgalaxy.mars.gravity", "Gravity: 3.721 m/s\u00b2");
        add("gui.cgalaxy.mars.tier", "Tier: 2");

        add("gui.cgalaxy.phobos", "Phobos");
        add("gui.cgalaxy.phobos.type", "Rocky Moon");
        add("gui.cgalaxy.phobos.diameter", "Diameter: 22.5 km");
        add("gui.cgalaxy.phobos.atmosphere", "Atmosphere: None");
        add("gui.cgalaxy.phobos.moons", "Submoons: 0");
        add("gui.cgalaxy.phobos.gravity", "Gravity: 0.0057 m/s\u00b2");
        add("gui.cgalaxy.phobos.tier", "Tier: 3");

        add("gui.cgalaxy.deimos", "Deimos");
        add("gui.cgalaxy.deimos.type", "Rocky Moon");
        add("gui.cgalaxy.deimos.diameter", "Diameter: 12.4 km");
        add("gui.cgalaxy.deimos.atmosphere", "Atmosphere: None");
        add("gui.cgalaxy.deimos.moons", "Submoons: 0");
        add("gui.cgalaxy.deimos.gravity", "Gravity: 0.003 m/s\u00b2");
        add("gui.cgalaxy.deimos.tier", "Tier: 3");
    }
}
