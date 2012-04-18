package ua.com.al1.hexviewer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JTextArea;

class JAsciiDataViewer extends JTextArea {
	private static final long serialVersionUID = 4876543219876500004L;
	protected static final int width = 130;
	private int selectedStartIndex = 0;
	private int seelctedLength = 0;

	public JAsciiDataViewer() {
		setFont(JBinaryViewer.font);
		setEditable(false);
	}

	protected void setData(byte[] data, int rowCount) {
		removeAll();
		setText("");

		if (data == null) {
			return;
		}

		int dataLength = data.length;
		int breakCounter = 0;
		for (int i = 0; i < dataLength; i++) {
			append(getAsciiStringValue(data[i]));
			breakCounter++;

			if (breakCounter > 15) {
				append("\n");
				breakCounter = 0;
			}

		}

		Dimension d = new Dimension(130, rowCount * 20);

		setMinimumSize(d);
		setMaximumSize(d);
	}

	private String getAsciiStringValue(byte b) {
		String s = ".";

		if (((b > 32) && (b < 127)) || ((b > 146) && (b < 165))) {
			s = new Character((char) b).toString();
		}

		return s;
	}

	public void setSelection(int startIndex, int length) {
		this.selectedStartIndex = startIndex;
		this.seelctedLength = length;

		repaint();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		if ((this.seelctedLength < 1) || (this.selectedStartIndex < 0)) {
			return;
		}

		int endIndex = this.selectedStartIndex + this.seelctedLength;

		for (int i = this.selectedStartIndex; i < endIndex; i++) {
			int column = i;
			int row = 0;
			while (column >= 16) {
				column -= 16;
				row += 1;
			}

			paintDataRect(g, row, column);
		}
	}

	private void paintDataRect(Graphics g, int row, int column) {
		if ((row > -1) && (column > -1)) {
			g.setColor(Color.ORANGE);
			g.drawRect(8 * column, 20 * row, 8, 20);
		}
	}
}