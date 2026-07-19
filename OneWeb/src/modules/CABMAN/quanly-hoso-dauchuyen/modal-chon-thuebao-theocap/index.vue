<template src="./index.template.html"></template>
<script>
import BssPagination2 from '@/components/BssPagination_2'

const color = {
    RED: 'red',
    GREEN: 'green',
    BLACK: 'black',
    BLUE: 'blue'
}

export default {
    components: {BssPagination2},
    props:{
        modalId: String,
        parent: Object
    },
    data() {
        return {
            dsTtvt: [],
            dsTo: [],
            dsTramTbi: [],
            dsNhomLc: [],
            dsCapGoc: [],
            dsCapNgon: [],
            dsThueBao: [],
            ttvtId: null,
            toId: null,
            tramTbiId: null,
            nhomLcId: null,
            capGocId: null,
            kieuHienThiThueBao: 1,
            keyTableCapNgon: 1,
            capNgonDuocChon: null,
            dsThueBaoIdDuocChon: [],
            selectAllThueBao: false,
            isModified: false
        }
    },
    computed: {
        disableBtnThucThi() {
            return !this.dsThueBao || this.dsThueBao.length == 0
        },
        disableSelectAllThueBao() {
            return !this.dsThueBao.some(item => item.ALLOW_SELECT)
        }
    },
    watch: {
        async ttvtId(newVal) {

            if (!newVal) {
                this.dsTo = []
                this.toId = null
                return
            }

            try {
                this.loading(true)
                await this.layDsTo(newVal)
            } catch (err) {

            } finally {
                this.loading(false)
            }

            if (this.dsTo && this.dsTo.length > 0) {
                this.toId = this.dsTo[0].DONVI_ID
            } else {
                this.toId = null
            }
        },
        async toId(newVal) {

            if (!newVal) {
                this.dsTramTbi = []
                this.tramTbiId = null
                return
            }

            try {
                this.loading(true)
                await this.layDsTramTbi(newVal)
            } catch (err) {

            } finally {
                this.loading(false)
            }

            if (this.dsTramTbi && this.dsTramTbi.length > 0) {
                this.tramTbiId = this.dsTramTbi[0].DONVI_ID
            } else {
                this.tramTbiId = null
            }
        },
        tramTbiId(newVal) {

            if (!newVal) {
                this.dsCapGoc = []
                return
            }

            this.thucHienLayDsCapGoc()
        },
        nhomLcId(newVal) {

            if (!newVal) {
                this.dsCapGoc = []
                return
            }

            this.thucHienLayDsCapGoc()
        },
        capGocId(newVal) {

            if (!newVal || this.kieuHienThiThueBao == 1/*theo cáp gốc*/) {
                this.dsCapNgon = []
                this.capNgonDuocChon = null
                return
            }

            if (this.kieuHienThiThueBao == 2/*theo cáp ngọn*/) {
                this.thucHienLayDsCapNgon()
            }
        },
        kieuHienThiThueBao(newVal) {

            if (newVal == 1/*theo cáp gốc*/ || !this.capGocId) {
                this.dsCapNgon = []
                this.capNgonDuocChon = null
                return
            } 

            if (newVal == 2/*theo cáp ngọn*/) {
                this.thucHienLayDsCapNgon()
            }
        },
        dsThueBaoIdDuocChon(newValue) {
            this.selectAllThueBao = newValue.length > 0 && newValue.length == this.dsThueBao.filter(item => item.ALLOW_SELECT).length
        },
        selectAllThueBao(newValue) {
            
            if (newValue) {
                this.dsThueBaoIdDuocChon = this.dsThueBao.filter(item => item.ALLOW_SELECT).map(item => item.THUEBAO_ID)
                return
            } 
            
            if (this.dsThueBaoIdDuocChon.length > 0 
                && this.dsThueBaoIdDuocChon.length == this.dsThueBao.filter(item => item.ALLOW_SELECT).length) {
                this.dsThueBaoIdDuocChon = []
            }
        },
        dsThueBao(newVal) {
            this.dsThueBaoIdDuocChon = []
        },
    },
    methods: {
        onModalHidden() {
            this.dsTtvt = []
            this.dsTo = []
            this.dsTramTbi = []
            this.dsNhomLc = []
            this.dsCapGoc = []
            this.dsCapNgon = []
            this.dsThueBao = []
            this.dsThueBaoIdDuocChon = []
            this.kieuHienThiThueBao = 1
            this.ttvtId = null
            this.toId = null
            this.tramTbiId = null
            this.nhomLcId = null
            this.capGocId = null
            this.capNgonDuocChon = null
            this.selectAllThueBao = false

            if (this.isModified) {
                this.$emit('change')
            }
        },
        async onModalShown() {

            this.isModified = false

            try {
                this.loading(true)
                let loading1 = this.layDsTtvt()
                let loading2 = this.layDsNhomLoaiCap()
                await loading1
                await loading2
            } catch (err) {

            } finally {
                this.loading(false)
            }

            if (this.dsTtvt && this.dsTtvt.length > 0) {
                this.ttvtId = this.dsTtvt[0].DONVI_ID
            }

            if (this.dsNhomLc && this.dsNhomLc.length > 0) {
                this.nhomLcId = this.dsNhomLc[0].NHOMLC_ID
            }

            this.kieuHienThiThueBao = 2
        },
        onQueryCellInfo(args) {
            if (args.column.field == 'TRANGTHAI') {
                args.cell.style.color = args.data.COLOR
            }
        },
        getColumnTemplateChon(parent) {
            return function() {
                return {
                    template: {
                        template: `
                            <div class="check-action">
                                <input type="checkbox" class="check" :value="data.THUEBAO_ID" v-model="parent.dsThueBaoIdDuocChon" :disabled="!data.ALLOW_SELECT">
                                <span class="name"></span>
                            </div>
                        `,
                        data() {
                            return {
                                parent: parent,
                                data: {}
                            }
                        }
                    }
                }
            }
        },
        getHeaderTemplateChon(parent) {
            return function() {
                return {
                    template: {
                        template: `
                            <div class="check-action">
                                <input type="checkbox" class="check" v-model="parent.selectAllThueBao" :disabled="parent.disableSelectAllThueBao">
                                <span class="name"></span>
                            </div>
                        `,
                        data() {
                            return {
                                parent: parent,
                                data: {}
                            }
                        }
                    }
                }
            }
        },
        async dangKyThueBao(thueBao) {

            let data = {
                thuebao_id: thueBao.THUEBAO_ID,
                ma_tb: thueBao.MA_TB,
                ten_tb: thueBao.TEN_TB,
                diachi_ld: thueBao.DIACHI_LD,
                ghichu: thueBao.DAUCUOI_ID,
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

            if (!this.dsThueBaoIdDuocChon || this.dsThueBaoIdDuocChon.length == 0) {
                this.$toast.error('Chưa tích chọn thuê bao cần đăng ký đấu chuyển.')
                return 
            }

            let dsThueBaoDuocChon = this.dsThueBao.filter(item => this.dsThueBaoIdDuocChon.indexOf(item.THUEBAO_ID) >= 0)
            let dsThueBaoDuocDangKyThanhCong = []
            
            try {
                this.loading(true)

                for (var i = 0; i < dsThueBaoDuocChon.length; i++) {
                    
                    let thueBao = dsThueBaoDuocChon[i]
                    thueBao.TRANGTHAI = 'Đang thực hiện...'
                    thueBao.COLOR = color.BLUE

                    try {
                        await this.dangKyThueBao(thueBao)
                        thueBao.TRANGTHAI = 'Hoàn thành'
                        thueBao.COLOR = color.GREEN
                        thueBao.ALLOW_SELECT = false
                        dsThueBaoDuocDangKyThanhCong.push(thueBao.THUEBAO_ID)
                        this.isModified = true
                    } catch(err) {
                        thueBao.TRANGTHAI = 'Lỗi: ' + err 
                        thueBao.COLOR = color.RED
                    }
                }
            } catch (err) {
                this.$toast.error('Có lỗi xảy ra trong quá trình đăng ký.\n' + err)
                return
            } finally {
                this.loading(false)
            }

            let message = []
            message.push('Tiến trình đăng ký đấu chuyển hoàn tất!')
            message.push('Tổng cộng ' + dsThueBaoDuocChon.length + ' thuê bao trên danh sách')
            message.push('Đăng ký thành công cho: ' + dsThueBaoDuocDangKyThanhCong.length + ' thuê bao')
            this.$root.toastSuccess(message.join('\n'))

            if (dsThueBaoDuocDangKyThanhCong.length > 0 
                && dsThueBaoDuocDangKyThanhCong.length == dsThueBaoDuocChon.length) {
                this.$bvModal.hide(this.modalId)
            }
        },
        onSelectRowCapNgon(rowData) {
            this.capNgonDuocChon = rowData
            this.thucHienLayDsThueBaoTheoKetCuoi()
        },
        onClickBtnTimKiem() {

            if (this.kieuHienThiThueBao == 1/*theo cáp gốc*/) {

                if (!this.capGocId) {
                    this.$toast.error('Chưa chọn cáp gốc')
                    return
                }

                this.thucHienLayDsThueBaoTheoCapGoc()
            } 

            if (this.kieuHienThiThueBao == 2/*theo cáp ngọn*/) {

                if (!this.capNgonDuocChon) {
                    this.$toast.error('Chưa chọn cáp ngọn')
                    return
                }
                
                this.thucHienLayDsThueBaoTheoKetCuoi()
            }
        },
        async thucHienLayDsThueBaoTheoKetCuoi() {
            try {
                this.loading(true)
                await this.layDsThueBaoTheoKetCuoi(this.capNgonDuocChon.KETCUOI_ID)
            } catch (err) {

            } finally {
                this.loading(false)
            }
        },
        async thucHienLayDsThueBaoTheoCapGoc() {
            try {
                this.loading(true)
                await this.layDsThueBaoTheoCapGoc(this.capGocId)
            } catch (err) {

            } finally {
                this.loading(false)
            }
        },
        async thucHienLayDsCapNgon() {
            try {
                this.loading(true)
                await this.layDsCapNgon(this.capGocId)
            } catch (err) {

            } finally {
                this.loading(false)
            }
        },
        async thucHienLayDsCapGoc() {

            let data = {
                loai_nhom_cap: this.nhomLcId,
                tong_dai_id: this.tramTbiId
            }

            try {
                this.loading(true)
                await this.layDsCapGoc(data)
            } catch (err) {

            } finally {
                this.loading(false)
            }

            if (this.dsCapGoc && this.dsCapGoc.length > 0) {
                this.capGocId = this.dsCapGoc[0].CAP_ID
            } else {
                this.capGocId = null
            }
        },
        async layDsTtvt() {
            try {
                let response = await this.$root.context.post('/web-ecms/danhmuc/layDsTTVTTheoNVTrongToken')

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    this.dsTtvt = response.data
                } else {
                    this.dsTtvt = []
                }
            } catch (err) {
                this.dsTtvt = []
            }
        },
        async layDsTo(ttvtId) {
            try {
                let response = await this.$root.context.post(`/web-ecms/danhmuc/layDsToKTTheoNVTrongTokenVaDonVi`, {id: ttvtId})

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    this.dsTo = response.data
                } else {
                    this.dsTo = []
                }
            } catch (err) {
                this.dsTo = []
            }
        },
        async layDsTramTbi(toId) {
            try {
                let response = await this.$root.context.post(`/web-ecms/danhmuc/layDsVeTinhTheoToQl`, {id: toId})

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    this.dsTramTbi = response.data
                } else {
                    this.dsTramTbi = []
                }
            } catch (err) {
                this.dsTramTbi = []
            }
        },
        async layDsNhomLoaiCap() {
            try {
                let response = await this.$root.context.get(`/web-cabman/danhmuc/layDsNhomLoaiCap`)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    this.dsNhomLc = response.data
                } else {
                    this.dsNhomLc = []
                }
            } catch (err) {
                this.dsNhomLc = []
            }
        },
        async layDsCapGoc(data) {
            try {
                let response = await this.$root.context.get(`/web-cabman/bando_tuyencot/ds_cap_goc`, data)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    this.dsCapGoc = response.data
                } else {
                    this.dsCapGoc = []
                }
            } catch (err) {
                this.dsCapGoc = []
            }
        },
        async layDsCapNgon(capGocId) {
            try {
                let response = await this.$root.context.get(`/web-cabman/cap/lay-capngon-theo-capgoc?capgoc_id=${capGocId}`)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    this.dsCapNgon = response.data
                } else {
                    this.dsCapNgon = []
                }
            } catch (err) {
                this.dsCapNgon = []
            }
        },
        async layDsThueBaoTheoCapGoc(capGocId) {
            try {
                let response = await this.$root.context.get(`/web-cabman/hoso-dauchuyen/danhsach-thuebao-capgoc?capgoc_id=${capGocId}`)

                if (response.error_code === 'BSS-00000000' && response.data != null) {

                    response.data.forEach((item, index) => {

                        item.STT = index + 1
                        item.THUEBAO_ID = item.DB_ID

                        if (parseInt(item.DANGCATCHUYEN) == 1) {
                            item.TRANGTHAI = 'Lỗi: Thuê bao đã đăng ký đấu chuyển'
                            item.COLOR = color.RED
                            item.ALLOW_SELECT = false
                        } else {
                            item.TRANGTHAI = null
                            item.COLOR = color.BLACK
                            item.ALLOW_SELECT = true
                        }
                    })

                    this.dsThueBao = response.data
                } else {
                    this.dsThueBao = []
                }
            } catch (err) {
                this.dsThueBao = []
            }
        },
        async layDsThueBaoTheoKetCuoi(ketCuoiId) {
            try {
                let response = await this.$root.context.get(`/web-cabman/hoso-dauchuyen/danhsach-thuebao-ketcuoi?ketcuoi_id=${ketCuoiId}`)

                if (response.error_code === 'BSS-00000000' && response.data != null) {

                    response.data.forEach((item, index) => {

                        item.STT = index + 1
                        item.THUEBAO_ID = item.DB_ID

                        if (parseInt(item.DANGCATCHUYEN) == 1) {
                            item.TRANGTHAI = 'Lỗi: Thuê bao đã đăng ký đấu chuyển'
                            item.COLOR = color.RED
                            item.ALLOW_SELECT = false
                        } else {
                            item.TRANGTHAI = null
                            item.COLOR = color.BLACK
                            item.ALLOW_SELECT = true
                        }
                    })

                    this.dsThueBao = response.data
                } else {
                    this.dsThueBao = []
                }
            } catch (err) {
                this.dsThueBao = []
            }
        },
    }
}
</script>
<style>
    .hsdc-chon-thuebao-theo-cap .grid-thuebao .e-grid div.e-gridheader div.e-headercontent thead tr.e-columnheader th[aria-colindex="1"],
    .hsdc-chon-thuebao-theo-cap .grid-thuebao .e-grid div.e-gridheader div.e-headercontent thead tr.e-columnheader th[aria-colindex="1"] > div {
        padding: 0 !important;
    }
</style>