package vn.vnpt.vnptit.ecms.service.hatang;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.hatang.GoDNIDGDto;

@Service
public class SoDoLogicService {
    private final DbContext dbContext;

    public SoDoLogicService(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    public Object getThongTinIDC(Number id) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id", id, Types.NUMERIC));
        params.add(new SqlParameter("p_result", Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToMap("ECMS.PKG_HATANG_SODO.SP_GET_THONGTIN_IDC", params);
    }

    public Object getThongTinHTAH(Number id) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id", id, Types.NUMERIC));
        params.add(new SqlParameter("p_result", Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToMap("ECMS.PKG_HATANG_SODO.SP_GET_THONGTIN_HTAH", params);
    }

    public Object getThongTinHTCLOUD(Number id) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id", id, Types.NUMERIC));
        params.add(new SqlParameter("p_result", Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToMap("ECMS.PKG_HATANG_SODO.SP_GET_THONGTIN_HTCLOUD", params);
    }

    public Object getThongTinHTIDG(Number id) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id", id, Types.NUMERIC));
        params.add(new SqlParameter("p_result", Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToMap("ECMS.PKG_HATANG_SODO.SP_GET_THONGTIN_HTIDG", params);
    }

    public Object getThongTinHTM(Number id) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id", id, Types.NUMERIC));
        params.add(new SqlParameter("p_result", Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToMap("ECMS.PKG_HATANG_SODO.SP_GET_THONGTIN_HTM", params);
    }

    public Object getThongTinHaTang(Number kieu, Number idc_id) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_TYPE", kieu, Types.NUMERIC));
        params.add(new SqlParameter("P_ID_IDC", idc_id, Types.NUMERIC));
        params.add(new SqlParameter("p_result", Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_SODO.SP_GET_HATANGMANG_BY_IDC", params);
    }
    
    public Object getDanhSachThietBi(Number kieu, Number hatang_id) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_TYPE", kieu, Types.NUMERIC));
        params.add(new SqlParameter("P_ID_HATANG", hatang_id, Types.NUMERIC));
        params.add(new SqlParameter("p_result", Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_SODO.SP_GET_THIETBI_BY_HATANGMANG", params);
    }
    
    public Object getDanhSachThietBiIDG(Number id) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id", id, Types.NUMERIC));
        params.add(new SqlParameter("p_result", Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_SODO.SP_GET_DS_THIETBI_IDG", params);
    }
    
    public Object getDanhSachThietBiAH(Number id) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id", id, Types.NUMERIC));
        params.add(new SqlParameter("p_result", Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_SODO.SP_GET_DS_THIETBI_AH", params);
    }
    
    public Object getDanhSachThietBiHTM(Number id) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id", id, Types.NUMERIC));
        params.add(new SqlParameter("p_result", Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_SODO.SP_GET_DS_THIETBI_HTM", params);
    }
    
    public Object getThongTinSoDo(Number id) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id", id, Types.NUMERIC));
        params.add(new SqlParameter("p_result", Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_SODO.SP_GET_THONGTIN_SODO", params);
    }
    
    public Object getDanhSachIDC() throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_result", Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_SODO.SP_GET_DANHSACH_IDC", params);
    }
    
    public Object getDanhSachHTAH() throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_result", Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_SODO.SP_GET_DANHSACH_HTAH", params);
    }
    
    public Object getDanhSachHTCLOUD() throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_result", Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_SODO.SP_GET_DANHSACH_HTCLOUD", params);
    }
    
    public Object getDanhSachHTIDG() throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_result", Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_SODO.SP_GET_DANHSACH_HTIDG", params);
    }
    
    public Object getDanhSachHTAHChuaDN() throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_result", Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_SODO.SP_GET_DANHSACH_HTAH_CHUADN", params);
    }
    
    public Object getDanhSachHTAHChuaDNbyHTM(Number hatangmangID) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_hatangmang_id", hatangmangID, Types.NUMERIC));
        params.add(new SqlParameter("p_result", Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_SODO.SP_GET_DANHSACH_HTAH_CHUADN_BY_HTM", params);
    }
    
    public Object getDanhSachHTCLOUDChuaDN() throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_result", Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_SODO.SP_GET_DANHSACH_HTCLOUD_CHUADN", params);
    }
    
    public Object getDanhSachHTCLOUDChuaDNbyHTM(Number hatangmangID) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_hatangmang_id", hatangmangID, Types.NUMERIC));
        params.add(new SqlParameter("p_result", Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_SODO.SP_GET_DANHSACH_HTCLOUD_CHUADN_BY_HTM", params);
    }
    
    public Object getDanhSachHTIDGChuaDN(Number id, Boolean doiNoiHTAH) throws AppSqlException {
        if(doiNoiHTAH)
        	return this.getDanhSachHTIDGChuaDNHTAH(id);
        else 
        	return this.getDanhSachHTIDGChuaDNHTCLOUD(id);
    }
    
    public Object getDanhSachHTIDGChuaDNHTAH(Number hatangaohoa_id) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_hatangaohoa_id", hatangaohoa_id, Types.NUMERIC));
        params.add(new SqlParameter("p_result", Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_SODO.SP_GET_DANHSACH_HTIDG_CHUADN_HTAH", params);
    }
    
    public Object getDanhSachHTIDGChuaDNHTCLOUD(Number hatangsmartcloud_id) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_hatangsmartcloud_id", hatangsmartcloud_id, Types.NUMERIC));
        params.add(new SqlParameter("p_result", Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_SODO.SP_GET_DANHSACH_HTIDG_CHUADN_HTCLOUD", params);
    }
    
    public Object getDanhSachDichVuHTM(Number id) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id", id, Types.NUMERIC));
        params.add(new SqlParameter("p_result", Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_SODO.SP_GET_DS_DICHVU_HTM", params);
    }
    
    public Object getDanhSachDichVuHTAH(Number id) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id", id, Types.NUMERIC));
        params.add(new SqlParameter("p_result", Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_SODO.SP_GET_DS_DICHVU_HTAH", params);
    }
    
    public Object getDanhSachDichVuHTCLOUD(Number id) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id", id, Types.NUMERIC));
        params.add(new SqlParameter("p_result", Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_SODO.SP_GET_DS_DICHVU_HTCLOUD", params);
    }
    
    public Object getDanhSachDichVuHTIDG(Number id) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id", id, Types.NUMERIC));
        params.add(new SqlParameter("p_result", Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_SODO.SP_GET_DS_DICHVU_HTIDG", params);
    }
    
    public Object getDanhSachKhachHangHTAH(Number id) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id", id, Types.NUMERIC));
        params.add(new SqlParameter("p_result", Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_SODO.SP_GET_DS_KHACHHANG_HTAH", params);
    }
    
    public Object getDanhSachKhachHangHTM(Number id) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id", id, Types.NUMERIC));
        params.add(new SqlParameter("p_result", Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_SODO.SP_GET_DS_KHACHHANG_HTM", params);
    }
    
    public Object dauNoiHTMvsHTAH(Number hatangmangID, Number hatangaohoaID) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_hatangmang_id", hatangmangID, Types.NUMERIC));
        params.add(new SqlParameter("p_hatangaohoa_id", hatangaohoaID, Types.NUMERIC));
        return dbContext.executeFunction("ECMS.PKG_HATANG_SODO.FN_DAUNOI_HTM_HTAH", java.sql.Types.NUMERIC, params).toString().equals("1");
    }
    
    public Object dauNoiHTMvsHTCLOUD(Number hatangmangID, Number hatangsmartcloud_id) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_hatangmang_id", hatangmangID, Types.NUMERIC));
        params.add(new SqlParameter("p_hatangsmartcloud_id", hatangsmartcloud_id, Types.NUMERIC));
        return dbContext.executeFunction("ECMS.PKG_HATANG_SODO.FN_DAUNOI_HTM_HTCLOUD", java.sql.Types.NUMERIC, params).toString().equals("1");
    }
    
    public Object dauNoiHTAHvsHTIDG(Number p_hatangaohoa_id, Number hatangidg_id) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_hatangaohoa_id", p_hatangaohoa_id, Types.NUMERIC));
        params.add(new SqlParameter("p_hatangidg_id", hatangidg_id, Types.NUMERIC));
        return dbContext.executeFunction("ECMS.PKG_HATANG_SODO.FN_DAUNOI_HTAH_HTIDG", java.sql.Types.NUMERIC, params).toString().equals("1");
    }
    
    public Object dauNoiHTCLOUDvsHTIDG(Number hatangsmartcloud_id, Number hatangidg_id) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_hatangsmartcloud_id", hatangsmartcloud_id, Types.NUMERIC));
        params.add(new SqlParameter("p_hatangidg_id", hatangidg_id, Types.NUMERIC));
        return dbContext.executeFunction("ECMS.PKG_HATANG_SODO.FN_DAUNOI_HTCLOUD_HTIDG", java.sql.Types.NUMERIC, params).toString().equals("1");
    }
    
    public Object goDauNoiHTMvsHTAH(Number id) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_hatangaohoa_id", id, Types.NUMERIC));
        return dbContext.executeFunction("ECMS.PKG_HATANG_SODO.FN_GODAUNOI_HTM_HTAH", java.sql.Types.NUMERIC, params).toString().equals("1");
    }
    
    public Object goDauNoiHTMvsHTCLOUD(Number id) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_hatangsmartcloud_id", id, Types.NUMERIC));
        return dbContext.executeFunction("ECMS.PKG_HATANG_SODO.FN_GODAUNOI_HTM_HTCLOUD", java.sql.Types.NUMERIC, params).toString().equals("1");
    }
    
    public Object goDauNoiHTIDG(Number id) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_hatangidg_id", id, Types.NUMERIC));
        return dbContext.executeFunction("ECMS.PKG_HATANG_SODO.FN_GODAUNOI_HTIDG", java.sql.Types.NUMERIC, params).toString().equals("1");
    }
    
    public Object goDauNoiDsHTvsIDG(Number haTangIDGID, GoDNIDGDto[] items) throws AppSqlException {
    	int count = 0;
    	for (int i = 0; i < items.length; i++) {
			if(items[i].getPhanLoai() == 0) {
				//Gỡ hạ tầng ảo hóa
				if(((Boolean)this.goDauNoiHTAHIDG(haTangIDGID, items[i].getId())).booleanValue())
					count ++;
			}
			else {
				//Gỡ hạ tầng cloud
				if(((Boolean)this.goDauNoiHTCLOUDIDG(haTangIDGID, items[i].getId())).booleanValue())
					count ++;
			}
		}
        return count;
    }
    
    public Object goDauNoiHTAHIDG(Number haTangIDGID, Number haTangAoHoaID) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_hatangidg_id", haTangIDGID, Types.NUMERIC));
        params.add(new SqlParameter("p_hatangaohoa_id", haTangAoHoaID, Types.NUMERIC));
        return dbContext.executeFunction("ECMS.PKG_HATANG_SODO.FN_GODAUNOI_HTIDG_HTAH", java.sql.Types.NUMERIC, params).toString().equals("1");
    }
    
    public Object goDauNoiHTCLOUDIDG(Number haTangIDGID, Number haTangSmartCloudID) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_hatangidg_id", haTangIDGID, Types.NUMERIC));
        params.add(new SqlParameter("p_hatangsmartcloud_id", haTangSmartCloudID, Types.NUMERIC));
        return dbContext.executeFunction("ECMS.PKG_HATANG_SODO.FN_GODAUNOI_HTIDG_HTCLOUD", java.sql.Types.NUMERIC, params).toString().equals("1");
    }

    public Object getAllHaTangByIDC(Number idc_id) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id_idc", idc_id, Types.NUMERIC));
        params.add(new SqlParameter("p_result", Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_SODO.GET_ALL_HATANG_BY_IDC", params);
    }

    public Object getSPDVByThietBi(Number thietbi_id) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_thietbi_id", thietbi_id, Types.NUMERIC));
        params.add(new SqlParameter("p_result", Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_SODO.GET_SPDV_BY_THIETBI", params);
    }

    public Object getKHByHietBi(Number thietbi_id) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_thietbi_id", thietbi_id, Types.NUMERIC));
        params.add(new SqlParameter("p_result", Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_SODO.GET_KHACHHANG_BY_THIETBI", params);
    }

    public Object getDsHaTangDNvsIDG(Number hatangidgID) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_hatangidg_id", hatangidgID, Types.NUMERIC));
        params.add(new SqlParameter("p_result", Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_SODO.SP_GET_DSHT_DN_IDG", params);
    }

    public Object getThietBiChaCon(Number thietbiId) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_device_id", thietbiId, Types.NUMERIC));
        params.add(new SqlParameter("p_result", Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_SODO.get_parrent_and_child_device_by_device_id", params);
    }
    
    public Object getCayThietBi(Number id) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id", id, Types.NUMERIC));
        params.add(new SqlParameter("p_result", Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_SODO.SP_GET_CAY_THIETBI", params);
    }
}
