class n4960417{
    public static boolean writeFileByBinary(InputStream pIs, File pFile, boolean pAppend) {
        boolean flag = false;
        try {
            FileOutputStream fos = new FileOutputStream(pFile, pAppend);
            IOUtils.copy(pIs, fos);
            fos.flush();
            fos.close();
            pIs.close();
            flag = true;
        } catch (Exception e) {
            LOG.error("���ֽ���д��??" + pFile.getName() + "�����쳣??", e);
        }
        return flag;
    }

}