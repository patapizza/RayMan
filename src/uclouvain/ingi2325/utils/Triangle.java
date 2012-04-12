package uclouvain.ingi2325.utils;

import uclouvain.ingi2325.exception.*;

/**
 * Represents a triangle
 * 
 * @author Julien Odent <julien.odent@student.uclouvain.be>
 */
public class Triangle extends Surface {

	private Point3D[] coordinates;
	private Vector3D[] normals;
	private TextureCoordinates[] textureCoordinates;

	public Triangle(Point3D[] coordinates, Vector3D[] normals, TextureCoordinates[] textureCoordinates, String name) {
		this.coordinates = coordinates;
		this.normals = normals;
		this.textureCoordinates = textureCoordinates;
		this.name = name;
	}

	public Point3D[] getCoordinates() {
		return coordinates;
	}

	public Vector3D getNormal() {
		return new Vector3D((normals[0].x + normals[1].x + normals[2].x) / 3, (normals[0].y + normals[1].y + normals[2].y) / 3, (normals[0].z + normals[1].z + normals[2].z) / 3);
	}

	public Point3D getCenter() {
		return new Point3D((coordinates[0].x + coordinates[1].x + coordinates[2].x) / 3, (coordinates[0].y + coordinates[1].y + coordinates[2].y) / 3, (coordinates[0].z + coordinates[1].z + coordinates[2].z) / 3);
	}

	public float traverse(Ray ray, float t1) {
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

		// Reducing number of operations
		float eihf = e * i - h * f;
		float gfdi = g * f - d * i;
		float dheg = d * h - e * g;
		float akjb = a * k - j * b;
		float jcal = j * c - a * l;
		float blkc = b * l - k * c;

		float M = a * eihf + b * gfdi + c * dheg;
		float t = - (f * akjb + e * jcal + d * blkc) / M;
		if (t < 0 || t > t1)
			return Float.POSITIVE_INFINITY;
		float gamma = (i * akjb + h * jcal + g * blkc) / M;
		if (gamma < 0 || gamma > 1)
			return Float.POSITIVE_INFINITY;
		float beta = (j * eihf + k * gfdi + l * dheg) / M;
		if (beta < 0 || beta > 1 - gamma)
			return Float.POSITIVE_INFINITY;
		return t;
	}

	public Color shade(PointLight p) {
		Point3D center = getCenter();
		Point3D position = p.getPosition();
		Vector3D l = new Vector3D(position.x - center.x, position.y - center.y, position.z - center.z);
		Vector3D n = getNormal().normalize();
		return material.shade(l, n, p.getIntensity());
	}

}
