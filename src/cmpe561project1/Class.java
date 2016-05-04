package cmpe561project1;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Class {
	//number of files that belong to this class
	int numberofmembers;
	//class name
	String name;
	//prior value of the class
	double prior;
	//maps every term to its frequency in the class
	Map<String, Integer> wordFrequencyRelation= new HashMap<String,Integer>();
	//maps every term in the whole vocabulary to its conditional probablity
	//in the class
	Map<String, Double> conditionalProbabilities= new HashMap<String,Double>();
	//stores files that belong to the class
	LinkedList<File> fileOfClass= new LinkedList<File>();
	//number of tokens(not unique) in the class
	int numberOfTotalTermsInClass;
	
	//constructor with name and number of members that belong to this class
	public Class(int numberofmembers, String name) {
		super();
		this.numberofmembers = numberofmembers;
		this.name = name;
		this.numberOfTotalTermsInClass=0;
	}
	//getters and setters
	public int getNumberofmembers() {
		return numberofmembers;
	}
	public void setNumberofmembers(int numberofmembers) {
		this.numberofmembers = numberofmembers;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPrior() {
		return prior;
	}
	public void setPrior(double prior) {
		this.prior = prior;
	}
	public Map<String, Integer> getWordFrequencyRelation() {
		return wordFrequencyRelation;
	}
	public void setWordFrequencyRelation(Map<String, Integer> wordFrequencyRelation) {
		this.wordFrequencyRelation = wordFrequencyRelation;
	}
	public Map<String, Double> getConditionalProbabilities() {
		return conditionalProbabilities;
	}
	public void setConditionalProbabilities(
			Map<String, Double> conditionalProbabilities) {
		this.conditionalProbabilities = conditionalProbabilities;
	}
	public LinkedList<File> getFileOfClass() {
		return fileOfClass;
	}
	public void setFileOfClass(LinkedList<File> fileOfClass) {
		this.fileOfClass = fileOfClass;
	}
	public int getNumberOfTotalTermsInClass() {
		return numberOfTotalTermsInClass;
	}
	public void setNumberOfTotalTermsInClass(int numberOfTotalTermsInClass) {
		this.numberOfTotalTermsInClass = numberOfTotalTermsInClass;
	}
	
	
}
