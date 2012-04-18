package ua.com.al1.hexviewer;

import java.awt.Dimension;
import java.awt.event.MouseListener;
import javax.swing.JTextArea;

class JRowViewer extends JTextArea {
	private static final long serialVersionUID = 4876543219876500000L;
	protected static final int width = 75;

	public JRowViewer() {
		setFont(JBinaryViewer.font);

		setAlignmentX(0.0F);
		setMinimumSize(new Dimension(270, 32767));
		setMaximumSize(new Dimension(270, 32767));

		setEditable(false);
		filterMouseEvents();
	}

	protected void setData(int rowCount, int rowItemMax) {
		int rowStartValue = 0;
		removeAll();
		for (int i = 0; i < rowCount; i++) {
			append(String.format("%08Xh\n",
					new Object[] { Integer.valueOf(rowStartValue) }));
			rowStartValue += rowItemMax;
		}

		Dimension d = new Dimension(75, rowCount * 20);

		setMinimumSize(d);
		setMaximumSize(d);
	}

	private void filterMouseEvents() {
		MouseListener[] allMl = getMouseListeners();
		if (allMl != null)
			for (int i = 0; i < allMl.length; i++)
				removeMouseListener(allMl[i]);
	}
}