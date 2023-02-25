package com.tacticalshroom.arlynnknight;

import processing.core.PImage;

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;

public class Coots {

    ArrayList<Rectangle> hitBoxes;
    int maxHealth;
    int health;
    int dmgPaw;
    int dmgLaser;
    int laserCoolDown;
    int laserX;
    int laserY;
    PImage paw;

    int attackX = -1, attackY = -1;
    Rectangle attackRect;

    public boolean hurt = false;
    public int deathCounter = 210;


    public Coots(int health, int dmgPaw, int dmgLaser, PImage paw)  {
        this.health = health;
        this.maxHealth = health;
        this.dmgPaw = dmgPaw;
        this.dmgLaser = dmgLaser;
        this.hitBoxes = new ArrayList<>();
        this.paw = paw;
        this.laserCoolDown = 0;
        this.laserX = 0;
        this.laserY = 0;
    }

    int counter = 60;
    int hitCounter = 5;
    boolean hitting = false;
    public void tick(Game g, PImage paw)  {
        if (hurt)   {
            hitCounter--;
            if (hitCounter <= 0)    {
                hurt = false;
                hitCounter = 5;
            }
        }
        if (attackX != -1 && attackY != -1) {
            counter--;

            if (attackX > g.displayWidth/2) {
                g.attackingRight = true;
            }
            else    {
                g.attackingLeft = true;
            }

            if (counter > 1)    {
                g.noFill();
                g.stroke(255, 0, 0);
                g.strokeWeight(15);
                g.circle(attackX, attackY, counter);
            }
            else if (counter >= -30)   {
                g.image(paw, attackX-paw.width/2, attackY-paw.height/2);
                if (counter == 1)   {
                    g.explosionSound.play();
                }
                Rectangle hit = new Rectangle(attackX-paw.width/2, attackY-paw.height/2, paw.width, paw.height);
                if ((hit.intersects(g.player.getEntity()) || hit.contains(g.player.getEntity())) && !hitting) {
                    g.player.hit(dmgPaw);


                    hitting = true;
                }
            }
            else    {
                attackX = -1;
                attackY = -1;
                hitting = false;
                counter = 60;
                hitBoxes.remove(attackRect);
                g.attackingRight = false;
                g.attackingLeft = false;
            }
        }

        if (laserCoolDown > 0)  {
            if (laserCoolDown == 75)   {
                laserX = g.player.getX();
                laserY = g.player.getY() + g.player.getEntity().width;
            }
            laserCoolDown--;
            g.stroke(255, 0, 0);
            g.strokeWeight(5);
            if (laserCoolDown <= 5 && laserCoolDown >= 1) {
                g.strokeWeight(20);
                if (laserCoolDown == 5) {
                    g.laserSound.play();

                    Rectangle r = g.player.getEntity();
                    Line2D l1 = new Line2D.Float(g.displayWidth/2 - g.cootsMad.width/5, g.displayHeight/5, laserX, laserY);
                    Line2D l2 = new Line2D.Float(g.displayWidth/2 + g.cootsMad.width/5, g.displayHeight/5, laserX, laserY);

                    if (l1.intersects(r) || l2.intersects(r))   {
                        g.player.hit(this.dmgLaser);
                    }
                }
            }
            g.line(g.displayWidth/2 - g.cootsMad.width/5, g.displayHeight/5, laserX, laserY);
            g.line(g.displayWidth/2 + g.cootsMad.width/5, g.displayHeight/5, laserX, laserY);
        }
        else    {
            this.laserX = -1;
            this.laserY = -1;
        }



    }

    public void pawAttack(int x, int y)    {
        this.attackX = x;
        this.attackY = y;
        this.attackRect = new Rectangle(x, y, paw.width, paw.height);
        this.hitBoxes.add(attackRect);
    }

    public void hit(double dmg)   {
        this.health -= dmg;
    }

    public ArrayList<Rectangle> getHitBoxes() {
        return hitBoxes;
    }

    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void laserAttack() {
        laserCoolDown = 75;
    }
}
