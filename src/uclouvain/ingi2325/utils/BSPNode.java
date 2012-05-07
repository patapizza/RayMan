package uclouvain.ingi2325.utils;

/**
 * Represents a BSP node
 *
 * @author Julien Odent <julien.odent@student.uclouvain.be>
 */
public class BSPNode extends Surface {

	private Surface left;
	private Surface right;
	private Triangle triangle;
	private float d;

	public BSPNode(Surface left, Surface right, Triangle triangle, float d) {
		this.left = left;
		this.right = right;
		this.triangle = triangle;
		this.d = d;
	}

	public Surface hit(Ray r, float t0, float t1) {
		Vector3D direction = r.getDirection();
		float xa = (r.getOrigin()).x;
		float xb = direction.x;
		float xp = xa + t0 * xb;
		float t;
		if (xp < d) {
			if (xb < 0) {
				//return left != null && left.hit(r, t0, t1);
				if (left == null)
					return null;
				return ((BSPNode) left).hit(r, t0, t1);
			}
			t = (d - xa) / xb;
			r.setDirection(new Vector3D(t * direction.x, t * direction.y, t * direction.z));
			if (t > t1) {
				//return left != null && left.hit(r, t0, t1);
				if (left == null)
					return null;
				return ((BSPNode) left).hit(r, t0, t1);
			}
			// if (left != null && left.hit(r, t0, t)) return true;
			if (left != null) {
				Surface s = ((BSPNode) left).hit(r, t0, t);
				if (s != null)
					return s;
			}
			// return right != null && right.hit(r, t, t1);
			if (right == null)
				return null;
			return ((BSPNode) right).hit(r, t, t1);
		}
		if (xb > 0) {
			if (right == null)
				return null;
			return ((BSPNode) right).hit(r, t0, t1);
		}
		// else : xp > d
		t = (d - xa) / xb;
		r.setDirection(new Vector3D(t * direction.x, t * direction.y, t * direction.z));
		if (t < t0) {
			if (right == null)
				return null;
			return ((BSPNode) right).hit(r, t0, t1);
		}
		if (right != null) {
			Surface s = ((BSPNode) right).hit(r, t, t1);
			if (s != null)
				return s;
		}
		if (left == null)
			return null;
		return ((BSPNode) left).hit(r, t0, t);
	}

	public Color shade(Light light) {
		return new Color(0, 0, 0);
	}

	public float traverse(Ray r, float t0, float t1) {
		return 0.0F;
	}

}
