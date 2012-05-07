package uclouvain.ingi2325.utils;

/**
 * Represents a hit record
 *
 * @author Julien Odent <julien.odent@student.uclouvain.be>
 */
public class HitRecord {
	
	private float t;
	private Surface s;

	public HitRecord() {
		t = 0.0F;
		s = null;
	}

	public float getT() {
		return t;
	}

	public void setT(float t) {
		this.t = t;
	}

	public Surface getS() {
		return s;
	}

	public void setS(Surface s) {
		this.s = s;
	}

}
