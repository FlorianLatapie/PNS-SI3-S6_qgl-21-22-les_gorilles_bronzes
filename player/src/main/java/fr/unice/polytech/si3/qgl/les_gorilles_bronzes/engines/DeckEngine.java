package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.engines;

import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.Actions.Action;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.InitGame;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.ship.Sailor;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.ship.Ship;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.ship.entity.Entity;
import fr.unice.polytech.si3.qgl.les_gorilles_bronzes.objects.ship.entity.Rame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class DeckEngine {
    private Ship ship;
    private Sailor[] sailors;
    private List<Rame> oars;
    private int i;

    public enum Direction {LEFT, RIGHT}

    public DeckEngine(InitGame initGame) {
        this.ship = initGame.getShip();
        this.sailors = initGame.getSailors();
        this.oars = this.getOars();
        beforeEachRound();
    }

    public void beforeEachRound() {
        Arrays.stream(sailors).collect(Collectors.toList()).forEach(s -> s.setFree(true));
    }

    public Ship getShip() {
        return ship;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }

    public List<Rame> getOars() {
        List<Rame> res = new ArrayList<>();
        for (int j = 0; j < ship.getEntities().length; j++) {
            if (ship.getEntities()[j] instanceof Rame)
                res.add((Rame) ship.getEntities()[j]);
        }
        return res;
    }


    public List<Action> moveSailorsToOars(int nbSailorsToMove, Direction direction) {
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

    public List<Sailor> sailorsWhoDontHaveAnOar() {
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
    }

    public Rame getTheClosestOarAvailable(Sailor sailor, List<Rame> oarsAvailable) {
        int sailorX = sailor.getX();
        int sailorY = sailor.getY();
        Rame closestOar = new Rame();
        int minDistance = 0, distance;

        for (Rame oar : oarsAvailable) {
            distance = (((sailorX - oar.getX()) ^ 2 + (sailorY - oar.getY()) ^ 2) ^ (-1 / 2));
            if (oarsAvailable.indexOf(oar) == 0 || distance < minDistance) {
                closestOar = oar;
                minDistance = distance;
            }
        }
        return closestOar;
    }

    public List<Rame> getOars(Direction direction) {
        List<Rame> res = new ArrayList<>();
        for (Rame r : oars) {
            if (direction.equals(Direction.LEFT))
                if (r.getY() == 0) res.add(r);
            if (direction.equals(Direction.RIGHT))
                if (r.getY() == ship.getDeck().getWidth() - 1) res.add(r);
        }
        return res;
    }

    public List<Rame> getLeftOars() {
        List<Rame> leftOars = new ArrayList<>();
        for (Rame r : oars) {
            if (r.getY() == 0) leftOars.add(r);
        }
        return leftOars;
    }

    public List<Rame> getRightOars() {
        List<Rame> rightOars = new ArrayList<>();
        for (Rame r : oars) {
            if (r.getY() == ship.getDeck().getWidth() - 1) rightOars.add(r);
        }
        return rightOars;
    }

    public Optional<Sailor> getSailorByEntity(Entity entity){
        return Arrays.stream(sailors).filter(sailor -> sailor.getX() == entity.getX() && sailor.getY() == entity.getY()).findFirst();
    }
}
