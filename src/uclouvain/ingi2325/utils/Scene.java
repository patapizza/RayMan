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
	private LinkedList<PointLight> point_lights;

	public Scene() {
		cameras = new LinkedList<Camera>();
		camera_default = -1;
		surfaces = new LinkedList<Surface>();
		materials = new Hashtable<String, Material>();
		point_lights = new LinkedList<PointLight>();
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

	public void addPointLight(PointLight p) {
		point_lights.add(p);
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
		return surface.shade(point_lights.get(0));
	}

	/*private Color shade(Triangle t, PointLight p) {
		Point3D center = t.getCenter();
		Point3D position = p.getPosition();
		Vector3D l = new Vector3D(position.x - center.x, position.y - center.y, position.z - center.z);
		Vector3D n = t.getNormal().normalize();
		Color c = t.getColor();
		
		// Lambertian shading
		float lambertian = p.getIntensity() * Math.max(0, n.dotProductWith(l.normalize()));
		Color r = new Color(c.x * lambertian, c.y * lambertian, c.z * lambertian);

		// Blin-Phong shading
		Vector3D h = getDefaultCamera().getDirection().addWith(l).normalize();
		float blinphong = 0.5F * p.getIntensity() * (float) Math.pow((double) Math.max(0, n.dotProductWith(h)), 100.0);
		r.x += blinphong;
		r.y += blinphong;
		r.z += blinphong;

		// Ambient shading
		r.x += c.x * 0.25;
		r.y += c.y * 0.25;
		r.z += c.z * 0.25;

		// Prevent overflows
		r.x = Math.min(1, r.x);
		r.y = Math.min(1, r.y);
		r.z = Math.min(1, r.z);

		return r;
	}*/

}
