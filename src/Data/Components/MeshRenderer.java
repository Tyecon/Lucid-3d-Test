package Data.Components;

import Data.Assets.Material;
import Data.Assets.Mesh;
import com.artemis.Component;

public class MeshRenderer extends Component {
	private Mesh mesh;
	private Material mat;

	public MeshRenderer(Mesh mesh) {
		this.mesh = mesh;
		this.mesh.alloc();
		this.mat = Material.getDefault();
		this.mat.alloc();
	}
	
	public void Render() {
		mesh.Render();
	}

	public Material getMat() {
		return mat;
	}

	public Mesh getMesh() {
		return mesh;
	}

	public void setMat(Material mat) {
		if (this.mat!=null) {this.mat.decay();}
		this.mat = mat;
		this.mesh.alloc();
	}

	public void setMesh(Mesh mesh) {
		if (this.mesh!=null) {this.mesh.decay();}
		this.mesh = mesh;
		this.mesh.alloc();
	}
	
}
