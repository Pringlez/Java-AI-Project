# Java-AI-Project
## John Walsh

Project Details
---------------
0. The project utilizes different search algorithms like A-star, DFS, Brute Force and the like to find a specific goal node randomly placed in a 2d maze.
0. The game also has different pickup items like weapons, health and armor to help the player navigate safely to the goal node. These items are randomly placed around the maze.
0. Enemies are also navigating the maze to kill the player, these enemies will implement different search algorithms to detect and defeat the player.

To Run the Game
---------------
Run the following command to start the maze game.

```
java –cp ./mazegame.jar ie.gmit.sw.Runner
```

How to Play
-----------
How to control the game character:

* Move Left: A
* Move Right: D
* Move Up: W
* Move Down: S
* Zoom Out Map: M
* Use Special Pick Up: K

The special pick up item allows the player to (hopefully) display a suitable path for the player to follow. I say hopefully because in the background it randomly chooses a search algorithm to find the goal node in the maze. Not every search algorithm is optimized and complete so beware of wild goose chases and dead ends!


Game Features & Items
---------------------
There is a number of different items placed throughout the maze, weapons, health, armor and a direction helper item. The direction helper item uses the A* search algorithm to compute the number of steps from the player position to the goal. Below is a list of items and their effects.

#### Health
Adds 50 hp the player's stats

![alt text](/res/health.png "Health Item")

#### Armor
Adds 50 armor to the player's stats

![alt text](/res/armor.png "Armor Item")

#### Sword
A weak weapon with 45 battle strength against enemies

![alt text](/res/sword.png "Sword Item")

#### Shotgun
A strong weapon with 65 battle strength against enemies

![alt text](/res/shotgun.png "Shotgun Item")

#### AK-47
A powerful weapon with 85 battle strength against enemies

![alt text](/res/ak47.png "AK-47 Item")

#### Pipe Bomb
An extremely powerful weapon against enemies with 100 battle strength

![alt text](/res/pipe_bomb.png "Pipe Bomb Item")

#### Helper
The helper item will display the path to the maze's exit, note that enemies can destroy the path has they walk over them!

![alt text](/res/help.png "Helper Item")

### Game Features
Below you'll find details about the different features implemented into the game.

#### Steps 
The game features a special step counter that display how many steps the player will need to take until the goal is reached, this re-computes every time the player moves position. The A* search algorithm is used to compute this variable.

#### Fuzzy Logic
The game also features a fuzzy logic battle system that determines the outcome of fights between player and enemies in the maze. Inside of the included 'fcl' folder you'll find the rules governing the outcome of battles between two entities.
