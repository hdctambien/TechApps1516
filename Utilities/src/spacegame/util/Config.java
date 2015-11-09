package spacegame.util;

import java.util.*;
import java.io.*;

public class Config {

	public static final String DEFAULT_PATH = "spacegame";
	public static final String FILE_EXTENSION = ".config";
	public static final String VERSION = "0.1";	
	
	private Hashtable<String, String> stringVars;
	private Hashtable<String, Integer> intVars;
	private Hashtable<String, Double> doubleVars;
	
	private String path;
	
	public Config(){
		this(DEFAULT_PATH);
	}
	public Config(String path){
		this.path = path+FILE_EXTENSION;
		stringVars = new Hashtable<String, String>();
		intVars = new Hashtable<String, Integer>();
		doubleVars = new Hashtable<String, Double>();
	}
	
	public void loadConfig(){
		try {
			FileInputStream in = new FileInputStream(path);
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			String firstline = reader.readLine();
			String[] firstpieces = firstline.split(" ");
			if(firstpieces.length<2){
				throw new ConfigParseException("Config Header formatted incorrectly!");
			}else if(!firstpieces[1].equals(VERSION)){
				throw new ConfigParseException("Config file version ("+firstpieces[1]+") is unsupported! Current version:"+VERSION);
			}else{
				String line;
				int lcount = 1;
				while((line=reader.readLine())!=null){
					String[] pieces = line.split(":",2);
					if(pieces.length<2){throw new ConfigParseException("Error in file line:"+lcount
							+ "seperator ':' missing!");}
					//int
					//double
					//String
					
				}
				reader.close();
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
