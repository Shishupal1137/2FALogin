package testData;

import de.taimos.totp.TOTP;
import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Hex;

public class OTPGenerator {

    // Method to generate OTP using secret key
    public static String getOTP(String secretKey) {
        try {
            Base32 base32 = new Base32();
            byte[] bytes = base32.decode(secretKey);
            String hexKey = Hex.encodeHexString(bytes);
            return TOTP.getOTP(hexKey); // Generates current OTP
        } catch (Exception e) {
            throw new RuntimeException("Error generating OTP", e);
        }
    }
}
