class n10839252 {
	public static String getWebContent(String remoteUrl) {
		StringBuffer sb = new StringBuffer();
		try {
			java.net.URL url = new java.net.URL(remoteUrl);
			String line;
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
			while ((line = in.readLine()) != null) {
				sb.append(line);
			}
			in.close();
		} catch (Exception e) {
			logger.error("��ȡԶ����ַ����ʧ�� - " + remoteUrl, e);
		}
		return sb.toString();
	}

}