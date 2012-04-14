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
		/* TODO: use efficient data structure to optimize the traversal (bounding boxes, uniform spatial subdivision, BSP tree...) */
		float t1 = Float.POSITIVE_INFINITY;
		Surface surface = null;
		for (Surface s : surfaces) {
			float intersection = s.traverse(r, 0.0F, t1);
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
