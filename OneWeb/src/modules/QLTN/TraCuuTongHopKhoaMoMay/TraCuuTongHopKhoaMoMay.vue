<template src="./TraCuuTongHopKhoaMoMay.html"></template>
<script>
import Vue from "vue"
import { DialogPlugin } from '@syncfusion/ej2-vue-popups'

Vue.use(DialogPlugin)

import { mapActions, mapGetters } from 'vuex'
import CommonAPI from '../api/Commons'
import API from '../api/TraCuuTongHopKhoaMoMayAPI'
import moment from 'moment'

export default Vue.extend({
    components: { },
    name: 'TraCuuTongHopKhoaMoMay',
    async created () {
        if (this.danhSachDichVuVienThongGetter == null || this.danhSachDichVuVienThongGetter.length === 0)
            await this.getDanhSachDichVuVienThong();
        this.danhSachDichVuVienThongGetter.forEach(item => {
            this.params.dichVu_Options.push({ id: item.DICHVUVT_ID, text: item.TEN_DVVT })
        });
        this.params.dichVu_Selected = this.params.dichVu_Options[0].id;
        await this.getLoaiHinhTB(this.params.dichVu_Selected);
    },
    computed: {
        ...mapGetters('qltnCommon', ['danhSachDichVuVienThongGetter'])
    },
    data () {
        return {
            target: '.main-wrapper',
            animationSettings: { effect: 'Zoom' },
            params: {
                dichVu_Selected: null,
                dichVu_Checked: false,
                dichVu_Options: [],
                loaiHinh_Selected: null,
                loaiHinh_Checked: false,
                loaiHinh_Options: [],
                tuNgay: new Date(),
                denNgay: new Date(),
                today: new Date(),
            },
            items: []
        }
    },
    methods: {
        ...mapActions('qltnCommon', ['getDanhSachDichVuVienThong']),

        openDialog () {
          this.$refs.dlgKhoaMoMay.show()
        },

        async onSearch() {
            if (this.params.tuNgay === null)
                this.$toast.error('Vui lòng chọn giá trị cho từ ngày!');
            else if (this.params.denNgay === null)
                this.$toast.error('Vui lòng chọn giá trị cho đến ngày!');
            else {
                await this.getLichSuKhoaMoMay();
                if (this.items.length <= 0)
                    this.$toast.error('Không có dữ liệu, vui lòng thử lại!');
            }
        },

        onExcelExport() {
            if (this.items.length <= 0)
                this.$toast.error("Chưa có danh sách để xuất excel!");
            else {
                let excelExportProperties = {
                    fileName:"Danh-sach-tong-hop-khoa-mo-may.xlsx",
                    dataSource: this.items
                };
                this.$refs.khoaMoMayGrid.excelExport(excelExportProperties);
            }                
        },

        close() {
            //Clear data before closing modal:
            this.items = [];
            this.params.dichVu_Selected = this.params.dichVu_Options[0].id;
            this.params.dichVu_Checked = false;
            this.params.loaiHinh_Selected = this.params.loaiHinh_Options[0].id;
            this.params.loaiHinh_Checked = false;
            this.params.tuNgay = this.params.today;
            this.params.denNgay = this.params.today;
            this.$refs.dlgKhoaMoMay.hide();
        },

        dialogOpen () {
            
        },

        dialogClose () {
        },

        async getLoaiHinhTB(id) {
            try {
                this.loading(true);
                this.params.loaiHinh_Options = [];
                let response = await CommonAPI.getDanhSachLoaiHinhThueBao(this.axios, id);
                if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined) {
                    if (response.data.data.length > 0) {
                        response.data.data.forEach(item => {
                            this.params.loaiHinh_Options.push({ id: item.LOAITB_ID, text: item.TEN_LOAIHINH });
                        });
                        this.params.loaiHinh_Selected = this.params.loaiHinh_Options[0].id;
                    }
                }
            }
            catch (error) {
                console.log(error);
            }
            finally {
                this.loading(false);
            }
        },

        async getLichSuKhoaMoMay() {
            try {
                this.loading(true);
                this.items = [];
                let apiParams = {
                    tuNgay: moment(this.params.tuNgay).format('DD/MM/YYYY'),
                    denNgay: moment(this.params.denNgay).format('DD/MM/YYYY'),
                    dichVuVTId: this.params.dichVu_Checked ? this.params.dichVu_Selected : 0,
                    loaiTBId: this.params.loaiHinh_Checked ? this.params.loaiHinh_Selected : 0
                };
                let response = await API.getLichSuKhoaMoMay(this.axios, apiParams);
                if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined) {
                    this.items = response.data.data;
                }
            }
            catch (error) {
                console.log(error.response);
            }
            finally {
                this.loading(false);
            }
        }
    },
    watch: {
        'params.dichVu_Selected': async function(newVal, oldVal) {
            await this.getLoaiHinhTB(newVal);
        }
    }
})
</script>
