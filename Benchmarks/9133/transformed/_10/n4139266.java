class n4139266 {
	public int down(String downLoadUrl, String saveUrl) {
		long fileSize = 0;
		int status = 1;
		byte[] bt = new byte[1024];
		int len = 0;
		long totalSize = 0;
		RandomAccessFile raFile = null;
		HttpURLConnection httpConn = null;
		URL url = null;
		BufferedInputStream bis = null;
		try {
			url = new URL(downLoadUrl);
			httpConn = (HttpURLConnection) url.openConnection();
			if (httpConn.getHeaderField("Content-Length") == null) {
				status = 500;
			} else {
				totalSize = Long.parseLong(httpConn.getHeaderField("Content-Length"));
				System.out.println("�ļ���С:" + totalSize / 1000000 + " M");
				httpConn.disconnect();
				httpConn = (HttpURLConnection) url.openConnection();
				fileSize = loadFileSize(saveUrl + BACK_SUFFIX);
				System.out.println("������:" + fileSize / 1000000 + " M");
				httpConn.setRequestProperty("RANGE", "bytes=" + fileSize + "-");
				httpConn.setRequestProperty("Accept", "image/gif,image/x-xbitmap,application/msword,*/*");
				raFile = new RandomAccessFile(saveUrl + BACK_SUFFIX, "rw");
				raFile.seek(fileSize);
				bis = new BufferedInputStream(httpConn.getInputStream());
				while ((len = bis.read(bt)) > 0) {
					raFile.write(bt, 0, len);
					float downSize = raFile.length();
					float progress = 0.f;
					progress = downSize / totalSize;
					System.out.println(progress * 100 + "%" + "\t\t" + downSize / 1000000 + "M");
				}
			}
		} catch (FileNotFoundException e) {
			status = 404;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (bis != null)
					bis.close();
				if (raFile != null)
					raFile.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (loadFileSize(saveUrl + BACK_SUFFIX) == totalSize) {
			fileRename(saveUrl + BACK_SUFFIX, saveUrl);
		}
		return status;
	}

}