package nine.geometry;

import nine.opengl.ShaderPlayer;

public interface ShadedSkinnedModel
{
    SkinnedModel shade(ShaderPlayer skinShader, ShaderPlayer staticShader);
}