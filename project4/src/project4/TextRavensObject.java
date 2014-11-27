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
public class TextRavensObject {
    private String name;
    private ArrayList<TextRavensAttribute> attributes;
    
    /**
     * Constructs a new TextRavensObject given a name.
     * 
     * Your agent does not need to use this method.
     * 
     * @param name the name of the object
     */
    public TextRavensObject(String name) {
        this.name=name;
        attributes=new ArrayList<>();
    }

    /**
     * The name of this TextRavensObject. 
     * Names do not imply correspondence between shapes in
     * different frames. Names are simply provided to allow agents to organize
     * their reasoning over different figures.
     * 
     * Within a TextRavensFigure, each TextRavensObject has a unique name.
     * 
     * @return the name of the TextRavensObject
     */
    public String getName() {
        return name;
    }
    /**
     * Returns an ArrayList of TextRavensAttribute characterizing this TextRavensObject.
     * 
     * @return an ArrayList of TextRavensAttribute
     * 
     */
    public ArrayList<TextRavensAttribute> getAttributes() {
        return attributes;
    }
}
