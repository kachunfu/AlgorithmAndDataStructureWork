package com.ads.redBlackTreeInsertion;

import java.util.Scanner;
import java.util.Stack;

public class App 
{
	public static void main(String[] args) {
		
		EasyScanner s = new EasyScanner();
		RedBlackTree tree = new RedBlackTree();
		
		System.out.println("Enter the key of nodes separated by space : ");
		String number = s.nextLine();
		String [] strA = number.split(" ");
		for(int i = 0; i < strA.length; i++)
		{
			int key = parseInput(strA[i]);
			tree.rbInsert(new Node(key));
		}
		System.out.println("--------------------------------");
		RedBlackTree.print2D(tree.root);
		System.out.println("--------------------------------");
		
		char choice;
		
		System.out.println("-----------------------------------------------");
		System.out.println("Please select the operation by number:");
		System.out.println("1. Inorder tree walk");
		System.out.println("2. RbInsert");
		System.out.println("3. TreeMinimum");
		System.out.println("4. TreeMaximum");
		System.out.println("5. Successor");
		System.out.println("6. Predecessor");
		System.out.println("7. Reverse inorder tree walk");
		System.out.println("8. Print tree structure");
		System.out.println("9. Exit");
		
		do 
		{
			choice = s.nextLine().charAt(0);
			
			switch(choice)
			{
				case '1' : System.out.println("Inorder tree walk :"); tree.inorderTreeWalk(tree.root);break;
				case '2' : 
							{ 
								System.out.println("Enter value to insert : ");
								String value = s.nextLine();
								int n = parseInput(value);
								tree.rbInsert(new Node(n));
								break;
							}
				case '3' : System.out.println("Tree minimum: " + tree.treeMinimum(tree.root).key);break;
				case '4' : System.out.println("Tree maximum: " + tree.treeMaximum(tree.root).key);break;
				case '5' :  
							{
								System.out.println("To return the successor , please enter value : ");
								String value = s.nextLine();
								int n = parseInput(value);
								
								Node node = tree.treeSearch(tree.root, n);
								if(node == RedBlackTree.nil)
									System.out.println("Node entered not found: " + n);
								else 
									{
										Node successor = tree.treeSuccessor(node);
										if(successor != RedBlackTree.nil)
											System.out.println("The successor of " + n + ": " + tree.treeSuccessor(node).key);
										else
											System.out.println("no successor for tree maximum : " + node.key);
									}
								break;
							}
				case '6' : 
							{
								System.out.println("To return the predecessor , please enter value : ");
								String value = s.nextLine();
								int n = parseInput(value);
					
								Node node = tree.treeSearchIterative(tree.root, n);
								if(node == RedBlackTree.nil)
									System.out.println("Node entered not found: " + n);
								else 
									{
									Node predecessor = tree.treePredecessor(node);
									if(predecessor!= RedBlackTree.nil)
										System.out.println("The predecessor of " + n + ": " + tree.treePredecessor(node).key);
									else
										System.out.println("no predecessor for tree minimum : " + node.key);
									}
								break;
							}
				case '7' : System.out.println("Reverse inorder tree walk :");tree.reverseInorderTreeWalk(tree.root);break;
				case '8' : System.out.println("Printing tree structure : ");RedBlackTree.print2D(tree.root);break;
				case '9' : break;
				default :System.out.println("Please enter number between 1 and 9");
							
			}
		}
		while(choice != '9');
		
	}
	
	public static int parseInput(String input)
	{
		try
		{
			return Integer.parseInt(input);
		}
		catch(NumberFormatException e)
		{
			throw new IllegalArgumentException("Input must be a valid integer.");
		}
	}

}

class Node
{
	int key;
	Node left;
	Node right;
	Node parent;
	int color;
	
	public Node(int key) {
		this.key = key;
	}
}

class RedBlackTree
{
	Node root;
	static Node nil;
	
	static final int BLACK = 1;
	static final int RED = 0;
	
	static {
        nil = new Node(Integer.MAX_VALUE);
        nil.color = BLACK; // NIL nodes are always black
        nil.left = nil;
        nil.right = nil;
    }
	
	
	
	public RedBlackTree() {
		this.root = nil;
	}

	void leftRotate(Node x)
	{
		Node y = x.right;
		x.right = y.left;
		
		if(y.left!= nil)
			y.left.parent = x;
		
		y.parent = x.parent;
		
		if(x.parent == nil)
			root = y;
		else if(x == x.parent.left)
			x.parent.left = y;
		else x.parent.right = y;
		
		y.left = x;
		x.parent = y;
	}
	
	void rightRotate(Node x)
	{
		Node y = x.left;
		x.left = y.right;
		
		if(y.right != nil)
			y.right.parent = x;
		
		y.parent = x.parent;
		
		if(x.parent ==nil)
			root = y;
		else if(x == x.parent.left)
			x.parent.left = y;
		else x.parent.right = y;
		
		y.right = x;
		x.parent = y;
	}
	
	//Insert
	void rbInsert(Node z)
	{
		Node y = nil;
		Node x = root;
		while(x != nil)
		{
			y = x;
			if(z.key < x.key)
				x = x.left;
			else 
				x = x.right;
		}
	    // Debugging statements
//	    System.out.println("Inserting node with key: " + z.key);
//	    System.out.println("Found position, y.key: " + (y == null ? "null" : y.key));
	    
		z.parent = y;
		if(y == nil)
			root = z;
		else if(z.key < y.key)
			y.left = z;
		else
			y.right =z;
		
		z.left = nil;
		z.right = nil;
		z.color = RED;
		rbInsertFixup(z);
	}
	
	void rbInsertFixup(Node z)
	{
		while(z.parent!= nil && z.parent.color == RED)
		{
			if(z.parent == z.parent.parent.left)
			{
				Node y = z.parent.parent.right;
				if(y.color == RED)
				{
					z.parent.color = BLACK;
					y.color = BLACK;
					z.parent.parent.color = RED;
					z = z.parent.parent;
				}
				else
				{
					if(z == z.parent.right)
					{
						z = z.parent;
						leftRotate(z);
					}
					z.parent.color = BLACK;
					z.parent.parent.color = RED;
					rightRotate(z.parent.parent);
				}
			}
			else
			{
				Node y = z.parent.parent.left;
				if(y.color == RED)
				{
					z.parent.color = BLACK;
					y.color = BLACK;
					z.parent.parent.color = RED;
					z = z.parent.parent;
				}
				else
				{
					if(z == z.parent.left)
					{
						z = z.parent;
						rightRotate(z);
					}
					z.parent.color = BLACK;
					z.parent.parent.color = RED;
					leftRotate(z.parent.parent);
				}
			}
		}
		root.color = BLACK;
	}
	
	void inorderTreeWalk(Node x)
	{
		if(x!= nil)
		{
			inorderTreeWalk(x.left);
			System.out.println(x.key);
			inorderTreeWalk(x.right);
		}
	}
	
	void reverseInorderTreeWalk(Node x)
	{
		if(x!= nil)
		{
			reverseInorderTreeWalk(x.right);
			System.out.println(x.key);
			reverseInorderTreeWalk(x.left);
		}
	}
	
	void inorderTreeWalkIterative(Node x)
	{
		Stack<Node> stack = new Stack<>();
		Node current = x;
		
		while(current != nil || !stack.isEmpty())
		{
			while(current != nil)
			{
				stack.push(current);
				current = current.left;
			}
			current = stack.pop();
			System.out.println(current.key);
			current = current.right;
		}
	}
	
	Node treeSearch(Node x,int k)
	{
		if(x == nil || x.key == k)
			return x;
		if(k < x.key)
			return treeSearch(x.left,k);
		else return treeSearch(x.right,k);
	}
	
	Node treeSearchIterative(Node x, int k)
	{
		while(x != nil && k != x.key)
		{
			if(k < x.key)
				x = x.left;
			else x = x.right;
		}
		return x;
	}
	
	Node treeSearchWithMissingKey(Node x, int k)
	{
		if(x == nil)
			return nil;
		
		if(x.key == k)
			return x;
		else if(k < x.key)
		{
			if( x.left != nil)
				return treeSearchWithMissingKey(x.left,k);
			return x;
		}
		else 
		{
			if(x.right!=nil)
				return treeSearchWithMissingKey(x.right,k);
			return x;
		}
	}
	
	Node treeSearchWithMissingKeyIterative(Node x, int k)
	{
		Node parent = nil;
		while(x != nil)
		{
			parent = x;
			if(k == x.key)
				return x;
			else if(k < x.key)
			{
				if(x.left!=nil)
					x = x.left;
				else return parent;
			}
			else 
			{
				if(x.right != nil)
					x = x.right;
				else return parent;
			}
		}
		return parent;
	}
	
	Node treeMinimum(Node x)
	{
		while(x.left != nil)
			x = x.left;
		return x;
	}
	
	Node treeMinimumRecursive(Node x)
	{
		if(x.left != nil)
			return treeMinimumRecursive(x.left);
		else return x;
	}
	
	Node treeMaximum(Node x)
	{
		while(x.right != nil)
			x = x.right;
		return x;
	}
	
	Node treeMaximumRecursive(Node x)
	{
		if(x.right != nil)
			return treeMaximumRecursive(x.right);
		else return x;
	}
	
	Node treeSuccessor(Node x)
	{
		if(x.right != nil)
			return treeMinimum(x.right);
		Node y = x.parent;
		while(y != nil && x == y.right)
		{
			x = y;
			y = y.parent;
		}
		return y;
	}
	
	Node treePredecessor(Node x)
	{
		if(x.left!=nil)
			return treeMaximum(x.left);
		Node y = x.parent;
		while(y != nil && x == y.left)
		{
			x = y;
			y = y.parent;
		}
		return y;
	}
    static void print2DUtil(Node root, int space) {
        // Base case
        if (root == nil)
            return;

        int COUNT = 10;
        
        // Increase distance between levels
        space += COUNT;

        // Process right child first
        print2DUtil(root.right, space);

        // Print current node after space count
        System.out.print("\n");
        for (int i = COUNT; i < space; i++)
            System.out.print(" ");
        String color = (root.color == RED) ? "R" : "B";
        System.out.print(root.key + color + "\n");

        // Process left child
        print2DUtil(root.left, space);
    }

    // Wrapper over print2DUtil()
    static void print2D(Node root) {
        // Pass initial space count as 0
        print2DUtil(root, 0);
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