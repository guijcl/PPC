package assignment4;

import java.io.File;

import library.Address;
import library.Message;

public class WorkOnFileProblemMessage extends Message {
	private File file;
	private Address replyTo;
	
	public WorkOnFileProblemMessage(File file, Address address) {
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
