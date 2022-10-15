package me.ikevoodoo.itemapi.meta;

import org.bukkit.inventory.meta.ItemMeta;

public abstract class MetaWrapper<T extends ItemMeta> {

    private final T meta;

    public MetaWrapper(T meta) {
        this.meta = meta;
    }

    public abstract Class<T> getMetaType();

    public abstract MetaWrapper<T> createFrom(T meta);

    protected final T getMeta() {
        return this.meta;
    }

}
