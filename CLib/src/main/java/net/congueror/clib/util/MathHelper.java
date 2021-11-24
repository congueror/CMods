package net.congueror.clib.util;

import com.mojang.math.Vector4f;

import java.text.DecimalFormat;

public class MathHelper {
    /**
     * Converts RGB to a decimal
     */
    public static int calculateRGB(int R, int G, int B) {
        return (R * 65536) + (G * 256) + B;
    }

    /**
     * Converts the argb hexadecimal of a fluid's colour to a {@link Vector4f} which contains the Red, Green, Blue and Alpha.
     *
     * @return x = Red, y = Blue, z = Green, w = Alpha
     */
    public static Vector4f getFluidColor(int argb) {
        return new Vector4f(((argb >> 16) & 0xFF) / 255f, ((argb >> 8) & 0xFF) / 255f, ((argb) & 0xFF) / 255f, ((argb >> 24) & 0xFF) / 255f);
    }

    /**
     * Extracts a percentage from the value and it's max value.
     *
     * @return A number from 0 to 100
     */
    public static int getPercent(int value, int max) {
        return value * 100 / max;
    }

    /**
     * Formats the given unit of the given value to n, μ, m, G, M and k.
     *
     * @return The formatted number including the unit.
     */
    public static String formatUnit(double value, String unit) {
        if (value < 1 && (value + "").contains("-")) {
            String l = (value + "").substring((value + "").indexOf("-"));
            int stringDouble = Integer.parseInt(l);
            if (stringDouble <= -9) {
                double formatted = value * 1e9;
                return "0" + new DecimalFormat("#.00").format(formatted) + "n" + unit;
            } else if (stringDouble <= -6) {
                return "0" + new DecimalFormat("#.00").format(value * 1e6) + "\u03bc" + unit;
            } else if (stringDouble <= -3) {
                return "0" + new DecimalFormat("#.00").format(value * 1e3) + "m" + unit;
            }
        }
        if (value > 1) {
            String stringDouble = ((int) value) + "";
            if (stringDouble.length() >= 10) {
                return new DecimalFormat("#.00").format(value / 1e9) + "G" + unit;
            } else if (stringDouble.length() >= 7) {
                return new DecimalFormat("#.00").format(value / 1e6) + "M" + unit;
            } else if (stringDouble.length() >= 4) {
                return new DecimalFormat("#.00").format(value / 1e3) + "k" + unit;
            }
        }
        return new DecimalFormat("#.00").format(value) + unit;
    }

    /**
     * @return
     */
    public static String simplifyNumber(double value) {
        if (value > 0) {
            String d = value + "";
            if (value >= 1000000000000.0) {
                return new DecimalFormat("#.00").format(value / 1000000000000.0) + "T";
            } else if (value >= 1000000000.0) {
                return new DecimalFormat("#.00").format(value / 1000000000.0) + "B";
            } else if (value >= 1000000.0) {
                return new DecimalFormat("#.00").format(value / 1000000.0) + "M";
            } else if (value >= 1000) {
                return new DecimalFormat("#.00").format(value / 1000) + "k";
            }
        }
        return "Unexpected Value";
    }

    /**
     * Converts celcius to kelvin
     */
    public static float celciusToKelvin(float celcius) {
        return celcius + 273.15F;
    }

    /**
     * Converts celcius to farrenheit
     */
    public static float celciusToFarrenheit(float celcius) {
        return (celcius * (9F / 5)) + 32;
    }

    /**
     * Converts sieverts to rem
     */
    public static float sievertsToRem(float sieverts) {
        return sieverts * 100;
    }

    /**
     * Converts pascals to pounds per square inch
     */
    public static double pascalsToPsi(double pascals) {
        return pascals / 6895;
    }

    /**
     * Converts pascals to bars
     */
    public static double pascalsToBars(double pascals) {
        return pascals / 100000;
    }

    /**
     * Converts pascals to torr
     */
    public static double pascalsToTorr(double pascals) {
        return pascals / 133;
    }

    /**
     * Converts pascals to standard atmospheres
     */
    public static double pascalsToStandardAtmospheres(double pascals) {
        return pascals / 101325;
    }
}
