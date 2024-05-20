package it.unibo.towerdefense.view.graphics;

import java.util.List;
import java.util.Objects;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JPanel;

import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.unibo.towerdefense.commons.engine.Position;
import it.unibo.towerdefense.commons.engine.Size;
import it.unibo.towerdefense.commons.patterns.Observer;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Main canvas of the game.
 */
public class CanvasImpl extends JPanel implements Canvas {

    private static final long serialVersionUID = 1L;
    private static final int FIRST_INDEX = 0;
    private static final int START_Y = 0;
    private static final int START_X = 0;
    private static final Logger logger =
        LoggerFactory.getLogger(CanvasImpl.class);

    private final transient List<Drawable> queue = new ArrayList<>();
    private final List<Observer<Position>> observers = new ArrayList<>();

    private Size mapSize;
    private Pair<Double, Double> scale = Pair.of(1.0, 1.0);

    /**
     * Default constructor.
     */
    public CanvasImpl() {
        super();
        // bind on click event
        this.addMouseListener(new MouseAdapter() {
            public void mouseClicked(final MouseEvent e) {
                CanvasImpl.this.onClick(e);
            }
        });
        // set double buffering
        this.setDoubleBuffered(true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void paintComponent(final Graphics g) {
        super.paintComponent(g);
        // create a copy of graphics and set rendering hints
        final Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        // clear the canvas
        g2d.clearRect(START_X, START_Y, this.getWidth(), this.getHeight());
        // draw all the elements in queue
        final var syncQueue = Collections.synchronizedList(new ArrayList<>(this.queue));
        synchronized (syncQueue) {
            for (final Drawable drawable: syncQueue) {
                drawable.setScale(this.scale);
                drawable.paint(g2d);
            }
        }
        // clear the queue and dispose the graphics
        synchronized (this.queue) {
            this.queue.clear();
        }
        g2d.dispose();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void render() {
        this.repaint();
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
    public void addClickObserver(final Observer<Position> observer) {
        this.observers.add(observer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setMapSize(final Size mapSize) {
        this.mapSize = mapSize;
        this.scale = Pair.of(
            this.getWidth() / (double) mapSize.getWidth(),
            this.getHeight() / (double) mapSize.getHeight()
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Size getCanvasSize() {
        return Size.of(this.getWidth(), this.getHeight());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setBounds(final int x, final int y, final int width, final int height) {
        super.setBounds(x, y, width, height);
    }

    private void onClick(final MouseEvent e) {
        logger.debug("Mouse clicked at: " + e.getX() + ", " + e.getY());
        // do not handle event if mapSize is not set
        if (Objects.isNull(this.mapSize)) {
            return;
        }
        // calc the cell position
        final Position cell = Position.of(
            (int) ((e.getX() / (double) this.getWidth()) * this.mapSize.getWidth()),
            (int) ((e.getY() / (double) this.getHeight()) * this.mapSize.getHeight())
        );
        // call all the observers
        this.observers.forEach(observer -> observer.notify(cell));
    }
}
