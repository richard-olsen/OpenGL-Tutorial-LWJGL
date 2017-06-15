import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MAJOR;
import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MINOR;
import static org.lwjgl.glfw.GLFW.GLFW_FALSE;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_CORE_PROFILE;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_FORWARD_COMPAT;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_PROFILE;
import static org.lwjgl.glfw.GLFW.GLFW_TRUE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

import org.joml.AxisAngle4f;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

public class Main {
	public static final int WIN_WIDTH = 1280;
	public static final int WIN_HEIGHT = 720;
	
	public static void main(String[] args) {
		if (!glfwInit()) {
			throw new IllegalStateException("Failed to initialize GLFW!");
		}
		
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
		glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);
		
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		long window = glfwCreateWindow(WIN_WIDTH, WIN_HEIGHT, "My OpenGL Program", 0, 0);
		if (window == 0) {
			throw new IllegalStateException("Failed to create window!");
		}
		glfwMakeContextCurrent(window);
		GL.createCapabilities();
		
		GLFWVidMode videoMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		glfwSetWindowPos(window, (videoMode.width() - WIN_WIDTH) / 2, (videoMode.height() - WIN_HEIGHT) / 2);
		
		glfwShowWindow(window);
		
		Mesh testMesh = new Mesh();
		testMesh.create(new float[] {
				-1,-1,0,
				0,1,0,
				1,-1,0
		});
	
		Shader testShader = new Shader();
		testShader.create("basic");
		
		Matrix4f projection = new Matrix4f().perspective((float)Math.toRadians(70.0f), (float)WIN_WIDTH / (float)WIN_HEIGHT, 0.01f, 1000.0f);
		
		Matrix4f model = new Matrix4f().translate(0,0,-3);
		
		while (!glfwWindowShouldClose(window)) {
			glfwPollEvents();
			
			model.rotate(new AxisAngle4f((float)Math.toRadians(1.0f), new Vector3f(0, 1, 0)));
			
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			
			testShader.useShader();
			testShader.setUniform("projection", projection);
			testShader.setUniform("model", model);
			testMesh.draw();
			
			glfwSwapBuffers(window);
		}
		testMesh.destroy();
		testShader.destroy();
		
		glfwTerminate();
	}
}
