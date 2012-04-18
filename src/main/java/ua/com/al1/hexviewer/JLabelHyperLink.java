package ua.com.al1.hexviewer;

import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;

public class JLabelHyperLink extends JLabel {
	private static final long serialVersionUID = 4876543219876500000L;
	private final String text;
	private final String url;
	private boolean isSupported;

	public JLabelHyperLink(String text, String url) {
		this.text = text;
		this.url = url;
		try {
			this.isSupported = ((Desktop.isDesktopSupported()) && (Desktop
					.getDesktop().isSupported(Desktop.Action.BROWSE)));
		} catch (Exception e) {
			this.isSupported = false;
		}

		setText(false);
		addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				JLabelHyperLink.this.setText(JLabelHyperLink.this.isSupported);
				if (JLabelHyperLink.this.isSupported)
					JLabelHyperLink.this.setCursor(new Cursor(12));
			}

			public void mouseExited(MouseEvent e) {
				JLabelHyperLink.this.setText(false);
			}

			public void mouseClicked(MouseEvent e) {
				try {
					Desktop.getDesktop().browse(
							new URI(JLabelHyperLink.this.url));
				} catch (Exception ex) {
					Logger.getLogger(JLabelHyperLink.class.getName()).log(
							Level.SEVERE, null, ex);
				}
			}
		});
	}

	private void setText(boolean b) {
		if (!b)
			setText("<html><font color=blue><u>" + this.text);
		else
			setText("<html><font color=red><u>" + this.text);
	}
}