package org.squat_team.vis.export.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Zips files and directories.
 */
public class FileZipper {

	/**
	 * Turns all the files and directories in a specified directory into a zip file.
	 * 
	 * @param parentOfFilesToZip the children of this file/directory will be zipped.
	 * @return the zip file
	 * @throws IOException
	 */
	public File zipChildFiles(File parentOfFilesToZip) throws IOException {
		File zipFile = new File(parentOfFilesToZip.getParent(), parentOfFilesToZip.getName() + ".zip");
		FileOutputStream fos = new FileOutputStream(zipFile);
		ZipOutputStream zipOut = new ZipOutputStream(fos);
		zipChildFiles(parentOfFilesToZip, parentOfFilesToZip.getName(), zipOut);
		zipOut.close();
		fos.close();
		return zipFile;
	}

	private void zipChildFiles(File fileToZip, String fileName, ZipOutputStream zipOut) throws IOException {
		zipFile(fileToZip, fileToZip.getName(), zipOut, true);
	}

	private void zipFile(File fileToZip, String fileName, ZipOutputStream zipOut, boolean initial) throws IOException {
		if (fileToZip.isHidden()) {
			return;
		}
		if (fileToZip.isDirectory()) {
			String prefix = "";
			if (!initial) {
				prefix = fileName + File.separator;
				writeDirectoryToZipOutput(fileName, zipOut);
			}
			writeChildrenToZipOutput(fileToZip, prefix, zipOut);
		} else {
			writeFileToZipOutput(fileToZip, fileName, zipOut);
		}
	}

	private void writeChildrenToZipOutput(File fileToZip, String prefix, ZipOutputStream zipOut) throws IOException {
		File[] children = fileToZip.listFiles();
		for (File childFile : children) {
			zipFile(childFile, prefix + childFile.getName(), zipOut, false);
		}
	}

	private void writeDirectoryToZipOutput(String fileName, ZipOutputStream zipOut) throws IOException {
		if (fileName.endsWith(File.separator)) {
			zipOut.putNextEntry(new ZipEntry(fileName));
			zipOut.closeEntry();
		} else {
			zipOut.putNextEntry(new ZipEntry(fileName + File.separator));
			zipOut.closeEntry();
		}
	}

	private void writeFileToZipOutput(File fileToZip, String fileName, ZipOutputStream zipOut) throws IOException {
		FileInputStream fis = new FileInputStream(fileToZip);
		ZipEntry zipEntry = new ZipEntry(fileName);
		zipOut.putNextEntry(zipEntry);
		byte[] bytes = new byte[1024];
		int length;
		while ((length = fis.read(bytes)) >= 0) {
			zipOut.write(bytes, 0, length);
		}
		fis.close();
	}
}