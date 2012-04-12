package uclouvain.ingi2325.utils;

/**
 * Represents materials
 *
 * @author Julien Odent <julien.odent@student.uclouvain.be>
 */
public abstract class Material {

	protected Color color;
	protected String name;

	public abstract Color shade(Vector3D l, Vector3D n, float intensity);

	public Color getColor() {
		return color;
	}

	public String getName() {
		return name;
	}

	protected Color getAmbient() {
		return new Color(color.x * 0.25F, color.y * 0.25F, color.z * 0.25F);
	}

}
