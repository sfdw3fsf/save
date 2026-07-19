<template src="./frmKhaoSatKH_HDTB.html">
</template>
<style scope>
.e-grid .e-gridheader
{
    position: -webkit-sticky;
    position: sticky;
    top: 0px;
    z-index: 1;
}

.select2-selection__rendered {
    font-weight: normal;
}
a.disabled {
    color: lightgrey !important;
    pointer-events: none;
    cursor: default;
    text-decoration: none;
}

.btn-custom.btn-second {
    background-color: #fff !important;
    border-color: #0176FF !important;
    color: #0176FF !important;
    font-weight: 600 !important;
}
.btn-second.btn {
    padding: 5px 10px !important;
    border-radius: 4px !important;
}
.legend-title{
    font-weight: 600 !important;
}
</style>
<script>
import moment from "moment";
import { mapActions } from "vuex";
import cauHoiKS from "./frmCauHoiKS.vue";
import frmLichSuHenBaoHong from "./frmLichSuHenBaoHong.vue";
import frmLichSuBaoHong from "./frmLichSuBaoHong.vue";
import YesNoModal from "../utils/YesNoModal.vue";
const FIMS_LYDO_KTL = {
    /// <summary>
    /// Khách hàng từ chối tiếp chuyện
    /// </summary>
    KH_TU_CHOI_TIEP_CHUYEN: 1,
    /// <summary>
    /// Khách hàng hẹn
    /// </summary>
    KHACHHANG_HEN: 20,
    /// <summary>
    /// Khách hàng không bắt máy, máy bận - 1h sau gọi lại
    /// </summary>
    MAY_BAN: 21,
    /// <summary>
    /// Không xác nhận dịch vụ - 24h sau gọi lại
    /// </summary>
    KHONG_XACNHAN_DICHVU: 22
};
export default {
    name: "frmKhaoSatKH_HDTB",
    components: {
        cauHoiKS,
        frmLichSuHenBaoHong,
        frmLichSuBaoHong,
        confirmPopup: YesNoModal
    },
    props: {
        _tbtlId: { type: Number, default: 0 },
        _phieuId: { type: Number, default: 0 },

        _baoHongId: { type: Number, default: 0 },
        _hdkhId: { type: Number, default: 0 },
        _hdtbId: { type: Number, default: 0 },
        _giaoPhieuKsId: { type: Number, default: 0 },
        _isDieuLai: { type: Boolean, default: false },
        _kieuKs: { type: Number, default: -1 },
        _moPhieu: { type: Number, default: 0 },
        isDaCapNhap: { type: Boolean, default: false },
        isSuaPhieu: { type: Boolean, default: false }
    },
    computed: {
        xacNhanDieuLai() {
            return "Bạn có chắc chắn muốn điều hỏng thuê bao " + this.pTxtMaTB + " ?"
        },
        ketQuaKS() {
                return this.$refs["cauHoiKS"].ketQuaKS
        },
        cauHoiKSPhu() {
            return this.$refs["cauHoiKS"].dsCauHoiPhu
        },
        cauHoiKSChinh() {
            return this.$refs["cauHoiKS"].dsCauHoiChinh
        },
        cauHoiKSChau() {
            return this.$refs["cauHoiKS"].dsCauHoiChau
        },
        lichSuKSUrl() {
            return `/#/cskh/TraCuuThueBaoKhaoSat?maTB=${this.pTxtMaTB}`
        }
    },
    data() {
        return {
            target: ".main-wrapper",
            animationSettings: { effect: "Zoom" },

            pThamSoMacDinh: {
                BATBUOC_TRALOI_ALL_CAUHOI: false,
                KIEMTRA_CAUHOI_KHL: false,
                FIMS_DIEU_XN_DONVI: false,
                OB_KHONGTL_KTC1: false,
                KHONGTL_CHUYEN_OB_TUDONG: false,
                CHOPHEP_SUA_OB_KHONG_TL: false,
                LAN_KS_TOIDA: 0,
                KHAOSAT_HDTB_THEO_HDKH_ID: false
            },
            pBtnStatus: {
                btnDieuXNVisible: true,
                btnDieuLaiEnabled: true
            },
            pDsLyDoKTL: [],
            pCboLyDoKTL: false,
            pCboLyDoKTLSelected: -1,
            tbtlId: 0,
            phieuId: 0,

            baoHongId: 0,
            hdkhId: 0,
            hdtbId: 0,
            giaoPhieuKsId: 0,
            thueBaoId: 0,
            kieuKs: -1,
            maTb: 0,
            pIsDieuLai: false,

            pchkHenKS_Tu: false,
            pchkHenKS_Den: false,
            pDtpHenKS_Tu: new Date(),
            pDtpHenKS_Den: new Date(),

            pTxtMaTB: "",
            pTxtTenTB: "",
            pTxtDiaChiLD: "",
            pTxtKieuLD: "",
            pTxtThueBaoId: 0,
            pTxtNgayHT: "",
            pTxtGhiChuHT: "",

            pTxtNgayYC: "",
            pTxtNgayHT: "",
            pTxtLoaiHD: "",
            pTxtTenNguoiTL: "",
            pTxtSoLH: "",
            pTxtDienThoaiLH: "",
            pTxtGhiChu: "",

            pTxtTenKH: "",
            pTxtMaKH:"",

            pDsThueBao: [],
            pDsTTHD: [],
            pDsLanGoiChuaTL: [],
            pDsTBTL: [],
            pIsNew: false,
            pIsDaCapNhap: false,
            pIsSuaPhieu: false,
            pNgayKS: new Date(),

            pNguoiCN: "",
            pMayCN: "",
            pNhanVienId: "",
            pNguoiDungId: "",
            pIPCN: "",

            pKhaoSat: {
                dsCauHoiChinh: [],
                dsCauHoiPhu: [],
                dsCauTraLoi: []
            },
            fakeData: {
                cauHoi: [],
                traLoi: []
            },

            today: new Date(),

            API_SUCCESS: "BSS-00000000",
            LAN_KS_TOI_DA: 4,
            isKhachHangHen: false,
        };
    },
    methods: {
        ...mapActions("cskhCommon", [
            "getThamSoMacDinh",
            "getListThamSoMacDinh"
        ]),
        initData() {
            this.pTxtMaTB = ""
            this.pTxtTenTB = ""
            this.pTxtDiaChiLD = ""
            this.pTxtKieuLD = ""
            this.pTxtThueBaoId = 0
            this.pTxtNgayHT = ""
            this.pTxtGhiChuHT = ""
            this.pTxtNgayYC = ""
            this.pTxtNgayHT = ""
            this.pTxtLoaiHD = ""
            this.pTxtTenNguoiTL = ""
            this.pTxtSoLH = ""
            this.pTxtDienThoaiLH = ""
            this.pTxtGhiChu = ""
            this.isKhachHangHen = false
        },
        async dialogOpen() {
            this.initData()
            await this.onFormLoad();
        },
        openDialog() {
            //   this.onFormLoad().then(() => {
            //     this.$refs.frmKhaoSatKH_HDTB.show()
            //   })

            this.$refs.frmKhaoSatKH_HDTB.show();
        },
        closeDialog() {
            this.$emit("onHideKhaoSat");
            this.$refs.frmKhaoSatKH_HDTB.hide();
        },
        onGridDsThueBao_RowSelected(dataItem) {
            this.phieuId = dataItem ? dataItem.HDTB_ID : -1
            this.pTxtMaTB = dataItem ? dataItem.MA_TB : ''
        },
        async onFormLoad() {
            try {
                this.$root.showLoading(true);
                this.pIsDaCapNhap = this.isDaCapNhap;
                this.pIsSuaPhieu = this.isSuaPhieu;
                // this.phieuId = this._phieuId;
                // this.baoHongId = this._baoHongId;
                // this.hdkhId = this._hdkhId;
                // this.hdtbId = this._hdtbId;
                // this.tbtlId = this._tbtlId;
                // console.log('tbtlId = ', this.tbtlId)
                // this.giaoPhieuKsId = this._giaoPhieuKsId;
                // this.pIsDieuLai = this._isDieuLai;
                // this.kieuKs = this._kieuKs

                await Promise.all([
                    this.getThamSoMacDinh(),
                    this.getLyDoKTL(),
                ]);

                await this.hienThiThongTinTB()
                await this.$refs.cauHoiKS.loadData()

                if (this.pThamSoMacDinh.FIMS_DIEU_XN_DONVI) {
                    this.pBtnStatus.btnDieuXNVisible = true;
                }
            } catch (e) {
                console.log(e);
            } finally {
                this.$root.showLoading(false);
            }
        },
        async getThamSoMacDinh() {
            try {
                let dsThamSoMacDinh = await this.getListThamSoMacDinh({
                    dsMaTs: Object.getOwnPropertyNames(
                        this.pThamSoMacDinh
                    ).join()
                });
                if (dsThamSoMacDinh && dsThamSoMacDinh.length > 0) {
                    Object.getOwnPropertyNames(this.pThamSoMacDinh).forEach(
                        ts => {
                            let rs = dsThamSoMacDinh.find(x => x.MA_TS === ts);
                            this.pThamSoMacDinh[ts] =
                                (rs ? true : false) && rs.TEN_TS == "1";
                        }
                    );
                }
            } catch (e) {
            } finally {
            }
        },
        async getLyDoKTL() {
            try {
                this.pDsLyDoKTL = [];
                this.pCboLyDoKTLSelected = "";
                let response = await this.$root.context.get(
                    "/web-cskh/api/danh-muc/ly-do-ktl"
                );
                if (response.error_code === "BSS-00000000") {
                    this.pDsLyDoKTL = response.data || [];
                    if (this.pDsLyDoKTL.length > 0) {
                        this.pCboLyDoKTLSelected = this.pDsLyDoKTL[0].LYDOKTL_ID;
                    }
                }
            } catch (e) {
                console.log(e);
                this.pDsLyDoKTL = [];
            }
        },
        async hienThiThongTinTB() {
            this.pDsThueBao = await this.layDsHDTheoHDTBID(this.hdtbId);
            if (this.pDsThueBao.length > 0) {
                this.hdkhId = this.pDsThueBao[0].HDKH_ID;
                this.pTxtMaTB = this.pDsThueBao[0].MA_TB;
                this.pTxtTenTB = this.pDsThueBao[0].TEN_TB;
                this.pTxtDiaChiLD = this.pDsThueBao[0].DIACHI_LD;
                this.pTxtKieuLD = this.pDsThueBao[0].TEN_KIEULD;
                this.pTxtNgayHT = this.pDsThueBao[0].NGAY_HT;
                this.pTxtGhiChuHT = this.pDsThueBao[0].GHICHU_TH;
                this.maTb = this.pDsThueBao[0].MA_TB;
                this.thueBaoId = this.pDsThueBao[0].THUEBAO_ID;
                this.pTxtMaKH = this.pDsThueBao[0].MA_KH;
                this.pTxtTenKH = this.pDsThueBao[0].TENNGUOITL;
            }
            this.pDsTTHD = await this.layTTHD(this.hdkhId);
            if (this.pDsTTHD.length > 0) {
                this.pTxtDienThoaiLH = this.pDsTTHD[0].SO_DT;
                // if (this.$root.token.getPhanVungID() == 26) {
                    this.pTxtSoLH = this.pDsTTHD[0].SO_DT;
                    this.pTxtTenNguoiTL = this.pDsTTHD[0].TEN_KH;
                // }
                this.pTxtLoaiHD = this.pDsTTHD[0].TEN_LOAIHD;
                this.pTxtNgayYC = this.pDsTTHD[0].NGAY_YC;

                this.pDsLanGoiChuaTL = await this.layDsLanGoiChuaTL(
                    this.hdkhId,
                    this.baoHongId
                );

                if (this.isSuaPhieu) {
                    this.pDsTBTL = await this.layDsTBTL(this.tbtlId, 3);
                } else {
                    this.pDsTBTL = await this.layDsTBTL(this.hdtbId, 4);
                }
                this.pBtnStatus.btnDieuLaiEnabled = true
                if (this.pDsTBTL.length > 0) {
                    this.pIsNew = false;
                    if (this.tbtlId == 0) {
                        this.tbtlId = this.pDsTBTL[0].TBTL_ID;
                    }
                    this.pTxtTenNguoiTL = this.pDsTBTL[0].NGUOI_TL;
                    this.pTxtSoLH = this.pDsTBTL[0].DIENTHOAI_LH;
                    this.pTxtGhiChu = this.pDsTBTL[0].GHICHU;
                    this.pCboLyDoKTL = this.pDsTBTL[0].TRANGTHAI == 0;
                    this.pTxtNgay = moment(this.pDsTBTL[0].NGAY_HOI);
                    if (this.pDsTBTL[0].TRANGTHAI > 0)
                        this.pBtnStatus.btnDieuLaiEnabled = false
                    if (this.pCboLyDoKTL) {
                        this.pIsNew = true;

                        if (!this.pThamSoMacDinh.CHOPHEP_SUA_OB_KHONG_TL) {
                            this.tbtlId = 0;
                        }

                        if (this.pDsTBTL[0].LYDOKTL_ID) {
                            this.pCboLyDoKTLSelected = this.pDsTBTL[0].LYDOKTL_ID;
                        }

                        this.pNguoiCN = this.pDsTBTL[0].NGUOI_CN;
                    }

                }
                else {
                    this.pBtnStatus.btnDieuLaiEnabled = true
                    this.pIsNew = true;
                    this.tbtlId = 0;
                }
            }
        },
        /*
      1: tìm kiếm theo hợp đồng hdkh_id
      2: báo hỏng: baohong_id
      3: tbtl_id
      4: hdtb_id
   */
        async layDsTBTL(id, loai) {
            try {
                let rs = await this.$root.context.get(
                    "/web-cskh/api/xac-nhan-phan-hoi-chat-luong/ds-thue-bao-tra-loi",
                    {
                        id: id,
                        loai: loai,
                        pageNo: 1,
                        pageSize: 10000
                    }
                );
                if (rs.error_code === this.API_SUCCESS) {
                    return rs.data.data || [];
                }
            } catch (e) {
                console.log(e);
            }
            return [];
        },
        async layDsHDTheoHDTBID(hdtbId) {
            try {
                let rs = await this.$root.context.get(
                    "/web-cskh/api/khao-sat-kh-hd-tb/thue-bao-theo-hop-dong",
                    {
                        hdtbId: hdtbId,
                        pageNo: 1,
                        pageSize: 10000
                    }
                );
                if (rs.error_code === this.API_SUCCESS) {
                    return rs.data.data || [];
                }
            } catch (e) {
                console.log(e);
            }
            return [];
        },
        async layTTHD(hdkhId) {
            try {
                let rs = await this.$root.context.get(
                    "/web-cskh/api/khao-sat-kh-hd-tb/thong-tin-hop-dong",
                    {
                        hdkhId: hdkhId,
                        pageNo: 1,
                        pageSize: 10000
                    }
                );
                if (rs.error_code === this.API_SUCCESS) {
                    return rs.data.data || [];
                }
            } catch (e) {
                console.log(e);
            }
            return [];
        },
        async layDsLanGoiChuaTL(hdkhId, baoHongId) {
            try {
                let rs = await this.$root.context.get(
                    "/web-cskh/api/xac-nhan-phan-hoi-chat-luong/ds-lan-goi-chua-tra-loi",
                    {
                        id: hdkhId,
                        loai: 1,
                        pageNo: 1,
                        pageSize: 10000
                    }
                );
                if (rs.error_code === this.API_SUCCESS) {
                    return rs.data.data || [];
                }
            } catch (e) {
                console.log(e);
            }
            return [];
        },
        focusInput(name) {
            this.$nextTick(() => this.$refs[name].select());
        },
        async onGhiLaiClicked() {
            if (this.pIsNew && !this.pIsDaCapNhap) {
                this.pIsDaCapNhap = false;
                if (await this.kiemTraDuLieu(true)) {
                    this.loading(true)
                    let data = this.taoDuLieu(true, false);
                    let rs = await this.$root.context.post(
                        "/web-cskh/api/khao-sat-kh-hd-tb/ghi-lai",
                        data
                    );
                    if (rs.error_code === "BSS-00000000") {
                        if (rs.data.ketQua == 1) {
                            this.$root.toastSuccess( "Cập nhật thành công!" );
                            if (rs.data.msgError)
                            {
                                let listMessages = rs.data.msgError.split("#")
                                listMessages.forEach(message => {
                                    let pattern = /.*\d/g;
                                    let result = message.match(pattern)
                                    if (result && result.length > 0 && result[0].includes('1'))
                                    {
                                        this.$root.toastSuccess(message.replace(result[0], ""))
                                    }
                                    else
                                    {
                                        this.$root.toastError(message.replace(result[0], ""))
                                    }
                                });
                            }
                            this.closeDialog();
                        } else {
                            this.$root.toastError(rs.data.msgError);
                        }
                        // if (rs.data.msgError) {
                        //     this.$root.toastError(
                        //         "Có lỗi!" + rs.data.msgError
                        //     );
                        // } else {
                        //     this.$root.toastSuccess("Cập nhật thành công!");
                        //     this.closeDialog()
                        // }
                    } else {
                        this.$root.toastError("Cập nhật thất bại");
                    }
                    this.loading(false)
                }
            }
            else {
                if (await this.kiemTraDuLieu(false)) {
                    this.loading(true)
                    let data = this.taoDuLieu(false, false);
                    let rs = await this.$root.context.post(
                        "/web-cskh/api/khao-sat-kh-hd-tb/ghi-lai",
                        data
                    );
                    if (rs.error_code === "BSS-00000000") {
                        if (rs.data.ketQua == 1) {
                            this.$root.toastSuccess( "Cập nhật thành công!" );
                            if (rs.data.msgError)
                            {
                                let listMessages = rs.data.msgError.split("#")
                                listMessages.forEach(message => {
                                    let pattern = /.*\d/g;
                                    let result = message.match(pattern)
                                    if (result && result.length > 0 && result[0].includes('1'))
                                    {
                                        this.$root.toastSuccess(message.replace(result[0], ""))
                                    }
                                    else
                                    {
                                        this.$root.toastError(message.replace(result[0], ""))
                                    }
                                });
                            }
                            this.closeDialog();
                        } else {
                            this.$root.toastError(rs.data.msgError);
                        }
                        // if (rs.data.msgError) {
                        //     this.$root.toastError(
                        //         "Có lỗi!" + rs.data.msgError
                        //     );
                        // } else {
                        //     this.$root.toastSuccess("Cập nhật thành công!");
                        //     this.closeDialog()
                        // }
                    } else {
                        this.$root.toastError("Cập nhật thất bại");
                    }
                    this.loading(false)
                }
            }
        },
        // async kiemTraGiuPhieu() {
        //   try {
        //     let rs = await this.$root.context.
        //   }
        //   catch (e) {
        //     console.log(e)
        //   }
        // },
        async kiemTraDuLieu(themMoi) {
            if (this.pTxtTenNguoiTL == "" && !this.pCboLyDoKTL) {
                this.$root.toastError("Chưa nhập tên người trả lời !");
                this.focusInput("txtTenNguoiTL");
                return false;
            }
            if (this.pTxtSoLH == "") {
                this.$root.toastError("Chưa nhập điện thoại liên hệ !");
                this.focusInput("txtSoLH");
                return false;
            }
            if (
                this.pCboLyDoKTL &&
                this.pCboLyDoKTLSelected == FIMS_LYDO_KTL.KHACHHANG_HEN
            ) {
                if (!this.pchkHenKS_Tu && !this.pchkHenKS_Den) {
                    this.$root.toastError("Hãy chọn Ngày hẹn !");
                    return false;
                }

                if (this.pchkHenKS_Tu && !this.pDtpHenKS_Tu) {
                    this.$root.toastError("Hãy chọn Ngày hẹn khảo sát từ !");
                    return false;
                }

                if (this.pchkHenKS_Den && !this.pDtpHenKS_Den) {
                    this.$root.toastError("Hãy chọn Ngày hẹn khảo sát đến !");
                    return false;
                }

                if (this.pDtpHenKS_Tu < this.today) {
                    this.$root.toastError(
                        "Ngày hẹn từ không thể nhỏ hơn ngày hiện tại !"
                    );
                    return false;
                }

                if (this.pDtpHenKS_Den < this.today) {
                    this.$root.toastError(
                        "Ngày hẹn đến không thể nhỏ hơn ngày hiện tại !"
                    );
                    return false;
                }
                if (
                    this.pchkHenKS_Tu &&
                    this.pDtpHenKS_Tu > moment(this.pTxtNgayHT, "DD/MM/YYYY HH:mm:ss").add(3, "days")
                ) {
                    this.$root.toastError(
                        "Không thể hẹn quá 72h tính từ thời điểm nghiệm thu: " +
                            this.pTxtNgayHT
                    );
                    return false;
                }

                if (
                    this.pchkHenKS_Den &&
                    this.pDtpHenKS_Den > moment(this.pTxtNgayHT, "DD/MM/YYYY HH:mm:ss").add(3, "days")
                ) {
                    this.$root.toastError(
                        "Không thể hẹn quá 72h tính từ thời điểm nghiệm thu: " +
                            this.pTxtNgayHT
                    );
                    return false;
                }
                return
            }
            if (
                this.$refs["cauHoiKS"].ketQuaKS.length > 0 &&
                this.pCboLyDoKTL
            ) {
                this.$root.toastError(
                    'Bạn đang chọn "Không trả lời" và đồng thời tích chọn trả lời trong Danh sách câu hỏi!'
                );
                return false;
            }
            let cauHoiKS = this.$refs["cauHoiKS"];
            let dsKetQua = this.$refs["cauHoiKS"].ketQuaKS;
            if (!this.pCboLyDoKTL) {
                if (dsKetQua.length <= 0) {
                    this.$root.toastError("Bạn phải chọn câu trả lời!");
                    return false;
                }
                if (this.pThamSoMacDinh.BATBUOC_TRALOI_ALL_CAUHOI) {
                }
            }
            let isFlat = false
            dsKetQua.map((item,index)=>{
                //nếu chọn câu trả lời " Đang lắp đặt sửa chữa hoặc chưa sử dụng được dịch vụ thì bắt buộc phải chọn câu hỏi số 3 và chọn KT3 - mục không hài lòng nvkt thì mới cho hoàn thành
                if(item.CAUHOI_ID == 8847 && item.TRALOI_ID == 523550){
                    if(!dsKetQua.some(ch => ch.TRALOI_ID == 523585)){
                        this.$root.toastError(`Câu 1 chọn "${item.TRALOI}" thì câu 3 phải chọn tiếp "Không hài lòng về nhân viên kỹ thuật" với lý do "KT3"!`);
                        isFlat = true
                    }
                }
                //nếu phiếu có hẹn, KH nói ko biết lịch hẹn --> phải chọn KT1 mới cho đóng phiếu
                if(item.CAUHOI_ID == 8852 && item.TRALOI_ID == 523562){
                    if(!dsKetQua.some(ch => ch.TRALOI_ID == 523583)){
                        this.$root.toastError(`Câu 2 chọn "Không hài lòng, nếu phiếu có lịch hẹn" thì câu 3 phải chọn tiếp "Không hài lòng về nhân viên kỹ thuật" với lý do "KT1"!`);
                        isFlat = true
                    }
                }
                //câu 2 chọn câu trả lời là Không hài lòng mà không chọn chi tiết KHL KD hay KHL KT
                if(item.CAUHOI_ID == 8848 && item.TRALOI_ID == 523564){
                    if(!dsKetQua.some(ch => ch.TRALOI_ID == 523601 || ch.TRALOI_ID == 523602)){
                        this.$root.toastError(`Cần chọn trả lời câu 3!`);
                        isFlat = true
                    }
                }
            })
            if(isFlat) return false;
            for (let chIndex = 0; chIndex < dsKetQua.length; chIndex++) {
                let traLoiId = dsKetQua[chIndex].RCH_ID;
                let cauHoiPhu = cauHoiKS.dsCauHoiChau.filter(
                    x => x.RCH_ID == traLoiId
                );
                let chpDcTL = false;
                for (
                    let chpIndex = 0;
                    chpIndex < cauHoiPhu.length;
                    chpIndex++
                ) {
                    if (cauHoiPhu[chpIndex].Checked) {
                        if(cauHoiPhu[chpIndex].Text.trim() == "" && cauHoiPhu[chpIndex].LOAI == 1){
                            this.$root.toastError(`Bạn chưa nhập nội dung cho câu trả lời "${cauHoiPhu[chpIndex].NOIDUNG_TL}"!`);
                            return false;
                        }
                        chpDcTL = true;
                        break;
                    }
                }
                if (cauHoiPhu.length> 0 && !chpDcTL) {
                    this.$root.toastError(
                        "Bạn chọn câu trả lời '" +
                            dsKetQua[chIndex].TRALOI +
                            "', mà chưa chọn lý do ! "
                    );
                    return false;
                }
                //kiểm tra các câu trả lời ý kiến khác mà không nhập text.
                let traLoi = cauHoiKS.dsCauHoiChau.filter(
                    x => x.TRALOI_ID == traLoiId
                );
                if (traLoi && traLoi.length > 0) {
                    if (
                        (traLoi[0].LOAI == 1 || traLoi[0].LOAI == 2) &&
                        dsKetQua[chIndex].NOIDUNG
                    ) {
                        this.$root.toastError(
                            "Bạn chưa nhập nội dung cho câu trả lời '" +
                                dsKetQua[chIndex].TRALOI +
                                " ! "
                        );
                        return false;
                    }
                }

                //Nếu câu trả lời lq đến NET thì fai tồn tại hướng giao về Net - VB 1348/VNPT-CLG-TL
                // if (a.Rows[0]["MA_TL"].ToString() == "NET1" || a.Rows[0]["MA_TL"].ToString() == "NET2" || a.Rows[0]["MA_TL"].ToString() == "NET3")
                // if (fims.kiemtra_giaophieu_net_v2(hdtb_id, 1) != "1")
                // {
                //     Message_Box.ShowTB("Bạn chỉ được chọn hạng mục 'NET1', 'NET2', 'NET3' sau khi kiểm tra có phiếu VNPT Net xử lý");
                //     return false;
                // }
            }
            // Kiểm tra chỉ user người khảo sát mới được sửa nội dung ks và chỉ sửa trong tháng
            if (!themMoi) {
            }

            if (this.$auth.getPhanVungID() == 28) {
                if (this.pIsNew) {
                    //kiem tra giu phieu
                }
            }
            return true;
        },
        taoDuLieu(isThemMoi, isXuatLai) {
            let kq = this.ketQuaKS;
            let data = {
                hinhThuc: 0,
                ctSuyHaoId: this.ctSuyHaoId,
                tbtlId: isThemMoi ? 0 : this.tbtlId,
                thueBaoId: this.thueBaoId,
                hdkhId: this.hdkhId,
                hdtbId: this.hdtbId,
                nguoiTl: this.pTxtTenNguoiTL,
                dienThoaiLh: this.pTxtSoLH,
                ghiChu: this.pTxtGhiChu,
                gpksId: this.giaoPhieuKsId,
                lyDoKtlId: this.pCboLyDoKTL ? this.pCboLyDoKTLSelected : 0,
                gioHenTu: (this.pCboLyDoKTL && this.isKhachHangHen && this.pchkHenKS_Tu)
                    ? moment(this.pDtpHenKS_Tu).format("DD/MM/yyyy")
                    : "",
                gioHenDen: (this.pCboLyDoKTL && this.isKhachHangHen && this.pchkHenKS_Den)
                    ? moment(this.pDtpHenKS_Den).format("DD/MM/yyyy")
                    : "",
                dsCauHoiId: kq.map(x => x.CAUHOI_ID).join("#"),
                dsTraLoiId: kq.map(x => x.TRALOI_ID).join("#"),
                dsNoiDung: JSON.stringify(kq.map(x => `${x.NOIDUNG}`)),
                isCnSoLienHe: 1,
                nguoiDungId: this.$root.token.getNguoiDungID()
            };

            if (this.pCboLyDoKTL) {
                data.ttksId = 1
                let dsLanHoiKtl = this.pDsTBTL.filter(x => x.LYDOKTL_ID > 0)
                if (dsLanHoiKtl.length + 1 > this.LAN_KS_TOI_DA && this.pThamSoMacDinh.LAN_KS_TOIDA == 1) {
                    data.ttksId = 7
                }
            }
            else {
                data.ttksId = 6
            }

            if (this.pThamSoMacDinh.OB_KHONGTL_KTC1 == 1 && this.pCboLyDoKTL && this.pCboLyDoKTLSelected == FIMS_LYDO_KTL.KH_TU_CHOI_TIEP_CHUYEN) {
                data.ttksId = 6
            }

            return data;
        },
        taoDuLieuHenKS() {
            let data = null;
            if (this.pchkHenKS_Tu || this.pchkhenKS_Den) {
                data.giaoPhieuKsId = this.giaoPhieuKsId;
                data.hdkhId = this.hdkhId;
                data.hdtbId = this.hdtbId;
                data.baoHongId = 0;

                if (this.pchkHenKS_Tu)
                    data.gioHenTu = moment(this.pDtpHenKS_Tu).format(
                        "DD/MM/YYYYY"
                    );

                if (this.pchkHenKS_Den)
                    data.gioHenDen = moment(this.pDtpHenKS_Den).format(
                        "DD/MM/YYYYY"
                    );

                data.nhanVienid = this.$root.token.getNhanVienID();
                data.ghiChu = this.pTxtGhiChu;
                data.kieu = 2;
            }
            return data;
        },
        onDieuLaiClicked() {
            if (this.phieuId <= 0) {
                this.$root.toastError("Bạn chưa chọn thuê bao để điều lại!");
                return;
            }
            if (
                this.pCboLyDoKTL &&
                this.pCboLyDoKTLSelected == FIMS_LYDO_KTL.KHACHHANG_HEN
            ) {
                this.$root.toastError(
                    "Không thể chọn lý do Khách hàng hẹn khi điều lại!"
                );
                return;
            }
            if (!this.pIsNew) {
                this.$root.toastError("Không thể điều lại cho phiếu đã khảo sát!")
                return
            }

            this.$bvModal.show("popup-confirmDieuLai_hdtb");
        },
        async onDieuLaiConfirmed() {
            try {
                this.$root.showLoading(true);
                if (this.pIsNew) {
                    if (await this.kiemTraDuLieu(true)) {
                        let data = this.taoDuLieu(true, true);
                        let rs = await this.$root.context.post(
                            "/web-cskh/api/khao-sat-kh-hd-tb/dieu-lai",
                            data
                        );
                        if (rs.error_code === "BSS-00000000") {
                            if (rs.data.ketQua == 1) {
                                // this.$root.toastSuccess("Điều lại thành công!");
                                if (rs.data.msgError)
                                {
                                    let listMessages = rs.data.msgError.split("#")
                                    listMessages.forEach(message => {
                                        let pattern = /.*\d/g;
                                        let result = message.match(pattern)
                                        console.log(result)
                                        if (message.includes('1'))
                                        {
                                            this.$root.toastSuccess(message.replace(result, ""))
                                        }
                                        else
                                        {
                                            this.$root.toastError(message.replace(result, ""))
                                        }
                                    });
                                }
                                this.closeDialog()
                            } else {
                                this.$root.toastError(rs.data.msgError);
                            }

                        } else {
                            this.$root.toastError("Điều lại thất bại!");
                        }
                    }
                }
            } catch (e) {
                console.log(e);
            } finally {
                this.$root.showLoading(false);
            }
        },
        onLichSuHenBHClicked() {
            this.$refs.popupLichSuHenBH.hdtbId = this.hdtbId
            this.$refs.popupLichSuHenBH.kieu = 1
            this.$refs.popupLichSuHenBH.openDialog()
        }
    },
    watch: {
        pCboLyDoKTLSelected: {
            handler: async function (value) {
                if (value == 20) {
                    this.pchkHenKS_Tu = false
                    this.pchkhenKS_Den = false
                    this.isKhachHangHen = true
                }
                else {
                    this.isKhachHangHen = false
                }
            },
        },
    }
};
</script>
