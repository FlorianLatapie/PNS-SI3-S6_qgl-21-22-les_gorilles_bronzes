package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects;

import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.goals.RegattaGoal;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.ship.Sailor;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.ship.Ship;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InitGameTest {
    InitGame initGame;

    @BeforeEach
    void setUp() {
        initGame = new InitGame();
        initGame.setGoal(new RegattaGoal());
        initGame.setShip(new Ship());
        initGame.setSailors(new Sailor[]{new Sailor()});
        initGame.setShipCount(1);
    }

    @Test
    void initGameTest() {
        assertEquals(initGame.getGoal(), new RegattaGoal());
        assertEquals(initGame.getShip(), new Ship());
        assertEquals(initGame.getSailors()[0], new Sailor());
        assertEquals(1, initGame.getShipCount());
    }

    @Test
    void toStringTest() {
        assertEquals("InitGame{" + System.lineSeparator() +
                        "goal=RegattaGoal{checkpoints=null}" + System.lineSeparator() +
                        ", ship=Ship{type='null', life=0, position=null, name='null', deck=null, entities=null, shape=null}" + System.lineSeparator() +
                        ", sailors=[Sailor{id=0, x=0, y=0, name='null', isFree=false}]" + System.lineSeparator() +
                        ", shipCount=1" + System.lineSeparator() +
                        "}",
                initGame.toString());
    }
}
