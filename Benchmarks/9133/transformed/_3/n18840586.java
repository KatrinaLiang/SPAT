class n18840586 {
	public static String encodeString(String encodeType, String str) {
		if (!(encodeType.equals("md5of16"))) {
			if (encodeType.equals("md5of32")) {
				MD5 m = new MD5();
				return m.getMD5ofStr(str);
			} else {
				try {
					MessageDigest gv = MessageDigest.getInstance(encodeType);
					gv.update(str.getBytes());
					return new BASE64Encoder().encode(gv.digest());
				} catch (java.security.NoSuchAlgorithmException e) {
					logger.error("BASE64����ʧ��", e);
					return null;
				}
			}
		} else {
			MD5 m = new MD5();
			return m.getMD5ofStr16(str);
		}
	}

}