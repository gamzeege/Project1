package cmpe561project1;

import java.awt.List;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
//gets the path and generates training and the test files

public class SplitFiles {
	String path;
	//Constructor
	public SplitFiles(String path){
		this.path=path;
	}
	public void Recognize() throws IOException{
		//Random Object
		//gets the file
		File inputDirectory= new File(path);
		// gets every author file in the input directory
		File[] input = inputDirectory.listFiles(new FileFilter(){
			public boolean accept(File file){
				return file.isDirectory();
			}
		});

		String TraDir= "./Training";
		String TeDir= "./Test";
		File fileTraining= new File(TraDir);
		fileTraining.mkdir();
		File fileTest= new File(TeDir);
		fileTest.mkdir();
		System.out.println("Cleaning the directories... ");
		
		String[] directories = fileTraining.list(new FilenameFilter() {
			public boolean accept(File current, String name) {
				return new File(current, name).isDirectory();
			}
		});
		for(String dir:directories){
			File file=new File(TraDir.concat("/").concat(dir));
			for (File f : file.listFiles()) {
				f.delete(); 
			}
		}
		String[] directories2 = fileTest.list(new FilenameFilter() {
			public boolean accept(File current, String name) {
				return new File(current, name).isDirectory();
			}
		});
		for(String dir:directories2){
			File file=new File(TeDir.concat("/").concat(dir));
			for (File f : file.listFiles()) {
				f.delete(); 
			}
		}
		//for every author, gets training and test document
		for(File file:input){

			String AuthorPath = file.getPath();
			String newDirectory= TraDir.concat("/"+file.getName());
			String newDirectory2= TeDir.concat("/"+file.getName());
			File f1=new File(newDirectory);
			f1.mkdir();
			File f2=new File(newDirectory2);
			f2.mkdir();
			File AuthorDirectory = new File(AuthorPath);


			File[] inputFiles = AuthorDirectory.listFiles(new FileFilter() {
				public boolean accept(File file) {
					return file.getName().endsWith(".txt");
				}
			});
			int itemTraining= (int) (inputFiles.length*0.6);

			int itemTest= inputFiles.length-itemTraining;
			//System.out.println("number of authors : "+inputFiles.length);
			FileInputStream in = null;	
			OutputStream out = null;

			ArrayList<Integer> numbers = new ArrayList<Integer>();
			for(int i=0; i<inputFiles.length; i++)
			{
				numbers.add(i);
			}
			Collections.shuffle(numbers);
			for(int i=0; i<itemTraining; i++)
			{
				in = new FileInputStream(inputFiles[numbers.get(i)]);
				out = new FileOutputStream(f1.getPath().concat("/").concat(inputFiles[numbers.get(i)].getName()));

				byte[] moveBuff = new byte[1024];
				int butesRead;
				while ((butesRead = in.read(moveBuff)) > 0) 
				{
					out.write(moveBuff, 0, butesRead);
				}
				in.close();
				out.close();
			}
			for(int i=itemTraining; i<numbers.size(); i++){
				in = new FileInputStream(inputFiles[numbers.get(i)]);
				out = new FileOutputStream(f2.getPath().concat("/").concat(inputFiles[numbers.get(i)].getName()));
				byte[] moveBuff = new byte[1024];
				int butesRead;
				while ((butesRead = in.read(moveBuff)) > 0) 
				{
					out.write(moveBuff, 0, butesRead);
				}
				in.close();
				out.close();
			}

		}


	}
}
