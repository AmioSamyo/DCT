package DCT.utility;

public class Node {

	private boolean open;
	private boolean viable;

	public Node() {
		this.open = true;
		this.viable = true;
	}

	public Node(boolean open, boolean viable) {
		this.open = open;
		this.viable = viable;
	}

	public boolean isOpen() {
		return this.open;
	}

	public boolean isViable() {
		return this.viable;
	}

	public void setOpen(boolean flag) {
		this.open = flag;
	}

	public void setViable(boolean flag) {
		this.viable = flag;
	}

}
