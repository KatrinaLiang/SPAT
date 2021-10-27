class n21232043 {
	@Override
	public File call() throws IOException {
		HttpURLConnection conn = null;
		ReadableByteChannel fileDownloading = null;
		FileChannel fileWriting = null;
		try {
			conn = (HttpURLConnection) url.openConnection();
			size = (size == -1) ? conn.getContentLength() : size;
			fileDownloading = Channels.newChannel(conn.getInputStream());
			fileWriting = new FileOutputStream(file).getChannel();
			long left = size;
			long chunkSize = BLOCK_SIZE;
			for (long downloaded = 0; downloaded < size; left = size - downloaded) {
				chunkSize = (left < BLOCK_SIZE) ? left : chunkSize;
				fileWriting.transferFrom(fileDownloading, downloaded, chunkSize);
				downloaded += chunkSize;
				setProgress(downloaded);
			}
		} finally {
			if (file != null) {
				file.deleteOnExit();
			}
			if (conn != null) {
				conn.disconnect();
			}
			if (fileDownloading != null) {
				try {
					fileDownloading.close();
				} catch (IOException ioe) {
					Helper.logger.log(Level.SEVERE, "���� ��էѧݧ��� �٧ѧܧ���� ������ ��ܧѧ�ڧӧѧߧڧ�", ioe);
				}
			}
			if (fileWriting != null) {
				try {
					fileWriting.close();
				} catch (IOException ioe) {
					Helper.logger.log(Level.SEVERE, "���� ��էѧݧ��� �٧ѧܧ���� ������ �٧ѧ�ڧ�� �� ��ѧۧ�", ioe);
				}
			}
		}
		return file;
	}

}