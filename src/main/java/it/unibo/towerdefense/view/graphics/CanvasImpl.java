package it.unibo.towerdefense.view.graphics;

import java.util.List;
import java.util.Objects;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JPanel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.unibo.towerdefense.commons.engine.Position;
import it.unibo.towerdefense.commons.engine.Size;
import it.unibo.towerdefense.commons.patterns.Observer;

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
    private static final Logger logger =
        LoggerFactory.getLogger(CanvasImpl.class);

    private final transient List<Drawable> queue = new ArrayList<>();
    private final List<Observer<Position>> observers = new ArrayList<>();
    private final Size canvasSize;

    private Size mapSize;

    /**
     * Constructor from width and height.
     * @param width the width of the canvas
     * @param height the height of the canvas
     */
    public CanvasImpl(final int width, final int height) {
        super();
        // set the size of the canvas
        this.canvasSize = Size.of(width, height);
        this.setPreferredSize(new Dimension(width, height));
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

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void addClickObserver(final Observer<Position> observer) {
        this.observers.add(observer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setMapSize(final Size mapSize) {
        this.mapSize = mapSize;
    }

    private void onClick(final MouseEvent e) {
        logger.debug("Mouse clicked at: " + e.getX() + ", " + e.getY());
        // do not handle event if mapSize is not set
        if (Objects.isNull(this.mapSize)) {
            return;
        }
        // calc the cell position
        final Position cell = Position.of(
            e.getX() / this.canvasSize.getWidth() * mapSize.getWidth(),
            e.getY() / this.canvasSize.getHeight() * mapSize.getHeight()
        );
        // call all the observers
        this.observers.forEach(observer -> observer.notify(cell));
    }
}
