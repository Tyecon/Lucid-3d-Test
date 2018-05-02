package Data.Components;

import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public class Camera extends Transform {
    private final Matrix4f projectionMatrix;
    private final FloatBuffer projBuffer;
    
    private float vpX, vpY;
    private int vpPX=0, vpPY=0;
    private float fieldOfView;
    private float aspectRatio;
    private float nearPlane;
    private float farPlane;
    private float yScale;
    private float xScale;
    private float frustumLength;
	
	public Camera() {
		super();
        projBuffer=BufferUtils.createFloatBuffer(16);
		projectionMatrix=new Matrix4f();
		CalculateProjection();
	}
    
    public final void processMouse() {
		while (Mouse.next()) {
			if (Mouse.getEventButtonState()) { 
				switch (Mouse.getEventButton()) {
					case(0): break;
					case(1): Mouse.setGrabbed(true); break;
					case(2): break;
				}
			} else { 
				switch (Mouse.getEventButton()) {
					case(0): break;
					case(1): Mouse.setGrabbed(false); break;
					case(2): break;
				}
			}
		}
		if (Mouse.isGrabbed()) {
			float mouseDX=Mouse.getDX()*0.1f;//InputHandler.dX*InputHandler.mouseS;
			float mouseDY=Mouse.getDY()*0.1f;//InputHandler.dY*InputHandler.mouseS;
			if (rot.x+mouseDX>=360) {
				rot.x=rot.x+mouseDX-360;
			} else if (rot.x+mouseDX<0) {
				rot.x=360-rot.x+mouseDX;
			} else {
				rot.x+=mouseDX;
			}
			if (rot.y-mouseDY>=-90 && rot.y-mouseDY<=90) {
				rot.y+=-mouseDY;
			} else if (rot.y-mouseDY<-90) {
				rot.y=-90;
			} else if (rot.y-mouseDY>0) {
				rot.y=90;
			}
			needCalc=true;
		}
    }

	public FloatBuffer getProjBuffer() {
		return projBuffer;
	}

	@Override
	protected Matrix4f calculateMatrix() {
		mat.load(new Matrix4f());
		Matrix4f.rotate((float)Math.toRadians(rot.z), new Vector3f(0,0,1), mat, mat);
		Matrix4f.rotate((float)Math.toRadians(rot.y), new Vector3f(1,0,0), mat, mat);
		Matrix4f.rotate((float)Math.toRadians(rot.x), new Vector3f(0,1,0), mat, mat);
		Matrix4f.translate(pos, mat, mat);
		mat.store(buffer);
		buffer.flip();
		Matrix4f.load(mat, null).transpose().invert().store(inv);
		inv.flip();
		needCalc=false;
		return mat;
	}
	
	public final void CalculateProjection() {
        projectionMatrix.load(new Matrix4f());
        vpX=1; vpY=1;
        fieldOfView=60f;
        aspectRatio=(Display.getWidth()*vpX)/(Display.getHeight()*vpY);
        nearPlane=0.01f;
        farPlane=100f;
		yScale=(float) (1/Math.tan(((fieldOfView/2f)*(Math.PI/180))));
        xScale=yScale/aspectRatio;
        frustumLength=farPlane-nearPlane;
        projectionMatrix.m00=xScale;
        projectionMatrix.m11=yScale;
        projectionMatrix.m22=-((farPlane+nearPlane)/frustumLength);
        projectionMatrix.m23=-1;
        projectionMatrix.m32=-((2*nearPlane*farPlane)/frustumLength);
        GL11.glViewport(vpPX,vpPY,Math.round(Display.getWidth()*vpX),Math.round(Display.getHeight()*vpY));
		projectionMatrix.store(projBuffer);
		projBuffer.flip();
    }
}
