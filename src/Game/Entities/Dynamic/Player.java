package Game.Entities.Dynamic;

import Main.Handler;
import Resources.Images;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Random;

import javax.swing.JOptionPane;

import Display.DisplayScreen;
import Game.GameStates.PauseState;

/**
 * Created by AlexVR on 7/2/2018.
 */
public class Player {

    public int length;
    private boolean justAte;
    private boolean toLoop;
    private Handler handler;
    private Tail tail;
    private int score;
	public int xCoord;
    public int yCoord;
    public int moveCounter;
    private int speedControl;
    private Color playerColor;
    private String lastDirection;
    private String eatSoundEffect;
    private String deathSoundEffect;
    public String direction;

    public Player(Handler handler){
        this.handler = handler;
        score = 0;
        xCoord = 0;
        yCoord = 0;
        moveCounter = 0;
        speedControl = 5;
        playerColor = Color.blue;
        eatSoundEffect = "res/music/bite.wav";
        deathSoundEffect = "res/music/evil morty.wav";
        direction= "Right";
        justAte = false;
        toLoop = true;
        length= 1;
    }

    public void tick(){
        moveCounter += 2;
        if(moveCounter>=speedControl) {
            checkCollisionAndMove();
            moveCounter=0;
        }
        if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_UP)){
        	lastDirection = direction;
            direction="Up";
        }else if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_DOWN)){
        	lastDirection = direction;
            direction="Down";
        }else if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_LEFT)){
        	lastDirection = direction;
            direction="Left";
        }else if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_RIGHT)){
        	lastDirection = direction;
            direction="Right";
        }
        if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_N)) {
        	this.setJustAte(false);
        	addTail();
        }
        if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_P)){
        	handler.getGame().gameState.setState(handler.getGame().pauseState);;
        }
        if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_EQUALS)){
        	speedControl--;
        }
        if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_MINUS)){
        	speedControl++;
        }
    }

    public void checkCollisionAndMove(){
        handler.getWorld().playerLocation[xCoord][yCoord]=false;
        int x = xCoord;
        int y = yCoord;
        switch (direction){
            case "Left":
                if(xCoord==0){
                    kill();
                }else if(handler.getWorld().body.size() > 0 && lastDirection == "Right"){
                	direction = "Right";
                	xCoord++;
                }
                else{
                    xCoord--;
                }
                break;
            case "Right":
                if(xCoord==handler.getWorld().GridWidthHeightPixelCount-1){
                    kill();
                }else if(handler.getWorld().body.size() > 0 && lastDirection == "Left"){
                	direction = "Left";
                	xCoord--;
                }
                else{
                    xCoord++;
                }
                break;
            case "Up":
                if(yCoord==0){
                    kill();
                }else if(handler.getWorld().body.size() > 0 && lastDirection == "Down"){
                	direction = "Down";
                	yCoord++;
                }
                else{
                    yCoord--;
                }
                break;
            case "Down":
                if(yCoord==handler.getWorld().GridWidthHeightPixelCount-1){
                    kill();
                }else if(handler.getWorld().body.size() > 0 && lastDirection == "Up"){
                	direction = "Up";
                	yCoord--;
                }
                else{
                    yCoord++;
                }
                break;
        }
        handler.getWorld().playerLocation[xCoord][yCoord]=true;
        
        if (handler.getWorld().body.size() > 0) {
        	for (int i = 0; i < handler.getWorld().body.size(); i++){
        		if(xCoord == handler.getWorld().body.get(i).x && yCoord == handler.getWorld().body.get(i).y) {
        			kill();
        		}  // when player collides with itself
        	}
        }
        
        if(handler.getWorld().appleLocation[xCoord][yCoord]){
            this.setJustAte(true);
            addTail();
        }
        if((xCoord > 0 || yCoord > 0) || justAte==true){
        	DisplayScreen.setMessage(String.format("Score: %d", score)); //Displays the score on the bottom as soon as the game starts and gets updated whenever the snake eats a dot
        }

        if(!handler.getWorld().body.isEmpty()) {//apple add tail
            handler.getWorld().playerLocation[handler.getWorld().body.getLast().x][handler.getWorld().body.getLast().y] = false;
            handler.getWorld().body.removeLast();
            handler.getWorld().body.addFirst(new Tail(x, y,handler));
        }
        
    }
	public void setEatSoundEffect(String eatSoundEffect) {
		this.eatSoundEffect = eatSoundEffect;
	}

	public void setDeathSoundEffect(String deathSoundEffect) {
		this.deathSoundEffect = deathSoundEffect;
	}

	public void setPlayerColor(Color playerColor) {
		this.playerColor = playerColor;
	}

	public void setAudioLoop(boolean toLoop) {
		this.toLoop = toLoop;
	}

	public void render(Graphics g,Boolean[][] playerLocation){
        Random r = new Random();
        for (int i = 0; i < handler.getWorld().GridWidthHeightPixelCount; i++) {
            for (int j = 0; j < handler.getWorld().GridWidthHeightPixelCount; j++) {
                g.setColor(playerColor);// changes snake and apple color

                if(playerLocation[i][j]||handler.getWorld().appleLocation[i][j]){
                    g.fillRect((i*handler.getWorld().GridPixelsize),
                            (j*handler.getWorld().GridPixelsize),
                            handler.getWorld().GridPixelsize,
                            handler.getWorld().GridPixelsize);
                }

            }
        }


    }

    public void addTail(){
        length++;
        if (justAte==true){
        	score+=100;
        	handler.getGame().playAudio(eatSoundEffect);
        	handler.getWorld().appleLocation[xCoord][yCoord]=false;
        	handler.getWorld().appleOnBoard=false;
        switch (direction){
            case "Left":
                if( handler.getWorld().body.isEmpty()){
                    if(this.xCoord!=handler.getWorld().GridWidthHeightPixelCount-1){
                        tail = new Tail(this.xCoord+1,this.yCoord,handler);
                    }else{
                        if(this.yCoord!=0){
                            tail = new Tail(this.xCoord,this.yCoord-1,handler);
                        }else{
                            tail =new Tail(this.xCoord,this.yCoord+1,handler);
                        }
                    }
                }else{
                    if(handler.getWorld().body.getLast().x!=handler.getWorld().GridWidthHeightPixelCount-1){
                        tail=new Tail(handler.getWorld().body.getLast().x+1,this.yCoord,handler);
                    }else{
                        if(handler.getWorld().body.getLast().y!=0){
                            tail=new Tail(handler.getWorld().body.getLast().x,this.yCoord-1,handler);
                        }else{
                            tail=new Tail(handler.getWorld().body.getLast().x,this.yCoord+1,handler);

                        }
                    }
                }
                break;
            case "Right":
                if( handler.getWorld().body.isEmpty()){
                    if(this.xCoord!=0){
                        tail=new Tail(this.xCoord-1,this.yCoord,handler);
                    }else{
                        if(this.yCoord!=0){
                            tail=new Tail(this.xCoord,this.yCoord-1,handler);
                        }else{
                            tail=new Tail(this.xCoord,this.yCoord+1,handler);
                        }
                    }
                }else{
                    if(handler.getWorld().body.getLast().x!=handler.getWorld().GridWidthHeightPixelCount-1){
                        tail=(new Tail(handler.getWorld().body.getLast().x-1,this.yCoord,handler));
                    }else{
                        if(handler.getWorld().body.getLast().y!=0){
                            tail=(new Tail(handler.getWorld().body.getLast().x,this.yCoord-1,handler));
                        }else{
                            tail=(new Tail(handler.getWorld().body.getLast().x,this.yCoord+1,handler));
                        }
                    }

                }
                break;
            case "Up":
                if( handler.getWorld().body.isEmpty()){
                    if(this.yCoord!=handler.getWorld().GridWidthHeightPixelCount-1){
                        tail=(new Tail(this.xCoord,this.yCoord+1,handler));
                    }else{
                        if(this.xCoord!=0){
                            tail=(new Tail(this.xCoord-1,this.yCoord,handler));
                        }else{
                            tail=(new Tail(this.xCoord+1,this.yCoord,handler));
                        }
                    }
                }else{
                    if(handler.getWorld().body.getLast().y!=handler.getWorld().GridWidthHeightPixelCount-1){
                        tail=(new Tail(handler.getWorld().body.getLast().x,this.yCoord+1,handler));
                    }else{
                        if(handler.getWorld().body.getLast().x!=0){
                            tail=(new Tail(handler.getWorld().body.getLast().x-1,this.yCoord,handler));
                        }else{
                            tail=(new Tail(handler.getWorld().body.getLast().x+1,this.yCoord,handler));
                        }
                    }

                }
                break;
            case "Down":
                if( handler.getWorld().body.isEmpty()){
                    if(this.yCoord!=0){
                        tail=(new Tail(this.xCoord,this.yCoord-1,handler));
                    }else{
                        if(this.xCoord!=0){
                            tail=(new Tail(this.xCoord-1,this.yCoord,handler));
                        }else{
                            tail=(new Tail(this.xCoord+1,this.yCoord,handler));
                        }
                    }
                }else{
                    if(handler.getWorld().body.getLast().y!=handler.getWorld().GridWidthHeightPixelCount-1){
                        tail=(new Tail(handler.getWorld().body.getLast().x,this.yCoord-1,handler));
                    }else{
                        if(handler.getWorld().body.getLast().x!=0){
                            tail=(new Tail(handler.getWorld().body.getLast().x-1,this.yCoord,handler));
                        }else{
                            tail=(new Tail(handler.getWorld().body.getLast().x+1,this.yCoord,handler));
                        }
                    }
                }
                break;
        }
        handler.getWorld().body.addLast(tail);
        handler.getWorld().playerLocation[tail.x][tail.y] = true;
        }else{
        	handler.getWorld().body.addFirst(new Tail(xCoord, yCoord, handler));
        }
    }

    public void kill(){
        length = 0;
        for (int i = 0; i < handler.getWorld().GridWidthHeightPixelCount; i++) {
            for (int j = 0; j < handler.getWorld().GridWidthHeightPixelCount; j++) {

                handler.getWorld().playerLocation[i][j]=false;
                handler.getGame().stopMainAudio();
                if(toLoop){
                	handler.getGame().setMainAudioAs(deathSoundEffect); 
                	handler.getGame().playMainAudio();
                }else{
                	handler.getGame().playAudio(deathSoundEffect);
                }
                int gameOver = JOptionPane.showConfirmDialog(null, "Sorry snake! The game is over...","Game Over", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, Images.GameOverIcon);
                System.exit(0);
                
            }
        }
    }
    public int getScore() {
		return score;
	}

    public boolean isJustAte() {
        return justAte;
    }

    public void setJustAte(boolean justAte) {
        this.justAte = justAte;
    }
}
