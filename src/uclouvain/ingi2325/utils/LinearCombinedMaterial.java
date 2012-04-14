package uclouvain.ingi2325.utils;

/**
 * Represents a linear combination of two materials
 *
 * @author Julien Odent <julien.odent@student.uclouvain.be>
 */
public class LinearCombinedMaterial extends Material {

	private Material m1, m2;
	private float w1, w2;
	private String name;

	public LinearCombinedMaterial(Material m1, float w1, Material m2, float w2, String name) {
		this.m1 = m1;
		this.w1 = w1;
		this.m2 = m2;
		this.w2 = w2;
		this.name = name;
		this.color = new Color(0, 0, 0);
		this.ambient = this.color;
	}

	public Color shade(Vector3D l, Vector3D n, float intensity) {
		Color c1 = m1.shade(l, n, intensity);
		c1.set(c1.x * w1, c1.y * w1, c1.z * w1);
		Color c2 = m2.shade(l, n, intensity);
		c2.set(c2.x * w2, c2.y * w2, c2.z * w2);
		c1.addWith(c2);
		return c1;
	}

}
