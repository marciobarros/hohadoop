package org.hadoop.test;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.hadoop.readers.DynamoTokenizer;
import org.junit.Test;

/**
 * Casos de teste para o tokenizador dos arquivos exportador pelo DynamoDB
 * 
 * @author marcio.barros
 */
public class TestDynamoTokenizer
{
	@Test
	public void testEmpty() throws Exception
	{
		List<String> tokens = DynamoTokenizer.split("", ',');
		assertEquals(0, tokens.size());
	}

	@Test
	public void testOneComponent() throws Exception
	{
		List<String> tokens = DynamoTokenizer.split("\\\"pt\\\":\\\"Texto em pt\\\"", ',');
		assertEquals(1, tokens.size());
		assertEquals("\\\"pt\\\":\\\"Texto em pt\\\"", tokens.get(0));
	}

	@Test
	public void testTwoComponents() throws Exception
	{
		List<String> tokens = DynamoTokenizer.split("\\\"pt\\\":\\\"Texto em pt\\\",\\\"es\\\":\\\"Texto em es\\\"", ',');
		assertEquals(2, tokens.size());
		assertEquals("\\\"pt\\\":\\\"Texto em pt\\\"", tokens.get(0));
		assertEquals("\\\"es\\\":\\\"Texto em es\\\"", tokens.get(1));
	}

	@Test
	public void testThreeComponents() throws Exception
	{
		List<String> tokens = DynamoTokenizer.split("\\\"pt\\\":\\\"Texto em pt\\\",\\\"es\\\":\\\"Texto em es\\\",\\\"en\\\":\\\"Texto em en\\\"", ',');
		assertEquals(3, tokens.size());
		assertEquals("\\\"pt\\\":\\\"Texto em pt\\\"", tokens.get(0));
		assertEquals("\\\"es\\\":\\\"Texto em es\\\"", tokens.get(1));
		assertEquals("\\\"en\\\":\\\"Texto em en\\\"", tokens.get(2));
	}

	@Test
	public void testManySeparators() throws Exception
	{
		List<String> tokens = DynamoTokenizer.split(",,,,,,", ',');
		assertEquals(0, tokens.size());
	}

	@Test
	public void testQuotes() throws Exception
	{
		List<String> tokens = DynamoTokenizer.split("\\\"pt\\\":\\\"Texto em \"pt\"\\\",\\\"es\\\":\\\"Texto em \"es\"\\\",\\\"en\\\":\\\"Texto em \"en\"\\\"", ',');
		assertEquals(3, tokens.size());
		assertEquals("\\\"pt\\\":\\\"Texto em \"pt\"\\\"", tokens.get(0));
		assertEquals("\\\"es\\\":\\\"Texto em \"es\"\\\"", tokens.get(1));
		assertEquals("\\\"en\\\":\\\"Texto em \"en\"\\\"", tokens.get(2));
	}

	@Test
	public void testSeparator() throws Exception
	{
		List<String> tokens = DynamoTokenizer.split("\\\"pt\\\":\\\"Texto em , pt\\\",\\\"es\\\":\\\"Texto em , es\\\",\\\"en\\\":\\\"Texto em , e,n\\\"", ',');
		assertEquals(3, tokens.size());
		assertEquals("\\\"pt\\\":\\\"Texto em , pt\\\"", tokens.get(0));
		assertEquals("\\\"es\\\":\\\"Texto em , es\\\"", tokens.get(1));
		assertEquals("\\\"en\\\":\\\"Texto em , e,n\\\"", tokens.get(2));
	}

	@Test
	public void testLostGuyAmongstSeparators() throws Exception
	{
		List<String> tokens = DynamoTokenizer.split(",,,I'm Lost,,,", ',');
		assertEquals(1, tokens.size());
		assertEquals("I'm Lost", tokens.get(0));
	}

	@Test
	public void testLostBlankedGuyAmongstSeparators() throws Exception
	{
		List<String> tokens = DynamoTokenizer.split(",,, I'm Lost ,,,", ',');
		assertEquals(1, tokens.size());
		assertEquals(" I'm Lost ", tokens.get(0));
	}

	@Test
	public void testManyBars() throws Exception
	{
		List<String> tokens = DynamoTokenizer.split("\\\\", ',');
		assertEquals(1, tokens.size());
		assertEquals("\\\\", tokens.get(0));
	}

	@Test
	public void testReallySeemsString() throws Exception
	{
		List<String> tokens = DynamoTokenizer.split("\\\\ \"", ',');
		assertEquals(1, tokens.size());
		assertEquals("\\\\ \"", tokens.get(0));
	}
}