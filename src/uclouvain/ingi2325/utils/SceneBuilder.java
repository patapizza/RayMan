package uclouvain.ingi2325.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

import org.xml.sax.InputSource;

import uclouvain.ingi2325.parser.Parser;
import uclouvain.ingi2325.parser.ParserHandler;

/**
 * Represents a builder for scene
 * 
 * @author Antoine Cailliau <antoine.cailliau@uclouvain.be>
 * @author Julien Dupuis
 * @author Julien Odent <julien.odent@student.uclouvain.be>
 */
public class SceneBuilder implements ParserHandler {

	/**
	 * The scene being build
	 */
	private Scene scene = null;

	/**
	 * Returns the build scene
	 * 
	 * @return the build scene
	 */
	public Scene getScene() {
		return scene;
	}

	/**
	 * The path to the xml directory. This path can be used to locate texture
	 * files.
	 */
	private String path = null;

	/**
	 * Returns the path
	 * 
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * Load a scene.
	 * 
	 * @param filename
	 *            The name of the file that contains the scene.
	 * @return The scene, or null if something went wrong.
	 * @throws FileNotFoundException
	 *             The file corresponding to the given filename could not be
	 *             found.
	 */
	public Scene loadScene(String filename) throws FileNotFoundException {

		File file = new File(filename);
		FileInputStream fileInputStream = new FileInputStream(file);

		InputSource inputSource = new InputSource(fileInputStream);
		path = file.getParentFile().getAbsolutePath() + "/";
		inputSource.setSystemId("file:///"
				+ file.getParentFile().getAbsolutePath() + "/");

		scene = new Scene();

		Parser parser = new Parser();
		parser.addHandler(this);

		if (!parser.parse(inputSource, /* validate */ true, /* echo */ false)) {
			scene = null;
		}

		return scene;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uclouvain.ingi2325.parser.ParserHandler#startSdl()
	 */
	@Override
	public void startSdl() throws Exception {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uclouvain.ingi2325.parser.ParserHandler#endSdl()
	 */
	@Override
	public void endSdl() throws Exception {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uclouvain.ingi2325.parser.ParserHandler#startCameras()
	 */
	@Override
	public void startCameras() throws Exception {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uclouvain.ingi2325.parser.ParserHandler#endCameras()
	 */
	@Override
	public void endCameras() throws Exception {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * uclouvain.ingi2325.parser.ParserHandler#startCamera(uclouvain.ingi2325
	 * .utils.Point3D, uclouvain.ingi2325.utils.Vector3,
	 * uclouvain.ingi2325.utils.Vector3, float, java.lang.String)
	 */
	@Override
	public void startCamera(Point3D position, Vector3D direction, Vector3D up,
			float fovy, String name) throws Exception {
		scene.addCamera(new Camera(position, direction, up, fovy, name));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uclouvain.ingi2325.parser.ParserHandler#endCamera()
	 */
	@Override
	public void endCamera() throws Exception {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uclouvain.ingi2325.parser.ParserHandler#startLights()
	 */
	@Override
	public void startLights() throws Exception {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uclouvain.ingi2325.parser.ParserHandler#endLights()
	 */
	@Override
	public void endLights() throws Exception {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * uclouvain.ingi2325.parser.ParserHandler#startDirectionalLight(uclouvain
	 * .ingi2325.utils.Vector3, float, uclouvain.ingi2325.utils.Color,
	 * java.lang.String)
	 */
	@Override
	public void startDirectionalLight(Vector3D direction, float intensity,
			Color color, String name) throws Exception {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uclouvain.ingi2325.parser.ParserHandler#endDirectionalLight()
	 */
	@Override
	public void endDirectionalLight() throws Exception {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * uclouvain.ingi2325.parser.ParserHandler#startPointLight(uclouvain.ingi2325
	 * .utils.Point3D, float, uclouvain.ingi2325.utils.Color, java.lang.String)
	 */
	@Override
	public void startPointLight(Point3D position, float intensity, Color color,
			String name) throws Exception {
		scene.addPointLight(new PointLight(position, intensity, color, name));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uclouvain.ingi2325.parser.ParserHandler#endPointLight()
	 */
	@Override
	public void endPointLight() throws Exception {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * uclouvain.ingi2325.parser.ParserHandler#startSpotLight(uclouvain.ingi2325
	 * .utils.Point3D, uclouvain.ingi2325.utils.Vector3, float, float,
	 * uclouvain.ingi2325.utils.Color, java.lang.String)
	 */
	@Override
	public void startSpotLight(Point3D position, Vector3D direction,
			float angle, float intensity, Color color, String name)
			throws Exception {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uclouvain.ingi2325.parser.ParserHandler#endSpotLight()
	 */
	@Override
	public void endSpotLight() throws Exception {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uclouvain.ingi2325.parser.ParserHandler#startGeometry()
	 */
	@Override
	public void startGeometry() throws Exception {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uclouvain.ingi2325.parser.ParserHandler#endGeometry()
	 */
	@Override
	public void endGeometry() throws Exception {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uclouvain.ingi2325.parser.ParserHandler#startSphere(float,
	 * java.lang.String)
	 */
	@Override
	public void startSphere(float radius, String name) throws Exception {
		scene.addSurface(new Sphere(radius, name));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uclouvain.ingi2325.parser.ParserHandler#endSphere()
	 */
	@Override
	public void endSphere() throws Exception {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uclouvain.ingi2325.parser.ParserHandler#startCylinder(float, float,
	 * boolean, java.lang.String)
	 */
	@Override
	public void startCylinder(float radius, float height, boolean capped,
			String name) throws Exception {
		scene.addSurface(new Cylinder(radius, height, capped, name));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uclouvain.ingi2325.parser.ParserHandler#endCylinder()
	 */
	@Override
	public void endCylinder() throws Exception {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uclouvain.ingi2325.parser.ParserHandler#startCone(float, float,
	 * boolean, java.lang.String)
	 */
	@Override
	public void startCone(float radius, float height, boolean capped,
			String name) throws Exception {
		scene.addSurface(new Cone(radius, height, capped, name));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uclouvain.ingi2325.parser.ParserHandler#endCone()
	 */
	@Override
	public void endCone() throws Exception {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uclouvain.ingi2325.parser.ParserHandler#startTorus(float, float,
	 * java.lang.String)
	 */
	@Override
	public void startTorus(float innerRadius, float outerRadius, String name)
			throws Exception {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uclouvain.ingi2325.parser.ParserHandler#endTorus()
	 */
	@Override
	public void endTorus() throws Exception {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uclouvain.ingi2325.parser.ParserHandler#startTeapot(float,
	 * java.lang.String)
	 */
	@Override
	public void startTeapot(float size, String name) throws Exception {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uclouvain.ingi2325.parser.ParserHandler#endTeapot()
	 */
	@Override
	public void endTeapot() throws Exception {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * uclouvain.ingi2325.parser.ParserHandler#startIndexedTriangleSet(uclouvain
	 * .ingi2325.utils.Point3D[], uclouvain.ingi2325.utils.Vector3[],
	 * uclouvain.ingi2325.utils.TextureCoordinates[], int[], int[], int[],
	 * java.lang.String)
	 */
	@Override
	public void startIndexedTriangleSet(Point3D[] coordinates,
			Vector3D[] normals, TextureCoordinates[] textureCoordinates,
			int[] coordinateIndices, int[] normalIndices,
			int[] textureCoordinateIndices, String name) throws Exception {
		Point3D[] t_coordinates = null;
		Vector3D[] t_normals = null;
		TextureCoordinates[] t_textureCoordinates = null;
		for (int i = 0 ; i < coordinateIndices.length ; i++) {
			if (i % 3 == 0) {
				if (i > 0)
					scene.addSurface(new Triangle(t_coordinates, t_normals, t_textureCoordinates, name));
				t_coordinates = new Point3D[3];
				t_normals = new Vector3D[3];
				t_textureCoordinates = new TextureCoordinates[3];
			}
			t_coordinates[i % 3] = coordinates[coordinateIndices[i]];
			t_normals[i % 3] = normals[normalIndices[i]];
			t_textureCoordinates[i % 3] = textureCoordinates[textureCoordinateIndices[i]];
		}
		scene.addSurface(new Triangle(t_coordinates, t_normals, t_textureCoordinates, name));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uclouvain.ingi2325.parser.ParserHandler#endIndexedTriangleSet()
	 */
	@Override
	public void endIndexedTriangleSet() throws Exception {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * uclouvain.ingi2325.parser.ParserHandler#startFileGeometry(java.lang.String
	 * , java.lang.String)
	 */
	@Override
	public void startFileGeometry(String filename, String name)
			throws Exception {
		FileReader f = new FileReader(path + filename);
		BufferedReader b = new BufferedReader(f);
		ArrayList<Point3D> coordinates = new ArrayList<Point3D>();
		ArrayList<TextureCoordinates> textureCoordinates = new ArrayList<TextureCoordinates>();
		ArrayList<Vector3D> normals = new ArrayList<Vector3D>();
		Point3D[] t_coordinates = null;
		TextureCoordinates[] t_textureCoordinates = null;
		Vector3D[] t_normals = null;
		String[][] triplets;
		String l;
		while ((l = b.readLine()) != null) {
			String[] chunks = l.split(" ");
			if (chunks[0].equals("v"))
				coordinates.add(Point3D.valueOf(chunks[1] + " " + chunks[2] + " " + chunks[3]));
			else if (chunks[0].equals("vt"))
				textureCoordinates.add(TextureCoordinates.valueOf(chunks[1] + " " + chunks[2]));
			else if (chunks[0].equals("vn"))
				normals.add(Vector3D.valueOf(chunks[1] + " " + chunks[2] + " " + chunks[3]));
			else if (chunks[0].equals("f")) {
				triplets = new String[3][3];
				for (int i = 0 ; i < triplets.length ; i++)
					triplets[i] = chunks[i + 1].split("/");
				t_coordinates = new Point3D[3];
				for (int i = 0 ; i < t_coordinates.length ; i++) 
					t_coordinates[i] = coordinates.get(Integer.valueOf(triplets[i][0]) - 1);
				t_textureCoordinates = new TextureCoordinates[3];
				for (int i = 0 ; i < t_textureCoordinates.length ; i++)
					try {
						t_textureCoordinates[i] = textureCoordinates.get(Integer.valueOf(triplets[i][1]) - 1);
					}
					catch (NumberFormatException e) {
						t_textureCoordinates[i] = TextureCoordinates.valueOf("0 0");
					}
				t_normals = new Vector3D[3];
				for (int i = 0 ; i < t_normals.length ; i++)
					t_normals[i] = normals.get(Integer.valueOf(triplets[i][2]) - 1);
				scene.addSurface(new Triangle(t_coordinates, t_normals, t_textureCoordinates, name));
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uclouvain.ingi2325.parser.ParserHandler#endFileGeometry()
	 */
	@Override
	public void endFileGeometry() throws Exception {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uclouvain.ingi2325.parser.ParserHandler#startTextures()
	 */
	@Override
	public void startTextures() throws Exception {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uclouvain.ingi2325.parser.ParserHandler#endTextures()
	 */
	@Override
	public void endTextures() throws Exception {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * uclouvain.ingi2325.parser.ParserHandler#startTexture(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public void startTexture(String src, String name) throws Exception {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uclouvain.ingi2325.parser.ParserHandler#endTexture()
	 */
	@Override
	public void endTexture() throws Exception {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uclouvain.ingi2325.parser.ParserHandler#startMaterials()
	 */
	@Override
	public void startMaterials() throws Exception {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uclouvain.ingi2325.parser.ParserHandler#endMaterials()
	 */
	@Override
	public void endMaterials() throws Exception {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * uclouvain.ingi2325.parser.ParserHandler#startDiffuseMaterial(uclouvain
	 * .ingi2325.utils.Color, java.lang.String)
	 */
	@Override
	public void startDiffuseMaterial(Color color, String name) throws Exception {
		scene.addMaterial(new DiffuseMaterial(color, name));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uclouvain.ingi2325.parser.ParserHandler#endDiffuseMaterial()
	 */
	@Override
	public void endDiffuseMaterial() throws Exception {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * uclouvain.ingi2325.parser.ParserHandler#startPhongMaterial(uclouvain.
	 * ingi2325.utils.Color, float, java.lang.String)
	 */
	@Override
	public void startPhongMaterial(Color color, float shininess, String name)
			throws Exception {
		scene.addMaterial(new PhongMaterial(color, shininess, name));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uclouvain.ingi2325.parser.ParserHandler#endPhongMaterial()
	 */
	@Override
	public void endPhongMaterial() throws Exception {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * uclouvain.ingi2325.parser.ParserHandler#startLinearCombinedMaterial(java
	 * .lang.String, float, java.lang.String, float, java.lang.String)
	 */
	@Override
	public void startLinearCombinedMaterial(String material1Name,
			float weight1, String material2Name, float weight2, String name)
			throws Exception {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uclouvain.ingi2325.parser.ParserHandler#endLinearCombinedMaterial()
	 */
	@Override
	public void endLinearCombinedMaterial() throws Exception {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uclouvain.ingi2325.parser.ParserHandler#startScene(java.lang.String,
	 * java.lang.String[], uclouvain.ingi2325.utils.Color)
	 */
	@Override
	public void startScene(String cameraName, String[] lightNames,
			Color background) throws Exception {
		scene.setDefaultCamera(cameraName);
		scene.setBackground(background);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uclouvain.ingi2325.parser.ParserHandler#endScene()
	 */
	@Override
	public void endScene() throws Exception {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uclouvain.ingi2325.parser.ParserHandler#startShape(java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public void startShape(String geometryName, String materialName,
			String textureName) throws Exception {
		scene.setShape(geometryName, materialName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uclouvain.ingi2325.parser.ParserHandler#endShape()
	 */
	@Override
	public void endShape() throws Exception {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * uclouvain.ingi2325.parser.ParserHandler#startRotate(uclouvain.ingi2325
	 * .utils.Vector3, float)
	 */
	@Override
	public void startRotate(Vector3D axis, float angle) throws Exception {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uclouvain.ingi2325.parser.ParserHandler#endRotate()
	 */
	@Override
	public void endRotate() throws Exception {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * uclouvain.ingi2325.parser.ParserHandler#startTranslate(uclouvain.ingi2325
	 * .utils.Vector3)
	 */
	@Override
	public void startTranslate(Vector3D vector) throws Exception {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uclouvain.ingi2325.parser.ParserHandler#endTranslate()
	 */
	@Override
	public void endTranslate() throws Exception {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * uclouvain.ingi2325.parser.ParserHandler#startScale(uclouvain.ingi2325
	 * .utils.Vector3)
	 */
	@Override
	public void startScale(Vector3D scale) throws Exception {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uclouvain.ingi2325.parser.ParserHandler#endScale()
	 */
	@Override
	public void endScale() throws Exception {
	}

}
