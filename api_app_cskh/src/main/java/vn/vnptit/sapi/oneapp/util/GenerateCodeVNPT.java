package vn.vnptit.sapi.oneapp.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by VietNH on 12/08/2021.
 * Copyright © 2021 VNPT IT. All rights reserved.
 */
public class GenerateCodeVNPT {

    public static String responseStart =
            "package io.swagger.model.dto;\n" +
            "\n" +
            "import com.fasterxml.jackson.annotation.JsonProperty;\n" +
            "import io.swagger.v3.oas.annotations.media.Schema;\n" +
            "import org.springframework.validation.annotation.Validated;\n" +
            "\n" +
            "import javax.annotation.Generated;\n" +
            "\n" +
            "/**\n" +
            " * %s\n" +
            " */\n" +
            "@Validated\n" +
            "@Generated(value = \"io.swagger.codegen.v3.generators.java.SpringCodegen\", date = \"%s\")\n" +
            "@Schema\n" +
            "public class %s {";

    public static String responseEnd = "}";

    public static String generateResponse(String className, String responseString) {
        StringBuilder code = new StringBuilder(String.format(responseStart, className, new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()), className) + "\n");
        String[] responseArray = responseString.split(",");
        for (String str : responseArray) {
            code.append(generateProperty(str.trim().toLowerCase())).append("\n\n");
        }
        for (String str : responseArray) {
            code.append(generateGetterAndSetter(str.trim().toLowerCase())).append("\n\n");
        }
        code.append(responseEnd);
        return code.toString();
    }

    public static String generateGetterAndSetter(String propertyName) {
        String code = "    public %s get%s() {\n" +
                "        return %s;\n" +
                "    }\n" +
                "\n" +
                "    public void set%s(%s %s) {\n" +
                "        this.%s = %s;\n" +
                "    }";
        if (propertyName.contains("_")) {
            String[] strArr = propertyName.split("_");
            StringBuilder propertyNameBuilder = new StringBuilder();
            for (int i = 0; i < strArr.length; i++) {
                if (i == 0) {
                    propertyNameBuilder.append(strArr[i]);
                } else {
                    String str = strArr[i];
                    propertyNameBuilder.append(str.substring(0, 1).toUpperCase()).append(str.substring(1).toLowerCase());
                }
            }
            propertyName = propertyNameBuilder.toString();
            String propertyType = propertyName.toLowerCase().endsWith("id") ? "Long" : "String";
            String setAndGetName = propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
            code = String.format(code, propertyType, setAndGetName, propertyName,
                    setAndGetName, propertyType, propertyName, propertyName, propertyName);
        } else {
            String propertyType = propertyName.toLowerCase().endsWith("id") ? "Long" : "String";
            String setAndGetName = propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
            code = String.format(code, propertyType, setAndGetName, propertyName,
                    setAndGetName, propertyType, propertyName, propertyName, propertyName);
        }
        return code;
    }

    public static String generateProperty(String propertyName) {
        String code = "    @JsonProperty(\"%s\")\n" +
                "    private %s %s = null;";
        if (propertyName.contains("_")) {
            String[] strArr = propertyName.split("_");
            StringBuilder propertyNameBuilder = new StringBuilder();
            for (int i = 0; i < strArr.length; i++) {
                if (i == 0) {
                    propertyNameBuilder.append(strArr[i]);
                } else {
                    String str = strArr[i];
                    propertyNameBuilder.append(str.substring(0, 1).toUpperCase()).append(str.substring(1).toLowerCase());
                }
            }
            propertyName = propertyNameBuilder.toString();
        }
        code = String.format(code, propertyName, propertyName.endsWith("id") ? "Long" : "String", propertyName);
        return code;
    }

    public static void main(String[] args) {
        System.out.println(generateResponse("Demo", "phieu_id, a.loaiphieu_id, dangky_id, donhang_id, chungtu_id, quyettoan_id, denghi_id, huonggiao_id, donvi_giao_id, nhanvien_giao_id, donvi_nhan_id, nhanvien_th_id, ngaygiao, ngay_th, nd_thuchien, ttph_id, otp, phieu_cha_id, lydotra_id, ngay_tra, nd_tra, ghichu, mota, ngay_cn, nguoi_cn, a.may_cn, a.ip_cn, ngay_yc, TRANGTHAI_ID, donvi_giao, donvi_nhan, nhanvien_giao,nhanvien_th, nhiemvu_id, nhiemvu, MA, loaiphieu, TRANGTHAI, trangthai_ph,lydotra, sophieu,ngay_ct, tongtien, kho_giao, kho_nhan, mota_cuoi"));
    }
}