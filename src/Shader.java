import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Shader {
	private int programObject;
	private int vertexShaderObject;
	private int fragmentShaderObject;
	
	public Shader() {
	}
	
	public boolean create(String shader) {
		int[] error = new int[1];
		
		vertexShaderObject = glCreateShader(GL_VERTEX_SHADER);
		glShaderSource(vertexShaderObject, getSourceFromFile(shader + ".vs"));
		glCompileShader(vertexShaderObject);
		
		glGetShaderiv(vertexShaderObject, GL_COMPILE_STATUS, error);
		if (error[0] == GL_FALSE) {
			System.err.println(glGetShaderInfoLog(vertexShaderObject));
			
			glDeleteShader(vertexShaderObject);
			
			return false;
		}
		
		fragmentShaderObject = glCreateShader(GL_FRAGMENT_SHADER);
		glShaderSource(fragmentShaderObject, getSourceFromFile(shader + ".fs"));
		glCompileShader(fragmentShaderObject);
		
		glGetShaderiv(fragmentShaderObject, GL_COMPILE_STATUS, error);
		if (error[0] == GL_FALSE) {
			System.err.println(glGetShaderInfoLog(fragmentShaderObject));
			
			glDeleteShader(vertexShaderObject);
			glDeleteShader(fragmentShaderObject);
			
			return false;
		}
		
		programObject = glCreateProgram();
		glAttachShader(programObject, vertexShaderObject);
		glAttachShader(programObject, fragmentShaderObject);
		
		glLinkProgram(programObject);
		
		glGetProgramiv(programObject, GL_LINK_STATUS, error);
		if (error[0] == GL_FALSE) {
			System.err.println(glGetProgramInfoLog(programObject));
			
			destroy();
			
			return false;
		}
		
		glValidateProgram(programObject);
		
		glGetProgramiv(programObject, GL_VALIDATE_STATUS, error);
		if (error[0] == GL_FALSE) {
			System.err.println(glGetProgramInfoLog(programObject));
			
			destroy();
			
			return false;
		}
		
		return true;
	}
	
	public void destroy() {
		glDetachShader(programObject, vertexShaderObject);
		glDetachShader(programObject, fragmentShaderObject);
		glDeleteShader(vertexShaderObject);
		glDeleteShader(fragmentShaderObject);
		glDeleteProgram(programObject);
	}
	
	public void bind() {
		glUseProgram(programObject);
	}
	
	public void unbind() {
		glUseProgram(0);
	}
	
	private static String getSourceFromFile(String filename) {
		BufferedReader bufferedReader;
		StringBuilder source = new StringBuilder();
		try {
			bufferedReader = new BufferedReader(new FileReader("./shaders/"+filename));
			String currentLine = null;
			
			while ((currentLine = bufferedReader.readLine()) != null) {
				source.append(currentLine).append("\n");
			}
			
			bufferedReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return source.toString();
	}
}
