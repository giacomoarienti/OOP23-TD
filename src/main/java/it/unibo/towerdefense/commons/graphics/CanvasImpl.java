package it.unibo.towerdefense.commons.graphics;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JPanel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Main canvas of the game.
 */
public class CanvasImpl extends JPanel implements Canvas {

    private static final long serialVersionUID = 1000L;
    private static final int FIRST_INDEX = 0;
    private static final int START_Y = 0;
    private static final int START_X = 0;

    private final transient List<Drawable> queue = new ArrayList<>();
    private final transient Logger logger;

    /**
     * Constructor from width and height.
     * @param width the width of the canvas
     * @param height the height of the canvas
     */
    public CanvasImpl(final int width, final int height) {
        super();
        // set the size of the canvas
        this.setPreferredSize(new Dimension(width, height));
        // create the logger
        this.logger = LoggerFactory.getLogger(this.getClass());
        // bind on click event
        this.addMouseListener(new MouseAdapter() {
            public void mouseClicked(final MouseEvent e) {
                CanvasImpl.this.onClick(e);
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void paintComponent(final Graphics g) {
        super.paintComponent(g);
        // create a copy of graphics and set rendering hints
        final Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        // clear the canvas
        g2d.clearRect(START_X, START_Y, this.getWidth(), this.getHeight());
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
    public synchronized void submit(final Drawable drawable) {
        this.queue.add(drawable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void submitAll(final List<? extends Drawable> drawables) {
        this.queue.addAll(Collections.unmodifiableCollection(drawables));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void submitBackground(final Drawable drawable) {
       this.queue.add(FIRST_INDEX, drawable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void submitBackgroundAll(final List<? extends Drawable> drawables) {
       this.queue.addAll(FIRST_INDEX, drawables);
    }

    private void onClick(final MouseEvent e) {
        this.logger.debug("Mouse clicked at: " + e.getX() + ", " + e.getY());
        // call selected cell on MapController
        // this.mapController.selectedCell(new PositionImpl(e.getX(), e.getY()));
    }
}
