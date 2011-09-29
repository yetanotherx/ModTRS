package yetanotherx.bukkitplugin.ModTRS.validator;

public abstract class Validator {

    public abstract boolean isValid(String[] args);
    /**
     * Returns true if the string is an integer
     */
    protected boolean isInteger( String arg ) {
	try {
	    Integer.parseInt(arg.replace(",", ""));
	    return true;
	}
	catch( Exception e ) {
	    return false;
	}
    }

    /**
     * Returns true if the string is a number
     */
    protected boolean isNumeric( String arg ) {
	try {
	    Double.parseDouble(arg);
	    return true;
	}
	catch( Exception e ) {
	    return false;
	}
    }

    /**
     * Returns true if a string is at least a length
     */
    protected boolean isAtLeastLength( String str, int length ) {
	return str.length() >= length;
    }

    /**
     * Returns true if there are at least a number of arguments
     */
    protected boolean isAtLeastArgs( String[] args, int length ) {
	return args.length >= length;
    }
    
    /**
     * Returns true if a value exists inside a String[]
     */
    protected boolean inArray(String substring, String[] strings) {
	
	for( String string : strings ) {
	    if( string.equals(substring) ) return true;
	}
	
	return false;
    }


}
