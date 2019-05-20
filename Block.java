import java.util.*;
public class Block {
	public Block() {
		y=2;
		x=4;
		config=0;
	}

	public void fall() {
		if(sticking != true) {
			y++;
		}
	}
	public int[] getConfig() {
		return  allConfig.get(config);
	}
	
	public int getX(){
		return x;
	}
	
	public void moveRight()
	{
		x++;
		
	}
	public void moveLeft()
	{
		x--;
		
	}
	
	public int getY(){
		return y;
	}

	public void rotate(){

	}
	
	protected int config;
	protected ArrayList<int[]> allConfig = new ArrayList<int[]>();
	
	private int x;
	private int y;
	private boolean sticking = false;
}
