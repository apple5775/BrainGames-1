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
