package com.tacticalshroom.arlynnknight;

import com.tacticalshroom.arlynnknight.entities.*;
import processing.core.PApplet;
import processing.core.PImage;
import processing.sound.SoundFile;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Game extends PApplet {


    HashMap<Integer, Boolean> keysHeld = new HashMap<>();
    Random r = new Random();

    PImage strButton;
    PImage dexButton;
    PImage potionButton;
    PImage logo;
    PImage milk;
    PImage playButton;
    PImage creditButton;
    PImage infoButton;
    PImage exit;
    PImage settings;
    PImage slash1;
    PImage slash2;
    PImage arena;
    PImage yarnBall;
    PImage eSymbol;
    PImage rSymbol;
    PImage fSymbol;
    PImage attackSymbol;
    PImage dashSymbol;
    PImage infoScreen;
    PImage trapOpen;
    PImage trapClosed;
    PImage mouseMiddle;
    PImage mouseBig;
    PImage mouseSmall;
    PImage healthBarFrame;
    PImage volumeBar;
    PImage volumeSlider;

    PImage wave1;
    PImage wave2;
    PImage wave3;
    PImage wave4;
    PImage wave5;

    public PImage cootsFace;
    public PImage cootsMad;
    public PImage cootsDead;
    public PImage paw;

    PImage playerMiddle;
    PImage playerLeft;
    PImage playerRight;

    public SoundFile openSound;
    public SoundFile hurtSound;
    public SoundFile collectSound;
    public SoundFile winSound;
    public SoundFile explosionSound;
    public SoundFile leftFoot;
    public SoundFile rightFoot;
    public SoundFile dashSound;
    public SoundFile attackSound;
    public SoundFile laserSound;

    SoundFile titleSong;
    SoundFile combatSong;
    SoundFile bossSong;
    SoundFile victorySong;

    ArrayList<SoundFile> sounds = new ArrayList<>();

    private long lastTime;
    private int fps;
    private int totalFrames;


    public int wave;
    public boolean attackingLeft = false;
    public boolean attackingRight = false;
    public Player player;
    public ArrayList<Entity> entities;
    public Coots coots;


    public enum GameState   {
        TITLE,
        CREDITS,
        PLAYING,
        SETTINGS
    }
    GameState state = GameState.TITLE;

    ArrayList<Entity> entitiesToKill = new ArrayList<>();
    float playerRotation = TWO_PI;
    int counter = 1;
    int killCount = 0;
    int strButtonX;
    int strButtonYIndent = 0;
    int dexButtonX;
    int dexButtonYIndent = 0;
    int healthButtonX;
    int healthButtonYIndent = 0;
    int xFacing = 0;
    int yFacing = -1;
    int dashStart = -1;
    float volumeSliderPos = 1.0f;
    int waveTransitionCounter = 180;
    boolean showInfo = true;
    boolean cootsAttacking = false;
    boolean logoH = false;
    boolean waveTransition = true;


    PImage prevPlayer;
    PImage currentPlayer;
    int logoY;
    int dashX;
    int dashY;

    int slashX = 0;
    int slashY = 0;
    float slashRot = 0;
    public void draw()  {
        background(20);
        counter ++;
        if (counter >= 481)  {
            counter = 1;
        }
        totalFrames++;
        if (System.nanoTime() > lastTime + 1000000000)  {
            lastTime = System.nanoTime();
            fps = totalFrames;
            totalFrames = 0;
        }

        System.out.println("FPS: " + fps);
//        System.out.println(player.getX() + " " + player.getY());
        switch (state)  {
            case TITLE:
                if (!titleSong.isPlaying()) {
                    titleSong.play();
                }
                if (combatSong.isPlaying()) {
                    combatSong.stop();
                }
                if (bossSong.isPlaying())   {
                    bossSong.stop();
                }

                if (counter % 3 == 0)   {
                    if (logoH)   {
                        logoY--;
                        logoH = logoY != displayHeight/12;
                    }
                    else  {
                        logoY++;
                        logoH = logoY == displayHeight/9;
                    }
                }

                image(logo, displayWidth/2 - logo.width/2, logoY);

                int titleX = displayWidth/2-playButton.width/2;
                int titleY = displayHeight/2-displayHeight/20;
                int titleWidth = playButton.width;
                int titleHeight = playButton.height;
                if (mouseOver(mouseX, mouseY, titleX, titleY, titleWidth, titleHeight) && !showInfo) {
                    titleX += 5;
                    titleY -= 5;

                    if (mousePressed)   {
                        state = GameState.PLAYING;
                        openSound.play();
                        startGame();
                    }
                }
                image(playButton, titleX, titleY);

                int creditX = displayWidth/2-creditButton.width/2;
                int creditY = displayHeight/2-displayHeight/20+titleHeight+displayHeight/20;
                int creditWidth = creditButton.width;
                int creditHeight = creditButton.height;

                if (mouseOver(mouseX, mouseY, creditX, creditY, creditWidth, creditHeight) && !showInfo) {
                    creditX += 5;
                    creditY -= 5;

                    if (mousePressed)   {
                        state = GameState.CREDITS;
                        openSound.play();
                    }
                }
                image(creditButton, creditX, creditY);

                int infoX = displayWidth/2 - infoButton.width/2;
                int infoY = displayHeight/2-displayHeight/20+titleHeight+displayHeight/20+creditHeight+displayHeight/20;
                int infoWidth = infoButton.width;
                int infoHeight = infoButton.height;

                if  (mouseOver(mouseX, mouseY, infoX, infoY, infoWidth, infoHeight) && !showInfo)    {
                    infoX += 5;
                    infoY -= 5;

                    if (mousePressed)  {
                        showInfo = true;
                        openSound.play();
                    }
                }
                image(infoButton, infoX, infoY);

                int exitX = 35;
                int exitY = 35;
                if (mouseOver(mouseX, mouseY, exitX, exitY, exit.width, exit.height) && !showInfo)   {
                    exitX += 5;
                    exitY -= 5;
                    if (mousePressed)   {
                        openSound.play();
                        exit();
                    }
                }
                image(exit, exitX, exitY);

                int settingsX = displayWidth - settings.width - 35;
                int settingsY = 35;
                if (mouseOver(mouseX, mouseY, settingsX, settingsY, settings.width, settings.height) && ! showInfo) {
                    settingsX += 5;
                    settingsY -=5;
                    if (mousePressed)   {
                        openSound.play();
                        state = GameState.SETTINGS;
                    }
                }
                image(settings, settingsX, settingsY);








                if (showInfo)   {
                    image(infoScreen, displayWidth/2 - infoScreen.width/2, displayHeight/2 - infoScreen.height/2);
                    int closeX = displayWidth/2 + infoScreen.width/2 - exit.width/2;
                    int closeY = displayHeight/2 - infoScreen.height/2 - exit.height/2;
                    if (mouseOver(mouseX, mouseY, closeX, closeY, exit.width, exit.height))   {
                        closeX += 5;
                        closeY -= 5;
                        if (mousePressed)   {
                            showInfo = false;
                            openSound.play();
                        }
                    }
                    image(exit, closeX, closeY);
                }

                break;
            case SETTINGS:
                if (!titleSong.isPlaying()) {
                    titleSong.play();
                }

                imageMode(CENTER);



                float volumeBarX = displayWidth/2.0f;
                float volumeBarY = displayHeight/2.0f;
                image(volumeBar, volumeBarX, volumeBarY);

                float rangeBottom = displayWidth/2.0f-volumeBar.width/2.0f+volumeSlider.width/2.0f;
                float rangeTop =  displayWidth/2.0f+volumeBar.width/2.0f-volumeSlider.width/2.0f;

                float volumeSliderX = (rangeBottom + (volumeSliderPos * (rangeTop - rangeBottom)));

                if (mouseOver(mouseX, mouseY, (int) rangeBottom - 100, displayHeight/2- volumeBar.height/2, (int) (rangeTop-rangeBottom)+100, volumeBar.height) && mousePressed)    {
                    volumeSliderPos = (Math.max(mouseX, rangeBottom) - rangeBottom) / (rangeTop-rangeBottom);
                    for (SoundFile s : sounds)  {
                        s.amp(volumeSliderPos);
                    }
                }

                image(volumeSlider, volumeSliderX, displayHeight/2.0f);


                imageMode(CORNER);
                break;
            case CREDITS:
                if (!titleSong.isPlaying()) {
                    titleSong.play();
                }
                strokeWeight(0);
                background(20);
                fill(250);
                textSize(55);
                textAlign(CENTER, CENTER);
                text("Programmer - Artist - Designer - Sound FX", displayWidth/2, displayHeight/2 - 200);
                text("Music", displayWidth/2, displayHeight/2+50);
                text("Creative Consult (Tester)", displayWidth/2, displayHeight/2 + 300);
                textSize(40);
                text("TacticalShroom#1848 - Gabriel Schallock", displayWidth/2, displayHeight/2-100);
                text("Nicolas Schallock", displayWidth/2, displayHeight/2+150);
                text("Kevin Sherrill", displayWidth/2, displayHeight/2+400);


                    break;
            case PLAYING:
                //--------------------------------------------------IN-GAME-LOOP-------------------------------------------

                if (titleSong.isPlaying())  {
                    titleSong.stop();
                }

                if (wave != 5 && !combatSong.isPlaying())  {
                    combatSong.play();
                }
                else if (wave == 5 && !bossSong.isPlaying() && coots.health > 0)    {
                    bossSong.play();
                    if (combatSong.isPlaying()) combatSong.stop();
                }

                if (!keysHeld.get(87) && !keysHeld.get(65) && !keysHeld.get(83) && !keysHeld.get(68) && dashStart == -1)   {
                    player.setMove(0, 0);
                }



                strokeWeight(0);
                int boardX = displayWidth/5;
                int boardY = displayHeight/5;
                stroke(0);
                image(arena, boardX, boardY);

                image(strButton, strButtonX, displayHeight/5+strButtonYIndent);
                image(eSymbol, strButtonX+strButton.width/2-eSymbol.width/2, displayHeight/5+strButtonYIndent+strButton.height-eSymbol.height);
                image(dexButton, dexButtonX, 2 * displayHeight/5+dexButtonYIndent);
                image(rSymbol, dexButtonX+dexButton.width/2-rSymbol.width/2, 2 * displayHeight/5+dexButtonYIndent+dexButton.height-rSymbol.height);
                image(potionButton, healthButtonX, 3 * displayHeight/5+healthButtonYIndent);
                image(fSymbol, healthButtonX+potionButton.width/2-fSymbol.width/2, 3 * displayHeight/5+healthButtonYIndent+potionButton.height-fSymbol.height);



                int attackSymbolX = displayWidth/2 - attackSymbol.width - displayWidth/40;
                int symbolY = boardY + arena.height + displayHeight/40;
                image(attackSymbol, attackSymbolX, symbolY);

                int dashSymbolX = displayWidth/2 + displayWidth/40;
                image(dashSymbol, dashSymbolX, symbolY);

                if (player.attackCoolDown > 0)  {
                    fill(60, 90);
                    rect(attackSymbolX, symbolY, (player.attackCoolDown / 45.0f)*attackSymbol.width, attackSymbol.height);
                }
                if (player.dashCoolDown > 0)    {
                    fill(60, 90);
                    rect(dashSymbolX, symbolY, (player.dashCoolDown / 180.0f) * dashSymbol.width, dashSymbol.height);
                }

                int milkX = (4 * displayWidth/5) + (displayWidth / 25);
                int milkY = displayHeight/50;
                image(milk, milkX, milkY, milk.width, milk.height);
                fill(250);
                textSize(90);
                text(player.xp, milkX+milk.width+displayWidth/22, milkY+milk.height/2);

                fill(250);



                if (player.slashing)    {
                    translate(player.getX() + player.getEntity().width/2, player.getY()+player.getEntity().height/2);
                    rotate(slashRot);
                    imageMode(CENTER);
                    if (player.slashCounter > 5)    {
                        image(slash1, slashX, slashY);
                    }
                    else    {
                        image(slash2, slashX, slashY);
                    }
                    if (player.slashCounter == 0)   {
                        player.slashing = false;
                        slashX = 0;
                        slashY = 0;
                        slashRot = 0;
                    }
                    resetMatrix();
                    imageMode(CORNER);
                    player.slashCounter--;
                }
                //-------------------------------------------------------------Entity Loop---------------------------------------------
                for (Entity entity : entities)  {
                    entity.update(this);
                    if (entity.isDead())    {
                        entitiesToKill.add(entity);
                        continue;
                    }
                    if (entity instanceof Milk) {
                        image(milk, entity.getX(), entity.getY(), milk.width/3, milk.height/3);
                    }
                    else if (entity instanceof Brute)    {
                        translate(entity.getX()+entity.getEntity().width/2, entity.getY()+entity.getEntity().height/2);
                        rotate(r.nextFloat(TWO_PI));
                        imageMode(CENTER);
                        image(yarnBall, 0, 0);
                        imageMode(CORNER);
                        resetMatrix();
                    }
                    else if (entity instanceof Trap trap)    {
                        if (trap.trapping)  {
                            image(trapClosed, entity.getX(), entity.getY());
                        }
                        else    {
                            image(trapOpen, entity.getX(), entity.getY());
                        }
                    }
                    else if (entity instanceof Mouse mouse)   {
                        float mouseRotation  = ((Mouse) entity).lastRotation;
                        translate((float) (entity.getX()+mouseMiddle.width/2), (float) (entity.getY()+mouseMiddle.height/2));

                        if (counter % 15 == 0)   {
                            if (mouse.currentMouse != mouseMiddle)  {
                                mouse.prevMouse = mouse.currentMouse;
                            }

                            if (mouse.currentMouse == mouseSmall || mouse.currentMouse == mouseBig) {
                                mouse.currentMouse = mouseMiddle;
                            }
                            else if (mouse.prevMouse == mouseSmall) {
                                mouse.currentMouse = mouseBig;
                            }
                            else if (mouse.prevMouse == mouseBig)   {
                                mouse.currentMouse = mouseSmall;
                            }
                            else if (mouse.prevMouse == mouseMiddle)    {
                                mouse.currentMouse = mouseSmall;
                            }
                        }

                        if (entity.getVx() > 0) {
                            mouseRotation = -HALF_PI;
                        }
                        else if (entity.getVx() < 0) {
                            mouseRotation = HALF_PI;
                        }
                        else if (entity.getVy() > 0) {
                            mouseRotation = TWO_PI;
                        }
                        else if (entity.getVy() < 0) {
                            mouseRotation = PI;
                        }
                        mouse.lastRotation = mouseRotation;
                        rotate(mouseRotation);
                        image(mouse.currentMouse, -mouseMiddle.width/2.0f, -mouseMiddle.height/2.0f);
                        resetMatrix();
                    }
                }
                for (Entity entity : entitiesToKill)    {
                    //----------------------------------------------KILLING ENTITIES----------------------------------------------------

                    entities.remove(entity);
                    if (entity instanceof Brute brute)    {
                        killCount++;
                        if (brute.loot())   {
                            entities.add(new Milk(entity.getX(), entity.getY(), milk.width/5, milk.height/5));
                        }
                    }
                    else if (entity instanceof Player)   {
                        player = new Player(displayWidth/2, displayHeight/2, playerMiddle.width, playerMiddle.height, displayHeight/18);
                        wave = 1;
                        killCount = 0;

                        state = GameState.TITLE;
                    }
                    else if (entity instanceof Milk)    {
                        player.xp++;
                        collectSound.play();
                    }
                    else if (entity instanceof Trap trap)    {
                        killCount++;
                        if (trap.loot())    {
                            entities.add(new Milk(entity.getX(), entity.getY(), milk.width/5, milk.height/5));
                        }
//                        entities.add(new Milk(entity.getX()+5, entity.getY()+5, milk.width/5, milk.height/5));

                        if (trap.trapping)  {
                            player.unTrap();
                        }
                    }
                    else if (entity instanceof Mouse mouse)   {
                        killCount++;
                        if (mouse.loot())    {
                            entities.add(new Milk(entity.getX(), entity.getY(), milk.width/5, milk.height/5));
                        }
                    }

                    //------------------------------------------------------------------------------------------------------------------
                }
                entitiesToKill = new ArrayList<>();

                if (counter % (20-Math.round(player.speed)*2) == 0) {
                    if (player.getVy() != 0 || player.getVx() != 0) {
                        if (currentPlayer != playerMiddle) {
                            prevPlayer = currentPlayer;
                        }

                        if (currentPlayer == playerLeft || currentPlayer == playerRight) {
                            currentPlayer = playerMiddle;
                        } else if (prevPlayer == playerLeft) {
                            rightFoot.play();
                            currentPlayer = playerRight;
                        } else {
                            leftFoot.play();
                            currentPlayer = playerLeft;
                        }
                    } else {
                        currentPlayer = playerMiddle;
                    }
                }

                if (counter-5 >= dashStart)    {
                    player.addMove(-dashX, -dashY);
                    dashX = 0;
                    dashY = 0;
                    dashStart = -1;
                }

                translate((float) (player.getX()+currentPlayer.width/2), (float) (player.getY()+currentPlayer.height/2));
                if (player.getVx() > 0) {
                    playerRotation = HALF_PI;
                }
                else if (player.getVx() < 0) {
                    playerRotation = -HALF_PI;
                }
                else if (player.getVy() > 0) {
                    playerRotation = PI;
                }
                else if (player.getVy() < 0) {
                    playerRotation = TWO_PI;
                }
                rotate(playerRotation);
                image(currentPlayer, -currentPlayer.width/2, -currentPlayer.height/2);
                resetMatrix();

                if (player.getVx() > 0) xFacing = 1;
                if (player.getVy() > 0) yFacing = 1;
                if (player.getVx() < 0) xFacing = -1;
                if (player.getVy() < 0) yFacing = -1;

                if (playerRotation != -HALF_PI && playerRotation != HALF_PI)    xFacing = 0;
                if (playerRotation != PI && playerRotation != TWO_PI)    yFacing = 0;

                strokeWeight(0);
                fill(255, 0, 0);
                rect(displayWidth/25, displayHeight/5 + (player.getMaxHP()-player.getHealth())*(3*displayHeight / (5 * player.getMaxHP())), 3 * displayWidth/25, player.getHealth()*(3*displayHeight / (5 * player.getMaxHP())));
                image(healthBarFrame, displayWidth/25, displayHeight/5);


                //-------------------------------------------------------WAVES-----------------------------------------------------------------
                if (!waveTransition)  {
                    int entityCount = entitySize();
                    switch (wave)   {
                        case 1:
                            if (entityCount <= 10)  {
                                if (counter % 120 == 0) {
                                    entities.add(new Mouse(r.nextInt(displayWidth/5, 4 * displayWidth/5 - 100), r.nextInt(displayHeight/5, 4 * displayHeight/5 - 100), mouseMiddle.width, mouseMiddle.height, 5, mouseMiddle));
                                }
                                if (counter % 480 == 0)  {
                                    entities.add(new Brute(r.nextInt(displayWidth/5, 4 * displayWidth/5 - 100), r.nextInt(displayHeight/5, 4 * displayHeight/5 - 100), 10));
                                }
                            }


                            nextWave(35);
                            break;
                        case 2:
                            if (entityCount <= 10)  {
                                if (counter % 120 == 0) {
                                    entities.add(new Brute(r.nextInt(displayWidth/5, 4 * displayWidth/5 - 100), r.nextInt(displayHeight/5, 4 * displayHeight/5 - 100), 15));
                                }
                                if (counter % 60 == 0) {
                                    entities.add(new Mouse(r.nextInt(displayWidth/5, 4 * displayWidth/5 - 100), r.nextInt(displayHeight/5, 4 * displayHeight/5 - 100), mouseMiddle.width, mouseMiddle.height, 5, mouseMiddle));
                                }
                            }


                            nextWave(50);
                            break;
                        case 3:
                            if (entityCount <= 10)  {
                                if (counter % 360 == 0) {
                                    Point coord = spawn(trapOpen.width, trapOpen.height);
                                    entities.add(new Trap(coord.x, coord.y, trapOpen.width, trapOpen.height));
                                }
                                if (counter % 90 == 0)  {
                                    entities.add(new Brute(r.nextInt(displayWidth/5, 4 * displayWidth/5 - 100), r.nextInt(displayHeight/5, 4 * displayHeight/5 - 100), 15));
                                }
                                if (counter % 30 == 0) {
                                    entities.add(new Mouse(r.nextInt(displayWidth/5, 4 * displayWidth/5 - 100), r.nextInt(displayHeight/5, 4 * displayHeight/5 - 100), mouseMiddle.width, mouseMiddle.height, 5, mouseMiddle));
                                }
                            }


                            nextWave(70);
                            break;
                        case 4:
                            if (entityCount <= 10)  {
                                if (counter % 180 == 0) {
                                    Point coord = spawn(trapOpen.width, trapOpen.height);
                                    entities.add(new Trap(coord.x, coord.y, trapOpen.width, trapOpen.height));
                                }
                                if (counter % 60 == 0)  {
                                    entities.add(new Brute(r.nextInt(displayWidth/5, 4 * displayWidth/5 - 100), r.nextInt(displayHeight/5, 4 * displayHeight/5 - 100), 25));
                                }

                                if (counter % 45 == 0) {
                                    entities.add(new Mouse(r.nextInt(displayWidth/5, 4 * displayWidth/5 - 100), r.nextInt(displayHeight/5, 4 * displayHeight/5 - 100), mouseMiddle.width, mouseMiddle.height, 5, mouseMiddle));
                                }
                            }


                            nextWave(85);
                            break;
                        case 5:
                            //---------------------------------------------------------------BOSS FIGHT------------------------------------------


                            fill(0);
                            stroke(0);

                            if (coots.health > coots.getMaxHealth()/2)   {
                                if (entityCount <= 8)  {
                                    if (counter % 60 == 0) {
                                        entities.add(new Brute(r.nextInt(displayWidth/5, 4 * displayWidth/5 - 100), r.nextInt(displayHeight/5, 4 * displayHeight/5 - 100), 15));
                                    }

                                    if (counter % 180 == 0) {
                                        Point coord = spawn(trapOpen.width, trapOpen.height);
                                        entities.add(new Trap(coord.x, coord.y, trapOpen.width, trapOpen.height));
                                    }
                                    if (counter % 60 == 0) {
                                        entities.add(new Mouse(r.nextInt(displayWidth/5, 4 * displayWidth/5 - 100), r.nextInt(displayHeight/5, 4 * displayHeight/5 - 100), mouseMiddle.width, mouseMiddle.height, 5, mouseMiddle));
                                    }
                                }

                                if (counter % 150 == 0)    {
                                    cootsAttacking = true;
                                    coots.pawAttack(player.getX()+currentPlayer.width/2, player.getY()+currentPlayer.height/2);
                                }

                                if (coots.hurt) {
                                    tint(150, 0, 0);
                                }
                                image(cootsFace, displayWidth/2.0f - cootsFace.width/2.0f, boardY - 2 * cootsFace.height/3.0f);
                                tint(255, 255);
                            }
                            else if (coots.health > 0)  {
                                if (entityCount <= 8)  {
                                    if (counter % 60 == 0) {
                                        entities.add(new Brute(r.nextInt(displayWidth/5, 4 * displayWidth/5 - 100), r.nextInt(displayHeight/5, 4 * displayHeight/5 - 100), 15));
                                    }
                                    if (counter % 180 == 0) {
                                        Point coord = spawn(trapOpen.width, trapOpen.height);
                                        entities.add(new Trap(coord.x, coord.y, trapOpen.width, trapOpen.height));
                                    }
                                    if (counter % 60 == 0) {
                                        entities.add(new Mouse(r.nextInt(displayWidth/5, 4 * displayWidth/5 - 100), r.nextInt(displayHeight/5, 4 * displayHeight/5 - 100), mouseMiddle.width, mouseMiddle.height, 5, mouseMiddle));
                                    }
                                }

                                if (counter % 240 == 0)  {
                                    coots.laserAttack();
                                }
                                if (counter % 110 == 0)    {
                                    cootsAttacking = true;
                                    coots.pawAttack(player.getX()+currentPlayer.width/2, player.getY()+currentPlayer.height/2);
                                }
                                if (coots.hurt) {
                                    tint(150, 0, 0);
                                }
                                image(cootsMad, displayWidth/2.0f - cootsFace.width/2.0f, boardY - 2 * cootsFace.height/3.0f);
                                tint(255, 255);
                            }
                            else    {

                                float cootsDeadX = displayWidth/2.0f - cootsFace.width/2.0f;
                                float cootsDeadY = boardY - 2 * cootsFace.height/3.0f;
                                if (coots.deathCounter > 60) {
                                    cootsDeadX += (r.nextInt(10)-5);
                                    cootsDeadY += (r.nextInt(10)-5);
                                }
                                image(cootsDead, cootsDeadX, cootsDeadY);
                            }

                            coots.tick(this, paw);

                            if (!attackingLeft) {
                                image(paw, displayWidth/2.0f - arena.width/5.0f - paw.width/2.0f, boardY - paw.height/2.0f);
                            }
                            if (!attackingRight)    {
                                image(paw, displayWidth/2.0f + arena.width/5.0f - paw.width/2.0f, boardY - paw.height/2.0f);
                            }

                            if (coots.getHealth() <= 0) {
                                entities = new ArrayList<>();
                                entities.add(player);
                                coots.deathCounter--;
                                if (bossSong.isPlaying()) bossSong.stop();
                                if (coots.deathCounter==209)    victorySong.play();
                                if (coots.deathCounter==209)    winSound.play();
                                if (coots.deathCounter <= 1)    {
                                    state = GameState.TITLE;
                                }
                            }
                            break;
                    }
                }
                else    {
                    if (waveTransitionCounter == 180)   winSound.play();

                    if (waveTransitionCounter > 120)    {
                        tint(255, 255 - (255.0f/(255-waveTransitionCounter)));
                    }

                    switch (wave)   {
                        case 1:
                            image(wave1, displayWidth/2.0f - wave1.width/2.0f, displayHeight/2.0f - wave1.height/2.0f);
                            break;
                        case 2:
                            image(wave2, displayWidth/2.0f - wave2.width/2.0f, displayHeight/2.0f - wave2.height/2.0f);
                            break;
                        case 3:
                            image(wave3, displayWidth/2.0f - wave3.width/2.0f, displayHeight/2.0f - wave3.height/2.0f);
                            break;
                        case 4:
                            image(wave4, displayWidth/2.0f - wave4.width/2.0f, displayHeight/2.0f - wave4.height/2.0f);
                            break;
                        case 5:
                            image(wave5, displayWidth/2.0f - wave5.width/2.0f, displayHeight/2.0f - wave5.height/2.0f);
                            break;
                    }

                    tint(255, 255);


                    waveTransitionCounter--;

                    if (waveTransitionCounter < 1)  {
                        waveTransition = false;
                    }
                }
        }
    }

    public int entitySize() {
        AtomicInteger counter = new AtomicInteger(entities.size());;
        entities.forEach(entity -> {if (entity instanceof Milk) counter.getAndDecrement();});
        return counter.get();
    }

    public void keyPressed()    {
//        System.out.println(keyCode);
        if (key == ESC) {
            key = 0;
            entities = new ArrayList<>();
            if (showInfo || state != GameState.TITLE)   {
                state = GameState.TITLE;
                showInfo = false;
                openSound.play();
            }
        }
        if (!keysHeld.containsKey(keyCode)) keysHeld.put(keyCode, false);
        if (state == GameState.PLAYING) {
            if (!keysHeld.containsKey(keyCode) || !keysHeld.get(keyCode)) {
                switch (Character.toLowerCase(key)) {
                    case 'w':
                        player.addMove(0, -player.speed);
                        break;
                    case 'a':
                        player.addMove(-player.speed, 0);
                        break;
                    case 's':
                        player.addMove(0, player.speed);
                        break;
                    case 'd':
                        player.addMove(player.speed, 0);
                        break;
                    case 'l':
                        player.hit(1);
                        break;
                    case 'e':
                        strButtonYIndent += 5;
                        strButtonX -= 5;
                        if (player.xp >= 1)   {
                            player.str += 0.2;
                            player.xp -= 1;
                        }
                        break;
                    case 'r':
                        dexButtonYIndent += 5;
                        dexButtonX -= 5;
                        if (player.xp >= 1)   {
                            if (player.dex <= 2.5)   {
                                player.dex += 0.2;
                                player.xp -= 1;
                            }
                            double xToSet = 0;
                            double yToSet = 0;
                            if (player.getVx() != 0)    {
                                xToSet = player.getBaseSpeed()*player.dex * (player.getVx()/Math.abs(player.getVx()));
                            }
                            if (player.getVy() != 0)    {
                                yToSet = player.getBaseSpeed()*player.dex * (player.getVy()/Math.abs(player.getVy()));
                            }
                            player.setMove(xToSet, yToSet);
                        }
                        break;
                    case 'f':
                        healthButtonYIndent += 5;
                        healthButtonX -= 5;
                        if (player.xp >= 1)   {
                            player.setHealth(player.maxHP);
                            player.xp -= 1;
                        }
                        break;
                }
                //handling attacks with arrow keys
                switch (keyCode)    {
                    case 38:
                        if (player.attackCoolDown <= 0) {
                            player.slash();
                            this.slashX = 0;
                            this.slashY = -2 * player.getEntity().height / 3;
                            this.slashRot = 0;

                        }
                        player.attack(new Rectangle(player.getX()-displayHeight/54, player.getY()-player.getRange(), player.getEntity().width+displayHeight/27, player.getRange()));
                        break;
                    case 37:
                        if (player.attackCoolDown <= 0) {
                            player.slash();
                            this.slashX = 0;
                            this.slashY = -2 * player.getEntity().height / 3;
                            this.slashRot = -HALF_PI;
                            resetMatrix();
                        }
                        player.attack(new Rectangle(player.getX()-player.getRange(), player.getY()-displayHeight/54, player.getRange(), player.getEntity().height+displayHeight/27));
                        break;
                    case 40:
                        if (player.attackCoolDown <= 0) {
                            player.slash();
                            this.slashX = 0;
                            this.slashY = -2 * player.getEntity().height / 3;
                            this.slashRot = PI;
                            resetMatrix();
                        }
                        player.attack(new Rectangle(player.getX()-displayHeight/54, player.getY()+player.getEntity().height, player.getEntity().width+displayHeight/27, player.getRange()));
                        break;
                    case 39:
                        if (player.attackCoolDown <= 0) {
                            player.slash();
                            this.slashX = 0;
                            this.slashY = -2 * player.getEntity().height / 3;
                            this.slashRot = HALF_PI;
                            resetMatrix();
                        }
                        player.attack(new Rectangle(player.getX()+player.getEntity().width, player.getY()-displayHeight/54, player.getRange(), player.getEntity().height+displayHeight/27));
                        break;
                    case 16:
                        if (player.dashCoolDown <= 0)   {
                            player.dashCoolDown = 180;
                            dashSound.play();
                            if (player.getVx() == 0)    {
                                dashX = 0;
                            }
                            else    {
                                dashX = (int) (player.getVx()/Math.abs(player.getVx()) * (3 * displayHeight/5) / 10);
                            }
                            if (player.getVy() == 0)    {
                                dashY = 0;
                            }
                            else    {
                                dashY = (int) (player.getVy()/Math.abs(player.getVy()) * (3 * displayHeight/5) / 10);
                            }
                            player.addMove(dashX, dashY);
                            dashStart = counter;
                        }
                        break;
                }
            }

            if (keysHeld.containsKey(keyCode))  {
                keysHeld.replace(keyCode, true);
            } else  {
                keysHeld.put(keyCode, true);
            }
        }
    }


    public boolean nextWave(int kills)  {
        if (killCount >= kills) {
            killCount = 0;
            player.setHealth(player.maxHP);
            waveTransition = true;
            waveTransitionCounter = 180;
            entities.forEach(entity -> {
                if (entity instanceof Brute || entity instanceof Trap || entity instanceof Milk || entity instanceof Mouse)    {
                    entitiesToKill.add(entity);
                }
            });
            wave++;
            entities = new ArrayList<>();
            entities.add(player);
            return true;
        }

        return false;
    }

    public void keyReleased()   {
        if (!keysHeld.containsKey(keyCode)) keysHeld.put(keyCode, false);
        if (state == GameState.PLAYING) {
            if (keysHeld.containsKey(keyCode) || keysHeld.get(keyCode)) {
                switch (Character.toLowerCase(key)) {
                    case 'w':
                        player.addMove(0, player.speed);
                        break;
                    case 'a':
                        player.addMove(player.speed, 0);
                        break;
                    case 's':
                        player.addMove(0, -player.speed);
                        break;
                    case 'd':
                        player.addMove(-player.speed, 0);
                        break;
                    case 'e':
                        strButtonYIndent -= 5;
                        strButtonX += 5;
                        break;
                    case 'r':
                        dexButtonYIndent -= 5;
                        dexButtonX += 5;
                        break;
                    case 'f':
                        healthButtonYIndent -= 5;
                        healthButtonX += 5;
                        break;
                }
            }


        }
        keysHeld.replace(keyCode, false);
    }


    public Point spawn(int w, int h)    {
        int x = r.nextInt(displayWidth/5, 4*displayWidth/5 - 100);
        int y = r.nextInt(displayHeight/5, 4 * displayHeight/5 - 100);
        while (new Rectangle(x-w, y-h, 2*w, 2*h).intersects(player.getEntity()))    {
            x = r.nextInt(displayWidth/5, 4*displayWidth/5 - 100);
            y = r.nextInt(displayHeight/5, 4 * displayHeight/5 - 100);
        }

        return new Point(x, y);
    }

    public void damage(Entity e, double dmg)    {
        e.hit(dmg);
    }

    public void damage(Coots c, double dmg)    {
        c.hit(dmg);
    }

    public boolean mouseOver(int mouseX, int mouseY, int x, int y, int w, int h)  {
        return mouseX > x && mouseX < x+w && mouseY < y+h && mouseY > y;
    }

    public void settings()  {
        fullScreen();
    }
    public void setup() {
        frameRate(60);
        keysHeld.put(16, false);
        keysHeld.put(-1, false);
        keysHeld.put(87, false);
        keysHeld.put(65, false);
        keysHeld.put(83, false);
        keysHeld.put(68, false);

        //init images
        logo = loadImage("arlynnKnightAssets/logo.png");
        exit = loadImage("arlynnKnightAssets/exit.png");
        settings = loadImage("arlynnKnightAssets/settings.png");
        playButton = loadImage("arlynnKnightAssets/playButton.png");
        creditButton = loadImage("arlynnKnightAssets/creditButton.png");
        infoButton = loadImage("arlynnKnightAssets/infoButton.png");
        strButton = loadImage("arlynnKnightAssets/statButtons/strButton.png");
        dexButton = loadImage("arlynnKnightAssets/statButtons/dexButton.png");
        potionButton = loadImage("arlynnKnightAssets/statButtons/healthPotion.png");
        playerMiddle = loadImage("arlynnKnightAssets/player/playerMiddle.png");
        playerLeft = loadImage("arlynnKnightAssets/player/playerLeft.png");
        playerRight = loadImage("arlynnKnightAssets/player/playerRight.png");
        slash1 = loadImage("arlynnKnightAssets/player/slash1.png");
        slash2 = loadImage("arlynnKnightAssets/player/slash2.png");
        arena = loadImage("arlynnKnightAssets/arena.png");
        milk = loadImage("arlynnKnightAssets/milk.png");
        yarnBall = loadImage("arlynnKnightAssets/yarnBall.png");
        eSymbol = loadImage("arlynnKnightAssets/statButtons/E.png");
        rSymbol = loadImage("arlynnKnightAssets/statButtons/R.png");
        fSymbol = loadImage("arlynnKnightAssets/statButtons/F.png");
        attackSymbol = loadImage("arlynnKnightAssets/attackSymbol.png");
        dashSymbol = loadImage("arlynnKnightAssets/dashSymbol.png");
        infoScreen = loadImage("arlynnKnightAssets/info.png");
        cootsFace = loadImage("arlynnKnightAssets/coots/coots.png");
        cootsMad = loadImage("arlynnKnightAssets/coots/cootsMad.png");
        cootsDead = loadImage("arlynnKnightAssets/coots/cootsDead.png");
        paw = loadImage("arlynnKnightAssets/coots/paw.png");
        trapOpen = loadImage("arlynnKnightAssets/trap.png");
        trapClosed = loadImage("arlynnKnightAssets/trapClosed.png");
        wave1 = loadImage("arlynnKnightAssets/waves/wave1.png");
        wave2 = loadImage("arlynnKnightAssets/waves/wave2.png");
        wave3 = loadImage("arlynnKnightAssets/waves/wave3.png");
        wave4 = loadImage("arlynnKnightAssets/waves/wave4.png");
        wave5 = loadImage("arlynnKnightAssets/waves/wave5.png");
        mouseMiddle = loadImage("arlynnKnightAssets/mouseMiddle.png");
        mouseBig = loadImage("arlynnKnightAssets/mouseBig.png");
        mouseSmall = loadImage("arlynnKnightAssets/mouseSmall.png");
        healthBarFrame = loadImage("arlynnKnightAssets/healthBarFrame.png");
        volumeBar = loadImage("arlynnKnightAssets/volumeBar.png");
        volumeSlider = loadImage("arlynnKnightAssets/volumeSlider.png");

        //init sounds
        openSound = new SoundFile(this, "arlynnKnightAssets/sounds/open.wav");
        collectSound = new SoundFile(this, "arlynnKnightAssets/sounds/collect.wav");
        explosionSound = new SoundFile(this, "arlynnKnightAssets/sounds/explosion.wav");
        hurtSound = new SoundFile(this, "arlynnKnightAssets/sounds/hurt.wav");
        winSound = new SoundFile(this, "arlynnKnightAssets/sounds/win.wav");
        leftFoot = new SoundFile(this, "arlynnKnightAssets/sounds/leftFoot.wav");
        rightFoot = new SoundFile(this, "arlynnKnightAssets/sounds/rightFoot.wav");
        dashSound = new SoundFile(this, "arlynnKnightAssets/sounds/dash.wav");
        attackSound = new SoundFile(this, "arlynnKnightAssets/sounds/attack.wav");
        laserSound = new SoundFile(this, "arlynnKnightAssets/sounds/laser.wav");

        //init music
        titleSong = new SoundFile(this, "arlynnKnightAssets/music/title.wav");
        combatSong = new SoundFile(this, "arlynnKnightAssets/music/combat.wav");
        bossSong = new SoundFile(this, "arlynnKnightAssets/music/boss.wav");
        victorySong = new SoundFile(this, "arlynnKnightAssets/music/victory.wav");


        sounds.add(openSound);
        sounds.add(collectSound);
        sounds.add(explosionSound);
        sounds.add(hurtSound);
        sounds.add(winSound);
        sounds.add(leftFoot);
        sounds.add(rightFoot);
        sounds.add(dashSound);
        sounds.add(attackSound);
        sounds.add(laserSound);
        sounds.add(titleSong);
        sounds.add(combatSong);
        sounds.add(bossSong);
        sounds.add(victorySong);


        //adjust volume
//        dashSound.amp(1.0f);
//        attackSound.amp(0.25f);
//        collectSound.amp(0.6f);
//        hurtSound.amp(0.8f);
//        titleSong.amp(0.1f);
//        combatSong.amp(0.2f);
//        bossSong.amp(0.2f);
//        victorySong.amp(0.1f);

        //image resize
        arena.resize(3 * displayWidth/5,3 * displayHeight/5);
        strButton.resize(displayHeight/5-25, displayHeight/5-25);
        dexButton.resize(displayHeight/5-25, displayHeight/5-25);
        potionButton.resize(displayHeight/5-25, displayHeight/5-25);
        playerMiddle.resize(4 * strButton.width / 7, 0);
        playerLeft.resize(playerMiddle.width, 0);
        playerRight.resize(playerMiddle.width, 0);
        milk.resize(0, strButton.height);
        playButton.resize(0, displayHeight/ 9);
        logo.resize(0, (int) (playButton.height*2.5));
        exit.resize(playButton.height, 0);
        settings.resize(exit.width, 0);
        creditButton.resize(0, playButton.height);
        infoButton.resize(0, playButton.height);
        currentPlayer = playerMiddle;
        player = new Player(displayWidth/2, displayHeight/2, playerMiddle.width, playerMiddle.height, displayHeight/18);
        slash1.resize(player.getEntity().width+40, 0);
        slash2.resize(slash1.width, 0);
        yarnBall.resize(player.getEntity().width, player.getEntity().width);
        eSymbol.resize(strButton.width/3, strButton.width/3);
        rSymbol.resize(strButton.width/3, strButton.width/3);
        fSymbol.resize(strButton.width/3, strButton.width/3);
        dashSymbol.resize(3*strButton.width/4, 3*strButton.width/4);
        attackSymbol.resize(3*strButton.width/4, 3*strButton.width/4);
        cootsFace.resize(3 * strButton.width / 2, 3 * strButton.width / 2);
        cootsDead.resize(cootsFace.width, cootsFace.height);
        cootsMad.resize(cootsFace.width, cootsFace.height);
        paw.resize(attackSymbol.width, attackSymbol.width);
        trapOpen.resize(paw.width - paw.width/4, paw.width - paw.width/4);
        trapClosed.resize(trapOpen.width, trapOpen.height);
        wave1.resize(0, displayHeight/5);
        wave2.resize(0, displayHeight/5);
        wave3.resize(0, displayHeight/5);
        wave4.resize(0, displayHeight/5);
        wave5.resize(0, displayHeight/5);
        mouseMiddle.resize(playerMiddle.width, 0);
        mouseBig.resize(mouseMiddle.width, mouseMiddle.height);
        mouseSmall.resize(mouseMiddle.width, mouseMiddle.height);
        healthBarFrame.resize(3 * displayWidth/25, 6 * displayHeight/10);
        volumeBar.resize(logo.width, 0);
        volumeSlider.resize(0, (int) (volumeBar.height*1.2));

        logoY = displayHeight/12;
    }

    public void startGame() {
        player = new Player(displayWidth/2, displayHeight/2, playerMiddle.width, playerMiddle.height, displayHeight/18);
        entities = new ArrayList<>();
        entities.add(player);
        killCount = 0;

        strButtonX = (4 * displayWidth/5) + (displayWidth / 25);
        dexButtonX = (4 * displayWidth/5) + (displayWidth / 25);
        healthButtonX = (4 * displayWidth/5) + (displayWidth / 25);

        coots = new Coots(500, 5, 10, paw);
        coots.hitBoxes.add(new Rectangle(displayWidth/2 - cootsFace.width/2, displayHeight/5 - 2 * cootsFace.height/3, cootsFace.width, cootsFace.height));

        wave = 1;
    }
    public static void main(String[] args)  {
        main("com.tacticalshroom.arlynnknight.Game");
    }
}