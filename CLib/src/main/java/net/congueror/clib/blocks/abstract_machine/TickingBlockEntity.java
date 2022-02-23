package net.congueror.clib.blocks.abstract_machine;

public interface TickingBlockEntity {
    void tick();

    default void clientTick() {

    }
}
