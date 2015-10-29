package spacegame.test;

import static org.junit.Assert.*;

import spacegame.map.*;
import org.junit.Test;

public class SerialTest {

	@Test
	public void test() {
		//fail("Not yet implemented");
		assertTrue(true);
	}
	
	@Test
	public void testSerialException() {
		GameMap map = createTestMap();
		GameMap map2 = new GameMap();
		map2.unserialize(map.serialize());
	}
	@Test
	public void testRecreateRecentSerialBug(){
		GameMap map = createTestMap();
		GameMap map2 = new GameMap();
		map2.unserialize("\n"+map.serialize());
	}
	
	@Test
	public void testSerialStringEquals(){
		GameMap map = createTestMap();
		GameMap map2 = new GameMap();
		String serial = map.serialize();
		System.out.println(serial);
		map2.unserialize(serial);
		String serial2 = map2.serialize();
		System.out.println(serial2);
		//assertTrue(serial.equals(serial2));
	}
	
	@Test
	public void testClone(){
		GameMap map = createTestMap();
		GameMap clone = map.clone();
		assertTrue(map.equals(clone));
	}
	
	@Test
	public void testSerialEquals(){
		GameMap map = createTestMap();
		GameMap map2 = new GameMap();
		map2.unserialize(map.serialize());
		assertTrue(map.equals(map2));
	}
	
	@Test
	public void testEquals(){
		GameMap map = createTestMap();
		assertTrue(map.equals(map));
	}
	
	private GameMap createTestMap(){
		GameMap map = new GameMap();
		EntityFactory factory = new EntityFactory();
		map.addEntity(factory.createAsteroid());
		map.addEntity(factory.createShip());
		Entity[] entities = factory.createEntityReferenceSerialTest();
		for(int i = 0; i<entities.length;i++){
			map.addEntity(entities[i]);
		}
		return map;
	}

}
