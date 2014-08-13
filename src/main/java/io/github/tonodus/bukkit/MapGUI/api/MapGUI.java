package io.github.tonodus.bukkit.MapGUI.api;

import org.bukkit.DyeColor;

/**
 * Created by Tonodus (http://tonodus.github.io) on 10.08.2014.
 */

/**
 * The base interface
 */
public interface MapGUI extends InputListenerController<MapGUI> {
    /**
     * Sets the current displayed {@link Window window}
     *
     * @param window the window to be displayer
     */
    public void setWindow(Window window);

    /**
     * Returns the current displayed window
     *
     * @return the window
     */
    public Window getCurrentWindow();

    /**
     * Returns the cursor for this map
     *
     * @return the cursor
     */
    public Cursor getCursor();

    /**
     * Let the map gui redraw itself. Call this, when ever you changed something of the gui.
     */
    public void invalidate();

    /**
     * //TODO
     * NOT READY YET
     *
     * @param color
     */
    public void setBackground(DyeColor color);

    /**
     * Shows this gui to the player.
     */
    public void show();


    /**
     * hides this map from the player
     */
    public void hide();

    /**
     * Call this to dispose the map and the memory it use, CALL {@link #hide hide} BEFORE!.
     */
    public void dispose();

    public void addDropListener(DropListener listener);

    public void removeDropListener(DropListener listener);
}
