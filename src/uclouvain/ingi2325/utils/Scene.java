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
	private LinkedList<Triangle> triangles;
	private Color background;
	private Hashtable<String, DiffuseMaterial> diffuse_materials;
	private LinkedList<PointLight> point_lights;

	public Scene() {
		cameras = new LinkedList<Camera>();
		camera_default = -1;
		triangles = new LinkedList<Triangle>();
		diffuse_materials = new Hashtable<String, DiffuseMaterial>();
		point_lights = new LinkedList<PointLight>();
	}

	public void addCamera(Camera c) {
		cameras.add(c);
	}

	public void addTriangle(Triangle t) {
		triangles.add(t);
	}

	public void addDiffuseMaterial(DiffuseMaterial d) {
		diffuse_materials.put(d.getName(), d);
	}

	public void addPointLight(PointLight p) {
		point_lights.add(p);
	}

	public void setShape(String geometry, String material) {
		for (Triangle t : triangles)
			if (t.getName().equals(geometry))
				t.setColor(((DiffuseMaterial) diffuse_materials.get(material)).getColor());
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
		Triangle triangle = null;
		for (Triangle t : triangles) {
			float intersection = traverseTriangle(r, t, t1);
			if (intersection < t1) {
				t1 = intersection;
				triangle = t;

			}
		}
		if (t1 == Float.POSITIVE_INFINITY)
			return background;
		return shade(triangle, point_lights.get(0));
	}

	private float traverseTriangle(Ray ray, Triangle triangle, float t1) {
		Point3D[] coordinates = triangle.getCoordinates();
		float a = coordinates[0].x - coordinates[1].x;
		float b = coordinates[0].y - coordinates[1].y;
		float c = coordinates[0].z - coordinates[1].z;
		float d = coordinates[0].x - coordinates[2].x;
		float e = coordinates[0].y - coordinates[2].y;
		float f = coordinates[0].z - coordinates[2].z;
		Vector3D direction = ray.getDirection();
		float g = direction.x;
		float h = direction.y;
		float i = direction.z;
		Point3D position = ray.getOrigin();
		float j = coordinates[0].x - position.x;
		float k = coordinates[0].y - position.y;
		float l = coordinates[0].z - position.z;
		float M = a * (e * i - h * f) + b * (g * f - d * i) + c * (d * h - e * g);
		float t = - (f * (a * k - j * b) + e * (j * c - a * l) + d * (b * l - k * c)) / M;
		if (t < 0 || t > t1)
			return Float.POSITIVE_INFINITY;
		float gamma = (i * (a * k - j * b) + h * (j * c - a * l) + g * (b * l - k * c)) / M;
		if (gamma < 0 || gamma > 1)
			return Float.POSITIVE_INFINITY;
		float beta = (j * (e * i - h * f) + k * (g * f - d * i) + l * (d * h - e * g)) / M;
		if (beta < 0 || beta > 1 - gamma)
			return Float.POSITIVE_INFINITY;
		return t;
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
