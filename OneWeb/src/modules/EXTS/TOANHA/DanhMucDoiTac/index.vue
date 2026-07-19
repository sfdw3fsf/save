<template src="./index.template.html"></template>
<style scoped src="../DanhMucToChucQLDA/style.scss"></style>
<script>
import ComboboxGrid from "../../../../components/Controls/ComboboxGrid"
import { required, maxLength, minLength, numeric } from "vuelidate/lib/validators"

export default {
    components: { appCombobox: ComboboxGrid },
    name: 'DanhMucDoiTac',

    async created() {
        this.setButton(-1);
        await this.getDsNganHang();
        await this.onLayTT(false);
        if (this.cbo_NganHang.items.length > 0)
            this.cbo_NganHang.selected = this.cbo_NganHang.items[0].nganhang_id;
    },

    beforeUpdate() {
        this.hideComboboxTableHeader();
    },

    data() {
        return {
            cbo_NganHang: {
                items: [],
                selected: null
            },
            txt_MaDoiTac: null,
            txt_MaDoiTac_ReadOnly: true,
            txt_TenDoiTac: null,
            txt_SoDT: null,
            txt_NguoiDD: null,
            txt_SoTaiKhoan: null,
            txt_DiaChi: null,
            txt_MaDTVisa: null,
            txt_UsernameSub: null,
            txt_GhiChu: null,
            btn_Enabled: {
                nhapMoi: false,
                ghiLai: false,
                xoa: false,
                huy: false
            },
            grid_DoiTac: {
                items: [],
                selected: {}
            }
        }
    },

    validations: {
        txt_TenDoiTac: {
            required,
            maxLength: maxLength(300)
        },
        txt_NguoiDD: {
            maxLength: maxLength(500)
        },
        txt_DiaChi: {
            maxLength: maxLength(300)
        },
        txt_GhiChu: {
            maxLength: maxLength(500)
        },
        txt_UsernameSub: {
            maxLength: maxLength(50)
        },
        txt_MaDTVisa: {
            maxLength: maxLength(50)
        },
        txt_SoDT: {
            numeric,
            maxLength: maxLength(11),
            minLength: minLength(7),
        },
        txt_SoTaiKhoan: {
            maxLength: maxLength(20)
        }
    },

    methods: {
        hideComboboxTableHeader() {
            let elemts = this.$refs.nganHangCbo.$refs.grid.$el.querySelectorAll('.e-headercell');
            elemts.forEach(elment => {
                elment.style.display = 'none';
            });
        },

        nganHangSelected(args) {
            if (args != null)
                this.cbo_NganHang.selected = args.nganhang_id;
        },

        validateData() {
            this.$v.$touch();
            let errorMessage = [];
            let focusInput = [];

            if (!this.$v.txt_TenDoiTac.required) {
                errorMessage.push("Tên đối tác không được để trống!");
                focusInput.push('tenDoiTac');
            } else if (!this.$v.txt_TenDoiTac.maxLength) {
                errorMessage.push("Tên đối tác không được lớn hơn 300 ký tự!");
                focusInput.push('tenDoiTac');
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

            if (!this.$v.txt_NguoiDD.maxLength) {
                errorMessage.push("Tên người đại diện không được lớn hơn 500 ký tự!");
                focusInput.push('nguoiDD');
            }

            if (!this.$v.txt_SoTaiKhoan.maxLength) {
                errorMessage.push("Số tài khoản không được lớn hơn 20 ký tự!");
                focusInput.push('soTaiKhoan');
            }

            if (!this.$v.txt_DiaChi.maxLength) {
                errorMessage.push("Địa chỉ không được lớn hơn 300 ký tự!");
                focusInput.push('diaChi');
            }

            if (!this.$v.txt_MaDTVisa.maxLength) {
                errorMessage.push("Mã ĐT Visa không được lớn hơn 50 ký tự!");
                focusInput.push('maDTVisa');
            }

            if (!this.$v.txt_UsernameSub.maxLength) {
                errorMessage.push("Username Sub không được lớn hơn 50 ký tự!");
                focusInput.push('userNameSub');
            }

            if (!this.$v.txt_GhiChu.maxLength) {
                errorMessage.push("Ghi chú không được lớn hơn 500 ký tự!");
                focusInput.push('ghiChu');
            }

            if (errorMessage.length > 0) {
                this.$toast.error(errorMessage.join("\r\n"));
                this.$refs[focusInput[0]].focus();
                return false;
            } else return true;
        },

        async onLayTT(isMessage = true) {
            await this.getDsDoiTac();
            if (this.grid_DoiTac.items.length <= 0) {
                this.setButton(1);
                if (isMessage) this.$toast.error('Không có dữ liệu, vui lòng thử lại');
            }
        },

        onNhapMoi() {
            this.setButton(1);
        },

        async onGhiLai() {
            if (this.validateData()) {
                if (!this.btn_Enabled.nhapMoi) {
                    let rs = await this.addDoiTac();
                    if (rs) this.$toast.success('Thêm mới thành công!');
                } else {
                    let rs = await this.updateDoiTac();
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
                this.onDoiTacSelected({ data: this.grid_DoiTac.selected });
                this.setButton(2);
            }
        },

        async onXoa() {
            let rs = await this.getSoToaNhaDoiTac();
            if (rs > 0)
                return this.$toast.error(`Bạn không thể xóa bản ghi này! Đối tác này đang tồn tại Tòa nhà!`);
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
                            let rs = await this.deleteDoiTac();
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
            if (this.grid_DoiTac.items.length <= 0)
                this.$toast.error("Chưa có danh sách để xuất excel!");
            else {
                let excelExportProperties = {
                    fileName:"Danh-muc-doi-tac.xlsx",
                    dataSource: this.grid_DoiTac.items
                };
                this.$refs.doiTacGrid.excelExport(excelExportProperties);
            }
        },

        onDoiTacSelected(args) {
            if (args != null) {
                this.grid_DoiTac.selected = args.data;
                this.txt_MaDoiTac = args.data.ma_dt;
                this.txt_TenDoiTac = args.data.ten_dt;
                this.txt_DiaChi = args.data.diachi;
                this.txt_SoDT = args.data.so_dt;
                this.txt_NguoiDD = args.data.nguoi_dd;
                this.txt_SoTaiKhoan = args.data.taikhoan;
                this.txt_MaDTVisa = args.data.ma_dt_visa;
                this.txt_UsernameSub = args.data.username_sub;
                this.txt_GhiChu = args.data.ghichu;
                this.cbo_NganHang.selected = args.data.nganhang_id;
                this.selectCboRow();
                this.setButton(2);
            }
        },

        selectCboRow() {
            let idx = this.cbo_NganHang.items.findIndex(e => e.nganhang_id === this.cbo_NganHang.selected);
            if (idx !== -1) 
                this.$refs.nganHangCbo.$refs.grid.setCurrentSelectedRow(idx);
        },

        setButton(kieu) {
            this.btn_Enabled = {
                nhapMoi: false,
                ghiLai: false,
                xoa: false,
                huy: false
            };
            // this.txt_MaDoiTac_ReadOnly = false;
            if (kieu === -1) {          //Bat dau  
                this.btn_Enabled.ghiLai = true;
                this.btn_Enabled.huy = true;
            } else if (kieu === 1) {    //Them moi
                this.clear();
                this.btn_Enabled.ghiLai = true;
                this.btn_Enabled.huy = true;
                this.txt_MaDoiTac_ReadOnly = true;
            } else if (kieu === 2) {    //Cap nhat
                this.btn_Enabled.nhapMoi = true;
                this.btn_Enabled.ghiLai = true;
                this.btn_Enabled.huy = true;
                this.btn_Enabled.xoa = true;
            }
        },

        clear() {
            this.txt_MaDoiTac = '';
            this.txt_TenDoiTac = '';
            this.txt_DiaChi = '';
            this.txt_SoDT = '';
            this.txt_NguoiDD = '';
            this.txt_SoTaiKhoan = '';
            this.txt_GhiChu = '';
            this.txt_MaDTVisa = '';
            this.txt_UsernameSub = '';
            this.cbo_NganHang.items = [...this.cbo_NganHang.items];
            this.$v.$reset();
            this.$refs.tenDoiTac.focus();
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

        async getDsDoiTac() {
            try {
                this.loading(true);
                this.grid_DoiTac.items = [];
                let url = `/web-toanha/danhmuc-doitac/ds-doitac`;
                let response = await this.$root.context.get(url);
                if (response.error_code === 'BSS-00000000' && response.data !== undefined) {
                    this.grid_DoiTac.items = response.data;
                }
            }
            catch (error) {
                console.log(error);
            }
            finally {
                this.loading(false);
            }
        },

        async addDoiTac() {
            try {
                this.loading(true);
                let apiBody = {
                    diaChi: this.txt_DiaChi,
                    ghiChu: this.txt_GhiChu,
                    nganHangId: this.cbo_NganHang.selected,
                    nguoiDD: this.txt_NguoiDD,
                    soDT: this.txt_SoDT,
                    taiKhoan: this.txt_SoTaiKhoan,
                    tenDT: this.txt_TenDoiTac,
                    maDTVisa: this.txt_MaDTVisa,
                    userNameSub: this.txt_UsernameSub
                };
                let url = `/web-toanha/danhmuc-doitac/them-doitac`;
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

        async updateDoiTac() {
            try {
                this.loading(true);
                let apiBody = {
                    doiTacId: this.grid_DoiTac.selected.doitac_id,
                    diaChi: this.txt_DiaChi,
                    ghiChu: this.txt_GhiChu,
                    nganHangId: this.cbo_NganHang.selected,
                    nguoiDD: this.txt_NguoiDD,
                    soDT: this.txt_SoDT,
                    taiKhoan: this.txt_SoTaiKhoan,
                    tenDT: this.txt_TenDoiTac,
                    maDTVisa: this.txt_MaDTVisa,
                    userNameSub: this.txt_UsernameSub
                };
                let url = `/web-toanha/danhmuc-doitac/capnhat-doitac`;
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

        async getSoToaNhaDoiTac() {
            try {
                this.loading(true);
                let url = `/web-toanha/danhmuc-doitac/so-toanha-doitac?doiTacId=${this.grid_DoiTac.selected.doitac_id}`;
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

        async deleteDoiTac() {
            try {
                this.loading(true);
                let url = `/web-toanha/danhmuc-doitac/xoa-doitac/${this.grid_DoiTac.selected.doitac_id}`;
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
        }
    }
}
</script>
