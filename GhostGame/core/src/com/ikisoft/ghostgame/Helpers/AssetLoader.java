package com.ikisoft.ghostgame.Helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Max on 2.4.2017.
 */

public class AssetLoader {


    public static Animation<TextureRegion> ghostAnimation,
            bronzeAnimation,
            silverAnimation,
            goldAnimation,
            diamondAnimation,
            ghostPirateAnimation,
            ghostKingAnimation,
            ghostNinjaAnimation;

    public static TextureRegion ghost1,
            ghost2,
            ghost3,
            ghostDead,
            ghostSpooking,
            ghostking1,
            ghostking2,
            ghostking3,
            ghostkingDead,
            ghostkingSpooking,
            ghostninja1,
            ghostninja2,
            ghostninja3,
            ghostninjaDead,
            ghostninjaSpooking,
            ghostpirate1,
            ghostpirate2,
            ghostpirate3,
            ghostpirateDead,
            ghostpirateSpooking,
            ghostpolice1,
            ghostpolice2,
            ghostpolice3,
            ghostpoliceDead,
            ghostpoliceSpooking,
            basicGhostSpooking, basicGhostDead,
            sun,
            background,
            ground,
            backMountain,
            frontMountain,
            shadow,
            mob1,
            mobDead,
            jumpingMob,
            jumpingMobDead,
            spike,
            longSpike,
            gravestone,
            mainmenu,
            menu,
            options,
            tutorial,
            pause,
            logo,
            toggledOff,
            kingIcon,
            ninjaIcon,
            pirateIcon,
            selectedIcon,
            rankStone,
            rankBronze1,
            rankBronze2,
            rankBronze3,
            rankSilver1,
            rankSilver2,
            rankSilver3,
            rankGold1,
            rankGold2,
            rankGold3,
            rankDiamond1,
            rankDiamond2,
            rankDiamond3,
            expCell,
            pirateUnlSplash,
            ninjaUnlSplash,
            kingUnlSplash;

    public static Texture texture;

    public static Sound dead,
            spook,
            ninjaspook,
            jump,
            score,
            mobhit,
            menuclick1,
            menuclick2,
            mobjump,
            ninjajump;

    public static Music theme;

    public static BitmapFont font,
            font2,
            font3,
            font4;

    public static void load() {

        loadTextures();
        loadAudio();
        loadFont();
        loadGhost();
        loadKing();
        loadNinja();
        loadPirate();
        //loadPolice();
        TextureRegion[] ghost = {ghost1, ghost2, ghost3};
        TextureRegion[] ghostKing = {ghostking1, ghostking2, ghostking3};
        TextureRegion[] ghostNinja = {ghostninja1, ghostninja2, ghostninja3};
        TextureRegion[] ghostPirate = {ghostpirate1, ghostpirate2, ghostpirate3};
        //TextureRegion[] ghostpolice = {ghostpolice1, ghostpolice2, ghostpolice3};
        ghostAnimation = new Animation(0.3f, ghost);
        ghostPirateAnimation = new Animation(0.3f, ghostPirate);
        ghostNinjaAnimation = new Animation(0.3f, ghostNinja);
        ghostKingAnimation = new Animation(0.3f, ghostKing);
        ghostAnimation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
        ghostPirateAnimation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
        ghostNinjaAnimation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
        ghostKingAnimation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

    }


    private static void loadTextures() {
        //GAMEOBJECTS
        texture = new Texture(Gdx.files.internal("textures/textureatlas.png"));
        texture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        background = new TextureRegion(texture, 0, 0, 1080, 1920);
        ground = new TextureRegion(texture, 0, 1368, 1080, 552);
        backMountain = new TextureRegion(texture, 1422, 0, 1080, 393);
        frontMountain = new TextureRegion(texture, 2503, 0, 1080, 393);
        shadow = new TextureRegion(texture, 1338, 0, 83, 25);
        mob1 = new TextureRegion(texture, 1080, 239, 85, 85);
        jumpingMob = new TextureRegion(texture, 2560, 1024, 85, 85);
        mobDead = new TextureRegion(texture, 1080, 325, 85, 85);
        jumpingMobDead = new TextureRegion(texture, 2688, 1024, 85, 85);
        spike = new TextureRegion(texture, 1422, 394, 85, 390);
        longSpike = new TextureRegion(texture, 1508, 394, 85, 770);
        sun = new TextureRegion(texture, 1080, 411, 220, 220);
        //UI OBJECTS
        gravestone = new TextureRegion(texture, 2379, 1922, 857, 932);
        mainmenu = new TextureRegion(texture, 0, 1921, 792, 536);
        menu = new TextureRegion(texture, 793, 1921, 792, 1236);
        options = new TextureRegion(texture, 0, 2458, 792, 536);
        tutorial = new TextureRegion(texture, 1586, 1921, 792, 1236);
        toggledOff = new TextureRegion(texture, 1080, 643, 274, 267);
        pause = new TextureRegion(texture, 0, 2995, 792, 338);
        logo = new TextureRegion(texture, 2503, 394, 944, 259);
        kingIcon = new TextureRegion(texture, 3328, 3328, 256, 256);
        ninjaIcon = new TextureRegion(texture, 3584, 3328, 256, 256);
        pirateIcon = new TextureRegion(texture, 3840, 3328, 256, 256);
        selectedIcon = new TextureRegion(texture, 3072, 3328, 256, 256);
        expCell = new TextureRegion(texture, 2560, 896, 128, 128);
        pirateUnlSplash = new TextureRegion(texture, 2816, 3072, 256, 256);
        ninjaUnlSplash = new TextureRegion(texture, 2560, 3072, 256, 256);
        kingUnlSplash = new TextureRegion(texture, 3072, 3072, 256, 256);
        //ranks
        rankStone = new TextureRegion(texture, 2560, 3328, 256, 256);
        rankBronze1 = new TextureRegion(texture, 2560, 3840, 256, 256);
        rankBronze2 = new TextureRegion(texture, 2816, 3840, 256, 256);
        rankBronze3 = new TextureRegion(texture, 3072, 3840, 256, 256);
        TextureRegion[] rankBronze = {rankBronze1, rankBronze2, rankBronze3};
        bronzeAnimation = new Animation(0.9f, rankBronze);
        bronzeAnimation.setPlayMode(Animation.PlayMode.LOOP_RANDOM);
        rankSilver1 = new TextureRegion(texture, 2560, 3584, 256, 256);
        rankSilver2 = new TextureRegion(texture, 2816, 3584, 256, 256);
        rankSilver3 = new TextureRegion(texture, 3072, 3584, 256, 256);
        TextureRegion[] rankSilver = {rankSilver1, rankSilver2, rankSilver3};
        silverAnimation = new Animation(0.9f, rankSilver);
        silverAnimation.setPlayMode(Animation.PlayMode.LOOP_RANDOM);
        rankGold1 = new TextureRegion(texture, 3328, 3840, 256, 256);
        rankGold2 = new TextureRegion(texture, 3584, 3840, 256, 256);
        rankGold3 = new TextureRegion(texture, 3840, 3840, 256, 256);
        TextureRegion[] rankGold = {rankGold1, rankGold2, rankGold3};
        goldAnimation = new Animation(0.9f, rankGold);
        goldAnimation.setPlayMode(Animation.PlayMode.LOOP_RANDOM);
        rankDiamond1 = new TextureRegion(texture, 3328, 3584, 256, 256);
        rankDiamond2 = new TextureRegion(texture, 3584, 3584, 256, 256);
        rankDiamond3 = new TextureRegion(texture, 3840, 3584, 256, 256);
        TextureRegion[] rankDiamond = {rankDiamond1, rankDiamond2, rankDiamond3};
        diamondAnimation = new Animation(0.9f, rankDiamond);
        diamondAnimation.setPlayMode(Animation.PlayMode.LOOP_RANDOM);
    }

    public static void loadGhost() {
        ghost1 = new TextureRegion(texture, 0, 3712, 128, 128);
        ghost2 = new TextureRegion(texture, 128, 3712, 128, 128);
        ghost3 = new TextureRegion(texture, 256, 3712, 128, 128);
        ghostDead = new TextureRegion(texture, 384, 3712, 128, 128);
        ghostSpooking = new TextureRegion(texture, 512, 3712, 128, 128);
    }

    public static void loadKing() {
        ghostking1 = new TextureRegion(texture, 0, 3840, 128, 256);
        ghostking2 = new TextureRegion(texture, 128, 3840, 128, 256);
        ghostking3 = new TextureRegion(texture, 256, 3840, 128, 256);
        ghostkingDead = new TextureRegion(texture, 384, 3840, 128, 256);
        ghostkingSpooking = new TextureRegion(texture, 512, 3840, 128, 256);

    }

    public static void loadNinja() {
        ghostninja1 = new TextureRegion(texture, 896, 3584, 128, 256);
        ghostninja2 = new TextureRegion(texture, 1024, 3584, 128, 256);
        ghostninja3 = new TextureRegion(texture, 1152, 3584, 128, 256);
        ghostninjaDead = new TextureRegion(texture, 1280, 3584, 128, 256);
        ghostninjaSpooking = new TextureRegion(texture, 1408, 3584, 256, 256);

    }

    public static void loadPirate() {
        ghostpirate1 = new TextureRegion(texture, 896, 3840, 128, 256);
        ghostpirate2 = new TextureRegion(texture, 1024, 3840, 128, 256);
        ghostpirate3 = new TextureRegion(texture, 1152, 3840, 128, 256);
        ghostpirateDead = new TextureRegion(texture, 1280, 3840, 128, 256);
        ghostpirateSpooking = new TextureRegion(texture, 1408, 3840, 128, 256);

    }

    public static void loadPolice() {
        ghostpolice1 = new TextureRegion(texture, 1792, 3712, 128, 128);
        ghostpolice2 = new TextureRegion(texture, 1920, 3712, 128, 128);
        ghostpolice3 = new TextureRegion(texture, 2048, 3712, 128, 128);
        ghostpoliceDead = new TextureRegion(texture, 2176, 3712, 128, 128);
        ghostpoliceSpooking = new TextureRegion(texture, 2304, 3712, 128, 128);

    }

    public static void loadAudio() {
        dead = Gdx.audio.newSound(Gdx.files.internal("sounds/dead.wav"));
        jump = Gdx.audio.newSound(Gdx.files.internal("sounds/jump.wav"));
        spook = Gdx.audio.newSound(Gdx.files.internal("sounds/spook.wav"));
        ninjaspook = Gdx.audio.newSound(Gdx.files.internal("sounds/ninja_spook.wav"));
        ninjajump = Gdx.audio.newSound(Gdx.files.internal("sounds/ninja_jump.wav"));
        score = Gdx.audio.newSound(Gdx.files.internal("sounds/score.wav"));
        mobhit = Gdx.audio.newSound(Gdx.files.internal("sounds/mobhit.wav"));
        menuclick1 = Gdx.audio.newSound(Gdx.files.internal("sounds/menuclick1.wav"));
        menuclick2 = Gdx.audio.newSound(Gdx.files.internal("sounds/menuclick2.wav"));
        mobjump = Gdx.audio.newSound(Gdx.files.internal("sounds/jumpingmob_jump.wav"));
        theme = Gdx.audio.newMusic(Gdx.files.internal("sounds/spookyghost_theme.mp3"));
        theme.setVolume(0.5f);
        theme.setLooping(true);
        if (!DataHandler.musicMuted) theme.play();
    }

    private static void loadFont() {
        font = new BitmapFont(Gdx.files.internal("data/font5.fnt"));
        font.getData().setScale(2, 2);
        font2 = new BitmapFont(Gdx.files.internal("data/pixel.fnt"));
        font2.getData().setScale(1f, 1f);
        font3 = new BitmapFont(Gdx.files.internal("data/font5.fnt"));
        font4 = new BitmapFont(Gdx.files.internal("data/font5.fnt"));
        font3.getData().setScale(0.7f, 0.7f);
    }

    public static void dispose() {
        texture.dispose();
        dead.dispose();
        jump.dispose();
        ninjajump.dispose();
        ninjaspook.dispose();
        mobjump.dispose();
        spook.dispose();
        score.dispose();
        mobhit.dispose();
        menuclick1.dispose();
        menuclick2.dispose();
        theme.dispose();
        font.dispose();
        font2.dispose();
        font3.dispose();
        font4.dispose();
    }

    public static TextureRegion getAnimation(float runTime){

        if(DataHandler.selectedCharacter == 0){
            return ghostAnimation.getKeyFrame(runTime);
        } else if(DataHandler.selectedCharacter == 1){
            return ghostPirateAnimation.getKeyFrame(runTime);
        } else if(DataHandler.selectedCharacter == 2){
            return ghostNinjaAnimation.getKeyFrame(runTime);
        } else if(DataHandler.selectedCharacter == 3){
            return ghostKingAnimation.getKeyFrame(runTime);
        }
        return null;
    }

    public static TextureRegion getDeadTexture(){

        if(DataHandler.selectedCharacter == 0){
            return ghostDead;
        } else if(DataHandler.selectedCharacter == 1){
            return ghostpirateDead;
        } else if(DataHandler.selectedCharacter == 2){
            return ghostninjaDead;
        } else if(DataHandler.selectedCharacter == 3){
            return ghostkingDead;
        }
        return null;
    }

    public static TextureRegion getSpookingTexture(){

        if(DataHandler.selectedCharacter == 0){
            return ghostSpooking;
        } else if(DataHandler.selectedCharacter == 1){
            return ghostpirateSpooking;
        } else if(DataHandler.selectedCharacter == 2){
            return ghostninjaSpooking;
        } else if(DataHandler.selectedCharacter == 3){
            return ghostkingSpooking;
        }
        return null;
    }
}


