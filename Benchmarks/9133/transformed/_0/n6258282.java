class n6258282 {
	public void testDigest() {
		try {
			String D1ROUZtj = "�ҵĲ�����Ϣ";
			MessageDigest RkDtqclW = MessageDigest.getInstance("SHA-1");
			RkDtqclW.update(D1ROUZtj.getBytes());
			byte[] CCDkRhx8 = RkDtqclW.digest();
			System.out.println("����ϢժҪ��:" + byte2hex(CCDkRhx8));
			MessageDigest eXbXsskR = MessageDigest.getInstance("SHA-1");
			eXbXsskR.update(D1ROUZtj.getBytes());
			if (MessageDigest.isEqual(CCDkRhx8, eXbXsskR.digest())) {
				System.out.println("��Ϣ�������");
			} else {
				System.out.println("ժҪ����ͬ");
			}
		} catch (NoSuchAlgorithmException MnpvaEbw) {
			System.out.println("�Ƿ�ժҪ�㷨");
		}
	}

}