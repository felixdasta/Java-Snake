package Game.GameStates;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import Display.DisplayScreen;
import Main.Handler;
import Resources.Images;
import UI.UIImageButton;
import UI.UIManager;

public class CreditsState extends State{

    private int timer = 0;
    private int yPosition = 0;
    private int creditsInitialPosition = handler.getHeight() + 100;
    private UIManager uiManager;

    public CreditsState(Handler handler) {
        super(handler);
        uiManager = new UIManager(handler);
        handler.getMouseManager().setUimanager(uiManager);
    }

    @Override
    public void tick() {
        handler.getMouseManager().setUimanager(uiManager);
        uiManager.tick();
    }
	@Override
    public void render(Graphics g) {
    	handler.getGame().getDisplay().setBackgroundColor(Color.black);
        DisplayScreen.setMessage("Thank you for playing Java Snake!");
    	g.setColor(Color.white);
        g.setFont(new Font("Impact", Font.PLAIN, 14));
        g.drawString("Credits:", handler.getWidth()/4, creditsInitialPosition + 10 - yPosition);
        g.drawString("Song used in the Pac-Man® Theme: Pac-Man Fever by Buckner & Garcia", handler.getWidth()/4,  creditsInitialPosition + 30 - yPosition);
        g.drawString("Song used in the default game over state: For the Damaged Coda by Blonde RedHead", handler.getWidth()/4,  creditsInitialPosition + 50 - yPosition);
        g.drawString("Game editors:", handler.getWidth()/4, creditsInitialPosition +  80 - yPosition);
        g.drawString("Félix Dasta", handler.getWidth()/4, creditsInitialPosition +  98 - yPosition);
        g.drawString("Ashley Johan", handler.getWidth()/4, creditsInitialPosition +  116 - yPosition);
        g.drawString("Special thanks to Josian A. Velez Ramos, who developed the snake game", handler.getWidth()/4, creditsInitialPosition +  146 - yPosition);
        g.drawString("NOTE: All of the sound clips were borrowed for educational purposes only", handler.getWidth()/4, creditsInitialPosition +  176 - yPosition);
        uiManager.Render(g);
        if(yPosition < handler.getHeight() - 100) {
            yPosition++;
            try {
				Thread.sleep(1000/90);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }else {
        	while(timer < 30) {
        		timer++;
        		try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
        	System.exit(0);
        }
	}
}


