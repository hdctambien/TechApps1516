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
		config.printDetailed();
	}
}
