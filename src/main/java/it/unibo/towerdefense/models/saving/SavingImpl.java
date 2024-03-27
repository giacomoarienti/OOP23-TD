package it.unibo.towerdefense.models.saving;

import java.util.List;
import java.util.Objects;

import org.json.JSONObject;

import it.unibo.towerdefense.models.game.Game;

/**
 * Class implementing the Saving interface.
 */
public class SavingImpl implements Saving {

    private Game game;
    private Object map;
    private List<Object> defenses;

    /**
     * SavingImpl constructor from a game, a map and a list of defenses.
     * @param game the game instance
     * @param map the map instance
     * @param defenses the list of defenses
     */
    public SavingImpl(final Game game, final Object map, final List<Object> defenses) {
        this.game = game;
        this.map = map;
        this.defenses = defenses;
    }

    /**
     * Constructs an empty SavingImpl.
     */
    public SavingImpl() {
        this(null, null, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Game getGame() {
        if (Objects.isNull(this.game)) {
            throw new IllegalStateException("Game is null");
        }
        return game;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getMap() {
        if (Objects.isNull(this.map)) {
            throw new IllegalStateException("Map is null");
        }
        return map;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Object> getDefenses() {
        if (Objects.isNull(this.defenses)) {
            throw new IllegalStateException("Defenses is null");
        }
        return defenses;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toJSON() {
        return new JSONObject()
            .put("game", game.toJSON())
            // .put("map", map.toJSON())
            // .put("defenses", null)
            .toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Saving fromJSON(final String jsonData) {
        // TODO implement map and defenses deserialization
        final JSONObject jsonObject = new JSONObject(jsonData);
        return new SavingImpl(
            Game.fromJson(jsonObject.getString("game")),
            null, //Map.fromJson(jsonObject.getString("map")),
            null //List.of(...);
        );
    }
}
