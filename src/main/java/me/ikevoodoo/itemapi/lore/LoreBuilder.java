package me.ikevoodoo.itemapi.lore;

import net.md_5.bungee.api.chat.BaseComponent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public final class LoreBuilder {

    private final List<String> components = new ArrayList<>();

    private LoreBuilder() {

    }

    public static LoreBuilder create() {
        return new LoreBuilder();
    }

    /**
     * Adds a list of strings to the LoreBuilder.
     *
     * @param lines A list of lines to add.
     * @since 1.0
     * */
    public LoreBuilder addLines(List<String> lines) {
        this.components.addAll(lines);
        return this;
    }

    /**
     * Takes a list of {@link BaseComponent}, converts it into a list of Strings and adds them to the builder.
     *
     * @param components The list of components to add.
     * @see BaseComponent
     * @since 1.0
     * */
    public LoreBuilder addComponents(List<BaseComponent> components) {
        components.forEach(comp -> this.components.add(comp.toLegacyText()));
        return this;
    }

    /**
     * Takes an array of {@link BaseComponent}, iterates over it and adds all components to the LoreBuilder.<br>
     * Components are added by using {@link BaseComponent#toLegacyText()}.
     *
     * @param components The array of BaseComponents to add.
     * @see BaseComponent
     * @since 1.0
     * */
    public LoreBuilder add(BaseComponent... components) {
        for (BaseComponent component : components) {
            this.components.add(component.toLegacyText());
        }

        return this;
    }

    /**
     * Takes an array of Strings and uses {@link Collections#addAll(Collection, Object[])} to add the array to the LoreBuilder.
     *
     * @param lines The array of lines to add.
     * @since 1.0
     * */
    public LoreBuilder add(String... lines) {
        Collections.addAll(this.components, lines);
        return this;
    }

    public List<String> build() {
        return new ArrayList<>(this.components);
    }

}
