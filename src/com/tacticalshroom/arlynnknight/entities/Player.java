package com.tacticalshroom.arlynnknight.entities;

import com.tacticalshroom.arlynnknight.Game;

import java.awt.*;

public class Player extends Entity {

    Game game;

    public double dex, str;
    public int xp;
    public int dashCoolDown;
    public int maxHP;
    public int attackCoolDown;


    public Player(int x, int y, int width, int height) {
        super(x, y, width, height, 3, 20, 80, 4);

        dex = 1;
        str = 1;
        xp = 0;
        maxHP = health;
        attackCoolDown = 0;
        dashCoolDown = 0;
    }


    public void tick(Game g) {
        if (attackCoolDown > 0) {
            if (attackCoolDown == 45)  {
                g.attackSound.play();
            }
            attackCoolDown--;
        }
        if (dashCoolDown > 0)   {
            dashCoolDown--;
        }
        this.game = g;
        this.speed = dex*baseSpeed;
    }

    public void trap(int x, int y)  {
        this.x = x-w/2;
        this.y = y-h/2;
        this.trapped = true;
    }

    public void unTrap()    {
        this.trapped = false;
    }


    public void attack(Rectangle hit) {
        if (attackCoolDown <= 0)    {
            attackCoolDown = 45;
            for (Entity entity : game.entities) {
                if (entity.getEntity().intersects(hit) || entity.getEntity().contains(hit)) {
                    game.damage(entity, dmg*str);
                    int xDir = entity.x == this.x ? 0 : (entity.getX() - this.x) / Math.abs(entity.getX() - this.x);
                    int yDir = entity.y == this.y ? 0 : (entity.getY() - this.y) / Math.abs(entity.getY() - this.y);
                    entity.setTempMove(xDir*5, yDir*5, 8);
                }
            }
            for (Rectangle rect : game.coots.getHitBoxes()) {
                if (rect.intersects(hit) || rect.contains(hit)) {
                    game.damage(game.coots, dmg*str);
                    game.coots.hurt = true;
                }
            }
        }
    }


}
