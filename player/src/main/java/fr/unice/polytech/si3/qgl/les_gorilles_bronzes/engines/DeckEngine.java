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
import java.util.stream.Collectors;

public class DeckEngine {
    private Ship ship;
    private Sailor[] sailors;
    private List<Rame> oars;
    private int i;

    /*public DeckEngine(Ship ship, Sailor[] sailors){
        this.ship = ship;
        this.sailors = sailors;
        this.oars = getOars();
    }*/

    public DeckEngine(InitGame initGame){
        beforeEachRound();
        this.ship = initGame.getShip();
        this.sailors = initGame.getSailors();
        this.oars = this.getOars();
    }

    public void beforeEachRound(){
        Arrays.stream(sailors).collect(Collectors.toList()).forEach(s -> s.setFree(true));
    }

    public Ship getShip() {
        return ship;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }

    public Sailor[] getSailors() {
        return sailors;
    }

    public void setSailors(Sailor[] sailors) {
        this.sailors = sailors;
    }

    public int getNbOfSailors(){
        return sailors.length;
    }

    public List<Rame> getOars(){
        List<Rame> oars = new ArrayList<>();
        for(int i = 0; i<ship.getEntities().length; i++){
            if(ship.getEntities()[i] instanceof Rame)
                oars.add((Rame) ship.getEntities()[i]);
        }
        return oars;
    }

    /*private Entity[][] getEntityDeckPosition(List<Entity> entities){
        Entity entitiesOnDeck[][] = new Entity[entities.size()][2];
        for (Entity e : entities) {
            entitiesOnDeck[entities.indexOf(e)][0] = e.getX();
        }
        return entitiesOnDeck;
    }

    private List<DeckPosition> getOarsDeckPosition(){
        List<DeckPosition> oarsDeckPosition = new ArrayList<>();
        oars.forEach(o->{
            oarsDeckPosition.add(new DeckPosition(o.getX(),o.getY()));
        });
        return oarsDeckPosition;
    }

    private List<DeckPosition> getSailorsDeckPosition(){
        List<DeckPosition> sailorsDeckPosition = new ArrayList<>();
        for(int i = 0; i<sailors.length; i++){
            sailorsDeckPosition.add(new DeckPosition(sailors[i].getX(),sailors[i].getY()));
        }
        return sailorsDeckPosition;
    }*/

    public void assignEachSailorAnOar(){
        List<Rame> oarsAvailable = oarsAvailable();
        for(Sailor s: sailorsWhoDontHaveAnOar()){
            Rame oar = getTheClosestOarAvailable(s, oarsAvailable);
            oarsAvailable.remove(oar);
            s.moveSailor(oar);
        }
    }

    public List<Action> moveSailorsToRightOars(int nbSailorsToMove){
        List<Action> actions = new ArrayList<>();
        int j=0;
        for(Sailor s: sailors){
            if(s.isFree()){
                actions.add(s.moveSailor(getRightOars().get(j)));
                j++;
            }
        }
        return actions;
    }

    public List<Action> moveSailorsToLeftOars(int nbSailorsToMove){
        List<Action> actions = new ArrayList<>();
        int j=0;
        for(Sailor s: sailors){
            if(s.isFree()){
                actions.add(s.moveSailor(getLeftOars().get(j)));
                j++;
            }
        }
        return actions;
    }

    public List<Sailor> sailorsWhoDontHaveAnOar(){
        List<Sailor> sailorsWhoDontHaveAnOar = new ArrayList<>(Arrays.stream(sailors).collect(Collectors.toList()));
        for(i = 0; i<sailors.length; i++){
            oars.forEach(o->{
                   if((sailors[i].getX()==o.getX() && sailors[i].getY()==o.getY())&& sailorsWhoDontHaveAnOar.contains(sailors[i])){
                            sailorsWhoDontHaveAnOar.remove(sailors[i]);
                   }
            });
        }
        return sailorsWhoDontHaveAnOar;
    }

    public List<Sailor> sailorsWhoHaveAnOar(){
        List<Sailor> sailorsWhoHaveAnOar = new ArrayList<>(Arrays.stream(sailors).collect(Collectors.toList()));
        sailorsWhoDontHaveAnOar().forEach(s->{
            if(sailorsWhoHaveAnOar.contains(s)) sailorsWhoHaveAnOar.remove(s);
        });
        return sailorsWhoHaveAnOar;
    }


    /*public List<Sailor> sailorsWhoDontHave(List<Entity> entities){
        List<Sailor> sailorsWhoDontHave = new ArrayList<>();
        for(i = 0; i<sailors.length; i++){
            entities.forEach(e->{
                if((sailors[i].getX()!=e.getX() || sailors[i].getY()!=e.getY())&& !sailorsWhoDontHave.contains(sailors[i])){
                    sailorsWhoDontHave.add(sailors[i]);
                }
            });
        }
        return sailorsWhoDontHave;
    }*/


    public List<Rame> oarsAvailable(){
        List<Rame> oarsAvailable = new ArrayList<>(oars);
        for(i = 0; i<sailors.length; i++){
            oars.forEach(o->{
                if((sailors[i].getX()==o.getX() && sailors[i].getY()==o.getY())&& oarsAvailable.contains(o)){
                    oarsAvailable.remove(o);
                }
            });
        }
        return oarsAvailable;
    }

    public Rame getTheClosestOarAvailable(Sailor sailor, List<Rame> oarsAvailable){
        int sailorX = sailor.getX();
        int sailorY = sailor.getY();
        Rame closestOar = new Rame();
        int minDistance = 0, distance;

        for(Rame oar: oarsAvailable){
            distance = (((sailorX-oar.getX())^2+(sailorY-oar.getY())^2)^(-1/2));
            if(oarsAvailable.indexOf(oar)==0 || distance<minDistance) {
                closestOar =oar;
                minDistance = distance;
            }
        }
        return closestOar;
    }

    public List<Rame> getLeftOars(){
        List<Rame> leftOars = new ArrayList<>();
        for(Rame r : oars){
            if(r.getY()==0) leftOars.add(r);
        }
        return leftOars;
    }

    public List<Rame> getRightOars(){
        List<Rame> rightOars = new ArrayList<>();
        for(Rame r : oars){
            if(r.getY()==ship.getDeck().getWidth()) rightOars.add(r);
        }
        return rightOars;
    }

    public void printSailorsLocations (){
        Arrays.stream(sailors).collect(Collectors.toList())
                .forEach(s -> System.out.println(s.getId()+" is on " + ship.getEntityNameWithPosition(s.getX(),s.getY())));
    }
}
