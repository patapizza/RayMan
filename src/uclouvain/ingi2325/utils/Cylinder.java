package uclouvain.ingi2325.utils;

import uclouvain.ingi2325.exception.*;

/**
 * Represents a sphere
 * 
 * @author Julien Odent <julien.odent@student.uclouvain.be>
 */
public class Cylinder extends Surface {

	private float radius;
	private float height;
	private boolean capped;
	private Vector3D hit;

	public Cylinder(float radius, float height, boolean capped, String name) {
		this.radius = radius;
		this.height = height;
		this.capped = capped;
		this.name = name;
		hit = new Vector3D(0, 0, 0);
	}

	public float traverse(Ray ray, float t1) {
		Point3D position = ray.getOrigin();
		Vector3D e = new Vector3D(position.x, position.y, position.z);
		Vector3D d = ray.getDirection();

		// Reducing number of operations
		float a = d.x * d.x + d.z * d.z;
		float b = 2 * (e.x * d.x + e.z * d.z);
		float c = e.x * e.x + e.z * e.z - radius * radius;

		float discriminant = b * b - 4 * a * c;
		float root1, root2;
		if (discriminant < 0) {
			root1 = Float.POSITIVE_INFINITY;
			root2 = Float.POSITIVE_INFINITY;
		}
		else {
			root1 = (float) ((- b - Math.sqrt(discriminant)) / (2 * a));
			if (root1 < 0 || root1 > t1)
				root1 = Float.POSITIVE_INFINITY;
			root2 = (float) ((- b + Math.sqrt(discriminant)) / (2 * a));
			if (root2 < 0 || root2 > t1)
				root2 = Float.POSITIVE_INFINITY;
		}

		// Checking if it satisfies 0 <= y <= h
		float t;
		if (root1 == Float.POSITIVE_INFINITY) {
			float y = e.y + root2 * d.y;
			if (y < 0 || y > height)
				t = Float.POSITIVE_INFINITY;
			else
				t = root2;
		}
		else if (root2 == Float.POSITIVE_INFINITY) {
			float y = e.y + root1 * d.y;
			if (y < 0 || y > height)
				t = Float.POSITIVE_INFINITY;
			else
				t = root1;
		}
		else {
			float y1 = e.y + root1 * d.y;
			float y2 = e.y + root2 * d.y;
			if (y1 < 0 || y1 > height && y2 < 0 || y2 > height)
				t = Float.POSITIVE_INFINITY;
			else if (y1 < 0 || y1 > height)
				t = root2;
			else if (y2 < 0 || y2 > height)
				t = root1;
			else {
				if (root1 <= root2)
					t = root1;
				else
					t = root2;
			}
		}

		// Intersecting with the caps
		if (capped) {
			float top, bottom;
			top = (height - e.y) / d.y;
			if (top < 0)
				top = Float.POSITIVE_INFINITY;
			else {
				float x = e.x + top * d.x;
				float z = e.z + top * d.z;
				if (x * x + z * z > radius * radius)
					top = Float.POSITIVE_INFINITY;
			}
			if (top < t)
				t = top;
			bottom = - e.y / d.y;
			if (bottom < 0)
				bottom = Float.POSITIVE_INFINITY;
			else {
				float x = e.x + bottom * d.x;
				float z = e.z + bottom * d.z;
				if (x * x + z * z > radius * radius)
					bottom = Float.POSITIVE_INFINITY;
			}
			if (bottom < t)
				t = bottom;
		}

		// Saving hit point's coordinates for later shading
		hit.set(e.x + t * d.x, e.y + t * d.y, e.z + t * d.z);

		return t;
	}

	public Color shade(PointLight p) {
		Point3D position = p.getPosition();
		Vector3D l = new Vector3D(position.x - 0, position.y - 0, position.z - 0);
		Vector3D n = (new Vector3D(2 * (hit.x - 0), 2 * (hit.y - 0), 2 * (hit.z - 0))).normalize();
		return material.shade(l, n, p.getIntensity());
	}

}
