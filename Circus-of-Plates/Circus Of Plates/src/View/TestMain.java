package View;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TestMain {
	public static void main(String[] args) {
		MyView m = MyView.getInstance();
		m.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		Thread myview = new Thread(m);
		for (int i = 0; i < 10000; i++) {
			myview.run();
			m.setVisible(true);
			try {
				myview.sleep(m.getDifficultyValue());
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	
	}
}
