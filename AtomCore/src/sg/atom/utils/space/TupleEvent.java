package sg.atom.utils.space;

import java.util.List;

import sg.atom.utils.space.interfaces.ITuple;

public class TupleEvent {
	
	private List<ITuple> eventContents;
	
	public TupleEvent(List<ITuple> l){
		eventContents = l;
	}

	public List<ITuple> getContents(){
		return eventContents;
	}
}
