<template src="./index.template.html"></template>
<script>
import Treeselect from '@riophae/vue-treeselect'
import '@riophae/vue-treeselect/dist/vue-treeselect.css'
import api from '../../../api'
import moment from 'moment';

export default {
  name: 'TaiSan',
  components: {
    Treeselect
  },
  props: {
    currentItem: {
      type: Object,
      required: true
    },
    currentMode: {
      type: String,
      required: true
    },
    controls: {
      type: Object,
      required: true
    }
  },
  data() {
    return {
      // Danh mục
      donViQuanLyList: [],
      caNhanQuanLyList: [],
      phongToQuanLyList: [],
    }
  },
  computed: {
    // Lấy cụm control tab tài sản
    tabTaiSanControl() {
      return this.controls?.tabTaiSanControl || {}
    },    
  },
  watch: {
    'currentItem.id': async function(newVal) {
      if (newVal && newVal !== 0) {
        console.log('vao tab tài sản')
        console.log(newVal)
        await this.getTTTaiSan()
      } else {
        //this.nghiepvuList = []
      }
    },
    'currentItem.donViQuanLy': async function(newVal) {
      if (newVal && newVal !== 0) {        
        await this.loadToQuanLy(newVal)
      } else {
        this.phongToQuanLyList = []
      }
    },    
    'currentItem.phongToQuanLy': async function(newVal) {
      if (newVal && newVal !== 0) {
        await this.loadCaNhanQuanLy(newVal)
      } else {
        this.caNhanQuanLyList = []
      }
    },  
  },
  methods: {
    resetValidation() {
      if (!this.tabTaiSanControl) {
        return
      }
      Object.keys(this.tabTaiSanControl).forEach((key) => {
        const control = this.tabTaiSanControl[key]
        if (control && control.invalid) {
          this.$set(control, 'invalid', false)
        }
      })
    },
    validateForm() {
      const item = this.currentItem || {}
      let isValid = true
      this.resetValidation()

      const require = (condition, controlKey) => {
        if (condition) {
          return
        }
        const control = this.tabTaiSanControl?.[controlKey]
        if (control) {
          this.$set(control, 'invalid', true)
        }
        isValid = false
      }
      require(item.maDuAn && item.maDuAn.trim() !== '', 'txtMaDuAn')
      require(item.theTaiSan && item.theTaiSan.trim() !== '', 'txtTheTaiSan')
      require(item.idChiTietTS && item.idChiTietTS.trim() !== '', 'txtIDChiTietTS')
      require(item.maVatTu && item.maVatTu.trim() !== '', 'txtMaVatTu')
      require(item.tenDuAn && item.tenDuAn !== 0, 'txtTenDuAn')
      require(item.donViQuanLy && item.donViQuanLy !== 0, 'selectDonViQuanLy')
      require(item.caNhanQuanLy && item.caNhanQuanLy !== 0, 'selectCaNhanQuanLy')
      require(item.phongToQuanLy && item.phongToQuanLy !== 0, 'selectPhongToQuanLy')
      require(item.emailSDT && item.emailSDT.trim() !== '', 'txtEmailSDT')
      
      if (!isValid) {
        this.$root.toastError('Vui lòng nhập đầy đủ thông tin bắt buộc!')
      }

      return isValid
    },
    formatDateValue(value) {
      if (!value) {
        return null
      }      
      return moment(new Date(value)).format('YYYY-MM-DD');
      //return this.$moment ? this.$moment(value).format('YYYY-MM-DD') : value
    },
    sanitizePayload(payload = {}) {
      const sanitized = {}
      Object.keys(payload).forEach((key) => {
        const value = payload[key]
        if (value !== undefined) {
          sanitized[key] = value
        }
      })
      return sanitized
    },    
    buildTabTaiSanPayload() {
      const item = this.currentItem || {}
      const payload = {
        id: item.id,
        maDa: item.maDuAn,
        tenDa: item.tenDuAn,
        maTs: item.theTaiSan,
        ngayKh: this.formatDateValue(item.ngayDKKhauHao),
        taiSanId: item.idChiTietTS,
        hanBaoHanh: this.formatDateValue(item.hanBaoHanh),
        maVtu: item.maVatTu,
        ngayLapDat: this.formatDateValue(item.ngayLapDat),
        ngaySuDung: this.formatDateValue(item.ngaySuDung),
        loaiKhachHang: item.loaiKhachHangId,
        chuQuanId: item.soHuuThietBiId,
        donViQLyId: item.donViQuanLy,
        nguoiQLyId: item.caNhanQuanLy,
        phongBanQLyId: item.phongToQuanLy,
        emailSDT: item.emailSDT
      }
      return this.sanitizePayload(payload)
    },
    
    normalizer(node) {
      return {
        id: node.ID || node.id,
        label: node.TEN || node.name || node.label,
        children: node.children || node.items
      }
    },
    async loadInitialData() {
      try {
        this.$root.showLoading(true)
        const loadPromises = [
          this.loadDonViQuanLy()          
        ]
        await Promise.all(loadPromises)
      } catch (error) {
        this.$root.toastError(error?.message || 'Có lỗi xảy ra khi tải danh mục')
      } finally {
        this.$root.showLoading(false)
      }
    },    
    async loadDonViQuanLy() {
      try {
        this.donViQuanLyList = await api.getDSDonViQuanLy(this.axios)
        this.onChangeDVQL()
      } catch (error) {
        this.donViQuanLyList = []
      }
    },
    async loadToQuanLy(donViQLyId) {
      try {
        this.phongToQuanLyList = await api.getDSToQuanLy(this.axios,donViQLyId)
        this.onChangeToQL()
      } catch (error) {
        this.phongToQuanLyList = []
      }
    },
    async loadCaNhanQuanLy(toqlId) {
      try {
        this.caNhanQuanLyList = await api.getDSNhanVienQuanLy(this.axios,toqlId)
      } catch (error) {
        this.caNhanQuanLyList = []
      }
    },
    async getTTTaiSan() {
      try {
        console.log('getTTTaiSan')
        console.log(this.currentItem.id)
        const response = await this.$root.context.post('/web-ecms/idc/hatang-cntt/thiet-bi/chi-tiet-tai-san',{'id':this.currentItem.id});
        if(response && response.data && response.data.length > 0){
          const item = response.data[0]
          this.currentItem.maDuAn =  item.MA_DA
          this.currentItem.theTaiSan =  item.MA_TS 
          this.currentItem.idChiTietTS =  item.TAISAN_ID 
          this.currentItem.maVatTu =  item.MA_VTU 
          this.currentItem.tenDuAn =  item.TEN_DA 
          this.currentItem.ngayDKKhauHao = item.NGAY_KHAUHAO?moment(item.NGAY_KHAUHAO,'YYYY-MM-DD').format('DD/MM/YYYY'):''   
          this.currentItem.hanBaoHanh =  item.NGAY_BAOHANH?moment(item.NGAY_BAOHANH,'YYYY-MM-DD').format('DD/MM/YYYY'):'' 
          this.currentItem.ngayLapDat =  item.NGAY_LAPDAT?moment(item.NGAY_LAPDAT,'YYYY-MM-DD').format('DD/MM/YYYY'):''  
          this.currentItem.ngaySuDung =  item.NGAY_SUDUNG?moment(item.NGAY_SUDUNG,'YYYY-MM-DD').format('DD/MM/YYYY'):''   
          this.currentItem.donViQuanLy =  item.DONVIQLY_ID 
          this.currentItem.caNhanQuanLy =  item.NGUOIQLY_ID 
          this.currentItem.phongToQuanLy =  item.PHONGBANQLY_ID 
          this.currentItem.emailSDT =  item.EMAIL_SDT 
        }
      } catch (error) {
        console.error('Error fetching nghiệp vụ list:', error);
      }
    },
    async onChangeDVQL() {        
        if (this.currentItem.donViQuanLy){
          await this.loadToQuanLy(this.currentItem.donViQuanLy)
        }
    },
    async onChangeToQL() {        
        if (this.currentItem.phongToQuanLy){
          await this.loadCaNhanQuanLy(this.currentItem.phongToQuanLy)
        }
    },
    async onChangeCNQL(item) {        
        if (item){
          this.currentItem.emailSDT = item.EMAIL
        }
    },
    async onSave(){
      const payload = await this.buildTabTaiSanPayload()
      const response = await this.$root.context.post('/web-ecms/idc/hatang-cntt/thiet-bi/cap-nhat-tai-san',payload);
      if(response && response.data && response.error_code == 'BSS-00000000'){
        this.$toast.success('Lưu tài sản thành công!')
      }

    },
    /**
     * Kiểm tra field có required không
     * @param {string} fieldKey - Tên field
     * @returns {boolean} true nếu required
     */
    
    /**
     * Lấy class cột theo section
     * @param {string} section - Tên section (system/basic/dacTinhKyThuat)
     * @returns {string} class cột Bootstrap
     */
    getSectionColClass(section) {
      const sectionClassMap = {
        system: 'col-sm-4 col-12',
        basic: 'col-sm-4 col-12',
        dacTinhKyThuat: 'col-sm-6 col-12'
      }
      return sectionClassMap[section] || 'col-sm-4 col-12'
    },
    /**
     * Lấy số field trên mỗi row theo section
     * @param {string} section
     * @returns {number}
     */
    getFieldsPerRow(section) {
      const sectionRowMap = {
        system: 3,
        basic: 3,
        dacTinhKyThuat: 2
      }
      return sectionRowMap[section] || 3
    }
  }  
}
</script>
<style scoped>
.form-control::placeholder {
  color: #999 !important;
}
/deep/ .vue-treeselect__placeholder {
  color: #999 !important;
}

.info-row {
  display: flex;
  align-items: center;
  margin-bottom: 5px;
}

.key {
  min-width: 140px;
  padding-right: 10px;
}

.value {
  flex: 1;
}

.padt4 {
  padding-top: 4px;
}

.check-action {
  display: flex;
  align-items: center;
}

.check-action .check {
  margin-right: 5px;
}

.red {
  color: red;
}

.avatar-upload .avatar-thumb {
  max-height: 120px;
  width: auto;
  border-radius: 6px;
  object-fit: cover;
}

.avatar-upload .avatar-preview {
  max-width: 180px;
}
.is-invalid {
  border: 1px solid #dc3545 !important;
  border-radius: 4px !important;
  box-shadow: none !important; /* Xóa bóng mờ */
  outline: none !important;    /* Xóa viền outline mặc định */
}

</style>
