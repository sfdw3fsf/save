<template src="./index.template.html"></template>
<script>
import ComboboxGridNhanVien from '../ComboboxGridNhanVien'
import ModalTraPhieu from './modal-traphieu/index.vue'
import ModalKhoaPhieu from '../khoa-phieu/index.vue'
import XLSX from "xlsx"
import { Edit, Sort, Filter, ForeignKey, Resize, Freeze } from "@syncfusion/ej2-vue-grids"
import { L10n } from '@syncfusion/ej2-base'
// import sendSms from '../send-sms'

L10n.load({
    'vi-VN': {
        grid: {
          EmptyRecord: 'Không có bản ghi nào để hiển thị'
        }
    }
})

var toanTu = {
    '>=': '≥',
    '>': '>',
    '=': '=',
    '<=': '≤',
    '<': '<'
}

var layGiaTriToanTu = value => {
    switch (value) {
        case toanTu['<=']:
            return -2;
        case toanTu['<']:
            return -1;
        case toanTu['=']:
            return 0;
        case toanTu['>']:
            return 1;
        case toanTu['>=']:
            return 2;
        default:
            return ''
    }
}

export default {
    provide: {
        grid: [Edit, Sort, Filter, ForeignKey, Resize, Freeze ]
    },
    components: {
        ComboboxGridNhanVien,
        ModalTraPhieu,
        ModalKhoaPhieu,
    },
    data() {
        return {
            dsNhomSuCo: [],
            nhomSuCoId: null,
            chkMaSuCo: false,
            chkNguoiBao: false,
            chkMdKhanCap: false,
            chkNguonSuCo: false,
            chkTinhTrang: false,
            chkNoiDungSuCo: false,
            chkNgaySuCo: false,
            chkNgayBao: false,
            chkNgayGiao: false,
            chkMucDoSuCo: false,
            chkKeoDaiTu: false,
            chkKeoDaiDen: false,
            nguoiBaoId: null,
            dsNguonSuCo: [],
            nguonSuCoId: null,
            dsTinhTrang: [],
            tinhTrangId: null,
            dsMucDoSuCo: [],
            mucDoSuCoId: null,
            ngaySuCoTu: null,
            ngaySuCoDen: null,
            ngayBaoTu: null,
            ngayBaoDen: null,
            keoDaiTuH: 0,
            keoDaiTuM: 0,
            keoDaiTuS: 0,
            keoDaiDenH: 0,
            keoDaiDenM: 0,
            keoDaiDenS: 0,
            toanTuKeoDaiTu: toanTu['>='],
            toanTuKeoDaiDen: toanTu['<='],
            showKeoDaiDen: true,
            noiDungSuCo: null,
            maSuCo: null,
            showTttk: true,
            expandDsPhieu: false,
            expandDsSuCo: false,
            dsPhieuDieuXuLy: [],
            dsSuCo: [],
            idSuCoDuocChon: null,
            idPhieuNt: null,
            idPhieuXl: null,
            keyPhieuDieuXuLyDuocChon: null,
            zoomImg: null,
            showZoomImg: false,
            ghiChuNt: null,
            noiDungNhacViec: null,
            allowFilteringGridPhieuDieuXuLy: true,
            allowFilteringGridSuCo: true,
            columnTemplate: function () {
                return { 
                    template: {
                        template: '<span :id="data.index" class="col-mark-selected-row fa fa-play text-main"></span>',
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
        }
    },
    watch: {
        idSuCoDuocChon(newVal) {

            if (!newVal) {
                this.dsPhieuDieuXuLy = []
                return
            }

            this.thucHienLayDsPhieuDieuXuLy(newVal)
        },
    },
    computed: {
        keoDaiTu() {
            return this.keoDaiTuH * 60 * 60 + this.keoDaiTuM * 60 + this.keoDaiTuS
        },
        keoDaiDen() {
            return this.keoDaiDenH * 60 * 60 + this.keoDaiDenM * 60 + this.keoDaiDenS
        },
        suCoDuocChon() {
            return this.dsSuCo.find(item => item.SUCO_ID == this.idSuCoDuocChon)
        },
        canhBaoDt() {
            return !this.suCoDuocChon ? '' : this.numberFormat(this.suCoDuocChon.CANHBAO_DT)
        }
    },
    async mounted() {
        try {
            this.loading(true)
            let loading1 = this.layDmNhomSuCo()
            let loading2 = this.layDmNguonSuCo()
            let loading3 = this.layDmTinhTrang()
            let loading4 = this.layDmMucDoSuCo()
            await loading1
            await loading2
            await loading3
            await loading4
        } catch (err) {

        } finally {
            this.loading(false)
        }

        if (this.dsNhomSuCo && this.dsNhomSuCo.length > 0) {
            this.nhomSuCoId = this.dsNhomSuCo[0].NHOMSC_ID
            this.onClickBtnTimKiem()
        }
    },
    methods: {
        getColumnTemplateChucNang(parent) {
            return function() {
                return { 
                    template: {
                        template: `
                            <div>
                                <span title="Nghiệm thu">
                                    <button class="btn btn-sm btn-second" :disabled="disableBtnGhiChu" @click="parent.onClickBtnGhiChu(data.PHIEU_NT_ID)">
                                        <span>✓</span>
                                    </button>
                                </span>
                                <span title="Trả phiếu">
                                    <button class="btn btn-sm btn-second" :disabled="disableBtnTraPhieu" @click="parent.onClickBtnTraPhieu(data.PHIEU_NT_ID)">
                                        <span>&#x2190;</span>
                                    </button>
                                </span>
                                <span title="Giao việc">
                                    <button class="btn btn-sm btn-second" :disabled="disableBtnNhacViec" @click="parent.onClickBtnNhacViec(data.PHIEU_XL_ID)">
                                        <span>sms</span>
                                    </button>
                                </span>
                            </div>
                        `,
                        data() {
                            return { 
                                parent: parent,
                                data: { }
                            }
                        },
                        computed: {
                            disableBtnGhiChu() {
                                return !this.data.PHIEU_NT_ID || this.data.TTPH_NT_ID == 4
                            },
                            disableBtnTraPhieu() {
                                return !this.data.PHIEU_NT_ID || this.data.TTPH_NT_ID == 4
                            },
                            disableBtnNhacViec() {
                                return !!this.data.PHIEU_NT_ID
                            }
                        }
                    }
                }
            }
        },
        getColumnTemplateChon(parent) {
            return function() {
                return { 
                    template: {
                        template: `
                            <div class="check-action">
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
                                data: { }
                            }
                        }
                    }
                }
            }
        },
        getColumnTemplateAnhSc(parent) {
            return function() {
                return { 
                    template: {
                        template: `
                            <div style="position: relative;">
                                <button ref="ref-btn-show-img" class="btn btn-sm btn-second" @click="toggleImgPanel">...</button>
                                <div ref="ref-panel-anh-suco" v-show="isShown" class="panel-anh-suco">
                                    <div style="position: absolute; height: 35px; text-align: left; top: 0; padding: 5px 0px">
                                        <span style="color: black;">Hình ảnh sự cố</span>
                                    </div>
                                    <button type="button" class="close" @click="closeImgPanel" style="position: absolute; right: 4px; top: 0;">×</button>
                                    <div style="border: 1px solid #ccc; height: 100%; overflow: auto;">
                                        <div v-for="image in images" style="width: 50%; height: 100%; float: left; padding: 5px; position: relative;">
                                            <img 
                                                v-on:mouseover="onMouseOver(image.presignedUrl)" 
                                                v-on:mouseleave="onMouseLeave" 
                                                :src="image.presignedUrl" alt="" style="height: 100%; width: 100%;" />
                                        </div>
                                    </div>
                                </div>
                            </div>
                        `,
                        data() {
                            return {
                                parent: parent,
                                data: { },
                                showImgPanel: false,
                                images: []
                            }
                        },
                        computed: {
                            isShown() {
                                return this.data.KEY == this.parent.keyPhieuDieuXuLyDuocChon
                            }
                        },
                        watch: {
                            async isShown(newVal) {

                                if (newVal) {
                                    let top = this.$refs['ref-btn-show-img'].getBoundingClientRect().top
                                    let height = this.$refs['ref-btn-show-img'].clientHeight
                                    this.$refs['ref-panel-anh-suco'].style.top = `${top + height + 3}px`
                                }

                                if (!newVal || !this.data.MOTA || this.images.length > 0) {
                                    return
                                }

                                let relativeUrls = this.data.MOTA.split('|')
                                let paths = []

                                try {
                                    this.loading(true)
                                    for (let i = 0; i < relativeUrls.length; i++) {
                                        let relativeUrl = relativeUrls[i]
                                        let presignedUrl = await this.parent.getFileUrl(relativeUrl)
                                        paths.push({relativeUrl, presignedUrl})
                                    }
                                } catch(err) {
                                    this.$root.toastError(err)
                                    return
                                } finally {
                                    this.loading(false)
                                }

                                this.images = paths
                            }
                        },
                        methods: {
                            toggleImgPanel() {

                                if (!this.parent.keyPhieuDieuXuLyDuocChon) {
                                    this.parent.keyPhieuDieuXuLyDuocChon = this.data.KEY
                                    return
                                } 
                                
                                if (this.parent.keyPhieuDieuXuLyDuocChon != this.data.KEY) {
                                    this.parent.keyPhieuDieuXuLyDuocChon = this.data.KEY
                                    return
                                }

                                this.parent.keyPhieuDieuXuLyDuocChon = null
                            },
                            closeImgPanel() {
                                this.parent.keyPhieuDieuXuLyDuocChon = null
                            },
                            onMouseOver(src) {
                                this.parent.zoomImg = src
                                this.parent.showZoomImg = true
                            },
                            onMouseLeave() {
                                this.parent.zoomImg = null
                                this.parent.showZoomImg = false
                            },
                        }
                    }
                }
            }
        },
        numberFormat(number) {
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
        onClickBtnNghiemThu() {

            if (!this.idSuCoDuocChon) {
                return
            }

            let suCo = this.dsSuCo.find(item => item.SUCO_ID == parseInt(this.idSuCoDuocChon))

            // if (suCo.NHOMSC_ID != 1) {
            //     this.$root.toastError(`Nhóm sự cố ${suCo.NHOM_SC} chưa được hỗ trợ`)
            //     return
            // }
            
            let modal = this.$refs['ref-modal-khoaphieu']
            modal.setNhomScId(suCo.NHOMSC_ID)
            modal.setNhomScText(suCo.NHOM_SC)
            modal.setSuCoId(suCo.SUCO_ID)
            modal.setNguonScId(suCo.NGUONSC_ID)
            modal.setKhoaPhieu(false)
            this.$bvModal.show('modal-khoaphieu')
        },
        async thucHienLayDsPhieuDieuXuLy(idSuCo) {
            try {
                this.loading(true)
                let dsPhieuDieuXuLy = await this.layDsPhieuDieuXuLy(idSuCo)
                dsPhieuDieuXuLy.forEach((item, index) => item.KEY = index + 1)
                this.dsPhieuDieuXuLy = dsPhieuDieuXuLy
            } catch (err) {

            } finally {
                this.loading(false)
            }
        },
        async okNhacViec() {
            try {
                this.loading(true)
                await this.xuLyGiaoViec(this.idPhieuXl, this.noiDungNhacViec);
                // let soDt = await this.laySoDtNhacViec(this.idPhieuXl)
                // await sendSms(this, soDt, this.noiDungNhacViec)
            } catch(err){
                this.$root.toastError(err)
                return
            } finally {
                this.loading(false)
            }

            this.$root.toastSuccess('Nhắc việc thành công')
            this.$bvModal.hide('modal-nhacviec')
        },
        onClickBtnNhacViec(idPhieuXl) {
            this.idPhieuXl = idPhieuXl
            this.noiDungNhacViec = 'Xử lý gấp'
            this.$bvModal.show('modal-nhacviec')
        },
        onTraPhieuThanhCong() {
            this.thucHienLayDsPhieuDieuXuLy(this.idSuCoDuocChon)
        },
        onClickBtnTraPhieu(idPhieuNt) {
            this.idPhieuNt = idPhieuNt
            this.$bvModal.show('modal-traphieu')
        },
        async okCapNhatGhiChu() {

            let data = {
                phieuId: this.idPhieuNt,
                ghiChu: this.ghiChuNt
            }

            try {
                this.loading(true)
                await this.ghiChuNghiemThuXuLy(data)
                this.$toast.success('Nghiệm thu thành công')
            } catch (err) {
                this.$toast.error('Nghiệm thu thất bại\n' + err)
                return
            } finally {
                this.loading(false)
            }

            this.$bvModal.hide('modal-ghichu')

            try {
                this.loading(true)

                let dsPhieuDieuXuLy = await this.layDsPhieuDieuXuLy(this.idSuCoDuocChon)
                let afterUpdated = dsPhieuDieuXuLy.filter(item => item.PHIEU_NT_ID == data.phieuId)[0]
                let beforeUpdated = this.dsPhieuDieuXuLy.filter(item => item.PHIEU_NT_ID == data.phieuId)[0]
                afterUpdated.KEY = beforeUpdated.KEY
                
                let grid = this.$refs['grid-phieudieu-xuly'].grid
                grid.batchUpdate({changedRecords: [afterUpdated]})
                grid.setCellValue(afterUpdated.KEY, 'NGAY_NT', afterUpdated.NGAY_NT)
                grid.setCellValue(afterUpdated.KEY, 'GHICHU_NT', afterUpdated.GHICHU_NT)
                grid.setCellValue(afterUpdated.KEY, 'NHANVIEN_NT', afterUpdated.NHANVIEN_NT)
            } catch (err) {

            } finally {
                this.loading(false)
            }
        },
        onClickBtnGhiChu(idPhieuNt) {
            this.idPhieuNt = idPhieuNt
            this.ghiChuNt = 'OK'
            this.$bvModal.show('modal-ghichu')
        },
        onQueryCellInfoSuCo(args) {
            if (!!args.column.field) {
                args.cell.style.setProperty('background-color', `#${args.data.MAUNEN}`, 'important')
                args.cell.style.color = `#${args.data.MAUCHU}`
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
                    divEmpty.style.backgroundColor = 'white'
                    divEmpty.innerHTML = 'Không có bản ghi nào để hiển thị'
                    grid.$el.querySelector('.e-gridcontent').append(divEmpty)
                }
            }
        },
        onClickBtnExcelSuCo() {

            let data = this.dsSuCo

            if (!data || data.length == 0) {
                this.$root.toastError('Không có dữ liệu để xuất')
                return
            }

            let exportData = []

            data.forEach(item => {
                exportData.push({
                    'Mã sự cố' : item.MA_SC,
                    'Mức độ' : item.MUCDO_SC,
                    'Nhóm' : item.NHOM_SC,
                    'Nguồn' : item.NGUON_SC,
                    'Nội dung sự cố' : item.NOIDUNG_SC,
                    'Ngày sự cố' : item.NGAY_SC,
                    'Kéo dài' : item.THOIGIAN,
                    'Trạng thái' : item.TRANGTHAI_SC,
                    'Khóa phiếu' : item.KHOAPHIEU
                })
            })

            let worksheet = XLSX.utils.json_to_sheet(exportData)
            let workbook = XLSX.utils.book_new()
            XLSX.utils.book_append_sheet(workbook, worksheet, "Sheet1")
            XLSX.writeFile(workbook, "export.xlsx")
        },
        onClickBtnExcelPhieuDieuXuLy() {

            let data = this.dsPhieuDieuXuLy

            if (!data || data.length == 0) {
                this.$root.toastError('Không có dữ liệu để xuất')
                return
            }

            let exportData = []

            data.forEach(item => {
                exportData.push({
                    'Đơn vị xử lý': item.DONVI_NHAN,
                    'Ngày giao': item.NGAYGIAO,
                    'Nội dung giao': item.ND_GIAO,
                    'Trạng thái': item.TTPH_XL,
                    'Khẩn': item.KHAN,
                    'Chỉ tiêu': item.CHITIEU_TG,
                    'Còn lại': item.CONLAI,
                    'Ngày XL': item.NGAY_XL,
                    'Ghi chú XL': item.GHICHU_XL,
                    'Nhân viên XL': item.NHANVIEN_XL,
                    'Ngày NT': item.NGAY_NT,
                    'Ghi chú NT': item.GHICHU_NT,
                    'Nhân viên NT': item.NHANVIEN_NT,
                    'Ảnh SC': item.MOTA
                })
            })

            let worksheet = XLSX.utils.json_to_sheet(exportData)
            let workbook = XLSX.utils.book_new()
            XLSX.utils.book_append_sheet(workbook, worksheet, "Sheet1")
            XLSX.writeFile(workbook, "export.xlsx")
        },
        onClickBtnLamMoiPhieuDieuXuLy() {
            if (!!this.idSuCoDuocChon) {
                this.thucHienLayDsPhieuDieuXuLy(this.idSuCoDuocChon)
            }
        },
        toggleExpandDsSuCo() {
            this.expandDsSuCo = !this.expandDsSuCo
        },
        toggleExpandDsPhieuDieuXuLy() {
            this.showTttk = !this.showTttk
            this.expandDsPhieu = !this.expandDsPhieu
        },
        onToggleTttk() {
            this.showTttk = !this.showTttk
        },
        onClickBtnLamMoiTttk() {

            this.maSuCo = null
            this.nguoiBaoId = null
            this.nguonSuCoId = null
            this.tinhTrangId = null
            this.mucDoSuCoId = null
            this.ngaySuCoTu = null
            this.ngaySuCoDen = null
            this.ngayBaoTu = null
            this.ngayBaoDen = null
            this.keoDaiTuH = 0
            this.keoDaiTuM = 0
            this.keoDaiTuS = 0
            this.keoDaiDenH = 0
            this.keoDaiDenM = 0
            this.keoDaiDenS = 0
            this.toanTuKeoDaiTu = toanTu['>=']
            this.toanTuKeoDaiDen = toanTu['<=']
            this.showKeoDaiDen = true
            this.noiDungSuCo = null

            this.chkMaSuCo = false
            this.chkNguoiBao = false
            this.chkMdKhanCap = false
            this.chkNguonSuCo = false
            this.chkTinhTrang = false
            this.chkNoiDungSuCo = false
            this.chkNgaySuCo = false
            this.chkNgayBao = false
            this.chkNgayGiao = false
            this.chkMucDoSuCo = false
            this.chkKeoDaiTu = false
            this.chkKeoDaiDen = false

            if (this.dsNhomSuCo && this.dsNhomSuCo.length > 0) {
                this.nhomSuCoId = this.dsNhomSuCo[0].NHOMSC_ID
                this.onClickBtnTimKiem()
            }
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
        ddmmyyyy(date) {

            if (!date) {
                return ''
            }
            
            date = this.getDateElements(date)
            return `${date.d}/${date.M}/${date.y}`
        },
        ddmmyyyyhhmi(date) {

            if (!date) {
                return ''
            }
            
            date = this.getDateElements(date)
            return `${date.d}/${date.M}/${date.y} ${date.h}:${date.m}`
        },
        onClickBtnKeoDaiDen() {
            switch (this.toanTuKeoDaiDen) {
                case toanTu['<=']:
                    this.toanTuKeoDaiDen = toanTu['<']
                    break
                case toanTu['<']:
                    this.toanTuKeoDaiDen = toanTu['<=']
                    break
            }
        },
        onClickBtnKeoDaiTu() {
            switch (this.toanTuKeoDaiTu) {
                case toanTu['>=']:
                    this.toanTuKeoDaiTu = toanTu['>']
                    this.showKeoDaiDen = true
                    break
                case toanTu['>']:
                    this.toanTuKeoDaiTu = toanTu['=']
                    this.showKeoDaiDen = false
                    break
                case toanTu['=']:
                    this.toanTuKeoDaiTu = toanTu['>=']
                    this.showKeoDaiDen = true
                    break
            }
        },
        onClickBtnTimKiem() {
            this.thucHienTraCuu()
        },
        async thucHienTraCuu() {

            let data = {
                nhomScId: this.nhomSuCoId,
                maSc: !this.chkMaSuCo ? '' : (!this.maSuCo ? '' : this.maSuCo.trim()),
                nguoiBaoId: !this.chkNguoiBao ? '' : (!this.nguoiBaoId ? '' : this.nguoiBaoId),
                nguonScId: !this.chkNguonSuCo ? '' : (!this.nguonSuCoId ? '' : this.nguonSuCoId),
                tinhTrangId: !this.chkTinhTrang ? '' : (!this.tinhTrangId ? '' : this.tinhTrangId),
                noiDungSc: !this.chkNoiDungSuCo ? '' : (!this.noiDungSuCo ? '' : this.noiDungSuCo.trim()),
                ngayScTu: !this.chkNgaySuCo ? null : this.ddmmyyyy(this.ngaySuCoTu),
                ngayScDen: !this.chkNgaySuCo ? null : this.ddmmyyyy(this.ngaySuCoDen),
                ngayBaoTu: !this.chkNgayBao ? null : this.ddmmyyyy(this.ngayBaoTu),
                ngayBaoDen: !this.chkNgayBao ? null :  this.ddmmyyyy(this.ngayBaoDen),
                mucDoScId: !this.chkMucDoSuCo ? '' : (!this.mucDoSuCoId ? '' : this.mucDoSuCoId),
                keoDaiTuOp: !this.chkKeoDaiTu ? '' : layGiaTriToanTu(this.toanTuKeoDaiTu),
                keoDaiTu: !this.chkKeoDaiTu ? '' : this.keoDaiTu,
                keoDaiDenOp: !this.chkKeoDaiDen || !this.showKeoDaiDen ? '' : layGiaTriToanTu(this.toanTuKeoDaiDen),
                keoDaiDen: !this.chkKeoDaiDen || !this.showKeoDaiDen ? '' : this.keoDaiDen
            }

            try {
                this.loading(true)
                await this.traCuuSuCo(data)
                this.idSuCoDuocChon = null
                this.dsPhieuDieuXuLy = []
            } catch (err) {

            } finally {
                this.loading(false)
            }
        },
        async capNhatGhiChu(data) {
            try {
                let response = await this.$root.context.post('/web-suco/ketqua-xuly-suco/capnhat-ghichu', data)
                
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
        async layDsPhieuDieuXuLy(suCoId) {
            try {
                let response = await this.$root.context.get(`/web-suco/qlsc/nghiem-thu/layDsPhieuDieuNt?suCoId=${suCoId}`)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    return response.data
                } else {
                    return []
                }
            } catch (err) {
                return []
            }
        },
        async traCuuSuCo(data) {
            try {
                let response = await this.$root.context.post('/web-suco/qlsc/nghiem-thu/layDsPhieuNghiemThuSc', data)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    response.data.forEach(item => item.KHOAPHIEU = '')
                    this.dsSuCo = response.data
                } else {
                    this.dsSuCo = []
                }
            } catch (err) {
                this.dsSuCo = []
            }
        },
        async layDmMucDoSuCo() {
            try {
                let response = await this.$root.context.get('/web-suco/danhmuc/mucdo-suco')

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    response.data.unshift({MUCDO_ID: 0, MUCDO_SC: 'Tất cả', CANHBAO_DT: -1 })
                    this.dsMucDoSuCo = response.data
                } else {
                    this.dsMucDoSuCo = []
                }
            } catch (err) {
                this.dsMucDoSuCo = []
            }
        },
        async layDmTinhTrang() {
            try {
                let response = await this.$root.context.get('/web-suco/qlsc/nghiem-thu/napDsTinhTrangSc')

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    this.dsTinhTrang = response.data
                } else {
                    this.dsTinhTrang = []
                }
            } catch (err) {
                this.dsTinhTrang = []
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
        async layDmNhomSuCo() {
            try {
                let response = await this.$root.context.get('/web-suco/qlsc/nghiem-thu/napDsNhomSc')

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    this.dsNhomSuCo = response.data
                } else {
                    this.dsNhomSuCo = []
                }
            } catch (err) {
                this.dsNhomSuCo = []
            }
        },
        xuLyGiaoViec: async function (phieu_id, noidung) {
            let rs = await this.$root.context.post(
                "/web-suco/qlsc/nghiem-thu/xuLyGiaoViec",
                {
                    phieu_id: phieu_id,
                    noiDung: noidung
                }
            );
            return rs.data;
        },        
        async laySoDtNhacViec(phieuId) {
            try {
                let response = await this.$root.context.get(`/web-suco/qlsc/nghiem-thu/lay-so-dt-nhac-viec?phieu_id=${phieuId}`)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    return response.data.SO_DT
                }
                
                throw {response: { data: response}}
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
        async ghiChuNghiemThuXuLy(data) {
            try {
                let response = await this.$root.context.post(`/web-suco/qlsc/nghiem-thu/nghiemThuSc`, data)
                
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
        async getFileUrl(src){
            try{
                var rs = await this.$root.context.post(`/web-suco/storage/getPresignedUrl`, {fileSource: src})
                if(rs.data != null)  {
                    return rs.data.result
                }
                throw ''
            } catch(err) {
                throw 'Có lỗi xảy ra trong quá trình lấy đường dẫn file'
            }
        },
    }
}
</script>
<style>
    .nghiemthu-suco .e-grid {
        font-family: "Roboto", Arial !important;
    }

    .nghiemthu-suco .e-grid .col-mark-selected-row {
        font-size: 12px;
        display: none;
    }

    .nghiemthu-suco .e-grid .e-active .col-mark-selected-row {
        display: block;
    }

    .nghiemthu-suco .e-grid .e-filterbarcell {
        padding: 5px 7px !important;
        border-width: 1px 0 0 1px !important;
    }

    .nghiemthu-suco .e-grid th.e-filterbarcell > div input.form-control {
        padding-bottom: 0 !important;
        padding-top: 0 !important;
        height: 26px !important;
    }

    .nghiemthu-suco .e-grid th.e-headercell, 
    .nghiemthu-suco .e-grid th.e-filterbarcell {
        background-color: #BAE7FF !important;
        border-color: white !important;
    }

    .nghiemthu-suco .e-grid th.e-filterbarcell {
        border-width: 1px 0 0 1px !important;
    }

    .nghiemthu-suco .e-grid td.e-active {
        background: #F9E1A9 !important;
    }

    .nghiemthu-suco .e-grid.e-gridhover tr[role='row']:not(.e-editedrow):hover .e-rowcell:not(.e-cellselectionbackground):not(.e-active):not(.e-updatedtd):not(.e-indentcell),
    .nghiemthu-suco .e-grid.e-gridhover tr[role='row']:hover .e-detailrowcollapse:not(.e-cellselectionbackground):not(.e-active):not(.e-updatedtd):not(.e-indentcell),
    .nghiemthu-suco .e-grid.e-gridhover tr[role='row']:hover .e-rowdragdrop:not(.e-cellselectionbackground):not(.e-active):not(.e-updatedtd):not(.e-indentcell),
    .nghiemthu-suco .e-grid.e-rtl .e-gridhover tr[role='row']:hover .e-rowdragdrop:not(.e-cellselectionbackground):not(.e-active):not(.e-updatedtd):not(.e-indentcell),
    .nghiemthu-suco .e-grid.e-gridhover tr[role='row']:hover .e-detailrowexpand:not(.e-cellselectionbackground):not(.e-active):not(.e-updatedtd):not(.e-indentcell) {
        background-color: #FFF9EB !important;
    }

    .nghiemthu-suco .e-grid td.e-rowcell { 
        line-height: 20px !important;
        padding: 5px 7px !important;
    }

    .nghiemthu-suco .e-grid .e-filterdiv.e-fltrinputdiv .e-input-group.e-control-wrapper {
        border: 0 !important;
    }

    .nghiemthu-suco .e-grid .e-filtertext.e-input.e-disable {
        display: none !important;
    }

    .nghiemthu-suco .e-grid tr.e-emptyrow td {
        text-align: center !important;
    }

    .nghiemthu-suco .e-grid div.e-gridheader div.e-headercontent thead tr.e-columnheader th[aria-colindex="1"],
    .nghiemthu-suco .e-grid div.e-gridheader div.e-headercontent thead tr.e-columnheader th[aria-colindex="1"] > div {
        padding: 0 !important;
    }

    .nghiemthu-suco .disable-btn:hover {
        background-color: white !important;
    }

    .nghiemthu-suco .disable-btn a {
        color: #6c757d !important;
    }

    .nghiemthu-suco .disable-btn a:hover {
        cursor: default;
        color: #6c757d;
    }

    .nghiemthu-suco .panel-anh-suco {
        width: 380px; 
        height: 180px; 
        background-color: white; 
        position: fixed; 
        right: 10px;
        z-index: 1;
        padding: 34px 5px 5px 5px;
        border: 1px solid #ccc;
    }

    .nghiemthu-suco .panel-zoom-img {
        position: fixed;
        width: 500px;
        height: 500px;
        background-color: transparent;
        z-index: 99999;
        margin: auto auto;
        left: 0;
        right: 0;
        top: 0;
        bottom: 0;
    }
</style>