package ua.dragunov.watchlist.config.security;

import ua.dragunov.watchlist.config.PropertyConfigProvider;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.util.Base64;
import java.util.Properties;

public class PasswordEncryptor implements EncryptorProvider<String, String>{
    private static final String ALGORITHM = "AES";
    private final Properties properties = PropertyConfigProvider.getPropertyConfig();

    @Override
    public String encode(String password) {
        String encodedPassword = null;
        try {

            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, getSecretKey());

            encodedPassword = Base64.getEncoder().encodeToString(cipher.doFinal(password.getBytes()));

        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException |
                 BadPaddingException e) {
            throw new RuntimeException(e);
        }
        return encodedPassword;
    }

    @Override
    public String decode(String password) {
        String decodedPassword = null;
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, getSecretKey());

            decodedPassword = new String(cipher.doFinal(Base64.getDecoder().decode(password)));

        } catch (NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException |
                 InvalidKeyException e) {
            throw new RuntimeException(e);
        }


        return decodedPassword;
    }

    private SecretKey getSecretKey() throws NoSuchAlgorithmException {
        return new SecretKeySpec(properties.getProperty("secret_key").getBytes(), ALGORITHM);
    }

}
