class n14878593 {
	public void hyperlinkUpdate(HyperlinkEvent e) {
		if (EventType.ACTIVATED == e.getEventType()) {
			try {
				URL url = e.getURL();
				InputStream stream = url.openStream();
				try {
					StringWriter writer = new StringWriter();
					IOUtils.copy(stream, writer, "UTF-8");
					JEditorPane editor = new JEditorPane("text/plain", writer.toString());
					editor.setEditable(false);
					editor.setBackground(Color.WHITE);
					editor.setCaretPosition(0);
					editor.setPreferredSize(new Dimension(600, 400));
					String name = url.toString();
					name = name.substring(name.lastIndexOf('/') + 1);
					JDialog dialog = new JDialog(this, "���ݽ���: " + name);
					dialog.add(new JScrollPane(editor));
					dialog.pack();
					dialog.setVisible(true);
				} finally {
					stream.close();
				}
			} catch (IOException exception) {
				exception.printStackTrace();
			}
		}
	}

}