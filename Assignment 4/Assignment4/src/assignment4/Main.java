package assignment4;

import java.io.File;

public class Main {
	public static void main(String[] args) {
		
		FolderManager main = new FolderManager();
		
		Manager c = new Manager(main.getAddress());
		
		c.getAddress().sendMessage(new BootstrapMessage());
		// The Customer decides when to shutdown everything.
		
	}
}
