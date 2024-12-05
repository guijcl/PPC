package assignment4_v2;

import java.io.File;

import library.Actor;
import library.Address;
import library.Message;

public class FileEmployee extends Actor  {

	public FileEmployee(Address address) {
		super(address);
	}

	@Override
	protected void handleMessage(Message m) {
		System.out.println("FileEmployee " + this + " received " + m);
		
		
		if (m instanceof WorkOnFileProblemMessage) {
			WorkOnFileProblemMessage m2 = ((WorkOnFileProblemMessage) m);
			
			/* EMULATES CRASHES ON THIS ACTOR
			 * if (new Random().nextBoolean()) {
				System.out.println("FileEmployee " + this + " crashed!");
				throw new FileMessageNotProcessedException(m2);
			}*/
			
			File file = m2.getValue();
			if(file.getName().contains("_" + m2.getNumToReplace() + ".txt")) {
				m2.getReplyTo().sendMessage(new ResponseFileMessage(file, true));
			} else {
				m2.getReplyTo().sendMessage(new ResponseFileMessage(file, false));
			}
		}
		
	}

}
