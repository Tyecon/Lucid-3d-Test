package Data.Assets;

import Data.Assets.ObjLoader.Face;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class Mesh extends Asset {
	private final List<Face> obj=new ArrayList();
	private boolean loaded=false;
	private final int vaoHandle;

	public Mesh(String path) {
		super(path);
		try {
			obj.addAll(
					ObjLoader.asTriangles(
					ObjLoader.load(
					ObjLoader.loadFile(new File(path)
			))));
			loaded=true;
		} catch (FileNotFoundException ex) {
			ex.printStackTrace(System.err);
			loaded=false;
		}
		FloatBuffer faces = BufferUtils.createFloatBuffer(obj.size()*24);
		FloatBuffer verts = BufferUtils.createFloatBuffer(obj.size()*9);
		FloatBuffer norms = BufferUtils.createFloatBuffer(obj.size()*9);
		FloatBuffer texcs = BufferUtils.createFloatBuffer(obj.size()*6);
		for (Face face : obj) {
			for (ObjLoader.Index index : face.indices) {
				verts.put(index.v.x); verts.put(index.v.y); verts.put(index.v.z);
				norms.put(index.n.x); norms.put(index.n.y); norms.put(index.n.z);
				texcs.put(index.t.x); texcs.put(index.t.y);
			}
		}
		verts.flip();
		norms.flip();
		texcs.flip();
		faces.put(verts); faces.put(norms); faces.put(texcs);
		faces.flip();
		vaoHandle=GL30.glGenVertexArrays();
		GL30.glBindVertexArray(vaoHandle);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, GL15.glGenBuffers());
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, faces, GL15.GL_STATIC_DRAW);
		GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);
		GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);
		GL20.glVertexAttribPointer(1, 3, GL11.GL_FLOAT, false, 0, 0);
		GL20.glVertexAttribPointer(2, 2, GL11.GL_FLOAT, false, 0, 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		GL30.glBindVertexArray(0);
	}
	
	public void Render() {
		if (loaded) {
			GL30.glBindVertexArray(vaoHandle);
            GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, obj.size()*3);
            GL30.glBindVertexArray(0);
		}
	}
}
