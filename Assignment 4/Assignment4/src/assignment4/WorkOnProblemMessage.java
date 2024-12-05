package assignment4;

import java.io.File;

import library.Address;
import library.Message;

public class WorkOnProblemMessage extends Message {
	private File file;
	private Address replyTo;
	
	public WorkOnProblemMessage(File file, Address address) {
		this.file = file;
		replyTo = address;
	}

	public File getValue() {
		return file;
	}

	public Address getReplyTo() {
		return replyTo;
	}

}
