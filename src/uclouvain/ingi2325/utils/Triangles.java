package uclouvain.ingi2325.utils;

import java.util.LinkedList;

import uclouvain.ingi2325.math.Matrix4;

/**
 * Represents a set of triangles
 *
 * @author Julien Odent <julien.odent@student.uclouvain.be>
 */
public class Triangles extends Surface {
	
	private LinkedList<Triangle> triangles;

	public Triangles(LinkedList<Triangle> triangles) {
		this.triangles = triangles;
	}

	public boolean hit(Ray ray, float t0, float t1, HitRecord rec) {
		Ray r_prime = new Ray(ray.getOrigin(), ray.getDirection());
		Matrix4 m;
		float intersection;
		t1 = Float.POSITIVE_INFINITY;
		Surface surface = null;
		for (Triangle t : triangles) {
			m = t.getM();

			// Instance ; applying transformations
			if (m != null) {
				Point3D position = ray.getOrigin();
				Vector3D direction = ray.getDirection();
				Vector4 new_position = m.multiplyWith(new Vector4(position.x, position.y, position.z, 1.0F));
				Vector4 new_direction = m.multiplyWith(new Vector4(direction.x, direction.y, direction.z, 0.0F));
				r_prime.setOrigin(new Point3D(new_position.x, new_position.y, new_position.z));
				r_prime.setDirection(new Vector3D(new_direction.x, new_direction.y, new_direction.z));
				intersection = t.traverse(r_prime, 0.0F, t1);
			}
			else
				intersection = t.traverse(ray, 0.0F, t1);
			if (intersection < t1) {
				t1 = intersection;
				surface = t;
			}
		}
		if (t1 == Float.POSITIVE_INFINITY)
			return false;
		return surface.hit(ray, 0.0F, t1, rec);
	}

	public float traverse(Ray ray, float t0, float t1) {
		return 0.0F;
	}

	public Color shade(Light light) {
		return new Color(0, 0, 0);
	}

}
