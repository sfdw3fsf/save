<template src="./SearchDeletedSubscribers.html"></template>
<style src="./SearchDeletedSubscribers.scss"></style>
<script>
import XLSX from 'xlsx'
import moment from 'moment'
import breadcrumb from '@/components/breadcrumb'
import api from './api.js'
import DatePicker from 'vue2-datepicker'
export default {
  components: {
    XLSX,
    moment,
    breadcrumb,
    DatePicker
  },
  name: 'SearchDeletedSubscribers',
  data() {
    return {
      target: '#app', 
      targetTop: '#app', 
      animationSettings: { effect: 'Zoom' },
      position: { X: 'center', Y: 'Center' },
      loading: false,
      dateFormat: 'DD/MM/YYYY',
      dateFormatMonth: 'MM/YYYY',
      typeFormat: 'datetime',
      typeFormatMonth: 'month',
      dateConfig: {
        dateFormat: 'd/m/Y',
        altFormat: 'd/m/Y'
      },
      inputMaTB: "",
      cboxMaTB: false,
      search: false,
      inputTuNgay: {
        checkbox: false,
        val: moment().subtract(10, 'days').format('DD/MM/YYYY'),
        Enabled: true
      },
      showTimePanel: false,
      inputDenNgay: {
        val: moment(new Date()).format('DD/MM/YYYY'),
        Enabled: true
      },
      infoKH: {
        ma: "",
        ten: "",
        diaChi: "",
        ngayLHD: {
          val: "",
          Enabled: true
        },
        ngaySinh: {
          val: "",
          Enabled: true
        }
      },
      infoTT: {
        ma: "",
        maDaiDien: "",
        tenTT: "",
        diaChiTT: "",
        diaChiBC: ""
      },
      infoTB: {
        dichVu: {
          checked: 0,
          list:[]
        },
        loaiHinh: {
          checked: 0,
          list:[]
        },
        Acc: "",
        soAo: "",
        diaChiTB: "",
        diaChiLD: "",
        trangThaiTB: "",
        doiTuong: {
          checked: 0,
          list:[]
        },
      },
      header: {
        title: 'Tra cứu thuê bao đã xóa khỏi danh bạ',
        list: [
          { name: 'Lập hợp đồng', link: { name: 'Ui.cards' } },
          { name: 'Lắp đặt mới', link: { name: 'Ui.cards' } }
        ]
      },
      headerGridbox: [
        { fieldName: 'ma_tb', headerText: 'Số máy/ACC', allowFiltering: true, allowSorting: true },
        { fieldName: 'dich_vu', headerText: 'Dịch vụ', allowFiltering: true, allowSorting: true },
        { fieldName: 'loai_hinh', headerText: 'Loại hình', allowFiltering: true, allowSorting: true },
        { fieldName: 'ma_lt', headerText: 'Số ảo', allowFiltering: true, allowSorting: true },
        { fieldName: 'trangthai_tb', headerText: 'Trạng thái', allowFiltering: true, allowSorting: true },
        { fieldName: 'ngay_cn', headerText: 'Ngày CN', allowFiltering: true, allowSorting: true, textAlign: 'center'}, // KO BT
        { fieldName: 'nguoi_cn', headerText: 'Người CN', allowFiltering: true, allowSorting: true }
      ],
      gridBox: {
        data: [],
        selected: null
      },
    }
  },
  mounted() {
    this.form_load()
  },
  created() {},
  watch: {
    loading: function (val, oldval) {
      this.$root.showLoading(val)
    }
  },
  methods: {
    GetData: function (response) {
      if (response.data.error === 200 || response.data.error === '200') {
        return response.data.data
      } else {
        console.log(response.data.error_code)
      }
      return []
    },
    async btnSearch_Click(){
      this.clear()
      this.loading = true
      let mathubao = ""
      let tungay = ""
      let denngay = ""
      if(this.cboxMaTB){
        mathubao =  this.inputMaTB
        this.search = true
      }else{
        this.inputMaTB = ""
      }
      if(this.inputTuNgay.checkbox){
        this.search = true
        let tu = moment(this.inputTuNgay.val,'DD/MM/YYYY')
        let den = moment(this.inputDenNgay.val,'DD/MM/YYYY')
        let count =  den.diff(tu, 'days')
        if(count > 60){
          this.search = false 
          let c = den.subtract(60, "d").format("DD/MM/YYYY");
          this.$bvModal
            .msgBoxConfirm('Thời gian tìm kiếm quá dài, giới hạn lại từ '+ c +' tới '+ this.inputDenNgay.val +'?', {
              title: 'Giới hạn lại thời gian',
              size: 'sm',
              okTitle: 'Đồng ý',
              cancelTitle: 'Không Đồng ý'
            })
            .then(async (yes) => {
              if (yes) {
                this.inputTuNgay.val = c
                this.btnSearch_Click()
              }
            })
        }
        tungay = this.inputTuNgay.val
        denngay = this.inputDenNgay.val
      }
      if(!this.inputTuNgay.checkbox && mathubao.length == 0){
          this.$toast.error("Hãy giới hạn kết quả tìm kiếm");
          this.search = false
      }
      //call api 
      if(this.search){
        let params ={
          "vma_tb": mathubao,
          "vtungay": tungay,
          "vdenngay": denngay
        }
        try {
          let data = this.GetData(await api.search_ds(this.axios, params))
          if(data && data.length > 0){
              let ds = []
              data.forEach((element, index) => {
                  let dv = this.infoTB.dichVu.list.filter( itemdv => {
                          return itemdv.id === element.dichvuvt_id;
                  })
                  let lh = this.infoTB.loaiHinh.list.filter( itemdv => {
                          return itemdv.id === element.loaitb_id;
                  })
                  if(dv && dv.length > 0){
                      element.dich_vu = dv[0].text
                  }
                  if(lh && lh.length > 0){
                    element.loai_hinh = lh[0].text
                  }
                  element.ngay_cn = moment(new Date(element.ngay_cn)).format('DD/MM/YYYY')
                  ds.push(element)
              })
            this.gridBox.data = ds
          }else{
            this.gridBox.data = []
          }
        } catch (error) {
            this.$toast.error('Lỗi: ' + error.data.message_detail )
        }
      }else{
        this.gridBox.data = []
      }
      this.loading = false
    },
    form_load: async function () {
      this.$refs.SearchDeleteAccountModal.show()
      this.loading = true
      let data = this.GetData(await api.form_load(this.axios, { vchuoi: "DOITUONG" }))
      if(data.doituong && data.doituong.length> 0){
         this.infoTB.doiTuong.list = data.doituong.map((x) => ({
            id: x.doituong_id,
            text: x.ten_dt
          }))
      }
      let datadv = this.GetData(await api.form_load(this.axios, { vchuoi: "DICHVU_VT" }))
      if(datadv.dichvu_vt && datadv.dichvu_vt.length> 0){
         this.infoTB.dichVu.list = datadv.dichvu_vt.map((x) => ({
            id: x.dichvuvt_id,
            text: x.ten_dvvt
          }))
      }
      let datalh = this.GetData(await api.form_load_loaihinhTB(this.axios))
      if(datalh && datalh.length> 0){
         this. infoTB.loaiHinh.list = datalh.map((x) => ({
            id: x.LOAITB_ID,
            text: x.LOAIHINH_TB
          }))
      }
      this.loading = false
    },
    clear(){
          this.infoKH.ma = ""
          this.infoKH.ten = ""
          this.infoKH.diaChi = ""
          this.infoKH.ngayLHD.val = ""
          this.infoKH.ngaySinh.val =""
          this.infoTT.ma = ""
          this.infoTT.maDaiDien = ""
          this.infoTT.tenTT = ""
          this.infoTT.diaChiTT = ""
          this.infoTT.diaChiBC = ""
          this.infoTB.dichVu.checked = 0
          this.infoTB.loaiHinh.checked = 0
          this.infoTB.doiTuong.checked = 0
          this.infoTB.Acc = ""
          this.infoTB.soAo = ""
          this.infoTB.diaChiTB =""
          this.infoTB.diaChiLD = ""
          this.infoTB.trangThaiTB = ""
    },
    selectedItemsChangedGridbox(item){
      if(item[0]){
          this.inputMaTB =  item[0].ma_tb
          this.infoKH.ma = item[0].ma_kh
          this.infoKH.ten = item[0].ten_kh
          this.infoKH.diaChi = item[0].diachi_kh
          if(item[0].ngaylap_hd){
            this.infoKH.ngayLHD.val = moment(new Date(item[0].ngaylap_hd)).format('DD/MM/YYYY')
          }
          if(item[0].ngay_sn){
            this.infoKH.ngaySinh.val = moment(new Date(item[0].ngay_sn)).format('DD/MM/YYYY')
          }
          this.infoTT.ma = item[0].ma_tt
          this.infoTT.maDaiDien = item[0].matb_dd
          this.infoTT.tenTT = item[0].ten_tt
          this.infoTT.diaChiTT = item[0].diachi_tt
          this.infoTT.diaChiBC = item[0].diachi_ct

          this.infoTB.dichVu.checked = item[0].dichvuvt_id
          this.infoTB.loaiHinh.checked = item[0].loaitb_id
          this.infoTB.doiTuong.checked = item[0].doituong_id
          this.infoTB.Acc = item[0].ma_tb
          this.infoTB.soAo = item[0].ma_lt
          this.infoTB.diaChiTB = item[0].diachi_tb
          this.infoTB.diaChiLD = item[0].diachi_ld
          this.infoTB.trangThaiTB = item[0].trangthai_tb
      }
    },
    disabledRange: function (date) {
      return date > new Date()
    },
    toggleTimePanel() {
      this.showTimePanel = !this.showTimePanel
    },
    handleOpenChange() {
      this.showTimePanel = false
    },
  }
}
</script>
