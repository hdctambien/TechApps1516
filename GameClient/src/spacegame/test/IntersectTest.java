package spacegame.test;

import static org.junit.Assert.*;
import spacegame.map.*;

import org.junit.Test;

import spacegame.map.collision.CircleCollider;
import spacegame.map.collision.Intersect;

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
	@Test
	public void testLineSegs2(){
		Vector2 s1 = new Vector2(0,0,Vector2.FLAG_RECT);
		Vector2 v1 = new Vector2(100,100,Vector2.FLAG_RECT);
		Vector2 s2 = new Vector2(0,100,Vector2.FLAG_RECT);
		Vector2 v2 = new Vector2(-100,-100,Vector2.FLAG_RECT);
		assertFalse(Intersect.lineSegmentsIntersect(s1,v1,s2,v2));
	}
	@Test
	public void testSameLineSegs(){
		Vector2 s1 = new Vector2(0,0,Vector2.FLAG_RECT);
		Vector2 v1 = new Vector2(100,100,Vector2.FLAG_RECT);
		Vector2 s2 = new Vector2(0,0,Vector2.FLAG_RECT);
		Vector2 v2 = new Vector2(100,100,Vector2.FLAG_RECT);
		assertTrue(Intersect.lineSegmentsIntersect(s1,v1,s2,v2));
	}
	@Test
	public void testOverlapLineSegs(){
		Vector2 s1 = new Vector2(0,0,Vector2.FLAG_RECT);
		Vector2 v1 = new Vector2(100,100,Vector2.FLAG_RECT);
		Vector2 s2 = new Vector2(50,50,Vector2.FLAG_RECT);
		Vector2 v2 = new Vector2(100,100,Vector2.FLAG_RECT);
		assertTrue(Intersect.lineSegmentsIntersect(s1,v1,s2,v2));
	}
	@Test
	public void testParallelLineSegs(){
		Vector2 s1 = new Vector2(0,0,Vector2.FLAG_RECT);
		Vector2 v1 = new Vector2(100,100,Vector2.FLAG_RECT);
		Vector2 s2 = new Vector2(200,0,Vector2.FLAG_RECT);
		Vector2 v2 = new Vector2(100,100,Vector2.FLAG_RECT);
		assertFalse(Intersect.lineSegmentsIntersect(s1,v1,s2,v2));
	}
	@Test
	public void testParallel2LineSegs(){
		Vector2 s1 = new Vector2(0,0,Vector2.FLAG_RECT);
		Vector2 v1 = new Vector2(100,100,Vector2.FLAG_RECT);
		Vector2 s2 = new Vector2(200,200,Vector2.FLAG_RECT);
		Vector2 v2 = new Vector2(100,100,Vector2.FLAG_RECT);
		assertFalse(Intersect.lineSegmentsIntersect(s1,v1,s2,v2));
	}
	@Test
	public void testLineCircle(){
		Vector2 s = new Vector2(-10,-20,Vector2.FLAG_RECT);
		Vector2 v = new Vector2(100,200,Vector2.FLAG_RECT);
		Vector2 c = new Vector2(10,10,Vector2.FLAG_RECT);
		double r = 10;
		assertTrue(Intersect.lineCircleIntersect(s,v,c,r));
	}
	@Test
	public void testLineCircleInfinite(){
		Vector2 s = new Vector2(-10,-20,Vector2.FLAG_RECT);
		Vector2 v = new Vector2(10,20,Vector2.FLAG_RECT);
		Vector2 c = new Vector2(10,10,Vector2.FLAG_RECT);
		double r = 10;
		assertTrue(Intersect.lineCircleIntersect(s,v,c,r,true));
	}
	@Test
	public void testLineCircle2(){
		Vector2 s = new Vector2(-20,-20,Vector2.FLAG_RECT);
		Vector2 v = new Vector2(-100,-200,Vector2.FLAG_RECT);
		Vector2 c = new Vector2(10,10,Vector2.FLAG_RECT);
		double r = 10;
		assertFalse(Intersect.lineCircleIntersect(s,v,c,r));
	}
	@Test
	public void testLineCircleInfinite2(){
		Vector2 s = new Vector2(-10,-20,Vector2.FLAG_RECT);
		Vector2 v = new Vector2(0,10,Vector2.FLAG_RECT);
		Vector2 c = new Vector2(10,10,Vector2.FLAG_RECT);
		double r = 10;
		assertFalse(Intersect.lineCircleIntersect(s,v,c,r,true));
	}
	
	@Test
	public void testCircleComponentIntersection(){
		EntityFactory factory = new EntityFactory();
		Entity a1 = factory.createAsteroid(0,0);
		Entity a2 = factory.createAsteroid(100,0);
		CircleCollider c1 = new CircleCollider(a1);
		c1.setRadius(60);
		CircleCollider c2 = new CircleCollider(a2);
		c2.setRadius(50);
		assertTrue(c1.collision(c2));
	}
	@Test
	public void testCircleComponentIntersection2(){
		EntityFactory factory = new EntityFactory();
		Entity a1 = factory.createAsteroid(0,0);
		Entity a2 = factory.createAsteroid(100,0);
		CircleCollider c1 = new CircleCollider(a1);
		c1.setRadius(50);
		CircleCollider c2 = new CircleCollider(a2);
		c2.setRadius(50);
		assertTrue(c1.collision(c2));
	}
	@Test
	public void testCircleComponentNoIntersection(){
		EntityFactory factory = new EntityFactory();
		Entity a1 = factory.createAsteroid(0,0);
		Entity a2 = factory.createAsteroid(100,0);
		CircleCollider c1 = new CircleCollider(a1);
		c1.setRadius(49);
		CircleCollider c2 = new CircleCollider(a2);
		c2.setRadius(49);
		assertFalse(c1.collision(c2));
	}
	
}
