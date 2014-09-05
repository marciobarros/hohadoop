package org.hadoop.test;

import org.hadoop.calc.RankingList;
import org.junit.Assert;
import org.junit.Test;

public class TestRankingList
{
	@Test
	public void testSingleElement()
	{
		RankingList<String, String> rankings = new RankingList<String, String>(4);
		rankings.add("a", "b", 1);

		Assert.assertEquals(1, rankings.size());
		
		Assert.assertEquals("a", rankings.getFirstItem(0));
		Assert.assertEquals("b", rankings.getSecondItem(0));
		Assert.assertEquals(1.0, rankings.getDistance(0), 0.001);
	}

	@Test
	public void testTwoElements()
	{
		RankingList<String, String> rankings = new RankingList<String, String>(4);
		rankings.add("a", "b", 1);
		rankings.add("a", "c", 2);
		
		Assert.assertEquals(2, rankings.size());
		
		Assert.assertEquals("a", rankings.getFirstItem(0));
		Assert.assertEquals("b", rankings.getSecondItem(0));
		Assert.assertEquals(1.0, rankings.getDistance(0), 0.001);

		Assert.assertEquals("a", rankings.getFirstItem(1));
		Assert.assertEquals("c", rankings.getSecondItem(1));
		Assert.assertEquals(2.0, rankings.getDistance(1), 0.001);
	}

	@Test
	public void testThreeElements()
	{
		RankingList<String, String> rankings = new RankingList<String, String>(4);
		rankings.add("a", "b", 1);
		rankings.add("a", "d", 3);
		rankings.add("a", "c", 2);
		
		Assert.assertEquals(3, rankings.size());
		
		Assert.assertEquals("a", rankings.getFirstItem(0));
		Assert.assertEquals("b", rankings.getSecondItem(0));
		Assert.assertEquals(1.0, rankings.getDistance(0), 0.001);

		Assert.assertEquals("a", rankings.getFirstItem(1));
		Assert.assertEquals("c", rankings.getSecondItem(1));
		Assert.assertEquals(2.0, rankings.getDistance(1), 0.001);

		Assert.assertEquals("a", rankings.getFirstItem(2));
		Assert.assertEquals("d", rankings.getSecondItem(2));
		Assert.assertEquals(3.0, rankings.getDistance(2), 0.001);
	}

	@Test
	public void testFourElements()
	{
		RankingList<String, String> rankings = new RankingList<String, String>(4);
		rankings.add("a", "b", 1);
		rankings.add("a", "d", 3);
		rankings.add("a", "c", 2);
		rankings.add("a", "e", 4);
		
		Assert.assertEquals(4, rankings.size());
		
		Assert.assertEquals("a", rankings.getFirstItem(0));
		Assert.assertEquals("b", rankings.getSecondItem(0));
		Assert.assertEquals(1.0, rankings.getDistance(0), 0.001);

		Assert.assertEquals("a", rankings.getFirstItem(1));
		Assert.assertEquals("c", rankings.getSecondItem(1));
		Assert.assertEquals(2.0, rankings.getDistance(1), 0.001);

		Assert.assertEquals("a", rankings.getFirstItem(2));
		Assert.assertEquals("d", rankings.getSecondItem(2));
		Assert.assertEquals(3.0, rankings.getDistance(2), 0.001);

		Assert.assertEquals("a", rankings.getFirstItem(3));
		Assert.assertEquals("e", rankings.getSecondItem(3));
		Assert.assertEquals(4.0, rankings.getDistance(3), 0.001);
	}

	@Test
	public void testFiveElements()
	{
		RankingList<String, String> rankings = new RankingList<String, String>(4);
		rankings.add("a", "b", 1);
		rankings.add("a", "d", 3);
		rankings.add("a", "c", 2);
		rankings.add("a", "e", 4);
		rankings.add("a", "f", 5);
		
		Assert.assertEquals(4, rankings.size());
		
		Assert.assertEquals("a", rankings.getFirstItem(0));
		Assert.assertEquals("b", rankings.getSecondItem(0));
		Assert.assertEquals(1.0, rankings.getDistance(0), 0.001);

		Assert.assertEquals("a", rankings.getFirstItem(1));
		Assert.assertEquals("c", rankings.getSecondItem(1));
		Assert.assertEquals(2.0, rankings.getDistance(1), 0.001);

		Assert.assertEquals("a", rankings.getFirstItem(2));
		Assert.assertEquals("d", rankings.getSecondItem(2));
		Assert.assertEquals(3.0, rankings.getDistance(2), 0.001);

		Assert.assertEquals("a", rankings.getFirstItem(3));
		Assert.assertEquals("e", rankings.getSecondItem(3));
		Assert.assertEquals(4.0, rankings.getDistance(3), 0.001);
	}

	@Test
	public void testFiveElementsInversed()
	{
		RankingList<String, String> rankings = new RankingList<String, String>(4);
		rankings.add("a", "f", 5);
		rankings.add("a", "e", 4);
		rankings.add("a", "d", 3);
		rankings.add("a", "c", 2);
		rankings.add("a", "b", 1);
		
		Assert.assertEquals(4, rankings.size());
		
		Assert.assertEquals("a", rankings.getFirstItem(0));
		Assert.assertEquals("b", rankings.getSecondItem(0));
		Assert.assertEquals(1.0, rankings.getDistance(0), 0.001);

		Assert.assertEquals("a", rankings.getFirstItem(1));
		Assert.assertEquals("c", rankings.getSecondItem(1));
		Assert.assertEquals(2.0, rankings.getDistance(1), 0.001);

		Assert.assertEquals("a", rankings.getFirstItem(2));
		Assert.assertEquals("d", rankings.getSecondItem(2));
		Assert.assertEquals(3.0, rankings.getDistance(2), 0.001);

		Assert.assertEquals("a", rankings.getFirstItem(3));
		Assert.assertEquals("e", rankings.getSecondItem(3));
		Assert.assertEquals(4.0, rankings.getDistance(3), 0.001);
	}

	@Test
	public void testFiveElementsBetweenExtremes()
	{
		RankingList<String, String> rankings = new RankingList<String, String>(4);
		rankings.add("a", "f", 5);
		rankings.add("a", "b", 1);
		rankings.add("a", "e", 4);
		rankings.add("a", "c", 2);
		rankings.add("a", "d", 3);
		
		Assert.assertEquals(4, rankings.size());
		
		Assert.assertEquals("a", rankings.getFirstItem(0));
		Assert.assertEquals("b", rankings.getSecondItem(0));
		Assert.assertEquals(1.0, rankings.getDistance(0), 0.001);

		Assert.assertEquals("a", rankings.getFirstItem(1));
		Assert.assertEquals("c", rankings.getSecondItem(1));
		Assert.assertEquals(2.0, rankings.getDistance(1), 0.001);

		Assert.assertEquals("a", rankings.getFirstItem(2));
		Assert.assertEquals("d", rankings.getSecondItem(2));
		Assert.assertEquals(3.0, rankings.getDistance(2), 0.001);

		Assert.assertEquals("a", rankings.getFirstItem(3));
		Assert.assertEquals("e", rankings.getSecondItem(3));
		Assert.assertEquals(4.0, rankings.getDistance(3), 0.001);
	}
}