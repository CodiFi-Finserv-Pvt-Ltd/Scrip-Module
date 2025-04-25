package in.codifi.scrips.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Vector;

import javax.enterprise.context.ApplicationScoped;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

@ApplicationScoped
public class SshFileTransferService {

	/**
	 * Method to move list file from directory in one ssh server to another server
	 * 
	 * @author Dinesh Kumar
	 * 
	 * @param sshSourceUserName
	 * @param sshSourceHost
	 * @param sshSourcePort
	 * @param sshSourcePassword
	 * @param sshSourcePath
	 * @param sshDestUserName
	 * @return
	 */
	public boolean transferFiles(String sshSourceUserName, String sshSourceHost, int sshSourcePort,
			String sshSourcePassword, String sshSourcePath, String destPath) {
		boolean rep = false;
		try {
			JSch jsch = new JSch();

			// Connect to the source server
			Session sourceSession = jsch.getSession(sshSourceUserName, sshSourceHost, sshSourcePort);
			sourceSession.setPassword(sshSourcePassword);
			sourceSession.setConfig("StrictHostKeyChecking", "no");
			sourceSession.connect();

			ChannelSftp sourceChannel = (ChannelSftp) sourceSession.openChannel("sftp");
			sourceChannel.connect();

			// List files in the source directory
			Vector<ChannelSftp.LsEntry> files = sourceChannel.ls(sshSourcePath);

			// Temporary folder to store files locally
			File tempDir = new File("temp");
			if (!tempDir.exists()) {
				tempDir.mkdirs();
			}

			for (ChannelSftp.LsEntry file : files) {
				if (!file.getAttrs().isDir()) {
					String fileName = file.getFilename();
					File localFile = new File(tempDir, fileName);

					try (FileOutputStream fos = new FileOutputStream(localFile)) {
						sourceChannel.get(sshSourcePath + fileName, fos);
					}
				}
			}
			sourceChannel.disconnect();
			sourceSession.disconnect();

//			// Connect to the destination server
//			Session destSession = jsch.getSession(sshDestUserName, sshDesteHost, sshDestPort);
//			destSession.setPassword(sshDestPassword);
//			destSession.setConfig("StrictHostKeyChecking", "no");
//			destSession.connect();
//
//			ChannelSftp destChannel = (ChannelSftp) destSession.openChannel("sftp");
//			destChannel.connect();
//			
			File destPathDir = new File(destPath);
			if (!destPathDir.exists()) {
				destPathDir.mkdirs();
			}
//
//			// Upload files to the destination directory
			for (File file : tempDir.listFiles()) {
//				try (FileInputStream fis = new FileInputStream(file)) {
				try (FileInputStream fis = new FileInputStream(file);
						FileOutputStream fos = new FileOutputStream(new File(destPath + file.getName()))) {
					byte[] buffer = new byte[1024];
					int length;
					while ((length = fis.read(buffer)) > 0) {
						fos.write(buffer, 0, length);
					}
					file.delete();
				}
				// Clean up local temporary file
			}
			tempDir.delete();
//
//			destChannel.disconnect();
//			destSession.disconnect();
			rep = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rep;
	}

}
