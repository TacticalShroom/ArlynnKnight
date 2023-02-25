package com.tacticalshroom.arlynnknight.entities;

import com.tacticalshroom.arlynnknight.Game;

import java.awt.*;
import java.util.Random;

public abstract class Entity {

    public boolean trapped = false;

    int x, y, w, h;
    int knockBackTimer = 0;
    double vx, vy;
    int health, range, dmg, baseSpeed;
    public double speed;
    boolean dead;
    Random r = new Random();

    //hy is relative to the y of the player
    public Entity(int x, int y, int w, int h, int speed, int health, int range, int dmg) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.vx = 0;
        this.vy = 0;
        this.speed = speed;
        this.baseSpeed = speed;
        this.health = health;
        this.range = range;
        this.dmg = dmg;
        this.dead = false;
    }

    public void update(Game g)    {
        if (!trapped)   {
            this.x += vx;
            this.y += vy;
        }
        else    {
            this.vx = 0;
            this.vy = 0;
        }

        if (knockBackTimer>0)   {
            knockBackTimer--;
        }

        if (this.x <= g.displayWidth/5)  {
            x = g.displayWidth/5+1;
        }
        if (this.x+this.w >= 4 * g.displayWidth/5)   {
            x = 4 * g.displayWidth/5 - 1 - getEntity().width;
        }
        if (this.y <= g.displayHeight/5)  {
            y = g.displayHeight/5 + 1;
        }
        if (this.y+this.h >= 4 * g.displayHeight/5)  {
            y = 4 * g.displayHeight/5 - 1 - getEntity().height;
        }
        tick(g);
    }

    public abstract void tick(Game g);
    public abstract void attack(Rectangle hit);

    public void hit(double dmg)  {
        if (health - dmg > 0)   {
            this.health -= dmg;
        }
        else    {
            this.health = 0;
            dead = true;
        }
    }

    public void die(Game g)   {
        g.entities.remove(this);
    }

    public Rectangle getEntity() {
        return new Rectangle(x, y, w, h);
    }

    public boolean hits(int x, int y, int w, int h) {
        return getEntity().intersects(x, y, w, h);
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void addMove(double vx, double vy)  {
        this.vx += vx;
        this.vy += vy;
    }

    public void setMove(double vx, double vy) {
        this.vx = vx;
        this.vy = vy;
    }

    public void setTempMove(double vx, double vy, int frames)   {
        knockBackTimer = frames;
        this.vx = vx;
        this.vy = vy;
    }

    public int getHealth() {
        return health;
    }

    public int getRange() {
        return range;
    }

    public boolean isDead() {
        return dead;
    }

    public void setHealth(int hp) {
        this.health = hp;
    }

    public double getVx() {
        return vx;
    }

    public double getVy() {
        return vy;
    }

    public int getBaseSpeed() {
        return baseSpeed;
    }
}
