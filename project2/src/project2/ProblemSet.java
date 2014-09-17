/*
 * DO NOT MODIFY THIS FILE.
 * 
 * Any modifications to this file will not be used when grading your project.
 * If you have any questions, please email the TAs.
 * 
 */
package project2;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

/**
 * A list of RavensProblems within one set.
 * 
 * Your agent does not need to use this class explicitly.
 * 
 */
public class ProblemSet {
    private String name;
    private ArrayList<RavensProblem> problems;
    Random random;
    
    /**
     * Initializes a new ProblemSet with the given name, an empty set of
     * problems, and a new random number generator.
     * 
     * Your agent does not need to use this method. 
     * 
     * @param name The name of the problem set.
     */
    public ProblemSet(String name) {
        this.name=name;
        problems=new ArrayList<>();
        random=new Random();
    }
    
    /**
     * Returns the name of the problem set.
     * 
     * Your agent does not need to use this method. 
     * 
     * @return the name of the problem set as a String
     */
    public String getName() {
        return name;
    }
    /**
     * Returns an ArrayList of the RavensProblems in this problem set.
     * 
     * Your agent does not need to use this method. 
     * 
     * @return the RavensProblems in this set as an ArrayList.
     */
    public ArrayList<RavensProblem> getProblems() {
        return problems;
    }
    /**
     * Adds a new problem to the problem set, read from an external file.
     * 
     * Your agent does not need to use this method. 
     * 
     * @param problem the File containing the new problem.
     */
    public void addProblem(File problem) {
        Scanner r=null;
        try{
            r = new Scanner(problem);
        } catch(Exception ex) {
            System.out.println(ex);
        }
        String name=r.nextLine();
        String type=r.nextLine();
        String currentAnswer=r.nextLine();
        //String answer="";
        String answer=currentAnswer;
        
        ArrayList<RavensFigure> figures=new ArrayList<>();
        RavensFigure currentFigure=null;
        RavensObject currentObject=null;
        ArrayList<String> options=new ArrayList<>(Arrays.asList("1","2","3","4","5","6"));
        while(r.hasNext()) {
            String line=r.nextLine();
            if(!line.startsWith("\t")) {
                /*if(tryParseInt(line)) {
                    int i=random.nextInt(options.size());
                    if(currentAnswer.equals(line)) {
                        answer=options.get(i);
                    }
                    line=options.get(i);
                    options.remove(i);
                }*/
                RavensFigure newFigure=new RavensFigure(line);
                figures.add(newFigure);
                currentFigure=newFigure;
            }
            else if(!line.startsWith("\t\t")) {
                line=line.replace("\t", "");
                RavensObject newObject=new RavensObject(line);
                currentFigure.getObjects().add(newObject);
                currentObject=newObject;
            }
            else if(line.startsWith("\t\t")) {
                line=line.replace("\t", "");
                String[] split=line.split(":");
                RavensAttribute newAttribute=new RavensAttribute(split[0], split[1]);
                currentObject.getAttributes().add(newAttribute);
            }
        }
        RavensProblem newProblem=new RavensProblem(name,type,answer);
        for(RavensFigure figure : figures) {
            newProblem.getFigures().put(figure.getName(), figure);
        }
        problems.add(newProblem);
    }
    private boolean tryParseInt(String i) {
     try  
     {  
         Integer.parseInt(i);  
         return true;  
      } catch(NumberFormatException nfe)  
      {  
          return false;  
      }  
    }
}
