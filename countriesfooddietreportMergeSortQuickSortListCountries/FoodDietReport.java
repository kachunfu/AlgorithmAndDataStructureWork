package com.ads.countriesfooddietreportMergeSortQuickSortListCountries;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

public class FoodDietReport 
{
	public static void main(String[] args) {
		
		DietData d = new DietData();
		
		CountryDietData c1 = new CountryDietData("UK");
		CountryDietData c2 = new CountryDietData("FRANCE");
		CountryDietData c3 = new CountryDietData("GERMANY");
		CountryDietData c4 = new CountryDietData("ITALY");
		CountryDietData c5 = new CountryDietData("SPAIN");
		
		CountryDietDataRegistry.addCountryDietData(c1, 0);
		CountryDietDataRegistry.addCountryDietData(c2, 1);
		CountryDietDataRegistry.addCountryDietData(c3, 2);
		CountryDietDataRegistry.addCountryDietData(c4, 3);
		CountryDietDataRegistry.addCountryDietData(c5, 4);
		
		d.addCountryDietData(c1);
		d.addCountryDietData(c2);
		d.addCountryDietData(c3);
		d.addCountryDietData(c4);
		d.addCountryDietData(c5);
		
		EasyScanner es = new EasyScanner();
		
		try 
		{
			System.out.println("Enter data for each country...");
			System.out.println("--------------------------------");
			for(int i = 0; i < d.getCountries().size(); i++)
			{
				for(FoodGroup f : FoodGroupRegistry.getFoodGroups().values())
				{
					System.out.println("Enter cost of " + f.getName() + " in " + d.getCountries().get(i).getCountry() + " : ");
					String input = es.nextLine();
					double cost = parseInput(input);
					checkCostValue(cost);
					d.getCountries().get(i).setCost(f.getName(), cost);
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

			for(int i = 0; i< d.getCountries().size(); i++)
			{
				double sum;
				do 
				{
					sum =0;
					for(FoodGroup f: FoodGroupRegistry.getFoodGroups().values())
					{
						System.out.println("Enter share of " + f.getName() + " in " + d.getCountries().get(i).getCountry() + " in % : ");
						String input = es.nextLine();
						double share = parseInput(input)/100.0;
						checkCostValue(share);
						d.getCountries().get(i).setShare(f.getName(), share);
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
			throw new IllegalArgumentException("Input must be a valid integer.");
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
	private List<CountryDietData> countries;
	
	public DietData()
	{
		this.countries = new ArrayList<>();
	}
	
	public List<CountryDietData> getCountries() {
		return countries;
	}
	
	public CountryDietData getCountryDietDataByName(String countryName)
	{
		for(CountryDietData c: countries)
		{
			if(c.getCountry().equalsIgnoreCase(countryName))
				return c;
		}
		return null;
	}
	
	public void addCountryDietData(CountryDietData data)
	{
		countries.add(data);
	}
	//task 3
	public void displayTotalCostOfAnimalSourcedFoods()
	{
		double totalCost = 0.0;
		for(CountryDietData c: countries)
			totalCost += c.getCost("ANIMAL_SOURCED_FOODS");
		System.out.println("Total cost of animal-sourced foods of all countries : " + totalCost);
	}
		
	//task 4
	public void displayTotalCostAndFindMinimum()
	{
		CountryDietData minCountry = null;
		double min = Double.MAX_VALUE;
		for(CountryDietData c : countries)
		{
			if(c.getTotalCost() < min)
			{
				min = c.getTotalCost();
				minCountry = c;
			}
			System.out.println("The total cost of a healthy diet for " + c.getCountry() +
					" : £" + c.getTotalCost());
		}
		System.out.println("The minimum cost : £" + min + 
				", the country with the minimum cost : " + minCountry.getCountry());
	}
		
	//task 5 : mergeSort
	public void displaySortedCountriesByFruitShare()
	{
		Comparator<CountryDietData> comparator = (c1, c2) ->
		Double.compare(c2.getShare("FRUITS"), c1.getShare("FRUITS"));
			
		this.countries = mergeSort(this.countries, comparator);
		System.out.println("Sort countries by the percentage share of fruits : ");
		for(CountryDietData c : countries)
			System.out.println(c.getCountry() + ": " + c.getShare("FRUITS")*100 + "%");
	}
		
	//task 6 : quickSort
	public void displaySortedFoodGroupByShareForGermany()
	{
		List<Map.Entry<FoodGroup, Double>> foodGroupSharePair = new ArrayList<>(getCountryDietDataByName("Germany")
																	.getShareOfFoodGroups().entrySet().stream().toList());
			
		quickSort(foodGroupSharePair);
		foodGroupSharePair.forEach(e-> System.out.println(e.getKey().getName() 
													+ " with cost, £" + countries.get(CountryDietDataRegistry.getCountryDietDataIdByName("Germany")).getCost(e.getKey().getName()) 
													+ " and share, " + e.getValue()*100 + "%"));
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
			for(Map.Entry<String, FoodGroup> entry : FoodGroupRegistry.getFoodGroups().entrySet())
			{
				double cost = c.getCost(entry.getKey())*c.getShare(entry.getKey());
				total += cost;
				System.out.println("Least cost healthy diet-" + entry.getKey() + ": £" + c.getCost(entry.getKey()) 
				+ "*" + c.getShare(entry.getKey())*100 + "/100 = £" + formatToTwoDecimalPlaces(cost));
			}
			System.out.println("Total : £" + formatToTwoDecimalPlaces(total));
		}
	}
		
	private <T> List<T> mergeSort(List<T> list, Comparator<? super T>comparator)
	{
		if(list.size()<=1)
			return list;
		int mid = list.size()/2;
			
		List<T> left = new ArrayList<>(list.subList(0, mid));
		List<T> right = new ArrayList<>(list.subList(mid, list.size()));
			
		left = mergeSort(left,comparator);
		right = mergeSort(right,comparator);
			
		return merge(left,right,comparator);
	}
		
	private <T> List<T> merge(List<T> left, List<T> right, Comparator<? super T> comparator)
	{
		int leftIndex = 0; int rightIndex = 0;
		List<T> result = new ArrayList<>();
			
		while(leftIndex < left.size() && rightIndex < right.size())
		{
			if(comparator.compare(left.get(leftIndex), right.get(rightIndex)) <= 0)
			{
				result.add(left.get(leftIndex));
				leftIndex++;
			}
			else
			{
				result.add(right.get(rightIndex));
				rightIndex++;
			}
		}
		while(leftIndex < left.size())
		{
			result.add(left.get(leftIndex));
			leftIndex++;
		}
		while(rightIndex < right.size())
		{
			result.add(right.get(rightIndex));
			rightIndex++;
		}
		return result;
	}
		
	private void quickSort(List<Map.Entry<FoodGroup, Double>> input)
	{
		quickSort(input,0,input.size()-1);
	}
		
	private void quickSort(List<Map.Entry<FoodGroup,Double>> input, int left ,int right)
	{
		if(left<right)
		{
			int pivotIndex = partition(input, left, right);
			quickSort(input, left, pivotIndex - 1);
			quickSort(input, pivotIndex+1, right);
		}
			
	}
		
	private int partition(List<Map.Entry<FoodGroup, Double>> input, int left, int right)
	{
		int pivotIndex = chooseRandomPivotIndex(left,right);
		swap(input, right, pivotIndex);
		double pivot = input.get(right).getValue();
		int i = left - 1;
		for(int j = left; j< right; j++)
		{
			if(input.get(j).getValue() >= pivot)
			{
				i++;
				swap(input,i,j);
			}
		}
		swap(input,i+1,right);
		return i+1;
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
	private Map<FoodGroup, Double> costOfFoodGroups;
	private Map<FoodGroup, Double> shareOfFoodGroups;
		
	public CountryDietData(String country)
	{
		this.country = country;
		this.costOfFoodGroups = new HashMap<>();
		this.shareOfFoodGroups = new HashMap<>();
	}
		
	public String getCountry()
	{
		return this.country;
	}
		
	public Map<FoodGroup, Double> getCostOfFoodGroups() {
		return costOfFoodGroups;
	}

	public Map<FoodGroup, Double> getShareOfFoodGroups() {
		return shareOfFoodGroups;
	}

	public void setCost(String foodGroupName, double cost)
	{
		FoodGroup foodGroup  = FoodGroupRegistry.getFoodGroup(foodGroupName);
		if(foodGroup != null)
			costOfFoodGroups.put(foodGroup, cost);
		else
			System.out.println("Food group not found : " + foodGroupName);
	}
		
	public double getCost(String foodGroupName)
	{
		FoodGroup foodGroup = FoodGroupRegistry.getFoodGroup(foodGroupName.toUpperCase());
		return costOfFoodGroups.getOrDefault(foodGroup, 0.0);
	}
		
	public void setShare(String foodGroupName, double share)
	{
		FoodGroup foodGroup = FoodGroupRegistry.getFoodGroup(foodGroupName);
		if(foodGroup!= null)
			shareOfFoodGroups.put(foodGroup, share);
		else
			System.out.println("Food group not found : " + foodGroupName);
	}
		
	public double getShare(String foodGroupName)
	{
		FoodGroup foodGroup = FoodGroupRegistry.getFoodGroup(foodGroupName);
		return shareOfFoodGroups.getOrDefault(foodGroup, 0.0);
	}
		
	public double getTotalCost()
	{
		double totalCost = 0.0;
		for(FoodGroup f: costOfFoodGroups.keySet())
			totalCost += costOfFoodGroups.get(f);
		return totalCost;
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
	private static Map<String, FoodGroup> foodGroups = new HashMap<>();
		
	static
	{
		foodGroups.put("FRUITS" , new FoodGroup("FRUITS"));
		foodGroups.put("VEGETABLES", new FoodGroup("VEGETABLES"));
		foodGroups.put("ANIMAL_SOURCED_FOODS", new FoodGroup("ANIMAL_SOURCED_FOODS"));
		foodGroups.put("LEGUMES_NUTS_AND_SEEDS", new FoodGroup("LEGUMES_NUTS_AND_SEEDS"));
		foodGroups.put("OILS_AND_FATS", new FoodGroup("OILS_AND_FATS"));
		foodGroups.put("STARTCHY_STAPLES", new FoodGroup("STARTCHY_STAPLES"));
	}
		
	public static Map<String, FoodGroup> getFoodGroups() {
		return foodGroups;
	}

	public static FoodGroup getFoodGroup(String name)
	{
		return foodGroups.get(name.toUpperCase());
	}
		
	public static void addFoodGroup(String name)
		{
		if(!foodGroups.containsKey(name.toUpperCase()))
		{
			foodGroups.put(name.toUpperCase(), new FoodGroup(name.toUpperCase()));
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