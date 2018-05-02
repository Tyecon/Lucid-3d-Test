package Data.Assets;

import java.io.File;

public class Asset extends File {
	int allocations=0;
    
	protected Asset(String path) {
		super(path);
	}
	
	public void alloc() {
		allocations++;
	}
	
	public void decay() {
		allocations--;
	}
}
