class n3291802 {
	public long add(T xhSMmdQR) throws BaseException {
		Connection gQnvcio7 = null;
		PreparedStatement brBinRm0 = null;
		long RGYxYqHR = -1L;
		boolean A4ghuELk = false;
		try {
			gQnvcio7 = getConnection();
			if (gQnvcio7 != null) {
				A4ghuELk = true;
			} else {
				gQnvcio7 = ConnectionManager.getConn(getStrConnection());
				gQnvcio7.setAutoCommit(false);
			}
			brBinRm0 = getAdd(gQnvcio7, xhSMmdQR, this.getTableName());
			brBinRm0.executeUpdate();
			RGYxYqHR = xhSMmdQR.getId();
		} catch (SQLException DPtumN24) {
			try {
				if (!A4ghuELk) {
					gQnvcio7.rollback();
				}
			} catch (Exception vSpujdph) {
				log.error("add(T " + xhSMmdQR.toString() + ")�ع�����������Ϣ��" + vSpujdph.getMessage());
			}
			log.error("add(T " + xhSMmdQR.toString() + ")��������:" + DPtumN24.getMessage());
		} catch (BaseException hUgfpdr7) {
			throw hUgfpdr7;
		} finally {
			try {
				if (!A4ghuELk) {
					gQnvcio7.setAutoCommit(true);
				}
			} catch (Exception tOblcOsZ) {
				log.error("add(T " + xhSMmdQR.toString() + ")���������Զ��ύ������ϢΪ:" + tOblcOsZ.getMessage());
			}
			ConnectionManager.closePreparedStatement(brBinRm0);
			if (!A4ghuELk) {
				ConnectionManager.closeConn(gQnvcio7);
			}
		}
		return RGYxYqHR;
	}

}