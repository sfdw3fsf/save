<template src="./index.template.html"></template>
<style scoped src="./style.scss"></style>
<script>
import breadcrumb from "@/components/breadcrumb"
import API from '../api/GachNoFileChuyenKhoanAPI.js'
import CommonAPI from '../api/Commons.js'
import { mapActions, mapGetters } from "vuex"
import moment from 'moment'

export default {
    components: { breadcrumb },

    async created() {
        if (this.danhSachNhanVienThuNganGetter === undefined || this.danhSachNhanVienThuNganGetter.length === 0) {
            await this.getDanhSachNhanVienThuNgan();                    
        }
        if (this.danhSachNhanVienThuNganGetter !== undefined && this.danhSachNhanVienThuNganGetter.length > 0) {
            // this.danhSachNhanVienThuNganGetter.forEach(item => {
            //     this.params.nhanVienTN_Options.push({ id: item.NHANVIEN_ID, text: item.TEN_NV });
            // });
            this.params.nhanVienTN_Selected = this.danhSachNhanVienThuNganGetter[0].NHANVIEN_ID;
        }

        if (this.danhSachHinhThucThanhToanGetter === undefined || this.danhSachHinhThucThanhToanGetter.length === 0) {
            await this.getDanhSachNganHangGachNo();                    
        }
        if (this.danhSachNganHangGachNoGetter !== undefined && this.danhSachNganHangGachNoGetter.length > 0) {
            this.danhSachNganHangGachNoGetter.forEach(item => {
                this.params.nganHang_Options.push({ id: item.NGANHANG_ID, text: item.TEN_NH });
            });
            this.params.nganHang_Selected = this.params.nganHang_Options[0].id;
        }

        await this.getHTTTNganHang();
    },

    computed: {
        ...mapGetters('qltnCommon', [
            'danhSachNganHangGachNoGetter',
            'danhSachNhanVienThuNganGetter'
        ])
    },

    data() {
        return {
            header: {
                title: 'GẠCH NỢ FILE CHỨNG TỪ NGÂN HÀNG',                
                list: [
                    { name: 'Kiểm kê công nợ', link: { name: 'Ui.cards' } },
                    { name: 'Gạch nợ file chuyển khoản', link: { name: 'Ui.buttons' } },
                ]
            },

            params: {
                nganHang_Options: [],
                nganHang_Selected: null,
                ngayTT: new Date(),
                today: new Date(),
                HTTTNganHang_Options: [],
                HTTTNganHang_Selected: null,
                nhanVienTN_Selected: null,
                checkSTT: false
            }
        }
    },

    methods: {
        ...mapActions('qltnCommon', [
            'getDanhSachNganHangGachNo',
            'getDanhSachNhanVienThuNgan'
        ]),

        async getHTTTNganHang() {
            try {
                this.loading(true);
                this.params.HTTTNganHang_Options = [];
                let response = await API.getHTTTNganHang(this.axios);
                if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined && response.data.data.length > 0) {
                    response.data.data.forEach(item => {
                        this.params.HTTTNganHang_Options.push({ id: item.HTTT_ID, text: item.HINHTHUC });
                    });
                    this.params.HTTTNganHang_Selected = this.params.HTTTNganHang_Options[0].id;                    
                }
            }
            catch (error) {
                this.$toast.error(error.response.data.message_detail);
            }
            finally {
                this.loading(false);
            }
        }
    }
}
</script>
