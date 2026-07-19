<template src="./index.template.html"></template>
<style scoped src="./style.scss"></style>
<script>
import ComboboxGrid from "../../../../components/Controls/ComboboxGrid"
import ModalChonDiaChi from '../../HOPDONG/ChuanHoaDLKhachHang/popupChonDiaChi/ModalChonDiaChi'
import { 
    required, 
    maxLength, 
    minLength, 
    numeric,
    email
} from "vuelidate/lib/validators"

export default {
    components: {
        appCombobox: ComboboxGrid,
        ModalChonDiaChi
    },
    name: 'DanhMucToChucQLDA',

    async created() {
        this.setButton(-1);
        await this.getDsNganHang();
        await this.getDsLoaiChuDauTu();
        await this.getDsTinhTrang()
        await this.onLayTT(false);
        this.txt_DiaChi_ReadOnly = !(await this.getKiemTraThamSoMD('DIACHI_CHU_DAUTU'));
        if (this.cbo_NganHang.items.length > 0)
            this.cbo_NganHang.selected = this.cbo_NganHang.items[0].nganhang_id;
        if (this.cbo_PhanLoai.items.length > 0)
            this.cbo_PhanLoai.selected = this.cbo_PhanLoai.items[0].loai_cdt_id;
        if (this.cbo_TinhTrang.items.length > 0)
            this.cbo_TinhTrang.selected = this.cbo_TinhTrang.items[0].tinhtrang_id;
    },

    beforeUpdate() {
        this.hideComboboxTableHeader();
    },

    data() {
        return {
            cbo_PhanLoai: {
                items: [],
                selected: null 
            },
            cbo_NganHang: {
                items: [],
                selected: null
            },
            cbo_TinhTrang: {
                items: [],
                selected: null
            },
            txt_MaTCQLDA: null,
            txt_TenTCQLDA: null,
            txt_SoDT: null,
            txt_NguoiLH: null,
            txt_ChucVu: null,
            txt_Email: null,
            txt_MST: null,
            txt_SoTaiKhoan: null,
            txt_DiaChi: null,
            txt_DiaChi_ReadOnly: false,
            txt_GhiChu: null,
            btn_Enabled: {
                nhapMoi: false,
                ghiLai: false,
                xoa: false,
                huy: false
            },
            grid_TCQLDA: {
                items: [],
                selected: {}
            },
            dataSend: null,
            diaChi_Obj: {
                diaChi_Id: 0,
                tinh_Id: 0,
                quan_Id: 0,
                phuong_Id: 0,
                pho_Id: 0,
                ap_Id: 0,
                khu_Id: 0,
                dacDiem_Id: 0,
                soNha: null,
                diaChi: null,
                viDo: null,
                kinhDo: null
            },
            phanVungID: this.$root.token.getPhanVungID(),
        }
    },

    validations: {
        txt_TenTCQLDA: {
            required,
            maxLength: maxLength(200)
        },
        txt_NguoiLH: {
            maxLength: maxLength(200)
        },
        txt_DiaChi: {
            required,
            maxLength: maxLength(500)
        },
        txt_GhiChu: {
            maxLength: maxLength(1000)
        },
        txt_SoDT: {
            numeric,
            maxLength: maxLength(11),
            minLength: minLength(7),
        },
        txt_SoTaiKhoan: {
            maxLength: maxLength(20)
        },
        txt_Email: {
            email
        },
        txt_MST: {
            required
        }
    },

    methods: {
        hideComboboxTableHeader() {
            let cbbRef = ['phanLoaiCbo', 'nganHangCbo','tinhTrangCbo'];
            cbbRef.forEach(item => {
                let elemts = this.$refs[item].$refs.grid.$el.querySelectorAll('.e-headercell');
                elemts.forEach(elment => {
                    elment.style.display = 'none';
                });
            });
        },

        phanLoaiSelected(args) {
            if (args != null)
                this.cbo_PhanLoai.selected = args.loai_cdt_id;
        },

        nganHangSelected(args) {
            if (args != null)
                this.cbo_NganHang.selected = args.nganhang_id;
        },
        tinhTrangSelected(args) {
            if (args != null)
                this.cbo_TinhTrang.selected = args.tinhtrang_id;
        },

        async validateData() {
            this.$v.$touch();
            let errorMessage = [];
            let focusInput = [];

            let kq = await this.kt_quyen_toanha_2({
                    chucnang: "CHU_DTU",
                    kieu: this.btn_Enabled.nhapMoi ? 2 : 1,
                    id: this.grid_TCQLDA.selected.chudtu_id,
                    ttvt_id: 0
                })
            if (kq.toUpperCase() != 'OK'){
                errorMessage.push(kq.toString())
            }

            if (!this.$v.txt_TenTCQLDA.required) {
                errorMessage.push("Tên chủ đầu tư không được để trống!");
                focusInput.push('tenChuDtu');
            } else if (!this.$v.txt_TenTCQLDA.maxLength) {
                errorMessage.push("Tên chủ đầu tư không được lớn hơn 200 ký tự!");
                focusInput.push('tenChuDtu');
            }

            if (!this.$v.txt_SoDT.numeric) {
                errorMessage.push("Số điện thoại phải là dạng số!");
                focusInput.push('soDT');
            } else if (!this.$v.txt_SoDT.maxLength) {
                errorMessage.push("Số điện thoại không được lớn hơn 11 ký tự!");
                focusInput.push('soDT');
            } else if (!this.$v.txt_SoDT.minLength) {
                errorMessage.push("Số điện thoại không được nhỏ hơn 7 ký tự!");
                focusInput.push('soDT');
            }

            // if (this.phanVungID == 28 && !this.$v.txt_MST.required) {
            //     errorMessage.push("Mã số thuế không được để trống!");
            //     focusInput.push('mst');
            // }

            let question = true
            if (this.txt_MST){
                let kq_kt  = await this.KiemTra_MST()
                if (kq_kt.toUpperCase() != 'OK'){
                    errorMessage.push(kq_kt);
                }
                if (this.btn_Enabled.nhapMoi == false){
                    // tlstpTop.Focus();
                    let kq  = await this.fn_kt_mst_tcqlda({
                        kieu: 1,
                        taikhoan: this.txt_MST
                    })
                    console.log(kq)
                    if (kq != "1"){
                        // await this.$bvModal.msgBoxConfirm("TCQLDA có cùng Mã số thuế: \n" + kq.toString() + "\n\nTiếp tục Thêm dữ liệu?", {
                        //     "okVariant": 'success',
                        //     "okTitle": 'Đồng ý',
                        //     "cancelTitle": 'Hủy',
                        //     "centered": true,
                        //     })
                        //     .then(async(value) => {
                        //         // == DialogResult.No
                        //         if (!value){
                        //             question = false
                        //         }
                                
                        //     })
                        //     .catch(err => {
                        //         // An error occurred
                        //     })
                        this.$toast.error("TCQLDA có cùng Mã số thuế: \n" + kq.toString())
                        return false
                    }
                } else {
                    let kq  = await this.fn_kt_mst_tcqlda_themmoi({
                        kieu: 1,
                        taikhoan: this.txt_MST,
                        matcqlda: this.txt_MaTCQLDA
                    })
                    console.log(kq)
                    if (kq != "1"){
                        this.$toast.error("TCQLDA có cùng Mã số thuế: \n" + kq.toString())
                        return false
                    }
                }
            }
            if (question == false){
                return false
            }

            if (!this.$v.txt_NguoiLH.maxLength) {
                errorMessage.push("Tên người liên hệ không được lớn hơn 200 ký tự!");
                focusInput.push('nguoiLH');
            }

            if (!this.$v.txt_Email.email) {
                errorMessage.push("Email không đúng định dạng!");
                focusInput.push('email');
            }

            if (!this.$v.txt_SoTaiKhoan.maxLength) {
                errorMessage.push("Số tài khoản không được lớn hơn 20 ký tự!");
                focusInput.push('soTK');
            }

            if (this.btn_Enabled.nhapMoi == false){
                let kq  = await this.fn_kt_mst_tcqlda({
                    kieu: 2,
                    taikhoan: this.txt_SoTaiKhoan
                })
                if (kq != "1"){
                    await this.$bvModal.msgBoxConfirm("TCQLDA có cùng Số tài khoản: \n" + kq.ToString() + "\n\nTiếp tục Thêm dữ liệu?", {
                        "okVariant": 'success',
                        "okTitle": 'Đồng ý',
                        "cancelTitle": 'Hủy',
                        "centered": true,
                        })
                        .then(async(value) => {
                            // == DialogResult.No
                            if (!value){
                                question = false
                            }
                            
                        })
                        .catch(err => {
                            // An error occurred
                        })
                }
            }
            if (question == false){
                return false
            }

            if (!this.$v.txt_DiaChi.required) {
                errorMessage.push("Địa chỉ không được để trống!");
                focusInput.push('diaChi');
            } else if (!this.$v.txt_DiaChi.maxLength) {
                errorMessage.push("Địa chỉ không được lớn hơn 500 ký tự!");
                focusInput.push('diaChi');
            }

            if (!this.$v.txt_GhiChu.maxLength) {
                errorMessage.push("Ghi chú không được lớn hơn 1000 ký tự!");
                focusInput.push('ghiChu');
            }

            if (errorMessage.length > 0) {
                this.$toast.error(errorMessage.join("\r\n"));
                this.$refs[focusInput[0]].focus();
                return false;
            } else return true;
        },

        async onLayTT(isMessage = true) {
            await this.getDsChuDauTu();
            if (this.grid_TCQLDA.items.length <= 0) {
                this.setButton(1);
                if (isMessage) this.$toast.error('Không có dữ liệu, vui lòng thử lại');
            }
        },

        onNhapMoi() {
            this.setButton(1);
        },

        async onGhiLai() {
            if (await this.validateData()) {
                if (!this.btn_Enabled.nhapMoi) {
                    if (this.diaChi_Obj.quan_Id !== 0) {
                        let res = await this.updateDiaChi(1);
                        if (!res) return this.$toast.error('Có lỗi khi cập nhật địa chỉ!');
                    }
                    let rs = await this.addChuDauTu();
                    if (rs) this.$toast.success('Thêm mới thành công!');
                } else {
                    let res = await this.updateDiaChi(2);
                    if (!res) return this.$toast.error('Có lỗi khi cập nhật địa chỉ!');
                    let rs = await this.updateChuDauTu();
                    if (rs) this.$toast.success('Cập nhật thành công!');
                }
            
                await this.onLayTT(false);
                // focus to lastest updated record
            }
        },

        onHuy() {
            if (!this.btn_Enabled.nhapMoi)   // Hủy nhập mới
                this.onNhapMoi();
            else {                           //Hủy cập nhật
                this.onToChucQLDASelected({ data: this.grid_TCQLDA.selected });
                this.setButton(2);
            }
        },

        async onXoa() {
            // Kiểm tra khóa ngoại
            let kq = await this.kt_quyen_toanha_2({
                chucnang: "CHU_DTU",
                kieu: 3,
                id: this.grid_TCQLDA.selected.chudtu_id,
                ttvt_id: 0
            })

            // let rs = await this.getSoDuAnChuDauTu();
            // if (rs > 0)
                // return this.$toast.error(`Bạn không thể xóa bản ghi này! Chủ đầu tư này đang tồn tại Dự án!`);
            if (kq != null && kq.toString().toUpperCase() != "OK")
                return this.$toast.error(kq.toString());
            else {
                try {                
                    await this.$bvModal.msgBoxConfirm('Bạn có chắc chắn muốn xóa?', {
                    "title": 'Thông báo',
                    "size": 'lg',
                    "buttonSize": 'lg',
                    "okVariant": 'primary',
                    "okTitle": 'Đồng ý',
                    "cancelTitle": 'Không đồng ý',
                    "hideHeaderClose": false,
                    "centered": true,
                    "modal-class": ["f18","text-center"],
                    })
                    .then(async(value) => {
                        if (value){
                            let res = await this.updateDiaChi(3);
                            if (!res) return this.$toast.error('Có lỗi khi xóa địa chỉ!');
                            let rs = await this.deleteChuDauTu();
                            if (rs)
                                this.$toast.success('Xóa dữ liệu thành công!');

                            await this.onLayTT(false);
                        }
                    })
                    .catch(err => {
                        console.log(err);
                    })
                } catch (error) {
                    console.log(error);
                    this.$toast.error("Có lỗi xảy ra, vui lòng thử lại!")
                }
            }
        },

        onXuatExcel() {
            if (this.grid_TCQLDA.items.length <= 0)
                this.$toast.error("Chưa có danh sách để xuất excel!");
            else {
                let excelExportProperties = {
                    fileName:"Danh-muc-to-chuc-qlda.xlsx",
                    dataSource: this.grid_TCQLDA.items
                };
                this.$refs.toChucQLDAGrid.excelExport(excelExportProperties);
            }
        },

        onDiaChi() {
            this.dataSend = {};
            this.dataSend.tinh_id = this.$auth.getPhanVungID();
            this.dataSend.quan_id = this.btn_Enabled.nhapMoi ? this.diaChi_Obj.quan_Id : 0;
            this.dataSend.phuong_id = this.btn_Enabled.nhapMoi ? this.diaChi_Obj.phuong_Id : 0;
            this.dataSend.pho_id = this.btn_Enabled.nhapMoi ? this.diaChi_Obj.pho_Id : 0;
            this.dataSend.ap_id = this.btn_Enabled.nhapMoi ? this.diaChi_Obj.ap_Id : 0;
            this.dataSend.khu_id = this.btn_Enabled.nhapMoi ? this.diaChi_Obj.khu_Id : 0;
            this.dataSend.dacdiem_id = this.btn_Enabled.nhapMoi ? this.diaChi_Obj.dacDiem_Id : 0;
            this.dataSend.so_nha = this.btn_Enabled.nhapMoi ? this.diaChi_Obj.soNha : '';
            this.dataSend.diachi = this.btn_Enabled.nhapMoi ? this.diaChi_Obj.diaChi : '';
            this.dataSend.lng = this.btn_Enabled.nhapMoi ? this.diaChi_Obj.kinhDo : 0;
            this.dataSend.lat = this.btn_Enabled.nhapMoi ? this.diaChi_Obj.viDo : 0;
            this.$bvModal.show('popupDiaChiCDT');
        },

        onDiaChiCDT(data) {
            this.diaChi_Obj.diaChi = data.diachimoi;
            this.diaChi_Obj.tinh_Id = data.tinh_id;
            this.diaChi_Obj.quan_Id = data.quan_id;
            this.diaChi_Obj.phuong_Id = data.phuong_id;
            this.diaChi_Obj.pho_Id = data.pho_id;
            this.diaChi_Obj.ap_Id = data.ap_id;
            this.diaChi_Obj.khu_Id = data.khu_id;
            this.diaChi_Obj.dacDiem_Id = data.dacdiem_id;
            this.diaChi_Obj.soNha = data.so_nha;
            this.diaChi_Obj.kinhDo = data.lng;
            this.diaChi_Obj.viDo = data.lat;
            this.txt_DiaChi = data.diachimoi;
        },

        onToChucQLDASelected(args) {
            if (args != null) {
                this.grid_TCQLDA.selected = args.data;
                this.txt_MaTCQLDA = args.data.ma_chudtu;
                this.txt_TenTCQLDA = args.data.ten_chudtu;
                this.txt_DiaChi = args.data.diachi;
                this.txt_SoDT = args.data.so_dt;
                this.txt_NguoiLH = args.data.nguoi_lh;
                this.txt_SoTaiKhoan = args.data.taikhoan;
                this.txt_ChucVu = args.data.chucvu;
                this.txt_Email = args.data.email;
                this.txt_MST = args.data.mst;
                this.txt_GhiChu = args.data.ghichu;
                this.cbo_PhanLoai.selected = args.data.loai_cdt_id;
                this.cbo_NganHang.selected = args.data.nganhang_id;
                this.cbo_TinhTrang.selected = args.data.tinhtrang != null ? args.data.tinhtrang : 0;
                this.selectCboRow();

                this.diaChi_Obj.diaChi_Id = args.data.diachi_id != null ? args.data.diachi_id : 0;
                this.diaChi_Obj.tinh_Id = args.data.tinh_id != null ? args.data.tinh_id : this.$auth.getPhanVungID();
                this.diaChi_Obj.quan_Id = args.data.quan_id != null ? args.data.quan_id : 0;
                this.diaChi_Obj.phuong_Id = args.data.phuong_id != null ? args.data.phuong_id : 0;
                this.diaChi_Obj.pho_Id = args.data.pho_id != null ? args.data.pho_id : 0;
                this.diaChi_Obj.ap_Id = args.data.ap_id != null ? args.data.ap_id : 0;
                this.diaChi_Obj.khu_Id = args.data.khu_id != null ? args.data.khu_id : 0;
                this.diaChi_Obj.dacDiem_Id = args.data.dacdiem_id != null ? args.data.dacdiem_id : 0;
                this.diaChi_Obj.soNha = args.data.sonha;
                this.diaChi_Obj.diaChi = args.data.diachi;
                this.diaChi_Obj.kinhDo = args.data.kinhdo;
                this.diaChi_Obj.viDo = args.data.vido;
                this.setButton(2);
            }
        },

        selectCboRow() {
            let idx1 = this.cbo_PhanLoai.items.findIndex(e => e.loai_cdt_id === this.cbo_PhanLoai.selected);
            let idx2 = this.cbo_NganHang.items.findIndex(e => e.nganhang_id === this.cbo_NganHang.selected);
            let idx3 = this.cbo_TinhTrang.items.findIndex(e => e.tinhtrang_id === this.cbo_TinhTrang.selected);
            if (idx1 !== -1)
                this.$refs.phanLoaiCbo.$refs.grid.setCurrentSelectedRow(idx1);
            if (idx2 !== -1) 
                this.$refs.nganHangCbo.$refs.grid.setCurrentSelectedRow(idx2);
            idx3 !== -1 ? this.$refs.tinhTrangCbo.$refs.grid.setCurrentSelectedRow(idx3) : ''
        },

        setButton(kieu) {
            this.btn_Enabled = {
                nhapMoi: false,
                ghiLai: false,
                xoa: false,
                huy: false
            };
            if (kieu === -1) {          //Bat dau  
                this.btn_Enabled.ghiLai = true;
                this.btn_Enabled.huy = true;
            } else if (kieu === 1) {    //Them moi
                this.clear();
                this.btn_Enabled.ghiLai = true;
                this.btn_Enabled.huy = true;
            } else if (kieu === 2) {    //Cap nhat
                this.btn_Enabled.nhapMoi = true;
                this.btn_Enabled.ghiLai = true;
                this.btn_Enabled.huy = true;
                this.btn_Enabled.xoa = true;
            }
        },

        clear() {
            this.txt_MaTCQLDA = '';
            this.txt_TenTCQLDA = '';
            this.txt_DiaChi = '';
            this.txt_SoDT = '';
            this.txt_NguoiLH = '';
            this.txt_SoTaiKhoan = '';
            this.txt_ChucVu = '';
            this.txt_Email = '';
            this.txt_MST = '';
            this.txt_GhiChu = '';
            this.cbo_PhanLoai.items = [...this.cbo_PhanLoai.items];
            this.cbo_NganHang.items = [...this.cbo_NganHang.items];
            this.cbo_TinhTrang.items = [...this.cbo_TinhTrang.items];
            this.diaChi_Obj.diaChi_Id = 0;
            this.$v.$reset();
            this.$refs.tenChuDtu.focus();
        },

        async getKiemTraThamSoMD(maTS) {
            try {
                this.loading(true);
                let url = `/web-toanha/danhmuc-tochuc-qlda/kiemtra-thamso-md?maTS=${maTS}`;
                let response = await this.$root.context.get(url);
                if (response.error_code === 'BSS-00000000' && response.data !== undefined) {
                    return response.data > 0;
                } else return false;
            }
            catch (error) {
                console.log(error);
                return false;
            }
            finally {
                this.loading(false);
            }
        },

        async getDsLoaiChuDauTu() {
            try {
                this.loading(true);
                let url = `/web-toanha/danhmuc-tochuc-qlda/ds-loai-chu-dautu`;
                let response = await this.$root.context.get(url);
                if (response.error_code === 'BSS-00000000' && response.data !== undefined) {
                    this.cbo_PhanLoai.items = response.data;
                }
            }
            catch (error) {
                console.log(error);
            }
            finally {
                this.loading(false);
            }
        },

        async getDsNganHang() {
            try {
                this.loading(true);
                let url = `/web-toanha/danhmuc-tochuc-qlda/ds-nganhang`;
                let response = await this.$root.context.get(url);
                if (response.error_code === 'BSS-00000000' && response.data !== undefined) {
                    this.cbo_NganHang.items = response.data;
                }
            }
            catch (error) {
                console.log(error);
            }
            finally {
                this.loading(false);
            }
        },
        async getDsTinhTrang() {
            this.cbo_TinhTrang.items = [
                { tinhtrang_id: 0, tinhtrang: "Không hoạt động"},
                { tinhtrang_id: 1, tinhtrang: "Hoạt động"},
            ]
        },
        async kt_quyen_toanha_2(body){
            try {
                this.loading(true);
                let url = `/web-toanha/danhmuc-tochuc-qlda/kt-quyen-toanha-2`;
                let response = await this.$root.context.post(url,body);
                console.log(response.data)
                return response.data || []
            }
            catch (error) {
                console.log(error);
                return []
            }
            finally {
                this.loading(false);
            }
        },
        async KiemTra_MST(){
            try {
                this.loading(true);
                let url = `/web-toanha/danhmuc-tochuc-qlda/kiemtra-mst?mst=${(this.txt_MST != null ? this.txt_MST : '').trim()}`;
                let response = await this.$root.context.get(url);
                console.log(response.data)
                return response.data || []
            }
            catch (error) {
                console.log(error);
                return []
            }
            finally {
                this.loading(false);
            }
        },
        async fn_kt_mst_tcqlda(body){
            try {
                this.loading(true);
                let url = `/web-toanha/danhmuc-tochuc-qlda/kt-mst-tcqlda`;
                let response = await this.$root.context.post(url,body);
                console.log(response.data)
                return response.data || []
            }
            catch (error) {
                console.log(error);
                return []
            }
            finally {
                this.loading(false);
            }
        },

        async fn_kt_mst_tcqlda_themmoi(body){
            try {
                this.loading(true);
                let url = `/web-toanha/danhmuc-tochuc-qlda/kt-mst-tcqlda-themmoi`;
                let response = await this.$root.context.post(url,body);
                console.log(response.data)
                return response.data || []
            }
            catch (error) {
                console.log(error);
                return []
            }
            finally {
                this.loading(false);
            }
        },

        async getDsChuDauTu() {
            try {
                this.loading(true);
                this.grid_TCQLDA.items = [];
                let url = `/web-toanha/danhmuc-tochuc-qlda/ds-chu-dautu`;
                let response = await this.$root.context.get(url);
                if (response.error_code === 'BSS-00000000' && response.data !== undefined) {
                    response.data.forEach(element => {
                        if (element.tinhtrang == 1) {
                            element.tinhtrangda = "Hoạt động"
                        } else {
                            element.tinhtrangda = "Không hoạt động"
                        }
                    });
                    this.grid_TCQLDA.items = response.data;
                }
            }
            catch (error) {
                console.log(error);
            }
            finally {
                this.loading(false);
            }
        },

        async addChuDauTu() {
            try {
                this.loading(true);
                let apiBody = {
                    chucVu: this.txt_ChucVu,
                    diaChi: this.txt_DiaChi,
                    email: this.txt_Email,
                    ghiChu: this.txt_GhiChu,
                    loaiCdtId: this.cbo_PhanLoai.selected,
                    mst: this.txt_MST,
                    nganHangId: this.cbo_NganHang.selected,
                    nguoiLH: this.txt_NguoiLH,
                    soDT: this.txt_SoDT,
                    taiKhoan: this.txt_SoTaiKhoan,
                    tenChuDtu: this.txt_TenTCQLDA,
                    diaChiId: this.diaChi_Obj.diaChi_Id !== 0 ? this.diaChi_Obj.diaChi_Id : null,
                    tinhTrangId: this.cbo_TinhTrang.selected != null ? this.cbo_TinhTrang.selected : 0
                };

                let url = `/web-toanha/danhmuc-tochuc-qlda/them-chu-dautu`;
                let response = await this.$root.context.post(url, apiBody);
                if (response.error_code === 'BSS-00000000') {
                    return true;
                } else return false;
            }
            catch (error) {
                this.$toast.error('Thêm mới không thành công!');
                return false;
            }
            finally {
                this.loading(false);
            }
        },

        async updateChuDauTu() {
            try {
                this.loading(true);
                let apiBody = {
                    chuDtuId: this.grid_TCQLDA.selected.chudtu_id,
                    chucVu: this.txt_ChucVu,
                    diaChi: this.txt_DiaChi,
                    email: this.txt_Email,
                    ghiChu: this.txt_GhiChu,
                    loaiCdtId: this.cbo_PhanLoai.selected,
                    mst: this.txt_MST,
                    nganHangId: this.cbo_NganHang.selected,
                    nguoiLH: this.txt_NguoiLH,
                    soDT: this.txt_SoDT,
                    taiKhoan: this.txt_SoTaiKhoan,
                    tenChuDtu: this.txt_TenTCQLDA,
                    diaChiId: this.diaChi_Obj.diaChi_Id !== 0 ? this.diaChi_Obj.diaChi_Id : null,
                    tinhTrangId: this.cbo_TinhTrang.selected != null ? this.cbo_TinhTrang.selected : 0
                };
                let url = `/web-toanha/danhmuc-tochuc-qlda/capnhat-chu-dautu`;
                let response = await this.$root.context.post(url, apiBody);
                if (response.error_code === 'BSS-00000000') {
                    return true;
                } else return false;
            }
            catch (error) {
                this.$toast.error('Cập nhật không thành công!');
                return false;
            }
            finally {
                this.loading(false);
            }
        },

        async getSoDuAnChuDauTu() {
            try {
                this.loading(true);
                let url = `/web-toanha/danhmuc-tochuc-qlda/so-duan-chu-dautu?chuDtuId=${this.grid_TCQLDA.selected.chudtu_id}`;
                let response = await this.$root.context.get(url);
                if (response.error_code === 'BSS-00000000' && response.data !== undefined) {
                    return response.data;
                } return 0;
            }
            catch (error) {
                console.log(error);
                return 0;
            }
            finally {
                this.loading(false);
            }
        },

        async deleteChuDauTu() {
            try {
                this.loading(true);
                let url = `/web-toanha/danhmuc-tochuc-qlda/xoa-chu-dautu/${this.grid_TCQLDA.selected.chudtu_id}`;
                let response = await this.$root.context.post(url);
                if (response.error_code === 'BSS-00000000') {
                    return true;
                } else return false;
            }
            catch (error) {
                this.$toast.error('Xóa không thành công!');
                return false;
            }
            finally {
                this.loading(false);
            }
        },

        async updateDiaChi(kieu) {
            try {
                this.loading(true);
                if (kieu === 2 && this.diaChi_Obj.diaChi_Id === 0) {
                    if (this.diaChi_Obj.quan_Id === 0)
                        return true;
                    else kieu = 1;
                }
                let apiBody = {
                    kieu: kieu,
                    apId: this.diaChi_Obj.ap_Id,
                    dacDiemId: this.diaChi_Obj.dacDiem_Id,
                    diaChi: this.diaChi_Obj.diaChi,
                    diaChiId: this.diaChi_Obj.diaChi_Id,
                    khuId: this.diaChi_Obj.khu_Id,
                    kinhDo: this.diaChi_Obj.kinhDo,
                    phoId: this.diaChi_Obj.pho_Id,
                    phuongId: this.diaChi_Obj.phuong_Id,
                    quanId: this.diaChi_Obj.quan_Id,
                    soNha: this.diaChi_Obj.soNha,
                    tinhId: this.diaChi_Obj.tinh_Id,
                    viDo: this.diaChi_Obj.viDo
                };
                let url = `/web-toanha/danhmuc-tochuc-qlda/capnhat-diachi`;
                let response = await this.$root.context.post(url, apiBody);
                if (response.error_code === 'BSS-00000000' && response.data !== undefined
                    && response.data.includes("OK")) {
                    if (kieu === 1)
                        this.diaChi_Obj.diaChi_Id = Number(response.data.split('-')[1]);
                    return true;
                } else return false;
            }
            catch (error) {
                this.$toast.error(error.message);
                return false;
            }
            finally {
                this.loading(false);
            }
        }
    }
}
</script>
