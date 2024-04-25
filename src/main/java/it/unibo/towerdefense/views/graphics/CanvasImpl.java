package it.unibo.towerdefense.views.graphics;

import java.util.List;
import java.util.ArrayList;
import javax.swing.JPanel;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

/**
 * Main canvas of the game.
 */
public class CanvasImpl extends JPanel implements Canvas {

    private static final int FIRST_INDEX = 0;
    private static final int START_Y = 0;
    private static final int START_X = 0;

    private final List<Drawable> queue = new ArrayList<>();

    /**
     * Constructor from width and height.
     * @param width the width of the canvas
     * @param height the height of the canvas
     */
    public CanvasImpl(final int width, final int height) {
        super();
        // set the size of the canvas
        this.setPreferredSize(new Dimension(width, height));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void paintComponent(final Graphics g) {
        super.paintComponent(g);
        // create a copy of graphics and set rendering hints
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        // clear the canvas
        g2d.clearRect(START_X, START_Y, getWidth(), getHeight());
        // draw all the elements in queue
        for (final Drawable drawable: this.queue) {
            drawable.paint(g2d);
        }
        // dispose the graphics and clear the queue
        g2d.dispose();
        this.queue.clear();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void render() {
        repaint();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void submit(Drawable drawable) {
        this.queue.add(drawable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void submitBackground(Drawable drawable) {
       this.queue.add(FIRST_INDEX, drawable);
    }
}
