package net.congueror.cgalaxy.data;

import net.congueror.cgalaxy.CGalaxy;
import net.congueror.cgalaxy.util.registry.data.CGLangDataProvider;
import net.minecraft.data.DataGenerator;

public class CGLangDataGen extends CGLangDataProvider {
    public CGLangDataGen(DataGenerator gen, String locale) {
        super(gen, CGalaxy.MODID, locale);
    }

    @Override
    protected void addTranslations() {
        super.addTranslations();
        if (locale.equals("en_us")) {
            add("credits", "me :)");

            add("item.cgalaxy.lunac_crop", "Lunac Seeds");
            add("item.cgalaxy.lunar_carrots", "Lunar Carrot");

            add("enchantment.cgalaxy.polarized", "Polarized");

            add("entity.cgalaxy.lunar_villager", "Lunar Villager");

            add("dimension.minecraft.overworld", "Overworld");
            add("dimension.minecraft.the_nether", "Nether");
            add("dimension.minecraft.the_end", "The End");
            add("dimension.cgalaxy.moon", "The Moon");
            add("biome.cgalaxy.the_moon", "Barren Lunar Plains");
            add("biome.cgalaxy.the_moon_south", "Lunar South Pole");
            add("dimension.cgalaxy.mars", "Mars");
            add("biome.cgalaxy.mars", "Martian Desert");
            add("biome.cgalaxy.mars_basalt", "Volcanic Martian Remnants");
            add("biome.cgalaxy.mars_south", "Martian South Pole");

            add("key.category.cgalaxy", "CGalaxy");
            add("key.cgalaxy.rocket_launch", "Launch Rocket");
            add("key.cgalaxy.open_space_suit_menu", "Open Space Suit Menu");

            add("chat.cgalaxy.launch_off", " is launching off to space!");
            add("key.cgalaxy.error_open_room", "Invalid block at");
            add("key.cgalaxy.idle_no_entities", "No blocks or entities that use oxygen in range.");
            add("key.cgalaxy.error_incomplete_pad", "The Launch Pad must be 3x3!");
            add("key.cgalaxy.error_missing_rocket", "No viable rocket detected!");
            add("key.cgalaxy.idle_rocket_full", "Rocket is full.");
            add("key.cgalaxy.fuel_remaining", "Remaining Fuel");
            add("key.cgalaxy.fuel", "Fuel");
            add("key.cgalaxy.map_no_material", "No acceptable space station item detected in inventory.");
            add("key.cgalaxy.private", "Private");
            add("key.cgalaxy.public", "Public");
            add("key.cgalaxy.blacklist", "Blacklist");
            add("key.cgalaxy.whitelist", "Whitelist");
            add("key.cgalaxy.selected_shift", "Hold shift to open list.");
            add("key.cgalaxy.edit", "Edit Coords");
            add("key.cgalaxy.error_no_blueprint", "No blueprint detected.");
            add("key.cgalaxy.error_insufficient_materials", "Insufficient materials.");
            add("key.cgalaxy.hover_tip1", "");
            add("key.cgalaxy.hover_tip2", "");
            add("gui.cgalaxy.galaxy_map", "Galaxy Map");
            add("gui.cgalaxy.space_suit", "Space Suit Menu");
            add("gui.cgalaxy.space_station_core", "Space Station Core");
            add("gui.cgalaxy.space_station_creator", "Space Station Creator");
            add("gui.cgalaxy.space_suit_settings", "Space Suit Settings");

            add("death.attack.cgalaxy.noOxygen0", "%1$s thought he could breath in space.");
            add("death.attack.cgalaxy.noOxygen1", "No one heard %1$s scream.");
            add("death.attack.cgalaxy.noOxygen2", "%1$s forgot to bring oxygen gear.");
            add("death.attack.cgalaxy.noOxygen3", "%1$s suffocated in the vacuum of space.");
            add("death.attack.cgalaxy.heat0", "%1$s got too hot.");
            add("death.attack.cgalaxy.heat1", "%1$s forgot to bring heat protection.");
            add("death.attack.cgalaxy.cold0", "%1$s got too cool for space.");
            add("death.attack.cgalaxy.cold1", "%1$s forgot to bring cold protection.");
            add("death.attack.cgalaxy.radiation0", "%1$s got sick of the radiation.");
            add("death.attack.cgalaxy.radiation1", "%1$s forgot to bring radiation protection.");
            add("death.attack.cgalaxy.laserBlast0", "%1$s was blasted by %2$s using %3$s");
            add("death.attack.cgalaxy.laserBlast1", "%2$s blasted %1$s into oblivion using %3$s");

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
