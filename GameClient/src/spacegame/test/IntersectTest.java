package spacegame.test;

import static org.junit.Assert.*;

import spacegame.map.*;
import org.junit.Test;

import spacegame.map.Intersect;

public class IntersectTest {

	@Test
	public void testLine(){
		Vector2 s1 = new Vector2(60,60,Vector2.FLAG_RECT);
		Vector2 v1 = new Vector2(60,-30,Vector2.FLAG_RECT);
		Vector2 s2 = new Vector2(40,40,Vector2.FLAG_RECT);
		Vector2 v2 = new Vector2(100,15,Vector2.FLAG_RECT);
		assertTrue(Intersect.linesIntersect(s1,v1,s2,v2));
	}
	@Test
	public void testParallel(){
		Vector2 s1 = new Vector2(60,60,Vector2.FLAG_RECT);
		Vector2 v1 = new Vector2(10,10,Vector2.FLAG_RECT);
		Vector2 s2 = new Vector2(30,-20,Vector2.FLAG_RECT);
		Vector2 v2 = new Vector2(10,10,Vector2.FLAG_RECT);
		assertFalse(Intersect.linesIntersect(s1,v1,s2,v2));
	}
	@Test
	public void testSameLine(){
		Vector2 s1 = new Vector2(60,60,Vector2.FLAG_RECT);
		Vector2 v1 = new Vector2(10,10,Vector2.FLAG_RECT);
		Vector2 s2 = new Vector2(80,80,Vector2.FLAG_RECT);
		Vector2 v2 = new Vector2(10,10,Vector2.FLAG_RECT);
		assertTrue(Intersect.linesIntersect(s1,v1,s2,v2));
	}
	@Test
	public void testSameLine2(){
		Vector2 s1 = new Vector2(60,60,Vector2.FLAG_RECT);
		Vector2 v1 = new Vector2(10,10,Vector2.FLAG_RECT);
		Vector2 s2 = new Vector2(60,60,Vector2.FLAG_RECT);
		Vector2 v2 = new Vector2(10,10,Vector2.FLAG_RECT);
		assertTrue(Intersect.linesIntersect(s1,v1,s2,v2));
	}
	@Test
	public void testLineSegs(){
		Vector2 s1 = new Vector2(0,0,Vector2.FLAG_RECT);
		Vector2 v1 = new Vector2(100,100,Vector2.FLAG_RECT);
		Vector2 s2 = new Vector2(0,100,Vector2.FLAG_RECT);
		Vector2 v2 = new Vector2(100,-100,Vector2.FLAG_RECT);
		assertTrue(Intersect.lineSegmentsIntersect(s1,v1,s2,v2));
	}
	
}
