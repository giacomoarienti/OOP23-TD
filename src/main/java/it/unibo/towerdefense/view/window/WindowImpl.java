package it.unibo.towerdefense.view.window;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.unibo.towerdefense.commons.engine.Position;
import it.unibo.towerdefense.commons.engine.Size;
import it.unibo.towerdefense.commons.patterns.Observer;
import it.unibo.towerdefense.view.graphics.Canvas;
import it.unibo.towerdefense.view.graphics.CanvasImpl;
import it.unibo.towerdefense.view.graphics.Drawable;
import it.unibo.towerdefense.view.modal.Modal;
import it.unibo.towerdefense.view.modal.ModalContent;
import it.unibo.towerdefense.view.modal.ModalImpl;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.awt.Color;

/**
 * Implementation of BaseView.
 */
public class WindowImpl implements Window {

    private static final String WINDOW_TITLE = "Tower Defense";
    private static final String ERROR_ALERT_TITLE = "Error";
    private static final Logger logger =
        LoggerFactory.getLogger(WindowImpl.class);

    private final List<Modal> openModals =
        new ArrayList<>();
    private final Size resolution;
    private final JFrame frame;
    private final Canvas canvas;
    private final JPanel buyMenu;
    private final JPanel gamePanel;
    private final JPanel controlsPanel;

    /**
     * Creates a window with the specified size.
     * @param resolution the size of the window
     */
    public WindowImpl(final Size resolution) {
        this.resolution = resolution.copy();
        // calculate sizes
        final int w = this.resolution.getWidth();
        final int h = this.resolution.getHeight();
        final int wMenu = (w - h) / 2;
        // create base frame
        this.frame = new JFrame(WINDOW_TITLE);
        this.frame.setLayout(null);
        this.frame.setPreferredSize(new Dimension(w, h));
        // create canvas
        this.canvas = new CanvasImpl();
        // create buy menus
        this.buyMenu = new JPanel();
        // create infoPanel
        final var infoPanel = new JPanel();
        infoPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        // create container for infoPanel
        final var container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        // create gamePanel and controlsPanel
        this.gamePanel = new JPanel();
        this.controlsPanel = new JPanel();
        // set bounds for each component
        infoPanel.setBounds(0, 0, wMenu, h);
        this.canvas.setBounds(wMenu, 0, h, h);
        this.buyMenu.setBounds(wMenu + h, 0, wMenu, h);
        // add panels to frame
        this.frame.add((JPanel) this.canvas);
        this.frame.add(infoPanel);
        this.frame.add(this.buyMenu);
        // add gamePanel and controlsPanel to container
        infoPanel.add(container);
        container.add(this.gamePanel);
        container.add(this.controlsPanel);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void display() {
        // set frame background
        this.frame.setBackground(Color.BLACK);
        // set default closing operation
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // position the frame on top left corner
        this.frame.pack();
        this.frame.setLocation(0, 0);
        // set frame not resizable
        this.frame.setResizable(false);
        // push frame on screen
        this.frame.setVisible(true);
        logger.info("Window displayed");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() {
        this.frame.dispose();
        logger.info("Window closed");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Size getResolution() {
        return this.resolution.copy();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Size getCanvasSize() {
        return this.canvas.getCanvasSize();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void displayError(final String message) {
        JOptionPane.showMessageDialog(frame, message, ERROR_ALERT_TITLE, JOptionPane.ERROR_MESSAGE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void displayModal(final String title, final ModalContent content) {
        this.hideAllModals();
        final Modal modal = new ModalImpl(
            this.frame,
            title,
            content,
            this::removeModal
        );
        this.addModal(modal);
        modal.display();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setGameContent(final JPanel panel) {
        this.gamePanel.removeAll();
        this.gamePanel.add(panel);
        this.gamePanel.revalidate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setControlsContent(final JPanel panel) {
        this.controlsPanel.removeAll();
        this.controlsPanel.add(panel);
        this.controlsPanel.revalidate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setBuyMenuContent(final JPanel panel) {
        this.buyMenu.removeAll();
        if (Objects.nonNull(panel)) {
            this.buyMenu.add(panel);
        }
        this.buyMenu.revalidate();
        this.buyMenu.repaint();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void submitToCanvas(final Drawable drawable) {
        this.canvas.submit(drawable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void submitAllToCanvas(final List<? extends Drawable> drawables) {
        this.canvas.submitAll(drawables);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void submitBackgroundAll(List<? extends Drawable> drawables) {
        this.canvas.submitBackgroundAll(drawables);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void renderCanvas() {
        this.canvas.render();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addCanvasClickObserver(final Observer<Position> observer) {
        this.canvas.addClickObserver(observer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setMapSize(final Size mapSize) {
        this.canvas.setMapSize(mapSize);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clearCanvasQueue() {
        this.canvas.clearCanvasQueue();
    }

    private void hideAllModals() {
        this.openModals.forEach((m )-> m.setVisible(false));
    }

    private void addModal(final Modal modal) {
        this.openModals.add(modal);
    }

    private void removeModal(final Modal modal) {
        // remove last modal
        this.openModals.remove(modal);
        // if there are still modals, show the last one
        if (!this.openModals.isEmpty()) {
            this.openModals.get(this.openModals.size() - 1)
                .setVisible(true);
        }
    }
}
