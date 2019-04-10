package ir.ac.iust.appstore.view.widget;

import java.util.ArrayList;
import java.util.List;

public class FormatHelper
{
    private static String currentAppLanguage="fa";
    private static List<String> persianNumbers;

    static
    {
        persianNumbers = new ArrayList<String>();
        persianNumbers.add("۰");
        persianNumbers.add("۱");
        persianNumbers.add("۲");
        persianNumbers.add("۳");
        persianNumbers.add("۴");
        persianNumbers.add("۵");
        persianNumbers.add("۶");
        persianNumbers.add("۷");
        persianNumbers.add("۸");
        persianNumbers.add("۹");
    }

    public static String convertEnglishNumberToPersian(String str)
    {
        if ("".equals(str))
            return "";

        int length = str.length();
        String result = "";
        for (int i = 0; i < length; i++)
        {
            char charAt = str.charAt(i);
            if ('0' <= charAt && charAt <= '9')
                result = result + persianNumbers.get(Integer.parseInt(String.valueOf(charAt)));
            else if (charAt == '\u066b')
                result = result + '\u060c';
            else
                result = result + charAt;
        }
        return result;
    }

    public static String convertPersianNumberToEnglish(String str)
    {
        if ("".equals(str))
            return "";

        int length = str.length();
        String result = "";
        for (int i = 0; i < length; i++)
        {
            char charAt = str.charAt(i);
            if ('۰'<= charAt && charAt <= '۹')
            {
                result = result + persianNumbers.indexOf(String.valueOf(charAt));
            }
            else if (charAt == '\u060c')
            {
                result = result + '\u066b';
            }
            else
                result = result + charAt;
        }
        return result;
    }

    public static String mapNumberCharacters(String str)
    {
        if (currentAppLanguage.equals("fa"))
            return convertEnglishNumberToPersian(str);
        else
            return str;
    }

    public static void setCurrentAppLanguage(String currentAppLanguage)
    {
        FormatHelper.currentAppLanguage = currentAppLanguage;
    }
}
