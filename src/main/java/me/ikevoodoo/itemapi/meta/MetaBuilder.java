package me.ikevoodoo.itemapi.meta;

import org.bukkit.inventory.meta.ItemMeta;

public abstract class MetaBuilder<T extends ItemMeta> {

    private final T meta;

    protected MetaBuilder(T meta) {
        this.meta = meta;
    }

    public abstract Class<T> getMetaType();

    public abstract MetaBuilder<T> createFrom(T meta);

    protected final T getMeta() {
        return this.meta;
    }

}
