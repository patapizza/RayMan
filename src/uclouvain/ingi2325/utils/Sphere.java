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
		float t = (float) (- dec - Math.sqrt(discriminant)) / dd;
		if (t < 0 || t > t1)
			return Float.POSITIVE_INFINITY;

		// Saving hit point's coordinates for later shading
		hit.set(e.x + t * d.x, e.y + t * d.y, e.z + t * d.z);

		return t;
	}

	public Color shade(PointLight p) {
		Point3D position = p.getPosition();
		Vector3D l = new Vector3D(position.x - 0, position.y - 0, position.z - 0);
		Vector3D n = (new Vector3D(2 * (hit.x - 0), 2 * (hit.y - 0), 2 * (hit.z - 0))).normalize();
		float lambertian = p.getIntensity() * Math.max(0, n.dotProductWith(l.normalize()));
		return new Color(color.x * lambertian, color.y * lambertian, color.z * lambertian);
	}

}
