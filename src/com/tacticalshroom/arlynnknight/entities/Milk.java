package com.tacticalshroom.arlynnknight.entities;

import com.tacticalshroom.arlynnknight.Game;

import java.awt.*;

public class Milk extends Entity {


    public Milk(int x, int y, int w, int h) {
        super(x, y, w, h, 0, 1, 0, 0);
    }

    public void tick(Game g) {
        if (g.player.getEntity().intersects(this.getEntity()))  {
            hit(10);
        }
    }

    public void attack(Rectangle hit) {

    }
}
