package com.cmbchina.o2o.wd.onlinemarket.config;


import com.cmbchina.o2o.wd.onlinemarket.util.Encryption;
import org.springframework.security.crypto.password.PasswordEncoder;

public class Md5Encoder implements PasswordEncoder {
    @Override
    public String encode(CharSequence charSequence) {
        // DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
        return Encryption.encodeMd5(charSequence.toString());
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {
        int start = s.lastIndexOf('{');
        int end = s.lastIndexOf('}');
        if (start < 0 || end < 0) {
            return this.encode(charSequence).equals(s);
        }
        charSequence = charSequence.toString() + s.substring(start + 1, end);
        return this.encode(charSequence).equals(s.substring(0, start));
    }
}
