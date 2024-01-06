import cn.newworld.util.EncryptionUtils;

import java.sql.Connection;
import java.sql.Statement;

public class test {
    public static void main(String[] args) {
        String string = EncryptionUtils.hashWithSalt("newworldstudio", "eatfan_Qwq");
        System.out.println(string);

        Connection connection = null;
    }
}
