package Data.Handlers;

import Data.Assets.Asset;
import Data.Assets.Mesh;
import Data.Assets.Shader;
import Data.Components.Camera;
import Data.Components.MeshRenderer;
import Data.Components.Script;
import Data.Components.Transform;
import com.artemis.Entity;
import com.artemis.World;
import com.artemis.managers.TagManager;
import java.io.File;
import java.util.HashMap;
import java.util.Random;

public final class Scene extends World {
	public static final HashMap<String, Asset> Assets=new HashMap();
	
	public Scene() {
		setSystem(new RenderSystem());
		setSystem(new CameraSystem());
		setManager(new TagManager());
		initialize();
		//TODO; replace hardcode resources & test objects
		Assets.put("Cube", new Mesh("src"+File.separatorChar+"Resources"+File.separatorChar+"Models"+File.separatorChar+"untitled.obj"));
		Assets.put("Diffuse", new Shader("src"+File.separatorChar+"Resources"+File.separatorChar+"Shaders"+File.separatorChar+"Compatibility.shader"));
		createCamera(true);
		Random ran=new Random();
		for (int i=0; i<50; i++) {
			createBasicObject().getComponent(Transform.class).setPosition(ran.nextInt(100)-50,ran.nextInt(100)-50,ran.nextInt(100)-50);
		}
		createScript();
	}
	
	/** Updates the scene and processes all systems */
	public void Update(float d) {
		setDelta(d);
		process();
	}
	
	
	public Camera getMainCamera() {
		return this.getManager(TagManager.class).getEntity("MainCamera").getComponent(Camera.class);
	}
	
	//TODO; Create entity
	public Entity createCamera(boolean setMain) {
		Entity e=this.createEntity();
		if (setMain) {this.getManager(TagManager.class).register("MainCamera", e);}
		e.addComponent(new Transform());
		e.addComponent(new Camera());
		e.addToWorld();
		return e;
	}
	
	public Entity createBasicObject() {
		Entity e=createEntity();
		e.addComponent(new Transform());
		e.addComponent(new MeshRenderer((Mesh)Assets.get("Cube")));
		e.addToWorld();
		return e;
	}
	
	public Entity createScript() {
		Entity e=createEntity();
		e.addComponent(new Script());
		e.addToWorld();
		return e;
	}
}
