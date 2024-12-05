package assignment4_v2;

public class FolderMessageNotProcessedException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9020488632238998405L;
	
	private WorkOnFolderProblemMessage messageLost;
	
	public FolderMessageNotProcessedException(WorkOnFolderProblemMessage m2) {
		this.messageLost = m2;
	}

	public WorkOnFolderProblemMessage getMessageLost() {
		return messageLost;
	}

}
