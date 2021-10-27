class n20490347 {
	public void run() throws Exception {
		logger.debug("#run enter");
		PreparedStatement jkuV7paj = null;
		try {
			connection.setAutoCommit(false);
			jkuV7paj = connection.prepareStatement(SQL_UPDATE_ITEM_MIN_QTTY);
			jkuV7paj.setInt(1, deliveryId);
			jkuV7paj.setInt(2, deliveryId);
			jkuV7paj.executeUpdate();
			jkuV7paj.close();
			logger.debug("#run update STORE.ITEM ok");
			jkuV7paj = connection.prepareStatement(SQL_DELETE_DELIVERY_LINE);
			jkuV7paj.setInt(1, deliveryId);
			jkuV7paj.executeUpdate();
			jkuV7paj.close();
			logger.debug("#run delete STORE.DELIVERY_LINE ok");
			jkuV7paj = connection.prepareStatement(SQL_DELETE_DELIVERY);
			jkuV7paj.setInt(1, deliveryId);
			jkuV7paj.executeUpdate();
			jkuV7paj.close();
			logger.debug("#run delete STORE.DELIVERY ok");
			connection.commit();
		} catch (Exception FkdgrMbs) {
			logger.error("#run Transaction roll back ", FkdgrMbs);
			connection.rollback();
			throw new Exception("#run ���� ��էѧݧ��� �٧ѧԧ��٧ڧ�� �� ���� �ڧߧ���ާѧ�ڧ� ��� ��ҧߧ�ӧݧ֧ߧڧ� ��ܧݧѧէ�. ����ڧҧܧ� : "
					+ FkdgrMbs.getMessage());
		} finally {
			connection.setAutoCommit(true);
		}
		logger.debug("#run exit");
	}

}