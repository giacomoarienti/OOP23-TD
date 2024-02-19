package it.unibo.towerdefense.views.window;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.Dimension;
import java.awt.Toolkit;

/**
 * Implementation of BaseView.
 */
public class WindowImpl implements Window {
    private static final String WINDOW_TITLE = "Tower Defense";
    private static final int PROPORTION = 5;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final JFrame frame;
    private final int screenWidth;
    private final int screenHeight;

    /**
     * Zero-argument constructorwidth.
     */
    public WindowImpl() {
        // create base frame
        this.frame = new JFrame(WindowImpl.WINDOW_TITLE);
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
        // resize the frame to the minimum size prior to displaying
        this.frame.pack();
        // push frame on screen
        this.frame.setVisible(true);
        this.logger.info("Window displayed");
    }
}
