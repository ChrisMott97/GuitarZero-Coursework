package org.gsep;

/**
 * @author  Abigail Lilley
 */
public enum ButtonNames {

    BUTTONNAMES (new String[]{ 	"fret1White",
            "fret1Black",
            "fret2White",
            "fret2Black",
            "fret3White",
            "fret3Black",
            "zeroPower",
            "strumBar",
            "escape",
            "power",
            "bender",
            "whammy"	    	});


    private final String[] names;

    ButtonNames (String[] names) {
        this.names = names;
    }

    public String[] getNames() {
        return names;
    }
}
