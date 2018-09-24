package com.gtri.icl.nij.disclose.Managers;

import android.os.Build;
import android.util.Log;
import android.security.keystore.KeyProperties;
import android.security.keystore.KeyGenParameterSpec;

import java.util.List;
import java.security.KeyPair;
import java.security.KeyStore;
import java.io.FileInputStream;
import java.security.Signature;
import java.security.PrivateKey;
import java.io.BufferedInputStream;
import java.security.KeyPairGenerator;
import java.security.spec.ECGenParameterSpec;

import com.gtri.icl.nij.disclose.API.Media;

public class SignatureManager
{
    public static void generateKeystore()
    {
        try
        {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KeyProperties.KEY_ALGORITHM_EC, "AndroidKeyStore");

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            {
                keyPairGenerator.initialize(
                        new KeyGenParameterSpec.Builder(
                                "disclose",
                                KeyProperties.PURPOSE_SIGN | KeyProperties.PURPOSE_VERIFY)
                                .setAlgorithmParameterSpec(new ECGenParameterSpec("secp256r1"))
                                .setDigests(KeyProperties.DIGEST_SHA256,
                                        KeyProperties.DIGEST_SHA384,
                                        KeyProperties.DIGEST_SHA512)
                                // Only permit the private key to be used if the user authenticated
                                // within the last five minutes.
                                // .setUserAuthenticationRequired(true)
                                // .setUserAuthenticationValidityDurationSeconds(5 * 60)
                                .build());

                KeyPair keyPair = keyPairGenerator.generateKeyPair();

                Signature signature = Signature.getInstance("SHA256withECDSA");

                signature.initSign(keyPair.getPrivate());
            }
            else
            {
                Log.d("xxx", "Unable to initialize key pair generator.");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static byte[] createSignature(List<Media> mediaList)
    {
        try
        {
            generateKeystore();

            Signature signature = Signature.getInstance("SHA256withECDSA");

            KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");

            keyStore.load(null);

            PrivateKey privateKey = (PrivateKey) keyStore.getKey("disclose", null);

            signature.initSign(privateKey);

            for (Media media : mediaList)
            {
                FileInputStream fileInputStream = new FileInputStream(media.filePath);

                BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);

                byte[] buffer = new byte[1024];

                int len;

                while ((len = bufferedInputStream.read(buffer)) >= 0)
                {
                    signature.update(buffer, 0, len);
                }

                bufferedInputStream.close();
            }

            return signature.sign();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return null;
    }
}
