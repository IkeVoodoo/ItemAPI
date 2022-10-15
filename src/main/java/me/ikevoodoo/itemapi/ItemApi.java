package me.ikevoodoo.itemapi;

import me.ikevoodoo.itemapi.enchantments.Enchantments;
import me.ikevoodoo.itemapi.lore.LoreBuilder;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

public final class ItemApi extends JavaPlugin {

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvent(
            PlayerJoinEvent.class,
            new Listener() {},
            EventPriority.HIGHEST,
            this::execute,
            this
        );
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void execute(Listener listener, Event event) {
        PlayerJoinEvent playerJoinEvent = (PlayerJoinEvent) event;

        LoreBuilder loreBuilder = LoreBuilder.create();
        TextComponent component = new TextComponent("I am the second worst thing that has happened to those orphans.");
        component.setColor(ChatColor.RED);
        loreBuilder.add(component);


        ItemStack stack = new ItemBuilder(Material.DIAMOND_SWORD)
            .enchant(Enchantments.SHARPNESS, 50)
            .enchant(Enchantments.KNOCKBACK, 50)
            .addItemFlags(ItemFlag.HIDE_ENCHANTS)
            .named("§4Orphan Obliterator")
            .lore(loreBuilder)
            .addKey(this, "orphan_obliterator", PersistentDataType.INTEGER, 1)
            .removeKey(this, "orphan_obliterator")
            .build();

        playerJoinEvent.getPlayer().getInventory().addItem(stack);
    }
}
