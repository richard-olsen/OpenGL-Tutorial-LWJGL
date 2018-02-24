package com.github.elegantwhelp.ogltut.graphics;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.*;

public class Shader {
	private int vertexShader, fragmentShader, program;
	
	public Shader() {
	}
	
	public boolean create(String shader) {
		int success;
		
		vertexShader = glCreateShader(GL_VERTEX_SHADER);
		glShaderSource(vertexShader, readSource(shader + ".vs"));
		glCompileShader(vertexShader);
		
		success = glGetShaderi(vertexShader, GL_COMPILE_STATUS);
		if (success == GL_FALSE) {
			System.err.println("Vertex: \n" + glGetShaderInfoLog(vertexShader));
			return false;
		}
		
		fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
		glShaderSource(fragmentShader, readSource(shader + ".fs"));
		glCompileShader(fragmentShader);
		
		success = glGetShaderi(fragmentShader, GL_COMPILE_STATUS);
		if (success == GL_FALSE) {
			System.err.println("Fragment: \n" + glGetShaderInfoLog(fragmentShader));
			return false;
		}
		
		program = glCreateProgram();
		glAttachShader(program, vertexShader);
		glAttachShader(program, fragmentShader);
		
		glLinkProgram(program);
		success = glGetProgrami(program, GL_LINK_STATUS);
		if (success == GL_FALSE) {
			System.err.println("Program Link: \n" + glGetProgramInfoLog(program));
			return false;
		}
		glValidateProgram(program);
		success = glGetProgrami(program, GL_VALIDATE_STATUS);
		if (success == GL_FALSE) {
			System.err.println("Program Validate: \n" + glGetProgramInfoLog(program));
			return false;
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
	
	private String readSource(String file) {
		BufferedReader reader = null;
		StringBuilder sourceBuilder = new StringBuilder();
		
		try {
			reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/shaders/" + file)));
			
			String line;
			
			while ((line = reader.readLine()) != null) {
				sourceBuilder.append(line + "\n");
			}
		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return sourceBuilder.toString();
	}
}
