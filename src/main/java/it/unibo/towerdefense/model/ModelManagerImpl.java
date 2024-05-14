package it.unibo.towerdefense.model;

import it.unibo.towerdefense.commons.dtos.game.GameDTO;
import it.unibo.towerdefense.commons.engine.Size;
import it.unibo.towerdefense.model.defenses.DefenseManager;
import it.unibo.towerdefense.model.defenses.DefenseManagerImpl;
import it.unibo.towerdefense.model.enemies.EnemiesManager;
import it.unibo.towerdefense.model.enemies.EnemiesManagerImpl;
import it.unibo.towerdefense.model.game.GameManager;
import it.unibo.towerdefense.model.game.GameManagerImpl;
import it.unibo.towerdefense.model.map.MapManager;
import it.unibo.towerdefense.model.map.MapManagerImpl;
import it.unibo.towerdefense.model.saving.Saving;

public class ModelManagerImpl implements ModelManager {

    private final MapManager map;
    private final DefenseManager defenses;
    private final EnemiesManager enemies;
    private final GameManager game;

    public ModelManagerImpl(final Size cellSize, final String playerName){
        map = new MapManagerImpl(cellSize);
        defenses = new DefenseManagerImpl();
        enemies = new EnemiesManagerImpl();
        game = new GameManagerImpl(playerName);
        map.bind(this);
        defenses.bind(this);
        enemies.bind(this);
    }

    public ModelManagerImpl(final Saving s){
        map = new MapManagerImpl(s.getMapJson());
        defenses = new DefenseManagerImpl(s.getDefensesJson());
        enemies = new EnemiesManagerImpl();
        game = new GameManagerImpl(
            GameDTO.fromJson(s.getGameJson())
        );
        map.bind(this);
        defenses.bind(this);
        enemies.bind(this);
    }

    @Override
    public MapManager getMap() {
        return map;
    }

    @Override
    public DefenseManager getDefenses() {
        return defenses;
    }

    @Override
    public EnemiesManager getEnemies() {
        return enemies;
    }

    @Override
    public GameManager getGame() {
        return game;
    }
}
