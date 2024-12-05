package assignment4_v3;

import java.io.File;

import library.Actor;
import library.Address;
import library.Message;
import library.SystemKillMessage;

public class Customer extends Actor  {

	private static final int NUMBER_OF_INITIAL_FOLDER_MESSAGES = 10;
	private static final int NUMBER_OF_FOLDER_MESSAGES = 1000;
	
	private Address serverAddress;
	private int folderMessagesReceived = 0;
	
	private String numToReplace;
	private String numResOnReplace;
	
	public Customer(Address address, String numToReplace, String numResOnReplace) {
		serverAddress = address;
		this.numToReplace = numToReplace;
		this.numResOnReplace = numResOnReplace;
	}

	@Override
	protected void handleMessage(Message m) {
		if (m instanceof BootstrapMessage) {
			System.out.println("Starting Customer");
			File[] folders = new File("tree").listFiles();
			for (int i = 0; i < NUMBER_OF_INITIAL_FOLDER_MESSAGES; i++) {
				serverAddress.sendMessage(new WorkOnProblemMessage(folders[i], this.getAddress(), numToReplace));
			}
			
		} else if (m instanceof ResponseMessage) {
			ResponseMessage m2 = (ResponseMessage) m;
			System.out.println("Customer received " + m2.getValue());
			if(m2.getChange()) {
				String[] strs = m2.getValue().toString().split("\\\\");
				int len = strs.length;
				String folder_str = "";
				System.out.println(folder_str);
				for(int i = 0; i < len - 1; i++) {
					folder_str += strs[i] + "\\";
				}
				folder_str += strs[len - 1].split("_")[0];
				File new_file = new File(m2.getValue().toString());
				
				String extension = strs[len - 1].split("_")[1];
				if(extension.contains(".")) extension = "." + extension.split("\\.")[1];
				else extension = "";
				new_file.renameTo(new File(folder_str + "_" + numResOnReplace + extension));

				serverAddress.sendMessage(new WorkOnProblemMessage(new File(folder_str + "_" + numResOnReplace + extension), this.getAddress(), numToReplace));
				
			} else {
				File[] folders = m2.getValue().listFiles();
				if(folders != null) {
					for(int j = 0; j < folders.length; j++) {
						serverAddress.sendMessage(new WorkOnProblemMessage(folders[j], this.getAddress(), numToReplace));
					}
				}
				folderMessagesReceived++;
			}
			
			if (folderMessagesReceived == NUMBER_OF_FOLDER_MESSAGES) {
				serverAddress.sendMessage(new SystemKillMessage());
				this.getAddress().sendMessage(new SystemKillMessage());
			}
		}
	}


	

}
