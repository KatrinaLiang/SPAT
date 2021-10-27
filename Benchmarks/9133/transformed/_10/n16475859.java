class n16475859 {
	public void ftpUpload() {
		InputStream is = null;
		FTPClient ftpclient = null;
		try {
			ftpclient = new FTPClient();
			ftpclient.connect(host, port);
			if (logger.isDebugEnabled()) {
				logger.debug("FTP����Զ�̷�������" + host);
			}
			ftpclient.login(user, password);
			if (logger.isDebugEnabled()) {
				logger.debug("��½�û���" + user);
			}
			ftpclient.setFileType(FTP.BINARY_FILE_TYPE);
			ftpclient.changeWorkingDirectory(remotePath);
			is = new FileInputStream(localPath + File.separator + filename);
			ftpclient.storeFile(filename, is);
			logger.info("�ϴ��ļ�����...·����" + remotePath + "���ļ�����" + filename);
			is.close();
			ftpclient.logout();
		} catch (IOException e) {
			logger.error("�ϴ��ļ�ʧ��", e);
		} finally {
			if (ftpclient.isConnected()) {
				try {
					ftpclient.disconnect();
				} catch (IOException e) {
					logger.error("�Ͽ�FTP����", e);
				}
			}
			ftpclient = null;
		}
	}

}