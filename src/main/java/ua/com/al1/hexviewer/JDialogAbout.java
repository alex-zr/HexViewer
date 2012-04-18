package ua.com.al1.hexviewer;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class JDialogAbout extends JDialog {
	private static final long serialVersionUID = 4876543219876500000L;

	public JDialogAbout(Frame owner, String title) {
		super(owner, title);
		setDefaultCloseOperation(2);
		setModal(true);

		initComponents();
		pack();
		setResizable(false);
	}

	private void initComponents() {
		setLayout(new BorderLayout());

		JButton buttonClose = new JButton("Close");
		buttonClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JDialogAbout.this.buttonOK_Clicked();
			}
		});
		JPanel listPane = new JPanel();
		listPane.setLayout(new BoxLayout(listPane, 3));

		JLabel label = new JLabel("Hex Fle Viewer");
		label.setFont(new Font("Dialog", 1, 20));
		listPane.add(label);
		listPane.add(Box.createRigidArea(new Dimension(0, 5)));
		listPane.add(new JLabel("Version: 1.0.0"));
		listPane.add(Box.createRigidArea(new Dimension(0, 5)));
		listPane.add(new JLabel("Author: Roshchupkin Alexandr"));
		listPane.add(Box.createRigidArea(new Dimension(0, 20)));
		listPane.add(new JLabelHyperLink("http://www.al1.com.ua/",
				"http://www.al1.com.ua/"));

		listPane.add(Box.createRigidArea(new Dimension(0, 5)));
		listPane.add(new JLabel("Free Tools to explore file content"));
		listPane.add(Box.createRigidArea(new Dimension(0, 5)));
		listPane.add(new JLabel("Based on: Java Class File Library, 2.0.161"));
		listPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new BoxLayout(buttonPane, 2));
		buttonPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
		buttonPane.add(Box.createHorizontalGlue());
		buttonPane.add(buttonClose);

		Container contentPane = getContentPane();
		contentPane.add(listPane, "Center");
		contentPane.add(buttonPane, "South");
	}

	private void buttonOK_Clicked() {
		setVisible(false);
	}
}