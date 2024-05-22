package it.unibo.towerdefense.view.modal;

import java.awt.BorderLayout;
import java.awt.Font;
import java.util.function.Consumer;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JFrame;

/**
 * Class implementing the Modal interface.
 */
public class ModalImpl implements Modal {

    private static final int BORDER_SIZE = 20;
    private static final String TITLE_FONT_NAME = "Calibri";
    private static final int TITLE_FONT_SIZE = 24;
    private static final int TITLE_FONT_STYLE = Font.BOLD;

    private final JDialog dialog;
    private final Consumer<Modal> onClose;

    /**
     * Constructor for ModalImpl.
     * @param parent the parent frame
     * @param title the title of the modal
     * @param content the content to be displayed
     * @param onClose the action to be performed when the modal is closed
     */
    public ModalImpl(
        final JFrame parent,
        final String title,
        final ModalContent content,
        final Consumer<Modal> onClose
    ) {
        this.onClose = onClose;
        // create the dialog frame
        this.dialog = new JDialog(parent, "", true);
        this.dialog.setUndecorated(true);
        // create main panel
        final JPanel panel = new JPanel(new BorderLayout());
        this.dialog.getContentPane().add(panel);
        // add title
        final JLabel titleLabel = new JLabel(title);
        titleLabel.setBorder(
            BorderFactory.createEmptyBorder(BORDER_SIZE, BORDER_SIZE, BORDER_SIZE, BORDER_SIZE)
        );
        titleLabel.setFont(new Font(TITLE_FONT_NAME, TITLE_FONT_STYLE, TITLE_FONT_SIZE));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        panel.add(titleLabel, BorderLayout.NORTH);
        // build and add the content view to the dialog
        panel.add(content.build(this::close), BorderLayout.CENTER);
    }

    /**
     * Constructor for ModalImpl.
     * @param parent the parent frame
     * @param title the title of the modal
     * @param content the content to be displayed
     */
    public ModalImpl(
        final JFrame parent,
        final String title,
        final ModalContent content
    ) {
        this(parent, title, content, (m) -> {});
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void display() {
        // pack the content
        this.dialog.pack();
        // set position relative to the parent frame
        this.dialog.setLocationRelativeTo(
            this.dialog.getParent()
        );
        // show the dialog
        this.dialog.setVisible(true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() {
        this.closeModal();
        this.onClose.accept(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void dispose() {
        this.closeModal();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setVisible(final boolean b) {
        this.dialog.setVisible(b);
    }

    private void closeModal() {
        this.dialog.dispose();
    }
}
