package project3;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.TreeMap;

import project3.OntologySet;
import project3.SemanticNet;
import project3.RavensAttribute;
import project3.RavensFigure;
import project3.RavensObject;


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
	private PrintStream logFile = null;
	private OntologySet ontologySet = null; 
	
    public Agent() 
    {
    	

		try {
			logFile = new PrintStream(new File("agent.log"));
			
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		// redirect all output to System.out to logfile instead
		System.setOut(new PrintStream(logFile));
        
    	// ensure that ontologies.txt is present   
    	// and readable in the program directory
    	try 
    	{
			ontologySet = new OntologySet();
		} catch (Exception e) 
    	{
			// TODO Auto-generated catch block
			e.printStackTrace();;
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
    public String Solve(RavensProblem problem) {
    	
    	System.out.println("------------------------------------------------------");
    	System.out.println("\n..solving problem: "+problem.getName());
    	
    	SemanticNet semanticNet = new SemanticNet(problem.getProblemType());
    	int objCtr = 0;
    	
    	TreeMap<String,RavensFigure> ravensFiguresSorted = new TreeMap<String,RavensFigure>();
    	ravensFiguresSorted.putAll(problem.getFigures());
    	
    	for ( RavensFigure rf : ravensFiguresSorted.values()) 
    	{
    		System.out.println("\n   >Figure: "+rf.getName());
    		
    		//Const.collecter = Const.collecter + rf.getName().trim();    	
    				
    		ArrayList<RavensObject> ravensObjects = rf.getObjects();
    		
    		ObjectCounterMap objCtrMap = new ObjectCounterMap();

    		for ( int i = 0; i < ravensObjects.size(); i++)
    		{
    			RavensObject ro = ravensObjects.get(i);
    			objCtr = i+1;
    			
    			//String roName = ro.getName().trim();
    			
    			// use numerical key for object instead of label .. same labels 
    			// between figures may not necessarily refer to same object
    			// and same object can have different labels between figures :(
    			    			
    			Frame f = new Frame();
    			
    			for ( RavensAttribute ra : ro.getAttributes())
    			{
    				// read attribute, convert to numericalValue using ontology and add to  frame
    				
	    			// get slots containing ontology keys and values for this attribute name
	    	    	ArrayList<NameValuePair> slots = ontologySet.getFrameDataSet(ra);
	    	    	
    	    		
	    	    	f.addSlots(slots);  				
    				
    			}
    			
    			// need to add a frameLabel to the frame
    			Tuple tp = objCtrMap.addObjectHash(f.getObjectHash(), objCtr);
    			
    			f.setFrameLabel(Integer.toString(tp.index));
    			
    			NameValuePair pair = new NameValuePair("shape-count", tp.count );
    			f.addSlot(pair);
    			    			
    			// add frame to semantic network
    			semanticNet.addFrameToNode( f, rf.getName().trim() );
    		}
    		
			System.out.println("           >Frames:");
			semanticNet.getNode(rf.getName().trim()).printFrames();

    		
    	}
    	//semanticNet.debugPrintNetwork();
            	
    	AnalogicalRPMSolver solver = new AnalogicalRPMSolver(semanticNet);
    	
		String answerCalculated = solver.computeSolution();

		String correctAnswer = problem.checkAnswer(answerCalculated);

    	System.out.println("\n\nAnswer Figure: Correct Answer is "+correctAnswer+"\n");

		semanticNet.getNode(correctAnswer).printFrames();

    	return answerCalculated;
    	

    }
}
