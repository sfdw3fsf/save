<template src="./index.template.html"></template>
<script>
import XLSX from "xlsx"
import { Edit, Sort, Filter, ForeignKey, Resize, Freeze, Page, Group } from "@syncfusion/ej2-vue-grids"
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
        grid: [Edit, Sort, Filter, ForeignKey, Resize, Freeze, Page, Group ]
    },
    data() {
        return {
            parameters: {
                kieu: null,
                suCoId: null,
                thamSo: null
            },
            dsThueBao: [],
            columnTemplate: function () {
                return { 
                    template: {
                        template: '<span :id="data.index" :style="{display: display }" style="font-size: 12px;" class="col-mark-selected-row fa fa-play text-main"></span>',
                        data() {
                            return { 
                                data: { } 
                            }
                        },
                        computed: {
                            display() {
                            return parseInt(this.data.index) == this.$parent.getSelectedRowIndexes()[0] ? 'block' : 'none'
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
        }
    },
    methods: {
        show() {
            return this.$refs['ref-modal'].show()
        },
        hide() {
            this.$refs['ref-modal'].hide()
        },
        setKieu(kieu) {
            this.parameters.kieu = kieu
        },
        setSuCoId(suCoId) {
            this.parameters.suCoId = suCoId
        },
        setThamSo(thamSo) {
            this.parameters.thamSo = thamSo
        },
        onModalHidden() {
            this.parameters.kieu = null
            this.parameters.suCoId = null
            this.parameters.thamSo = null
            this.dsThueBao = []
        },
        onModalShown() {
            this.onClickBtnLamMoi()
        },
        onDataBound(refGrid) {
            return () => {
                let grid = this.$refs[refGrid]

                if (grid.getDataRows().length > 0) {

                    if (grid.isFrozenGrid()) {
                        let cells = grid.getMovableRows()[0].querySelectorAll('.e-rowcell')
                        cells.forEach(item => item.classList.add('e-active'))
                    }

                    let firstRow = grid.getRowByIndex(0)
                    let cells = firstRow.querySelectorAll('.e-rowcell')
                    cells.forEach(item => item.classList.add('e-active'))
                    this.onRowSelected(grid.getRowInfo(firstRow))
                } else if (grid.isFrozenGrid()) {
                    let visibleColumnCount = grid.getVisibleColumns().length
                    let emptyRows = grid.$el.querySelectorAll('.e-emptyrow')
                    let cell0 = emptyRows[0].cells[0]
                    let cell1 = emptyRows[1].cells[0]
                    cell0.style.color = 'white'
                    cell1.innerHTML = cell0.innerHTML
                    cell0.innerHTML = 'x'
                    cell1.setAttribute('colspan', visibleColumnCount - grid.getFrozenLeftCount())
                    cell1.style.setProperty('text-align', 'left', 'important')
                }
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
        onRowSelecting(args) {
            let refValue = args.row.closest(".e-grid").getAttribute('ref-value')
            this.$refs[refValue].$el.querySelectorAll('.e-rowcell').forEach(item => {
                item.classList.remove('e-active')
            })
        },
        onRowSelected(args) {
            let refValue = args.row.closest(".e-grid").getAttribute('ref-value')
            this.$refs[refValue].$el.querySelectorAll('.col-mark-selected-row').forEach(item => {
                item.style.display = args.rowIndex == parseInt(item.id) ? 'block' : 'none'
            })
        },
        onClickBtnXuatExcel() {

            let exportData = []

            this.dsThueBao.forEach(item => {
                exportData.push({
                    'Số máy / ACC': item.MA_TB,
                    'Đầu cuối': item.DAUCUOI,
                    'Tên thuê bao': item.TEN_TB,
                    'Địa chỉ lắp đặt': item.DIACHI_LD,
                    'Số điện thoại': item.SO_DT,
                    'Dịch vụ': item.DICHVU_VT,
                    'Loại hình': item.LOAIHINH_TB,
                    'Tủ cáp gốc': null,
                    'Cáp gốc': null,
                    'Đôi cáp gốc': null,
                    'Tủ cáp phối': null,
                    'Cáp phối': null,
                    'Đôi cáp phối': null,
                })
            })

            let worksheet = XLSX.utils.json_to_sheet(exportData)
            let workbook = XLSX.utils.book_new()
            XLSX.utils.book_append_sheet(workbook, worksheet, "Sheet1")
            XLSX.writeFile(workbook, "export.xlsx")
        },
        async onClickBtnLamMoi() {
            try {
                this.loading(true)

                switch (this.parameters.kieu) {
                    case 0:
                        await this.layDsThueBaoTheoLoaiHinh({
                            suco_id: this.parameters.suCoId,
                            loaitb_id: this.parameters.thamSo
                        })
                        break
                    case 1: 
                        await this.layDsThueBaoTrenCong(this.parameters.thamSo)
                        break
                    case 2:
                        await this.layDsThueBaoTheoDichVu({
                            suco_id: this.parameters.suCoId,
                            dvvt_id: this.parameters.thamSo
                        })
                        break
                    case 3:
                        await this.layDsThueBaoTheoPtm({
                            suco_id: this.parameters.suCoId,
                            loaiptm_id: this.parameters.thamSo[0],
                            ptm_id: this.parameters.thamSo[1]
                        })
                        break
                    default:
                        this.dsThueBao = []
                }
            } catch(err) {

            } finally {
                this.loading(false)
            }
        },
        async layDsThueBaoTheoLoaiHinh(data) {
            try {
                let response = await this.$root.context.post(`/web-suco/quanlysuco/layDsThueBaoTheoLoaiHinh`, data)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    this.dsThueBao = response.data
                    return
                } 
            
                throw {response: { data: response}}
            } catch (err) {
                this.dsThueBao = []
            }
        },
        async layDsThueBaoTrenCong(rscId) {
            try {
                let response = await this.$root.context.post(`/web-suco/quanlysuco/layDsThueBaoTrenCong`, {id: rscId})

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    this.dsThueBao = response.data
                    return
                } 
            
                throw {response: { data: response}}
            } catch (err) {
                this.dsThueBao = []
            }
        },
        async layDsThueBaoTheoDichVu(data) {
            try {
                let response = await this.$root.context.post(`/web-suco/quanlysuco/layDsThueBaoTheoDichVu`, data)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    this.dsThueBao = response.data
                    return
                } 
            
                throw {response: { data: response}}
            } catch (err) {
                this.dsThueBao = []
            }
        },
        async layDsThueBaoTheoPtm(data) {
            try {
                let response = await this.$root.context.post(`/web-suco/quanlysuco/layDsThueBaoTheoPtm`, data)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    this.dsThueBao = response.data
                    return
                } 
            
                throw {response: { data: response}}
            } catch (err) {
                this.dsThueBao = []
            }
        },
     }
}
</script>
<style>
    .tnsc-danhsach-thuebao .e-grid {
        font-family: "Roboto", Arial !important;
    }

    .tnsc-danhsach-thuebao .e-grid .e-filterbarcell {
        padding: 5px 7px !important;
        border-width: 1px 0 0 1px !important;
    }

    .tnsc-danhsach-thuebao .e-grid th.e-headercell,
    .tnsc-danhsach-thuebao .e-grid .e-gridcontent td.e-rowcell {
        padding: 5px 7px !important;
        height: 30px !important;
    }

    .tnsc-danhsach-thuebao .e-grid th.e-grouptopleftcell,
    .tnsc-danhsach-thuebao .e-grid th.e-headercell, 
    .tnsc-danhsach-thuebao .e-grid th.e-filterbarcell {
        background-color: #BAE7FF !important;
        border-color: white !important;
    }

    .tnsc-danhsach-thuebao .e-grid th.e-filterbarcell > div input.form-control {
        padding-bottom: 0 !important;
        padding-top: 0 !important;
        height: 26px !important;
    }

    .tnsc-danhsach-thuebao .e-grid td.e-active {
        background: #F9E1A9 !important;
    }

    .tnsc-danhsach-thuebao .e-grid.e-gridhover tr[role='row']:not(.e-editedrow):hover .e-rowcell:not(.e-cellselectionbackground):not(.e-active):not(.e-updatedtd):not(.e-indentcell),
    .tnsc-danhsach-thuebao .e-grid.e-gridhover tr[role='row']:hover .e-detailrowcollapse:not(.e-cellselectionbackground):not(.e-active):not(.e-updatedtd):not(.e-indentcell),
    .tnsc-danhsach-thuebao .e-grid.e-gridhover tr[role='row']:hover .e-rowdragdrop:not(.e-cellselectionbackground):not(.e-active):not(.e-updatedtd):not(.e-indentcell),
    .tnsc-danhsach-thuebao .e-grid.e-rtl .e-gridhover tr[role='row']:hover .e-rowdragdrop:not(.e-cellselectionbackground):not(.e-active):not(.e-updatedtd):not(.e-indentcell),
    .tnsc-danhsach-thuebao .e-grid.e-gridhover tr[role='row']:hover .e-detailrowexpand:not(.e-cellselectionbackground):not(.e-active):not(.e-updatedtd):not(.e-indentcell) {
        background-color: #FFF9EB !important;
    }

    .tnsc-danhsach-thuebao .e-grid td.e-rowcell { 
        line-height: 20px !important;
        padding: 5px 7px !important;
    }

    .tnsc-danhsach-thuebao .e-grid .e-filterdiv.e-fltrinputdiv .e-input-group.e-control-wrapper {
        border: 0 !important;
    }

    .tnsc-danhsach-thuebao .e-grid .e-filtertext.e-input.e-disable {
        display: none !important;
    }

    .tnsc-danhsach-thuebao .e-grid tr.e-emptyrow td {
        text-align: center !important;
    }

    .tnsc-danhsach-thuebao .e-grid .e-pagerexternalmsg,
    .tnsc-danhsach-thuebao .e-grid .e-sortnumber {
        display: none !important;
    }
</style>
