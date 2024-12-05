package assignment4;

public class MessageNotProcessedException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9020488632238998405L;
	
	private WorkOnProblemMessage messageLost;
	
	public MessageNotProcessedException(WorkOnProblemMessage m2) {
		this.messageLost = m2;
	}

	public WorkOnProblemMessage getMessageLost() {
		return messageLost;
	}

}
