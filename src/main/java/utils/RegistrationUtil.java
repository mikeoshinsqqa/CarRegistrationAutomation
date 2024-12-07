package utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrationUtil
{

    public static List<String> getVehicleRegistrations(String inputText)
    {
        String pattern = "\\b([A-Z]{2}\\d{2}[A-Z]{1,3}|[A-Z]{1,2}\\d{1,4}[A-Z]{1,3}|[A-Z]{1}[A-Z]{2}\\d{1,4}|[A-Z]{1,2}\\d{1,4}\\s?[A-Z]{1,3})\\b";
        List<String> registrationNumbers = new ArrayList<>();

        Pattern regexPattern = Pattern.compile(pattern);
        Matcher matcher = regexPattern.matcher(inputText);

        while (matcher.find())
        {
            String registration = matcher.group().replaceAll(" ", "");
            registrationNumbers.add(registration);
        }

        return registrationNumbers;
    }

    public static String getRandomMileage()
    {
        Random random = new Random();
        int mileage = (random.nextInt(100000) + 1);
        return Integer.toString(mileage);
    }

}
