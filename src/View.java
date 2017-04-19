import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class View extends JFrame {
	JLabel lbDescription;
	JLabel lbData;
	JLabel site1;
	JLabel site2;
	JTextArea report;
	JTextField firstPartDescription;
	JTextField secondPartDescription;
	JButton chooseFile;
	JTextField firstPartData;
	JTextField secondPartData;
	JButton run;
	JScrollPane sp;

	public View() {
		setLayout(new FlowLayout());
		lbDescription = new JLabel("Description URL:");
		site1 = new JLabel(" + ...site... + ");
		site2 = new JLabel(" + ...site... + ");
		lbData = new JLabel("Data URL:");
		chooseFile = new JButton("Choose Site File");
		firstPartDescription = new JTextField(20);
		secondPartDescription = new JTextField(40);

		firstPartData = new JTextField(20);
		secondPartData = new JTextField(40);
		run = new JButton("Run");
		run.setEnabled(false);
		report = new JTextArea(20, 60);
		sp = new JScrollPane(report);
		
		
		add(lbDescription);
		add(firstPartDescription);
		add(site1);
		add(secondPartDescription);
		
		add(lbData);
		add(firstPartData);
		add(site2);
		add(secondPartData);
		add(chooseFile);
		add(run);
		add(sp);
		setSize(900, 500);
		setVisible(true);
	}
}