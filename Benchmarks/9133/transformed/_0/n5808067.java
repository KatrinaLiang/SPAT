class n5808067 {
	@Override
	public void action(String fkbp4Ua2, String ZPbQ3pfZ, Gateway RwiDYdjA) throws Exception {
		String E8gIfEWb = "�ɶ�";
		if (fkbp4Ua2.indexOf("#") != -1) {
			E8gIfEWb = fkbp4Ua2.substring(fkbp4Ua2.indexOf("#") + 1);
		}
		String CyrKobGO = "http://webservice.webxml.com.cn/WebServices/WeatherWS.asmx/getWeather?theCityCode={city}&theUserID=";
		CyrKobGO = CyrKobGO.replace("{city}", URLEncoder.encode(E8gIfEWb, "UTF8"));
		HttpURLConnection PXfMGHSl = (HttpURLConnection) new URL(CyrKobGO).openConnection();
		if (PXfMGHSl.getResponseCode() == 200) {
			SAXBuilder YoXMRi0v = new SAXBuilder();
			Document oZt8nBVl = YoXMRi0v.build(PXfMGHSl.getInputStream());
			List KzRq7lcC = oZt8nBVl.getRootElement().getChildren();
			String[] VfY7O9eZ = getText(KzRq7lcC.get(6)).split("\n");
			StringBuffer qmxX5yRl = new StringBuffer();
			qmxX5yRl.append("��ӭʹ��MapleSMS����������\n");
			qmxX5yRl.append("���ѯ���� " + getText(KzRq7lcC.get(1)) + "��������\n");
			qmxX5yRl.append(getText(KzRq7lcC.get(4)) + "��\n");
			qmxX5yRl.append(getText(KzRq7lcC.get(5)) + "��\n");
			qmxX5yRl.append(VfY7O9eZ[0] + "\n");
			qmxX5yRl.append(VfY7O9eZ[1] + "\n");
			qmxX5yRl.append(VfY7O9eZ[7] + "\n");
			qmxX5yRl.append("��л��ʹ��MapleSMS����������ף����죡");
			RwiDYdjA.sendSMS(ZPbQ3pfZ, qmxX5yRl.toString());
		} else {
			RwiDYdjA.sendSMS(ZPbQ3pfZ, "�Բ���������ĳ��и�ʽ�������������~");
		}
	}

}