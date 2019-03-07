import java.util.EventObject;
public class ButtonEvent extends EventObject {
    private ButtonState _state;
    
    public ButtonEvent( Object source, ButtonState state ) {
        super( source );
        _state = state;
    }
    public ButtonState state() {
        return _state;
    }
    
}