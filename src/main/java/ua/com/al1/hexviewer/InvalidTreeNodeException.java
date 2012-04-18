package ua.com.al1.hexviewer;

public class InvalidTreeNodeException extends Exception {
	private static final long serialVersionUID = 4876543219876500003L;

	public InvalidTreeNodeException() {
	}

	public InvalidTreeNodeException(String msg) {
		super(msg);
	}
}