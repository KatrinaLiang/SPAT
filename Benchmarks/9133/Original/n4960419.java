class n4960419{
    public static boolean writeFileByChars(Reader pReader, File pFile, boolean pAppend) {
        boolean flag = false;
        try {
            FileWriter fw = new FileWriter(pFile, pAppend);
            IOUtils.copy(pReader, fw);
            fw.flush();
            fw.close();
            pReader.close();
            flag = true;
        } catch (Exception e) {
            LOG.error("���ַ���д��??" + pFile.getName() + "�����쳣??", e);
        }
        return flag;
    }

}