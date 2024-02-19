package it.unibo.towerdefense;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.unibo.towerdefense.views.BaseView;
import it.unibo.towerdefense.views.BaseViewImpl;

public class TowerDefense {

    public static void main(final String[] args) {
        final Logger logger = LoggerFactory.getLogger(TowerDefense.class);
        logger.info("app started");
        // display BaseView
        final BaseView baseView = new BaseViewImpl();
        baseView.display();
    }
}