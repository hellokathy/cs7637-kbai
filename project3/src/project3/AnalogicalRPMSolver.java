package project3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;

public class AnalogicalRPMSolver {

	private SemanticNet semanticNet = null;
	
	public AnalogicalRPMSolver(SemanticNet semanticNet)
	{
		this.semanticNet = semanticNet;
	}
	
	private int getMissingValue(List<Integer> values)
	{
		if (values.size() != 8)
		{
			throw new RuntimeException ("List must contain 8 values");
		}
		
		// split values into two lists
		List<Integer> list1 = new ArrayList<Integer>();  // row1  
		List<Integer> list2 = new ArrayList<Integer>();  // row2
		List<Integer> listOf2 = new ArrayList<Integer>();  // row missing a value

		for (int i = 0 ; i < 3 ; i++) list1.add(values.get(i));
		for (int i = 3 ; i < 6 ; i++) list2.add(values.get(i));
		for (int i = 6 ; i < 8 ; i++) listOf2.add(values.get(i));

		// first, check for constants in rows
		
		CheckForConstantAcrossRowOrCol chk1 = new CheckForConstantAcrossRowOrCol(list1);
		CheckForConstantAcrossRowOrCol chk2 = new CheckForConstantAcrossRowOrCol(list2);
		
		if (chk1.isConstantAcrossRowOrCol() && chk2.isConstantAcrossRowOrCol())
		{
			// check g and h. if they are equal check for constants across rows has passed
			if (listOf2.get(0) == listOf2.get(1)) return listOf2.get(0);
		}

		// second, check for distribution of 3 values
		
		CheckForDistributionOf3Values chk3 = new CheckForDistributionOf3Values(list1,list2);
		
		if (chk3.isDistributionOf3Values() && chk3.isPartOfDistribution(listOf2))
		{
			return chk3.getMissingValue(listOf2);
		}
		
		// third, check for distribution of 2 values

		CheckForDistributionOf2Values chk4 = new CheckForDistributionOf2Values(list1,list2);
		if (chk4.isDistributionOf2Values() && chk4.isPartOfDistribution(listOf2))
		{
			return chk4.getMissingValue(listOf2);
		}
		
		
		// fourth, check for arithmetic progression
		CheckForArithmeticSeries chk5 = new CheckForArithmeticSeries(list1);
		CheckForArithmeticSeries chk6 = new CheckForArithmeticSeries(list2);
		
		if (chk5.isArithmeticSeries() && chk6.isArithmeticSeries() && chk5.isAnalogousTo(chk6))
		{
			return chk6.getMissingValue(listOf2);
		}
		
		// fifth, check for geometric progression
		CheckForGeometricSeries chk7 = new CheckForGeometricSeries(list1);
		CheckForGeometricSeries chk8 = new CheckForGeometricSeries(list2);
		
		if (chk7.isGeometricSeries() && chk8.isGeometricSeries() && chk7.isAnalogousTo(chk8))
		{
			return chk8.getMissingValue(listOf2);
		}
		
		// sixth, check for binary operation
		
		CheckForBinaryOp chk9 = new CheckForBinaryOp(list1);
		CheckForBinaryOp chk10 = new CheckForBinaryOp(list2);
		
		if (chk9.isAnalogousTo(chk10))
		{ 
			return chk10.getMissingValue(listOf2);
		}
		
		return 0;
		
	}
	
	public String computeSolution()
	{
    	ArrayList<String> slotNameList = this.semanticNet.getAllSlots();
    	ArrayList<String> frameLabelList = this.semanticNet.getAllFrameLabels();
    	
    	Node generatedNode = new Node("?");
    	
		if (this.semanticNet.rpmType.compareTo("3x3") == 0 )
		{
			List<String> labels = Arrays.asList("A","B","C","D","E","F","G","H");

	    	for (String frameLabel : frameLabelList)
	    	{
    			Frame generatedFrame = new Frame(frameLabel);
    			
	    		for (String slotName : slotNameList)
	    		{
	    			List<Integer> values = new ArrayList<Integer>();
	    			
	    			for (String l : labels)
	    			{
		    			values.add( this.semanticNet.getNode(l).getFrame(frameLabel).getSlot(slotName) );
	    			}

	    			System.out.println("\nFrame "+frameLabel+" | "+slotName+"| a:"+values.get(0)+", b:"+values.get(1)+", c:"+values.get(2)+", d:"+values.get(3)+", e:"+values.get(4)+", f:"+values.get(5)+", g:"+values.get(6)+", h:"+values.get(7));
	    			
	    			int missingValue = getMissingValue(values);
	    			
	    			// TODO : need to transpose missingValue to cater for angle > 360
	    			int transposedValue = transpose(slotName, missingValue);
	    			
	    			NameValuePair pair = new NameValuePair(slotName,transposedValue);
	    			generatedFrame.addSlot(pair);
	    		}
	    		
    			generatedNode.addFrame(generatedFrame);

	    	}		
	    	
	    	// test generatedNode against the possible solutions 1 - 6
	    	System.out.println("\n\nGenerated Figure:\n");
	    	generatedNode.printFrames();
		}

		return "1";
	}
	
	public int transpose(String slotName, int missingValue)
	{
		if (slotName.compareTo("angle")==0)
		{
			return missingValue % 360;
		}
		else
		{
			return missingValue;
		}
	}
}
