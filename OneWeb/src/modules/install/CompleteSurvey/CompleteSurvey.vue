<template src="./CompleteSurvey.html"></template>
<style src="./CompleteSurvey.scss"></style>
<script>
import breadcrumb from '@/components/breadcrumb'
import VueFlatPickr from 'vue-flatpickr-component';
import CompleteSurveyAPI from './CompleteSurveyAPI'
import gridview from '@/components/Shared/gridview'
import DatePicker from 'vue2-datepicker' 
import moment from 'moment'
//CSS
import '@/assets/vendor/bootstrap-table/bootstrap-table.min.css';
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.css';
//JS
import '@/assets/vendor/jquery/split'
import '@/assets/vendor/bootstrap-table/bootstrap-table.min'
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.min'
//
export default {
  components: {breadcrumb,VueFlatPickr,gridview,DatePicker },
  name: 'CompleteSurvey',
  mounted () {
   
    //Lấy ds dịch vụ viễn thông
    CompleteSurveyAPI.lay_ds_dvvt(this.axios, {}).then((response) => {
    if(response.data.error_code && response.data.error_code === this.success_code) {
        this.listbox.ds_dichvu_vt.list = response.data.data.map(x=>({id:x.dichvuvt_id,text:x.ten_dvvt}));
        //this.listbox.donvinhan.list = response.data.data.map(x=>({id:x.dichvuvt_id,text:x.ten_dvvt}));
        
        }
      }
    ); 
    //Lấy ds nhân viên khảo sát
    CompleteSurveyAPI.lay_ds_nv_theo_dv(this.axios, {}).then((response) => {
    if(response.data.error_code && response.data.error_code === this.success_code) {
        this.listbox.ds_nhanvien_ks.list = response.data.data.map(x=>({id:x.nhanvien_id,text:x.ten_nv}));
        
        }
      }
    ); 
    //Lấy danh sách hợp đồng cần khảo sát
   CompleteSurveyAPI.lay_danhsach_hd_ks_v3(this.axios, {loaihd_id:0}).then((response) => {
        if(response.data.error_code && response.data.error_code === this.success_code) {
          this.listbox.ds_phieuyc_khaosat.list = response.data.data;
          console.log(this.listbox.ds_phieuyc_khaosat.list);
          //this.listbox.ds_phieuyc_khaosat.header = [{name:'ma_gd',text:'Mã GD'},{name:'ten_kh',text:'Tên khách hàng'},{name:'diachi_ld',text:'Địa chỉ lắp đặt'},{name:'ten_kieuld',text:'Kiểu yêu cầu'},{name:'loaihinh_tb',text:'Loại hình TB'}]
        }
        else {
          this.listbox.ds_phieuyc_khaosat.list = [];
        }
      }); 
      
  },
   
  data () {
    return {
      dateconfig: {
        altFormat: 'd/m/Y',
        altInput: true,
        dateFormat: 'd/m/Y',
        allowInput:true,
      },
      dateFormat: "DD/MM/YYYY",
      ngay_ht: new Date(),
      header: {
        title: 'CẬP NHẬT HOÀN THÀNH KHẢO SÁT',
        list: [
          { name: 'Lập hợp đồng', link: { name: 'Ui.cards' } },
          {
            name: 'Lắp đặt mới',
            link: { name: 'Ui.buttons' }
          }
        ]
      },
      isAddNew: false,
      isUpdate: false,
      isReturn: false,
      isPrint: false,
      isComplete: false,
      listbox: {
        ds_dichvu_vt: {
          list: [],
          value: { dichvuvt_id: 0 , ten_dvvt:"" },
          isEnabled: false,
        },
        ds_nhanvien_ks: {
          list: [],
          value: { nhanvien_id:"", ten_nv:"" },
          isEnabled: false,
        },
        ds_ketqua_ks: {
          list: [{ketqua:"Thi công được",id_ketqua: 1},{ketqua:"Không thi công được",id_ketqua: 0}],
          value: { ketqua:"", id_ketqua:"" },
          isEnabled: false,
        },
        ds_phieuyc_khaosat: {
          list: [],
          header:[ {fieldName: 'ma_gd', headerText: 'Mã GD', allowFiltering: true}, 
                      {fieldName: 'ten_kh', headerText: 'Tên khách hàng', allowFiltering: true},
                      {fieldName: 'diachi_ld', headerText: 'Địa chỉ lắp đặt', allowFiltering: true , allowHtml : true},
                      {fieldName: 'ten_kieuld', headerText: 'Kiểu yêu cầu', allowFiltering: true , allowHtml : true},                                
                      {fieldName: 'loaihinh_tb', headerText: 'Loại hình TB', allowFiltering: true , allowHtml : true},                                     
                      ],
          value: {},
          isEnabled: true,
        },
      },

      dateConfig: {
        altInput: true,
        dateFormat: 'd/m/Y',
        altFormat: 'd/m/Y'
      },
      from_date: new Date(),
      to_date: new Date(),
      success_code: "BSS-00000000",
      //parameters for update
      magd: "",
      tenkh: "",
      diachikh: "",
      diachild: "",
      kieuld: "",
      somayacc: "",
      loaihdid: 0,
      mday: 0,
      doicap: "",
      soluong: 0,
      hopcap: "",
      ghichu:"",
      phieuid:"",
      dichvuvtid: 0,
    }
  },
  methods: {
    ShowAlert(mesage,val)
    {
      if (val==0)
      {
        this.$toast.success(mesage);
      }
      else if (val==1)
      {
        this.$toast.warning(mesage);
      }
      else if (val==2)
      {
        this.$toast.error(mesage);
      }
    },
    dichvuvtchange() {
      var temp= this.listbox.ds_dichvu_vt.list.filter(a => a.id == this.listbox.ds_dichvu_vt.value.dichvuvt_id);
      this.loading(true);
       CompleteSurveyAPI.lay_danhsach_hd_ks_v3(this.axios, {loaihd_id:0}).then((response) => {
         this.loading(false);
        if(response.data.error_code && response.data.error_code === this.success_code) {
          this.listbox.ds_phieuyc_khaosat.list = response.data.data;
          this.listbox.ds_phieuyc_khaosat.list = this.listbox.ds_phieuyc_khaosat.list.filter(a=> a.ten_dvvt==null || a.ten_dvvt.toLowerCase().indexOf(temp[0]["text"].toLowerCase())>=0);
          //this.listbox.ds_phieuyc_khaosat.header = [{name:'ma_gd',text:'Mã GD'},{name:'ten_kh',text:'Tên khách hàng'},{name:'diachi_ld',text:'Địa chỉ lắp đặt'},{name:'ten_kieuld',text:'Kiểu yêu cầu'},{name:'loaihinh_tb',text:'Loại hình TB'}]
        }
        else {
          this.listbox.ds_phieuyc_khaosat.list = [];
        }
      }); 
    },
    gridChanged(obj){
      console.log(obj)
      if(!obj) { 
        this.isUpdate = false;
        this.isReturn = false;
        this.isPrint = false;
        this.isComplete = false;
        return; 
      }
      else 
      {
        this.isUpdate = true;
        this.isReturn = true;
        this.isPrint = true;
        this.isComplete = true;
      }
      
       //hiển thị thông tin trong Cập nhật thông tin khảo sát
      this.somayacc = obj.hdtb_id;
      this.loaihdid = obj.loaihd_id;
      this.phieuid = obj.phieu_id;
      this.dichvuvtid = obj.dichvuvt_id;
      this.ngay_ht = moment(obj.ngay_ht_gp).format("DD/MM/YYYY"); 
      //Hiển thị thông tin thuê bao
      this.magd = obj.ma_gd;
      this.tenkh = obj.ten_kh;
      this.diachikh = obj.diachi_kh;
      this.diachild = obj.diachi_ld;
      this.kieuld = obj.ten_kieuld;
      this.soluong = obj.soluong;
      this.doicap = obj.doicap;
      this.mday = obj.culy;
      this.hopcap = obj.hopcap;
      this.listbox.ds_ketqua_ks.value.id_ketqua =  obj.ketquaks;
      this.listbox.ds_nhanvien_ks.value.nhanvien_id = obj.nhanvien_khaosat_id;
      this.listbox.ds_dichvu_vt.value.dichvuvt_id = obj.dichvuvt_id;
    },

    rowClicked(idx,obj)
    {
      //console.log(obj);
      this.index = idx;
      if(!obj) { 
        return; 
      }
      console.log(obj.hdtb_id); 
      //hiển thị thông tin trong Cập nhật thông tin khảo sát
      this.somayacc = obj.hdtb_id;
      this.loaihdid = obj.loaihd_id;
      this.phieuid = obj.phieu_id;
      this.dichvuvtid = obj.dichvuvt_id;
      //Hiển thị thông tin thuê bao
      this.magd = obj.ma_gd;
      this.tenkh = obj.ten_kh;
      this.diachikh = obj.diachi_kh;
      this.diachild = obj.diachi_ld;
      this.kieuld = obj.ten_kieuld;
      this.soluong = obj.soluong;
      this.doicap = obj.doicap;
      this.mday = obj.culy;
      this.hopcap = obj.hopcap;
      this.listbox.ds_ketqua_ks.value.id_ketqua =  obj.ketquaks;
      this.listbox.ds_nhanvien_ks.value.nhanvien_id = obj.nhanvien_khaosat_id;
      this.listbox.ds_dichvu_vt.value.dichvuvt_id = obj.dichvuvt_id;
    },


    filterDocFiles(){
      this.listbox.ds_phieuyc_khaosat.list = this.listbox.ds_phieuyc_khaosat.list.filter(a=> a.hdtb_id==null || a.hdtb_id.toLowerCase().indexOf(this.listbox.ds_phieuyc_khaosat.value.hdtb_id.toLowerCase())>=0);
    },

    btnUpdateClicked() {
      var pdata = {};
      pdata.culy = this.mday;
      pdata.dichvuvt_id = this.dichvuvtid;
      pdata.doicap = this.doicap;
      pdata.ghichu = this.ghichu;
      pdata.hdtb_id = this.somayacc;
      pdata.hopcap = this.hopcap;
      pdata.ketquaks = this.listbox.ds_ketqua_ks.value.id_ketqua;
      pdata.loaihd_id = this.loaihdid;
      pdata.ngay_ht = this.ngay_ht.toLocaleString('en-GB').substring(0,10) + this.ngay_ht.toLocaleString('en-GB').substring(11,20);
      pdata.nhanvien_id = this.listbox.ds_nhanvien_ks.value.nhanvien_id;
      pdata.phieu_id = this.phieuid;
      this.loading(true);
      console.log(pdata);
      CompleteSurveyAPI.capnhat_khaosat(this.axios,pdata).then((response) => {
        this.loading(false);
        console.log(response.data);
        if(response.data.error_code && response.data.error_code === this.success_code) {
            this.ShowAlert("Đã cập nhật thông tin khảo sát",0);
          } else this.ShowAlert(response.data.message);
        }).catch(function(){
          this.ShowAlert('Cập nhật kết quả khảo sát không thành công! Vui lòng thử lại hoặc liên hệ quản trị viên.',2);
        }); 
    },

    btnCompleteClicked()
    {
      var pdata = {};
      pdata.hdtb_id = this.somayacc;
      pdata.ketquaks = this.listbox.ds_ketqua_ks.value.id_ketqua;
      pdata.loaihd_id = this.loaihdid;
      pdata.ngay_ht = this.ngay_ht.toLocaleString('en-GB').substring(0,10) + this.ngay_ht.toLocaleString('en-GB').substring(11,20);
      pdata.phieu_id = this.phieuid;
      console.log(pdata);
      this.loading(true);
      CompleteSurveyAPI.capnhat_hoanthanh_khaosat(this.axios,pdata).then((response) => {
        this.loading(false);
        if(response.data.error_code && response.data.error_code === this.success_code) {
            this.ShowAlert("Đã trả kết quả khảo sát",0);
          } else this.ShowAlert(response.data.message);
        }).catch(function(){
          this.ShowAlert('Cập nhật kết quả khảo sát không thành công! Vui lòng thử lại hoặc liên hệ quản trị viên.',2);
        }); 
    },

    btnReturnClicked(){
      var pdata = {};
      pdata.phieu_id = this.phieuid;
      console.log(pdata);
      this.loading(true);
      CompleteSurveyAPI.traphieu_giaosai(this.axios,pdata).then((response) => {
        this.loading(false);
        if(response.data.error_code && response.data.error_code === this.success_code) {
            this.ShowAlert("Đã trả phiếu khảo sát giao sai",0);
          } else this.ShowAlert(response.data.message);
        }).catch(function(){
          this.ShowAlert('Trả phiếu khảo sát không thành công! Vui lòng thử lại hoặc liên hệ quản trị viên.',2);
        }); 
    },
    
  },
};

</script>
