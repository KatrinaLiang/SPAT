class n12973570 {
	public boolean executeUpdate(String strSql) throws SQLException {
		getConnection();
		boolean flag = false;
		stmt = con.createStatement();
		logger.info("###############::ִ��SQL������(�������� �޲���):" + strSql);
		try {
			if (!(0 < stmt.executeUpdate(strSql)))
				;
			else {
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