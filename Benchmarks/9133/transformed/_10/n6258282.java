class n6258282 {
	public void testDigest() {
		try {
			MessageDigest alga = MessageDigest.getInstance("SHA-1");
			String myinfo = "�ҵĲ�����Ϣ";
			alga.update(myinfo.getBytes());
			byte[] digesta = alga.digest();
			System.out.println("����ϢժҪ��:" + byte2hex(digesta));
			MessageDigest algb = MessageDigest.getInstance("SHA-1");
			algb.update(myinfo.getBytes());
			if (MessageDigest.isEqual(digesta, algb.digest())) {
				System.out.println("��Ϣ�������");
			} else {
				System.out.println("ժҪ����ͬ");
			}
		} catch (NoSuchAlgorithmException ex) {
			System.out.println("�Ƿ�ժҪ�㷨");
		}
	}

}