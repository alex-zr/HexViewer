package ua.com.al1.hexviewer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public final class Tool {
	public static byte[] readClassFile(ZipFile zipFile, ZipEntry zipEntry)
			throws IOException {
		if (zipFile == null) {
			throw new IllegalArgumentException("Parameter 'zipFile' is null.");
		}
		if (zipEntry == null) {
			throw new IllegalArgumentException("Parameter 'zipEntry' is null.");
		}

		long fileSize = zipEntry.getSize();
		byte[] contents = new byte[(int) fileSize];
		ByteBuffer byteBuf = ByteBuffer.allocate(contents.length);
		InputStream is = null;
		int bytesRead = 0;
		int bytesAll = 0;
		try {
			is = zipFile.getInputStream(zipEntry);
			while (true) {
				bytesRead = is.read(contents);
				if (bytesRead == -1)
					break;
				byteBuf.put(contents, 0, bytesRead);
				bytesAll += bytesRead;
			}

		} catch (IOException ex) {
			Logger.getLogger(Tool.class.getName()).log(Level.SEVERE, null, ex);
			throw ex;
		}

		if (bytesAll == fileSize) {
			return byteBuf.array();
		}
		throw new IOException(
				String.format(
						"File read error: expected = %d bytes, result = %d bytes.\nzipFile = %s\nzipEntry = %s",
						new Object[] { Long.valueOf(fileSize),
								Integer.valueOf(byteBuf.array().length),
								zipFile.getName(), zipEntry.getName() }));
	}

	public static byte[] readClassFile(File file) {
		byte[] contents = null;

		FileInputStream input = null;

		long fileLength = file.length();
		int fileLengthInt = (int) (fileLength % 2147483647L);

		contents = new byte[fileLengthInt];
		ByteBuffer byteBuf = ByteBuffer.allocate(contents.length);

		int bytesAll = 0;
		try {
			input = new FileInputStream(file);
			while (true) {
				int bytesRead = input.read(contents);
				if (bytesRead == -1)
					break;
				byteBuf.put(contents, 0, bytesRead);
				bytesAll += bytesRead;
			}

		} catch (FileNotFoundException ex) {
			Logger.getLogger(Tool.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			Logger.getLogger(Tool.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			try {
				if (input != null)
					input.close();
			} catch (IOException ex) {
				Logger.getLogger(Tool.class.getName()).log(Level.SEVERE, null,
						ex);
			}
		}

		if (bytesAll == fileLengthInt) {
			return byteBuf.array();
		}
		return null;
	}

	public static String getByteDataHexView(byte[] data) {
		if (data == null) {
			return "";
		}
		if (data.length < 1) {
			return "";
		}

		StringBuilder sb = new StringBuilder(data.length * 5);
		int length = data.length;

		int lineBreakCounter = 0;
		for (int i = 0; i < length; i++) {
			sb.append(String.format(" %02X",
					new Object[] { Byte.valueOf(data[i]) }));
			lineBreakCounter++;
			if (lineBreakCounter == 16) {
				sb.append('\n');
				lineBreakCounter = 0;
			}
		}
		sb.append('\n');

		return sb.toString();
	}
}