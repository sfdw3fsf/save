package vn.vnpt.vnptit.qlsc.dto.mnv;

import com.fasterxml.jackson.annotation.JsonProperty;
import vn.vnpt.common.Utils;
import vn.vnpt.database.AppSqlException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PhanHoiYeuCauDTO {
    private long phanVungId;
    private long suCoId;
    private long xuLyId;

    private String noiDung;

    private int isTiepNhan;  //1:tiếp nhận else từ chối
    private int isDoKiem;    //1:có đo kiểm else không đo kiểm

    private List<FileDinhKem> dsFileDinhKem = new ArrayList<>();
    private List<ThongTinDoKiem> dsThongTinDoKiem = new ArrayList<>();

    public enum LOAI_KIEMTRA {
        LINK_MAN_E_BTS,
        CONG_OLT,
        CRC_LUULUONG,
        KET_QUA
    }

    public void valid() throws AppSqlException {
        List<String> errList = new ArrayList<>();
        if (this.phanVungId==0) errList.add("Tham số phân vùng id là bắt buộc.");
        if (this.suCoId==0) errList.add("Tham số sự cố id là bắt buộc.");
        if (this.xuLyId==0) errList.add("Tham số xử lý id là bắt buộc.");
        if (Utils.isEmpty(this.noiDung)) errList.add("Vui lòng nhập Nội dung.");

        // valid file đính kèm
        if (!this.dsFileDinhKem.isEmpty()) {
            boolean f = this.dsFileDinhKem.stream().anyMatch(file -> Utils.isEmpty(file.getPath())||Utils.isEmpty(file.getTenFile()));
            if (f) errList.add("File đính kèm không hợp lệ.");
        }

        if (this.isTiepNhan == 1 && this.isDoKiem == 1 && this.dsThongTinDoKiem.isEmpty()) {
            errList.add("Cần thêm thông tin đo kiểm thiết bị khi tích 'Có đo kiểm'!");
        } else if (this.isTiepNhan == 1 && this.isDoKiem == 1) {
            for (ThongTinDoKiem item : this.dsThongTinDoKiem) {
                LOAI_KIEMTRA loai;
                try {
                    loai = LOAI_KIEMTRA.valueOf(item.getLoai());
                } catch (IllegalArgumentException e) {
                    errList.add(String.format("loại đo kiểm '%s' không khớp.", item.getLoai()));
                    continue;
                }
                if (!loai.equals(LOAI_KIEMTRA.CRC_LUULUONG) && !loai.equals(LOAI_KIEMTRA.KET_QUA)) {
                    if (Utils.isEmpty(item.getSystemName())) errList.add(String.format("Loại -- '%s' Vui lòng nhập System.", loai.name()));

                    if (Utils.isEmpty(item.getCardName())) errList.add(String.format("Loại -- '%s' Vui lòng nhập Card.", loai.name()));

                    if (Utils.isEmpty(item.getPortName())) errList.add(String.format("Loại -- '%s' Vui lòng nhập Port.", loai.name()));

                    if (Utils.isEmpty(item.getKetQua())) errList.add(String.format("Loại -- '%s' Vui lòng chọn kết quả.", loai.name()));
                } else if (loai.equals(LOAI_KIEMTRA.KET_QUA)) {
                    if (Utils.isEmpty(item.getKetQua())) errList.add(String.format("Loại -- '%s' Vui lòng chọn kết quả.", loai.name()));
                } else {
                    if (Utils.isEmpty(item.getCrcResult())) errList.add(String.format("Loại -- '%s' Vui lòng nhập CRC.", loai.name()));
                    if (Utils.isEmpty(item.getLuuLuongPortResult())) errList.add(String.format("Loại -- '%s' Vui lòng nhập Lưu lượng port.", loai.name()));
                }

                if (loai.equals(LOAI_KIEMTRA.LINK_MAN_E_BTS)) {
                    if (Utils.isEmpty(item.getTxVal())) errList.add(String.format("Loại -- '%s' Vui lòng nhập Tx.", loai.name()));

                    if (Utils.isEmpty(item.getRxVal())) errList.add(String.format("Loại -- '%s' Vui lòng nhập Rx.", loai.name()));

                } else if (loai.equals(LOAI_KIEMTRA.CONG_OLT)) {
                    if (Utils.isEmpty(item.getTxVal())) errList.add(String.format("Loại -- '%s' Vui lòng nhập Tx.", loai.name()));

                    if (Utils.isEmpty(item.getRxNgoaiNguong()) || Utils.isEmpty(item.getRxTrongNguong()))  errList.add(String.format("Loại -- '%s' Vui lòng nhập Rx.", loai.name()));

                }
            }
        }


        if (errList.isEmpty()) return;
        String errMsg = IntStream.range(0, errList.size())
                .mapToObj(i -> (i + 1) + ". " + errList.get(i))
                .collect(Collectors.joining("\r\n", "", "\r\n"));
        throw new AppSqlException(errMsg);
    }


    public static class FileDinhKem {
        @JsonProperty("TEN_FILE")
        private String tenFile;
        @JsonProperty("PATH")
        private String path;

        public String getTenFile() {
            return tenFile;
        }

        public String getPath() {
            return path;
        }
    }

    public static class ThongTinDoKiem {
        @JsonProperty("STT")
        private int stt;
        @JsonProperty("LOAI")
        private String loai;

        // Thông tin chung
        @JsonProperty("SYSTEM_NAME")
        private String systemName;
        @JsonProperty("CARD_NAME")
        private String cardName;
        @JsonProperty("PORT_NAME")
        private String portName;

        //Thông số Tx/Rx (dùng cho Link Man E/BTS và OLT)
        @JsonProperty("TX_VALUE")
        private String txVal;
        @JsonProperty("RX_VALUE")
        private String rxVal;

        // Dành riêng cho OLT
        @JsonProperty("RX_TRONG_NGUONG")
        private String rxTrongNguong;//Đạt/Không đạt
        @JsonProperty("RX_NGOAI_NGUONG")
        private String rxNgoaiNguong;//Đạt/Không đạt

        //Dành riêng cho CRC + Lưu lượng port
        @JsonProperty("CRC_RESULT")
        private String crcResult;          // Đạt/Không đạt
        @JsonProperty("LUULUONG_PORT_RESULT")
        private String luuLuongPortResult; // Đạt/Không đạt

        //Kết quả kiểm tra tổng của phần động thiết bị
        @JsonProperty("KET_QUA")
        private String ketQua; //Đạt/Không đạt
        @JsonProperty("GHI_CHU")
        private String ghiChu;


        public int getStt() {
            return stt;
        }

        public String getLoai() {
            return loai;
        }

        public String getSystemName() {
            return systemName;
        }

        public String getCardName() {
            return cardName;
        }

        public String getPortName() {
            return portName;
        }

        public String getTxVal() {
            return txVal;
        }

        public String getRxVal() {
            return rxVal;
        }

        public String getRxTrongNguong() {
            return rxTrongNguong;
        }

        public String getRxNgoaiNguong() {
            return rxNgoaiNguong;
        }

        public String getCrcResult() {
            return crcResult;
        }

        public String getLuuLuongPortResult() {
            return luuLuongPortResult;
        }

        public String getKetQua() {
            return ketQua;
        }

        public String getGhiChu() {
            return ghiChu;
        }
    }



    public long getPhanVungId() {
        return phanVungId;
    }

    public long getSuCoId() {
        return suCoId;
    }

    public long getXuLyId() {
        return xuLyId;
    }

    public int getIsTiepNhan() {
        return isTiepNhan;
    }

    public int getIsDoKiem() {
        return isDoKiem;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public List<FileDinhKem> getDsFileDinhKem() {
        return dsFileDinhKem.isEmpty()?null:dsFileDinhKem;
    }

    public List<ThongTinDoKiem> getDsThongTinDoKiem() {
        return dsThongTinDoKiem.isEmpty()?null:dsThongTinDoKiem;
    }
}
