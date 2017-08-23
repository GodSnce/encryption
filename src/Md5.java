import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5 {
	public String getMD5(String str) {
		String result = "";

		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
			try {
				md5.update(str.getBytes("UTF-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		byte[] b = md5.digest();

		StringBuffer buf = new StringBuffer("");

		for (int offset = 0; offset < b.length; ++offset) {
			int i = b[offset];
			if (i < 0) {
				i += 256;
			}
			if (i < 16) {
				buf.append("0");
			}
			buf.append(Integer.toHexString(i));
		}

		result = buf.toString();
		return result;
	}

	public static void main(String[] args) throws NoSuchAlgorithmException,
			UnsupportedEncodingException {
		Md5 md5 = new Md5();
	}
}
