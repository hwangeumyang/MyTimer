import javax.sound.midi.*;
public class SSESequence{
	private Sequence SSESequence;
	public SSESequence() throws InvalidMidiDataException {
		SSESequence = new Sequence(Sequence.PPQ, 4);
		
		Track SSEt = SSESequence.createTrack();
		

	}
	
	public static Sequence getSSESequence() {
		Sequence output = null;
		
		return output;		
	}
}
