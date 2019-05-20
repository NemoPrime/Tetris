import java.util.*;
public class LeftL extends Block{
	
	public LeftL(){

		int one[] ={-1,-1,-1,0,0,0,1,0};
		int two[] ={0,-1,0,0,0,1,1,-1};
		int three[] ={-1,0,0,0,1,0,1,1};
		int four[] ={-1,1,0,1,0,0,0,-1};
		
		allConfig.add(one);
		allConfig.add(two);
		allConfig.add(three);
		allConfig.add(four);
		
		
		
	}
	@Override
	public void rotate(){
		if(config == 0){
			
			config = 1;
		}
		else if(config == 1){
			config = 2;
		}
		else if(config == 2){
			config = 3;
		}
		else if(config == 3){
			config = 0;
		}
	
	}
	
}
