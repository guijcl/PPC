package assignment4_v3;

import java.io.File;

import library.Actor;
import library.Address;
import library.Message;

public class Employee extends Actor  {

	public Employee(Address address) {
		super(address);
	}

	@Override
	protected void handleMessage(Message m) {
		System.out.println("Employee " + this + " received " + m);
		
		
		if (m instanceof WorkOnProblemMessage) {
			WorkOnProblemMessage m2 = ((WorkOnProblemMessage) m);
			
			/* EMULATES CRASHES ON THIS ACTOR
			 * if (new Random().nextBoolean()) {
				System.out.println("FolderEmployee " + this + " crashed!");
				throw new FolderMessageNotProcessedException(m2);
			}*/
			
			File folder = m2.getValue();
			String folder_num = folder.getName().split("_")[1];
			if(folder_num.contains(".")) folder_num = folder_num.split("\\.")[0];
			if(folder_num.equals(m2.getNumToReplace())) {
				m2.getReplyTo().sendMessage(new ResponseMessage(folder, true));
			} else {
				m2.getReplyTo().sendMessage(new ResponseMessage(folder, false));
			}
		}
		
	}

}
