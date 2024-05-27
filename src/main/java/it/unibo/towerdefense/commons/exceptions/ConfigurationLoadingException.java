package it.unibo.towerdefense.commons.exceptions;

/**
 * Class for signaling an unrecoverable error during the loading of the
 * configuration from file.
 */
public class ConfigurationLoadingException extends RuntimeException {

    public static final long serialVersionUID = 29389428;

    /**
     * Constructor for the exception.
     *
     * @param s     a text description of the problem
     */
    public ConfigurationLoadingException(final String s) {
        super(s);
    }

    /**
     * Constructor for the exception.
     *
     * @param s     a text description of the problem
     * @param cause the throwable which caused this
     */
    public ConfigurationLoadingException(final String s, final Throwable cause) {
        super(s, cause);
    }
}
