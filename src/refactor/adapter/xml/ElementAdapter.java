package refactor.adapter.xml;


import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class ElementAdapter implements NodeAdapter {
    private Element node;
    public ElementAdapter(Element node) {
        this.node = node;
    }

    @Override
    public void addAttribute(String name, String value) {
        node.setAttribute(name,value);
    }

    @Override
    public void appendChild(NodeAdapter childNode) {
        node.appendChild((Node) childNode.getNode());
    }

    @Override
    public Object getNode() {
        return node;
    }
}
