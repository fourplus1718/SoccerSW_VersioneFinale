package entries;

/**
 * PJDCC - Summary for class responsabilities.
 *
 * @author fourplus <fourplus1718@gmail.com>
 * @since 1.0
 * @version 11 Changes done
 */

public class AllEntry {
    /**
     * This field is a variable of class final entry
     */
	public FinalEntry fe;
	
	/**
     * This field is a variable of class final entry
     */
	public float zero;
	
	/**
     * This field is a variable of class final entry
     */
	public float one;
	
	/**
     * This field is a variable of class final entry
     */
	public float two;
	
	/**
     * This field is a variable of class final entry
     */
	public float more;
	
	/**
     * This field is a variable of class final entry
     */
	public float basic;
	
	/**
     * This field is a variable of class final entry
     */
	public float poisson;
	
	/**
     * This field is a variable of class final entry
     */
	public float weighted;
	
	/**
     * This field is a variable of class final entry
     */
	public float shots;

	public AllEntry(FinalEntry fe, float zero, float one, float two, float more, float basic, float poisson,
			float weighted, float shots) {
		super();
		this.fe = fe;
		this.zero = zero;
		this.one = one;
		this.two = two;
		this.more = more;
		this.basic = basic;
		this.poisson = poisson;
		this.weighted = weighted;
		this.shots = shots;
	}


}
