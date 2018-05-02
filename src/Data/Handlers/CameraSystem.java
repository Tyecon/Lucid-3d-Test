package Data.Handlers;

import Data.Components.Camera;
import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;

public class CameraSystem extends EntityProcessingSystem {
	@Mapper ComponentMapper<Camera> cam;
	
	public CameraSystem() {
		super(Aspect.getAspectForAll(Camera.class));
	}

	@Override
	protected void process(Entity entity) {
		cam.get(entity).processMouse();
	}
}
