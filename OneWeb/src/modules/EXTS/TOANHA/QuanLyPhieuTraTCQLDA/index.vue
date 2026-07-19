<template src="./index.template.html"></template>
<style scoped src="../DanhMucHopDongDauTu/style.scss"></style>
<script>
import ComboboxGrid from "../../../../components/Controls/ComboboxGrid"
import KyCuoc from '@/components/KyCuoc'
import EventBus from '@/utils/eventBus'
import moment from 'moment'
import Vue from 'vue'
export default {
    name: 'PhieuTraTCQLDA',
    components: { 
        appKyCuoc: KyCuoc,
        appCombobox: ComboboxGrid
    },
    props: {
        id: String,
        vloai: {
            type: Number,
            default: 1
        },
        vdt_id: {
            type: Number,
            default: 0
        }
    },

    async created() {
        EventBus.$on('chukycuocChange', this.chukycuocChangeHandler);
        this.cbo_Loai.items = [
            { id: 1, text: 'Đối tác'},
            { id: 2, text: 'Chủ đầu tư'}
        ];
        this.cbo_Loai.selected = 1;
        await this.getDsNhomHTTT();
        if (this.cbo_Nhom_HTTT.items.length > 0)
            this.cbo_Nhom_HTTT.selected = this.cbo_Nhom_HTTT.items[0].id;
    },

    computed: {
        aggregatesColumns () {
            return [
                { field: 'tientra', type: 'Sum', format: 'N0', footerTemplate: this.sumTemplate },
                { field: 'tragoc', type: 'Sum', format: 'N0', footerTemplate: this.sumTemplate },
                { field: 'trathue', type: 'Sum', format: 'N0', footerTemplate: this.sumTemplate },
                { field: 'sotientra', type: 'Sum', format: 'N0', footerTemplate: this.sumTemplate }
            ]
        },
    },

    data() {
        return {
            thongtinKyHoaDon: {
                kyHoaDonSelected : null,
                chuKySelected : null
            },
            cbo_Loai: {
                items: [],
                selected: 0
            },
            cbo_DTCDT: {
                items: [],
                selected: null
            },
            cbo_Nhom_HTTT: {
                items: [],
                selected: null
            },
            cbo_HTTT: {
                items: [],
                selected: -1,
                enabled: true
            },
            txt_UserGach: null,
            txt_TongTra: 0,
            txt_GhiChu: null,
            date_NgayTT: new Date(),
            today: new Date(),
            grid_PhieuTT: {
                items: [],
                checked: [],
                key: 1
            },
            grid_ChiTietTT: {
                items: [],
                select: {},
                key: 1
            },
            row1: true,
            row2: true,
            sumTemplate: function() {
                return { template: Vue.component('sumTemplate', {
                    template: `<input type="text" class="form-control tright" :value="data.Sum" :disabled="true" style="margin: 7px 0px 7px 0px;" />`,
                        data: function () {return {data: {data: {}}};}
                    })
                }
            }
        }
    },

    methods: {
        onShownModal() {
            this.row1 = true;
            this.row2 = true;
            this.cbo_Loai.selected = this.vloai;
            this.onSelectRow(this.vdt_id);
            this.grid_PhieuTT.items = [];
            this.grid_ChiTietTT.items = [];
            this.txt_UserGach = '';
            this.txt_TongTra = 0;
            this.txt_GhiChu = '';
            this.date_NgayTT = this.today;
            this.cbo_Nhom_HTTT.selected = this.cbo_Nhom_HTTT.items[0].id;
            this.hideComboboxTableHeader();
        },

        onHiddenModal() {
        },

        hideComboboxTableHeader() {
            let cbbRef = ['dtCbo', 'htttCbo'];
            this.$nextTick(function () {
                cbbRef.forEach(item => {
                    let elemts = this.$refs[item].$refs.grid.$el.querySelectorAll('.e-headercell');
                    elemts.forEach(elment => {
                        elment.style.display = 'none';
                    });
                });
            })
        },

        onDTSelected(args) {
            if (args)
                this.cbo_DTCDT.selected = args.dt_id;
        },
        
        onSelectRow(id) {
            let index = this.cbo_DTCDT.items.findIndex(e => e.dt_id === id);
            if (index !== -1)
                this.$refs.dtCbo.$refs.grid.setCurrentSelectedRow(index);
        },

        onHTTTSelected(args) {
            if (args)
                this.cbo_HTTT.selected = args.httt_id;
        },

        chukycuocChangeHandler(e) {
            this.thongtinKyHoaDon.kyHoaDonSelected = moment(e.p_kyhoadon).format('YYYYMM') + '01';
            this.thongtinKyHoaDon.chuKySelected = e.p_chuky_no;
        },

        async onTimKiem(isMessage = true) {
            await this.getDsPhieuTraDT();
            if (this.grid_PhieuTT.items.length <= 0) {
                this.txt_TongTra = 0;
                this.grid_ChiTietTT.items = [];
                if (isMessage)
                    this.$toast.error('Không có dữ liệu. Vui lòng thử lại!');
            } else
                this.txt_TongTra = this.grid_PhieuTT.items.reduce((accumulator, object) => {
                    return accumulator + object.sotientra;
                }, 0);
            
            this.txt_UserGach = this.$auth.getUserName();
        },

        async onXoaPhieu() {
            if (this.grid_PhieuTT.items.length <= 0)
                return this.$toast.error('Chưa có phiếu thanh toán để thực hiện!');
            if (this.grid_PhieuTT.checked.length <= 0)
                return this.$toast.error('Chưa chọn phiếu thanh toán để thực hiện!');
            
            try {                
                await this.$bvModal.msgBoxConfirm('Bạn chắc chắn muốn xóa phiếu thanh toán không?', {
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
                        let count = 0;
                        for (let item of this.grid_PhieuTT.checked) {
                            let rs = await this.postXoaPhieuThanhToanDT(item);
                            if (rs)
                                count++;
                        }
                        
                        if (count === this.grid_PhieuTT.checked.length)
                            this.$toast.success('Xóa phiếu thanh toán thành công!');
                        await this.onTimKiem(false);
                    }
                })
                .catch(err => {
                    console.log(err);
                })
            } catch (error) {
                console.log(error);
                this.$toast.error("Có lỗi xảy ra, vui lòng thử lại!")
            }
        },

        onHuy() {
            this.txt_UserGach = '';
            this.txt_GhiChu = '';
            this.date_NgayTT = this.today;
        },

        async onSelectPhieuTT(args) {
            if (args.data != null) {
                await this.getChiTietTraDT(args.data.phieu_id);
                this.txt_GhiChu = args.data.ghichu;
            }
        },

        onCheckedPhieuTT(args) {
            this.grid_PhieuTT.checked = args;
        },

        async getDsDoiTac() {
            try {
                this.loading(true);
                this.cbo_DTCDT.items = [];
                let url = `/web-toanha/gachno-doitac-chudautu/ds-thamso-doitac`;
                let response = await this.$root.context.get(url);
                if (response.error_code === 'BSS-00000000' && response.data !== undefined) {
                    this.cbo_DTCDT.items = response.data;
                }
            }
            catch (error) {
                console.log(error);
            }
            finally {
                this.loading(false);
            }
        },

        async getDsChuDauTu() {
            try {
                this.loading(true);
                this.cbo_DTCDT.items = [];
                let url = `/web-toanha/gachno-doitac-chudautu/ds-thamso-chudautu`;
                let response = await this.$root.context.get(url);
                if (response.error_code === 'BSS-00000000' && response.data !== undefined) {
                    this.cbo_DTCDT.items = response.data;
                }
            }
            catch (error) {
                console.log(error);
            }
            finally {
                this.loading(false);
            }
        },

        async getDsNhomHTTT() {
            try {
                this.loading(true);
                this.cbo_Nhom_HTTT.items = [];
                let url = `/web-toanha/quanly-phieutra/ds-nhom-httt`;
                let response = await this.$root.context.get(url);
                if (response.error_code === 'BSS-00000000' && response.data !== undefined) {
                    response.data.map(item => {
                        this.cbo_Nhom_HTTT.items.push({ id: item.nhom_httt_id, text: item.nhom_httt});
                    })
                }
            }
            catch (error) {
                console.log(error);
            }
            finally {
                this.loading(false);
            }
        },

        async getDsHTTTTheoNhom(id) {
            try {
                this.loading(true);
                this.cbo_HTTT.items = [];
                let url = `/web-toanha/quanly-phieutra/ds-httt-theo-nhom-httt-id/${id}`;
                let response = await this.$root.context.get(url);
                if (response.error_code === 'BSS-00000000' && response.data !== undefined) {
                    this.cbo_HTTT.items = response.data;
                }
            }
            catch (error) {
                console.log(error);
            }
            finally {
                this.loading(false);
            }
        },

        async getDsPhieuTraDT() {
            try {
                this.loading(true);
                this.grid_PhieuTT.items = [];
                let apiParams = {
                    kyHoaDon: this.thongtinKyHoaDon.kyHoaDonSelected,
                    loai: this.cbo_Loai.selected,
                    dtId: this.cbo_DTCDT.selected,
                    nhomHtId: Number(this.cbo_Nhom_HTTT.selected) !== -1 ? this.cbo_Nhom_HTTT.selected : 0,
                    htttId: this.cbo_HTTT.selected !== -1 ? this.cbo_HTTT.selected : 0,
                    ngayTT: moment(this.date_NgayTT).format('DD/MM/YYYY')
                };
                let url = `/web-toanha/quanly-phieutra/ds-phieutra-dt`;
                let response = await this.$root.context.get(url, apiParams);
                if (response.error_code === 'BSS-00000000' && response.data !== undefined) {
                    this.grid_PhieuTT.key++;
                    this.grid_PhieuTT.items = response.data;
                }
            }
            catch (error) {
                console.log(error);
            }
            finally {
                this.loading(false);
            }
        },

        async getChiTietTraDT(id) {
            try {
                this.loading(true);
                this.grid_ChiTietTT.items = [];
                let apiParams = {
                    kyHoaDon: this.thongtinKyHoaDon.kyHoaDonSelected,
                    phieuId: id
                };
                let url = `/web-toanha/quanly-phieutra/ds-cttra-dt`;
                let response = await this.$root.context.get(url, apiParams);
                if (response.error_code === 'BSS-00000000' && response.data !== undefined) {
                    this.grid_ChiTietTT.key++;
                    this.grid_ChiTietTT.items = response.data;
                }
            }
            catch (error) {
                console.log(error);
            }
            finally {
                this.loading(false);
            }
        },

        async postXoaPhieuThanhToanDT(id) {
            try {
                this.loading(true);
                let apiBody = {
                    kyHoaDon: this.thongtinKyHoaDon.kyHoaDonSelected,
                    phieuId: id
                };
                let url = `/web-toanha/quanly-phieutra/xoa-phieu-thanhtoan-dt`;
                let response = await this.$root.context.post(url, apiBody);
                if (response.error_code === 'BSS-00000000' && response.data !== undefined) {
                    return response.data.startsWith('OK');
                } return false;
            }
            catch (error) {
                console.log(error);
                return false;
            }
            finally {
                this.loading(false);
            }
        }
    },

    watch: {
        'cbo_Loai.selected': async function(newVal, oldVal) {
            if (newVal != null) {
                if (newVal === 1)
                    await this.getDsDoiTac();
                else if (newVal === 2)
                    await this.getDsChuDauTu();
                this.onSelectRow(this.vdt_id);
            }
        },

        'cbo_Nhom_HTTT.selected': async function(newVal, oldVal) {
            if (newVal != null) {
                if (Number(newVal) === -1)
                    this.cbo_HTTT.enabled = false;
                else {
                    this.cbo_HTTT.enabled = true;
                    await this.getDsHTTTTheoNhom(newVal);
                }
            }
        }
    }
}
</script>
