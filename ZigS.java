import java.util.ArrayList;
public class ZigS extends Block{
	public ZigS(){

		int one[] ={-1,1,0,1,0,0,1,0};
		int two[] ={0,-1,0,0,1,0,1,1};
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
}

