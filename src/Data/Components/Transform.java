package Data.Components;

import com.artemis.Component;
import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public class Transform extends Component {
	protected final FloatBuffer buffer, inv;
	protected final Matrix4f mat;
	protected final Vector3f pos;
	protected final Vector3f rot;
	private final Vector3f scl;
	public boolean needCalc=false;

	public Transform() {
		buffer=BufferUtils.createFloatBuffer(16);
		inv=BufferUtils.createFloatBuffer(16);
		mat=new Matrix4f();
		pos=new Vector3f(0,0,0);
		rot=new Vector3f(0,0,0);
		scl=new Vector3f(1,1,1);
		calculateMatrix();
	}
	
	public Transform moveForward(float distance, boolean y) {
        pos.z += distance*Math.cos(Math.toRadians(rot.x));
        pos.x += distance*Math.sin(Math.toRadians(rot.x));
        if (y) {pos.y -= distance*Math.sin(Math.toRadians(rot.y));}
		needCalc=true;
		return this;
    }
    
    public Transform moveSide(float distance) {
        pos.x += distance*Math.cos(Math.toRadians(-rot.x));
        pos.z += distance*Math.sin(Math.toRadians(-rot.x));
		needCalc=true;
		return this;
    }

	protected Matrix4f calculateMatrix() {
		mat.load(new Matrix4f());
		Matrix4f.scale(scl, mat, mat);
		Matrix4f.translate(pos, mat, mat);
		Matrix4f.rotate((float)Math.toRadians(rot.z), new Vector3f(0,0,1), mat, mat);
		Matrix4f.rotate((float)Math.toRadians(rot.y), new Vector3f(0,1,0), mat, mat);
		Matrix4f.rotate((float)Math.toRadians(rot.x), new Vector3f(1,0,0), mat, mat);
		mat.store(buffer);
		buffer.flip();
		Matrix4f.load(mat, null).transpose().invert().store(inv);
		inv.flip();
		needCalc=false;
		return mat;
	}
	
	public FloatBuffer getBuffer() {
		if (needCalc) {calculateMatrix();}
		return buffer;
	}

	public FloatBuffer getInv() {
		return inv;
	}

	public Vector3f getPosition() {
		return pos;
	}

	public Vector3f getRotation() {
		return rot;
	}

	public Vector3f getScale() {
		return scl;
	}
	
	public Transform setPosition(Vector3f position) {
		pos.set(position);
		needCalc=true;
		return this;
	}
	
	public Transform setPosition(float x, float y, float z) {
		pos.set(x, y, z);
		needCalc=true;
		return this;
	}
	
	public Transform setRotation(Vector3f rotation) {
		rot.set(rotation);
		needCalc=true;
		return this;
	}
	
	public Transform setRotation(float x, float y, float z) {
		rot.set(x, y, z);
		needCalc=true;
		return this;
	}
	
	public Transform setScale(Vector3f scale) {
		scl.set(scale);
		needCalc=true;
		return this;
	}
	
	public Transform setScale(float x, float y, float z) {
		scl.set(x, y, z);
		needCalc=true;
		return this;
	}
}
