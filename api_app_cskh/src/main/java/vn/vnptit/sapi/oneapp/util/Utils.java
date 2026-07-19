package vn.vnptit.sapi.oneapp.util;

import io.swagger.model.dto.FileApp;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    public static boolean isPhone(String phone) {
        Pattern p = Pattern.compile("(84|0[3|5|7|8|9])+([0-9]{8})\\b");
        return p.matcher(phone).matches();
    }

    public static java.sql.Date _toSqlDate(String val, DateFormat df) {
        try {
            if (val != null) {
                return new java.sql.Date(df.parse(val).getTime());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static String getStringDataByKey(ResultSet rs, String key) {
        try {
            return rs.getString(key);
        } catch (Exception ex) {
            return null;
        }
    }

    public static String getStringNumberDataByKey(ResultSet rs, String key) {
        try {
            return rs.getString(key);
        } catch (Exception ex) {
            return "0";
        }
    }

    public static Long getLongDataByKey(ResultSet rs, String key) {
        try {
            return rs.getLong(key);
        } catch (Exception ex) {
            return null;
        }
    }

    public static String convertToDayAndHour(String date) {
        try {
            if (date.contains(".")) {
                date = date.split("\\.")[0];
            }
            String[] dateStr = date.split(" ");
            return dateStr[0].split("-")[2] + "/" + dateStr[0].split("-")[1] + "/" + dateStr[0].split("-")[0] + " "
                    + dateStr[1].split(":")[0] + ":" + dateStr[1].split(":")[1] + ":" + dateStr[1].split(":")[2];
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }

    public static String convertToDayAndHour1(String date) {
        try {
            if (date.contains(".")) {
                date = date.split("\\.")[0];
            }
            String[] dateStr = date.split(" ");
            return dateStr[0].split("-")[2] + "/" + dateStr[0].split("-")[1] + "/" + dateStr[0].split("-")[0] + " "
                    + dateStr[1].split(":")[0] + ":" + dateStr[1].split(":")[1];
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }

    public static String convertToDayAndHour2(String date) {
        try {
            if (date.contains(".")) {
                date = date.split("\\.")[0];
            }
            String[] dateStr = date.split(" ");
            return dateStr[0].split("/")[2] + dateStr[0].split("/")[1] + dateStr[0].split("/")[0];
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }

    public static String convertToDay(String date) {
        try {
            if (date.contains(".")) {
                date = date.split("\\.")[0];
            }
            String[] dateStr = date.split(" ");
            return dateStr[0].split("-")[2] + "/" + dateStr[0].split("-")[1] + "/" + dateStr[0].split("-")[0];
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }

    public static String convertToDayOther(String date) {
        try {
            if (date.contains(".")) {
                date = date.split("\\.")[0];
            }
            String[] dateStr = date.split(" ");
            return dateStr[0].split("-")[2] + "-" + dateStr[0].split("-")[1] + "-" + dateStr[0].split("-")[0];
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }

    public static String convertMessageError(String mes) {
        if (mes != null && !mes.isEmpty()) {
            if (mes.contains("ORA-20001") || mes.contains("ORA-20000") || mes.contains("ORA-20105")) {
                String ret = mes.replace("ORA-20001: ", "")
                        .replace("ORA-20000: ", "")
                        .replace("ORA-20105: ", "");
                return ret.substring(0, ret.indexOf("ORA-"));
            } else {
                return mes;
            }
        }
        return "";
    }

    public static String toUpperCaseFirstWords(String words) {
        return words.substring(0, 1).toUpperCase() + words.substring(1) + " đồng";
    }

    /**
     * return true if the String passed in is something like XML
     *
     *
     * @param inXMLStr a string that might be XML
     * @return true of the string is XML, false otherwise
     */
    public static boolean isXMLLike(String inXMLStr) {
        boolean retBool = false;
        Pattern pattern;
        Matcher matcher;
        // REGULAR EXPRESSION TO SEE IF IT AT LEAST STARTS AND ENDS
        // WITH THE SAME ELEMENT
        final String XML_PATTERN_STR = "<(\\S+?)(.*?)>(.*?)</\\1>";
        // IF WE HAVE A STRING
        if (inXMLStr != null && inXMLStr.trim().length() > 0) {
            // IF WE EVEN RESEMBLE XML
            if (inXMLStr.trim().startsWith("<")) {
                pattern = Pattern.compile(XML_PATTERN_STR, Pattern.CASE_INSENSITIVE | Pattern.DOTALL | Pattern.MULTILINE);
                // RETURN TRUE IF IT HAS PASSED BOTH TESTS
                matcher = pattern.matcher(inXMLStr);
                retBool = matcher.matches();
            }
            // ELSE WE ARE FALSE
        }
        return retBool;
    }

    public static List<FileApp> getUrlFromXML(String html) {
        List<FileApp> urls = new ArrayList<>();
        Document document = Jsoup.parse(html);
        Elements imgs = document.select("img[url]");
        Elements ids = document.select("img[id]");
        for (int i = 0; i < imgs.size(); i++) {
            String id = "-1";
            if (ids != null && ids.size() == imgs.size()) {
                id = ids.get(i).attr("id");
                try {
                    Long.parseLong(id);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    id = "-1";
                }
            }
            urls.add(new FileApp(Long.parseLong(id), imgs.get(i).attr("url")));
        }
        return urls;
    }

    public static byte[] downloadUrl(URL toDownload) {
        try (InputStream stream = toDownload.openStream();
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            byte[] chunk = new byte[4096];
            int bytesRead;
            while ((bytesRead = stream.read(chunk)) > 0) {
                outputStream.write(chunk, 0, bytesRead);
            }
            return outputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] downloadUrl(String filePath, String dhsx_img_url) {
        String url = "http://10.165.11.174:8081/file/".replace("10.165.11.174", dhsx_img_url) + filePath;
        if (filePath.toUpperCase().startsWith("FILES/") || filePath.toUpperCase().startsWith("FILE/")) {
            url = "http://10.165.11.174:8081/".replace("10.165.11.174", dhsx_img_url) + filePath;
        } else if (filePath.toLowerCase().startsWith("http://10.165.11.174:8081/")) {
            url = filePath.replace("10.165.11.174", dhsx_img_url);
        }
        try {
            URL toDownload = new URL(url);
            try (InputStream stream = toDownload.openStream();
                 ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
                byte[] chunk = new byte[4096];
                int bytesRead;
                while ((bytesRead = stream.read(chunk)) > 0) {
                    outputStream.write(chunk, 0, bytesRead);
                }
                return outputStream.toByteArray();
            } catch (IOException ex) {
                ex.printStackTrace();
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static String putUrl(List<String> urls) {
        String CABMAN_TAG = "<cabman_img_data>%s</cabman_img_data>";
        String imageTags = "";
        for (String url : urls) {
            imageTags += "<img id=\"-1\" title=\"\" url=\"" + url + "\" />";
        }
        return String.format(CABMAN_TAG, imageTags);
    }

    public static List<String> getMonthsBetween(String fdate, String tdate) {
        List<String> result = new ArrayList<>();
        String date1 = fdate.split("/")[2] + fdate.split("/")[1];
        String date2 = tdate.split("/")[2] + tdate.split("/")[1];

        DateFormat formater = new SimpleDateFormat("yyyyMM");

        Calendar beginCalendar = Calendar.getInstance();
        Calendar finishCalendar = Calendar.getInstance();

        try {
            beginCalendar.setTime(formater.parse(date1));
            finishCalendar.setTime(formater.parse(date2));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        while (beginCalendar.before(finishCalendar) || beginCalendar.equals(finishCalendar)) {
            // add one month to date per loop
            result.add(formater.format(beginCalendar.getTime()).toUpperCase());
            beginCalendar.add(Calendar.MONTH, 1);
        }
        return result;
    }
}
