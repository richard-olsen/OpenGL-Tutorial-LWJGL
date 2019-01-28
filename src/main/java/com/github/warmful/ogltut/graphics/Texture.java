package com.github.warmful.ogltut.graphics;

import static org.lwjgl.opengl.GL11.*;

import static org.lwjgl.stb.STBImage.*;

import java.nio.ByteBuffer;

import com.github.warmful.ogltut.util.FileUtil;

public class Texture {
	private int textureObject;
	
	private int width;
	private int height;
	
	public Texture() {}
	
	public boolean create(String texture_file) {
		int x[] = new int[1];
		int y[] = new int[1];
		int component[] = new int[1];
		
		//ByteBuffer pixels = stbi_load(texture_file, x, y, component, 4);
		ByteBuffer pixels = stbi_load_from_memory(FileUtil.loadResource(texture_file), x, y, component, 4);
		
		if (pixels == null) {
			System.err.println("Failed to load texture: " + texture_file);
			return false;
		}
		
		textureObject = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, textureObject);
		
		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		
		width = x[0];
		height = y[0];
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, pixels);
		
		return true;
	}
	
	public void destroy() {
		glDeleteTextures(textureObject);
	}
	
	public void bind() {
		glBindTexture(GL_TEXTURE_2D, textureObject);
	}
	
	public int getWidth() { return width; }
	public int getHeight() { return height; }
}
