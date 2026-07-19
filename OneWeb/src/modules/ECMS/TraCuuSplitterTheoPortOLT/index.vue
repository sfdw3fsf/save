<template src="./index.template.html"></template>
<script>
import { L10n } from '@syncfusion/ej2-base'
import { CheckBoxSelection } from "@syncfusion/ej2-vue-dropdowns"
import XLSX from "xlsx"
import ModalXuatDuLieu from './ModalXuatDuLieu/index.vue'
import BssErrorMarker from '@/components/BssErrorMarker'
import BssRequiredMarker from '@/components/BssRequiredMarker'
import { required } from 'vuelidate/lib/validators'
import { Page as TreePage, 
         Filter as TreeFilter,
         Sort as TreeSort,
         Resize as TreeResize,
         Freeze as TreeFreeze,
         CommandColumn as TreeCommandColumn } from "@syncfusion/ej2-vue-treegrid";
import FormDanDo from "../tracuu-dandocap_theoketcuoi/index.vue";

export default {
    provide: {
        treegrid: [TreePage, TreeFilter, TreeSort, TreeResize, TreeFreeze, TreeCommandColumn],
        multiselect: [CheckBoxSelection]
    },
    name: 'TraCuuSplitterTheoPortOLT',
    components: { XLSX, ModalXuatDuLieu, FormDanDo, BssErrorMarker, BssRequiredMarker },
    data() {
        return {
            dsSplitterTree: [],
            dsSplitterFlat: [],
            dsTtvt: [],
            dsToQl: [],
            dsVeTinh: [],
            dsOlt: [],
            dsCardOlt: [],
            dsPortOlt: [],
            ttvtId: null,
            toQlId: null,
            veTinhId: null,
            oltId: null,
            cardOltId: null,
            portOltId: null,
            chkVeTinhChecked: true,
            chkOltChecked: true,
            chkCardOltChecked: true,
            chkPortOltChecked: true,
            kyHieu: null,
            diaChi: null,
            tuyChonXuatExcel: 1,
            dsThuocTinh: [
                "STT", "KETCUOI_ID", "CAP_SP", "TEN_KC", "KYHIEU", "DIACHI", "NGAY_CN", "DUNGLUONG", "DLSD", "DLROI", "TEN_OLT", "PORT_OLT", "KEY_FIELD", "PARENT_FIELD"
                // {name: 'STT', width: 100, textAlign: 'Right'},
                // {name: 'KETCUOI_ID', width: 130, textAlign: 'Right'},
                // {name: 'CAP_SP', width: 100, textAlign: 'Right'},
                // {name: 'TEN_KC', width: 130, textAlign: 'Left'},
                // {name: 'KYHIEU', width: 130, textAlign: 'Left'},
                // {name: 'DIACHI', width: 150, textAlign: 'Left'},
                // {name: 'NGAY_CN', width: 130, textAlign: 'Right'},
                // {name: 'DUNGLUONG', width: 100, textAlign: 'Right'},
                // {name: 'DLSD', width: 100, textAlign: 'Right'},
                // {name: 'DLROI', width: 100, textAlign: 'Right'},
                // {name: 'TEN_OLT', width: 150, textAlign: 'Left'},
                // {name: 'PORT_OLT', width: 100, textAlign: 'Right'},
                // {name: 'KEY_FIELD', width: 150, textAlign: 'Left'},
                // {name: 'PARENT_FIELD', width: 150, textAlign: 'Left'}
            ],
            dsThueBaoXuatExcel: [],
            columnTemplate: function () {
                return { 
                    template: {
                        template: '<span class="col-mark-selected-row fa fa-play text-main"></span>',
                        data() {
                            return { 
                                data: { } 
                            }
                        }
                    }
                }
            },
            filterBarTemplate: {
                create: function (args) {

                    let div = document.createElement('div')
                    div.classList.add('input-icon-right')
                    
                    let input = document.createElement('input')
                    input.classList.add('form-control')
                    input.id = args.column.foreignKeyField

                    let span = document.createElement('span')
                    span.classList.add('icon', 'nc-icon-outline', 'ui-1_zoom')

                    div.append(input)
                    div.append(span)

                    return div
                },
                write: function (args) {

                    args.element.querySelector('input').addEventListener('input', (args) => {

                        let value = args.currentTarget.value
                        value = !!value ? value.trim() : value

                        if (!value) {
                            this.parent.removeFilteredColsByField(args.currentTarget.id);
                        } else {
                            this.parent.filterByColumn(args.currentTarget.id, 'contains', value)
                        }
                    })
                }
            },
            commands: [{ buttonOption: { cssClass: 'btn btn-primary', content: "...", click: this.btnCommandClick } }],
            currentKyHieu: ""
        }
    },
    validations: {
        ttvtId: {
            required
        },
        toQlId: {
            required
        }
    },
    watch: {
        async ttvtId(newVal) {

            this.toQlId = null

            if (!newVal) {
                this.dsToQl = []
                return
            }

            try {
                this.loading(true)
                await this.layDmToQl(newVal)

            } catch (err) {

            } finally {
                this.loading(false)
            }

            if (this.dsToQl && this.dsToQl.length > 0) {
                this.toQlId = this.dsToQl[0].DONVI_ID
            }
        },
        async toQlId(newVal) {

            this.veTinhId = null

            if (!newVal) {
                this.dsVeTinh = []
                return
            }

            try {
                this.loading(true)
                await this.layDmVeTinh(newVal)
            } catch (err) {

            } finally {
                this.loading(false)
            }

            if (this.dsVeTinh && this.dsVeTinh.length > 0) {
                this.veTinhId = this.dsVeTinh[0].DONVI_ID
            }
        },
        async veTinhId(newVal) {

            this.oltId = null

            if (!newVal) {
                this.dsOlt = []
                return
            }

            try {
                this.loading(true)
                await this.layDmOlt(newVal)
            } catch (err) {

            } finally {
                this.loading(false)
            }

            if (this.dsOlt && this.dsOlt.length > 0) {
                this.oltId = this.dsOlt[0].THIETBI_ID
            }
        },
        async oltId(newVal) {

            this.cardOltId = null

            if (!newVal) {
                this.dsCardOlt = []
                return
            }

            try {
                this.loading(true)
                await this.layDmCardOlt(newVal)
            } catch (err) {

            } finally {
                this.loading(false)
            }
        },
        async cardOltId(newVal) {

            this.portOltId = null

            if (!newVal) {
                this.dsPortOlt = []
                return
            }

            try {
                this.loading(true)
                await this.layDmPortOlt(newVal.split('#')[1])
            } catch (err) {

            } finally {
                this.loading(false)
            }
        },
    },
    async mounted () {

        L10n.load({
            'vi-VN': {
                dropdowns: {
                    noRecordsTemplate: "Không có dữ liệu",
                    actionFailureTemplate: ""
                },
                grid: {
                    EmptyRecord: 'Không có bản ghi nào để hiển thị'
                },
            }
        })

        try {
            this.loading(true)
            await this.layDmTtvt()
        } catch (err) {

        } finally {
            this.loading(false)
        }

        if (this.dsTtvt && this.dsTtvt.length > 0) {
            this.ttvtId = this.dsTtvt[0].DONVI_ID
        }

        this.$refs['input-ten-kyhieu'].focus()
    },
    methods: {
        btnCommandClick: function(args){
            let rowInfo = this.$refs["grid-splitter"].getRowInfo(args.target);
            this.currentKyHieu = rowInfo.rowData.KYHIEU;
            this.$refs.modalDanDo.show();
        },
        modalDanDo_Show: async function (args) {
            this.$refs.formDanDo.bindData(this.currentKyHieu);
        },
        onDataBoundTreeGrid(ref) {
            return () => {
                let grid = this.$refs[ref]

                if (!!grid.ej2Instances.grid.focusModule) {
                    grid.ej2Instances.grid.focusModule.destroy()
                }

                if (grid.getDataRows().length > 0) {
                    grid.selectRow(0)
                } else if (grid.frozenColumns > 0) {
                    let visibleColumnCount = grid.getVisibleColumns().length
                    let emptyRows = grid.$el.querySelectorAll('.e-emptyrow')
                    let cell0 = emptyRows[0].cells[0]
                    let cell1 = emptyRows[1].cells[0]
                    cell0.style.visibility = 'hidden'
                    cell1.innerHTML = cell0.innerHTML
                    cell1.setAttribute('colspan', visibleColumnCount - grid.frozenColumns)
                    cell1.style.setProperty('text-align', 'left', 'important')
                }
            }
        },
        onQueryCellInfo(args) {
            if (!!args.column.field) {
                args.cell.style.setProperty('background-color', args.data.COLOR, 'important')
            }
        },
        okChonKieuXuatExcel() {

            if (this.tuyChonXuatExcel == 1) {
                this.dsThueBaoXuatExcel = [...this.dsSplitterFlat].sort((a, b) => { return a.STT - b.STT })
                this.$bvModal.show('modal-xuat-dulieu-tracuu-splitter-theo-port-olt')
            }

            if (this.tuyChonXuatExcel == 2) {

                let data = []
                let isTree = this.dsSplitterFlat.some(item => !!item.PARENT_FIELD)

                if (!isTree) {
                    this.dsSplitterFlat.forEach(item => {
                        data.push({
                            'Tên Splitter': item.TEN_KC,
                            'STT': item.STT,
                            'ID': item.KETCUOI_ID,
                            'Cấp SP': item.CAP_SP,
                            'Ngày cập nhật': item.NGAY_CN,
                            'Địa chỉ': item.DIACHI,
                            'Dung lượng': item.DUNGLUONG,
                            'Đã dùng': item.DLSD,
                            'Chưa dùng': item.DLROI,
                            'Thiết bị OLT': item.TEN_OLT,
                            'Cổng OLT (slot/port)': item.PORT_OLT
                        })
                    })
                } else {
                    this.dsSplitterTree.forEach(item => {
                        data.push({
                            'Tên Splitter': item.TEN_KC,
                            '': null,
                            'STT': item.STT,
                            'ID': item.KETCUOI_ID,
                            'Cấp SP': item.CAP_SP,
                            'Ngày cập nhật': item.NGAY_CN,
                            'Địa chỉ': item.DIACHI,
                            'Dung lượng': item.DUNGLUONG,
                            'Đã dùng': item.DLSD,
                            'Chưa dùng': item.DLROI,
                            'Thiết bị OLT': item.TEN_OLT,
                            'Cổng OLT (slot/port)': item.PORT_OLT
                        })

                        if (item.childrent.length > 0) {
                            item.childrent.forEach(item1 => {
                                data.push({
                                    'Tên Splitter': null,
                                    '': item1.TEN_KC,
                                    'STT': item1.STT,
                                    'ID': item1.KETCUOI_ID,
                                    'Cấp SP': item1.CAP_SP,
                                    'Ngày cập nhật': item1.NGAY_CN,
                                    'Địa chỉ': item1.DIACHI,
                                    'Dung lượng': item1.DUNGLUONG,
                                    'Đã dùng': item1.DLSD,
                                    'Chưa dùng': item1.DLROI,
                                    'Thiết bị OLT': item1.TEN_OLT,
                                    'Cổng OLT (slot/port)': item1.PORT_OLT
                                })
                            })
                        }
                    })                
                }

                let worksheet = XLSX.utils.json_to_sheet(data)

                if (isTree) {

                    worksheet["!merges"] = [
                        { s: { r: 0, c: 0 }, e: { r: 0, c: 1 } }
                    ]

                    worksheet["!cols"] = [
                        { width: 4},
                        { width: 20 },
                        { width: 10 },
                        { width: 10 },
                        { width: 10 },
                        { width: 20 },
                        { width: 40 },
                        { width: 10 },
                        { width: 10 },
                        { width: 10 },
                        { width: 30 },
                        { width: 30 }
                    ]
                } else {
                    worksheet["!cols"] = [
                        { width: 20 },
                        { width: 10 },
                        { width: 10 },
                        { width: 10 },
                        { width: 20 },
                        { width: 40 },
                        { width: 10 },
                        { width: 10 },
                        { width: 10 },
                        { width: 30 },
                        { width: 30 }
                    ]
                }

                let workbook = XLSX.utils.book_new()
                XLSX.utils.book_append_sheet(workbook, worksheet, "Sheet1")
                XLSX.writeFile(workbook, "export.xlsx")
            }
        },
        onClickButtonXuatExcel() {

            if (!this.dsSplitterFlat || this.dsSplitterFlat.length == 0) {
                this.$toast.error('Không có dữ liệu để xuất Excel !')
                return
            }
            this.tuyChonXuatExcel = 1
            this.$bvModal.show('modal-tuychon')
        },
        onClickButtonLamLai() {
            this.ttvtId = null
            this.kyHieu = null
            this.diaChi = null
        },
        async onClickButtonTraCuu() {
            try {
                this.loading(true)
                await this.layDsThueBao()

                Object.keys(this.sort).forEach(item => {
                    this.sort[item].sortDirection = null
                    this.sort[item].sortIcon = 'fa-sort'
                })
            } catch (err) {

            } finally {
                this.loading(false)
            }
        },
        async layDsThueBao() {
            try {

                if (this.$root.token.getPhanVungID() != 21/*HNI*/) {
                    
                    let errorMessage = []

                    this.$v.$touch()

                    if (this.$v.$invalid) {

                        if (!this.$v.ttvtId.required) {
                            errorMessage.push("Bạn chưa chọn trung tâm viễn thông")
                        }

                        if (!this.$v.toQlId.required) {
                            errorMessage.push("Bạn chưa chọn tổ quản lý")
                        }
                    }

                    if (errorMessage.length > 0) {
                        this.$toast.error(errorMessage.join('\n'))
                        return
                    }
                }

                let data = {
                    ttvt_id: this.ttvtId,
                    tokt_id: this.toQlId,
                    tramtb_id: !this.chkVeTinhChecked ? '' : (!this.veTinhId ? '' : this.veTinhId),
                    olt_id: !this.chkOltChecked ? '' : (!this.oltId ? '' : this.oltId),
                    cardolt_id: !this.chkCardOltChecked ? '' : (!this.cardOltId ? '' : this.cardOltId.split('#')[1]),
                    lst_port_vl_id: !this.chkPortOltChecked ? '' : (!this.portOltId ? '' : this.portOltId.join(',')),
                    ky_hieu: this.kyHieu,
                    dia_chi: this.diaChi
                }

                let response = await this.$root.context.post('/web-ecms/tracuu/ds_splitter_theo_port_olt', data)

                if (response.error_code === 'BSS-00000000' && response.data != null && response.data.length > 0) {
                    
                    let dsSplitter = response.data

                    dsSplitter.forEach(item => {
                        item.KEY_FIELD = `${item.KETCUOI_ID}-${item.PORT_OLT}-${item.TEN_OLT}`
                        item.PARENT_FIELD = null
                        let dungLuongRoi = parseInt(item.DLROI)
                        item.COLOR = dungLuongRoi == 0 ? '#ED5050' : (dungLuongRoi == 1 ? '#F39738' : (dungLuongRoi == 2 ? '#EAC344' : ''))
                    })

                    dsSplitter.forEach(item => {
                        
                        if (item.CAP_SP == 1) {
                            return
                        }

                        dsSplitter.forEach(item1 => {
                        
                            if (item1.KETCUOI_ID == item.KETCUOI_ID) {
                                return
                            }

                            if (item1.CAP_SP == 1 && item1.PORT_OLT == item.PORT_OLT && item1.TEN_OLT == item.TEN_OLT) {
                                item.PARENT_FIELD = item1.KEY_FIELD
                            }
                        })
                    })

                    let dsSplitterRoot = dsSplitter.filter(item => item.PARENT_FIELD == null)

                    for (let i = 0; i < dsSplitter.length; i++) {
                        
                        let parent = dsSplitter[i]

                        parent.childrent = []

                        for (let j = 0; j < dsSplitter.length; j++) {

                            let child = dsSplitter[j]

                            if (child.PARENT_FIELD == parent.KEY_FIELD) {
                                parent.childrent.push(child)
                            }
                        }
                    }

                    this.dsSplitterTree = dsSplitterRoot
                    this.dsSplitterFlat = dsSplitter
                } else {
                    this.dsSplitterTree = []
                    this.dsSplitterFlat = []
                }
            } catch (err) {
                this.dsSplitterTree = []
                this.dsSplitterFlat = []
            }
        },
        async layDmPortOlt (cardOltId) {
            try {
                let response = await this.$root.context.post('/web-ecms/danhmuc/layDsPortOltTheoCardOlt', {id: cardOltId})

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    this.dsPortOlt = response.data
                } else {
                    this.dsPortOlt = []
                }
            } catch (err) {
                this.dsPortOlt = []
            }
        },
        async layDmCardOlt (oltId) {
            try {
                let response = await this.$root.context.post('/web-ecms/danhmuc/layDsCardOltTheoOlt', {id: oltId})

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    
                    let dsCardOlt = response.data
                    let dsCardOltRoot = []

                    if (dsCardOlt.length > 0) {

                        dsCardOlt = dsCardOlt.map(item => {return {id: item.KEY, label: item.TEN_TB, pid: item.PKEY, children: []}})
                        let dsCardOltId = dsCardOlt.map(item => item.id)
                        dsCardOltRoot = dsCardOlt.filter(item => dsCardOltId.indexOf(item.pid) < 0)

                        dsCardOlt.forEach(item => { 
                            
                            dsCardOlt.forEach(item1 => { 
                                if (item1.pid == item.id) {
                                    item.children.push(item1)
                                }
                            })

                            if (item.children.length == 0) {
                                delete item.children
                            } else {
                                item.isDisabled = true
                                item.isDefaultExpanded = true
                            }
                        })
                    }

                    this.dsCardOlt = dsCardOltRoot
                } else {
                    this.dsCardOlt = []
                }
            } catch (err) {
                this.dsCardOlt = []
            }
        },
        async layDmOlt (veTinhId) {
            try {
                let response = await this.$root.context.post('/web-ecms/danhmuc/layDsOltTheoVeTinh', {id: veTinhId})

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    this.dsOlt = response.data
                } else {
                    this.dsOlt = []
                }
            } catch (err) {
                this.dsOlt = []
            }
        },
        async layDmTtvt () {
            try {
                let response = await this.$root.context.post('/web-ecms/danhmuc/layDsTTVTTheoNVTrongToken')

                if (response.error_code === 'BSS-00000000' && response.data != null) {

                    if (this.$root.token.getPhanVungID() == 21/*HNI*/) {
                        this.dsTtvt = [{DONVI_ID: -1, TEN_DV: ""}, ...response.data]
                    } else {
                        this.dsTtvt = response.data
                    }
                } else {
                    this.dsTtvt = []
                }
            } catch (err) {
                this.dsTtvt = []
            }
        },
        async layDmToQl (ttvtId) {
            try {
                let response = await this.$root.context.post('/web-ecms/danhmuc/layDsToKT', {id: ttvtId})

                if (response.error_code === 'BSS-00000000' && response.data != null) {

                    if (this.$root.token.getPhanVungID() == 21/*HNI*/) {
                        this.dsToQl = [{DONVI_ID: -1, TEN_DV: ""}, ...response.data]
                    } else {
                        this.dsToQl = response.data
                    }
                } else {
                    this.dsToQl = []
                }
            } catch (err) {
                this.dsToQl = []
            }
        },
        async layDmVeTinh (toQlId) {
            try {
                let response = await this.$root.context.post('/web-ecms/danhmuc/layDsVeTinh', {id: toQlId})

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    this.dsVeTinh = response.data
                } else {
                    this.dsVeTinh = []
                }
            } catch (err) {
                this.dsVeTinh = []
            }
        },
    }
}
</script>
<style>
    .tracuu-splitter-theo-port-olt .e-grid {
        font-family: "Roboto", Arial !important;
    }

    .tracuu-splitter-theo-port-olt .e-grid .col-mark-selected-row {
        font-size: 12px;
        display: none;
    }

    .tracuu-splitter-theo-port-olt .e-grid .e-active .col-mark-selected-row {
        display: block;
    }

    .tracuu-splitter-theo-port-olt .e-grid .e-filterbarcell {
        padding: 5px 7px !important;
        border-width: 1px 0 0 1px !important;
    }

    .tracuu-splitter-theo-port-olt .e-grid th.e-headercell,
    .tracuu-splitter-theo-port-olt .e-grid .e-gridcontent td.e-rowcell {
        padding: 5px 7px !important;
        height: 30px !important;
    }

    .tracuu-splitter-theo-port-olt .e-grid th.e-grouptopleftcell,
    .tracuu-splitter-theo-port-olt .e-grid th.e-headercell, 
    .tracuu-splitter-theo-port-olt .e-grid th.e-filterbarcell {
        background-color: #BAE7FF !important;
        border-color: white !important;
    }

    .tracuu-splitter-theo-port-olt .e-grid th.e-filterbarcell > div input.form-control {
        padding-bottom: 0 !important;
        padding-top: 0 !important;
        height: 26px !important;
    }

    .tracuu-splitter-theo-port-olt .e-grid td.e-active {
        background: transparent !important;
    }

    .tracuu-splitter-theo-port-olt .e-grid td.cell-mark-selection.e-active {
        background: #F9E1A9 !important;
    }

    .tracuu-splitter-theo-port-olt .e-grid.e-gridhover tr[role='row']:not(.e-editedrow):hover .e-rowcell:not(.e-cellselectionbackground):not(.e-active):not(.e-updatedtd):not(.e-indentcell),
    .tracuu-splitter-theo-port-olt .e-grid.e-gridhover tr[role='row']:hover .e-detailrowcollapse:not(.e-cellselectionbackground):not(.e-active):not(.e-updatedtd):not(.e-indentcell),
    .tracuu-splitter-theo-port-olt .e-grid.e-gridhover tr[role='row']:hover .e-rowdragdrop:not(.e-cellselectionbackground):not(.e-active):not(.e-updatedtd):not(.e-indentcell),
    .tracuu-splitter-theo-port-olt .e-grid.e-rtl .e-gridhover tr[role='row']:hover .e-rowdragdrop:not(.e-cellselectionbackground):not(.e-active):not(.e-updatedtd):not(.e-indentcell),
    .tracuu-splitter-theo-port-olt .e-grid.e-gridhover tr[role='row']:hover .e-detailrowexpand:not(.e-cellselectionbackground):not(.e-active):not(.e-updatedtd):not(.e-indentcell),
    .tracuu-splitter-theo-port-olt .e-grid.e-gridhover .e-frozenhover {
        background-color: #FFF9EB !important;
    }

    .tracuu-splitter-theo-port-olt .e-grid td.e-rowcell { 
        line-height: 20px !important;
        padding: 5px 7px !important;
    }

    .tracuu-splitter-theo-port-olt .e-grid .e-filterdiv.e-fltrinputdiv .e-input-group.e-control-wrapper {
        border: 0 !important;
    }

    .tracuu-splitter-theo-port-olt .e-grid .e-filtertext.e-input.e-disable {
        display: none !important;
    }

    .tracuu-splitter-theo-port-olt .e-grid tr.e-emptyrow td {
        text-align: center !important;
    }

    .tracuu-splitter-theo-port-olt .e-grid .e-pagerexternalmsg,
    .tracuu-splitter-theo-port-olt .e-grid .e-sortnumber {
        display: none !important;
    }

    .tracuu-splitter-theo-port-olt .vue-treeselect__control {
        height: 32px !important;
    }

    .tracuu-splitter-theo-port-olt .vue-treeselect--disabled .vue-treeselect__control {
        background-color: #f1f1f1;
    }

    .tracuu-splitter-theo-port-olt .vue-treeselect__placeholder, 
    .tracuu-splitter-theo-port-olt .vue-treeselect__single-value {
        line-height: 30px;
    }

    .tracuu-splitter-theo-port-olt span.vue-treeselect__tip-text.vue-treeselect__no-options-tip-text {
        text-align: center;
        font-size: 14px;
        line-height: 30px;
    }

    .tracuu-splitter-theo-port-olt div.vue-treeselect__icon-container {
        display: none;
    }

    .tracuu-splitter-theo-port-olt .vue-treeselect__control-arrow {
        width: 7px;
        height: 6px;
        color: inherit;
        margin-left: 7px;
    }

    .tracuu-splitter-theo-port-olt-ejs-multiselect .e-control-wrapper .e-input-group-icon {
        border: 0;
        background-color: transparent !important;
        color: inherit !important;
        box-shadow: none;
    }
</style>