<template src="./TaoDanhSachNhacNo.html"></template>
<script>
import { mapActions, mapGetters } from 'vuex'
import KyCuoc from '@/components/KyCuoc2'
import Calculator from '@/components/Calculator'
import moment from 'moment'
import Vue from "vue"
import { MultiSelectPlugin, CheckBoxSelection } from "@syncfusion/ej2-vue-dropdowns"
import { CheckBoxPlugin } from "@syncfusion/ej2-vue-buttons"
import { DialogUtility } from '@syncfusion/ej2-popups';
import TaoNhacNoAPI from '../api/TaoDanhSachNhacNo'
import CommonAPI from '../api/Commons'
import GachNoAPI from '../api/GachNoAPI'
import { Query } from '@syncfusion/ej2-data'
import { currency } from '@/filters/currency'
import GuiEmail from './components/GuiEmail'

Vue.use(DialogUtility);
let DialogObj = undefined

Vue.use(MultiSelectPlugin)
Vue.use(CheckBoxPlugin)

import {
    DichVuVienThong
  } from "@/const/enums"

export default {
  components : {
    appKyCuoc: KyCuoc,
    appCalculator: Calculator,
    appGuiEmail: GuiEmail
  },
  provide: {
    multiselect: [CheckBoxSelection],
  },
  async created () {

    this.params.p_kycuoc = this.config.default_khd
    
    // init Danh sách Dịch vụ VT
    if (this.danhSachDichVuVienThongGetter === undefined
      || this.danhSachDichVuVienThongGetter.length === 0) {
        await this.getDanhSachDichVuVienThong();
        
    }


    this.params.p_dichvu_options = this.danhSachDichVuVienThongGetter

    if(this.params.p_dichvu_options.length > 0)
      this.params.p_dichvu = DichVuVienThong.CO_DINH

    // init Danh sách đơn vị
    if (this.danhsachDonViGetter === undefined
      || this.danhsachDonViGetter.length === 0) {
        await this.getDanhSachDonVi();
    }

    this.params.p_donvi_options.push({
      DONVI_ID: 0,
      TEN_DV: 'Tất cả'
    })

    this.params.p_donvi_options = [...this.params.p_donvi_options, ...this.danhsachDonViGetter]
    this.params.p_donvi = 0

    // init Danh sách loại KH lớn
    if (this.danhSachKHLonGetter === undefined
      || this.danhSachKHLonGetter.length === 0) {
        await this.getDanhSachKHLon();
    }
    this.danhSachKHLonGetter.forEach(item => {
      this.params.p_loaikhlon_options.push({ id: item.KHLON_ID, text: item.TEN_LKHL });
    });


    // init Danh sách loại KH
    if (this.danhSachLoaiKHGetter === undefined
      || this.danhSachLoaiKHGetter.length === 0) {
        await this.getDanhSachLoaiKhachHang();
    }
    this.params.p_loaikh_options = this.danhSachLoaiKHGetter
    if(this.params.p_loaikh_options.length > 0)
      this.params.p_loaikh = this.params.p_loaikh_options[0].LOAIKH_ID
    /*
    this.danhSachLoaiKHGetter.forEach(item => {
      this.params.p_loaikkh_options.push({ id: item.LOAIKH_ID, text: item.TEN_LOAIKH });
    });
    */
    
  },
  computed : {
    ...mapGetters('qltnCommon', ['danhSachDichVuVienThongGetter', 'danhsachDonViGetter', 'danhSachKHLonGetter', 'danhSachLoaiKHGetter']),
    f_loaikh_enabled () {
      return this.params.p_chk_loaikh === '1'
    },
    f_loaihinh_tb_enabled () {
      return this.params.p_chk_loaihinh_tb === '1'
    },
    f_loaikhlon_enabled () {
      return this.params.p_chk_loaikhlon === '1'
    },
    f_dentien_enabled () {
      return this.params.p_chk_tien_den === '1'
    },
    p_ngayhethan : {
      get() {
        return this.params.p_ngayhethan
      },
      set(value) {
        return this.params.p_ngayhethan = value
      }
    },
    p_ngaytao : {
      get() {
        return this.params.p_ngaytao
      },
      set(value) {
        return this.params.p_ngaytao = value
      }
    },
    label_them_tb_le () {
      return "Thời gian tạo: " + this.params.p_thoigian_tao + " - Số lượng tạo: " + this.params.p_soluong_tao
    }
  },
  data() {
    return {
      selectionSettings: {checkboxOnly: true, type: 'Multiple'},
      contextMenuItems: [
        {text: 'Danh sách lần tạo', target: '.e-content', id: 'refreshGrid', iconCss: 'e-icons e-search'},
        {text: 'Xóa lần tạo', target: '.e-content', id: 'removeItem', iconCss: 'e-icons e-search'},
      ],
      config: {
        dateConfig: {
          value: new Date(),
          format: 'dd/MM/y',
          max: new Date()
        },
        default_khd: moment(new Date()).subtract(1,'months').startOf('month').format('YYYYMMDD'),
        loai_hinh: {
          fields : { text: 'TEN_LOAIHINH', value: 'LOAITB_ID'},
          mode: 'CheckBox',
          showSelectAll: true,
          showDropDownIcon: true,
          filterBarPlaceholder: 'Tìm kiếm nhanh'
        },
        isTienTuCalShow : false,
        isTienDenCalShow : false,
        fieldsDonVi : {text: 'TEN_DV', value: 'DONVI_ID' },
        fieldsLoaiKH : {text: 'TEN_LOAIKH', value: 'LOAIKH_ID' },
        fieldsDichVu: {text: 'TEN_DVVT', value: 'DICHVUVT_ID' },
        fieldsLoaihinhTB: {text: 'TEN_LOAIHINH', value: 'LOAITB_ID' },
      },
      params: {
        p_kycuoc : null,
        
        p_donvi_options: [],
        p_donvi: null,
        p_htnhacno_options: [
          { id: 0, text : "Nợ đầu kỳ"},
          { id: 1, text : "Tổng nợ"},
          { id: 5, text : "Phát sinh"},
        ],
        p_htnhacno: 0,
        p_tien_tu: 0,
        p_chk_tien_den: '0',
        p_tien_den: 0,
        p_ngayhethan: new Date(),
        p_chk_locno: '1',
        p_dichvu_options: [],
        p_dichvu: null,
        p_loaihinh_tb_options: [],
        p_loaihinh_tb: [],
        p_chk_loaihinh_tb: '0',
        p_somay_acc: null,
        p_loaikh_options: [],
        p_loaikh: null,
        p_chk_loaikh: '0',
        p_loaikhlon_options: [],
        p_loaikhlon: [],
        p_chk_loaikhlon: '0',
        p_ten_tb: null,
        p_ngaytao: new Date(),
        p_thoigian_tao: 0,
        p_soluong_tao: 0,
        p_lantao_id: null
      },
      footerSum: function () {
          return  { template : Vue.component('sumTemplate', {
              template: `<span>{{data.Sum}}</span>`,
              data () {return { data: {}};}
              })
            }
        },
      columnsDSTB: [
          {fieldName: 'THUEBAO_ID', visible: false},
          {fieldName: 'MA_TB', headerText: 'Số máy/Acc', isPrimaryKey: true, allowFiltering: true, width: 100},
          {fieldName: 'MA_TT', headerText: 'Mã TT', allowFiltering: true, width: 120},
          {fieldName: 'LOAIHINH_TB', headerText: 'Loại hình', allowFiltering: true},
          {fieldName: 'TEN_TT', headerText: 'Tên thanh toán', allowFiltering: true},
          {fieldName: 'MANV_TC', headerText: 'Mã NVTC', allowFiltering: true, width: 120},
          {fieldName: 'TONGNO', headerText: 'Tổng nợ', allowFiltering: true, format:'N0', type: 'number'}
      ],
      sumColumnsDSTB:[
        {field: 'TONGNO', type: 'Sum', format: 'N0', footerTemplate: this.footerSum}
      ],
      pageinfoDSTB : {
        page: 0,
        maxSize: 10,
        totalElement: 0,
        totalPages: 0,
        sort: null,
        propertiesSort: null,
        currentRow: -1
      },
      columnsDSNhacNo: [
          {fieldName: 'DICHVUVT_ID', visible: false},
          {fieldName: 'LANTAO_NN_ID', visible: false},
          {fieldName: 'THUEBAO_ID', visible: false},
          {fieldName: 'NGAY_TAO', headerText: 'Ngày tạo', allowFiltering: true, textAlign: 'Right'},
          {fieldName: 'SOLUONG', headerText: 'Số lượng', allowFiltering: true, format:'N0', type: 'number', width: 100},
          {fieldName: 'TONGTIEN', headerText: 'Tổng tiền', allowFiltering: true, format:'N0', type: 'number', width: 120},
          {fieldName: 'TU_TIEN', headerText: 'Từ tiền', allowFiltering: true, format:'N0', type: 'number', width: 120},
          {fieldName: 'DEN_TIEN', headerText: 'Đến tiền', allowFiltering: true, format:'N0', type: 'number', width: 120},
          {fieldName: 'DANHAC', headerText: 'Đã nhắc', allowFiltering: true, width: 120},
          {fieldName: 'NGUOI_CN', headerText: 'Người tạo', allowFiltering: true},
          {fieldName: 'DICHVU_VT', headerText: 'Dịch vụ', allowFiltering: true},
      ],
      sumColumnsDSNhacNo:[
        {field: 'TONGTIEN', type: 'Sum', format: 'N0', footerTemplate: this.footerSum}
      ],
      pageinfoDSNhacNo : {
        page: 0,
        maxSize: 10,
        totalElement: 0,
        totalPages: 0,
        sort: null,
        propertiesSort: null,
        currentRow: -1
      },
      resultLanTaoNhacNo:[],
      resultDSTB: [],
      selectedRow: null,
      selectedLanTao: null,
      f_search: false,
      f_refresh: false,
      f_addTB: false,
      keyDSTB: 1,
      keyDSNhacNo: 1
    }
  },
  methods : {
    ...mapActions('qltnCommon', ['getDanhSachDichVuVienThong', 'getDanhSachDonVi', 'getDanhSachKHLon', 'getDanhSachLoaiKhachHang']),
    clearLanTaoResult: function()
    {
      this.resultLanTaoNhacNo = []
      this.params.p_soluong_tao = 0
      this.params.p_thoigian_tao = 0

      this.pageinfoDSNhacNo.page = 0
      this.pageinfoDSNhacNo.totalElement = 0
      this.pageinfoDSNhacNo.totalPages = 0

      this.pageinfoDSNhacNo.currentRow = -1

      //this.clearDSTBResult()
    },
    clearDSTBResult: function(){
      this.resultDSTB = []
      this.pageinfoDSTB.page = 0
      this.pageinfoDSTB.totalElement = 0
      this.pageinfoDSTB.totalPages = 0

      this.pageinfoDSTB.currentRow = -1
    },
    clearResult()
    {
      this.clearLanTaoResult()
      this.clearDSTBResult()
    },

    selectMaTB(index)
    {
      let page =  parseInt(index/this.pageinfoDSTB.maxSize);
      
      //console.log(this.$refs.gridDSTB.$refs.pagination)
      this.$refs.gridDSTB.$refs.pagination.gotoPage(page)
      //this.$refs.gridDSTB.$refs.grid.ej2Instances.goToPage(page)
      //index = this.$refs.gridDSTB.$refs.grid.getRowIndexByPrimaryKey(this.params.p_somay_acc)
      //console.log(page, index)
      let offset = index%this.pageinfoDSTB.maxSize
      //console.log(offset)
      
      this.$refs.gridDSTB.$refs.grid.selectRow(offset)

      this.pageinfoDSTB.currentRow = offset
      this.pageinfoDSTB.page = page
      
    },
    async getThongTinTB(){
      if(this.params.p_somay_acc === null || this.params.p_somay_acc === '' || this.params.p_somay_acc.trim() === '')
      {
        this.$toast.error('Chưa nhập thuê bao.')
        return false
      }

      this.params.p_somay_acc = this.params.p_somay_acc.trim()


      //let index = this.$refs.gridDSTB.$refs.grid.getRowIndexByPrimaryKey(this.params.p_somay_acc)
      //console.log(index)

      //let ds = this.$refs.gridDSTB.$refs.grid.ej2Instances.dataSource

      let ds = this.$refs.gridDSTB.$refs.grid.ej2Instances.currentViewData
      
      //let ds = this.resultDSTB
      let keyword = this.params.p_somay_acc
      
      let index = ds.findIndex( x => x.MA_TB === keyword );

      if(index > -1)
      {
        let info = ds.find(x => x.MA_TB === keyword)
        this.params.p_ten_tb = info.TEN_TT
        this.$refs.gridDSTB.$refs.grid.selectRow(index)
      } else
      {
        this.loading(true)
        const info = await this.loadThongTinTB(this.params.p_somay_acc)
        this.loading(false)

        if(info !== null) // tìm thấy thông tin thuê bao trong lần nhắc
        {
          this.resultDSTB.unshift(info)
          this.$refs.gridDSTB.$refs.grid.selectRow(0)
          this.pageinfoDSTB.currentRow = 0
          this.params.p_ten_tb = info.TEN_TT
        } else
        {
          this.loading(true)
          const response = await this.getDanhBaTheoMaTB(this.params.p_somay_acc)
          if(response.length > 0)
          {
              this.params.p_ten_tb = response[0].TEN_TB
              let vma_tt = response[0].MA_TT
              let vten_tt = response[0].TEN_TT
              let vnhanvientc_id = response[0].NHANVIEN_ID
              let vmanv_tc = -1

              if (vmanv_tc == "-1")
                  vmanv_tc = "";
              let vtrangthaitb_id = response[0].TRANGTHAITB_ID
              let vtrangthai_tb = response[0].TRANGTHAI_TB
              let vthuebao_cha_id = ''//ds.Tables[0].Rows[0]["thuebao_cha_id"].ToString().Trim();
              let vstt = "0";
              if (vthuebao_cha_id != "")
                  vstt = "1";
              let vthuebao_id = response[0].THUEBAO_ID


              const res = await this.getTongNoTheoMaTB(this.params.p_somay_acc)

              if(res.length > 0)
              {
                let tongno = parseInt(res[0].TONGNO)

                if(isNaN(tongno) || tongno <= 0)
                {
                  this.$toast.error("Thuê bao " + this.params.p_somay_acc + " có tổng nợ " + tongno + ". Bạn không được tạo dữ liệu thuê bao này")
                  this.loading(false)
                  return
                } else
                {
                  if(this.resultDSTB.length <= 0)
                  {
                    this.params.p_lantao_id = null
                  }
                  this.loading(false)
                  DialogObj = DialogUtility.confirm({
                      title: 'Thông báo',
                      content: '<div style="padding:20px">Bạn thật sự muốn tạo mới dữ liệu với thuê bao ' +  this.params.p_somay_acc + '</div>',
                      okButton: {  text: 'Đồng ý', click: this.confirmAdd},
                      cancelButton: {  text: 'Hủy bỏ'},
                      showCloseIcon: true,
                      closeOnEscape: true,
                      animationSettings: { effect: 'Zoom' }
                    });
                }

              }
              
          } else
          {
            this.$toast.error('Không tìm thấy thông tin thuê bao')
            this.loading(false)
          }
        }
      }
      
      
/*      
      if(index > -1)  // tìm thấy thuê bao trong dnah sách
      {
        let info = this.resultDSTB.filter(x => x.MA_TB === keyword)
        this.params.p_ten_tb = info[0].TEN_TT
        this.selectMaTB(index)
        this.f_search = true
        //console.log(page, offset)
      } else
      {
        this.loading(true)
        const response = await this.getDanhBaTheoMaTB(this.params.p_somay_acc)
        if(response.length > 0)
        {
            this.params.p_ten_tb = response[0].TEN_TB
            let vma_tt = response[0].MA_TT
            let vten_tt = response[0].TEN_TT
            let vnhanvientc_id = response[0].NHANVIEN_ID
            let vmanv_tc = -1

            if (vmanv_tc == "-1")
                vmanv_tc = "";
            let vtrangthaitb_id = response[0].TRANGTHAITB_ID
            let vtrangthai_tb = response[0].TRANGTHAI_TB
            let vthuebao_cha_id = ''//ds.Tables[0].Rows[0]["thuebao_cha_id"].ToString().Trim();
            let vstt = "0";
            if (vthuebao_cha_id != "")
                vstt = "1";
            let vthuebao_id = response[0].THUEBAO_ID


            const res = await this.getTongNoTheoMaTB(this.params.p_somay_acc)

            if(res.length > 0)
            {
              let tongno = parseInt(res[0].TONGNO)

              if(isNaN(tongno) || tongno <= 0)
              {
                this.$toast.error("Thuê bao " + this.params.p_somay_acc + " có tổng nợ " + tongno + ". Bạn không được tạo dữ liệu thuê bao này")
                this.loading(false)
                return
              } else
              {
                if(this.resultDSTB.length <= 0)
                {
                  this.params.p_lantao_id = null
                }
                this.loading(false)
                DialogObj = DialogUtility.confirm({
                    title: 'Thông báo',
                    content: '<div style="padding:20px">Bạn thật sự muốn tạo mới dữ liệu với thuê bao ' +  this.params.p_somay_acc + '</div>',
                    okButton: {  text: 'Đồng ý', click: this.confirmAdd},
                    cancelButton: {  text: 'Hủy bỏ'},
                    showCloseIcon: true,
                    closeOnEscape: true,
                    animationSettings: { effect: 'Zoom' }
                  });
              }

            }
            
        } else
        {
          this.$toast.error('Không tìm thấy thông tin thuê bao')
          this.loading(false)
        }
      }
*/      

    },

    async confirmAdd()
    {
      DialogObj.hide()

      ////
      this.params.p_lantao_id = null
      ////
      
      let postData = {
        kyCuoc: this.params.p_kycuoc,
        maTB: this.params.p_somay_acc,
        ngayHetHan: moment(this.params.p_ngayhethan).format('DD/MM/YYYY'),
        lanTaoId: this.params.p_lantao_id,
        donViId: this.params.p_donvi,
        kieuNhac: 1
      }
      
      
      if(this.params.p_lantao_id === null)
      {
        this.loading(true)

        try{
          const res = await TaoNhacNoAPI.addTBRiengLe(this.axios, postData)
          const data = await res.data

          
          if(data.error_code === 'BSS-00000000')
          {
            this.$toast.success('Tạo nhắc nợ thành công.')
            await this.doSearch()

            this.params.p_somay_acc = null
            this.params.p_ten_tb = null
            
            this.loading(false)
          }
        }catch(error){
          this.$toast.error(error.data.message_detail)
          this.loading(false)
        }finally{}
      }
      else
      {
        this.loading(true)

        try{
          const res = await TaoNhacNoAPI.updateTBRiengLe(this.axios, postData)
          const data = await res.data

          
          if(data.error_code === 'BSS-00000000')
          {
            this.$toast.success('Tạo nhắc nợ thành công.')
            this.params.p_lantao_id = null
            this.f_addTB = true
            //await this.doSearch()
            this.loadDSLanTaoNhacNo()

            
            //this.f_search = true
            
            this.loading(false)
          }
        }catch(error){
          this.$toast.error(error.data.message_detail)
          this.loading(false)
        }finally{}

      }
    
    },
    async getDanhBaTheoMaTB(paycode) {
      try{
        let postData = {
          maTB : paycode,
          donViDLId : 0
        }
        const results = await CommonAPI.getDanhBaTheoMaThueBao(this.axios, postData);
        const data = await results.data
        return data.error_code === "BSS-00000000" && data.data !== undefined  ? data.data : []
      }catch(err) {
        if(err.data !== undefined && err.data.message_detail)
          this.$toast.error(err.data.message_detail)
        else
          this.$toast.error(err)
        return []
      }finally{
        
      }
    },
    async getTongNoTheoMaTB(paycode) 
    {
      try{
        let postData = {
          pKyHoaDon: this.params.p_kycuoc,
          pMaTT: null,
          pMaTB: this.params.p_somay_acc,
          pKieu: 1
        }
        const results = await CommonAPI.getTongNoKHTheoTB(this.axios, postData);
        const data = await results.data
        return data.error_code === "BSS-00000000" && data.data !== undefined  ? data.data : []
      }catch(err) {
        console.log(err)
        return []
      }
    },
    async onChangeDichVuVT(args)
    {
      //console.log(args, this.params.p_dichvu)
      if(args !== null)
      {
        this.params.p_lantao_id = null
        this.clearLanTaoResult()
        this.clearDSTBResult()

        this.loadDSLoaiHinhTB(this.params.p_dichvu)
        this.loading(true)
        await this.loadDSLanTaoNhacNo()        
        this.loading(false)
      }
      
    },
    loadDSLoaiHinhTB: function(dichvuvt_id){
      try{
          CommonAPI.getDanhSachLoaiHinhThueBao(this.axios, dichvuvt_id).then(
            (response) => {
              //console.log(response)
              if(response.data.error_code === 'BSS-00000000')
              {
                this.params.p_loaihinh_tb_options = response.data.data
                this.params.p_loaihinh_tb = []
//                this.params.p_loaihinh_tb.push(response.data.data[0].LOAITB_ID)
                //this.params.p_loaihinh_tb =
              }
              else
                this.$toast.error(response.data.message)
            }
          )
        } catch (error) {
          this.$toast.error(error)
      }
    },

    async loadDSLanTaoNhacNo()
    {

      if(this.params.p_dichvu === null)
      {
        this.$toast.error('Chưa chọn dịch vụ.')
        return
      }

      let postData = {
        kyCuoc: this.params.p_kycuoc,
        dichVuVTId: this.params.p_dichvu,
        nguoiCN: this.$auth.getUserName()
      }
      //this.clearLanTaoResult()
        
      try{
        const res = await TaoNhacNoAPI.getDSLanTaoNhacNo(this.axios, postData)
        const data = await res.data

        if(data.error_code === 'BSS-00000000')
        {
          this.keyDSNhacNo++
          this.resultLanTaoNhacNo = data.data
          this.pageinfoDSNhacNo.totalElement = data.data.length
        }
      }catch(error){
        this.$toast.error(error.data.message_detail)
      }finally{}
    },
    async loadDSDaNhac(lantao_id){
      let arrLoaiHinhTB = []
      if(this.params.p_chk_loaihinh_tb === '1')
      {
        this.params.p_loaihinh_tb.forEach((item) => {
          arrLoaiHinhTB.push(item)
        })
      }

      let postData = {
        kyCuoc: this.params.p_kycuoc,
        dichVuVTId: this.params.p_dichvu,
        donViLQId: this.params.p_donvi,
        dsLoaiTBId: this.params.p_chk_loaihinh_tb === '1' ? arrLoaiHinhTB.join(',') : null,
        kieuNN: this.params.p_chk_locno,
        lanTaoId: lantao_id,
        pageNum: this.pageinfoDSTB.page,
        pageSize: this.pageinfoDSTB.maxSize
      }
      
      this.loading(true)

      try{
        const res = await TaoNhacNoAPI.getDSTBDaNhac(this.axios, postData)
        const data = await res.data

        if(data.error_code === 'BSS-00000000'
                && data.data !== undefined
                && data.data.length > 0
              )
        {
          this.keyDSTB++
          this.resultDSTB = data.data
          this.pageinfoDSTB.totalElement = this.params.p_soluong_tao//data.data.length
          //console.log(this.pageinfoDSTB)
          /*
          if(this.f_search)
          {
            let keyword = this.params.p_somay_acc
            let index = this.resultDSTB.findIndex( x => x.MA_TB === keyword )
            
            if(index > -1)  // tìm thấy thuê bao trong dnah sách
            {
              this.selectMaTB(index)
            } 
          }
          */
          
        }
      }catch(error){
        if(error.data !== undefined && error.data.message_detail !== undefined)
          this.$toast.error(error.data.message_detail)
        else
          this.$toast.error(error)
        
      }finally{
        this.loading(false)
      }

    },
    async doSearch(){
      /*
      if(!this.f_search)
      {
        this.params.p_lantao_id = -1
        this.clearResult()
      }        
      else
      {
        this.resultLanTaoNhacNo = []
        this.resultDSTB = []
      }
      */

      this.resultLanTaoNhacNo = []
      this.resultDSTB = []
      this.pageinfoDSTB.page = 0
      this.pageinfoDSTB.currentRow = -1
      this.pageinfoDSNhacNo.page = 0
      this.pageinfoDSNhacNo.currentRow = -1
      this.params.p_lantao_id = null
        
      this.loading(true)
      await this.loadDSLanTaoNhacNo()
      this.loading(false)

    },
    validateCreateData: function(){
      let errors = []

      if(this.params.p_tien_tu === null)
      {
        errors.push('Từ tiền không được để trống.')
      }
      if(this.params.p_chk_tien_den === '1'
        && this.params.p_tien_den === null)
      {
        errors.push('Đến tiền không được để trống.')
      }

      if(this.params.p_chk_tien_den === '0')
        this.params.p_tien_den = 0

      if(errors.length > 0) {
        this.$toast.error(erros.join('\r\n'))
        return false
      }
      return true
    },
    createData: function(){
      if(!this.validateCreateData())
        return false
      let message = ''

      if(this.params.p_chk_tien_den === '1')
        message = "<div style='padding: 20px'>Bạn thật sự muốn tạo dữ liệu nhắc nợ với số tiền nợ >= " + (this.params.p_tien_tu | currency) + " và <= " + this.params.p_tien_den  + " ?</div>"
      else
        message = "<div style='padding: 20px'>Bạn thật sự muốn tạo dữ liệu nhắc nợ với số tiền nợ >= " + (this.params.p_tien_tu | currency)  + " ? </div>"

       DialogObj = DialogUtility.confirm({
        title: 'Thông báo',
        content: message,
        okButton: {  text: 'Đồng ý', click: this.confirmCreate},
        cancelButton: {  text: 'Hủy bỏ'},
        showCloseIcon: true,
        closeOnEscape: true,
        animationSettings: { effect: 'Zoom' }
      });



    },
    confirmCreate(){
      let postData = {
        kyCuoc: this.params.p_kycuoc,
        loaiKHId: this.params.p_chk_loaikh === '1' ? this.params.p_loaikh : null,
        kieuNhac: this.params.p_htnhacno,
        donViId: this.params.p_donvi,
        tuTien: this.params.p_tien_tu,
        denTien: this.params.p_tien_den,
        dsKHLonId: this.params.p_chk_loaikhlon === '1' ? this.params.p_loaikhlon : null,
        dichVuVTId: this.params.p_dichvu,
        dsLoaiTBId: this.params.p_chk_loaihinh_tb === '1' ? this.params.p_loaihinh_tb : null,
        lanTaoId: 1000,
        ngayHetHan: moment(this.params.p_ngayhethan).format('DD/MM/YYYY'),
        locNhac: this.params.p_chk_locno
      }
      this.loading(true)
      TaoNhacNoAPI.addDSNhacNo(this.axios, postData)
      .then(
        (res) => {
          if(res.data.error_code === 'BSS-00000000')
          {
            this.$toast.success('Tạo dữ liệu nhắc nợ thành công.')
            this.doSearch()
          } else if(res.data.error_code === 'BSS-00009491')
          {
            this.$toast.error(res.data.message_detail)
          }
        }
      )
      .catch(error => {
        this.$toast.error(error.data.message_detail)
      })
      .finally(() => {
        DialogObj.hide()
        this.loading(false);
      })
    },
    doForward(){
      let selected = this.$refs.gridDSNhacNo.getSelectedRecords()

      if(selected.length <= 0)
      {
        this.$toast.error('Chưa có dữ liệu lần tạo để chuyển')
        return
      }

      DialogObj = DialogUtility.confirm({
        title: 'Thông báo',
        content: '<div  style="padding:20px">Bạn có chắc chắn muốn chuyển danh sách sang nhắc nợ ?</div>',
        okButton: {  text: 'Đồng ý', click: this.confirmForward},
        cancelButton: {  text: 'Hủy bỏ'},
        showCloseIcon: true,
        closeOnEscape: true,
        animationSettings: { effect: 'Zoom' }
      });

    },

    confirmForward(){
      let postData = {
        kyCuoc: this.selectedLanTao.KYHOADON,
        lanTaoId: this.selectedLanTao.LANTAO_NN_ID,
        kieu: 1,  // 1: chuyển tất cả , 0: chuyển phiếu có flag = 0
        mayCN: '-'
        //giaoPhieuNN: 0
      }

      this.loading(true)
      TaoNhacNoAPI.updateChuyenDSNhacNo(this.axios, postData)
      .then(
        (res) => {
          if(res.data.error_code === 'BSS-00000000')
          {
            this.$toast.success('Chuyển dữ liệu nhắc nợ thành công.')
            this.f_refresh = true
            this.params.p_lantao_id = null
            this.pageinfoDSTB.currentRow = -1
            //this.doSearch()
            this.loading(true)
            this.loadDSLanTaoNhacNo()
            this.loading(false)
          }
        }
      )
      .catch(error => {
        //console.log(error)
        this.$toast.error(error.data.message_detail)
      })
      .finally(() => {
        DialogObj.hide()
        this.loading(false);
      })


    },
    async exportExcel(){
      
      let postData = {
        kyCuoc: this.params.p_kycuoc,
        dichVuVTId: this.params.p_dichvu,
        donViLQId: this.params.p_donvi,
        dsLoaiTBId: this.params.p_chk_loaihinh_tb === '1' ? arrLoaiHinhTB.join(',') : null,
        kieuNN: this.params.p_chk_locno,
        lanTaoId: this.params.p_lantao_id,
        pageNum: 0,
        pageSize: this.params.p_soluong_tao
      }
      
      this.loading(true)

      try{
        const res = await TaoNhacNoAPI.getDSTBDaNhac(this.axios, postData)
        const data = await res.data

        if(data.error_code === 'BSS-00000000'
                && data.data !== undefined
                && data.data.length > 0
              )
        {
          let excelExportProperties = {
              dataSource: data.data
          };
          this.$refs.gridDSTB.excelExport(excelExportProperties)
          this.loading(false)
        } else
        {
          this.$toast.error('Không có số liệu để xuất file.')
        }
      }catch(error){
        this.$toast.error('Lỗi khi xuất dữ liệu')
        this.loading(false)
      }

      
    },

    sendEmail(){
      this.$refs.refGuiEmail.openDialog()
    },
    /* Event Handler */
    chukycuocChangeHandler(e) {
      if(e.p_kyhoadon !== null)
      {
        this.params.p_kycuoc = moment(e.p_kyhoadon).format('YYYYMM') + '01'
        if(this.params.p_dichvu !== null)
        {
          this.loading(true)
          this.loadDSLanTaoNhacNo()
          this.loading(false)
        }        
      }
      else
        this.params.p_kycuoc = null
    },

    doDelete()
    {
      let selected = this.$refs.gridDSTB.getSelectedRecords()

      if(selected.length <= 0)
      {
        this.$toast.error('Chưa chọn thuê bao cần xóa.')
        return false
      }
      DialogObj = DialogUtility.confirm({
        title: 'Thông báo',
        content: '<div style="padding:20px">Bạn có chắc chắn muốn xóa thuê bao khỏi danh sách nhắc nợ ?</div>',
        okButton: {  text: 'Đồng ý', click: this.confirmDelete},
        cancelButton: {  text: 'Hủy bỏ'},
        showCloseIcon: true,
        closeOnEscape: true,
        animationSettings: { effect: 'Zoom' }
      });
    },

    confirmDelete(){
      if(this.selectedLanTao === null)
      {
        this.$toast.error('Bạn chưa chọn lần tạo')
        return false
      }

      let selected = this.$refs.gridDSTB.getSelectedRecords()
      let ds = []
      selected.forEach((item) => {
        ds.push(item.MA_TB)
      })
      let postData = {
        lanTaoId: this.selectedLanTao.LANTAO_NN_ID,
        dsMaTB: ds
      }
      this.loading(true)
      TaoNhacNoAPI.xoaTBNhacNo(this.axios, postData)
      .then(
        (res) => {
          if(res.data.error_code === 'BSS-00000000')
          {
            this.$toast.success('Xóa thuê bao nhắc nợ thành công.')
            this.params.p_lantao_id = null
            this.pageinfoDSTB.currentRow = -1
            //this.doSearch()
            this.loading(true)
            this.loadDSLanTaoNhacNo()
            this.loading(false)
          } else if(res.data.error_code === 'BSS-00009491')
          {
            this.$toast.error(res.data.message_detail)
          }
        }
      )
      .catch(error => {
        //console.log(error)
        this.$toast.error(error.data.message_detail)
      })
      .finally(() => {
        DialogObj.hide()
        this.loading(false);
      })
    },
    rowActiveHandler(data) {
    },
    pageChangeDSTBHandler(e)
    {
      this.pageinfoDSTB.page = e.pageIndex
      this.pageinfoDSTB.maxSize = e.pageSize


      console.log(this.pageinfoDSTB)

      this.loadDSDaNhac(this.params.p_lantao_id)
    },

    pageChangeDSNNHandler(e)
    {
      this.pageinfoDSNhacNo.page = e.pageIndex
      this.pageinfoDSNhacNo.maxSize = e.pageSize
    },
    selectedRowDSTBChanged(e)
    {
      this.selectedRow = e
      
    },
    dsNhacNoRecordClick(e)
    {
      
    },
    dsNhacNoActionComplete(args)
    {
      /*
      if(args.requestType === 'refresh')
      {
        if(!this.f_refresh)
          this.pageinfoDSNhacNo.currentRow = 0

        this.$refs.gridDSNhacNo.$refs.grid.selectRow(this.pageinfoDSNhacNo.currentRow)
        this.f_refresh = false
      }
      */
    },
    dsNhacNoDataBound(args)
    {
      if(this.resultLanTaoNhacNo.length > 0)
      {
        this.$refs.gridDSNhacNo.$refs.pagination.gotoPage(this.pageinfoDSNhacNo.page)
        
        if(this.pageinfoDSNhacNo.currentRow === -1)
          this.pageinfoDSNhacNo.currentRow = 0
        this.$refs.gridDSNhacNo.$refs.grid.selectRow(this.pageinfoDSNhacNo.currentRow)
      }

      /*
      if(this.f_refresh)
      {
        if(this.resultLanTaoNhacNo.length > 0)
        {
          this.$refs.gridDSNhacNo.$refs.pagination.gotoPage(this.pageinfoDSNhacNo.page)
          this.$refs.gridDSNhacNo.$refs.grid.selectRow(this.pageinfoDSNhacNo.currentRow)
        }
          
      }
      if(this.f_search)
      {
        if(this.resultLanTaoNhacNo.length > 0)
        {
          this.$refs.gridDSNhacNo.$refs.pagination.gotoPage(this.pageinfoDSNhacNo.page)
          this.$refs.gridDSNhacNo.$refs.grid.selectRow(this.pageinfoDSNhacNo.currentRow)
        }
          
      }
      
      if(this.resultLanTaoNhacNo.length > 0)
      {
        this.$refs.gridDSNhacNo.$refs.pagination.gotoPage(this.pageinfoDSNhacNo.page)
        if(this.pageinfoDSNhacNo.currentRow > -1)
          this.$refs.gridDSNhacNo.$refs.grid.selectRow(this.pageinfoDSNhacNo.currentRow)
        else
          this.$refs.gridDSNhacNo.$refs.grid.selectRow(0)
      }
        //this.$refs.gridDSNhacNo.$refs.grid.selectRow(this.pageinfoDSNhacNo.currentRow)

      //this.f_refresh = false
      /*
      if(this.f_addTB)
      {
        let ds = this.resultDSTB
        let keyword = this.params.p_somay_acc
        let index = ds.findIndex( x => x.MA_TB === keyword)
        console.log(ds)
        console.log(index)
        if(index >= 0)
          this.selectMaTB(index)
        this.f_addTB = false
      }
      */
    },
    dsThueBaoDataBound()
    {
      if(this.resultDSTB.length > 0)
      {
        if(this.pageinfoDSTB.currentRow >= 0)
          this.$refs.gridDSTB.$refs.grid.selectRow(this.pageinfoDSTB.currentRow)
        if(this.f_addTB)
        {
          this.getThongTinTB()
          this.f_addTB = false
        }          
      }
      /*
      if(this.f_refresh)
      {
        if(this.resultDSTB.length > 0)
        {
          this.$refs.gridDSTB.$refs.pagination.gotoPage(this.pageinfoDSTB.page)
          if(this.pageinfoDSTB.currentRow >= 0)
            this.$refs.gridDSTB.$refs.grid.selectRow(this.pageinfoDSTB.currentRow)
        }
          
      }
      */

      /*

      if(this.f_addTB && this.resultDSTB.length > 0)
      {
        let keyword = this.params.p_somay_acc
        let index = this.resultDSTB.findIndex( x => x.MA_TB === keyword )

        
        if(index > -1)  // tìm thấy thuê bao trong dnah sách
        {
          this.selectMaTB(index)
          this.f_search = true
          //console.log(page, offset)
        } 
        this.f_addTB = false
        //this.pageinfoDSTB.currentRow = -1
      }

      */
    },
    selectedRowDSNhacNoChanged(e)
    {
      //console.log(e)
      if(e === null)
      {
        this.params.p_soluong_tao = 0
        this.resultDSTB = []
        this.pageinfoDSTB.page = 0
        this.pageinfoDSTB.totalElement = 0
        this.params.p_lantao_id = null
        return
      }

      if(e.LANTAO_NN_ID === this.params.p_lantao_id)
        return

      this.params.p_soluong_tao = e.SOLUONG
      this.pageinfoDSTB.page = 0
      this.pageinfoDSTB.totalElement = 0
      this.pageinfoDSTB.currentRow = -1

      this.loadDSDaNhac(e.LANTAO_NN_ID)

      this.selectedLanTao = e
      this.params.p_lantao_id = e.LANTAO_NN_ID
      let index = this.resultLanTaoNhacNo.findIndex( x => x.LANTAO_NN_ID === e.LANTAO_NN_ID);
      this.pageinfoDSNhacNo.currentRow = index
      
    },
    pageChangeDSNhacNoHandler(e)
    {
      this.pageinfo.page = e.pageIndex
      this.pageinfo.maxSize = e.pageSize
      //this.onReloadDSTreoNo()
    },
    onFilteringLoaiKH(e){
      var query = new Query();
      query = (e.text !== '') ? query.where('TEN_LOAIKH', 'contains', e.text, true) : query;
      e.updateData(this.params.p_loaikh_options, query);
    },
    contextMenuClick(args)
    {
      if(args.item.id === 'refreshGrid') {
        this.doSearch()
      } else if(args.item.id === 'removeItem')
      {
        if(args.rowInfo !== undefined && args.rowInfo.rowData !== undefined)
        {
          this.params.p_lantao_id = args.rowInfo.rowData.LANTAO_NN_ID
          let sMsg = 'Bạn thật sự muốn Xóa dữ liệu lần tạo ' + args.rowInfo.rowData.NGAY_TAO + " ?"
          DialogObj = DialogUtility.confirm({
            title: 'Thông báo',
            content: '<div style="padding:20px">' +  sMsg + '</div>',
            okButton: {  text: 'Đồng ý', click: this.confirmDeleteLanTao},
            cancelButton: {  text: 'Hủy bỏ'},
            showCloseIcon: true,
            closeOnEscape: true,
            animationSettings: { effect: 'Zoom' }
          });
        }
      }
    },

    confirmDeleteLanTao()
    {
      DialogObj.hide()
       let postData = {
         kyCuoc: this.params.p_kycuoc,
         lanTaoNNId: this.params.p_lantao_id
       }
      
      this.loading(true)
      TaoNhacNoAPI.xoaLanNhacNo(this.axios, this.params.p_kycuoc, this.params.p_lantao_id)
      .then(
        (res) => {
          if(res.data.error_code === 'BSS-00000000')
          {
            this.$toast.success('Xóa lần nhắc nợ thành công.')
            this.loading(false)
            this.doSearch()
          } else if(res.data.error_code === 'BSS-00009491')
          {
            this.$toast.error(res.data.message_detail)
          }
        }
      )
      .catch(error => {
        //console.log(error)
        this.$toast.error(error.data.message_detail)
        this.loading(false);
      })
      .finally(() => {
        
      })


    },

    async loadThongTinTB(ma_tb)
    {
      let postData = {
        kyCuoc: this.params.p_kycuoc,
        lanNNId : this.params.p_lantao_id,
        maTB: ma_tb
      }

      try{
        const res = await TaoNhacNoAPI.getThongTinTB(this.axios, postData)
        const data = await res.data

        if(data.error_code === 'BSS-00000000' && data.data !== undefined && data.data.length > 0)
          return data.data[0]
        else
          return null
      }catch(error){
        return null
      }finally{} 

      
    },

    actionComplete: function(args) {
      //console.log(args)
      if(args.requestType == 'refresh')
      {
        //console.log('actionComplete')

        if(this.f_search)
        {
          if(this.resultDSTB.length > 0)
          {
            //console.log(this.pageinfoDSTB.page, this.pageinfoDSTB.currentRow)
            //this.$refs.gridDSTB.$refs.pagination.gotoPage(this.pageinfoDSTB.page)
            //this.$refs.gridDSTB.$refs.grid.selectRow(this.pageinfoDSTB.currentRow)
            //this.pageinfoDSTB.currentRow = -1
            //this.f_search = false
            /*
            if(this.f_addTB)
            {
              this.f_addTB = false
              this.pageinfoDSTB.currentRow = -1
            }
            */
            
          }
            //this.$refs.gridDSTB.$refs.pagination.gotoPage(this.pageinfoDSTB.page)
        }
        /*
        if(this.f_addTB)
        {
          let keyword = this.params.p_somay_acc
          let index = this.resultDSTB.findIndex( x => x.MA_TB === keyword )
          
          if(index > -1)  // tìm thấy thuê bao trong dnah sách
          {
            console.log('databound', index)
            this.selectMaTB(index)
            this.f_search = true
            //console.log(page, offset)
          } 
          this.f_addTB = false
        }
        */

        /*
        if(!this.f_search)
        {
          this.pageinfoDSTB.currentRow = 0
        }

        this.$refs.gridDSTB.$refs.grid.selectRow(this.pageinfoDSTB.currentRow)     
        this.f_search = false
        */
        
      }
    },
    DeSelectedRowDSNNHandler(args)
    {
      /*
      this.params.p_soluong_tao = 0
      this.resultDSTB = []
      this.pageinfoDSTB.page = 0
      this.pageinfoDSTB.totalElement = 0
      this.pageinfoDSNhacNo.currentRow = -1

      this.params.p_lantao_id = null
      this.selectedLanTao = null
      */
    }
  }
}

</script>
