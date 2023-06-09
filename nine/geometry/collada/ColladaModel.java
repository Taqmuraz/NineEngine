package nine.geometry.collada;

import java.util.ArrayList;
import java.util.List;

import nine.collection.IterableFlow;
import nine.geometry.Model;
import nine.opengl.CompositeDrawing;
import nine.opengl.Drawing;
import nine.opengl.OpenGL;
import nine.opengl.ShaderPlayer;

public class ColladaModel implements Model
{
    ColladaNode node;
    ColladaGeometryParser parser;

    public ColladaModel(ColladaNode node, ColladaGeometryParser parser)
    {
        this.node = node;
        this.parser = parser;
    }
    public ColladaModel(ColladaNode node)
    {
        this(node, new ColladaBasicGeometryParser());
    }

    @Override
    public Drawing load(OpenGL gl, ShaderPlayer shader)
    {
        List<Drawing> drawings = new ArrayList<Drawing>();

        parser.read(node, (source, floatBuffers, intBuffers) ->
        {
            drawings.add(gl.vao(intBuffers.map("INDEX"))
                    .attribute(3, floatBuffers.map("VERTEX"))
                    .attribute(2, floatBuffers.map("TEXCOORD"))
                    .attribute(3, floatBuffers.map("NORMAL"))
                    .drawing());
        });

        return shader.play(new CompositeDrawing(new IterableFlow<Drawing>(drawings)));
    }
}
