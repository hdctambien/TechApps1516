package spacegame.util;

import java.util.Map;

public class ConfigTester {

	public static void main(String[] args){
		Config config = new Config();
		try {
			config.loadConfig();
		} catch (ConfigParseException e) {
			e.printStackTrace();
		}
		System.out.println("Loaded 'spacegame.config'");
		System.out.println("ALL properties:");
		for(Map.Entry<String, String> entry: config.getAll().entrySet()){
			System.out.println("   "+entry.getKey()+"=="+entry.getValue());
		}
		System.out.println("STRING properties:");
		for(Map.Entry<String, String> entry: config.getStrings().entrySet()){
			System.out.println("   "+entry.getKey()+"=="+entry.getValue());
		}
		System.out.println("INT properties:");
		for(Map.Entry<String, Integer> entry: config.getInts().entrySet()){
			System.out.println("   "+entry.getKey()+"=="+entry.getValue());
		}
		System.out.println("DOUBLE properties:");
		for(Map.Entry<String, Double> entry: config.getDoubles().entrySet()){
			System.out.println("   "+entry.getKey()+"=="+entry.getValue());
		}
		System.out.println("BOOL properties:");
		for(Map.Entry<String, Boolean> entry: config.getBools().entrySet()){
			System.out.println("   "+entry.getKey()+"=="+entry.getValue());
		}
	}
}
