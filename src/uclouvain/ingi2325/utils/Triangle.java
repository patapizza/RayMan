package uclouvain.ingi2325.utils;

import uclouvain.ingi2325.exception.*;

/**
 * Represents a triangle
 * 
 * @author Julien Odent <julien.odent@student.uclouvain.be>
 */
public class Triangle {

	private Point3D[] coordinates;
	private Vector3D[] normals;
	private TextureCoordinates[] textureCoordinates;
	private String name;
	private Color color;

	public Triangle(Point3D[] coordinates, Vector3D[] normals, TextureCoordinates[] textureCoordinates, String name) {
		this.coordinates = coordinates;
		this.normals = normals;
		this.textureCoordinates = textureCoordinates;
		this.name = name;
	}

	public Point3D[] getCoordinates() {
		return coordinates;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Color getColor() {
		return color;
	}

	public String getName() {
		return name;
	}

	public Vector3D getNormal() {
		return new Vector3D((normals[0].x + normals[1].x + normals[2].x) / 3, (normals[0].y + normals[1].y + normals[2].y) / 3, (normals[0].z + normals[1].z + normals[2].z) / 3);
	}

	public Point3D getCenter() {
		return new Point3D((coordinates[0].x + coordinates[1].x + coordinates[2].x) / 3, (coordinates[0].y + coordinates[1].y + coordinates[2].y) / 3, (coordinates[0].z + coordinates[1].z + coordinates[2].z) / 3);
	}

}
