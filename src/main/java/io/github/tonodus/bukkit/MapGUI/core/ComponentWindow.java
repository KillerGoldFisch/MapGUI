package io.github.tonodus.bukkit.MapGUI.core;


import io.github.tonodus.bukkit.MapGUI.api.Component;
import io.github.tonodus.bukkit.MapGUI.api.FocusWindow;
import io.github.tonodus.bukkit.MapGUI.api.MapGUI;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Tonodus (http://tonodus.github.io) on 10.08.2014.
 */
public abstract class ComponentWindow extends InputWindow implements FocusWindow {
    private Collection<Component> cs;
    private MapGUI gui;

    private Component focused = null;

    public ComponentWindow() {
        this.cs = new ArrayList<Component>();
    }

    public void addComponent(Component component) {
        component.onAttachedTo(this);
        cs.add(component);
        invalidate();
    }

    @Override
    public void invalidate() {
        if (gui != null)
            gui.invalidate();
    }

    public void removeComponent(Component component) {
        component.onDetachedFrom(this);
        cs.remove(component);
        gui.invalidate();
    }

    public Component getFocused() {
        return focused;
    }

    @Override
    public void setFocused(Component on) {
        this.focused.onLostFocus();
        this.focused = on;
        if (this.focused != null)
            this.focused.onFocus();

        this.invalidate();
    }

    @Override
    public void updateSync() {
        for (Component c : cs)
            c.updateSync();
    }

    @Override
    public void drawAsync(Graphics2D canvas) {
        drawBackground(canvas, DefaultMapGui.WIDTH, DefaultMapGui.HEIGHT);

        for (Component c : cs) {
            canvas.clipRect(c.getX(), c.getY(), c.getWidth(), c.getHeight());
            c.drawAsync(canvas);
            canvas.setClip(null);
        }
    }


    protected abstract void drawBackground(Graphics2D g, int width, int height);
}
