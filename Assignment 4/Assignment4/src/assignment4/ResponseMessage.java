package assignment4;

import library.Message;

public class ResponseMessage extends Message {
	private String value;
	
	public ResponseMessage(String i) {
		value = i;
	}

	public String getValue() {
		return value;
	}
}
