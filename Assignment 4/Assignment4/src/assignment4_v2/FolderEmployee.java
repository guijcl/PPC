package assignment4_v2;

import java.io.File;

import library.Actor;
import library.Address;
import library.Message;

public class FolderEmployee extends Actor  {

	public FolderEmployee(Address address) {
		super(address);
	}

	@Override
	protected void handleMessage(Message m) {
		System.out.println("FolderEmployee " + this + " received " + m);
		
		
		if (m instanceof WorkOnFolderProblemMessage) {
			WorkOnFolderProblemMessage m2 = ((WorkOnFolderProblemMessage) m);
			
			/* EMULATES CRASHES ON THIS ACTOR
			 * if (new Random().nextBoolean()) {
				System.out.println("FolderEmployee " + this + " crashed!");
				throw new FolderMessageNotProcessedException(m2);
			}*/
			
			File folder = m2.getValue();
			if(folder.getName().equals("folder_" + m2.getNumToReplace())) {
				m2.getReplyTo().sendMessage(new ResponseFolderMessage(folder, true));
			} else {
				m2.getReplyTo().sendMessage(new ResponseFolderMessage(folder, false));
			}
		}
		
	}

}
