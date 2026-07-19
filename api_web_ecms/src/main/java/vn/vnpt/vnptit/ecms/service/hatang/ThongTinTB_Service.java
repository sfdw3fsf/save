package vn.vnpt.vnptit.ecms.service.hatang;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.ConnectionManager;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import com.fasterxml.jackson.databind.JsonNode;
import vn.vnpt.vnptit.ecms.dto.hatang.ThietBiPortKhaiBao_DTO;
import vn.vnpt.vnptit.ecms.dto.hatang.ThongTinThietBi_DTO;

@Service
public class ThongTinTB_Service {
    private final DbContext dbContext;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ThongTinTB_Service(DbContext dbContext, JdbcTemplate jdbcTemplate) {
        this.dbContext = dbContext;
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Chuyển đổi chuỗi JSON thành danh sách List<Long>.
     * Nếu dữ liệu null hoặc lỗi, trả về danh sách rỗng.
     */
    private List<Long> parseJsonArray(Object rawData, ObjectMapper objectMapper) {
        List<Long> idsList = new ArrayList<>();
        if (rawData instanceof String) {
            String jsonStr = (String) rawData;
            try {
                idsList = objectMapper.readValue(jsonStr, List.class);
            } catch (Exception e) {
                System.err.println("Lỗi parse JSON: " + e.getMessage());
            }
        }
        return idsList;
    }

    // Get danh sach thong tin thiet bi
    public Object getAllTB() throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_result", Types.REF_CURSOR));
        ArrayList<Map<String, Object>> dbResult = dbContext
                .executeSpWithCursorToListMap("ECMS.PKG_HATANG_DANHMUC_THIETBI.SP_THONG_TIN_TB_GET", params);

        ObjectMapper objectMapper = new ObjectMapper(); // Dùng để parse JSON array

        // List<Map<String, Object>> result = dbResult.stream()
        // .map(map -> map.entrySet().stream().filter(entry -> entry.getValue() !=
        // null).collect(Collectors.toMap(entry -> entry.getKey().toLowerCase(),
        // Map.Entry::getValue))).collect(Collectors.toList());
        List<Map<String, Object>> result = dbResult.stream()
                .map(map -> {
                    Map<String, Object> transformed = map.entrySet().stream()
                            .filter(entry -> entry.getValue() != null)
                            .collect(Collectors.toMap(
                                    entry -> entry.getKey().toLowerCase(),
                                    Map.Entry::getValue));

                    // Xử lý vaitrothietbi_ids từ chuỗi JSON thành List<Long>
                    // if (transformed.containsKey("vaitrothietbi_ids")) {
                    // Object rawData = transformed.get("vaitrothietbi_ids");
                    //
                    // List<Long> idsList = new ArrayList<>();
                    // if (rawData instanceof String) {
                    // String jsonStr = (String) rawData;
                    // try {
                    // idsList = objectMapper.readValue(jsonStr, List.class);
                    // } catch (Exception e) {
                    // System.err.println("Lỗi parse JSON: " + e.getMessage());
                    // }
                    // }
                    // transformed.put("vaitrothietbi_ids", idsList);
                    // } else {
                    // transformed.put("vaitrothietbi_ids", new ArrayList<>()); // Trả về []
                    // }

                    // Xử lý vaitrothietbi_ids từ chuỗi JSON thành List<Long>
                    transformed.put("vaitrothietbi_ids",
                            parseJsonArray(transformed.get("vaitrothietbi_ids"), objectMapper));

                    // Xử lý ip_ids từ chuỗi JSON thành List<Long>
                    transformed.put("ip_ids", parseJsonArray(transformed.get("ip_ids"), objectMapper));

                    return transformed;
                }).collect(Collectors.toList());

        return result;
    }

    public Object searchTB(JsonNode dto) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        String jsonString = dto != null ? dto.toString() : null;
        params.add(new SqlParameter("p_json", jsonString, Types.CLOB));
        params.add(new SqlParameter("p_result", Types.REF_CURSOR));
        ArrayList<Map<String, Object>> dbResult = dbContext
                .executeSpWithCursorToListMap("ECMS.PKG_HATANG_DANHMUC_THIETBI.SP_THONG_TIN_TB_SEARCH", params);

        ObjectMapper objectMapper = new ObjectMapper(); // Dùng để parse JSON array

        // List<Map<String, Object>> result = dbResult.stream()
        // .map(map -> map.entrySet().stream().filter(entry -> entry.getValue() !=
        // null).collect(Collectors.toMap(entry -> entry.getKey().toLowerCase(),
        // Map.Entry::getValue))).collect(Collectors.toList());
        List<Map<String, Object>> result = dbResult.stream().map(map -> {
            Map<String, Object> transformed = map.entrySet().stream().filter(entry -> entry.getValue() != null)
                    .collect(Collectors.toMap(entry -> entry.getKey().toLowerCase(), Map.Entry::getValue));

            // Xử lý vaitrothietbi_ids từ chuỗi JSON thành List<Long>
            transformed.put("vaitrothietbi_ids", parseJsonArray(transformed.get("vaitrothietbi_ids"), objectMapper));

            // Xử lý ip_ids từ chuỗi JSON thành List<Long>
            transformed.put("ip_ids", parseJsonArray(transformed.get("ip_ids"), objectMapper));

            return transformed;
        }).collect(Collectors.toList());
        return result;
    }

    public Object getTBById(Number id) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id", id, Types.NUMERIC));
        params.add(new SqlParameter("p_result", Types.REF_CURSOR));
        ArrayList<Map<String, Object>> dbResult = dbContext
                .executeSpWithCursorToListMap("ECMS.PKG_HATANG_DANHMUC_THIETBI.SP_THONG_TIN_TB_GET_BY_ID", params);

        ObjectMapper objectMapper = new ObjectMapper(); // Dùng để parse JSON array

        // List<Map<String, Object>> result = dbResult.stream()
        // .map(map -> map.entrySet().stream().filter(entry -> entry.getValue() !=
        // null).collect(Collectors.toMap(entry -> entry.getKey().toLowerCase(),
        // Map.Entry::getValue))).collect(Collectors.toList());
        List<Map<String, Object>> result = dbResult.stream().map(map -> {
            Map<String, Object> transformed = map.entrySet().stream().filter(entry -> entry.getValue() != null)
                    .collect(Collectors.toMap(entry -> entry.getKey().toLowerCase(), Map.Entry::getValue));

            // Xử lý vaitrothietbi_ids từ chuỗi JSON thành List<Long>
            transformed.put("vaitrothietbi_ids", parseJsonArray(transformed.get("vaitrothietbi_ids"), objectMapper));

            // Xử lý ip_ids từ chuỗi JSON thành List<Long>
            transformed.put("ip_ids", parseJsonArray(transformed.get("ip_ids"), objectMapper));

            return transformed;
        }).collect(Collectors.toList());

        return result.get(0);
    }

    public Object getTBDauNoi(Integer tb_id) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id", tb_id, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_result", Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_DANHMUC_THIETBI.SP_THONG_TIN_TB_DAU_NOI_GET",
                params);
    }

    public Object getTBHaTang(Integer tb_id) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id", tb_id, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_result", Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_DANHMUC_THIETBI.SP_TT_TB_HA_TANG_GET", params);
    }

    public Object getTBLichSu(Integer ht_id) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id", ht_id, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_result", Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_DANHMUC_THIETBI.SP_TT_TB_LICH_SU_GET", params);
    }

    public Object getTBColocation(Integer id) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_thietbi_id", id, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_result", Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_DANHMUC_THIETBI.SP_TT_TB_COLOCATION_GET",
                params);
    }

    public Object getTBPortKhaiBao(Integer id) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_chungloaiTB_id", id, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_result", Types.REF_CURSOR));
        // return
        // dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_DANHMUC_THIETBI.SP_TT_TB_PORT_KHAI_BAO_GET",
        // params);
        ArrayList<Map<String, Object>> dbResult = dbContext
                .executeSpWithCursorToListMap("ECMS.PKG_HATANG_DANHMUC_THIETBI.SP_TT_TB_PORT_KHAI_BAO_GET", params);

        List<Map<String, Object>> result = dbResult.stream().map(map -> map.entrySet().stream()
                // .filter(entry -> entry.getValue() != null)
                .collect(Collectors.toMap(entry -> entry.getKey().toLowerCase(),
                        // Map.Entry::getValue
                        entry -> entry.getValue() == null ? "" : entry.getValue())))

                .collect(Collectors.toList());
        return result;
    }

    public Object getTBIP(Integer id) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_thietbi_id", id, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_result", Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_DANHMUC_THIETBI.SP_TT_TB_IP_GET", params);
    }

    public Object CreateUpdateTBPortKhaiBao(ThietBiPortKhaiBao_DTO input, Number tb_id) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        // getThietbi_id voi truong hop update, tb_id voi truong hop tao moi thiet bi
        Integer thietbiId = Optional.ofNullable(input.getThietbi_id()).map(Number::intValue).filter(id -> id != 0)
                .orElse(tb_id != null ? tb_id.intValue() : 0);

        params.add(new SqlParameter("p_id", input.getId(), Types.NUMERIC));
        params.add(new SqlParameter("p_thietbi_id", thietbiId, Types.NUMERIC));
        params.add(new SqlParameter("p_phanloai", input.getPhanloai(), Types.NUMERIC));
        params.add(new SqlParameter("p_loaicong_id", input.getLoaicong_id(), Types.NUMERIC));
        params.add(new SqlParameter("p_so_cong", input.getSo_cong(), Types.NUMERIC));
        params.add(new SqlParameter("p_bat_dau", input.getBat_dau(), Types.NUMERIC));
        params.add(new SqlParameter("p_port_fname", input.getPort_fname(), Types.VARCHAR));
        params.add(new SqlParameter("p_mucdichsudung_cong_id", input.getMucdichsudung_cong_id(), Types.NUMERIC));
        params.add(new SqlParameter("p_bangthongcong_id", input.getBangthongcong_id(), Types.NUMERIC));
        params.add(new SqlParameter("p_thongtinmodule_id", input.getThongtinmodule_id(), Types.NVARCHAR));
        params.add(new SqlParameter("p_ketnoi", input.getKetnoi(), Types.NUMERIC));

        SqlParameter result = new SqlParameter("p_reusult", Types.NUMERIC);
        params.add(result);

        dbContext.setConnection(ConnectionManager.Connections.CSS);
        dbContext.executeStoredProcedure("ecms.PKG_HATANG_DANHMUC_THIETBI.SP_TT_TB_PORT_KHAI_BAO_CREATE_UPDATE",
                params);

        return result.getOutValue();
    }

    public boolean deleteTBPortByIds(Number[] ids) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_ids", StringUtils.join(ids, ";"), Types.VARCHAR));
        SqlParameter result = new SqlParameter("p_result", Types.NUMERIC);
        params.add(result);
        dbContext.executeStoredProcedure("ECMS.PKG_HATANG_DANHMUC_THIETBI.SP_TT_TB_PORT_KHAI_BAO_DELETE_MULTI", params);
        return result.getOutValue().toString().equals("1");
    }

    public Object createTB(ThongTinThietBi_DTO input) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        // Thông tin chung
        params.add(new SqlParameter("p_id", input.getId(), Types.NUMERIC));
        params.add(new SqlParameter("p_ten", input.getTen(), Types.NVARCHAR));
        params.add(new SqlParameter("p_kyhieu", input.getKyhieu(), Types.VARCHAR));
        params.add(new SqlParameter("p_mathietbi_infra", input.getMathietbi_infra(), Types.VARCHAR));
        params.add(new SqlParameter("p_thietbicha_id", input.getThietbicha_id(), Types.NUMERIC));
        params.add(new SqlParameter("p_loaithietbi_id", input.getLoaithietbi_id(), Types.NUMERIC));
        params.add(new SqlParameter("p_loaikhachhang", input.getLoaikhachhang(), Types.NUMERIC));
        params.add(new SqlParameter("p_serial_number", input.getSerial_number(), Types.VARCHAR));
        params.add(new SqlParameter("p_model", input.getModel(), Types.VARCHAR));
        params.add(new SqlParameter("p_part_number", input.getPart_number(), Types.VARCHAR));
        params.add(new SqlParameter("p_hsm", input.getHsm(), Types.NUMERIC));
        params.add(new SqlParameter("p_hieu_luc", input.getHieu_luc(), Types.NUMERIC));
        params.add(new SqlParameter("p_ghi_chu", input.getGhi_chu(), Types.NVARCHAR));
        params.add(new SqlParameter("p_loaitudia_id", input.getLoaitudia_id(), Types.NUMERIC));
        params.add(new SqlParameter("p_chungloaithietbi_id", input.getChungloaithietbi_id(), Types.NUMERIC));

        // Đặc tính
        params.add(new SqlParameter("p_hangsx_id", input.getHangsx_id(), Types.NUMERIC));
        params.add(new SqlParameter("p_namsx", input.getNamsx(), Types.NUMERIC));
        params.add(new SqlParameter("p_nhacungcap_id", input.getNhacungcap_id(), Types.NUMERIC));
        params.add(new SqlParameter("p_cs_dinhdanh", input.getCs_dinhdanh(), Types.NUMERIC));
        params.add(new SqlParameter("p_cs_thucte", input.getCs_thucte(), Types.NUMERIC));
        params.add(new SqlParameter("p_hedieuhanh_id", input.getHedieuhanh_id(), Types.NUMERIC));
        params.add(new SqlParameter("p_trangthai_sd", input.getTrangthai_sd(), Types.NUMERIC));
        params.add(new SqlParameter("p_ngay_lapdat", input.getNgay_lapdat(), Types.DATE));
        params.add(new SqlParameter("p_ngay_baohanh", input.getNgay_baohanh(), Types.DATE));
        params.add(new SqlParameter("p_ngay_sudung", input.getNgay_sudung(), Types.DATE));
        // Thiet bi may chu
        params.add(new SqlParameter("p_cpu_sokhe", input.getCpu_sokhe(), Types.NUMERIC));
        params.add(new SqlParameter("p_cpu_dacam", input.getCpu_dacam(), Types.NUMERIC));
        params.add(new SqlParameter("p_cpu_total", input.getCpu_total(), Types.NUMERIC));
        params.add(new SqlParameter("p_hdd_sokhe", input.getHdd_sokhe(), Types.NUMERIC));
        params.add(new SqlParameter("p_hdd_dacam", input.getHdd_dacam(), Types.NUMERIC));
        params.add(new SqlParameter("p_hdd_total", input.getHdd_total(), Types.NUMERIC));
        params.add(new SqlParameter("p_ram_sokhe", input.getRam_sokhe(), Types.NUMERIC));
        params.add(new SqlParameter("p_ram_dacam", input.getRam_dacam(), Types.NUMERIC));
        params.add(new SqlParameter("p_ram_total", input.getRam_total(), Types.NUMERIC));
        // Thiet bi phu tro
        params.add(new SqlParameter("p_psu_qty_in", input.getPsu_qty_in(), Types.NUMERIC));
        params.add(new SqlParameter("p_psu_qty_out", input.getPsu_qty_out(), Types.NUMERIC));
        params.add(new SqlParameter("p_cau_hinh", input.getCau_hinh(), Types.NVARCHAR));
        // Thiet bi tu dia
        params.add(new SqlParameter("p_bodieukhien_qty", input.getBodieukhien_qty(), Types.NUMERIC));
        params.add(new SqlParameter("p_bodieukhien_dau", input.getBodieukhien_dau(), Types.NUMERIC));
        params.add(new SqlParameter("p_bodieukhien_fname", input.getBodieukhien_fname(), Types.NVARCHAR));
        params.add(new SqlParameter("p_khaydia_qty", input.getKhaydia_qty(), Types.NUMERIC));
        params.add(new SqlParameter("p_khaydia_dau", input.getKhaydia_dau(), Types.NUMERIC));
        params.add(new SqlParameter("p_khaydia_fname", input.getKhaydia_fname(), Types.NVARCHAR));

        // Năng lực lưu trữ
        params.add(new SqlParameter("p_hdd_tong", input.getHdd_tong(), Types.NUMERIC));
        params.add(new SqlParameter("p_hdd_nangluc", input.getHdd_nangluc(), Types.NUMERIC));
        params.add(new SqlParameter("p_hdd_capphat", input.getHdd_capphat(), Types.NUMERIC));
        params.add(new SqlParameter("p_ssd_tong", input.getSsd_tong(), Types.NUMERIC));
        params.add(new SqlParameter("p_ssd_nangluc", input.getSsd_nangluc(), Types.NUMERIC));
        params.add(new SqlParameter("p_ssd_capphat", input.getSsd_capphat(), Types.NUMERIC));
        params.add(new SqlParameter("p_iops_tong", input.getIops_tong(), Types.NUMERIC));
        params.add(new SqlParameter("p_iops_nangluc", input.getIops_nangluc(), Types.NUMERIC));
        params.add(new SqlParameter("p_iops_capphat", input.getIops_capphat(), Types.NUMERIC));
        params.add(new SqlParameter("p_mem_cache", input.getMem_cache(), Types.NUMERIC));
        params.add(new SqlParameter("p_flash_cache", input.getFlash_cache(), Types.NUMERIC));

        // Vị trí lắp đặt
        params.add(new SqlParameter("p_idc_id", input.getIdc_id(), Types.NUMERIC));
        params.add(new SqlParameter("p_building_id", input.getBuilding_id(), Types.NUMERIC));
        params.add(new SqlParameter("p_floor_id", input.getFloor_id(), Types.NUMERIC));
        params.add(new SqlParameter("p_zone_id", input.getZone_id(), Types.NUMERIC));
        params.add(new SqlParameter("p_rack_id", input.getRack_id(), Types.NUMERIC));
        params.add(new SqlParameter("p_unit_qty", input.getUnit_qty(), Types.NUMERIC));
        params.add(new SqlParameter("p_up_unit_id", input.getUp_unit_id(), Types.NUMERIC));
        params.add(new SqlParameter("p_down_unit_id", input.getDown_unit_id(), Types.NUMERIC));
        // Thông tin kiểm kê tài sản
        params.add(new SqlParameter("p_project_id", input.getProject_id(), Types.NUMERIC));
        params.add(new SqlParameter("p_project_code", input.getProject_code(), Types.VARCHAR));
        params.add(new SqlParameter("p_so_the_ts", input.getSo_the_ts(), Types.VARCHAR));
        params.add(new SqlParameter("p_donviqly_id", input.getDonviqly_id(), Types.NUMERIC));
        params.add(new SqlParameter("p_phongbanqly_id", input.getPhongbanqly_id(), Types.NUMERIC));
        params.add(new SqlParameter("p_nguoiqly_id", input.getNguoiqly_id(), Types.NUMERIC));
        // Thông tin cổng thiết bị
        // Interface
        params.add(new SqlParameter("p_so_linecard", input.getSo_linecard(), Types.NUMERIC));
        params.add(new SqlParameter("p_so_port_interface", input.getSo_port_interface(), Types.NUMERIC));
        params.add(new SqlParameter("p_so_interface", input.getSo_interface(), Types.NUMERIC));
        params.add(new SqlParameter("p_interface_start", input.getInterface_start(), Types.NUMERIC));
        params.add(new SqlParameter("p_interface_name", input.getInterface_name(), Types.VARCHAR));
        // Power
        params.add(new SqlParameter("p_so_power", input.getSo_power(), Types.NUMERIC));
        params.add(new SqlParameter("p_so_port_power", input.getSo_port_power(), Types.NUMERIC));
        params.add(new SqlParameter("p_so_powerslot", input.getSo_powerslot(), Types.NUMERIC));
        params.add(new SqlParameter("p_power_start", input.getPower_start(), Types.NUMERIC));
        params.add(new SqlParameter("p_power_name", input.getPower_name(), Types.VARCHAR));
        // Console
        params.add(new SqlParameter("p_so_controlcard", input.getSo_controlcard(), Types.NUMERIC));
        params.add(new SqlParameter("p_so_port_console", input.getSo_port_console(), Types.NUMERIC));
        params.add(new SqlParameter("p_so_console", input.getSo_console(), Types.NUMERIC));
        params.add(new SqlParameter("p_console_start", input.getConsole_start(), Types.NUMERIC));
        params.add(new SqlParameter("p_console_name", input.getConsole_name(), Types.VARCHAR));
        // Phan loai card con
        params.add(new SqlParameter("p_is_interface", input.getIs_interface(), Types.NUMERIC));
        params.add(new SqlParameter("p_is_power", input.getIs_power(), Types.NUMERIC));
        params.add(new SqlParameter("p_is_console", input.getIs_console(), Types.NUMERIC));
        params.add(new SqlParameter("p_is_controller", input.getIs_controller(), Types.NUMERIC));
        params.add(new SqlParameter("p_is_khaydia", input.getIs_khaydia(), Types.NUMERIC));
        // Thông tin người tác động
        params.add(new SqlParameter("p_nhanvien_id", AppRequestContext.getCurrent().getNhanVienID(), Types.NUMERIC));
        params.add(new SqlParameter("p_may_cn", AppRequestContext.getCurrent().getClientName(), Types.VARCHAR));
        params.add(new SqlParameter("p_nguoi_cn", AppRequestContext.getCurrent().getUserName(), Types.VARCHAR));
        params.add(new SqlParameter("p_ip_cn", AppRequestContext.getCurrent().getClientIpAddress(), Types.VARCHAR));

        SqlParameter result = new SqlParameter("p_reusult", Types.NUMERIC);
        params.add(result);

        dbContext.setConnection(ConnectionManager.Connections.CSS);
        dbContext.executeStoredProcedure("ecms.PKG_HATANG_DANHMUC_THIETBI.SP_TT_TB_CREATE_UPDATE", params);

        return result.getOutValue();
    }

    public boolean deleteTBByIds(Number[] ids) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_ids", StringUtils.join(ids, ";"), Types.VARCHAR));
        SqlParameter result = new SqlParameter("p_result", Types.NUMERIC);
        params.add(result);
        dbContext.executeStoredProcedure("ECMS.PKG_HATANG_DANHMUC_THIETBI.SP_TT_TB_DELETE_MULTI", params);
        return result.getOutValue().toString().equals("1");
    }

    public Object changeIPRel(Number tb_id, ThongTinThietBi_DTO input) throws AppSqlException {
        String ip_ids = "";

        if (input.getIp_ids().length > 0) {
            ip_ids = Arrays.stream(input.getIp_ids()).mapToObj(String::valueOf).collect(Collectors.joining(","));
        }

        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_tb_id", tb_id.intValue(), Types.NUMERIC));
        params.add(new SqlParameter("p_ip_ids", ip_ids, Types.VARCHAR));

        SqlParameter result = new SqlParameter("p_result", Types.NUMERIC);
        params.add(result);

        dbContext.setConnection(ConnectionManager.Connections.CSS);
        dbContext.executeStoredProcedure("ecms.PKG_HATANG_DANHMUC_THIETBI.SP_CHANGE_IP_REL", params);

        return result.getOutValue();
    }

    public Object changeVaiTroRel(Number tb_id, ThongTinThietBi_DTO input) throws AppSqlException {
        String vaitrothietbi_ids = "";

        if (input.getVaitrothietbi_ids().length > 0) {
            vaitrothietbi_ids = Arrays.stream(input.getVaitrothietbi_ids()).mapToObj(String::valueOf)
                    .collect(Collectors.joining(","));
        }

        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_tb_id", tb_id.intValue(), Types.NUMERIC));
        params.add(new SqlParameter("p_vaitro_ids", vaitrothietbi_ids, Types.VARCHAR));

        SqlParameter result = new SqlParameter("p_result", Types.NUMERIC);
        params.add(result);

        dbContext.setConnection(ConnectionManager.Connections.CSS);
        dbContext.executeStoredProcedure("ecms.PKG_HATANG_DANHMUC_THIETBI.SP_CHANGE_VAITROTHIETBI_REL", params);

        return result.getOutValue();
    }

    public Object createAndUpdateTBWithChangeIP(ThongTinThietBi_DTO input) throws AppSqlException {
        // Tạo thiết bị cha
        Object result = createTB(input);
        // Thay đổi quan hệ IP & vai trò cho thiết bị cha
        changeIPRel((Number) result, input);
        changeVaiTroRel((Number) result, input);

        // Tạo port khai báo cho thiết bị cha
        if (input.getKhaibao_port() != null) {
            for (ThietBiPortKhaiBao_DTO khaibao : input.getKhaibao_port()) {
                CreateUpdateTBPortKhaiBao(khaibao, (Number) result);
            }
        }

        // Xử lý thiết bị con (cards)
        if (input.getCards() != null) {
            for (ThongTinThietBi_DTO card : input.getCards()) {
                // Tạo card
                card.setThietbicha_id((Number) result);
                Object id = createTB(card);
                // Thay đổi quan hệ IP & Thay đổi quan hệ vai trò
                changeIPRel((Number) id, card);
                changeVaiTroRel((Number) id, card);

                if (card.getKhaibao_port() != null) {
                    // Tạo port khai báo
                    for (ThietBiPortKhaiBao_DTO cardPort : card.getKhaibao_port()) {
                        CreateUpdateTBPortKhaiBao(cardPort, (Number) id);
                    }
                }
            }
        }
        System.out.println("Chung loại thiết bị ID: " + input.getChungloaithietbi_id());
        System.out.println("Kết quả: " + result);
        if (input.getChungloaithietbi_id() != null && result != null) {
            Object genResult = genThongTinThietBiTheoChungLoai((Number) result, input.getChungloaithietbi_id());
            System.out.println("Kết quả gen thông tin thiết bị theo chung loại: " + genResult);
        }
        return result;
    }

    public Object getVM(Integer id) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_tb_id", id, Types.NUMERIC));
        params.add(new SqlParameter("p_result", Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ecms.PKG_HATANG_DANHMUC_THIETBI.SP_GET_VM", params);
    }

    public Object getNFS(Integer id) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_tb_id", id, Types.NUMERIC));
        params.add(new SqlParameter("p_result", Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ecms.PKG_HATANG_DANHMUC_THIETBI.SP_GET_NFS", params);
    }

    public Object getThietBiPort(Integer tb_id) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_thietbi_id", tb_id, Types.NUMERIC));
        params.add(new SqlParameter("p_result", Types.REF_CURSOR));
        // return
        // dbContext.executeSpWithCursorToListMap("ecms.PKG_HATANG_DANHMUC_THIETBI.SP_TT_TB_PORT_GET",
        // params);

        ArrayList<Map<String, Object>> dbResult = dbContext
                .executeSpWithCursorToListMap("ecms.PKG_HATANG_DANHMUC_THIETBI.SP_TT_TB_PORT_GET", params);

        List<Map<String, Object>> result = dbResult.stream().map(map -> map.entrySet().stream()
                // .filter(entry -> entry.getValue() != null)
                .collect(Collectors.toMap(entry -> entry.getKey().toLowerCase(),
                        // Map.Entry::getValue
                        entry -> entry.getValue() == null ? "" : entry.getValue())))

                .collect(Collectors.toList());
        return result;
    }

    public Object genThongTinThietBiTheoChungLoai(Number thietbi_id, Number chungloaithietbi_id)
            throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_thietbi_id", thietbi_id, Types.NUMERIC));
        params.add(new SqlParameter("p_chungloaithietbi_id", chungloaithietbi_id, Types.NUMERIC));
        SqlParameter result = new SqlParameter("p_result", Types.NUMERIC);
        params.add(result);

        dbContext.executeStoredProcedure(
                "ecms.PKG_HATANG_DANHMUC_THIETBI.SP_GEN_THONG_TIN_THIET_BI_THEO_CHUNG_LOAI", params);
        return result.getOutValue();
    }
}