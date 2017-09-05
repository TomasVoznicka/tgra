package com.mygdx.game;

import java.nio.FloatBuffer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.BufferUtils;

public class CircleGraphic {

	static private FloatBuffer vertexBuffer;
	private static int verticesPerCircle = 40;

	private static int vertexPointer;

	public static void create(int vertexPointer) {
		CircleGraphic.vertexPointer = vertexPointer;
		float[] array = new float[2 * verticesPerCircle];
		int index = 0;
		// VERTEX ARRAY IS FILLED HERE
		for (float i = 0; i < 2 * Math.PI; i += (2 * Math.PI) / (verticesPerCircle / 2)) {
			array[index] = (float) (Math.cos(i));
			index++;
			array[index] = (float) (Math.sin(i));
			index++;
		}

		vertexBuffer = BufferUtils.newFloatBuffer(2 * verticesPerCircle);
		vertexBuffer.put(array);
		vertexBuffer.rewind();
	}

	public static void drawSolidCircle() {

		Gdx.gl.glVertexAttribPointer(vertexPointer, 2, GL20.GL_FLOAT, false, 0, vertexBuffer);

		Gdx.gl.glDrawArrays(GL20.GL_TRIANGLE_FAN, 0, verticesPerCircle);

	}
	public static void drawOutlineCircle() {


		Gdx.gl.glVertexAttribPointer(vertexPointer, 2, GL20.GL_FLOAT, false, 0, vertexBuffer);

		Gdx.gl.glDrawArrays(GL20.GL_LINE_LOOP, 0, 4);

	}

}
