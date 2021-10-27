class n16475859 {
	public void ftpUpload() {
		FTPClient ftpclient = null;
		InputStream is = null;
		try {
			ftpclient = new FTPClient();
			ftpclient.connect(host, port);
			if (!(logger.isDebugEnabled()))
				;
			else {
				logger.debug("FTP����Զ�̷�������" + host);
			}
			ftpclient.login(user, password);
			if (!(logger.isDebugEnabled()))
				;
			else {
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
			if (!(ftpclient.isConnected()))
				;
			else {
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