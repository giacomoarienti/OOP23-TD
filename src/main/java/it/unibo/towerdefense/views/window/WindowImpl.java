package it.unibo.towerdefense.views.window;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.unibo.towerdefense.views.modal.ModalContent;
import it.unibo.towerdefense.models.engine.Size;
import it.unibo.towerdefense.views.graphics.Canvas;
import it.unibo.towerdefense.views.graphics.CanvasImpl;
import it.unibo.towerdefense.views.graphics.Drawable;
import it.unibo.towerdefense.views.modal.Modal;
import it.unibo.towerdefense.views.modal.ModalImpl;

import java.awt.Dimension;
import java.awt.FlowLayout;
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

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final Size size;
    private final JFrame frame;
    private final Canvas canvas;
    private final JPanel upgradeMenu;
    private final JPanel buyMenu;
    private final JPanel infoPanel;

    /**
     * Creates a window with the specified size.
     * @param size the size of the window
     */
    public WindowImpl(final Size size) {
        this.size = size.copy();
        final int w = this.size.getWidth();
        final int h = this.size.getHeight();
        // create base frame
        this.frame = new JFrame(WINDOW_TITLE);
        this.frame.setLayout(new BorderLayout());
        this.frame.setPreferredSize(new Dimension(w, h));
        // create canvas
        this.canvas = new CanvasImpl(w / CANVAS_PROPORTION, h);
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
        // this.frame.add(this.buyMenu, BorderLayout.EAST);
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
        this.logger.info("Window displayed");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() {
        this.frame.dispose();
        this.logger.info("Window closed");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Size getSize() {
        return this.size.copy();
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
        final Modal modal = new ModalImpl(this.frame, title, content);
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
        this.infoPanel.revalidate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setUpgradesContent(final JPanel panel) {
        this.upgradeMenu.removeAll();
        this.upgradeMenu.add(panel);
        this.infoPanel.revalidate();
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

    private JPanel createPanel(final int width, final int height) {
        final JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(width, height));
        return panel;
    }

}
