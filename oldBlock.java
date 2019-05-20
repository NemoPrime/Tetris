import java.util.*;
public class Block {
	//Data
	private int x;
	private int y;
	private boolean sticking = false;
	protected int config = 0;
	protected ArrayList<int[]> allConfig = new ArrayList<int []>();

	//Methods
	public Block() {
		y=4;
		x=2;
	}
	public void moveLeft() {
		y--;
	}
	public void moveRight() {
		y++;
	}
	public void fall() {
		if(sticking != true) {
			x++;
		}
	}
	public int [] getConfig() {
		return allConfig.get(config);
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
}
