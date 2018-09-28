package Display;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

/**
 * Created by AlexVR on 7/1/2018.
 */

public class DisplayScreen {

    private static JLabel message;
    private JFrame frame;
    private Canvas canvas;
	private String title;
    private int width, height;
    private Font messageFont;
    private Color backgroundColor;

    public DisplayScreen(String title, int width, int height){
        this.title = title;
        this.width = width;
        this.height = height;
        
        backgroundColor = Color.gray; 

        createDisplay();
    }

    public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
		canvas.setBackground(backgroundColor);
	}

	private void createDisplay(){
		try {
			messageFont = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("res/Font/Evogria.otf"))).deriveFont(Font.PLAIN, 14);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (FontFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	message = new JLabel("Welcome to the snake game!");
    	message.setFont(messageFont);
    	
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
        canvas.setBackground(backgroundColor);

        frame.add(canvas);
        frame.pack();
    }

    public Canvas getCanvas(){
        return canvas;
    }
    
    public static void setMessage(String text) {
		message.setText(text);
	}
    public static void enableMessage() {
    	message.setVisible(true);
    }
    public static void disableMessage() {
    	message.setVisible(false);;
    }

    public JFrame getFrame(){
        return frame;
    }
}
