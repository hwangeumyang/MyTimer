/**
 * 2021-08-23
 * 기존에 있던 타이머를 이용해 새 타이머를 만든다.
 * 우선 50분/10분 간격으로 직무시간, 쉬는시간을 구분하기 위한 것을 알림용도로 쓰기 위해 수정하고,
 * 추후, 현재시간을 표시하고, 소리를 내는 시간을 따로 사용자가 프로그램 내에서 적고, 저장할 수 있게 수정한다. 현재 쓰고 있는 시간마다 반복기능역시 추가한다. 
 */

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import javax.sound.midi.InvalidMidiDataException;
import javax.swing.*;


public class MyNewTimer {
	static SimpleDateFormat timeformat = new SimpleDateFormat("HH:mm:ss");
	
	private JFrame frame;
	private JButton go;
	private JButton stop;
	private JLabel time;
	
	private MyNewTimer.Clock clock;
	
	public MyNewTimer() {
		SoundMaker m;
		frame = new JFrame();
		go = new JButton("START");
		stop = new JButton("STOP");
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
		
		time = new JLabel();
			time.addMouseListener(new PauseListener());

		time.setFont(new Font("dodum", Font.PLAIN, 80));
	
		frame.add(time, BorderLayout.CENTER);
		frame.add(go, BorderLayout.EAST);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 130);
		frame.setVisible(true);
		
	}
	
	public static void main(String [] args) {
		MyNewTimer timer = new MyNewTimer();
		
	}
	
	class PauseListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			

		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	class Clock extends Thread{
		public void run() {
			try {
				long adjustTime = (1000*60*60*15); // for 00:00:00 start. default is 09:00:00 start.
				long start = System.currentTimeMillis()-adjustTime;
				long realtime;
				long displaytime;
				
				
				final long effectTime = Time.sec*10;//갱신
				long ssetime = Time.sec*3+adjustTime;
				long esetime = Time.sec*10+adjustTime;
				
				System.out.println("sse:"+ssetime);
				System.out.println("ese:"+esetime);
				while(true) {
					realtime = System.currentTimeMillis();
					displaytime = realtime-start;
					String display =timeformat.format(realtime-start); 
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
