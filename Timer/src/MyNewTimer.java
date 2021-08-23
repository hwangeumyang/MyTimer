/**
 * 2021-08-23
 * ������ �ִ� Ÿ�̸Ӹ� �̿��� �� Ÿ�̸Ӹ� �����.
 * �켱 50��/10�� �������� �����ð�, ���½ð��� �����ϱ� ���� ���� �˸��뵵�� ���� ���� �����ϰ�,
 * ����, ����ð��� ǥ���ϰ�, �Ҹ��� ���� �ð��� ���� ����ڰ� ���α׷� ������ ����, ������ �� �ְ� �����Ѵ�. ���� ���� �ִ� �ð����� �ݺ���ɿ��� �߰��Ѵ�. 
 * 
 * 
 * 
 */

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.sound.midi.InvalidMidiDataException;
import javax.swing.*;


public class MyNewTimer {
	static SimpleDateFormat timeformat = new SimpleDateFormat("HH:mm:ss");
	
	private JFrame frame;
	private JButton go;
	private JButton stop;
	private JButton pause;
	private JLabel time;
	
	private MyNewTimer.Clock clock;
	
	public MyNewTimer() {
		SoundMaker m;
		frame = new JFrame();
		go = new JButton("START");
		stop = new JButton("STOP");
//		pause = new JButton("Pause");
		
			go.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if(clock == null) clock = new Clock();
					clock.start();
					frame.remove(go);
					frame.add(stop, BorderLayout.EAST);
					frame.repaint();
				}
			});
			stop.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					clock.interrupt();
					clock = null;
					
					frame.remove(stop);
					frame.add(go, BorderLayout.EAST);
					frame.repaint();
				}
			});
//			pause.addActionListener(new ActionListener() {
//				@Override
//				public void actionPerformed(ActionEvent e) {
//					clock.interrupt();
//					clock = null;
//					
//					frame.remove(pause);
//					frame.add(go, BorderLayout.EAST);
//					frame.repaint();
//				}
//			});
		
		time = new JLabel();

		time.setFont(new Font("dodum", Font.PLAIN, 80));
	
		frame.add(time, BorderLayout.CENTER);
		frame.add(go, BorderLayout.EAST);
//		frame.add(pause, BorderLayout.EAST);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 130);
		frame.setVisible(true);
		
	}
	
	public static void main(String [] args) {
		
		long durationInMillis = System.currentTimeMillis();
		long millis = durationInMillis % 1000;
		long second = (durationInMillis / 1000) % 60;
		long minute = (durationInMillis / (1000 * 60)) % 60;
		long hour = (durationInMillis / (1000 * 60 * 60)) % 24;
		
		System.out.println(System.currentTimeMillis());
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(System.currentTimeMillis());
		

		String time = String.format("%02d:%02d:%02d.%d", hour, minute, second, millis);
		System.out.println(time);
		@SuppressWarnings("unused")
		MyNewTimer timer = new MyNewTimer();
	}
	class Clock extends Thread{
		public void run() {
			try {
				long adjustTime = 0; // for 00:00:00 start. default is 09:00:00 start.
				long start = System.currentTimeMillis()-adjustTime;
				long realtime;
				long displaytime;
				
				
				final long effectTime = Time.hour*1;//����
				long ssetime = Time.min*0+adjustTime;
				long esetime = Time.min*50+adjustTime;
				
				System.out.println("sse:"+ssetime);
				System.out.println("ese:"+esetime);
				while(true) {
					realtime = System.currentTimeMillis();
					displaytime = realtime;
					String display =timeformat.format(realtime); 
					time.setText(display);
					
					System.out.println(start);
					System.out.println(realtime);
					System.out.println(displaytime);
					
					if(displaytime>ssetime) {
						ssetime+=effectTime;
						new Thread(new Runnable() {
							@Override
							public void run() {
								SoundMaker maker = new SoundMaker();
								try {
									maker.playSSE();
								} catch (InvalidMidiDataException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}).run();
						System.out.println("start!");
					}
					if(displaytime>esetime) {
						esetime+=effectTime;
						System.out.println("end!");
						new Thread(new Runnable() {
							@Override
							public void run() {
								SoundMaker maker = new SoundMaker();
								try {
									maker.playESE();
									
								} catch (InvalidMidiDataException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}).run();
					}
					
					Thread.sleep(1000);
				}
			} catch(InterruptedException e) {
				System.err.println("clock closed");
			}
			
		}
	}

}
