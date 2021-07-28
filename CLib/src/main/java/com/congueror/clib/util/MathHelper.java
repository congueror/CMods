package com.congueror.clib.util;

import com.sun.scenario.effect.Color4f;

public class MathHelper {
    public static int calculateRGB(int R, int G, int B) {
        return (R * 65536) + (G * 256) + B;
    }

    public static Color4f getFluidColor(int argb) {
        return new Color4f(((argb >> 16) & 0xFF) / 255f, ((argb >> 8) & 0xFF) / 255f, ((argb >> 0) & 0xFF) / 255f, ((argb >> 24) & 0xFF) / 255f);
    }

    public static int getPercent(int value, int max) {
        return value * 100 / max;
    }
}
