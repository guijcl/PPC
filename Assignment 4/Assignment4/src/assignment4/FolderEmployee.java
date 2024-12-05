package assignment4;

import java.io.File;
import java.util.Random;

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
		
		
		if (m instanceof WorkOnProblemMessage) {
			WorkOnProblemMessage m2 = ((WorkOnProblemMessage) m);
			
			/*if (new Random().nextBoolean()) {
				System.out.println("Employee " + this + " crashed!");
				throw new MessageNotProcessedException(m2);
			}*/

			m2.getReplyTo().sendMessage(new ResponseMessage("WORKS"));
			
			/**File folder = m2.getValue();
			if(folder.getName().contains("1")) {
				//folder.renameTo(new File(folder.getName().split("_")[0] + "_3"));
				m2.getReplyTo().sendMessage(new ResponseMessage("HAS"));
			} else {
				//System.out.println(folder.getName());
				m2.getReplyTo().sendMessage(new ResponseMessage("DOESN'T"));
			}*/
		}
		
	}

}
