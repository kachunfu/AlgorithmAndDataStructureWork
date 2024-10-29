package com.ads.huffmanCode;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class App {
	
	static int heapSize;
	
	public static void main(String[] args) {
		
//		int n = 6;
//		char[] charA = {'a','b','c','d','e','f'};
//		int[] charF = {5,9,12,13,16,45};
//		heapSize = 6;
		
		int n = 6;
		char[] charA = {'m','t','y','x','j','s'};
		int[] charF = {6,4,3,11,16,12};
		heapSize = 6;
		
		EasyScanner es = new EasyScanner();
		
		
//		System.out.println("Enter the characters separated by space");
//		String strChar = es.nextLine();
//		String[] strCharA = strChar.split(" ");
//		
//		char[] charA = new char[strCharA.length];
//		
//		for(int i = 0; i < strCharA.length;i++)
//			charA[i] = strCharA[i].charAt(0);
//		System.out.println("Enter the frequency of each character separated by space");
//		String strFreq = es.nextLine();
//		String[] strFreqA = strFreq.split(" ");
//		
//		int[] freqA = new int[strFreqA.length];
//		for(int i =0; i < strFreqA.length; i++)
//			freqA[i] = Integer.parseInt(strFreqA[i]);
//		
//		heapSize = charA.length;
//		int n = charA.length;
		
		
		List<HuffmanNode> queue = new ArrayList<>();
		
		HuffmanComparator hC = new HuffmanComparator();
		
		for(int i = 0; i<n; i++)
		{
			HuffmanNode h = new HuffmanNode();
			
			h.c = charA[i];
//			h.freq = freqA[i];
			
			h.freq = charF[i];
			
			h.left = null;
			h.right = null;
			
			queue.add(h);
		}
		
		buildMinHeap(queue,hC);
		
		HuffmanNode root = huffman(queue,hC);
		
		printCode(root,"");
		
	}
	
	public static HuffmanNode huffman(List<HuffmanNode> queue, Comparator<? super HuffmanNode> comparator)
	{
		int size = queue.size();
		
		for(int i = 0; i < size-1; i++)
		{
			HuffmanNode z = new HuffmanNode();
			HuffmanNode x = heapExtractMin(queue, comparator);
			z.left = x;
			HuffmanNode y = heapExtractMin(queue, comparator);
			z.right = y;
			z.freq = x.freq + y.freq;
			minHeapInsert(queue, z.freq, z, comparator);
		}
		//return the root of the tree
		return heapExtractMin(queue, comparator);
		
	}
	
	public static void printCode(HuffmanNode root, String s)
	{
		if(root.left == null && root.right == null && Character.isLetter(root.c))
		{
			System.out.println(root.c + ":" + s);
			return;
		}
			
		printCode(root.left, s+"0");
		printCode(root.right, s+"1");
	}
	
	
	public static void swap(List<HuffmanNode> input, int index1, int index2)
	{
		HuffmanNode temp = input.get(index1);
		input.set(index1, input.get(index2));
		input.set(index2, temp);
	}
	
	public static void minHeapify(List<HuffmanNode> list, int i, Comparator<? super HuffmanNode> comparator)
	{
		int l = left(i);
		int r = right(i);
		int min;
		//heapSize - 1 : because list is 0-based array
		if(l <= heapSize-1 && comparator.compare(list.get(l),list.get(i)) < 0)
			min = l;
		else min = i;
		
		//heapSize - 1 : because list is 0-based array
		if(r <= heapSize-1 && comparator.compare(list.get(r), list.get(min))< 0)
			min = r;
		if(min != i)
		{
			swap(list, i, min);
			minHeapify(list, min, comparator);
		}
	}
	
	
	
	public static void minHeapInsert(List<HuffmanNode> list, int key, HuffmanNode h, Comparator<? super HuffmanNode> comparator)
	{
		heapSize = heapSize + 1;
		list.set(heapSize-1, h);
		list.get(heapSize-1).freq = Integer.MAX_VALUE;
		minHeapDecreaseKey(list,heapSize-1,key, comparator);
	}
	
	public static void minHeapDecreaseKey(List<HuffmanNode> list, int i, int key, Comparator<? super HuffmanNode> comparator)
	{
		if(key > list.get(i).freq)
			throw new IllegalArgumentException("New frequency is larger than current frequency");
		list.get(i).freq = key;
		
		while(i > 0 && list.get(parent(i)).freq > list.get(i).freq)
		{
			swap(list,i,parent(i));
			i = parent(i);
		}
	}
	
	public static void buildMinHeap(List<HuffmanNode> list, Comparator<? super HuffmanNode> comparator)
	{
		heapSize = list.size();
		for(int i = list.size()/2 -1; i>= 0; i--)
			minHeapify(list,i,comparator);
	}
	
	public static HuffmanNode heapExtractMin(List<HuffmanNode> list, Comparator<? super HuffmanNode> comparator)
	{
		if(heapSize < 1)
			System.err.println("heap underflow");
		HuffmanNode min = list.get(0);
		list.set(0, list.get(heapSize-1));
		heapSize--;
		minHeapify(list, 0, comparator);
		return min;
	}
	
	public static int parent(int i)
	{
		return (i-1)/2;
	}
	
	public static int left(int i)
	{
		return 2*i + 1;
	}
	
	public static int right(int i)
	{
		return 2*i + 2;
	}
	

}

class HuffmanNode
{
	int freq;
	char c;
	HuffmanNode left;
	HuffmanNode right;
	
	
}

class HuffmanComparator implements Comparator<HuffmanNode>
{
	@Override
	public int compare(HuffmanNode h1, HuffmanNode h2) {
		return h1.freq - h2.freq;
	}
	
}
class EasyScanner
{
	String nextLine()
	{
		Scanner s = new Scanner(System.in);
		String str = s.nextLine();
		return str;
	}
}