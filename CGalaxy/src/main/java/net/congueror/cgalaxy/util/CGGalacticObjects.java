package net.congueror.cgalaxy.util;

import net.congueror.cgalaxy.CGalaxy;
import net.congueror.cgalaxy.gui.galaxy_map.GalacticObjectBuilder;
import net.congueror.cgalaxy.world.CGDimensions;

import static net.congueror.cgalaxy.gui.galaxy_map.GalacticObjectBuilder.*;

public class CGGalacticObjects {
    public static final GalacticObject<Galaxy> MILKY_WAY = new GalacticObjectBuilder.Galaxy("Milky Way")
            .withAge(13.51e9)
            .withDiameter(105700, "LY")
            .withStars(250e9)
            .withTexture(CGalaxy.location("textures/gui/galaxy_map/milky_way.png"))
            .build();
    public static final GalacticObject<SolarSystem> SOLAR_SYSTEM = new GalacticObjectBuilder.SolarSystem("Solar System", MILKY_WAY)
            .withAge(4.6e9)
            .withDiameter(284.46e9, "km")
            .withCelestialObjects(7)
            .withX((width, leftPos) -> 3 * width / 4)
            .withY((height, topPos) -> height / 2 + 10)
            .withTexture(CGalaxy.location("textures/sky/sun.png"))
            .build();
    public static final GalacticObject<Planet> EARTH = new GalacticObjectBuilder.Planet("Earth", SOLAR_SYSTEM)
            .withDiameter(12742, "km")
            .withMoons(1)
            .withAtmosphere("Breathable")
            .withGravity(CGDimensions.OVERWORLD.getGravity())
            .withTier(0)
            .withX((width, leftPos) -> leftPos + 440)
            .withY((height, topPos) -> topPos + 281)
            .withTexture(CGalaxy.location("textures/sky/earth.png"))
            .withRingIndex(2)
            .build();
    public static final GalacticObject<Moon> MOON = new Moon("Moon", EARTH)
            .withDiameter(3474.2, "km")
            .withMoons(0)
            .withAtmosphere("Negligible")
            .withGravity(CGDimensions.MOON.getGravity())
            .withTier(1)
            .withX((width, leftPos) -> leftPos + 245)
            .withY((height, topPos) -> topPos + 340)
            .withTexture(CGalaxy.location("textures/sky/moon.png"))
            .build();
    public static final GalacticObject<Planet> MARS = new Planet("Mars", SOLAR_SYSTEM)
            .withDiameter(6779, "km")
            .withMoons(2)
            .withAtmosphere("Thin")
            .withGravity(3.721)
            .withTier(2)
            .withX((width, leftPos) -> leftPos + 165)
            .withY((height, topPos) -> topPos + 180)
            .withTexture(CGalaxy.location("textures/sky/mars.png"))
            .withRingIndex(3)
            .build();
    public static final GalacticObject<Moon> PHOBOS = new Moon("Phobos", MARS)
            .withDiameter(22.5, "km")
            .withMoons(0)
            .withAtmosphere("None")
            .withGravity(0.0057)
            .withTier(1)
            .withX((width, leftPos) -> leftPos + 245)
            .withY((height, topPos) -> topPos + 335)
            .withTexture(CGalaxy.location("textures/sky/phobos.png"))
            .build();
    public static final GalacticObject<Moon> DEIMOS = new Moon("Deimos", MARS)
            .withDiameter(12.4, "km")
            .withMoons(0)
            .withAtmosphere("None")
            .withGravity(0.003)
            .withTier(1)
            .withX((width, leftPos) -> leftPos + 460)
            .withY((height, topPos) -> topPos + 300)
            .withTexture(CGalaxy.location("textures/sky/deimos.png"))
            .build();
    public static final GalacticObject<Planet> VENUS = new Planet("Venus", SOLAR_SYSTEM)
            .withDiameter(12104, "km")
            .withMoons(0)
            .withAtmosphere("Very Thick")
            .withGravity(8.87)
            .withTier(4)
            .withX((width, leftPos) -> leftPos + 240)
            .withY((height, topPos) -> topPos + 280)
            .withTexture(CGalaxy.location("textures/sky/venus.png"))
            .withRingIndex(1)
            .build();
    public static final GalacticObject<Planet> MERCURY = new Planet("Mercury", SOLAR_SYSTEM)
            .withDiameter(4879.4, "km")
            .withMoons(0)
            .withAtmosphere("Negligible")
            .withGravity(3.7)
            .withTier(3)
            .withX((width, leftPos) -> leftPos + 349)
            .withY((height, topPos) -> topPos + 241)
            .withTexture(CGalaxy.location("textures/sky/mercury.png"))
            .withRingIndex(0)
            .build();

    public static void init() {}
}
