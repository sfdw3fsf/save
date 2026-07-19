<template src="./DanhSachPhieuDuyet.html"></template>
<style scoped src="./DanhSachPhieuDuyet.scss"></style>
<script>
import moment from 'moment'
import DatePicker from 'vue2-datepicker'
import 'vue2-datepicker/locale/vi.js'
import 'vue2-datepicker/index.css'
import ActionTop from '@/components/kylq_components/ActionTop.vue'
import api from "@/modules/contract/setup/ChangeCustomerInfor/API.js";
import SuppliesAPI from '@/modules/admin/category/Supplies/SuppliesAPI.js'
import VueExcelXlsx from "vue-excel-xlsx"
import Vue from "vue"
Vue.use(VueExcelXlsx)
export default {
  name:'DanhSachPhieuDuyet',
  props: ['dulieu'],
  components:{
    ActionTop,
    DatePicker
  },
  data(){
    return {
      formWidth: 0,
      dateFormat: 'DD/MM/YYYY hh:mm:ss',
      nhanvien_id: 0,
      popupComponentName: '',
      popupComponentHeader: '',
      popupComponent: null,
      popupComponentData: null,
      popupComponentAttr: {},
      popupComponentEvts: {},
      // TT tim kiem
      txtMaKH: {text: 'Mã KH', value: '', Enabled: true},
      txtMaGD: {text: 'Mã GD', value: '', Enabled: true},
      dtpNgayBD: {text: 'Từ ngày', value: '', Enabled: true},
      dtpNgayKT: {text: 'Đến ngày', value: '', Enabled: true},
      // TT thay doi
      txtNVYC: {text: 'NV yêu cầu', value: '', Enabled: true},
      txtSDTNV: {text: 'SDT NVYC', value: '', Enabled: true},
      txtDVYC: {text: 'Đơn vị YC', value: '', Enabled: true},
      dtpNgayYC: {text: 'TG yêu cầu', value: '', Enabled: true},
      txtNVDuyet: {text: 'NV duyệt', value: '', Enabled: true},
      txtDVDuyet: {text: 'Đơn vị duyệt', value: '', Enabled: true},
      txtKetQuaDuyet: { text: 'Kết quả duyệt', value: '', Enabled: true},
      dtpNgayDuyet: {text: 'TG duyệt', value: '', Enabled: true},
      txtLyDoDuyet: {text: 'Lý do duyệt', value: '', Enabled: true},
      cbb_AnhGT: {text: 'Ảnh giấy tờ', ds_hinhanh: [], Enabled: true},
      DS_Phieu: [],
      json_fields:[{
        label: 'Mã giao dịch',
        field: 'ma_gd'
      },
      {
        label: 'Mã thuê bao',
        field: 'ma_tb'
      },
      {
        label: 'Tên KH mới',
        field: 'ten_kh'
      },
      {
        label: 'Tên KH cũ',
        field: 'ten_kh_cu'
      },
      {
        label: 'Số giấy tờ mới',
        field: 'so_gt'
      },
      {
        label: 'Số giấy tờ cũ',
        field: 'so_gt_cu'
      },
      {
        label: 'Số điện thoại mới',
        field: 'so_dt'
      },
      {
        label: 'Số điện thoại cũ',
        field: 'so_dt_cu'
      },
      {
        label: 'Thời gian giao phiếu',
        field: 'ngay_yc'
      },
      {
        label: 'Người giao phiếu',
        field: 'nhanvien_yc'
      },
      {
        label: 'Nhân viên duyệt',
        field: 'ten_nv'
      },
      {
        label: 'Đơn vị giao phiếu',
        field: 'donvi_yc'
      },
      {
        label: 'Thời gian duyệt',
        field: 'tg_duyet'
      },
      {
        label: 'Kết quả duyệt',
        field: 'kq_duyet'
      },
      {
        label: 'Lý do duyệt',
        field: 'lydo_duyet'
      },
      {
        label: "Đơn vị duyệt",
        field: "ten_dv",
      }
      ],
      json_data: [],
  }},
  mounted() {
    this.formWidth = Number(window.innerWidth)
    this.nhanvien_id = this.$root.token.getNhanVienID()
    if (this.dulieu) {
      if (this.dulieu.ma_gd) this.txtMaGD.value = this.dulieu.ma_gd.trim()
      if (this.dulieu.ma_kh) this.txtMaKH.value = this.dulieu.ma_kh.trim()
      this.LayDS_Phieu()
    }
  },
  watch: {
    'dulieu.ma_gd'(val) {
      if (val) this.txtMaGD.value = val.trim()
    },
    'dulieu.ma_kh'(val) {
      if (val) this.txtMaKH.value = val.trim()
    },
  },
  methods:{
    showModal() {
    },
    hideModal() {
      this.$emit("form-close", {})
    },
    async handleShowModal(){        
    },
    openImage(item){
      window.open(item.url, '_blank');
    },
    ShowSearchAccount() {
      this.popupComponent = () => import('@/modules/search/subscriber/SearchAccount')
      this.popupComponentName = 'SearchAccount'
      this.Popup('popupComponents1')
      this.popupComponentEvts = {"form-close": this.popupClosed}
    },
    btnSearch_click: async function () {
      await this.LayDS_Phieu()
    },
    async LayDS_Phieu(){
      try {
        this.loading(true)
        var input = {
          p_nhanvien_id: this.nhanvien_id,
          p_ma_gd: this.txtMaGD.value ? this.txtMaGD.value : -1,
          p_ma_kh: this.txtMaKH.value ? this.txtMaKH.value : -1,
          p_tungay: this.dtpNgayBD.value ? this.dtpNgayBD.value.split(" ")[0] : -1,
          p_denngay: this.dtpNgayKT.value ? this.dtpNgayKT.value.split(" ")[0] : -1
        }
        let data = this.GetDataList(await api.fn_lay_ds_phieu_pheduyet_theo_nv(this.axios, input));
        this.DS_Phieu = data
        console.log("tu kiểm tra ", data)
        if (data.length>0){
          data.forEach(item => {
            if(item.tg_duyet!=null){
              item.parsedDate = new Date(item.tg_duyet.replace(/(\d{2})\/(\d{2})\/(\d{4}) (\d{2}):(\d{2}):(\d{2})/, '$3-$2-$1T$4:$5:$6'))
            }
          })
          data.sort((a, b) => b.parsedDate - a.parsedDate)
          this.json_data=data
        }
        else this.$toast.error('Lỗi LayDS_Phieu  không có data để kết xuất' )
      } catch (e) {
        this.$toast.error('Lỗi LayDS_Phieu ' + JSON.stringify(e))
      } finally {        
        this.loading(false)
      }
    },
    lay_thong_tin_file_upload: async function (p_tdtt_hdtb_id) {
      try {
        let response = await api.sp_lay_thong_tin_file_upload(this.axios, p_tdtt_hdtb_id);
        if (
          response &&
          response.data &&
          response.data.error_code == "BSS-00000000"
        ) {
          var data = response.data.data
          if (data instanceof Array) return data
          if (data instanceof Object) return [data]
        } else if (response.data.error_code == "BSS-00000204") {
          // this.ShowError("Không tìm thấy thông tin file upload");
          return []
        } else {
          this.ShowError("Lỗi lấy thông tin file upload: " + response.data.message_detail);
          return [];
        }
      } catch (ex) {
        console.log(ex);
        if (ex.data && ex.data.message_detail) {
          this.ShowError("Lỗi lấy thông tin file upload: " + ex.data.message_detail);
        } else {
          this.ShowError("Lỗi lấy thông tin file upload: " + JSON.stringify(ex));
        }
        
      }
    },
    grdPhieuYC_SelectionChanged: async function (item) {
      if (item && item.length > 0) {
        this.txtMaKH.value = item[0]['ma_kh']
        this.txtMaGD.value = item[0]['ma_gd']
        this.txtNVYC.value = item[0]['nhanvien_yc']
        this.txtSDTNV.value = item[0]['so_dt_yc']
        this.txtDVYC.value = item[0]['donvi_yc']
        this.dtpNgayYC.value = item[0]['ngay_yc']
        this.txtNVDuyet.value = item[0]['ten_nv']
        this.txtDVDuyet.value = item[0]['ten_dv']
        this.txtKetQuaDuyet.value = item[0]['kq_duyet']
        this.dtpNgayDuyet.value = item[0]['tg_duyet']
        this.txtLyDoDuyet.value = item[0]['lydo_duyet']

        if (item[0]['tdtt_hdtb_id']) {
          var ds_hinhanh = await this.lay_thong_tin_file_upload(item[0]['tdtt_hdtb_id'])
          if(ds_hinhanh.length > 0){
            var list_file = ''
            for (let [index, item] of ds_hinhanh.entries()) {
              list_file += (index > 0 ? ',' : '') + item.url
            }
            var ds_hinhhanh_temp = []
            let resultImageMoi = await this.link_view_file(list_file)
            for(let i=0;i<resultImageMoi.length;i++){
              ds_hinhhanh_temp.push({
                name: resultImageMoi[i].file_name,
                url: resultImageMoi[i].file_url,
                check: false
              })
            }
            this.cbb_AnhGT.ds_hinhanh = ds_hinhhanh_temp.map((x, i)=>Object.assign(x, {id: i+1}))
          }
        }        
      }      
    },
    async link_view_file(list_file){
      try{
        this.loading(true)
        let response = await SuppliesAPI.link_view_file(this.axios, list_file)
        this.loading(false)
        if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
          return response.data.data
        }else{
          return []
        }
      }catch(e){
        this.loading(false)
        return []
      }
    },
    GetDataList: function (response) {
      console.log(response)
      if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
        return response.data.data
      } else if (response && response.data && response.data.error_code === 'BSS-00000204') {} else {
        this.$root.$toast.error('Lấy dữ liệu không thành công')
      }
      return []
    },
    Popup(modalId) {
      this.$bvModal.show(modalId);
    },
    ClosePopup(modalId) {
      this.$bvModal.hide(modalId);
      this.popupComponent = null;
    },
    async popupClosed(val) {
      if (!val) {
        this.ClosePopup("popupComponents1");
        return;
      }
      this.loading(true);
      try {
        switch (this.popupComponentName) {
          case 'SearchAccount':
          if (val.ma_kh) this.txtMaKH.value = val.ma_kh
          if (val.hdkh_id) this.hdkh_id = val.hdkh_id
          if (this.txtMaKH.value && this.txtMaKH.value.trim() != '') this.MaKHChanged()
          break
        }
      } catch (ex) {
        if (ex.data && ex.data.message_detail) {
          this.$toast.error("" + ex.data.message_detail);
        } else {
          this.$toast.error("" + ex);
        }
        
        if (this.debug) console.error(ex);
      } finally {
        this.ClosePopup("popupComponents1");
        this.loading(false);
      }
    },
  }
}
</script>
