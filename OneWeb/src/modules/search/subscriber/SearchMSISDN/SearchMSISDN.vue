<template src="./SearchMSISDN.html"></template>
<style src="./SearchMSISDN.scss"></style>
<script>
import breadcrumb from '@/components/breadcrumb'
import VueFlatPickr from 'vue-flatpickr-component';
import bssFlatPicker from "@/components/BssDatePicker";
import SearchMSISDNAPI from './SearchMSISDNAPI'
import gridview from './gridview'
import xlsx from "xlsx";
//CSS
import '@/assets/vendor/bootstrap-table/bootstrap-table.min.css';
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.css';
//JS
import '@/assets/vendor/jquery/split'
import '@/assets/vendor/bootstrap-table/bootstrap-table.min'
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.min'
import { log } from 'pdfmake/build/pdfmake';
//
export default {
  components: { breadcrumb,gridview },
  name: 'SearchMSISDNAPI',
  mounted () {
    this.$bvModal.show('tracuukhoso')
    //load dịch vụ
    SearchMSISDNAPI.lay_ds_dv(this.axios, {}).then((response) => {
    if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000' && response.data.data) {
        this.listbox.dichvu_vienthong.list = response.data.data;
        this.filterbox.dichvu_vienthong.list = response.data.data;
        }
      }
    );
    //load trạng thái số
    SearchMSISDNAPI.lay_ds_tt_so(this.axios, {}).then((response) => {
    if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000' && response.data.data) {
        this.listbox.ds_tt_so.list = response.data.data;
        }
      }
    );
    // load danh sách kiểu số
    SearchMSISDNAPI.lay_ds_kieu_so(this.axios, {}).then((response) => {
    if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000' && response.data.data) {
        this.listbox.ds_kieu_so.list = response.data.data;
        }
      }
    );
    // load danh sách host
    SearchMSISDNAPI.lay_ds_host(this.axios, {}).then((response) => {
    if(response && response.data) {
        this.listbox.ds_host.list = response.data.data;
        }
      }
    );
    var input1 = {};
    input1.p_nghiepvu = "GET_QUANTRI_NGUOIDUNG";
    input1.p_ds_para = "{NGUOIDUNG_ID:"+ this.$auth.getNguoiDungID()+"}"
    // console.log(input1);
    SearchMSISDNAPI.nghiep_vu(this.axios, input1).then((response) => {
      // console.log(response.data.data[0].quantri);
        if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000' && response.data.data) {
          // console.log(response.data.data[0].quantri);
          if(response.data.data[0].quantri == 1){
            this.checkxoags = 1
            this.GiuSo = true;
          }  
        }
      });
  },
  destroyed () {},
  data () {
    return {
      header: {
        title: 'TRA CỨU KHO SỐ',
        list: [
          { name: 'Tra cứu', link: { name: 'Ui.cards' } },
          {
            name: 'Phát triển thuê bao',
            link: { name: 'Ui.buttons' }
          },
          {
            name: 'Tra cứu kho số',
            link: { name: 'Ui.buttons' }
          }
        ]
      },
      txtTraCuu: "",
      User: "",
      SoMay: "",
      XoaGS: false,
      checkxoags: 0,
      GiuSo: false,
      danhsachsomay: {
          list: [],
          header:[],
          value: {},
          isEnabled: true,
          selected: 0,
          checked: [],
          columns: [
          { fieldName: 'stt', headerText: 'Số TT', allowFiltering: true, width: '80px'},
          { fieldName: 'somay', headerText: 'Số máy', allowFiltering: true },
          { fieldName: 'kieu_so', headerText: 'Kiểu số', allowFiltering: true },
          { fieldName: 'tien_ck', headerText: 'Tiền cam kết', allowFiltering: true},
          { fieldName: 'ten_ttso', headerText: 'Trạng thái số', allowFiltering: true },
          { fieldName: 'ngay_gs', headerText: 'Ngày giữ', allowFiltering: true },
          { fieldName: 'thoigian_gs', headerText: 'Thời gian giữ', allowFiltering: true},
          { fieldName: 'nguoi_cn', headerText: 'Người giữ', allowFiltering: true },
          { fieldName: 'ghichu', headerText: 'Ghi chú', allowFiltering: true },                
        ],
      },
      danhsachdiadanh: {
          list: [],
          header:[],
          value: {},
          isEnabled: true,
          columns: [
          { fieldName: 'ten_quan', headerText: 'Quận/Huyện', allowFiltering: true},
          { fieldName: 'ten_phuong', headerText: 'Phường/Xã', allowFiltering: true },              
        ],
      },
      listbox: {
        dichvu_vienthong:{
          list:[],
          value: 1,
          isEnabled:false,
        },
        ds_tt_so: {
          list:[],
          value: 0,
          isEnabled:false,
        },
        ds_kieu_so: {
          list:[],
           value: 0,
          isEnabled:false,
        },
        ds_host: {
          list:[],
          value: 400,
          isEnabled:false,
        },
        ketquatimkiem: {
          list: [],
          header:[],
          value: {},
          isEnabled: true,
        },
        danhsachdiadanh: {
          list: [],
          header:[],
          value: {},
          isEnabled: true,
        },
        ketquatimkiem_temp: {
          list: [],
          header:[],
          value: {},
          isEnabled: true,
        },
        danhsachdiadanh_temp: {
          list: [],
          header:[],
          value: {},
          isEnabled: true,
        },
      },
      filterbox: {
        dichvu_vienthong:{
          list:[],
          value: "",
          isEnabled:false,
        },
        loaihinh_thuebao:{
          list:[],
          value: "",
          isEnabled:false,
        },
        isType:false,
      },
      dateConfig: {
        altInput: true,
        dateFormat: 'd/m/Y',
        altFormat: 'd/m/Y'
      },
      from_date: new Date(),
      to_date: new Date()
    }
  },
  methods: {
    ChageTTSo(){
      if(this.listbox.ds_tt_so.value == 6){
      //if(this.listbox.ds_tt_so.value == 6 && this.checkxoags == 1){
        this.XoaGS = true;
      }
      else this.XoaGS = false;
    },
    btnSearchClicked() {
      this.danhsachsomay.list = null;
      var pdata = {};
      pdata.dichVuVTId = this.listbox.dichvu_vienthong.value;
      pdata.kieuSoId = this.listbox.ds_kieu_so.value;
      pdata.kieuTK = 2;
      pdata.trangThaiSoId = this.listbox.ds_tt_so.value;
      pdata.timKiem = this.txtTraCuu;
      console.log(pdata);
      this.loading(true)
      SearchMSISDNAPI.tracuu(this.axios, pdata).then((response) => {
        this.loading(false)
        if(response && response.data && response.data.data) {
            if(response.data.data == null || response.data.data == "")
            {
              this.$toast.error("Không tìm thấy danh sách số máy!");
            }
            else this.danhsachsomay.list = response.data.data;
        }
      }).catch((e)=>{
        this.loading(false)
      });
    },
    btnPrefixClicked() {
      this.danhsachdiadanh.list = null;
      SearchMSISDNAPI.lay_ds_dia_danh(this.axios, {}).then((response) => {
        if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000' && response.data.data) {
            this.danhsachdiadanh.list = response.data.data;
            this.listbox.danhsachdiadanh.list = response.data.data;
        }
      });
      
    },
    select_selectedItemsChangedCG: function (dataKeys) {
      this.danhsachsomay.checked = dataKeys
      this.danhsachsomay.list.forEach(function (item) {
        if (dataKeys.filter(x => x === item.somay).length > 0) {
          item.ischecked = true
        } else {
          item.ischecked = false
        }
      })
    },
    selectedRowChanged(obj){
      this.SoMay = obj.somay;
    },
    GiuSo_Click(){
      if(this.User == ""){
        this.$refs.User.focus();
        this.$toast.error("Hãy nhập mã người dùng được giữ số");
      }
      else{
      var pdata = {};
      var json = {};
      json.SOMAY = this.SoMay;
      pdata.dichvu_id = this.listbox.dichvu_vienthong.value;
      var data = this.$refs.gridDSThueBao.getSelectedRecords();
      let thuebao_id;
      let thuebao_id1 = "";
      let thuebao_id2 = "";
      for(var i=0 ; i<data.length; i++){
        thuebao_id = data[i].somay;
        if(i==0){
          if(data.length == 1) thuebao_id2 = thuebao_id2.concat("[{\"SOMAY\":", thuebao_id+'}');
          else thuebao_id2 = thuebao_id2.concat("[{\"SOMAY\":", thuebao_id+'},');
        }
        else {
        thuebao_id1 = thuebao_id1.concat(",{\"SOMAY\":", thuebao_id+'}');
        }
      }
      thuebao_id1 = thuebao_id1.slice(1);
      thuebao_id1 = thuebao_id2+thuebao_id1
      thuebao_id1 = thuebao_id1 + "]"
      if(thuebao_id1==""){
        this.$toast.error("Bạn phải chọn ít nhất một thuê bao");
      }
      // else if(!data || data.length <= 0) {
      //   this.$toast.error("Hãy chọn số máy để giữ số!");
      // }
      else {
      pdata.ds_somay = thuebao_id1;
      pdata.ma_nd = this.User;
      SearchMSISDNAPI.giu_so(this.axios, pdata).then((response) => {
        if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000' && response.data.data) {
            if (response.data.data == "Cập nhật thành công"){
              this.$toast.success(response.data.data);
              this.btnSearchClicked();
            }
            else this.$toast.error(response.data.data);
        }
      });
      }
      }
    },
    HuyGS(){
      var data = this.$refs.gridDSThueBao.getSelectedRecords();
      if(data.length==0){
        this.$toast.error('Chưa chọn thuê bao muốn xóa giữ số.')
        return
      }
      var dsSoMay=[]
      var dsKhongThoa=[]

      if(this.checkxoags==1){
        dsSoMay=data.map(x=>x.somay)
      }else{
        dsSoMay=data.filter(x=>x.nguoi_cn==this.$root.token.getUserName()).map(x=>x.somay)
        dsKhongThoa=data.filter(x=>x.nguoi_cn!=this.$root.token.getUserName()).map(x=>x.somay)
      }
      if(dsKhongThoa.length>0){
        this.$toast.error(`Các thuê bao ${dsKhongThoa.toString()} bạn không phải người giữ, không thể thao tác`)
        return
      }
      if(dsSoMay.length==0){
        this.$toast.error('Bạn phải chọn ít nhất một thuê bao')
        return
      }
      
      this.$bvModal.msgBoxConfirm('Bạn có chắc chắn muốn xóa giữ số?', {
                	title: '',
                	size: 'sm',
                	okTitle: 'Đồng ý',
                	cancelTitle: 'Hủy',
            	    }).then(async v => {
                	if(!v) {
                  	return;
                	}
      var pdata = {};
      pdata.dichvu_id = this.listbox.dichvu_vienthong.value;
      pdata.ds_somay = JSON.stringify(dsSoMay.map(x=>{
        return {
          SOMAY:x
        }
      }));

      console.log('pdata', pdata)

    //   let thuebao_id;
    //   let thuebao_id1 = "";
    //   let thuebao_id2 = "";
    //   for(var i=0 ; i<data.length; i++){
    //     thuebao_id = data[i].somay;
    //     if(i==0){
    //     thuebao_id2 = thuebao_id2.concat("[{\"SOMAY\":", thuebao_id+'},');
    //   //  console.log(thuebao_id2);
    //     }
    //     else {
    //     thuebao_id1 = thuebao_id1.concat(",{\"SOMAY\":", thuebao_id+'}');
    //     }
    //   }
    //   thuebao_id1 = thuebao_id1.slice(1);
    //   thuebao_id1 = thuebao_id2+thuebao_id1
    //   thuebao_id1 = thuebao_id1 + "]"
    //   if(thuebao_id1==""){
    //     this.$toast.error("Bạn phải chọn ít nhất một thuê bao");
    //   }
    //  // console.log(thuebao_id1);
    //   pdata.ds_somay = thuebao_id1;
    //  console.log(pdata);
      this.loading(true)
      SearchMSISDNAPI.huy_giu_so(this.axios, pdata).then((response) => {
        this.loading(false)
        if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000' && response.data.data) {
            this.btnSearchClicked();
            this.$toast.success(response.data.data);
        }
      }).catch((e)=>{
        this.loading(false)
      });
      });
    },
    XuatExcel() {
        //console.log("File add", this.listFileMau.length);
        console.log(this.danhsachsomay.list);
        if (this.danhsachsomay.list.length <= 0) {
          this.$toast.error("Chưa có danh sách để xuất Excel!");
          return;
        }
        this.loading(true);
        try {
          var dsach = xlsx.utils.json_to_sheet(this.danhsachsomay.list);
          var wb = xlsx.utils.book_new();
          xlsx.utils.book_append_sheet(wb, dsach, "DS");
          xlsx.writeFile(wb, "Danhsachsomay.xlsx");
        } catch (error) {
        } finally {
          this.loading(false);
        }
      },

    created: async function () {
      this.loading(true);
      this.loadData();
      this.loading(false);
    },
  },
};

</script>
