package project4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class AnalogicalRPMSolver {

	private SemanticNet semanticNet = null;
	
	public AnalogicalRPMSolver(SemanticNet semanticNet)
	{
		this.semanticNet = semanticNet;
	}
	
	private int getMissing2x2Value(List<Integer> values, String slotName)
	{
		if (values.size() != 3)
		{
			throw new RuntimeException ("List must contain 3 values");
		}
		
		CheckFor2x2Pattern chk2x2p1 = new CheckFor2x2Pattern(values);
		
		// check for 1111 pattern
		if (chk2x2p1.isPattern1111())
		{
			System.out.print(" via check for 1111 pattern");
			return chk2x2p1.getMissingValueFromPattern1111();
		}

		// check for 0101 pattern
		if (chk2x2p1.isPattern0101())
		{
			System.out.print(" via check for 0101 pattern");
			return chk2x2p1.getMissingValueFromPattern0101();
		}

		// check for 0011 pattern
		if (chk2x2p1.isPattern0011())
		{
			System.out.print(" via check for 0011 pattern");
			return chk2x2p1.getMissingValueFromPattern0011();
		}

		// check for 0110 pattern
		if (chk2x2p1.isPattern0011())
		{
			System.out.print(" via check for 0110 pattern");
			return chk2x2p1.getMissingValueFromPattern0110();
		}
		
//		// check for 0111 pattern
//		if (chk2x2p1.isPattern0111())
//		{
//			System.out.print(" via check for 0111 pattern");
//			return chk2x2p1.getMissingValueFromPattern0111();
//		}

		// check for possible addition pattern
		if (chk2x2p1.isPossibleAdditionPattern() )
		{
			System.out.print(" via check for possible addition pattern");
			return chk2x2p1.getMissingValueFromAdditionPattern();
		}
		
		System.out.print(" (no pattern found)");
		
		return 0;
	}
	
	private int getMissing3x3Value(List<Integer> values)
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

		// 1, check for constants in rows
		
		CheckForConstantAcrossRowOrCol chkConst1 = new CheckForConstantAcrossRowOrCol(list1);
		CheckForConstantAcrossRowOrCol chkConst2 = new CheckForConstantAcrossRowOrCol(list2);
		
		if (chkConst1.isConstantAcrossRowOrCol() && chkConst2.isConstantAcrossRowOrCol())
		{
			// check g and h. if they are equal check for constants across rows has passed
			if (listOf2.get(0) == listOf2.get(1)) 
			{
				System.out.print(" via CheckForConstantAcrossRowOrCol");
				return listOf2.get(0);
			}
		}

		// 2, check for arithmetic progression
		CheckForArithmeticSeries chkArithSeries1 = new CheckForArithmeticSeries(list1);
		CheckForArithmeticSeries chkArithSeries2 = new CheckForArithmeticSeries(list2);
		
		if (chkArithSeries1.isArithmeticSeries() && chkArithSeries2.isArithmeticSeries() && chkArithSeries1.isAnalogousTo(chkArithSeries2))
		{
			System.out.print(" viaCheckForArithmeticSeries (same constants across rows or cols");

			return chkArithSeries2.getMissingValue(listOf2);
		}

		
		// 3, check for geometric progression
		CheckForGeometricSeries chkGeomSeries1 = new CheckForGeometricSeries(list1);
		CheckForGeometricSeries chkGeomSeries2 = new CheckForGeometricSeries(list2);
		
		if (chkGeomSeries1.isGeometricSeries() && chkGeomSeries2.isGeometricSeries() && chkGeomSeries1.isAnalogousTo(chkGeomSeries2))
		{
			System.out.print(" via CheckForGeometricSeries");

			return chkGeomSeries2.getMissingValue(listOf2);
		}
		
		// 4, check for alternating series
		CheckForAlternatingSeries chkAltSeries = new CheckForAlternatingSeries(list1,list2,listOf2);
		
		if (chkAltSeries.isAlternatingSeries1())
		{
			System.out.print(" via CheckForAlternatingSeries");
			return chkAltSeries.getMissingValue();
		}
		
		// 5, strict check for distribution of 3 values
		
		CheckForDistributionOf3Values chkDistOf3Strict = new CheckForDistributionOf3Values(list1,list2, listOf2);
		
		if (chkDistOf3Strict.isDistributionOf3Values() )
		{
			System.out.print(" via CheckForDistributionOf3Values(Strict)");

			return chkDistOf3Strict.getMissingValue();
		}
		
		// 6, strict check for distribution of 2 values

		CheckForDistributionOf2Values chkDistOf2Strict = new CheckForDistributionOf2Values(list1,list2,listOf2);
		if (chkDistOf2Strict.isDistributionOf2Values())
		{
			System.out.print(" via CheckForDistributionOf2Values(Strict)");

			return chkDistOf2Strict.getMissingValue();
		}

		// 7, check for identical rows where 2 values are different from the 3rd or all 3 are different
		CheckForAllIdenticalRows chkAllIdRows1 = new CheckForAllIdenticalRows(list1, list2, listOf2);
		
		if (chkAllIdRows1.isIdenticalRows())
		{
			System.out.print(" viaCheckForIdenticalRows (2 values are different from the 3rd or all 3 are different)");

			return chkAllIdRows1.getMissingValue();
		}
		
		
		// 8, check for binary operation
		
		CheckForBinaryOp chkBinOp1 = new CheckForBinaryOp(list1);
		CheckForBinaryOp chkBinOp2 = new CheckForBinaryOp(list2);
		
		if (chkBinOp1.isAnalogousTo(chkBinOp2))
		{ 
			System.out.print(" via CheckForBinaryOp");

			return chkBinOp2.getMissingValue(listOf2);
		}
		
		// 9, check for arithmetic progression - all rows are arithmetic progressions but each with different constants
		CheckForArithmeticSeries chkArithSeries3 = new CheckForArithmeticSeries(list1);
		CheckForArithmeticSeries chkArithSeries4 = new CheckForArithmeticSeries(list2);
		
		if (chkArithSeries3.isArithmeticSeries() && chkArithSeries4.isArithmeticSeries() && chkArithSeries3.getConstant() != chkArithSeries4.getConstant())
		{
			System.out.print(" viaCheckForArithmeticSeries (different constants across rows or cols)");

			return listOf2.get(1) + listOf2.get(1) - listOf2.get(0);
		}

		// 10, check for arithmetic progression - just check that previous row only is an arithmetic series
		CheckForArithmeticSeries chkArithSeries5 = new CheckForArithmeticSeries(list2);
		
		if (chkArithSeries5.isArithmeticSeries() )
		{
			System.out.print(" via CheckForArithmeticSeries (just check that previous row only is an arithmetic series)");

			return listOf2.get(1) + listOf2.get(1) - listOf2.get(0);
		}
		
		// 11, check for possible misaligned values
		CheckForMisalignedValues chkMisalignVal = new CheckForMisalignedValues(list1, list2, listOf2);
		
		if (chkMisalignVal.isPossibleMisalignedValues())
		{
			System.out.print(" via Check for possible misaligned values");

			return chkMisalignVal.getMissingValue();
		}
		
		System.out.print(" (no pattern found)");

		return 0;
		
	}
	
	public String computeSolution()
	{
    	ArrayList<String> slotNameList = this.semanticNet.getAllSlotsFromNodes();
    	ArrayList<String> frameLabelList = this.semanticNet.getAllFrameLabels();
    	
    	Node generatedNode = new Node("?");
    	
    	List<String> labels = null;
    	
    	boolean is3x3RPM = this.semanticNet.rpmType.compareTo(Const.RPM_TYPE_3x3) == 0;
		if (is3x3RPM )
		{
			labels = Arrays.asList("A","B","C","D","E","F","G","H");
		} 
		else
		{
			labels = Arrays.asList("A","B","C");				
		}
	    	
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

	    		System.out.print("\nGetting missing value for ["+slotName +"]");
	    		
	    		int missingValue = 0;
	    		if (is3x3RPM)
	    		{
	    			missingValue = getMissing3x3Value(values);
	    		}
	    		else
	    		{
	    			missingValue = getMissing2x2Value(values,slotName);
	    		}
	    		
	    		// need to transpose missingValue where required e.g. for angle > 360
	    		int transposedValue = transpose(slotName, missingValue);
	    		
	    		if (is3x3RPM )
	    		{
	    			System.out.println("\nFrame "+frameLabel+" | "+slotName+"| a:"+values.get(0)+", b:"+values.get(1)+", c:"+values.get(2)+", d:"+values.get(3)+", e:"+values.get(4)+", f:"+values.get(5)+", g:"+values.get(6)+", h:"+values.get(7)+", ?:"+transposedValue);
	    		}
	    		else
	    		{
	    			System.out.println("\nFrame "+frameLabel+" | "+slotName+"| a:"+values.get(0)+", b:"+values.get(1)+", c:"+values.get(2)+", ?:"+transposedValue);	    			
	    		}
  			
		
	    		NameValuePair pair = new NameValuePair(slotName,transposedValue);
	    		generatedFrame.addSlot(pair);
	    		
	    	}
	    		
    		generatedNode.addFrame(generatedFrame);

	    }		
	    	
	    // test generatedNode against the possible solutions 1 - 6
	    System.out.println("\n\nGenerated Figure:\n");
	    generatedNode.printFrames();
	    	
	    String computedAnswer = testGeneratedSolution(generatedNode);
	    return computedAnswer;
	
	}

	
	
	public String testGeneratedSolution(Node generatedNode)
	{
		String currAnswer = null;
		Map<String, VectorExt<Double>> mapOfDistanceVectors = new HashMap<String, VectorExt<Double>>();
					
		for (int i = 1 ; i <= 6 ; i++ )
		{
			Node candidateNode = this.semanticNet.getNode(String.valueOf(i));

			VectorExt<Double> distanceVector = new VectorExt<Double>();
			
			System.out.println("Solution Figure "+candidateNode.getFigureLabel());
			
			for (String currFrameLabel : candidateNode.getFrameListKeys())
			{
				Frame currCandidateFrame = candidateNode.getFrame(currFrameLabel);
				Frame currGeneratedFrame = generatedNode.getFrame(currFrameLabel);
				
				VectorExt<Double> generatedVector = new VectorExt<Double>();
				VectorExt<Double> candidateVector = new VectorExt<Double>();
				
				for (String slotName : currCandidateFrame.getSlotNames())
				{
					generatedVector.add(currGeneratedFrame.getSlot(slotName).doubleValue());
					candidateVector.add(currCandidateFrame.getSlot(slotName).doubleValue());
					
				}
				
				// get cosine difference between vectors
				System.out.println("frame "+currFrameLabel+": generated vector "+ generatedVector);
				System.out.println("frame "+currFrameLabel+": candidate vector "+candidateVector);
				
				Double cosDiff = 0.0;
				
				// if there are no shapes in generated frame then leave cosDiff at 0
				if (currGeneratedFrame.getSlot(Const.Attr.shape_count.toString())>0)
					cosDiff = VectorExt.getCosineSimilarity(generatedVector, candidateVector);
				
				// add cosine diff to distance vector
				distanceVector.add(cosDiff);
				
			}
			// do a special check for a blank figure i.e. no shapes
			setDistanceForBlankFigures(generatedNode, candidateNode, distanceVector);
	
			System.out.println("cos diff vector : "+distanceVector+"\n");
			
			// add distanceVector to map of keyed by candidate solution labels (1 - 6)
			mapOfDistanceVectors.put(candidateNode.getFigureLabel(), distanceVector  );
		}
		
		//System.out.println(mapOfDistanceVectors);
		// find distance vector with smallest difference to unity vector since cosSimilarity(vectorA,vectorA) = 1
		
		// initialize largest Value to small value
		double maxValue = -999.0;
		
		for (Entry<String,VectorExt<Double>> entry : mapOfDistanceVectors.entrySet())
		{
			VectorExt<Double> currDistVector = entry.getValue();
			String currFigureLabel = entry.getKey();
			
			
			Double sumOfVector = currDistVector.getSum();
			
			System.out.println("calculating distance for figure "+currFigureLabel);
			System.out.println("currDistVector :" +currDistVector);

			System.out.println("vector sum :" +sumOfVector+"\n");
			
			System.out.println("max value :" +maxValue+"\n");

			if ( Double.compare(sumOfVector, maxValue) > 0  )
			{
				// return distance vector with max value
				maxValue = sumOfVector;
				currAnswer = currFigureLabel;
				System.out.println("currAnswer changed to :" +currAnswer+"\n");

			}
		}
		System.out.println("answer returned :"+currAnswer);
		return currAnswer;
	}

	public void setDistanceForBlankFigures(Node generatedNode, Node candidateNode, VectorExt<Double> distanceVector)
	{
		if (candidateNode.getFrameListKeys().isEmpty())
		{
			// solution figure is empty - check whether generated figure is comparable
			if (generatedNode.getFrameListKeys().isEmpty())
			{
				distanceVector.add(1.0);
				return;
			}
			else 
			{
				// check whether all frames in generatedNode have shape-count = 0
				for (Frame f : generatedNode.getFrameListValues())
				{
					if (f.getSlot(Const.Attr.shape_count.toString()) != 0 )
					{
						distanceVector.add(0.0);
						return;
					}
				}
				distanceVector.add(1.0);
			}
			
		}
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
