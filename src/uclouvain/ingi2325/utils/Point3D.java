package uclouvain.ingi2325.utils;

import uclouvain.ingi2325.exception.*;
import uclouvain.ingi2325.math.*;

/**
 * Represents a 3D point by its three x, y, z components
 * 
 * @author Antoine Cailliau <antoine.cailliau@uclouvain.be>
 * @author Julien Dupuis
 * @author Sébastien Doeraene <sjrdoeraene@gmail.com>
 * @author Julien Odent <julien.odent@student.uclouvain.be>
 */
public class Point3D extends Tuple3 {

	public Point3D() {
		super();
	}

	public Point3D(float x, float y, float z) {
		super(x, y, z);
	}

	/**
	 * Parse a Point3D from a string
	 * @param string   String representation
	 * @return The Point3D represented by string
	 * @throws ParseException string is not a valid Point3D
	 * @see Tuple3#valueOf(String, Tuple3)
	 */
	public static Point3D valueOf(String string) throws ParseException {
		return valueOf(string, new Point3D());
	}
}
