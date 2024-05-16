package it.unibo.towerdefense.view.window;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.unibo.towerdefense.commons.engine.Position;
import it.unibo.towerdefense.commons.engine.Size;
import it.unibo.towerdefense.commons.engine.SizeImpl;
import it.unibo.towerdefense.commons.patterns.Observer;
import it.unibo.towerdefense.view.graphics.Canvas;
import it.unibo.towerdefense.view.graphics.CanvasImpl;
import it.unibo.towerdefense.view.graphics.Drawable;
import it.unibo.towerdefense.view.modal.Modal;
import it.unibo.towerdefense.view.modal.ModalContent;
import it.unibo.towerdefense.view.modal.ModalImpl;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;
import java.awt.Color;
import java.awt.BorderLayout;

/**
 * Implementation of BaseView.
 */
public class WindowImpl implements Window {

    private static final String WINDOW_TITLE = "Tower Defense";
    private static final String ERROR_ALERT_TITLE = "Error";
    private static final int CANVAS_PROPORTION = 2;
    private static final int SIDE_MENUS_PROPORTION = 8;
    private final static Logger logger =
        LoggerFactory.getLogger(WindowImpl.class);

    private final List<Modal> openModals =
        new ArrayList<>();
    private final Size resolution;
    private final JFrame frame;
    private final Canvas canvas;
    private final JPanel upgradeMenu;
    private final JPanel buyMenu;
    private final JPanel infoPanel;
    private final Size canvasSize;

    /**
     * Creates a window with the specified size.
     * @param resolution the size of the window
     */
    public WindowImpl(final Size resolution) {
        this.resolution = resolution.copy();
        // create base frame
        this.frame = new JFrame(WINDOW_TITLE);
        this.frame.setExtendedState(JFrame.NORMAL);
        this.frame.pack();
        // get the insets (decorations like title bar and borders)
        final Insets insets = this.frame.getInsets();
        // calculate the frame size considering the insets
        final int w = this.resolution.getWidth() - insets.left - insets.right;
        final int h = this.resolution.getHeight() - insets.top - insets.bottom;
        this.frame.setPreferredSize(new Dimension(w, h));
        // set frame layout
        this.frame.setLayout(new BorderLayout());
        // create canvas
        final int canvasWidth = w / CANVAS_PROPORTION;
        final int canvasHeight = h;
        this.canvasSize = new SizeImpl(canvasWidth, canvasHeight);
        this.canvas = new CanvasImpl(canvasWidth, canvasHeight);
        // create panels
        this.upgradeMenu = createPanel(w / SIDE_MENUS_PROPORTION, h);
        final var rightMenu = createPanel(w / SIDE_MENUS_PROPORTION, h);
        rightMenu.setLayout(new FlowLayout());
        this.infoPanel = new JPanel();
        this.buyMenu = new JPanel();
        rightMenu.add(this.infoPanel);
        rightMenu.add(this.buyMenu);
        // add panels to frame
        this.frame.add((JPanel) this.canvas, BorderLayout.CENTER);
        this.frame.add(this.upgradeMenu, BorderLayout.WEST);
        this.frame.add(rightMenu, BorderLayout.EAST);
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
        /*
         * Instead of appearing at (0,0), upper left corner of the screen, this
         * flag makes the OS window manager take care of the default positioning
         * on screen. Results may vary, but it is generally the best choice.
         */
        this.frame.setLocationByPlatform(true);
        // set frame not resizable
        this.frame.setResizable(false);
        // push frame on screen
        this.frame.pack();
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
        return this.canvasSize.copy();
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
    public void setInfoContent(final JPanel panel) {
        this.infoPanel.removeAll();
        this.infoPanel.add(panel);
        this.infoPanel.revalidate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setBuyMenuContent(final JPanel panel) {
        this.buyMenu.removeAll();
        this.buyMenu.add(panel);
        this.buyMenu.revalidate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setUpgradesContent(final JPanel panel) {
        this.upgradeMenu.removeAll();
        this.upgradeMenu.add(panel);
        this.upgradeMenu.revalidate();
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

    private JPanel createPanel(final int width, final int height) {
        final JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(width, height));
        return panel;
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
