package Data.Handlers;

import Data.Assets.Shader;
import Data.Components.Camera;
import Data.Components.MeshRenderer;
import Data.Components.Transform;
import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.managers.TagManager;
import com.artemis.systems.EntityProcessingSystem;

public class RenderSystem extends EntityProcessingSystem {
	@Mapper ComponentMapper<Transform> tran;
	@Mapper ComponentMapper<MeshRenderer> mesh;

	public RenderSystem() {
		super(Aspect.getAspectForAll(Transform.class, MeshRenderer.class).exclude(Camera.class));
	}
	
	@Override
	protected void process(Entity e) {
		Transform pos=tran.get(e);
		MeshRenderer rend=mesh.get(e);
		rend.getMat().getShader().Bind(pos, e.getWorld().getManager(TagManager.class).getEntity("MainCamera").getComponent(Camera.class));
		rend.Render();
		Shader.Unbind();
	}
}
