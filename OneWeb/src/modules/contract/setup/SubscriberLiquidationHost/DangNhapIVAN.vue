<template>
    <ejs-dialog :enableResize='true' :visible="false" ref="dlg_ivan" :header='"ĐĂNG NHẬP HỆ THỐNG BHXH"'
                    showCloseIcon=false target=".main-wrapper" width='50%' height="300">
        <div class="modal-content popup-box">
            <div class="list-actions-top">
                <ul class="list">
                    <li>
                        <a @click="tsbtnTimKiem_Click">
                            <span class="icon ui-1_check-circle-08 nc-icon-glyph"></span>Đăng nhập
                        </a>
                    </li>
                </ul>
            </div>
            <div class="popup-body">
                <div class="box-form">
                    <div class="info-row">
                        <div class="key w90">Tên đăng nhập</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model="txtTenDangNhap">
                        </div>
                    </div>
                    <div class="info-row">
                        <div class="key w90">Mật khẩu</div>
                        <div class="value">
                            <input type="password" class="form-control" v-model="txtMatKhau">
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </ejs-dialog>
</template>
<script>
import api from './API.js'
export default {
    name:'DangNhapIVAN',
    props:{
        hdtb_id: {
            type: Number,
            default: 0
        }
    },
    data() {
        return {
            txtTenDangNhap: "",
            txtMatKhau: "",
        }
    },
    methods: {
        openDialog() {
            this.txtTenDangNhap = ""
            this.txtMatKhau = ""
            this.$refs.dlg_ivan.show()
        },
        async tsbtnTimKiem_Click() {
            try {
                let params = {
                    'ten_dang_nhap': this.txtTenDangNhap.trim(),
                    'mat_khau': this.txtMatKhau.trim(),
                }
                let response = await api.ivan_xac_thuc(this.axios, params)
                if (response && response.data && response.data.errorCode == 0 && response.data.data && response.data.data.status == 200) {
                    this.$refs.dlg_ivan.hide()
                    this.$emit("logINSuccess", this.txtTenDangNhap.trim())
                } else {
                    this.$toast.error(response.data.faultString)
                }
            } catch (e) {
                console.log(e)
                this.$toast.error(e)
            }
        },
    },
}
</script>