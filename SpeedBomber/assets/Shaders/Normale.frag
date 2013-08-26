
varying vec3 vNormal;

void main(){
	vec3 normal = normalize(vNormal);
    //returning the color of the pixel (here solid blue)
    //- gl_FragColor is the standard GLSL variable that holds the pixel
    //color. It must be filled in the Fragment Shader.
    gl_FragColor = vec4((normal.x + 1)/2,(normal.y + 1)/2,(normal.z + 1)/2,1);
}