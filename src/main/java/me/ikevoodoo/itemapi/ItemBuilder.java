package me.ikevoodoo.itemapi;

import me.ikevoodoo.itemapi.lore.LoreBuilder;
import me.ikevoodoo.itemapi.meta.MetaBuilder;
import me.ikevoodoo.itemapi.meta.MetaBuilderRegistry;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public final class ItemBuilder {

    private final ItemStack base;
    private final ItemMeta meta;
    private final MetaBuilder<?> metaBuilder;

    ItemBuilder(final ItemStack base) {
        if (base == null) {
            throw new IllegalArgumentException("Cannot provide in a null base item to ItemBuilder!");
        }

        this.base = base;
        this.meta = this.base.getItemMeta();
        this.metaBuilder = MetaBuilderRegistry.fromMeta(this.meta);
    }

    @SuppressWarnings("unchecked")
    public <T extends MetaBuilder<?>> T getMetaBuilder() {
        return (T) metaBuilder;
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
     * Returns the ItemStack made by this builder.<br>
     * Changes to this builder will NOT edit the item.
     *
     * @return The constructed ItemStack
     * @see ItemStack
     * @since 1.0
     * */
    public ItemStack toItemStack() {
        ItemStack clone = this.base.clone();
        clone.setItemMeta(this.meta);
        return clone;
    }

    private BaseComponent mergeComponents(BaseComponent[] components) {
        return new TextComponent(components);
    }
}
