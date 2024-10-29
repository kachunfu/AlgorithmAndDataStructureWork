package com.ads.countriesfooddietreportBinarySearchTreeQuickSortThreePartitionWhileLoopCountries;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class FoodDietReport 
{
	public static void main(String[] args) {
		
		DietData d = new DietData();
		
		CountryDietData c1 = new CountryDietData("UK");
		CountryDietDataRegistry.addCountryDietData(c1, 1);
		d.addCountryDietData(c1, 1);
		CountryDietData c2 = new CountryDietData("France");
		CountryDietDataRegistry.addCountryDietData(c2, 2);
		d.addCountryDietData(c2, 2);
		CountryDietData c3 = new CountryDietData("Germany");
		CountryDietDataRegistry.addCountryDietData(c3, 3);
		d.addCountryDietData(c3, 3);
		CountryDietData c4 = new CountryDietData("Italy");
		CountryDietDataRegistry.addCountryDietData(c4, 4);
		d.addCountryDietData(c4, 4);
		CountryDietData c5 = new CountryDietData("Spain");
		CountryDietDataRegistry.addCountryDietData(c5, 5);
		d.addCountryDietData(c5, 5);
		
		FoodGroupRegistry.addFoodGroup(new FoodGroup("FRUITS"), 1);
		FoodGroupRegistry.addFoodGroup(new FoodGroup("VEGETABLES"), 2);
		FoodGroupRegistry.addFoodGroup(new FoodGroup("ANIMAL_SOURCED_FOODS"), 3);
		FoodGroupRegistry.addFoodGroup(new FoodGroup("LEGUMES_NUTS_AND_SEEDS"), 4);
		FoodGroupRegistry.addFoodGroup(new FoodGroup("OILS_AND_FATS"), 5);
		FoodGroupRegistry.addFoodGroup(new FoodGroup("STARTCHY_STAPLES"), 6);
		
		
		EasyScanner es = new EasyScanner();
		
		try 
		{
			System.out.println("Enter data for each country...");
			System.out.println("--------------------------------");
			for(int i = 1; i < CountryDietDataRegistry.getMapCountryToId().size()+1; i++)
			{
				for(FoodGroup f : FoodGroupRegistry.getMapFoodGroupToId().keySet())
				{
					System.out.println("Enter cost of " + f.getName() + " in " + d.getCountryById(i).getCountry() + " : ");
					String input = es.nextLine();
					double cost = parseInput(input);
					checkCostValue(cost);
					d.getCountryById(i).setCost(FoodGroupRegistry.getFoodGroupId(f), cost);
				}
			}
		}catch(IllegalArgumentException e)
		{
			System.err.println("Invalid value : " + e.getMessage());
		}
		
		try 
		{
			System.out.println("Enter share data for each country...");
			System.out.println("-----------------------------------------------");

			for(int i = 1; i< CountryDietDataRegistry.getMapCountryToId().size()+1; i++)
			{
				double sum;
				do 
				{
					sum =0;
					for(FoodGroup f: FoodGroupRegistry.getMapIdToFoodGroup().values())
					{
						System.out.println("Enter share of " + f.getName() + " in " + d.getCountryById(i).getCountry() + " in % : ");
						String input = es.nextLine();
						double share = parseInput(input)/100.0;
						checkCostValue(share);
						d.getCountryById(i).setShare(FoodGroupRegistry.getFoodGroupId(f), share);
						sum += share;
					}
					if(Math.abs(sum -1.0) >= 1e-9)
						System.out.println("The sum of the array element is not qual to 1. Please re-enter the values.");
				}
				while(Math.abs(sum -1.0) >= 1e-9);
			}
		}catch(IllegalArgumentException e)
		{
			System.err.println("Invalid value : " + e.getMessage());
		}
		
		char choice;
		
		System.out.println("-----------------------------------------------");
		System.out.println("Please select the operation by number : ");
		System.out.println("1.	Display the total cost of animal-sourced foods from the diet");
		System.out.println("2.	Calculate and display the total cost of a healthy diet for each country and  identify the minimum.");
		System.out.println("3.	Sort the diet data of the five countries by the percentage share of fruits from maximum to minimum ");
		System.out.println("4.	Sort the diet data for Germany by the percentage share of each food group from maximum to minimum");
		System.out.println("5.	Search the country input and find the cost of oils and fats for a healthy diet using Table 1 and also find the % share of the diet");
		System.out.println("6.	Search one of the countries, then find the cost of the food groups and total cost in a least-cost healthy diet");
		System.out.println("7.	Exit");
		System.out.println("-----------------------------------------------");
		
		do
		{
			choice = es.nextLine().charAt(0);
			System.out.println();
			
			switch(choice)
			{
			case '1': d.displayTotalCostOfAnimalSourcedFoods();break;
			case '2': d.displayTotalCostAndFindMinimum();break;
			case '3': d.displaySortedCountriesByFruitShare();break;
			case '4': d.displaySortedFoodGroupByShareForGermany();break;
			case '5': 
					{
						System.out.println("Enter country : ");
						String countryName = es.nextLine();
						d.searchForTheCostOfOilsAndFats(countryName.toUpperCase());
						break;
					}
			case '6': 
					{
						System.out.println("Enter country : ");
						String countryName = es.nextLine();
						d.searchForTheCostOfTheFoodGroupsAndTotalCost(countryName.toUpperCase());
						break;
					}
			case '7': System.out.println("Bye9Bye");break;
			default: System.out.println("Invalid entry. Please choose between 1 and 7.");
			}
		}
		while(choice != '7');

	}
	
	public static double parseInput(String input)
	{
		try
		{
			return Double.parseDouble(input);
		}
		catch(NumberFormatException e)
		{
			throw new IllegalArgumentException("Input must be a valid double.");
		}
	}
	
	public static void checkCostValue(double value)
	{
		if(value <= 0)
			throw new IllegalArgumentException("Cost value must be positive");
	}
	
	public static void checkShareValue(double value)
	{
		if(value > 100)
			throw new IllegalArgumentException("Share value must be smaller than or equal to 100");
	}

	public static class EasyScanner
	{
		public int nextInt()
		{
			Scanner s = new Scanner(System.in);
			int i = s.nextInt();
			return i;
		}
		public double nextDouble()
		{
			Scanner s = new Scanner(System.in);
			double d = s.nextDouble();
			return d;
		}
		public String nextLine()
		{
			Scanner s = new Scanner(System.in);
			String str = s.nextLine();
			return str;
		}
	}
}

class DietData
{
	//List of countries
	private CountryDietData[] countries;
	
	static int heapSize;
	
	public DietData()
	{
		this.countries = new CountryDietData[256];
	}
	
	public CountryDietData[] getCountries() {
		return countries;
	}
	
	public CountryDietData getCountryById(int index)
	{
		return countries[index];
	}
	
	public CountryDietData getCountryDietDataByName(String countryName)
	{
		int i = CountryDietDataRegistry.getCountryDietDataIdByName(countryName);
		if(i!= -1)
			return countries[i];
		return null;
	}
	
	public void addCountryDietData(CountryDietData data, int i)
	{
		CountryDietDataRegistry.addCountryDietData(data, i);
		countries[i] = data;
	}
	//task 3
	public void displayTotalCostOfAnimalSourcedFoods()
	{
		double totalCost = 0.0;
		int numberOfCountries = CountryDietDataRegistry.getMapCountryToId().size();
		for(int i = 1 ; i<= numberOfCountries; i++)
			totalCost += this.countries[i].getCost(FoodGroupRegistry.getFoodGroupId("ANIMAL_SOURCED_FOODS"));
		System.out.println("Total cost of animal-sourced foods of all countries : " + totalCost);
	}
		
	//task 4
	public void displayTotalCostAndFindMinimum()
	{
		CountryDietData minCountry = null;
		double min = Double.MAX_VALUE;
		int numberOfCountries = CountryDietDataRegistry.getMapCountryToId().size();
		for(int i = 1 ; i< 1+numberOfCountries; i++)
		{
			if(this.countries[i].getTotalCost() < min)
			{
				min = this.countries[i].getTotalCost();
				minCountry = this.countries[i];
			}
			System.out.println("The total cost of a healthy diet for " + this.countries[i].getCountry() +
					" : £" + this.countries[i].getTotalCost());
		}
		System.out.println("The minimum cost : £" + min + 
				", the country with the minimum cost : " + minCountry.getCountry());
	}
		
	//task 5 : binary search tree reverse inorder tree walk
	public void displaySortedCountriesByFruitShare()
	{
		ShareOfFruitComparator comparator = new ShareOfFruitComparator();
		
		List<CountryDietData> countryList = Arrays.stream(this.countries)
												.filter(Objects::nonNull)
												.collect(Collectors.toList());
		
		BinarySearchTree tree = new BinarySearchTree();
		
		for(int i=0; i<countryList.size();i++)
		{
			Node n = new Node(countryList.get(i));
			tree.treeInsert(n, comparator);
		}
		System.out.println("Sort countries by the percentage share of fruits : ");
		tree.reverseInorderTreeWalkPrintSharesOfFruitOfCountries(tree.getRoot());
	}
		
	//task 6 : quickSort
	public void displaySortedFoodGroupByShareForGermany()
	{
		int i = CountryDietDataRegistry.getCountryDietDataIdByName("Germany");
		Set<Map.Entry<Integer,Double>> entrySet = countries[i].getShareOfFoodGroups().entrySet();
		
		List<Map.Entry<Integer, Double>> foodGroupSharePair = new ArrayList<>(entrySet);
			
		quickSort(foodGroupSharePair);
		
		foodGroupSharePair.forEach(e-> System.out.println(FoodGroupRegistry.getFoodGroup(e.getKey()).getName()
									+ " with cost, £" + countries[CountryDietDataRegistry.getCountryDietDataIdByName("Germany")].getCost(e.getKey())
									+ " with share, " + e.getValue()*100 + "%"));
	}
		
	//task 7 : Search the country input and find the cost of oils and fats for a healthy diet 
	public void searchForTheCostOfOilsAndFats(String countryName)
	{
		CountryDietData c = getCountryDietDataByName(countryName);
		if(c == null)
			System.out.println("Country, " + countryName + " not found");
		else
		{
			System.out.println("The cost of oils and Fats in " + c.getCountry() + ": £" + c.getCost("OILS_AND_FATS"));
			System.out.println("The share of oils and Fats " + c.getCountry() +": " + c.getShare("OILS_AND_FATS")*100 + "%");
		}
	}
		
	//task 8 :	Search one of the countries, then find the cost of the food groups and total cost in a least-cost healthy diet
	public void searchForTheCostOfTheFoodGroupsAndTotalCost(String countryName)
	{
		CountryDietData c = getCountryDietDataByName(countryName);
		if(c==null)
			System.out.println("Country, " + countryName + " not found");
		else
		{
			double total = 0.0;
			System.out.println(c.getCountry() + ":");
			for(Map.Entry<Integer,FoodGroup> entry : FoodGroupRegistry.getMapIdToFoodGroup().entrySet())
			{
				double cost = c.getCost(entry.getKey())*c.getShare(entry.getKey());
				total += cost;
				System.out.println("Least cost healthy diet-" + entry.getValue().getName() + ": £" + c.getCost(entry.getKey()) 
				+ "*" + c.getShare(entry.getKey())*100 + "/100 = £" + formatToTwoDecimalPlaces(cost));
			}
			System.out.println("Total : £" + formatToTwoDecimalPlaces(total));
		}
	}
	
	private void quickSort(List<Map.Entry<Integer, Double>> input)
	{
		quickSort(input,0,input.size()-1);
	}
		
	private void quickSort(List<Map.Entry<Integer,Double>> input, int left ,int right)
	{
		if(left<right)
		{
			int[] equalIndexPair = partition(input, left, right);
			quickSort(input, left, equalIndexPair[0]);
			quickSort(input, equalIndexPair[1]+1, right);
		}
			
	}
		
	private int[] partition(List<Map.Entry<Integer, Double>> input, int left, int right)
	{
		int[] equalIndexPair = new int[2];
		int pivotIndex = chooseRandomPivotIndex(left,right);
		swap(input, right, pivotIndex);
		double pivot = input.get(right).getValue();
		
		int i = left - 1;    // Tracks elements less than pivot
	    int j = left;        // Iterates through the list
	    int k = right;       // Tracks elements greater than pivot
	    
	    while (j < k) 
	    {
	        if (input.get(j).getValue() > pivot) 
	        {
	            i++;
	            swap(input, i, j);
	            j++;
	        } 
	        else if (input.get(j).getValue() < pivot) 
	        {
	            k--;
	            swap(input, j, k);
	        } 
	        else 
	            j++;
	    }
	    
	    // Move the pivot to its correct place
	    swap(input, k, right);
	    
	    equalIndexPair[0] = i;
	    equalIndexPair[1] = k;
	    return equalIndexPair;
	}
		
	private <T> void swap(List<T> input, int index1, int index2)
	{
		T temp = input.get(index1);
		input.set(index1, input.get(index2));
		input.set(index2, temp);
		
	}
		
	private int chooseRandomPivotIndex(int left , int right)
	{
		int pivotIndex = new Random().nextInt(right - left) + left;
		return pivotIndex;
	}
		
	private static String formatToTwoDecimalPlaces(double number)
	{
		DecimalFormat df = new DecimalFormat("0.0000");
		return df.format(number);
	}
}

class CountryDietData
{
	private String country;
	private Map<Integer, Double> costOfFoodGroups;
	private Map<Integer, Double> shareOfFoodGroups;
		
	public CountryDietData(String country)
	{
		this.country = country.toUpperCase();
		this.costOfFoodGroups = new HashMap<>();
		this.shareOfFoodGroups = new HashMap<>();
	}
		
	public String getCountry()
	{
		return this.country;
	}
		
	public Map<Integer, Double> getCostOfFoodGroups() {
		return costOfFoodGroups;
	}

	public Map<Integer, Double> getShareOfFoodGroups() {
		return shareOfFoodGroups;
	}

	
	public void setCost(String foodGroupName, double cost)
	{
		setValue(costOfFoodGroups,foodGroupName,cost);
	}
	
	public void setCost(int i, double cost)
	{
		setValue(costOfFoodGroups,i,cost);
	}
		
	public double getCost(int i)
	{
		return getValue(costOfFoodGroups, i);
	}
	
	public double getCost(String foodGroupName)
	{
		return getValue(costOfFoodGroups,foodGroupName);
	}
	
	public void setShare(int i, double share)
	{
		setValue(shareOfFoodGroups,i,share);
	}
	
	public void setShare(String foodGroupName, double share)
	{
		setValue(shareOfFoodGroups,foodGroupName,share);
	}
		
	public double getShare(int i)
	{
		return getValue(shareOfFoodGroups, i);
	}
	public double getShare(String foodGroupName)
	{
		return getValue(shareOfFoodGroups, foodGroupName);
	}
		
	public double getTotalCost()
	{
		double totalCost = 0.0;
		for(Integer i : costOfFoodGroups.keySet())
			totalCost += costOfFoodGroups.get(i);
		return totalCost;
	}
	
	private int getFoodGroupId(String foodGroupName)
	{
		return FoodGroupRegistry.getFoodGroupId(foodGroupName);
	}
	private double getValue(Map<Integer,Double> map, int i)
	{
		return map.getOrDefault(i, -999.9);
	}
	
	private double getValue(Map<Integer,Double> map, String foodGroupName)
	{
		int i = FoodGroupRegistry.getFoodGroupId(foodGroupName);
		return map.getOrDefault(i, -999.9);
	}
	
	private void setValue(Map<Integer,Double> map, int i, double value)
	{
		if(i!=-1)
			map.put(i, value);
	}
	
	private void setValue(Map<Integer,Double> map, String foodGroupName, double value)
	{
		int i = getFoodGroupId(foodGroupName);
		if(i!=-1)
			map.put(i, value);
		else System.out.println("Food group not food: "+ foodGroupName);
	}
	
	

	@Override
	public int hashCode() {
		return Objects.hash(country);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CountryDietData other = (CountryDietData) obj;
		return Objects.equals(country, other.country);
	}
}

class BinarySearchTree
{
	private Node root;
	
	public BinarySearchTree() {
		this.root = null;
	}

	public BinarySearchTree(Node root) {
		this.root = root;
		this.root.setParent(null);
	}
	
	public Node getRoot() {
		return root;
	}

	public void treeInsert(Node z, Comparator<CountryDietData> comparator)
	{
		Node y = null;
		Node x = root;
		while(x != null)
		{
			y = x;
			if(comparator.compare(z.getContryDietData(), x.getContryDietData())<0)
				x = x.getLeft();
			else 
				x = x.getRight();
		}
		z.setParent(y);
		if(y==null)
			root = z;
		else if(comparator.compare(z.getContryDietData(), y.getContryDietData())<0)
			y.setLeft(z);
		else
			y.setRight(z);
	}
	
	public void reverseInorderTreeWalkPrintSharesOfFruitOfCountries(Node x)
	{
		if(x!=null)
		{
			reverseInorderTreeWalkPrintSharesOfFruitOfCountries(x.getRight());
			System.out.println(x.getContryDietData().getCountry() + ": " + x.getContryDietData().getShare("FRUITS")*100+"%");
			reverseInorderTreeWalkPrintSharesOfFruitOfCountries(x.getLeft());
		}
	}
	
}
class ShareOfFruitComparator implements Comparator<CountryDietData>
{

	@Override
	public int compare(CountryDietData o1, CountryDietData o2) {
		return Double.compare(o1.getShare("FRUITS"), o2.getShare("FRUITS"));
	}
	
}

class Node
{
	private CountryDietData contryDietData;
	
	private Node left;
	
	private Node right;
	
	private Node parent;

	public Node(CountryDietData contryDietData) 
	{
		this.contryDietData = contryDietData;
	}

	public CountryDietData getContryDietData() {
		return contryDietData;
	}

	public Node getLeft() {
		return left;
	}

	public void setLeft(Node left) {
		this.left = left;
	}

	public Node getRight() {
		return right;
	}

	public void setRight(Node right) {
		this.right = right;
	}

	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}
}

class CountryDietDataRegistry
{
	private static Map<CountryDietData, Integer> mapCountryToId = new HashMap<>();
	
	public static Map<CountryDietData, Integer> getMapCountryToId() {
		return mapCountryToId;
	}

	public static void addCountryDietData(CountryDietData countryDietData, int index)
	{
		if(!mapCountryToId.containsKey(countryDietData))
			mapCountryToId.put(countryDietData, index);
	}
		
	public static int getCountryDietDataId(CountryDietData countryDietData)
	{
		if(mapCountryToId.containsKey(countryDietData))
			return mapCountryToId.get(countryDietData);
		else return -1;
	}
		
	public static int getCountryDietDataIdByName(String name)
	{
		CountryDietData c = new CountryDietData(name.toUpperCase());
		if(mapCountryToId.containsKey(c))
			return mapCountryToId.get(c);
		else return -1;
	}
		
	public static boolean containsCountry(String countryName)
	{
		CountryDietData c = new CountryDietData(countryName);
		return mapCountryToId.containsKey(c);
	}
}

class FoodGroupRegistry
{
	private static Map<Integer,FoodGroup> mapIdToFoodGroup = new HashMap<>();
	private static Map<FoodGroup, Integer> mapFoodGroupToId = new HashMap<>();
		
	public static Map<Integer,FoodGroup>  getMapIdToFoodGroup() {
		return mapIdToFoodGroup;
	}
	
	public static Map<FoodGroup,Integer> getMapFoodGroupToId(){
		return mapFoodGroupToId;
	}
	
	public static FoodGroup getFoodGroup(int i)
	{
		return mapIdToFoodGroup.getOrDefault(i, null);
	}
	
	public static int getFoodGroupId(FoodGroup foodGroup)
	{
		return mapFoodGroupToId.getOrDefault(foodGroup, -1);
	}
	
	public static int getFoodGroupId(String name)
	{
		FoodGroup f = new FoodGroup(name.toUpperCase());
		return mapFoodGroupToId.getOrDefault(f, -1);
	}
		
	public static void addFoodGroup(FoodGroup foodGroup, int index)
	{
		if(!mapIdToFoodGroup.containsKey(index) && !mapFoodGroupToId.containsKey(foodGroup))
		{
			mapIdToFoodGroup.put(index, foodGroup);
			mapFoodGroupToId .put(foodGroup,index);
		}
	}
}

class FoodGroup
{
	private String name;
		
	public FoodGroup(String name)
	{
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FoodGroup other = (FoodGroup) obj;
		return Objects.equals(name, other.name);
	}

	@Override
	public String toString() {
		return "FoodGroup [name=" + name + "]";
	}
}
