package me.ikevoodoo.itemapi;

import me.ikevoodoo.itemapi.enchantments.Enchantments;
import me.ikevoodoo.itemapi.lore.LoreBuilder;
import me.ikevoodoo.itemapi.meta.SkullMetaBuilder;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

public final class ItemApi extends JavaPlugin {

    @Override
    public void onEnable() {
        Items.create(Material.DIAMOND_SWORD)
            .count(1)
            .enchant(Enchantments.SHARPNESS, 5)
            .named("§cOrphan Obliterator")
            .lore(LoreBuilder.create().add("I am the second worst thing that has happened to those orphans"))
            .customModelData(7)
            .<SkullMetaBuilder>getMetaBuilder()
            .toItemStack();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

}
