package nine.geometry.collada;

import nine.io.StorageResource;

public interface ColladaNode
{
    void attribute(String name, StringReader reader);
    void content(StringReader reader);
    void children(String tag, NodeReader reader);

    public interface Selector
    {
        void select(StringReader reader);
    }

    default void manyChildren(Selector selector, NodeReader reader)
    {
        selector.select(tag -> children(tag, reader));
    }
    default void manyAttributes(Selector selector, StringReader reader)
    {
        selector.select(name -> attribute(name, reader));
    }

    static ColladaNode fromFile(StorageResource file)
    {
        return new FileColladaNode(file, System.out::println);
    }
}