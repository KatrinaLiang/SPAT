class n16475859 {
	public void ftpUpload() {
		FTPClient WNo2tvU5 = null;
		InputStream ziCpEpkI = null;
		try {
			WNo2tvU5 = new FTPClient();
			WNo2tvU5.connect(host, port);
			if (logger.isDebugEnabled()) {
				logger.debug("FTP����Զ�̷�������" + host);
			}
			WNo2tvU5.login(user, password);
			if (logger.isDebugEnabled()) {
				logger.debug("��½�û���" + user);
			}
			WNo2tvU5.setFileType(FTP.BINARY_FILE_TYPE);
			WNo2tvU5.changeWorkingDirectory(remotePath);
			ziCpEpkI = new FileInputStream(localPath + File.separator + filename);
			WNo2tvU5.storeFile(filename, ziCpEpkI);
			logger.info("�ϴ��ļ�����...·����" + remotePath + "���ļ�����" + filename);
			ziCpEpkI.close();
			WNo2tvU5.logout();
		} catch (IOException pndP7cUy) {
			logger.error("�ϴ��ļ�ʧ��", pndP7cUy);
		} finally {
			if (WNo2tvU5.isConnected()) {
				try {
					WNo2tvU5.disconnect();
				} catch (IOException BY8lrcrS) {
					logger.error("�Ͽ�FTP����", BY8lrcrS);
				}
			}
			WNo2tvU5 = null;
		}
	}

}