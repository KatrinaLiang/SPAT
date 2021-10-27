class n3291802{
    public long add(T t) throws BaseException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        long result = -1L;
        boolean flag = false;
        try {
            conn = getConnection();
            if (conn != null) {
                flag = true;
            } else {
                conn = ConnectionManager.getConn(getStrConnection());
                conn.setAutoCommit(false);
            }
            pstmt = getAdd(conn, t, this.getTableName());
            pstmt.executeUpdate();
            result = t.getId();
        } catch (SQLException e) {
            try {
                if (!flag) {
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
                if (!flag) {
                    conn.setAutoCommit(true);
                }
            } catch (Exception e) {
                log.error("add(T " + t.toString() + ")���������Զ��ύ������ϢΪ:" + e.getMessage());
            }
            ConnectionManager.closePreparedStatement(pstmt);
            if (!flag) {
                ConnectionManager.closeConn(conn);
            }
        }
        return result;
    }

}