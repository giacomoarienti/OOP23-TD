package it.unibo.towerdefense.model.saving;

import java.util.Map;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONObject;

/**
 * Class implementing the Saving interface.
 */
public class SavingImpl implements Saving {

    private static final int FILE_NAME_LENGTH = 16;
    private static final String NAME_FIELD = "name";
    private static final String EXTENSION = ".json";

    private final String name;
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
        // random generated name
        this.name = RandomStringUtils.randomAlphanumeric(FILE_NAME_LENGTH);
    }

    /**
     * SavingImpl constructor from the json representation of objects
     * defined in SavingFieldsEnum.
     * @param json the json representation of the saving
     */
    public SavingImpl(
        final Map<SavingFieldsEnum, String> json,
        final String name
    ) {
        this.json = json;
        this.name = name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return String.format(
            "%s.%s",
            this.name,
            SavingImpl.EXTENSION
        );
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
            .put(NAME_FIELD, this.name);
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
        final String name = jsonObject.getString(NAME_FIELD);
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
            name
        );
    }
}
