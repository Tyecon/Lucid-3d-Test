#version 140

in vec4 pass_Pos;
in vec3 pass_Normal;
in vec4 pass_Eye;

out vec4 out_Color;

void main(void) {
    vec3 normalDir= normalize(pass_Normal);
    vec3 lightDir = normalize(pass_Eye-pass_Pos);
    float Intensity = 5 / (gl_FragCoord.z/gl_FragCoord.w);
    float cosAngIncidence = dot(normalDir, lightDir);
    cosAngIncidence = clamp(cosAngIncidence, 0, 1);
    vec3 ambient = vec3(0.2,0.2,0.2) * Intensity;
    vec3 diffuse = vec3(1,1,1) * Intensity * cosAngIncidence;
    vec3 specular= vec3(1,1,1) * vec3(1,1,1);
    if (dot(normalDir, lightDir) < 0.0) {
        specular=specular*0;
    } else {
        specular=specular * Intensity * pow(max(0.0, dot(reflect(-lightDir, normalDir), normalize(pass_Eye))), 25);
    }
    out_Color = vec4(ambient + diffuse, 1.0);
}