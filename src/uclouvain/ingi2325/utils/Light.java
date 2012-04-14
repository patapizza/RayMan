package uclouvain.ingi2325.utils;

/**
 * Represents a light srouce
 *
 * @author Julien Odent <julien.odent@student.uclouvain.be>
 */
public abstract class Light {

	protected float intensity;
	protected Color color;
	protected String name;

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
