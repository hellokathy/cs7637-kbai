package package1;

public enum FIGURE_LABEL 
{
    A("A"), 
    B("B"),
    C("C"), 
    D("D"), 
    E("E"), 
    F("F"), 
    G("G"), 
    H("H"), 
    D1("1"), 
    D2("2"),
    D3("3"),
    D4("4"),
    D5("5"),
    D6("6");
   
    private String value;

    private FIGURE_LABEL(String _value) {
            this.value = _value;
    }
    
    public String getValue()
    {
    	return value;
    }
};  





