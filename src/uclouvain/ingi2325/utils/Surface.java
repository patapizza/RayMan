package uclouvain.ingi2325.utils;

import uclouvain.ingi2325.exception.*;

/**
 * Represents a surface
 * 
 * @author Julien Odent <julien.odent@student.uclouvain.be>
 */
public abstract class Surface {

	protected String name;
	protected Color color;

	public abstract float traverse(Ray r, float t1);

	public abstract Color shade(PointLight p);

	public String getName() {
		return name;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

}
