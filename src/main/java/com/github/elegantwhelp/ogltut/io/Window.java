package com.github.elegantwhelp.ogltut.io;

import static org.lwjgl.glfw.GLFW.*;

import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

public class Window {
	private long window;
	
	public Window() {
	}
	
	/**
	 * Initializes GLFW and creates a window with the given width and height.
	 * @param width
	 * @param height
	 */
	public void createWindow(int width, int height) {
		if (!glfwInit()) {
			throw new IllegalStateException("Failed to initialize GLFW!");
		}
		
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
		glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);
		
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		window = glfwCreateWindow(width, height, "My OpenGL Program", 0, 0);
		if (window == 0) {
			throw new IllegalStateException("Failed to create window!");
		}
		glfwMakeContextCurrent(window);
		GL.createCapabilities();
		
		GLFWVidMode videoMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		glfwSetWindowPos(window, (videoMode.width() - width) / 2, (videoMode.height() - height) / 2);
		
		glfwShowWindow(window);
	}
	
	/**
	 * Cleans up and destroys the window. Also de-initializes GLFW. 
	 */
	public void free() {
		glfwDestroyWindow(window);
		
		glfwTerminate();
	}
	
	/**
	 * Polls all events of the window. 
	 * @return
	 * True, if the window should close.
	 */
	public boolean update() {
		glfwPollEvents();
		
		if (glfwWindowShouldClose(window))
			return true;
		return false;
	}
	
	/**
	 * Swaps the buffers to display an image.
	 */
	public void swapBuffers() {
		glfwSwapBuffers(window);
	}
}
