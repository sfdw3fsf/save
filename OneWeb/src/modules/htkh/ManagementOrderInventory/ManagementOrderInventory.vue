<template src="./ManagementOrderInventory.html"></template>

<script>
import breadcrumb from '@/components/breadcrumb'
import API from './api'
import apiHelper from './api.helper'

import DanhSachKetQua from './components/DanhSachKetQua.vue'
import '@riophae/vue-treeselect/dist/vue-treeselect.css'

import Utils from './utils'
import _ from 'lodash'



export default {
    name: 'ManagementOrderInventory',
    components: { breadcrumb, DanhSachKetQua },
    data() {
        return {
            header: {
                title: 'Xứ lý tồn đơn hàng',
                list: [
                    { name: 'Xứ lý tồn đơn hàng', link: { name: 'Ui.cards' } },
                    { name: 'Xứ lý tồn đơn hàng', link: { name: 'Ui.buttons' } }
                ]
            },
            actions: [
                { id: 'tsbtnSearch', icon: 'icon one-search', name: 'Tìm kiếm', visible: true, enable: true },
                { id: 'tsbtnChuyenPhieu', icon: 'nc-icon-glyph arrows-1_curved-next', name: 'Chuyển phiếu chưa giao', visible: false, enable: false },
                { id: 'tsbtnStop', icon: 'icon fa fa-stop-circle', name: 'Dừng Xử lý', visible: true, enable: false },
                { id: 'tsbtnDeleteOrder', icon: 'icon one-trash', name: 'Xoá Phiếu chủ động', visible: true, enable: true },
                { id: 'tsbtnHuyGiao', icon: 'nc-icon-glyph ui-1_circle-remove text-red', name: 'Huỷ Giao', visible: false, enable: true },
                { id: 'tsbtnExportExcel', icon: 'icon one-xlsx', name: 'Xuất Excel', visible: true, enable: true },
                { id: 'tsbtnTraCuuChiTiet', icon: 'nc-icon-glyph arrows-1_curved-next', name: 'Tra cứu chi tiết', visible: true, enable: true },
                { id: 'tsbtnHuyGiaoPhieu', icon: 'nc-icon-glyph ui-1_circle-remove', name: 'Hủy giao phiếu', visible: true, enable: true }
            ],

            chanelOption: {
                dataSources: [],
                value: 1
            },
            statusOption: {
                dataSources: [],
                value: 1
            },
            lPCCheckBox: 1,
            bKTCheckBox: 1,

            // Đơn Vị
            donviTree: {
                options: [],
                selected: [],
            },
            tinhTree: {
                options: [],
                selected: [],
            },
            ketQuaSelected: null



        }
    },
    computed: {
        phanVungId() {
            return +this.$root.token.getPhanVungID()
        },
        donViId() {
            return +this.$root.token.getDonViID()
        },
        nhanVienId() {
            return +this.$root.token.getNhanVienID()
        },



    },
    async created() {

        this.getCobLuong();
        this.getCboDvDai();
        this.getCboTinh();
        this.getCboTrangThai();


    },
    async mounted() {
        $(document).on('click', '.dropdown-menu', function (e) {
            e.stopPropagation()
        })
        if ($(window).width() < 992) {
            $('.dropdown-menu a').click(function (e) {
                e.preventDefault()
                if ($(this).next('.submenu').length) {
                    $(this).next('.submenu').toggle()
                }
                $('.dropdown').on('hide.bs.dropdown', function () {
                    $(this).find('.submenu').hide()
                })
            })
        }

    },
    methods: {


        async onClickAction(id) {
            switch (id) {
                case 'tsbtnSearch':
                    await this.tsbtnSearch_Click()
                    break
                case 'tsbtnStop':
                    await this.tsbtnStop_Click()
                    break
                case 'tsbtnDeleteOrder':
                    await this.tsbtnDeleteOrder_Click()
                    break
                case 'tsbtnTraCuuChiTiet':
                    await this.tsbtnTraCuuChiTiet_Click()
                    break
                case 'tsbtnHuyGiao':
                    await this.tsbtnHuyGiao_Click()
                    break
                case 'tsbtnExportExcel':
                    await this.tsbtnExportExcel_Click()
                    break
                case 'tsbtnHuyGiaoPhieu':
                    await this.tsbtnHuyGiaoPhieu_Click()
                    break
                default:
                    console.log("click Actions")
                    break
            }
        },


        async tsbtnHuyGiaoPhieu_Click(){
            console.log(2)
            this.$refs.refDsKetQua.huy_donhang()
        },


        async tsbtnSearch_Click() {
            
            if (this.tinhTree.selected.length == 0) {
                this.$toast.warning("Vui lòng chọn tỉnh")
                return;
            }
            const donViSelected = this._buildJsonDonViTree()

            const prams = {
                lstPhanvung: this.tinhTree.selected.join(","),
                p_loaihd_id: 38, // hardcode trên form
                p_kenhtiepnhan_id: 10, //hardcode trên form
                p_trangthai_id: + this.statusOption.value,
                p_json_donvi: JSON.stringify(donViSelected),
                p_kieu: + this.chanelOption.value,
                p_boqua_kenhtrang: this.bKTCheckBox ? 1 : 0,
            }

            this.$refs.refDsKetQua.getDanhSachDonHang(prams)
        },

        tsbtnStop_Click() {
            this.$refs.refDsKetQua.disconnect();
            this.onSetButtonEnable("tsbtnStop", false)
            this.onSetButtonEnable("tsbtnSearch", true)
        },

        tsbtnExportExcel_Click() {
            this.$refs.refDsKetQua.ExportExcel()
        },
        //xoá phiếu chủ động
        async  tsbtnDeleteOrder_Click() {
            this.$refs.refDsKetQua.xoaPhieu();
        },
   


        tsbtnTraCuuChiTiet_Click() {
   
            // this.$router.push({ path: '/search/SearchInstallDetail', query: { ma_tb: "HDDT0349556"}})
            this.$refs.refDsKetQua.searchDetail()
        },


        
        onChangeTreeSelect(){},

        removeNoChildren(node) {
            return {
                children: node.children && node.children.length ? node.children : 0
            }
        },

        async getCobLuong() {
            const response = await API.getDSLuong(this.axios)
            this.chanelOption.dataSources = apiHelper.getDataFromResponseApiReturnArray(response) || []
        },

        async getCboDvDai() {
            try {

                const response = await API.getDsDonViDaiV2(this.axios)
                this.donviTree.options = apiHelper.getDataFromResponseApiReturnArray(response)
            } catch (error) {
                console.log(error)
            }

        },
        async getCboTinh() {
            try {

                const response = await API.getDanhSachDonViTinh(this.axios)
                this.tinhTree.options = apiHelper.getDataFromResponseApiReturnArray(response)
            } catch (error) {
                console.log(error)
            }
        },
        async getCboTrangThai() {
            try {
                const response = await API.getTrangThai(this.axios)
                this.statusOption.dataSources = apiHelper.getDataFromResponseApiReturnArray(response)
            } catch (error) {
                console.log(error)
            }
        },

        onSetButtonEnable(id, enable) {
            this.actions.find(item => item.id === id).enable = enable
        },


        onSetButtonVisible(id, isVisible) {
            this.actions.find(item => item.id === id).visible = isVisible
        },

        _buildJsonDonViTree(){
            let dvTemp = [];
            if (this.donviTree.selected.length) {
                for (let i = 0; i < this.donviTree.selected.length; i++) {
                    const donvi = this.donviTree.selected[i];
                    dvTemp.push({ donvi_id: donvi })
                }
            }

            if(this.lPCCheckBox){
                dvTemp.push({ donvi_id: "9990" })
                dvTemp.push({ donvi_id: "1186" })
            }

            return dvTemp
        }









    },



    watch: {

    }
}
</script>
<style  scoped>
.vue-treeselect__menu {
    height: 330px !important;
}
</style>
