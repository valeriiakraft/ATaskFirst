import com.github.javafaker.Faker;
import lombok.Value;

import java.util.Locale;


public class DataHelper {

    private static Faker faker = new Faker(new Locale("en"));

    public DataHelper() {
    }

    public static PayInfo getPayInfo() {
        return new PayInfo(getPhoneNumber(), getSum(), getMail());

    }

    public static String getPhoneNumber() {
        return "297777777";
    }

    public static String getSum() {

        return "250.00";

    }

    public static String getMail() {


        return faker.internet().emailAddress();

    }


    @Value
    public static class PayInfo {
        String phoneNumber;
        String sum;
        String mail;


    }
}
