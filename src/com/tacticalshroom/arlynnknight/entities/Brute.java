package com.tacticalshroom.arlynnknight.entities;

import com.tacticalshroom.arlynnknight.Game;

import java.awt.*;
import java.util.Random;

public class Brute extends Entity {

    public Brute(int x, int y, int health) {
        super(x, y, 75, 75, 2, health, 20, 2);
    }

    int attackCounter = 0;
    public void tick(Game g) {
        int xDir = x >= g.player.x+g.player.w+range ? -1 : x <= g.player.x+g.player.w+range && x >= g.player.x-w-range ? 0 : 1;
        int yDir = y >= g.player.y+g.player.h+range ? -1 : y <= g.player.y+g.player.h+range && y >= g.player.y-h-range ? 0 : 1;

        if (this.knockBackTimer <= 0)    {
            this.vx = speed*xDir;
            this.vy = speed*yDir;
        }

//                System.out.println(xDir + " " + yDir);

        if (xDir == 0 && yDir == 0) {
            attackCounter++;
        }

        if (attackCounter >= 30)    {
            attackCounter = 0;
            g.player.hit(dmg);
            g.hurtSound.play();
        }
    }

    public boolean loot()   {
        return new Random().nextInt(4) == 0;
    }

    @Override
    public void attack(Rectangle hit) {

    }
}
