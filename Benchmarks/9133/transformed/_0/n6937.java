class n6937 {
	public static void main(String[] xfvauPTD) throws Exception {
		String oIEAwB7N = "http://php.tech.sina.com.cn/download/d_load.php?d_id=7877&down_id=151542";
		oIEAwB7N = EncodeUtils.encodeURL(oIEAwB7N);
		URL lHPMMzgR = new URL(oIEAwB7N);
		System.out.println("��һ�Σ�" + lHPMMzgR);
		HttpURLConnection rNWJoOjf = (HttpURLConnection) lHPMMzgR.openConnection();
		HttpURLConnection.setFollowRedirects(true);
		Map ui6KC2li = rNWJoOjf.getRequestProperties();
		System.out.println("��һ������ͷ��");
		printMap(ui6KC2li);
		rNWJoOjf.connect();
		System.out.println("��һ����Ӧ��");
		System.out.println(rNWJoOjf.getResponseMessage());
		int k5VHQNDR = rNWJoOjf.getResponseCode();
		System.out.println("��һ��code:" + k5VHQNDR);
		printMap(rNWJoOjf.getHeaderFields());
		System.out.println(rNWJoOjf.getURL().getFile());
		if (k5VHQNDR == 404 && !(rNWJoOjf.getURL() + "").equals(oIEAwB7N)) {
			System.out.println(rNWJoOjf.getURL());
			String zn2ZDUxU = URLEncoder.encode(rNWJoOjf.getURL().toString(), "gbk");
			System.out.println(URLEncoder.encode("�������ֲ��Žű�", "GBK"));
			System.out.println(zn2ZDUxU);
			lHPMMzgR = new URL(zn2ZDUxU);
			System.out.println("�ڶ��Σ�" + lHPMMzgR);
			rNWJoOjf = (HttpURLConnection) lHPMMzgR.openConnection();
			System.out.println("�ڶ�����Ӧ��");
			System.out.println("code:" + k5VHQNDR);
			printMap(rNWJoOjf.getHeaderFields());
		}
	}

}