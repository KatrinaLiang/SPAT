class n21164146 {
	public String requestGET(String baseUrl, Map<String, String> params) throws Exception {
		StringBuffer url = new StringBuffer();
		String result = "";
		url.append(baseUrl);
		if (params != null && !params.isEmpty()) {
			List<String> keys = new ArrayList<String>(params.keySet());
			for (String key : keys) {
				url.append(key);
				url.append("/");
				url.append(URLEncoder.encode(params.get(key), "UTF-8"));
			}
		}
		HttpGet get = new HttpGet(url.toString());
		HttpEntity entity = null;
		HttpResponse res = client.execute(get);
		if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			entity = res.getEntity();
			String read = "";
			BufferedReader in = new BufferedReader(new InputStreamReader(entity.getContent()));
			StringBuffer content = new StringBuffer();
			while ((read = in.readLine()) != null) {
				content.append(read);
			}
			in.close();
			JSONObject rObj = new JSONObject(content.toString());
			result = rObj.getString("msg");
		} else {
			result = "HTTP����ʧ��";
		}
		if (entity != null)
			entity.consumeContent();
		client.getConnectionManager().shutdown();
		get = null;
		return result;
	}

}