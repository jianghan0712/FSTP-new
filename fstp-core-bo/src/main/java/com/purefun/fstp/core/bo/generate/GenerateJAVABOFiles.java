package com.purefun.fstp.core.bo.generate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Field;

public class GenerateJAVABOFiles {
	PrintWriter myFileWriter;
	String path = null;
	String protofileDirectory = null;
	String otwfileDirectory = null;
	String builderfileDirectory = null;
	String bopackageName = null;
	String TargetPath = "resource\\";
	final String TAB = "    ";
	final String BO = "bo";
	final String BUILDER = "builder";
	
	public GenerateJAVABOFiles(String path) {

	}
	
	private void println(String str) {
		this.myFileWriter.println(str);
		System.out.println(str);   
    }
	
	@Deprecated
	private StringBuilder analysis(String line,int i) {
		// TODO Auto-generated method stub
//		String[] str = line.substring(0, line.indexOf("=")).trim().split(" "); 
		line = line.replaceAll(";", "");		
		String[] str = line.trim().split(" "); 
		StringBuilder fin = new StringBuilder();
		//   str[0] str[1] str[2]
		// required string uuid = 1;
		if(str[2].equalsIgnoreCase("uuid") || str[2].equalsIgnoreCase("boid") || str[2].equalsIgnoreCase("destination")) {
			fin.append("    optional ");
		}else {
			fin.append("    optional ");
		}
		
		if(str[1].equalsIgnoreCase("double")) {
			fin.append("double ");
		}else if(str[1].equalsIgnoreCase("float")) {
			fin.append("float ");
		}else if(str[1].equalsIgnoreCase("long")) {
			fin.append("sint64 ");
		}else if(str[1].equalsIgnoreCase("String")) {
			fin.append("string ");
		}else if(str[1].equalsIgnoreCase("boolean")) {
			fin.append("bool ");
		}else if(str[1].equalsIgnoreCase("int")) {
			fin.append("int32 ");
		}else {
			System.out.println("con't find type:" + str[1]);
		}		
		fin.append(" ").append(str[2]).append(" = ").append(String.valueOf(i)).append(";");
		
		return fin;		
	}
	
	public void genFile(File directory) throws IOException, ClassNotFoundException {
		File flist[] = directory.listFiles();
		if (flist == null || flist.length == 0) {
		    System.out.println("NO BO Found");
		}else {
			for (File f : flist) {
			    if (f.isDirectory()) {
			    	genFile(f);
			    } else {			    	 
			    	String className = f.getName().substring(0,f.getName().indexOf("."));
			    	if(!className.contains("BO") ||  className.contains("Generate"))
			    		continue;
			    	String filePath = f.getPath().substring(0,f.getPath().lastIndexOf("\\"));
			    	String targetPath = TargetPath + filePath.substring(filePath.indexOf("com"));
				 	protofileDirectory = targetPath + "\\protofile\\";
				 	otwfileDirectory = targetPath + "\\otw\\";
				 	String temp = targetPath.substring(targetPath.indexOf("com"),targetPath.length());
				 	bopackageName = temp.replaceAll("\\\\", ".");
			    	System.out.println(targetPath +"----" +  className +"----" +  protofileDirectory  +"----" + otwfileDirectory +"----" +builderfileDirectory +"-----" + bopackageName);
					File tardir = new File(protofileDirectory);
					if(!tardir.exists()) {
						tardir.mkdirs();
				   	}
					File otwdir = new File(otwfileDirectory);
					if(!otwdir.exists()) {
						otwdir.mkdirs();
				   	}
			    	myFileWriter = new PrintWriter(new FileWriter(protofileDirectory + className + ".proto"),true);
			    	String superClass = genProFiles(f);
			    	myFileWriter = new PrintWriter(new FileWriter(otwfileDirectory + className + "_OTW.java"),true);
			    	genOTWContent(className, superClass);
			    	genBuildFile(className + ".proto");
			    }		    		    	
			}
		}		
	}
	
	private String genProFiles(File f) throws IOException {
		// TODO Auto-generated method stub
		String className = f.getName().substring(0,f.getName().indexOf("."));
		String fileName = f.getPath();
		InputStream input = new FileInputStream(f);
		InputStreamReader inp = new InputStreamReader(input,"UTF-8");
		BufferedReader br = new BufferedReader (inp);
		
		println(new StringBuilder().append("import \"google/protobuf/any.proto\";").toString());
		println("");
		println(new StringBuilder().append("option java_outer_classname = \"").append(className).append("_PRO\";").toString());
		println(new StringBuilder().append("option java_package = \"").append(bopackageName).append(".pro").append("\";").toString());
		println("");
		println(new StringBuilder().append("message ").append(className).append(" {").toString());
		
		String boName = fileName.substring(fileName.indexOf("com"), fileName.indexOf(".")).replace("\\", ".");
		String line = null;
		String[] l = null;
		String superclass = null;
		Field[] fileds = null;
		int count = 4;
		
		try {
			fileds = Class.forName(boName).getFields();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		println(new StringBuilder(TAB).append("optional string uuid = 1;").toString());
		println(new StringBuilder(TAB).append("optional sint64 boid = 2;").toString());
		println(new StringBuilder(TAB).append("optional string destination = 3;").toString());
			
		for(Field e:fileds) {
			if(e.getName().equalsIgnoreCase("uuid") || e.getName().equalsIgnoreCase("boid") ||e.getName().equalsIgnoreCase("destination"))
				continue;//先设了该值
			StringBuilder fin = new StringBuilder(TAB).append("optional ");
			if(e.getType().equals(java.lang.String.class)) {
				fin.append("string ").append(e.getName()).append(" = ").append(String.valueOf(count++)).append(";");
			}else if(e.getType().equals(double.class)) {
				fin.append("double ").append(e.getName()).append(" = ").append(String.valueOf(count++)).append(";");
			}else if(e.getType().equals(long.class)) {
				fin.append("sint64 ").append(e.getName()).append(" = ").append(String.valueOf(count++)).append(";");
			}else if(e.getType().equals(boolean.class)) {
				fin.append("bool ").append(e.getName()).append(" = ").append(String.valueOf(count++)).append(";");
			}else if(e.getType().equals(float.class)) {
				fin.append("float ").append(e.getName()).append(" = ").append(String.valueOf(count++)).append(";");
			}else if(e.getType().equals(int.class)) {
				fin.append("int32 ").append(e.getName()).append(" = ").append(String.valueOf(count++)).append(";");
			}
			println(fin.toString());
		}	
		println("}");
		return superclass;
	}
	
	private void genBuildFile(String fileName) {
		String strCmd = "./resource/protoc.exe -I=./" + protofileDirectory + " --java_out=./resource/ ./" + protofileDirectory + fileName;  		
//		String strCmd = "D:/software/protobuf-3.5.1/src/protoc.exe -I=./" + protofileDirectory + " --java_out=./resource/ ./" + protofileDirectory + fileName;  
		try {
		    Runtime.getRuntime().exec(strCmd);
		} catch (IOException e) {
	        e.printStackTrace();
		}			  
	}

	private void genOTWContent(String boName, String superClassName) throws ClassNotFoundException {
		// TODO Auto-generated method stub
		String _proName = boName + "_PRO";
		String _otwName = boName + "_OTW";
		StringBuilder builder = new StringBuilder(_proName).append(".").append(boName).append(".Builder");
		String boClassName = bopackageName + "." + boName;
		
		Class bo = Class.forName(boClassName);
		Field[] fields=bo.getFields();

		/*************    文件头    ******************/
		println(new StringBuilder("package ").append(bopackageName).append(".otw;").toString());
		println("");
		
		println(new StringBuilder("import ").append(bopackageName).append(".").append(boName).append(";").toString());
		println(new StringBuilder("import ").append(bopackageName).append(".pro.").append(boName).append("_PRO;").toString());
		println(new StringBuilder("import com.google.protobuf.InvalidProtocolBufferException;").toString());
		println(new StringBuilder("import com.purefun.fstp.core.bo.commom.ICommom_OTW;").toString());
		println(new StringBuilder("import com.google.protobuf.Any;").toString());
		
		/*************    文件头    ******************/
		println("");
		println(new StringBuilder("public class ").append(_otwName).append(" implements ICommom_OTW {").toString());
		
		//定义变量
		println(new StringBuilder(TAB).append(builder).append(" ").append(BUILDER).append(" = null;").toString());
		println(new StringBuilder(TAB).append(boName).append(" ").append(BO).append(" = null;").toString());
		println("");
		
		//定义构造器1
		println(new StringBuilder(TAB).append("public ").append(_otwName).append("() {").toString());
		println(new StringBuilder(TAB).append(TAB).append(BUILDER).append(" = ")
									  .append(_proName).append(".").append(boName).append(".").append("newBuilder();").toString());
		println(new StringBuilder(TAB).append(TAB).append(BO).append("= new ").append(boName).append("();").toString());
		println(new StringBuilder(TAB).append(TAB).append(BUILDER).append(".setUuid(bo.uuid);").toString());
		println(new StringBuilder(TAB).append(TAB).append(BUILDER).append(".setBoid(bo.boid);").toString());
		println(new StringBuilder(TAB).append(TAB).append(BUILDER).append(".setDestination(bo.destination);").toString());
		println(new StringBuilder(TAB).append("}").toString());
		println("");
		
		//定义构造器2
		println(new StringBuilder(TAB).append("public ").append(_otwName).append("(byte[] message) throws InvalidProtocolBufferException {").toString());
		println(new StringBuilder(TAB).append(TAB).append(BUILDER).append(" = ")
				  .append(_proName).append(".").append(boName).append(".").append("newBuilder();").toString());
		println(new StringBuilder(TAB).append(TAB).append(BO).append("= new ").append(boName).append("();").toString());
		println(new StringBuilder(TAB).append(TAB).append(_proName).append(".").append(boName).append(" receive = ")
									  .append(_proName).append(".").append(boName).append(".parseFrom(message);").toString());
		genStructSetMethod(fields);
		println(new StringBuilder(TAB).append("}").toString());
		println("");
		
		//定义构造器3
		println(new StringBuilder(TAB).append("public ").append(_otwName).append("(").append(boName).append(" bofrom){").toString());
		println(new StringBuilder(TAB).append(TAB).append(BUILDER).append(" = ")
				  .append(_proName).append(".").append(boName).append(".").append("newBuilder();").toString());
		println(new StringBuilder(TAB).append(TAB).append(BO).append("= new ").append(boName).append("();").toString());

		genBOStructSetMethod(fields);
		println(new StringBuilder(TAB).append("}").toString());
		println("");
		
		//定义serial
		println(new StringBuilder(TAB).append("public byte[] serial() {").toString());
		println(new StringBuilder(TAB).append(TAB).append("return builder.build().toByteArray();").toString());
		println(new StringBuilder(TAB).append("}").toString());
		println("");
		
		//定义@Override
		//builder
		println(new StringBuilder(TAB).append("@Override").toString());
		println(new StringBuilder(TAB).append("public com.google.protobuf.GeneratedMessageV3.Builder getBuilder() { ").toString());
		println(new StringBuilder(TAB).append(TAB).append("return builder;").toString());
		println(new StringBuilder(TAB).append("}").toString());
		println("");
		
		//bo
		println(new StringBuilder(TAB).append("@Override").toString());
		println(new StringBuilder(TAB).append("public ").append(boName).append(" getBo() { ").toString());
		println(new StringBuilder(TAB).append(TAB).append("return bo;").toString());
		println(new StringBuilder(TAB).append("}").toString());
		println("");	
		
		genMethod(fields);
		
		genToString(_otwName,fields);
		println("}");					
	}

	@Deprecated
	private void genOtherSetMethod(Field[] fields) {
		// TODO Auto-generated method stub
		for(Field field : fields) {
			StringBuilder setmethodName = new StringBuilder();
			String fieldName = field.getName();
			StringBuilder first = null;
			StringBuilder last = null;
			
			last = new StringBuilder(fieldName.substring(0, 1).toUpperCase()).append(fieldName.substring(1));
			if(-1 != fieldName.indexOf("_")) {
				first = new StringBuilder();
				String[] temp = fieldName.split("_");
				for(String t:temp) {
					first.append(t.substring(0,1).toUpperCase()).append(t.substring(1));
				}
			}else {
				first = last;
			}
			println(new StringBuilder(TAB).append(TAB).append("set").append(last).append("(")
										  .append("bofrom.get").append(first).append("());").toString());
		}
	}

	@Deprecated
	private String importSuperClass(String superClassName) {
		// TODO Auto-generated method stub
		String proName = superClassName + "_PRO.java";
		String filePath = importFile(new File(TargetPath), proName);
		String temp = null;
		if (filePath!=null) {
			temp = filePath.substring(filePath.indexOf("\\")+1, filePath.indexOf(".")).replace("\\", ".");
		}
		return temp;	
	}

	private String importFile(File directory, String superName) {
		// TODO Auto-generated method stub
		File flist[] = directory.listFiles();
		String ret = null;
		if (flist == null || flist.length == 0) {
		    System.out.println("没有找到需要导入的父类");
		    return null;
		}
		for (File f : flist) {
		    if (f.isDirectory()) {
		    	ret = importFile(f, superName);
		    	if(ret!=null) {
		    		return ret;
		    	}
		    } else {
		    	if(f.getName().equalsIgnoreCase(superName)) {
		    		ret = f.getPath();
		    		return ret;
		    	}
		    }
		}
		return ret;
	}

	private void genBOStructSetMethod(Field[] fields) {
		// TODO Auto-generated method stub
		for(Field field : fields) {
			StringBuilder setmethodName = new StringBuilder();
			String fieldName = field.getName();
			StringBuilder first = new StringBuilder(fieldName.substring(0, 1).toUpperCase());
			StringBuilder last = new StringBuilder(fieldName.substring(1));
			setmethodName.append(first).append(last);
			println(new StringBuilder(TAB).append(TAB).append("set").append(setmethodName).append("(")
										  .append("bofrom.").append(fieldName).append(");").toString());
		}					
	}

	private void genToString(String name,Field[] fields) {
		// TODO Auto-generated method stub
		println(new StringBuilder(TAB).append("public String toString() {").toString()); 
		println(new StringBuilder(TAB).append(TAB).append("return \"").append(name).append(" [\"+").toString());
		
		println(new StringBuilder(TAB).append(TAB).append(TAB).append("\"").append("uuid")
                .append(" = \" + ").append("getUuid").append("() +").append("\",\" +").toString());			
		println(new StringBuilder(TAB).append(TAB).append(TAB).append("\"").append("boid")
                .append(" = \" + ").append("getBoid").append("() +").append("\",\" +").toString());			
		println(new StringBuilder(TAB).append(TAB).append(TAB).append("\"").append("destination")
                .append(" = \" + ").append("getDestination").append("() +").append("\",\" +").toString());			

		for(Field field:fields) {
			if(field.getName().equalsIgnoreCase("uuid") || field.getName().equalsIgnoreCase("boid") ||field.getName().equalsIgnoreCase("destination"))
				continue;//先设了该值
			StringBuilder fieldName = new StringBuilder(field.getName());
			StringBuilder methodName = new StringBuilder("get");
			StringBuilder first = new StringBuilder(fieldName.substring(0, 1).toUpperCase());
			StringBuilder last = new StringBuilder(fieldName.substring(1));
			methodName.append(first).append(last);
			println(new StringBuilder(TAB).append(TAB).append(TAB).append("\"").append(field.getName())
					                      .append(" = \" + ").append(methodName).append("() +").append("\",\" +").toString());			
		}
		println(new StringBuilder(TAB).append(TAB).append(" \"]\";").toString()); 
		println(new StringBuilder(TAB).append("}").toString());
		
	}

	private void genStructSetMethod(Field[] fields) {
		// TODO Auto-generated method stub
		for(Field field : fields) {
			StringBuilder setmethodName = new StringBuilder();
			StringBuilder getmethodName = new StringBuilder();
			String fieldName = field.getName();
			StringBuilder first = new StringBuilder(fieldName.substring(0, 1).toUpperCase());
			StringBuilder last = new StringBuilder(fieldName.substring(1));
						
			String[] str = fieldName.split("_");
			setmethodName.append(first).append(last);
			
			if(str.length == 1) {//没有  _
				getmethodName = setmethodName;
			}else {
				StringBuilder all = new StringBuilder();
				for(String each:str) {
					all.append(each.substring(0, 1).toUpperCase());
					all.append(each.substring(1).toString());
				}
				getmethodName.append(all);
			}
							
			println(new StringBuilder(TAB).append(TAB).append("set").append(setmethodName).append("(")
										  .append("receive.get").append(getmethodName).append("());").toString());
		}					
	}

	private void genMethod(Field[] fields) {
		// TODO Auto-generated method stub
		for(Field field : fields) {
			Class type = field.getType();
			genGetMethod(field,type);
			genSetMethod(field,type);
		}	
	}

	private void genSetMethod(Field field,Class type) {
		// TODO Auto-generated method stub
		String fieldName = field.getName();		
		StringBuilder methodName = new StringBuilder();
		StringBuilder all = new StringBuilder("set");
		
		String[] str = fieldName.split("_");
		if(str.length == 1) {//没有  _
			methodName = all;
		}else {
			StringBuilder first = new StringBuilder(fieldName.substring(0, 1).toUpperCase());
			StringBuilder last = new StringBuilder(fieldName.substring(1));
			methodName.append("set").append(first).append(last);
		}
		for(String each:str) {
			all.append(each.substring(0, 1).toUpperCase());
			all.append(each.substring(1).toString());
		}
		
		println(new StringBuilder(TAB).append("public void ").append(methodName)
				                      .append("(").append(type.getName()).append(" ").append(field.getName()).append(") {").toString());
		println(new StringBuffer(TAB).append(TAB)
                .append("bo.").append(field.getName()).append(" = ").append(field.getName()).append(";").toString());
		println(new StringBuffer(TAB).append(TAB)
				                     .append("builder.").append(all)
				                     .append("(").append(field.getName()).append(");").toString());
		println(new StringBuilder(TAB).append("}").toString());
		println("");		
	}

	private void genGetMethod(Field field,Class type) {
		// TODO Auto-generated method stub
		String fieldName = field.getName();
		
		StringBuilder methodName = new StringBuilder();
		StringBuilder all = new StringBuilder("get");
		
		String[] str = fieldName.split("_");
		if(str.length == 1) {//没有  _
			methodName = all;
		}else {
			StringBuilder first = new StringBuilder(fieldName.substring(0, 1).toUpperCase());
			StringBuilder last = new StringBuilder(fieldName.substring(1));
			methodName.append("get").append(first).append(last);
		}
			
		for(String each:str) {
			all.append(each.substring(0, 1).toUpperCase());
			all.append(each.substring(1).toString());
		}
		println(new StringBuilder(TAB).append("public ").append(type.getName()).append(" ").append(methodName).append("() {").toString());
		println(new StringBuilder(TAB).append(TAB).append("return ").
				                       append(BUILDER).append(".").append(all).append("()").append(";").toString());
		println(new StringBuilder(TAB).append("}").toString());
		println("");		
	}

	public static void main(String[] args) throws IOException, ClassNotFoundException {	
		GenerateJAVABOFiles writer = new GenerateJAVABOFiles("src/main/java/");
		writer.path = "src/main/java/";
		writer.genFile(new File(writer.path));
	}
}
