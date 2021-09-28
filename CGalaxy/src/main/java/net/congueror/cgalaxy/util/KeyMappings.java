package net.congueror.cgalaxy.util;

import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;

public class KeyMappings {
    public static final KeyMapping LAUNCH_ROCKET = new KeyMapping("key.cgalaxy.rocket_launch", GLFW.GLFW_KEY_SPACE, "key.category.cgalaxy");
    public static final KeyMapping OPEN_SPACE_SUIT_MENU = new KeyMapping("key.cgalaxy.open_space_suit_menu", GLFW.GLFW_KEY_KP_1, "key.category.cgalaxy");
}
