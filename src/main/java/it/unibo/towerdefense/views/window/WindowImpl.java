package it.unibo.towerdefense.views.window;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Color;
import java.awt.Canvas;
import java.awt.BorderLayout;

/**
 * Implementation of BaseView.
 */
public class WindowImpl implements Window {

    private static final String WINDOW_TITLE = "Tower Defense";
    private static final int CANVAS_PROPORTION = 2;
    private static final int SIDE_MENUS_PROPORTION = 8;
    private static final int INFO_PROPORTION = 12;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final JFrame frame;
    private final Canvas canvas;
    private final JPanel upgradeMenu;
    private final JPanel buyMenu;
    private final JPanel infoPanel;

    /**
     * Zero-argument constructor.
     */
    public WindowImpl() {
        // create base frame
        this.frame = new JFrame(WindowImpl.WINDOW_TITLE);
        this.frame.setLayout(new BorderLayout());
        // calc and set starting frame size
        final Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        final int width = (int) screen.getWidth();
        final int height = (int) screen.getHeight();
        this.frame.setSize(width, height);
        // create canvas
        this.canvas = new Canvas();
        this.canvas.setPreferredSize(new Dimension(
            width / CANVAS_PROPORTION,
            height
        ));
        // create upgrade menu
        this.upgradeMenu = new JPanel();
        this.upgradeMenu.setPreferredSize(new Dimension(
            width / SIDE_MENUS_PROPORTION,
            height
        ));
        // create buy menu
        this.buyMenu = new JPanel();
        this.buyMenu.setPreferredSize(new Dimension(
            width / SIDE_MENUS_PROPORTION,
            height
        ));
        // create info panel
        this.infoPanel = new JPanel();
        this.infoPanel.setPreferredSize(new Dimension(
            width,
            height / INFO_PROPORTION
        ));
        // add panels to frame
        this.frame.add(this.canvas, BorderLayout.CENTER);
        this.frame.add(this.upgradeMenu, BorderLayout.WEST);
        this.frame.add(this.buyMenu, BorderLayout.EAST);
        this.frame.add(this.infoPanel, BorderLayout.NORTH);
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
        // push frame on screen
        this.frame.setVisible(true);
        this.logger.info("Window displayed");
    }

    /**
     * {@inheritDoc}
     */
    @SuppressFBWarnings(
        value = { "EI_EXPOSE_REP" },
        justification = "Intended behavior. Other views should be" +
            "able to edit its contents."
    )
    @Override
    public Canvas getCanvas() {
        return this.canvas;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressFBWarnings(
        value = { "EI_EXPOSE_REP" },
        justification = "Intended behavior. Other views should be" +
            "able to edit its contents."
    )
    @Override
    public JPanel getBuyMenuContainer() {
        return this.buyMenu;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressFBWarnings(
        value = { "EI_EXPOSE_REP" },
        justification = "Intended behavior. Other views should be" +
            "able to edit its contents."
    )
    @Override
    public JPanel getUpgradeMenuContainer() {
        return this.upgradeMenu;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressFBWarnings(
        value = { "EI_EXPOSE_REP" },
        justification = "Intended behavior. Other views should be" +
            "able to edit its contents."
    )
    @Override
    public JPanel getInfoContainer() {
       return this.infoPanel;
    }
}
