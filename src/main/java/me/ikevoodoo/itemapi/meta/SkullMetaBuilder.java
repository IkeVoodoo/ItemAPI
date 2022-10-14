package me.ikevoodoo.itemapi.meta;

import org.bukkit.inventory.meta.SkullMeta;

public class SkullMetaBuilder extends MetaBuilder<SkullMeta> {
    protected SkullMetaBuilder(SkullMeta meta) {
        super(meta);
    }

    @Override
    public Class<SkullMeta> getMetaType() {
        return SkullMeta.class;
    }

    @Override
    public SkullMetaBuilder createFrom(SkullMeta meta) {
        return new SkullMetaBuilder(meta);
    }
}
