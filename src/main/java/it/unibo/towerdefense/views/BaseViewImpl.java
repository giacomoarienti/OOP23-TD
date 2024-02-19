package it.unibo.towerdefense.views;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.Dimension;
import java.awt.Toolkit;

import it.unibo.towerdefense.commons.Constants;

public class BaseViewImpl extends JFrame implements BaseView {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final int screenWidth;
    private final int screenHeight;
    
    public BaseViewImpl() {
        super(Constants.WINDOW_TITLE);
        // calc and save window dimension
        final Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        this.screenWidth = (int) screen.getWidth() / Constants.PROPORTION;
        this.screenHeight = (int) screen.getHeight() / Constants.PROPORTION;
    }

    public int getScreenWidth() {
        return this.screenWidth;
    }

    public int getScreenHeight() {
        return this.screenHeight;
    }

    public void setContet(final JPanel panel) {
        this.setContet(panel);
    }

    public void display() {
        // set default closing operation
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        /*
         * Make the frame one fifth the resolution of the screen. This very method is
         * enough for a single screen setup. In case of multiple monitors, the
         * primary is selected.
         */
        this.setSize(this.getWidth(), this.getHeight());
        /*
         * Instead of appearing at (0,0), upper left corner of the screen, this
         * flag makes the OS window manager take care of the default positioning
         * on screen. Results may vary, but it is generally the best choice.
         */
        this.setLocationByPlatform(true);
        // resize the frame to the minimum size prior to displaying
        this.pack();
        // push frame on screen
        this.setVisible(true);
        this.logger.info("BaseView displayed");
    }
}
