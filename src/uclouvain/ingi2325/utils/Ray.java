package uclouvain.ingi2325.utils;

import uclouvain.ingi2325.exception.*;

/**
 * Represents a ray
 * 
 * @author Julien Odent <julien.odent@student.uclouvain.be>
 */
public class Ray {

	private Point3D origin;
	private Vector3D direction;

	public Ray(Point3D origin, Vector3D direction) {
		this.origin = origin;
		this.direction = direction;
	}
	
	public Ray(Point3D origin) {
		this.origin = origin;
	}

	public Point3D getOrigin() {
		return origin;
	}

	public void setOrigin(Point3D origin) {
		this.origin = origin;
	}

	public Vector3D getDirection() {
		return direction;
	}

	public void setDirection(Vector3D direction) {
		this.direction = direction;
	}

}
