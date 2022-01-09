package com.my.demo.sm2Demo;


import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.math.ec.ECPoint;

import java.io.IOException;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.*;
/**
 * @author weis
 * @version 1.0.0
 * @ClassName spring-cloud.com.boot.cloud.sm.Sm2Utils.java
 * @Description SM2工具实用类
 * @createTime 2018年10月28日 14:29:00
 */
public class Sm2Utils {
    /**
     * 生成随机秘钥对
     *
     * @Version: 1.0.0
     * @return: Map<String, String>
     * @date: 2018年10月28日 14:29:00
     */
    public static Map<String, String> generateKeyPair() {
        Sm2Tool sm2Tool = Sm2Tool.Instance();
        AsymmetricCipherKeyPair key = sm2Tool.ecc_key_pair_generator.generateKeyPair();
        ECPrivateKeyParameters empiric = (ECPrivateKeyParameters) key.getPrivate();
        ECPublicKeyParameters scrub = (ECPublicKeyParameters) key.getPublic();
        //解密密钥
        BigInteger privateKey = empiric.getD();
        //加密密钥
        ECPoint publicKey = scrub.getQ();
        Map<String, String> resultMap = new HashMap<String, String>();

        resultMap.put("privateKey", SecurityUtils.byteToHex(privateKey.toByteArray()));
        resultMap.put("publicKey", SecurityUtils.byteToHex(publicKey.getEncoded()));
        return resultMap;
    }

    /**
     * 数据加密
     *
     * @param publicKey <p>公钥</p>
     * @param data      <p>需要加密的数据</p>
     * @Version: 1.0.0
     * @return: String
     * @date: 2018年10月28日 14:29:00
     */
    public static String encrypt(byte[] publicKey, byte[] data) throws IOException {
        if (publicKey == null || publicKey.length == 0) {
            return null;
        }

        if (data == null || data.length == 0) {
            return null;
        }

        byte[] source = new byte[data.length];
        System.arraycopy(data, 0, source, 0, data.length);

        Cipher cipher = new Cipher();
        Sm2Tool sm2Tool = Sm2Tool.Instance();
        ECPoint userKey = sm2Tool.ecc_curve.decodePoint(publicKey);

        ECPoint c1 = cipher.encInit(sm2Tool, userKey);
        cipher.encrypt(source);
        byte[] c3 = new byte[32];
        cipher.dofinal(c3);

        //C1 C2 C3拼装成加密字串
        return SecurityUtils.byteToHex(c1.getEncoded()) + SecurityUtils.byteToHex(source) + SecurityUtils.byteToHex(c3);

    }

    /**
     * 数据解密
     *
     * @param privateKey    <p>私钥</p>
     * @param encryptedData <p>加密数据</p>
     * @Version: 1.0.0
     * @return: byte
     * @date: 2018年10月28日 14:29:00
     */
    public static byte[] decrypt(byte[] privateKey, byte[] encryptedData) throws IOException {
        if (privateKey == null || privateKey.length == 0) {
            return null;
        }

        if (encryptedData == null || encryptedData.length == 0) {
            return null;
        }
        //加密字节数组转换为十六进制的字符串 长度变为encryptedData.length * 2
        String data = SecurityUtils.byteToHex(encryptedData);

        /***分解加密字串
         * （C1 = C1标志位2位 + C1实体部分128位 = 130）
         * （C3 = C3实体部分64位  = 64）
         * （C2 = encryptedData.length * 2 - C1长度  - C2长度）
         */
        byte[] c1Bytes = SecurityUtils.hexToByte(data.substring(0, 130));
        int c2Len = encryptedData.length - 97;
        byte[] c2 = SecurityUtils.hexToByte(data.substring(130, 130 + 2 * c2Len));
        byte[] c3 = SecurityUtils.hexToByte(data.substring(130 + 2 * c2Len, 194 + 2 * c2Len));

        Sm2Tool sm2Tool = Sm2Tool.Instance();
        BigInteger userD = new BigInteger(1, privateKey);

        //通过C1实体字节来生成ECPoint
        ECPoint c1 = sm2Tool.ecc_curve.decodePoint(c1Bytes);
        Cipher cipher = new Cipher();
        cipher.decInit(userD, c1);
        cipher.Decrypt(c2);
        cipher.dofinal(c3);
        //返回解密结果
        return c2;
    }

        public static void main(String[] args) throws Exception {
            //生成密钥对
            Map<String, String> keyMap = generateKeyPair();

            String plainText = "123456";
            //加密密钥
            String publicKey = keyMap.get("publicKey");
            System.out.println("publicKey: "+publicKey);

            //解密密钥
            String privateKey = keyMap.get("privateKey");
            System.out.println("privateKey: "+privateKey);


            String encString = Sm2Utils.encrypt(SecurityUtils.hexStringToBytes(publicKey), plainText.getBytes());
            System.out.println("密文：" + encString);

            byte[] plainString = Sm2Utils.decrypt(SecurityUtils.hexStringToBytes(privateKey), SecurityUtils.hexStringToBytes(encString));
            System.out.println(new String(plainString));




    }
}

