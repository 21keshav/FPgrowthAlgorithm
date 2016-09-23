/**
 * 
 */
public class FPobject {
	
	protected String attribute;
	protected int count;
	protected int prority = 0;
	protected int totalcount = 0;
	
	public FPobject(String attribute, int count) {
		
		this.attribute = attribute;
		this.count = count;
		
	}
    
	public FPobject(String attribute, int count, int c) {
		
		this.attribute = attribute;
		this.count = count;
		this.totalcount = c;
	}
}
