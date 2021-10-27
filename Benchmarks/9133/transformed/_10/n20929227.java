class n20929227 {
	private void insertService(String table, int type) {
		Connection con = null;
		log.info("");
		log.info("��������" + table + "�ķ��񡣡�����������");
		try {
			con = DODataSource.getDefaultCon();
			con.setAutoCommit(false);
			Statement stmt = con.createStatement();
			Statement stmt2 = con.createStatement();
			String serviceUid = UUIDHex.getInstance().generate();
			List props = new ArrayList();
			DOBO bo = DOBO.getDOBOByName(StringUtil.getDotName(table));
			String name = "";
			StringBuffer mainSql = null;
			String l10n = "";
			Boolean isNew = null;
			String prefix = StringUtil.getDotName(table);
			switch (type) {
			case 1:
				name = prefix + ".insert";
				l10n = name;
				props = bo.retrieveProperties();
				mainSql = getInsertSql(props, table);
				isNew = Boolean.TRUE;
				break;
			case 2:
				name = prefix + ".update";
				l10n = name;
				props = bo.retrieveProperties();
				mainSql = this.getModiSql(props, table);
				isNew = Boolean.FALSE;
				break;
			case 3:
				DOBOProperty property = DOBOProperty.getDOBOPropertyByName(bo.getName(), "objuid");
				System.out.println("BOBOBO::::::" + bo);
				System.out.println("Property::::::" + property);
				if (property == null) {
					return;
				}
				name = prefix + ".delete";
				l10n = name;
				props.add(property);
				mainSql = new StringBuffer("delete from ").append(table).append(" where objuid = ?");
				break;
			case 4:
				property = DOBOProperty.getDOBOPropertyByName(bo.getName(), "objuid");
				if (property == null) {
					return;
				}
				name = prefix + ".browse";
				l10n = name;
				props.add(property);
				mainSql = new StringBuffer("select * from ").append(table).append(" where objuid = ?");
				break;
			case 5:
				name = prefix + ".list";
				l10n = name;
				mainSql = new StringBuffer("select * from ").append(table);
			}
			this.setParaLinkBatch(props, stmt2, serviceUid, isNew);
			StringBuffer aSql = new StringBuffer("insert into DO_Service(objuid,l10n,name,bouid,mainSql) values(")
					.append("'").append(serviceUid).append("','").append(l10n).append("','").append(name).append("','")
					.append(this.getDOBOUid(table)).append("','").append(mainSql).append("')");
			log.info("Servcice's Sql:" + aSql.toString());
			stmt.executeUpdate(aSql.toString());
			stmt2.executeBatch();
			con.commit();
		} catch (SQLException ex) {
			try {
				con.rollback();
			} catch (SQLException ex2) {
				ex2.printStackTrace();
			}
			ex.printStackTrace();
		} finally {
			try {
				if (!con.isClosed()) {
					con.close();
				}
			} catch (SQLException ex1) {
				ex1.printStackTrace();
			}
		}
	}

}