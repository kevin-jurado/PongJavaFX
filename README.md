# PONG - By Kevin Jurado

# Introduction
This is a reacreation of the classic Atari game with Java using JavaFX. 

# Project elements
## Classes
This reacreation utilizes a base GameScene class which is extended by the various scenes in the game,
e.g. StartScreen, Game, and PauseScreen. 
These GameScene classes are controled by one central orchestrator class. 
Within the Game class the ball and paddle are abstracted into their own classes.

## Exception Handling
The user input of this game is very simple and only consists of a few buttons.
We correct user input to be within the bounds of the game screen. No exceptions
will ever be thrown. 

## GUI
The core of the user interface is the controlling of the ball and paddles through the 
JavaFX GraphicsContext. 
For the menus I used simple JavaFX UI elements within GridPanes

## Files
I was intending on adding a leaderboard, but I was not able to include it in time. 
If I had more time, this would be the first thing I would add, mechanics-wise.

# Challenges / Advanced Topics
I had issues changing the state from within the individual game scenes. This lead me to learn about 
dependency injection. This is how I was able to access the orchestrator from individual game scenes. 

Drawing the objects in the game required more flexible drawing functions which focred me to 
learn how to use the graphical context. 

Understanding vectors and their core mathematical principles. 
I used Point2D, which is a vector, to store and manuipulate the positions of game objects. 

# Changes to design
Removed GameObject class for simplicities sake. 
Added two more states for winning and losing