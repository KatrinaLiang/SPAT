class n12973571 {
	public boolean executeUpdate(String LQIJ4D3l, HashMap<Integer, Object> FfMucrC3)
			throws SQLException, ClassNotFoundException {
		getConnection();
		boolean NGySFkVv = false;
		try {
			pstmt = con.prepareStatement(LQIJ4D3l);
			setParamet(pstmt, FfMucrC3);
			logger.info("###############::ִ��SQL������(�������� �в���):" + LQIJ4D3l);
			if (0 < pstmt.executeUpdate()) {
				close_DB_Object();
				NGySFkVv = true;
				con.commit();
			}
		} catch (SQLException F7MXb6x2) {
			logger.info("###############Error DBManager Line121::ִ��SQL������(�������� �޲���):" + LQIJ4D3l + "ʧ��!");
			NGySFkVv = false;
			con.rollback();
			throw F7MXb6x2;
		} catch (ClassNotFoundException n0Th3mcC) {
			logger.info("###############Error DBManager Line152::ִ��SQL������(�������� �޲���):" + LQIJ4D3l + "ʧ��! �����������ʹ���!");
			con.rollback();
			throw n0Th3mcC;
		}
		return NGySFkVv;
	}

}