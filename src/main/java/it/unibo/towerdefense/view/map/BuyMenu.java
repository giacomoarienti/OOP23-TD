package it.unibo.towerdefense.view.map;

import java.awt.GridLayout;
import java.util.List;
import java.util.stream.Stream;

import javax.swing.JButton;
import javax.swing.JPanel;

import org.apache.commons.lang3.tuple.Pair;

import it.unibo.towerdefense.commons.dtos.defenses.DefenseDescription;

public class BuyMenu extends JPanel{

    public BuyMenu(List<Pair<DefenseDescription, Boolean>> options) {
        super(new GridLayout(options.size(), 1));
        options.stream().map(o -> new JButton(o.getLeft().getDescription() + "\n" + o.getLeft().getCost()))
            .forEach(b -> this.add(b));
    }
}
