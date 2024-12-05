package assignment4;

import java.io.File;

import library.Actor;
import library.Address;
import library.Message;
import library.SystemKillMessage;

public class Manager extends Actor  {
	
	private static File tree = new File("tree");

	private static final int NUMBER_OF_MESSAGES = (int) tree.length();
	
	private Address serverAddress;
	private int messagesReceived = 0;
	
	public Manager(Address address) {
		serverAddress = address;
	}

	@Override
	protected void handleMessage(Message m) {
		if (m instanceof BootstrapMessage) {
			System.out.println("Starting Manager");
			File[] folder = tree.listFiles();
			for (int i=0;i<NUMBER_OF_MESSAGES;i++) {
				serverAddress.sendMessage(new WorkOnProblemMessage(folder[i], this.getAddress()));
			}
		} else if (m instanceof ResponseMessage) {
			ResponseMessage m2 = (ResponseMessage) m;
			System.out.println("Manager received " + m2.getValue());
			messagesReceived++;
			if (messagesReceived == NUMBER_OF_MESSAGES) {
				serverAddress.sendMessage(new SystemKillMessage());
				this.getAddress().sendMessage(new SystemKillMessage());
			}
		}
	}


	

}
