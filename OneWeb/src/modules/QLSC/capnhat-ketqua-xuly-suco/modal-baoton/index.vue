<template src="./index.template.html"></template>
<script>

import Vue from 'vue';
import { DateTimePickerPlugin } from '@syncfusion/ej2-vue-calendars'
Vue.use(DateTimePickerPlugin)

import BssErrorMarker from '@/components/BssErrorMarker'
import BssRequiredMarker from '@/components/BssRequiredMarker'
import { required } from 'vuelidate/lib/validators'

export default {
    components: {BssErrorMarker, BssRequiredMarker},
    props:{
        modalId: String,
        parent: Object
    },
    data() {
        return {
            phieuDuocChon: null,
            nhomScId: null,
            dsNhomSuCo: [],
            dsNhomTon: [],
            nhomTonId: null,
            dsNguyenNhanTon: [],
            nguyenNhanTonId: null,
            dsNhanVien: [],
            nhanVienId: null,
            ngayBaoTon: new Date(),
            dsBaoTon: [],
            ghiChu: null,
            phieuTonDuocChon: null
        }
    },
    validations: {
        nhanVienId: {
            required
        },
        nguyenNhanTonId: {
            required
        },
        ngayBaoTon: {
            required
        }
    },
    watch: {
        async nhomScId(newVal) {

            if (!newVal) {
                this.dsNhomTon = []
                this.nhomTonId = null
                return
            }

            try {
                this.loading(true)
                await this.layDsNhomTon(newVal)
            } catch (err) {

            } finally {
                this.loading(false)
            }

            if (this.dsNhomTon && this.dsNhomTon.length > 0) {
                this.nhomTonId = this.dsNhomTon[0].NHOMTON_ID
            }
        },
        async nhomTonId(newVal) {

            if (!newVal) {
                this.dsNguyenNhanTon = []
                this.nguyenNhanTonId = null
                return
            }

            try {
                this.loading(true)
                await this.layDsNguyenNhanTon(newVal)
            } catch (err) {

            } finally {
                this.loading(false)
            }

            if (this.dsNguyenNhanTon && this.dsNguyenNhanTon.length > 0) {
                this.nguyenNhanTonId = this.dsNguyenNhanTon[0].NNTON_ID
            }
        }
    },
    methods: {
        onModalHidden() {
            this.phieuDuocChon = null
            this.nhomScId = null
            this.dsNhomSuCo = []
            this.dsNhomTon = []
            this.nhomTonId = null
            this.dsNguyenNhanTon = []
            this.nguyenNhanTonId = null
            this.dsNhanVien = []
            this.nhanVienId = null
            this.ngayBaoTon = new Date()
            this.dsBaoTon = []
            this.ghiChu = null
            this.phieuTonDuocChon = null
            this.$v.$reset()
        },
        async onModalShown() {

            this.phieuDuocChon = this.parent.phieuDuocChon

            try {
                this.loading(true)
                let loading1 = this.layDsNhanVien()
                let loading2 = this.layDsBaoTon(this.phieuDuocChon.PHIEU_ID)
                await loading1
                await loading2
            } catch (err) {

            } finally {
                this.loading(false)
            }
            
            this.nhomScId = this.phieuDuocChon.NHOMSC_ID
            this.dsNhomSuCo = this.parent.dsNhomSuCo.filter(item => item.NHOMSC_ID == this.nhomScId)
        },
        onClickBtnHuongDan() {
            this.$root.toastInfo('Hướng dẫn sử dụng')
        },
        async okXoaPhieuTon() {

            let data = {
                phieu_id: this.phieuDuocChon.PHIEU_ID,
                nnton_id: this.phieuTonDuocChon.NNTON_ID
            }

            try {
                this.loading(true)
                await this.huyBaoTon(data)
            } catch (err) {
                this.$root.toastError(err)
                return
            } finally {
                this.loading(false)
            }

            this.$root.toastSuccess('Hủy phiếu thành công!')
            
            try {
                this.loading(true)
                await this.layDsBaoTon(this.phieuDuocChon.PHIEU_ID)
            } catch (err) {

            } finally {
                this.loading(false)
            }
        },
        onClickBtnHuy() {

            if (!this.phieuTonDuocChon) {
                this.$root.toastError('Hãy chọn phiếu báo tồn trên danh sách để hủy!')
                return
            }

            this.$bvModal.show('modal-xacnhan-xoa')
        },
        onSelectRow(rowData) {
            this.phieuTonDuocChon = rowData
        },
        async onClickBtnCapNhat() {

            let errorMessage = []

            this.$v.$touch()

            if (this.$v.$invalid) {

                if (!this.$v.nhanVienId.required) {
                    errorMessage.push("Hãy nhập nhân viên báo tồn")
                }

                if (!this.$v.nguyenNhanTonId.required) {
                    errorMessage.push("Hãy nhập nguyên nhân tồn")
                }

                if (!this.$v.ngayBaoTon.required) {
                    errorMessage.push("Hãy nhập ngày báo tồn")
                }
            }

            if (errorMessage.length > 0) {
                this.$root.toastError(errorMessage.join('\n'))
                return
            }

            let data = {
                phieu_id: this.phieuDuocChon.PHIEU_ID,
                nnton_id: this.nguyenNhanTonId,
                ngay_bt: this.ddmmyyyyhhmi(this.ngayBaoTon),
                nhanvien_bt_id: this.nhanVienId,
                ghichu: this.ghiChu
            }

            try {
                this.loading(true)
                await this.capNhatBaoTon(data)
            } catch (err) {
                this.$root.toastError(err)
                return
            } finally {
                this.loading(false)
            }

            this.$root.toastSuccess('Cập nhật thành công!')
            this.$emit('success')
            this.$bvModal.hide(this.modalId)
        },
        async huyBaoTon(data) {
            try {
                let response = await this.$root.context.post(`/web-suco/ketqua-xuly-suco/huy-baoton`, data)
                
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
        async capNhatBaoTon(data) {
            try {
                let response = await this.$root.context.post(`/web-suco/ketqua-xuly-suco/capnhat-baoton`, data)
                
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
        async layDsNhomTon(nhomScId) {
            try {
                let response = await this.$root.context.get(`/web-suco/danhmuc/ds-nhomton?nhomsc_id=${nhomScId}`)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    this.dsNhomTon = response.data
                } else {
                    this.dsNhomTon = []
                }
            } catch (err) {
                this.dsNhomTon = []
            }
        },
        async layDsNguyenNhanTon(nhomTonId) {
            try {
                let response = await this.$root.context.get(`/web-suco/danhmuc/ds-nguyennhan-ton?nhomton_id=${nhomTonId}`)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    this.dsNguyenNhanTon = response.data
                } else {
                    this.dsNguyenNhanTon = []
                }
            } catch (err) {
                this.dsNguyenNhanTon = []
            }
        },
        async layDsNhanVien() {
            try {
                let response = await this.$root.context.get(`/web-suco/danhmuc/ds-nhanvien-donvi-login`)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    this.dsNhanVien = response.data
                } else {
                    this.dsNhanVien = []
                }
            } catch (err) {
                this.dsNhanVien = []
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