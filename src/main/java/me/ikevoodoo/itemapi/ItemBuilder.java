package me.ikevoodoo.itemapi;

import me.ikevoodoo.itemapi.lore.LoreBuilder;
import me.ikevoodoo.itemapi.meta.MetaWrapper;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

import java.util.function.Consumer;
import java.util.function.Function;

public final class ItemBuilder {

    private final ItemStack base;
    private final ItemMeta meta;

    public ItemBuilder(final ItemStack base) {
        if (base == null) {
            throw new IllegalArgumentException("Cannot provide in a null base item to ItemBuilder!");
        }

        this.base = base;
        this.meta = this.base.getItemMeta();
    }

    public ItemBuilder(final Material mat) {
        this(new ItemStack(mat));
    }

    @SuppressWarnings("unchecked")
    public <M extends ItemMeta, T extends MetaWrapper<M>> ItemBuilder applyMetaWrapper(Function<M, T> metaWrapper, Consumer<T> consumer) {
        try {
            consumer.accept(metaWrapper.apply((M) this.meta));
        } catch (ClassCastException exception) {
            throw new IllegalArgumentException(
                String.format(
                    "Item of type '%s' has item meta of type '%s'",
                    this.base.getType(),
                    this.meta.getClass().getSimpleName()
                )
            );
        }

        return this;
    }

    /**
     * Edit the count of the ItemStack.
     *
     * @param count An integer within the range 1 to 64.
     *              If the number is outside the range it will be clamped.
     * @since 1.0
     * */
    public ItemBuilder count(final int count) {
        this.base.setAmount(Math.max(1, Math.min(64, count)));
        return this;
    }

    /**
     * Append an enchantment to the ItemStack.
     *
     * @param enchantment The enchantment to append.
     *                    If this is null an {@link IllegalArgumentException} will be thrown.
     * @param level An integer within the range of 1 to 255.
     *              If the number is outside the range it will be clamped.
     * @see Enchantment
     * @since 1.0
     * */
    public ItemBuilder enchant(Enchantment enchantment, int level) {
        this.meta.addEnchant(enchantment, Math.max(1, Math.min(255, level)), true);
        return this;
    }

    /**
     * Sets the display name of an ItemStack.
     *
     * @param name The display name to set.
     * @since 1.0
     * */
    public ItemBuilder named(String name) {
        this.meta.setDisplayName(name);
        return this;
    }

    /**
     * Converts the BaseComponent to legacy text using {@link BaseComponent#toLegacyText()}.<br>
     * It then calls {@link ItemBuilder#named(String)}
     *
     * @param component The component that holds the text.
     * @see BaseComponent
     * @since 1.0
     * */
    public ItemBuilder named(BaseComponent component) {
        return this.named(component.toLegacyText());
    }

    /**
     * Converts an array of BaseComponent to a single BaseComponent and calls {@link ItemBuilder#named(BaseComponent)}.<br>
     * The converting is done by constructing a new TextComponent and passing in the array.
     *
     * @param components An array of BaseComponents
     * @see BaseComponent
     * @see TextComponent
     * @since 1.0
     * */
    public ItemBuilder named(BaseComponent... components) {
        return this.named(this.mergeComponents(components));
    }

    /**
     * Takes a LoreBuilder, calls {@link LoreBuilder#build()} and sets the lore.
     *
     * @param builder The LoreBuilder used to build the lore.
     * @see LoreBuilder
     * @since 1.0
     * */
    public ItemBuilder lore(LoreBuilder builder) {
       this.meta.setLore(builder.build());

       return this;
    }

    /**
     * Sets the ItemBuilder's custom model data.
     *
     * @param data The custom model data to set
     * @since 1.0
     * */
    public ItemBuilder customModelData(int data) {
        this.meta.setCustomModelData(data);

        return this;
    }

    /**
     * Adds an array of {@link ItemFlag} to the ItemBuilder's hidden flag list.
     *
     * @param flags The array of flags to hide.
     *              Duplicates are silently ignored.
     * @see ItemFlag
     * @since 1.0
     * */
    public ItemBuilder addItemFlags(ItemFlag... flags) {
        this.meta.addItemFlags(flags);

        return this;
    }

    /**
     * Hides all flags from an item, effectively showing only name, lore and base item info.<br>
     * To achieve this {@link ItemBuilder#addItemFlags(ItemFlag...)} is called with {@link ItemFlag#values()}
     *
     * @see ItemFlag
     * @see ItemFlag#values()
     * @since 1.0
     * */
    public ItemBuilder addAllItemFlags() {
        return this.addItemFlags(ItemFlag.values());
    }

    /**
     * Removes an array of {@link ItemFlag} from the ItemBuilder's hidden flag list.
     *
     * @param flags The array of flags to show.
     *              Duplicates are silently ignored.
     * @see ItemFlag
     * @since 1.0
     * */
    public ItemBuilder removeItemFlags(ItemFlag... flags) {
        this.meta.removeItemFlags(flags);

        return this;
    }

    /**
     * Shows all flags on an item, effectively letting all data show.<br>
     * This is done by calling {@link ItemBuilder#removeItemFlags(ItemFlag...)} with {@link ItemFlag#values()}
     *
     * @see ItemFlag
     * @see ItemFlag#values()
     * @since 1.0
     * */
    public ItemBuilder removeAllItemFlags() {
        return this.removeItemFlags(ItemFlag.values());
    }

    /**
     * Adds a key to this ItemBuilder.
     *
     * @param key The key to set
     * @param type The type of value
     * @param value The value to set.
     * @see NamespacedKey
     * @since 1.0
     * */
    public <T, Z> ItemBuilder addKey(NamespacedKey key, PersistentDataType<T, Z> type, Z value) {
        PersistentDataContainer container = this.meta.getPersistentDataContainer();
        container.set(key, type, value);

        return this;
    }

    /**
     * Creates and adds a key to this ItemBuilder.<br>
     * Adding the key is done using {@link ItemBuilder#addKey(NamespacedKey, PersistentDataType, Object)}
     *
     * @param plugin The plugin used to construct the key
     * @param key The key to set
     * @param type The type of value
     * @param value The value to set.
     * @see NamespacedKey
     * @since 1.0
     * */
    public <T, Z> ItemBuilder addKey(Plugin plugin, String key, PersistentDataType<T, Z> type, Z value) {
        return this.addKey(new NamespacedKey(plugin, key), type, value);
    }

    /**
     * Removes a key from this ItemBuilder.
     *
     * @param key The key to remove
     * @see NamespacedKey
     * @since 1.0
     * */
    public ItemBuilder removeKey(NamespacedKey key) {
        PersistentDataContainer container = this.meta.getPersistentDataContainer();
        container.remove(key);

        return this;
    }

    /**
     * Creates and removes a key from this ItemBuilder.<br>
     * Removing the key is done using {@link ItemBuilder#removeKey(NamespacedKey)}
     *
     * @param plugin The plugin used to construct the key
     * @param key The key to remove
     * @see NamespacedKey
     * @since 1.0
     * */
    public ItemBuilder removeKey(Plugin plugin, String key) {
        return this.removeKey(new NamespacedKey(plugin, key));
    }

    /**
     * Returns the ItemStack made by this builder.<br>
     * Changes to this builder will NOT edit the item.
     *
     * @return The constructed ItemStack
     * @see ItemStack
     * @since 1.0
     * */
    public ItemStack build() {
        ItemStack clone = this.base.clone();
        clone.setItemMeta(this.meta);
        return clone;
    }

    private BaseComponent mergeComponents(BaseComponent[] components) {
        return new TextComponent(components);
    }
}
