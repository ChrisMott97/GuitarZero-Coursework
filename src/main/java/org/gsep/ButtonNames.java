package org.gsep;

import java.util.ArrayList;

public enum ButtonNames {
//     	FRETONEWHITE ( "fret1White" ),
//        FRETONEBLACK ( "fret1Black" ),
//        FRETTWOWHITE ( "fret2White" ),
//        FRETTWOBLACK ( "fret2Black" ),
//        FRETTHREEWHITE ( "fret3White" ),
//        FRETTHREEBLACK ( "fret3Black" ),
//        ZEROPOWER ( "zeroPower" ),
//        STRUMBAR ( "strumBar" ),
//        ESCAPE ( "escape" ),
//        POWER ( "power" ),
//        BENDER ( "bender" ),
//        WHAMMY ( "whammy" );

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
