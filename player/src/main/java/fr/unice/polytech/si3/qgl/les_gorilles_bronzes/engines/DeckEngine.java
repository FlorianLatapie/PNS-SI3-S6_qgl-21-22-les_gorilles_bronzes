package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.engines;

import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.InitGame;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.actions.Action;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.ship.Sailor;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.ship.Ship;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.ship.entity.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class DeckEngine {
    private Ship ship;
    private Entity[] entities;
    private Sailor[] sailors;
    private List<Rame> oars;
    private int availableSailors;
    private int sailorsPlacedOnOars = -1;


    public enum Direction {LEFT, RIGHT}

    public DeckEngine(InitGame initGame) {
        this.ship = initGame.getShip();
        this.sailors = initGame.getSailors();
        this.entities = ship.getEntities();

        this.availableSailors = sailors.length;

        this.oars = this.getOars();
        this.beforeEachRound();
    }

    public void beforeEachRound() {
        Arrays.stream(sailors).collect(Collectors.toList()).forEach(s -> s.setFree(true));
        Arrays.stream(entities).collect(Collectors.toList()).forEach(e -> e.setFree(true));
        availableSailors = sailors.length;
    }

    public List<Action> placeSailors() {
        List<Action> actions = new ArrayList<>();

        actions.addAll(placeSailors(new Gouvernail()));
        actions.addAll(placeSailors(new Voile()));
        actions.addAll(placeSailors(new Vigie()));

        actions.addAll(placeSailorsOnOars());

        return actions;
    }

    public List<Action> placeSailors(Entity entityToMatch) {
        return placeSailors(entities, entityToMatch);
    }
    public List<Action> placeSailors(Entity[] search, Entity entityToMatch) {
        List<Action> actions = new ArrayList<>();

        Arrays
                .stream(search)
                .filter(entity -> entity.getClass().equals(entityToMatch.getClass()))
                .forEach(entity -> {
                    var sailor = nearestSailorToThisEntity(entity).get();
                    if (entity.isFree() && sailor.isFree()){
                        actions.add(sailor.moveSailor(entity));
                        sailor.setFree(false);
                        entity.setFree(false);
                        availableSailors--;
                    }
                });
        ;

        return actions;
    }

    public Optional<Sailor> nearestSailorToThisEntity(Entity entity) {
        Optional<Sailor> nearestSailor = Optional.empty();
        int minDistance = Integer.MAX_VALUE;
        for (Sailor sailor : sailors) {
            int distance = Math.abs(sailor.getX() - entity.getX()) + Math.abs(sailor.getY() - entity.getY());
            if (distance < minDistance) {
                minDistance = distance;
                nearestSailor = Optional.of(sailor);
            }
        }
        return nearestSailor;
    }

    public List<Rame> getOars() {
        List<Rame> res = new ArrayList<>();
        for (int j = 0; j < ship.getEntities().length; j++) {
            if (ship.getEntities()[j] instanceof Rame)
                res.add((Rame) ship.getEntities()[j]);
        }
        return res;
    }

    public int getTotalNbSailorsOnOars() {
        return sailorsPlacedOnOars;
    }
    public List<Action> placeSailorsOnOars() {
        List<Action> actions = new ArrayList<>();

        int nbSailorsToMove = availableSailors / 2;
        this.sailorsPlacedOnOars = nbSailorsToMove*2;

        actions.addAll(placeSailorsOnOars(nbSailorsToMove, Direction.LEFT));
        actions.addAll(placeSailorsOnOars(nbSailorsToMove, Direction.RIGHT));
        return actions;
    }

    public List<Action> placeSailorsOnOars(int nbSailorsToMove, Direction direction) {
        List<Action> actions = new ArrayList<>();
        int j = 0;
        for (Sailor s : sailors) {
            if (s.isFree() && j < nbSailorsToMove) {
                if (direction.equals(Direction.RIGHT)) actions.add(s.moveSailor(getOars(Direction.RIGHT).get(j)));
                if (direction.equals(Direction.LEFT)) actions.add(s.moveSailor(getOars(Direction.LEFT).get(j)));
                s.setFree(false);
                j++;
            }
        }
        return actions;
    }

    /*public List<Sailor> sailorsWhoDontHaveAnOar() {
        List<Sailor> sailorsWhoDontHaveAnOar = new ArrayList<>(Arrays.stream(sailors).collect(Collectors.toList()));
        for (i = 0; i < sailors.length; i++) {
            oars.forEach(o -> {
                if ((sailors[i].getX() == o.getX() && sailors[i].getY() == o.getY()) && sailorsWhoDontHaveAnOar.contains(sailors[i])) {
                    sailorsWhoDontHaveAnOar.remove(sailors[i]);
                }
            });
        }
        return sailorsWhoDontHaveAnOar;
    }

    public List<Sailor> sailorsWhoHaveAnOar() {
        List<Sailor> sailorsWhoHaveAnOar = new ArrayList<>(Arrays.stream(sailors).collect(Collectors.toList()));
        sailorsWhoDontHaveAnOar().forEach(s -> {
            if (sailorsWhoHaveAnOar.contains(s)) sailorsWhoHaveAnOar.remove(s);
        });
        return sailorsWhoHaveAnOar;
    }

    public List<Rame> oarsAvailable() {
        List<Rame> oarsAvailable = new ArrayList<>(oars);
        for (i = 0; i < sailors.length; i++) {
            oars.forEach(o -> {
                if ((sailors[i].getX() == o.getX() && sailors[i].getY() == o.getY()) && oarsAvailable.contains(o)) {
                    oarsAvailable.remove(o);
                }
            });
        }
        return oarsAvailable;
    }*/

    public List<Rame> getOars(Direction direction) {
        List<Rame> res = new ArrayList<>();
        for (Rame r : oars) {
            if (direction.equals(Direction.LEFT) && r.getY() == 0) {
                res.add(r);
            }
            if (direction.equals(Direction.RIGHT) && r.getY() == ship.getDeck().getWidth() - 1) {
                res.add(r);
            }
        }
        return res;
    }

    public Optional<Sailor> getSailorByEntity(Entity entity) {
        return Arrays.stream(sailors).filter(sailor -> sailor.getX() == entity.getX() && sailor.getY() == entity.getY()).findFirst();
    }
}
