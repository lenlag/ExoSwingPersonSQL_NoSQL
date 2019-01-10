package manager;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

public class CfgManager {

	String[] services;
	String filePathCfg = "src/cfg/cfg.ini";
	String filePathService = "src/cfg/service.cfg";

	public CfgManager() {
	}

	public String[] getServices() {
		Integer index = 0;
		int i = 0;
		String rawString = readFile(filePathCfg);
		String[] rawLines = rawString.split("\r\n");

		// String[] lines = new String[rawLines.length];

		List<String[]> lines = new ArrayList<>();

		this.services = new String[rawLines.length];

		for (String raw : rawLines) {
			lines.add(raw.split(","));
		}
		for (String[] strings : lines) {
			for (String string : strings) {

				if (index % 2 == 1) {
					services[i] = string.trim();
					i++;
				}
				index++;
			}
		}
		return services;
	}

	public void saveSelectedService(String serviceName) throws IOException {
		RandomAccessFile raf = new RandomAccessFile(filePathService , "rw");
		FileOutputStream fileOutputStream = new FileOutputStream(filePathService);
		raf.setLength(0);
		String text = "class="+serviceName;
		fileOutputStream.write(text.getBytes());
		raf.close();
		closeWriteFile(fileOutputStream);

	}

	private static String readFile(String fileRead) {
		byte[] bytes = new byte[1024];
		int offset = 0;
		int intByteLength = offset;
		String text = "";
		FileInputStream fileInputStream = null;

		try {
			fileInputStream = new FileInputStream(fileRead);
			// System.out.println("Read File Imported");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			closeWriteFile(fileInputStream);
		}

		try {
			do {
				intByteLength = fileInputStream.read(bytes);

				if (intByteLength != -1) {
					text += new String(bytes, offset, intByteLength);

				}
			} while (intByteLength != -1);

			// System.out.println("Read File readed");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			closeWriteFile(fileInputStream);
		}
		return text;
	}

	private static void closeWriteFile(FileInputStream fileInputStream) {
		try {
			fileInputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void closeWriteFile(FileOutputStream fileOutputStream) {
		try {
			fileOutputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
