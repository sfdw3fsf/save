<template src="./frmKhaoSatKH_TB.html">

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
</style>
<script>
import moment from "moment";
import { mapActions } from "vuex";
import cauHoiKS from "./frmCauHoiKS.vue";
import YesNoModal from "../utils/YesNoModal.vue";
import {CSKH_CONST, FIMS_LYDO_KTL, LOAI_NV} from "../utils/functions/constants.js"
import LichSuBaoHong from "../TraCuuLichSuBaoHong";
// import Vue from "vue";
// import { DateTimePickerPlugin } from "@syncfusion/ej2-vue-calendars";

// Vue.use(DateTimePickerPlugin);
export default {
    name: "frmKhaoSatKH_TB",
    components: {
        cauHoiKS,
        confirmPopup: YesNoModal,
        LichSuBaoHong
    },
    props: {
    },
    computed: {
        xacNhanDieuLai() {
            return (
                "Bạn có chắc chắn muốn điều hỏng thuê bao " +
                this.pTxtMaTB +
                " ?"
            );
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
        }
    },
    data() {
        return {
            target: ".main-wrapper",
            animationSettings: { effect: "Zoom" },

            pThamSoMacDinh: {
                BATBUOC_TRALOI_ALL_CAUHOI: false,
                KIEMTRA_CAUHOI_KHL: false,
                OUTBOUND_KHONG_DIEULAI: false,
            },

            pTxtMaTB: '',
            pTxtMaGD: '',
            pTxtNgayYC: '',
            pTxtLoaiHD: '',
            pTxtLoaiHinh: '',
            pTxtTenKH: '',
            pTxtDiaChiKH: '',
            pTxtDienThoaiLH: '',
            pTxtTenNguoiTL: '',
            pTxtSoLH: '',
            pTxtGhiChu: '',

            pDsLyDoKTL: [],
            pCboLyDoKTL: false,
            pCboLyDoKTLSelected: "",

            maTb: '',
            hdkhId: 0,
            hdtbId: 0,
            baoHongId: 0,
            tbtlId: 0,
            giaoPhieuKsId: 0,
            thueBaoId: 0,

            isNew: false,
            isDaCapNhap: false,
            isDieuLai: false,
            isDieuLaiXN: false,
            ngayKS: '',

            API_SUCCESS: "BSS-00000000",

            dsThueBao: [],
            dsLanGoiChuaTraLoi: [],
            dsTBTL: [],

            pBtnStatus: {
                btnDieuLaiVisible: true,
                btnDieuLaiEnabled: true,
            },
            pTxtMoTa:null,
            txtTitle: "",
            hinhThucId: 3,
            today: new Date(),
            pchkHenKS_Tu: false,
            pchkHenKS_Den: false,
            pDtpHenKS_Tu: new Date(),
            pDtpHenKS_Den: new Date(),
            pTxtTrangThai: ""
        };
    },
    methods: {
        ...mapActions("cskhCommon", [
            "getThamSoMacDinh",
            "getListThamSoMacDinh"
        ]),
        async dialogOpen() {
             await this.onFormLoad();
        },
        openDialog() {
            this.$refs.frmKhaoSatKH_TB.show();
        },
        closeDialog() {
            this.$emit("onHideKhaoSat");
            this.$refs.frmKhaoSatKH_TB.hide();
            this.resetData()
        },
        resetData(){
            this.pTxtGhiChu = ''
            this.pTxtMoTa = null
            this.pTxtTrangThai = ''
        },
        async onFormLoad() {
            try {
                this.$root.showLoading(true);
                await Promise.all([
                    // this.getThamSoMacDinh(),
                    this.getLyDoKTL(),
                ]);
                await this.hienThiThongTinTB()
                await this.$refs.cauHoiKS.loadData()
                // if (this.pThamSoMacDinh.OUTBOUND_KHONG_DIEULAI) {
                //     this.pBtnStatus.btnDieuLaiVisible = false
                // }
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
            try {
                let rs = await this.$root.context.get('/web-cskh/api/xac-nhan-phan-hoi-chat-luong/thong-tin-phieu-khao-sat', {id: this.thueBaoId, loai: 3, giaophieu_ks_id: this.giaoPhieuKsId})
                if (rs.error_code === this.API_SUCCESS) {
                    let ds = rs.data.data || []
                    if (ds.length > 0) {
                        this.pTxtMaTB = ds[0].MA_TB
                        this.pTxtTenKH = ds[0].TEN_TB
                        this.pTxtDiaChiKH = ds[0].DIACHI_LD
                        this.pTxtDienThoaiLH = ds[0].DIENTHOAI_LH
                        this.pTxtMoTa = ds[0].MO_TA
                        this.pTxtTrangThai = ds[0].TRANGTHAI_TB
                        //khác HPG
                        if (this.$root.token.getPhanVungID() != 26)
                        {
                            this.pTxtSoLH = ds[0].DIENTHOAI_LH
                            this.pTxtTenNguoiTL = ds[0].TEN_TB
                        }
                        this.pTxtLoaiHD = ds[0].LOAIHINH_TB

                        this.dsLanGoiChuaTraLoi = await this.layDsLanGoiChuaTL(this.thueBaoId, 5)
                        this.dsTBTL = await this.layDsTBTL(this.thueBaoId, 7)
                        this.pBtnStatus.btnDieuLaiEnabled = true
                        if (this.dsTBTL.length > 0) {
                            this.isNew =false
                            if (this.tbtlId == 0) {
                                this.tbtlId = this.dsTBTL[0].TBTL_ID
                            }

                            this.pTxtTenNguoiTL = this.dsTBTL[0].NGUOI_TL
                            this.pTxtSoLH = this.dsTBTL[0].DIENTHOAI_LH
                            this.pTxtGhiChu = this.dsTBTL[0].ghiChu
                            this.ngayKS = this.dsTBTL[0].NGAY_HOI

                            this.pCboLyDoKTL = this.dsTBTL[0].TRANGTHAI == 0
                            if (this.dsTBTL[0].TRANGTHAI > 0)
                                this.pBtnStatus.btnDieuLaiEnabled = false

                            if (this.pCboLyDoKTL) {
                                this.isNew = true
                                this.tbtlId = 0
                                if (this.dsTBTL[0].LYDOKTL_ID) {
                                    this.pCboLyDoKTLSelected = this.dsTBTL[0].LYDOKTL_ID
                                }
                            }
                        }
                        else {
                            this.pBtnStatus.btnDieuLaiEnabled = true
                            this.isNew = true
                            this.tbtlId = 0
                        }
                    }
                }

                this.dsThueBao = await this.layDsHDTheoHdkhId(this.hdkhId)
                if (this.dsThueBao.length > 0) {
                    this.pTxtLoaiHinh = this.dsThueBao[0].LOAIHINH_TB
                }
            }
            catch (e) {

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
                        giaoPhieuKsId: this.giaoPhieuKsId,
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
        async layDsHDTheoHdkhId(hdkhId) {
            try {
                let rs = await this.$root.context.get(
                    "/web-cskh/api/khao-sat-kh-lm/hop-dong-thue-bao-theo-hdkh",
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
        async layDsLanGoiChuaTL(thueBaoId, loai) {
            try {
                let rs = await this.$root.context.get(
                    "/web-cskh/api/xac-nhan-phan-hoi-chat-luong/ds-lan-goi-chua-tra-loi",
                    {
                        id: thueBaoId,
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
        focusInput(name) {
            this.$nextTick(() => this.$refs[name].select());
        },
        async onGhiLaiClicked() {
            // this.focusInput('txtMaGD')
            try {
                this.loading(true)
                if (this.isNew) {
                    this.isDaCapNhap = false
                    if (await this.kiemTraDuLieu(true)) {
                        let data = this.taoDuLieu(true, false)
                        this.loading(true)
                        this.$root.context.post("/web-cskh/api/khao-sat-kh-lm/khtb/ghi-lai", data)
                            .then(res => {
                                if (res.error_code === this.API_SUCCESS)
                                {
                                    if(res.data.ketQua == 1){
                                        this.$root.toastSuccess("Cập nhật thành công!")
                                        if (res.data.msgError)
                                        {
                                            let listMessages = res.data.msgError.split("#")
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
                                        this.closeDialog()
                                    } else {
                                        this.$root.toastError(res.data.msgError);
                                    }

                                } else {
                                    this.$root.toastError("Cập nhật thất bại");
                                }
                            })
                            .catch(e => this.$toast.error("Có lỗi xảy ra!"))
                            .finally(() => this.loading(false))
                        // if (this.isDieuLai) {
                        //     this.$bvModal.msgBoxConfirm(`Cập nhật nội dung này sẽ điều phiếu hỏng cho thuê bao ${this.pTxtMaTB}. Bạn có chắc chắn muốn điều?`,
                        //     {
                        //         title: "Xác nhận hành động",
                        //         centered: true,
                        //         size: "sm"
                        //     }).then((value) => {
                        //         if (value){
                        //             this.loading(true)
                        //             this.$root.context.post("/web-cskh/api/khao-sat-kh-lm/ghi-lai", data)
                        //                 .then(res => {
                        //                     if (res.error_code === this.API_SUCCESS)
                        //                     {
                        //                         this.$root.toastSuccess("Cập nhật thành công!")
                        //                         this.closeDialog()
                        //                     }
                        //                 })
                        //                 .catch(e => this.$toast.error("Có lỗi xảy ra!"))
                        //                 .finally(() => this.loading(false))
                        //         }
                        //         else{
                        //             return
                        //         }
                        //     })
                        // }
                        // else {
                        //     this.loading(true)
                        //     this.$root.context.post("/web-cskh/api/khao-sat-kh-lm/ghi-lai", data)
                        //         .then(res => {
                        //             if (res.error_code === this.API_SUCCESS)
                        //             {
                        //                 this.$root.toastSuccess("Cập nhật thành công!")
                        //                 this.closeDialog()
                        //             }
                        //         })
                        //         .catch(e => this.$toast.error("Có lỗi xảy ra!"))
                        //         .finally(() => this.loading(false))
                        // }
                    }
                    else {
                        return
                    }
                }
                else {
                    if (await this.kiemTraDuLieu(false)) {
                        let data = this.taoDuLieu(false, false)
                        this.loading(true)
                        this.$root.context.post("/web-cskh/api/khao-sat-kh-lm/khtb/ghi-lai", data)
                            .then(res => {
                                if (res.error_code === this.API_SUCCESS)
                                {
                                    if(res.data.ketQua == 1){
                                        this.$root.toastSuccess("Cập nhật thành công!")
                                        if (res.data.msgError)
                                        {
                                            let listMessages = res.data.msgError.split("#")
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
                                        this.closeDialog()
                                    } else {
                                        this.$root.toastError(res.data.msgError);
                                    }
                                } else {
                                    this.$root.toastError("Cập nhật thất bại");
                                }
                            })
                            .catch(e => this.$toast.error("Có lỗi xảy ra!"))
                            .finally(() => this.loading(false))
                        // if (this.isDieuLai) {
                        //     this.$bvModal.msgBoxConfirm(`Cập nhật nội dung này sẽ điều phiếu hỏng cho thuê bao ${this.pTxtMaTB}. Bạn có chắc chắn muốn điều?`,
                        //     {
                        //         title: "Xác nhận hành động",
                        //         centered: true,
                        //         size: "sm"
                        //     }).then((value) => {
                        //         if (value){
                        //             this.loading(true)
                        //             this.$root.context.post("/web-cskh/api/khao-sat-kh-lm/ghi-lai", data)
                        //                 .then(res => {
                        //                     if (res.error_code === this.API_SUCCESS)
                        //                     {
                        //                         this.$root.toastSuccess("Cập nhật thành công!")
                        //                         this.closeDialog()
                        //                     }
                        //                 })
                        //                 .catch(e => this.$toast.error("Có lỗi xảy ra!"))
                        //                 .finally(() => this.loading(false))
                        //         }
                        //         else{
                        //             return
                        //         }
                        //     })
                        // }
                        // else {
                        //     this.loading(true)
                        //     this.$root.context.post("/web-cskh/api/khao-sat-kh-lm/ghi-lai", data)
                        //         .then(res => {
                        //             if (res.error_code === this.API_SUCCESS)
                        //             {
                        //                 this.$root.toastSuccess("Cập nhật thành công!")
                        //                 this.closeDialog()
                        //             }
                        //         })
                        //         .catch(e => this.$toast.error("Có lỗi xảy ra!"))
                        //         .finally(() => this.loading(false))
                        // }
                    }
                }
            }
            catch (e) {
                console.log(e)
            } finally {
                this.loading(false)
            }
        },
        async ghiLaiConfirm() {

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
            if (this.pTxtSoLH == "" ) {
                this.$root.toastError("Chưa nhập điện thoại liên hệ !");
                this.focusInput("txtSoLH");
                return false;
            }

            if (this.ketQuaKS.length > 0 && this.pCboLyDoKTL) {
                this.$root.toastError("Bạn đang chọn \"Không trả lời\" và đồng thời tích chọn trả lời trong Danh sách câu hỏi. Bạn có muốn tiếp tục?");
                return false;
            }

            if ( this.ketQuaKS.length > 0 && this.pCboLyDoKTL )
            {
                this.$root.toastError( 'Bạn đang chọn "Không trả lời" và đồng thời tích chọn trả lời trong Danh sách câu hỏi!' );
                return false;
            }

            let cauHoiKS = this.$refs["cauHoiKS"];
            if (!this.pCboLyDoKTL) {
                if (this.ketQuaKS.length <= 0) {
                    this.$root.toastError("Bạn phải chọn câu trả lời!");
                    return false;
                }
                if (this.pThamSoMacDinh.BATBUOC_TRALOI_ALL_CAUHOI) {
                }
            }

            for (let chIndex = 0; chIndex < this.ketQuaKS.length; chIndex++) {
                let traLoiId = this.ketQuaKS[chIndex].RCH_ID;
                let cauHoiPhu = this.cauHoiKSChau.filter( x => x.RCH_ID == traLoiId );

                if (cauHoiPhu.length > 0) {
                    let chpDcTL = false;
                    for ( let chpIndex = 0; chpIndex < cauHoiPhu.length; chpIndex++ ) {
                        if (cauHoiPhu[chpIndex].Checked) {
                            chpDcTL = true;
                            break;
                        }
                    }
                    if (!chpDcTL) {
                        this.$root.toastError( "Bạn chọn câu trả lời '" + this.ketQuaKS[chIndex].TRALOI + "', mà chưa chọn lý do ! " );
                        return false;
                    }
                }


                //kiểm tra các câu trả lời ý kiến khác mà không nhập text.
                let traLoi = this.cauHoiKSChau.filter( x => x.TRALOI_ID == traLoiId );

                if (traLoi.length > 0) {
                    if ( (traLoi[0].LOAI == 1 || traLoi[0].LOAI == 2) && this.ketQuaKS[chIndex].NOIDUNG ) {
                        this.$root.toastError( "Bạn chưa nhập nội dung cho câu trả lời '" + this.ketQuaKS[chIndex].TRALOI + " ! " );
                        return false;
                    }
                }

            }

            // Kiểm tra chỉ user người khảo sát mới được sửa nội dung ks và chỉ sửa trong tháng
            if (!themMoi) {
            }

            if (this.$auth.getPhanVungID() == 28) {
                //kiem tra giu phieu
            }
            return true;
        },

        async kiemTraKhaoSat()
        {
            try
            {
                let kq = this.ketQuaKS;
                let postData = {
                    "nhanVienId": this.$root.token.getNhanVienID(),
                    "tbtlId": this.tbtlId,
                    "loaiId": 1,
                    "dsCauHoiId": kq.map(x => x.CAUHOI_ID).join("#"),
                    "dsTraLoiId": kq.map(x => x.TRALOI_ID).join("#"),
                    "dsNoiDung": kq.map(x => x.NOIDUNG).join("#"),
                    "dsThamSo": ""
                }
                let response = await this.$root.context.post("/web-cskh/api/common/kiem-tra-ks", postData)
                if (response.error_code === CSKH_CONST.API_SUCCESS)
                return true
            }
            catch (e)
            {
                console.log(e)
            }
            return false
        },

        taoDuLieu(isThemMoi, isXuatLai) {
            let kq = this.ketQuaKS;
            let ttksId = 0
            let lanKsToiDa = 4

            this.isDieuLai = false
            this.isDieuLaiXN = false

            if (this.pCboLyDoKTL) {
                ttksId = 1  //chưa trả lời
                let dsktl = this.dsLanGoiChuaTraLoi.filter(x => x.LYDO_KTL)
                //quá số lần ks tối đa thì không gọi lại KH nữa
                if (dsktl.length + 1 > lanKsToiDa) {
                    ttksId = 7
                }
            }
            else {
                ttksId = 6 // đã hoàn thành
            }

            this.isDaCapNhap = true

            let data = {
                tbtlId: isThemMoi ? 0 : this.tbtlId,
                hdkhId: this.hdkhId,
                hdtbId: this.hdtbId,
                thueBaoId: this.thueBaoId,
                nguoiTl: this.pTxtTenNguoiTL,
                dienThoaiLh: this.pTxtSoLH,
                ghiChu: this.pTxtGhiChu,

                ttksId: ttksId,
                giaoPhieuKsId: this.giaoPhieuKsId,

                lyDoKtlId: this.pCboLyDoKTL ? this.pCboLyDoKTLSelected : 0,
                dsCauHoiId: kq.map(x => x.CAUHOI_ID).join("#"),
                dsTraLoiId: kq.map(x => x.TRALOI_ID).join("#"),
                dsNoiDung: JSON.stringify(kq.map(x => `${x.NOIDUNG}`)),
                hinhThuc: this.hinhThucId,
                gioHenTu: this.pchkHenKS_Tu ? moment(this.pDtpHenKS_Tu).format("DD/MM/yyyy HH:mm") : "",
                gioHenDen: this.pchkHenKS_Den ? moment(this.pDtpHenKS_Den).format("DD/MM/yyyy HH:mm") : ""
            };

            kq.forEach(row => {
                if (row.DIEULAI == 1) {
                    this.isDieuLai = true
                }
                if (row.DIEULAI_XN == 1) {
                    this.isDieuLaiXN = true
                }
            })
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

            this.$bvModal.show("popup-confirmDieuLai_tb");
        },
        async onDieuLaiConfirmed() {
            try {
                this.$root.showLoading(true);
                if (this.isNew)
                {
                    if (await this.kiemTraDuLieu(true))
                    {
                        let data = this.taoDuLieu(true, true);
                        let rs = await this.$root.context.post( "/web-cskh/api/khao-sat-kh-lm/khtb/dieu-lai", data );
                        if (rs.error_code === "BSS-00000000")
                        {
                            if (rs.data.ketQua != 1) {
                                this.$root.toastError(rs.data.msgError);
                            } else
                                this.$root.toastSuccess("Điều phiếu thành công!");
                        } else {
                            this.$root.toastError("Điều phiếu thất bại");
                        }
                    }
                }
            }
            catch (e)
            {
                console.log(e);
            }
            finally
            {
                this.$root.showLoading(false);
            }
        },
        onDsThueBaoRowSelected(item) {
            if (item)
            {
                this.phieuId = item.HDTB_ID
                this.maTb = item.MA_TB
            }
        },
        onDsLanGoiRowSelected(item) {

        },
        onLichSuBHClicked() {
            this.$bvModal.show("modal-tracuu-baohong");
            // this.$root.toastError("Gọi tới form frmTraCuuPhieuBH - Tra cứu điều hành xử lý sự cố")
        },
    }
};
</script>
