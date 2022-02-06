package fr.unice.polytech.si3.qgl.les_gorilles_bronzes.engines;

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
    private  Sailor[] sailors;
    private List<Rame> oars;
    private int i;

    public DeckEngine(Ship ship, Sailor[] sailors){
        this.ship = ship;
        this.sailors = sailors;
        this.oars = getOars();
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

    public List<Sailor> sailorsWhoDontHaveAnOar(){

        List<Sailor> sailorsWhoDontHaveAnOar = new ArrayList<>(Arrays.stream(sailors).collect(Collectors.toList()));
        for(i = 0; i<sailors.length; i++){
            oars.forEach(o->{
                   if((sailors[i].getX()==o.getX() || sailors[i].getY()==o.getY()) && sailorsWhoDontHaveAnOar.contains(sailors[i])){
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
                if((sailors[i].getX()==o.getX() || sailors[i].getY()==o.getY())&& oarsAvailable.contains(o)){
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

    public List<Sailor> getLeftSailorsWithAnOar(){
        List<Sailor> leftSailors = new ArrayList<>();
        for(Sailor s : sailorsWhoHaveAnOar()){
            if(s.getY()==0) leftSailors.add(s);
        }
        return leftSailors;
    }

    public List<Sailor> getRightSailorsWithAnOar(){
        List<Sailor> rightSailors = new ArrayList<>();
        for(Sailor s : sailorsWhoHaveAnOar()){
            if(s.getY()==ship.getDeck().getWidth()) rightSailors.add(s);
        }
        return rightSailors;
    }
}
