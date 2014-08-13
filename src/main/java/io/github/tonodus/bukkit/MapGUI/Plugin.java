package io.github.tonodus.bukkit.MapGUI;

import io.github.tonodus.bukkit.MapGUI.api.MapGUI;
import io.github.tonodus.bukkit.MapGUI.core.MapGuiCollection;
import io.github.tonodus.bukkit.MapGUI.core.WorkerThread;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Tonodus (http://tonodus.github.io) on 10.08.2014.
 */
public class Plugin extends JavaPlugin implements Listener {
    private static Plugin i;
    private WorkerThread thread;
    private MapGuiCollection collection;

    public static MapGUI registerMapGui(Plugin yourPlugin) {
        if (i == null)
            throw new IllegalStateException("MapGui plugin was disabled!");

        return i.collection.registerMapGui(yourPlugin);
    }

    @Override
    public void onEnable() {
        i = this;

        thread = new WorkerThread();
        thread.start();
        collection = new MapGuiCollection();
        collection.onEnable();
    }

    @Override
    public void onDisable() {
        i = null;

        collection.onDisable();
        collection = null;
        thread.stopWhenFinished();
        thread = null;
    }
}
