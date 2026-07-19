<template src="./index.template.html"></template>
<script>
import XLSX from "xlsx"

const color = {
    RED: 'red',
    GREEN: 'green',
    BLACK: 'black',
    BLUE: 'blue'
}

export default {
    props:{
        modalId: String,
        file: File,
        parent: Object
    },
    data() {
        return {
            isModified: false,
            dsThueBao: [],
            fileExcel: null,
            isDungTienTrinh: false,
            isDangThucThi: false,
            isDaThucThi: false,
            progressBarPercent: 0,
        }
    },
    computed: {
        fileName() {
            return !this.fileExcel ? null : this.fileExcel.name
        }
    },
    methods: {
        onModalHidden() {
            this.dsThueBao = []
            this.fileExcel = null
            this.isDungTienTrinh = false
            this.isDaThucThi = false
            
            if (this.isModified) {
                this.$emit('change')
            }
        },
        onModalShown() {
            this.isModified = false
            this.fileExcel = this.file
            this.onClickBtnNapLai()
        },
        onClickBtnDungTienTrinh() {
            this.isDungTienTrinh = true
        },
        onClickBtnNapLai() {
            
            this.isDaThucThi = false
            
            try {
                this.loading(true)
                this.checkFileUpload(this.fileExcel)
            } catch (error) {
                this.$root.toastError(error)
                return
            } finally {
                this.loading(false)
            }

            const reader = new FileReader()

            reader.onload = async e => {

                const bstr = e.target.result
                const wb = XLSX.read(bstr, { type: "binary" })
                const wsname = wb.SheetNames[0]
                const ws = wb.Sheets[wsname]
                const fileData = XLSX.utils.sheet_to_json(ws, { header: 1 })
                const headerName = fileData[0]

                if (headerName == null || headerName[0] != 'MA_TB') {
                    this.$root.toastError('File có cấu trúc không hợp lệ!')
                    return
                }

                let dsMaThueBao = []

                for (let i = 1; i < fileData.length; i++) {
                    if (!!fileData[i][0]) {
                        dsMaThueBao.push(fileData[i][0])
                    }
                }

                if (!dsMaThueBao || dsMaThueBao.length == 0) {
                    this.$root.toastError('Tệp excel không có dữ liệu')
                    return
                }

                try {
                    this.loading(true)
                    await this.layDsThueBao(dsMaThueBao)
                } catch (error) {
                    this.$root.toastError(error)
                } finally {
                    this.loading(false)
                }
            }

            reader.readAsBinaryString(this.fileExcel)
        },
        onFileChange() {
            this.fileExcel = this.$refs['ref-file-upload'].files[0]
        },
        checkFileUpload: function (file) {

            let message = []

            if (file == null) {
                message.push('Chưa chọn file nào!')
            }

            const fileTypeXlsx = 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
            const fileTypeXls = 'application/vnd.ms-excel'

            if (file.type != fileTypeXlsx && file.type != fileTypeXls) {
                message.push('File không phải định dạng xlsx hoặc xls!')
            }

            if (message.length > 0) {
                throw message.join('\n')
            }
        },
        chooseFile() {
            this.$refs['ref-file-upload'].click()
        },
        async dangKyThueBao(thueBao) {

            let data = {
                thuebao_id: thueBao.THUEBAO_ID,
                ma_tb: thueBao.MA_TB,
                ten_tb: thueBao.TEN_TB,
                diachi_ld: thueBao.DIACHI_LD,
                ghichu: '',
                ne: '',
                kieucc_id: this.parent.kieuCatChuyenId,
                ttcc_id: this.parent.trangThaiId,
                donvi_id: this.parent.veTinhId ? this.parent.veTinhId.split('_')[0] : null,
                ngaybd_dk: this.parent.yyyymmddhhmissminus(this.parent.ngayBatDau),
                ngaykt_dk: this.parent.yyyymmddhhmissminus(this.parent.ngayKetThuc),
                hscc_id: this.parent.getHsccId(),
                chan_baohong: this.parent.chkChuyen119 ? 1 : 0
            }

            await this.parent.themThueBaoDangKyDauChuyen(data)
        },
        async onClickBtnThucThi() {

            let dsThueBaoDuocDangKyThanhCong = []
            this.progressBarPercent = 0
            this.isDaThucThi = true
            this.isDangThucThi = true

            try {
                for (var i = 0; i < this.dsThueBao.length; i++) {
                    
                    if (this.isDungTienTrinh) {
                        break
                    }

                    let thueBao = this.dsThueBao[i]
                    
                    if (!!thueBao.TRANGTHAI) {
                        continue
                    }
                    
                    if (!thueBao.THUEBAO_ID) {
                        thueBao.TRANGTHAI = 'Lỗi: Mã thuê bao trống hoặc không có trong CSDL'
                        thueBao.COLOR = color.RED
                        continue
                    }

                    thueBao.TRANGTHAI = 'Đang thực hiện...'
                    thueBao.COLOR = color.BLUE

                    try {
                        await this.dangKyThueBao(thueBao)
                        thueBao.TRANGTHAI = 'Hoàn thành'
                        thueBao.COLOR = color.GREEN
                        dsThueBaoDuocDangKyThanhCong.push(thueBao.THUEBAO_ID)
                        this.isModified = true
                    } catch(err) {
                        thueBao.TRANGTHAI = 'Lỗi: ' + err
                        thueBao.COLOR = color.RED
                    }

                    let grid = this.$refs['grid-thuebao'].grid
                    grid.batchUpdate({changedRecords: [{...thueBao}]})

                    this.progressBarPercent = ((i+1) / this.dsThueBao.length) * 100
                }
            } catch (err) {
                this.$root.toastError('Có lỗi xảy ra trong quá trình đăng ký.\n' + err)
                return
            } finally {
                this.isDangThucThi = false
                this.isDungTienTrinh = false
                this.progressBarPercent = 0
            }

            let message = []
            message.push('Tiến trình hoàn tất!')
            message.push('Tổng cộng: ' + this.dsThueBao.length)
            message.push('Hoàn thành: ' + dsThueBaoDuocDangKyThanhCong.length)
            message.push('Lỗi: ' + (this.dsThueBao.length - dsThueBaoDuocDangKyThanhCong.length))
            this.$root.toastSuccess(message.join('\n'))
        },
        onQueryCellInfo(args) {
            if (!!args.column.field && args.column.field == 'TRANGTHAI') {
                args.cell.style.color = args.data.COLOR
            }
        },
        async layDsThueBao(dsMaThueBao) {

            let data = {
                ds_ma_tb: dsMaThueBao.join(),
                ds_ma_lt: dsMaThueBao.join(),
                hscc_id: this.parent.getHsccId()
            }

            try {
                let response = await this.$root.context.post(`/web-cabman/hoso-dauchuyen/lay-thongtin-ds-thuebao-theo-ma`, data)

                if (response.error_code === 'BSS-00000000' && response.data != null) {

                    let dsThueBao = dsMaThueBao.map(item => { 
                        return { MA_TB: item, TRANGTHAI: null, COLOR: color.BLACK }
                    })
                    
                    dsThueBao.forEach(item => {
                        
                        let thueBao = response.data.filter(element => { 
                            return `${element.MA_TB}`.toLowerCase() == `${item.MA_TB}`.toLowerCase() 
                        })[0]

                        if (!thueBao) {
                            item.TRANGTHAI = 'Lỗi: Không tìm thấy thông tin thuê bao'
                            item.COLOR = color.RED
                        } else {
                            Object.assign(item, thueBao)

                            if (!!item.TRANGTHAI && item.TRANGTHAI.startsWith('Lỗi')) {
                                item.COLOR = color.RED
                            }
                        }
                    })

                    dsThueBao.forEach((thueBao, index) => thueBao.KEY = index + 1)
                    this.dsThueBao = dsThueBao
                } else {
                    let message = [] 

                    if (!!response.message) {
                        message.push(response.message)
                    }

                    if (!!response.message_detail) {
                        message.push(response.message_detail)
                    }

                    if (message.length > 0) {
                        throw message.join('\n')
                    }
                }
            } catch (err) {
                this.dsThueBao = []

                if (typeof err == "string") {
                    throw err
                } else {
                    throw "Có lỗi xảy ra"
                }
            }
        },

    }
}
</script>
<style>
    .dangky-dauchuyen-theofile {
        position: absolute;
        height: 100%;
        width: 100%;
        z-index: 9;
    }

    .dangky-dauchuyen-theofile .progress{
        top: 0;
        bottom: 0;
        margin: auto;
        position: absolute;
        left: 0;
        right: 0;
    }
</style>