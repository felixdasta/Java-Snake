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
public class PauseState extends State {

    private int count = 0;
    private UIManager uiManager;

    public PauseState(Handler handler) {
        super(handler);
        uiManager = new UIManager(handler);
        handler.getMouseManager().setUimanager(uiManager);

        uiManager.addObjects(new UIImageButton(56, 223, 128, 64, Images.Resume, () -> {
            handler.getMouseManager().setUimanager(null);
            State.setState(handler.getGame().gameState);
        }));

        uiManager.addObjects(new UIImageButton(56, 223+(64+16), 128, 64, Images.Options, () -> {
            handler.getMouseManager().setUimanager(null);
            State.setState(handler.getGame().optionsState);
        }));

        uiManager.addObjects(new UIImageButton(56, (223+(64+16))+(64+16), 128, 64, Images.BTitle, () -> {
            handler.getMouseManager().setUimanager(null);
            handler.getWorld().player.checkScore();
            handler.getGame().getDisplay().setBackgroundColor(Color.gray);
            handler.getGame().stopMainAudio();
            handler.getGame().setMainAudioAs("res/music/nature.wav");
            handler.getGame().playMainAudio();
            State.setState(handler.getGame().menuState);
            DisplayScreen.setMessage("Welcome to the snake game!");
        }));
        
        uiManager.addObjects(new UIImageButton(56 + (225+64+16), 223+(64+16), 128, 64, Images.Mute, () -> {
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
