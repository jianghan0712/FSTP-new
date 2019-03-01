package com.purefun.fstp.core.bo.tool;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import com.purefun.fstp.example.bo.ExampleQnsBO;

public class BoFactory {
	public static Object createBo(Class<?> c1) {
		fstpbo annotation = (fstpbo)c1.getAnnotation(fstpbo.class);
		if(annotation==null) {
			return null;
		}

		String[] spiltTemp = c1.getName().split("\\.");
		int len = spiltTemp.length;
		StringBuffer otwBoName = new StringBuffer();
		for(int i = 0;i<len-1;i++)
			otwBoName.append(spiltTemp[i]).append(".");
		
		otwBoName.append("otw.").append(spiltTemp[len-1]).append("_OTW");
		Object ret = null;

		try {		
			Object bo = c1.newInstance();		
			Field boid = c1.getField("boid");
			boid.set(bo, annotation.boid());
			Field des = c1.getField("destination");
			des.set(bo, annotation.destination());
			Field uuid = c1.getField("uuid");
			uuid.set(bo, UUID.createUuid());
			
			Field[] fileds = c1.getFields();
			for(Field e:fileds) {
				if(e.getName().equalsIgnoreCase("boid") ||e.getName().equalsIgnoreCase("destination") || e.getName().equalsIgnoreCase("uuid"))
					continue;				
				Class<?> type = e.getType();
				if(type.equals(java.lang.String.class)) {
					e.set(bo, "");
				}else if(type.equals(long.class)) {
					e.set(bo, -1L);
				}else if(type.equals(int.class)) {
					e.set(bo, -1);
				}
			}
			Class c2 = Class.forName(otwBoName.toString());
			ret = c2.getConstructor(c1).newInstance(bo);
			
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | NoSuchFieldException | SecurityException | NoSuchMethodException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return ret;		
	}

}
