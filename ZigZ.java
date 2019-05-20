public class ZigZ extends Block{
	public ZigZ() {
		int one [] = {-1,0,0,0,0,1,1,1};
		int two [] = {-1,1,-1,0,0,0,0,-1};
		allConfig.add(one);
		allConfig.add(two);
	}
	@Override
	public void rotate() {
		if(config==0) {
			config = 1;
		}else if(config ==1) {
			config = 0;
		}
	}
}
