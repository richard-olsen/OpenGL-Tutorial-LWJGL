package com.github.elegantwhelp.ogltut;

import com.github.elegantwhelp.ogltut.graphics.Mesh;
import com.github.elegantwhelp.ogltut.graphics.Shader;
import com.github.elegantwhelp.ogltut.io.Window;

import static org.lwjgl.opengl.GL11.*;

public class Main {
	public static void main(String[] args) {
		Window window = new Window();
		
		window.createWindow(640, 480);
		
		Mesh mesh = new Mesh();
		mesh.create(new float[] {
				-1,-1,0,
				0,1,0,
				1,-1,0
		});
		
		Shader shader = new Shader();
		shader.create("basic");
		
		boolean isRunning = true;
		
		while (isRunning) {
			isRunning = !window.update();
			
			glClear(GL_COLOR_BUFFER_BIT);
			
			shader.useShader();
			mesh.draw();
			
			window.swapBuffers();
			
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		mesh.destroy();
		shader.destroy();
		
		window.free();
	}
}
