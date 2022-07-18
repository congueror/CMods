package net.congueror.clib.blocks.machine_base;

public interface TickingBlockEntity {
    void tick();

    default void clientTick() {

    }
}
