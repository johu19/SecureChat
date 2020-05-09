package Client;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class EncryterDecrypter {
	private static String cipherAlgoritm="AES";
	private static String cipherAlgoritmType="AES/ECB/PKCS5Padding";
	

	//encrypta una cadena con una clave recibida
	public static String DoEncrypt(String msgToEncrypt, long claveSecreta) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
        SecretKeySpec secretKey = crearClave(claveSecreta+"");
        
        Cipher cipher = Cipher.getInstance(cipherAlgoritmType);        
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] datosEncriptar = msgToEncrypt.getBytes("UTF-8");
        byte[] bytesEncriptados = cipher.doFinal(datosEncriptar);
        String encriptado = Base64.getEncoder().encodeToString(bytesEncriptados);
 
        return encriptado;
    }
	//encrypta una cadena con una clave recibida
	public static String doDecrypt(String encryptText, long desKey) throws Exception {
		
		SecretKeySpec secretKey = crearClave(desKey+"");
		 
        Cipher cipher = Cipher.getInstance(cipherAlgoritmType);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
         
        byte[] bytesEncriptados = Base64.getDecoder().decode(encryptText);
        byte[] datosDesencriptados = cipher.doFinal(bytesEncriptados);
        String datos = new String(datosDesencriptados);
         
        return datos;
	}
	//genera una clave privada AES con tamaño de 16 bytes
	private static SecretKeySpec crearClave(String clave) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        byte[] claveEncriptacion = clave.getBytes("UTF-8");
         
        MessageDigest sha = MessageDigest.getInstance("SHA-1");
         
        claveEncriptacion = sha.digest(claveEncriptacion);
        claveEncriptacion = Arrays.copyOf(claveEncriptacion, 16);
         
        SecretKeySpec secretKey = new SecretKeySpec(claveEncriptacion, cipherAlgoritm);
 
        return secretKey;
    }
 
	
}
