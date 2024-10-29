package com.ads.hashTableChainingSinglyLinkedList;

import java.util.Scanner;


public class HashTable 
{
	/**
	 * The ASCII code of ’w’ is 119, for ’o’ it is 111, for ’r’ it is 114, for ’l’ it is 108, and for ’d’
		it is 100. Thus, ℎ(“world") = (119 + 111 × 263 + 114 × 2632 + 108 × 2633 + 100 × 2634 mod 1 000 000 007) mod 5 = 4.
	 *  where 5 is the bucket size 
	 * 
	 * 
	 *  world: 4; HellO: 4; World: 2; luck: 2; GooD: 2; good : 3; 
	 * 	5
		12
		add world
		add HellO
		check 4
		find World
		find world
		del world
		check 4
		del HellO
		add luck
		add GooD
		check 2
		del good
		
		Ans: 
		HellO world
		no
		yes
		HellO
		GooD luck
		------------------------------
		test : 0;  Test: 0;
		4
		8
		add test
		add test
		find test
		del test
		find test
		find Test
		add Test	
		find Test
		
		Ans:
		yes
		no
		no
		yes
		------------------------------
		help: 1; del: 2; add: 1 
		3
		12
		check 0
		find help
		add help
		add del
		add add
		find add
		find del
		del del
		find del
		check 0
		check 1
		check 2
		
		Ans:
		
		no
		yes
		yes
		no
		
		add help
		
	 **/
	
	public static long x = 263;
	public static long p = 1000000007;
	public static LinkedList[] hashTable;
	public static int size;
	
	public static void main(String[] args) 
	{
		EasyScanner es = new EasyScanner();
		System.out.println("Enter number of bucket  : ");
		size = es.nextInt();
		hashTable = new LinkedList[size];
		
		for(int i =0; i < size; i++)
		{
			hashTable[i] = new LinkedList();
		}
		System.out.println("Enter number of queries : ");
		int queries = es.nextInt();
		
		String[]  queriesA = new String[queries];
		
		for(int i = 0; i < queries ; i++)
		{
			System.out.println("Enter query by separating entity by space : ");
			String query = es.nextLine();
			queriesA[i] = query;
		}
		
		for(int i = 0; i < queries; i++)
			hashingWithChain(queriesA[i]);
	}
	
	public static void hashingWithChain(String s)
	{
		String[] strA = s.split(" ");
		char option = strA[0].charAt(0);
		switch(option)
		{
			case 'a' : 
			{
				String str = strA[1];
				add(str);
				break;
			}
			
			case 'c' :
			{
				int i = Integer.parseInt(strA[1]);
				check(i);
				break;
			}
			
			case 'f':
			{
				String str = strA[1];
				System.out.println(find(str));
				break;
			}
			case 'd':
			{
				String str = strA[1];
				delete(str);
				break;
			}
		}
		
	}
	
	//CHAINED-HASH-INSERT
	public static void add(String s)
	{
		int index = polyHash(s,size);
		if(hashTable[index].find(s) == null)
			hashTable[index].pushFront(s);
		else return;
	}
	
	//CHAINED-HASH-SEARCH
	public static String find(String s)
	{
		int index = polyHash(s,size);
		if(hashTable[index].find(s) == null)
			return "No";
		else
			return "Yes";
	}
	
	//CHAINED-HASH-DELETE
	public static void delete(String s)
	{
		int index = polyHash(s,size);
		Node node = hashTable[index].find(s);
		
		if(node == null)
			return;
		else 
			hashTable[index].listDelete(node);
	}
	
	public static void check(int i)
	{
		if(hashTable[i].isEmpty())
			System.out.println();
		else
			hashTable[i].print();
	}
	
	public static int polyHash(String s, int m)
	{
		long hash = 0;
		for(int i = s.length()-1; i >= 0; i--)
		{
			//!!!!!!!important polyHash
			hash = ((hash * x + (long)s.charAt(i) )%p + p)%p;
			
		}
		return (int) hash%m;
	}

}
//Singly
class LinkedList
{
	Node head;
	Node tail;
	
	public LinkedList() {
	}

	void pushFront(String key)
	{
		Node node = new Node(key);
		node.next = head;
		head = node;
		if(tail == null)
			tail = head;
	}
	
	Node find(String key)
	{
		Node node = head;
		while(node != null && !node.s.equals(key))
			node = node.next;
		return node;
	}
	
	void listDelete(Node x)
	{
		if(head == null)
			return;
		if(head == x)
		{
			head = x.next;
			return;
		}
		Node e = head;
		while(e != null && e.next != x)
			e = e.next;
		if(e == null)
			return;
		if(e.next == x)
			e.next = x.next;
	}
	
	void print()
	{
		Node x = head;
		while(x!=null)
		{
			System.out.print(x.s + " ");
			x = x.next;
		}
		System.out.println();	
	}
	
	boolean isEmpty()
	{
		return head == null ? true:false;
	}
}

class Node
{
	String s;
	Node next;
	
	public Node(String s) {
		this.s = s;
	}
	
	
}

class EasyScanner
{
	public int nextInt()
	{
		Scanner s = new Scanner(System.in);
		int i = s.nextInt();
		return i;
	}
	
	public String nextLine()
	{
		Scanner s = new Scanner(System.in);
		String str = s.nextLine();
		return str;
	}
}