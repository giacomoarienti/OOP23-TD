package it.unibo.towerdefense.model;

import java.util.Objects;
import java.util.stream.Stream;

import it.unibo.towerdefense.commons.dtos.defenses.DefenseDescription;
import it.unibo.towerdefense.commons.dtos.enemies.EnemyInfo;
import it.unibo.towerdefense.commons.dtos.game.GameDTO;
import it.unibo.towerdefense.commons.dtos.map.CellInfo;
import it.unibo.towerdefense.commons.engine.Position;
import it.unibo.towerdefense.commons.engine.Size;
import it.unibo.towerdefense.commons.patterns.Observer;
import it.unibo.towerdefense.model.defenses.DefenseManager;
import it.unibo.towerdefense.model.defenses.DefenseManagerImpl;
import it.unibo.towerdefense.model.enemies.EnemiesManager;
import it.unibo.towerdefense.model.enemies.EnemiesManagerImpl;
import it.unibo.towerdefense.model.game.GameManager;
import it.unibo.towerdefense.model.game.GameManagerImpl;
import it.unibo.towerdefense.model.map.MapManager;
import it.unibo.towerdefense.model.map.MapManagerImpl;
import it.unibo.towerdefense.model.saving.Saving;

/**
 * Implementation of the ModelManager interface.
 */
public class ModelImpl implements ModelManager, Model {

    private MapManager map;
    private DefenseManager defenses;
    private EnemiesManager enemies;
    private GameManager game;
    private  boolean initialized;

    /**
     * Empty constructor.
     */
    public ModelImpl() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void init(final String playerName, final Size cellSize){
        map = new MapManagerImpl(cellSize);
        defenses = new DefenseManagerImpl();
        enemies = new EnemiesManagerImpl();
        game = new GameManagerImpl(playerName);
        this.bindManagers();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void init(final Saving s){
        map = new MapManagerImpl(s.getMapJson());
        defenses = new DefenseManagerImpl(s.getDefensesJson());
        enemies = new EnemiesManagerImpl();
        game = new GameManagerImpl(
            GameDTO.fromJson(s.getGameJson())
        );
        this.bindManagers();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MapManager getMap() {
        if (Objects.isNull(map)) {
            throw new IllegalStateException("Map not initialized");
        }
        return map;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DefenseManager getDefenses() {
        if (Objects.isNull(defenses)) {
            throw new IllegalStateException("Defenses not initialized");
        }
        return defenses;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EnemiesManager getEnemies() {
        if (Objects.isNull(enemies)) {
            throw new IllegalStateException("Enemies not initialized");
        }
        return enemies;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameManager getGame() {
        if (Objects.isNull(game)) {
            throw new IllegalStateException("Game not initialized");
        }
        return game;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isPlaying() {
        return game.isPlaying();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void resume() {
        game.resume();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addGameObserver(final Observer<GameDTO> observer) {
        game.addObserver(observer);
    }

    /**
     * {@inheritDoc}
     */
    public Stream<EnemyInfo> getEnemiesDTOs() {
        return enemies.getEnemies().stream().map(e -> e.info());
    }

    /**
     * {@InheritDoc}
     */
    @Override
    public Stream<DefenseDescription> getDefensesDTOs() {
        return defenses.getDefenses().stream();
    }

    /**
     * {@InheritDoc}
     */
    @Override
    public Stream<CellInfo> getMapDTOs() {
        return map.getMap();
    }

    /**
     * {@InheritDoc}
     */
    @Override
    public void selectCell(Position position) {
        map.select(position);
    }

    /**
     * {@InheritDoc}
     */
    @Override
    public void startWave() {
        this.game.startWave();
    }

    /**
     * {@InheritDoc}
     */
    @Override
    public void update() {
        this.initializationCheck();
        // update the models
        game.update();
        enemies.update();
    }

    private void bindManagers() {
        game.bind(this);
        map.bind(this);
        defenses.bind(this);
        enemies.bind(this);
        // set initialized to true
        initialized = true;
    }

    private void initializationCheck() {
        if (!initialized) {
            throw new IllegalStateException("Model not initialized");
        }
    }
}
