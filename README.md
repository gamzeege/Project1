I worked on macbook osx 10.10.3 and Eclipse luna 4.4.1

If you try to run the code on your windows computer, the path representations in the code will be a problem since mac use "/" to split directory.

The program first asks a path to a file formatted just like the raw_texts in 69yazar file. 

So first path is supposed to be like: /Users/gamzeegekaya/Downloads/69yazar/raw_texts

After getting the file it creates a training and test directories in the same directory namely;

            Training
            
            Test

If you want to train these files you should give the path of training path
      
            Enter the path of training set: 
            
            Training
            
            Enter the path of test set: 
            
            Test

If you want to train another files you should give the paths in the above i/o.

            Paths are supposed to be exact structure as raw_texts (ie.one directory containing 69 directories named by authors and inside
            
            these author directories, the text files are located. )Then it tries to predict the class.

I added an executable jar file into the repository.

I also added the copy of the report.

To run the executable jar on the command line:

      java -jar CmpE561.jar
      
To run the code itself on the command line:

      javac -d bin/ -cp src src/cmpe561project1/Main.java
      
      java -cp bin cmpe561project1.Main
      
      
      
