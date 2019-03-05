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

public class GeneratePythonBOFiles {
	PrintWriter myFileWriter;
	private String myIndent = "";
	String protofileDirectory = null;
	String modelDirectory = null;
	String otwfileDirectory = null;
	String builderfileDirectory = null;
	String bopackageName = null;
	final String TAB = "    ";
	final String BO = "bo";
	final String BUILDER = "builder";	
	String TargetPath = "python\\";
	
	
	public GeneratePythonBOFiles() {
	}
	
	private void println(String str) {
		this.myFileWriter.println(str); 
    }

	@Deprecated
	private Object currIndent() {
		// TODO Auto-generated method stub
		return myIndent;
	}
	
	@Deprecated
	private StringBuilder analysis(String line,int i) {
		// TODO Auto-generated method stub
		line = line.replaceAll(";", "");
		String[] str = line.trim().split(" "); 
		StringBuilder fin = new StringBuilder();

		if(str[2].equalsIgnoreCase("uuid") || str[2].equalsIgnoreCase("boid") || str[2].equalsIgnoreCase("destination")) {
			fin.append("    required ");
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
	
	public void genProtoFile(File directory) throws IOException {
		File flist[] = directory.listFiles();
				
		if (flist == null || flist.length == 0) {
		    System.out.println("NO BO Found");
		}else {
			File tardir = new File(protofileDirectory);
			if(!tardir.exists()) {
				tardir.mkdirs();
	    	}
			for (File f : flist) {
			    if (f.isDirectory()) {
//			    	generateProtoFile(f);
			    } else {			    	 
			    	 String className = f.getName().substring(0,f.getName().indexOf("."));
			    	 if(!className.contains("BO") || className.equalsIgnoreCase("BaseBO"))
			    		 continue;
			    	 myFileWriter = new PrintWriter(new FileWriter(protofileDirectory + className + ".proto"),true);
			    	 genProFiles(f);
			    }		    		    	
			}
		}		
	}
	
	public void genModelFile(File directory) throws IOException, ClassNotFoundException {
		File flist[] = directory.listFiles();
//		String targetDirectory = directory.getPath() + "\\protofile\\";
				
		if (flist == null || flist.length == 0) {
		    System.out.println("NO BO Found");
		}else {
			File tardir = new File(modelDirectory);
			if(!tardir.exists()) {
				tardir.mkdirs();
	    	}
			for (File f : flist) {
			    if (f.isDirectory()) {
//			    	generateProtoFile(f);
			    } else {			    	 
			    	 String className = f.getName().substring(0,f.getName().indexOf("."));
			    	 if(!className.contains("BO") || className.equalsIgnoreCase("BaseBO"))
			    		 continue;
			    	 myFileWriter = new PrintWriter(new FileWriter(modelDirectory + className + ".py"),true);
			    	 genPyModel(f);
			    }		    		    	
			}
		}		
	}	
	private void genProFiles(File f) throws IOException {
		// TODO Auto-generated method stub
		String className = f.getName().substring(0,f.getName().indexOf("."));
		String fileName = f.getPath();

		println(new StringBuilder().append("syntax = \"proto2\";").toString());
		println(new StringBuilder().append("package ").append(bopackageName).append(";").toString());
		println("");
		println(new StringBuilder().append("message ").append(className).append(" {").toString());
				
		String boName = fileName.substring(fileName.indexOf("com"), fileName.indexOf(".")).replace("\\", ".");

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
	}

	private void genOTWContent(String boName) throws ClassNotFoundException {
		// TODO Auto-generated method stub
		String _pb2Name = boName + "_pb2";
		String _otwName = boName + "_OTW";
		String boClassName = bopackageName + "." + boName;
		
		Class bo = Class.forName(boClassName);
		Field[] fields=bo.getFields();
		
		println(new StringBuilder("from src.core.common.ICommon_OTW import ICommon_OTW").toString());
		println(new StringBuilder("from ").append("src.").append(bopackageName).append(".pro import ").append(_pb2Name).toString());
		println(new StringBuilder("from ").append("src.").append(bopackageName).append(".model.").append(boName).append(" import ").append(boName).toString());
		println("");
		
		println(new StringBuilder("class ").append(_otwName).append("(ICommon_OTW):").toString());
		println("");
		
		//_init(self,object)
		println(new StringBuilder(TAB).append("def __init__(self, byteMsg = None):").toString());
		println(new StringBuilder(TAB).append(TAB).append("self._bo_pro = ").append(_pb2Name).append(".").append(boName).append("()").toString());
		println(new StringBuilder(TAB).append(TAB).append("self._bo = ").append(boName).append("()").toString());
		println("");
		println(new StringBuilder(TAB).append(TAB).append("if byteMsg is not None:").toString());
		println(new StringBuilder(TAB).append(TAB).append(TAB).
									append("self._bo_pro.ParseFromString(byteMsg)").toString());
		println(new StringBuilder(TAB).append(TAB).append(TAB).
									append("self.__setDataFromPB()").toString());
		println(new StringBuilder(TAB).append(TAB).append("else:").toString());
		println(new StringBuilder(TAB).append(TAB).append(TAB).
									append("self.__setDataFromBO()").toString());		
		println("");
		
		//__setDataFromBO
		println(new StringBuilder(TAB).append("def __setDataFromBO(self):").toString());
		genDataFromBO(fields);
		println("");
		
		//__setDataFromPB
		println(new StringBuilder(TAB).append("def __setDataFromPB(self):").toString());
		genDataFromPB(fields);
		println("");
		
		//getBO
		println(new StringBuilder(TAB).append("def getBO(self):").toString());
		println(new StringBuilder(TAB).append(TAB).append("return self._bo").toString());
		println("");
		
		//getProBO
		println(new StringBuilder(TAB).append("def getProBO(self):").toString());
		println(new StringBuilder(TAB).append(TAB).append("return self._bo_pro").toString());
		println("");
		
		//get/set method
		genMethod(fields);
		
		genToString(_otwName, fields);
		
	}
	
	private void genMethod(Field[] fields) {
		// TODO Auto-generated method stub
		for(Field field : fields) {
			genGetMethod(field);
			genSetMethod(field);
		}
	}

	private void genSetMethod(Field field) {
		// TODO Auto-generated method stub
		String name = field.getName();
		String part1 = name.substring(0, 1).toUpperCase();
		String part2 = name.substring(1);
		println(new StringBuilder(TAB).append("def set").append(part1).append(part2).append("(self, ").append(name).append("):").toString());
		println(new StringBuilder(TAB).append(TAB).append("self._bo.").append(name).append(" = ").append(name).toString());
		println(new StringBuilder(TAB).append(TAB).append("self._bo_pro.").append(name).append(" = ").append(name).toString());
		println("");
	}

	private void genGetMethod(Field field) {
		// TODO Auto-generated method stub
		String name = field.getName();
		String part1 = name.substring(0, 1).toUpperCase();
		String part2 = name.substring(1);
		println(new StringBuilder(TAB).append("def get").append(part1).append(part2).append("(self):").toString());
		println(new StringBuilder(TAB).append(TAB).append("return self._bo.").append(name).toString());
		println("");
	}

	private void genDataFromPB(Field[] fields) {
		// TODO Auto-generated method stub
		for(Field each : fields) {
			println(new StringBuilder(TAB).append(TAB).
					append("self._bo.").append(each.getName()).append(" = ").append("self._bo_pro.").append(each.getName()).toString());
		}	
	}

	private void genDataFromBO(Field[] fields) {
		// TODO Auto-generated method stub
		for(Field each : fields) {
			println(new StringBuilder(TAB).append(TAB).
					append("self._bo_pro.").append(each.getName()).append(" = ").append("self._bo.").append(each.getName()).toString());
		}		
	}

	private void genToString(String name,Field[] fields) {
		// TODO Auto-generated method stub
		println(new StringBuilder(TAB).append("def toString(self):").toString()); 
		StringBuilder all = new StringBuilder(TAB).append(TAB).append("return \"").append(name).append(" [\"+");
		for(Field field:fields) {
			StringBuilder fieldName = new StringBuilder(field.getName());
			StringBuilder methodName = new StringBuilder("get");
			StringBuilder first = new StringBuilder(fieldName.substring(0, 1).toUpperCase());
			StringBuilder last = new StringBuilder(fieldName.substring(1));
			methodName.append(first).append(last);
			if(!String.class.equals(field.getType())) {
				all.append("\"").append(field.getName()).append(" = \" + str(self.").append(methodName).append("()) +").append("\",\" +");
			}else {
				all.append("\"").append(field.getName()).append(" = \" + self.").append(methodName).append("() +").append("\",\" +");
			}
		}
		all.append("\"]\"");
		println(all.toString());
	}

	private void genPyModel(File f) throws IOException, ClassNotFoundException {
		// TODO Auto-generated method stub
		String className = f.getName().substring(0,f.getName().indexOf("."));
		String fileName = f.getPath();
		InputStream input = new FileInputStream(f);
		InputStreamReader inp = new InputStreamReader(input,"UTF-8");
		BufferedReader br = new BufferedReader (inp);
			
		println(new StringBuilder().append("import uuid").toString());
		println("");
		println(new StringBuilder().append("class ").append(className).append("(object):").toString());
		println("");
		println(new StringBuilder().append(TAB).append("def __init__(self):").toString());	
				
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
		
		long boid = 0L;
		String des = null;
		
		while((line = br.readLine())!=null) {
			if(-1 != line.indexOf("@fstpbo")) {			
				String[] temp = line.substring(line.indexOf("(")+1, line.indexOf(")")).split(",");
				System.out.println(temp[0]+"-----"+temp[1]);
//				boid = 4L, destination = "fstp.core.manager.qnsrespond"
				boid = Long.parseLong(temp[0].substring(temp[0].indexOf("=")+1, temp[0].indexOf("L")).trim());
				des = temp[1].substring(temp[1].indexOf("=")+1).replace("\"", "").trim();
			}
			if(-1 != line.indexOf("}")) {
				break;
			}
		}	
		
		for(Field e:fileds) {
			if(e.getName().equalsIgnoreCase("uuid")) {
				println(new StringBuilder().append(TAB).append(TAB).append("self.uuid = str(uuid.uuid1())").toString());
			}else if(e.getName().equalsIgnoreCase("boid")){
				println(new StringBuilder().append(TAB).append(TAB).append("self.boid = ").append(boid).toString());
			}else if(e.getName().equalsIgnoreCase("destination")){
				println(new StringBuilder().append(TAB).append(TAB).append("self.destination = \"").append(des).append("\"").toString());
			}else if(e.getType().equals(java.lang.String.class)){
				println(new StringBuilder().append(TAB).append(TAB).append("self.").append(e.getName()).append(" = ''").toString());
			}else if(e.getType().equals(double.class)){
				println(new StringBuilder().append(TAB).append(TAB).append("self.").append(e.getName()).append(" = 0.0").toString());
			}else if(e.getType().equals(boolean.class)){
				println(new StringBuilder().append(TAB).append(TAB).append("self.").append(e.getName()).append(" = False").toString());
			}else if(e.getType().equals(float.class)){
				println(new StringBuilder().append(TAB).append(TAB).append("self.").append(e.getName()).append(" = 0.0").toString());
			}else if(e.getType().equals(int.class)){
				println(new StringBuilder().append(TAB).append(TAB).append("self.").append(e.getName()).append(" = 0").toString());
			}else if(e.getType().equals(long.class)){
				println(new StringBuilder().append(TAB).append(TAB).append("self.").append(e.getName()).append(" = 0L").toString());
			}
		}		
	}

	private void genBuildFile(String fileName) {
		String strCmd = "./resource/protoc.exe -I=./" + protofileDirectory + " --python_out=./" + builderfileDirectory + " ./" + protofileDirectory + fileName;  
		try {
		    Runtime.getRuntime().exec(strCmd);
		} catch (IOException e) {
	        e.printStackTrace();
		}			  
	}
	
	private void genInit(File directory) throws IOException {
		// TODO Auto-generated method stub
		File flist[] = directory.listFiles();
		if (flist == null || flist.length == 0) {
		    return;
		}else {
			for (File f : flist) {
			    if (f.isDirectory()) {	
			    	PrintWriter initWriter = new PrintWriter(new FileWriter(f.getPath() + "/__init__.py"),true);
			    	initWriter.println("");
			    	genInit(f);		    	
			    } 
			}
		}
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
			    	if(!className.contains("BO") || className.equalsIgnoreCase("BaseBO") || className.contains("Generate"))
			    		continue;
			    	String filePath = f.getPath().substring(0,f.getPath().lastIndexOf("\\"));
			    	String targetPath = TargetPath + filePath.substring(filePath.indexOf("com"));
				 	protofileDirectory = targetPath + "\\protofile\\";
				 	otwfileDirectory = targetPath + "\\otw\\";
				 	builderfileDirectory = targetPath + "\\pro\\";
				 	modelDirectory = targetPath + "\\model\\";
				 	System.out.println(targetPath);
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
					File prodir = new File(builderfileDirectory);
					if(!prodir.exists()) {
						prodir.mkdirs();
				   	}
					File modeldir = new File(modelDirectory);
					if(!modeldir.exists()) {
						modeldir.mkdirs();
				   	}
			    	myFileWriter = new PrintWriter(new FileWriter(protofileDirectory + className + ".proto"),true);
			    	genProFiles(f);
			    	myFileWriter = new PrintWriter(new FileWriter(modelDirectory + className + ".py"),true);
			    	genPyModel(f);
			    	myFileWriter = new PrintWriter(new FileWriter(otwfileDirectory + className + "_OTW.py"),true);
			    	genOTWContent(className);
//			    	myFileWriter = new PrintWriter(new FileWriter(otwfileDirectory + className + "_OTW.java"),true);
			    	genBuildFile(className + ".proto");
			    }		    		    	
			}
		}		
	}
	
	public static void main(String[] args) throws IOException, ClassNotFoundException {	
		GeneratePythonBOFiles writer = new GeneratePythonBOFiles();	
		writer.genFile(new File("src/main/java/"));
		writer.genInit(new File("python/"));		
	}


}
