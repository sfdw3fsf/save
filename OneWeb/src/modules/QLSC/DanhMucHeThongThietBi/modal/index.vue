<template src="./index.html"></template>
<script>
import { required, maxLength, numeric, email } from "vuelidate/lib/validators"
import BssRequiredMarker from "@/components/BssRequiredMarker"
export default {
    props: [
        "actionType",
        "capNhatDto"
    ],
    components: {
        BssRequiredMarker,
    },
    data() {
        return {
            
        }
    },
    validations: {
        capNhatDto: {
            ma_hethong: {
                required,
                maxLength: maxLength(200)
            },
            ten_thietbi: {
                required,
                maxLength: maxLength(500)
            },
            ten_hethong: {
                required,
                maxLength: maxLength(500)
            },
            linh_vuc: {
                maxLength: maxLength(200)
            },
            email_nv: {
                required,
                maxLength: maxLength(100),
                email
            },
            so_dt_nv: {
                maxLength: maxLength(10),
                numeric
            }
        }
    },
    methods: {
        async capNhatChiTietHeThongDichVu(data) {
            try {
                let response = await this.$root.context.post(`/web-suco/danhmuchethongthietbi/capnhat-hethong-dichvu`, data)
                console.log(response)
                if (response.error_code === 'BSS-00000000') {
                    return true
                } else {
                    return false
                }
            } catch (err) {
                return false
            } finally {

            }
        },
        async capNhap() {
            if (this.validateData()) {
                let data = {
                    actionType: this.actionType, //1 - cập nhật, 0 - thêm mới
                    capNhatDto: this.capNhatDto
                }
                let rs = await this.capNhatChiTietHeThongDichVu(data)
                if (rs) {
                    if (this.actionType == 1) {
                        this.$toast.success("Cập nhật thành công!")
                    } else {
                        this.$toast.success("Thêm mới thành công!")
                    }
                } else {
                    this.$toast.error("Cập nhật không thành công!")
                }
            }
        },
        validateData() {
            this.$v.$touch();
            let errorMessage = [];
            let focusInput = [];

            if (!this.$v.capNhatDto.ma_hethong.required) {
                errorMessage.push("Mã hệ thống không được để trống!");
                focusInput.push('ma_hethong');
            } else if (!this.$v.capNhatDto.ma_hethong.maxLength) {
                errorMessage.push("Mã hệ thống không được lớn hơn 200 ký tự!");
                focusInput.push('ma_hethong');
            }

            if (!this.$v.capNhatDto.ten_thietbi.required) {
                errorMessage.push("Tên thiết bị không được để trống!");
                focusInput.push('ten_thietbi');
            } else if (!this.$v.capNhatDto.ten_thietbi.maxLength) {
                errorMessage.push("Tên thiết bị không được lớn hơn 500 ký tự!");
                focusInput.push('ten_thietbi');
            }

            if (!this.$v.capNhatDto.ten_hethong.required) {
                errorMessage.push("Tên hệ thống không được để trống!");
                focusInput.push('ten_hethong');
            } else if (!this.$v.capNhatDto.ten_hethong.maxLength) {
                errorMessage.push("Tên hệ thống không được lớn hơn 500 ký tự!");
                focusInput.push('ten_hethong');
            }

            if (!this.$v.capNhatDto.linh_vuc.maxLength) {
                errorMessage.push("Lĩnh vực không được lớn hơn 200 ký tự!");
                focusInput.push('linh_vuc');
            }

            if (!this.$v.capNhatDto.email_nv.required) {
                errorMessage.push("Email không được để trống!");
                focusInput.push('email_nv');
            } else if (!this.$v.capNhatDto.email_nv.maxLength) {
                errorMessage.push("Email không được lớn hơn 100 ký tự!");
                focusInput.push('email_nv');
            } else if (!this.$v.capNhatDto.email_nv.email){
                errorMessage.push("Email không đúng định dạng!");
                focusInput.push('email_nv');
            }

            if (!this.$v.capNhatDto.so_dt_nv.maxLength) {
                errorMessage.push("Số điện thoại không được lớn hơn 10 ký tự!");
                focusInput.push('so_dt_nv');
            } else if (!this.$v.capNhatDto.so_dt_nv.numeric) {
                errorMessage.push("Số điện thoại không đúng định dạng!");
                focusInput.push('so_dt_nv');
            }

            if (errorMessage.length > 0) {
                this.$toast.error(errorMessage.join("\r\n"));
                this.$refs[focusInput[0]].focus();
                return false;
            } else return true;
        },
        cleanForm() {
            // this.capNhatDto.hethong_id = 0
            // this.capNhatDto.dvi_chuquan = null
            this.capNhatDto.ma_hethong = ""
            this.capNhatDto.ten_thietbi = ""
            this.capNhatDto.ten_hethong = ""
            this.capNhatDto.linh_vuc = ""
            this.capNhatDto.email_nv = ""
            this.capNhatDto.so_dt_nv = ""
        },
        close() {
            this.cleanForm()
            this.$emit("reload")
            this.$bvModal.hide("popup-capnhat");
        }
    }
}
</script>