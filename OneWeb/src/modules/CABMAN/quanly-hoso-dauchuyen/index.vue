<template src="./index.template.html"></template>
<script>
import Pagination from "@/components/Controls/Pagination"
import BssPagination2 from '@/components/BssPagination_2'
import ModalLapHoSo from './modal-lap-hoso'
import frmCTHoSoDauChuyen from '../caitao-baoduong/modals/CTHoSoDauChuyen/CTHoSoDauChuyen.vue'
import {Workbook} from "@syncfusion/ej2-excel-export/index"

export default {
    components: {BssPagination2, ModalLapHoSo, frmCTHoSoDauChuyen, Pagination},
    data() {
        return {
            tenHoSo: null,
            dsLoaiHoSo: [],
            dsKieuCatChuyen: [],
            dsTrangThaiCatChuyen: [],
            dsTtvt: [],
            dsToQuanLy: [],
            dsVeTinh: [],
            dsHoSo: [],
            loaiHoSoId: null,
            kieuCatChuyenId: null,
            chkNgayDangKy: false,
            chkNgayBatDau: false,
            chkNgayKetThuc: false,
            chkVeTinh: false,
            chkToQuanLy: false,
            ngayDangKy: new Date(),
            ngayBatDau: new Date(),
            ngayKetThuc: new Date(),
            ttvtId: null,
            toQuanLyId: null,
            veTinhId: null,
            trangThaiId: null,
            nguoiDangKy: null,
            tableHoSo: {
                pageSize: 10,
                paginationKey: 1,
                dataSource: [],
                pagerData: [],
                selectedData: null,
                selectedKey: null,
                searchColumn: {
                    TEN_HS: '',
                    LOAI_HS_CC: '',
                    TEN_KIEU: '',
                    TRANGTHAI_CC: '',
                    NGUOI_DK: '',
                    NGAY_YC: '',
                    NGAYBD_DK: '',
                    NGAYKT_DK: '',
                    SO_TB_DK: '',
                }
            },
            sort: {
                TEN_HS: {
                    sortIcon: 'fa-sort',
                    sortDirection: null // -1: desc, 1: asc
                },
                LOAI_HS_CC: {
                    sortIcon: 'fa-sort',
                    sortDirection: null
                },
                TEN_KIEU: {
                    sortIcon: 'fa-sort',
                    sortDirection: null
                },
                TRANGTHAI_CC: {
                    sortIcon: 'fa-sort',
                    sortDirection: null
                },
                NGUOI_DK: {
                    sortIcon: 'fa-sort',
                    sortDirection: null
                },
                NGAY_YC_HT: {
                    sortIcon: 'fa-sort',
                    sortDirection: null
                },
                NGAYBD_DK_HT: {
                    sortIcon: 'fa-sort',
                    sortDirection: null
                },
                NGAYKT_DK_HT: {
                    sortIcon: 'fa-sort',
                    sortDirection: null
                },
                SO_TB_DK: {
                    sortIcon: 'fa-sort',
                    sortDirection: null
                }
            },
            showGridDsHoSo: false,
            idHoSoDuocChon: null,
            loaihs:this.$route.name==='QuanLyHoSoBaoDuong'?"BAO_DUONG":"DAU_CHUYEN",
            idHoSoDeXoa: null,
            dsThuGon: []
        }
    },
    watch: {
        showGridDsHoSo(value) {
            if (value) {
                this.$refs['grid-hoso'].grid.refresh()
            }
        },
        async ttvtId(newVal) {
            try {
                this.loading(true)
                await this.layDsToQuanLy(newVal)
            } catch (err) {

            } finally {
                this.loading(false)
            }

            if (this.dsToQuanLy.length > 0) {
                this.toQuanLyId = this.dsToQuanLy[0].DONVI_ID
            }
        },
        async toQuanLyId(newVal) {
            try {
                this.loading(true)
                await this.layDsVeTinh(newVal)
            } catch (err) {

            } finally {
                this.loading(false)
            }

            if (this.dsVeTinh.length > 0) {
                this.veTinhId = this.dsVeTinh[0].DONVI_ID
            }
        },
        dsHoSo(newVal) {
            newVal.forEach((item, index) => item.KEY = index + 1)
        },
    },
    async mounted() {

        this.$refs['ref-ten-hoso'].focus()

        try {
            this.loading(true)
            let loading1 = this.layDsLoaiHoSo()
            let loading2 = this.layDsKieuCatChuyen()
            let loading3 = this.layDsTrangThaiCatChuyen()
            let loading4 = this.layDsTtvt()
            await loading1
            await loading2
            await loading3
            await loading4
        } catch (err) {
            if (typeof err == 'string') {
                this.$root.toastError(err)
            }
        } finally {
            this.loading(false)
        }

        if (this.dsLoaiHoSo.length > 0) {
            this.loaiHoSoId = this.dsLoaiHoSo[0].LOAIHSCC_ID
        }

        if (this.dsKieuCatChuyen.length > 0) {
            this.kieuCatChuyenId = this.dsKieuCatChuyen[0].KIEUCC_ID
        }

        if (this.dsTtvt.length > 0) {
            this.ttvtId = this.dsTtvt[0].DONVI_ID
        }

        if (this.dsTrangThaiCatChuyen.length > 0) {
            this.trangThaiId = this.dsTrangThaiCatChuyen[0].TTCC_ID
        }
    },
    methods: {
        getColumnTemplateChucNang(parent) {
            return function() {
                return {
                    template: {
                        template: `
                            <div style="text-align: center">
                                <a href="javascript:void(0)" class="act text-main" @click="parent.onClickBtnEditHoSo(data.HSCC_ID)">
                                    <span class="lh20 f20 nc-icon-outline ui-1_edit-76"></span>
                                </a>
                                <a href="javascript:void(0)" class="act text-main" @click="parent.onClickBtnXoaHoSo(data.HSCC_ID, data.SO_TB_DK)">
                                    <span class="lh20 f20 nc-icon-outline ui-1_trash"></span>
                                </a>
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
        onClickBtnSort(colName) {

            let direction = this.sort[colName].sortDirection

            if (!direction) {
                this.sort[colName].sortDirection = 1
            } else {
                this.sort[colName].sortDirection = direction == 1 ? -1 : 1
            }

            Object.keys(this.sort).forEach(item => {
                if (item != colName) {
                    this.sort[item].sortDirection = null
                }
            })

            this.setSortIcon(colName)
            this.sortTable(colName)
        },
        setSortIcon(colName) {

            let direction = this.sort[colName].sortDirection
            let className = ''

            if (!direction) {
                className = 'fa-sort'
            } else if (direction == 1) {
                className = 'btn-sorted fa-caret-up'
            } else {
                className = 'btn-sorted fa-caret-down'
            }

            this.sort[colName].sortIcon = className

            Object.keys(this.sort).forEach(item => {
                if (item != colName) {
                    this.sort[item].sortIcon = 'fa-sort'
                }
            })
        },
        sortTable(colName) {

            let direction = this.sort[colName].sortDirection

            if (this.dsHoSo.length == 0 || !direction) {
                return
            }

            let dsHoSo = [...this.tableHoSo.pagerData]

            dsHoSo.sort((a, b) => {

                var nameA = a[colName]
                var nameB = b[colName]

                nameA = !nameA ? '' : `${nameA}`.toUpperCase()
                nameB = !nameB ? '' : `${nameB}`.toUpperCase()

                if (nameA < nameB) {
                    return direction == 1 ? -1 : 1
                }

                if (nameA > nameB) {
                    return direction == 1 ? 1 : -1
                }

                return 0
            })

            this.tableHoSo.pagerData = dsHoSo
        },
        onPageChangedTableHoSo(args) {

            this.tableHoSo.pageSize = args.pageSize

            let pageIndex = args.pageIndex + 1

            if (pageIndex > 0) {
                let to = pageIndex * args.pageSize
                let from = to - args.pageSize
                this.tableHoSo.pagerData = this.tableHoSo.dataSource.slice(from, to)

                if (this.tableHoSo.pagerData.length > 0) {
                    this.tableHoSo.selectedKey = this.tableHoSo.pagerData[0].KEY
                }
            } else {
                this.tableHoSo.pagerData = []
                this.tableHoSo.selectedKey = null
            }
        },
        onMousemoveTraCuu(e) {
            var tooltipSpan = document.getElementById('tooltip-span-tracuu')
            var x = e.clientX, y = e.clientY;
            tooltipSpan.style.top = (y + 20) + 'px'
            tooltipSpan.style.left = (x + 10) + 'px'
        },
        onMousemoveThemMoi(e) {
            var tooltipSpan = document.getElementById('tooltip-span-themmoi')
            var x = e.clientX, y = e.clientY;
            tooltipSpan.style.top = (y + 20) + 'px'
            tooltipSpan.style.left = (x + 10) + 'px'
        },
        onSearchTableHoSo(name, event) {

            let value = event.target.value
            this.tableHoSo.searchColumn[name] = !value ? '' : value.trim()

            this.tableHoSo.dataSource = this.dsHoSo.filter(item => {

                let match = true

                for (let propName in this.tableHoSo.searchColumn) {

                    let searchValue = this.tableHoSo.searchColumn[propName]
                    searchValue = !searchValue ? '' : searchValue

                    let sourceValue = item[propName]
                    sourceValue = !sourceValue ? '' : sourceValue.toString()

                    match = sourceValue.toLowerCase().includes(searchValue.toLowerCase())

                    if (!match) {
                        break
                    }
                }

                return match
            })

            this.tableHoSo.paginationKey++
            this.onPageChangedTableHoSo({pageIndex: 0, pageSize: this.tableHoSo.pageSize})
        },
        xuatExcelDangPanel() {
            let dsNhomHoSo = []
            let tmp = []

            this.dsHoSo.forEach((item, index) => {

                if ((index + 1) % 3 != 0) {
                    tmp.push(item)
                } else {
                    tmp.push(item)
                    dsNhomHoSo.push(tmp)
                    tmp = []
                }
            })

            if (tmp.length > 0) {
                dsNhomHoSo.push(tmp)
            }

            let columnCount = dsNhomHoSo[0].length * 3
            let columns = []

            for (let i = 0; i < columnCount; i++) {

                let column = {
                    index: i + 1
                }

                if ((i + 1) % 3 == 1) {
                    column.width = 50
                }

                if ((i + 1) % 3 == 2) {
                    column.width = 150
                }

                if ((i + 1) % 3 == 0) {
                    column.width = 200
                }

                columns.push(column)
            }

            let propName = {
                TEN_HS: '',
                LOAI_HS_CC: 'Loại',
                TEN_KIEU: 'Hình thức',
                TRANGTHAI_CC: 'Trạng thái',
                NGUOI_DK: 'Người ĐK',
                NGAY_YC: 'Ngày ĐK',
                NGAYBD_DK: 'Ngày bắt đầu',
                NGAYKT_DK: 'Ngày kết thúc',
                SO_TB_DK: 'Số lượng thuê bao',
                MA_HS_CC: 'Mã hs'
            }

            let rows = []
            let rowIndex = 1

            dsNhomHoSo.forEach(item => {

                rowIndex++

                for (let element in propName) {

                    let row = {
                        index: rowIndex++,
                        cells: []
                    }

                    if (!!item[0]) {

                        if (!propName[element]) {
                            let cell = {
                                index: 2,
                                value: item[0][element],
                                colSpan: 2,
                                wrapText: true,
                                style: {
                                    hAlign: 'Center',
                                    bold: true,
                                    borders: {
                                        lineStyle: 'thin',
                                        color: '#000000'
                                    }
                                }
                            }
                            row.cells.push(cell)
                        } else {
                            let cell = {
                                index: 2,
                                value: propName[element],
                                style: {
                                    leftBorder: {
                                        lineStyle: 'thin',
                                        color: '#000000'
                                    },
                                    bottomBorder: {
                                        lineStyle: element == 'MA_HS_CC' ? 'thin' : 'none',
                                        color: element == 'MA_HS_CC' ? '#000000' : '#FFFFFF',
                                    }
                                }
                            }
                            row.cells.push(cell)

                            cell = {
                                index: 3,
                                value: item[0][element],
                                style: {
                                    rightBorder: {
                                        lineStyle: 'thin',
                                        color: '#000000'
                                    },
                                    bottomBorder: {
                                        lineStyle: element == 'MA_HS_CC' ? 'thin' : 'none',
                                        color: element == 'MA_HS_CC' ? '#000000' : '#FFFFFF',
                                    }
                                }
                            }
                            row.cells.push(cell)
                        }
                    }

                    if (!!item[1]) {

                        if (!propName[element]) {
                            let cell = {
                                index: 5,
                                value: item[1][element],
                                colSpan: 2,
                                style: {
                                    hAlign: 'Center',
                                    bold: true,
                                    borders: {
                                        lineStyle: 'thin',
                                        color: '#000000'
                                    }
                                }
                            }
                            row.cells.push(cell)
                        } else {
                            let cell = {
                                index: 5,
                                value: propName[element],
                                style: {
                                    leftBorder: {
                                        lineStyle: 'thin',
                                        color: '#000000'
                                    },
                                    bottomBorder: {
                                        lineStyle: element == 'MA_HS_CC' ? 'thin' : 'none',
                                        color: element == 'MA_HS_CC' ? '#000000' : '#FFFFFF',
                                    }
                                }
                            }
                            row.cells.push(cell)

                            cell = {
                                index: 6,
                                value: item[1][element],
                                style: {
                                    rightBorder: {
                                        lineStyle: 'thin',
                                        color: '#000000'
                                    },
                                    bottomBorder: {
                                        lineStyle: element == 'MA_HS_CC' ? 'thin' : 'none',
                                        color: element == 'MA_HS_CC' ? '#000000' : '#FFFFFF',
                                    }
                                }
                            }
                            row.cells.push(cell)
                        }
                    }

                    if (!!item[2]) {

                        if (!propName[element]) {
                            let cell = {
                                index: 8,
                                value: item[2][element],
                                colSpan: 2,
                                style: {
                                    hAlign: 'Center',
                                    bold: true,
                                    borders: {
                                        lineStyle: 'thin',
                                        color: '#000000'
                                    }
                                }
                            }
                            row.cells.push(cell)
                        } else {
                            let cell = {
                                index: 8,
                                value: propName[element],
                                style: {
                                    leftBorder: {
                                        lineStyle: 'thin',
                                        color: '#000000'
                                    },
                                    bottomBorder: {
                                        lineStyle: element == 'MA_HS_CC' ? 'thin' : 'none',
                                        color: element == 'MA_HS_CC' ? '#000000' : '#FFFFFF',
                                    }
                                }
                            }
                            row.cells.push(cell)

                            cell = {
                                index: 9,
                                value: item[2][element],
                                style: {
                                    rightBorder: {
                                        lineStyle: 'thin',
                                        color: '#000000'
                                    },
                                    bottomBorder: {
                                        lineStyle: element == 'MA_HS_CC' ? 'thin' : 'none',
                                        color: element == 'MA_HS_CC' ? '#000000' : '#FFFFFF',
                                    }
                                }
                            }
                            row.cells.push(cell)
                        }
                    }

                    rows.push(row)
                }
            })

            let worksheets = [{ columns, rows }]
            let workbook = new Workbook({worksheets}, 'xlsx')
            workbook.save('Export.xlsx')
        },
        xuatExcelDangGrid() {
            let columns = [
                { index: 1, width: 130 },
                { index: 2, width: 130 },
                { index: 3, width: 130 },
                { index: 4, width: 130 },
                { index: 5, width: 130 },
                { index: 6, width: 130 },
                { index: 7, width: 130 },
                { index: 8, width: 130 },
                { index: 9, width: 130 },
            ]

            let propName = {
                TEN_HS: 'Tên hồ sơ',
                LOAI_HS_CC: 'Loại',
                TEN_KIEU: 'Hình thức',
                TRANGTHAI_CC: 'Trạng thái',
                NGUOI_DK: 'Người đăng ký',
                NGAY_YC: 'Ngày đăng ký',
                NGAYBD_DK: 'Ngày bắt đầu',
                NGAYKT_DK: 'Ngày kết thúc',
                SO_TB_DK: 'Số lượng thuê bao'
            }

            let rows = []

            let headerRow = {
                index: 1,
                cells: []
            }

            Object.keys(propName).forEach((item, index) => {
                headerRow.cells.push({
                    index: index + 1,
                    value: propName[item],
                    style: {
                        borders: {
                            color: '#00000000',
                            lineStyle: 'thin'
                        }
                    }
                })
            })

            rows.push(headerRow)

            this.dsHoSo.forEach((item, index) => {

                let row = {
                    index: index + 2,
                    cells: []
                }

                Object.keys(propName).forEach((element, index) => {
                    row.cells.push({
                        index: index + 1,
                        value: item[element],
                        style: {
                            borders: {
                                color: '#00000000',
                                lineStyle: 'thin'
                            }
                        }
                    })
                })
                rows.push(row)
            })

            let worksheets = [{ columns, rows }]
            let workbook = new Workbook({worksheets}, 'xlsx')
            workbook.save('Export.xlsx')
        },
        onClickBtnXuatExcel() {

            if (!this.dsHoSo || this.dsHoSo.length == 0) {
                this.$toast.error('Không có dữ liệu để xuất Excel !')
                return
            }

            if (!this.showGridDsHoSo) {
                this.xuatExcelDangPanel()
                return
            }

            this.xuatExcelDangGrid()
        },
        onChangeModalLapHoso() {
            this.onClickBtnTraCuu()
        },
        onClickBtnXoaHoSo(hsccId, soTbDk) {

            if (soTbDk.toLowerCase() != '0 thuê bao') {
                this.$toast.error('Hồ sơ đang có thuê bao đăng ký, không thể xóa!')
                return
            }
            this.idHoSoDeXoa = hsccId
            this.$bvModal.show('modal-xacnhan-xoa-hoso')
        },
        async okXoaHoSo() {
            try {
		        this.loading(true)
                await this.xoaHoSo(this.idHoSoDeXoa)
                this.idHoSoDeXoa = null
                this.$toast.success('Xóa hồ sơ thành công !')
            } catch (err) {
                this.$toast.error(err)
                return
            } finally {
                this.loading(false)
            }

            this.onClickBtnTraCuu()
        },
        onClickBtnEditHoSo(hsccId) {
            if (this.loaihs=='BAO_DUONG'){
                if(this.$root.token.getMaTinh()=='HPG'){
                    this.$refs['frmCTHoSoBaoDuong'].hscc_id=hsccId
                    this.$refs['frmCTHoSoBaoDuong'].Tag = 1
                    this.$refs['frmCTHoSoBaoDuong'].GiaoPhieuTuDong = false
                    this.$refs['frmCTHoSoBaoDuong'].show()
                }else{
                    this.idHoSoDuocChon = hsccId
                    this.$bvModal.show('id-modal-lap-hoso')
                }
            }else{
                this.idHoSoDuocChon = hsccId
                this.$bvModal.show('id-modal-lap-hoso')
            }
        },
        onClickBtnThemMoi() {
            this.idHoSoDuocChon = null

            if (this.loaihs=='BAO_DUONG'){
                if(this.$root.token.getMaTinh()=='HPG'){
                    this.$refs['frmCTHoSoBaoDuong'].Tag = 1
                    this.$refs['frmCTHoSoBaoDuong'].GiaoPhieuTuDong = false
                    this.$refs['frmCTHoSoBaoDuong'].show()
                }else{
                    this.$bvModal.show('id-modal-lap-hoso')
                }
            }else{
                this.$bvModal.show('id-modal-lap-hoso')
            }
        },
        onPageChangeTableHoSo(data) {

            this.tableHoSo.selectedRowIndexOnPage = data.rowIndexOnPage
            this.tableHoSo.selectedRowIndexOnData = data.rowIndexOnData

            if (data.pageIndex > 0) {
                let to = data.pageIndex * data.pageSize
                let from = to - data.pageSize
                this.tableHoSo.pagerData = this.tableHoSo.dataSource.slice(from, to)
                this.tableHoSo.selectedData = this.tableHoSo.pagerData[data.rowIndexOnPage - 1]
            } else {
                this.tableHoSo.pagerData = []
                this.tableHoSo.selectedData = null
            }
        },
        onRowChangeTableHoSo(data) {
            this.tableHoSo.selectedRowIndexOnPage = data.rowIndexOnPage
            this.tableHoSo.selectedRowIndexOnData = data.rowIndexOnData
            this.tableHoSo.selectedData = this.tableHoSo.pagerData[data.rowIndexOnPage - 1]
        },
        onClickRowTableHoSo(rowData) {
            this.tableHoSo.selectedData = rowData
            this.tableHoSo.selectedKey = rowData.KEY
        },
        getDateElements(date) {

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
        },
        yyyymmdd(date) {

            if (!date) {
                return ''
            }

            date = this.getDateElements(date)
            return `${date.y}-${date.M}-${date.d}`
        },
        ddmmyyyy(date) {

            if (!date) {
                return ''
            }

            date = this.getDateElements(date)
            return `${date.d}/${date.M}/${date.y}`
        },
        ddmmyyyyhhmiss(date) {

            if (!date) {
                return ''
            }

            date = this.getDateElements(date)
            return `${date.d}/${date.M}/${date.y} ${date.h}:${date.m}:${date.s}`
        },
        async onClickBtnTraCuu() {

            let data = {
                ten_hs: !this.tenHoSo ? '' : this.tenHoSo.toString().trim(),
                loaihscc_id: this.loaiHoSoId,
                kieucc_id: this.kieuCatChuyenId,
                ngay_yc: !this.chkNgayDangKy ? null : this.yyyymmdd(this.ngayDangKy),
                ngaybd_dk: !this.chkNgayBatDau ? null : this.yyyymmdd(this.ngayBatDau),
                ngaykt_dk: !this.chkNgayKetThuc ? null : this.yyyymmdd(this.ngayKetThuc),
                ttvt_id: this.ttvtId,
                toql_id: !this.chkToQuanLy ? -1 : this.toQuanLyId,
                ttcc_id: this.trangThaiId,
                tramtbi_id: !this.chkVeTinh ? -1 : this.veTinhId,
                nguoidk: !this.nguoiDangKy ? '' : this.nguoiDangKy.toString().trim()
            }

            try {
                this.loading(true)
                await this.traCuuHoSo(data)
            } catch (err) {

            } finally {
                this.loading(false)
            }
        },
        async traCuuHoSo(data) {
            try {
                let response = await this.$root.context.post('/web-cabman/hoso-dauchuyen/tracuu-hoso', data)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    response.data.forEach(element => {
                        element.NGAY_YC_HT = this.ddmmyyyy(element.NGAY_YC)
                        element.NGAYBD_DK_HT = this.ddmmyyyyhhmiss(element.NGAYBD_DK)
                        element.NGAYKT_DK_HT = this.ddmmyyyyhhmiss(element.NGAYKT_DK)
                    })
                    this.dsHoSo = response.data
                } else {
                    this.dsHoSo = []
                }
            } catch (err) {
                this.dsHoSo = []
            }
        },
        async layDsVeTinh(toQuanLyId) {
            try {
                let response = await this.$root.context.post('/web-ecms/danhmuc/layDsVeTinhTheoToQl', {id: toQuanLyId})

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    this.dsVeTinh = response.data
                } else {
                    this.dsVeTinh = []
                }
            } catch (err) {
                this.dsVeTinh = []
            }
        },
        async layDsToQuanLy(ttvtId) {
            try {
                let response = await this.$root.context.post('/web-ecms/danhmuc/layDsToKTTheoNVTrongTokenVaDonVi', {id: ttvtId})

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    this.dsToQuanLy = response.data
                } else {
                    this.dsToQuanLy = []
                }
            } catch (err) {
                this.dsToQuanLy = []
            }
        },
        async layDsTtvt() {
            try {
                let response = await this.$root.context.post('/web-ecms/danhmuc/layDsTTVTTrongToken')

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    this.dsTtvt = response.data
                } else {
                    this.dsTtvt = []
                }
            } catch (err) {
                this.dsTtvt = []

                if (!err.response && err.message.toString().toLowerCase() == 'network error') {
                    throw 'Xảy ra lỗi kết nối.\nVui lòng kiểm tra kết nối mạng và load lại trang'
                }
            }
        },
        async layDsTrangThaiCatChuyen() {
            try {
                let response = await this.$root.context.get('/web-cabman/danhmuc/layDsTrangThaiCatChuyen')

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    this.dsTrangThaiCatChuyen = response.data
                } else {
                    this.dsTrangThaiCatChuyen = []
                }
            } catch (err) {
                this.dsTrangThaiCatChuyen = []

                if (!err.response && err.message.toString().toLowerCase() == 'network error') {
                    throw 'Xảy ra lỗi kết nối.\nVui lòng kiểm tra kết nối mạng và load lại trang'
                }
            }
        },
        async layDsKieuCatChuyen() {
            try {
                let response = await this.$root.context.get('/web-cabman/danhmuc/layDsKieuCatChuyen')

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    response.data.unshift({KIEUCC_ID: -1, TEN_KIEU: '---Tất cả---'})
                    this.dsKieuCatChuyen = response.data
                } else {
                    this.dsKieuCatChuyen = []
                }
            } catch (err) {
                this.dsKieuCatChuyen = []

                if (!err.response && err.message.toString().toLowerCase() == 'network error') {
                    throw 'Xảy ra lỗi kết nối.\nVui lòng kiểm tra kết nối mạng và load lại trang'
                }
            }
        },
        async layDsLoaiHoSo() {
            try {
                let response = await this.$root.context.get('/web-cabman/danhmuc/layDsLoaiHoSo')

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    if (this.loaihs=='BAO_DUONG'){
                        this.dsLoaiHoSo = response.data.filter((e) => { return e.LOAIHSCC_ID == 5 })
                    }else{
                        this.dsLoaiHoSo = response.data.filter((e) => { return e.LOAIHSCC_ID != 5 })
                        this.dsLoaiHoSo.unshift({LOAIHSCC_ID: -1, LOAI_HS_CC: '---Tất cả---'})
                    }
                } else {
                    this.dsLoaiHoSo = []
                }
            } catch (err) {
                this.dsLoaiHoSo = []

                if (!err.response && err.message.toString().toLowerCase() == 'network error') {
                    throw 'Xảy ra lỗi kết nối.\nVui lòng kiểm tra kết nối mạng và load lại trang'
                }
            }
        },
        async xoaHoSo(hsccId) {
            try {
                let response = await this.$root.context.post(`/web-cabman/hoso-dauchuyen/xoa-hoso?hscc_id=${hsccId}`)

                if (response.error_code !== 'BSS-00000000') {
                    throw {response: { data: response}}
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
    .quanly-hoso-dauchuyen .tooltip-e {
        text-decoration:none;
        position:relative;
    }

    .quanly-hoso-dauchuyen .tooltip-e #tooltip-span-tracuu,
    .quanly-hoso-dauchuyen .tooltip-e #tooltip-span-themmoi {
        display:none;
    }

    .quanly-hoso-dauchuyen .tooltip-e:hover #tooltip-span-tracuu,
    .quanly-hoso-dauchuyen .tooltip-e:hover #tooltip-span-themmoi {
        display:block;
        position:fixed;
        overflow:hidden;
        border: 1px solid #ccc;
        padding: 5px;
        background-color: white;
        font-size: 12px;
        line-height: 15px;
    }

    .quanly-hoso-dauchuyen .e-datepicker.e-input {
        font-family: "Roboto", Arial !important;
        color: #444 !important;
    }
</style>
