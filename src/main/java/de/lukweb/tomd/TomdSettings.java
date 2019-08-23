package de.lukweb.tomd;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import org.jetbrains.annotations.NotNull;

@State(
        name = "TodoMarkdownSettings",
        storages = @Storage(
                value = "todoMarkdownSettings.xml"
        )
)
public class TomdSettings implements PersistentStateComponent<TomdSettingsState> {

    public static TomdSettings getInstance() {
        return ServiceManager.getService(TomdSettings.class);
    }

    private TomdSettingsState state;

    public TomdSettings() {
        noStateLoaded();
    }

    @NotNull
    @Override
    public TomdSettingsState getState() {
        if (state == null) {
            noStateLoaded();
        }
        return state;
    }

    @Override
    public void loadState(@NotNull TomdSettingsState state) {
        this.state = state;
    }

    @Override
    public void noStateLoaded() {
        this.state = newState();
    }

    private TomdSettingsState newState() {
        return new TomdSettingsState();
    }


}
