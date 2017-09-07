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

	private ArrayList<Point2D> balls = new ArrayList<Point2D>();

	private ArrayList<Rectangle> rectanglesDraw = new ArrayList<Rectangle>();

	private ArrayList<Line> linesDraw = new ArrayList<Line>();

	private ArrayList<Line> bounceLines = new ArrayList<Line>();

	int size1 = 10;

	float deltaT;
	Point2D b1, b2, Phit, Pt, addline1 = null, addline2 = null, addRect1 = null, addrect2 = null;
	Vector2D n;
	Vector2D eee;
	float Thit;
	float aa, bb, ccc;
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
		checkForSpacebar();
		checkForLeftButton();

		checkForRightButton();

		moveBall();

	}

	private void checkForSpacebar() {
		if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {

			balls.add(new Point2D(0, 0, new Vector2D(200, 200)));
		}
	}

	private void checkForRightButton() {
		if (Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {
			if (addRect1 == null) {

				addRect1 = new Point2D(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());

			}

		} else {
			if (addRect1 != null) {

				addrect2 = new Point2D(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());

			}
		}
		if (addRect1 != null && addrect2 != null) {
			Rectangle S = new Rectangle(addRect1, addrect2);
			rectanglesDraw.add(S);

			bounceLines.add(new Line(S.p1, S.p2));
			bounceLines.add(new Line(S.p2, S.p3));
			bounceLines.add(new Line(S.p4, S.p3));
			bounceLines.add(new Line(S.p1, S.p4));

			addRect1 = null;
			addrect2 = null;
		}
	}

	private void checkForLeftButton() {
		if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
			if (addline1 == null) {

				addline1 = new Point2D(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());

			}

		} else {
			if (addline1 != null) {

				addline2 = new Point2D(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());

			}
		}
		if (addline1 != null && addline2 != null) {

			bounceLines.add(new Line(addline1, addline2));
			linesDraw.add(new Line(addline1, addline2));
			addline1 = null;
			addline2 = null;

		}
	}

	private void display() {

		Gdx.gl20.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		OrthographicProjection2D(0, Gdx.graphics.getWidth(), 0, Gdx.graphics.getHeight());

		for (Point2D ball : balls) {
			clearModelMatrix();
			setModelMatrixTranslation(ball.x, ball.y);
			setModelMatrixScale(size1, size1);
			Gdx.gl.glUniform4f(colorLoc, 0.9f, 0.4f, 0, 1);
			CircleGraphic.drawSolidCircle();
		}

		clearModelMatrix();
		Gdx.gl.glUniform4f(colorLoc, 0.2f, 0.8f, 0.2f, 1);

		for (Rectangle S : rectanglesDraw) {
			setModelMatrixTranslation(S.center().x, S.center().y);
			setModelMatrixScale(S.width(), S.height());
			RectangleGraphic.drawSolidSquare();

		}

		clearModelMatrix();
		Gdx.gl.glUniform4f(colorLoc, 0.9f, 0.8f, 0, 1);
		for (Line L : linesDraw) {

			L.draw(positionLoc);

		}
	}

	private void moveBall() {
		testForBounce();
		for (Point2D P : balls) {
			P.translate(P.v.x * deltaT, P.v.y * deltaT);
		}

	}

	private void testForBounce() {

		// for (float i = 0; i < 2 * Math.PI; i += (2 * Math.PI) / (8 / 2)) {
		// Pt = new Point2D(((float) Math.cos(i) * size1) + Ball.x, ((float) Math.sin(i)
		// * size1) + Ball.y);

		for (Point2D ball : balls) {
			edgesOfWindow(ball);

			hitDetection(ball,deltaT);
		}

	}

	private void hitDetection(Point2D ball,float currentDeltaT) {
		for (Line L : bounceLines) {

			b1 = L.p1;
			b2 = L.p2;

			n = Point2D.makeVector(b1, b2).getUnit();

			eee = Point2D.makeVector(ball, b1);
			aa = Vector2D.dot(n, eee);
			bb = Vector2D.dot(n, ball.v);
			Thit = (aa) / (bb);

			Vector2D R;
			if (Thit <= currentDeltaT && Thit >= 0) {
				Phit = Point2D.translatePointByVector(ball, Vector2D.scale(ball.v, Thit));

				maxX = Math.max(b1.x, b2.x);
				minX = Math.min(b1.x, b2.x);
				maxY = Math.max(b1.y, b2.y);
				minY = Math.min(b1.y, b2.y);

				if (Phit.y <= maxY && Phit.y >= minY && Phit.x >= minX && Phit.x <= maxX) {

					ccc = (2 * Vector2D.dot(ball.v, n) / Vector2D.dot(n, n));
					R = Vector2D.subbTwo(ball.v, Vector2D.scale(n, ccc));
					ball.v = R;
					hitDetection(ball,currentDeltaT-Thit);
					return;

				}

			}
		}
	}

	private void edgesOfWindow(Point2D ball) {
		if (ball.v.x > 0) {

			if (ball.x >= Gdx.graphics.getWidth() - size1)
				ball.v.x = ball.v.x * (-1);
		} else {
			if (ball.x <= size1)
				ball.v.x = ball.v.x * (-1);
		}
		if (ball.v.y > 0) {
			if (ball.y >= Gdx.graphics.getHeight() - size1)
				ball.v.y = ball.v.y * (-1);
		} else {

			if (ball.y <= size1)
				ball.v.y = ball.v.y * (-1);

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