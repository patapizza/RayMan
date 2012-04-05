package uclouvain.ingi2325.utils;

import uclouvain.ingi2325.exception.*;
import uclouvain.ingi2325.math.Tuple3;

/**
 * Represents a vector of three float.
 * 
 * @author Antoine Cailliau <antoine.cailliau@uclouvain.be>
 * @author Julien Dupuis
 * @author Sébastien Doeraene <sjrdoeraene@gmail.com>
 * @author Julien Odent <julien.odent@student.uclouvain.be>
 */
public class Vector3D extends Tuple3 {

	public Vector3D() {
		super();
	}

	public Vector3D(float x, float y, float z) {
		super(x, y, z);
	}

	/**
	 * Parse a Vector3D from a string
	 * @param string   String representation
	 * @return The Vector3D represented by string
	 * @throws ParseException string is not a valid Vector3D
	 * @see Tuple3#valueOf(String, Tuple3)
	 */
	public static Vector3D valueOf(String string) throws ParseException {
		return valueOf(string, new Vector3D());
	}

	public Vector3D normalize() {
		double norm = Math.sqrt((double) (this.x * this.x + this.y * this.y + this.z * this.z));
		return new Vector3D((float) (this.x / norm), (float) (this.y / norm), (float) (this.z / norm));
	}

	public Vector3D crossProductWith(Vector3D v) {
		return new Vector3D(this.y * v.z - this.z * v.y, this.z * v.x - this.x * v.z, this.x * v.y - this.y * v.x);
	}

	public Vector3D addWith(Vector3D v) {
		return new Vector3D(x + v.x, y + v.y, z + v.z);
	}

	public float dotProductWith(Vector3D v) {
		return x * v.x + y * v.y + z * v.z;
	}

}
