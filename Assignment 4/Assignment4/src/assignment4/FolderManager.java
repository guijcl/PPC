package assignment4;

import library.Actor;
import library.Message;
import library.SystemKillMessage;

public class FolderManager extends Actor {

	private static int NUMBER_OF_EMPLOYEES = 3;
	private FolderEmployee[] employees = new FolderEmployee[NUMBER_OF_EMPLOYEES];
	private int nextFreeEmployee = 0;

	public FolderManager() {
		for (int i=0; i<NUMBER_OF_EMPLOYEES; i++) {
			this.employees[i] = new FolderEmployee(this.getAddress());
		}
	}

	@Override
	protected void handleMessage(Message m) {
		if (m instanceof SystemKillMessage)  {
			for (FolderEmployee e : employees) {
				e.getAddress().sendMessage(m);
			}
		} else {
			employees[nextFreeEmployee].getAddress().sendMessage(m);
			nextFreeEmployee = (nextFreeEmployee + 1) % employees.length;
		}
	}
	
	@Override
	protected boolean handleException(Exception e) {
		System.out.println("FolderManager is taking care of " + e);
		if (e instanceof MessageNotProcessedException) {
			MessageNotProcessedException e2 = (MessageNotProcessedException) e;
			for (int i=0; i<NUMBER_OF_EMPLOYEES; i++) {
				if (!employees[i].isAlive()) {
					employees[i] = new FolderEmployee(this.getAddress());
				}
			}
			employees[0].getAddress().sendMessage(e2.getMessageLost());
		}
		// Restart the dead actor
		return true;
	}

}
