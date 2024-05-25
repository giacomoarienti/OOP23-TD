package it.unibo.towerdefense.model.saving;

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
 * Class implementing the Saving interface.
 */
public class SavingImpl implements Saving {

    private static final String DATE_FIELD = "name";
    private static final String EXTENSION = "json";
    private static final String DATE_FORMAT = "yyyy-MM-dd_HH-mm-ss-SSS";

    private final Date date;
    private final Map<SavingFieldsEnum, String> json;

    /**
     * SavingImpl constructor from the json representation and date.
     * @param json the json representation of the saving
     * @param date the date of the saving
     */
    public SavingImpl(
        final Map<SavingFieldsEnum, String> json,
        final Date date
    ) {
        this.json = Map.copyOf(json);
        this.date = new Date(date.getTime());
    }

    /**
     * SavingImpl constructor from the json representation.
     * @param json the json representation of the saving
     */
    public SavingImpl(
        final Map<SavingFieldsEnum, String> json
    ) {
        this(json, new Date());
    }

    /**
     * SavingImpl constructor from the json representation and date string.
     * @param json the json representation of the saving
     * @param date the date of the saving
     * @throws ParseException if the date is not in the correct format
     */
    public SavingImpl(
        final Map<SavingFieldsEnum, String> json,
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
        try {
            return new SavingImpl(
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
        if (obj instanceof SavingImpl) {
            final SavingImpl savingObject = (SavingImpl) obj;
            return this.hashCode() == savingObject.hashCode();
        }
        return false;
    }

    private String getFormattedDate() {
        return new SimpleDateFormat(DATE_FORMAT).format(this.date);
    }
}
