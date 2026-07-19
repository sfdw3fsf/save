<template src="./template.html"></template>
<script>
import breadcrumb from '@/components/breadcrumb'
import DatePicker from 'vue2-datepicker'
import moment from 'moment'
import _ from 'lodash'
import DiaChiKhachHangModal from './popups/ChonDiaChi/DiaChiKhachHangModal.vue'
import DauMoiLienHe from './components/DauMoiLienHe.vue'
import NhanSuQuyetDinh from './components/NhanSuQuyetDinh.vue'
import API from './api'
import apiHelper from './api.helper'
import util from './utils'

export default {
  name: 'ThuThapThongTinKHTiemNangDN',
  components: {
    DatePicker,
    breadcrumb,
    DiaChiKhachHangModal,
    DauMoiLienHe,
    NhanSuQuyetDinh
  },
  data() {
    return {
      header: {
        title: 'Thu thập thông tin khách hàng tiềm năng doanh nghiệp',
        list: [
          { name: 'Thu thập thông tin khách hàng tiềm năng doanh nghiệp', link: { name: 'Ui.cards' } },
          { name: 'Thu thập thông tin khách hàng tiềm năng doanh nghiệp', link: { name: 'Ui.buttons' } }
        ]
      },
      actions: [
        { id: 'btnNew', icon: 'icon one-file-plus', name: 'Nhập mới', visible: true, enable: false },
        { id: 'btnSave', icon: 'icon one-save', name: 'Ghi lại', visible: true, enable: true },
        { id: 'btnDelete', icon: 'icon one-trash', name: 'Xoá ', visible: true, enable: true }
      ],
      isNew: true,
      customerId: '',
      customerName: '',
      taxIdentification: '',
      txtAddress: '',
      revenue: 0,
      enterpriseSize: 0,
      corporateDate: null,
      phoneNumber: '',
      txtWebsite: '',
      txtGhiChu: '',
      relevantInfo: [],
      officerDecideInfo: [],
      createBy: '',

      dataDiaChi: {
        diachi_id: 0,
        tinh_id: 0,
        quan_id: 0,
        phuong_id: 0,
        pho_id: 0,
        ap_id: 0,
        khu_id: 0,
        dacdiem_id: 0,
        sonha: '',
        diachi: ''
      },
      dataDiaChiKH: {
        diachi_id: 0,
        tinh_id: 0,
        quan_id: 0,
        phuong_id: 0,
        pho_id: 0,
        ap_id: 0,
        khu_id: 0,
        dacdiem_id: 0,
        sonha: '',
        diachi: ''
      },

      cboLinhVucHD: {
        dataSource: [],
        value: 0,
        visible: true,
        enable: true
      },
      cboNganhNghe: {
        dataSource: [],
        value: 0,
        visible: true,
        enable: true
      },
      dropdownKhoiKH: {
        value: 'GOV 1',
        options: []
      },
      dropdownKVHoatDong: {
        value: '',
        options: []
      }
    }
  },
  computed: {
    addressCpt(){

    },
    isAuthorityCreate() {
       this.$root.token.getUserName() == this.createBy
    }
  },
  async created() {

  },
  async mounted() {
    this.dropdownKhoiKH.options = [
      { value: 'GOV 1', text: 'GOV 1' },
      { value: 'GOV 2', text: 'GOV 2' },
      { value: 'SOE 1', text: 'SOE 1' },
      { value: 'SOE 2', text: 'SOE 2' },
      { value: 'SOE 3', text: 'SOE 3' },
      { value: 'SOE 4', text: 'SOE 4' },
      { value: 'SME 1', text: 'SME 1' },
      { value: 'SME 2', text: 'SME 2' }
    ]
    await Promise.all([
      this.getKhuVucHoatDong(),
      this.getNganhnghe(),
      this.getLinhVuc()
    ])
  },
  methods: {
    async onClickAction(id) {
      switch (id) {
        case 'btnNew':
          await this.btnNew_Click()
          break
        case 'btnSave':
          await this.btnSave_Click()
          break
        case 'btnDelete':
          await this.btnDelete_Click()
          break
        default:
          console.log('click Actions')
          break
      }
    },

    onEnterTaxCode() {
      this.handleOnclickSearchTaxCode()
    },

    btnNew_Click() {
      this.isNew = true
      this.onSetButtonEnable('btnNew', false)
    },

    async handleOnclickSearchTaxCode() {
      try {
        if (this.taxIdentification == '') {
          this.$toast.warning('Mã số thuế không được để trống')
          return
        }
        const data = await this.searchCustomerByTaxCode(this.taxIdentification)
        const { elements } = data

        if (!elements  || elements?.length == 0) {
          this.$toast.warning(`Khách hàng chưa tồn tại trên hệ thống - MST: ${this.taxIdentification}`)
          return
        }
        const customerInfo = elements[0]
        if (!customerInfo) return

        this.isNew = false
        this.onSetButtonEnable('btnNew', true)

        this.dropdownKhoiKH.value = customerInfo.enterpriseInfo.customerDepartment || 0
        this.cboNganhNghe.value = customerInfo.enterpriseInfo.career || 0
        this.cboLinhVucHD.value = customerInfo.enterpriseInfo.activity || 0
        this.dropdownKVHoatDong.value = customerInfo.enterpriseInfo.workingArea || 0

        this.customerId = customerInfo.enterpriseInfo.customerId || ""
        this.customerName = customerInfo.enterpriseInfo.customerName || ""
        this.phoneNumber = customerInfo.enterpriseInfo.phoneNumber || ""
        this.txtWebsite = customerInfo.enterpriseInfo.website || ""
        this.revenue = customerInfo.enterpriseInfo.revenuePerYear || 0
        this.corporateDate = customerInfo.enterpriseInfo.corporateDate ? new Date(moment(customerInfo.enterpriseInfo.corporateDate, 'DD/MM/YYYY')) : null

        this.relevantInfo = customerInfo.relevantInfos || []; 
        this.officerDecideInfo = customerInfo.deciderInfos || [];
        this.createBy = customerInfo.createBy || '';
       
       
        this.enterpriseSize = customerInfo.enterpriseInfo.scale || 0
        this.revenue = customerInfo.enterpriseInfo.totalRevenue || 0
        
        const addressInfo = customerInfo.enterpriseInfo.addressInfo
        if(addressInfo){
          // this.dataDiaChi.diachi_id = customerInfo.addressInfo. || {};
          this.dataDiaChi.tinh_id = addressInfo.cityId || 0;
          this.dataDiaChi.quan_id = addressInfo.districtId || 0;
          this.dataDiaChi.phuong_id = addressInfo.wardId || 0;
          this.dataDiaChi.pho_id = addressInfo.streetId || 0;
          this.dataDiaChi.khu_id = addressInfo.areaId || 0;
          this.dataDiaChi.ap_id = addressInfo.hamletId || 0
          this.dataDiaChi.diachi = addressInfo.additionalAddress || '';
          this.txtAddress = addressInfo.additionalAddress || '';
        }
      } catch (error) {
        console.log(error)
      }
    },

    async btnDelete_Click() {
        this.cleanInput()
    },
    async btnSave_Click() {
      try {
        if (this.isNew) {
          const result = await this.handleCreateNew()
        } else {
          const result = await this.handleUpdate()
        }
      } catch (error) {
        console.log(error)
      }
    },

    onSetButtonEnable(id, enable) {
      this.actions.find((item) => item.id === id).enable = enable
    },

    onSetButtonVisible(id, isVisible) {
      this.actions.find((item) => item.id === id).visible = isVisible
    },

    acceptDiaChiKH(data) {
      //handle
      this.txtAddress = data.diachi
      Object.assign(this.dataDiaChiKH, data)
      console.log(this.dataDiaChiKH)
      this.$emit('callbackChonDiChiKH', this.dataDiaChiKH)
    },

    showPopupSearchAddress() {
      Object.assign(this.dataDiaChi, this.dataDiaChiKH)
      //địa chỉ khách hàng
      this.$refs.diaChiKhachHangModal.showModal()
    },

    async getNganhnghe() {
      const response = await API.getNganhNghe(this.axios, null)
      this.cboNganhNghe.dataSource = apiHelper.getDataFromResponseApiReturnArray(response) || []
      if (this.cboNganhNghe.dataSource.length > 0) {
        this.cboNganhNghe.value = this.cboNganhNghe.dataSource[0].nganhnghe_id
      }

    },

    async getLinhVuc() {
      const response = await API.getLinhVuc(this.axios, null)
      this.cboLinhVucHD.dataSource = apiHelper.getDataFromResponseApiReturnArray(response) || []
      if (this.cboLinhVucHD.dataSource.length > 0) {
        this.cboLinhVucHD.value = this.cboLinhVucHD.dataSource[0].linhvuc_id
      }
    },
    async getKhuVucHoatDong() {
      const response = await API.getKhuVucHoatDong(this.axios)
      this.dropdownKVHoatDong.options = apiHelper.getDataFromResponseApiReturnArray(response) || []
      if (this.dropdownKVHoatDong.options.length > 0) {
        this.dropdownKVHoatDong.value = this.dropdownKVHoatDong.options[0].khuvuc_hoatdong_id
      }
    },

    async searchCustomerByTaxCode(taxCode) {
      try {
        this.$root.loading(true)
        const response = await API.searchCusByTaxCode(this.axios, taxCode)
        this.$root.loading(false)
        return apiHelper.getDataFromResponseApiReturnArray(response)
      } catch (error) {
        console.log(error)
        this.$root.loading(false)
      }
    },

    async handleCreateNew() {


      if (!this.hanldeValidateImput()) return

      const relevantInfo =  this.$refs['refRelevantInfo'].$refs['gridDanhSach'].dataSource;
      if (!this.validateRelevantInfo(relevantInfo)) return;

      const officerDecideInfo =  this.$refs['refOfficerDecideInfo'].$refs['gridDanhSach'].dataSource;

      if (!this.validateOfficerDecideInfo(officerDecideInfo)) return
      
      try {
        const prams = {
          enterpriseInfo: {
            customerId: this.customerId,
            customerName: this.customerName,
            customerDepartment: this.dropdownKhoiKH.value,
            taxId: this.taxIdentification,
            activity: this.cboLinhVucHD.value,
            career: this.cboNganhNghe.value,
            scale: this.enterpriseSize,
            revenue: this.revenue,
            workingArea: this.dropdownKVHoatDong.value,
            corporateDate: moment(this.corporateDate).format('DD/MM/YYYY'),
            phoneNumber: this.phoneNumber,
            socialNetworkInfo: this.txtWebsite
          },
          addressInfo: {
            cityId: this.dataDiaChiKH.tinh_id,
            cityName: this.dataDiaChiKH.ten_tinh,
            districtId: this.dataDiaChiKH.quan_id,
            districtName: this.dataDiaChiKH.ten_quan,
            wardId: this.dataDiaChiKH.phuong_id,
            wardName: this.dataDiaChiKH.ten_phuong,
            homeNumber: this.dataDiaChiKH.sonha,
            areaId: this.dataDiaChiKH.khu_id,
            areaName: this.dataDiaChiKH.ten_khu,
            streetId: this.dataDiaChiKH.pho_id,
            streetName: this.dataDiaChiKH.ten_pho,
            hamletId: this.dataDiaChiKH.ap_id,
            hamletName: this.dataDiaChiKH.ten_ap,
            characteristic: this.dataDiaChiKH.dacdiem,
            additionalAddress: this.dataDiaChiKH.diachi,
            acreage: '',
            infrastructure: ''
          },
          relevantInfo: relevantInfo,
          officerDecideInfo: officerDecideInfo,
          note: this.txtGhiChu,
          createTime: null, //media fix
        }
        this.$root.loading(true)

        const reponse = await API.createNewCustomer(this.axios, prams)
        this.$root.loading(false)
        const strCode = apiHelper.getSrtCodeError(reponse)
       
        if (strCode != "ok") {
          this.$toast.error(strCode)
          return
        }
        this.$toast.success('Tạo mới khách hàng thành công')
        this.cleanInput();
      } catch (error) {
        this.$root.loading(false)
        console.log(error)
        this.$toast.error('Có lỗi xảy ra')
      }
    },
    async handleUpdate() {

      if (!this.hanldeValidateImput()) return

      const relevantInfo =  this.$refs['refRelevantInfo'].$refs['gridDanhSach'].dataSource;
      if (!this.validateRelevantInfo(relevantInfo)) return;

      const officerDecideInfo =  this.$refs['refOfficerDecideInfo'].$refs['gridDanhSach'].dataSource;
      if (!this.validateOfficerDecideInfo(officerDecideInfo)) return;


      try {
        const param = {
          enterpriseInfo: {
            customerId: this.customerId,
            customerName: this.customerName,
            customerDepartment: this.dropdownKhoiKH.value,
            taxId: this.taxIdentification,
            activity: this.cboLinhVucHD.value,
            career: this.cboNganhNghe.value,
            scale: this.enterpriseSize,
            revenue: this.revenue,
            workingArea: this.dropdownKVHoatDong.value,
            corporateDate: moment(this.corporateDate).format('DD/MM/YYYY'),
            phoneNumber: this.phoneNumber,
            socialNetworkInfo: this.txtWebsite
          },
          addressInfo: {
            cityId: this.dataDiaChiKH.tinh_id,
            cityName: this.dataDiaChiKH.ten_tinh,
            districtId: this.dataDiaChiKH.quan_id,
            districtName: this.dataDiaChiKH.ten_quan,
            wardId: this.dataDiaChiKH.phuong_id,
            wardName: this.dataDiaChiKH.ten_phuong,
            homeNumber: this.dataDiaChiKH.sonha,
            areaId: this.dataDiaChiKH.khu_id,
            areaName: this.dataDiaChiKH.ten_khu,
            streetId: this.dataDiaChiKH.pho_id,
            streetName: this.dataDiaChiKH.ten_pho,
            hamletId: this.dataDiaChiKH.ap_id,
            hamletName: this.dataDiaChiKH.ten_ap,
            characteristic: this.dataDiaChiKH.dacdiem,
            additionalAddress: this.txtAddress,
            acreage: '',
            infrastructure: ''
          },
          relevantInfo: relevantInfo,
          officerDecideInfo: officerDecideInfo,
          note: this.txtGhiChu,
        }
        this.$root.loading(true)
        const response = await API.updateCustomer(this.axios, param)
        this.$root.loading(false)
        const strCode = apiHelper.getSrtCodeError(response)
        if (strCode != "ok") {
          this.$toast.error(strCode)
          return
        }
        this.$toast.success('Cập nhật thông tin khách hàng thành công')
        this.cleanInput()
      } catch (error) {
        console.log(error)
        this.$root.loading(false)
        this.$toast.error('Có lỗi xảy ra')
      }
    },
    cleanInput(){
    
      this.customerId = ''
      this.customerName = ''
      this.taxIdentification = ''
      this.txtAddress = ''
      this.revenue = 0
      this.enterpriseSize = 0
      this.corporateDate = null
      this.phoneNumber = ''
      this.txtWebsite = ''
      this.txtGhiChu = ''
      this.cboLinhVucHD.value = 0
      this.cboNganhNghe.value = 0
      this.dropdownKhoiKH.value = ''
      this.dropdownKVHoatDong.value = ''
      this.createBy = ''
      
      this.dataDiaChiKH = {
        diachi_id: 0,
        tinh_id: 0,
        quan_id: 0,
        phuong_id: 0,
        pho_id: 0,
        ap_id: 0,
        khu_id: 0,
        dacdiem_id: 0,
        sonha: '',
        diachi: ''
      };
      this.relevantInfo = [ {
        relevantName: '',
        department: '',
        dateOfBirth: '',
        gender: '',
        phoneNumber: '',
        email: '',
        action: ''
      }]
      this.officerDecideInfo = [  {
          officerName: "",
          dateOfBirth: "",
          gender: "",
          phoneNumber: "",
          email: "",
          action: ""
      }]
    },

    hanldeValidateImput() {
      if (this.customerId == '') {
        this.$toast.warning('Mã khách hàng không được để trống')
        return false
      }
      if (this.customerName == '') {
        this.$toast.warning('Tên khách hàng không được để trống')
        return false
      }
      if (this.taxIdentification == '') {
        this.$toast.warning('Mã số thuế không được để trống')
        return false
      }
      if (this.txtAddress == '') {
        this.$toast.warning('Địa chỉ không được để trống')
        return false
      }
      if (this.revenue == 0) {
        this.$toast.warning('Doanh thu không được để trống')
        return false
      }
      if (this.enterpriseSize == 0) {
        this.$toast.warning('Quy mô doanh nghiệp không được để trống')
        return false
      }
      if (this.corporateDate == null) {
        this.$toast.warning('Ngày thành lập không được để trống')
        return false
      }
      if (this.phoneNumber == '') {
        this.$toast.warning('Số điện thoại không được để trống')
        return false
      }
      if(!util.validatePhoneNumber(this.phoneNumber)){
        this.$toast.warning('Số điện thoại không đúng định dạng')
        return false
      }
      // if(this.txtWebsite == ''){
      //   this.$toast.warning('Website không được để trống')
      //   return false
      // }
      if (this.cboLinhVucHD.value == 0) {
        this.$toast.warning('Lĩnh vực hoạt động không được để trống')
        return false
      }
      if (this.cboNganhNghe.value == 0) {
        this.$toast.warning('Ngành nghề không được để trống')
        return false
      }
      if (this.dropdownKhoiKH.value == '') {
        this.$toast.warning('Khối khách hàng không được để trống')
        return false
      }
      if (this.dropdownKVHoatDong.value == '') {
        this.$toast.warning('Khu vực hoạt động không được để trống')
        return false
      }

      return true
    },
    validateRelevantInfo(relevantInfo) {
     
      if (relevantInfo.length == 0) {
        this.$toast.warning('Danh sách đầu mối liên hệ không được để trống')
        return false
      }
      for (let i = 0; i < relevantInfo.length; i++) {
        if (relevantInfo[i].relevantName == '') {
          this.$toast.warning('Tên đầu mối liên hệ không được để trống')
          return false
        }
        if (relevantInfo[i].department == '') {
          this.$toast.warning('Phòng ban không được để trống')
          return false
        }
        if (!util.validatePhoneNumber(relevantInfo[i].phoneNumber)) {
          this.$toast.warning('Số điện thoại đầu mối liên hệ không đúng định dạng')
          return false
        }
        if (!util.validateEmail(relevantInfo[i].email)) {
          this.$toast.warning('Email đầu mối liên hệ không đúng định dạng')
          return false
        }
      }
      return true
    },
    validateOfficerDecideInfo(officerDecideInfos) {
      if (officerDecideInfos.length == 0) {
        this.$toast.warning('Danh sách nhân sự quyết định không được để trống')
        return false
      }
      for (let i = 0; i < officerDecideInfos.length; i++) {
        if (officerDecideInfos[i].officerName == '') {
          this.$toast.warning('Tên nhân sự quyết định không được để trống')
          return false
        }
        // if (officerDecideInfos[i].dateOfBirth == '') {
        //   this.$toast.warning('Ngày sinh nhân sự quyết định không được để trống')
        //   return false
        // }
        if (!util.validatePhoneNumber(officerDecideInfos[i].phoneNumber)) {
          this.$toast.warning('Số điện thoại nhân sự quyết định không đúng định dạng')
          return false
        }
        if (!util.validateEmail(officerDecideInfos[i].email)) {
          this.$toast.warning('Email nhân sự quyết định không đúng định dạng')
          return false
        }
      }
      return true
    }
  },

  watch: {
  }
}
</script>
<style lang="scss" scoped>
  .select-custom{
    padding-left: 3px;
  }
</style>
