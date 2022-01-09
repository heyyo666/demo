package com.my.demo.test;
import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.SM2;
import com.my.demo.Utils.FileUtils02;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.HashMap;

@Slf4j
public class Test02 {
    public static class EncryptionTools {

        private static SM2 SM_2 = null;
        SM2 sm2 = new SM2();



        static {
            try {
                SM2 sm2 = new SM2();
                String privateKeyBase64 = sm2.getPrivateKeyBase64();
                String publicKeyBase64 = sm2.getPublicKeyBase64();
                System.out.println(privateKeyBase64.length());
                BouncyCastleProvider BC = new BouncyCastleProvider();
                String publicKeyStr = "MFkwEwYHKoZIzj0CAQYIKoEcz1UBgi0DQgAE6/ZmNaBRnZnisjtfxxh2r5F7MXjr2Rjf6wi5++WkUqU0APNadWN+jcZZeupwrkpOaS" +
                        "+epBpYFwaHhlMGIbUzKw==";
                String privateKeyStr = "MIGTAgEAMBMGByqGSM49AgEGCCqBHM9VAYItBHkwdwIBAQQgsTwYNqhtSjlpYDQnaKaEQbn9kVdSW3RJnFejbKTYa5KgCgYIKoEcz1UBgi2hRANCAATr9mY1oFGdmeKyO1/HGHavkXsxeOvZGN/rCLn75aRSpTQA81p1Y36Nxll66nCuSk5pL56kGlgXBoeGUwYhtTMr";
                KeyFactory keyFactory = KeyFactory.getInstance("EC", BC);
//                PublicKey publicKey = keyFactory.generatePublic(new X509EncodedKeySpec(Base64.decode(publicKeyStr)));
//                PrivateKey privateKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(Base64.decode(privateKeyStr)));
                PublicKey publicKey = keyFactory.generatePublic(new X509EncodedKeySpec(Base64.decode(publicKeyBase64)));
                PrivateKey privateKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(Base64.decode(privateKeyBase64)));
                SM_2 = SmUtil.sm2(privateKey, publicKey);
            } catch (Exception e) {
                log.info("SM2密钥初始化错误", e);
            }

        }


        /**
         * 文件加/解密
         *
         * @param source   原始文件路径
         * @param out      输出路径
         * @param fileName 输出文件名
         * @param action   行为（true为加密，false为解密）
         */
        public static void encryptionOrDecryption(String source, String out, String fileName, boolean action) throws IOException {

            byte[] bytes = FileUtils02.fileToByte(source);
            byte[] data;
            if (action) {
                data = SM_2.encrypt(bytes, KeyType.PublicKey);
                System.out.println("PublicKey:"+KeyType.PublicKey);
            } else {
                data = SM_2.decrypt(bytes, KeyType.PrivateKey);
                System.out.println("PrivateKey:"+KeyType.PrivateKey);
            }
            FileUtils02.byteToFile(data, out, fileName);
        }

        public static ArrayList<String> filesList(String filsPath){
                ArrayList<String> files = new ArrayList<String>();
                File file = new File(filsPath);
                File[] tempList = file.listFiles();

                for (int i = 0; i < tempList.length; i++) {
                    if (tempList[i].isFile()) {
                        System.out.println(tempList[i].getName());
                        System.out.println(tempList[i].getAbsolutePath());
                        files.add(tempList[i].getName());
                    }

                }

                return files;
            }




        /**
         * 测试方法
         */
        public static void main(String[] args) throws IOException {
            // 加密
           encryptionOrDecryption("D:\\test\\test.html", "D:\\test", "test01.html", true);
             //解密
           encryptionOrDecryption("D:\\test\\test01.html", "D:\\test", "test02.html", false);
            //filesList("d:\\test");
        }

    }
}
