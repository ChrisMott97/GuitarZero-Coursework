import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
public class Button {
    private ButtonState _state = ButtonState.OFF;
    private List _listeners = new ArrayList();
	private String _name;
	private int _num;
	
	public Button( String name, int num ) {
		this._name = name;
		this._num  = num;
	}
	
	public String getName() {
		return this._name;
	}
	
	public int getNum() {
		return this._num;
	}
    
    public synchronized void buttonOn() {
            _state = ButtonState.ON;
            _fireMoodEvent();
    }
	
    public synchronized void buttonOff() {
            _state = ButtonState.OFF;
            _fireMoodEvent();
    }
	
    public synchronized void scrollForward() {
            _state = ButtonState.FORWARD;
            _fireMoodEvent();
    }
	
    public synchronized void scrollBackward() {
            _state = ButtonState.BACKWARD;
            _fireMoodEvent();
    }
	
    public synchronized void addButtonListener( ButtonListener l ) {
        _listeners.add( l );
    }
    
    public synchronized void removeButtonListener( ButtonListener l ) {
        _listeners.remove( l );
    }
     
    private synchronized void _fireMoodEvent() {
        //System.out.println("This name: " + this.getName());
		
        ButtonEvent state = new ButtonEvent( this, _state );
        Iterator listeners = _listeners.iterator();
        while( listeners.hasNext() ) {
            ( (ButtonListener) listeners.next() ).stateReceived( this.getName(), state );
        }
    }
}