/**
 * 加密的密钥生成 
 * 
 * JAVA 的JCE 密钥生成 
 */
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.util.Random;

public class generatorKey {

	public static final String KEY_ALGORITHM = "DES";

	public static byte[] initKey() throws Exception {

		/*
		 * 实例化密钥生成器
		 * 
		 * 若要使用64bit密钥注意替换 将下述代码中的KeyGenerator.getInstance(CIPHER_ALGORITHM);
		 * 替换为KeyGenerator.getInstance(CIPHER_ALGORITHM, "BC");
		 */
		KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);

		/*
		 * 初始化密钥生成器 若要使用64bit密钥注意替换 将下述代码kg.init(56); 替换为kg.init(64);
		 */
		kg.init(56, new SecureRandom());

		// 生成秘密密钥
		SecretKey secretKey = kg.generateKey();

		// 获得密钥的二进制编码形式
		return secretKey.getEncoded();
	}

	public static String getAESRandomKeyString()
			throws UnsupportedEncodingException {
		// 随机生成密钥
		/*
		 * KeyGenerator keygen; try { keygen = KeyGenerator.getInstance("AES");
		 * SecureRandom random = new SecureRandom(); keygen.init(random); Key
		 * key = keygen.generateKey();
		 * 
		 * byte[] encoded = key.getEncoded(); // 获取秘钥字符串 String key64Str =
		 * Base64.encodeBase64String(key.getEncoded()); // String keyStr = new
		 * String(key.getEncoded(),"UTF-8"); return key64Str; } catch
		 * (NoSuchAlgorithmException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); return null; }
		 */

		String key = generatorKey.createRandomCharData(24);
		return key;

	}

	// 根据指定长度生成字母和数字的随机数
	// 0~9的ASCII为48~57
	// A~Z的ASCII为65~90
	// a~z的ASCII为97~122
	public static String createRandomCharData(int length) {
		StringBuilder sb = new StringBuilder();
		Random rand = new Random();// 随机用以下三个随机生成器
		Random randdata = new Random();
		int data = 0;
		for (int i = 0; i < length; i++) {
			int index = rand.nextInt(3);
			// 目的是随机选择生成数字，大小写字母
			switch (index) {
			case 0:
				data = randdata.nextInt(10);// 仅仅会生成0~9
				sb.append(data);
				break;
			case 1:
				data = randdata.nextInt(26) + 65;// 保证只会产生65~90之间的整数
				sb.append((char) data);
				break;
			case 2:
				data = randdata.nextInt(26) + 97;// 保证只会产生97~122之间的整数
				sb.append((char) data);
				break;
			}
		}
		String result = sb.toString();

		return result;
	}

}
