package com.bupt.common;

import java.io.*;

public final class ObjectBytes {
	public static final byte[] objTobytes(Object obj) {
		
		ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
		ObjectOutputStream objStream;
		try {
			objStream = new ObjectOutputStream(byteStream);
			objStream.writeObject(obj);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return byteStream.toByteArray();
	}

	public static final Object bytesToObj(byte[] bytes){
		ByteArrayInputStream byteStream = new ByteArrayInputStream(bytes);
		ObjectInputStream objStream;
		Object object = null;
		try {
			objStream = new ObjectInputStream(byteStream);
			object = objStream.readObject();
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return object;
	}
}
