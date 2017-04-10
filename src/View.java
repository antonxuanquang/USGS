import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class View extends JFrame {
	JLabel lb;
	JTextArea report;
	JButton chooseFile;
	JButton run;
	JScrollPane sp;

	public View() {
		setLayout(new FlowLayout());
		lb = new JLabel("Choose Site File:");
		chooseFile = new JButton("Choose File");
		run = new JButton("Run");
		run.setEnabled(false);
		report = new JTextArea(20, 30);
		sp = new JScrollPane(report);
		add(lb);
		add(chooseFile);
		add(run);
		add(sp);
		setSize(400, 500);
		setVisible(true);
	}
}