package refactor.adapter.xml;

import org.apache.xerces.dom.DocumentImpl;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import java.io.IOException;
import java.io.StringWriter;

public class DOMBuilder extends AbstractBuilder {
	private Document doc;

	public DOMBuilder(String rootName) {
		super.startNewBuild(rootName);
	}

	@Override
	public NodeAdapter createElement(String child) {
		return new ElementAdapter(doc.createElement(child));
	}

	public Document getDocument() {
		return doc;
	}

	@Override
	protected void initRoot(String rootName) {
		doc = new DocumentImpl();
		setRoot(createElement(rootName));
		doc.appendChild((Node) getRoot().getNode());
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

	@Override
	public void addValue(String value) {
		getCurrent().addValue(doc.createTextNode(value));	}
}
