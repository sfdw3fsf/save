<template src="./index.template.html"></template>
<script>
import { L10n } from '@syncfusion/ej2-base'
import { CheckBoxSelection } from "@syncfusion/ej2-vue-dropdowns"
import ModalCongSoiBiSuCo from '../khoa-phieu/chon-congsoi/index.vue'
import ModalXemThongTin from '../capnhat-ketqua-xuly-suco/modal-xem-thongtin/index.vue'
import XLSX from "xlsx"
import { Edit, Sort, Filter, ForeignKey, Resize, Freeze, Group } from "@syncfusion/ej2-vue-grids"
import BssErrorMarker from '@/components/BssErrorMarker'
import BssRequiredMarker from '@/components/BssRequiredMarker'
import { required } from 'vuelidate/lib/validators'
import ModalInSuCo from './modal-in-suco/index.vue'
import ModalInPhieuPreview from './modal-in-phieu-preview/index.vue'
import ModalKhoaPhieu from '../khoa-phieu/index.vue'
import Pagination from "@/components/Controls/Pagination"
import ComboMultiSelection from '../khoa-phieu/tab-item/ComboMultiSelection'

let getDateElements = (date) => {

    date = new Date(date)

    let day = date.getDate()
    day = day > 9 ? day : '0' + day

    let month = date.getMonth() + 1
    month = month > 9 ? month : '0' + month

    let hour = date.getHours()
    hour = hour > 9 ? hour : '0' + hour

    let min = date.getMinutes()
    min = min > 9 ? min : '0' + min

    let second = date.getSeconds()
    second = second > 9 ? second : '0' + second

    return {
        y: date.getFullYear(),
        M: month,
        d: day,
        h: hour,
        m: min,
        s: second
    }
}

let ddmmyyyy = (date) => {

    if (!date) {
        return ''
    }

    date = getDateElements(date)
    return `${date.d}/${date.M}/${date.y}`
}

let convertSecondToTime = function (seconds) {
    let hours = Math.floor(seconds / 3600)
    let minutes = Math.floor((seconds - (hours * 3600)) / 60)
    seconds = seconds - (hours * 3600) - (minutes * 60)

    if (hours < 10) { hours = "0" + hours }
    if (minutes < 10) { minutes = "0" + minutes }
    if (seconds < 10) { seconds = "0" + seconds }
    return hours + ':' + minutes + ':' + seconds
}

const colorCode = {
    black: 'Black',
    blue: 'Blue',
    darkOrange: 'DarkOrange',
    green: 'Green',
    darkRed: 'DarkRed',
    darkGray: 'DarkGray'
}

export default {
    provide: {
        multiselect: [CheckBoxSelection],
        grid: [Edit, Sort, Filter, ForeignKey, Resize, Freeze, Group]
    },
    components: {
        ModalCongSoiBiSuCo,
        ModalXemThongTin,
        BssErrorMarker,
        BssRequiredMarker,
        ModalInSuCo,
        ModalInPhieuPreview,
        ModalKhoaPhieu,
        Pagination,
        ComboMultiSelection
    },
    validations: {
        noiDungGiao: {
            required
        },
        chiTieuGio: {
            required
        }
    },
    data() {
        return {
            chkNguonSuCo: false,
            dsNguonSuCo: [],
            nguonSuCoId: null,

            dsMucDoSuCo: [],
            mucDoSuCoId: null,

            dsMucDoKhanCap: [],
            mucDoKhanCapId: null,

            chkNgaySuCo: true,
            ngaySuCo: new Date(),
            labelNgaySuCo: 'Ngày sự cố',

            chkQuyTrinh: false,
            dsQuyTrinh: [],
            quyTrinhId: null,

            dsThaoTac: [],
            thaoTacId: null,

            chkKeoDai: false,
            keoDaiGio: 0,
            keoDaiPhut: 0,
            keoDaiGiay: 0,

            dsHuongGiao: [],
            huongGiaoId: null,

            dsDonVi: [],
            donViId: null,
            dsDonViId: [],
            dsDonViIdError: false,
            labelDonVi: 'Đơn vị giao bổ sung',

            dsSuCo: [],
            dsPhieu: [],
            tablePhieu: {
                dataSource: [],
                paginationKey: 1,
                pagerData: [],
                pageSize: 10
            },

            chiTieuGio: 0,
            maSc: null,
            noiDungGiao: null,

            idSuCoDuocChon: null,
            suCoDuocChon: null,

            idPhieuDuocChon: null,
            dsIdSuCoPtmDuocChon: [],
            phieuDuocChon: null,
            checkAllPhieu: false,

            btnGiaoPhieuEnabled: false,
            btnHuyGiaoEnabled: false,
            btnCapNhatEnabled: false,
            btnKhoaPhieuEnabled: false,

            allowFilteringGridSuCo: true,
            allowFilteringGridPhieu: true,
            kieuNhomPhieu: 1,
            visibleChonMotPhieu: true,

            columnTemplateChiTiet: function () {
                return {
                    template: {
                        template: `<button class="btn btn-sm btn-second" @click="parent.showCongSoiBiSuCo(data)">...</button>`,
                        data() {
                            return {
                                data: {}
                            }
                        },
                        computed: {
                            parent() {
                                return this.$parent.$parent
                            }
                        },
                    }
                }
            },
            headerTemplateChonNhieuPhieu: function () {
                return {
                    template: {
                        template: `
                            <div class="check-action">
                                <input type="checkbox" v-model="parent.checkAllPhieu" class="check"/>
                                <span class="name"></span>
                            </div>
                        `,
                        data: function () {
                            return {}
                        },
                        computed: {
                            parent() {
                                return this.$parent.$parent
                            }
                        }
                    }
                }
            },
            columnTemplateChonNhieuPhieu: function () {
                return {
                    template: {
                        template: `
                            <div class="check-action">
                                <input
                                    type="checkbox"
                                    class="check"
                                    v-model="parent.dsIdSuCoPtmDuocChon"
                                    :value="value"/>
                                <span class="name"></span>
                            </div>
                        `,
                        data() {
                            return {
                                data: {}
                            }
                        },
                        computed: {
                            parent() {
                                return this.$parent.$parent
                            },
                            value() {
                                return `${this.data.SUCO_PTM_ID}_${this.data.DONVI_ID}_${this.data.KEY}`
                            }
                        }
                    }
                }
            },
            columnTemplateChonMotPhieu: function () {
                return {
                    template: {
                        template: `
                            <div>
                                <div v-if="show" class="check-action" @click="onClick">
                                    <input
                                        type="radio"
                                        class="check"
                                        v-model="parent.idPhieuDuocChon"
                                        :value="value"/>
                                    <span class="name"></span>
                                </div>
                            </div>
                        `,
                        data() {
                            return {
                                data: {}
                            }
                        },
                        computed: {
                            parent() {
                                return this.$parent.$parent
                            },
                            show() {
                                let thaoTacId = parseInt(this.parent.thaoTacId)
                                return !([4, 5].indexOf(this.data.TTPH_ID) >= 0 && thaoTacId == 2)
                            },
                            isSelected() {
                                return this.parent.idPhieuDuocChon == this.value
                            },
                            value() {
                                let phieuId = !this.data.PHIEU_ID ? '' : this.data.PHIEU_ID
                                return `${phieuId}_${this.data.KEY}`
                            }
                        },
                        methods: {
                            onClick() {
                                if (this.isSelected) {
                                    this.parent.idPhieuDuocChon = null
                                    this.parent.phieuDuocChon = null
                                } else {
                                    this.parent.idPhieuDuocChon = this.value
                                    this.parent.phieuDuocChon = this.data
                                }
                            }
                        }
                    }
                }
            },
            columnTemplate: function () {
                return {
                    template: {
                        template: `<span :id="data.index" :style="{display: display }" style="font-size: 12px;" class="col-mark-selected-row fa fa-play text-main"></span>`,
                        data() {
                            return {
                                data: {}
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
            groupSettingsGridPhieu: {
                showDropArea: false,
                columns: ['PHIEU_ID'],
                captionTemplate: '${if(field == "PHIEU_ID")} ${if(!key)} Phiếu ${else} Phiếu ${key} ${/if} ${else} ${key} ${/if}'
            }
        }
    },
    computed: {
        btnGiaoPhieuVisible() {
            return [1, 3].indexOf(parseInt(this.thaoTacId)) >= 0
        },
        btnCapNhatVisible() {
            return parseInt(this.thaoTacId) == 2
        },
        btnHuyGiaoVisible() {
            return [2, 3].indexOf(parseInt(this.thaoTacId)) >= 0
        },
        canhBaoDt() {
            return !this.suCoDuocChon ? '' : this.numberFormat(this.suCoDuocChon.CANHBAO_DT)
        }
    },
    watch: {
        dsPhieu(newVal) {
            this.tablePhieu.dataSource = [...newVal]
            this.tablePhieu.paginationKey++
            this.onPageChangedTablePhieu({ pageIndex: 0, pageSize: this.tablePhieu.pageSize })
        },
        dsDonViId(newVal) {
            if (!!newVal && newVal.length > 0) {
                this.dsDonViIdError = false
            }
        },
        kieuNhomPhieu(newVal) {

            let grid = this.$refs['grid-phieu']

            if (parseInt(newVal) == 1) {
                grid.ungroupColumn('TINHTRANG')
                grid.groupColumn('PHIEU_ID')
                return
            }

            grid.ungroupColumn('PHIEU_ID')
            grid.groupColumn('TINHTRANG')
        },
        phieuDuocChon(phieu) {
            if (!!phieu) {
                this.donViId = null
                this.dsDonViId = []
                this.chiTieuGio = !phieu.CHITIEU_TG ? 0 : phieu.CHITIEU_TG
                this.mucDoKhanCapId = phieu.KHAN
            }
        },
        suCoDuocChon(suCoDuocChon) {
            this.napThongTinPhieu(suCoDuocChon)
        },
        async quyTrinhId(newVal) {

            if (!newVal) {
                this.dsHuongGiao = []
                this.huongGiaoId = null
                return
            }

            try {
                this.loading(true)
                let quyTrinhId = !this.chkQuyTrinh ? this.quyTrinhId : newVal
                await this.layDmHuongGiao(quyTrinhId)
            } catch (err) {

            } finally {
                this.loading(false)
            }

            if (this.dsHuongGiao && this.dsHuongGiao.length > 0) {
                this.huongGiaoId = this.dsHuongGiao[0].HUONGGIAO_ID
            }
        },
        async chkQuyTrinh(newVal) {

            if (!this.quyTrinhId) {
                this.dsHuongGiao = []
                this.huongGiaoId = null
                return
            }

            try {
                this.loading(true)
                let quyTrinhId = !newVal ? this.quyTrinhId : this.quyTrinhId
                await this.layDmHuongGiao(quyTrinhId)
            } catch (err) {

            } finally {
                this.loading(false)
            }

            if (this.dsHuongGiao && this.dsHuongGiao.length > 0) {
                this.huongGiaoId = this.dsHuongGiao[0].HUONGGIAO_ID
            }
        },
        async huongGiaoId(newVal) {

            if (!newVal) {
                this.dsDonVi = []
                this.donViId = null
                this.dsDonViId = []
                return
            }

            try {
                this.loading(true)
                await this.layDmDonVi(newVal)
                await this.onClickBtnTimKiem()
            } catch (err) {

            } finally {
                this.loading(false)
            }
        },
        thaoTacId(newVal) {
            switch (parseInt(newVal)) {
                case 1:
                case 3:
                    this.labelNgaySuCo = "Ngày sự cố"
                    this.labelDonVi = "Đơn vị giao bổ sung"
                    this.visibleChonMotPhieu = false
                    break
                case 2:
                    this.labelNgaySuCo = "Ngày giao"
                    this.labelDonVi = "Đơn vị giao thay thế"
                    this.visibleChonMotPhieu = true
                    break
            }

            this.onClickBtnTimKiem()
        },
        checkAllPhieu(newVal) {

            if (newVal) {
                this.dsIdSuCoPtmDuocChon = this.dsPhieu.map(item => `${item.SUCO_PTM_ID}_${item.DONVI_ID}_${item.KEY}`)
                return
            }

            if (this.dsIdSuCoPtmDuocChon.length > 0 && this.dsIdSuCoPtmDuocChon.length == this.dsPhieu.length) {
                this.dsIdSuCoPtmDuocChon = []
            }
        },
        dsIdSuCoPtmDuocChon(newVal) {
            this.checkAllPhieu = newVal.length > 0 && newVal.length == this.dsPhieu.length
        },
    },
    async mounted() {

        L10n.load({
            'vi-VN': {
                'multi-select': {
                    overflowCountTemplate: "và ${count} giá trị khác"
                },
                grid: {
                    EmptyRecord: 'Không có bản ghi nào để hiển thị'
                }
            }
        })

        try {
            this.loading(true)
            let loading1 = this.layDmNguonSuCo()
            let loading2 = this.layDmMucDoSuCo()
            let loading3 = this.layDmMucDoKhanCap()
            let loading4 = this.layDmQuyTrinh()
            let loading5 = this.layDmThaoTac()
            await loading1
            await loading2
            await loading3
            await loading4
            await loading5
        } catch (err) {

        } finally {
            this.loading(false)
        }

        if (this.dsNguonSuCo && this.dsNguonSuCo.length > 0) {
            this.nguonSuCoId = this.dsNguonSuCo[0].NGUONSC_ID
        }

        if (this.dsMucDoSuCo && this.dsMucDoSuCo.length > 0) {
            this.mucDoSuCoId = this.dsMucDoSuCo[0].MUCDO_ID
        }

        if (this.dsMucDoKhanCap && this.dsMucDoKhanCap.length > 0) {
            this.mucDoKhanCapId = this.dsMucDoKhanCap[0].MUCDO_ID
        }

        if (this.dsQuyTrinh && this.dsQuyTrinh.length > 0) {
            this.quyTrinhId = this.dsQuyTrinh[0].QUYTRINH_ID
        }

        if (this.dsThaoTac && this.dsThaoTac.length > 0) {
            this.thaoTacId = this.dsThaoTac[0].TRANGTHAI_ID
        }

        setInterval(() => {

            let gridSuCo = this.$refs['grid-suco']

            if (!gridSuCo || !gridSuCo.dataSource || gridSuCo.dataSource.length == 0) {
                return
            }
         
            gridSuCo.dataSource.forEach(item => {
                let time = convertSecondToTime(++item.SECOND)
                gridSuCo.setCellValue(item.KEY, 'THOIGIAN', time)
            })
        }, 1000)
    },
    methods: {
        getColumnTemplateChon(parent) {
            return function () {
                return {
                    template: {
                        template: `
                            <div class="check-action" @click="onClick()">
                                <input
                                    type="radio"
                                    class="check"
                                    v-model="parent.idSuCoDuocChon"
                                    :value="data.SUCO_ID"/>
                                <span class="name"></span>
                            </div>
                        `,
                        data() {
                            return {
                                parent: parent,
                                data: {}
                            }
                        },
                        computed: {
                            isSelected() {
                                return this.parent.idSuCoDuocChon == this.data.SUCO_ID
                            }
                        },
                        methods: {
                            onClick() {
                                if (this.isSelected) {
                                    this.parent.idSuCoDuocChon = null
                                    this.parent.suCoDuocChon = null
                                } else {
                                    this.parent.idSuCoDuocChon = this.data.SUCO_ID
                                    this.parent.suCoDuocChon = this.data
                                }
                            }
                        }
                    }
                }
            }
        },
        onPageChangedTablePhieu(args) {

            this.tablePhieu.pageSize = args.pageSize

            let pageIndex = args.pageIndex + 1

            if (pageIndex > 0) {
                let to = pageIndex * args.pageSize
                let from = to - args.pageSize
                this.tablePhieu.pagerData = this.tablePhieu.dataSource.slice(from, to)
            } else {
                this.tablePhieu.pagerData = []
            }
        },
        numberFormat(number) {

            if (!number) {
                return ''
            }

            number = `${number}`
            let group = []

            while (number.length > 3) {
                group.push(number.substr(number.length - 3))
                number = number.substr(0, number.length - 3)
            }

            if (number.length > 0) {
                group.push(number)
            }

            return group.reverse().join()
        },
        onQueryCellInfoPhieu(args) {

            if (!args.column.field || !args.data) {
                return
            }

            if (args.column.field == 'TRANGTHAI_PH') {

                let color = null

                switch (args.data.TTPH_ID) {
                    case 1:
                        color = colorCode.black
                        break
                    case 2:
                        color = colorCode.blue
                        break
                    case 3:
                        color = colorCode.darkOrange
                        break
                    case 4:
                        color = colorCode.green
                        break
                    case 5:
                        color = colorCode.darkRed
                        break
                    default:
                        color = colorCode.darkGray
                }

                args.cell.style.setProperty('color', color, 'important')
            }

            if (args.column.field == 'CONLAI' && args.data.CONLAI < 0) {
                args.cell.style.setProperty('color', colorCode.darkRed, 'important')
                args.cell.style.setProperty('font-weight', 'bold', 'important')
            }
        },
        onDataBound(ref) {
            return () => {

                let grid = this.$refs[ref]

                if (!!grid.ej2Instances.focusModule) {
                    grid.ej2Instances.focusModule.destroy()
                }

                let divEmpty = grid.$el.querySelector('.bss-customize-empty')

                if (!!divEmpty) {
                    divEmpty.remove()
                }

                if (grid.getDataRows().length > 0) {
                    grid.$el.querySelector('.e-content').style.visibility = 'visible'
                    grid.selectRow(0)
                } else {
                    let eGridContent = grid.$el.querySelector('.e-gridcontent')
                    eGridContent.style.position = 'relative'
                    let eEmptyRow = eGridContent.querySelector('.e-emptyrow')
                    eEmptyRow.style.visibility = 'hidden'
                    let divEmpty = document.createElement('div')
                    divEmpty.classList.add('bss-customize-empty')
                    divEmpty.style.textAlign = 'center'
                    divEmpty.style.borderBottom = '1px solid lightgray'
                    divEmpty.style.padding = '10px'
                    divEmpty.style.lineHeight = '21px'
                    divEmpty.style.position = 'absolute'
                    divEmpty.style.top = '0'
                    divEmpty.style.width = '100%'
                    divEmpty.style.color = '#a5a5a5'
                    divEmpty.innerHTML = 'Không có bản ghi nào để hiển thị'
                    grid.$el.querySelector('.e-gridcontent').append(divEmpty)
                }

                this.$refs[ref].$el.querySelector('.e-gridcontent').querySelectorAll('.e-indentcell').forEach(item => {

                    item.classList.add('tree-first', 'bg-white')

                    let div1 = document.createElement('div')
                    div1.classList.add('tree-line')

                    let div2 = document.createElement('div')
                    div2.classList.add('tree-indent')

                    item.append(div1)
                    item.append(div2)
                })
            }
        },
        onQueryCellInfoSuCo(args) {
            if (!!args.column.field) {
                args.cell.style.setProperty('background-color', `#${args.data.MAUNEN}`, 'important')
                args.cell.style.color = `#${args.data.MAUCHU}`
                args.cell.title = args.cell.innerHTML
            }
        },
        onClickBtnXuatExcelPhieu() {

            let exportData = []

            this.dsPhieu.forEach(item => {
                exportData.push({
                    'Tình trạng': item.TINHTRANG,
                    'Phiếu số': item.PHIEU_ID,
                    'Đơn vị nhận': item.DONVI_NHAN,
                    'Ngày giao': item.NGAYGIAO,
                    'Nội dung giao': item.ND_GIAO,
                    'Khẩn': item.KHAN_HIENTHI,
                    'Chỉ tiêu (giờ)': item.CHITIEU_TG,
                    'Còn lại': item.CONLAI,
                    'Trạng thái phiếu': item.TRANGTHAI_PH,
                    'Ngày trả': item.NGAY_TRA,
                    'Lý do trả': item.LYDOTRA,
                    'Nội dung trả': item.ND_TRA,
                    'Ngày hoàn thành': item.NGAY_TH,
                    'Người hoàn thành': item.NGUOI_TH,
                    'Ghi chú xử lý': item.GHICHU_TH
                })
            })

            if (exportData.length == 0) {
                this.$root.toastError('Không có dữ liệu để xuất')
                return
            }

            let worksheet = XLSX.utils.json_to_sheet(exportData)
            let workbook = XLSX.utils.book_new()
            XLSX.utils.book_append_sheet(workbook, worksheet, "Sheet1")
            XLSX.writeFile(workbook, "export.xlsx")
        },
        onClickBtnXuatExcelSuCo() {
            let exportData = []

            this.dsSuCo.forEach(item => {
                exportData.push({
                    'Nhóm': item.NHOM_SC,
                    'Mã sự cố': item.MA_SC,
                    'Nội dung sự cố': item.NOIDUNG_SC_HIENTHI,
                    'Mức độ': item.MUCDO_SC,
                    'Ngày sự cố': item.NGAY_SC,
                    'Kéo dài': item.THOIGIAN,
                    'Trạng thái': item.TRANGTHAI_SC
                })
            })

            if (exportData.length == 0) {
                this.$root.toastError('Không có dữ liệu để xuất')
                return
            }

            let worksheet = XLSX.utils.json_to_sheet(exportData)
            let workbook = XLSX.utils.book_new()
            XLSX.utils.book_append_sheet(workbook, worksheet, "Sheet1")
            XLSX.writeFile(workbook, "export.xlsx")
        },
        onClickBtnInPhieu() {

            if (!this.dsPhieu || this.dsPhieu.length == 0) {
                this.$root.toastError('Không có dữ liệu để in')
                return
            }

            let modal = this.$refs['ref-modal-in-phieu-preview']
            modal.setData({
                dsPhieu: this.dsPhieu,
                kieuNhomPhieu: this.kieuNhomPhieu,
            })
            modal.show()
        },
        onClickBtnInSuCo() {

            if (!this.dsSuCo || this.dsSuCo.length == 0) {
                this.$root.toastError('Không có dữ liệu để in')
                return
            }

            this.$refs['ref-modal-in-suco'].setData({
                dsSuCo: this.dsSuCo,
                suCoId: this.idSuCoDuocChon
            })

            this.$bvModal.show('modal-in-suco')
        },
        onClickBtnKhoaPhieu() {

            if (!this.btnKhoaPhieuEnabled) {
                return
            }

            let suCo = this.suCoDuocChon

            if (suCo.NHOMSC_ID != 1) {
                this.$root.toastError(`Nhóm sự cố ${suCo.NHOM_SC} chưa được hỗ trợ`)
                return
            }

            let modal = this.$refs['ref-modal-khoaphieu']
            modal.setNhomScId(suCo.NHOMSC_ID)
            modal.setNhomScText(suCo.NHOM_SC)
            modal.setSuCoId(suCo.SUCO_ID)
            modal.setNguonScId(suCo.NGUONSC_ID)
            modal.setKhoaPhieu(true)
            this.$bvModal.show('modal-khoaphieu')
        },
        async okHuyGiaoPhieu() {
            let dsKey = []
            let thaoTacId = parseInt(this.thaoTacId)

            if (thaoTacId == 2) {
                let key = this.idPhieuDuocChon.split('_')[1]
                dsKey.push(key)
            }

            if (thaoTacId == 3) {
                this.dsIdSuCoPtmDuocChon.forEach(item => {
                    let key = item.split('_')[2]
                    dsKey.push(key)
                })
            }

            let doc = document.implementation.createDocument("", "", null)
            let root = doc.createElement("root")

            let dsPhieuDuoChon = this.dsPhieu.filter(item => dsKey.indexOf(`${item.KEY}`) >= 0)
            let dsPhieuChuaGiao = dsPhieuDuoChon.filter(item => !item.PHIEU_ID)

            if (dsPhieuChuaGiao.length > 0) {
                this.$root.toastError('Tồn tại phiếu chưa được giao nên không thể hủy giao')
                return
            }

            dsPhieuDuoChon.forEach(item => {
                let scp = doc.createElement("scp")
                scp.setAttribute("phieu_id", item.PHIEU_ID)
                scp.setAttribute("suco_ptm_id", item.SUCO_PTM_ID)
                root.appendChild(scp)
            })

            doc.appendChild(root)

            let sucoPtm = (new XMLSerializer()).serializeToString(doc)

            let data = {
                suco_id: this.suCoDuocChon.SUCO_ID,
                suco_ptm: sucoPtm
            }

            try {
                this.loading(true)
                await this.huyPhieu(data)
                this.$root.toastSuccess('Hủy giao phiếu thành công')
            } catch (err) {
                this.$root.toastError(err)
                return
            } finally {
                this.loading(false)
            }

            this.napThongTinPhieu(this.suCoDuocChon)
        },
        onClickBtnHuy() {

            if (!this.btnHuyGiaoEnabled) {
                return
            }
            if (this.suCoDuocChon.NHOMSC_ID != 0) {
                if (!this.idPhieuDuocChon
                    && (!this.dsIdSuCoPtmDuocChon || this.dsIdSuCoPtmDuocChon.length == 0)) {
                    this.$root.toastError('Hãy chọn ít nhất 1 phiếu để hủy giao')
                    return
                }
            }
            this.$bvModal.show('modal-xacnhan-huy-giaophieu-xuly')
        },
        async onClickBtnCapNhat() {

            if (!this.btnCapNhatEnabled) {
                return
            }

            if (!this.idPhieuDuocChon) {
                this.$root.toastError('Hãy chọn phiếu để cập nhật')
                return
            }

            let idPhieu = this.idPhieuDuocChon.split('_')[0]

            if (!idPhieu) {
                this.$root.toastError('Phiếu chưa giao đi không thể cập nhật')
                return
            }

            let errorMessage = []

            this.$v.$touch()

            if (this.$v.$invalid) {

                if (!this.$v.noiDungGiao.required) {
                    errorMessage.push("Hãy nhập nội dung giao")
                }

                if (!this.$v.chiTieuGio.required) {
                    errorMessage.push("Hãy nhập chỉ tiêu thời gian")
                }
            }

            if (parseFloat(this.chiTieuGio) <= 0) {
                errorMessage.push("Chỉ tiêu thời gian phải lớn hơn 0")
            }

            if (errorMessage.length > 0) {
                this.$root.toastError(errorMessage.join('\n'))
                return
            }

            let data = {
                phieu_id: idPhieu,
                donvi_nhan_id: this.donViId,
                nd_giao: this.noiDungGiao,
                khan: this.mucDoKhanCapId,
                chitieu_tg: this.chiTieuGio
            }

            try {
                this.loading(true)
                await this.capNhatPhieu(data)
                this.$root.toastSuccess('Cập nhật phiếu thành công')
            } catch (err) {
                this.$root.toastError(err)
                return
            } finally {
                this.loading(false)
            }

            this.napThongTinPhieu(this.suCoDuocChon)
        },
        async onClickBtnGiaoPhieu() {

            if (!this.btnGiaoPhieuEnabled) {
                return
            }

            let errorMessage = []

            if (this.suCoDuocChon.NHOMSC_ID != 0) {
                if (!this.dsIdSuCoPtmDuocChon || this.dsIdSuCoPtmDuocChon.length == 0) {
                    errorMessage.push("Hãy chọn ít nhất 1 phiếu để giao")
                }
            }
            this.$v.$touch()

            if (this.$v.$invalid) {

                if (!this.$v.noiDungGiao.required) {
                    errorMessage.push("Hãy nhập nội dung giao")
                }

                if (!this.$v.chiTieuGio.required) {
                    errorMessage.push("Hãy nhập chỉ tiêu thời gian")
                }
            }

            if (parseFloat(this.chiTieuGio) <= 0) {
                errorMessage.push("Chỉ tiêu thời gian phải lớn hơn 0")
            }

            if (parseInt(this.thaoTacId) == 3 && (!this.dsDonViId || this.dsDonViId.length == 0)) {
                errorMessage.push("Hãy chọn đơn vị giao bổ sung")
                this.dsDonViIdError = true
            }

            if (errorMessage.length > 0) {
                this.$root.toastError(errorMessage.join('\n'))
                return
            }

            let doc = document.implementation.createDocument("", "", null)
            let root = doc.createElement("root")

            this.dsIdSuCoPtmDuocChon.forEach(item => {
                let array = item.split('_')
                let scp = doc.createElement("scp")
                scp.setAttribute("suco_ptm_id", array[0])
                scp.setAttribute("donvi_id", array[1])
                root.appendChild(scp)
            })

            doc.appendChild(root)

            let sucoPtm = (new XMLSerializer()).serializeToString(doc)

            let data = {
                phieu_cha_id: this.suCoDuocChon.PHIEU_ID,
                huonggiao_id: this.huongGiaoId,
                nd_giao: this.noiDungGiao,
                suco_id: this.suCoDuocChon.SUCO_ID,
                donvi_nhan_id: !this.dsDonViId ? '' : this.dsDonViId.join(),
                suco_ptm: sucoPtm,
                khan: this.mucDoKhanCapId,
                chitieu_tg: this.chiTieuGio
            }

            try {
                this.loading(true)
                await this.giaoPhieu(data)
                this.$root.toastSuccess('Giao phiếu thành công')
            } catch (err) {
                this.$root.toastError(err)
                return
            } finally {
                this.loading(false)
            }

            let thaoTacId = parseInt(this.thaoTacId)

            if ([2, 3].indexOf(thaoTacId) >= 0) {
                this.napThongTinPhieu(this.suCoDuocChon)
                return
            }

            await this.onClickBtnTimKiem()
            this.$emit('success')
        },
        async onClickBtnTimKiem() {

            try {
                this.loading(true)

                let keoDaiGio = !this.keoDaiGio ? 0 : parseInt(this.keoDaiGio)
                let keoDaiPhut = !this.keoDaiPhut ? 0 : parseInt(this.keoDaiPhut)
                let keoDaiGiay = !this.keoDaiGiay ? 0 : parseInt(this.keoDaiGiay)

                let data = {
                    nguonScId: !this.chkNguonSuCo ? 0 : (!this.nguonSuCoId ? 0 : this.nguonSuCoId),
                    maSc: !this.maSc ? '' : this.maSc.trim(),
                    ngayGiao: !this.chkNgaySuCo ? '' : (!this.ngaySuCo ? '' : ddmmyyyy(this.ngaySuCo)),
                    trangThai: !this.thaoTacId ? 0 : this.thaoTacId,
                    mucDoScId: !this.mucDoSuCoId ? 0 : this.mucDoSuCoId,
                    keoDai: !this.chkKeoDai ? '' : keoDaiGio * 60 + keoDaiPhut + keoDaiGiay / 60,
                    huongGiaoId: !this.huongGiaoId ? 0 : this.huongGiaoId
                }

                await this.layDsSuCo(data)
                this.suCoDuocChon = null
                this.idSuCoDuocChon = null
            } catch (err) {

            } finally {
                this.loading(false)
            }
        },
        showCongSoiBiSuCo(data) {
            switch (this.suCoDuocChon.NGUONSC_ID) {
                case 1:
                case 3:
                    let modalChonCongSoi = this.$refs['ref-modal-congsoi-bi-suco']
                    modalChonCongSoi.setLoaiTbiId(data.LOAIPTM_ID)
                    modalChonCongSoi.setThietBiId(data.PTM_ID)
                    modalChonCongSoi.setCongSoi(data.CONGSOI)
                    modalChonCongSoi.setKieu(1)
                    modalChonCongSoi.show()
                    break
                case 2:
                    this.$refs['ref-modal-xem-thongtin'].setTitle('Thông tin lỗi')
                    this.$refs['ref-modal-xem-thongtin'].setContent(data.CONGSOI)
                    this.$bvModal.show('modal-xem-thongtin')
                    break
            }
        },
        async napThongTinPhieu(suCo) {

            this.$v.$reset()
            this.dsDonViIdError = false

            this.phieuDuocChon = null
            this.idPhieuDuocChon = null
            this.dsIdSuCoPtmDuocChon = []
            this.checkAllPhieu = false

            if (!suCo) {
                this.noiDungGiao = null
                this.donViId = null
                this.dsDonViId = []
                this.chiTieuGio = 0
                this.dsPhieu = []
                this.btnGiaoPhieuEnabled = false
                this.btnHuyGiaoEnabled = false
                this.btnCapNhatEnabled = false
                this.btnKhoaPhieuEnabled = false
                this.mucDoKhanCapId = this.dsMucDoKhanCap[0].MUCDO_ID
                return
            }

            this.noiDungGiao = suCo.NOIDUNG_SC
            this.btnGiaoPhieuEnabled = [1, 3].indexOf(parseInt(this.thaoTacId)) >= 0
            this.btnCapNhatEnabled = parseInt(this.thaoTacId) == 2
            this.btnHuyGiaoEnabled = [2, 3].indexOf(parseInt(this.thaoTacId)) >= 0
            this.btnKhoaPhieuEnabled = true

            let data = {
                suco_id: suCo.SUCO_ID,
                trangthai_id: this.thaoTacId
            }

            try {
                this.loading(true)
                await this.layDsPhieu(data)
            } catch (err) {

            } finally {
                this.loading(false)
            }
        },
        async layDmNguonSuCo() {
            try {
                let response = await this.$root.context.get('/web-suco/danhmuc/nguon-suco')

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    this.dsNguonSuCo = response.data.filter(item => item.NGUONSC_ID != 0)
                } else {
                    this.dsNguonSuCo = []
                }
            } catch (err) {
                this.dsNguonSuCo = []
            }
        },
        async layDmMucDoKhanCap() {
            try {
                let response = await this.$root.context.get('/web-suco/danhmuc/mucdo-khancap')

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    this.dsMucDoKhanCap = response.data
                } else {
                    this.dsMucDoKhanCap = []
                }
            } catch (err) {
                this.dsMucDoKhanCap = []
            }
        },
        async layDmMucDoSuCo() {
            try {
                let response = await this.$root.context.get('/web-suco/danhmuc/mucdo-suco')

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    response.data.unshift({ MUCDO_ID: 0, MUCDO_SC: 'Tất cả', CANHBAO_DT: -1 })
                    this.dsMucDoSuCo = response.data
                } else {
                    this.dsMucDoSuCo = []
                }
            } catch (err) {
                this.dsMucDoSuCo = []
            }
        },
        async layDmQuyTrinh() {
            try {
                let response = await this.$root.context.get('/web-suco/danhmuc/ds-quytrinh')

                if (response.error_code === 'BSS-00000000' && response.data != null) {

                    let dsQuyTrinh = []

                    response.data.forEach(x => {
                        if (x.QUYTRINH.toLowerCase().includes("mạng viễn thông")) {
                            dsQuyTrinh.unshift(x)
                        } else {
                            dsQuyTrinh.push(x)
                        }
                    })

                    this.dsQuyTrinh = dsQuyTrinh
                } else {
                    this.dsQuyTrinh = []
                }
            } catch (err) {
                this.dsQuyTrinh = []
            }
        },
        async layDmThaoTac() {
            try {
                let response = await this.$root.context.get('/web-suco/danhmuc/ds-thaotac-giaophieu')

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    this.dsThaoTac = response.data
                } else {
                    this.dsThaoTac = []
                }
            } catch (err) {
                this.dsThaoTac = []
            }
        },
        async layDmHuongGiao(quyTrinhId) {
            try {
                let response = await this.$root.context.get(`/web-suco/danhmuc/ds-huong-giao?quytrinh_id=${quyTrinhId}`)

                if (response.error_code === 'BSS-00000000' && response.data != null) {

                    let dsHuongGiao = []

                    response.data.forEach(x => {
                        if (x.HUONGGIAO.toLowerCase().includes("mạng viễn thông")
                            || x.HUONGGIAO.toLowerCase().includes("mvt")) {
                            dsHuongGiao.unshift(x)
                        } else {
                            dsHuongGiao.push(x)
                        }
                    })

                    this.dsHuongGiao = dsHuongGiao
                } else {
                    this.dsHuongGiao = []
                }
            } catch (err) {
                this.dsHuongGiao = []
            }
        },
        async layDmDonVi(huongGiaoId) {
            try {
                let response = await this.$root.context.get(`/web-suco/danhmuc/ds-donvi-giao?huonggiao_id=${huongGiaoId}`)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    this.dsDonVi = response.data
                } else {
                    this.dsDonVi = []
                }
            } catch (err) {
                this.dsDonVi = []
            }
        },
        async layDsSuCo(data) {
            try {
                let response = await this.$root.context.post(`/web-suco/giao-phieu-xu-li-su-co/ds-phieu-xuly`, data)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    response.data.forEach((item, index) => {

                        item.KEY = index + 1

                        let hms = !item.THOIGIAN ? ['0'] : `${item.THOIGIAN}`.split(':')
                        let second = 0

                        switch (hms.length) {
                            case 3:
                                second = parseInt(hms[0] * 3600) + parseInt(hms[1] * 60) + parseInt(hms[2])
                                break
                            case 2:
                                second = parseInt(hms[0] * 60) + parseInt(hms[1])
                                break
                            case 1:
                                second = parseInt(hms[0])
                                break
                            default:
                                second = 0
                        }

                        item.SECOND = second

                        item.NOIDUNG_SC_HIENTHI = item.NOIDUNG_SC

                        if (!!item.DS_THIETBI) {
                            item.NOIDUNG_SC_HIENTHI += ` (${item.DS_THIETBI})`
                        }
                    })
                    this.dsSuCo = response.data
                } else {
                    this.dsSuCo = []
                }
            } catch (err) {
                this.dsSuCo = []
            }
        },
        async layDsPhieu(data) {
            try {
                let response = await this.$root.context.post(`/web-suco/giao-phieu-xu-li-su-co/ds-tinhtrang-xuly`, data)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    response.data.forEach((item, index) => {
                        item.KEY = index + 1
                        item.KHAN = item.KHAN == 's0' ? 0 : (item.KHAN == 's1' ? 1 : null)
                        item.KHAN_HIENTHI = item.KHAN == 1 ? 'Khẩn' : (item.KHAN == 0 ? 'BT' : '')
                    })
                    this.dsPhieu = response.data
                } else {
                    this.dsPhieu = []
                }
            } catch (err) {
                this.dsPhieu = []
            }
        },
        async giaoPhieu(data) {
            try {
                let response = await this.$root.context.post(`/web-suco/giao-phieu-xu-li-su-co/giaophieu-xuly`, data)

                if (response.error_code !== 'BSS-00000000') {
                    throw { response: { data: response } }
                }
            } catch (err) {
                let responseData = err.response.data
                let message = []

                if (responseData.message) {
                    message.push(responseData.message)
                }

                if (responseData.message_detail) {
                    message.push(responseData.message_detail)
                }

                if (responseData.data) {
                    let detailMessage = Object.getOwnPropertyNames(responseData.data).map(item => responseData.data[item])
                    message.push([...new Set(detailMessage)].join('\n'))
                }

                throw message.join('\n')
            }
        },
        async capNhatPhieu(data) {
            try {
                let response = await this.$root.context.post(`/web-suco/giao-phieu-xu-li-su-co/capnhat-phieu-xuly`, data)

                if (response.error_code !== 'BSS-00000000') {
                    throw { response: { data: response } }
                }
            } catch (err) {
                let responseData = err.response.data
                let message = []

                if (responseData.message) {
                    message.push(responseData.message)
                }

                if (responseData.message_detail) {
                    message.push(responseData.message_detail)
                }

                if (responseData.data) {
                    let detailMessage = Object.getOwnPropertyNames(responseData.data).map(item => responseData.data[item])
                    message.push([...new Set(detailMessage)].join('\n'))
                }

                throw message.join('\n')
            }
        },
        async huyPhieu(data) {
            try {
                let response = await this.$root.context.post(`/web-suco/giao-phieu-xu-li-su-co/huy-phieu-xuly`, data)

                if (response.error_code !== 'BSS-00000000') {
                    throw { response: { data: response } }
                }
            } catch (err) {
                let responseData = err.response.data
                let message = []

                if (responseData.message) {
                    message.push(responseData.message)
                }

                if (responseData.message_detail) {
                    message.push(responseData.message_detail)
                }

                if (responseData.data) {
                    let detailMessage = Object.getOwnPropertyNames(responseData.data).map(item => responseData.data[item])
                    message.push([...new Set(detailMessage)].join('\n'))
                }

                throw message.join('\n')
            }
        },
    }
}
</script>
<style>
.giaophieu-xuly-suco .e-control.e-numerictextbox.e-lib.e-input {
    text-align: right !important;
    padding-right: 5px !important;
}

.giaophieu-xuly-suco .e-grid {
    font-family: "Roboto", Arial !important;
}

.giaophieu-xuly-suco .e-grid .col-mark-selected-row {
    font-size: 12px;
    display: none;
}

.giaophieu-xuly-suco .e-grid .e-active .col-mark-selected-row {
    display: block !important;
}

.giaophieu-xuly-suco .e-grid th.e-filterbarcell {
    padding: 1px 7px !important;
    border-width: 1px 0 0 1px !important;
}

.giaophieu-xuly-suco .e-grid th.e-filterbarcell>div input.form-control {
    padding-bottom: 0 !important;
    padding-top: 0 !important;
    height: 26px !important;
}

.giaophieu-xuly-suco .e-grid th.e-grouptopleftcell,
.giaophieu-xuly-suco .e-grid th.e-headercell,
.giaophieu-xuly-suco .e-grid th.e-filterbarcell {
    background-color: #BAE7FF !important;
    border-color: white !important;
}

.giaophieu-xuly-suco .e-grid td.e-active {
    background: #F9E1A9 !important;
}

.giaophieu-xuly-suco .e-grid.e-gridhover tr[role='row']:not(.e-editedrow):hover .e-rowcell:not(.e-cellselectionbackground):not(.e-active):not(.e-updatedtd):not(.e-indentcell),
.giaophieu-xuly-suco .e-grid.e-gridhover tr[role='row']:hover .e-detailrowcollapse:not(.e-cellselectionbackground):not(.e-active):not(.e-updatedtd):not(.e-indentcell),
.giaophieu-xuly-suco .e-grid.e-gridhover tr[role='row']:hover .e-rowdragdrop:not(.e-cellselectionbackground):not(.e-active):not(.e-updatedtd):not(.e-indentcell),
.giaophieu-xuly-suco .e-grid.e-rtl .e-gridhover tr[role='row']:hover .e-rowdragdrop:not(.e-cellselectionbackground):not(.e-active):not(.e-updatedtd):not(.e-indentcell),
.giaophieu-xuly-suco .e-grid.e-gridhover tr[role='row']:hover .e-detailrowexpand:not(.e-cellselectionbackground):not(.e-active):not(.e-updatedtd):not(.e-indentcell) {
    background-color: #FFF9EB !important;
}

.giaophieu-xuly-suco .e-grid td.e-rowcell {
    line-height: 20px !important;
    padding: 5px 7px !important;
}

.giaophieu-xuly-suco .e-grid .e-filterdiv.e-fltrinputdiv .e-input-group.e-control-wrapper {
    border: 0 !important;
}

.giaophieu-xuly-suco .e-grid .e-filtertext.e-input.e-disable {
    display: none !important;
}

.giaophieu-xuly-suco .e-grid tr.e-emptyrow td {
    text-align: center !important;
}

.giaophieu-xuly-suco .e-grid div.e-gridheader div.e-headercontent thead tr.e-columnheader th[aria-colindex="1"],
.giaophieu-xuly-suco .e-grid div.e-gridheader div.e-headercontent thead tr.e-columnheader th[aria-colindex="1"]>div,
.giaophieu-xuly-suco .grid-phieu div.e-gridheader div.e-headercontent thead tr.e-columnheader th[aria-colindex="2"],
.giaophieu-xuly-suco .grid-phieu div.e-gridheader div.e-headercontent thead tr.e-columnheader th[aria-colindex="2"]>div,
.giaophieu-xuly-suco .grid-phieu div.e-gridheader div.e-headercontent thead tr.e-columnheader th[aria-colindex="3"],
.giaophieu-xuly-suco .grid-phieu div.e-gridheader div.e-headercontent thead tr.e-columnheader th[aria-colindex="3"]>div {
    padding: 0 !important;
}

.giaophieu-xuly-suco .e-grid .e-gdiagonaldown.e-icon-gdownarrow,
.giaophieu-xuly-suco .e-grid .e-gnextforward.e-icon-grightarrow {
    width: 16px;
    height: 16px;
    color: #0176FF;
    font-family: "ap";
    font-size: 16px;
    cursor: pointer;
    text-align: center;
    line-height: 20px;
    display: inline-block;
    text-indent: 0px;
}

.giaophieu-xuly-suco .e-grid .e-icon-gdownarrow::before {
    content: "\E6B8"
}

.giaophieu-xuly-suco .e-grid .e-icon-grightarrow::before {
    content: "\E6BB"
}

.giaophieu-xuly-suco .disable-btn:hover {
    background-color: white !important;
}

.giaophieu-xuly-suco .disable-btn a {
    color: #6c757d !important;
}

.giaophieu-xuly-suco .disable-btn a:hover {
    cursor: default;
    color: #6c757d;
}
</style>
