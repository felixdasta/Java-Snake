package Game.GameStates;

import java.awt.Color;
import java.awt.Graphics;

import Display.DisplayScreen;
import Main.Handler;
import Resources.Images;
import UI.UIImageButton;
import UI.UIManager;

/**
 * Created by AlexVR on 7/1/2018.
 */
public class GameOverState extends State {

    private UIManager uiManager;

    public GameOverState(Handler handler) {
        super(handler);
        uiManager = new UIManager(handler);
        handler.getMouseManager().setUimanager(uiManager);

        uiManager.addObjects(new UIImageButton((780/2)-(96/2), 540, 96, 48, Images.yes, () -> {
            handler.getMouseManager().setUimanager(null);
            handler.getWorld().player.checkScore();
			handler.getGame().stopAudio();
			handler.getGame().playMainAudioAs("res/music/nature.wav");
			handler.getGame().getDisplay().setBackgroundColor(Color.gray);
			State.setState(handler.getGame().menuState);
			DisplayScreen.setMessage("Welcome to the snake game!");
        }));

        uiManager.addObjects(new UIImageButton((780/2)-(96/2), 600, 96, 48, Images.no, () -> {
            handler.getMouseManager().setUimanager(null);
            handler.getWorld().player.checkScore();
            State.setState(handler.getGame().creditsState);
        }));
    }

    @Override
    public void tick() {
        handler.getMouseManager().setUimanager(uiManager);
        uiManager.tick();
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Images.GameOver,0,0,780,780,null);
        uiManager.Render(g);
    }
}
