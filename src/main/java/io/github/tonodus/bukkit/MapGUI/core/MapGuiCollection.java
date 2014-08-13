package io.github.tonodus.bukkit.MapGUI.core;

import io.github.tonodus.bukkit.MapGUI.Plugin;
import io.github.tonodus.bukkit.MapGUI.api.DropListener;
import io.github.tonodus.bukkit.MapGUI.api.MapGUI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Tonodus (http://tonodus.github.io) on 13.08.2014.
 */
public class MapGuiCollection {
    private WorkerThread w;
    private Plugin mainPlugin;
    private Listener internListener = new InternListener();

    private Collection<DefaultMapGui> guis;

    /**
     * @hide
     */
    public MapGuiCollection(WorkerThread thread, Plugin mainPlugin) {
        this.w = thread;
        this.mainPlugin = mainPlugin;
        this.guis = new ArrayList<DefaultMapGui>();
    }

    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(internListener, mainPlugin);
    }

    public void onDisable() {
        HandlerList.unregisterAll(internListener);
    }

    public MapGUI registerMapGui(Plugin yourPlugin, Player player) {
        final DefaultMapGui gui = new DefaultMapGui(yourPlugin, player, w);
        gui.addDropListener(new DropListener() {
            @Override
            public boolean onPossibleDrop(MapGUI me, Player player) {
                return false;
            }

            @Override
            public void onPreDispose(MapGUI me, Player player) {

            }

            @Override
            public void onDispose(MapGUI me) {
                guis.remove(me);
            }
        });
        guis.add(gui);
        return gui;
    }

    private class InternListener implements Listener {
        @EventHandler
        public void move(PlayerMoveEvent event) {
            for (DefaultMapGui gui : guis)
                gui.moveHelper.onMove(event);
        }

        @EventHandler
        public void click(PlayerInteractEvent event) {
            for (DefaultMapGui gui : guis)
                gui.onClick(event);
        }

        @EventHandler
        public void scroll(PlayerItemHeldEvent event) {
            for (DefaultMapGui gui : guis)
                gui.onScroll(event);
        }

        @EventHandler
        public void drop(PlayerDropItemEvent event) {
            for (DefaultMapGui gui : guis)
                gui.onDrop(event);
        }

        @EventHandler
        public void quit(PlayerQuitEvent event) {
            for (DefaultMapGui gui : guis)
                gui.onQuit(event);
        }
    }
}
