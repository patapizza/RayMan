package uclouvain.ingi2325.utils;

/**
 * Represents phong materials
 *
 * @author Julien Odent <julien.odent@student.uclouvain.be>
 */
public class PhongMaterial extends DiffuseMaterial {

	private float shininess;
	private Vector3D direction;

	public PhongMaterial(Color color, float shininess, String name) {
		super(color, name);
		this.shininess = shininess;
	}

	public void setDirection(Vector3D direction) {
		this.direction = direction;
	}

	public Color shade(Vector3D l, Vector3D n, float intensity) {
		Color color = super.shade(l, n, intensity);
		Vector3D h = ((direction.normalize()).addWith(l.normalize())).normalize();
		float phong = 0.5F * intensity * (float) Math.pow((double) Math.max(0, n.dotProductWith(h)), shininess);
		color.x += phong;
		color.y += phong;
		color.z += phong;
		return color;
	}

}
