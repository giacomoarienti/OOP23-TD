package it.unibo.towerdefense.models.savingloader.saving;

import java.util.List;
import java.util.Objects;
import java.util.Collections;

import org.json.JSONObject;

import it.unibo.towerdefense.models.game.GameDTO;

/**
 * Class implementing the Saving interface.
 */
public class SavingImpl implements Saving {

    private String name;
    private GameDTO game;
    private Object map;
    private List<Object> defenses;

    /**
     * SavingImpl constructor from a game, a map and a list of defenses.
     * @param game the game instance
     * @param map the map instance
     * @param defenses the list of defenses
     */
    public SavingImpl(
        final String name,
        final GameDTO game,
        final Object map,
        final List<Object> defenses
    ) {
        this.name = name;
        this.game = game;
        this.map = map;
        this.defenses = Collections.unmodifiableList(defenses);
    }

    /**
     * Constructs an empty SavingImpl.
     */
    public SavingImpl() {
        this(null, null, null, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        if (Objects.isNull(this.name)) {
            throw new IllegalStateException("Name is null");
        }
        return this.name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameDTO getGame() {
        if (Objects.isNull(this.game)) {
            throw new IllegalStateException("Game is null");
        }
        return this.game;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getMap() {
        if (Objects.isNull(this.map)) {
            throw new IllegalStateException("Map is null");
        }
        return this.map;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Object> getDefenses() {
        if (Objects.isNull(this.defenses)) {
            throw new IllegalStateException("Defenses is null");
        }
        return this.defenses;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toJSON() {
        return new JSONObject()
            .put("name", this.name)
            .put("game", this.game.toJSON())
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
            jsonObject.getString("name"),
            GameDTO.fromJson(jsonObject.getString("game")),
            null, //Map.fromJson(jsonObject.getString("map")),
            null //List.of(...);
        );
    }
}
