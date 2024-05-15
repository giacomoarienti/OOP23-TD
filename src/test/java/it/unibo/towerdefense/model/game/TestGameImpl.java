package it.unibo.towerdefense.model.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.towerdefense.commons.dtos.game.GameDTO;
import it.unibo.towerdefense.commons.dtos.game.GameDTOImpl;

import java.io.IOException;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Assertions;

/**
 * Test class for the GameImpl class.
 */
class TestGameImpl {

    private static final String PLAYER_NAME = "TestPlayer";
    private static final int INVALID_MONEY_AMOUNT = -1;
    private static final int VALID_MONEY_AMOUNT = 10;
    private static final int INITIAL_WAVE = 1;
    private static final int INITIAL_LIVES = 100;
    private static final int INITIAL_MONEY = 100;
    private static final GameStatusEnum PAUSE_STATE = GameStatusEnum.PAUSE;

    private GameManager game;

    /**
     * Configuration step: this is performed BEFORE each test.
     * @throws IOException if the file cannot be created
     */
    @BeforeEach
    void setUp() throws IOException {
        this.game = new GameManagerImpl(PLAYER_NAME);
    }

    /**
     * Test the decreaseLives method.
     */
    @Test
    void testDecreaseLives() {
        final int initLives = this.game.getLives();
        IntStream.range(1, initLives).forEach((i) -> {
            Assertions.assertTrue(this.game.decreaseLives());
            Assertions.assertEquals(initLives - i, this.game.getLives());
        });
        // make sure at next call it will return false, as the lives are 0
        Assertions.assertFalse(this.game.decreaseLives());
    }

    /**
     * Test the addMoney and purchase methods.
     */
    @Test
    void testMoney() {
        int money = this.game.getMoney();
        // add money errors
        Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> this.game.addMoney(INVALID_MONEY_AMOUNT)
        );
        // correct add money
        this.game.addMoney(VALID_MONEY_AMOUNT);
        money += 10;
        Assertions.assertEquals(money, this.game.getMoney());
        // purchase errors
        Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> this.game.purchase(INVALID_MONEY_AMOUNT)
        );
        Assertions.assertFalse(this.game.purchase(money + 1));
        // correct purchase
        this.game.purchase(money);
        Assertions.assertEquals(0, this.game.getMoney());
    }

    /**
     * Test advanceWave method.
     */
    @Test
    void testWave() {
        int wave = this.game.getWave();
        Assertions.assertEquals(INITIAL_WAVE, wave);
        // advance wave
        this.game.advanceWave();
        wave++;
        Assertions.assertEquals(wave, this.game.getWave());
    }

    /**
     * Test GameImpl constructor providing initial values.
     */
    @Test
    void testConstructor() {
        final var game = new GameManagerImpl(new GameDTOImpl(PLAYER_NAME, INITIAL_LIVES, INITIAL_MONEY, INITIAL_WAVE));
        Assertions.assertEquals(PLAYER_NAME, game.getPlayerName());
        Assertions.assertEquals(INITIAL_MONEY, game.getMoney());
        Assertions.assertEquals(INITIAL_MONEY, game.getLives());
        Assertions.assertEquals(INITIAL_WAVE, game.getWave());
        Assertions.assertEquals(PAUSE_STATE, game.getGameState());
    }

    /**
     * Test json from DTO.
     */
    @Test
    void testJson() {
        // create json string from game
        final var gameDTO = this.game.toDTO();
        final var json = gameDTO.toJSON();
        // reconvert json string to game
        final var gameFromJson = GameDTO.fromJson(json);
        final var gameFromDTO = GameManager.fromDTO(gameFromJson);
        Assertions.assertEquals(this.game, gameFromDTO);
    }
}
