Reykjavik University SC-T-511-TGRA Computer Graphics
Programming assignment 2 - 2D game with collision
Julius Elfving & Tomás Voznicka

This document is a readme for programming assigment 2.

----------
Basic idea
----------

Our game is a combination of cannon ball game and mini golf. The player should get the ball to the target using the right speed (not too fast). The player can change the launching angle and velocity of the ball. The player must also add obstacles (rectangles and lines) to the game area. The player must hit the obstacles with the ball to slow it down (every bounce reduces speed of the ball).

-------------
Game controls
-------------

Left key
	- Rotate the cannon counterclockwise
	
Right key
	- Rotate the cannon clockwise
	
Up key
	- Increase launch speed
	
Down key
	- Decrease launch speed
	
Space bar
	- Launch the ball
	
Mouse left (hold)
	- Add a line obstacle
	
Mouse right (hold)
	- Add a rectangle obstacle
	
Q
	- Reset level

-------------------	
Collision detection
-------------------

The ball can bounce off the borders of the game area and obstacles added by the player. The collisions are treated differently for borders and for obstacles.

Collision with borders:
Collision with borders of the game area are treated in a very simple way. If the ball has crossed the border, the velocity purpendicular to the border is flipped. The collision doesn't slow down the ball.

Collision with obstacles:
Collision with obstacles means collision with rectangles and lines added by the player. Bouncing from the lines is implemented using vector math that has been introduced on lectures. "Thit" and "Phit" are calculated for every line. When a matching line is found, a bounce happens. If there is time left for that frame, bounces are checked again. This means that multiple bounces can happen during one frame. 
To be accurate, bounce should happen from the line that has the smallest "Thit". Now the first one to match is chosen, meaning that the order of adding obstacles do matter. This should however be a small issue so it hasn't been fixed.

Collission with rectangles is easy when the collision for lines is already implemented because a rectangle is treated as a four lines.

Both collisions with lines and rectangles decrease the speed of the ball making it possible to hit the target.

Collission with the target:
If the ball is moving slowly enough and hits the target, the player wins. If the speed isn't slow enough, the ball just passes through the target.

------------------
Collision response
------------------

The more complicated way of bouncing is used. The math introduced in the assignment pdf is used for calculating the bounce angles.

-------------------------------
About drawing different objects
-------------------------------

Circles and rectangles are drawn using the classes CircleGraphic and RectangleGraphic, respectively. For lines there is a class Line which is both a model for the lines and also is responsible for actually drawing the lines.

-----------------------
Assignment requirements
-----------------------

An object controlled by the player
	- Cannon
	
An object the moves on its own
	- Cannonball
	
Objects that collide with other objects
	- Cannonball collides with borders, rectangles, lines and the target.


------------------------
Bouncing cannonball game
------------------------

- In this game, the obstacles can be added before firing the cannon and also when the cannonball has already been launched.
- Both rectangles and lines are implemented, making it "most complicated obstacle field".


------
Extras
------

- Multiple collisions within a frame
	The collision with lines (that is all the obstacles) is checked in function hitDetection(). If a collision happens, the function is called recursively using deltaTime = deltaTime - Thit. This allows multiple collisions during a single frame.
