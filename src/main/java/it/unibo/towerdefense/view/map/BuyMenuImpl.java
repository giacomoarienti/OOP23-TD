package it.unibo.towerdefense.view.map;

import java.awt.GridLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

import it.unibo.towerdefense.commons.dtos.map.BuildingOption;
import it.unibo.towerdefense.commons.patterns.Observer;

/**
 * Class to obtain a buy JPanel with buttons for each option witch call the given observer.
 */
public class BuyMenuImpl implements BuyMenu {

    private final Observer<Integer> ob;

    /**
     * Constructor from the observer.
     * @param observer observer to use in buttons action listeners.
     */
    public BuyMenuImpl(final Observer<Integer> observer) {
        this.ob = observer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JPanel getJPanel(final List<BuildingOption> options) {
        final JPanel jp = new JPanel(new GridLayout(options.size(), 1));
        if (!options.isEmpty()) {
            jp.setBorder(BorderFactory.createTitledBorder(getTitle(options.get(options.size() - 1).getText())));
            options.stream().map(o -> {
                final JButton b = new JButton(o.getText() + " " + o.getCost());
                b.setEnabled(o.isAvailable());
                b.addActionListener(e -> ob.notify(options.indexOf(o)));
                b.setToolTipText(htmlEncode(o.getDescription()));
                return b;
            })
            .forEach(b -> jp.add(b));
        }

        return jp;
    }

    private static String getTitle(final String text) {
        return ("Sell".equals(text) ? "Upgrades" : "Building") + " menu\n";
    }

    /**
     * @return a html encoded string for the option tooltip.
     * @param tooltip the original string.
     */
    private static String htmlEncode(final String tooltip) {
        return "<html>" + tooltip.replace("\n", "<br>") + "</html>";
    }
}
