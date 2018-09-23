package Display;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

import Game.Entities.Dynamic.Player;

/**
 * Created by AlexVR on 7/1/2018.
 */

public class DisplayScreen {

    private JFrame frame;
    private Canvas canvas;
    private static JLabel message;
	private String title;
    private int width, height;
    private Color backgroundColor;

    public DisplayScreen(String title, int width, int height){
        this.title = title;
        this.width = width;
        this.height = height;
        
        backgroundColor = Color.gray; //default background color

        createDisplay();
    }

    public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
		canvas.setBackground(backgroundColor);
	}

	private void createDisplay(){
    	message = new JLabel("Welcome to the snake game!");
    	message.setFont(new Font("Impact", Font.PLAIN, 14));
    	
        frame = new JFrame(title);
        frame.setSize(width, height);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setBackground(Color.black);
        frame.add(message, BorderLayout.SOUTH);

        try {
            frame.setIconImage(ImageIO.read(new File("res/Sheets/icon.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(width, height));
        canvas.setMaximumSize(new Dimension(width, height));
        canvas.setMinimumSize(new Dimension(width, height));
        canvas.setFocusable(false);
        canvas.setBackground(backgroundColor);//changes color snakes background

        frame.add(canvas);
        frame.pack();
    }

    public Canvas getCanvas(){
        return canvas;
    }
    public static void setMessage(String text) {
		message.setText(text);
	}

    public JFrame getFrame(){
        return frame;
    }

}
