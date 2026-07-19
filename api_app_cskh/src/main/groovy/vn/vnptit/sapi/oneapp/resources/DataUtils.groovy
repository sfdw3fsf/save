package vn.vnptit.sapi.oneapp.resources

import com.fasterxml.jackson.databind.ObjectMapper
import groovy.sql.GroovyRowResult
import groovy.sql.OutParameter
import groovy.sql.Sql
import io.swagger.model.dto.FileApp
import io.swagger.model.dto.ThongTinNguoiDung
import io.swagger.model.dto.cdsdiaban.Kri
import oracle.jdbc.OracleTypes
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import vn.vnptit.sapi.oneapp.managed.HttpManaged
import vn.vnptit.sapi.oneapp.managed.ObjectStoragesManaged
import vn.vnptit.sapi.oneapp.managed.OracleManaged
import vn.vnptit.sapi.oneapp.util.Constant
import vn.vnptit.sapi.oneapp.util.Utils

import java.sql.ResultSet
import java.text.SimpleDateFormat

@Component
class DataUtils {

    static OutParameter CURSOR_PARAMETER = { OracleTypes.CURSOR }

    @Autowired
    OracleManaged oracleManaged

    @Autowired
    ObjectStoragesManaged objectStoragesManaged

    private String phanVungId = ""
    private String dataSrc = ""
    private String ma_tinh

    public DataUtils() {}

    public init(String phanVungId, String dataSrc) {
        this.phanVungId = phanVungId
        this.dataSrc = dataSrc
    }

    public init(String phanVungId, String dataSrc, String ma_tinh) {
        this.phanVungId = phanVungId
        this.dataSrc = dataSrc
        this.ma_tinh = ma_tinh
    }

    public String getMaTinh() {
        try {
            String r = ""
            String sqlString = "SELECT TENTAT FROM css.TINH WHERE TINH_ID = ?"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                def ret = sql.firstRow(sqlString, [Long.parseLong(phanVungId)])
                if (!ret.isEmpty()) {
                    r = ret.get("TENTAT").toString()
                }
            }
            return r
        } catch (Exception ex) {
            return null
        }
    }

    public ThongTinNguoiDung ThongTinNguoiDung(long nhanVienId) {
        ThongTinNguoiDung response
        String sqlString = "{ call admin.sp_lay_thongtin_nguoidung(?,?,?) }"
        Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
            sql.call(sqlString, [Long.parseLong(phanVungId), nhanVienId, CURSOR_PARAMETER]) { ResultSet rs ->
                while (rs != null && rs.next()) {
                    response = new ThongTinNguoiDung().convertDataFromDatabase(rs)
                }
            }
            response
        }
    }

    def String lay_ten_kieu_xacthuc(String authMode) {
        String response = ""
        String sqlString = "select ghichu ten_hienthi from ecms.kieu_xt a where lower(kieu_xt)=lower('${authMode}')"
        Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
            GroovyRowResult ret = sql.firstRow(sqlString)
            if (ret != null && ret.hasProperty("TEN_HIENTHI")) {
                response = ret.getProperty("TEN_HIENTHI")
            }
        }
        return response
    }

    Date sysdate() {
        Date retn = null
        String sqlString = "select to_char(sysdate,'dd/mm/yyyy hh24:mi:ss') ngay_cn from dual"
        Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
            def ret = sql.firstRow(sqlString)
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
            retn = formatter.parse(ret.get("ngay_cn").toString())
        }
        retn
    }

    String sysdate_string() {
        String retn = ""
        String sqlString = "select to_char(sysdate,'dd/mm/yyyy hh24:mi:ss') ngay_cn from dual"
        Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
            def ret = sql.firstRow(sqlString)
            retn = ret.get("ngay_cn").toString()
        }
        retn
    }

    void GIAOPHIEU_KN_KHI_HC(long khieunai_id, long phieukn_id, int huonggiao_id, String donvi_giao_id, String donvi_nhan_id, String nhanvien_id, String nguoi_cn, String may_cn) {
        String sqlString = "{ call QLTN.XULY_KHIEUNAI.GIAOPHIEU_KN_KHI_HC(?,?,?,?,?,?,?,?,?) }"
        Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
            sql.call(sqlString, [Long.parseLong(phanVungId), khieunai_id, phieukn_id, huonggiao_id, donvi_giao_id, donvi_nhan_id, nhanvien_id, nguoi_cn, may_cn]) {
            }
        }
    }

    public GroovyRowResult LAY_DS_THAMSO_MD_MATS(String ma_ts) {
        GroovyRowResult row = null
        String sqlString = "select ma_ts, ten_ts, macdinh from admin.thamso_md where phanvung_id = ? and ma_ts = ?"
        Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
            row = sql.firstRow(sqlString, [phanVungId, ma_ts]) {}
        }
        return row
    }

    public List<FileApp> getFileUrls(String moTa, String bucketName) {
        List<FileApp> files = new ArrayList<>()
        if (moTa == null || moTa == "" || moTa.trim() == "" || moTa == "null")
            return files
        if (Utils.isXMLLike(moTa)) {
            List<FileApp> tmp = Utils.getUrlFromXML(moTa)
            for (FileApp file : tmp) {
                String urlTmp = file.getUrl();
                if (urlTmp.contains(Constant.MINIO_STORAGE_KEY)) {
                    urlTmp = objectStoragesManaged.getUrl(urlTmp, bucketName)
                } else if (urlTmp.indexOf("http") == 0) {
                    file.setUrl(urlTmp)
                } else {
                    urlTmp = Constant.BASE_IMAGE_CAB + urlTmp
                }
                file.setUrl(urlTmp)
                files.add(file)
            }
        } else {
            if (moTa.contains("|")) {
                String[] str = moTa.split("\\|")
                for (String url : str) {
                    FileApp file = new FileApp()
                    if (url.contains(Constant.MINIO_STORAGE_KEY) || url.toUpperCase().contains(Constant.MINIO_STORAGE_KEY2)) {
                        url = objectStoragesManaged.getUrl(url, bucketName)
                    } else if (url.indexOf("http") == 0) {
                        file.setUrl(url)
                    } else {
                        url = Constant.BASE_IMAGE_CAB + url
                    }
                    file.setUrl(url)
                    files.add(file)
                }
            } else {
                FileApp file = new FileApp()
                String url = moTa
                if (url.contains(Constant.MINIO_STORAGE_KEY) || url.toUpperCase().contains(Constant.MINIO_STORAGE_KEY2)) {
                    url = objectStoragesManaged.getUrl(url, bucketName)
                } else if (url.indexOf("http") == 0) {
                    file.setUrl(url)
                } else {
                    url = Constant.BASE_IMAGE_CAB + url
                }
                file.setUrl(url)
                files.add(file)
            }
        }
        return files
    }

    public void Log_Action(String username, String computerName, String actionName, String keyParameter, String serverName, String note) {
        String sqlString = "{ call ADMIN.pkg_quantri_hethong.Log_Action(?,?,?,?,?,?,?) }"
        Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
            sql.call(sqlString, [Long.parseLong(phanVungId), username, computerName, actionName,
                                 keyParameter, serverName, note]) {

            }
        }
    }

    int GET_KEYS(String keyName) {
        int response
        String sqlString = "{ ? = call admin.GET_KEYS(?,?,?,?) }"
        Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
            sql.call(sqlString, [Sql.INTEGER, Long.parseLong(phanVungId), keyName, 1, 10]) { Integer rs ->
                response = rs
            }
        }
        return response
    }

    String replaceSchema(String s) {
        return replaceSchema(dataSrc, s)
    }

    static String replaceSchema(String dataSrc, String s) {
//        s = s.toUpperCase().replaceAll(/(\w\.)?PHANVUNG_ID[ ]*=[ ]*(\?)/, "(1 = 1 or 1 = ?)")
//        String maTinh = dataSrc.split("_")[1].toUpperCase()
//        if(maTinh == "KHDN") maTinh= "DBN"
//
//        s = s.replaceAll("ADMIN.", "ADMIN_${maTinh}.");
//        s = s.replaceAll("CSS.", "CSS_${maTinh}.");
//        s = s.replaceAll("QLTN.", "QLTN_${maTinh}.");
//        s = s.replaceAll("TINHCUOC.", "TINHCUOC_${maTinh}.");
//        s = s.replaceAll("CABMAN.", "CABMAN_${maTinh}.");
//        s = s.replaceAll("BCSS.", "BCSS_${maTinh}.");
//        s = s.replaceAll("BAOHONG.", "BAOHONG_${maTinh}.");
//        s = s.replaceAll("ECMS.", "ECMS_${maTinh}.");
//        s = s.replaceAll("FIMS.", "FIMS_${maTinh}.");
//        s = s.replaceAll("DHSX.", "DHSX_${maTinh}.");
//        s = s.replaceAll("HRM.", "HRM_${maTinh}.");
//        s = s.replaceAll("QLVT.", "QLVT_${maTinh}.");
//        s = s.replaceAll("QLSC.", "QLSC_${maTinh}.");
//        s = s.replaceAll("DULIEU.", "DULIEU_${maTinh}.");
        return s;
    }

    public void logKri(String username, String kri) {
        try {
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.execute("insert into css.kri_user_log(TINH_ID,MATINH,USER_LOG,KRI,LOG_DATE) values (?,?,?,?,sysdate)", [phanVungId, ma_tinh, username, kri])
            }
        } catch (Exception ex) {
            ex.printStackTrace()
        }
    }

    String doKiemTheoLo(Map input, Map header){
        String body = new ObjectMapper().writeValueAsString(input)
        String resp = HttpManaged.post([url: "https://api-onebss.vnpt.vn/tichhop-ex/cts/doKiemTheoLo"], header, body)
        return resp
    }
}