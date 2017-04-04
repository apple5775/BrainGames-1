## What we have to do

The game looks nice but is not playable.  I think that the top priority should be to create the matching game.
At this point, the matching game is very easy.  We need to:
- [x] Create the grid of buttons
- [ ] Gather images to be used for matching
- [ ] Randomly assign an image to each button
- [ ] Create the flipping motion
- [ ] Create a system to check if two arbitrary tiles have the same image (I was thinking of using a hash map)
- [ ] Improve the general look and feel by creating:
  - [ ] Play and pause
  - [ ] Timer
  - [ ] Score
  - [ ] Restart

When you get one of these done, just tick it off so we can tell for later.  By the way, github uses some
[crazy markup syntax](https://help.github.com/articles/basic-writing-and-formatting-syntax/) for their
README, so you might want to check it out.

## Matchsticks update

I wrote the gen stip code, and it is functional now.  I found 7 sticks to be the upper bound, and 8 leads to games
only with 0 squares, which is bad.  So, I remade the stip upper bound and it is all working.  Yay!  Now, we have to
write the win/lose stuff.

#### Kenny's update 4/1/17
- AndroidManifest.xml: 
Added two activities GameOver and MatchstickGame for send and receive intent to work. MatchstickGame and GameOver can be started as new activities. 
- Board.java, Button.java, Timer.java:
Changed the Board and Button and Timer constructor to use Activity instead of MainActivity as its parameter. 
- MatchstickGame.java:
It extends Activity class now as an individucal activity and can be started by passing intent. 
The restart button now calls a "restart()" procedure and does not construct a new MatchstickGame object.
The registerClick method is changed to use intent to start the GameOver activity.
- MainActivity.java:
It uses two ImageButtons to select which game to play and uses intent to start the games as Activities. 
- GameOver.java:
It is an Activity that gets called by the games to declare the status of the game.
It also has a button to start the MainActivity again.
- activity_main.xml:
Game descripitions and two buttons to start games.
- gameover.xml
Says if you win or lose at end of game with play again. Play again goes to main activity screen.

#### Theo's update 4/3/17
Took everything that you added with the intent and simplified it a little.  It was pretty complicated for no reason, and I think you
just needed another pair of eyes on it.  Also updated the GameOver to have a button to bring you back to the exited activity using 
Class<?> and prepared to display the solution clickMap for a matchsticks game.

## Pushing

The commands are pretty easy.  Heres a table:
Command | Function
--------|---------
git init | Initializes the git repo or something.
git add <file> | Do this when you want to sync file <file> to GitHub.
git remote add <remote_name> <repo_URL> | You only have to do this once, and <repo_URL> should be https://github.com/tgrossb/BrainGames.git
git commit -m "message goes here" | Sets you up for a commit and adds the message.
git push -u <remote_name> master | Pushes the added file to github.

You should only have to do `git init` and `git remote add <remote_name> <repo_URL>` once.  However, when you add something
it should go add, commit, then push.

