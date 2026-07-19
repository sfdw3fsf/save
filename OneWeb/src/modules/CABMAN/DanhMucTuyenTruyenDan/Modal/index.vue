<template src="./index.template.html"></template>
<script>
import TrinhBienTapHinhAnh from './TrinhBienTapHinhAnh'
import BssErrorMarker from '@/components/BssErrorMarker'
import BssRequiredMarker from '@/components/BssRequiredMarker'
import { required } from 'vuelidate/lib/validators'

import Vue from 'vue'
import { ColorPickerPlugin } from '@syncfusion/ej2-vue-inputs'

Vue.use(ColorPickerPlugin)

export default {
    components: { 
        TrinhBienTapHinhAnh, 
        BssRequiredMarker, 
        BssErrorMarker 
    },
    validations: {
        tuyenTruyenDanModel: {
            TUYEN_TD: {
                required
            },
            LOAITTD_ID: {
                required
            }
        },
        loaiTuyenTruyenDanModel: {
            LOAI_TUYEN: {
                required
            }
        },
        loaiCapModel: {
            LOAI_CAP: {
                required
            }
        },
        loaiThietBiModel: {
            LOAI_TBI: {
                required
            }
        },
        loaiDoiTuongModel: {
            TEN_LDT: {
                required
            },
            LENH_SELECT: {
                required
            },
            TRUONG_KHOA: {
                required
            },
            TRUONG_TEN: {
                required
            },
            KICHTHUOC: {
                required
            },
            PHONGCHU: {
                required
            },
            COCHU: {
                required
            },
            KIEUCHU: {
                required
            },
            MAUNEN: {
                required
            },
            MAUCHU: {
                required
            },
            MAUVIEN: {
                required
            },
            COVIEN: {
                required
            },
            PHONGCHU_CHON: {
                required
            },
            COCHU_CHON: {
                required
            },
            KIEUCHU_CHON: {
                required
            },
            MAUNEN_CHON: {
                required
            },
            MAUCHU_CHON: {
                required
            },
            MAUVIEN_CHON: {
                required
            },
            COVIEN_CHON: {
                required
            },
        }
    },
    data() {
        return {
            FORM_MODE: {
                READ_ONLY: 1,
                UPDATE: 2,
                INSERT: 3
            },
            LOAI_TTD: {
                MANG_LOI: 3,
                LIEN_DAI: 4
            },
            LOAI_DM: {
                TUYEN_TRUYEN_DAN: 1,
                LOAI_TUYEN_TRUYEN_DAN: 2,
                LOAI_CAP: 3,
                LOAI_THIET_BI: 4,
                LOAI_DOI_TUONG: 5
            },
            dsLoaiDanhMuc: [],
            loaiDanhMucId: null,
            dsTuyenTruyenDan: [],
            dsLoaiTuyenTruyenDan: [],
            dsLoaiCap: [],
            dsLoaiThietBi: [],
            dsLoaiDoiTuong: [],
            formTuyenTruyenDan: {
                TUYEN_TD: {
                    readonly: false
                },
                LOAITTD_ID: {
                    disabled: false
                }
            },
            tuyenTruyenDanModel: {
                TUYENTD_ID: 0,
                TUYEN_TD: null,
                LOAITTD_ID: null
            },
            comboLoaiTuyenTruyenDan01: {
                dataSource: []
            },
            comboLoaiTuyenTruyenDan02: {
                dataSource: []
            },
            formLoaiTuyenTruyenDan: {
                LOAI_TUYEN: {
                    readonly: false
                }
            },
            loaiTuyenTruyenDanModel: {
                LOAITTD_ID: -1,
                LOAI_TUYEN: null
            },
            formLoaiCap: {
                LOAI_CAP: {
                    readonly: false
                }
            },
            loaiCapModel: {
                LOAICAP_ID: 0,
                LOAI_CAP: null
            },
            formLoaiThietBi: {
                MA_LOAITBI: {
                    readonly: false
                },
                LOAI_TBI: {
                    readonly: false
                },
                GHICHU: {
                    readonly: false
                }
            },
            loaiThietBiModel: {
                LOAITBI_ID: 0,
                MA_LOAITBI: null,
                LOAI_TBI: null,
                GHICHU: null
            },
            formLoaiDoiTuong: {
                TEN_LDT: {
                    readonly: false
                },
                LOAITUYENTD_ID: {
                    disabled: false
                },
                LENH_SELECT: {
                    readonly: false
                },
                TRUONG_KHOA: {
                    readonly: false
                },
                TRUONG_TEN: {
                    readonly: false
                },
                LOAITB_ID: {
                    readonly: false
                },
                KICHTHUOC: {
                    readonly: false
                },
                PHONGCHU: {
                    readonly: false
                },
                COCHU: {
                    readonly: false
                },
                KIEUCHU: {
                    readonly: false
                },
                MAUNEN: {
                    disabled: false
                },
                MAUCHU: {
                    disabled: false
                },
                MAUVIEN: {
                    disabled: false
                },
                COVIEN: {
                    readonly: false
                },
                PHONGCHU_CHON: {
                    readonly: false
                },
                COCHU_CHON: {
                    readonly: false
                },
                KIEUCHU_CHON: {
                    readonly: false
                },
                MAUNEN_CHON: {
                    disabled: false
                },
                MAUCHU_CHON: {
                    disabled: false
                },
                MAUVIEN_CHON: {
                    disabled: false
                },
                COVIEN_CHON: {
                    readonly: false
                }
            },
            loaiDoiTuongModel: {
                LOAIDT_ID: 0,
                TEN_LDT: null,
                LOAITUYENTD_ID: null,
                LENH_SELECT: null,
                TRUONG_KHOA: null,
                TRUONG_TEN: null,
                LOAITB_ID: 0,
                KICHTHUOC: 0,
                PHONGCHU: null,
                COCHU: 0,
                KIEUCHU: 0,
                MAUNEN: '#000000',
                MAUCHU: '#000000',
                MAUVIEN: '#000000',
                COVIEN: 0,
                HINHNEN: null,
                PHONGCHU_CHON: null,
                COCHU_CHON: 0,
                KIEUCHU_CHON: 0,
                MAUNEN_CHON: '#000000',
                MAUCHU_CHON: '#000000',
                MAUVIEN_CHON: '#000000',
                COVIEN_CHON: 0,
                HINHNEN_CHON: null
            },
            formMode: null,
            btnNhapMoi: {
                enabled: false
            },
            btnSua: {
                enabled: false
            },
            btnGhiLai: {
                enabled: false
            },
            btnHuyBo: {
                enabled: false
            },
            btnXoa: {
                enabled: false
            },
            hasData: false
        }
    },
    computed: {
        title() {
            let loaiDanhMuc = this.dsLoaiDanhMuc.find(item => item.id == this.loaiDanhMucId)
            return !loaiDanhMuc ? 'Danh mục truyền dẫn' : `Danh mục ${loaiDanhMuc.name}`
        },
        placeholderHinhNen() {
            return !this.loaiDoiTuongModel.HINHNEN ? 'Chưa có hình ảnh' : 'Nhấn nút để xem'
        },
        placeholderHinhNenChon() {
            return !this.loaiDoiTuongModel.HINHNEN_CHON ? 'Chưa có hình ảnh' : 'Nhấn nút để xem'
        }
    },
    watch: {
        async loaiDanhMucId(value) {

            if (!value) {
                return
            }

            await this.thucHienLayDuLieuLoaiDanhMuc()
            this.formMode = this.FORM_MODE.READ_ONLY
            this.$v.$reset()
            this.enableForm(false)
        },
        formMode(value) {
            switch (value) {
                case this.FORM_MODE.READ_ONLY:
                    this.btnNhapMoi.enabled = !!this.loaiDanhMucId
                    this.btnSua.enabled = this.hasData
                    this.btnGhiLai.enabled = false
                    this.btnHuyBo.enabled = false
                    this.btnXoa.enabled = this.hasData
                    this.enableForm(false)
                    this.$v.$reset()
                    break
                case this.FORM_MODE.UPDATE:
                    this.btnNhapMoi.enabled = false
                    this.btnSua.enabled = false
                    this.btnGhiLai.enabled = true
                    this.btnHuyBo.enabled = true
                    this.btnXoa.enabled = false
                    this.enableForm(true)
                    break
                case this.FORM_MODE.INSERT:
                    this.btnNhapMoi.enabled = false
                    this.btnSua.enabled = false
                    this.btnGhiLai.enabled = true
                    this.btnHuyBo.enabled = true
                    this.btnXoa.enabled = false
                    this.enableForm(true)
                    break
            }
        }
    },
    methods: {
        show() {
            return this.$refs['ref-modal'].show()
        },
        hide() {
            this.$refs['ref-modal'].hide()
        },
        onModalHidden() {
            this.dsLoaiDanhMuc = []
            this.loaiDanhMucId = null
            this.dsTuyenTruyenDan = []
            this.dsLoaiTuyenTruyenDan = []
            this.dsLoaiCap = []
            this.dsLoaiThietBi = []
            this.dsLoaiDoiTuong = []
            this.tuyenTruyenDanModel.TUYENTD_ID = 0
            this.tuyenTruyenDanModel.TUYEN_TD = null
            this.tuyenTruyenDanModel.LOAITTD_ID = null
            this.comboLoaiTuyenTruyenDan01.dataSource = []
            this.comboLoaiTuyenTruyenDan01.disabled = false
            this.comboLoaiTuyenTruyenDan02.dataSource = []
            this.comboLoaiTuyenTruyenDan02.disabled = false
            this.loaiTuyenTruyenDanModel.LOAITTD_ID = -1
            this.loaiTuyenTruyenDanModel.LOAI_TUYEN = null
            this.loaiCapModel.LOAICAP_ID = 0
            this.loaiCapModel.LOAI_CAP = null
            this.loaiThietBiModel.LOAITBI_ID = 0
            this.loaiThietBiModel.MA_LOAITBI = null
            this.loaiThietBiModel.LOAI_TBI = null
            this.loaiThietBiModel.GHICHU = null
            this.loaiDoiTuongModel.LOAIDT_ID = 0
            this.loaiDoiTuongModel.TEN_LDT = null
            this.loaiDoiTuongModel.LOAITUYENTD_ID = null
            this.loaiDoiTuongModel.LENH_SELECT = null
            this.loaiDoiTuongModel.TRUONG_KHOA = null
            this.loaiDoiTuongModel.TRUONG_TEN = null
            this.loaiDoiTuongModel.LOAITB_ID = 0
            this.loaiDoiTuongModel.KICHTHUOC = 0
            this.loaiDoiTuongModel.PHONGCHU = null
            this.loaiDoiTuongModel.COCHU = 0
            this.loaiDoiTuongModel.KIEUCHU = 0
            this.loaiDoiTuongModel.MAUNEN = '#000000'
            this.loaiDoiTuongModel.MAUCHU = '#000000'
            this.loaiDoiTuongModel.MAUVIEN = '#000000'
            this.loaiDoiTuongModel.COVIEN = 0
            this.loaiDoiTuongModel.HINHNEN = null
            this.loaiDoiTuongModel.PHONGCHU_CHON = null
            this.loaiDoiTuongModel.COCHU_CHON = 0
            this.loaiDoiTuongModel.KIEUCHU_CHON = 0
            this.loaiDoiTuongModel.MAUNEN_CHON = '#000000'
            this.loaiDoiTuongModel.MAUCHU_CHON = '#000000'
            this.loaiDoiTuongModel.MAUVIEN_CHON = '#000000'
            this.loaiDoiTuongModel.COVIEN_CHON = 0
            this.loaiDoiTuongModel.HINHNEN_CHON = null
            this.formMode = null
            this.btnNhapMoi.enabled = false
            this.btnSua.enabled = false
            this.btnGhiLai.enabled = false
            this.btnHuyBo.enabled = false
            this.btnXoa.enabled = false
            this.hasData = false
            this.formTuyenTruyenDan.TUYEN_TD.readonly = false
            this.formTuyenTruyenDan.LOAITTD_ID.disabled = false
            this.formLoaiTuyenTruyenDan.LOAI_TUYEN.readonly = false
            this.formLoaiCap.LOAI_CAP.readonly = false
            this.formLoaiThietBi.MA_LOAITBI.readonly = false
            this.formLoaiThietBi.LOAI_TBI.readonly = false
            this.formLoaiThietBi.GHICHU.readonly = false
            this.formLoaiDoiTuong.TEN_LDT.readonly = false
            this.formLoaiDoiTuong.LOAITUYENTD_ID.disabled = false
            this.formLoaiDoiTuong.LENH_SELECT.readonly = false
            this.formLoaiDoiTuong.TRUONG_KHOA.readonly = false
            this.formLoaiDoiTuong.TRUONG_TEN.readonly = false
            this.formLoaiDoiTuong.LOAITB_ID.readonly = false
            this.formLoaiDoiTuong.KICHTHUOC.readonly = false
            this.formLoaiDoiTuong.PHONGCHU.readonly = false
            this.formLoaiDoiTuong.COCHU.readonly = false
            this.formLoaiDoiTuong.KIEUCHU.readonly = false
            this.formLoaiDoiTuong.MAUNEN.disabled = false
            this.formLoaiDoiTuong.MAUCHU.disabled = false
            this.formLoaiDoiTuong.MAUVIEN.disabled = false
            this.formLoaiDoiTuong.COVIEN.readonly = false
            this.formLoaiDoiTuong.PHONGCHU_CHON.readonly = false
            this.formLoaiDoiTuong.COCHU_CHON.readonly = false
            this.formLoaiDoiTuong.KIEUCHU_CHON.readonly = false
            this.formLoaiDoiTuong.MAUNEN_CHON.disabled = false
            this.formLoaiDoiTuong.MAUCHU_CHON.disabled = false
            this.formLoaiDoiTuong.MAUVIEN_CHON.disabled = false
            this.formLoaiDoiTuong.COVIEN_CHON.readonly = false
        },
        async onModalShown() {

            this.loading(true)

            this.comboLoaiTuyenTruyenDan02.dataSource = await this.layDuLieuLoaiDanhMuc(this.LOAI_DM.LOAI_TUYEN_TRUYEN_DAN)

            if (this.comboLoaiTuyenTruyenDan02.dataSource.length > 0) {

                this.comboLoaiTuyenTruyenDan02.value = this.comboLoaiTuyenTruyenDan02.dataSource[0].LOAITTD_ID

                this.comboLoaiTuyenTruyenDan01.dataSource = this.comboLoaiTuyenTruyenDan02.dataSource.filter(item => {
                    return item.LOAITTD_ID == this.LOAI_TTD.MANG_LOI || item.LOAITTD_ID == this.LOAI_TTD.LIEN_DAI
                })

                if (this.comboLoaiTuyenTruyenDan01.dataSource.length > 0) {
                    this.comboLoaiTuyenTruyenDan01.value = this.comboLoaiTuyenTruyenDan01.dataSource[0].LOAITTD_ID
                }
            }

            this.dsLoaiDanhMuc = [
                {id: this.LOAI_DM.TUYEN_TRUYEN_DAN, name: 'Tuyến truyền dẫn'},
                {id: this.LOAI_DM.LOAI_TUYEN_TRUYEN_DAN, name: 'Loại tuyến truyền dẫn'},
                {id: this.LOAI_DM.LOAI_CAP, name: 'Loại cáp'},
                {id: this.LOAI_DM.LOAI_THIET_BI, name: 'Loại thiết bị'},
                {id: this.LOAI_DM.LOAI_DOI_TUONG, name: 'Loại đối tượng'},
            ]
            
            this.loaiDanhMucId = 1
        },
        onChangeMauNen(args) {
            this.loaiDoiTuongModel.MAUNEN = args.currentValue.hex
        },
        onChangeMauChu(args) {
            this.loaiDoiTuongModel.MAUCHU = args.currentValue.hex
        },
        onChangeMauVien(args) {
            this.loaiDoiTuongModel.MAUVIEN = args.currentValue.hex
        },
        onChangeMauNenChon(args) {
            this.loaiDoiTuongModel.MAUNEN_CHON = args.currentValue.hex
        },
        onChangeMauChuChon(args) {
            this.loaiDoiTuongModel.MAUCHU_CHON = args.currentValue.hex
        },
        onChangeMauVienChon(args) {
            this.loaiDoiTuongModel.MAUVIEN_CHON = args.currentValue.hex
        },
        onClickBtnGhiLai() {

            if (!this.btnGhiLai.enabled) {
                return
            }

            switch (parseInt(this.loaiDanhMucId)) {
                case this.LOAI_DM.TUYEN_TRUYEN_DAN:
                    this.thucHienLuuDmTuyenTruyenDan()
                    break
                case this.LOAI_DM.LOAI_TUYEN_TRUYEN_DAN:
                    this.thucHienLuuDmLoaiTuyenTruyenDan()
                    break
                case this.LOAI_DM.LOAI_CAP:
                    this.thucHienLuuDmLoaiCap()
                    break
                case this.LOAI_DM.LOAI_THIET_BI:
                    this.thucHienLuuDmLoaiTbi()
                    break
                case this.LOAI_DM.LOAI_DOI_TUONG:
                    this.thucHienLuuDmLoaiDoiTuong()
                    break
            }
        },
        async thucHienLuuDmLoaiDoiTuong() {

            let errorMessage = []

            this.$v.$touch()

            if (!this.$v.loaiDoiTuongModel.TEN_LDT.required) {
                 errorMessage.push("Tên loại ĐT không được để trống")
            }

            if (!this.$v.loaiDoiTuongModel.LENH_SELECT.required) {
                 errorMessage.push("Lệnh select không được để trống")
            }

            if (!this.$v.loaiDoiTuongModel.TRUONG_KHOA.required) {
                 errorMessage.push("Trường khoá không được để trống")
            }

            if (!this.$v.loaiDoiTuongModel.TRUONG_TEN.required) {
                 errorMessage.push("Trường tên không được để trống")
            }

            if (!this.$v.loaiDoiTuongModel.KICHTHUOC.required) {
                 errorMessage.push("Kích thước không được để trống")
            }

            if (!this.$v.loaiDoiTuongModel.PHONGCHU.required) {
                 errorMessage.push("Phông chữ không được để trống")
            }

            if (!this.$v.loaiDoiTuongModel.COCHU.required) {
                 errorMessage.push("Cỡ chữ không được để trống")
            }

            if (!this.$v.loaiDoiTuongModel.KIEUCHU.required) {
                 errorMessage.push("Kiểu chữ không được để trống")
            }

            if (!this.$v.loaiDoiTuongModel.MAUNEN.required) {
                 errorMessage.push("Màu nền không được để trống")
            }

            if (!this.$v.loaiDoiTuongModel.MAUCHU.required) {
                 errorMessage.push("Màu chữ không được để trống")
            }

            if (!this.$v.loaiDoiTuongModel.MAUVIEN.required) {
                 errorMessage.push("Màu viền không được để trống")
            }

            if (!this.$v.loaiDoiTuongModel.COVIEN.required) {
                 errorMessage.push("Cỡ viền không được để trống")
            }


            if (!this.$v.loaiDoiTuongModel.PHONGCHU_CHON.required) {
                 errorMessage.push("Phông chữ không được để trống")
            }

            if (!this.$v.loaiDoiTuongModel.COCHU_CHON.required) {
                 errorMessage.push("Cỡ chữ không được để trống")
            }

            if (!this.$v.loaiDoiTuongModel.KIEUCHU_CHON.required) {
                 errorMessage.push("Kiểu chữ không được để trống")
            }

            if (!this.$v.loaiDoiTuongModel.MAUNEN_CHON.required) {
                 errorMessage.push("Màu nền không được để trống")
            }

            if (!this.$v.loaiDoiTuongModel.MAUCHU_CHON.required) {
                 errorMessage.push("Màu chữ không được để trống")
            }

            if (!this.$v.loaiDoiTuongModel.MAUVIEN_CHON.required) {
                 errorMessage.push("Màu viền không được để trống")
            }

            if (!this.$v.loaiDoiTuongModel.COVIEN_CHON.required) {
                 errorMessage.push("Cỡ viền không được để trống")
            }

            if (errorMessage.length > 0) {
                this.$root.toastError(errorMessage.join('\n'))
                return
            }

            let data = {
                ten_ldt: this.loaiDoiTuongModel.TEN_LDT,
                loaituyentd_id: this.loaiDoiTuongModel.LOAITUYENTD_ID,
                lenh_select: this.loaiDoiTuongModel.LENH_SELECT,
                truong_khoa: this.loaiDoiTuongModel.TRUONG_KHOA,
                truong_ten: this.loaiDoiTuongModel.TRUONG_TEN,
                loaitb_id: this.loaiDoiTuongModel.LOAITB_ID,
                kichthuoc: this.loaiDoiTuongModel.KICHTHUOC,
                phongchu: this.loaiDoiTuongModel.PHONGCHU,
                cochu: this.loaiDoiTuongModel.COCHU,
                kieuchu: this.loaiDoiTuongModel.KIEUCHU,
                maunen: this.loaiDoiTuongModel.MAUNEN,
                mauchu: this.loaiDoiTuongModel.MAUCHU,
                mauvien: this.loaiDoiTuongModel.MAUVIEN,
                covien: this.loaiDoiTuongModel.COVIEN,
                hinhnen: this.loaiDoiTuongModel.HINHNEN,
                phongchu_chon: this.loaiDoiTuongModel.PHONGCHU_CHON,
                cochu_chon: this.loaiDoiTuongModel.COCHU_CHON,
                kieuchu_chon: this.loaiDoiTuongModel.KIEUCHU_CHON,
                maunen_chon: this.loaiDoiTuongModel.MAUNEN_CHON,
                mauchu_chon: this.loaiDoiTuongModel.MAUCHU_CHON,
                mauvien_chon: this.loaiDoiTuongModel.MAUVIEN_CHON,
                covien_chon: this.loaiDoiTuongModel.COVIEN_CHON,
                hinhnen_chon: this.loaiDoiTuongModel.HINHNEN_CHON
            }

            try {
                this.loading(true)

                if (this.formMode == this.FORM_MODE.INSERT) {
                    let result = await this.luuDmLoaiDoiTuong(data)
                    this.$root.toastSuccess('Thêm danh mục loại đối tượng thành công !')
                    this.dsLoaiDoiTuong.unshift(result)
                    let grid = this.$refs['grid-loai-doi-tuong'].grid
                    grid.refresh()
                    grid.selectRow(0)
                } else {
                    data.loaidt_id = this.loaiDoiTuongModel.LOAIDT_ID
                    let result = await this.luuDmLoaiDoiTuong(data)
                    this.$refs['grid-loai-doi-tuong'].grid.batchUpdate({ changedRecords: [result] })
                    this.$root.toastSuccess('Cập nhật danh mục loại đối tượng thành công !')
                }
            } catch (err) {
                this.$root.toastError(err)
                return
            } finally {
                this.loading(false)
            }

            this.formMode = this.FORM_MODE.READ_ONLY
        },
        async thucHienLuuDmLoaiTbi() {

            let errorMessage = []

            this.$v.$touch()

            if (!this.$v.loaiThietBiModel.LOAI_TBI.required) {
                 errorMessage.push("Tên loại thiết bị không được để trống")
            }

            if (errorMessage.length > 0) {
                this.$root.toastError(errorMessage.join('\n'))
                return
            }

            let data = {
                ma_loaitbi: this.loaiThietBiModel.MA_LOAITBI,
                loai_tbi: this.loaiThietBiModel.LOAI_TBI,
                ghichu: this.loaiThietBiModel.GHICHU
            }

            try {
                this.loading(true)

                if (this.formMode == this.FORM_MODE.INSERT) {
                    let result = await this.luuDmLoaiThietBi(data)
                    this.$root.toastSuccess('Thêm danh mục loại thiết bị thành công !')
                    this.dsLoaiThietBi.unshift(result)
                    let grid = this.$refs['grid-loai-thiet-bi'].grid
                    grid.refresh()
                    grid.selectRow(0)
                } else {
                    data.loaitbi_id = this.loaiThietBiModel.LOAITBI_ID
                    let result = await this.luuDmLoaiThietBi(data)
                    this.$refs['grid-loai-thiet-bi'].grid.batchUpdate({ changedRecords: [result] })
                    this.$root.toastSuccess('Cập nhật danh mục loại thiết bị thành công !')
                }
            } catch (err) {
                this.$root.toastError(err)
                return
            } finally {
                this.loading(false)
            }

            this.formMode = this.FORM_MODE.READ_ONLY
        },
        async thucHienLuuDmLoaiCap() {

            let errorMessage = []

            this.$v.$touch()

            if (!this.$v.loaiCapModel.LOAI_CAP.required) {
                 errorMessage.push("Tên loại cáp không được để trống")
            }

            if (errorMessage.length > 0) {
                this.$root.toastError(errorMessage.join('\n'))
                return
            }

            let data = {
                loai_cap: this.loaiCapModel.LOAI_CAP
            }

            try {
                this.loading(true)

                if (this.formMode == this.FORM_MODE.INSERT) {
                    let result = await this.luuDmLoaiCap(data)
                    this.$root.toastSuccess('Thêm danh mục loại cáp thành công !')
                    this.dsLoaiCap.unshift(result)
                    let grid = this.$refs['grid-loai-cap'].grid
                    grid.refresh()
                    grid.selectRow(0)
                } else {
                    data.loaicap_id = this.loaiCapModel.LOAICAP_ID
                    let result = await this.luuDmLoaiCap(data)
                    this.$refs['grid-loai-cap'].grid.batchUpdate({ changedRecords: [result] })
                    this.$root.toastSuccess('Cập nhật danh mục loại cáp thành công !')
                }
            } catch (err) {
                this.$root.toastError(err)
                return
            } finally {
                this.loading(false)
            }

            this.formMode = this.FORM_MODE.READ_ONLY
        },
        async thucHienLuuDmLoaiTuyenTruyenDan() {

            let errorMessage = []

            this.$v.$touch()

            if (!this.$v.loaiTuyenTruyenDanModel.LOAI_TUYEN.required) {
                 errorMessage.push("Tên loại tuyến không được để trống")
            }

            if (errorMessage.length > 0) {
                this.$root.toastError(errorMessage.join('\n'))
                return
            }

            let data = {
                loai_tuyen: this.loaiTuyenTruyenDanModel.LOAI_TUYEN,
            }

            try {
                this.loading(true)

                if (this.formMode == this.FORM_MODE.INSERT) {
                    let result = await this.luuDmLoaiTuyenTruyenDan(data)
                    this.$root.toastSuccess('Thêm danh mục loại tuyến truyền dẫn thành công !')
                    this.dsLoaiTuyenTruyenDan.unshift(result)
                    let grid = this.$refs['grid-loai-tuyen-truyen-dan'].grid
                    grid.refresh()
                    grid.selectRow(0)
                } else {
                    data.loaittd_id = this.loaiTuyenTruyenDanModel.LOAITTD_ID
                    let result = await this.luuDmLoaiTuyenTruyenDan(data)
                    this.$refs['grid-loai-tuyen-truyen-dan'].grid.batchUpdate({ changedRecords: [result] })
                    this.$root.toastSuccess('Cập nhật danh mục loại tuyến truyền dẫn thành công !')
                }
            } catch (err) {
                this.$root.toastError(err)
                return
            } finally {
                this.loading(false)
            }

            this.formMode = this.FORM_MODE.READ_ONLY
        },
        async thucHienLuuDmTuyenTruyenDan() {

            let errorMessage = []

            this.$v.$touch()

            if (!this.$v.tuyenTruyenDanModel.TUYEN_TD.required) {
                 errorMessage.push("Tên tuyến không được để trống")
            }

            if (!this.$v.tuyenTruyenDanModel.LOAITTD_ID.required) {
                 errorMessage.push("Loại tuyến không được để trống")
            }

            if (errorMessage.length > 0) {
                this.$root.toastError(errorMessage.join('\n'))
                return
            }

            let data = {
                tuyen_td: this.tuyenTruyenDanModel.TUYEN_TD,
                loaittd_id: this.tuyenTruyenDanModel.LOAITTD_ID
            }

            try {
                this.loading(true)

                if (this.formMode == this.FORM_MODE.INSERT) {
                    let result = await this.luuDmTuyenTruyenDan(data)
                    this.$root.toastSuccess('Thêm danh mục tuyến truyền dẫn thành công !')
                    this.dsTuyenTruyenDan.unshift(result)
                    let grid = this.$refs['grid-tuyen-truyen-dan'].grid
                    grid.refresh()
                    grid.selectRow(0)
                } else {
                    data.tuyentd_id = this.tuyenTruyenDanModel.TUYENTD_ID
                    let result = await this.luuDmTuyenTruyenDan(data)
                    this.$refs['grid-tuyen-truyen-dan'].grid.batchUpdate({ changedRecords: [result] })
                    this.$root.toastSuccess('Cập nhật danh mục tuyến truyền dẫn thành công !')
                }
            } catch (err) {
                this.$root.toastError(err)
                return
            } finally {
                this.loading(false)
            }

            this.formMode = this.FORM_MODE.READ_ONLY
        },
        async okXoaDanhMuc() {

            this.$refs['ref-modal-xacnhan-xoa'].hide()

            try {
                this.loading(true)

                let danhMucId = null

                switch (parseInt(this.loaiDanhMucId)) {
                    case this.LOAI_DM.TUYEN_TRUYEN_DAN:
                        danhMucId = this.tuyenTruyenDanModel.TUYENTD_ID
                        break
                    case this.LOAI_DM.LOAI_TUYEN_TRUYEN_DAN:
                        danhMucId = this.loaiTuyenTruyenDanModel.LOAITTD_ID
                        break
                    case this.LOAI_DM.LOAI_CAP:
                        danhMucId = this.loaiCapModel.LOAICAP_ID
                        break
                    case this.LOAI_DM.LOAI_THIET_BI:
                        danhMucId = this.loaiThietBiModel.LOAITBI_ID
                        break
                    case this.LOAI_DM.LOAI_DOI_TUONG:
                        danhMucId = this.loaiDoiTuongModel.LOAIDT_ID
                        break
                }

                await this.xoaDanhMuc({
                    loai_dm: this.loaiDanhMucId,
                    danhmuc_id: danhMucId
                })
            } catch (err) {
                this.$root.toastError(err)
                return
            } finally {
                this.loading(false)
            }

            this.$root.toastSuccess(`Xóa ${this.title} thành công !`)
            this.thucHienLayDuLieuLoaiDanhMuc()
            this.formMode = this.FORM_MODE.READ_ONLY
            this.enableForm(false)
        },
        onClickBtnXoa() {
            if (this.btnXoa.enabled) {
                this.$refs['ref-modal-xacnhan-xoa'].show()
            }
        },
        enableForm(allowEnableForm) {

            if (!allowEnableForm) {
                switch (parseInt(this.loaiDanhMucId)) {
                    case this.LOAI_DM.TUYEN_TRUYEN_DAN:
                        this.formTuyenTruyenDan.TUYEN_TD.readonly = true
                        this.formTuyenTruyenDan.LOAITTD_ID.disabled = true
                        break
                    case this.LOAI_DM.LOAI_TUYEN_TRUYEN_DAN:
                        this.formLoaiTuyenTruyenDan.LOAI_TUYEN.readonly = true
                        break
                    case this.LOAI_DM.LOAI_CAP:
                        this.formLoaiCap.LOAI_CAP.readonly = true
                        break
                    case this.LOAI_DM.LOAI_THIET_BI:
                        this.formLoaiThietBi.MA_LOAITBI.readonly = true
                        this.formLoaiThietBi.LOAI_TBI.readonly = true
                        this.formLoaiThietBi.GHICHU.readonly = true
                        break
                    case this.LOAI_DM.LOAI_DOI_TUONG:
                        this.formLoaiDoiTuong.TEN_LDT.readonly = true
                        this.formLoaiDoiTuong.LOAITUYENTD_ID.disabled = true
                        this.formLoaiDoiTuong.LENH_SELECT.readonly = true
                        this.formLoaiDoiTuong.TRUONG_KHOA.readonly = true
                        this.formLoaiDoiTuong.TRUONG_TEN.readonly = true
                        this.formLoaiDoiTuong.LOAITB_ID.readonly = true
                        this.formLoaiDoiTuong.KICHTHUOC.readonly = true
                        this.formLoaiDoiTuong.PHONGCHU.readonly = true
                        this.formLoaiDoiTuong.COCHU.readonly = true
                        this.formLoaiDoiTuong.KIEUCHU.readonly = true
                        this.formLoaiDoiTuong.MAUNEN.disabled = true
                        this.formLoaiDoiTuong.MAUCHU.disabled = true
                        this.formLoaiDoiTuong.MAUVIEN.disabled = true
                        this.formLoaiDoiTuong.COVIEN.readonly = true
                        this.formLoaiDoiTuong.PHONGCHU_CHON.readonly = true
                        this.formLoaiDoiTuong.COCHU_CHON.readonly = true
                        this.formLoaiDoiTuong.KIEUCHU_CHON.readonly = true
                        this.formLoaiDoiTuong.MAUNEN_CHON.disabled = true
                        this.formLoaiDoiTuong.MAUCHU_CHON.disabled = true
                        this.formLoaiDoiTuong.MAUVIEN_CHON.disabled = true
                        this.formLoaiDoiTuong.COVIEN_CHON.readonly = true
                        break
                }
                return
            }
            
            switch (parseInt(this.loaiDanhMucId)) {
                case this.LOAI_DM.TUYEN_TRUYEN_DAN:
                    this.formTuyenTruyenDan.TUYEN_TD.readonly = false
                    this.formTuyenTruyenDan.LOAITTD_ID.disabled = false
                    break
                case this.LOAI_DM.LOAI_TUYEN_TRUYEN_DAN:
                    this.formLoaiTuyenTruyenDan.LOAI_TUYEN.readonly = false
                    break
                case this.LOAI_DM.LOAI_CAP:
                    this.formLoaiCap.LOAI_CAP.readonly = false
                    break
                case this.LOAI_DM.LOAI_THIET_BI:
                    this.formLoaiThietBi.MA_LOAITBI.readonly = false
                    this.formLoaiThietBi.LOAI_TBI.readonly = false
                    this.formLoaiThietBi.GHICHU.readonly = false
                    break
                case this.LOAI_DM.LOAI_DOI_TUONG:
                    this.formLoaiDoiTuong.TEN_LDT.readonly = false
                    this.formLoaiDoiTuong.LOAITUYENTD_ID.disabled = false
                    this.formLoaiDoiTuong.LENH_SELECT.readonly = false
                    this.formLoaiDoiTuong.TRUONG_KHOA.readonly = false
                    this.formLoaiDoiTuong.TRUONG_TEN.readonly = false
                    this.formLoaiDoiTuong.LOAITB_ID.readonly = false
                    this.formLoaiDoiTuong.KICHTHUOC.readonly = false
                    this.formLoaiDoiTuong.PHONGCHU.readonly = false
                    this.formLoaiDoiTuong.COCHU.readonly = false
                    this.formLoaiDoiTuong.KIEUCHU.readonly = false
                    this.formLoaiDoiTuong.MAUNEN.disabled = false
                    this.formLoaiDoiTuong.MAUCHU.disabled = false
                    this.formLoaiDoiTuong.MAUVIEN.disabled = false
                    this.formLoaiDoiTuong.COVIEN.readonly = false
                    this.formLoaiDoiTuong.PHONGCHU_CHON.readonly = false
                    this.formLoaiDoiTuong.COCHU_CHON.readonly = false
                    this.formLoaiDoiTuong.KIEUCHU_CHON.readonly = false
                    this.formLoaiDoiTuong.MAUNEN_CHON.disabled = false
                    this.formLoaiDoiTuong.MAUCHU_CHON.disabled = false
                    this.formLoaiDoiTuong.MAUVIEN_CHON.disabled = false
                    this.formLoaiDoiTuong.COVIEN_CHON.readonly = false
                    break
            }
        },
        onClickBtnSua() {
            if (this.btnSua.enabled) {
                this.formMode = this.FORM_MODE.UPDATE
            }
        },
        onClickBtnHuyBo() {

            if (!this.btnHuyBo.enabled) {
                return
            }

            this.formMode = this.FORM_MODE.READ_ONLY
            this.$v.$reset()

            if (!this.hasData) {
                return
            }

            let data = null

            switch (parseInt(this.loaiDanhMucId)) {
                case this.LOAI_DM.TUYEN_TRUYEN_DAN:
                    data = this.$refs['grid-tuyen-truyen-dan'].grid.getSelectedRecords()[0]
                    this.setDataTuyenTruyenDan(data)
                    break
                case this.LOAI_DM.LOAI_TUYEN_TRUYEN_DAN:
                    data = this.$refs['grid-loai-tuyen-truyen-dan'].grid.getSelectedRecords()[0]
                    this.setDataLoaiTuyenTruyenDan(data)
                    break
                case this.LOAI_DM.LOAI_CAP:
                    data = this.$refs['grid-loai-cap'].grid.getSelectedRecords()[0]
                    this.setDataLoaiCap(data)
                    break
                case this.LOAI_DM.LOAI_THIET_BI:
                    data = this.$refs['grid-loai-thiet-bi'].grid.getSelectedRecords()[0]
                    this.setDataLoaiThietBi(data)
                    break
                case this.LOAI_DM.LOAI_DOI_TUONG:
                    data = this.$refs['grid-loai-doi-tuong'].grid.getSelectedRecords()[0]
                    this.setDataLoaiDoiTuong(data)
                    break
            }
        },
        onClickBtnNhapMoi() {
            
            if (!this.btnNhapMoi.enabled) {
                return    
            }

            this.formMode = this.FORM_MODE.INSERT

            switch (parseInt(this.loaiDanhMucId)) {
                case this.LOAI_DM.TUYEN_TRUYEN_DAN:
                    this.setDataTuyenTruyenDan(null)
                    break
                case this.LOAI_DM.LOAI_TUYEN_TRUYEN_DAN:
                    this.setDataLoaiTuyenTruyenDan(null)
                    break
                case this.LOAI_DM.LOAI_CAP:
                    this.setDataLoaiCap(null)
                    break
                case this.LOAI_DM.LOAI_THIET_BI:
                    this.setDataLoaiThietBi(null)
                    break
                case this.LOAI_DM.LOAI_DOI_TUONG:
                    this.setDataLoaiDoiTuong(null)
                    break
            }
        },
        async onSelectHinhNenChon(fileImage) {
            this.loaiDoiTuongModel.HINHNEN_CHON = !fileImage ? null : await this.getFileBase64(fileImage)
        },
        async onSelectHinhNen(fileImage) {
            this.loaiDoiTuongModel.HINHNEN = !fileImage ? null : await this.getFileBase64(fileImage)
        },
        getFileBase64(file) {
            return new Promise((resolve, reject) => {
                const reader = new FileReader()
                reader.readAsDataURL(file);
                reader.onload = () => resolve(reader.result.split("base64,")[1])
                reader.onerror = error => reject(error)
            });
        },
        onClickBtnChonHinhNenChon() {

            if (this.formMode == this.FORM_MODE.READ_ONLY) {
                return
            }

            let modalTrinhBienTapHinhAnh = this.$refs['ref-chon-hinh-nen-chon']
            modalTrinhBienTapHinhAnh.setFileImage(this.loaiDoiTuongModel.HINHNEN_CHON)
            modalTrinhBienTapHinhAnh.show()
        },
        onClickBtnChonHinhNen() {

            if (this.formMode == this.FORM_MODE.READ_ONLY) {
                return
            }

            let modalTrinhBienTapHinhAnh = this.$refs['ref-chon-hinh-nen']
            modalTrinhBienTapHinhAnh.setFileImage(this.loaiDoiTuongModel.HINHNEN)
            modalTrinhBienTapHinhAnh.show()
        },
        onSelectRowLoaiDoiTuong(data) {
            this.setDataLoaiDoiTuong(data)
            this.formMode = this.FORM_MODE.READ_ONLY
            this.$v.$reset()
        },
        onSelectRowLoaiThietBi(data) {
            this.setDataLoaiThietBi(data)
            this.formMode = this.FORM_MODE.READ_ONLY
            this.$v.$reset()
        },
        onSelectRowLoaiCap(data) {
            this.setDataLoaiCap(data)
            this.formMode = this.FORM_MODE.READ_ONLY
            this.$v.$reset()
        },
        onSelectRowLoaiTuyenTruyenDan(data) {
            this.setDataLoaiTuyenTruyenDan(data)
            this.formMode = this.FORM_MODE.READ_ONLY
            this.$v.$reset()
        },
        onSelectRowTuyenTruyenDan(data) {
            this.setDataTuyenTruyenDan(data)
            this.formMode = this.FORM_MODE.READ_ONLY
            this.$v.$reset()
        },
        setDataLoaiDoiTuong(data) {

            if (!data) {
                this.loaiDoiTuongModel.LOAIDT_ID = 0
                this.loaiDoiTuongModel.TEN_LDT = null
                this.loaiDoiTuongModel.LOAITUYENTD_ID = null
                this.loaiDoiTuongModel.LENH_SELECT = null
                this.loaiDoiTuongModel.TRUONG_KHOA = null
                this.loaiDoiTuongModel.TRUONG_TEN = null
                this.loaiDoiTuongModel.LOAITB_ID = 0
                this.loaiDoiTuongModel.KICHTHUOC = 0
                this.loaiDoiTuongModel.PHONGCHU = null
                this.loaiDoiTuongModel.COCHU = 0
                this.loaiDoiTuongModel.KIEUCHU = 0
                this.loaiDoiTuongModel.MAUNEN = '#000000'
                this.loaiDoiTuongModel.MAUCHU = '#000000'
                this.loaiDoiTuongModel.MAUVIEN = '#000000'
                this.loaiDoiTuongModel.COVIEN = 0
                this.loaiDoiTuongModel.HINHNEN = null
                this.loaiDoiTuongModel.PHONGCHU_CHON = null
                this.loaiDoiTuongModel.COCHU_CHON = 0
                this.loaiDoiTuongModel.KIEUCHU_CHON = 0
                this.loaiDoiTuongModel.MAUNEN_CHON = '#000000'
                this.loaiDoiTuongModel.MAUCHU_CHON = '#000000'
                this.loaiDoiTuongModel.MAUVIEN_CHON = '#000000'
                this.loaiDoiTuongModel.COVIEN_CHON = 0
                this.loaiDoiTuongModel.HINHNEN_CHON = null
                return
            }

            this.loaiDoiTuongModel.LOAIDT_ID = data.LOAIDT_ID
            this.loaiDoiTuongModel.TEN_LDT = data.TEN_LDT
            this.loaiDoiTuongModel.LOAITUYENTD_ID = data.LOAITUYENTD_ID
            this.loaiDoiTuongModel.LENH_SELECT = data.LENH_SELECT
            this.loaiDoiTuongModel.TRUONG_KHOA = data.TRUONG_KHOA
            this.loaiDoiTuongModel.TRUONG_TEN = data.TRUONG_TEN
            this.loaiDoiTuongModel.LOAITB_ID = data.LOAITB_ID
            this.loaiDoiTuongModel.KICHTHUOC = data.KICHTHUOC
            this.loaiDoiTuongModel.PHONGCHU = data.PHONGCHU
            this.loaiDoiTuongModel.COCHU = data.COCHU
            this.loaiDoiTuongModel.KIEUCHU = data.KIEUCHU
            this.loaiDoiTuongModel.MAUNEN = data.MAUNEN
            this.loaiDoiTuongModel.MAUCHU = data.MAUCHU
            this.loaiDoiTuongModel.MAUVIEN = data.MAUVIEN
            this.loaiDoiTuongModel.COVIEN = data.COVIEN
            this.loaiDoiTuongModel.HINHNEN = data.HINHNEN
            this.loaiDoiTuongModel.PHONGCHU_CHON = data.PHONGCHU_CHON
            this.loaiDoiTuongModel.COCHU_CHON = data.COCHU_CHON
            this.loaiDoiTuongModel.KIEUCHU_CHON = data.KIEUCHU_CHON
            this.loaiDoiTuongModel.MAUNEN_CHON = data.MAUNEN_CHON
            this.loaiDoiTuongModel.MAUCHU_CHON = data.MAUCHU_CHON
            this.loaiDoiTuongModel.MAUVIEN_CHON = data.MAUVIEN_CHON
            this.loaiDoiTuongModel.COVIEN_CHON = data.COVIEN_CHON
            this.loaiDoiTuongModel.HINHNEN_CHON = data.HINHNEN_CHON
        },
        setDataLoaiThietBi(data) {

            if (!data) {
                this.loaiThietBiModel.LOAITBI_ID = 0
                this.loaiThietBiModel.MA_LOAITBI = null
                this.loaiThietBiModel.LOAI_TBI = null
                this.loaiThietBiModel.GHICHU = null
                return
            }

            this.loaiThietBiModel.LOAITBI_ID = data.LOAITBI_ID
            this.loaiThietBiModel.MA_LOAITBI = data.MA_LOAITBI
            this.loaiThietBiModel.LOAI_TBI = data.LOAI_TBI
            this.loaiThietBiModel.GHICHU = data.GHICHU
        },
        setDataLoaiCap(data) {

            if (!data) {
                this.loaiCapModel.LOAICAP_ID = 0
                this.loaiCapModel.LOAI_CAP = null
                return
            }

            this.loaiCapModel.LOAICAP_ID = data.LOAICAP_ID
            this.loaiCapModel.LOAI_CAP = data.LOAI_CAP
        },
        setDataLoaiTuyenTruyenDan(data) {

            if (!data) {
                this.loaiTuyenTruyenDanModel.LOAITTD_ID = -1
                this.loaiTuyenTruyenDanModel.LOAI_TUYEN = null
                return
            }

            this.loaiTuyenTruyenDanModel.LOAITTD_ID = data.LOAITTD_ID
            this.loaiTuyenTruyenDanModel.LOAI_TUYEN = data.LOAI_TUYEN
        },
        setDataTuyenTruyenDan(data) {

            if (!data) {
                this.tuyenTruyenDanModel.TUYENTD_ID = 0
                this.tuyenTruyenDanModel.TUYEN_TD = null
                this.tuyenTruyenDanModel.LOAITTD_ID = null
                return
            }

            this.tuyenTruyenDanModel.TUYENTD_ID = data.TUYENTD_ID
            this.tuyenTruyenDanModel.TUYEN_TD = data.TUYEN_TD
            this.tuyenTruyenDanModel.LOAITTD_ID = data.LOAITTD_ID
        },
        async thucHienLayDuLieuLoaiDanhMuc() {
            try {
                this.loading(true)
                let result = await this.layDuLieuLoaiDanhMuc(this.loaiDanhMucId)
                this.hasData = result.length > 0

                switch (parseInt(this.loaiDanhMucId)) {
                    case this.LOAI_DM.TUYEN_TRUYEN_DAN:
                        this.dsTuyenTruyenDan = result
                        break
                    case this.LOAI_DM.LOAI_TUYEN_TRUYEN_DAN:
                        this.dsLoaiTuyenTruyenDan = result
                        break
                    case this.LOAI_DM.LOAI_CAP:
                        this.dsLoaiCap = result
                        break
                    case this.LOAI_DM.LOAI_THIET_BI:
                        this.dsLoaiThietBi = result
                        break
                    case this.LOAI_DM.LOAI_DOI_TUONG:
                        this.dsLoaiDoiTuong = result
                        break
                    default:
                        this.dsTuyenTruyenDan = []
                        this.dsLoaiTuyenTruyenDan = []
                        this.dsLoaiCap = []
                        this.dsLoaiThietBi = []
                        this.dsLoaiDoiTuong = []
                }
            } catch (err) {

            } finally {
                this.loading(false)
            }
        },
        async layDuLieuLoaiDanhMuc(loaiDmId) {
            try {
                let response = await this.$root.context.get(`/web-cabman/danhmuc/tuyentd/napLoaiDmTd?loaiDm=${loaiDmId}`)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    return response.data
                } 
            
                throw {response: { data: response}}
            } catch (err) {
                return []
            }
        },
        async xoaDanhMuc(data) {
            try {
                let response = await this.$root.context.post(`/web-cabman/danhmuc/tuyentd/xoaDanhMuc`, data)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    
                    if (response.data.KQ == 1) {
                        return
                    } 

                    throw response.data.MESSAGE
                } 
            
                throw 'Có lỗi khi thực hiện xóa'
            } catch (err) {
                throw err
            }
        },
        async luuDmTuyenTruyenDan(data) {
            try {
                let response = await this.$root.context.post(`/web-cabman/danhmuc/tuyentd/luuDmTuyenTruyenDan`, data)
                
                if (response.error_code == 'BSS-00000000') {
                    return response.data
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
        async luuDmLoaiTuyenTruyenDan(data) {
            try {
                let response = await this.$root.context.post(`/web-cabman/danhmuc/tuyentd/luuDmLoaiTuyenTruyenDan`, data)
                
                if (response.error_code == 'BSS-00000000') {
                    return response.data
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
        async luuDmLoaiCap(data) {
            try {
                let response = await this.$root.context.post(`/web-cabman/danhmuc/tuyentd/luuDmLoaiCap`, data)
                
                if (response.error_code == 'BSS-00000000') {
                    return response.data
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
        async luuDmLoaiThietBi(data) {
            try {
                let response = await this.$root.context.post(`/web-cabman/danhmuc/tuyentd/luuDmLoaiThietBi`, data)
                
                if (response.error_code == 'BSS-00000000') {
                    return response.data
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
        async luuDmLoaiDoiTuong(data) {
            try {
                let response = await this.$root.context.post(`/web-cabman/danhmuc/tuyentd/luuDmLoaiDoiTuong`, data)
                
                if (response.error_code == 'BSS-00000000') {
                    return response.data
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
     }
}
</script>
<style>
    .frm-danhmuc-tuyen-truyen-dan .disable-btn:hover {
        background-color: white !important;
    }

    .frm-danhmuc-tuyen-truyen-dan .disable-btn a {
        color: #6c757d !important;
    }

    .frm-danhmuc-tuyen-truyen-dan .disable-btn a:hover {
        cursor: default;
        color: #6c757d;
    }
    
    .frm-danhmuc-tuyen-truyen-dan .select2-container .select2-selection--single .select2-selection__rendered {
        white-space: initial;
    }

    .frm-danhmuc-tuyen-truyen-dan .btn-border {
        border: 1px solid skyblue;
        border-radius: 4px;
    }

    .frm-danhmuc-tuyen-truyen-dan .e-colorpicker-wrapper .e-split-preview {
        border: 1px solid black;
    }

    .frm-danhmuc-tuyen-truyen-dan .e-colorpicker-wrapper .e-split-colorpicker.e-split-btn {
        padding: 0 5px !important;
        width: 70px;
    }

    .frm-danhmuc-tuyen-truyen-dan .e-colorpicker-wrapper .e-split-colorpicker.e-split-btn .e-btn-icon {
        width: 100%;
    }
</style>
