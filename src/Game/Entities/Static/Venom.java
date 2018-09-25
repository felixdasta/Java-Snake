package Game.Entities.Static;

import Main.Handler;

public class Venom {

    private Handler handler;

    public int xCoord;
    public int yCoord;

    public Venom(Handler handler,int x, int y){
        this.handler=handler;
        this.xCoord=x;
        this.yCoord=y;
    }

    
}
