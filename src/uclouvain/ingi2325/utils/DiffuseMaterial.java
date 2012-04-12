package uclouvain.ingi2325.utils;

/**
 * Represents diffuse colors
 *
 * @author Julien Odent <julien.odent@student.uclouvain.be>
 */
public class DiffuseMaterial extends Material {

	public DiffuseMaterial(Color color, String name) {
		this.color = color;
		this.name = name;
	}

	public Color shade(Vector3D l, Vector3D n, float intensity) {
		float lambertian = intensity * Math.max(0, n.dotProductWith(l.normalize()));
		return new Color(color.x * lambertian, color.y * lambertian, color.z * lambertian);
	}

}
