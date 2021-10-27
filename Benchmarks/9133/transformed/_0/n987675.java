class n987675 {
	public void run() throws Exception {
		logger.debug("#run enter");
		logger.debug("#run lineId : " + lineId);
		logger.debug("#run quantityNew : " + quantityNew);
		logger.debug("#run priceNew : " + priceNew);
		ResultSet S3v7jo4L = null;
		PreparedStatement aR5qWBIP = null;
		try {
			connection.setAutoCommit(false);
			Integer KEdR7Sxn = null;
			Integer sq1k9cvP = null;
			aR5qWBIP = connection.prepareStatement(SQL_SELECT_ORDER_LINE);
			aR5qWBIP.setInt(1, lineId);
			S3v7jo4L = aR5qWBIP.executeQuery();
			while (S3v7jo4L.next()) {
				KEdR7Sxn = S3v7jo4L.getInt("ITEM_ID");
				sq1k9cvP = S3v7jo4L.getInt("QUANTITY");
			}
			S3v7jo4L.close();
			aR5qWBIP.close();
			aR5qWBIP = connection.prepareStatement(SQL_UPDATE_ITEM_BALANCE);
			aR5qWBIP.setInt(1, quantityNew - sq1k9cvP);
			aR5qWBIP.setInt(2, KEdR7Sxn);
			aR5qWBIP.executeUpdate();
			aR5qWBIP = connection.prepareStatement(SQL_UPDATE_ORDER_LINE);
			aR5qWBIP.setDouble(1, priceNew);
			aR5qWBIP.setInt(2, quantityNew);
			aR5qWBIP.setInt(3, lineId);
			aR5qWBIP.executeUpdate();
			aR5qWBIP.close();
			aR5qWBIP.close();
			connection.commit();
		} catch (SQLException cramdHml) {
			logger.error("SQLException", cramdHml);
			connection.rollback();
			throw new Exception("���� ��էѧݧ��� ��ҧߧ�ӧڧ�� ���٧ڧ�ڧ� �� �٧ѧܧѧ٧�. ����ڧҧܧ� : " + cramdHml.getMessage());
		} finally {
			connection.setAutoCommit(true);
		}
		logger.debug("#run exit");
	}

}