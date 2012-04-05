import javax.swing.*;

import uclouvain.ingi2325.utils.Camera;
import uclouvain.ingi2325.utils.Color;
import uclouvain.ingi2325.utils.PixelPanel;
import uclouvain.ingi2325.utils.Point3D;
import uclouvain.ingi2325.utils.Ray;
import uclouvain.ingi2325.utils.Scene;
import uclouvain.ingi2325.utils.SceneBuilder;
import uclouvain.ingi2325.utils.Vector3D;

/**
 * The demo :-)
 *
 * @author Antoine Cailliau <antoine.cailliau@uclouvain.be>
 * @author Julien Dupuis
 * @author Julien Odent <julien.odent@student.uclouvain.be>
 */
public class Demo {

	private Scene scene;

	public static void main(String[] args) {
		if (args.length != 3) {
			System.out.println("Usage: java Demo <sdl file> <width> <height>");
			System.exit(0);
		}
		new Demo(args);
	}

	private JFrame frame;
	private PixelPanel panel;

	public Demo(String[] args) {
		scene = null;
		try {
			SceneBuilder sceneBuilder = new SceneBuilder();
			scene = sceneBuilder.loadScene(args[0]);
			if (scene == null)
				System.out.println("wtf???");
		} catch (Exception e) {
			e.printStackTrace();
		}

		panel = new PixelPanel(Integer.valueOf(args[1]), Integer.valueOf(args[2]));
		frame = new JFrame();
		frame.getContentPane().add(panel);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		draw();
	}

	public void drawPixels() {
		panel.clear(0, 0, 1);
		int height = panel.getHeight();
		int width = panel.getWidth();
		int r = width / 2;
		int l = -r;
		int t = height / 2;
		int b = -t;

		Camera camera = scene.getDefaultCamera();
		float d = t / (float) (Math.tan((camera.getFovy() / 2) * Math.PI / 180));

		Point3D e = camera.getPosition();
		Vector3D ee = new Vector3D(e.x, e.y, e.z);
		Vector3D u = camera.getU();
		Vector3D v = camera.getV();
		Vector3D w = camera.getW();

		for (int y = 0 ; y < height ; y++) {
			for (int x = 0 ; x < width ; x++) {
				float xu = (float) (l + (r - l) * (x + 0.5) / width);
				float yv = (float) (b + (t - b) * (height - y + 0.5) / height);
				Vector3D uu = new Vector3D(xu * u.x, xu * u.y, xu * u.z);
				Vector3D vv = new Vector3D(yv * v.x, yv * v.y, yv * v.z);
				Vector3D direction = (new Vector3D(-d * w.x, -d * w.y, -d * w.z)).addWith(uu).addWith(vv);
				Ray ray = new Ray(e, direction);
				Color color = scene.traverse(ray);
				panel.drawPixel(x, y, color.x, color.y, color.z);
			}
			panel.repaint();
		}
	}

	public void draw() {
		drawPixels();
		panel.repaint();
		panel.saveImage("image.png");
	}
}
