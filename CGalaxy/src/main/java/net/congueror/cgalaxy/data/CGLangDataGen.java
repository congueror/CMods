package net.congueror.cgalaxy.data;

import net.congueror.cgalaxy.CGalaxy;
import net.congueror.cgalaxy.api.data.CGLangDataProvider;
import net.minecraft.data.DataGenerator;

public class CGLangDataGen extends CGLangDataProvider {
    public CGLangDataGen(DataGenerator gen, String locale) {
        super(gen, CGalaxy.MODID, locale);
    }

    @Override
    protected void addTranslations() {
        super.addTranslations();
        if (locale.equals("en_us")) {
            add("dimension.minecraft.overworld", "Overworld");
            add("dimension.minecraft.the_nether", "The Nether");
            add("dimension.minecraft.the_end", "The End");
            add("dimension.cgalaxy.moon", "The Moon");
            add("biome.cgalaxy.the_moon", "Moon Barren Plains");
            add("biome.cgalaxy.the_moon_south", "Moon South Pole");

            add("chat.cgalaxy.launch_off", " is launching off to space!");
            add("key.cgalaxy.empty", "Empty");
            add("key.cgalaxy.error_open_room", "Invalid block at");
            add("key.cgalaxy.idle_no_entities", "No blocks or entities that use oxygen in range.");
            add("key.cgalaxy.error_incomplete_pad", "The Launch Pad must be 3x3!");
            add("key.cgalaxy.error_missing_rocket", "No viable rocket detected!");
            add("key.cgalaxy.idle_rocket_full", "Rocket is full.");
            add("key.cgalaxy.fuel_remaining", "Remaining Fuel");
            add("key.cgalaxy.fuel", "Fuel");
            add("key.cgalaxy.map_no_material", "No acceptable space station item detected in inventory.");
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

            add("recipe.cgalaxy.fuel_refining", "Fuel Refining");
            add("recipe.cgalaxy.fuel_loading", "Fuel Loading");
            add("recipe.cgalaxy.gas_extracting", "Gas Extracting");

            add("text.cgalaxy.insufficient_fuel", "Insufficient Fuel!");
            add("text.cgalaxy.about_to_launch", "You are about to go to space. Are you sure you wish to proceed?");
            add("text.cgalaxy.about_to_launch1", "Press launch to continue.");

            add("gui.cgalaxy.diameter", "Diameter: ");
            add("gui.cgalaxy.age", "Age: ");
            add("gui.cgalaxy.stars", "Stars: ");
            add("gui.cgalaxy.bodies", "Terrestrial Bodies: ");
            add("gui.cgalaxy.atmosphere", "Atmosphere: ");
            add("gui.cgalaxy.moons", "Moons: ");
            add("gui.cgalaxy.gravity", "Gravity: ");
            add("gui.cgalaxy.tier", "Tier: ");
        }
    }
}
