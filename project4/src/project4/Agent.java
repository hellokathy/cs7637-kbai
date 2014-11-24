package project4;

/**
 * Your Agent for solving Raven's Progressive Matrices. You MUST modify this
 * file.
 * 
 * You may also create and submit new files in addition to modifying this file.
 * 
 * Make sure your file retains methods with the signatures:
 * public Agent()
 * public char Solve(VisualRavensProblem problem)
 * 
 * These methods will be necessary for the project's main method to run.
 * 
 */
public class Agent {
    /**
     * The default constructor for your Agent. Make sure to execute any
     * processing necessary before your Agent starts solving problems here.
     * 
     * Do not add any variables to this signature; they will not be used by
     * main().
     * 
     */
	PrepositionalAgent prepositionalAgent = null; // previous agent from Project3
	
    public Agent() {
        this.prepositionalAgent = new PrepositionalAgent();
    }
    /**
     * The primary method for solving incoming Raven's Progressive Matrices.
     * For each problem, your Agent's Solve() method will be called. At the
     * conclusion of Solve(), your Agent should return a String representing its
     * answer to the question: "1", "2", "3", "4", "5", or "6". These Strings
     * are also the Names of the individual RavensFigures, obtained through
     * TextRavensFigure.getName().
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
     * @param problem the VisualRavensProblem your agent should solve
     * @return your Agent's answer to this problem
     */
    public String Solve(VisualRavensProblem problem) {
    	
    	// step 1 : generate equivalent TextRavensProbem
    	TextRavensProblemCreator trpCreator = new TextRavensProblemCreator(problem);
    	TextRavensProblem  trp = trpCreator.getTextRavensProblem();
    	
    	// step 2 : get answer from prepositional agent (have it return a confidence% along with answer)
    	String answer1 = this.prepositionalAgent.Solve(trp);
    	System.out.println(answer1);
    	
    	// step 3 : attempt to get an answer via visual reasoning
    	
    	// step 4 : if both answers match then return answer else return one with higher confidence%
    	
    	
        return answer1;
    }
}
