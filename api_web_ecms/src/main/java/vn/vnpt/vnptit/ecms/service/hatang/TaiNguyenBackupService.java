package vn.vnpt.vnptit.ecms.service.hatang;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.ConnectionManager;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.hatang.BackupMayAoDTO;
import vn.vnpt.vnptit.ecms.dto.hatang.TaiNguyenBackupDTO;

@Service
public class TaiNguyenBackupService {
        @Autowired
        private DbContext dbContext;

        public Object createOrUpdate(TaiNguyenBackupDTO dto) throws AppSqlException {
                List<SqlParameter> params = new ArrayList<>();
                params.add(new SqlParameter("p_id", dto.getId(), Types.NUMERIC));
                params.add(new SqlParameter("p_ma", dto.getMa(), Types.NVARCHAR));
                params.add(new SqlParameter("p_ten", dto.getTen(), Types.NVARCHAR));
                params.add(new SqlParameter("p_hatangbackup_id", dto.getHatangbackup_id(), Types.NUMERIC));
                params.add(new SqlParameter("p_backup_loai_id", dto.getBackup_loai_id(), Types.NUMERIC));
                params.add(new SqlParameter("p_tansuat_id", dto.getTansuat_id(), Types.NUMERIC));
                params.add(new SqlParameter("p_ip_backup_id", dto.getIp_backup_id(), Types.NUMERIC));
                params.add(new SqlParameter("p_link_backup", dto.getLink_backup(), Types.NVARCHAR));
                params.add(new SqlParameter("p_phieuyeucau_id", dto.getPhieuyeucau_id(), Types.NUMERIC));
                params.add(new SqlParameter("p_ghi_chu", dto.getGhi_chu(), Types.NVARCHAR));
                params.add(new SqlParameter("p_hieu_luc", dto.getHieu_luc(), Types.NUMERIC));
                params.add(new SqlParameter("p_loaihatang_luutru", dto.getLoaihatang_luutru(), Types.NUMERIC));
                params.add(new SqlParameter("p_hatang_luutru_id", dto.getHatang_luutru_id(), Types.NUMERIC));
                params.add(new SqlParameter("p_nangluc", dto.getNangluc(), Types.NUMERIC));
                params.add(new SqlParameter("p_loailuutru", dto.getLoailuutru(), Types.NUMERIC));
                params.add(new SqlParameter("p_dungluong", dto.getDungluong(), Types.NUMERIC));
                params.add(new SqlParameter("p_donviqly_id", dto.getDonviqly_id(), Types.NUMERIC));
                params.add(new SqlParameter("p_phongbanqly_id", dto.getPhongbanqly_id(), Types.NUMERIC));
                params.add(new SqlParameter("p_nguoiqly_id", dto.getNguoiqly_id(), Types.NUMERIC));
                params.add(new SqlParameter("p_nhanvien_id", dto.getNhanvien_id(), Types.NUMERIC));
                params.add(new SqlParameter("p_may_capnhap", dto.getMay_capnhap(), Types.NUMERIC));
                params.add(new SqlParameter("p_nguoi_capnhap", dto.getNguoi_capnhap(), Types.NUMERIC));
                params.add(new SqlParameter("p_ip_capnhap", dto.getIp_capnhap(), Types.NUMERIC));
                SqlParameter result = new SqlParameter("p_result", Types.NUMERIC);
                params.add(result);
                dbContext.setConnection(ConnectionManager.Connections.CSS);
                dbContext.executeStoredProcedure("ECMS.PKG_HATANG_TAINGUYEN_QUANLY.SP_TAINGUYEN_BACKUP_INSERT_UPDATE",
                                params);
                return result.getOutValue();
        }

        public Object getAll() throws AppSqlException {
                List<SqlParameter> params = new ArrayList<>();
                params.add(new SqlParameter("p_result", Types.REF_CURSOR));
                ArrayList<Map<String, Object>> dbResult = dbContext.executeSpWithCursorToListMap(
                                "ECMS.PKG_HATANG_TAINGUYEN_QUANLY.SP_TAINGUYEN_BACKUP_GETALL", params);
                List<Map<String, Object>> result = dbResult.stream()
                                .map(map -> map.entrySet()
                                                .stream()
                                                .filter(entry -> entry.getValue() != null)
                                                .collect(Collectors.toMap(
                                                                entry -> entry.getKey().toLowerCase(),
                                                                Map.Entry::getValue)))
                                .collect(Collectors.toList());
                return result;
        }

        public Object deleteById(Number id) {
                try {
                        List<SqlParameter> params = new ArrayList<>();
                        params.add(new SqlParameter("p_id", id, Types.NUMERIC));
                        params.add(new SqlParameter("p_table_name", "BACKUP", Types.NVARCHAR));
                        params.add(new SqlParameter("p_result", Types.NUMERIC));
                        dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_TAINGUYEN_QUANLY.SP_DELETE_BY_ID",
                                        params);
                } catch (Exception e) {
                        e.printStackTrace();
                        return Boolean.FALSE;
                }
                return Boolean.TRUE;
        }

        public Object taiNguyenBackupRelationship(Number id, String tabName) throws AppSqlException {
                List<SqlParameter> params = new ArrayList<>();
                params.add(new SqlParameter("p_object_id", id, Types.NUMERIC));
                params.add(new SqlParameter("p_table_name", tabName, Types.NVARCHAR));
                params.add(new SqlParameter("p_result", Types.REF_CURSOR));
                ArrayList<Map<String, Object>> dbResult = dbContext.executeSpWithCursorToListMap(
                                "ECMS.PKG_HATANG_TAINGUYEN_QUANLY.SP_TAINGUYEN_BACKUP_RELATIONSHIP", params);
                List<Map<String, Object>> result = dbResult.stream()
                                .map(map -> map.entrySet()
                                                .stream()
                                                .filter(entry -> entry.getValue() != null)
                                                .collect(Collectors.toMap(
                                                                entry -> entry.getKey().toLowerCase(),
                                                                Map.Entry::getValue)))
                                .collect(Collectors.toList());
                return result;
        }

        public Object getPhieuCapTheoTrangThai(int trangthai) throws AppSqlException {
                List<SqlParameter> params = new ArrayList<>();
                params.add(new SqlParameter("p_trangthai", trangthai, Types.NUMERIC));
                params.add(new SqlParameter("p_result", Types.REF_CURSOR));
                ArrayList<Map<String, Object>> dbResult = dbContext.executeSpWithCursorToListMap(
                                "ECMS.PKG_HATANG_TAINGUYEN_QUANLY.SP_PHIEU_CAPPHAT_HOANTHANH", params);
                List<Map<String, Object>> result = dbResult.stream()
                                .map(map -> map.entrySet()
                                                .stream()
                                                .filter(entry -> entry.getValue() != null)
                                                .collect(Collectors.toMap(
                                                                entry -> entry.getKey().toLowerCase(),
                                                                Map.Entry::getValue)))
                                .collect(Collectors.toList());
                return result;
        }

        public Object getDanhSachMayAo(String nameSearch, Long backupId) throws AppSqlException {
                List<SqlParameter> params = new ArrayList<>();
                params.add(new SqlParameter("P_NAME_SEARCH", nameSearch, Types.NVARCHAR));
                params.add(new SqlParameter("P_BACKUP_ID", backupId, Types.NUMERIC));
                params.add(new SqlParameter("P_RS", Types.REF_CURSOR));
                ArrayList<Map<String, Object>> dbResult = dbContext.executeSpWithCursorToListMap(
                                "ECMS.PKG_HATANG_TAINGUYEN_AOHOA.SP_GET_DS_MAYAO", params);
                List<Map<String, Object>> result = dbResult.stream()
                                .map(map -> map.entrySet()
                                                .stream()
                                                .filter(entry -> entry.getValue() != null)
                                                .collect(Collectors.toMap(
                                                                entry -> entry.getKey().toLowerCase(),
                                                                Map.Entry::getValue)))
                                .collect(Collectors.toList());
                return result;
        }

        public Object themMayAoBackup(BackupMayAoDTO dto) throws AppSqlException {
                List<SqlParameter> params = new ArrayList<>();
                params.add(new SqlParameter("P_BACKUP_ID", dto.getBackupId(), Types.NUMERIC));
                params.add(new SqlParameter("P_MAYAO_IDS", dto.getMayAoIds(), Types.NVARCHAR));
                params.add(new SqlParameter("P_RS", Types.NUMERIC));
                dbContext.setConnection(ConnectionManager.Connections.CSS);
                dbContext.executeStoredProcedure("ECMS.PKG_HATANG_TAINGUYEN_AOHOA.SP_THEM_MAYAO_BACKUP",
                                params);
                return params.get(2).getOutValue();
        }

        public Object xoaMayAoBackup(BackupMayAoDTO dto) throws AppSqlException {
                List<SqlParameter> params = new ArrayList<>();
                params.add(new SqlParameter("P_BACKUP_ID", dto.getBackupId(), Types.NUMERIC));
                params.add(new SqlParameter("P_MAYAO_IDS", dto.getMayAoIds(), Types.NVARCHAR));
                params.add(new SqlParameter("P_RS", Types.NUMERIC));
                dbContext.setConnection(ConnectionManager.Connections.CSS);
                dbContext.executeStoredProcedure("ECMS.PKG_HATANG_TAINGUYEN_AOHOA.SP_DELETE_MAYAO_BACKUP",
                                params);
                return params.get(2).getOutValue();
        }
}
