package io.github.tonodus.bukkit.MapGUI.components;

import io.github.tonodus.bukkit.MapGUI.api.Component;
import io.github.tonodus.bukkit.MapGUI.api.ComponentContainer;
import io.github.tonodus.bukkit.MapGUI.api.Drawable;
import io.github.tonodus.bukkit.MapGUI.core.BaseComponent;
import io.github.tonodus.bukkit.MapGUI.drawable.ColorDrawable;

import java.awt.*;

/**
 * Created by Tonodus (http://tonodus.github.io) on 11.08.2014.
 */
public class Panel extends BaseComponent implements ComponentContainer {
    private Component content;
    private Drawable background = new ColorDrawable(new Color(0, 0, 0, 255));
    private boolean centerContent = true;

    public Panel(Component content) {
        this.content = content;
    }

    @Override
    public void drawAsync(Graphics2D g) {
        drawBackgroundAsync(g);

        if (content != null)
            if (centerContent) {
                int px = Math.round((getWidth() - content.getWidth()) / 2);
                int py = Math.round((getHeight() - content.getHeight()) / 2);
                g.translate(px, py);
                drawContentAsync(g);
                g.translate(-px, -py);
            } else
                drawContentAsync(g);
    }


    protected void drawBackgroundAsync(Graphics2D g) {
        int x = getX(), y = getY();
        g.translate(x, y);
        background.drawAsync(g, getWidth(), getHeight());
        g.translate(-x, -y);
    }

    @Override
    public void setX(int x) {
        if (content != null)
            content.setX(content.getX() + x - getX());

        super.setX(x);
    }

    @Override
    public void setY(int y) {
        if (content != null)
            content.setY(content.getY() + y - getY());

        super.setY(y);
    }

    @Override
    public void updateSync() {
        if (content != null)
            content.updateSync();
        background.updateSync();
    }

    protected void drawContentAsync(Graphics2D g) {
        content.drawAsync(g);
    }

    public void setBackground(Drawable background) {
        this.background = background;
    }

    public void setBackground(Color color) {
        this.background = new ColorDrawable(color);
    }

    /**
     * Whether the content should be centered.
     *
     * @param center true if it should be centered, false otherwise
     */
    public void setCenterContent(boolean center) {
        this.centerContent = center;
    }

    public Component getContent() {
        return content;
    }

    public void setContent(Component content) {
        this.content = content;
    }

    @Override
    public void addComponent(Component component) {
        setContent(component);
    }

    @Override
    public void removeComponent(Component component) {
        setContent(null);
    }
}
