package uclouvain.ingi2325.utils;

/**
 * Represents materials
 *
 * @author Julien Odent <julien.odent@student.uclouvain.be>
 */
public abstract class Material {

	protected Color color;
	protected String name;
	protected Color ambient;

	public abstract Color shade(Vector3D l, Vector3D n, float intensity);

	public Color getColor() {
		return color;
	}

	public String getName() {
		return name;
	}

	protected Color getAmbient() {
		return ambient;
	}

}
