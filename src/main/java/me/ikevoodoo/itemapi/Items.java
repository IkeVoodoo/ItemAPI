package me.ikevoodoo.itemapi;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public final class Items {

    private Items() {

    }

    /**
     * Creates a new ItemStack and constructs an ItemBuilder with it.<br>
     * This is a convenience method in case you do not want to create an ItemStack yourself.<br>
     * If you do have an ItemStack, please see {@link Items#wrap(ItemStack)}
     *
     * @param material The type of the ItemStack
     * @return A new instance of ItemBuilder created with a new ItemStack
     * @see Items#wrap(ItemStack)
     * @see ItemBuilder
     * @see ItemStack
     * @see Material
     * @since 1.0
     * */
    public static ItemBuilder create(Material material) {
        return new ItemBuilder(new ItemStack(material));
    }

    /**
     * Clones the ItemStack and creates an ItemBuilder with it.<br>
     * This method is the preferred method if you want to edit an item stack, it is also the only way.<br>
     * If you want to instead create a blank ItemStack, use {@link #create(Material)}
     *
     * @param stack The base ItemStack that will then be edited.
     *              The stack gets cloned so the original stack isn't modified.
     * @return A new instance of ItemBuilder
     * @see Items#create(Material)
     * @see ItemBuilder
     * @see ItemStack
     * @see Material
     * @since 1.0
     * */
    public static ItemBuilder wrap(ItemStack stack) {
        return new ItemBuilder(stack.clone());
    }

}
