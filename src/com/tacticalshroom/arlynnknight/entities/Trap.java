package com.tacticalshroom.arlynnknight.entities;

import com.tacticalshroom.arlynnknight.Game;

import java.awt.*;

public class Trap extends Entity {

    public Trap(int x, int y, int w, int h) {
        super(x, y, w, h, 0, 25, 0, 5);
    }

    public boolean trapping = false;
    int trapCoolDown = 0;

    public void tick(Game g) {
        if (g.player.getEntity().intersects(getEntity()) && !trapping && !g.player.trapped) {
            trapCoolDown = 60;
            g.player.trap(x+w/2, y+h/2);
            trapping = true;
        }
        this.vx = 0;
        this.vy = 0;

        if (trapping)   {
            trapCoolDown--;
            if (trapCoolDown <= 0)  {
                g.player.trapped = false;
                g.player.hit(dmg);
                this.hit(30);
                trapCoolDown = 0;
                trapping = false;
                g.hurtSound.play();
            }
        }
    }

    public void attack(Rectangle hit) {

    }
}
