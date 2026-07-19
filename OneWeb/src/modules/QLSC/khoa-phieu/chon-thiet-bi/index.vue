<template src="./index.template.html"></template>
<script>
import { Edit, Sort, Filter, ForeignKey, Resize, Freeze, Page } from "@syncfusion/ej2-vue-grids"
import { L10n } from '@syncfusion/ej2-base'

L10n.load({
    'vi-VN': {
        grid: {
          EmptyRecord: 'Không có bản ghi nào để hiển thị'
        }
    }
})

export default {
    provide: {
        grid: [Edit, Sort, Filter, ForeignKey, Resize, Freeze, Page ]
    },
    data() {
        return {
            treeDonVi: {
                dataSource: [],
                searchText: ""
            },
            dsDonVi: [],
            dsNhomTbi: [],
            dsLoaiTbi: [],
            dsKieuTbi: [],
            dsDungLuongTbi: [],
            dsLopMc: [],
            dsPhanLoaiKc: [],
            dsThietBi: [],
            tenThietBi: null,
            nhomTbiId: null,
            loaiTbiId: null,
            kieuTbiId: null,
            dungLuongId: null,
            lopMcId: null,
            phanLoaiKcId: null,
            donViId: null,
            showComboLopMc: false,
            showComboPhanLoaiKc: false,
            checkAllThietBi: false,
            dsKeyThietBiDuocChon: [],
        }
    },
    computed: {
        treeFields() {
            return {
                dataSource: this.treeDonVi.dataSource,
                id: "DONVI_ID",
                text: "TEN_DV",
                parentID: "DONVI_CHA_ID",
                hasChildren: 'hasChildren'
            }
        } 
    },
    watch: {
        dsDonVi(newVal) {
            if (!newVal || newVal.length == 0) {
                this.treeDonVi.dataSource = []
            } else {
                this.treeDonVi.searchText = ""
                this.treeDonVi.dataSource = newVal.map(x => { return {...x} })
            }
        },
        async nhomTbiId(newVal) {

            if (!newVal) {
                return
            }

            try {
                this.loading(true)
                await this.layDsLoaiTbiTheoNhom(newVal)
            } catch (err) {

            } finally {
                this.loading(false)
            }

            if (!!this.dsLoaiTbi && this.dsLoaiTbi.length > 0) {
                this.loaiTbiId = this.dsLoaiTbi[0].LOAITBI_ID
            }
        },
        async loaiTbiId(newVal) {

            if (!newVal) {
                return
            }

            let loaiTbiId = parseInt(newVal)
            let nhomTbiId = parseInt(this.nhomTbiId)

            if (nhomTbiId != 3 /*tbi ngoại vi*/ || loaiTbiId == -1) {
                this.showComboLopMc = false
                this.showComboPhanLoaiKc = false
            } else if (loaiTbiId == 26 /*cáp*/) {
                this.lopMcId = -1
                this.showComboLopMc = true
                this.showComboPhanLoaiKc = false
            } else if (loaiTbiId == 25 /*kết cuối*/) {
                this.phanLoaiKcId = -1
                this.showComboLopMc = false
                this.showComboPhanLoaiKc = true
            }

            if (loaiTbiId == -1) {
                this.dsKieuTbi = [{KIEUTBI_ID: -1, KIEU_TBI: '-- Tất cả --'}]
                this.dsDungLuongTbi = [{DUNGLUONG_ID: -1, DUNGLUONG: '-- Tất cả --'}]
                this.kieuTbiId = -1
                this.dungLuongId = -1
                return
            }

            try {
                this.loading(true)
                let loading1 = this.layDsKieuTbiTheoLoai(loaiTbiId)
                let loading2 = this.layDsDungLuongTbiTheoLoai(loaiTbiId)
                await loading1
                await loading2
            } catch (err) {

            } finally {
                this.loading(false)
            }

            if (!!this.dsKieuTbi && this.dsKieuTbi.length > 0) {
                this.kieuTbiId = this.dsKieuTbi[0].KIEUTBI_ID
            }

            if (!!this.dsDungLuongTbi && this.dsDungLuongTbi.length > 0) {
                this.dungLuongId = this.dsDungLuongTbi[0].DUNGLUONG_ID
            }
        },
        checkAllThietBi(newVal) {

            if (newVal) {
                this.dsKeyThietBiDuocChon = this.dsThietBi.map(item => item.KEY)
                return
            } 
            
            if (this.dsKeyThietBiDuocChon.length > 0 && this.dsKeyThietBiDuocChon.length == this.dsThietBi.length) {
                this.dsKeyThietBiDuocChon = []
            }
        },
        dsKeyThietBiDuocChon(newVal) {
            this.checkAllThietBi = newVal.length > 0 && newVal.length == this.dsThietBi.length
        },
        dsThietBi() {
            this.dsKeyThietBiDuocChon = []
        }
    },
    methods: {
        show() {
            return this.$refs['ref-modal'].show()
        },
        hide() {
            this.$refs['ref-modal'].hide()
        },
        onModalHidden() {
            this.dsDonVi = []
            this.dsNhomTbi = []
            this.dsLoaiTbi = []
            this.dsKieuTbi = []
            this.dsDungLuongTbi = []
            this.dsLopMc = []
            this.dsPhanLoaiKc = []
            this.dsThietBi = []
            this.tenThietBi = null
            this.nhomTbiId = null
            this.loaiTbiId = null
            this.kieuTbiId = null
            this.dungLuongId = null
            this.lopMcId = null
            this.phanLoaiKcId = null
            this.donViId = null
            this.showComboLopMc = false
            this.showComboPhanLoaiKc = false
            this.checkAllThietBi = false
            this.dsKeyThietBiDuocChon = []
        },
        async onModalShown() {

            this.$refs['ref-ten-thietbi'].focus()

            this.dsLoaiTbi = [{LOAITBI_ID: -1, LOAI_TBI: '-- Tất cả --'}]
            this.dsKieuTbi = [{KIEUTBI_ID: -1, KIEU_TBI: '-- Tất cả --'}]
            this.dsDungLuongTbi = [{DUNGLUONG_ID: -1, DUNGLUONG: '-- Tất cả --'}]
            this.dsLopMc = [{LOPMC_ID: -1, LOP_MC: '-- Tất cả --'}]
            this.dsPhanLoaiKc = [{PHANLOAIKC_ID: -1, PHANLOAI_KC: '-- Tất cả --'}]
            this.loaiTbiId = -1
            this.kieuTbiId = -1
            this.dungLuongId = -1
            this.lopMcId = -1
            this.phanLoaiKcId = -1

            try {
                this.loading(true)
                let loading1 = this.layDsDonVi()
                let loading2 = this.layDsNhomTbi()
                let loading3 = this.layDsLopMc()
                let loading4 = this.layDsPhanLoaiKc()
                await loading1
                await loading2
                await loading3
                await loading4
            } catch (err) {

            } finally {
                this.loading(false)
            }

            if (!!this.dsNhomTbi && this.dsNhomTbi.length > 0) {
                this.nhomTbiId = this.dsNhomTbi[0].NHOMTBI_ID
            }

            if (!!this.dsLopMc && this.dsLopMc.length > 0) {
                this.lopMcId = this.dsLopMc[0].LOPMC_ID
            }

            if (!!this.dsPhanLoaiKc && this.dsPhanLoaiKc.length > 0) {
                this.phanLoaiKcId = this.dsPhanLoaiKc[0].PHANLOAIKC_ID
            }
        },
        getColumnTemplateChonNhieu(parent) {
            return function() {
                return { 
                    template: {
                        template: `
                            <div class="check-action">
                                <input 
                                    type="checkbox" 
                                    class="check"
                                    v-model="parent.dsKeyThietBiDuocChon"
                                    :value="data.KEY"/>
                                <span class="name"></span>
                            </div>
                        `,
                        data() {
                            return { 
                                parent: parent,
                                data: { }
                            }
                        }
                    }
                }
            }
        },
        getHeaderTemplateChonNhieu(parent) {
            return function() {
                return {
                    template: {
                        template: `
                            <div class="check-action">
                                <input type="checkbox" v-model="parent.checkAllThietBi" class="check"/>
                                <span class="name"></span>
                            </div>
                        `,
                        data() {
                            return { 
                                parent: parent
                            }
                        }
                    }
                }
            }
        },
        async onClickBtnNapLai() {
            try {
                this.loading(true)
                await this.layDsDonVi()
            } catch (err) {

            } finally {
                this.loading(false)
            }
        },
        onActionComplete(args) {
            if (args.requestType == "filtering") {
                let filterObj = args.currentFilterObject
                let grid = document.querySelector(`[e-mappinguid="${filterObj.uid}"]`).closest('.e-grid')
                let filterInput = grid.querySelector(`#${filterObj.field}`)
                filterInput.click() 
                filterInput.focus() 
            }
        },
        onRowSelected(args) {
            let refValue = args.row.closest(".e-grid").getAttribute('ref-value')
            this.$refs[refValue].$el.querySelectorAll('.col-mark-selected-row').forEach(item => {
                item.style.display = args.rowIndex == parseInt(item.id) ? 'block' : 'none'
            })
        },
        onDataBound() {
            this.$refs['grid-thietbi'].selectRow(0)
        },
        filterTreeDonVi() {

            let searchText = !this.treeDonVi.searchText ? null : this.treeDonVi.searchText.toLowerCase()

            if (!searchText) {
                this.treeDonVi.dataSource = this.dsDonVi.map(x => { return {...x} })
                return
            }

            let filter = this.dsDonVi.filter(x => x.TEN_DV.toLowerCase().includes(searchText)).map(x => { return {...x} })
            
            filter.forEach(x => {

                if (filter.some(y => y.DONVI_ID == x.DONVI_CHA_ID)) {
                    return
                }

                x.DONVI_CHA_ID = null
            })

            this.treeDonVi.dataSource = filter
        },
        onNodeClickedDonVi(args) {
            this.donViId = args.node.dataset.uid
        },
        onClickBtnChon() {
            if (this.dsKeyThietBiDuocChon.length == 0) {
                this.$root.toastError('Chưa có thiết bị nào được chọn')
                return
            }

            let data = this.dsThietBi.filter(item => this.dsKeyThietBiDuocChon.some(key => key == item.KEY))
            this.$emit('select', data)
            this.hide()
        },
        async onClickBtnTimKiem() {

            let data = {
                ten_tbi: this.tenThietBi,
                nhomtbi_id: this.nhomTbiId,
                loaitbi_id: this.loaiTbiId,
                kieutbi_id: this.kieuTbiId,
                donvi_id: this.donViId,
                dungluong_id: this.dungLuongId,
                lopmc_id: this.lopMcId,
                phanloaikc_id: this.phanLoaiKcId,
            }

            try {
                this.loading(true)
                await this.timKiemDsThietBi(data)
            } catch (err) {

            } finally {
                this.loading(false)
            }
        },
        async timKiemDsThietBi(data) {
            try {
                let response = await this.$root.context.post(`/web-suco/qlsc/nghiem-thu/timKiemDsThietBi`, data)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    response.data.forEach((item, index) => item.KEY = index + 1)
                    this.dsThietBi = response.data
                    return
                } 
            
                throw {response: { data: response}}
            } catch (err) {
                this.dsThietBi = []
            }
        },
        async layDsDonVi() {
            try {
                let response = await this.$root.context.get(`/web-suco/danhmuc/ds-donvi-dulieu`)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    response.data.forEach(item => {
                        item.hasChildren = true
                        item.expanded = !item.DONVI_CHA_ID
                    })
                    this.dsDonVi = response.data
                    return
                } 
            
                throw {response: { data: response}}
            } catch (err) {
                this.dsDonVi = []
            }
        },
        async layDsNhomTbi() {
            try {
                let response = await this.$root.context.get(`/web-ecms/danhmuc/layDsNhomTbi`)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    this.dsNhomTbi = response.data
                    return
                } 
            
                throw {response: { data: response}}
            } catch (err) {
                this.dsNhomTbi = []
            }
        },
        async layDsLoaiTbiTheoNhom(nhomTbiId) {
            try {
                let response = await this.$root.context.post(`/web-ecms/danhmuc/layDsLoaiTbiTheoNhom`, {id: nhomTbiId})

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    response.data.unshift({LOAITBI_ID: -1, LOAI_TBI: '-- Tất cả --'})
                    this.dsLoaiTbi = response.data
                    return
                } 
            
                throw {response: { data: response}}
            } catch (err) {
                this.dsLoaiTbi = [{LOAITBI_ID: -1, LOAI_TBI: '-- Tất cả --'}]
            }
        },
        async layDsKieuTbiTheoLoai(loaiTbiId) {
            try {
                let response = await this.$root.context.get(`/web-suco/danhmuc/ds-kieu-tbi?loaitbi_id=${loaiTbiId}`)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    response.data.unshift({KIEUTBI_ID: -1, KIEU_TBI: '-- Tất cả --'})
                    this.dsKieuTbi= response.data
                    return
                } 
            
                throw {response: { data: response}}
            } catch (err) {
                this.dsKieuTbi = [{KIEUTBI_ID: -1, KIEU_TBI: '-- Tất cả --'}]
            }
        },
        async layDsDungLuongTbiTheoLoai(loaiTbiId) {
            try {
                let response = await this.$root.context.get(`/web-suco/danhmuc/ds-dungluong-tbi?loaitbi_id=${loaiTbiId}`)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    response.data.unshift({DUNGLUONG_ID: -1, DUNGLUONG: '-- Tất cả --'})
                    this.dsDungLuongTbi= response.data
                    return
                } 
            
                throw {response: { data: response}}
            } catch (err) {
                this.dsDungLuongTbi = [{DUNGLUONG_ID: -1, DUNGLUONG: '-- Tất cả --'}]
            }
        },
        async layDsLopMc() {
            try {
                let response = await this.$root.context.post(`/web-cabman/danhmuc/layDsLopMC`)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    response.data.unshift({LOPMC_ID: -1, LOP_MC: '-- Tất cả --'})
                    this.dsLopMc = response.data
                    return
                } 
            
                throw {response: { data: response}}
            } catch (err) {
                this.dsLopMc = [{LOPMC_ID: -1, LOP_MC: '-- Tất cả --'}]
            }
        },
        async layDsPhanLoaiKc() {
            try {
                let response = await this.$root.context.post(`/web-cabman/danhmuc/layDsPhanLoaiKC`)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    response.data.unshift({PHANLOAIKC_ID: -1, PHANLOAI_KC: '-- Tất cả --'})
                    this.dsPhanLoaiKc = response.data
                    return
                } 
            
                throw {response: { data: response}}
            } catch (err) {
                this.dsPhanLoaiKc = [{PHANLOAIKC_ID: -1, PHANLOAI_KC: '-- Tất cả --'}]
            }
        },
     }
}
</script>
<style>
    .chon-thietbi-suco .e-grid {
        font-family: "Roboto", Arial !important;
    }

    .chon-thietbi-suco .e-grid .e-filterbarcell {
        padding: 5px 7px !important;
        border-width: 1px 0 0 1px !important;
    }

    .chon-thietbi-suco .e-grid th.e-headercell,
    .chon-thietbi-suco .e-grid .e-gridcontent td.e-rowcell {
        padding: 5px 7px !important;
        height: 30px !important;
    }

    .chon-thietbi-suco .e-grid th.e-grouptopleftcell,
    .chon-thietbi-suco .e-grid th.e-headercell, 
    .chon-thietbi-suco .e-grid th.e-filterbarcell {
        background-color: #BAE7FF !important;
        border-color: white !important;
    }

    .chon-thietbi-suco .e-grid th.e-filterbarcell > div input.form-control {
        padding-bottom: 0 !important;
        padding-top: 0 !important;
        height: 26px !important;
    }

    .chon-thietbi-suco .e-grid td.e-active {
        background: #F9E1A9 !important;
    }

    .chon-thietbi-suco .e-grid.e-gridhover tr[role='row']:not(.e-editedrow):hover .e-rowcell:not(.e-cellselectionbackground):not(.e-active):not(.e-updatedtd):not(.e-indentcell),
    .chon-thietbi-suco .e-grid.e-gridhover tr[role='row']:hover .e-detailrowcollapse:not(.e-cellselectionbackground):not(.e-active):not(.e-updatedtd):not(.e-indentcell),
    .chon-thietbi-suco .e-grid.e-gridhover tr[role='row']:hover .e-rowdragdrop:not(.e-cellselectionbackground):not(.e-active):not(.e-updatedtd):not(.e-indentcell),
    .chon-thietbi-suco .e-grid.e-rtl .e-gridhover tr[role='row']:hover .e-rowdragdrop:not(.e-cellselectionbackground):not(.e-active):not(.e-updatedtd):not(.e-indentcell),
    .chon-thietbi-suco .e-grid.e-gridhover tr[role='row']:hover .e-detailrowexpand:not(.e-cellselectionbackground):not(.e-active):not(.e-updatedtd):not(.e-indentcell) {
        background-color: #FFF9EB !important;
    }

    .chon-thietbi-suco .e-grid td.e-rowcell { 
        line-height: 20px !important;
        padding: 5px 7px !important;
    }

    .chon-thietbi-suco .e-grid .e-filterdiv.e-fltrinputdiv .e-input-group.e-control-wrapper {
        border: 0 !important;
    }

    .chon-thietbi-suco .e-grid .e-filtertext.e-input.e-disable {
        display: none !important;
    }

    .chon-thietbi-suco .e-grid tr.e-emptyrow td {
        text-align: center !important;
    }

    .chon-thietbi-suco .e-grid div.e-gridheader div.e-headercontent thead tr.e-columnheader th[aria-colindex="1"],
    .chon-thietbi-suco .e-grid div.e-gridheader div.e-headercontent thead tr.e-columnheader th[aria-colindex="1"] > div {
        padding: 0 !important;
    }

    .chon-thietbi-suco .e-grid .e-pagerexternalmsg {
        display: none !important;
    }

    .chon-thietbi-suco .tree-plus-ext .e-treeview,
    .chon-thietbi-suco .tree-plus-ext .e-treeview .e-ul {
        overflow: visible !important;
    }

    @media (min-width: 1200px) {
        #id-frm-chon-thietbi-suco .modal-xl {
            max-width: 80% !important;
        }
    }
</style>
