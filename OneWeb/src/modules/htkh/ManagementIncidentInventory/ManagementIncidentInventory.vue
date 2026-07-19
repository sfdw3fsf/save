
<template src="./ManagementIncidentInventory.html"></template>

<script>
import breadcrumb from '@/components/breadcrumb'
import API from './api'
import apiHelper from './api.helper'

import DanhSachKetQua from './components/DanhSachKetQua.vue'
import '@riophae/vue-treeselect/dist/vue-treeselect.css'

import Utils from './utils'
import _ from 'lodash'
import GiaoPhieuNhanVien from '@/modules/contract/setup/DeclareLandline/popup/giaophieunhanvien'
import Lichsuphieugiao from "./popups/Lichsuphieugiao/Lichsuphieugiao.vue";

export default {
    name: 'ManagementIncidentInventory',
    components: {
        breadcrumb,
        DanhSachKetQua,
        GiaoPhieuNhanVien,
        Lichsuphieugiao
    },
    data() {
        return {
            header: {
                title: 'Kiểm soát tồn báo hỏng',
                list: [{
                        name: 'Kiểm soát tồn báo hỏng',
                        link: {
                            name: 'Ui.cards'
                        }
                    },
                    {
                        name: 'Kiểm soát tồn báo hỏng',
                        link: {
                            name: 'Ui.buttons'
                        }
                    }
                ]
            },
            ttGiaoViec: {
                ngayGiao: "",
                nguoiGiaoId: 0,
                phieuId: 0
            },
            actions: [{
                    id: 'tsbtnSearch',
                    icon: 'icon one-search',
                    name: 'Tìm kiếm',
                    visible: true,
                    enable: true
                }, //R
                {
                    id: 'tsbtnStop',
                    icon: 'icon fa fa-stop-circle',
                    name: 'Dừng Xử lý',
                    visible: true,
                    enable: false
                }, //R
                {
                    id: 'tsbtnDeleteOrder',
                    icon: 'icon one-trash',
                    name: 'Xoá Phiếu chủ động',
                    visible: true,
                    enable: true
                }, //R
                {
                    id: 'tsbtnGiaophieu',
                    icon: 'icon one-file-arrow-right1',
                    name: 'Giao phiếu',
                    visible: true,
                    enable: true
                },
                {
                    id: 'tsbtnHuyGiao',
                    icon: 'nc-icon-glyph ui-1_circle-remove text-red',
                    name: 'Huỷ Giao',
                    visible: true,
                    enable: true
                },
                {
                    id: 'tsbtnExportExcel',
                    icon: 'icon one-xlsx',
                    name: 'Xuất Excel',
                    visible: true,
                    enable: true
                }

            ],

            chanelOption: {
                dataSources: [],
                value: 1
            },
            statusOption: {
                dataSources: [],
                value: []
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
            ketQuaSelected: null,
            errorMessage: "",
            Tag: 1

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

    },
    async mounted() {
        console.log(this.$route.query.tag)
        if (this.$route.query.tag && this.$route.query.tag !== '') {
            this.Tag = this.$route.query.tag.replaceAll(' ', '+')
            console.log(this.Tag)
        }
        this.getCobLuong();
        this.getCboDvDai();
        this.getCboTinh();
        this.getCboTrangThai();
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
                    await this.tsbtnSearch_Click() //r
                    break
                case 'tsbtnStop':
                    await this.tsbtnStop_Click() //r
                    break
                case 'tsbtnChuyenPhieu':
                    await this.tsbtnChuyenphieu_Click()
                    break
                case 'tsbtnDeleteOrder':
                    await this.tsbtnDeleteOrder_Click() //r
                    break
                case 'tsbtnGiaophieu':
                    await this.tsbtnGiaophieu_Click()
                    break
                case 'tsbtnHuyGiao':
                    await this.tsbtnHuyGiao_Click()
                    break
                case 'tsbtnExportExcel':
                    await this.tsbtnExportExcel_Click()
                    break
                default:
                    console.log("click Actions")
                    break
            }
        },

        async tsbtnSearch_Click() {

            if (this.tinhTree.selected.length == 0) {
                this.$toast.warning("Vui lòng chọn tỉnh")
                return;
            }
            const donViSelected = this._buildJsonDonViTree()
            console.log(donViSelected)
            if (donViSelected.length == 0) {
                this.$toast.warning("Hãy chọn đơn vị tiếp nhận ");
            }
            const prams = {
                lstPhanvung: this.tinhTree.selected.join(","),
                p_trangthai_id: this.statusOption.value.join(','),
                p_json_donvi: JSON.stringify(donViSelected),
                p_kieu: +this.chanelOption.value,
                p_tag: +this.Tag,
                p_boqua_baohong_ao: this.bKTCheckBox ? 1 : 0,
            }
            console.log(prams)
            this.$refs.refDsKetQua.getDanhSachBaohong(prams)
        },
        tsbtnStop_Click() {
            this.$refs.refDsKetQua.disconnect();
            this.onSetButtonEnable("tsbtnStop", false)
        },
        Lichsu_Click(index, rowData) {
            console.log(rowData)
            this.$refs.lichsugiaophieuModal.showModal();
        },
        tsbtnExportExcel_Click() {
            this.$refs.refDsKetQua.ExportExcel()
        },
        //xoá phiếu chủ động
        async tsbtnDeleteOrder_Click() {
            this.$refs.refDsKetQua.xoaPhieu();
        },

        tsbtnTraCuuChiTiet_Click() {
            this.$refs.refDsKetQua.searchDetail()
        },
        // handleError(error) {
        //     if (error.response && error.response.status === 403) {
        //         this.errorMessage = 'Hủy giao cần quyền tổ trưởng, hoặc quyền Hỗ trợ cho Tổ trưởng, hãy liên hệ phòng nghiệp vụ đài HTKH để xin quyền';
        //     } else {
        //         this.errorMessage = 'Lỗi chưa được xử lý';
        //     }
        // },
     
        async tsbtnHuyGiao_Click() {
            console.log(111111111)
            this.actions[5].Enabled = false;
            const sb = [];
                let baohong = this.$refs.refDsKetQua.danhSachKetQua.selectedRow;
                console.log(22222222)
                if (baohong.ttbh_id !== 3&&baohong.ttbh_id !== 9) {
                    this.$toast.warning("Báo hỏng cần ở trạng thái chờ viễn thông xử lý: " + baohong.ma_tb)
                    return;
                }
                let input = {
                    "p_phanvung_id": baohong.tinh_id,
                    "p_json_baohong_id": JSON.stringify([{
                        "baohong_id": baohong.baohong_id
                    }])
                }
                let DsPhieu = this.GetDataList(await API.sp_tracuugiaophieu_bh_toanquoc(this.axios,input));
                let phieu_id = DsPhieu.find(x => x.ttph_id == 1||x.ttph_id == 9).phieu_id;

                const { tinh_id } = baohong
                const param = {tinh_id , phieu_id }
                const result = await API.huygiao_baohong(this.axios,param);
                if (result.data.error_code !== 'BSS-00000000') {
                    this.$toast.error("Lỗi hủy giao: " + result.data.message_detail);
                } else if (result.data.error_code === 'BSS-00000000') {
                    this.$root.toastSuccess("Hủy giao thành công");
                    const {
                            tinh_id,
                            baohong_id
                    } = this.$refs.refDsKetQua.danhSachKetQua.selectedRow
                    this.$refs.refDsKetQua.danhSachKetQua.dataSources = this._removeItemFormArray(this.$refs.refDsKetQua.danhSachKetQua.dataSources, tinh_id,baohong_id)
                    this.actions[5].Enabled = true;
                }
        },
         GetDataList: function (response) {
            console.log(response)
            if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                return response.data.data
            } else if (response && response.data && response.data.error_code === 'BSS-00000204') {} else {
                this.$root.$toast.error('Lấy dữ liệu không thành công')
            }
            return []
        },
        cboTrangthai_SelectedValueChanged(value) {
            if(value != 1){
                this.actions[2].enable = false
            }
            if(value == 1){
                this.actions[2].enable = true
            }
            
        },

        async tsbtnChuyenphieu_Click() {
            let numbers = this.tinhTree.selected.filter(item => typeof item === 'number' && !isNaN(item));
            this.loading(true);
            for (let i = 0; i < numbers.length; i++) {
                let str_err = "";
                let tinh_id = numbers[i];
                let params = {
                    phanvung_id: tinh_id
                }
                const response = await API.chuyenphieu_chuagiao(this.axios, params);
                if (response.data.error_code !== 'BSS-00000000') {
                    str_err += numbers[i]
                }
            }
            if (str_err != "") {
                this.$toast.error("Lỗi chuyển phiếu tại các tỉnh: " + str_err);
            } else {
                this.$root.toastSuccess("Đã chuyển phiếu chưa giao của " + numbers.length + " tỉnh")
            }
            this.loading(false);
        },
        async tsbtnGiaophieu_Click() {
            let row = this.$refs.refDsKetQua.danhSachKetQua.selectedRow
            this.loading(true);
            if (row.ttbh_id == 1) {
             
                        let input = {
                            p_phanvung_id: row.tinh_id,
                            p_ma_ts: "NHANHONG_TB_KHOAMAY"
                        }
                        const thamso = await API.fn_kiemtra_thamso_md(this.axios, input);
                        if (thamso.data.error_code === 'BSS-00000000') {
                            this.NHANHONG_TB_KHOAMAY = true;
                        }
                        if (this.NHANHONG_TB_KHOAMAY && row.trangthaitb_id != 1) {
                            this.$toast.error("Trạng thái hiện tại của thuê bao không thể giao phiếu");
                            this.loading(false);
                            return;
                        }
                        let ttnd = {
                            "NHANVIEN_ID": this.$root.token.getNhanVienID(),
                            "DONVI_ID": this.$root.token.getDonViID(),
                            "NGUOI_CN": this.$root.token.getUserName(),
                            "MAY_CN": this.$root.token.getMachine(),
                            "IP_CN": this.$root.token.getIP()
                        }
                        let param = {
                            p_phanvung_id: row.tinh_id,
                            p_ds_para: JSON.stringify({
                                "BAOHONG_ID": row.baohong_id,
                                "TINH_ID": row.tinh_id
                            }),
                            p_ds_ttnd: JSON.stringify(ttnd)
                        }
                        const {
                            tinh_id,
                            baohong_id
                        } = this.$refs.refDsKetQua.danhSachKetQua.selectedRow
                        const giaonhanh = await API.fn_tudong_giaophieu_baohong_dai(this.axios, param);
                        if (giaonhanh.data.error_code === "BSS-00000000") {
                            this.$refs.refDsKetQua.danhSachKetQua.dataSources = this._removeItemFormArray(this.$refs.refDsKetQua.danhSachKetQua.dataSources, tinh_id,baohong_id)
                            this.$toast.success("Giao phiếu thành công!")
                        } else {
                            this.$toast.error("Lỗi giao phiếu: " + giaonhanh.data.message);
                        }
                    
            } else {
                path = "https://onebss.vnpt.vn/#/incident/ProcessAcceptanceIncidentByTag?tag=" + row.ttbh_id + "&kieu_Load=1&matb_tk=" + row.ma_tb + "&dichvt_tk_id=" + row.dichvuvt_id + "&quytrinh_tk_id=" + row.quytrinh_id
                if (process.env.NODE_ENV == 'testing') {
                    path = path.replace('https://onebss.vnpt.vn', 'https://dev-onebss.vnpt.vn')
                }
                window.open(path, '_blank')
            }
            this.loading(false);
        },
        onChangeTreeSelect() {

        },
      
         _removeItemFormArray(dsKetqua, tinh_id, baohong_id) {
            console.log(dsKetqua)
            const indexToRemove = dsKetqua.findIndex(item => item.tinh_id === tinh_id && item.baohong_id === baohong_id);
            if (indexToRemove !== -1) {
                dsKetqua.splice(indexToRemove, 1);
            }
            return dsKetqua;
        },
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
                console.log(1111111)
                let input = {
                    "p_tag": this.Tag
                }
                console.log(input)
                const response = await API.getTrangThai(this.axios, input)
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

        _buildJsonDonViTree() {
            let dvTemp = [];
            if (this.donviTree.selected.length) {
                for (let i = 0; i < this.donviTree.selected.length; i++) {
                    const donvi = this.donviTree.selected[i];
                    dvTemp.push({
                        donvi_id: donvi
                    })
                }
            }

            if (this.lPCCheckBox) {
                dvTemp.push({
                    donvi_id: "9990"
                })
                dvTemp.push({
                    donvi_id: "1186"
                })
            }

            return dvTemp
        }

    },

    watch: {

    }
}
</script>

<style scoped>
.vue-treeselect__menu {
    height: 330px !important;
}
</style>
