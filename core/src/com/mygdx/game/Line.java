package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.BufferUtils;

import java.nio.FloatBuffer;

public class Line {
	Point2D p1;
	Point2D p2;

	Vector2D v;

	public Line(Point2D p1, Point2D p2) {

		this.p1 = new Point2D(p1.x, p1.y);
		this.p2 = new Point2D(p2.x, p2.y);

		v = Point2D.makeVector(p1, p2);

	}

	public void draw(int vertexPointer) {
		FloatBuffer vertexBuffer;

		// VERTEX ARRAY IS FILLED HERE
		float[] array = { p1.x, p1.y, p2.x, p2.y };

		vertexBuffer = BufferUtils.newFloatBuffer(4);
		vertexBuffer.put(array);
		vertexBuffer.rewind();

		Gdx.gl.glVertexAttribPointer(vertexPointer, 2, GL20.GL_FLOAT, false, 0, vertexBuffer);
		Gdx.gl.glDrawArrays(GL20.GL_LINE_LOOP, 0, 2);
	}

}
