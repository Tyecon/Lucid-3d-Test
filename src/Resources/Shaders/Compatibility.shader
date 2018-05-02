<VERSION>120</VERSION>
<VERT>
    uniform mat4 projectionMatrix;
    uniform mat4 viewMatrix;
    uniform mat4 modelMatrix;
    uniform mat4 normalMatrix;

    attribute vec3 in_Position;
    attribute vec3 in_Normal;
    attribute vec2 in_texCoord;

    varying vec4 v[2];
    varying vec3 N, eye;

    void main(void) {
        gl_Position=viewMatrix * modelMatrix * vec4(in_Position,1.0);
        N = vec3(normalMatrix * vec4(in_Normal, 0.0));
        eye = -gl_Position.xyz;
        v[0]=modelMatrix * vec4(in_Position,1.0);
        v[1]=vec4(-transpose(mat3(viewMatrix)) * viewMatrix[3].xyz, 1.0);
        gl_Position=projectionMatrix * gl_Position;
    }
</VERT>
<FRAG>
    varying vec4 v[2];
    varying vec3 N, eye;

    void main(void) {
        vec4 ambient;
        vec4 diffuse;
        vec4 specular;
        float distance= (gl_FragCoord.z/gl_FragCoord.w);
        vec3 Nn = normalize(N);
        vec3 L = vec3(v[1]-v[0]);
        ambient = vec4(0.2,0.2,0.2,1.0);
        //ambient = vec4(0.1f,0.1f,0.2f,1.0f);
        diffuse = (5 / distance) * clamp(vec4(1,1,1,1) * max(dot(Nn,L), 0.0), 0.0, 1.0);
        specular = clamp(vec4(1,1,1,1) * pow(max(dot(reflect(-L,Nn),normalize(eye)),0.0),15),0.0,1.0);
        gl_FragColor  = (ambient + diffuse);
        //gl_FragColor = vec4(0.5f, 0.5f, 0.5f,1);
    }
</FRAG>