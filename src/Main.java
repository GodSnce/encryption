public class Main {

    public static void main(String[] args) {

        String password = "As1234@1.0";
        //随机产生密钥
        String miyao = generatorKey.createRandomCharData(24);
        //加密
        String encodeSMS4 = EncryptUtil.encodeSMS4(password, miyao.getBytes());
        //解密
        String decodeSMS4toString = EncryptUtil.decodeSMS4toString(encodeSMS4, miyao.getBytes());

        System.out.println("密钥：" + miyao + "\n" + "加密：" + encodeSMS4 + "\n" + "解密：" + decodeSMS4toString);
    }
}
