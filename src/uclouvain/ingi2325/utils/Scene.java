package uclouvain.ingi2325.utils;

import java.util.Hashtable;
import java.util.LinkedList;

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
	private Hashtable<String, DiffuseMaterial> diffuse_materials;
	private LinkedList<PointLight> point_lights;

	public Scene() {
		cameras = new LinkedList<Camera>();
		camera_default = -1;
		surfaces = new LinkedList<Surface>();
		diffuse_materials = new Hashtable<String, DiffuseMaterial>();
		point_lights = new LinkedList<PointLight>();
	}

	public void addCamera(Camera c) {
		cameras.add(c);
	}

	public void addSurface(Surface s) {
		surfaces.add(s);
	}

	public void addDiffuseMaterial(DiffuseMaterial d) {
		diffuse_materials.put(d.getName(), d);
	}

	public void addPointLight(PointLight p) {
		point_lights.add(p);
	}

	public void setShape(String geometry, String material) {
		for (Surface s : surfaces)
			if (s.getName().equals(geometry))
				s.setColor(((DiffuseMaterial) diffuse_materials.get(material)).getColor());
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
		cameras.get(camera_default).makeFrame();
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
			float intersection = s.traverse(r, t1);
			if (intersection < t1) {
				t1 = intersection;
				surface = s;
			}
		}
		if (t1 == Float.POSITIVE_INFINITY)
			return background;
		/*if (surface instanceof Sphere)
			return surface.getColor();
		return shade((Triangle) surface, point_lights.get(0));*/
		return surface.shade(point_lights.get(0));
	}

	private Color shade(Triangle t, PointLight p) {
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
	}

}
