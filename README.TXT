# PONG - By Kevin Jurado

# Introduction
This is a reacreation of the classic Atari game with Java using JavaFX. 
This reacreation utilizes a base GameScene class which is extended by the various scenes in the game,
e.g. StartScreen, Game, and PauseScreen. 
These GameScene classes are controled by one central orchestrator class. 

The user input of this game is very simple and only consists of a few buttons.
Therefore there is no need to manually validate user input. No exceptions
will ever be thrown. 

The core of the user interface is the controlling of the ball and paddles through the 
JavaFX GraphicsContext. 

Drawing the objects in the game required more flexible drawing functions which focred me to 
learn how to use the graphical context. 

I had issues changing the state from within the individual game scenes. This lead me to learn about 
dependency injection. This is how I was able to access the orchestrator from individual game scenes. 


## sub header