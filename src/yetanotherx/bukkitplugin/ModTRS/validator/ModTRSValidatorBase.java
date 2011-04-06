package yetanotherx.bukkitplugin.ModTRS.validator;

public class ModTRSValidatorBase {

    protected boolean isInteger( String arg ) {
	try {
	    Integer.parseInt(arg);
	    return true;
	}
	catch( Exception e ) {
	    return false;
	}
    }

    protected boolean isNumeric( String arg ) {
	try {
	    Double.parseDouble(arg);
	    return true;
	}
	catch( Exception e ) {
	    return false;
	}
    }

    protected boolean isAtLeastLength( String str, int length ) {
	return str.length() >= length;
    }

    protected boolean isAtLeastArgs( String[] args, int length ) {
	return args.length >= length;
    }
    
    protected boolean inArray(String substring, String[] strings) {
	
	for( String string : strings ) {
	    if( string.equals(substring) ) return true;
	}
	
	return false;
    }


}
