<template src="./UpdateUnitPrice3883.html"></template>
<style src="./UpdateUnitPrice3883.scss"></style>
<script>
import breadcrumb from '@/components/breadcrumb'
import VueFlatPickr from 'vue-flatpickr-component';
import gridview from '@/components/Shared/gridview'
//import gridview from "./components/DataGrid";


import API from './API'
import { DataManager, Query, ODataAdaptor, Predicate } from '@syncfusion/ej2-data'
//CSS
import '@/assets/vendor/bootstrap-table/bootstrap-table.min.css';
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.css';
//JS
import '@/assets/vendor/jquery/split'
import '@/assets/vendor/bootstrap-table/bootstrap-table.min'
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.min'
//
export default {
  components: {breadcrumb,VueFlatPickr,API,DataManager, Query, ODataAdaptor, Predicate, gridview},
  name: 'UpdateUnitPrice3883',
  mounted: async function() {
    await this.InitMonthYear();
    await this.GetDSDonGia();
    await this.SetButton(0);
  },
  data () {
    return {
      dateconfig: {
        altFormat: 'd/m/Y H:i:S',
        altInput: true,
        dateFormat: 'd/m/Y H:i:S',
        allowInput:true,
      },
      ngay_ht: new Date(),
      header: {
        title: 'GÁN THUỘC TÍNH VẬT TƯ',
        list: [
          { name: 'Quản trị hệ thống', link: { name: 'Ui.cards' } },
          {
            name: 'Cập nhật đơn giá theo công văn 3883',
            link: { name: 'Ui.buttons' }
          }
        ]
      },
      isNhapMoi:false,
      isGhiLai:false,
      isHuy:false,
      isXoa:false,

      isCapNhat:false,
      isCachTim: false,

       listbox: {
          ds_dongia: {
          list: [],
          header: [ {fieldName: 'thangnam', headerText: 'Tháng cập nhật', allowFiltering: true, width: 130}, 
                    {fieldName: 'dongia', headerText: 'Đơn giá', allowFiltering: true,allowSorting: false, type: "Number", format: "N0", width: 130}, 
                    {fieldName: 'sl_fiber', headerText: 'Số lượng Fiber', allowFiltering: true,allowSorting: false, type: "Number", format: "N0", width: 120}, 
                    {fieldName: 'quy_tienluong', headerText: 'Quỹ tiền lương', allowFiltering: true,allowSorting: false, type: "Number", format: "N0", width: 130}, 
                    {fieldName: 'muc_chi', headerText: 'Mức chi', allowFiltering: true,allowSorting: false, type: "Number", format: "N0", width: 120}, 
                    {fieldName: 'muc_thuhoi', headerText: 'Mức thu hồi', allowFiltering: true,allowSorting: false, type: "Number", format: "N0", width: 130}, 
                    {fieldName: 'khokhan', headerText: 'Khó khăn', allowFiltering: true}, 
                    {fieldName: 'dexuat', headerText: 'Đề xuất', allowFiltering: true},   
                    {fieldName: 'user_cn', headerText: 'User CN', allowFiltering: true, width: 150}, 
                    {fieldName: 'ngay_cn', headerText: 'Ngày CN', allowFiltering: true, width: 150}, 
                    {fieldName: 'may_cn', headerText: 'Máy CN', allowFiltering: true, width: 150}, 
                       ],
          value: {},
          checked: []
        },
        fields: {
          dataSource: null,
          id: 'donvi_id',
          parentID: 'donvi_cha_id',
          text: 'ten_dv',
          hasChildren: 'hasChild'
        },
        thang: {
          list: ["01","02","03","04","05","06","07","08","09","10","11","12"],
          value: {},
          },
        nam: {
          list: [],
          value: {},
          },
      },
      dateConfig: {
        altInput: true,
        dateFormat: 'd/m/Y',
        altFormat: 'd/m/Y'
      },
      dongia: "0",
      slfiber: "0",
      quytienluong: "0",
      mucchi: "0",
      mucthuhoi: "0",
      khokhan: "",
      dexuat: "",
      dongia_id: "",
      index:0,
      success_code: "BSS-00000000",
      
    }
  },
  computed: {
     NhapMoi: {
      get() { return this.isNhapMoi; },
      set(value) { this.isNhapMoi=value; 
      }
    },
    GhiLai: {
      get() { return this.isGhiLai; },
      set(value) { this.isGhiLai=value; 
      }
    },
    Huy: {
      get() { return this.isHuy; },
      set(value) { this.isHuy=value; 
      }
    },
    Xoa: {
      get() { return this.isXoa; },
      set(value) { this.isXoa=value; 
      }
    }, 
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
    GetData: function (response) {
      if (response.data.error === 200 || response.data.error === '200') {
        return response.data.data
      } else {
        console.log(response.data.error_code)
      }
      return []
    },
    focusByClass(classText){
      const collection = document.getElementsByClassName(classText);
      if(collection!=null && collection.length > 0){
        collection[0].focus()
      }
    },
    SetButton(kieu)
    {
      this.isNhapMoi = false;
      this.isGhiLai = false;
      this.isHuy = false;
      this.isXoa = false;
      if (kieu == -1)//Bat dau
      {
        this.isGhiLai = true;
        this.isHuy = true;
      }
      if (kieu == 0)//Bat dau
      {
        this.isNhapMoi = true;
        this.isGhiLai = true;
        this.isXoa = true;
        this.Clear();
        this.$refs.grid.setCurrentSelectedRow(this.index);
      }
      if (kieu == 1)//Them moi
      {
        this.isGhiLai = true;
        this.isHuy = true;
        this.Clear();
        this.$nextTick(function () {
          document.getElementById('thangcapnhat').focus()
        })
        
      }
      if (kieu == 2)//Huy
      {
        this.isNhapMoi = true;
        this.isXoa = true;
        this.Clear();
      }
      if (kieu == 3)//Edit
      {
        this.isNhapMoi = true;
        this.isXoa = true;
        this.isGhiLai = true;
        this.isHuy = true;
        
      }
    },

    isNumber($event) {
      alert("xx");
      //console.log($event.keyCode); //keyCodes value
      let keyCode = ($event.keyCode ? $event.keyCode : $event.which);
      if ((keyCode < 48 || keyCode > 57) && keyCode !== 46) { // 46 is dot
          $event.preventDefault();
      }
    },

    Clear()
    {
      this.dongia = 0;
      this.slfiber = "0";
      this.quytienluong = "0";
      this.mucchi= "0";
      this.mucthuhoi = "0";
      this.khokhan = "";
      this.dexuat = "";
      var currentyear = (new Date()).getFullYear();
      var currentmonth = (new Date()).getMonth()+1;
      this.listbox.nam.value.id = currentyear;
      if (currentmonth<10)
      {
        this.listbox.thang.value.id = "0" + currentmonth;
      }
      else
      {
        this.listbox.thang.value.id = currentmonth;
      }
    },
    InitMonthYear()
    {
      var currentyear = (new Date()).getFullYear();
      var currentmonth = (new Date()).getMonth();
      for (var i = currentyear - 5; i <= currentyear + 5; i++)
      {
        this.listbox.nam.list.push(i);
      }
      this.listbox.nam.value.id = currentyear;
      this.listbox.thang.value.id = currentmonth;
    },

    btnNhapMoi()
    {
      this.SetButton(1);
    },

    btnHuy()
    {
      this.SetButton(0);
    },

    GetDSDonGia: async function ()
    {
      var input = {};
      this.loading(true);
      await API.sp_ds_dongia_3883(this.axios, input).then((response) => {
            this.loading(false);
            if(response.data.error_code && response.data.error_code === this.success_code) {
              this.listbox.ds_dongia.list = response.data.data;
              this.$refs.grid.setCurrentSelectedRow(this.index);

            }
           
          }
          ); 
      //this.listbox.ds_dongia.list = this.GetData(await API.sp_ds_dongia_3883(this.axios, input));
      //this.$refs.grid.setCurrentSelectedRow(this.index);
      //this.loading(false);
    },
    rowClicked(idx,obj)
    {
      this.index = idx;
      if(!obj) { 
        return; 
      }
      this.dongia_id = obj.id;
      this.dongia = obj.dongia;
      this.slfiber = obj.sl_fiber;
      this.quytienluong = obj.quy_tienluong;
      this.mucchi = obj.muc_chi;
      this.mucthuhoi = obj.muc_thuhoi;
      this.khokhan = obj.khokhan;
      this.dexuat = obj.dexuat;
      var thang = obj.thangnam.substring(0, 2);
      var nam = obj.thangnam.substring(3, 7);

      this.listbox.thang.value.id = thang;
      this.listbox.nam.value.id = nam;
    },
    gridChanged(obj)
    {
       if(!obj) { 
        return; 
      }
      this.dongia_id = obj.id;
      this.dongia = obj.dongia;
      this.slfiber = obj.sl_fiber;
      this.quytienluong = obj.quy_tienluong;
      this.mucchi = obj.muc_chi;
      this.mucthuhoi = obj.muc_thuhoi;
      this.khokhan = obj.khokhan;
      this.dexuat = obj.dexuat;
      var thang = obj.thangnam.substring(0, 2);
      var nam = obj.thangnam.substring(3, 7);

      this.listbox.thang.value.id = thang;
      this.listbox.nam.value.id = nam; 
    },

    btnGhiLai: async function()
    {
      if (this.dongia ==0 )
      {
        this.ShowAlert("Đơn giá phải lớn hơn 0!",2);
        this.focusByClass('txtDonGia');
        return;
      }
      if (this.isNhapMoi) //sửa 
      {
        var inputdata = [];
        var data = {};
        data.KHOKHAN = this.khokhan;
        data.MUC_THUHOI = this.mucthuhoi;
        data.SL_FIBER = this.slfiber;
        data.MUC_CHI = this.mucchi;
        data.QUY_TIENLUONG = this.quytienluong;
        data.DONGIA = this.dongia;
        data.ID = this.dongia_id;
        data.DEXUAT = this.dexuat;
        data.THANGNAM = this.listbox.thang.value.id + "/" + this.listbox.nam.value.id;
        inputdata.push(data);
        var input = {};
        input.data = inputdata;
        input.is_insert = 0;
        console.log(input);
        this.loading(true);
        await API.sp_dongia_update(this.axios, input).then((response) => {
            this.loading(false);
            if(response.data.error_code && response.data.error_code === this.success_code) {
              this.ShowAlert("Cập nhật đơn giá thành công!",0);
              this.GetDSDonGia();
              this.SetButton(0);
            }
            else
            {
              this.ShowAlert("Lỗi khi cập nhật đơn giá: "+ response.data.message,2);
            }
          }
          ); 
      }
      else //thêm mới
      {
        var inputdata = [];
        var data = {};
        data.KHOKHAN = this.khokhan;
        data.MUC_THUHOI = this.mucthuhoi;
        data.SL_FIBER = this.slfiber;
        data.MUC_CHI = this.mucchi;
        data.QUY_TIENLUONG = this.quytienluong;
        data.DONGIA = this.dongia;
        data.ID = "";
        data.DEXUAT = this.dexuat;
        data.THANGNAM = this.listbox.thang.value.id + "/" + this.listbox.nam.value.id;
        inputdata.push(data);
        var input = {};
        input.data = inputdata;
        input.is_insert = 1;
        console.log(input);
        this.loading(true);
          await API.sp_dongia_update(this.axios, input).then((response) => {
          console.log(response.data);
            this.loading(false);
            if(response.data.error_code && response.data.error_code === this.success_code) {
              this.ShowAlert("Thêm mới đơn giá thành công!",0);
              this.index = 0;
              this.GetDSDonGia();
              this.SetButton(0);
            }
            else
            {
              this.ShowAlert("Lỗi khi thêm mới đơn giá: "+ response.data.message,2);
            }
          }
          ); 
      }
    },

    btnXoa: async function()
    {
      this.$bvModal.msgBoxConfirm('Bạn thật sự muốn xoá đơn giá này không?', {
      title: '', size: 'sm', okTitle: 'Đồng ý',cancelTitle: 'Hủy',}).then(async v => {
      if(v) {
          var input = {};
          input.dongia_id = this.dongia_id;
          console.log(input);
          this.loading(true);
          await API.fn_dongia_delete(this.axios, input).then((response) => {
            this.loading(false);
            if(response.data.error_code && response.data.error_code === this.success_code) {
              this.ShowAlert("Xóa đơn giá thành công!",0);
              this.index = 0;
              this.GetDSDonGia();
            }
            else
            {
              this.ShowAlert("Lỗi khi xóa đơn giá!",2);
            }
          }
          ); 
      }
      });
      
    },
 


  },
};

</script>
