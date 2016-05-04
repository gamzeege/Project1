package cmpe561project1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class Tokenizer {
	private File inputDir;
	private File outputDir;
	LinkedList<String> words=new LinkedList();
	static Pattern p;

	ArrayList<String> Vocabulary=new ArrayList<String>();//for vocabulary


	public Tokenizer(File inputDir, File outputDir) {
		super();
		this.inputDir = inputDir;
		this.outputDir = outputDir;
		System.out.println("Deleting all files in " + outputDir);
		for (File f : outputDir.listFiles()) {
			f.delete();
		}
	}
	public void tokenize() throws IOException {
		String[] directories = inputDir.list(new FilenameFilter() {
			public boolean accept(File current, String name) {
				return new File(current, name).isDirectory();
			}
		});
		for(String directory:directories){
			File f=new File(inputDir.getPath().concat("/").concat(directory));
			File[] inputFiles = f.listFiles(new FileFilter() {
				public boolean accept(File file) {
					return file.getName().endsWith(".txt");
				}
			});
			if (inputFiles != null && inputFiles.length > 0) {
				for (File inpFile : inputFiles) {
					tokenizeFile(inpFile,directory);
				}
			} else {
				System.err.println("No .txt files in " + inputDir);
			}
		}
	}
	private void tokenizeFile(File inpFile, String directory) throws IOException {
		FileReader file = new FileReader(inpFile);
		BufferedReader br = new BufferedReader(file);
		String storeWordList = "";

		try {
			while ((storeWordList = br.readLine()) != null) {

				StringTokenizer st = new StringTokenizer(storeWordList);
				while (st.hasMoreTokens()){

					try
					{
						p = Pattern.compile ("[\\s]"); //matching pattern for punctuation
					}
					catch (PatternSyntaxException e)
					{
						System.err.println ("Regex syntax error: " + e.getMessage ());
						System.err.println ("Error description: " + e.getDescription ());
						System.err.println ("Error index: " + e.getIndex ());
						System.err.println ("Erroneous pattern: " + e.getPattern ());
						return;
					}
					String token = st.nextToken();
					Matcher m = p.matcher (token);
					if(m.find() == true){
						while(m.find ()){
							token=m.replaceAll("");
						}
						token=m.replaceAll("");
					}
					//getting rid of punctuation at the beginning or end of the token.
					if(!token.isEmpty()){
						while(token.startsWith(".")||token.startsWith(",")||token.startsWith("!")||token.startsWith("?")
								||token.startsWith(";")||token.startsWith(":")||token.startsWith("\'")||token.startsWith("\\")||token.startsWith("'")
								||token.startsWith("+")||token.startsWith("<")||token.startsWith(">")||token.startsWith("/'"))
						{
							token=token.substring(1);
						}
						while(token.endsWith(".")||token.endsWith(",")||token.endsWith("!")||token.endsWith("?")
								||token.endsWith(";")||token.endsWith(":")||token.endsWith("\'")||token.endsWith("\\")||token.endsWith("'")
								||token.endsWith("+")||token.endsWith("<")||token.endsWith(">")||token.endsWith("/'"))
						{
							token=token.substring(0, token.length()-1);
						}
						//case folding
						token=token.toLowerCase();
						if(!token.matches(".*\\d+.*"))
							words.add(token);	
						//Create the vocabulary 
						if (!Vocabulary.contains(token)) {  //avoid duplicate entry
							Vocabulary.add(token);
						}
					}			
				}
			}  	  
			// dispose all the resources after using them.
			br.close();
			file.close();

			//write into a file tokenized words
			File outFile = new File(outputDir, directory.concat("_").concat(inpFile.getName()));
			FileWriter writer = new FileWriter(outFile);
			writer.write("\n");
			for(int i=0; i<words.size(); i++){
				writer.write(words.get(i));
				writer.write("\n");
			}
			writer.close();
			
			words.clear();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		br.close();
	}

}
