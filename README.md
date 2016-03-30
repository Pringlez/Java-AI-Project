# Java-AI-Project
## John Walsh

Project Details
---------------
0. The project utilizes different search algorithms like A-star, DFS, Brute Force and the like to find a specific goal node randomly placed in a 2d maze.
0. The game also has different pickup items like weapons, health and armor to navigate safely to the goal node. These items are randomly placed around the maze.
0. Enemies are also navigating the maze to kill the player, these enemies will implement different search algorithms to detect and defeat the player.

To Run the Game
---------------
Run the following command to start the maze game.

```
java –cp ./mazegame.jar ie.gmit.sw.Runner
```

How to Play
-----------
How to control the game character

* Move Left: A
* Move Right: D
* Move Up: W
* Move Down: S
* Zoom Out Map: M
* Use Special Pick Up: K

The special pick up item allows the player to (hopefully) find a suitable path for the player to follow. I say hopefully because in the background it randomly chooses a search algorithm to find the goal node in the maze. Not every search algorithm is optimized and complete so beware of wild goose chases and dead ends!