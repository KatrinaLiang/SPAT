class n10839252 {
	public static String getWebContent(String ReRJGnZ8) {
		StringBuffer VcLo5GXv = new StringBuffer();
		try {
			java.net.URL PN41aSE9 = new java.net.URL(ReRJGnZ8);
			BufferedReader yfUFbSrw = new BufferedReader(new InputStreamReader(PN41aSE9.openStream()));
			String MNPBmFWW;
			while ((MNPBmFWW = yfUFbSrw.readLine()) != null) {
				VcLo5GXv.append(MNPBmFWW);
			}
			yfUFbSrw.close();
		} catch (Exception d5ofF7yv) {
			logger.error("��ȡԶ����ַ����ʧ�� - " + ReRJGnZ8, d5ofF7yv);
		}
		return VcLo5GXv.toString();
	}

}