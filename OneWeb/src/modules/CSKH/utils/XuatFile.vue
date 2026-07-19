<template>
    <b-modal
    :id="id"
    size="xl"
    no-close-on-backdrop
    hide-footer
    hide-header
    hide-header-close
    body-class="modal-body p-0"
    @shown="onModalShown"
    @hidden="onModalHidden">
    <div class="modal-content popup-box">
        <div class="popup-header">
            <div class="title">Xuất dữ liệu</div>
            <div
                class="close -ap icon-close"
                data-dismiss="modal"
                @click="$bvModal.hide(id)">
            </div>
        </div>
        <div class="list-actions-top">
            <ul class="list">
                <li>
                    <a href="javascript:void(0)" @click="onClickButtonXuatFile">
                        <span class="icon one-download"></span>Xuất file
                    </a>
                </li>
                <li>
                    <a href="javascript:void(0)" @click="onClickButtonXemDuLieu">
                        <span class="icon one-list-search"></span>Xem dữ liệu
                    </a>
                </li>
            </ul>
        </div>
        <div class="popup-body">
            <div class="grid-stack-box tracuu-splitter-theo-port-olt-xuat-excel">
                <Split style="height: 700px;" :gutterSize="16" :key="key">
                    <SplitArea :size="40" :minSize="300">
                        <div class="box-col box-form" id="boxLeft">
                            <div class="legend-title">Cấu trúc bảng dữ liệu</div>
                            <div class="table-content" style="height: 600px;">
                                <table class="table-result table-gachle">
                                    <thead class="center">
                                        <tr>
                                            <th class="w20"></th>
                                            <th class="w20"></th>
                                            <th>Tên cột</th>
                                            <th class="w30"></th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr v-if="dsThuocTinh.length == 0">
                                            <td colspan="4"> Không có bản ghi nào để hiển thị</td>
                                        </tr>
                                        <tr v-for="(thuocTinh, index) in dsThuocTinh">
                                            <td class="w20 center">
                                                <span v-if="thuocTinhDuocChonIndex == index" class="fa fa-play text-main"></span>
                                            </td>
                                            <td class="w20 center">
                                                <div class="check-action">
                                                    <input type="checkbox" class="check" :checked="true">
                                                    <span class="name"></span>
                                                </div>
                                            </td>
                                            <td :class="{highlight1: thuocTinhDuocChonIndex == index, selected: true}"
                                                @click="onClickRowTableThuocTinh(index)">
                                                {{thuocTinh}}
                                            </td>
                                            <td>
                                                <button class="btn btn-danger lh14 pad3" @click="onClickButtonXoaThuocTinh(thuocTinh)">
                                                    <span class="-ap icon-close"></span>
                                                </button>
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </SplitArea>
                    <SplitArea :size="60" :minSize="500">
                        <div class="box-col box-form" id="boxRight">
                            <div class="legend-title">
                                <div class="pull-left">Xem trước dữ liệu</div>
                                <div class="pull-right">
                                    <div class="check-action normal">
                                        <input type="checkbox" class="check" v-model="isTiengVietKoDau">
                                        <span class="name">Xuất dữ liệu tiếng Việt không dấu</span>
                                    </div>
                                </div>
                                <div class="clearfix"></div>
                            </div>
                            <div class="table-content" style="height: 600px;">
                                <DataGrid2 ref='grid-data' 
                                    :dataSource="tableDuLieu.dataSource"
                                    :columns="dsThuocTinhHeader"
                                    />
                            </div>
                            <!-- <div class="table-content">
                                <ejs-grid
                                    ref='grid-data' 
                                    ref-value='grid-data'
                                    class="table-result"
                                    gridLines='Both'
                                    locale="vi-VN"
                                    :height="261"
                                    :allowSorting="true"
                                    :allowFiltering='true'
                                    :hideSpinner="true"
                                    :filterSettings="{type: 'CheckBox'}"
                                    :selectionSettings="{enableToggle: false}"
                                    :dataSource="tableDuLieu.dataSource"
                                    :allowResizing='true'
                                    :rowSelected='onRowSelected'
                                    :dataBound="onDataBound">
                                    <e-columns>
                                        <e-column headerText='' textAlign="Center" width="20" :allowResizing="false" :allowFiltering='false' :template='columnTemplate'></e-column>
                                        <e-column v-for="thuocTinh in dsThuocTinhHeader" :key="thuocTinh.name" :field='thuocTinh.name' :headerText='thuocTinh.name' headerTextAlign='Center' :textAlign='thuocTinh.textAlign' :width="thuocTinh.width" :filter="{hideSearchbox: true}"></e-column>
                                    </e-columns>
                                </ejs-grid>
                            </div>
                            <BssPagination1
                                :key="tableDuLieu.tableKey"
                                :totalRows="tableDuLieu.dataSource.length"
                                :rowIndex="tableDuLieu.selectedRowIndexOnPage"
                                @pagechange="onPageChange"
                                @rowchange="onRowChange"></BssPagination1> -->
                        </div>
                    </SplitArea>
                </Split>
            </div>
        </div>
    </div>
</b-modal>

</template>
<script>
import BssPagination1 from '@/components/BssPagination_1'
import XLSX from "xlsx"
import { Edit, Sort, Filter, ForeignKey, Resize, Freeze } from "@syncfusion/ej2-vue-grids"
import { L10n } from '@syncfusion/ej2-base'

L10n.load({
    'vi-VN': {
        grid: {
            FilterButton: 'Lọc',
            ClearButton: 'Đóng',
            SelectAll: 'Chọn tất cả'
        }
    }
})

export default {
    provide: {
        grid: [Edit, Sort, Filter, ForeignKey, Resize, Freeze ]
    },
    name: 'ModalXuatDuLieu',
    components: { BssPagination1 },
    props: [ 'id', 'thuocTinh', 'data', 'tenFileXuat' ],
    computed: {
        thuocTinhGridData() {
            return this.thuocTinh.filter(x => this.dsThuocTinh.includes(x.fieldName))
        },
        dataSourceKoDau() {
            
        }
    },
    data() {
        return {
            dsThuocTinh: [],
            dsThuocTinhHeader: [],
            thuocTinhDuocChonIndex: 0,
            key: 1,
            isTiengVietKoDau: false,
            tableDuLieu: {
                dataSource: [],
                dataSourceKoDau: [],
                pagerData: [],
                selectedRowIndexOnPage: 0,
                tableKey: 1
            },
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
        }
    },
    methods: {
        onModalShown() {

            let dsThuocTinh = []
            let dsThuocTinhHeader = []
            this.isTiengVietKoDau = false
            console.time('abc')
            this.thuocTinh.forEach(item => {
                
                let thuocTinh

                if (typeof item == 'string') {
                    thuocTinh = {
                        name: item,
                        width: 100,
                        textAlign: 'Left'
                    }
                } else {
                    thuocTinh = {...item}
                }

                dsThuocTinh.push(thuocTinh.fieldName)
                // dsThuocTinhHeader.push(thuocTinh.fieldName)
            })

            this.dsThuocTinh = dsThuocTinh
            this.dsThuocTinhHeader = this.thuocTinhGridData
            // this.tableDuLieu.dataSource = [...this.data]
            this.tableDuLieu.tableKey++
            console.timeEnd('abc')
        },
        onModalHidden() {
            this.dsThuocTinh = []
            this.dsThuocTinhHeader = []
            this.tableDuLieu.dataSource = []
            this.tableDuLieu.dataSourceKoDau = []
            ++this.key
        },
        onRowSelected(args) {
            let refValue = args.row.closest(".e-grid").getAttribute('ref-value')
            this.$refs[refValue].$el.querySelectorAll('.col-mark-selected-row').forEach(item => {
                item.style.display = args.rowIndex == parseInt(item.id) ? 'block' : 'none'
            })
        },
        onDataBound() {
            this.$refs['grid-data'].selectRow(0)
        },
        onClickButtonXuatFile() {

            if (this.dsThuocTinh.length == 0) {
                this.$toast.error('Bạn đã xóa hết các cột lấy DL!')
                return
            }

            let exportData = []

            this.data.forEach(item => {

                let data = {}

                this.dsThuocTinh.forEach(item1 => {
                    if (this.isTiengVietKoDau) {
                        if (!item[item1] || item[item1] == '')
                            data[item1] = ''
                        else 
                            data[item1] = this.xoa_dau('' + item[item1])
                    }
                    else {
                        data[item1] = item[item1] || ''
                    }
                    
                })

                exportData.push(data)
            })

            let worksheet = XLSX.utils.json_to_sheet(exportData)
            let workbook = XLSX.utils.book_new()
            XLSX.utils.book_append_sheet(workbook, worksheet, this.tenFileXuat ? this.tenFileXuat : "ds_baocao")
            XLSX.writeFile(workbook, "export.xls")
            this.$root.toastSuccess("Xuất dữ liệu thành công!")
        },
        onClickRowTableDuLieu(index) {
            this.tableDuLieu.selectedRowIndexOnPage = index
        },
        onPageChange (data) {
            let to = data.pageIndex * data.pageSize
            let from = to - data.pageSize
            this.tableDuLieu.selectedRowIndexOnPage = data.rowIndexOnPage
            this.tableDuLieu.pagerData = this.tableDuLieu.dataSource.slice(from, to)
        },
        onRowChange (rowIndexOnPage) {
            this.tableDuLieu.selectedRowIndexOnPage = rowIndexOnPage
        },
        onClickButtonXemDuLieu() {
            this.dsThuocTinhHeader = this.thuocTinhGridData
            if (this.isTiengVietKoDau) {
                let data = [...this.data]
                this.tableDuLieu.dataSource = []
                data.forEach(row => {
                    let newRow = {}
                    this.dsThuocTinh.forEach(col => {
                        newRow[col] = this.xoa_dau('' + row[col])
                    })
                    this.tableDuLieu.dataSource.push(newRow)
                })
                // this.tableDuLieu.dataSource.forEach(row => {
                //     Object.keys(row).forEach((prop) => {
                //         row[prop] = this.xoa_dau('' + row[prop])
                //     })
                // })
            }
            else {
                this.tableDuLieu.dataSource = [...this.data]
            }
            // this.dsThuocTinhHeader = this.dsThuocTinhHeader.filter(item => {
            //     return this.dsThuocTinh.indexOf(item.name) >= 0
            // })
            // this.tableDuLieu.dataSource = [...this.data]
        },
        onClickRowTableThuocTinh(index) {
            this.thuocTinhDuocChonIndex = index
        },
        onClickButtonXoaThuocTinh(thuocTinh, index) {
            
            this.dsThuocTinh = this.dsThuocTinh.filter(item => item != thuocTinh)

            if (this.thuocTinhDuocChonIndex >= this.dsThuocTinh.length) {
                this.thuocTinhDuocChonIndex = this.dsThuocTinh.length - 1
            }
        },
        xoa_dau(str) {
            try {
                str = str.replace(/à|á|ạ|ả|ã|â|ầ|ấ|ậ|ẩ|ẫ|ă|ằ|ắ|ặ|ẳ|ẵ/g, "a");
                str = str.replace(/è|é|ẹ|ẻ|ẽ|ê|ề|ế|ệ|ể|ễ/g, "e");
                str = str.replace(/ì|í|ị|ỉ|ĩ/g, "i");
                str = str.replace(/ò|ó|ọ|ỏ|õ|ô|ồ|ố|ộ|ổ|ỗ|ơ|ờ|ớ|ợ|ở|ỡ/g, "o");
                str = str.replace(/ù|ú|ụ|ủ|ũ|ư|ừ|ứ|ự|ử|ữ/g, "u");
                str = str.replace(/ỳ|ý|ỵ|ỷ|ỹ/g, "y");
                str = str.replace(/đ/g, "d");
                str = str.replace(/À|Á|Ạ|Ả|Ã|Â|Ầ|Ấ|Ậ|Ẩ|Ẫ|Ă|Ằ|Ắ|Ặ|Ẳ|Ẵ/g, "A");
                str = str.replace(/È|É|Ẹ|Ẻ|Ẽ|Ê|Ề|Ế|Ệ|Ể|Ễ/g, "E");
                str = str.replace(/Ì|Í|Ị|Ỉ|Ĩ/g, "I");
                str = str.replace(/Ò|Ó|Ọ|Ỏ|Õ|Ô|Ồ|Ố|Ộ|Ổ|Ỗ|Ơ|Ờ|Ớ|Ợ|Ở|Ỡ/g, "O");
                str = str.replace(/Ù|Ú|Ụ|Ủ|Ũ|Ư|Ừ|Ứ|Ự|Ử|Ữ/g, "U");
                str = str.replace(/Ỳ|Ý|Ỵ|Ỷ|Ỹ/g, "Y");
                str = str.replace(/Đ/g, "D");
            }
            catch (e) {

            }
            
            return str;
        }
    }
}
</script>
<style>
    .tracuu-splitter-theo-port-olt-xuat-excel .gutter {
        background-color: transparent;
    }

    .tracuu-splitter-theo-port-olt-xuat-excel .e-grid {
        font-family: "Roboto", Arial !important;
    }

    .tracuu-splitter-theo-port-olt-xuat-excel .e-grid .e-filterbarcell {
        padding: 5px 7px !important;
    }

    .tracuu-splitter-theo-port-olt-xuat-excel .e-grid th.e-headercell, 
    .tracuu-splitter-theo-port-olt-xuat-excel .e-grid th.e-filterbarcell {
        background-color: #BAE7FF !important;
        border-color: white !important;
    }

    .tracuu-splitter-theo-port-olt-xuat-excel .e-grid th.e-filterbarcell {
        border-width: 1px 0 0 1px !important;
    }

    .tracuu-splitter-theo-port-olt-xuat-excel .e-grid td.e-active {
        background: #F9E1A9 !important;
    }

    .tracuu-splitter-theo-port-olt-xuat-excel .e-grid.e-gridhover tr[role='row']:not(.e-editedrow):hover .e-rowcell:not(.e-cellselectionbackground):not(.e-active):not(.e-updatedtd):not(.e-indentcell),
    .tracuu-splitter-theo-port-olt-xuat-excel .e-grid.e-gridhover tr[role='row']:hover .e-detailrowcollapse:not(.e-cellselectionbackground):not(.e-active):not(.e-updatedtd):not(.e-indentcell),
    .tracuu-splitter-theo-port-olt-xuat-excel .e-grid.e-gridhover tr[role='row']:hover .e-rowdragdrop:not(.e-cellselectionbackground):not(.e-active):not(.e-updatedtd):not(.e-indentcell),
    .tracuu-splitter-theo-port-olt-xuat-excel .e-grid.e-rtl .e-gridhover tr[role='row']:hover .e-rowdragdrop:not(.e-cellselectionbackground):not(.e-active):not(.e-updatedtd):not(.e-indentcell),
    .tracuu-splitter-theo-port-olt-xuat-excel .e-grid.e-gridhover tr[role='row']:hover .e-detailrowexpand:not(.e-cellselectionbackground):not(.e-active):not(.e-updatedtd):not(.e-indentcell) {
        background-color: #FFF9EB !important;
    }

    .tracuu-splitter-theo-port-olt-xuat-excel .e-grid td.e-rowcell { 
        line-height: 20px !important;
        padding: 5px 7px !important;
    }

    .tracuu-splitter-theo-port-olt-xuat-excel .e-grid .e-filterdiv.e-fltrinputdiv .e-input-group.e-control-wrapper {
        border: 0 !important;
    }

    .tracuu-splitter-theo-port-olt-xuat-excel .e-grid .e-filtertext.e-input.e-disable {
        display: none !important;
    }

    .tracuu-splitter-theo-port-olt-xuat-excel .e-grid tr.e-emptyrow td {
        text-align: center !important;
    }

    .tracuu-splitter-theo-port-olt-xuat-excel .e-grid .e-gridheader .e-sortfilter .e-fltr-icon .e-headercelldiv {
        margin-right: 0 !important;
    }

    .e-checkboxfilter .e-checkbox-wrapper .e-frame.e-check, .e-css.e-checkbox-wrapper .e-frame.e-check {
        background-color: white !important;
        padding-top: 2px !important;
    }
</style>>
