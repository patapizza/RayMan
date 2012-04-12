package uclouvain.ingi2325.utils;

import uclouvain.ingi2325.exception.*;

/**
 * Represents a sphere
 * 
 * @author Julien Odent <julien.odent@student.uclouvain.be>
 */
public class Sphere extends Surface {

	private float radius;
	private Vector3D hit;

	public Sphere(float radius, String name) {
		this.radius = radius;
		this.name = name;
		hit = new Vector3D(0, 0, 0);
	}

	public float traverse(Ray ray, float t1) {
		Point3D position = ray.getOrigin();
		Vector3D e = new Vector3D(position.x, position.y, position.z);
		Vector3D d = ray.getDirection();
		Vector3D ec = new Vector3D(e.x - 0, e.y - 0, e.z - 0);

		// Reducing number of operations
		float dec = d.dotProductWith(ec);
		float dd = d.dotProductWith(d);

		float discriminant = (float) (Math.pow(2 * dec, 2) - 4 * dd * (ec.dotProductWith(ec) - radius * radius));
		if (discriminant < 0)
			return Float.POSITIVE_INFINITY;
		float root1 = (float) (- 2 * dec - Math.sqrt(discriminant)) / (2 * dd);
		float root2 = (float) (- 2 * dec + Math.sqrt(discriminant)) / (2 * dd);
		if (root1 < 0 || root1 > t1 && root2 < 0 || root2 > t1)
			return Float.POSITIVE_INFINITY;

		float t;
		if (root1 < 0 || root1 > t1)
			t = root2;
		else if (root2 < 0 || root2 > t1 || root1 <= root2)
			t = root1;
		else
			t = root2;

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
