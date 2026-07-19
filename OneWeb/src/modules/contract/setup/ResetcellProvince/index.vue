<template src="./index.html"></template>

<script>
import define from "./define/index.js";
import api from "./api/index.js";

export default {
    components: {
        api
    },
    props: ['tag'],
    created() {

        // this.handleTag();

        this.initialize();
    },
    data() {
        return {
            ...define
        }
    },
    methods: {
        async initialize() {
            let tem = await this.axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_nguoidung', {
                type: 1, 
                param: this.$root.token.getNguoiDungID()
            });
            // console.log(tem.data.data);
            this.vma_tinh = await this.MAP_ID('tentat', 'css.tinh', "where tinh_id = " + this.$root.token.getPhanVungID());
            this.userGP = tem.data.data;// await this.MAP_ID("user_gphone", "admin.nguoidung", "where nguoidung_id = " + this.$root.token.getNguoiDungID());
            // console.log(this.vma_tinh, this.userGP);
            await this.getDsTinh();
            await this.getLoaiThueBao();
            this.LayLoaiTB();
        },
        LayLoaiTB() {
            console.log('LayLoaiTB', this.cbLoai_tb[0]['name'], this.cbTinh);
            if (this.cbLoai_tb.length > 0) {
                this.form.loai_tb = this.cbLoai_tb[0]['id'];
            }
            if (this.cbTinh.length > 0) {
                this.form.tinh = this.$root.token.getPhanVungID();
            }
            this.isDisableTB = true;
        },
        async MAP_ID(id_neo, table, dk) {
            try {
                let data = await this.axios.post('/web-ccdv/khaibaotsl/map_id', {
                    id_neo: id_neo,
                    table: table,
                    dk: dk
                });
                if (data.data.error == 200) {
                    return data.data.data;
                } else {
                    return '';
                }
            } catch (error) {
                this.$toast.error("Đã có lỗi xảy ra !!")
            }
        },
        clickButton(key) {
            if (!this.button[key]) {
                return false;
            }
            if (key == "thuc_hien") {
                this.tsbtnthuc_hien_Click();
            }
            if (key == "thoat") {
                this.tsbtnthoat_Click();
            }
        },
        async tsbtnthuc_hien_Click() {
            // if (DONGBO_GPHONE.DONGBO) {
                try {
                    if (this.userGP == "-1") {
                        this.$toast.error("Người dùng chưa được gán user kết nối hệ thống gphone. Liên hệ Admin để gán quyền.");
                        return;
                    }
                    let kq = await this.axios.post('/ccbs/gphone/reset_cell_agent', {
                        agent: this.vma_tinh,
                        ghi_chu: this.form.ghi_chu,
                        reset_type: this.form.loai_tb
                    });
                    if (kq.data.message == "OK")
                        this.$toast.success("Reset Cell " + this.vma_tinh + " thành công");
                    else
                        this.$toast.error("Reset Cell " + this.vma_tinh + " không thành công");
                } catch (error) {
                    this.$toast.error("Đã có lỗi xảy ra !!")
                }

            //}
        },
        tsbtnthoat_Click(){
            
        },
        handleTag() {
            // Lay gia trị tag truyen tu url
            if (this.$route.query.tag != null &&
                this.$route.params.tag != '' &&
                this.$route.query.tag.length > 0) {
                this.Tag = this.$route.query.tag
            }

            // Gan vao theo tham so cua form
            if (this.Tag != null && this.Tag.length > 0) {
                let arr = this.Tag.split('+')
                if (arr.length >= 1) this.ThHdId = parseInt(arr[0])
                if (arr.length >= 2) this.initLoaiHdArr = arr[1].split(',')
                if (arr.length >= 3) this.form.dich_vu_id = arr[2].split(';')
            }
        },
        async getDsTinh() {
            try {
                let res = await api.getTinh(this.axios);
                this.cbTinh = res.data.data;
                // console.log('get tinh');
            } catch (error) {
                this.$toast.error("Đã có lỗi xảy ra !!")
            }
        },
        async getLoaiThueBao() {
            try {
                let res = await api.getLoaiThueBao(this.axios);
                this.cbLoai_tb = res.data.data;
                // console.log('get thue bao');
            } catch (error) {
                this.$toast.error("Đã có lỗi xảy ra !!")
            }
        }
    },
    mounted() {

    }
}
</script>

<style>
</style>