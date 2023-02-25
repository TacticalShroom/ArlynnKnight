package com.tacticalshroom.arlynnknight.entities;

import com.tacticalshroom.arlynnknight.Game;
import processing.core.PImage;

import java.awt.*;

public class Mouse extends Entity {

    public float lastRotation = 0;
    public PImage prevMouse;
    public PImage currentMouse;

    public Mouse(int x, int y, int w, int h, int health, PImage mouseMiddle) {
        super(x, y, w, h, 3, health, 0, 1);

        this.prevMouse = mouseMiddle;
        this.currentMouse = mouseMiddle;
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

    public boolean loot()  {
        if (r.nextInt(6) == 0)  {
            return true;
        }
        return false;
    }

    public void attack(Rectangle hit) {

    }
}
