package com.github.warmful.ogltut.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import org.lwjgl.BufferUtils;

public class FileUtil {
	public static ByteBuffer loadResource(String file) {
		InputStream is = null;
		
		try {
			is = FileUtil.class.getResourceAsStream(file);
			
			ByteBuffer buffer = BufferUtils.createByteBuffer(1024);
			
			int data = -1;
			while ((data = is.read()) != -1) {
				buffer.put((byte) data);
				
				if (buffer.remaining() == 0) {
					ByteBuffer newBuffer = BufferUtils.createByteBuffer(buffer.capacity() + 1024);
					buffer.flip();
					newBuffer.put(buffer);
					buffer = newBuffer;
				}
			}
			
			is.close();
			
			buffer.flip();
			return buffer;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return null;
	}
}
