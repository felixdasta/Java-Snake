package Resources;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * Created by AlexVR on 7/1/2018.
 */
public class Images {


    public static BufferedImage[] butstart;
    public static BufferedImage title;
    public static BufferedImage Pause;
    public static BufferedImage[] Resume;
    public static BufferedImage[] BTitle;
    public static BufferedImage[] Options;
    public static BufferedImage[] Back;
    public static BufferedImage[] pacmanTheme;
    public static BufferedImage[] defaultTheme;
    public static ImageIcon icon;
    public static ImageIcon GameOverIcon;
    
    public static BufferedImage[] Mute;

    public Images() {

        butstart = new BufferedImage[3];
        Resume = new BufferedImage[2];
        BTitle = new BufferedImage[2];
        Options = new BufferedImage[2];
        Back = new BufferedImage[2];
        pacmanTheme = new BufferedImage[2];
        defaultTheme = new BufferedImage[2];
        Mute = new BufferedImage[2];

        
        try {

            title = ImageIO.read(new File("res/Sheets/Title.png"));
            Pause = ImageIO.read(getClass().getResourceAsStream("/Buttons/Pause.png"));
            Resume[0] = ImageIO.read(getClass().getResourceAsStream("/Buttons/Resume.png"));
            Resume[1] = ImageIO.read(getClass().getResourceAsStream("/Buttons/ResumeP.png"));
            BTitle[0] = ImageIO.read(getClass().getResourceAsStream("/Buttons/BTitle.png"));
            BTitle[1] = ImageIO.read(getClass().getResourceAsStream("/Buttons/BTitleP.png"));
            Options[0] = ImageIO.read(getClass().getResourceAsStream("/Buttons/Options.png"));
            Options[1] = ImageIO.read(getClass().getResourceAsStream("/Buttons/OptionsP.png"));
            butstart[0]= ImageIO.read(getClass().getResourceAsStream("/Buttons/NormBut.png"));//normbut
            butstart[1]= ImageIO.read(getClass().getResourceAsStream("/Buttons/HoverBut.png"));//hoverbut
            butstart[2]= ImageIO.read(getClass().getResourceAsStream("/Buttons/ClickedBut.png"));//clickbut
            pacmanTheme[0] = ImageIO.read(new File("res/Buttons/Pacman.png"));
            pacmanTheme[1] = ImageIO.read(new File("res/Buttons/PacmanP.png"));
            defaultTheme[0] = ImageIO.read(new File("res/Buttons/DefaultTheme.png"));
            defaultTheme[1] = ImageIO.read(new File("res/Buttons/DefaultThemeP.png"));
            Back[0] = ImageIO.read(new File("res/Buttons/Back.png"));
            Back[1] = ImageIO.read(new File("res/Buttons/BackP.png"));
            
            Mute[0] = ImageIO.read(getClass().getResourceAsStream("/Buttons/Mute.png"));
            Mute[1] = ImageIO.read(getClass().getResourceAsStream("/Buttons/MuteP.png"));
            
            icon =  new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/Sheets/icon.png")));
            GameOverIcon =  new ImageIcon("res/Sheets/GameOverIcon.png");


        }catch (IOException e) {
        e.printStackTrace();
    }


    }

    public static BufferedImage loadImage(String path) {
        try {
            return ImageIO.read(Images.class.getResourceAsStream(path));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }

}
