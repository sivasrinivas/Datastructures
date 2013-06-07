//package datastructures;

import java.io.File;
import java.text.DecimalFormat;
import java.util.Random;
import java.util.Scanner;

public class Heap {

	/**
	 * Program execution start point. Takes mode of execution as an argument and calls the 	corresponding method.
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String mode=args[0];
		
		if(mode.equals("-r"))
			randomMode();
		else if(mode.equals("-il")){
			String fileName=args[1];
			leftistInputMode(fileName);
		}
			
		else if(mode.equals("-ib")){
			String fileName=args[1];
			binomialInputMode(fileName);
		}
			
	}
	
	/**
	 * Generates a sequence of n random numbers and initialize Min Leftist Tree and Min Binomial Heap. 
	 * Then starts the random mode execution of both Min Leftist Tree and Min Binomial Heap.
	 */
	public static void randomMode(){
		int iterationSizes[]={100, 500, 1000, 2000, 3000, 4000, 5000};
		double leftistTime;
		double binomialTime;
		double leftistStart;
		double binomialStart;
		DecimalFormat df=new DecimalFormat("##.####");

		System.out.println("Randome mode resutls : "+"(Time in Micro Seconds)\n");
		System.out.println("Iteration Size \t\t MinLeftistTree \t BinomialHeap \t\t Which is better?"+"\n");

		for(int i=0; i<iterationSizes.length;i++){
			leftistTime=0.0;
			binomialTime=0.0;

			for(int j=0; j<5; j++){
				MinLeftistTree leftistTree=new MinLeftistTree();
				BinomialHeap binomialHeap=new BinomialHeap();
				
				int randomNumbers[]=new int[iterationSizes[i]];
				randomNumbers=random(randomNumbers);

				for(int k=0; k<iterationSizes[i];k++){
					leftistTree.insert(randomNumbers[k]);
					binomialHeap.insert(randomNumbers[k]);
				}
					

				int sequence[][]=new int[5000][2];
				Random randomOp=new Random();
				Random randomVal=new Random();
				int x;
				for(int l=0; l<5000; l++){
					x=randomOp.nextInt(2);
					if(x==0)
						sequence[l][0]=x;
					else
					{
						sequence[l][0]=x;
						sequence[l][1]=randomVal.nextInt(iterationSizes[i]);
					}
				}

				
				leftistStart=  System.currentTimeMillis();
				initializeLeftist(leftistTree,sequence);
				leftistStart=   System.currentTimeMillis()-leftistStart;
				leftistTime = leftistTime + (leftistStart/5000.0);
				
				binomialStart=  System.currentTimeMillis();
				initializeBinomial(binomialHeap,sequence);
				binomialStart=   System.currentTimeMillis()-binomialStart;
				binomialTime = binomialTime + (binomialStart/5000.0);

			}
			
			leftistTime=(leftistTime/5.0)*1000;
			binomialTime=(binomialTime/5.0)*1000 ;
			String better=new String();
			if(leftistTime<binomialTime)
				better="MinLeftistTree";
			else
				better="BinomialHeap";
			
			System.out.println(iterationSizes[i] + "\t\t\t" + df.format(leftistTime) +"\t\t\t"+ df.format(binomialTime) +"\t\t\t"+better+"\n");
//			System.out.println(iterationSizes[i] + "\t\t\t" + leftistTime +"\t\t\t"+ binomialTime +"\t\t\t"+better+"\n");
		}
	}

	/**
	 * Initialize the Min Leftist Tree with the given sequence of 	values.
	 */
	public static void initializeLeftist(MinLeftistTree leftistTree, int[][] sequence) {
		// TODO Auto-generated method stub
		int i=0;

		while(i<5000){
			if(sequence[i][0] == 0)
				leftistTree.insert(sequence[i][1]);
			else
				leftistTree.removeMin();
			i++;
		}
	}
	
	/**
	 * Initialize the Min Binomial Heap with the given sequence of 	values.
	 */
	public static void initializeBinomial(BinomialHeap binomialHeap, int[][] sequence) {
		// TODO Auto-generated method stub
		int i=0;

		while(i<5000){
			if(sequence[i][0] == 0)
				binomialHeap.insert(sequence[i][1]);
			else
				binomialHeap.removeMin();
			i++;
		}
	}

	/**
	 * Reads the input from the given file and executes the given operations on Min Leftist tree.
	 */
	public static void leftistInputMode(String fileName){
		MinLeftistTree leftistTree=new MinLeftistTree();
		int data=0;
		try{
			Scanner scanner=new Scanner(new File(fileName));

			while(scanner.hasNext()){
				String command=scanner.next();
				
				if(command.equals("I")){
					data=scanner.nextInt();
					leftistTree.insert(data);
				}
				
				else if(command.equals("D"))
					leftistTree.removeMin();
					
				else if(command.equals("*"))
					break;
					
			}
			leftistTree.displayTree();
			scanner.close();
		}
		catch(Exception e){
			System.out.println("Exception in Min Leftist Tree: "+e.getMessage());
		}
	}

	/**
	 * Reads the input from the given file and executes the given operations on Min Binomial heap.
	 */
	public static void binomialInputMode(String fileName){
		BinomialHeap binomialHeap=new BinomialHeap();
		int data=0;
		
		try{
			Scanner scanner=new Scanner(new File(fileName));
			while(scanner.hasNext()){
				String command=scanner.next();

				if(command.equals("I")){
					data=scanner.nextInt();
					binomialHeap.insert(data);
				}
				else if(command.equals("D"))
					binomialHeap.removeMin();
				else if(command.equals("*"))
					break;
			}

			binomialHeap.display();
			scanner.close();
		}
		catch(Exception e){
			System.out.println("Exception in Binomial Heap: "+e.getMessage());
			System.out.println("Are you trying to remove node from a empty Min Leftist Tree?");
		}
	}

	/**
	 * Takes an array and assigns random numbers from 0 to n-1 to the array and return it.
	 */
	public static int[] random(int tempArray[]){
		Random randomGen=new Random();
		int length=tempArray.length;
		int index;
		for(int i=0;i<length;i++){
			tempArray[i]=i;
		}

		for(int i=0;i<length;i++){
			index=randomGen.nextInt(length);
			int x=tempArray[i];
			tempArray[i]=tempArray[index];
			tempArray[index]=x;
		}

		return tempArray;
	}

}
