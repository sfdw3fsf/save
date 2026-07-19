<template src="./SearchInformationChanges.html"></template>
<style src="./SearchInformationChanges.scss"></style>
<script>
import XLSX from 'xlsx'
import moment from 'moment'
import breadcrumb from '@/components/breadcrumb'
import api from './api.js'
import DiaChiKhachHangModal from '@/modules/contract/setup/SurveyRequest/popups/ChonDiaChi/DiaChiKhachHangModal.vue'
import UpdateCodeInformation from '@/modules/contract/setup/UpdateCodeInformation/UpdateCodeInformation.vue'
 
export default {
  components: {
    XLSX,
    moment,
    breadcrumb,
    DiaChiKhachHangModal,
    UpdateCodeInformation
  },
  name: 'SearchInformationChanges',
  data() {
    return {
      loading: false,
      header: {
        title: 'tra cứu danh sách thanh toán',
        list: [
          { name: 'Lập hợp đồng', link: { name: 'Ui.cards' } },
          { name: 'Lắp đặt mới', link: { name: 'Ui.cards' } }
        ]
      },
      dataDiaChiKH: {
          diachi_id:0,
          tinh_id:0,
          quan_id:0,
          phuong_id:0,
          pho_id:0,
          ap_id:0,
          khu_id:0,
          dacdiem_id:0,
          sonha:'',
          diachi:''
      },
      model: {
        khachhang_id: 0,
        fmig_id: 0,
        tucthi: 0,
        kyhoadon: '',
        vma_kh: '',
        ds_MaIG: [],
        ds_GanMIG: []
      },
      dataGanMIG:[],
      dataMig:[],
      input_diachi_kh: '',
      v_kyhoadon: '',
      v_kycuoc: '',
      v_chuky:'',
      v_ck:'',
      v_mm:'',
      v_yyyy:'',
      cb_search1: 0,
      cb_search2: 0,
      listCBXSearch: [],
      v_dieukien_1: 'like',
      v_dieukien_2: 'like',
      listDieukien: [
        { id: 'like', text: 'like' },
        { id: '=', text: '=' },
        { id: '>', text: '>' },
        { id: '<', text: '<' },
        { id: '>=', text: '>=' },
        { id: '<=', text: '<=' } 
      ],
      v_giatri_1: '',
      v_giatri_2: '',
      headerGridbox: [
        { fieldName: 'ma_kh', headerText: 'Mã khách hàng', allowFiltering: true, allowSorting: true },
        { fieldName: 'ma_tt', headerText: 'Mã TT', allowFiltering: true, allowSorting: true },
        { fieldName: 'matb_dd', headerText: 'Số đại diện', allowFiltering: true, allowSorting: true },
        { fieldName: 'ten_tt', headerText: 'Tên TT', allowFiltering: true, allowSorting: true },
        { fieldName: 'diachi_bc', headerText: 'Địa chỉ BC', allowFiltering: true, allowSorting: true },
        { fieldName: 'tienno', headerText: 'Tiền nợ', allowFiltering: true, allowSorting: true }, // KO BT
        { fieldName: 'mst', headerText: 'MST', allowFiltering: true, allowSorting: true },
        { fieldName: 'main_ghep', headerText: 'Mã in ghép', allowFiltering: true, allowSorting: true },
        { fieldName: 'diachi_ct', headerText: 'Địa chỉ CT', allowFiltering: true, allowSorting: true },
        { fieldName: 'ten_loaikh', headerText: 'Loại KH', allowFiltering: true, allowSorting: true },
        { fieldName: 'stk', headerText: 'Số tài khoản', allowFiltering: true, allowSorting: true },
        { fieldName: 'diachi_tt', headerText: 'Địa chỉ VAT', allowFiltering: true, allowSorting: true },
        { fieldName: 'so_gt', headerText: 'CMT', allowFiltering: true, allowSorting: true }
      ],
      gridBox: {
        data: [],
        selected: null
      },
      arr_param : []
    }
  },
  mounted() {
    this.lay_ds_kycuoc_chuky()
    this.lay_ds_dieukien_timkiem()
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
    closeGhepMIG(){
        this.btnSearch_Click()
    },
    selectedItemsChangedGridbox(selected) {
      this.gridBox.selected = selected
    },
    chonDiaChiKH() {
      // địa chỉ khách hàng
      this.$refs.diaChiKhachHangModal.showModal()
    },
    acceptDiaChiKH(data) {
      // handle
      this.input_diachi_kh = data.diachi
      Object.assign(this.dataDiaChiKH, data)
    },
    btnExport_Click: function () {
     // this.$refs.tableDSTBKHGhep.excelExport();
      if (this.gridBox.data.length > 0) {
        let exportData = [];
        let exportHeader = this.headerGridbox.map((item) => item.headerText);
        this.gridBox.data.forEach((row) => {
          const r = {};
          this.headerGridbox.forEach((column) => {
            r[column.headerText] = row[column.fieldName] || "";
          });
          exportData.push(r);
        });

        let data = XLSX.utils.json_to_sheet(exportData, { header: exportHeader });
        let wb = XLSX.utils.book_new();
        XLSX.utils.book_append_sheet(wb, data, "TS");
        // export Excel file
        XLSX.writeFile(wb, "export.xlsx");
      } else {
        this.$toast.error("Không có dữ liệu để xuất");
      }
    },
    close(){
      this.btnSearch_Click()
    },
    btnghepMig_Click: function () {
     if(this.gridBox.selected && this.gridBox.selected.length > 0){
        var data = []
        this.dataMIG= []
        this.dataGanMIG = []
        this.gridBox.selected.forEach((item,index ) => {
                    if(item.main_ghep == "" || item.main_ghep == null){
                      var dataItem = {
                        "chon":false,
                        "ma_tt":item.ma_tt,
                        "hinhthuc_tt" : item.hinhthuc_tt,
                        "ten_tt" : item.ten_tt,
                        "diachi_tt" : item.diachi_tt,
                        "diachi_bc" :item.diachi_bc,
                        "mst" : item.mst,
                        "thanhtoan_id" : item.thanhtoan_id,
                        "ma_kh" : item.ma_kh,
                        "mig_id" : null
                      }
                      this.dataGanMIG.push(dataItem)
                    }else{
                      var itemMIG = {
                        "chon":false,
                        "main_ghep":item.main_ghep,
                        "diachi_ig" : item.diachi_ig,
                        "ma_nv" : item.manv_tc,
                        "mig_id" : item.mig_id,
                        "tuyenthu_id" :item.tuyenthu_id,
                        "manv_tc" : item.manv_tc
                      }
                      data.push(item.ma_tt)
                      this.dataMIG.push(itemMIG)
                    }
                });
        this.model.ds_GanMIG = this.dataGanMIG
        this.model.ds_MaIG = this.dataMIG
        //console.log(this.model)
        if(this.gridBox.selected.length > this.dataMIG.length){
          this.$refs.UpdateCodeInformationModal.showDialog()
        }
        if(data.length > 0){
          this.$bvModal
            .msgBoxOk('Mã thanh toán : '+ data.join(", ") + 'đã được ghép mã in ghép', {
            title: '',
            size: 'sm',
            okTitle: 'OK',
          })
        }
     }else{
        this.$toast.warning('Bạn chưa chọn mã thanh toán trên lưới để ghép mã in ghép')
     }
    },
    btngoMig_Click: async function (){
        if(this.gridBox.selected && this.gridBox.selected.length > 0){
            this.$bvModal
            .msgBoxConfirm('Bạn có muốn gỡ mã in ghép không?', {
              title: 'Thông báo',
              size: 'sm',
              okTitle: 'Đồng ý',
              cancelTitle: 'Không Đồng ý'
            })
            .then(async (yes) => {
              if (yes) {
                 this.gridBox.selected.forEach((item,index ) => {
                    this.go_ma_mig(item.thanhtoan_id)
                });
              }
            })
        }else {
          this.$toast.warning('Bạn chưa chọn khách hàng trên lưới để gỡ mã in ghép!')
      }
    },
    //cap nhat chung tu
    btncapNhat_Click: async function () {
      if (this.input_diachi_kh && this.gridBox.selected && this.gridBox.selected.length > 0) {
        let params = {
          "THANHTOAN_ID": 0,
          "MA_TT": "",
          "KYHOADON": this.v_yyyy+this.v_mm+this.v_ck,
          "DIACHI_CT": this.input_diachi_kh
        }
        this.gridBox.selected.forEach((item,index ) => {
          params.THANHTOAN_ID = item.thanhtoan_id
          params.MA_TT = item.ma_tt
          this.arr_param.push(params)
        });
        this.capnhat_diachi_chungtu(this.arr_param)
      } else {
        if (!this.input_diachi_kh) {
          this.$toast.warning('Bạn chưa nhập địa chỉ chứng từ!')
        }
        if (this.gridBox.selected === null || this.gridBox.selected.length === 0) {
          this.$toast.warning('Bạn chưa chọn khách hàng trên lưới để cập nhật!')
        }
      }
    },
    // UR1.5.017_001
    lay_ds_kycuoc_chuky: async function () {
      this.loading = true
      let ckthangtruoc = moment(new Date()).subtract(1,'M').format('YYYYMM').toString()
      let data = this.GetData(await api.lay_chukyno_theo_kyhd(this.axios, { p_thangnam: ckthangtruoc }))
      this.loading = false
      this.v_ck = data[0].chuky
      this.v_mm = ckthangtruoc.substr(4,2)
      this.v_yyyy = ckthangtruoc.substr(0,4)
      this.v_kyhoadon = this.v_ck+ this.v_mm+ this.v_yyyy
      this.v_kycuoc = this.v_mm +'/'+ this.v_yyyy
      this.v_chuky = this.v_ck
    },
    // UR1.5.017_002
    lay_ds_dieukien_timkiem: async function () {
      this.loading = true
      let data = this.GetData(await api.lay_cb_timkiem(this.axios, { nhom_id: 4 }))
      this.loading = false
      this.listCBXSearch = data.map((x) => ({
        id: x.dieukien,
        text: x.kieu_tk
      }))
      this.cb_search1 = this.cb_search2 = data[0].dieukien
    },
    // UR1.5.017_003
    btnSearch_Click: async function () {
      this.loading = true
      var dk = ''
      var status = true
      if(!this.v_giatri_1 && !this.v_giatri_2){
        status =  false
        this.$toast.warning('Hãy nhập điều kiện tìm kiếm!')
      }
      if(this.v_giatri_1 && this.cb_search1){
        dk =  this.cb_search1.replace(':dieukien', this.v_dieukien_1).replace(':values', this.v_giatri_1)
      }
      if(this.v_giatri_2 && this.cb_search2){
        dk += ' and ' + this.cb_search2.replace(':dieukien', this.v_dieukien_2).replace(':values', this.v_giatri_2)
      }
      var params = {
        kyhoadon: this.v_kyhoadon,
        dieukien: dk
      }
      this.gridBox = { data: [], selected: null }
      if(status){
          try {
            let data = this.GetData(await api.lay_ds_thanhtoan(this.axios, params))
            if (data && data.length > 0) {
                this.gridBox.data = data
              } else {
                this.$toast.info('Không có thông tin thanh toán!')
              }
          } catch (error) {
            this.$toast.error('lỗi: ' + error.data.message_detail)
          }
      }
      this.loading = false
    },
    // UR1.5.017_004
    capnhat_diachi_chungtu: async function (arrparams) {
      var params = {
        data: JSON.stringify(arrparams)
      }
      this.loading = true
      try {
          this.GetData(await api.capnhat_diachi_ct(this.axios, params))
            this.loading = false
          this.$toast.success('Cập nhật địa chỉ chứng từ thành công!') 
      } catch (error) {
          this.loading = false
          this.$toast.error('Cập nhật địa chỉ chứng từ lỗi: ' + error.data.message_detail )
      }
      this.btnSearch_Click()
    },
    
    // UR1.5.017_006
    go_ma_mig: async function (thanhtoanid) {
      var params = {
        vkyhoadon: this.v_kyhoadon,
        vmay_cn: await this.$root.token.getMachine()? await this.$root.token.getMachine():"",
        vnguoi_cn:await this.$root.token.getUserName()? await this.$root.token.getUserName():"",
        vthanhtoan_id: thanhtoanid,
        vtucthi: 0,
        vip_cn: await this.$root.token.getIP()? await this.$root.token.getIP():""
      }
      try{
        this.GetData(await api.capnhat_go_mig(this.axios, params))
        this.$toast.success('Gỡ mã in ghép thành công')
        this.btnSearch_Click()
      }catch (error) {  
        this.$toast.error('Gỡ mã in ghép lỗi' + error.data.message_detail)
      }
    }
  }
}
</script>
