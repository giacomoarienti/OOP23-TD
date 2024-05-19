package it.unibo.towerdefense.view.map;

import java.awt.GridLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;

import it.unibo.towerdefense.commons.dtos.map.BuildingOption;
import it.unibo.towerdefense.commons.patterns.Observer;

public class BuyMenu {

    private Observer<Integer> ob;

    public BuyMenu(Observer<Integer> observer) {
        this.ob = observer;
    }

    public JPanel getJPanel(List<BuildingOption> options) {
        JPanel jp = new JPanel(new GridLayout(options.size(), 1));
        options.stream().map(o -> {
            JButton b = new JButton(o.getDescription() + "\n" + o.getCost());
            b.setEnabled(o.isPurchasable());
            b.addActionListener(e -> ob.notify(options.indexOf(o)));
            return b;
        })
        .forEach(b -> jp.add(b));
        return jp;
    }
}
