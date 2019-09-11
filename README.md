game
====

This project implements the game of Breakout.

Name: Alex Qiao

### Timeline

Start Date: 8/31/2019

Finish Date: 9/8/2019

Hours Spent: 40

### Resources Used
Built based off of lab_bounce starter code.
Credits to tutorialspoint.com for introducing JavaFX and refreshing class extensions to me.
Credits to Stack Overflow users Dhanushka Gayashan and bluedroid for refreshing me on the use 
of a scanner to read text files. Also credits to Sevle and James_D on Stacks Overflow for explaining
how to resize images in JavaFX.

### Running the Program

Main class: Breakout

Data files needed: All data files used are located in the resources folder of this repository.

Key/Mouse inputs:
A or LEFT ARROW keys to move paddle to the left.
D or RIGHT ARROW keys to move paddle to the right.
W or UP ARROW to move paddle upwards (unlocks after level 1).
S or DOWN ARROW to move paddle downwards (unlocks after level 1).
SPACE key to release the ball from the paddle.
ESC to quit out of the game.

Cheat keys:
R key to reset paddle and ball positions, as well as paddle size if it was originally the
length of the bottom side.
L key to add additional lives.
1-3 DIGIT keys to instantly transition to the corresponding level.
E key will fit paddle to the length of the bottom side, pressing again will revert the effect. 

Known Bugs: 
Stage 3 bottom most row the two furthest pink bricks on either side get hit for all 3 lives
in a single collision and disappear.

When the ball hits the paddle perfectly on the edge, interesting things can sometimes happen, such
as rolling across the paddle before being registered as blocked by the paddle.

Sometimes when catching a power-up and the ball falls off the screen relatively simultaneously,
the power-up effect will be consumed as normal but the image will still remain (visual effect
only, no game play effects).

Extra credit:
Lava squares spawn and the number depends on the current level. Avoiding hitting these 
floating squares with your paddle at all cost! Failure to do so will result in instant loss; 
after all, your paddle has melted! These squares can bounce off all sides of the game screen, 
and may collide with bricks, but all bricks are tough enough to withstand its heat and will 
not weaken when hit!

### Notes
Total of 4 cheat keys (additional life, extend paddle to screen width, reset ball, skip to stage).
Total of 8 power-ups, some beneficial, some harmful. Total of 4 paddle abilities (increase/decrease size,
vertical movement after level 1, left half reflects ball velocity and right half normal bounce, and finally
warping from side to side). My main "extra" thing is that there are lava squares spawning from the bottom right
spawner, which spawns a different amount depending on the level. Immediate loss if the player's paddle
hits any of the lava squares, and lava squares can bounce off of all four sides and bricks while unaffecting brick
life. Other extra things include larger, slower falling power-ups when harmful and smaller, faster falling power-ups
when beneficial.

### Impressions
I felt pretty good about my work done on this project. I spent a lot of time and effort planning out 
classes and methods to use. I spent even more time on debugging several minute issues that affected the game and
although not every little bug was squashed, I think a good number of them were fixed. I am also happy with the
different power-ups I thought of and implemented, as well as following through on my plan to include the extra 
lava square implementation. Although not every detail was covered in the plan, I am pleased with how closely
I stuck with the original plan. The plan was created without knowing the number of power-ups, cheat keys, brick types
were needed let alone which specific ones (listed under COMPLETE and NOT under PLAN), I was able to implement
most if not all of the ones along with the additional ones later discovered listed under COMPLETE (some were cross overs
or were redundant in some fashion, and so was not implemented).
Overall, I am very pleased with how the game worked out to be.