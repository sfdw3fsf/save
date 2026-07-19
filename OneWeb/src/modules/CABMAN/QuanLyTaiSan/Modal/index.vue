<template src="./index.template.html"></template>
<style>
    div#popupTCTTTB___BV_modal_outer_ {
        z-index: 10 !important;
        margin-top: 100px;
    }

    .dashboard-header {
        z-index: 9 !important;
    }

    .quanly-taisan .disable-btn:hover {
        background-color: white !important;
    }

    .quanly-taisan .disable-btn a {
        color: #6c757d !important;
    }

    .quanly-taisan .disable-btn a:hover {
        cursor: default;
        color: #6c757d;
    }
</style>
<script>
    import BssErrorMarker from '@/components/BssErrorMarker'
    import BssRequiredMarker from '@/components/BssRequiredMarker'
    import { required, maxLength } from 'vuelidate/lib/validators'

    export default {
        name:'ModalQuanLyTaiSan',
        components: { BssErrorMarker, BssRequiredMarker },
        props: ['modalId'],
        data () {
            return {
                parameters: {
                    duAnId: null
                },
                duAnId: null,
                duAnChuyenId: null,
                loiChuyenDuAn: false,
                ttvtId: null,
                maTaiSan: null,
                nguyenGia: 0,
                tenTaiSan: null,
                maTimKiem: null,
                buttonState: {
                    tsbtnNhapMoiEnabled: false,
                    tsbtnGhiLaiEnabled: true
                },
                showModalXacNhanXoa: false,
                taiSanTimKiemSelected: null,
                dsTaiSan: [],
                dsTaiSanTimKiem: [],
                dsDuAn: [],
                dsTtvt: [],
                idTaiSanDuocChon: null,
                duAnSelected: null
            }
        },
        watch: {
            duAnChuyenId (newVal) {
                this.loiChuyenDuAn = false
            }
        },
        methods: {
            setDuAnId(duAnId) {
                this.parameters.duAnId = duAnId
            },
            onModalHidden() {
                this.parameters.duAnId = null
                this.duAnId = null
                this.duAnChuyenId = null
                this.loiChuyenDuAn = false
                this.ttvtId = null
                this.maTaiSan = null
                this.nguyenGia = 0
                this.tenTaiSan = null
                this.maTimKiem = null
                this.buttonState.tsbtnNhapMoiEnabled = false
                this.buttonState.tsbtnGhiLaiEnabled = true
                this.showModalXacNhanXoa = false
                this.taiSanTimKiemSelected = null
                this.dsTaiSan = []
                this.dsTaiSanTimKiem = []
                this.dsDuAn = []
                this.dsTtvt = []
                this.idTaiSanDuocChon = null
                this.$v.$reset()
            },
            async onModalShown() {

                this.$refs['input-timkiem'].focus()

                try {
                    this.loading(true)
                    await this.layDsDuAn()
                    await this.layDsTTVT()

                    if (this.dsTtvt && this.dsTtvt.length > 0) {
                        this.ttvtId = this.dsTtvt[0].DONVI_ID
                    }

                    if (this.dsDuAn.length > 0) {

                        let duAn = this.dsDuAn.find(item => item.DUAN_ID == this.parameters.duAnId)

                        if (!!duAn) {
                            setTimeout(() => {
                                this.$refs['ref-combo-duan'].$children[0].selectRowByRowData(duAn['DUAN_ID'])
                            }, 300)
                        }
                    }
                } finally {
                    this.loading(false)
                }

                this.setButton(1)
            },
            onSelectDuAnChuyen(data) {
                this.duAnChuyenId = !data ? null : data.DUAN_ID
            },
            getColumnTemplateXoa(parent) {
                return function() {
                    return {
                        template: {
                            template: `
                                <a href="javascript:void(0)" class="padt1 padb1 padl4 padr3 lh20 btn btn-main" @click="parent.onClickButtonXoaTaiSan(data.TAISAN_ID)">
                                    <span class="-ap icon-close"></span>
                                </a>
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
            onSelectDuAn(data) {

                if (!data) {
                    this.duAnId = null
                    return
                }

                this.duAnId = data.DUAN_ID
                this.setButton(1)
                this.hienThiDanhSach()
            },
            async layDsDuAn () {
                try {
                    let response = await this.$root.context.post('/web-cabman/duan/danhsach')

                    if (response.error_code === 'BSS-00000000' && response.data != null) {
                        this.dsDuAn = response.data
                    } else {
                        this.dsDuAn = []
                    }
                } catch (err) {
                    this.dsDuAn = []
                }
            },
            async layDsTTVT () {
                try {
                    let response = await this.$root.context.get('/web-cabman/bando_tuyencot/dsttvt_theonv')

                    if (response.error_code === 'BSS-00000000' && response.data != null) {
                        this.dsTtvt = response.data
                    } else {
                        this.dsTtvt = []
                    }
                } catch (err) {
                    this.dsTtvt = []
                }
            },
            async layDsTaiSanTheoDuAn(duAnId) {
                try {
                    let response = await this.$root.context.get('/web-cabman/taisan/danhSachTaiSanTheoDuAn', {duAnId})

                    if (response.error_code === 'BSS-00000000' && response.data != null) {
                        this.dsTaiSan = response.data
                    } else {
                        this.dsTaiSan = []
                    }
                } catch (err) {
                    this.dsTaiSan = []
                }
            },
            async layDsTaiSanTheoMaTaiSan (maTaiSan) {
                try {
                    let response = await this.$root.context.get('/web-cabman/taisan/danhSachTaiSanTheoMaTaiSan', {maTaiSan})

                    if (response.error_code === 'BSS-00000000' && response.data != null) {
                        this.dsTaiSanTimKiem = await response.data
                        let taiSan = this.dsTaiSanTimKiem[0]
                        this.$refs['ref-combo-duan'].$children[0].selectRowByRowData(taiSan.DUAN_ID)
                    } else {
                        this.dsTaiSanTimKiem = []
                    }
                } catch (err) {
                    this.dsTaiSanTimKiem = []
                }
            },
            async themTaiSan (data) {
                try {
                    let response = await this.$root.context.post('/web-cabman/taisan/themTaiSan', data)

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
            async capNhatTaiSan (data) {
                try {
                    let response = await this.$root.context.post('/web-cabman/taisan/capNhatTaiSan', data)

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
            async chuyenDuAn (data) {
                try {
                    let response = await this.$root.context.post('/web-cabman/taisan/chuyen-duan', data)

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
            async xoaTaiSan (taiSanId) {
                try {
                    let response = await this.$root.context.post('/web-cabman/taisan/xoaTaiSan', {taiSanId})

                    if (response.error_code !== 'BSS-00000000') {
                        throw {response: { data: response}}
                    }
                } catch (err) {
                    let message = []

                    if (err.response.data.message) {
                        message.push(err.response.data.message)
                    }

                    if (err.response.data.message_detail) {
                        message.push(err.response.data.message_detail)
                    }

                    throw message.join('\n')
                }
            },
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
                this.$v.$reset()
            },
            async hienThiDanhSach() {
                try {
                    this.loading(true)
                    await this.layDsTaiSanTheoDuAn(this.duAnId)
                } finally {
                    this.loading(false)
                }
            },
            hienThiThongTinTaiSan(taiSanId) {
                let taiSanInfo = this.dsTaiSan.find(item => item.TAISAN_ID === taiSanId)
                this.duAnId = taiSanInfo.DUAN_ID
                this.maTaiSan = taiSanInfo.MA_TS
                this.tenTaiSan = taiSanInfo.TEN_TS
                this.nguyenGia = !taiSanInfo.GIA ? 0 : taiSanInfo.GIA
                let ttvt = this.dsTtvt.find(item => item.DONVI_ID == taiSanInfo.DONVI_ID)
                this.ttvtId = !ttvt ? null : taiSanInfo.DONVI_ID
            },
            onSelectTaiSan(data) {
                if (!!data) {
                    this.idTaiSanDuocChon = data.TAISAN_ID
                    this.hienThiThongTinTaiSan(data.TAISAN_ID)
                    this.setButton(3)
                }
            },
            onClickButtonXoaTaiSan (taiSanId) {
                this.idTaiSanDuocChon = taiSanId
                this.showModalXacNhanXoa = true
            },
            onClickButtonNhapMoi () {
                this.setButton(1)
            },
            async onClickButtonGhiLai () {

                let errorMessage = []

                this.$v.$touch()

                if (this.$v.$invalid) {

                    if (!this.$v.maTaiSan.required) {
                        errorMessage.push("Chưa nhập mã tài sản !")
                    }

                    if (!this.$v.maTaiSan.maxLength) {
                        errorMessage.push("Mã tài sản không được lớn hơn 200 ký tự!")
                    }

                    if (!this.$v.tenTaiSan.required) {
                        errorMessage.push("Chưa nhập tên tài sản !")
                    }

                    if (!this.$v.tenTaiSan.maxLength) {
                        errorMessage.push("Tên tài sản không được lớn hơn 500 ký tự!")
                    }

                    if (!this.$v.ttvtId.required) {
                        errorMessage.push("Chưa chọn TTVT !")
                    }
                }

                if (errorMessage.length > 0) {
                    this.$toast.error(errorMessage.join('\n'))
                    return
                }

                let data = {
                    donvi_id: this.ttvtId,
                    duan_id: this.duAnId,
                    gia: this.nguyenGia,
                    ma_ts: this.maTaiSan.trim(),
                    ten_ts: this.tenTaiSan.trim()
                }

                try {
                    this.loading(true)

                    if (!this.buttonState.tsbtnNhapMoiEnabled
                        || this.dsTaiSan.length == 0) {
                        await this.themTaiSan(data)
                        this.$toast.success('Thêm mới tài sản thành công !')
                    } else {
                        data.taisan_id = this.idTaiSanDuocChon
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
                    await this.xoaTaiSan(this.idTaiSanDuocChon)
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

                if (!this.idTaiSanDuocChon) {
                    this.$toast.error('Chưa chọn tài sản để chuyển dự án')
                    return
                }

                this.$bvModal.show('modal-chuyen-duan')
            },
            async okChuyenDuAn (bvModalEvt) {

                bvModalEvt.preventDefault()

                try {
                    if (!this.duAnChuyenId) {
                        this.loiChuyenDuAn = true
                        throw 'Chưa chọn dự án cần chuyển tài sản tới'
                    }

                    this.loading(true)

                    let data = {
                        duan_id: this.duAnChuyenId,
                        taisan_id: this.idTaiSanDuocChon
                    }

                    await this.chuyenDuAn(data)
                    this.$toast.success('Chuyển tài sản thành công')
                } catch (err) {
                    this.$toast.error(err)
                    return
                } finally {
                    this.loading(false)
                }

                this.$bvModal.hide('modal-chuyen-duan')
                this.setButton(1)
                this.hienThiDanhSach()
            },
            async onEnterInputTimKiem () {

                let maTimKiem = !this.maTimKiem ? null : this.maTimKiem.trim()

                if (!maTimKiem) {
                    return
                }

                this.loading(true)
                await this.layDsTaiSanTheoMaTaiSan(maTimKiem)
                this.loading(false)

                if (this.dsTaiSanTimKiem.length == 0) {
                    this.$toast.error('Không tìm thấy tài sản.')
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
            async okChonTaiSanTimKiem() {
                let taiSan = this.dsTaiSanTimKiem.find(item => item.TAISAN_ID === this.taiSanTimKiemSelected)
                this.$refs['ref-combo-duan'].$children[0].selectRowByRowData(taiSan.DUAN_ID)
            },
        },
        validations: {
            maTaiSan: {
                required,
                maxLength: maxLength(200)
            },
            tenTaiSan: {
                required,
                maxLength: maxLength(500)
            },
            ttvtId: {
                required
            }
        },
    }
</script>

