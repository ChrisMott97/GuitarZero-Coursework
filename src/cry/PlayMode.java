public class PlayMode implements ButtonListener {
    public void stateReceived(String buttonName, ButtonEvent event) {
		//System.out.println(buttonName);
        if( event.state() == ButtonState.ON )
        {
            System.out.println( buttonName + " is ON" );
        }
        else if( event.state() == ButtonState.OFF )
        {
            System.out.println( buttonName + " is OFF" );
        }
        else
        {
            System.out.println( "Something went wrong" );
        }
    }
}    