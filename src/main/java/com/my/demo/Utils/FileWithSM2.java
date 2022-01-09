package com.my.demo.Utils;
import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;

@Slf4j
public class FileWithSM2 {
    public static class EncryptionTools {

        private static cn.hutool.crypto.asymmetric.SM2 SM_2 = null;


        static {
            try {
                BouncyCastleProvider BC = new BouncyCastleProvider();
                String publicKeyStr = "MFkwEwYHKoZIzj0CAQYIKoEcz1UBgi0DQgAE6/ZmNaBRnZnisjtfxxh2r5F7MXjr2Rjf6wi5++WkUqU0APNadWN+jcZZeupwrkpOaS" +
                        "+epBpYFwaHhlMGIbUzKw==";
                String privateKeyStr = "MIGTAgEAMBMGByqGSM49AgEGCCqBHM9VAYItBHkwdwIBAQQgsTwYNqhtSjlpYDQnaKaEQbn9kVdSW3RJnFejbKTYa5KgCgYIKoEcz1UBgi2hRANCAATr9mY1oFGdmeKyO1/HGHavkXsxeOvZGN/rCLn75aRSpTQA81p1Y36Nxll66nCuSk5pL56kGlgXBoeGUwYhtTMr";
                KeyFactory keyFactory = KeyFactory.getInstance("EC", BC);
                PublicKey publicKey = keyFactory.generatePublic(new X509EncodedKeySpec(Base64.decode(publicKeyStr)));
                PrivateKey privateKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(Base64.decode(privateKeyStr)));
                SM_2 = SmUtil.sm2(privateKey, publicKey);
            } catch (Exception e) {
                log.info("SM2密钥初始化错误", e);
            }

        }


        /**
         * 文件加/解密
         *
         * @param out      输出路径
         * @param fileName 输出文件名
         */
        public static void encryption(MultipartFile file, String out, String fileName) throws IOException {

            byte[] bytes = FileUtils02.fileToByte02(file);
            byte[] data;
            data = SM_2.encrypt(bytes, KeyType.PublicKey);
            FileUtils02.byteToFile(data, out, fileName);
        }

        public static void decryption(String source, String fileName) throws IOException {
            String out = "D:\\test_decryption";
            byte[] bytes = FileUtils02.fileToByte(source+"\\"+fileName);
            byte[] data;
            data = SM_2.decrypt(bytes, KeyType.PrivateKey);
            FileUtils02.byteToFile(data, out, fileName);
        }





    }
}
