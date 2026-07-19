<template src="./index.template.html"></template>
<style>
    .loai-ewsd .disable-btn:hover {
        background-color: white !important;
    }

    .loai-ewsd .disable-btn a {
        color: #6c757d !important;
    }

    .loai-ewsd .disable-btn a:hover {
        cursor: default;
        color: #6c757d;
    }

    .loai-ewsd .long-text {
        white-space: normal;
        word-break: break-all;
    }
</style>
<script>
import BssErrorMarker from '@/components/BssErrorMarker'
import BssRequiredMarker from '@/components/BssRequiredMarker'
import { required, maxLength } from 'vuelidate/lib/validators'
export default {
    props:{
        modalId: String
    },
    components: {BssErrorMarker, BssRequiredMarker},
    data() {
        return {
            dsLoaiEwsd: [],
            tenLoaiThietBi: null,
            loaiEwsdDuocChon: null,
            tableLoaiEwsdKey: 1,
            btnNhapMoiEnabled: false,
            btnGhiLaiEnabled: false,
            btnXoaEnabled: false,
            btnHuyBoEnabled: false,
            value: null
        }
    },
    validations: {
        tenLoaiThietBi: { required, maxLength: maxLength(100) }
    },
    methods:{
        onModalHidden() {
            this.dsLoaiEwsd = []
            this.tenLoaiThietBi = null
            this.loaiEwsdDuocChon = null
            this.btnNhapMoiEnabled = false
            this.btnGhiLaiEnabled = false
            this.btnXoaEnabled = false
            this.btnHuyBoEnabled = false
        },
        onModalShown() {
            this.setButton(1)
            this.$refs['ref-ten-loai-ewsd'].focus()
            this.thucHienLayDsLoaiEwsd()
        },
        async thucHienLayDsLoaiEwsd() {
            try {
                this.loading(true)
                await this.layDsLoaiEwsd()
            } catch (err) {

            } finally {
                this.loading(false)
            }
        },
        async okXoa() {
            try {
                this.loading(true)
                await this.xoaLoaiEwsd(this.loaiEwsdDuocChon.LOAI_EWSD_ID)
                this.loaiEwsdDuocChon = null
            } catch (err) {
                this.$toast.error(err)
                return
            } finally {
                this.loading(false)
            }

            this.$toast.success('Xóa loại thiết bị EWSD thành công !')
            this.thucHienLayDsLoaiEwsd()
        },
        onClickBtnXoa() {

            if (!this.btnXoaEnabled) {
                return
            }

            if (!this.loaiEwsdDuocChon) {
                this.$toast.error("Chưa chọn loại Ewsd để xóa !")
                return
            }

            this.$bvModal.show('modal-xacnhan-xoa')
        },
        onClickRowTableLoaiEwsd(data) {
            this.loaiEwsdDuocChon = data
            this.tenLoaiThietBi = data.LOAI_EWSD
            this.setButton(3)
        },
        onClickBtnHuy() {

            if (!this.btnHuyBoEnabled) {
                return
            }

            this.setButton(2)
            this.tenLoaiThietBi = null
            this.$v.$reset()
        },
        async onClickBtnGhiLai() {

            if (!this.btnGhiLaiEnabled) {
                return
            }

            let errorMessage = []

            this.$v.$touch()

            if (this.$v.$invalid) {

                if (!this.$v.tenLoaiThietBi.required) {
                    errorMessage.push("Tên loại Ewsd không được để trống !")
                }

                if (!this.$v.tenLoaiThietBi.maxLength) {
                    errorMessage.push("Tên loại thiết bị không được quá 100 ký tự !")
                }
            }

            if (this.btnNhapMoiEnabled && !this.loaiEwsdDuocChon) {
                errorMessage.push("Chưa chọn loại Ewsd để cập nhật !")
            }

            if (errorMessage.length > 0) {
                this.$toast.error(errorMessage.join('\n'))
                return
            }

            let data = {
                loai_ewsd: this.tenLoaiThietBi
            }

            try {
                this.loading(true)

                if (!this.btnNhapMoiEnabled) {
                    var result = await this.themLoaiEwsd(data)
                    this.dsLoaiEwsd.unshift(result)
                    this.$toast.success('Thêm loại thiết bị EWSD thành công !')
                } else {
                    data.loai_ewsd_id = this.loaiEwsdDuocChon.LOAI_EWSD_ID
                    await this.capNhatLoaiEwsd(data)
                    this.loaiEwsdDuocChon.LOAI_EWSD = data.loai_ewsd
                    this.$refs['ref-grid'].grid.setCellValue(data.loai_ewsd_id, 'LOAI_EWSD', data.loai_ewsd)
                    this.$toast.success('Cập nhật loại thiết bị EWSD thành công !')
                }
            } catch (err) {
                this.$toast.error(err)
                return
            } finally {
                this.loading(false)
            }

            this.setButton(3)
        },
        onClickBtnNhapMoi() {

            if (!this.btnNhapMoiEnabled) {
                return
            }

            this.setButton(1)
            this.tenLoaiThietBi = null
            this.$v.$reset()
        },
        setButton(kieu) {
            this.btnNhapMoiEnabled = false
            this.btnGhiLaiEnabled = false
            this.btnXoaEnabled = false
            this.btnHuyBoEnabled = false

            if (kieu == -1) {//Bat dau
                this.btnGhiLaiEnabled = true
                this.btnHuyBoEnabled = true
            }

            if (kieu == 0) {//Bat dau
                this.$refs['ref-ten-loai-ewsd'].focus()
                this.btnNhapMoiEnabled = true
            }

            if (kieu == 1) {//Them moi
                this.$refs['ref-ten-loai-ewsd'].focus()
                this.btnGhiLaiEnabled = true
                this.btnHuyBoEnabled = true
            }

            if (kieu == 2) {//Huy
                this.btnNhapMoiEnabled = true
                this.btnXoaEnabled = false
            }

            if (kieu == 3) {//Edit
                this.btnNhapMoiEnabled = true
                this.btnXoaEnabled = true
                this.btnGhiLaiEnabled = true
                this.btnHuyBoEnabled = true
            }
        },
        async layDsLoaiEwsd() {
            try {
                let response = await this.$root.context.get('/web-ecms/danhmuc/loaiewsd/dsloaiewsd')

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    this.dsLoaiEwsd = response.data
                } else {
                    this.dsLoaiEwsd = []
                }
            } catch (err) {
                this.dsLoaiEwsd = []
            }
        },
        async themLoaiEwsd(data) {
            try {
                let response = await this.$root.context.post('/web-ecms/danhmuc/loaiewsd/them', data)
                
                if (response.error_code != 'BSS-00000000') {
                    throw {response: { data: response}}
                }

                return response.data
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
        async capNhatLoaiEwsd(data) {
            try {
                let response = await this.$root.context.post('/web-ecms/danhmuc/loaiewsd/capnhat', data)
                
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
        async xoaLoaiEwsd(loaiEwsdId) {
            try {
                let response = await this.$root.context.post(`/web-ecms/danhmuc/loaiewsd/xoa?loai_ewsd_id=${loaiEwsdId}`)
                
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

