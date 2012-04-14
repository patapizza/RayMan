package uclouvain.ingi2325.utils;

import uclouvain.ingi2325.math.Matrix4;

/**
 * Represents a surface
 * 
 * @author Julien Odent <julien.odent@student.uclouvain.be>
 */
public abstract class Surface {

	protected String name;
	protected Material material;
	protected Point3D hit;

	public abstract float traverse(Ray r, float t0, float t1);

	public abstract Color shade(PointLight p);

	public abstract void transform(Matrix4 m);

	public String getName() {
		return name;
	}

	public Color getAmbient() {
		return material.getAmbient();
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

}
