import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.joml.Matrix4f;

public class Shader {
	private int program;
	private int vertexShader;
	private int fragmentShader;
	
	public Shader() {
	}
	
	public boolean create(String shader) {
		int success[] = new int[1];
		
		vertexShader = glCreateShader(GL_VERTEX_SHADER);
		glShaderSource(vertexShader, readShader(shader + ".vs"));
		glCompileShader(vertexShader);
		
		glGetShaderiv(vertexShader, GL_COMPILE_STATUS, success);
		if (success[0] == GL_FALSE) {
			System.err.println("Vertex Shader Error: \n" + glGetShaderInfoLog(vertexShader));
		}
		
		fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
		glShaderSource(fragmentShader, readShader(shader + ".fs"));
		glCompileShader(fragmentShader);
		
		glGetShaderiv(fragmentShader, GL_COMPILE_STATUS, success);
		if (success[0] == GL_FALSE) {
			System.err.println("Fragment Shader Error: \n" + glGetShaderInfoLog(fragmentShader));
		}
		
		program = glCreateProgram();
		
		glAttachShader(program, vertexShader);
		glAttachShader(program, fragmentShader);
		
		glLinkProgram(program);
		
		glGetProgramiv(program, GL_LINK_STATUS, success);
		if (success[0] == GL_FALSE) {
			System.err.println("Program Link Error: \n" + glGetProgramInfoLog(program));
		}
		
		glValidateProgram(program);
		
		glGetProgramiv(program, GL_VALIDATE_STATUS, success);
		if (success[0] == GL_FALSE) {
			System.err.println("Program Validate Error: \n" + glGetProgramInfoLog(program));
		}
		
		return true;
	}
	
	public void destroy() {
		glDetachShader(program, vertexShader);
		glDetachShader(program, fragmentShader);
		glDeleteShader(vertexShader);
		glDeleteShader(fragmentShader);
		glDeleteProgram(program);
	}
	
	public void useShader() {
		glUseProgram(program);
	}
	
	public void setUniform(String name, Matrix4f value) {
		int location = glGetUniformLocation(program, name);
		if (location != -1) {
			float m[] = new float[16];
			value.get(m);
			glUniformMatrix4fv(location, false, m);
		}
	}
	
	private static String readShader(String filename) {
		BufferedReader bufferedReader;
		StringBuilder stringBuilder = new StringBuilder();
		
		try {
			bufferedReader = new BufferedReader(new FileReader("./shaders/" + filename));
			
			String line;
			
			while ( (line = bufferedReader.readLine()) != null) {
				stringBuilder.append(line).append("\n");
			}
			
			bufferedReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return stringBuilder.toString();
	}
}
