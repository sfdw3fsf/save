<template src="./frmKhaoSatKH_TDDV.html">

</template>
<style scope>
.e-grid .e-gridheader
{
    position: -webkit-sticky;
    position: sticky;
    top: 0px;
    z-index: 1;
}
</style>
<script>
import moment from "moment";
import { mapActions } from "vuex";
import cauHoiKS from "./frmCauHoiKS.vue";
import frmLichSuHenBaoHong from "./frmLichSuHenBaoHong.vue";
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
    name: "frmKhaoSatKH_TDDV",
    components: {
        cauHoiKS,
        frmLichSuHenBaoHong,
        confirmPopup: YesNoModal
    },
    props: {
    },
    computed: {
        xacNhanDieuLai() {
            return (
                "Bạn có chắc chắn muốn điều hỏng thuê bao " +
                this.maTb +
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
        },
        lichSuKSUrl() {
            return `/#/cskh/TraCuuThueBaoKhaoSat?maTB=${this.maTb}`
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

            pTxtMaGD: '',
            pTxtNgayYC: '',
            pTxtLoaiHD: '',
            // pTxtLoaiHinh: '',
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
                btnDieuLaiVisible: true
            },

            loaiNhomId: 0,
            hinhThuc: 0
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
            this.$refs.frmKhaoSatKH_TDDV.show();
        },
        closeDialog() {
            this.$emit("onHideKhaoSat");
            this.$refs.frmKhaoSatKH_TDDV.hide();
            this.tbtlId = 0;
        },
        clearData(){
            this.pTxtMaGD = ""
            this.pTxtNgayYC = ""
            this.pTxtLoaiHD = ""
            this.pTxtTenKH = ""
            this.pTxtDiaChiKH = ""
            this.pTxtDienThoaiLH = ""
            this.pTxtTenNguoiTL = ""
            this.pTxtSoLH = ""
            this.pTxtGhiChu = ""

            this.baoHongId = 0
            this.thueBaoId = 0

            this.pCboLyDoKTL = false
            this.pCboLyDoKTLSelected = ""
            this.isNew = false
            this.isDaCapNhap = false
            this.isDieuLai = false
            this.isDieuLaiXN = false
        },
        async onFormLoad() {
            try {
                this.$root.showLoading(true);
                this.clearData();
                // await Promise.all([
                //     // this.getThamSoMacDinh(),
                //     this.getLyDoKTL(),
                //     this.hienThiThongTinTB(),
                //     this.hienThiCH()
                // ]);
                await this.getLyDoKTL(),
                await this.hienThiThongTinTB(),
                await this.hienThiCH()

                if (this.pThamSoMacDinh.OUTBOUND_KHONG_DIEULAI) {
                    this.pBtnStatus.btnDieuLaiVisible = false
                }
            } catch (e) {
                console.log(e);
            } finally {
                this.$root.showLoading(false);
            }
        },
        async hienThiCH() {
            this.hinhThuc = 0
                if (this.loaiNhomId == 5)//Khóa máy
                    this.hinhThuc = 4;
                else if (this.loaiNhomId == 6)//Tạm dừng
                    this.hinhThuc = 5;
                else if (this.loaiNhomId == 10)//Di động tham gia gói GĐ
                    this.hinhThuc = 9;
                else//Thanh lý theo yc
                    this.hinhThuc = 6;

                this.$refs.cauHoiKS.nhanVienId = 33
                this.$refs.cauHoiKS.hinhThuc  = this.hinhThuc
                this.$refs.cauHoiKS.tbtlId = this.tbtlId
                await this.$refs.cauHoiKS.loadData()

                // dt = fims.LAY_DS_CAUHOI_THEOLOAINV_TDDV(Loai_nv_ks, hinhthuc);
                // if (!isNew)
                //     dtCTTBTL = fims.CT_TBTRALOI(tbtl_id);
                // listQuestion1.dt = dt;
                // listQuestion1.dtCTTBTL = dtCTTBTL;
                // listQuestion1.isNew = isNew;
                // listQuestion1.KhoiTaoCauHoi();
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
                let rs = "";
                if(this.loaiNhomId != 10) {
                    rs = await this.$root.context.get('/web-cskh/api/khao-sat-kh-lm/hop-dong-thue-bao-theo-hdkh', {hdkhId: this.hdkhId})
                } else {
                    rs = await this.$root.context.get('/web-cskh/api/khao-sat-kh-lm/hop-dong-tbdd-theo-hdkh', {hdkhId: this.hdkhId})
                }

                if (rs.error_code === this.API_SUCCESS) {
                    let ds = rs.data.data || []
                    this.dsThueBao = rs.data.data || []
                    if(this.loaiNhomId == 10) {
                        this.$refs.gridDsThueBao.selectedValues.push(this.hdtbId)
                    }

                    if (ds.length > 0) {
                        this.thueBaoId = ds[0].THUEBAO_ID
                        this.pTxtMaGD = ds[0].MA_GD
                        this.pTxtTenKH = ds[0].TEN_KH
                        this.pTxtDiaChiKH = ds[0].DIACHI_KH
                        this.pTxtDienThoaiLH = ds[0].SO_DT
                        if (this.$root.token.getPhanVungID() == 26) {
                            this.pTxtSoLH = ds[0].SO_DT
                            this.pTxtTenNguoiTL = ds[0].TEN_KH
                        }
                        this.pTxtLoaiHD = ds[0].TEN_LOAIHD
                        this.pTxtNgayYC = ds[0].NGAY_YC
                        if(this.baoHongId > 0)    //if(this.loaiNhomId != 10)
                        {
                            this.dsLanGoiChuaTraLoi = await this.layDsLanGoiChuaTL(this.hdkhId, this.baoHongId, 2)
                        }
                        else
                        {
                            this.dsLanGoiChuaTraLoi = await this.layDsLanGoiChuaTL(this.hdkhId, this.baoHongId, 1)
                        }

                        this.dsTBTL = await this.layDsTBTL(this.hdkhId, this.loaiNhomId)
                        if (this.dsTBTL.length > 0) {
                            this.isNew =false
                            if (this.tbtlId == 0) {
                                this.tbtlId = this.dsTBTL[0].TBTL_ID
                            }

                            this.pTxtTenNguoiTL = this.dsTBTL[0].NGUOI_TL
                            this.pTxtSoLH = this.dsTBTL[0].DIENTHOAI_LH
                            this.pTxtGhiChu = this.dsTBTL[0].GHICHU
                            this.ngayKS = this.dsTBTL[0].NGAY_HOI

                            this.pCboLyDoKTL = this.dsTBTL[0].TRANGTHAI == 0
                            if (this.pCboLyDoKTL) {
                                this.isNew = true
                                this.tbtlId = 0
                                if (this.dsTBTL[0].LYDOKTL_ID) {
                                    this.pCboLyDoKTLSelected = this.dsTBTL[0].LYDOKTL_ID
                                }
                            }
                        }
                        else {
                            this.isNew = true
                            this.tbtlId = 0
                        }
                    }
                }

                this.dsThueBao = await this.layDsHDTheoHdkhId(this.hdkhId)
                // if (this.dsThueBao.length > 0) {
                //     this.pTxtLoaiHinh = this.dsThueBao[0].LOAIHINH_TB
                // }
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
                        loai: 1,
                        giaoPhieuKsId: 6,
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
        async layDsLanGoiChuaTL(hdkhId, baoHongId, loai) {
            try {
                let rs = await this.$root.context.get(
                    "/web-cskh/api/xac-nhan-phan-hoi-chat-luong/ds-lan-goi-chua-tra-loi",
                    {
                        id: hdkhId,
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
                            this.loading(true)
                            this.$root.context.post("/web-cskh/api/khao-sat-kh-lm/tddv/ghi-lai", data)
                                .then(res => {
                                    if (res.error_code === this.API_SUCCESS)
                                    {
                                        if (res.data.ketQua == 1) {
                                            this.$root.toastSuccess("Cập nhật thành công!")
                                            if (res.data.msgError)
                                            {
                                                let listMessages = res.data.msgError.split("#")
                                                listMessages.forEach(message => {
                                                    let pattern = /.*\d/g;
                                                    let result = message.match(pattern)
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
                                            this.$root.toastError(res.data.msgError);
                                        }
                                    }
                                })
                                .catch(e => this.$root.toastError("Có lỗi xảy ra!"))
                                .finally(() => this.loading(false))
                        // }
                    }
                    else {
                        return
                    }
                }
                else {
                    if (await this.kiemTraDuLieu(false)) {
                        let data = this.taoDuLieu(false, false)
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
                            this.loading(true)
                            this.$root.context.post("/web-cskh/api/khao-sat-kh-lm/tddv/ghi-lai", data)
                                .then(res => {
                                    if (res.error_code === this.API_SUCCESS)
                                    {
                                        if (res.data.ketQua == 1) {
                                            this.$root.toastSuccess("Cập nhật thành công!")
                                            if (res.data.msgError)
                                            {
                                                let listMessages = res.data.msgError.split("#")
                                                listMessages.forEach(message => {
                                                    let pattern = /.*\d/g;
                                                    let result = message.match(pattern)
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
                                            this.$root.toastError(res.data.msgError);
                                        }
                                    }
                                })
                                .catch(e => this.$root.toastError("Có lỗi xảy ra!"))
                                .finally(() => this.loading(false))
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

            // if (this.ketQuaKS.length > 0 && this.pCboLyDoKTL) {
            //     this.$root.toastError("Bạn đang chọn \"Không trả lời\" và đồng thời tích chọn trả lời trong Danh sách câu hỏi. Bạn có muốn tiếp tục?");
            //     return false;
            // }

            // if (
            //     this.ketQuaKS.length > 0 &&
            //     this.pCboLyDoKTL
            // ) {
            //     this.$root.toastError(
            //         'Bạn đang chọn "Không trả lời" và đồng thời tích chọn trả lời trong Danh sách câu hỏi!'
            //     );
            //     return false;
            // }

            let cauHoiKS = this.$refs["cauHoiKS"];
            if (!this.pCboLyDoKTL) {
                if (this.ketQuaKS.length <= 0) {
                    this.$root.toastError("Bạn phải chọn câu trả lời!");
                    return false;
                }
                if (this.pThamSoMacDinh.BATBUOC_TRALOI_ALL_CAUHOI) {
                }
            }
            console.log(cauHoiKS)
            for (let chIndex = 0; chIndex < this.ketQuaKS.length; chIndex++) {
                let traLoiId = this.ketQuaKS[chIndex].RCH_ID;
                let cauHoiPhu = this.cauHoiKSChau.filter(
                    x => x.RCH_ID == traLoiId
                );
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
                let traLoi = this.cauHoiKSChau.filter(
                    x => x.TRALOI_ID == traLoiId
                );
                if (traLoi.length > 0) {
                    if ( (traLoi[0].LOAI == 1 || traLoi[0].LOAI == 2) && this.ketQuaKS[chIndex].NOIDUNG ) {
                        this.$root.toastError( "Bạn chưa nhập nội dung cho câu trả lời '" + this.ketQuaKS[chIndex].TRALOI + " ! " );
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
// {
// "tbtlId": 0, -
// "hdkhId": 1406008, -
// "hdtbId": 1474319, -
// "thueBaoId": 1474319, -
// "hinhThuc": 3,
// "ttksId": 3, -
// "lyDoKtlId": 22,-
// "giaoPhieuKsId": 0, -
// "nguoiTl": "Nguyễn Văn A", -
// "dienThoaiLh": "0914566777", -
// "ghiChu": "test ", -
// "dsCauHoiId": "8851#8852#8853", -
// "dsTraLoiId": "523557#523562#523567", -
// "dsNoiDung": "tesst kh nói được trả lời 1 #test nd trả lời 2#", -
// "nguoiDungId": 17237
// }
            let data = {
                tbtlId: isThemMoi ? 0 : this.tbtlId,
                hdkhId: this.hdkhId,
                hdtbId: this.hdtbId,
                thueBaoId: this.thueBaoId,
                hinhThuc: this.hinhThuc,
                nguoiTl: this.pTxtTenNguoiTL,
                dienThoaiLh: this.pTxtSoLH,
                ghiChu: this.pTxtGhiChu,
                ttksId: ttksId,
                gpksId: this.giaoPhieuKsId,
                lyDoKtlId: this.pCboLyDoKTL ? this.pCboLyDoKTLSelected : 0,
                dsCauHoiId: kq.map(x => x.CAUHOI_ID).join("#"),
                dsTraLoiId: kq.map(x => x.TRALOI_ID).join("#"),
                dsNoiDung: JSON.stringify(kq.map(x => `${x.NOIDUNG}`)),
            };
            console.log(data)
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

            this.$bvModal.show("popup-confirmDieuLai_tddv");
        },
        async onDieuLaiConfirmed() {
            try {
                this.$root.showLoading(true);
                if (this.isNew) {
                    if (await this.kiemTraDuLieu(true)) {
                        let data = this.taoDuLieu(true, true);
                        let rs = await this.$root.context.post(
                            "/web-cskh/api/khao-sat-kh-lm/tddv/dieu-lai",
                            data
                        );
                        console.log(rs);
                        if (rs.error_code === "BSS-00000000") {
                            if (rs.data.ketQua == 1) {
                                this.$root.toastSuccess("Điều lại thành công!");
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
        onDsThueBaoRowSelected(item) {
            if (item) {
                this.phieuId = item.HDTB_ID
                this.maTb = item.MA_TB
            }
        },
        onDsLanGoiRowSelected(item) {

        }
    }
};
</script>
