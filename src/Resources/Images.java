package Resources;

import java.awt.image.BufferedImage;
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
    public static BufferedImage GameOver;
    public static BufferedImage OptionsState;
    public static BufferedImage[] Resume;
    public static BufferedImage[] yes;
    public static BufferedImage[] no;
    public static BufferedImage[] BTitle;
    public static BufferedImage[] Options;
    public static BufferedImage[] Back;
    public static BufferedImage[] pacmanTheme;
    public static BufferedImage[] defaultTheme;
    public static BufferedImage[] MuteBM;
    public static BufferedImage[] MuteSE;
    public static BufferedImage[] UnmuteBM;
    public static BufferedImage[] UnmuteSE;
    public static BufferedImage[] GameInfo;

    public static ImageIcon icon;
    public static ImageIcon CelebrationIcon;

    public Images() {

        butstart = new BufferedImage[3];
        Resume = new BufferedImage[2];
        yes = new BufferedImage[2];
        no = new BufferedImage[2];
        BTitle = new BufferedImage[2];
        Options = new BufferedImage[2];
        Back = new BufferedImage[2];
        pacmanTheme = new BufferedImage[2];
        defaultTheme = new BufferedImage[2];
        MuteBM = new BufferedImage[2];
        MuteSE = new BufferedImage[2];
        UnmuteBM = new BufferedImage[2];
        UnmuteSE = new BufferedImage[2];
        GameInfo = new BufferedImage[2];
        
        
        try {

            title = ImageIO.read(getClass().getResourceAsStream("/Sheets/Title.png"));
            Pause = ImageIO.read(getClass().getResourceAsStream("/Buttons/Pause.png"));
            GameOver =  ImageIO.read(getClass().getResourceAsStream("/Sheets/GameOver.png"));
            OptionsState = ImageIO.read(getClass().getResourceAsStream("/Buttons/OptionsState.png"));
            Resume[0] = ImageIO.read(getClass().getResourceAsStream("/Buttons/Resume.png"));
            Resume[1] = ImageIO.read(getClass().getResourceAsStream("/Buttons/ResumeP.png"));
            yes[0] = ImageIO.read(getClass().getResourceAsStream("/Buttons/yesBut.png"));
            yes[1] = ImageIO.read(getClass().getResourceAsStream("/Buttons/yesBut_P.png"));
            no[0] = ImageIO.read(getClass().getResourceAsStream("/Buttons/noBut.png"));
            no[1] = ImageIO.read(getClass().getResourceAsStream("/Buttons/noBut_P.png"));
            BTitle[0] = ImageIO.read(getClass().getResourceAsStream("/Buttons/BTitle.png"));
            BTitle[1] = ImageIO.read(getClass().getResourceAsStream("/Buttons/BTitleP.png"));
            Options[0] = ImageIO.read(getClass().getResourceAsStream("/Buttons/Options.png"));
            Options[1] = ImageIO.read(getClass().getResourceAsStream("/Buttons/OptionsP.png"));
            butstart[0]= ImageIO.read(getClass().getResourceAsStream("/Buttons/NormBut.png"));//normbut
            butstart[1]= ImageIO.read(getClass().getResourceAsStream("/Buttons/HoverBut.png"));//hoverbut
            butstart[2]= ImageIO.read(getClass().getResourceAsStream("/Buttons/ClickedBut.png"));//clickbut
            pacmanTheme[0] = ImageIO.read(getClass().getResourceAsStream("/Buttons/Pacman.png"));
            pacmanTheme[1] = ImageIO.read(getClass().getResourceAsStream("/Buttons/PacmanP.png"));
            defaultTheme[0] = ImageIO.read(getClass().getResourceAsStream("/Buttons/DefaultTheme.png"));
            defaultTheme[1] = ImageIO.read(getClass().getResourceAsStream("/Buttons/DefaultThemeP.png"));
            Back[0] = ImageIO.read(getClass().getResourceAsStream("/Buttons/Back.png"));
            Back[1] = ImageIO.read(getClass().getResourceAsStream("/Buttons/BackP.png"));

            GameInfo[0] = ImageIO.read(getClass().getResourceAsStream("/Buttons/gameInfo1.png"));
            GameInfo[1] = ImageIO.read(getClass().getResourceAsStream("/Buttons/gameInfo1.png"));
                  
            MuteBM[0] = ImageIO.read(getClass().getResourceAsStream("/Buttons/MuteBM.png"));
            MuteBM[1] = ImageIO.read(getClass().getResourceAsStream("/Buttons/MuteBM_P.png"));
            MuteSE[0] = ImageIO.read(getClass().getResourceAsStream("/Buttons/MuteSE.png"));
            MuteSE[1] = ImageIO.read(getClass().getResourceAsStream("/Buttons/MuteSE_P.png"));
            UnmuteBM[0] = ImageIO.read(getClass().getResourceAsStream("/Buttons/UnmuteBM.png"));
            UnmuteBM[1] = ImageIO.read(getClass().getResourceAsStream("/Buttons/UnmuteBM_P.png"));
            UnmuteSE[0] = ImageIO.read(getClass().getResourceAsStream("/Buttons/UnmuteSE.png"));
            UnmuteSE[1] = ImageIO.read(getClass().getResourceAsStream("/Buttons/UnmuteSE_P.png"));
            
            icon =  new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/Sheets/icon.png")));
            CelebrationIcon = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/Sheets/Celebrate.png")));


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
