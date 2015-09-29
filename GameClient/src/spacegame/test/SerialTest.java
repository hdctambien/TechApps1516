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
		GameMap map = new GameMap();
		EntityFactory factory = new EntityFactory();
		map.addEntity(factory.createAsteroid());
		map.addEntity(factory.createShip());
		GameMap map2 = new GameMap();
		map2.unserialize(map.serialize());
	}

}
