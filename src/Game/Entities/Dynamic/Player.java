package Game.Entities.Dynamic;

import java.awt.Color;
import java.awt.Graphics;						
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

import Display.DisplayScreen;
import Game.GameStates.State;
import Main.Handler;
import Resources.Images;

/**
 * Created by AlexVR on 7/2/2018.
 */
public class Player {
    private DisplayScreen display;

    public int length;
    private boolean justAte;
    private boolean ateVenom;
    private boolean soundLoop;
    private boolean appleRoundShape;
    private Handler handler;
    private Tail tail;
    private int score;
	public int xCoord;
    public int yCoord;
    public int moveCounter;
    private int speedControl;
    private Color playerColor;
    private Color appleColor;
    private String eatSoundEffect;
    private String deathSoundEffect;
    private String highScore;
    public String direction;

    public Player(Handler handler){
        this.handler = handler;
        score = 0;
        xCoord = 0;
        yCoord = 0;
        moveCounter = 0;
        speedControl = 5;
        playerColor = Color.green;
        appleColor = Color.red;
        eatSoundEffect = "res/music/bite.wav";
        deathSoundEffect = "res/music/evil morty.wav";
        highScore = this.getHighScore();
        direction= "Right";
        justAte = false;
        soundLoop = true;
        length= 1;
        
        ateVenom = false;
    }

    public void tick(){
        moveCounter += 2;
        if(moveCounter>=speedControl) {
            checkCollisionAndMove();
            moveCounter=0;
        }
        if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_UP) || handler.getKeyManager().keyJustPressed(KeyEvent.VK_W)){
            if(handler.getWorld().body.isEmpty())
            	direction = "Up";
            else if(!direction.equals("Down") && handler.getWorld().body.size() > 0)
            	direction="Up";
        }else if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_DOWN) || handler.getKeyManager().keyJustPressed(KeyEvent.VK_S)){
            if(handler.getWorld().body.isEmpty())
            	direction="Down";
            else if(!direction.equals("Up") && handler.getWorld().body.size() > 0)
            	direction="Down";
        }else if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_LEFT) || handler.getKeyManager().keyJustPressed(KeyEvent.VK_A)){
        	if(handler.getWorld().body.isEmpty())
        		direction = "Left";
        	else if(!direction.equals("Right") && handler.getWorld().body.size() > 0)
            	direction="Left";
        }else if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_RIGHT) || handler.getKeyManager().keyJustPressed(KeyEvent.VK_D)){
        	if(handler.getWorld().body.isEmpty())
        		direction = "Right";
        	else if(!direction.equals("Left") && handler.getWorld().body.size() > 0)
            	direction="Right";
        }
        if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_N)) {
        	this.setJustAte(false);
        	addTail();
        }
        if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_P)){
        	State.setState(handler.getGame().pauseState);;
        }
        if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_EQUALS)){
        	speedControl-=2;
        }
        if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_MINUS)){
        	speedControl+=2;
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
                }
                else{
                    xCoord--;
                }
                break;
            case "Right":
                if(xCoord==handler.getWorld().GridWidthHeightPixelCount-1){
                    kill();
                }
                else{
                    xCoord++;
                }
                break;
            case "Up":
                if(yCoord==0){
                    kill();
                }
                else{
                    yCoord--;
                }
                break;
            case "Down":
                if(yCoord==handler.getWorld().GridWidthHeightPixelCount-1){
                    kill();
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
        
//        if(handler.getWorld().venomLocation[xCoord +10][yCoord +10]) {
//        	this.setAteVenom(true);
//        	speedControl--;
//        }
        if(handler.getWorld().appleLocation[xCoord][yCoord]){
            this.setJustAte(true);
            addTail();
        }
        if((xCoord > 0 || yCoord > 0) || justAte==true){
        	DisplayScreen.setMessage(String.format("Your current score: %d; %s", score, highScore)); //Displays the score on the bottom as soon as the game starts and gets updated whenever the snake eats a dot
        }

        if(!handler.getWorld().body.isEmpty()) {//apple add tail
            handler.getWorld().playerLocation[handler.getWorld().body.getLast().x][handler.getWorld().body.getLast().y] = false;
            handler.getWorld().body.removeLast();
            handler.getWorld().body.addFirst(new Tail(x, y,handler));
        }
    }
    
    public void setJustAte(boolean justAte) {
        this.justAte = justAte;
    }
    public void setAteVenom(boolean ateVenom) {
        this.ateVenom = ateVenom;
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
	
	public void setAppleColor(Color appleColor) {
		this.appleColor = appleColor;
	}

	public void setAudioLoop(boolean toLoop) {
		this.soundLoop = toLoop;
	}

	public void render(Graphics g,Boolean[][] playerLocation){
		
		// changes snake color
        for (int i = 0; i < handler.getWorld().GridWidthHeightPixelCount; i++) {
            for (int j = 0; j < handler.getWorld().GridWidthHeightPixelCount; j++) {
                g.setColor(playerColor);

                if(playerLocation[i][j]){
                    g.fillRect((i*handler.getWorld().GridPixelsize),
                            (j*handler.getWorld().GridPixelsize),
                            handler.getWorld().GridPixelsize,
                            handler.getWorld().GridPixelsize);
                }
            }
        }
       
        // changes apple color
        
        for (int i = 0; i < handler.getWorld().GridWidthHeightPixelCount; i++) {
            for (int j = 0; j < handler.getWorld().GridWidthHeightPixelCount; j++) {
                g.setColor(appleColor);

                if(handler.getWorld().appleLocation[i][j]){
                	if (appleRoundShape){
                        g.fillOval((i*handler.getWorld().GridPixelsize),
                                (j*handler.getWorld().GridPixelsize),
                                handler.getWorld().GridPixelsize,
                                handler.getWorld().GridPixelsize);
                	}else{
                        g.fillRect((i*handler.getWorld().GridPixelsize),
                                (j*handler.getWorld().GridPixelsize),
                                handler.getWorld().GridPixelsize,
                                handler.getWorld().GridPixelsize);
                	}
                }
            }
        }
        
        //changes venom color
        
//        for (int i = 0; i < handler.getWorld().GridWidthHeightPixelCount; i++) {
//            for (int j = 0; j < handler.getWorld().GridWidthHeightPixelCount; j++) {
//                g.setColor(Color.yellow);
//
//                if(handler.getWorld().venomLocation[i][j]){
//                    g.fillRect((i*handler.getWorld().GridPixelsize),
//                            (j*handler.getWorld().GridPixelsize),
//                            handler.getWorld().GridPixelsize,
//                            handler.getWorld().GridPixelsize);
//                }
//            }
//        }
    }

    public void setAppleRoundShape(boolean appleRoundShape) {
		this.appleRoundShape = appleRoundShape;
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
        	handler.getWorld().body.addLast(new Tail(xCoord, yCoord, handler));
        }
    }
    
    public String getHighScore(){
    	BufferedReader reader = null;
    	try{
    		reader = new BufferedReader(new FileReader("highscore.dat"));
    		return reader.readLine();
    	}catch(Exception e){
    		return "highest score: 0 (by nobody)";
    	}
    	finally{
    		try {
				if(reader != null){
					reader.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    }
    
    public void checkScore(){
    	if(score > Integer.parseInt(highScore.substring(highScore.indexOf(":") + 2, highScore.indexOf("(")-1))){
    		if(soundLoop){
    			handler.getGame().stopMainAudio();
    			handler.getGame().playAudio("res/music/cheering.wav");
    		}else{
    			handler.getGame().playAudio("res/music/cheering.wav");
    		}
    		String name = JOptionPane.showInputDialog(null, "Congratulations! You set a new high score.\nPlease enter your name: ");
    		highScore = String.format("highest score: %d (by %s)", score, name); 
    		
    		File scoreFile = new File("highscore.dat");
    		BufferedWriter writer;
    		if(!scoreFile.exists()){
    				try {
						scoreFile.createNewFile();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}			
    		}
			try {
				writer = new BufferedWriter(new FileWriter(scoreFile));
				writer.write(highScore);
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    }

    public void kill(){
        length = 0;
        handler.getGame().stopMainAudio();
        if(soundLoop){ 
        	handler.getGame().setMainAudioAs(deathSoundEffect);  
        	handler.getGame().playMainAudio(); 
        }else{ 
        	handler.getGame().playAudio(deathSoundEffect); 
        } 
        int gameOver = JOptionPane.showConfirmDialog(null, "Sorry snake! The game is over.\nDo you want to return to the menu?","Game Over", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, Images.GameOverIcon);
        if (gameOver == JOptionPane.YES_OPTION) {
            checkScore();
            handler.getGame().stopMainAudio();
            handler.getGame().setMainAudioAs("res/music/nature.wav");
            handler.getGame().playMainAudio();
            handler.getGame().getDisplay().setBackgroundColor(Color.gray);
        	State.setState(handler.getGame().menuState);
        }else{
            checkScore();
            for (int i = 0; i < handler.getWorld().GridWidthHeightPixelCount; i++) {
                for (int j = 0; j < handler.getWorld().GridWidthHeightPixelCount; j++) {
                	handler.getWorld().playerLocation[i][j]=false;
                }
            }
        	System.exit(0);
        }
    }
}
