<template>  
    <div class="modal-content popup-box">
        <div class="list-actions-top">
            <ul class="list">
                <li>
                    <a @click="tim_kiem">
                        <span class="icon ui-1_check-circle-08 nc-icon-glyph"></span> Tìm kiếm
                    </a>
                </li>
            </ul>
        </div>
        <div class="popup-body">
            <div class="box-form">
                <div class="info-row">
                    <div class="key w90">
                        Mã nhân viên
                    </div>
                    <div class="value">
                        <ejs-textbox v-model="ma_nv_search"></ejs-textbox>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
import api from "./api/index.js";
export default {
    name:'TimKiemNhanVien',
    components:{
        
    },
    data() {
        return {
            ma_nv_search: "",
        }
    },
    methods: {
        async fn_tt_nhanvien(type, param) {
            let result;
            try {
                const params = { 
                    type: type,
                    param: param
                }
                let response = await api.fn_tt_nhanvien(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data;                
                }
            } catch(e) {
                console.log(e);           
            }
            return result;
        },
        async fn_tt_nguoidung(type, param) {
            let result;
            try {
                const params = { 
                    type: type,
                    param: param
                }
                let response = await api.fn_tt_nguoidung(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data;                
                }
            } catch(e) {
                console.log(e);           
            }
            return result;
        },
        async tim_kiem() {       
            if (this.ma_nv_search.trim() != "") {
                try {
                    this.loading(true);
                    let vnhanvienid = Number(await this.fn_tt_nhanvien(1, this.ma_nv_search.trim()));

                    // Ko tìm được theo mã người dùng
                    if (vnhanvienid == -1) {
                        this.$bvModal.msgBoxConfirm("Không tìm thấy nhân viên theo mã nhân viên " + this.ma_nv_search.trim() + ".\n. Bạn muốn tiếp tục tìm theo User đăng nhập không ?", {
                            title: '', size: 'sm', okTitle: 'Đồng ý', cancelTitle: 'Hủy',
                        }).then(async v => {
                            if (v) {
                                vnhanvienid = Number(await this.fn_tt_nguoidung(4, this.ma_nv_search.trim()));
                                if (vnhanvienid == -1) {
                                    this.$toast.error("Không tìm thấy thông tin về mã nhân viên vừa nhập! ");
                                    return;
                                }
                                
                                let vdonviid = Number(await this.fn_tt_nhanvien(2, vnhanvienid));
                                this.$emit("onTimKiemNhanVien", {nhanvien_id: vnhanvienid, donvi_id: vdonviid})
                            }
                        });    
                    } else {
                        let vdonviid = Number(await this.fn_tt_nhanvien(2, vnhanvienid));
                        this.$emit("onTimKiemNhanVien", {nhanvien_id: vnhanvienid, donvi_id: vdonviid})
                    }
                } catch (exp) {
                    this.$toast.error("Có lỗi xảy ra: " + exp)
                } finally {
                    this.loading(false)
                }
            }
        }
    },
    mounted() {
        
    },
}
</script>