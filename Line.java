import java.util.*;
public class Line extends Block{
	public Line(){
		int one[] ={0,-1,0,0,0,1,0,2};
		int two[] ={-1,0,0,0,1,0,2,0};
		allConfig.add(one);
		allConfig.add(two);
	}
	
	@Override
	public void rotate(){
		if(config == 0){
			
			config = 1;
		}
		else if(config == 1){
			config = 0;
		}
	
	}
	public int[] getConfig() {
		return  allConfig.get(config);
	}
}
