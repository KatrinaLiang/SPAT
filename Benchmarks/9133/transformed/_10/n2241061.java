class n2241061 {
	public static void save(String from, String recipient, InputStream in, MimeMessage message)
			throws IOException, MessagingException, DocumentVideException {
		ConversationManager conversationManager = FGDSpringUtils.getConversationManager();
		conversationManager.beginConversation();
		FGDDelegate delegate = new FGDDelegate();
		UtilisateurIFGD utilisateur = delegate.getUtilisateurParCourriel(from);
		if (utilisateur == null) {
			String responseEmailSubject = "Votre adresse ne correspond pas �� celle d'un utilisateur d'IntelliGID";
			String responseEmailMessage = "<h3>Pour sauvegarder un courriel, vous devez ��tre un utilisateur d'IntelliGID et l'adresse de courrier ��lectronique utilis��e doit ��tre celle apparaissant dans votre profil.</h3>";
			String sender = recipient.endsWith("localhost") ? FGDSpringUtils.getExpediteurSupport() : recipient;
			Map<String, String> recipients = new HashMap<String, String>();
			recipients.put(from, null);
			MailUtils.sendSimpleHTMLMessage(recipients, responseEmailSubject, responseEmailMessage, sender);
			return;
		}
		File tempFile = File.createTempFile("email", ".eml");
		tempFile.deleteOnExit();
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(tempFile));
		BufferedInputStream bis = new BufferedInputStream(in);
		IOUtils.copy(bis, bos);
		IOUtils.closeQuietly(bis);
		IOUtils.closeQuietly(bos);
		String subject;
		if (message == null) {
			GestionnaireProprietesMimeMessageParser gestionnaire = new GestionnaireProprietesMimeMessageParser();
			message = gestionnaire.asMimeMessage(new BufferedInputStream(new FileInputStream(tempFile)));
		}
		File tempDir = new File(System.getProperty("java.io.tmpdir"));
		try {
			subject = message.getSubject().replace("Fwd:", "").trim();
		} catch (MessagingException e) {
			subject = "Message sans sujet";
		}
		if (!tempDir.exists()) {
			tempDir.mkdirs();
		}
		File emailFile = new File(tempDir, FilenameUtils.normalize(subject) + ".eml");
		FileUtils.copyFile(tempFile, emailFile);
		FicheDocument ficheDocument = new FicheDocument();
		ficheDocument.setFicheCompletee(false);
		ficheDocument.setDateCreationHorodatee(new Date());
		ficheDocument.setUtilisateurSoumetteur(utilisateur);
		ficheDocument.getLangues().addAll(getLanguesDefaut());
		ficheDocument.setCourriel(true);
		SupportDocument support = new SupportDocument();
		FileIOContenuFichierElectronique contenuFichier = new FileIOContenuFichierElectronique(emailFile,
				"multipart/alternative");
		support.setFicheDocument(ficheDocument);
		FichierElectroniqueUtils.setContenu(ficheDocument, support, contenuFichier, utilisateur);
		ficheDocument.setTitre(subject);
		delegate.sauvegarder(ficheDocument, utilisateur);
		String modifyEmail = "http://" + FGDSpringUtils.getServerHost() + ":" + FGDSpringUtils.getServerPort() + "/"
				+ FGDSpringUtils.getApplicationName() + "/app/modifierDocument/id/" + ficheDocument.getId();
		System.out.println(modifyEmail);
		String responseEmailSubject = "Veuillez compl��ter la fiche du courriel ?" + subject + "?";
		String responseEmailMessage = "<h3>Le courrier ��lectronique a ��t�� sauvegard��, mais il est n��cessaire de <a href=\""
				+ modifyEmail + "\">compl��ter sa fiche</a>.</h3>";
		String sender = recipient.endsWith("localhost") ? FGDSpringUtils.getExpediteurSupport() : recipient;
		try {
			MailUtils.sendSimpleHTMLMessage(utilisateur, responseEmailSubject, responseEmailMessage, sender);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		conversationManager.commitTransaction();
		tempFile.delete();
	}

}