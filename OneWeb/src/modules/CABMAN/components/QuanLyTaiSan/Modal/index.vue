<template src="./index.html"></template>
<style scoped>
    .btn {
        color: #212529;
    }

    .btn-main {
        background: #0176FF;
        color: #fff;
    }

    .btn-main:hover {
        background: #035FCC;
        color: #fff; 
    }

    .btn-second {
        background-color: #fff;
        border-color: #0176FF;
        color: #0176FF;
        font-weight: 600;
    }

    .btn-second:hover {
        background: #0176FF;
        color: #fff;
    }
</style>
<script>
    import { actionName, statePropertyName } from '../../../store/QuanLyTaiSan'
    import { mapActions, mapState } from 'vuex'

    export default {
        name:'ModalQuanLyTaiSan',
        props: ['modalId'],
        created () {
            this.clearState()
            this.createApiManager()
        },
        destroyed () {
            this.clearState()
        },
        computed: {
            ...mapState('cabman/quanLyTaiSan', statePropertyName),
        },
        methods: {
            ...mapActions('cabman/quanLyTaiSan', actionName),
            setButton(kieu) {
                this.buttonState.tsbtnNhapMoiEnabled = false
                this.buttonState.tsbtnGhiLaiEnabled = false

                if (kieu == -1) { //Bat dau
                    this.buttonState.tsbtnGhiLaiEnabled = true
                }
                if (kieu == 0) { //Bat dau
                    this.buttonState.tsbtnNhapMoiEnabled = true
                    this.clear()
                }
                if (kieu == 1) { //Them moi
                    this.buttonState.tsbtnGhiLaiEnabled = true
                    this.clear()
                }
                if (kieu == 2) { //Huy
                    this.buttonState.tsbtnNhapMoiEnabled = true
                    this.clear()
                }
                if (kieu == 3) { //Edit
                    this.buttonState.tsbtnNhapMoiEnabled = true
                    this.buttonState.tsbtnGhiLaiEnabled = true
                }
            },
            clear () {
                this.maTaiSan = ''
                this.tenTaiSan = ''
                this.nguyenGia = 0
            },
            async hienThiDanhSach () {
                try {
                    this.loading(true)
                    await this.layDsTaiSanTheoDuAn(this.duAnId)
                } catch (err) {

                } finally {
                    this.loading(false)
                }
            },
            hienThiThongTinTaiSan () {
                let taiSanInfo = this.tableDsTaiSan.data.filter(item => item.TAISAN_ID === this.tableDsTaiSan.selected)[0]
                this.duAnId = taiSanInfo.DUAN_ID
                this.ttvtId = taiSanInfo.DONVI_ID
                this.maTaiSan = taiSanInfo.MA_TS
                this.tenTaiSan = taiSanInfo.TEN_TS
                this.nguyenGia = !taiSanInfo.GIA ? 0 : taiSanInfo.GIA
            },
            onPageChangeDsTaiSan(pageData) {
                this.tableDsTaiSan.data = pageData

                if (pageData && pageData.length > 0) {
                    this.tableDsTaiSan.selected = pageData[0].TAISAN_ID
                    this.hienThiThongTinTaiSan()
                    this.setButton(3)
                }
            },
            onChangeComboDuAn () {
                this.setButton(1)
                this.hienThiDanhSach()
            },
            onClickRowDsTaiSan (taiSanId) {
                this.tableDsTaiSan.selected = taiSanId
                this.hienThiThongTinTaiSan()
                this.setButton(3)
            },
            onClickButtonXoaTaiSan (taiSanId) {
                this.tableDsTaiSan.selected = taiSanId
                this.showModalXacNhanXoa = true
            },
            onClickButtonNhapMoi () {
                this.setButton(1)
            },
            async onClickButtonGhiLai () {
                let data = {
                    donvi_id: this.ttvtId,
                    duan_id: this.duAnId,
                    gia: this.nguyenGia,
                    ma_ts: this.maTaiSan,
                    ten_ts: this.tenTaiSan
                }
                try {
                    this.loading(true)

                    if (!this.buttonState.tsbtnNhapMoiEnabled 
                        || this.tableDsTaiSan.data.length == 0) {
                        await this.themTaiSan(data)
                        this.$toast.success('Thêm mới tài sản thành công !')
                    } else {
                        data.taisan_id = this.tableDsTaiSan.selected
                        await this.capNhatTaiSan(data)
                        this.$toast.success('Cập nhật tài sản thành công !')
                    }
                } catch (err) {
                    this.$toast.error(err)
                    return
                } finally {
                    this.loading(false)
                }

                this.setButton(1)
                this.hienThiDanhSach()
            },
            async okXoaTaiSan () {
                try {
                    this.loading(true)
                    await this.xoaTaiSan(this.tableDsTaiSan.selected)
                    this.$toast.success('Xóa tài sản thành công')
                    this.setButton(1)
                    this.hienThiDanhSach()
                } catch (err) {
                    this.$toast.error(err)
                } finally {
                    this.loading(false)
                }
            },
            onClickButtonChuyenDuAn () {
                this.duAnChuyenId = this.duAnId
                this.$bvModal.show('modal-chuyen-duan')
            },
            async okChuyenDuAn () {
                try {
                    if (!this.duAnChuyenId) {
                        throw 'Chưa chọn dự án cần chuyển tài sản tới'
                    }
                    this.loading(true)

                    let data = {
                        duan_id: this.duAnChuyenId,
                        taisan_id: this.tableDsTaiSan.selected
                    }

                    await this.chuyenDuAn(data)
                    this.$toast.success('Chuyển tài sản thành công')
                } catch (err) {
                    this.$toast.error(err)
                    return
                } finally {
                    this.loading(false)
                }

                this.setButton(1)
                this.hienThiDanhSach()
            },
            async onEnterInputTimKiem () {
                this.loading(true)
                await this.layDsTaiSanTheoMaTaiSan(this.maTimKiem)
                this.loading(false)

                if (this.dsTaiSanTimKiem.length == 0) {
                    this.$toast.info('Không tìm thấy tài sản.')
                    return
                }

                if (this.dsTaiSanTimKiem.length == 1
                    || this.dsTaiSanTimKiem[0].SELECTED == 1) {
                    this.duAnId = this.dsTaiSanTimKiem[0].DUAN_ID
                    this.setButton(1)
                    this.hienThiDanhSach()
                    return
                }

                this.taiSanTimKiemSelected = this.dsTaiSanTimKiem[0].TAISAN_ID
                this.$bvModal.show('modal-chon-taisan')
            },
            onClickButtonTimTaiSan () {
                this.onEnterInputTimKiem()
            },
            async okChonTaiSanTimKiem () {
                let taiSan = this.dsTaiSanTimKiem.filter(item => item.TAISAN_ID === this.taiSanTimKiemSelected)[0]
                this.duAnId = taiSan.DUAN_ID
                this.setButton(1)
                this.hienThiDanhSach()
            }
        },
        async mounted () {
            try {
                this.loading(true)
                let loading1 = this.layDsDuAn()
                let loading2 = this.layDsTTVT()
                await loading1
                await loading2

                if (this.dsDuAn && this.dsDuAn.length > 0) {
                    this.duAnId = this.dsDuAn[0].DUAN_ID
                }

                if (this.dsTtvt && this.dsTtvt.length > 0) {
                    this.ttvtId = this.dsTtvt[0].DONVI_ID
                }
            } catch (err) {
                console.log(err)
            } finally {
                this.loading(false)
            }

            this.setButton(1)
            this.hienThiDanhSach()
        },
        data () {
            return {
                duAnId: null,
                duAnChuyenId: null,
                ttvtId: null,
                maTaiSan: null,
                nguyenGia: 0,
                tenTaiSan: null,
                maTimKiem: null,
                buttonState: {
                    tsbtnNhapMoiEnabled: false,
                    tsbtnGhiLaiEnabled: false
                },
                tableDsTaiSan: { data: [], selected: null},
                showModalXacNhanXoa: false,
                taiSanTimKiemSelected: null
            }
        }
    }
</script>

    