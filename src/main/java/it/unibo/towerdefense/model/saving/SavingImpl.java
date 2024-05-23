package it.unibo.towerdefense.model.saving;

import java.util.Map;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.json.JSONObject;

/**
 * Class implementing the Saving interface.
 */
public class SavingImpl implements Saving {

    private static final String DATE_FIELD = "name";
    private static final String EXTENSION = "json";
    private static final String DATE_FORMAT = "yyyy-MM-dd_HH-mm-ss-SSS";

    private final Date date;
    private final Map<SavingFieldsEnum, String> json;

    /**
     * SavingImpl constructor from the json representation of objects
     * defined in SavingFieldsEnum.
     * @param json the json representation of the saving
     */
    public SavingImpl(
        final Map<SavingFieldsEnum, String> json
    ) {
        this.json = json;
        this.date = new Date();
    }

    /**
     * SavingImpl constructor from the json representation of objects
     * defined in SavingFieldsEnum.
     * @param json the json representation of the saving
     */
    public SavingImpl(
        final Map<SavingFieldsEnum, String> json,
        final String date
    ) {
        this.json = json;
        try {
            this.date = new SimpleDateFormat(DATE_FORMAT).parse(date);
        } catch (final ParseException e) {
            throw new IllegalArgumentException("Invalid date format");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getFileName() {
        return String.format(
            "%s.%s",
            this.getFormattedDate(),
            SavingImpl.EXTENSION
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Date getDate() {
        return (Date) this.date.clone();
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
            .put(DATE_FIELD, this.getFormattedDate());
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
        final String date = jsonObject.getString(DATE_FIELD);
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
            json,
            date
        );
    }

    private String getFormattedDate() {
        return new SimpleDateFormat(DATE_FORMAT).format(this.date);
    }
}
