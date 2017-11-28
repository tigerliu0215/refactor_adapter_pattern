package refactor.adapter.xml;

public class TagNodeAdapter implements NodeAdapter {

    private final TagNode node;

    public TagNodeAdapter(TagNode node) {
        this.node = node;
    }

    @Override
    public void addAttribute(String name, String value) {
        node.addAttribute(name,value);
    }

    @Override
    public void appendChild(NodeAdapter childNode) {
        node.add((TagNode)childNode.getNode());
    }

    @Override
    public Object getNode() {
        return node;
    }

    @Override
    public void addValue(Object value) {
        node.addValue(value.toString());
    }
}
