import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;
import java.io.*;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import javax.sound.sampled.*;

//import javafx.scene.media.Media;
//import javafx.scene.media.MediaPlayer;
class Sound {
	public static void playSound(String name) {
		try {
			Clip clip = AudioSystem.getClip();
			AudioInputStream is = AudioSystem.getAudioInputStream( new File(name));
			clip.open(is);
			clip.start();
		} catch(Exception e) {
		}
	}
	public static void backGround(String name) {
		try {
			Clip clip = AudioSystem.getClip();
			AudioInputStream ls = AudioSystem.getAudioInputStream( new File(name));
			clip.open(ls);
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		}catch(Exception e) {
			System.out.print(e);
		}
	}
}

class Basic extends JComponent implements KeyListener{
	private int height = 20;
	private int width = 10;
	private int grid[][];
	private int score = 0;
	private boolean sticking;
	private int currX;
	private int currY;
	private int currConfig[];
	private boolean lost;
	private int place;
	private Block currentBlock;
	private BufferedWriter out;
	private FileReader in;
	//private MediaPlayer mediaPlayer;
	private int speed;

	//Select a block type
	private Block selectBlock() {
		Random typeGen = new Random();
		int bl = typeGen.nextInt(7);
		//bl = 1;
		if(bl == 0) {
			System.out.println("Line");
			return new Line();
		}else if(bl == 1) {
			System.out.println("Box");
			return new Box();
		}else if(bl == 2) {
			System.out.println("CrossT");
			return new CrossT();
		}else if(bl == 3) {
			System.out.println("LeftL");
			return new LeftL();
		}else if(bl == 4) {
			System.out.println("RightL");
			return new RightL();
		}else if(bl == 5) {
			System.out.println("ZigS");
			return new ZigS();
		}else if(bl == 6) {
			System.out.println("ZigZ");
			return new ZigZ();
		}
		return new ZigZ();
	}
	public void draw(Graphics g){

		g.fillRect(50, 50, 20, 20);

	}
	public void paintComponent(Graphics g) {
		/* set the color to light blue */

		g.setColor(new Color(100, 150, 255));
		g.fillRect(0, 0, 375, 600);

		System.out.println("testing this is being called");
		g.setColor(Color.white);

		if(lost == false){

			updateFrame(g);
		}

		revalidate( );
		repaint( );

		try {
			Thread.sleep(100);
		} catch(InterruptedException e) {
			Thread.currentThread( ).interrupt( );
		}
	}

	public void checkRows(){

		boolean complete = false;
		for(int i =0; i < height; i++)
		{    
			complete = true;
			for(int j = 0; j < width; j ++)
			{
				if(grid[i][j] != 2){
					complete = false;
				}
			}
			if(complete == true){
				Sound.playSound("explosion.wav");
				//Sound.playSound("jig.mid");
				for(int q = 0; q < width; q ++){
					grid[i][q] = 0;
					score = score + 10;
				}
				for(int row = i-1; row >= 0; row--)
				{
					for(int column = 0; column < width; column++){

						grid[row+1][column] = grid[row][column];
						grid[row][column] = 0;
					}
				}
			}    
		}
		/*boolean bleh;
		bleh = true;
		if(complete == true){
			checkRows();
		}
			System.out.println(score);
		if(score > 400) {
			bleh = false;
			place =0;
			speed = 8;
			System.out.println("Level up!");
		}else if(score > 800) {
			bleh = false;
			place =0;
			speed = 7;
			System.out.println("Level up!");
		}else if(score > 1200) {
			bleh = false;
			place =0;
			speed = 6;
			System.out.println("Level up!");
		}else if(score > 1600) {
			bleh = false;
			place =0;
			speed = 5;
			System.out.println("Level up!");
		}else if(score > 2000) {
			bleh = false;
			place =0;
			speed = 4;
			System.out.println("Level up!");
		}else if(score > 2400) {
			bleh = false;
			place =0;
			speed = 3;
			System.out.println("Level up!");
		}else if(score > 2800 && !bleh) {
			bleh = true;
			place =0;
			speed = 2;
			System.out.println("Level up!");
		}*/
	}



	//KeyListeners
	@Override
		public void keyTyped(KeyEvent e) {

		}

	@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_RIGHT ) {

				boolean canMove = true;
				System.out.println("hehehehehheh");
				for(int i = 0; i < 8; i = i + 2){
					if (currX + currConfig[i] >= 9)
					{
						canMove = false;

					}
					else if (grid[currY][currX + currConfig[i] + 1] == 2 ){

						canMove = false;
					}
				}
				if(canMove){
					currentBlock.moveRight();
					currX++;

				}

			} else if (e.getKeyCode() == KeyEvent.VK_LEFT ) {
				boolean canMove = true;
				System.out.println("hehehehehheh");
				for(int i = 0; i < 8; i = i + 2){
					if (currX + currConfig[i] <= 0)
					{
						canMove = false;

					}
					else if (grid[currY][currX + currConfig[i] - 1] == 2 ){

						canMove = false;
					}
				}
				if(canMove){
					currentBlock.moveLeft();
					currX--;

				}

			} else if (e.getKeyCode() == KeyEvent.VK_UP ) {
				currentBlock.rotate();
			} else if (e.getKeyCode() == KeyEvent.VK_DOWN ) {
				//Jump ahead in loop to falling
				place = 8;
			}
		}

	@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub

		}

	public void updateFrame(Graphics g){

		currX = currentBlock.getX();
		currY = currentBlock.getY();
		currConfig = currentBlock.getConfig();
		sticking = false;

		//Only fall one in ten update cycles
		if(place < speed) {
			place++;
		}else if(place == speed) {
			place = 0;
		}

		//Make block fall
		if(place == 9) {
			currentBlock.fall();
		}
		//Draw block based on block anchor
		for(int i=0; i<4; i++) {
			try{
				grid[currY+currConfig[i*2+1]][currX+currConfig[i*2]]=1;
			}
			catch(ArrayIndexOutOfBoundsException e)
			{
				System.out.println(currX + " " + currConfig[i*2]);

			}
		}
		//Print out grid to commmand line
		for(int i=0; i<height; i++) {
			for(int j=0; j<width; j++) {
				System.out.print(grid[i][j]);
				System.out.print(' ');
			}
			System.out.print('\n');
		}
		System.out.print('\n');
		//Print out grid to GUI
		for(int row = 0; row < height; row++)
		{
			for(int column = 0; column < width; column++)
			{
				if(grid[row][column] == 1 || grid[row][column] == 2)
				{
					g.setColor(Color.black);
					g.fillRect(column*30 , row*30 -30, 30, 30);

					g.setColor(Color.white);
					g.fillRect(column*30 , row*30 -30, 28, 28);

					revalidate( );
					repaint( );

				}

			}

		}
		//Check to see if any of the falling blocks are at the bottom
		for(int i=0; i<height; i++) {
			for(int j=0; j<width; j++) {
				if(grid[i][j]==1 && i==19) {
					sticking = true;
				}
				else if(grid[i][j]==1 && grid[i+1][j]==2) {
					sticking = true;
				}
			}
		}
		//If stuck, make all 1's into 2's
		if(sticking) {
			for(int i=0; i<height; i++) {
				for(int j=0; j<width; j++) {
					if(grid[i][j]==1) {
						grid[i][j]=2;
					}
				}
			}
			currentBlock = selectBlock();
		}
		checkRows();
		//Check for and remove complete rows
		for(int j=0; j<width; j++) {
			if(grid[3][j]==2) {
				System.out.println("You lose!");
				lost=true;
			}
		}
		if(lost) {
			Scanner sn;
			int currentHighScore = 0;
			try{
				sn = new Scanner(new File("highScore.txt"));
				currentHighScore = sn.nextInt();
			}catch(IOException e) {

			}
			System.out.println(currentHighScore);
			boolean newHigh = false;	
			if(score > currentHighScore) {
				newHigh = true;
				Integer intScore = new Integer(score);
				try{
					out = new BufferedWriter(new FileWriter("highScore.txt"));
					out.write(intScore.toString());
					out.close();
				}catch(IOException e){
					e.printStackTrace();
				}
			}
			if(!newHigh){
				JOptionPane.showMessageDialog(this, "Game over, Your score: " + score + "\nHighScore: " + currentHighScore);
			}
			else{
				JOptionPane.showMessageDialog(this, "New High Score!!! :" + score);
			}
		}
		//Also if stuck, spawn a new block
		//Rewrite all the twos and make the 1's zeroes
		//Clear off all ones
		for(int i=0; i<height; i++) {
			for(int j=0; j<width; j++) {
				if(grid[i][j]==1) {
					grid[i][j]=0;
				}
				if(grid[i][j]==2) {
					grid[i][j]=2;
				}
			}
		}
	}

	//Constructor initializes variables
	public Basic () {

		grid = new int [height][width];
		//Grid information
		for(int i=0; i<height; i++) {
			for(int j=0; j<width; j++) {
				grid[i][j]=0;
			}
		}

		currentBlock = selectBlock();

		lost = false;
		place = 0;
		//mediaPlayer = new MediaPlayer("mora.mp3");
		speed = 9;
		Sound.backGround("symphony.wav");
	}
}

public class tetrisGame {
	public static void main(String args[]) {
		// create and set up the window.
		JFrame frame = new JFrame("Tetris Classic");

		// make the program close when the window closes
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// add the GameWorld component
		Basic g = new Basic();

		frame.add(g);
		frame.addKeyListener(g);

		// display the window.
		frame.setSize(300, 600);
		frame.setVisible(true);

	}    
}
