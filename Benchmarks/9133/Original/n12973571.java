class n12973571{
    public boolean executeUpdate(String strSql, HashMap<Integer, Object> prams) throws SQLException, ClassNotFoundException {
        getConnection();
        boolean flag = false;
        try {
            pstmt = con.prepareStatement(strSql);
            setParamet(pstmt, prams);
            logger.info("###############::ִ��SQL������(�������� �в���):" + strSql);
            if (0 < pstmt.executeUpdate()) {
                close_DB_Object();
                flag = true;
                con.commit();
            }
        } catch (SQLException ex) {
            logger.info("###############Error DBManager Line121::ִ��SQL������(�������� �޲���):" + strSql + "ʧ��!");
            flag = false;
            con.rollback();
            throw ex;
        } catch (ClassNotFoundException ex) {
            logger.info("###############Error DBManager Line152::ִ��SQL������(�������� �޲���):" + strSql + "ʧ��! �����������ʹ���!");
            con.rollback();
            throw ex;
        }
        return flag;
    }

}