package assignment4_v2;

import java.io.File;

import library.Address;
import library.Message;

public class WorkOnFileProblemMessage extends Message {
	private File value;
	private Address replyTo;
	private String numToReplace;
	
	public WorkOnFileProblemMessage(File i, Address address, String numToReplace) {
		value = i;
		replyTo = address;
		this.numToReplace = numToReplace;
	}

	public File getValue() {
		return value;
	}

	public Address getReplyTo() {
		return replyTo;
	}
	
	public String getNumToReplace() {
		return numToReplace;
	}

}
