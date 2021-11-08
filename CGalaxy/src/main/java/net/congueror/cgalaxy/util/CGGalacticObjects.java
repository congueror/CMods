package net.congueror.cgalaxy.util;

import net.congueror.cgalaxy.gui.galaxy_map.GalacticObjects;

public class CGGalacticObjects {
    public static final GalacticObjects.Galaxy MILKY_WAY = new GalacticObjects.Galaxy("Milky Way")
            .withAge(13.51e9)
            .withDiameter(105700, "LY")
            .withStars(250e9);
    public static final GalacticObjects.SolarSystem SOLAR_SYSTEM = new GalacticObjects.SolarSystem("Solar System", MILKY_WAY)
            .withAge(4.6e9)
            .withDiameter(284.46e9, "km")
            .withCelestialObjects(7);
    public static final GalacticObjects.Planet EARTH = new GalacticObjects.Planet("Earth", SOLAR_SYSTEM);
}
