package Game.GameStates;

import Main.Handler;
import Resources.Images;
import UI.UIImageButton;
import UI.UIManager;

import java.awt.*;

import Display.DisplayScreen;

/**
 * Created by AlexVR on 7/1/2018.
 */
public class OptionsState extends State {

    private int count = 0;
    private UIManager uiManager;

    public OptionsState(Handler handler) {
        super(handler);
        uiManager = new UIManager(handler);
        handler.getMouseManager().setUimanager(uiManager);

        uiManager.addObjects(new UIImageButton(135, 280, 160, 80, Images.pacmanTheme, () -> {
            handler.getMouseManager().setUimanager(null);
            handler.getWorld().player.setPlayerColor(Color.yellow);
            handler.getWorld().player.setAppleColor(new Color(239, 199, 127));
            handler.getWorld().player.setAppleRoundShape(true);
            handler.getWorld().setLineColor(Color.blue);
            handler.getGame().getDisplay().setBackgroundColor(Color.black);
            handler.getWorld().player.setEatSoundEffect("res/music/pacman_chomp.wav");
            handler.getWorld().player.setDeathSoundEffect("res/music/pacman_death.wav");
            handler.getWorld().player.setAudioLoop(false);
            handler.getGame().stopMainAudio();
            handler.getGame().playMainAudioAs("res/music/pacman_fever.wav");
            State.setState(handler.getGame().pauseState);
        }));
        uiManager.addObjects(new UIImageButton(120, 380, 192, 96, Images.defaultTheme, () -> {
            handler.getMouseManager().setUimanager(null);
            handler.getWorld().player.setPlayerColor(Color.blue);
            handler.getWorld().setLineColor(Color.red);
            handler.getGame().getDisplay().setBackgroundColor(Color.gray);
            handler.getWorld().player.setEatSoundEffect("res/music/bite.wav");
            handler.getWorld().player.setDeathSoundEffect("res/music/evil morty.wav");
            handler.getWorld().player.setAudioLoop(true);
            handler.getGame().stopMainAudio();
            handler.getGame().playMainAudioAs("res/music/nature.wav");
            State.setState(handler.getGame().pauseState);
        }));
        uiManager.addObjects(new UIImageButton(183, 500, 69, 27, Images.Back, () -> {
            handler.getMouseManager().setUimanager(null);
            State.setState(handler.getGame().pauseState);
        }));
        uiManager.addObjects(new UIImageButton(320, 300, 128, 64, Images.Mute, () -> {
            handler.getMouseManager().setUimanager(null);
            handler.getGame().stopMainAudio();
            }));
    }
    
    @Override
    public void tick() {
        handler.getMouseManager().setUimanager(uiManager);
        uiManager.tick();
        count++;
        if( count>=30){
            count=30;
        }
        if(handler.getKeyManager().pbutt && count>=30){
            count=0;

            State.setState(handler.getGame().gameState);
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Images.Pause,0,0,780,780,null);
        uiManager.Render(g);

    }
}
