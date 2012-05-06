package uclouvain.ingi2325.utils;

import uclouvain.ingi2325.math.Matrix4;

/**
 * Represents a sphere
 * 
 * @author Julien Odent <julien.odent@student.uclouvain.be>
 */
public class Sphere extends Surface {

	private float radius;
	private Point3D center;

	public Sphere(float radius, String name) {
		this.radius = radius;
		this.name = name;
		center = new Point3D(0, 0, 0);
		hit = new Point3D(0, 0, 0);
	}

	public float traverse(Ray ray, float t0, float t1) {
		Point3D position = ray.getOrigin();
		Vector3D e = new Vector3D(position.x, position.y, position.z);
		Vector3D d = ray.getDirection();
		Vector3D ec = new Vector3D(e.x - center.x, e.y - center.y, e.z - center.z);

		// Reducing number of operations
		float dec = d.dotProductWith(ec);
		float dd = d.dotProductWith(d);

		float discriminant = (float) (Math.pow(2 * dec, 2) - 4 * dd * (ec.dotProductWith(ec) - radius * radius));
		if (discriminant < 0)
			return Float.POSITIVE_INFINITY;
		float root1 = (float) (- 2 * dec - Math.sqrt(discriminant)) / (2 * dd);
		float root2 = (float) (- 2 * dec + Math.sqrt(discriminant)) / (2 * dd);
		if (root1 < t0 || root1 > t1 && root2 < t0 || root2 > t1)
			return Float.POSITIVE_INFINITY;

		float t;
		if (root1 < t0 || root1 > t1)
			t = root2;
		else if (root2 < t0 || root2 > t1 || root1 <= root2)
			t = root1;
		else
			t = root2;

		// Saving hit point's coordinates for later shading
		hit.set(e.x + t * d.x, e.y + t * d.y, e.z + t * d.z);

		return t;
	}

	public Color shade(Light light) {
		Point3D position = ((PointLight) light).getPosition();
		//Vector3D l = (new Vector3D(position.x - hit.x, position.y - hit.y, position.z - hit.z)).normalize();
		Vector3D l = new Vector3D(position.x - hit.x, position.y - hit.y, position.z - hit.z);

		// Shadows
		if (traverse(new Ray(hit, l), 0.042F, Float.POSITIVE_INFINITY) != Float.POSITIVE_INFINITY)
			return new Color(0, 0, 0);

		Vector3D n = (new Vector3D(2 * (hit.x - center.x), 2 * (hit.y - center.y), 2 * (hit.z - center.z))).normalize();
		return material.shade(l, n, light.getIntensity());
	}

	public void transform(Matrix4 m) {
		super.transform(m);
		float x = m.getElement(0, 0) * center.x + m.getElement(0, 1) * center.y + m.getElement(0, 2) * center.z + m.getElement(0, 3) * 1.0F;
		float y = m.getElement(1, 0) * center.x + m.getElement(1, 1) * center.y + m.getElement(1, 2) * center.z + m.getElement(1, 3) * 1.0F;
		float z = m.getElement(2, 0) * center.x + m.getElement(2, 1) * center.y + m.getElement(2, 2) * center.z + m.getElement(2, 3) * 1.0F;
		center = new Point3D(x, y, z);
	}

}
