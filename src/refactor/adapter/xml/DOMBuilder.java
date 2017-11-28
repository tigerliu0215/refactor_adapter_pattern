package refactor.adapter.xml;

import org.apache.xerces.dom.DocumentImpl;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Stack;

public class DOMBuilder extends AbstractBuilder {
	private Document doc;

	private NodeAdapter rootAdapter;
	private NodeAdapter parentAdapter;
	private NodeAdapter currentAdapter;

	public DOMBuilder(String rootName) {
		init(rootName);
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

	private NodeAdapter createElement(String child) {
		return new ElementAdapter(doc.createElement(child));
	}

	public void addBeside(String sibling) {
		if (getCurrent() == getRoot())
			throw new RuntimeException(CANNOT_ADD_BESIDE_ROOT);
		NodeAdapter siblingNode = createElement(sibling);
		getParent().appendChild(siblingNode);
		setCurrent(siblingNode);
		history.pop();
		history.push(getCurrent());
	}

	public void addValue(String value) {
		getCurrent().addValue(doc.createTextNode(value));	}

	public Document getDocument() {
		return doc;
	}

	protected void init(String rootName) {
		doc = new DocumentImpl();
		setRoot(createElement(rootName));
		doc.appendChild((Node) getRoot().getNode());
		setCurrent(getRoot());
		setParent(getRoot());
		history = new Stack();
		history.push(getCurrent());
	}

	public void startNewBuild(String rootName) {
		init(rootName);
	}

	public String toString() {
		OutputFormat format = new OutputFormat(doc);
		StringWriter stringOut = new StringWriter();
		XMLSerializer serial = new XMLSerializer(stringOut, format);
		try {
			serial.asDOMSerializer();
			serial.serialize(doc.getDocumentElement());
		} catch (IOException ioe) {
			ioe.printStackTrace();
			return ioe.getMessage();
		}
		return stringOut.toString();
	}

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
}
