package cmpe561project1;

import java.awt.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.text.html.HTMLDocument.Iterator;


public class MultinomialNaiveBayes {
	File pathTraining;
	File pathTest;
	File[] inputFiles;
	File[] testFiles;
	CopyOnWriteArrayList<Class> allClasses= new CopyOnWriteArrayList<Class>();
	Map<String, Double> classifierValues= new HashMap<String,Double>();
	
	//constructor with training and test files.
	public MultinomialNaiveBayes(File pathTraining, File pathTest) {
		super();
		this.pathTraining = pathTraining;
		this.pathTest = pathTest;
		inputFiles = pathTraining.listFiles(new FileFilter() {
			public boolean accept(File file) {
				return file.getName().endsWith(".txt");
			}
		});
		testFiles= pathTest.listFiles(new FileFilter() {
			public boolean accept(File file) {
				return file.getName().endsWith(".txt");
			}
		});

	}
	public void calculatePriors()
	{
		String filename=inputFiles[0].getName();
		String[] words= filename.split("_");
		Class newc= new Class(0,words[0]);

		allClasses.add(newc);
		for(File f:inputFiles)
		{	
			String[] Authorname= f.getName().split("_");
			int flag=0;
			Class equality= new Class(0,"");
			for(Class c:allClasses)
			{
				if(c.getName().equals(Authorname[0])){
					flag=1;
					equality=c;
				}
			}
			if(flag==1)
			{
				equality.setNumberofmembers(equality.numberofmembers+1);
				equality.fileOfClass.add(f);
			}else
			{
				Class newClass= new Class(1,Authorname[0]);
				newClass.fileOfClass.add(f);
				allClasses.add(newClass);
			}
		}

		//System.out.println(allClasses.size());
		
		//sets the prior values for all classes
		//puts the prior values of each class to a hashmap to later calculate classifier.

		
	}
	public void calculateConditionalProb(ArrayList <String> Vocabulary) throws IOException
	{
		//creates a small vocabulary for each class
		for(Class c:allClasses)
		{
			LinkedList<File> fileOfThisClass= c.fileOfClass;
			for(File f:fileOfThisClass)
			{
				BufferedReader reader = new BufferedReader(new FileReader(f));
				String emptyLine= reader.readLine();
				for (String line = reader.readLine(); line != null; line = reader.readLine()) 
				{
					if(!c.wordFrequencyRelation.containsKey(line))
					{
						c.wordFrequencyRelation.put(line, 1);
					}else
					{
						int freq= c.wordFrequencyRelation.get(line);
						c.wordFrequencyRelation.put(line, freq+1);
					}
					c.setNumberOfTotalTermsInClass(c.numberOfTotalTermsInClass+1);
					
				}
			}
		}
		for(String s: Vocabulary)
		{
			for(Class c:allClasses)
			{
				if(c.wordFrequencyRelation.containsKey(s))
				{
					int freq=c.wordFrequencyRelation.get(s);
					int total=c.getNumberOfTotalTermsInClass();
					int size=Vocabulary.size();
					double condProb=Math.log((double)(freq+1)/(c.getNumberOfTotalTermsInClass()+Vocabulary.size()));
					c.conditionalProbabilities.put(s, condProb);
				}else
				{
					double condProb=Math.log((double)(1)/(c.getNumberOfTotalTermsInClass()+Vocabulary.size()));
					c.conditionalProbabilities.put(s, condProb);
				}
			}
		}	
	}
	
	public void MNBclassifier(ArrayList <String> Vocabulary) throws IOException
	{
		int trues=0;
		int falses=0;
		for(File f:testFiles)
		{
			String[] words= f.getName().split("_");
			//System.out.print(f.getName()+" is classified as ");
			BufferedReader reader = new BufferedReader(new FileReader(f));
			String emptyLine= reader.readLine();
			for(Class c:allClasses)
			{
				double prior= Math.log(((double)c.getNumberofmembers())/allClasses.size());
				c.setPrior(prior);
				classifierValues.put(c.getName(), prior);
			}
			for (String line = reader.readLine(); line != null; line = reader.readLine()) 
			{
				//System.out.print(line+" ");
				for (Class c:allClasses)
				{
					//System.out.print(c.getName()+" ");
					double condProb;
					if(c.conditionalProbabilities.containsKey(line))
					{
						condProb=c.conditionalProbabilities.get(line);
					}else
					{
						condProb=Math.log((double)(1)/(c.getNumberOfTotalTermsInClass()+Vocabulary.size()));
					}
					double oldValue=classifierValues.get(c.getName());
					double newValue=condProb+oldValue;
					//System.out.println(newValue);
					classifierValues.put(c.getName(), newValue);
				}
			}
			
			ArrayList<String> mapKeys = new ArrayList<String>(classifierValues.keySet());
			ArrayList<Double> mapValues = new ArrayList<Double>(classifierValues.values());
			Collections.sort(mapValues);
			Collections.reverse(mapValues);

			//System.out.println(getKeyFromValue(classifierValues,mapValues.get(0)));
			if(getKeyFromValue(classifierValues,mapValues.get(0)).toString().equals(words[0]))
				trues++;
			else
				falses++;
			
			classifierValues.clear();
		}
		System.out.println("===== Bag Of Words Representation =====");
		System.out.println("Number of correctly classified documents "+trues);
		System.out.println("Number of incorrectly classified documents "+falses);
		System.out.println("Micro Precision is "+ ((double)trues/falses));
		System.out.println("Micro Recall is "+ ((double)trues/numbOfTestFiles));
		double p=(double)trues/falses;
		double r=(double)trues/numbOfTestFiles;
		System.out.println("Micro F-value is "+ (2*p*r/(p+r)));
	}
	public static Object getKeyFromValue(Map hm, Object value) {
		for (Object o : hm.keySet()) {
			if (hm.get(o).equals(value)) {
				return o;
			}
		}
		return null;
	}
}
