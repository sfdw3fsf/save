<template src="./index.template.html"></template>
<script>
import BssErrorMarker from '@/components/BssErrorMarker'
import BssRequiredMarker from '@/components/BssRequiredMarker'
import { required } from 'vuelidate/lib/validators'

export default {
    components: {BssErrorMarker, BssRequiredMarker, required},
    props:{
        modalId: String
    },
    data() {
        return {
            dsLoaiDslam: [],
            dsNhomDslam: [],
            loaiDslamDuocChon: null,
            nhomDslamId: null,
            loaiDslam: null,
            thamSo: 0,
            ghiChu: null,
            chkPortTd: false,
            chkBrasTs: false,
            tableKey: 1,
            btnNhapMoiEnabled: false,
            btnGhiLaiEnabled: false,
            btnXoaEnabled: false,
            btnHuyBoEnabled: false,
        }
    },
    validations: {
        nhomDslamId: {
            required
        },
        loaiDslam: {
            required
        },
        thamSo: {
            required
        },
    },
    methods: {
        onModalHidden() {
            this.dsLoaiDslam = []
            this.dsNhomDslam = []
            this.loaiDslamDuocChon = null
            this.nhomDslamId = null
            this.chkPortTd = false
            this.chkBrasTs = false
            this.btnNhapMoiEnabled = false
            this.btnGhiLaiEnabled = false
            this.btnXoaEnabled = false
            this.btnHuyBoEnabled = false
        },
        async onModalShown() {

            this.setButton(1)
            this.$refs['ref-loai-dslam'].focus()

            try {
                this.loading(true)
                let loading1 = this.layDsNhomDslam()
                let loading2 = this.layDsLoaiDslam()
                await loading1
                await loading2
            } catch (err) {

            } finally {
                this.loading(false)
            }

            if (this.dsNhomDslam && this.dsNhomDslam.length > 0) {
                this.nhomDslamId = this.dsNhomDslam[0].NHOM_DSLAM_ID
            }
        },
        async thucHienLayDsLoaiDslam() {
            try {
                this.loading(true)
                await this.layDsLoaiDslam()
            } catch (err) {

            } finally {
                this.loading(false)
            }
        },
        async okXoa() {
            try {
                this.loading(true)
                await this.xoa(this.loaiDslamDuocChon.LOAI_DSLAM_ID)
                this.loaiDslamDuocChon = null
            } catch (err) {
                this.$toast.error(err)
                return
            } finally {
                this.loading(false)
            }

            this.$toast.success('Xóa loại Dslam thành công !')
            this.thucHienLayDsLoaiDslam()
        },
        onClickBtnXoa() {

            if (!this.btnXoaEnabled) {
                return
            }

            if (!this.loaiDslamDuocChon) {
                this.$toast.error('Chưa chọn loại Dslam để xóa')
                return
            }

            this.$bvModal.show('modal-xacnhan-xoa')
        },
        async onClickBtnGhiLai() {

            if (!this.btnGhiLaiEnabled) {
                return
            }

            let errorMessage = []

            this.$v.$touch()

            if (this.$v.$invalid) {

                if (!this.$v.nhomDslamId.required) {
                    errorMessage.push("Chưa chọn nhóm dslam !")
                }

                if (!this.$v.loaiDslam.required) {
                    errorMessage.push("Loại dslam không được để trống !")
                }

                if (!this.$v.thamSo.required) {
                    errorMessage.push("Tham số không được để trống !")
                }
            }

            if (this.btnNhapMoiEnabled && !this.loaiDslamDuocChon) {
                errorMessage.push("Chưa chọn loại Dslam để cập nhật !")
            }

            if (errorMessage.length > 0) {
                this.$toast.error(errorMessage.join('\n'))
                return
            }

            let data = {
                p_nhom_dslam_id: this.nhomDslamId,
                p_loai_dslam: this.loaiDslam,
                p_thamso: this.thamSo,
                p_ghichu: this.ghiChu,
                p_port_td: this.chkPortTd ? 1 : 0,
                p_bras_td: this.chkBrasTs ? 1 : 0
            }

            let tenNhomDsLam = this.dsNhomDslam.filter(item => item.NHOM_DSLAM_ID == this.nhomDslamId)[0].NHOM_DSLAM

            try {
                this.loading(true)

                if (!this.btnNhapMoiEnabled) {
                    var result = await this.themMoi(data)
                    result.NHOM_DSLAM = tenNhomDsLam
                    this.dsLoaiDslam.unshift(result)
                    this.$toast.success('Thêm loại Dslam thành công !')
                } else {
                    data.p_loai_dslam_id = this.loaiDslamDuocChon.LOAI_DSLAM_ID
                    await this.capNhat(data)
                    this.loaiDslamDuocChon.NHOM_DSLAM_ID = data.p_nhom_dslam_id
                    this.loaiDslamDuocChon.LOAI_DSLAM = data.p_loai_dslam
                    this.loaiDslamDuocChon.GHICHU = data.p_ghichu
                    this.loaiDslamDuocChon.THAMSO = data.p_thamso
                    this.loaiDslamDuocChon.PORT_TD = data.p_port_td
                    this.loaiDslamDuocChon.BRAS_TS = data.p_bras_td
                    this.loaiDslamDuocChon.NHOM_DSLAM = tenNhomDsLam
                    this.$refs['ref-grid'].grid.batchUpdate({ changedRecords: [{...this.loaiDslamDuocChon}] })
                    this.$toast.success('Cập nhật loại Dslam thành công !')
                }
            } catch (err) {
                this.$toast.error(err)
                return
            } finally {
                this.loading(false)
            }

            this.setButton(3)
        },
        onClickBtnHuy() {
            if (this.btnHuyBoEnabled) {
                this.setButton(2)
            }
        },
        onClickBtnNhapMoi() {
            if (this.btnNhapMoiEnabled) {
                this.setButton(1)
            }
        },
        clear() {
            this.nhomDslamId = this.dsNhomDslam && this.dsNhomDslam.length > 0 ? this.dsNhomDslam[0].NHOM_DSLAM_ID : null
            this.loaiDslam = null
            this.thamSo = 0
            this.ghiChu = null
            this.chkPortTd = false
            this.chkBrasTs = false
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
                this.$refs['ref-loai-dslam'].focus()
                this.btnNhapMoiEnabled = true
                this.clear()
            }
			
            if (kieu == 1) {//Them moi
                this.$refs['ref-loai-dslam'].focus()
                this.btnGhiLaiEnabled = true
                this.btnHuyBoEnabled = true
                this.clear()
            }
			
            if (kieu == 2) {//Huy
                this.btnNhapMoiEnabled = true
                this.btnXoaEnabled = false
                this.clear()
            }
			
            if (kieu == 3) {//Edit
                this.btnNhapMoiEnabled = true
                this.btnXoaEnabled = true
                this.btnGhiLaiEnabled = true
                this.btnHuyBoEnabled = true
                this.$v.$reset()
            }
        },
        onClickRow(dataItem) {
            this.loaiDslamDuocChon = dataItem

            if (!dataItem) {
                return
            }

            this.nhomDslamId = dataItem.NHOM_DSLAM_ID
            this.loaiDslam = dataItem.LOAI_DSLAM
            this.thamSo = !dataItem.THAMSO ? 0 : dataItem.THAMSO
            this.ghiChu = !dataItem.GHICHU ? null : dataItem.GHICHU
            this.chkPortTd = dataItem.PORT_TD == 1
            this.chkBrasTs = dataItem.BRAS_TS == 1
            this.setButton(3)
        },
        async layDsLoaiDslam() {
            try {
                let response = await this.$root.context.get(`/web-ecms/danhmuc/dongthietbi/lietKeDs`)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    this.dsLoaiDslam = response.data
                } else {
                    this.dsLoaiDslam = []
                }
            } catch (err) {
                this.dsLoaiDslam = []
            }
        },
        async layDsNhomDslam() {
            try {
                let response = await this.$root.context.get(`/web-ecms/danhmuc/NhomDslam/DanhSach`)

                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    this.dsNhomDslam = response.data
                } else {
                    this.dsNhomDslam = []
                }
            } catch (err) {
                this.dsNhomDslam = []
            }
        },
        async themMoi(data) {
            try {
                let response = await this.$root.context.post('/web-ecms/danhmuc/dongthietbi/them', data)
                
                if (response.error_code !== 'BSS-00000000') {
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
        async capNhat(data) {
            try {
                let response = await this.$root.context.post('/web-ecms/danhmuc/dongthietbi/capnhat', data)
                
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
        async xoa(loaiDslamId) {
            try {
                let response = await this.$root.context.post(`/web-ecms/danhmuc/dongthietbi/xoa?loai_dslam_id=${loaiDslamId}`)
                
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
    .loai-dslam .disable-btn:hover {
        background-color: white !important;
    }

    .loai-dslam .disable-btn a {
        color: #6c757d !important;
    }

    .loai-dslam .disable-btn a:hover {
        cursor: default;
        color: #6c757d;
    }
</style>