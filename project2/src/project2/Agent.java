package project2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Your Agent for solving Raven's Progressive Matrices. You MUST modify this
 * file.
 * 
 * You may also create and submit new files in addition to modifying this file.
 * 
 * Make sure your file retains methods with the signatures:
 * public Agent()
 * public char Solve(RavensProblem problem)
 * 
 * These methods will be necessary for the project's main method to run.
 * 
 */
public class Agent 
{
    /**
     * The default constructor for your Agent. Make sure to execute any
     * processing necessary before your Agent starts solving problems here.
     * 
     * Do not add any variables to this signature; they will not be used by
     * main().
     * 
     */
	
	private OntologySet ontologySet = null; 
	
    public Agent() 
    {
    	try {
    	    System.setOut(new PrintStream(new File("agent.log")));
    	} catch (Exception e) {
    	     e.printStackTrace();
    	}
    	
    	// ensure that ontologies.txt is present   
    	// and readable in the program directory
    	try 
    	{
			ontologySet = new OntologySet();
		} catch (Exception e) 
    	{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
    }
    
    /**
     * The primary method for solving incoming Raven's Progressive Matrices.
     * For each problem, your Agent's Solve() method will be called. At the
     * conclusion of Solve(), your Agent should return a String representing its
     * answer to the question: "1", "2", "3", "4", "5", or "6". These Strings
     * are also the Names of the individual RavensFigures, obtained through
     * RavensFigure.getName().
     * 
     * In addition to returning your answer at the end of the method, your Agent
     * may also call problem.checkAnswer(String givenAnswer). The parameter
     * passed to checkAnswer should be your Agent's current guess for the
     * problem; checkAnswer will return the correct answer to the problem. This
     * allows your Agent to check its answer. Note, however, that after your
     * agent has called checkAnswer, it will *not* be able to change its answer.
     * checkAnswer is used to allow your Agent to learn from its incorrect
     * answers; however, your Agent cannot change the answer to a question it
     * has already answered.
     * 
     * If your Agent calls checkAnswer during execution of Solve, the answer it
     * returns will be ignored; otherwise, the answer returned at the end of
     * Solve will be taken as your Agent's answer to this problem.
     * 
     * @param problem the RavensProblem your agent should solve
     * @return your Agent's answer to this problem
     */
    public String Solve(RavensProblem problem) 
    {
    	
       	// traverse RavensProblem - populate KnnSemanticNetwork
    	
    	SemanticNet semanticNet = new SemanticNet(problem.getProblemType());
    	
    	System.out.println("------------------------------------------------------");
    	System.out.println("\n..solving problem: "+problem.getName());
    	
    	TreeMap<String,RavensFigure> sortedFigures = new TreeMap<String,RavensFigure>();
    	sortedFigures.putAll(problem.getFigures());
    	
    	for ( RavensFigure rf : sortedFigures.values()) 
    	{
    		System.out.println("   >Figure: "+rf.getName());
    		
    		ArrayList<RavensObject> objects = rf.getObjects();
    		for ( int i = 0; i < objects.size(); i++)
    		{
    			RavensObject ro = objects.get(i);
    			
    			System.out.println("     >Object: "+ro.getName());
    			System.out.println("         >slots | fillers:");
    			
    			Frame f = new Frame();
    			for ( RavensAttribute ra : ro.getAttributes())
    			{
    				// read attribute, convert to simlarityWeight using ontology
    				// and add to  frame
    				
    				// get slots containing ontology keys and values for this attribute name
    	    		ArrayList<NameValuePair> slots = ontologySet.getFrameDataSet(ra);
    	    		// log slots
    	    		    	    		
    	    		for (NameValuePair p : slots)
    	    			System.out.println("          "+p.getName()+" : "+p.getValue());
    				
    	    		f.addSlots(slots);
    			}
    			// add frame to semantic network
    			semanticNet.addFrameToNode(f, rf.getName().trim() );
    		}
    		
    	}
    	//semanticNet.debugPrintNetwork();
    	
    	// TODO: check integrity of semantic network at this point
    	
    	// create new solver - pass semantic network into constructor
    	KnnSolver knnSolver = new KnnSolver(semanticNet);
    	
    	String answerCalculated = knnSolver.computeSolution();
    	
    	System.out.println("Correct answer : "+problem.checkAnswer(answerCalculated));
        
    	return answerCalculated;
    }
    

}
