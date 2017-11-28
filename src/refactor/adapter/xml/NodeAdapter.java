package refactor.adapter.xml;

public interface NodeAdapter {
    void addAttribute(String name, String value);

    void appendChild(NodeAdapter childNode);

    Object getNode();
}
