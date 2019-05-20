import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;
import javax.swing.*;
public class Basic extends JComponent implements KeyListener{
	private int height = 20;
	private int width = 10;
	private int grid[][];
	private int score = 0;
	private boolean sticking;
	private int currX;
	private int currY;
	private int currConfig[];
	private boolean lost;
	private Block currentBlock = new Block();

	//Select a block type
	private Block selectBlock() {
		Random typeGen = new Random();
		int bl = typeGen.nextInt(7);
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
		if(complete == true){
			checkRows();
		}
	}

	private void checkLeft() {
		boolean movableL = true;
				System.out.println("True!");
		for(int i=0; i<4; i++) {
			if(currY+currConfig[i*2+1]-1<0){
				movableL=false;
				System.out.println("False!");
			}
		}
		if(movableL) {
			currentBlock.moveLeft();
		}
	}
	private void checkRight() {
		boolean movableR = true;
				System.out.println("True!");
		for(int i=0; i<4; i++) {
			if(currY+currConfig[i*2+1]+1>10){
				movableR=false;
				System.out.println("False!");
			}
		}
		if(movableR) {
			currentBlock.moveRight();
		}
	}
	private void checkRotate() {
		System.out.println("Checking rotation");
	}

	//KeyListeners
	@Override
		public void keyTyped(KeyEvent e) {
/*			if (e.getKeyCode() == KeyEvent.VK_RIGHT ) {
				checkRight();
			} else if (e.getKeyCode() == KeyEvent.VK_LEFT ) {
				checkLeft();
			} else if (e.getKeyCode() == KeyEvent.VK_UP ) {
				checkRotate();
			} else if (e.getKeyCode() == KeyEvent.VK_DOWN ) {
				
			}*/
		}

	@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_RIGHT ) {
				checkRight();
			} else if (e.getKeyCode() == KeyEvent.VK_LEFT ) {
				checkLeft();
			} else if (e.getKeyCode() == KeyEvent.VK_UP ) {
				checkRotate();
			} else if (e.getKeyCode() == KeyEvent.VK_DOWN ) {

			}
		}

	@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub

		}


	public Basic () {
		int grid [][] = new int [height][width];
		//Grid information
		for(int i=0; i<height; i++) {
			for(int j=0; j<width; j++) {
				grid[i][j]=0;
			}
		}
		//Game loop
		//Create very first block
		lost = false;

		currentBlock = selectBlock();

		//Action listener rotata/move/drop
		while(!lost) {
			currX = currentBlock.getX();
			currY = currentBlock.getY();
			currConfig = currentBlock.getConfig();
			sticking = false;
			//Make block fall
			currentBlock.fall();
			//Draw block based on block anchor
			for(int i=0; i<4; i++) {
				//if(currY+currConfig[i*2+1]>0){
				grid[currX+currConfig[i*2]][currY+currConfig[i*2+1]]=1;
				//}
			}
			for(int i=0; i<height; i++) {
				for(int j=0; j<width; j++) {
					System.out.print(grid[i][j]);
					System.out.print(' ');
				}
				System.out.print('\n');
			}
			System.out.print('\n');
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
			//Stop block somehow
			//Check for and remove complete rows
			for(int j=0; j<width; j++) {
				if(grid[3][j]==2) {
					System.out.println("You lose!");
					lost=true;
					break;
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
			//Draw graphics
			for(int i=0; i<height; i++) {
				for(int j=0; j<width; j++) {
					System.out.print(grid[i][j]);
					System.out.print(' ');
				}
				System.out.print('\n');
			}
			System.out.print('\n');
			try {
				Thread.sleep(600);
			} catch(InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
	}
	public static void main(String args[]){
		JFrame frame = new JFrame();
		Basic g = new Basic();
		frame.add(g);
		frame.addKeyListener(g);
	}
}
