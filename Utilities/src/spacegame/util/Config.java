package spacegame.util;

import java.util.*;
import java.io.*;

public class Config {

	public static final String DEFAULT_PATH = "spacegame";
	public static final String FILE_EXTENSION = ".config";
	public static final String VERSION = "0.2";	
	public static final String DELIMINATOR = "=";
	
	private Hashtable<String, String> stringVars;
	private Hashtable<String, Integer> intVars;
	private Hashtable<String, Double> doubleVars;
	private Hashtable<String, Boolean> boolVars;
	
	private String path;
	
	public Config(){
		this(DEFAULT_PATH);
	}
	public Config(String path){
		this.path = path+FILE_EXTENSION;
		stringVars = new Hashtable<String, String>();
		intVars = new Hashtable<String, Integer>();
		doubleVars = new Hashtable<String, Double>();
		boolVars = new Hashtable<String, Boolean>();
	}
	public Config(Hashtable<String, String> strings, Hashtable<String, Integer> ints,
			Hashtable<String, Double> doubles, Hashtable<String, Boolean> bools){
		this();
		putDefaultValues(strings,ints,doubles,bools);
	}
	public Config(String path, Hashtable<String, String> strings, Hashtable<String, Integer> ints,
			Hashtable<String, Double> doubles, Hashtable<String, Boolean> bools){
		this(path);
		putDefaultValues(strings, ints, doubles, bools);
	}
	
	public void putDefaultValues(Hashtable<String, String> strings, Hashtable<String, Integer> ints,
			Hashtable<String, Double> doubles, Hashtable<String, Boolean> bools){
		stringVars = (Hashtable<String, String>) strings.clone();
		intVars = (Hashtable<String, Integer>) ints.clone();
		doubleVars = (Hashtable<String, Double>) doubles.clone();
		boolVars = (Hashtable<String, Boolean>) bools.clone();		
	}
	
	public void loadConfig() throws ConfigParseException{
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
					String[] pieces = line.split(DELIMINATOR,2);
					if(pieces.length<2){throw new ConfigParseException("Error in file line:"+lcount
							+ "deliminator "+DELIMINATOR+" missing!");}
					//Relying on some side effects of short-circuit evaluation
					//Order of parse precedence: int, double, boolean, String
					if(tryInt(pieces[0],pieces[1])||tryDouble(pieces[0],pieces[1])||tryBool(pieces[0],pieces[1])){}
					else{
						stringVars.put(pieces[0],pieces[1]);
					}					
				}
				reader.close();
			}
			
		} catch (FileNotFoundException e) {
			throw new ConfigParseException("File could not be opened!",e);
		} catch (IOException e) {
			throw new ConfigParseException( "IO Error occured while reading!",e);
		}
	}
	
	public void saveConfig() throws IOException{
		FileOutputStream out = new FileOutputStream(path);
		PrintWriter writer = new PrintWriter(out);
		writer.println("Config "+VERSION);
		for(Map.Entry<String, String> entry: getAll().entrySet()){
			writer.println(entry.getKey()+DELIMINATOR+entry.getValue());
		}
		writer.close();
	}
	
	public String getString(String key){
		return getString(key, false);
	}
	
	public String getString(String key, boolean include){
		if(stringVars.containsKey(key)){
			return stringVars.get(key);
		}else if(include){
			//include String values of ints, doubles, and booleans
			if(intVars.containsKey(key)){
				return Integer.toString(intVars.get(key));
			}else if(doubleVars.containsKey(key)){
				return Double.toString(doubleVars.get(key));
			}else if(boolVars.containsKey(key)){
				return Boolean.toString(boolVars.get(key));
			}
		}
		return null;
	}
	
	public int getInt(String key){
		return intVars.get(key);
	}
	
	public double getDouble(String key){
		return doubleVars.get(key);
	}
	
	public boolean getBool(String key){
		return boolVars.get(key);
	}
	
	public void put(String key, String value){
		stringVars.put(key, value);
	}
	
	public void put(String key, int value){
		intVars.put(key, value);
	}
	
	public void put(String key, double value){
		doubleVars.put(key, value);
	}
	
	public void put(String key, boolean value){
		boolVars.put(key, value);
	}
	
	public void put(String key, Object value){
		if(value instanceof Integer){
			intVars.put(key,(Integer)value);
		}else if(value instanceof Double){
			doubleVars.put(key, (Double)value);
		}else if(value instanceof Boolean){
			boolVars.put(key, (Boolean)value);
		}else if(value instanceof String){
			stringVars.put(key, (String)value);
		}
	}
	
	public boolean hasInt(String key){
		return intVars.containsKey(key);
	}
	public boolean hasDouble(String key){
		return doubleVars.containsKey(key);
	}
	public boolean hasBool(String key){
		return boolVars.containsKey(key);
	}
	public boolean hasString(String key){
		return stringVars.containsKey(key);
	}
	public boolean hasAny(String key){
		return intVars.containsKey(key)||doubleVars.containsKey(key)||boolVars.containsKey(key)
				||stringVars.containsKey(key);
	}
	
	public boolean tryInt(String key, String value){
		try{
			int i = Integer.parseInt(value);
			intVars.put(key, i);
			return true;
		}catch(NumberFormatException e){
			return false;
		}
	}
	public boolean tryDouble(String key, String value){
		try{
			double d = Double.parseDouble(value);
			doubleVars.put(key, d);
			return true;
		}catch(NumberFormatException e){
			return false;
		}
	}
	public boolean tryBool(String key, String value){
		String lower = value.toLowerCase();
		if(lower.equals("true")){
			boolVars.put(key, true);
		}else if(lower.equals("false")){
			boolVars.put(key, false);
		}else{
			return false;
		}
		return true;
	}
	
	public Hashtable<String,String> getStrings(){
		return (Hashtable<String, String>) stringVars.clone();
	}
	public Hashtable<String, Integer> getInts(){
		return (Hashtable<String, Integer>) intVars.clone();
	}
	public Hashtable<String, Double> getDoubles(){
		return (Hashtable<String, Double>) doubleVars.clone();
	}
	public Hashtable<String, Boolean> getBools(){
		return (Hashtable<String, Boolean>) boolVars.clone();
	}
	public Hashtable<String,String> getAll(){
		Hashtable<String,String> allVars = (Hashtable<String, String>) stringVars.clone();
		for(Map.Entry<String, Integer> entry: intVars.entrySet()){
			allVars.put(entry.getKey(), entry.getValue().toString());
		}
		for(Map.Entry<String, Double> entry: doubleVars.entrySet()){
			allVars.put(entry.getKey(), entry.getValue().toString());
		}
		for(Map.Entry<String, Boolean> entry: boolVars.entrySet()){
			allVars.put(entry.getKey(), entry.getValue().toString());
		}
		return allVars;
	}
	
}
