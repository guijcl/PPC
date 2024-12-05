package assignment4_v2;

public class FileMessageNotProcessedException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9020488632238998405L;
	
	private WorkOnFileProblemMessage messageLost;
	
	public FileMessageNotProcessedException(WorkOnFileProblemMessage m2) {
		this.messageLost = m2;
	}

	public WorkOnFileProblemMessage getMessageLost() {
		return messageLost;
	}

}
