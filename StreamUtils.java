package com.example.htmlview;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class StreamUtils {
	
	public static String is2Str(InputStream is) throws IOException{
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		byte[] buffer = new byte[1024];
		int len;
		while((len=is.read(buffer))!=-1){
			baos.write(buffer, 0, len);
		}
		
		return baos.toString();
	}
	

}
