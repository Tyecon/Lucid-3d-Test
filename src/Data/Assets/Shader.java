package Data.Assets;

import org.lwjgl.opengl.GL11;
import java.io.File;
import Data.Components.Transform;
import Data.Components.Camera;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import static org.lwjgl.opengl.GL20.*;

public class Shader extends Asset {
	private int ShaderID=-1, vertShader=-1, fragShader=-1;
    private final int projectionMatrix, viewMatrix, modelMatrix, tranMatrix;

	public Shader(String path) {
		super(path);
		try {
			String shader=Charset.defaultCharset().decode(ByteBuffer.wrap(Files.readAllBytes(new File(path).toPath()))).toString();
            String version=shader.substring(
                    (shader.indexOf("<VERSION>")==-1) ? 0 : shader.indexOf("<VERSION>")+9,
                    (shader.indexOf("</VERSION>")==-1) ? 0 : shader.indexOf("</VERSION>")).trim();
            vertShader=createShader(
                    ((version.isEmpty()) ? "#version 120" : ("#version "+version))
                    + shader.substring(shader.indexOf("<VERT>")+6, shader.indexOf("</VERT>")), GL_VERTEX_SHADER);
            fragShader=createShader(
                    ((version.isEmpty()) ? "#version 120" : ("#version "+version))
                    + shader.substring(shader.indexOf("<FRAG>")+6, shader.indexOf("</FRAG>")), GL_FRAGMENT_SHADER);
			ShaderID=glCreateProgram();
			glAttachShader(ShaderID, vertShader);
			glAttachShader(ShaderID, fragShader);
			glLinkProgram(ShaderID);
			if (glGetProgrami(ShaderID, GL_LINK_STATUS) == GL11.GL_FALSE) {
				System.err.println("Shader program wasn't linked correctly.");
				System.err.println(glGetProgramInfoLog(ShaderID, 1024));
			}
			glDeleteShader(vertShader);
			glDeleteShader(fragShader);
			System.out.print(glGetProgramInfoLog(ShaderID, GL_INFO_LOG_LENGTH));
		} catch(IOException ex) {ex.printStackTrace(System.err);}
		projectionMatrix=glGetUniformLocation(ShaderID, "projectionMatrix");
        viewMatrix=glGetUniformLocation(ShaderID, "viewMatrix");
        modelMatrix=glGetUniformLocation(ShaderID, "modelMatrix");
        tranMatrix=glGetUniformLocation(ShaderID, "normalMatrix");
        System.out.println("Shader:"+ShaderID+" Pro:"+projectionMatrix+" View:"+viewMatrix+" Model:"+modelMatrix+" Normal:"+tranMatrix);
	}
	
	private int createShader(String shader, int type) {
		int id=glCreateShader(type);
		glShaderSource(id, shader);
		glCompileShader(id);
		//System.out.print(glGetShaderInfoLog(id, GL_INFO_LOG_LENGTH));
		if (glGetShaderi(id, GL_COMPILE_STATUS) == GL11.GL_FALSE) {
			System.out.println(glGetShaderInfoLog(id, glGetProgrami(ShaderID, GL_INFO_LOG_LENGTH)));
		}
		return id;
	}
	
	public void Bind(Transform model, Camera cam) {
		glUseProgram(ShaderID);
        glUniformMatrix4(projectionMatrix, false, cam.getProjBuffer());
        glUniformMatrix4(viewMatrix, false, cam.getBuffer());
        glUniformMatrix4(modelMatrix, false, model.getBuffer());
        glUniformMatrix4(tranMatrix, false, model.getInv());
	}
	
	public static void Unbind() {
        glUseProgram(0);
    }

	public int getShaderID() {
		return ShaderID;
	}
}
