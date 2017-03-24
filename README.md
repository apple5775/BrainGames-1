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

## Some thoughts on Matchsticks

I have finished all of the properties of the game except the generating of stipulations (remove n matchsticks to create m squares)
and determining the number of squares created.  So, for the generating of the stipulations, I think the best way to go is to generate
two random numbers and use a brute force method to check if it is possible.  If a pair (n, m) is generated, and both can be restricted, 
we just need to check n * 2^24 possibilities maximum.  Now, on to the problem of determining the number of squares in an arbitrary grid.
First, we can remove extraneous matches by removing all matches that are only touching one other match.  Then, the number of squares in
a rectangle of size n x m can be represented as (1/6) * n * (n+1) * (3m-n+1) assuming m >= n.  However, I don't know what we can do from 
there, so we should meet up to figure this out with some big old white boards.

#### Edit

We are almost done with Matchsticks.  All we have left to do is to generate stipulations.
