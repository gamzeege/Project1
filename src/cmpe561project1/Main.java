package cmpe561project1;

import java.awt.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Main {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		//Classifies the documents as Training and Test 
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String path;
		//inputs path ie.69yazar
		System.out.println("Enter the path: ");
		path=br.readLine();
		//Splits files as training and test 
		SplitFiles splitter = new SplitFiles(path);
		splitter.Recognize();
		//creates directories for tokenization of training and test sets
		System.out.println("Enter the path of training set: ");
		String tokenizerTrainingPath = br.readLine();
		File fileTraining = new File(tokenizerTrainingPath);
		System.out.println("Enter the path of test set: ");
		String tokenizerTestPath= br.readLine();
		File fileTest = new File(tokenizerTestPath);
		String tokenizerTrainingOutput= "./Tokenized_Training";
		File fileTrainingOutput = new File(tokenizerTrainingOutput);
		fileTrainingOutput.mkdir();
		String tokenizerTestOutput = "./Tokenized_Test";
		File fileTestOutput = new File(tokenizerTestOutput);
		fileTestOutput.mkdir();
		
		//tokenizes training and test sets
		Tokenizer tokenizerTraining= new Tokenizer(fileTraining,fileTrainingOutput);
		Tokenizer tokenizerTest= new Tokenizer(fileTest, fileTestOutput);
		tokenizerTraining.tokenize();
		tokenizerTest.tokenize();
		//vocabulary size 
		
		int vocabularySize=tokenizerTraining.Vocabulary.size();
		ArrayList<String> vocab=new ArrayList<String>();
		vocab=tokenizerTraining.Vocabulary;
		
		String path1="./Tokenized_Training";
		File f1=new File(path1);
		String path2="./Tokenized_Test";
		File f2=new File(path2);
		
		MultinomialNaiveBayes mnb= new MultinomialNaiveBayes(f1,f2);
		mnb.calculatePriors();
		mnb.calculateConditionalProb(vocab);
		mnb.MNBclassifier(vocab);
		
	}

}
