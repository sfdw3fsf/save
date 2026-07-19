<template src="./PayLaterNoTempFee.html"></template>
<style src="./PayLaterNoTempFee.scss"></style>
<script>
import breadcrumb from '@/components/breadcrumb'
import api from './API'
import moment from 'moment'
import KTableCustom from "./components/KTableCustom.vue"
import KRequiredMarker from '@/components/kylq_components/KRequiredMarker.vue'
import KDatePicker from '@/components/kylq_components/KDatePicker.vue'
export default {
  name: 'PayLaterNoTempFee',
  components: {
    breadcrumb,
    KTableCustom,
    KRequiredMarker,
    KDatePicker,
  },
  data() {
    return {
      thongtin_input:{
        ma_tb:'',
        ngay_yeucau: moment(new Date()).format('DD/MM/YYYY'),
      },
      thongtin_tracuu:{
        TEN_TB:'',
        DIACHI_TB:'',
        DIACHI_LD:'',
        TEN_GOI:'',
        THUEBAO_CG:''
      },
      thongtin_khachhang:{
        MA_KH:'',
        SO_DT:'',
        TEN_KH:'',
        DIACHI_KH:''
      },
      thongtin_thanhtoan:{
        MA_TT:'',
        SO_DT:'',
        TEN_TT:'',
        DIACHI_TT:''
      },
      hangtratruocduocchon:[],
      hangtrasauduocchon:[],
      List_TB_tratruoc: [],
      List_TB_trasau:[],


      isDisabled:false,
      isDisabled1:true,
      gridDSthuebaoTraTruoc:[],
      gridDSthuebaoTraSau:[],
      columns_1: {
        headers: [
          {fieldName: 'group', headerText: '', isGroupedColumn: true },
          {fieldName: 'MA_TB', headerText: 'Số máy/Acc', allowfiltering: true},
          {fieldName: 'TEN_TB', headerText: 'Tên TB', allowfiltering: true},
          {fieldName: 'LOAIHINH_TB', headerText: 'Loại TB', allowfiltering: true},
          {fieldName: 'DIACHI_LD', headerText: 'Địa chỉ LĐ', allowfiltering: true},
          {fieldName: 'MA_TT', headerText: 'Mã TT', allowfiltering: true},
          {fieldName: 'TEN_TT', headerText: 'Tên TT', allowfiltering: true,allowSorting: true},
          {fieldName: 'SO_DT', headerText: 'SĐT TT', allowfiltering: true},
          {fieldName: 'TEN_GOI', headerText: 'Gói đa dịch vụ', allowfiltering: true},
          {fieldName: 'TEN_CTKM', headerText: 'Chi tiết trả trước', allowfiltering: true},
          {fieldName: 'HUONG_DC', headerText: 'Số tháng TT', allowfiltering: true},
          {fieldName: 'NGAY_BDDC', headerText: 'Ngày BĐĐC', allowfiltering: true},
          {fieldName: 'NGAY_KTDC', headerText: 'Ngày KTĐC', allowfiltering: true},
        ],
        selectedIndex: 0,
        selectedItem: null,
        checked: []
      },
      columns_2: {
        headers: [
          {fieldName: 'group', headerText: '', isGroupedColumn: true },
          {fieldName: 'MA_TB', headerText: 'Số máy/Acc', allowfiltering: true},
          {fieldName: 'TEN_TT', headerText: 'Tên TB', allowfiltering: true},
          {fieldName: 'LOAIHINH_TB', headerText: 'Loại TB', allowfiltering: true},
          {fieldName: 'DIACHI_LD', headerText: 'Địa chỉ LĐ', allowfiltering: true},
          {fieldName: 'MA_TT', headerText: 'Mã TT', allowfiltering: true},
          {fieldName: 'TEN_TT', headerText: 'Tên TT', allowfiltering: true,allowSorting: true},
          {fieldName: 'SO_DT', headerText: 'SĐT TT', allowfiltering: true},
          {fieldName: 'NGAY_CN', headerText: 'Ngày ĐK chuyển', allowfiltering: true},
          {fieldName: 'NGUOI_CN', headerText: 'Người CN', allowfiltering: true},
          {fieldName: 'MAY_CN', headerText: 'Máy CN', allowfiltering: true},

        ],
        selectedIndex: 0,
        selectedItem: null,
        checked: []
      },
      tt_nd:[],
      ip:'',
      selectedRow:false,
      popupComponentName: '',
      popupComponentHeader: '',
      popupComponent: null,
      popupComponentData: null,
      popupComponentAttr: {},
      popupComponentEvts: {},
      dulieu:'',
    }
  },
  created: async function () {
  },
  mounted :async function() {
    this.loading(true)
    this.tt_nd = await this.$root.token.getThongTinNguoiDung()
    this.ip = await this.$root.token.getIP()
    this.phanvung_id = this.tt_nd.phanvung_id
    console.log('Phân vùng id người dùng:',this.phanvung_id,this.tt_nd, this.ip)
    window.addEventListener('keydown', this.handleKeyDown);
    this.loading(false)
  },
  beforeDestroy() {
    window.removeEventListener('keydown', this.handleKeyDown);
  },
  methods: {
    async nhapmoiClick(){
      this.thongtin_input.ma_tb=''
      this.clearAll()
  },
    onRowCheckChange(event, item) {
      console.log('Row check changed:', event, item);
      if (event.target.checked) {

      } else {

      }
    },
    onRowCheckChange_1(event, item) {
      console.log('Row check changed:', event, item);
      if (event.target.checked) {

      } else {

      }
    },
    checkBoxChange(item){
      console.log('a Khánh qq',item)
      var data = []
      const checkList = item.selectedRowIndexes
      for (let i = 0; i < checkList.length; i++) {
        var index_checkList = checkList[i]
        var data_checkList = this.gridDSthuebaoTraTruoc[index_checkList]
        data.push(data_checkList)
      }
      this.hangtratruocduocchon = data
      this.List_TB_tratruoc = this.hangtratruocduocchon.map(item => item.MA_TB)
    },
    checkBoxChange_1(item){
      console.log('a Khánh qq11',item)
      var data1 = []
      const checkList1 = item.selectedRowIndexes
      for (let i =0; i < checkList1.length; i++){
        var index_checkList1 = checkList1[i]
        var data_checkList1 = this.gridDSthuebaoTraSau[index_checkList1]
        data1.push(data_checkList1)
      }
      this.hangtrasauduocchon = data1
      this.List_TB_trasau = this.hangtrasauduocchon.map(item => item.MA_TB)
    },
    async onSelectedRow(item){
      if(item){
        this.selectedRow = true

      }else{
        this.selectedRow = false
      }

    },
    async onSelectedRow_1(item){
      if(item){
        this.selectedRow = true

      }else{
        this.selectedRow = false
      }

    },
    ShowSearchAccount() {
        this.popupComponent = () => import('@/modules/search/subscriber/SearchAccount')
        this.popupComponentName = 'SearchAccount'
        this.Popup('popupComponents')
        this.popupComponentEvts = {"form-close": this.popupClosed}
      },
    Popup(modalId) {
      this.$bvModal.show(modalId);
    },
   async ClosePopup(modalId) {
      this.$bvModal.hide(modalId);
      this.popupComponent = null;
    },
    async popupClosed(val) {
      this.loading(true);
      try {
        switch (this.popupComponentName) {
          case "SearchAccount": 
            console.log("Tú check", val);
            if (val.ma_tb) this.thongtin_input.ma_tb=val.ma_tb
            if (this.thongtin_input.ma_tb && this.thongtin_input.ma_tb.trim() != '') await this.txtMaTB_KeyPress()
          break;
        }
      } catch (ex) {
        if (ex.data && ex.data.message_detail) {
          this.$toast.error("" + ex.data.message_detail);
        } else {
          this.$toast.error("" + ex);
        }
      } finally {
        this.ClosePopup("popupComponents");
        this.loading(false);
      }
    },
    async txtMaTB_KeyPress(){
      this.clearAll()
      console.log('Thiện check nhấn enter mã TB',this.thongtin_input.ma_tb )
      if (this.thongtin_input.ma_tb.trim()==''){
        this.$toast.error('Nhập số máy/Acc')
        return
      }
      try{
        const Input = {
          vma_tb: this.thongtin_input.ma_tb.trim()
        }
        const response = await api.laydsThueBao_TraTruoc(this.axios, Input);
        if (response && response.data && response.data.data.ERROR_CODE === 1 && response.data.data.TT_TB.length === 0 &&  response.data.data.TT_KHACHHANG.length === 0 &&  response.data.data.TT_KHACHHANG.length === 0){
          this.$toast.error('Thuê bao không tồn tại trong hệ thống')
          return
        }else{
          if (response.data.data.TT_TB.length != 0) {
            // Load thông tin
            this.isDisabled1=false
            this.gridDSthuebaoTraTruoc = response.data.data.TT_TRATRUOC
            this.gridDSthuebaoTraSau = response.data.data.TT_CHUYENDOI
            this.thongtin_tracuu = response.data.data.TT_TB[0]
            this.thongtin_khachhang = response.data.data.TT_KHACHHANG[0]
            this.thongtin_thanhtoan = response.data.data.TT_THANHTOAN[0]

            // Group ds Thuê bao trả trước
            this.gridDSthuebaoTraTruoc.forEach(item => {
              if (item.GOI_ID !== null && item.NHOMTB_ID !== null) {
                item.group = 'Gói đa dịch vụ: ' + item.TEN_GOI + ' - '+ item.HUONG_DC +' tháng';
              }
              else {
                item.group = 'Mã TT: ' + item.MA_TT +' - '+ item.TEN_TT +' - '+ item.SO_DT +' - '+ item.DIACHI_TT;
              }
            });

            // Group ds Thuê bao đã chuyển trả sau
            this.gridDSthuebaoTraSau.forEach(item => {
              if (item.GOI_ID !== null && item.NHOMTB_ID !== null) {
                item.group = 'Gói đa dịch vụ: ' + item.TEN_GOI;
              }
              else {
                item.group = 'Mã TT: ' + item.MA_TT +' - '+ item.TEN_TT +' - '+ item.SO_DT +' - '+ item.DIACHI_TT;
              }
            });
          }else{
            this.thongtin_khachhang = response.data.data.TT_KHACHHANG[0]
            this.thongtin_thanhtoan = response.data.data.TT_THANHTOAN[0]
            this.$toast.error('Thuê bao không tham gia trả trước')
            return
          }
          if ( response.data.data.TT_TRATRUOC.length === 0){
            this.$toast.error('Không có thông tin chuyển trả sau của khách hàng')
          }
        }
      }catch(error){
        this.$toast.error('Xảy ra lỗi khi lấy danh sách thuê bao trả trước')
      }finally{
        this.loading(false);
      }
    },
    async chuyentrasauClick(){
      try{
        const params = this.hangtratruocduocchon.map(item => ({ "THUEBAO_ID": item.THUEBAO_ID }));
        const dataInput = {
          vds_thuebao_id: JSON.stringify(params)
        };
        const response = await api.chuyenTraSau(this.axios, dataInput)
        if (response && response.data && response.data.error_code === 'BSS-00000000') {
          this.$toast.success('Chuyển trả sau thành công')
          await this.loadSauKhiChuyen()

        }else{
          this.$toast.error(response.data.message)
        }
      }catch(error){
        this.$toast.error('Xảy ra lỗi khi chuyển trả sau')
      }finally{
        this.loading(false);
      }

    },
    async chuyentrasauClick_1(index){
      console.log('Các hàng thuê bao trả trước được chọn: ',this.hangtratruocduocchon)
      if (this.hangtratruocduocchon.length<=0){
        this.$toast.error('Bạn phải chọn thuê bao cần chuyển trả sau')
        return
      }
      this.$bvModal
        .msgBoxConfirm("Bạn có chắc chắn muốn chuyển trả sau không tạm thu cho các thuê bao " + this.List_TB_tratruoc +" này không?", {
          title: "Xác nhận",
          size: "sm",
          okTitle: "Có",
          cancelTitle: "Không",
        })
        .then(async (v) => {
          if (v) {
            this.loading(true);
            await this.chuyentrasauClick()
            this.loading(false);
          }
        })
        .catch((e) => {
          console.log(e);
          this.loading(false);
        });
    },
    async xoaChuyentrasauClick(){
      try{
        const params = this.hangtrasauduocchon.map(item => ({
          "THUEBAO_ID": item.THUEBAO_ID,
          "THANG": item.THANG,
          "KIEUTRA_ID": item.KIEUTRA_ID
        }));
        const dataInput = {
          vds_thuebao_id: JSON.stringify(params)
        };
        const response = await api.xoaTraSau(this.axios, dataInput)
        if (response && response.data && response.data.error_code === 'BSS-00000000') {
          this.$toast.success('Xóa chuyển trả sau thành công')
          await this.loadSauKhiChuyen()

        }else{
          this.$toast.error(response.data.message)
        }
      }catch(error){
        this.$toast.error('Xảy ra lỗi khi xóa chuyển trả sau')
      }finally{
        this.loading(false);
      }

    },
    async xoaChuyentrasauClick_1(){
      console.log('Các hàng thuê bao trả trước được chọn: ',this.hangtrasauduocchon)
      if (this.hangtrasauduocchon.length<=0){
        this.$toast.error('Bạn phải chọn thuê bao chuyển trả sau cần xóa')
        return
      }
      this.$bvModal
        .msgBoxConfirm("Bạn có chắc chắn xóa chuyển trả sau không tạm thu cho các thuê bao " + this.List_TB_trasau +" này không?", {
          title: "Xác nhận",
          size: "sm",
          okTitle: "Có",
          cancelTitle: "Không",
        })
        .then(async (v) => {
          if (v) {
            this.loading(true);
            await this.xoaChuyentrasauClick()
            this.loading(false);
          }
        })
        .catch((e) => {
          console.log(e);
          this.loading(false);
        });
    },

    async loadSauKhiChuyen(){
      console.log('Thiện check load',this.thongtin_input.ma_tb )
      this.hangtratruocduocchon= []
      this.hangtrasauduocchon= []
      this.gridDSthuebaoTraTruoc =[]
      this.gridDSthuebaoTraSau=[]
      try{
        const Input = {
          vma_tb: this.thongtin_input.ma_tb.trim()
        }
        const response = await api.laydsThueBao_TraTruoc(this.axios, Input);
        if (response && response.data && response.data.data.ERROR_CODE === 1 && response.data.data.TT_TB.length != 0) {
          // Load thông tin
          this.gridDSthuebaoTraTruoc = response.data.data.TT_TRATRUOC
          this.gridDSthuebaoTraSau = response.data.data.TT_CHUYENDOI
          // Group ds Thuê bao trả trước
          this.gridDSthuebaoTraTruoc.forEach(item => {
            if (item.GOI_ID !== null && item.NHOMTB_ID !== null) {
              item.group = 'Gói đa dịch vụ: ' + item.TEN_GOI + ' - '+ item.HUONG_DC +' tháng';
            }
            else {
              item.group = 'Mã TT: ' + item.MA_TT +' - '+ item.TEN_TT +' - '+ item.SO_DT +' - '+ item.DIACHI_TT;
            }
          });

          // Group ds Thuê bao đã chuyển trả sau
          this.gridDSthuebaoTraSau.forEach(item => {
            if (item.GOI_ID !== null && item.NHOMTB_ID !== null) {
              item.group = 'Gói đa dịch vụ: ' + item.TEN_GOI;
            }
            else {
              item.group = 'Mã TT: ' + item.MA_TT +' - '+ item.TEN_TT +' - '+ item.SO_DT +' - '+ item.DIACHI_TT;
            }
          });
        }

      }catch(error){
        this.$toast.error('Xảy ra lỗi khi lấy danh sách thuê bao')
      }finally{
        this.loading(false);
      }

    },
    clearAll(){
      this.hangtratruocduocchon= []
      this.hangtrasauduocchon= []
      this.thongtin_tracuu = {}
      this.thongtin_thanhtoan = {}
      this.thongtin_khachhang = {}
      this.gridDSthuebaoTraTruoc =[]
      this.gridDSthuebaoTraSau=[]
    },
    handleKeyDown(event) {
      if (event.key === 'F9') {
        this.chuyentrasauClick();
      }
    }
  },
}
</script>
