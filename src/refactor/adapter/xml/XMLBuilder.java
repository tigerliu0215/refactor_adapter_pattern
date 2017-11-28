package refactor.adapter.xml;

import java.util.Stack;

public class XMLBuilder extends AbstractBuilder {

	public XMLBuilder(String rootName) {
		startNewBuild(rootName);
	}


	@Override
	public NodeAdapter createElement(String child) {
		return new TagNodeAdapter(new TagNode(child));
	}

	public void addValue(String value) {
		getCurrent().addValue(value);
	}

	@Override
	protected void initRoot(String rootName) {
		setRoot(new TagNodeAdapter((new TagNode(rootName))));
	}

	public String toString() {
		return getRoot().getNode().toString();
	}
}
