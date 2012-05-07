package uclouvain.ingi2325.utils;

import uclouvain.ingi2325.math.Tuple3;

/**
 * Represents a bounding box
 *
 * @author Julien Odent <julien.odent@student.uclouvain.be>
 */
public class BoundingBox {

	private Tuple3 min;
	private Tuple3 max;

	public BoundingBox(Tuple3[] vertices) {
		float min_x = vertices[0].x;
		float max_x = vertices[0].x;
		float min_y = vertices[0].y;
		float max_y = vertices[0].y;
		float min_z = vertices[0].z;
		float max_z = vertices[0].z;
		for (int i = 1 ; i < vertices.length ; i++) {
			if (vertices[i].x < min_x)
				min_x = vertices[i].x;
			else if (vertices[i].x > max_x)
				max_x = vertices[i].x;
			if (vertices[i].y < min_y)
				min_y = vertices[i].y;
			else if (vertices[i].y > max_y)
				max_y = vertices[i].y;
			if (vertices[i].z < min_z)
				min_z = vertices[i].z;
			else if (vertices[i].z > max_z)
				max_z = vertices[i].z;
		}
		this.min = new Point3D(min_x, min_y, min_z);
		this.max = new Point3D(max_x, max_y, max_z);
	}

	public Tuple3 getMin() {
		return min;
	}

	public Tuple3 getMax() {
		return max;
	}

}
