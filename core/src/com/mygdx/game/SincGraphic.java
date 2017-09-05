package com.mygdx.game;

import java.nio.FloatBuffer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.BufferUtils;

public class SincGraphic {

	static private FloatBuffer vertexBuffer;
	private static int verticesPerPlot = 40;

	private static int vertexPointer;

	public static void create(int vertexPointer) {
		SincGraphic.vertexPointer = vertexPointer;
		double f = -4.0f;
		vertexBuffer = BufferUtils.newFloatBuffer(2*verticesPerPlot);
		for(int i = 0; i < verticesPerPlot; i++)
		{
		vertexBuffer.put(2*i, (float)f);
		if(f == 0.0) {
		vertexBuffer.put(2*i + 1, 1.0f);
		} else {
		vertexBuffer.put(2*i + 1, (float)(Math.sin(Math.PI * f) / (Math.PI * f)));
		}
		f += 8.0 / (double)verticesPerPlot;
		}
		vertexBuffer.rewind();
	}

	public static void drawPlot() {

		
		Gdx.gl.glVertexAttribPointer(vertexPointer, 2, GL20.GL_FLOAT, false, 0, vertexBuffer);

		Gdx.gl.glDrawArrays(GL20.GL_LINE_STRIP, 0, verticesPerPlot);

	}

}
