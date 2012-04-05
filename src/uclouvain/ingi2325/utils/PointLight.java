package uclouvain.ingi2325.utils;

/**
 * Represents a light point
 *
 * @author Julien Odent <julien.odent@student.uclouvain.be>
 */
public class PointLight {

	private Point3D position;
	private float intensity;
	private Color color;
	private String name;

	public PointLight(Point3D position, float intensity, Color color, String name) {
		this.position = position;
		this.intensity = intensity;
		this.color = color;
		this.name = name;
	}

	public Point3D getPosition() {
		return position;
	}

	public float getIntensity() {
		return intensity;
	}

	public Color getColor() {
		return color;
	}

	public String getName() {
		return name;
	}

}
