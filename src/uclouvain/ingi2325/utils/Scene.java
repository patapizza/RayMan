package uclouvain.ingi2325.utils;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Random;

import uclouvain.ingi2325.math.Matrix4;
import uclouvain.ingi2325.math.Tuple3;

/**
 * Represents a scene
 * 
 * @author Julien Odent <julien.odent@student.uclouvain.be>
 */
public class Scene {

	private LinkedList<Camera> cameras;
	private int camera_default;
	private LinkedList<Surface> surfaces;
	private LinkedList<Triangle> triangles;
	private Color background;
	private Hashtable<String, Material> materials;
	private LinkedList<Light> lights;
	private BSPNode bsp;
	private float min, max;

	public Scene() {
		cameras = new LinkedList<Camera>();
		camera_default = -1;
		surfaces = new LinkedList<Surface>();
		triangles = new LinkedList<Triangle>();
		materials = new Hashtable<String, Material>();
		lights = new LinkedList<Light>();
	}

	public void addCamera(Camera c) {
		cameras.add(c);
	}

	public void addSurface(Surface s) {
		if (s instanceof Triangle)
			triangles.add((Triangle) s);
		else
			surfaces.add(s);
	}

	public void addMaterial(Material m) {
		materials.put(m.getName(), m);
	}

	public void addLinearCombinedMaterial(String m1, float w1, String m2, float w2, String name) {
		materials.put(name, new LinearCombinedMaterial(materials.get(m1), w1, materials.get(m2), w2, name));
	}

	public void addLight(Light l) {
		lights.add(l);
	}

	public void setShape(String geometry, String material, Matrix4 m) {
		surfaces.addAll(triangles);
		for (Surface s : surfaces)
			if (s.getName().equals(geometry)) {
				s.setMaterial(materials.get(material));
				if (m != null)
					s.transform(m);
			}
		surfaces.removeAll(triangles);
	}

	public void setBackground(Color c) {
		background = c;
	}

	public void setDefaultCamera(String s) {
		for (Camera c : cameras)
			if (c.getName().equals(s)) {
				camera_default = cameras.indexOf(c);
				break;
			}
		Camera c = cameras.get(camera_default);
		c.makeFrame();

		// Setting dir vector for phong materials
		Vector3D direction = c.getDirection();
		direction.set(- direction.x, - direction.y, - direction.z);

		Enumeration e = materials.keys();
		Material m;
		while (e.hasMoreElements()) {
			m = (Material) materials.get((String) e.nextElement());
			if (m instanceof PhongMaterial)
				((PhongMaterial) m).setDirection(direction);
		}
	}

	public Camera getDefaultCamera() {
		if (camera_default == -1)
			return null;
		return cameras.get(camera_default);
	}

	public Color traverse(Ray r) {
		// computing new ray if instance
		Ray r_prime = new Ray(r.getOrigin(), r.getDirection());
		Matrix4 m;

		float intersection = Float.POSITIVE_INFINITY;
		float t1 = Float.POSITIVE_INFINITY;
		Surface surface = null;
		for (Surface s : surfaces) {
			m = s.getM();

			// Instance ; applying transformations
			if (m != null) {
				Point3D position = r.getOrigin();
				Vector3D direction = r.getDirection();
				Vector4 new_position = m.multiplyWith(new Vector4(position.x, position.y, position.z, 1.0F));
				Vector4 new_direction = m.multiplyWith(new Vector4(direction.x, direction.y, direction.z, 0.0F));
				r_prime.setOrigin(new Point3D(new_position.x, new_position.y, new_position.z));
				r_prime.setDirection(new Vector3D(new_direction.x, new_direction.y, new_direction.z));
				intersection = s.traverse(r_prime, 0.0F, t1);
			}
			else
				intersection = s.traverse(r, 0.0F, t1);
			if (intersection < t1) {
				t1 = intersection;
				surface = s;
			}
		}

		HitRecord rec = new HitRecord();
		if (bsp.hit(r, min, max, rec))
			intersection = rec.getT();
		if (intersection < t1) {
			t1 = intersection;
			surface = rec.getS();
		}

		if (t1 == Float.POSITIVE_INFINITY)
			return background;
		
		// Shading
		Color res = new Color(0, 0, 0);
		for (Light l : lights)
			res.addWith(surface.shade(l));

		// Ambient shading
		Color ambient = surface.getAmbient();
		res.addWith(ambient);

		// Preventing overflows
		res.x = Math.min(1, res.x);
		res.y = Math.min(1, res.y);
		res.z = Math.min(1, res.z);

		return res;
	}

	public void buildBSPTree() {
		 /*
		 * d[axis][boundary]
		 * axis == 0 -> x
		 * axis == 1 -> y
		 * axis == 2 -> z
		 * boundary == 0 -> lower/min
		 * boundary == 1 -> upper/max
		 */
		float[][] d = new float[3][2];
		for (int i = 0 ; i < 3 ; i++) {
			d[i][0] = 100.0F;
			d[i][1] = -100.0F;
		}

		BoundingBox bb;
		Tuple3 min, max;
		for (Triangle t : triangles) {
			bb = t.getBoundingBox();
			min = bb.getMin();
			if (min.x < d[0][0])
				d[0][0] = min.x;
			if (min.y < d[1][0])
				d[1][0] = min.y;
			if (min.z < d[2][0])
				d[2][0] = min.z;
			max = bb.getMax();
			if (max.x > d[0][1])
				d[0][1] = max.x;
			if (max.y > d[1][1])
				d[1][1] = max.y;
			if (max.z > d[2][1])
				d[2][1] = max.z;
		}
		this.min = d[0][0];
		this.max = d[0][1];
		
		//float mid = (this.min + this.max) / 2;
		float mid = this.min - 0.00042F;

		LinkedList<Triangle> left_side = new LinkedList<Triangle>();
		LinkedList<Triangle> right_side = new LinkedList<Triangle>();
		for (Triangle t : triangles) {
			bb = t.getBoundingBox();
			if (bb.getMin().x <= mid)
				left_side.add(t);
			if (bb.getMax().x >= mid)
				right_side.add(t);
		}
		bsp = new BSPNode(buildBSPTree(left_side, this.min, mid, 0, d, 10), buildBSPTree(right_side, mid, this.max, 0, d, 10), null, mid, 0);
	}

	private Surface buildBSPTree(LinkedList<Triangle> triangles, float d_min, float d_max, int axis, float[][] d, int maxdepth) {
		if (--maxdepth == 0 || triangles.size() < 30)
			return new Triangles(triangles);
		/*int size = triangles.size();
		if (size < 100)
			return new Triangles(triangles);*/
		
		float[][] d_new = new float[3][2];
		for (int i = 0 ; i < 3 ; i++) {
			d_new[i][0] = d[i][0];
			d_new[i][1] = d[i][1];
		}
		d_new[axis][0] = d_min;
		d_new[axis][1] = d_max;
		//System.out.println("Axis: "+axis+" ["+d_min+","+d_max+"]");
		LinkedList<Triangle> left_side = new LinkedList<Triangle>();
		LinkedList<Triangle> right_side = new LinkedList<Triangle>();
		int axis_new = (axis + 1) % 3;
		//float mid = (d_new[axis_new][0] + d_new[axis_new][1]) / 2;
		int rand = (new Random()).nextInt(1);
		float mid = (rand == 0) ? d_new[axis_new][rand] - 0.00042F : d_new[axis_new][rand] + 0.00042F;
		//float mid = d_new[axis_new][(new Random()).nextInt(1)] + 0.00042F;
		BoundingBox bb;
		for (Triangle t : triangles) {
			bb = t.getBoundingBox();
			if (bb.getMin().get(axis) <= mid)
				left_side.add(t);
			if (bb.getMax().get(axis) >= mid)
				right_side.add(t);
		}
		//System.out.println("Axis_new: "+axis_new+" Left: ["+d_new[axis_new][0]+","+mid+"] -> "+left_side.size()+" Right: ["+mid+","+d_new[axis_new][1]+"] -> "+right_side.size()+")");
		return new BSPNode(buildBSPTree(left_side, d_new[axis_new][0], mid, axis_new, d_new, maxdepth), buildBSPTree(right_side, mid, d_new[axis_new][1], axis_new, d_new, maxdepth), null, mid, axis_new);
	}

}
