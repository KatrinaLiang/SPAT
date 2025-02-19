class n5389524 {
	public ActualTask(TEditor editor, TIGDataBase dataBase, String directoryPath) {
            File myDirectory = new File(directoryPath);
            int i;
            String[] list = myDirectory.list();
            for (i = 0; ((i < list.length) && !stop); i++) {
                current = i;
                if ((list[i].compareTo("Images") != 0) && ((list[i].substring(list[i].lastIndexOf('.'), list[i].length()).toLowerCase().compareTo(".jpg") == 0) || (list[i].substring(list[i].lastIndexOf('.'), list[i].length()).toLowerCase().compareTo(".bmp") == 0) || (list[i].substring(list[i].lastIndexOf('.'), list[i].length()).toLowerCase().compareTo(".png") == 0))) {
                    String name = list[i];
                    name = name.replace(' ', '_').replace(',', '-').replace('��', 'a').replace('��', 'e')
							.replace('��', 'i').replace('��', 'o').replace('��', 'u').replace('?', 'A').replace('?', 'E')
							.replace('?', 'I').replace('?', 'O').replace('?', 'U');
                    String pathSrc = directoryPath.concat(list[i]);
                    Vector aux = new Vector();
                    String pathDst = directoryPath.concat(name);
                    aux = dataBase.imageSearch(name.substring(0, name.lastIndexOf('.')));
                    File src = new File(pathSrc);
                    if (aux.size() != 0)
						pathDst = pathDst.substring(0, pathDst.lastIndexOf('.')) + '_' + aux.size() + ".png";
                    String nameSrc = '.' + src.separator + "Images" + src.separator + name.substring(0, 1).toUpperCase()
							+ src.separator
							+ pathDst.substring(pathDst.lastIndexOf(src.separator) + 1, pathDst.length());
                    File absPath = new File("");
                    String newDirectory = '.' + src.separator + "Images" + src.separator + name.substring(0, 1).toUpperCase();
                    ImageIcon image = null;
                    String imagePathThumb = (nameSrc.substring(0, nameSrc.lastIndexOf("."))).concat("_th.jpg");
                    if (src != null) {
                        if (TFileUtils.isJAIRequired(src)) {
                            RenderedOp src_aux = JAI.create("fileload", src.getAbsolutePath());
                            BufferedImage bufferedImage = src_aux.getAsBufferedImage();
                            image = new ImageIcon(bufferedImage);
                        } else {
                            image = new ImageIcon(src.getAbsolutePath());
                        }
                        if (image.getImageLoadStatus() == MediaTracker.ERRORED) {
                            System.out.print("Error al insertar imagen: ");
                            System.out.println(pathDst);
                        } else {
                            imageFile = new File(directoryPath + "Images");
                            int option = 0;
                            if (!imageFile.exists()) {
                                TIGNewImageDataDialog dialog = new TIGNewImageDataDialog(editor, dataBase, image, nameSrc.substring(nameSrc.lastIndexOf(File.separator) + 1, nameSrc.length()), list[i].substring(0, list[i].lastIndexOf('.')), myTask);
                                option = dialog.getOption();
                                if (option != 0) {
                                    File newDirectoryFolder = new File(newDirectory);
                                    newDirectoryFolder.mkdirs();
                                    try {
                                        FileChannel srcChannel = new FileInputStream(pathSrc).getChannel();
                                        FileChannel dstChannel = new FileOutputStream(nameSrc).getChannel();
                                        dstChannel.transferFrom(srcChannel, 0, srcChannel.size());
                                        srcChannel.close();
                                        dstChannel.close();
                                    } catch (IOException exc) {
                                        System.out.println(exc.getMessage());
                                        System.out.println(exc.toString());
                                    }
                                }
                            }
                            if (imageFile.exists()) {
                                dataBase.insertImageDB(list[i].substring(0, list[i].lastIndexOf('.')), nameSrc.substring(nameSrc.lastIndexOf(File.separator) + 1, nameSrc.length()));
                                File newDirectoryFolder = new File(newDirectory);
                                newDirectoryFolder.mkdirs();
                                try {
                                    FileChannel srcChannel = new FileInputStream(pathSrc).getChannel();
                                    FileChannel dstChannel = new FileOutputStream(nameSrc).getChannel();
                                    dstChannel.transferFrom(srcChannel, 0, srcChannel.size());
                                    srcChannel.close();
                                    dstChannel.close();
                                } catch (IOException exc) {
                                    System.out.println(exc.getMessage());
                                    System.out.println(exc.toString());
                                }
                            }
                            try {
                                int thumbWidth = PREVIEW_WIDTH;
                                int thumbHeight = PREVIEW_HEIGHT;
                                int imageWidth = image.getIconWidth();
                                double thumbRatio = (double) thumbWidth / (double) thumbHeight;
                                int imageHeight = image.getIconHeight();
                                double imageRatio = (double) imageWidth / (double) imageHeight;
                                if (thumbRatio < imageRatio) {
                                    thumbHeight = (int) (thumbWidth / imageRatio);
                                } else {
                                    thumbWidth = (int) (thumbHeight * imageRatio);
                                }
                                BufferedImage thumbImage = new BufferedImage(thumbWidth, thumbHeight, BufferedImage.TYPE_INT_RGB);
                                Graphics2D graphics2D = thumbImage.createGraphics();
                                graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                                graphics2D.drawImage(image.getImage(), 0, 0, thumbWidth, thumbHeight, null);
                                BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(imagePathThumb));
                                JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
                                int quality = 100;
                                JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(thumbImage);
                                quality = Math.max(0, Math.min(quality, 100));
                                param.setQuality((float) quality / 100.0f, false);
                                encoder.setJPEGEncodeParam(param);
                                encoder.encode(thumbImage);
                                out.close();
                            } catch (Exception ex) {
                                System.out.println(ex.getMessage());
                                System.out.println(ex.toString());
                            }
                        }
                    }
                }
            }
            if (imageFile.exists() && !stop) {
                try {
                    DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
                    DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
                    Document doc = docBuilder.parse(imageFile);
                    Element dataBaseElement = doc.getDocumentElement();
                    if (dataBaseElement.getTagName().equals("dataBase")) {
                        NodeList imageNodeList = dataBaseElement.getElementsByTagName("image");
                        for (int j = 0; j < imageNodeList.getLength(); j++) {
                            current++;
                            Node imageNode = imageNodeList.item(j);
                            NodeList lista = imageNode.getChildNodes();
                            Node nameNode = imageNode.getChildNodes().item(0);
                            String imageName = nameNode.getChildNodes().item(0).getNodeValue();
                            int imageKey = dataBase.imageKeySearchName(imageName.substring(0, imageName.lastIndexOf('.')));
                            if (imageKey != -1) {
                                for (int k = 1; k < imageNode.getChildNodes().getLength(); k++) {
                                    Node keyWordNode = imageNode.getChildNodes().item(k);
                                    String keyWord = keyWordNode.getChildNodes().item(0).getNodeValue();
                                    int conceptKey = dataBase.conceptKeySearch(keyWord);
                                    if (conceptKey == -1) {
                                        dataBase.insertConceptDB(keyWord);
                                        conceptKey = dataBase.conceptKeySearch(keyWord);
                                    }
                                    dataBase.insertAsociatedDB(conceptKey, imageKey);
                                }
                            }
                        }
                    }
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                    System.out.println(ex.toString());
                }
            }
            current = lengthOfTask;
        }

}