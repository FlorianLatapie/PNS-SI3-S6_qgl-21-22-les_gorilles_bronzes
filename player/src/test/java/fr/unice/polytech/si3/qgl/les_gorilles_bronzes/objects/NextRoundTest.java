package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects;

import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.goals.RegattaGoal;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.obstacles.Wind;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.obstacles.visible_entities.Reef;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.obstacles.visible_entities.Stream;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.obstacles.visible_entities.VisibleEntity;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.ship.Sailor;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.ship.Ship;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.ship.entity.Entity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NextRoundTest {
    NextRound nextRound;

    @BeforeEach
    void setUp() {
        nextRound = new NextRound();
        nextRound.setShip(new Ship());
        nextRound.setWind(new Wind());
        nextRound.setVisibleEntities(new VisibleEntity[]{new Stream(), new Reef()});
    }

    @Test
    void initGameTest() {
        assertEquals(new Ship(), nextRound.getShip());
        assertEquals(new Wind(), nextRound.getWind());
        assertEquals(new VisibleEntity[]{new Stream(), new Reef()}[0], nextRound.getVisibleEntities()[0]);
        assertEquals(new VisibleEntity[]{new Stream(), new Reef()}[1], nextRound.getVisibleEntities()[1]);
    }

    @Test
    void toStringTest() {
        assertEquals("NextRound{ship=Ship{type='null', life=0, position=null, name='null', deck=null, entities=null, shape=null}, wind=Wind{orientation=0.0, strength=0.0}, visibleEntities=[Stream{position=null, shape=null, strength=0.0}, VisibleEntity{position=null, shape=null}]}",nextRound.toString());
    }
}
