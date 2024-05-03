package it.unibo.towerdefense.models.savingloader.saving;

import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Class implementing the Saving interface.
 */
public class SavingImpl implements Saving {

    private static final String EXTENSION = ".json";
    private static final String GAME_FIELD = "game";
    private static final String MAP_FIELD = "map";
    private static final String DEFENSES_FIELD = "defenses";
    private static final String DATE_FIELD = "date";

    private final String date;
    private final String game;
    private final String map;
    private final String defenses;

    /**
     * SavingImpl constructor from the json representation of game, map and defenses.
     * @param game the game json representation
     * @param map the map json representation
     * @param defenses the defenses json representation
     */
    public SavingImpl(
        final String game,
        final String map,
        final String defenses
    ) {
        this.game = game;
        this.map = map;
        this.defenses = defenses;
        // set the saving name
        this.date = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss")
            .format(new Date());
    }


    /**
     * SavingImpl constructor from its name and
     * the json representation of game, map and defenses.
     * @param name the saving name
     * @param game the game json representation
     * @param map the map json representation
     * @param defenses the defenses json representation
     */
    public SavingImpl(
        final String date,
        final String game,
        final String map,
        final String defenses
    ) {
        this.date = date;
        this.game = game;
        this.map = map;
        this.defenses = defenses;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return this.date + EXTENSION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getGameJson() {
        return this.game;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getMapJson() {
        return this.map;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDefensesJson() {
        return this.defenses;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toJSON() {
        return new JSONObject()
            .put(DATE_FIELD, this.date)
            .put(GAME_FIELD, new JSONObject(this.game))
            .put(MAP_FIELD, new JSONObject(map))
            .put(DEFENSES_FIELD, new JSONObject(this.defenses))
            .toString();
    }

    /**
     * Returns the saving object from JSON string.
     * @param jsonData the JSON representation of the saving
     * @return the saving object
     */
    public static Saving fromJson(final String jsonData) {
        final JSONObject jsonObject = new JSONObject(jsonData);
        return new SavingImpl(
            jsonObject.getString(DATE_FIELD),
            jsonObject.getString(GAME_FIELD),
            jsonObject.getString(MAP_FIELD),
            jsonObject.getString(DEFENSES_FIELD)
        );
    }
}
