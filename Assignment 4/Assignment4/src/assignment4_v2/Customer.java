package assignment4_v2;

import java.io.File;

import library.Actor;
import library.Address;
import library.Message;
import library.SystemKillMessage;

public class Customer extends Actor  {

	private static final int NUMBER_OF_FOLDER_MESSAGES = 10;
	private static final int NUMBER_OF_FILE_MESSAGES = 1000;
	
	private Address serverAddress;
	//private int folderMessagesReceived = 0;
	private int fileMessagesReceived = 0;
	
	private String numToReplace;
	private String folderNumResOnReplace;
	private String fileNumResOnReplace;
	
	public Customer(Address address, String numToReplace, 
			String folderNumResOnReplace, String fileNumResOnReplace) {
		serverAddress = address;
		this.numToReplace = numToReplace;
		this.folderNumResOnReplace = folderNumResOnReplace;
		this.fileNumResOnReplace = fileNumResOnReplace;
	}

	@Override
	protected void handleMessage(Message m) {
		if (m instanceof BootstrapMessage) {
			System.out.println("Starting Customer");
			File[] folders = new File("tree").listFiles();
			for (int i = 0; i < NUMBER_OF_FOLDER_MESSAGES; i++) {
				serverAddress.sendMessage(new WorkOnFolderProblemMessage(folders[i], this.getAddress(), numToReplace));
			}
			
		} else if (m instanceof ResponseFolderMessage) {
			ResponseFolderMessage m2 = (ResponseFolderMessage) m;
			System.out.println("Customer received " + m2.getValue());
			
			if(!m2.getChange()) {
				File[] files = m2.getValue().listFiles();
				for (int i = 0; i < 100; i++) {
					serverAddress.sendMessage(new WorkOnFileProblemMessage(files[i], this.getAddress(), numToReplace));
				}
			} else {
				new File(m2.getValue().toString()).renameTo(new File("tree/folder_" + folderNumResOnReplace));
				serverAddress.sendMessage(new WorkOnFolderProblemMessage(new File("tree/folder_" + folderNumResOnReplace), this.getAddress(), numToReplace));
			}
			
		} else if (m instanceof ResponseFileMessage) {
			ResponseFileMessage m2 = (ResponseFileMessage) m;
			System.out.println("Customer received " + m2.getValue());
			if(m2.getChange()) {
				String[] strs = m2.getValue().toString().split("\\\\");
				String folder_num = strs[1].split("_")[1];
				new File(m2.getValue().toString()).renameTo(new File("tree/folder_" + folder_num + "/file_" + fileNumResOnReplace + ".txt"));
			}
			
			fileMessagesReceived++;
			if (fileMessagesReceived == NUMBER_OF_FILE_MESSAGES) {
				serverAddress.sendMessage(new SystemKillMessage());
				this.getAddress().sendMessage(new SystemKillMessage());
			}
		}
	}


	

}
