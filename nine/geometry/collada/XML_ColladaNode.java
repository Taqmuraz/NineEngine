package nine.geometry.collada;

import org.w3c.dom.Node;

public class XML_ColladaNode implements ColladaNode
{
    Node node;

    public XML_ColladaNode(Node node)
    {
        this.node = node;
    }

    @Override
    public void attribute(String name, StringReader reader)
    {
        Node item = node.getAttributes().getNamedItem(name);
        if(item != null)
        {
            String value = item.getNodeValue();
            if(value != null) reader.read(value);
        }
    }

    @Override
    public void children(String tag, NodeReader reader)
    {
        Node child = node.getFirstChild();
        if(child == null) return;
        do
        {
            if(child.getNodeName().equals(tag))
            {
                reader.read(new XML_ColladaNode(child));
            }
        }
        while((child = child.getNextSibling()) != null);
    }

    @Override
    public void content(StringReader reader)
    {
        reader.read(node.getTextContent());
    }
}