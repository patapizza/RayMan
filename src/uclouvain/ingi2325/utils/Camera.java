package uclouvain.ingi2325.utils;

/**
 * Represents a camera
 *
 * @author Julien Odent <julien.odent@student.uclouvain.be>
 */
public class Camera {

	private Point3D e;
	private Vector3D direction, up, w, u, v;
	private float fovy;
	private String name;

	public Camera(Point3D position, Vector3D direction, Vector3D up, float fovy, String name) {
		this.e = position;
		this.direction = direction;
		this.up = up;
		this.fovy = fovy;
		this.name = name;
	}

	public Vector3D getDirection() {
		return direction;
	}

	public float getFovy() {
		return fovy;
	}

	public String getName() {
		return name;
	}

	public Point3D getPosition() {
		return e;
	}

	public Vector3D getU() {
		return u;
	}

	public Vector3D getV() {
		return v;
	}

	public Vector3D getW() {
		return w;
	}

	public void makeFrame() {
		w = (new Vector3D(-direction.x, -direction.y, -direction.z)).normalize();
		u = (up.crossProductWith(w)).normalize();
		v = w.crossProductWith(u);
	}
}
