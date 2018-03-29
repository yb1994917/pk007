package zsly.xiangdian.cn.hookyueyuu;


public class AESUtil {
    private static String se = "";
    private static String sk = "";
    private static byte[] skb = new byte[]{(byte) 53, (byte) 55, (byte) 51, (byte) 104, (byte) 104, (byte) 104, (byte) 103, (byte) 105, (byte) 105, (byte) 119, (byte) 119, (byte) 119};
    private static byte[] ske = new byte[]{(byte) 52, (byte) 68, (byte) 47, (byte) 101, (byte) 106, (byte) 90, (byte) 97, (byte) 54, (byte) 52, (byte) 90, (byte) 100, (byte) 112, (byte) 82, (byte) 56, (byte) 119, (byte) 109, (byte) 79, (byte) 97, (byte) 67, (byte) 81, (byte) 109, (byte) 119, (byte) 61, (byte) 61};
    private static String sr = "";

    public static String kk() {
        sk = new String(skb);
        se = new String(ske);
        try {
            sr = AESCrypt.decrypt(sk, se);
            return sr;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}