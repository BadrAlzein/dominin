package de.btu.swp.dominion.gameLogic;

import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.AudioClip;
import java.awt.Toolkit;

public class GuiDesigner {

    private final static int widthOld = 1600;
    private final static int heightOld = 900;
    private static int widthNew = Toolkit.getDefaultToolkit().getScreenSize().width;
    private static int heightNew = Toolkit.getDefaultToolkit().getScreenSize().height;
    private static boolean fullScreen = false;
    // Music
    private String musicPath = "/src/main/resources/music/DominionNewGen.mp3";
    private AudioClip backgroundMusic = new AudioClip(GuiDesigner.class.getResource(musicPath).toString());
    // Game End Music
    private String gameEndMusicPath = "/src/main/resources/music/GameOverSound.mp3";
    private AudioClip GameEndMusic = new AudioClip(GuiDesigner.class.getResource(gameEndMusicPath).toString());

    private Image volumeOn = new Image("src/main/resources/icons/icons8-audio.png");
    private Image volumeOff = new Image("src/main/resources/icons/icons8-no_audio.png");
    private Boolean isMusicOn = true;

    public static void setWidth(int width) {
        widthNew = width;
    }

    public static int getWidth() {
        return widthNew;
    }

    public static void setHeight(int height) {
        heightNew = height;
    }

    public static int getHeight() {
        return heightNew;
    }

    public static void setFullScreen(boolean screen) {
        fullScreen = screen;
    }

    public static boolean getFullScreen() {
        return fullScreen;
    }

    public static void scaling(BorderPane mainBorder) {
        mainBorder.setScaleX(((double) widthNew) / widthOld);
        mainBorder.setScaleY(((double) heightNew) / heightOld);

        mainBorder.setTranslateX(((double) (widthNew - widthOld)) / 2);
        mainBorder.setTranslateY(((double) (heightNew - heightOld)) / 2);
    }

    public AudioClip getBackgroundAudio() {
        return backgroundMusic;
    }

    public AudioClip getGameEndAudio() {
        return GameEndMusic;
    }

    public Image getVolumOffImg() {
        return volumeOff;
    }

    public Image getVolumOnImg() {
        return volumeOn;
    }

    public boolean getIsMusicOn() {
        return this.isMusicOn;
    }

}