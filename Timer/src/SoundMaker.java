import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

public class SoundMaker {
	private static Sequencer sequencer;
	Sequence sSE;//start sound effect
	Sequence eSE;//end SE
	
	SoundMaker(){
		try {
			sequencer = MidiSystem.getSequencer();
			sSE = new Sequence(Sequence.PPQ, 4);
			eSE = new Sequence(Sequence.PPQ, 4);
			
			@SuppressWarnings("unused")
			Track sSEtrack = getSSETrack(sSE);
			@SuppressWarnings("unused")
			Track eSEtrack = getESETrack(eSE);

		
		} catch(MidiUnavailableException e) {
			System.err.println("failed:getSequencer()");
			e.printStackTrace();
		} catch (InvalidMidiDataException e) {
			// TODO Auto-generated catch block
			System.err.println("effect create failed:");
			e.printStackTrace();
		}
	}
	public static void main(String [] args) {
//		new MyProtoMidiPlayer1();
		SoundMaker maker = new SoundMaker();
		try {
			maker.playSSE();
//			maker.playSSE();
			maker.playESE();
			
//			System.exit(1);;
			
		} catch(Exception e ) {
			e.printStackTrace();
		}
	}
	public void playSSE() throws InvalidMidiDataException{
		play(sSE);
	}
	public void playESE() throws InvalidMidiDataException{
		play(eSE);
	}
	private void play(Sequence sequence) throws InvalidMidiDataException {
		try {
			sequencer.open();
			sequencer.setSequence(sequence);
			sequencer.setTempoInBPM(220);
			sequencer.start();
			
//			while(true) {
//				if(!sequencer.isRunning()) {
//					System.out.println("hello");
//					sequencer.close();
//					break;
//				}
//			}
			while(sequencer.isRunning());
			sequencer.close();

		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	private Track getSSETrack(Sequence sSE) {
		Track track = null;
		
		track = sSE.createTrack();
		
		track.add(makeEvent(144, 1, 70, 100, 0));
		track.add(makeEvent(128, 1, 70, 100, 3));	
		//마지막음이 들리기전에 정지하기때문에 한음 더 넣음
		track.add(makeEvent(144, 1, 70, 100, 4));
		track.add(makeEvent(128, 1, 70, 100, 8));	
		return track;		
	}
	private Track getESETrack(Sequence sSE) {
		Track track = null;
		
		track = sSE.createTrack();
		
		track.add(makeEvent(144, 1, 40, 100, 0));
		track.add(makeEvent(128, 1, 40, 100, 3));
		//정지전 한음넣기.
		track.add(makeEvent(144, 1, 40, 100, 4));
		track.add(makeEvent(128, 1, 40, 100, 6));
		
		return track;		
	}	
	public MidiEvent makeEvent(int command, int channel, int note, int velocity, int tick) {
		MidiEvent event = null;
		
		try {
			
			ShortMessage a = new ShortMessage();
			a.setMessage(command, channel, note, velocity);
			
			event = new MidiEvent(a, tick);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return event;
	}
}

class MyProtoMidiPlayer1{
	public MyProtoMidiPlayer1(){
		int numOfNotes = 5;
		
		setUpPlayer(numOfNotes);
	}
	
	public void setUpPlayer(int numOfNotes) {
		try {
			Sequencer seq = MidiSystem.getSequencer();
			seq.open();
			
			Sequence sequence = new Sequence(Sequence.PPQ, 4);
			
			Track track = sequence.createTrack();
			Track track2 = sequence.createTrack();
			
			for(int i=5; i<(4*numOfNotes)+5; i+=4 ) {
				track.add(makeEvent(144, 1, 70, 100, i));
				track.add(makeEvent(128, 1, 30, 100, i+2));
			}
			{
				track2.add(makeEvent(144, 1, 30, 100, 5));
				track2.add(makeEvent(128, 1, 30, 100, 5+2));
			}
			seq.setSequence(sequence);
			seq.setTempoInBPM(220);
			seq.start();
			
			
			
			while(true) {
				if(!seq.isRunning()) {

					Thread.sleep(1000);
//					Thread.sleep(5000);

					System.out.println(sequence);
					seq.close();
					seq.open();
					seq.setSequence(sequence);
					seq.setTempoInBPM(220);

					seq.start();

					Thread.sleep(1000);
					System.exit(1);
				}
			}
			
		} catch(Exception e) {
			e.printStackTrace();
			
		}
	}
	
	public MidiEvent makeEvent(int command, int channel, int note, int velocity, int tick) {
		MidiEvent event = null;
		
		try {
			
			ShortMessage a = new ShortMessage();
			a.setMessage(command, channel, note, velocity);
			
			event = new MidiEvent(a, tick);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return event;
	}
}