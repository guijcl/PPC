package assignment4_v2;

import java.io.File;

import library.Message;

public class ResponseFolderMessage extends Message {
	private File value;
	private boolean change;
	
	public ResponseFolderMessage(File i, boolean change) {
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
