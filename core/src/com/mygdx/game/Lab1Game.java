package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;

import java.awt.Point;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import com.badlogic.gdx.utils.BufferUtils;

public class Lab1Game extends ApplicationAdapter {

	private FloatBuffer modelMatrix;
	private FloatBuffer projectionMatrix;

	private int renderingProgramID;
	private int vertexShaderID;
	private int fragmentShaderID;

	private int positionLoc;

	private int modelMatrixLoc;
	private int projectionMatrixLoc;

	private int colorLoc;

	Point2D Ball = new Point2D(0, 0);
	private ArrayList<Rectangle> rectangles = new ArrayList<Rectangle>();

	private ArrayList<Line> Lines = new ArrayList<Line>();

	int size1 = 10;

	Vector2D movement = new Vector2D(682, 178);
	float deltaT;
	Point2D b1, b2, Phit, Pt;
	Vector2D n;
	Vector2D eee;
	float Thit;
	float aa, bb;
	float maxX, maxY, minX, minY;

	@Override
	public void create() {

		String vertexShaderString;
		String fragmentShaderString;

		vertexShaderString = Gdx.files.internal("shaders/simple2D.vert").readString();
		fragmentShaderString = Gdx.files.internal("shaders/simple2D.frag").readString();

		vertexShaderID = Gdx.gl.glCreateShader(GL20.GL_VERTEX_SHADER);
		fragmentShaderID = Gdx.gl.glCreateShader(GL20.GL_FRAGMENT_SHADER);

		Gdx.gl.glShaderSource(vertexShaderID, vertexShaderString);
		Gdx.gl.glShaderSource(fragmentShaderID, fragmentShaderString);

		Gdx.gl.glCompileShader(vertexShaderID);
		Gdx.gl.glCompileShader(fragmentShaderID);

		renderingProgramID = Gdx.gl.glCreateProgram();

		Gdx.gl.glAttachShader(renderingProgramID, vertexShaderID);
		Gdx.gl.glAttachShader(renderingProgramID, fragmentShaderID);

		Gdx.gl.glLinkProgram(renderingProgramID);

		positionLoc = Gdx.gl.glGetAttribLocation(renderingProgramID, "a_position");
		Gdx.gl.glEnableVertexAttribArray(positionLoc);

		modelMatrixLoc = Gdx.gl.glGetUniformLocation(renderingProgramID, "u_modelMatrix");
		projectionMatrixLoc = Gdx.gl.glGetUniformLocation(renderingProgramID, "u_projectionMatrix");

		colorLoc = Gdx.gl.glGetUniformLocation(renderingProgramID, "u_color");

		Gdx.gl.glUseProgram(renderingProgramID);

		float[] mm = new float[16];

		mm[0] = 1.0f;
		mm[4] = 0.0f;
		mm[8] = 0.0f;
		mm[12] = 0.0f;
		mm[1] = 0.0f;
		mm[5] = 1.0f;
		mm[9] = 0.0f;
		mm[13] = 0.0f;
		mm[2] = 0.0f;
		mm[6] = 0.0f;
		mm[10] = 1.0f;
		mm[14] = 0.0f;
		mm[3] = 0.0f;
		mm[7] = 0.0f;
		mm[11] = 0.0f;
		mm[15] = 1.0f;

		modelMatrix = BufferUtils.newFloatBuffer(16);
		modelMatrix.put(mm);
		modelMatrix.rewind();

		Gdx.gl.glUniformMatrix4fv(modelMatrixLoc, 1, false, modelMatrix);

		RectangleGraphic.create(positionLoc);
		CircleGraphic.create(positionLoc);
		SincGraphic.create(positionLoc);

	}

	private void update() {
		deltaT = Gdx.graphics.getDeltaTime();

		// if (Gdx.input.justTouched()) {
		// points.add(new Point2D(Gdx.input.getX(), Gdx.graphics.getHeight() -
		// Gdx.input.getY()));
		// }
		rectangles.clear();

		Lines.add(new Line(new Point2D(100, 200), new Point2D(300, 500)));
		rectangles.add(new Rectangle(new Point2D(100, 200), new Point2D(200, 100)));
		rectangles.add(new Rectangle(new Point2D(300, 500), new Point2D(500, 300)));
		for (Rectangle S : rectangles) {
			Lines.add(new Line(S.p1, S.p2));
			Lines.add(new Line(S.p2, S.p3));
			Lines.add(new Line(S.p4, S.p3));
			Lines.add(new Line(S.p1, S.p4));
		}

		updateSqr1();

		updateSqr3();
	}

	private void display() {

		Gdx.gl.glUniform4f(colorLoc, 0.9f, 0.8f, 0, 1);
		Gdx.gl20.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		OrthographicProjection2D(0, Gdx.graphics.getWidth(), 0, Gdx.graphics.getHeight());

		clearModelMatrix();
		setModelMatrixTranslation(Ball.x, Ball.y);
		setModelMatrixScale(size1, size1);
		Gdx.gl.glUniform4f(colorLoc, 0.9f, 0.4f, 0, 1);
		CircleGraphic.drawSolidCircle();
		

		clearModelMatrix();
		Gdx.gl.glUniform4f(colorLoc, 0.2f, 0.8f, 0.2f, 1);
		for (Rectangle S : rectangles) {
			setModelMatrixTranslation(S.center().x, S.center().y);
			setModelMatrixScale(S.width(), S.height());
			RectangleGraphic.drawSolidSquare();

		}
		clearModelMatrix();
		Line l = new Line(new Point2D(100, 200), new Point2D(300, 500));
		l.draw(positionLoc);
	}

	private void updateSqr1() {
		testForBounce();
		Ball.translate(movement.x * deltaT, movement.y * deltaT);

	}

	private void updateSqr3() {

	}

	private void testForBounce() {
		if (movement.x > 0) {

			if (Ball.x >= Gdx.graphics.getWidth() - size1)
				movement.x = movement.x * (-1);
		} else {
			if (Ball.x <= size1)
				movement.x = movement.x * (-1);
		}
		if (movement.y > 0) {
			if (Ball.y >= Gdx.graphics.getHeight() - size1)
				movement.y = movement.y * (-1);
		} else {

			if (Ball.y <= size1)
				movement.y = movement.y * (-1);

		}

		for (float i = 0; i < 2 * Math.PI; i += (2 * Math.PI) / (8 / 2)) {
			Pt = new Point2D(((float) Math.cos(i) * size1) + Ball.x, ((float) Math.sin(i) * size1) + Ball.y);

			for (Line L : Lines) {

				b1 = L.p1;
				b2 = L.p2;

				n = Point2D.makeVector(b1, b2).getUnit();

				eee = Point2D.makeVector(Pt, b1);
				aa = Vector2D.dot(n, eee);
				bb = Vector2D.dot(n, movement);
				Thit = (aa) / (bb);

				Vector2D R;
				if (Thit < deltaT && Thit > 0) {
					Phit = Point2D.translatePointByVector(Pt, Vector2D.scale(movement, Thit));

					maxX = Math.max(b1.x, b2.x);
					minX = Math.min(b1.x, b2.x);
					maxY = Math.max(b1.y, b2.y);
					minY = Math.min(b1.y, b2.y);

					if (Phit.y <= maxY && Phit.y >= minY && Phit.x >= minX && Phit.x <= maxX) {

						float ccc = (2 * Vector2D.dot(movement, n) / Vector2D.dot(n, n));
						R = Vector2D.subbTwo(movement, Vector2D.scale(n, ccc));
						movement = R;

					}

				}
			}
		}

	}

	@Override
	public void render() {

		Gdx.graphics.setTitle("00000");
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// put the code inside the update and display methods, depending on the nature
		// of the code
		update();
		display();

	}

	private void clearModelMatrix() {
		modelMatrix.put(0, 1.0f);
		modelMatrix.put(1, 0.0f);
		modelMatrix.put(2, 0.0f);
		modelMatrix.put(3, 0.0f);
		modelMatrix.put(4, 0.0f);
		modelMatrix.put(5, 1.0f);
		modelMatrix.put(6, 0.0f);
		modelMatrix.put(7, 0.0f);
		modelMatrix.put(8, 0.0f);
		modelMatrix.put(9, 0.0f);
		modelMatrix.put(10, 1.0f);
		modelMatrix.put(11, 0.0f);
		modelMatrix.put(12, 0.0f);
		modelMatrix.put(13, 0.0f);
		modelMatrix.put(14, 0.0f);
		modelMatrix.put(15, 1.0f);

		Gdx.gl.glUniformMatrix4fv(modelMatrixLoc, 1, false, modelMatrix);
	}

	private void setModelMatrixTranslation(float xTranslate, float yTranslate) {
		modelMatrix.put(12, xTranslate);
		modelMatrix.put(13, yTranslate);

		Gdx.gl.glUniformMatrix4fv(modelMatrixLoc, 1, false, modelMatrix);
	}

	private void setModelMatrixScale(float xScale, float yScale) {
		modelMatrix.put(0, xScale);
		modelMatrix.put(5, yScale);

		Gdx.gl.glUniformMatrix4fv(modelMatrixLoc, 1, false, modelMatrix);
	}

	private void OrthographicProjection2D(float left, float right, float bottom, float top) {
		float[] pm = new float[16];

		pm[0] = 2.0f / (right - left);
		pm[4] = 0.0f;
		pm[8] = 0.0f;
		pm[12] = -(right + left) / (right - left);
		pm[1] = 0.0f;
		pm[5] = 2.0f / (top - bottom);
		pm[9] = 0.0f;
		pm[13] = -(top + bottom) / (top - bottom);
		pm[2] = 0.0f;
		pm[6] = 0.0f;
		pm[10] = 1.0f;
		pm[14] = 0.0f;
		pm[3] = 0.0f;
		pm[7] = 0.0f;
		pm[11] = 0.0f;
		pm[15] = 1.0f;

		projectionMatrix = BufferUtils.newFloatBuffer(16);
		projectionMatrix.put(pm);
		projectionMatrix.rewind();
		Gdx.gl.glUniformMatrix4fv(projectionMatrixLoc, 1, false, projectionMatrix);
	}

}