package assignment4_v2;

import library.Actor;
import library.Message;
import library.SystemKillMessage;

public class Manager extends Actor {

	private static int NUMBER_OF_FOLDER_EMPLOYEES = 3;
	private static int NUMBER_OF_FILE_EMPLOYEES = 100;
	private FolderEmployee[] folder_employees = new FolderEmployee[NUMBER_OF_FOLDER_EMPLOYEES];
	private FileEmployee[] file_employees = new FileEmployee[NUMBER_OF_FILE_EMPLOYEES];
	private int nextFreeFolderEmployee = 0;
	private int nextFreeFileEmployee = 0;

	public Manager() {
		for (int i = 0; i < NUMBER_OF_FOLDER_EMPLOYEES; i++) {
			this.folder_employees[i] = new FolderEmployee(this.getAddress());
		}
		for (int i = 0; i < NUMBER_OF_FILE_EMPLOYEES; i++) {
			this.file_employees[i] = new FileEmployee(this.getAddress());
		}
	}

	@Override
	protected void handleMessage(Message m) {
		if (m instanceof SystemKillMessage) {
			for (FolderEmployee e : folder_employees) {
				e.getAddress().sendMessage(m);
			}
			for (FileEmployee e : file_employees) {
				e.getAddress().sendMessage(m);
			}
		} else if(m instanceof WorkOnFolderProblemMessage) {
			folder_employees[nextFreeFolderEmployee].getAddress().sendMessage(m);
			nextFreeFolderEmployee = (nextFreeFolderEmployee + 1) % folder_employees.length;
		} else {
			file_employees[nextFreeFileEmployee].getAddress().sendMessage(m);
			nextFreeFileEmployee = (nextFreeFileEmployee + 1) % file_employees.length;
		}
	}
	
	@Override
	protected boolean handleException(Exception e) {
		System.out.println("Manager is taking care of " + e);
		if (e instanceof FolderMessageNotProcessedException) {
			FolderMessageNotProcessedException e2 = (FolderMessageNotProcessedException) e;
			for (int i = 0; i < NUMBER_OF_FOLDER_EMPLOYEES; i++) {
				if (!folder_employees[i].isAlive()) {
					folder_employees[i] = new FolderEmployee(this.getAddress());
				}
			}
			folder_employees[0].getAddress().sendMessage(e2.getMessageLost());
		} else if (e instanceof FileMessageNotProcessedException) {
			FileMessageNotProcessedException e2 = (FileMessageNotProcessedException) e;
			for (int i = 0; i < NUMBER_OF_FILE_EMPLOYEES; i++) {
				if (!file_employees[i].isAlive()) {
					file_employees[i] = new FileEmployee(this.getAddress());
				}
			}
			file_employees[0].getAddress().sendMessage(e2.getMessageLost());
		}
		// Restart the dead actor
		return true;
	}

}
