package uclouvain.ingi2325.math;

/**
 * Represents a 3x3 matrix of floats
 *
 * @author Julien Odent <julien.odent@student.uclouvain.be>
 */

public class Matrix3 {

	private float[][] m;
	
	public Matrix3() {
		m = new float[3][3];
		for (int i = 0 ; i < 3 ; i++)
			for (int j = 0 ; j < 3 ; j++)
				m[i][j] = 0.0F;
	}

	public Matrix3(Matrix4 m4) {
		m = new float[3][3];
		for (int i = 0 ; i < 3 ; i++)
			for (int j = 0 ; j < 3 ; j++)
				m[i][j] = m4.getElement(i, j);
	}

	public float getElement(int row, int column) {
		return m[row][column];
	}

	public void setElement(int row, int column, float value) {
		m[row][column] = value;
	}

	public Matrix3 normalize() {
		Matrix3 n = new Matrix3();
		n.m[0][0] = m[1][1] * m[2][2] - m[1][2] * m[2][1];
		n.m[0][1] = m[1][2] * m[2][0] - m[1][0] * m[2][2];
		n.m[0][2] = m[1][0] * m[2][1] - m[1][1] * m[2][0];
		n.m[1][0] = m[0][2] * m[2][1] - m[0][1] * m[2][2];
		n.m[1][1] = m[0][0] * m[2][2] - m[0][2] * m[2][0];
		n.m[1][2] = m[0][1] * m[2][0] - m[0][0] * m[2][1];
		n.m[2][0] = m[0][1] * m[1][2] - m[0][2] * m[1][1];
		n.m[2][1] = m[0][2] * m[1][0] - m[0][0] * m[1][2];
		n.m[2][2] = m[0][0] * m[1][1] - m[0][1] * m[1][0];
		return n;
	}

}
