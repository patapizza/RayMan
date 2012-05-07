package uclouvain.ingi2325.utils;

import java.util.LinkedList;

/**
 * Represents a BSP node
 *
 * @author Julien Odent <julien.odent@student.uclouvain.be>
 */
public class BSPNode extends Surface {

	private Surface left;
	private Surface right;
	private LinkedList<Triangle> triangles;
	private float d;
	private int axis;

	public BSPNode(Surface left, Surface right, LinkedList<Triangle> triangles, float d, int axis) {
		this.left = left;
		this.right = right;
		this.triangles = triangles;
		this.d = d;
		this.axis = axis;
	}

	public boolean hit(Ray r, float t0, float t1, HitRecord rec) {
		Vector3D direction = r.getDirection();
		float xa = (r.getOrigin()).get(axis);
		float xb = direction.get(axis);
		float xp = xa + t0 * xb;
		float t;
		if (xp < d) {
			if (xb < 0)
				return left != null && left.hit(r, t0, t1, rec);
			t = (d - xa) / xb;
			r.setDirection(new Vector3D(t * direction.x, t * direction.y, t * direction.z));
			if (t > t1)
				return left != null && left.hit(r, t0, t1, rec);
			if (left != null && left.hit(r, t0, t, rec))
				return true;
			return right != null && right.hit(r, t, t1, rec);
		}
		else {
			if (xb > 0)
				return right != null && right.hit(r, t0, t1, rec);
			t = (d - xa) / xb;
			r.setDirection(new Vector3D(t * direction.x, t * direction.y, t * direction.z));
			if (t < t0)
				return right != null && right.hit(r, t0, t1, rec);
			if (right != null && right.hit(r, t0, t, rec))
				return true;
			return left != null && left.hit(r, t0, t, rec);
		}
	}

	public Color shade(Light light) {
		return new Color(0, 0, 0);
	}

	public float traverse(Ray r, float t0, float t1) {
		return 0.0F;
	}

}
