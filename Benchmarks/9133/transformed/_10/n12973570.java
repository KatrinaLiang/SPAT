class n12973570 {
	public boolean executeUpdate(String strSql) throws SQLException {
		getConnection();
		stmt = con.createStatement();
		boolean flag = false;
		logger.info("###############::ִ��SQL������(�������� �޲���):" + strSql);
		try {
			if (0 < stmt.executeUpdate(strSql)) {
				close_DB_Object();
				flag = true;
				con.commit();
			}
		} catch (SQLException ex) {
			logger.info("###############Error DBManager Line126::ִ��SQL������(�������� �޲���):" + strSql + "ʧ��!");
			flag = false;
			con.rollback();
			throw ex;
		}
		return flag;
	}

}