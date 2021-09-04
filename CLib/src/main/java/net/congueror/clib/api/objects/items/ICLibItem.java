package net.congueror.clib.api.objects.items;

/**
 * An interface which can be implemented by an Item class to add additional functionality for the {@link net.congueror.clib.api.registry.ItemBuilder}. See {@link CLItem} for example on how to implement properly
 */
public interface ICLibItem {
    void setBurnTime(int burnTime);
    void setContainerType(int containerType);
}
