package it.unibo.towerdefense.views.window;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Color;

/**
 * Implementation of BaseView.
 */
public class WindowImpl implements Window {
    private static final String WINDOW_TITLE = "Tower Defense";
    private static final int PROPORTION = 2;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final JFrame frame;
    private final JPanel panel;
    private final int screenWidth;
    private final int screenHeight;

    /**
     * Zero-argument constructor.
     */
    public WindowImpl() {
        // create base frame
        this.frame = new JFrame(WindowImpl.WINDOW_TITLE);
        // create main panel
        this.panel = new JPanel();
        this.panel.setBackground(Color.BLACK);
        this.frame.add(panel);
        // calc and save window dimension
        final Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        this.screenWidth = (int) screen.getWidth() / WindowImpl.PROPORTION;
        this.screenHeight = (int) screen.getHeight() / WindowImpl.PROPORTION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getWidth() {
        return this.screenWidth;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getHeight() {
        return this.screenHeight;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setBody(final JPanel panel) {
        this.frame.setContentPane(panel);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void display() {
        // set default closing operation
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        /*
         * Make the frame one fifth the resolution of the screen. This very method is
         * enough for a single screen setup. In case of multiple monitors, the
         * primary is selected.
         */
        this.frame.setSize(this.getWidth(), this.getHeight());
        /*
         * Instead of appearing at (0,0), upper left corner of the screen, this
         * flag makes the OS window manager take care of the default positioning
         * on screen. Results may vary, but it is generally the best choice.
         */
        this.frame.setLocationByPlatform(true);
        // push frame on screen
        this.frame.setVisible(true);
        this.logger.info("Window displayed");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressFBWarnings(
        value = { "EI_EXPOSE_REP" },
        justification = "Only way to access the panel from outside."
    )
    public JPanel getPanel() {
        return this.panel;
    }
}
