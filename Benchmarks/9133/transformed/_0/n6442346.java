class n6442346 {
	public static boolean copyFileCover(String tIzi1aci, String vDLhUTGw, boolean QaKKmsBs) {
		File JbDLi5P9 = new File(tIzi1aci);
		if (!JbDLi5P9.exists()) {
			System.out.println("�����ļ�ʧ�ܣ�Դ�ļ�" + tIzi1aci + "������!");
			return false;
		} else if (!JbDLi5P9.isFile()) {
			System.out.println("�����ļ�ʧ�ܣ�" + tIzi1aci + "����һ���ļ�!");
			return false;
		}
		File b5YtY6kX = new File(vDLhUTGw);
		if (b5YtY6kX.exists()) {
			if (QaKKmsBs) {
				System.out.println("Ŀ���ļ��Ѵ��ڣ�׼��ɾ��!");
				if (!FileOperateUtils.delFile(vDLhUTGw)) {
					System.out.println("ɾ��Ŀ���ļ�" + vDLhUTGw + "ʧ��!");
					return false;
				}
			} else {
				System.out.println("�����ļ�ʧ�ܣ�Ŀ���ļ�" + vDLhUTGw + "�Ѵ���!");
				return false;
			}
		} else {
			if (!b5YtY6kX.getParentFile().exists()) {
				System.out.println("Ŀ���ļ����ڵ�Ŀ¼�����ڣ�����Ŀ¼!");
				if (!b5YtY6kX.getParentFile().mkdirs()) {
					System.out.println("����Ŀ���ļ����ڵ�Ŀ¼ʧ��!");
					return false;
				}
			}
		}
		int nvvfjk6B = 0;
		InputStream hgP8YQlz = null;
		OutputStream Mh7Bu92h = null;
		try {
			hgP8YQlz = new FileInputStream(JbDLi5P9);
			Mh7Bu92h = new FileOutputStream(b5YtY6kX);
			byte[] DfrYfiAG = new byte[1024];
			while ((nvvfjk6B = hgP8YQlz.read(DfrYfiAG)) != -1) {
				Mh7Bu92h.write(DfrYfiAG, 0, nvvfjk6B);
			}
			System.out.println("���Ƶ����ļ�" + tIzi1aci + "��" + vDLhUTGw + "�ɹ�!");
			return true;
		} catch (Exception rpSty7LX) {
			System.out.println("�����ļ�ʧ�ܣ�" + rpSty7LX.getMessage());
			return false;
		} finally {
			if (Mh7Bu92h != null) {
				try {
					Mh7Bu92h.close();
				} catch (IOException NGYDqVH2) {
					NGYDqVH2.printStackTrace();
				}
			}
			if (hgP8YQlz != null) {
				try {
					hgP8YQlz.close();
				} catch (IOException SToDv30Q) {
					SToDv30Q.printStackTrace();
				}
			}
		}
	}

}