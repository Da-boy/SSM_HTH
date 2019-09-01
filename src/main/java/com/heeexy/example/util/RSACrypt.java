package com.heeexy.example.util;

import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class RSACrypt {

    /**
     * 生成RAS公钥与私钥字符串，直接返回
     *
     * @return
     */
    public static HashMap<String, String> getKeys() {
        HashMap<String, String> map = new HashMap<String, String>();
        KeyPairGenerator keyPairGen = null;
        try {
            keyPairGen = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // 初始化密钥对生成器，密钥大小为96-1024位
        keyPairGen.initialize(1024, new SecureRandom());
        // 生成一个密钥对，保存在keyPair中
        KeyPair keyPair = keyPairGen.generateKeyPair();
        // 得到公钥字符串
        String publicKey = base64ToStr(keyPair.getPublic().getEncoded());
        // 得到私钥字符串
        String privateKey = base64ToStr(keyPair.getPrivate().getEncoded());
        map.put("publicKey", publicKey);
        map.put("privateKey", privateKey);
        return map;
    }

    /**
     * 根据公钥字符串加载公钥
     *
     * @param publicKeyStr 公钥字符串
     * @return
     * @throws Exception
     */
    public static RSAPublicKey loadPublicKey(String publicKeyStr) throws Exception {
        try {
            byte[] buffer = javax.xml.bind.DatatypeConverter.parseBase64Binary(publicKeyStr);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
            return (RSAPublicKey) keyFactory.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此算法", e);
        } catch (InvalidKeySpecException e) {
            throw new Exception("公钥非法", e);
        } catch (NullPointerException e) {
            throw new Exception("公钥数据为空", e);
        }
    }

    /**
     * 根据私钥字符串加载私钥
     *
     * @param privateKeyStr 私钥字符串
     * @return
     * @throws Exception
     */
    public static RSAPrivateKey loadPrivateKey(String privateKeyStr) throws Exception {
        try {
            byte[] buffer = javax.xml.bind.DatatypeConverter.parseBase64Binary(privateKeyStr);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此算法", e);
        } catch (InvalidKeySpecException e) {
            throw new Exception("私钥非法", e);
        } catch (NullPointerException e) {
            throw new Exception("私钥数据为空", e);
        }
    }

    /**
     * 公钥加密
     *
     * @param publicKey     公钥
     * @param plainTextData 明文数据
     * @return
     * @throws Exception 加密过程中的异常信息
     */
    public static String encrypt(RSAPublicKey publicKey, byte[] plainTextData) throws Exception {
        if (publicKey == null) {
            throw new Exception("加密公钥为空, 请设置");
        }
        Cipher cipher = null;
        try {
            // 使用默认RSA
            cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] output = cipher.doFinal(plainTextData);
            return base64ToStr(output);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此加密算法");
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            return null;
        } catch (InvalidKeyException e) {
            throw new Exception("加密公钥非法,请检查");
        } catch (IllegalBlockSizeException e) {
            throw new Exception("明文长度非法");
        } catch (BadPaddingException e) {
            throw new Exception("明文数据已损坏");
        }
    }


    /**
     * 私钥解密
     *
     * @param privateKey 私钥
     * @param cipherData 密文数据
     * @return 明文
     * @throws Exception 解密过程中的异常信息
     */
    public static String decrypt(RSAPrivateKey privateKey, byte[] cipherData) throws Exception {
        if (privateKey == null) {
            throw new Exception("解密私钥为空, 请设置");
        }
        Cipher cipher = null;
        try {
            // 使用默认RSA
            cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] output = cipher.doFinal(cipherData);
            return new String(output);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此解密算法");
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            return null;
        } catch (InvalidKeyException e) {
            throw new Exception("解密私钥非法,请检查");
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
            throw new Exception("密文长度非法");
        } catch (BadPaddingException e) {
            e.printStackTrace();
            throw new Exception("密文数据已损坏");
        }
    }

    public static String base64ToStr(byte[] b) {
        return javax.xml.bind.DatatypeConverter.printBase64Binary(b);
    }

    public static byte[] strToBase64(String str) {
        return javax.xml.bind.DatatypeConverter.parseBase64Binary(str);
    }
    public static void main(String[] args) throws Exception {
        //初始化阶段，初始化后生成秘钥对
        //公钥发送给消息发送方用于加密传输数据；私钥严格保存于消息接收方，收到加密的消息之后进行解密
        HashMap<String, String> map = RSACrypt.getKeys();
//        String privateKeyStr=map.get("privateKey");
//        String publicKeyStr=map.get("publicKey");
        String privateKeyStr = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCd1i6VlLp6ymqQRHBokABNu8ZbZ+9p5GXGPDWWX1/s4cdPYyozpmcmJOfe0aHwUZMToIxzcjvxP+DeCla/+l/g9hSZRw9eMp/7cRpZ4IlDyi8vCMFlsga2XVsVfcFDM0VMdvJmWSlQL0tzHT6fsM2aj+QRbNp72UUUu/1YgA3TiiNhyYo7YNUVLEeHr84Mgw8hHAW4NQ4Ty+RlXU9fv9HjxewgfsUlN8gGFAHnsyijX27Ye7o/IkjcDAAElCmwrIjSO0nSvQxfehmWRBv7lcIuDlVlMYYSdtIidL39Cj6ixRCIkxNu277227pcPVYAdPBBj4KENrQZ8ga8BVeNCLx9AgMBAAECggEBAIgcX+fWbkndrK/klRtWsyxVteS1aJzkvW+KFJwse0iIG5y2Y3pPEbyvLtXT6daYQkE2vmgAJvkQoRZZzB2aCHyzOWhFA1e0SIpJKvQ+0aCIBtCEuGK+d1NkhluuKq+M00HxgXAJi7r+Tg5jNdH4YhXb6E2ulWsgHpaW1w8uvr2gkrGZVr1iprJCF83cFwKiYsFi9TP8voov2J7HMcRua7gUqz8WC8KtfuCkijg9bIELT7nliCszziqBZ3I71+PHn+tphym/jEQJcs4Mx9TxHYv/Ox+VuRm9tG/T62lx1xeJ0od4IWSYqvC5jpp8/s0oCd7SgGrfjnlGlQBWuKY9KQECgYEA2hko7YN0UbrYNW/pchXjvtguwxzwuQEdW559XLN336qGVx9aJym5WEXfNR804sfrQlm2Icu6x6xSLjiZFZMSUmr0D0+kFnNmUcB2S+QQCGoHozZoORcjrsucfkbK1FkRuKji/SVzLkq8dgNo/p8P4BT+KADLeiFQOc5TBwjVRQ0CgYEAuUQUVc6xX4KDIkXsfssc43JNYvitrnUqILV7S7XUKqb5TWsYgDplhCSRl12Td+0jeCdX1z9CNcQDAq7QErjh5EaDURwGlD7tNgSEGOKhIMiygIrvPQDf3+41Dn7DLCJg8qgRvkfXRdqKR4kiyivSpl2dFHEeeCIHU+Zp/4DJWTECgYBN5g6oRvuU9Zej/oTzKr5du/l31y5j5fIGd2VvZuq6CL5S/+/DbTO2Q76lyq+pEJ8G4+QZzhq4luQDflOvUQiKR4lErr19B1rUeFnIYX0YhEaWFSjEu28TT8ZllAN+NRIPUsNAh8/MCQWnYTBvQYD6GIVu5FnJFX0CKJ+fM5h5QQKBgHQmAVW2aq9FRG81RUpMjoTtwSR+YWzRWaL+BBl4GlbPLdbigI7LjZ9lyb0K1oxGKImIqOJyb1ED3RtOe3ZxYHVb27H7Dwjb1p2aATMUyg2ipX4/HDOzm6dpUOwQfs3pk7wtsEoKAiVyGGU2zY/QH5VbdUI+lOqd8tgfyW580D7RAoGBAKX9JPy2RKeED4xsdr3qPHL+FOr5FBmY2GFd1DOUXgShQHwG2lPqjEFXsBomBuzfKjcLMLKDNhlsHa/76m1gVbSnpyCtwYT3T3ZHzdwDl65HSXZ2dclFwHoszHo+L5w9bvvtO+PFDZDKo4Lm9ZfFJh2PZ+Usv1Sn+pTxoUvrYr86";
        String publicKeyStr = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAndYulZS6espqkERwaJAATbvGW2fvaeRlxjw1ll9f7OHHT2MqM6ZnJiTn3tGh8FGTE6CMc3I78T/g3gpWv/pf4PYUmUcPXjKf+3EaWeCJQ8ovLwjBZbIGtl1bFX3BQzNFTHbyZlkpUC9Lcx0+n7DNmo/kEWzae9lFFLv9WIAN04ojYcmKO2DVFSxHh6/ODIMPIRwFuDUOE8vkZV1PX7/R48XsIH7FJTfIBhQB57Moo19u2Hu6PyJI3AwABJQpsKyI0jtJ0r0MX3oZlkQb+5XCLg5VZTGGEnbSInS9/Qo+osUQiJMTbtu+9tu6XD1WAHTwQY+ChDa0GfIGvAVXjQi8fQIDAQAB";
        System.out.println("初始化私钥为："+privateKeyStr);
        System.out.println("初始化共钥为："+publicKeyStr);

        //消息发送方
        String originData="周末约你看电影吧";
        System.out.println("信息原文："+originData);
        String encryptData=RSACrypt.encrypt(RSACrypt.loadPublicKey(publicKeyStr),originData.getBytes());
        System.out.println("加密后："+encryptData);

        //消息接收方
        String decryptData=RSACrypt.decrypt(RSACrypt.loadPrivateKey(privateKeyStr),RSACrypt.strToBase64(encryptData));
        System.out.println("解密后："+decryptData);
    }
}
