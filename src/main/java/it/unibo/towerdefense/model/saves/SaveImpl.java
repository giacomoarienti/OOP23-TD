package it.unibo.towerdefense.model.saves;

import java.util.Map;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.json.JSONObject;

import com.google.common.base.Objects;

/**
 * Class implementing the Save interface.
 */
public class SaveImpl implements Save {

    private static final String DATE_FIELD = "name";
    private static final String EXTENSION = "json";
    private static final String DATE_FORMAT = "yyyy-MM-dd_HH-mm-ss-SSS";

    private final Date date;
    private final Map<SaveFieldsEnum, String> json;

    /**
     * SaveImpl constructor from the json representation and date.
     * @param json the json representation of the save
     * @param date the date of the save
     */
    public SaveImpl(
        final Map<SaveFieldsEnum, String> json,
        final Date date
    ) {
        this.json = Map.copyOf(json);
        this.date = new Date(date.getTime());
    }

    /**
     * SaveImpl constructor from the json representation.
     * @param json the json representation of the save
     */
    public SaveImpl(
        final Map<SaveFieldsEnum, String> json
    ) {
        this(json, new Date());
    }

    /**
     * SaveImpl constructor from the json representation and date string.
     * @param json the json representation of the save
     * @param date the date of the save
     * @throws ParseException if the date is not in the correct format
     */
    public SaveImpl(
        final Map<SaveFieldsEnum, String> json,
        final String date
    ) throws ParseException {
        this(
            json,
            new SimpleDateFormat(DATE_FORMAT, Locale.getDefault()).parse(date)
        );
    }



    /**
     * {@inheritDoc}
     */
    @Override
    public String getFileName() {
        return String.format(
            "%s.%s",
            this.getFormattedDate(),
            SaveImpl.EXTENSION
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
        return this.json.get(SaveFieldsEnum.GAME);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getMapJson() {
        return this.json.get(SaveFieldsEnum.MAP);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDefensesJson() {
        return this.json.get(SaveFieldsEnum.DEFENSES);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toJSON() {
        final JSONObject obj = new JSONObject()
            .put(DATE_FIELD, this.getFormattedDate());
        // Add all the fields to the JSON object
        List.of(SaveFieldsEnum.values())
            .forEach(field ->
                obj.put(field.toString(), this.json.get(field)
            ));
        return obj.toString();
    }

    /**
     * Returns the save object from JSON string.
     * @param jsonData the JSON representation of the save
     * @return the save object
     */
    public static Save fromJson(final String jsonData) {
        final JSONObject jsonObject = new JSONObject(jsonData);
        final String date = jsonObject.getString(DATE_FIELD);
        // create the map from the JSON object
        final Map<SaveFieldsEnum, String> json = List.of(SaveFieldsEnum.values())
            .stream()
            .collect(
                Collectors.toMap(
                    field -> field,
                    field -> jsonObject.getString(field.toString())
                )
            );
        // return the save object
        try {
            return new SaveImpl(
                json,
                date
            );
        } catch (final ParseException e) {
            throw new IllegalStateException("Invalid date", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(
            this.getDate(),
            this.getGameJson(),
            this.getMapJson(),
            this.getDefensesJson()
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof SaveImpl) {
            final SaveImpl saveObject = (SaveImpl) obj;
            return this.hashCode() == saveObject.hashCode();
        }
        return false;
    }

    private String getFormattedDate() {
        return new SimpleDateFormat(DATE_FORMAT).format(this.date);
    }
}
