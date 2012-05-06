package uclouvain.ingi2325.utils;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.LinkedList;

import uclouvain.ingi2325.math.Matrix4;

/**
 * Represents a scene
 * 
 * @author Julien Odent <julien.odent@student.uclouvain.be>
 */
public class Scene {

	private LinkedList<Camera> cameras;
	private int camera_default;
	private LinkedList<Surface> surfaces;
	private Color background;
	private Hashtable<String, Material> materials;
	private LinkedList<Light> lights;

	public Scene() {
		cameras = new LinkedList<Camera>();
		camera_default = -1;
		surfaces = new LinkedList<Surface>();
		materials = new Hashtable<String, Material>();
		lights = new LinkedList<Light>();
	}

	public void addCamera(Camera c) {
		cameras.add(c);
	}

	public void addSurface(Surface s) {
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
		for (Surface s : surfaces)
			if (s.getName().equals(geometry)) {
				s.setMaterial(materials.get(material));
				if (m != null)
					s.transform(m);
			}
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

		float intersection;
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

}
