<template src="./index.html"></template>

<script>
export default {
  name: 'ModalPortThietBi',
  props: {
    loaiCongList: {
      type: Array,
      default: () => []
    }
  },
  data: function() {
    return {
      // Port information model - đúng format với data grid
      portInfo: {
        ID: null, // ID của port (null khi insert, có giá trị khi update)
        TEN: '', // Tên cổng
        LOAICONG_ID: '', // Loại cổng ID
        GIATRI_CONG: '', // Tốc độ/giá trị cổng
        THONGSO_KYTHUAT: '', // Thông số kỹ thuật
        TRANGTHAI_SD: '', // Trạng thái port vận hành
        GHI_CHU: '' // Ghi chú
      },

      // Form validation controls - đúng format với data grid
      controls: {
        TEN: { invalid: false },
        LOAICONG_ID: { invalid: false },
        GIATRI_CONG: { invalid: false },
        THONGSO_KYTHUAT: { invalid: false },
        TRANGTHAI_SD: { invalid: false }
      },

      // Dropdown options (loaded from API)
      trangThaiPortVanHanhOptions: [],
      isEditMode: false,
    }
  },
  components: {},
  computed: {},
  watch: {},
  created: function() {
  },
  methods: {
    async show(portData = null) {
      this.$bvModal.show('ModalPortThietBi')
      await this.loadDefaultData()
      this.portInfo = { ...portData }
      if(!portData) {
        this.portInfo.TRANGTHAI_DAUNOI = 'Chưa kết nối'
        this.portInfo.TRANGTHAI_SD = '0'
      }
      this.isEditMode = portData ? portData.isEdit : false
    },

    closeModal() {
      this.$bvModal.hide('ModalPortThietBi')
      this.resetForm()
    },

    resetForm() {
      this.portInfo = {
        ID: null,
        TEN: '',
        LOAICONG_ID: '',
        GIATRI_CONG: '',
        THONGSO_KYTHUAT: '',
        TRANGTHAI_SD: '',
        GHI_CHU: ''
      },
      this.resetValidation()
    },

    async loadDefaultData() {
      this.resetValidation()
      try {
        this.$root.showLoading(true)
        this.loadTrangThaiPortVanHanhOptions()
      } catch (e) {
        console.error(e)
      } finally {
        this.$root.showLoading(false)
      }
    },

    loadTrangThaiPortVanHanhOptions() {
      this.trangThaiPortVanHanhOptions = [
        { ID: '0', TEN: 'Chưa sử dụng' },
        { ID: '1', TEN: 'Đang sử dụng' },
        { ID: '2', TEN: 'Hỏng' }
      ]
    },
    // Lấy danh mục chung
    async getDanhMucCommon(axios, loaiDanhMuc, thamSo1 = null) {
      try {
        const payload = { loaiDanhMuc }
        if (thamSo1 !== null && thamSo1 !== undefined) {
          payload.thamSo1 = thamSo1
        }
        const rs = await axios.post('/web-ecms/idc/danh-muc/common', payload)
        return rs && rs.data ? rs.data.data : []
      } catch (error) {
        handleApiError(error)
        return []
      }
    },

    resetValidation() {
      // Reset validation states
      Object.keys(this.controls).forEach((key) => {
        this.controls[key].invalid = false
      })
    },

    validateForm() {
      let isValid = true
      this.resetValidation()

      if (!this.portInfo.LOAICONG_ID) {
        this.controls.LOAICONG_ID.invalid = true
        isValid = false
      }

      if (!this.portInfo.TEN || this.portInfo.TEN.trim() === '') {
        this.controls.TEN.invalid = true
        isValid = false
      }

      if (!this.portInfo.GIATRI_CONG || this.portInfo.GIATRI_CONG.trim() === '') {
        this.controls.GIATRI_CONG.invalid = true
        isValid = false
      }

      if (!this.portInfo.THONGSO_KYTHUAT || this.portInfo.THONGSO_KYTHUAT.trim() === '') {
        this.controls.THONGSO_KYTHUAT.invalid = true
        isValid = false
      }

      if (
        this.portInfo.TRANGTHAI_SD === null ||
        this.portInfo.TRANGTHAI_SD === '' ||
        this.portInfo.TRANGTHAI_SD === undefined
      ) {
        this.controls.TRANGTHAI_SD.invalid = true
        isValid = false
      }

      return isValid
    },

    savePortInfo() {
      if (!this.validateForm()) {
        this.$toast.error('Vui lòng điền đầy đủ các trường bắt buộc!')
        return
      }

      // Xác định mode dựa trên ID
      this.isEditMode = this.portInfo.ID !== null && this.portInfo.ID !== undefined
      const { GIATRI_CONG, ID, LOAICONG_ID, TEN, THIETBI_ID, THONGSO_KYTHUAT, TRANGTHAI_SD, TRANGTHAI_DAUNOI, GHI_CHU } = this.portInfo
      this.$emit('portSaved', {
        GIATRI_CONG,
        ID,
        LOAICONG_ID,
        TEN,
        THIETBI_ID,
        THONGSO_KYTHUAT,
        TRANGTHAI_SD,
        TRANGTHAI_DAUNOI,
        GHI_CHU,
        isEdit: this.isEditMode // Thêm flag để component cha biết đây là edit hay add
      })
      this.closeModal()
    }
  }
}
</script>
<style>
.is-invalid {
  border: 1px solid red;
  border-radius: 5px;
}
</style>
