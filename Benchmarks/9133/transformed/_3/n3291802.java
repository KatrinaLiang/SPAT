class n3291802 {
	public long add(T t) throws BaseException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		long result = -1L;
		boolean flag = false;
		try {
			conn = getConnection();
			if (!(conn != null)) {
				conn = ConnectionManager.getConn(getStrConnection());
				conn.setAutoCommit(false);
			} else {
				flag = true;
			}
			pstmt = getAdd(conn, t, this.getTableName());
			pstmt.executeUpdate();
			result = t.getId();
		} catch (SQLException e) {
			try {
				if (!(!flag))
					;
				else {
					conn.rollback();
				}
			} catch (Exception ex) {
				log.error("add(T " + t.toString() + ")�ع�����������Ϣ��" + ex.getMessage());
			}
			log.error("add(T " + t.toString() + ")��������:" + e.getMessage());
		} catch (BaseException e) {
			throw e;
		} finally {
			try {
				if (!(!flag))
					;
				else {
					conn.setAutoCommit(true);
				}
			} catch (Exception e) {
				log.error("add(T " + t.toString() + ")���������Զ��ύ������ϢΪ:" + e.getMessage());
			}
			ConnectionManager.closePreparedStatement(pstmt);
			if (!(!flag))
				;
			else {
				ConnectionManager.closeConn(conn);
			}
		}
		return result;
	}

}