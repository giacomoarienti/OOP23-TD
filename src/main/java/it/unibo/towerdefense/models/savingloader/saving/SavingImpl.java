package it.unibo.towerdefense.models.savingloader.saving;

import java.util.Map;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.text.SimpleDateFormat;

import org.json.JSONObject;

/**
 * Class implementing the Saving interface.
 */
public class SavingImpl implements Saving {

    private static final String EXTENSION = ".json";
    private static final String DATE_FIELD = "date";
    private static final String DATE_FORMAT = "yyyy.MM.dd.HH.mm.ss";

    private final String date;
    private final Map<SavingFieldsEnum, String> json;

    /**
     * SavingImpl constructor from the json representation of objects
     * defined in SavingFieldsEnum.
     * @param date the date of the saving
     * @param json the json representation of the saving
     */
    public SavingImpl(
        final String date,
        final Map<SavingFieldsEnum, String> json
    ) {
        this.date = date;
        this.json = json;
    }

    /**
     * SavingImpl constructor from the json representation of objects
     * defined in SavingFieldsEnum.
     * @param json the json representation of the saving
     */
    public SavingImpl(
        final Map<SavingFieldsEnum, String> json
    ) {
        this(
            new SimpleDateFormat(DATE_FORMAT).format(new Date()),
            json
        );
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
        return this.json.get(SavingFieldsEnum.GAME);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getMapJson() {
        return this.json.get(SavingFieldsEnum.MAP);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDefensesJson() {
        return this.json.get(SavingFieldsEnum.DEFENSES);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toJSON() {
        final JSONObject obj = new JSONObject()
            .put(DATE_FIELD, this.date);
        // Add all the fields to the JSON object
        List.of(SavingFieldsEnum.values())
            .forEach(field ->
                obj.put(field.toString(), this.json.get(field)
            ));
        return obj.toString();
    }

    /**
     * Returns the saving object from JSON string.
     * @param jsonData the JSON representation of the saving
     * @return the saving object
     */
    public static Saving fromJson(final String jsonData) {
        final JSONObject jsonObject = new JSONObject(jsonData);
        // create the map from the JSON object
        final Map<SavingFieldsEnum, String> json = List.of(SavingFieldsEnum.values())
            .stream()
            .collect(
                Collectors.toMap(
                    field -> field,
                    field -> jsonObject.getString(field.toString())
                )
            );
        // return the saving object
        return new SavingImpl(
            jsonObject.getString(DATE_FIELD),
            json
        );
    }
}
