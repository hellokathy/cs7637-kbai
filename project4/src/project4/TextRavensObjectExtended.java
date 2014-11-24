package project4;
/*
 * DO NOT MODIFY THIS FILE.
 * 
 * Any modifications to this file will not be used when grading your project.
 * If you have any questions, please email the TAs.
 * 
 */


import java.util.ArrayList;

/**
 * A single object in a TextRavensFigure -- typically, a single shape in a frame,
 * such as a triangle or a circle -- comprised of a list of RavensAttributes.
 * 
 */
public class TextRavensObjectExtended {
    private String name;
    private ArrayList<TextRavensAttribute> attributes;
    
    /**
     * Constructs a new TextRavensObjectExtended given a name.
     * 
     * Your agent does not need to use this method.
     * 
     * @param name the name of the object
     */
    public TextRavensObjectExtended(String name) {
        this.name=name;
        attributes=new ArrayList<>();
    }

    /**
     * The name of this TextRavensObjectExtended. Names are assigned starting with the
     * letter Z and proceeding backwards in the alphabet through the objects
     * in the Frame. Names do not imply correspondence between shapes in
     * different frames. Names are simply provided to allow agents to organize
     * their reasoning over different figures.
     * 
     * Within a TextRavensFigure, each TextRavensObjectExtended has a unique name.
     * 
     * @return the name of the TextRavensObjectExtended
     */
    public String getName() {
        return name;
    }
    /**
     * Returns an ArrayList of TextRavensAttribute characterizing this TextRavensObjectExtended.
     * 
     * @return an ArrayList of TextRavensAttribute
     * 
     */
    public ArrayList<TextRavensAttribute> getAttributes() {
        return attributes;
    }
}
