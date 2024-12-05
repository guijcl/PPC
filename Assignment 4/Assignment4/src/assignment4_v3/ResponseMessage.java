package assignment4_v3;

import java.io.File;

import library.Message;

public class ResponseMessage extends Message {
	private File value;
	private boolean change;
	
	public ResponseMessage(File i, boolean change) {
		value = i;
		this.change = change;
	}

	public File getValue() {
		return value;
	}
	
	public boolean getChange() {
		return change;
	}
}
