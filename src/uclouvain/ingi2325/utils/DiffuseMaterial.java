package uclouvain.ingi2325.utils;

/**
 * Represents diffuse colors
 *
 * @author Julien Odent <julien.odent@student.uclouvain.be>
 */
public class DiffuseMaterial {

	private Color color;
	private String name;

	public DiffuseMaterial(Color color, String name) {
		this.color = color;
		this.name = name;
	}

	public Color getColor() {
		return color;
	}

	public String getName() {
		return name;
	}

}
