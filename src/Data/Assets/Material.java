package Data.Assets;

import Data.Handlers.Scene;

public final class Material extends Asset {

	public static Material getDefault() {
		return new Material("",(Shader)Scene.Assets.get("Diffuse"));
	}
	private Shader shader;

	public Material(String path) {
		super(path);
	}
	
	public Material(String path, Shader shader) {
		super(path);
		setShader(shader);
	}

	public Shader getShader() {
		return shader;
	}

	public void setShader(Shader shader) {
		if (this.shader!=null) {this.shader.decay();}
		this.shader = shader;
		this.shader.alloc();
	}
}
