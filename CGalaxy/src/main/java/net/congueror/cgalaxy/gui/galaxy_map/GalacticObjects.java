package net.congueror.cgalaxy.gui.galaxy_map;

public abstract class GalacticObjects<T extends GalacticObjects<T>> {

    public final String name;
    private String diameter;
    private double age;

    public GalacticObjects(String name) {
        this.name = name;
    }//TODO: Translation key generation.

    public String getName() {
        return name;
    }
    public String getDiameter() {
        return diameter;
    }
    public double getAge() {
        return age;
    }

    public T withDiameter(double diameter, String unit) {
        this.diameter = diameter + unit;
        return self();
    }

    public T withAge(double age) {
        this.age = age;
        return self();
    }

    @SuppressWarnings("unchecked")
    final T self() {
        return (T) this;
    }

    public static class Galaxy extends GalacticObjects<Galaxy> {

        private double stars;

        public Galaxy(String name) {
            super(name);
        }

        public double getStars() {
            return stars;
        }

        public Galaxy withStars(double stars) {
            this.stars = stars;
            return this;
        }
    }

    public static class SolarSystem extends GalacticObjects<SolarSystem> {

        public final Galaxy galaxy;
        private int celestialObjects;

        public SolarSystem(String name, Galaxy galaxy) {
            super(name);
            this.galaxy = galaxy;
        }

        public int getCelestialObjects() {
            return celestialObjects;
        }

        public SolarSystem withCelestialObjects(int celestialObjects) {
            this.celestialObjects = celestialObjects;
            return this;
        }
    }

    public static class Planet extends GalacticObjects<Planet> {

        public final SolarSystem solarSystem;
        private String atmosphere;
        private int moons;
        private double gravity;
        private int tier;

        public Planet(String name, SolarSystem solarSystem) {
            super(name);
            this.solarSystem = solarSystem;
        }
    }

    public static class Moon extends GalacticObjects<Moon> {

        public final Planet planet;
        private String atmosphere;
        private int moons;
        private double gravity;
        private int tier;

        public Moon(String name, Planet planet) {
            super(name);
            this.planet = planet;
        }
    }

    public static class MoonMoon extends GalacticObjects<MoonMoon> {

        public final Moon moon;
        private String atmosphere;
        private int moons;
        private double gravity;
        private int tier;

        public MoonMoon(String name, Moon moon) {
            super(name);
            this.moon = moon;
        }
    }
}
