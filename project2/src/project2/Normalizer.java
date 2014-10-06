package project2;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import project2.RuleConstants.SLOT;

public class Normalizer {
	
	private List<Frame> normFrames = new ArrayList<Frame>();
	
	public Normalizer(Frame frame1, Frame frame2)
	{
		normalize(frame1,frame2);
	}
	
	private void normalize(Frame frame1, Frame frame2)
	{
		// set up context
		final Context context = new Context();
		
		if (!frame1.slots.containsKey("angle")) frame1.slots.put("angle", 0);
		if (!frame2.slots.containsKey("angle")) frame2.slots.put("angle", 0);

		for (Entry frameEntry : frame1.slots.entrySet())
		{
			context.set("1_"+frameEntry.getKey().toString(),frameEntry.getValue());
		}

		for (Entry frameEntry : frame2.slots.entrySet())
		{
			context.set("2_"+frameEntry.getKey().toString(),frameEntry.getValue());
		}
	
	
		// which rules to apply
		RavensProductionSystem prodSystem = new RavensProductionSystem(frame1, frame2);
		
		RuleEngine engine = new RuleEngine();
		
		// run the rule engine until no rules fire
		engine.engage(prodSystem.ruleset, context);
		
		// build normalized frames from context
		Frame normFrame1 = new Frame(frame1.getObjectLabel());
		Frame normFrame2 = new Frame(frame2.getObjectLabel());
		
		for (Entry e : context.getEntrySet())
		{
			String[] frameNoAndSlotName = e.getKey().toString().split("_");
			
			String frameNo = frameNoAndSlotName[0];
			String slotName = frameNoAndSlotName[1];
			
			if (isValidNormFrameSlot(slotName))
			{
				if (frameNo.compareTo("1")==0)
				{
					normFrame1.slots.put(slotName, (Integer) e.getValue());
				}
				
				if (frameNo.compareTo("2")==0)
				{
					normFrame2.slots.put(slotName, (Integer) e.getValue());
				}
			}
		}
		
		this.normFrames.add(normFrame1);
		this.normFrames.add(normFrame2);
	}
	
	private boolean isValidNormFrameSlot(String slotName)
	{
		/* filters which slot names will be used in normalized frames
		 * 
		 */
		List<String> normalizedSlots = new ArrayList<String>();
		
		normalizedSlots.add(SLOT.leftOf.str());
		normalizedSlots.add(SLOT.rightOf.str());
		normalizedSlots.add(SLOT.above.str());
		normalizedSlots.add(SLOT.below.str());	
		normalizedSlots.add(SLOT.overlaps.str());
		normalizedSlots.add(SLOT.numSides.str());
		normalizedSlots.add(SLOT.size.str());
		normalizedSlots.add(SLOT.finalAngle.str());
		normalizedSlots.add(SLOT.horizontalFlip.str());
		normalizedSlots.add(SLOT.verticalFlip.str());
		normalizedSlots.add(SLOT.fill.str());
		//normalizedSlots.add(SLOT.shape.str());
		normalizedSlots.add(SLOT.shapeChanged.str());


		
		return normalizedSlots.contains(slotName.trim());

	}
	
	public Frame get(int i)
	{
		
		return this.normFrames.get(i);
	}
}
