package Game.GameStates;


import Main.Handler;
import Resources.Images;
import UI.ClickListlener;
import UI.UIImageButton;
import UI.UIManager;
import java.awt.Graphics;

import java.awt.*;

/**
 * Created by AlexVR on 7/1/2018.
 */
public class MenuState extends State {

    private UIManager uiManager;

    public MenuState(Handler handler) {
        super(handler);
        uiManager = new UIManager(handler);
        handler.getMouseManager().setUimanager(uiManager);
       
       uiManager.addObjects(new UIImageButton(handler.getWidth()/20, handler.getHeight()/20-10, 32, 32, Images.GameInfo, ()-> {
           handler.getMouseManager().setUimanager(null); 
       }));
       
        uiManager.addObjects(new UIImageButton(handler.getWidth()/2-64, handler.getHeight()/2-32, 128, 64, Images.butstart, new ClickListlener() {
            @Override
            public void onClick() {
                handler.getMouseManager().setUimanager(null);
                handler.getGame().reStart();
                State.setState(handler.getGame().gameState);
            }
        }));
    }

    @Override
    public void tick() {
        handler.getMouseManager().setUimanager(uiManager);
        uiManager.tick();

    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.darkGray);
        g.fillRect(0,0,handler.getWidth(),handler.getHeight());
        g.drawImage(Images.title,0,0,handler.getWidth(),handler.getHeight(),null);
        uiManager.Render(g);
        
        g.setColor(Color.white);
        g.drawString("Press:\r\n", handler.getWidth()/10, handler.getHeight()/20);
        g.drawString("P �> Pause game\r\n", handler.getWidth()/10, handler.getHeight()/20 +10);
        g.drawString("N �> adds piece of tail\r\n", handler.getWidth()/10, handler.getHeight()/20 +20);
        g.drawString("+  �> increases speed\r\n", handler.getWidth()/10, handler.getHeight()/20+30);
        g.drawString("-  �> decreases speed\r\n", handler.getWidth()/10, handler.getHeight()/20+40);
    }


}
