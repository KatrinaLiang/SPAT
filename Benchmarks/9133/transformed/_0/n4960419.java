class n4960419 {
	public static boolean writeFileByChars(Reader J3fLHRzf, File sFtHLeJY, boolean vRSCWDKO) {
		boolean lVTtEpMO = false;
		try {
			FileWriter Btcwn2f9 = new FileWriter(sFtHLeJY, vRSCWDKO);
			IOUtils.copy(J3fLHRzf, Btcwn2f9);
			Btcwn2f9.flush();
			Btcwn2f9.close();
			J3fLHRzf.close();
			lVTtEpMO = true;
		} catch (Exception WvwWskPm) {
			LOG.error("���ַ���д��??" + sFtHLeJY.getName() + "�����쳣??", WvwWskPm);
		}
		return lVTtEpMO;
	}

}