package refactor.adapter.xml;

import java.util.Stack;

public abstract class AbstractBuilder implements OutputBuilder {
	static final protected String CANNOT_ADD_ABOVE_ROOT = "Cannot add node above the root node.";
	static final protected String CANNOT_ADD_BESIDE_ROOT = "Cannot add node beside the root node.";
	protected Stack history = new Stack();
	private NodeAdapter rootAdapter;
	private NodeAdapter parentAdapter;
	private NodeAdapter currentAdapter;

	public NodeAdapter getRoot() {
		return rootAdapter;
	}

	public NodeAdapter getParent() {
		return parentAdapter;
	}

	public NodeAdapter getCurrent() {
		return currentAdapter;
	}

	public void setRoot(NodeAdapter root) {
		this.rootAdapter = root;
	}

	public void setParent(NodeAdapter parent) {
		this.parentAdapter = parent;
	}

	public void setCurrent(NodeAdapter current) {
		this.currentAdapter = current;
	}

	public void addAbove(String uncle) {
		if (getCurrent() == getRoot())
			throw new RuntimeException(CANNOT_ADD_ABOVE_ROOT);
		history.pop();
		boolean atRootNode = (history.size() == 1);
		if (atRootNode)
			throw new RuntimeException(CANNOT_ADD_ABOVE_ROOT);
		history.pop();
		setCurrent((NodeAdapter) history.peek());
		addBelow(uncle);
	}

	public void addGrandfather(String grandfather) {
		if (getCurrent() == getRoot())
			throw new RuntimeException(CANNOT_ADD_ABOVE_ROOT);
		history.pop();
		boolean atRootNode = (history.size() == 1);
		if (atRootNode)
			throw new RuntimeException(CANNOT_ADD_ABOVE_ROOT);
		history.pop();
		history.pop();
		setCurrent((NodeAdapter) history.peek());
		addBelow(grandfather);
	}

	public void addAttribute(String name, String value) {
		getCurrent().addAttribute(name, value);
	}

	public void addBelow(String child) {
		NodeAdapter childNode = createElement(child);
		getCurrent().appendChild(childNode);
		setParent(getCurrent());
		setCurrent(childNode);
		history.push(getCurrent());
	}

	public abstract NodeAdapter createElement(String child);

	public void addBeside(String sibling) {
		if (getCurrent() == getRoot())
			throw new RuntimeException(CANNOT_ADD_BESIDE_ROOT);
		NodeAdapter siblingNode = createElement(sibling);
		getParent().appendChild(siblingNode);
		setCurrent(siblingNode);
		history.pop();
		history.push(getCurrent());
	}

	public abstract void addValue(String value);

	protected void init(String rootName) {
		initRoot(rootName);
		setCurrent(getRoot());
		setParent(getRoot());
		history = new Stack();
		history.push(getCurrent());
	}

	public void startNewBuild(String rootName) {
		init(rootName);
	}

	protected abstract void initRoot(String rootName);
}
