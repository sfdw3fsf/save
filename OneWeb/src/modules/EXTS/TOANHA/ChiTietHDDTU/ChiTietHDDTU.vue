<template src='./ChiTietHDDTU.html'></template>
<script>
import { mapActions, mapState, mapGetters, mapMutations } from "vuex";
import moment from 'moment';
import Vue from "vue";
import ChiTietHDDTUAPI from '../api/ChiTietHDDTUAPI'
import DateTimeLib from '../../../../utils/DateTimeLib'
import { DatePickerPlugin } from '@syncfusion/ej2-vue-calendars'
// import { getterName,actionName,statePropertyName,mutationName} from '../store/tientrinhdautu'
// import { DialogPlugin } from '@syncfusion/ej2-vue-popups'

Vue.use(DatePickerPlugin)
export default {
    components: {},
    name: "ChiTietHDDTU",
    mounted() {
    },
    computed: {
        // ...mapState("quanlythietbibts", statePropertyName),
        // ...mapGetters("quanlythietbibts", getterName),
        enabledTongTien: function() {
            return this.params.p_Loai_ChiPhi != 3;
        },
        vloai_chiphi: {
            get() {
                return parseInt(this.params.p_Loai_ChiPhi);
            }
        },
        vthang: {
            get() {
                return this.params.p_dtpThang != null && this.params.p_dtpThang != '' ? this.f_DateToString(this.params.p_dtpThang, 'YYYYMM') : ''
            }
        },
        vdongia: {
            get() {
                return this.params.p_txtDonGia != null && this.params.p_txtDonGia != '' ? parseInt(this.params.p_txtDonGia) : 0
            }
        },
        vgiatri_cu: {
            get() {
                return this.params.p_txtGiaTri_Cu != null && this.params.p_txtGiaTri_Cu != '' ? parseInt(this.params.p_txtGiaTri_Cu) : 0
            }
        },
        vgiatri_moi: {
            get() {
                return this.params.p_txtGiaTri_Moi != null && this.params.p_txtGiaTri_Moi != '' ? parseInt(this.params.p_txtGiaTri_Moi) : 0
            }
        },
        vtongtien: {
            get() {
                return this.params.p_txtTongTien != null && this.params.p_txtTongTien != '' ? parseInt(this.params.p_txtTongTien) : 0
            }
        },
        vloai_chiphi_luoi: {
            get() {
                return this.grvDanhSach_FocusedRow != null && this.grvDanhSach_FocusedRow["loai_chiphi"] != '' ? parseInt(this.grvDanhSach_FocusedRow["loai_chiphi"]) : 1
            }
        },
        vthang_luoi: {
            get() {
                return this.grvDanhSach_FocusedRow != null && this.grvDanhSach_FocusedRow["thang"] != '' ? this.f_StringToDate(this.grvDanhSach_FocusedRow["thang"], 'YYYYMMDD') : new Date()
            }
        },
        vdongia_luoi: {
            get() {
                return this.grvDanhSach_FocusedRow != null && this.grvDanhSach_FocusedRow["dongia"] != '' ? parseInt(this.grvDanhSach_FocusedRow["dongia"]) : 0
            }
        },
        vgiatri_cu_luoi: {
            get() {
                return this.grvDanhSach_FocusedRow != null && this.grvDanhSach_FocusedRow["giatri_cu"] != '' ? parseInt(this.grvDanhSach_FocusedRow["giatri_cu"]) : 0
            }
        },
        vgiatri_moi_luoi: {
            get() {
                return this.grvDanhSach_FocusedRow != null && this.grvDanhSach_FocusedRow["giatri_moi"] != '' ? parseInt(this.grvDanhSach_FocusedRow["giatri_moi"]) : 0
            }
        },
        vtongtien_luoi: {
            get() {
                return this.grvDanhSach_FocusedRow != null && this.grvDanhSach_FocusedRow["tongtien"] != '' ? parseInt(this.grvDanhSach_FocusedRow["tongtien"]) : 0
            }
        },
        isRequiredHT: function(){
            return this.$root.token.getPhanVungID() == 28;
        }

    },
    data() {
        return {
            grvDanhSach_FocusedRow: null,
            params: {
                p_txtToaNha: "",
                p_txtHDDT: "",
                p_Loai: "",
                p_DT: "",
                p_Loai_ChiPhi: "",
                p_txtGhiChu: "",
                p_DichVuVT: "",
                p_LoaiTB: "",
                p_txtTyLeHH: "",
                p_dtpThang: new Date(),
                p_txtDonGia: "",
                p_txtGiaTri_Cu: "",
                p_txtGiaTri_Moi: "",
                p_txtTongTien: ""
            },

            cboLoai: [],
            cboDT: [],
            cboLoai_ChiPhi: [],
            cboDichVuVT: [],
            cboLoaiTB: [],
            dgvDanhSach: [],

            ctdtu_id: 0,
            dt: [],
            dsChiTiet: [],
            vhddtu_id: 0,
            vma_hd: "",
            vten_hd: "",
            vten_toanha: "",
            vchudtu_id: 0,
            vdoitac_id: 0,

            Enabled: {
                tsbtnNhapMoi: false,
                tsbtnGhiLai: false,
                tsbtnXoa: false,
                tsbtnHuyBo: false,
                chayLaiDTPC: false,
            },
            Text: {
                layoutControlGroup4: ""
            },
            Visible: {
                layoutDichVuVT: true,
                layoutLoaiHinhTB: true,
                layoutTyLeHH: true,
                layoutThang: true,
                layoutDonGia: true,
                layoutGiaTri_Cu: true,
                layoutGiaTri_Moi: true,
                layoutTongTien: true,
            },
            checkThangChot: false,
            thangChot:{
                value: new Date(),
                currentDate: new Date(),
                changeDate: new Date(),
            },
            waterMark: "Chọn ngày",
            dateFormat: "MM/yyyy",
        };
    },
    methods: {
        // ...mapActions("ecms/quanlythietbibts", actionName),
        // ...mapMutations("quanlythietbibts", mutationName),
        onCheckThangChot(args){
            if(args){
                 this.Enabled.tsbtnXoa = false;
                 this.Enabled.tsbtnNhapMoi = false;
            }
        },

        onChangeThangChot(args){
            // if(args != null && this.isRequiredHT){
            //     var selectedYear=args.getFullYear();
            //     var selectedMonth=args.getMonth()+1;
            //     var currentDate=new Date();
            //     if(selectedYear == currentDate.getFullYear() || (selectedMonth == 12 && selectedYear == currentDate.getFullYear() - 1)){
            //         // this.tylePCDT.readonly=false;
            //     }else{
            //         // this.tylePCDT.readonly=true;
            //     }
            // }
        },

        onChangeTyLePCDT(args){
            // if(args != null && this.isRequiredHT){
            //     this.tylePCDT.changeValue=args;
            //     if(this.tylePCDT.changeValue != this.tylePCDT.currentValue){
            //         this.Enabled.chayLaiDTPC=true;
            //     }else{
            //         this.Enabled.chayLaiDTPC=false;
            //     }
            // }
        },

        // chuyển Date thành type string
        f_DateToString: function (value, format = 'DD/MM/YYYY') {
            return moment(value).format(format)
        },
        f_StringToDate(value, format = 'DD/MM/YYYY') {
            return moment(value, format).toDate();
        },
        async frmChiTiet_HDDTU_Load() {
            await this.SetButton(-1)
            await this.Load_cbo()
            await this.ShowData()
            this.params.p_txtHDDT = this.vma_hd + " -- " + this.vten_hd;
            this.params.p_txtToaNha = this.vten_toanha;
            this.params.p_DT = this.vchudtu_id;

            var kq = []

            // let url = `/web-toanha/chitiet-hddtu/fn-lay-tt-qlda/${this.vhddtu_id}`;
            // let response = await this.$root.context.get(url);
            // if (response.error_code === 'BSS-00000000' && response.data != undefined) {
            //     kq = response.data.data || []
            // } else { 
            //     this.$toast.error(response.data);
            // }

            await ChiTietHDDTUAPI.getThongTinQLDA(this.axios, { hddtu_id: this.vhddtu_id })
                .then(
                    (response) => {
                        kq = response.data.data || []
                    }
                )
                .catch(error => this.$toast.error(error)).finally(function () { })

            this.vchudtu_id = parseInt(kq.toString().split('-')[0]);
            this.vdoitac_id = parseInt(kq.toString().split('-')[1]);
        },
        async ShowData() {
            this.dt = []
            this.dgvDanhSach = []

            // let url = `/web-toanha/chitiet-hddtu/sp-lay-ds-chitiet-hddtu/${this.vhddtu_id}`;
            // let response = await this.$root.context.get(url);
            // if (response.error_code === 'BSS-00000000' && response.data != undefined) {
            //     this.dgvDanhSach = response.data || []
            // } else { 
            //     this.$toast.error(response.data);
            // }
            let chuky = 0;
            if (this.checkThangChot) {
                chuky = parseInt(DateTimeLib.toDateDisplayWithFormat(this.thangChot.value, "YYYYMM"));
            }

            await ChiTietHDDTUAPI.getDSChiTietHDDTU(this.axios, { hddtu_id: this.vhddtu_id, chuky: chuky })
                .then(
                    (response) => {
                        this.dt = response.data.data || []
                    }
                )
                .catch(error => this.$toast.error(error)).finally(function () { })
            if (this.dt.length) {
                this.dgvDanhSach = this.dt
            }
            else {
                await this.SetButton(1)
            }
            
        },
        async Load_cbo() {
            //cboLoai
            this.cboLoai = [
                { LOAI_ID: 2, TEN_LOAI: "Chủ đầu tư" }
            ]
            if(!this.isRequiredHT){
                this.cboLoai.push({ LOAI_ID: 1, TEN_LOAI: "Đối tác" })
            }
            //cboDichVuVT
            var dtDVVT = []

            // let url = `/web-toanha/chitiet-hddtu/sp-lay-dichvuvt`;
            // let response = await this.$root.context.get(url);
            // if (response.error_code === 'BSS-00000000' && response.data != undefined) {
            //     this.cboDichVuVT = response.data.data || []
            // } else { 
            //     this.$toast.error(response.data);
            // }

            await ChiTietHDDTUAPI.getDSDichVuVT(this.axios,)
                .then(
                    (response) => {
                        dtDVVT = response.data.data || []
                    }
                )
                .catch(error => this.$toast.error(error)).finally(function () { })
            this.cboDichVuVT = dtDVVT

            //cboLoai_ChiPhi
            this.cboLoai_ChiPhi = [
                { VID: 1, VNAME: "1. Tỷ lệ PCDT/Hoa hồng" },
                { VID: 2, VNAME: "2. Tiền thuê mặt bằng" },
                { VID: 3, VNAME: "3. Tiền điện" },
                { VID: 4, VNAME: "4. Khác" },
                { VID: 5, VNAME: "5. Doanh thu phân chia khoán" },
                { VID: 6, VNAME: "6. Tiền lắp đặt" },
            ]
        },
        async cboLoai_EditValueChanged() {
            let vdt_id = 0;
            // var dtDT = []
            this.cboDT = []

            if (this.params.p_Loai.toString() == "1") {
                vdt_id = this.vdoitac_id;
            }
            else if (this.params.p_Loai.toString() == "2") {
                vdt_id = this.vchudtu_id;
            }
            // await ChiTietHDDTUAPI.getDanhSachDT(this.axios, {loai: this.params.p_Loai})
            //     .then(
            //         (response) => {
            //           dtDT = response.data.data || []
            //         }
            //     )
            //     .catch(error=>this.$toast.error(error)).finally(function() {})
            let url = `/web-toanha/chitiet-hddtu/sp-lay-ds-dt/${this.params.p_Loai}`;
            let response = await this.$root.context.get(url);
            if (response.error_code == "BSS-00000000" && response.data) {
                this.cboDT = response.data || []
            } else {
                this.cboDT = []
            }

            if (vdt_id != 0) {
                this.params.p_DT = vdt_id
            }
        },
        async cboDichVuVT_EditValueChanged() {
            //cboLoaiTB
            var dtLoaiTB = []
            if (this.params.p_DichVuVT != null && this.params.p_DichVuVT != "") {

                // let url = `/web-toanha/chitiet-hddtu/sp-lay-tt-loaihinh-tb/${this.params.p_DichVuVT}`;
                // let response = await this.$root.context.get(url);
                // if (response.error_code == "BSS-00000000" && response.data) {
                //     this.cboLoaiTB = response.data.data || []
                // } else {
                //     this.cboLoaiTB = []
                // }

                await ChiTietHDDTUAPI.getThongTinLoaiHinhTB(this.axios, { dichvuvt_id: this.params.p_DichVuVT })
                    .then(
                        (response) => {
                            dtLoaiTB = response.data.data || []
                        }
                    )
                    .catch(error => this.$toast.error(error)).finally(function () { })
                this.cboLoaiTB = dtLoaiTB
                // cboLoaiTB.Properties.View.Columns["MA_LHTB"].Visible = false;
            }
        },
        async SetButton(status) {
            this.Enabled.tsbtnNhapMoi = false;
            this.Enabled.tsbtnGhiLai = false;
            this.Enabled.tsbtnXoa = false;
            this.Enabled.tsbtnHuyBo = false;
            switch (status) {
                case -1: //Begin
                    this.Enabled.tsbtnGhiLai = true;
                    this.Enabled.tsbtnHuyBo = true;
                    break;
                case 0: //Begin
                    this.Enabled.tsbtnNhapMoi = true;
                    await this.Clear();
                    break;
                case 1: //Insert
                    await this.Clear();
                    this.Enabled.tsbtnGhiLai = true;
                    this.Enabled.tsbtnHuyBo = false;
                    this.Enabled.tsbtnXoa = true;
                    this.Enabled.tsbtnNhapMoi = false;
                    break;
                case 2: //Cancel
                    this.Enabled.tsbtnNhapMoi = true;
                    //this.Enabled.tsbtnXoa = true;
                    await this.Clear();
                    break;
                case 3: //Edit
                    this.Enabled.tsbtnNhapMoi = true;
                    this.Enabled.tsbtnXoa = true;
                    this.Enabled.tsbtnGhiLai = true;
                    this.Enabled.tsbtnHuyBo = true;
                    break;
            }

        },
        async Clear() {
            this.params.p_Loai = 2;
            await this.cboLoai_EditValueChanged()
            this.params.p_DT = this.vchudtu_id;
            this.params.p_DichVuVT = 0;
            this.params.p_LoaiTB = 0;
            this.params.p_txtTyLeHH = 0;

            this.params.p_Loai_ChiPhi = 1;
            this.params.p_dtpThang = "";
            this.params.p_txtDonGia = 0;
            this.params.p_txtGiaTri_Cu = 0;
            this.params.p_txtGiaTri_Moi = 0;
            this.params.p_txtTongTien = 0;
            this.params.p_txtGhiChu = "";
            this.$refs.cboLoai.focusIn();
        },
        async tsbtnNhapMoi_Click() {
            await this.SetButton(1)
        },
        async tsbtnGhiLai_Click() {
            await this.CapNhat()
        },
        async tsbtnXoa_Click() {
            await this.$bvModal.msgBoxConfirm("Bạn chắc chắn muốn xóa?", {
                "okVariant": 'success',
                "okTitle": 'Đồng ý',
                "cancelTitle": 'Hủy',
                "centered": true,
            })
                .then(async (value) => {
                    if (value) {
                        let chuky = 0;
                        if (this.checkThangChot) {
                            chuky = parseInt(DateTimeLib.toDateDisplayWithFormat(this.thangChot.value, "YYYYMM"));
                        }
                        await ChiTietHDDTUAPI.CapNhatChiTietHDDTU(this.axios, {
                            p_kieu: 3,
                            p_data: JSON.stringify([{ CTDTU_ID: this.ctdtu_id, HDDTU_ID: this.vhddtu_id, DICHVUVT_ID: this.params.p_DichVuVT, LOAI_CHIPHI: this.vloai_chiphi }]),
                            chuky: chuky
                        })
                            .then(async response => {
                                // var d = response.data.data || []
                                this.$toast.success("Xóa dữ liệu thành công!")
                                await this.ShowData()
                            }
                            )
                            .catch(error => this.$toast.error(error)).finally(function () { })
                    }
                })
                .catch(err => {
                    // An error occurred
                })
        },
        async tsbtnHuyBo_Click() {
            await this.SetButton(2)
        },
        async tsbtnThoat_Click() {

        },
        async tsbtnXem_Click() {
            await this.ShowData()
        },
        async CapNhat() {
            // tlstpTop.Focus();
            if (!this.Enabled.tsbtnNhapMoi) {
                if (!(await this.Kiemtra_DL(true))) { return }

                let dt_id = parseInt(this.params.p_DT);
                let dichvuvt_id = parseInt(this.params.p_DichVuVT);
                let loaitb_id = parseInt(this.params.p_LoaiTB);
                let rs = await this.KiemTra_DaTonTai(dt_id, this.vhddtu_id, dichvuvt_id, loaitb_id);

                if (rs != 0) {
                    this.$root.toastError("Bản ghi đã tồn tại nên bạn không thể thêm mới.");
                    // await this.$bvModal.msgBoxConfirm("Bản ghi đã tồn tại, bạn có muốn cập nhật Tỷ lệ hoa hồng không?", {
                    //     "okVariant": 'success',
                    //     "okTitle": 'Đồng ý',
                    //     "cancelTitle": 'Hủy',
                    //     "centered": true,
                    // })
                    //     .then(async (value) => {
                    //         if (value) {
                    //             await this.TaoDuLieu(false);
                    //             // btd.Update(dsChiTiet, DatabaseConstants.DB2);
                    //             await ChiTietHDDTUAPI.CapNhatChiTietHDDTU(this.axios, {
                    //                 p_kieu: 2,
                    //                 p_data: JSON.stringify(this.dsChiTiet)
                    //             })
                    //                 .then(response => {
                    //                     this.ctdtu_id = response.data.data || 0
                    //                     this.$toast.success("Cập nhật thành công!")
                    //                 }
                    //                 )
                    //                 .catch(error => this.$toast.error(error)).finally(function () { })
                    //         }
                    //     })
                    //     .catch(err => {
                    //         // An error occurred
                    //     })
                }
                else {
                    await this.TaoDuLieu(true);
                    // btd.Insert(dsChiTiet, DatabaseConstants.DB2);
	                let chuky = 0;
                    if (this.checkThangChot) {
                        chuky = parseInt(DateTimeLib.toDateDisplayWithFormat(this.thangChot.value, "YYYYMM"));
                    }
                    await ChiTietHDDTUAPI.CapNhatChiTietHDDTU(this.axios, {
                        p_kieu: 1,
                        p_data: JSON.stringify(this.dsChiTiet),
                        chuky: chuky
                    })
                        .then(response => {
                            this.ctdtu_id = response.data.data || 0
                            this.$toast.success("Thêm mới thành công!")
                        }
                        )
                        .catch(error => this.$toast.error(error)).finally(function () { })
                }

            }
            else {
                if (! await this.Kiemtra_DL(false)) { return }
                await this.TaoDuLieu(false);
                // btd.Update(dsChiTiet, DatabaseConstants.DB2);
                let chuky = 0;
                    if (this.checkThangChot) {
                        chuky = parseInt(DateTimeLib.toDateDisplayWithFormat(this.thangChot.value, "YYYYMM"));
                    }
                await ChiTietHDDTUAPI.CapNhatChiTietHDDTU(this.axios, {
                    p_kieu: 2,
                    p_data: JSON.stringify(this.dsChiTiet),
                    chuky: chuky
                })
                    .then(response => {
                        this.ctdtu_id = response.data.data || 0
                        this.$toast.success("Cập nhật thành công!")
                    }
                    )
                    .catch(error => this.$toast.error(error)).finally(function () { })

            }

            var _id = parseInt(this.ctdtu_id);
            await this.ShowData();
            // this.$refs.grvDanhSach.selectRow(this.dgvDanhSach.findIndex(a=>a.ctdtu_id == _id),true)
            // grvDanhSach.ClearColumnsFilter();
            // grvDanhSach.FocusedRowHandle = GridControl.AutoFilterRowHandle;
            // ChucNang.FocusGridViewRow(grvDanhSach, "CTDTU_ID", _id);
        },
        async Kiemtra_DL(Them_Moi) {
            if (this.params.p_DT == null || this.params.p_DT == "") {
                this.$toast.error("Hãy chọn Chủ đầu tư/Đối tác!");
                this.$refs.cboDT.focusIn();
                return false;
            }
            if (this.vloai_chiphi == 1 || this.vloai_chiphi == 5) {

                if (this.params.p_DichVuVT == null || this.params.p_DichVuVT === "") {
                    this.$toast.error("Hãy chọn Dịch vụ viễn thông!");
                    this.$refs.cboDichVuVT.focusIn();
                    return false;
                }
                if (this.vloai_chiphi == 1) {
                    if (this.params.p_LoaiTB == null || this.params.p_LoaiTB === "") {
                        this.$toast.error("Hãy chọn Loại hình thuê bao!");
                        this.$refs.cboLoaiTB.focusIn();
                        return false;
                    }
                    if (this.params.p_txtTyLeHH === "") {
                        this.$toast.error("Hãy nhập Tỷ lệ hoa hồng");
                        this.$refs.txtTyLeHH.focus();
                        return false;
                    }
                    else {
                        try {
                            if (parseInt(this.params.p_txtTyLeHH) < 0 || parseInt(this.params.p_txtTyLeHH) > 100) {
                                this.$toast.error("Tỷ lệ hoa hồng từ 0.00% -> 100.00%");
                                this.params.p_txtTyLeHH = 0;
                                return false;
                            }
                        }
                        catch (error) {
                            this.$toast.error("Tỷ lệ hoa hồng từ 0.00% -> 100.00%");
                            this.params.p_txtTyLeHH = 0;
                            return false;
                        }
                    }
                }
            }
            else {
                if (this.params.p_txtTongTien === "") {
                    this.$toast.error("Hãy nhập tổng tiền!");
                    this.$refs.txtTongTien.focus();
                    return false;
                }
            }
            //Kiểm tra thao tác cập nhật có thay đổi loại chi phí
            if (this.vloai_chiphi != this.vloai_chiphi_luoi && !Them_Moi) {
                await this.$bvModal.msgBoxConfirm("Bạn đang chọn loại chi phí khác với loại chi phí ban đầu. Tiếp tục cập nhật?", {
                    "okVariant": 'success',
                    "okTitle": 'Đồng ý',
                    "cancelTitle": 'Hủy',
                    "centered": true,
                })
                    .then(async (value) => {
                        if (value) {
                            return true
                        }
                        else {
                            return false
                        }
                    })
                    .catch(err => {
                        // An error occurred
                    })
            }
            return true;
        },
        async TaoDuLieu(Them_Moi) {
            this.dsChiTiet = [];
            let row = {}
            // Thêm mới
            if (this.Enabled.tsbtnNhapMoi == false) {
                // ctdtu_id = parseInt(kt.GetKey("CTDTU_ID"));
            }
            else {
                row.CTDTU_ID = this.ctdtu_id;
            }

            row.HDDTU_ID = this.vhddtu_id;
            row.LOAI = parseInt(this.params.p_Loai);
            row.DT_ID = parseInt(this.params.p_DT);
            row.LOAI_CHIPHI = this.vloai_chiphi;
            if (this.vloai_chiphi == 1) {
                row.DICHVUVT_ID = parseInt(this.params.p_DichVuVT);
                row.LOAITB_ID = parseInt(this.params.p_LoaiTB);
                row.TYLE_HH = parseFloat(this.params.p_txtTyLeHH);
            }
            else if (this.vloai_chiphi == 2 || this.vloai_chiphi == 4) {
                row.TONGTIEN = this.vtongtien;
            }
            else if (this.vloai_chiphi == 3) {
                row.THANG = this.vthang;
                row.DONGIA = this.vdongia;
                row.GIATRI_CU = this.vgiatri_cu;
                row.GIATRI_MOI = this.vgiatri_moi;
                row.TONGTIEN = this.vtongtien;
            }
            else if (this.vloai_chiphi == 5) {
                row.DICHVUVT_ID = parseInt(this.params.p_DichVuVT);
                row.TONGTIEN = this.vtongtien;
            }
            else if (this.vloai_chiphi == 6) {
                row.THANG = this.vthang;
                row.TONGTIEN = this.vtongtien;
            }


            row.GHICHU = this.params.p_txtGhiChu || '';
            row.NGUOI_CN = this.$auth.getUserName()//tt_nd.ma_nd;
            // row.NGAY_CN = tt_nd.ngay_cn;
            // row.MAY_CN = tt_nd.may_cn;
            // row.IP_CN = tt_nd.ip;

            this.dsChiTiet.push(row)
            if (this.Enabled.tsbtnNhapMoi || Them_Moi == false) {
                // dsChiTiet.CHITIET_HDDTU.Rows[0].SetModified();
            }
        },
        async grvDanhSach_FocusedRowChanged(row) {
            if (Object.keys(row).length) {
                this.grvDanhSach_FocusedRow = row
                this.params.p_Loai = this.grvDanhSach_FocusedRow["loai"];
                // quy thêm, trigger event editchanged before done event selectRow
                await this.cboLoai_EditValueChanged()
                this.params.p_Loai_ChiPhi = this.vloai_chiphi_luoi;
                await this.cboLoai_ChiPhi_EditValueChanged()
                this.ctdtu_id = parseInt(this.grvDanhSach_FocusedRow["ctdtu_id"]);

                // if (this.params.p_DT != null && this.params.p_DT != ""){
                this.params.p_DT = parseInt(this.grvDanhSach_FocusedRow["dt_id"]);
                // }


                if (this.vloai_chiphi == 1) {
                    // if (this.params.p_DichVuVT != null && this.params.p_DichVuVT != ""){
                    this.params.p_DichVuVT = this.grvDanhSach_FocusedRow["dichvuvt_id"];
                    await this.cboDichVuVT_EditValueChanged()
                    // }

                    // if (this.params.p_LoaiTB != null && this.params.p_LoaiTB != ""){
                    this.params.p_LoaiTB = this.grvDanhSach_FocusedRow["loaitb_id"];
                    // }

                    this.params.p_txtTyLeHH = parseFloat(this.grvDanhSach_FocusedRow["tyle_hh"]);
                }
                else if (this.vloai_chiphi == 2 || this.vloai_chiphi == 4) {
                    this.params.p_txtTongTien = this.vtongtien_luoi;
                }
                else if (this.vloai_chiphi == 3) {
                    this.params.p_dtpThang = this.vthang_luoi;
                    this.params.p_txtDonGia = this.vdongia_luoi;
                    this.params.p_txtGiaTri_Cu = this.vgiatri_cu_luoi;
                    this.params.p_txtGiaTri_Moi = this.vgiatri_moi_luoi;
                    this.params.p_txtTongTien = this.vtongtien_luoi;
                }
                else if (this.vloai_chiphi == 5) {
                    // if (this.params.p_DichVuVT != null && this.params.p_DichVuVT != ""){
                    this.params.p_DichVuVT = this.grvDanhSach_FocusedRow["dichvuvt_id"];
                    // }

                    this.params.p_txtTongTien = this.vtongtien_luoi;
                }
                else if (this.vloai_chiphi == 6) {
                    this.params.p_dtpThang = this.vthang_luoi;
                    this.params.p_txtTongTien = this.vtongtien_luoi;
                }

                this.params.p_txtGhiChu = this.grvDanhSach_FocusedRow["ghichu"];
                await this.SetButton(3);
            }
        },
        async KiemTra_DaTonTai(dt_id, vhddtu_id, dichvuvt_id, loaitb_id) {
            var d = []
            let chuky = 0;
            if (this.checkThangChot) {
                chuky = parseInt(DateTimeLib.toDateDisplayWithFormat(this.thangChot.value, "YYYYMM"));
            }
            await ChiTietHDDTUAPI.KiemTraDaTonTai(this.axios, {
                dt_id: dt_id,
                hddtu_id: vhddtu_id,
                dichvuvt_id: dichvuvt_id,
                loaitb_id: loaitb_id,
                chuky: chuky
            })
                .then(
                    (response) => {
                        d = response.data.data || []
                    }
                )
                .catch(error => this.$toast.error(error)).finally(function () { })
            if (d.length) {
                return d[0]["ctdtu_id"]
            }
            return -1
        },
        async cboLoai_ChiPhi_EditValueChanged() {
            this.tinhTongChiPhiDien();
            this.Text.layoutControlGroup4 = this.cboLoai_ChiPhi.find(item => item.VID == this.params.p_Loai_ChiPhi).VNAME
            switch (this.vloai_chiphi) {
                case 1:
                    this.Visible.layoutDichVuVT = true;
                    this.Visible.layoutLoaiHinhTB = true;
                    this.Visible.layoutTyLeHH = true;

                    this.Visible.layoutThang = false;
                    this.Visible.layoutDonGia = false;
                    this.Visible.layoutGiaTri_Cu = false;
                    this.Visible.layoutGiaTri_Moi = false;
                    this.Visible.layoutTongTien = false;
                    break;
                case 2:
                case 4:
                    this.Visible.layoutDichVuVT = false;
                    this.Visible.layoutLoaiHinhTB = false;
                    this.Visible.layoutTyLeHH = false;

                    this.Visible.layoutThang = false;
                    this.Visible.layoutDonGia = false;
                    this.Visible.layoutGiaTri_Cu = false;
                    this.Visible.layoutGiaTri_Moi = false;
                    this.Visible.layoutTongTien = true;
                    break;

                case 3:
                    this.Visible.layoutDichVuVT = false;
                    this.Visible.layoutLoaiHinhTB = false;
                    this.Visible.layoutTyLeHH = false;

                    this.Visible.layoutThang = true;
                    this.Visible.layoutDonGia = true;
                    this.Visible.layoutGiaTri_Cu = true;
                    this.Visible.layoutGiaTri_Moi = true;
                    this.Visible.layoutTongTien = true;
                    break;

                case 5:
                    this.Visible.layoutDichVuVT = false;
                    this.Visible.layoutLoaiHinhTB = false;
                    this.Visible.layoutTyLeHH = false;

                    this.Visible.layoutThang = false;
                    this.Visible.layoutDonGia = false;
                    this.Visible.layoutGiaTri_Cu = false;
                    this.Visible.layoutGiaTri_Moi = false;
                    this.Visible.layoutTongTien = true;
                    break;
                case 6:
                    this.Visible.layoutDichVuVT = false;
                    this.Visible.layoutLoaiHinhTB = false;
                    this.Visible.layoutTyLeHH = false;

                    this.Visible.layoutThang = true;
                    this.Visible.layoutDonGia = false;
                    this.Visible.layoutGiaTri_Cu = false;
                    this.Visible.layoutGiaTri_Moi = false;
                    this.Visible.layoutTongTien = true;
                    break;
            }
        },
        tinhTongChiPhiDien: function () {
            this.params.p_txtTongTien = 0;
            if (this.params.p_Loai_ChiPhi == 3) {
                if (!(!this.checkNumber(this.params.p_txtDonGia) || !this.checkNumber(this.params.p_txtGiaTri_Cu) || !this.checkNumber(this.params.p_txtGiaTri_Moi))) {
                    if (this.params.p_txtGiaTri_Moi > this.params.p_txtGiaTri_Cu) {
                        this.params.p_txtTongTien = this.params.p_txtDonGia * (this.params.p_txtGiaTri_Moi - this.params.p_txtGiaTri_Cu);
                        if(this.params.p_txtTongTien < 0)
                            this.params.p_txtTongTien = 0;
                    }
                }
            }
        },
        checkNumber: function (value) {
            if (!(value == undefined || value == null)) {
                let regEx = /^(?!0\d)\d*(\.\d+)?$/;
                return value.toString().match(regEx);
            }
            return false;
        },
        onChayLaiDTPC(){

        }
    },

    created: async function () {
        await this.frmChiTiet_HDDTU_Load()
    },
    watch: {
        'cboLoai.selected': async function (newVal, oldVal) {
            if (newVal)
                await this.cboLoai_EditValueChanged();
        },
    },
    destroyed() {
    }
};
</script>
<style scoped>

a.disabled {
    color: lightgrey !important;
    pointer-events: none;
    cursor: default;
}
</style>
