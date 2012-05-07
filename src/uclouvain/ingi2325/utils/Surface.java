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
	protected Matrix4 m;

	public abstract float traverse(Ray r, float t0, float t1);

	public abstract boolean hit(Ray r, float t0, float t1, HitRecord rec);

	public abstract Color shade(Light light);

	public void transform(Matrix4 m) {
		// Saving invert transform matrix for ray tracing
		this.m = m.invert();
	}

	public Matrix4 getM() {
		return m;
	}

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
