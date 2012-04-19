package ua.com.al1.hexviewer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Main extends JFrame {
	private static final long serialVersionUID = 5476487897463309242L;
	private static Logger log = Logger.getLogger(Main.class.getName());
	private File file;
	private long lastModified;
	private JBinaryViewer binaryViewer = new JBinaryViewer();
	private JScrollPane binaryViewerView;

	private Main(File file) {
		if(file != null && file.exists()) {
			this.file = file;
		}
		
		setTitle("File hex viewer");
		try {
			LogManager.getLogManager().readConfiguration(          
					Main.class.getResourceAsStream("/logging.properties"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.setLevel(Level.OFF);
		setSize(627, 600);
		setResizable(false);
		setDefaultCloseOperation(Main.EXIT_ON_CLOSE);
		createMenu();

		startRefreshThread();
		
		binaryViewer.setData(new byte[]{});
		binaryViewerView = new JScrollPane(binaryViewer);
		binaryViewerView.getVerticalScrollBar().setValue(0);
		setContentPane(binaryViewerView);
	}

	private void startRefreshThread() {
		new Thread(new Runnable() {

			public void run() {
				while (true) {
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					SwingUtilities.invokeLater(new Runnable() {

						public void run() {
							// file = new File(filePath);
							if (file != null
									&& lastModified != file.lastModified()) {
								lastModified = file.lastModified();
								binaryViewer.setData(Tool.readClassFile(file));
								log.info("last update: " + lastModified);
								log.info("last file update: "
										+ file.lastModified());
								log.info("Free memory: "
										+ Runtime.getRuntime().freeMemory());
							}
						}
					});
				}
			}
		}).start();
	}

	private JScrollPane menuFileOpen() {
		FileNameExtensionFilter filterClass = new FileNameExtensionFilter(
				"All File", new String[] { "*" });
		JFileChooser chooser = new JFileChooser();
		chooser.addChoosableFileFilter(filterClass);
		int returnVal = chooser.showOpenDialog(this);

		if (returnVal == 0) {
			file = chooser.getSelectedFile();
			// System.out.println(file.getCanonicalPath());
			// file = new File("/home/al1/text.txt");
			lastModified = file.lastModified();
			final byte[] data = Tool.readClassFile(file);
			binaryViewer.setData(data);

			setVisible(true);
		}
		return binaryViewerView;
	}

	private void createMenu() {
		JMenuBar bar = new JMenuBar();
		JMenuItem mOpen = new JMenuItem("Open");
		mOpen.setMnemonic(79);
		// mOpen.setAccelerator(KeyStroke.getKeyStroke(79, 2));
		setJMenuBar(bar);
		mOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menuFileOpen();
			}
		});
		bar.add(mOpen);

		JMenuItem aboutDialog = new JMenuItem("About");
		aboutDialog.setMnemonic(65);
		aboutDialog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				aboutMenu();
			}
		});
		bar.add(aboutDialog);
	}

	private void aboutMenu() {
		JDialogAbout about = new JDialogAbout(this, "About");
		about.setLocationRelativeTo(this);
		about.setVisible(true);
	}

	public static void main(String[] args) {
		File file = null;
		if(args.length == 1 && !"".equals(args[0])) {
			try {
				file = new File(args[0]);
				if(!file.exists()) {
					throw new FileNotFoundException(args[0]);
				}
			} catch(Exception e) {
				log.log(Level.SEVERE, "Can't read file " + file.getName(), e);
			}
		}
		final File fileArg = file;
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException ex) {
			log.log(Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			log.log(Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			log.log(Level.SEVERE, null, ex);
		} catch (UnsupportedLookAndFeelException ex) {
			log.log(Level.SEVERE, null, ex);
		}

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new Main(fileArg).setVisible(true);
			}
		});
	}
}
