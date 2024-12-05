package assignment4_v3;

import library.Actor;
import library.Message;
import library.SystemKillMessage;

public class Manager extends Actor {

	private static int NUMBER_OF_FOLDER_EMPLOYEES = 50;
	private Employee[] employees = new Employee[NUMBER_OF_FOLDER_EMPLOYEES];
	private int nextFreeEmployee = 0;

	public Manager() {
		for (int i = 0; i < NUMBER_OF_FOLDER_EMPLOYEES; i++) {
			this.employees[i] = new Employee(this.getAddress());
		}
	}

	@Override
	protected void handleMessage(Message m) {
		if (m instanceof SystemKillMessage) {
			for (Employee e : employees) {
				e.getAddress().sendMessage(m);
			}
		} else {
			employees[nextFreeEmployee].getAddress().sendMessage(m);
			nextFreeEmployee = (nextFreeEmployee + 1) % employees.length;
		}
	}
	
	@Override
	protected boolean handleException(Exception e) {
		System.out.println("Manager is taking care of " + e);
		if (e instanceof MessageNotProcessedException) {
			MessageNotProcessedException e2 = (MessageNotProcessedException) e;
			for (int i = 0; i < NUMBER_OF_FOLDER_EMPLOYEES; i++) {
				if (!employees[i].isAlive()) {
					employees[i] = new Employee(this.getAddress());
				}
			}
			employees[0].getAddress().sendMessage(e2.getMessageLost());
		}
		// Restart the dead actor
		return true;
	}

}
