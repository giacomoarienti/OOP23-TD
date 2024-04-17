package it.unibo.towerdefense.views.modal;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import it.unibo.towerdefense.views.window.Window;

/**
 * Class implementing the Modal interface.
 */
public class ModalImpl implements Modal {

    private static final int BORDER_SIZE = 10;
    private static final String TITLE_FONT_NAME = "Calibri";
    private static final int TITLE_FONT_SIZE = 24;
    private static final int TITLE_FONT_STYLE = Font.BOLD;
    private final JDialog dialog;

    /**
     * Constructor for ModalImpl.
     * @param parent the parent window
     * @param title the title of the modal
     * @param content the content to be displayed
     */
    public ModalImpl(final Window parent, final String title, final ModalContent content) {
        // create the dialog frame
        this.dialog = new JDialog(parent.getFrame(), "", true);
        this.dialog.setUndecorated(true);
        // create main panel
        final JPanel panel = new JPanel(new BorderLayout());
        this.dialog.getContentPane().add(panel);
        // create top bar panel
        final JPanel topBar = new JPanel(new BorderLayout());
        panel.add(topBar, BorderLayout.NORTH);
        // add title
        final JLabel titleLabel = new JLabel(title);
        titleLabel.setBorder(
            BorderFactory.createEmptyBorder(BORDER_SIZE, BORDER_SIZE, BORDER_SIZE, BORDER_SIZE)
        );
        titleLabel.setFont(new Font(TITLE_FONT_NAME, TITLE_FONT_STYLE, TITLE_FONT_SIZE));
        topBar.add(titleLabel, BorderLayout.CENTER);
        // closing button
        final JButton closeButton = new JButton("X");
        closeButton.addActionListener((final ActionEvent e) -> this.closeModal());
        topBar.add(closeButton, BorderLayout.EAST);
        // build and add the content view to the dialog
        panel.add(content.build(this::close), BorderLayout.CENTER);
    }

    private void closeModal() {
        this.dialog.dispose();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void show() {
        // pack the content
        this.dialog.pack();
        // set position to center of the screen
        this.dialog.setLocationRelativeTo(null);
        // show the dialog
        this.dialog.setVisible(true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() {
        this.closeModal();
    }
}
