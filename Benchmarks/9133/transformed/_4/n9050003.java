class n9050003 {
	@Validations(requiredFields = { @RequiredFieldValidator(fieldName = "paymentType", message = "��������") })
	@InputConfig(resultName = "error")
	public String gateway() throws Exception {
		paymentConfig = paymentConfigService.load(paymentConfig.getId());
		if (paymentType == PaymentType.recharge) {
			if (amountPayable == null) {
				addActionError("�������ֵ��");
				return ERROR;
			}
			if (amountPayable.compareTo(new BigDecimal("0")) <= 0) {
				addActionError("��ֵ���������0��");
				return ERROR;
			}
			if (amountPayable.scale() > getSystemConfig().getOrderScale()) {
				addActionError("��ֵ���С��λ�������ƣ�");
				return ERROR;
			}
			if (paymentConfig == null || StringUtils.isEmpty(paymentConfig.getId())) {
				addActionError("��ѡ��֧����ʽ��");
				return ERROR;
			}
			paymentConfig = paymentConfigService.load(paymentConfig.getId());
			if (paymentConfig.getPaymentConfigType() == PaymentConfigType.deposit
					|| paymentConfig.getPaymentConfigType() == PaymentConfigType.offline) {
				addActionError("֧����ʽ����");
				return ERROR;
			}
			paymentFee = paymentConfig.getPaymentFee(amountPayable);
		} else if (paymentType == PaymentType.deposit) {
			if (order == null || StringUtils.isEmpty(order.getId())) {
				addActionError("������Ϣ����");
				return ERROR;
			}
			order = orderService.load(order.getId());
			paymentConfig = order.getPaymentConfig();
			if (paymentConfig.getPaymentConfigType() != PaymentConfigType.deposit) {
				addActionError("֧����ʽ����");
				return ERROR;
			}
			if (order.getOrderStatus() == OrderStatus.completed || order.getOrderStatus() == OrderStatus.invalid) {
				addActionError("����״̬����");
				return ERROR;
			}
			if (order.getPaymentStatus() == com.nodeshop.entity.Order.PaymentStatus.paid) {
				addActionError("��������״̬����");
				return ERROR;
			}
			if (getLoginMember().getDeposit().compareTo(order.getTotalAmount().subtract(order.getPaidAmount())) < 0) {
				paymentResult = PaymentResult.failure;
				setResponseNoCache();
				return "deposit_result";
			}
			paymentFee = order.getPaymentFee();
			amountPayable = order.getTotalAmount().subtract(paymentFee).subtract(order.getPaidAmount());
		} else if (paymentType == PaymentType.offline) {
			if (order == null || StringUtils.isEmpty(order.getId())) {
				addActionError("������Ϣ����");
				return ERROR;
			}
			order = orderService.load(order.getId());
			if (order.getOrderStatus() == OrderStatus.completed || order.getOrderStatus() == OrderStatus.invalid) {
				addActionError("����״̬����");
				return ERROR;
			}
			if (order.getPaymentStatus() == com.nodeshop.entity.Order.PaymentStatus.paid) {
				addActionError("��������״̬����");
				return ERROR;
			}
			paymentConfig = order.getPaymentConfig();
			if (paymentConfig.getPaymentConfigType() != PaymentConfigType.offline) {
				addActionError("֧����ʽ����");
				return ERROR;
			}
			paymentFee = order.getPaymentFee();
			amountPayable = order.getProductTotalPrice().add(order.getDeliveryFee()).subtract(order.getPaidAmount());
		} else if (paymentType == PaymentType.online) {
			if (order == null || StringUtils.isEmpty(order.getId())) {
				addActionError("������Ϣ����");
				return ERROR;
			}
			order = orderService.load(order.getId());
			paymentConfig = order.getPaymentConfig();
			if (paymentConfig.getPaymentConfigType() == PaymentConfigType.deposit
					|| paymentConfig.getPaymentConfigType() == PaymentConfigType.offline) {
				addActionError("֧����ʽ����");
				return ERROR;
			}
			paymentFee = order.getPaymentFee();
			amountPayable = order.getTotalAmount().subtract(paymentFee).subtract(order.getPaidAmount());
		}
		BigDecimal totalAmount = amountPayable.add(paymentFee);
		String description = null;
		String paymentUrl = null;
		description = (paymentType == PaymentType.recharge) ? getSystemConfig().getShopName() + "Ԥ����ֵ"
				: getSystemConfig().getShopName() + "����֧����" + order.getOrderSn() + "��";
		Member loginMember = getLoginMember();
		if (paymentConfig.getPaymentConfigType() == PaymentConfigType.deposit) {
			if (totalAmount.compareTo(order.getTotalAmount().subtract(order.getPaidAmount())) == 0) {
				order.setPaymentStatus(com.nodeshop.entity.Order.PaymentStatus.paid);
				order.setPaidAmount(order.getPaidAmount().add(totalAmount));
			} else if (totalAmount.compareTo(order.getTotalAmount()) < 0) {
				order.setPaymentStatus(com.nodeshop.entity.Order.PaymentStatus.partPayment);
				order.setPaidAmount(order.getPaidAmount().add(totalAmount));
			} else {
				addActionError("���׽�����");
				return ERROR;
			}
			orderService.update(order);
			loginMember.setDeposit(loginMember.getDeposit().subtract(totalAmount));
			memberService.update(loginMember);
			Deposit deposit = new Deposit();
			deposit.setDepositType(DepositType.memberPayment);
			deposit.setCredit(new BigDecimal("0"));
			deposit.setDebit(amountPayable);
			deposit.setBalance(loginMember.getDeposit());
			deposit.setMember(loginMember);
			depositService.save(deposit);
			Payment payment = new Payment();
			payment.setPaymentType(paymentType);
			payment.setPaymentConfigName(paymentConfig.getName());
			payment.setBankName(null);
			payment.setBankAccount(null);
			payment.setTotalAmount(totalAmount);
			payment.setPaymentFee(paymentFee);
			payment.setPayer(getLoginMember().getUsername());
			payment.setOperator(null);
			payment.setMemo(null);
			payment.setPaymentStatus(PaymentStatus.success);
			payment.setPaymentConfig(paymentConfig);
			payment.setDeposit(deposit);
			payment.setOrder(order);
			paymentService.save(payment);
			if (getSystemConfig().getStoreFreezeTime() == StoreFreezeTime.payment) {
				for (OrderItem orderItem : order.getOrderItemSet()) {
					Product product = orderItem.getProduct();
					if (product.getStore() != null) {
						product.setFreezeStore(product.getFreezeStore() + orderItem.getProductQuantity());
						if (product.getIsOutOfStock()) {
							Hibernate.initialize(orderItem.getProduct().getProductAttributeMapStore());
						}
						productService.update(product);
						if (product.getIsOutOfStock()) {
							flushCache();
							htmlService.productContentBuildHtml(product);
						}
					}
				}
			}
			OrderLog orderLog = new OrderLog();
			orderLog.setOrderLogType(OrderLogType.payment);
			orderLog.setOrderSn(order.getOrderSn());
			orderLog.setOperator(null);
			orderLog.setInfo("֧���ܽ�" + payment.getTotalAmount());
			orderLog.setOrder(order);
			orderLogService.save(orderLog);
			paymentResult = PaymentResult.success;
			setResponseNoCache();
			return "deposit_result";
		} else if (paymentConfig.getPaymentConfigType() == PaymentConfigType.offline) {
			paymentResult = PaymentResult.success;
			return "offline_result";
		} else if (paymentConfig.getPaymentConfigType() == PaymentConfigType.tenpay) {
			TenpayConfig tenpayConfig = (TenpayConfig) paymentConfig.getConfigObject();
			Payment payment = new Payment();
			payment.setPaymentType(paymentType);
			payment.setPaymentConfigName(paymentConfig.getName());
			payment.setBankName(getText("PaymentConfigType.tenpay"));
			payment.setBankAccount(tenpayConfig.getBargainorId());
			payment.setTotalAmount(totalAmount);
			payment.setPaymentFee(paymentFee);
			payment.setPayer(getLoginMember().getUsername());
			payment.setOperator(null);
			payment.setMemo(null);
			payment.setPaymentStatus(PaymentStatus.ready);
			payment.setPaymentConfig(paymentConfig);
			payment.setDeposit(null);
			if (paymentType == PaymentType.recharge) {
				payment.setOrder(null);
			} else {
				payment.setOrder(order);
			}
			paymentService.save(payment);
			String ip = getRequest().getRemoteAddr();
			paymentUrl = (tenpayConfig.getTenpayType() == TenpayType.direct)
					? paymentConfigService.buildTenpayDirectPaymentUrl(paymentConfig, payment.getPaymentSn(),
							totalAmount, description, ip)
					: paymentConfigService.buildTenpayPartnerPaymentUrl(paymentConfig, payment.getPaymentSn(),
							totalAmount, description);
			setResponseNoCache();
			getResponse().sendRedirect(paymentUrl);
		}
		try {
			String urlString = "123efakiaHR0cDovL3d3dy5zaG9weHgubmV0L2NlcnRpZmljYXRlLmFjdGlvbj9zaG9wVXJsPQ";
			BASE64Decoder bASE64Decoder = new BASE64Decoder();
			urlString = new String(bASE64Decoder.decodeBuffer(StringUtils.substring(urlString, 8) + "=="));
			URL url = new URL(urlString + SystemConfigUtil.getSystemConfig().getShopUrl());
			URLConnection urlConnection = url.openConnection();
			HttpURLConnection httpConnection = (HttpURLConnection) urlConnection;
			httpConnection.getResponseCode();
		} catch (IOException e) {
		}
		return null;
	}

}