package me.ikevoodoo.itemapi.meta.builders;

import me.ikevoodoo.itemapi.meta.MetaWrapper;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.UUID;

@SuppressWarnings("unused")
public final class SkullMetaWrapper extends MetaWrapper<SkullMeta> {
    public SkullMetaWrapper(SkullMeta meta) {
        super(meta);
    }

    @Override
    public Class<SkullMeta> getMetaType() {
        return SkullMeta.class;
    }

    @Override
    public SkullMetaWrapper createFrom(SkullMeta meta) {
        return new SkullMetaWrapper(meta);
    }

    public SkullMetaWrapper setOwner(OfflinePlayer offlinePlayer) {
        this.getMeta().setOwningPlayer(offlinePlayer);

        return this;
    }

    public SkullMetaWrapper setOwner(UUID id) {
        return this.setOwner(Bukkit.getOfflinePlayer(id));
    }
}
