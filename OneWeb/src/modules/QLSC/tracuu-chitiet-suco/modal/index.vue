<template src="./index.template.html"></template>
<script>
import XLSX from "xlsx"
export default {
    props:{
        modalId: String
    },
    data() {
        return {
            dsNguonSuCo: [],
            dsTrangThaiSuCo: [],
            dsBaoTon: [],
            dsSuCo: [],
            dsTienTrinh: [],
            dsCongBiAnhHuong: [],
            dsNhanVienXuLy: [],
            nguonSuCoId: null,
            trangThaiSuCoId: null,
            chkNgayYeuCau: false,
            chkNgayTiepNhan: false,
            ngayYeuCauTu: new Date(),
            ngayYeuCauDen: new Date(),
            ngayTiepNhanTu: new Date(),
            ngayTiepNhanDen: new Date(),
            showTimKiemPanel: true,
            maSuCo: null,
            collapseBangSuCo: false,
            showFilter: true,
            idPhieuDuocChon: null,
            idSuCoDuocChon: null,
            tabDuocChon: 1
        }
    },
    watch: {
        dsSuCo(newVal) {
            if (!newVal || newVal.length == 0) {
                this.dsTienTrinh = []
                this.dsNhanVienXuLy = []
                this.dsBaoTon = []
                this.idSuCoDuocChon = null
                this.idPhieuDuocChon = null
                return
            }

            this.$refs['grid-suco'].setCurrentSelectedRow(0)
        },
        dsTienTrinh(newVal) {
            if (!!newVal && newVal.length > 0) {
                this.$refs['grid-tientrinh'].setCurrentSelectedRow(0)
            }
        }
    },
    methods: {
        onClickBtnExcelDsTienTrinh() {

            if (!this.dsTienTrinh || this.dsTienTrinh.length == 0) {
                this.$toast.error('Không có dữ liệu để xuất')
                return
            }

            let dsHuongGiao = this.dsTienTrinh.map(item => item.HUONGGIAO)
            dsHuongGiao = [...new Set(dsHuongGiao)]

            let exportData = []

            dsHuongGiao.forEach(element => {
                exportData.push({
                    'Hướng giao': element,
                    'Đơn vị giao': null,
                    'Ngày giao': null,
                    'Nhân viên giao': null,
                    'Nội dung giao': null,
                    'Đơn vị nhận': null,
                    'Ngày trả': null,
                    'Nội dung trả': null,
                    'Ngày TH': null,
                    'Nhân viên TH': null,
                    'Ghi chú TH': null,
                    'Trạng thái phiếu': null,
                })

                this.dsTienTrinh.forEach(item => {
                    if (item.HUONGGIAO === element) {
                        exportData.push({
                            'Hướng giao': '',
                            'Đơn vị giao': item.DV_GIAO,
                            'Ngày giao': item.NGAYGIAO_F,
                            'Nhân viên giao': item.NV_GIAO,
                            'Nội dung giao': item.ND_GIAO,
                            'Đơn vị nhận': item.DV_NHAN,
                            'Ngày trả': item.NGAY_TRA_F,
                            'Nội dung trả': item.ND_TRA,
                            'Ngày TH': item.NGAY_TH_F,
                            'Nhân viên TH': item.NV_TH,
                            'Ghi chú TH': item.GHICHU_TH,
                            'Trạng thái phiếu': item.TRANGTHAI_PH
                        })
                    }
                })  
            })

            let worksheet = XLSX.utils.json_to_sheet(exportData)
            let workbook = XLSX.utils.book_new()
            XLSX.utils.book_append_sheet(workbook, worksheet, "Sheet1")
            XLSX.writeFile(workbook, "ds_tientrinh.xlsx")
        },
        onClickBtnExcelThongTinTienTrinh() {

            if ([1, 2].indexOf(this.tabDuocChon) < 0) {
                return
            }

            let exportData = []
            let fileName = ''

            if (this.tabDuocChon == 1) {

                fileName = 'ds_nhanvien'

                this.dsNhanVienXuLy.forEach(item => {
                    exportData.push({
                        'Ngày giao': item.NGAYGIAO,
                        'Mã NV': item.MA_NV,
                        'Tên nhân viên': item.TEN_NV,
                        'Đơn vị': item.TEN_DV,
                        'Điện thoại': item.SO_DT,
                        'Nhiệm vụ': item.NHIEMVU,
                        'Trạng thái': item.TRANGTHAI,
                        'Ghi chú': item.GHICHU
                    })
                })  
            } else {

                fileName = 'ds_baoton'

                this.dsBaoTon.forEach(item => {
                    exportData.push({
                        'Ngày báo tồn': item.NGAY_BT,
                        'Ghi chú': item.GHICHU,
                        'Người báo tồn': item.NHANVIEN_BT,
                        'Đơn vị báo tồn': item.DONVI_BT,
                        'Ngày cập nhật': item.NGAY_CN,
                        'Người cập nhật': item.NGUOI_CN
                    })
                })  
            }

            if (exportData.length == 0) {
                this.$toast.error('Không có dữ liệu để xuất')
                return
            }

            let worksheet = XLSX.utils.json_to_sheet(exportData)
            let workbook = XLSX.utils.book_new()
            XLSX.utils.book_append_sheet(workbook, worksheet, "Sheet1")
            XLSX.writeFile(workbook, fileName + '.xlsx')
        },
        onClickBtnXuatExcelDsSuCo() {

            if (!this.dsSuCo || this.dsSuCo.length == 0) {
                this.$toast.error('Không có dữ liệu để xuất')
                return
            }

            let exportData = []

            this.dsSuCo.forEach(item => {
                exportData.push({
                    'Nhóm': item.NHOM_SC,
                    'Mã sự cố': item.MA_SC,
                    'Nội dung sự cố': item.NOIDUNG_SC,
                    'Trạng thái': item.TRANGTHAI_SC,
                    'Mức độ': item.MUCDO_SC,
                    'Ngày sự cố': item.NGAY_SC,
                    'Kéo dài': item.THOIGIAN,
                    'Ngày báo': item.NGAY_TB,
                    'Người báo': item.NHANVIEN_TB,
                    'Đơn vị báo': item.DONVI_TB,
                    'Ngày TN': item.NGAY_TN,
                    'Người TN': item.NHANVIEN_TN
                })
            })   

            let worksheet = XLSX.utils.json_to_sheet(exportData)
            let workbook = XLSX.utils.book_new()
            XLSX.utils.book_append_sheet(workbook, worksheet, "Sheet1")
            XLSX.writeFile(workbook, "ds_suco.xlsx")
        },
        onClickRowTableDsSuCo(data) {
            if (!!data) {
                this.idSuCoDuocChon = data.SUCO_ID
                this.thucHienLayDsTienTrinh()
            }
        },
        async thucHienLayDsTienTrinh() {

            if (!this.idSuCoDuocChon) {
                return
            }

            try {
                this.loading(true)
                await this.layDsTienTrinh(this.idSuCoDuocChon)
            } catch (err) {

            } finally {
                this.loading(false)
            }
        },
        onClickRowTableDsTienTrinh(data) {
            this.idPhieuDuocChon = data.PHIEU_ID
            this.layThongTinTienTrinh()
        },
        async layThongTinTienTrinh() {

            if (!this.idPhieuDuocChon) {
                return
            }

            try {
                this.loading(true)
                let loading1 = this.layDsNhanVienXuLy(this.idPhieuDuocChon)
                let loading2 = this.layDsBaoTon(this.idPhieuDuocChon)
                await loading1
                await loading2
            } catch (err) {

            } finally {
                this.loading(false)
            }
        },
        async onClickBtnLayThongTin() {

            let data = {
                donViId: 0,
                maSuCo: !this.maSuCo ? '' : this.maSuCo,
                nguonSuCoId: this.nguonSuCoId,
                trangThaiSuCoID: this.trangThaiSuCoId,
                tuNgayYC: this.chkNgayYeuCau ? this.ngayYeuCauTu : '',
                denNgayYC: this.chkNgayYeuCau ? this.ngayYeuCauDen : '',
                tuNgayHT: this.chkNgayTiepNhan ? this.ngayTiepNhanTu : '',
                denNgayHT: this.chkNgayTiepNhan ? this.ngayTiepNhanDen : ''
            }
            
            try {
                this.loading(true)
                await this.traCuuSuCo(data)

                if (this.dsSuCo.length == 0) {
                    this.$toast.error('Không tìm thấy dữ liệu')
                }
            } catch (err) {

            } finally {
                this.loading(false)
            }
        },
        onClickBtnArrow() {
            this.showTimKiemPanel = !this.showTimKiemPanel
        },
        async onModalShown() {

            this.dsSuCo = []
            this.dsTienTrinh = []
            this.dsNhanVienXuLy = []
            this.dsBaoTon = []
            this.idSuCoDuocChon = null
            this.idPhieuDuocChon = null

            this.$refs['ref-ma-suco'].focus()
            this.showTimKiemPanel = true

            try {
                this.loading(true)
                let loading1 = this.layDmNguonSuCo()
                let loading2 =  this.layDmTrangThaiSuCo()
                await loading1
                await loading2
            } catch (err) {

            } finally {
                this.loading(false)
            }

            if (this.dsNguonSuCo && this.dsNguonSuCo.length > 0) {
                this.nguonSuCoId = this.dsNguonSuCo[0].NGUONSC_ID
            }

            if (this.dsTrangThaiSuCo && this.dsTrangThaiSuCo.length > 0) {
                this.trangThaiSuCoId = this.dsTrangThaiSuCo[0].TTSC_ID
            }
        },
        async layDmNguonSuCo() {
            try {
                let response = await this.$root.context.get('/web-suco/danhmuc/nguon-suco')

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    this.dsNguonSuCo = response.data
                } else {
                    this.dsNguonSuCo = []
                }
            } catch (err) {
                this.dsNguonSuCo = []
            }
        },
        async layDmTrangThaiSuCo() {
            try {
                let response = await this.$root.context.get('/web-suco/danhmuc/trangthai-suco')

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    this.dsTrangThaiSuCo = response.data
                } else {
                    this.dsTrangThaiSuCo = []
                }
            } catch (err) {
                this.dsTrangThaiSuCo = []
            }
        },
        async traCuuSuCo(data) {
            try {
                let response = await this.$root.context.post('/web-suco/TienTrinhSuCo/DanhSachSuCo', data)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    this.dsSuCo = response.data
                } else {
                    this.dsSuCo = []
                }
            } catch (err) {
                this.dsSuCo = []
            }
        },
        async layDsTienTrinh(suCoId) {
            try {
                let response = await this.$root.context.get(`/web-suco/tracuu/suco?suco_id=${suCoId}`)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    this.dsTienTrinh = response.data
                } else {
                    this.dsTienTrinh = []
                }
            } catch (err) {
                this.dsTienTrinh = []
            }
        },
        async layDsCongBiAnhHuong(suCoId) {
            try {
                let response = await this.$root.context.post(`web-suco/tracuu/suco/ds-cong-bi-anhhuong?suco_id=${suCoId}`)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    this.dsCongBiAnhHuong = response.data
                } else {
                    this.dsCongBiAnhHuong = []
                }
            } catch (err) {
                this.dsCongBiAnhHuong = []
            }
        },
        async layDsNhanVienXuLy(phieuId) {
            try {
                let response = await this.$root.context.get(`web-suco/tracuu/suco/ds-nhanvien-xuly?phieu_id=${phieuId}&suco_ptm_id=-1`)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    response.data.forEach(item => item.NGAYGIAO = this.ddmmyyyyhhmi(item.NGAYGIAO))
                    this.dsNhanVienXuLy = response.data
                } else {
                    this.dsNhanVienXuLy = []
                }
            } catch (err) {
                this.dsNhanVienXuLy = []
            }
        },
        async layDsBaoTon(phieuId) {
            try {
                let response = await this.$root.context.get(`web-suco/tracuu/suco/ds-bao-ton?phieu_id=${phieuId}&suco_ptm_id=-1`)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    response.data.forEach(item => {
                        item.NGAY_BT = this.ddmmyyyyhhmi(item.NGAY_BT)
                        item.NGAY_CN = this.ddmmyyyyhhmi(item.NGAY_CN)
                    })
                    this.dsBaoTon = response.data
                } else {
                    this.dsBaoTon = []
                }
            } catch (err) {
                this.dsBaoTon = []
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
        ddmmyyyyhhmi(date) {

            if (!date) {
                return ''
            }
            
            date = this.getDateElements(date)
            return `${date.d}/${date.M}/${date.y} ${date.h}:${date.m}`
        },
    }
}
</script>