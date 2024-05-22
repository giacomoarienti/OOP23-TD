package it.unibo.towerdefense.view.map;

import java.util.List;

import javax.swing.JPanel;

import it.unibo.towerdefense.commons.dtos.map.BuildingOption;

/**
 * Interface to obtain a buy JPanel from a list of BuildingOptions.
 */
public interface BuyMenu {

    /**
     * New JPanel getter.
     * @param options options to convert in buttons.
     * @return A JPanel with buttons.
     */
    JPanel getJPanel(List<BuildingOption> options);

}
