package me.ikevoodoo.itemapi.meta;

import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;

public final class MetaBuilderRegistry {

    private static final HashMap<Class<?>, MetaBuilder<?>> BUILDERS = new HashMap<>();

    private MetaBuilderRegistry() {

    }

    @SuppressWarnings("unchecked")
    public static <T extends ItemMeta> MetaBuilder<T> fromMeta(T meta) {
        return ((MetaBuilder<T>) BUILDERS.get(meta.getClass())).createFrom(meta);
    }

    public static <T extends ItemMeta> void register(Class<T> meta, MetaBuilder<T> builder) {
        BUILDERS.put(meta, builder);
    }

}
