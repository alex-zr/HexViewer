package ua.com.al1.hexviewer;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JViewport;

public class JBinaryViewer extends JPanel {
	private static final long serialVersionUID = 4876543219876500005L;
	protected static final Font font = new Font("DialogInput", 0, 14);
	protected static final int ITEM_WIDTH = 8;
	protected static final int ITEM_WIDTH_HALF = 4;
	protected static final int DATA_ITEM_WIDTH = 24;
	protected static final int ITEM_HEIGHT = 20;
	protected static final int ROW_ITEM_MAX = 16;
	private byte[] data = null;
	private final JRowViewer rowViewer;
	private final JRawDataViewer rawViewer;
	private final JAsciiDataViewer asciiViewer;

	public JBinaryViewer() {
		setFont(font);

		this.rowViewer = new JRowViewer();
		this.rawViewer = new JRawDataViewer();
		this.asciiViewer = new JAsciiDataViewer();

		setLayout(new BoxLayout(this, 0));
		add(this.rowViewer);
		add(Box.createRigidArea(new Dimension(5, 32767)));
		add(this.rawViewer);
		add(Box.createRigidArea(new Dimension(5, 32767)));
		add(this.asciiViewer);
	}

	public void setData(byte[] data) {
		if (data == null || data.length == 0) {
			this.rowViewer.setText("");
			this.rawViewer.setText("");
			this.asciiViewer.setText("");
			return;
		}

		this.data = ((byte[]) data.clone());

		int rowCount = getRowCount();
		this.rowViewer.setData(rowCount, 16);
		this.rawViewer.setData(this.data, rowCount);
		this.asciiViewer.setData(this.data, rowCount);
	}

	private int getRowCount() {
		int rowCount = 0;

		if (this.data != null) {
			int dataLength = this.data.length;
			while (dataLength > 0) {
				rowCount++;
				dataLength -= 16;
			}
		}

		return rowCount;
	}

	public Dimension getPreferredSize() {
		int height = getRowCount() * 20;
		height = height > 600 ? height : 600;

		return new Dimension(627, height);
	}

	public void setSelection(int selectionStart, int length) {
		if ((this.data == null) || (selectionStart < 0)) {
			return;
		}
		if (this.data.length < selectionStart + length - 1) {
			return;
		}

		ensureVisible(selectionStart);

		this.rawViewer.setSelection(selectionStart, length);
		this.asciiViewer.setSelection(selectionStart, length);
	}

	private void ensureVisible(int startPos) {
		if ((this.data == null) || (startPos < 0)
				|| (this.data.length < startPos - 1)) {
			return;
		}

		Container objJViewport = getParent();
		int pos = startPos;
		if ((objJViewport instanceof JViewport)) {
			JViewport jvp = (JViewport) objJViewport;
			Rectangle rect = jvp.getViewRect();

			int newValue = 0;
			while (pos > 16) {
				newValue += 20;
				pos -= 16;
			}

			if (!rect.contains(1, newValue)) {
				Container objJScrollPane = objJViewport.getParent();
				if ((objJScrollPane instanceof JScrollPane)) {
					JScrollPane jsp = (JScrollPane) objJScrollPane;
					JScrollBar jsb = jsp.getVerticalScrollBar();
					jsb.setValue(newValue);
				}
			}
		}
	}
}