in vec2 uv;
in vec3 worldNormal;

out vec4 out_Color;

uniform sampler2D texture2d;
uniform vec3 worldLight;

void main (void)
{	
	out_Color = vec4(texture(texture2d, uv).rgb * (dot(worldNormal, -worldLight) + 1) * 0.75, 1);
}