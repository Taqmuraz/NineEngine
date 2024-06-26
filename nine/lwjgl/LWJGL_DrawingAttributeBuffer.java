package nine.lwjgl;

import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;

import nine.buffer.Buffer;
import nine.buffer.FloatCollector;
import nine.opengl.Drawing;
import nine.opengl.DrawingAttributeBuffer;

public class LWJGL_DrawingAttributeBuffer implements DrawingAttributeBuffer
{
    int index;
    int stride;
    Buffer<Float> data;
    LWJGL_DrawingAttributeBuffer previous;
    LWJGL_Vao vao;
    static final FloatCollector floatCollector = new FloatCollector();
    
    private LWJGL_DrawingAttributeBuffer(LWJGL_Vao vao, int index, int stride, Buffer<Float> data, LWJGL_DrawingAttributeBuffer previous)
    {
        this.index = index;
        this.stride = stride;
        this.data = data;
        this.previous = previous;
        this.vao = vao;
    }

    LWJGL_DrawingAttributeBuffer(LWJGL_Vao vao, int index, int stride, Buffer<Float> data)
    {
        this.index = index;
        this.stride = stride;
        this.data = data;
        this.vao = vao;
    }

    @Override
    public DrawingAttributeBuffer attribute(int stride, Buffer<Float> data)
    {
        return new LWJGL_DrawingAttributeBuffer(vao, index + 1, stride, data, this);
    }

    private LWJGL_VboActivation activation(LWJGL_VboAllocator vbos)
    {
        LWJGL_Vbo vbo = vbos.vbo(index);
        LWJGL_VboActivation last = previous == null ? () -> { } : previous.activation(vbos);
        vbo.bind(() ->
        {
            GL15.glBufferData(GL15.GL_ARRAY_BUFFER, floatCollector.collect(data), GL15.GL_STATIC_DRAW);
            GL20.glVertexAttribPointer(index, stride, GL20.GL_FLOAT, false, 0, 0);
        });
        return () ->
        {
            last.activate();
            GL20.glEnableVertexAttribArray(index);
        };
    }

    @Override
    public Drawing drawing()
    {
        var allocator = vao.allocate(index + 1);
        return allocator.drawing(activation(allocator));
    }
}