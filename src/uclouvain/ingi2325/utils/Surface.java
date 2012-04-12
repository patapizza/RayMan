package uclouvain.ingi2325.utils;

import uclouvain.ingi2325.exception.*;

/**
 * Represents a surface
 * 
 * @author Julien Odent <julien.odent@student.uclouvain.be>
 */
public abstract class Surface {

	protected String name;
	protected Material material;

	public abstract float traverse(Ray r, float t1);

	public abstract Color shade(PointLight p);

	public String getName() {
		return name;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

}
