package uclouvain.ingi2325.utils;

import uclouvain.ingi2325.math.Matrix3;
import uclouvain.ingi2325.math.Matrix4;

/**
 * Represents a cylinder
 * 
 * @author Julien Odent <julien.odent@student.uclouvain.be>
 */
public class Cylinder extends Surface {

	private float radius;
	private float height;
	private boolean capped;
	private Point3D center;

	public Cylinder(float radius, float height, boolean capped, String name) {
		this.radius = radius;
		this.height = height;
		this.capped = capped;
		this.name = name;
		center = new Point3D(0, 0, 0);
		hit = new Point3D(0, 0, 0);
	}

	public float traverse(Ray ray, float t0, float t1) {
		Point3D position = ray.getOrigin();
		Vector3D e = new Vector3D(position.x + center.x, position.y + center.y, position.z + center.z);
		Vector3D d = ray.getDirection();

		// Reducing number of operations
		float a = d.x * d.x + d.z * d.z;
		float b = 2 * (e.x * d.x + e.z * d.z);
		float c = e.x * e.x + e.z * e.z - radius * radius;

		float discriminant = b * b - 4 * a * c;
		float root1, root2;
		if (discriminant < 0)
			return Float.POSITIVE_INFINITY;
		root1 = (float) ((- b - Math.sqrt(discriminant)) / (2 * a));
		root2 = (float) ((- b + Math.sqrt(discriminant)) / (2 * a));
		if (root1 < t0 || root1 > t1 && root2 < t0 || root2 > t1)
			return Float.POSITIVE_INFINITY;
		if (root1 < t0 || root1 > t1)
			root1 = Float.POSITIVE_INFINITY;
		if (root2 < t0 || root2 > t1)
			root2 = Float.POSITIVE_INFINITY;

		// Checking if it satisfies 0 <= y <= h
		float t;
		if (root1 == Float.POSITIVE_INFINITY) {
			float y = e.y + root2 * d.y;
			if (y < center.y || y > center.y + height)
				return Float.POSITIVE_INFINITY;
			t = root2;
		}
		else if (root2 == Float.POSITIVE_INFINITY) {
			float y = e.y + root1 * d.y;
			if (y < center.y || y > center.y + height)
				return Float.POSITIVE_INFINITY;
			t = root1;
		}
		else {
			float y1 = e.y + root1 * d.y;
			float y2 = e.y + root2 * d.y;
			if (y1 < center.y || y1 > center.y + height && y2 < center.y || y2 > center.y + height)
				return Float.POSITIVE_INFINITY;
			if (y1 < center.y || y1 > center.y + height)
				t = root2;
			else if (y2 < center.y || y2 > center.y + height)
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

	public boolean hit(Ray ray, float t0, float t1, HitRecord rec) {
		float t = traverse(ray, t0, t1);
		if (t == Float.POSITIVE_INFINITY)
			return false;
		rec.setT(t);
		rec.setS(this);
		return true;
	}

	public Color shade(Light light) {
		Point3D position = ((PointLight) light).getPosition();
		Vector3D l = new Vector3D(position.x - hit.x, position.y - hit.y, position.z - hit.z);

		// Shadows
		if (traverse(new Ray(hit, l), 0.042F, Float.POSITIVE_INFINITY) != Float.POSITIVE_INFINITY)
			return new Color(0, 0, 0);
		
		// Computing transformed normal vector
		Vector3D n = (new Vector3D(2 * (hit.x - 0), 2 * (hit.y - 0), 2 * (hit.z - 0))).normalize();
		Matrix4 m4 = getM();
		if (m4 != null) {
			Matrix3 m  = (new Matrix3(getM())).normalize();
			float x = m.getElement(0, 0) * n.x + m.getElement(0, 1) * n.y + m.getElement(0, 2) * n.z;
			float y = m.getElement(1, 0) * n.x + m.getElement(1, 1) * n.y + m.getElement(1, 2) * n.z;
			float z = m.getElement(2, 0) * n.x + m.getElement(2, 1) * n.y + m.getElement(2, 2) * n.z;
			n = (new Vector3D(x, y, z)).normalize();
		}

		return material.shade(l, n, light.getIntensity());
	}

	public void transform(Matrix4 m) {
		super.transform(m);

		// Recomputing center
		float x = m.getElement(0, 0) * center.x + m.getElement(0, 1) * center.y + m.getElement(0, 2) * center.z + m.getElement(0, 3) * 1.0F;
		float y = m.getElement(1, 0) * center.x + m.getElement(1, 1) * center.y + m.getElement(1, 2) * center.z + m.getElement(1, 3) * 1.0F;
		float z = m.getElement(2, 0) * center.x + m.getElement(2, 1) * center.y + m.getElement(2, 2) * center.z + m.getElement(2, 3) * 1.0F;
		center = new Point3D(x, y, z);
	}

}
