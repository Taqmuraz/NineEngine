package nine.game;

import nine.function.FunctionSingle;
import nine.geometry.MaterialProvider;
import nine.math.Matrix4f;
import nine.math.Vector3f;
import nine.opengl.Drawing;

public interface HumanState extends UpdatedDrawing
{
    HumanState next();

    static HumanState ofDrawing(UpdatedDrawing drawing, FunctionSingle<HumanState, HumanState> next)
    {
        return new HumanState()
        {
            @Override
            public Drawing update(Matrix4f projection, Vector3f cameraPosition, Vector3f cameraRotation, Vector3f worldLight, MaterialProvider materials)
            {
                return drawing.update(projection, cameraPosition, cameraRotation, worldLight, materials);
            }

            @Override
            public HumanState next()
            {
                return next.call(this);
            }
        };
    }
}