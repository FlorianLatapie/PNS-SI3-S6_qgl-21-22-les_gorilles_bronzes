# ⚓ Équipe Les Gorilles bronzés ⚓
![flag](./flag.png)

## Team ID
`les_gorilles_bronzes`

## Navire
*Gorille Rapide*

## Équipage
* Florian Latapie
* Loïc Le Bris
* Marius Lesaulnier
* Thomas Paul

# Applications développées dans la partie tooling 

## Gorille simulator 
### Informations 
C'est un simulateur assez simple, qui est adapté aux specs données dans le document suivant : [UNDERSTAND_THE_REFEREE.md](https://github.com/mathiascouste/qgl-2122/blob/master/project/UNDERSTAND_THE_REFEREE.md)

Au lancement de l'application deux fenêtres sont affichées une fois que la WEEK à été choisie. :
- À gauche, une fenêtre d'affichage des marins dans le bateau.
- À droite, une fenêtre d'affichage des du bateau dans la mer, avec tous les éléments du pathfinding : les liens et le chemin choisi.

Il est possible de se déplacer à l'aide de la souris en maintenant le clic, et il est aussi possible de zommer/dézommer la vue à l'aide de la molette de la souris.

### Où le lancer (depuis IntelliJ IDEA) ?
Il se lance depuis package `fr.unice.polytech.si3.qgl.les_gorilles_bronzes.tooling` avec la classe `Application.java`.

Au lancement il faut entrer dans la console la WEEK que l'on souhaite simuler puis instantanément la course démarre et affiche les deux fenêtres.


## BumpViewer 
### Informations
Après une courte discussion avec M. Cousté nous avons décidé de créer un programme qui permet d'afficher correctement les données de fichiers .bump renvoyés par le QGL Webrunner. En effet nous avons constaté que l'échelle entre le chemin parcourru par le bateau et les récifs n'étant pas les mêmes il pouvait y avoir un doute. Afin de clarifier ce doute le BumpViewer a été créé.

Ce programme reprends une grosse partie du simulateur précédent, les controles sont donc identiques.

### Où le lancer (depuis IntelliJ IDEA) ?
Il se lance depuis package `bumpViewer` avec la classe `BumpViewerMain.java`.
Les données sont codées en dur dans le programme aux lignes suivantes 12 et 16.