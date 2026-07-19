<template src="./Units.html"></template>
<style src="./Units.scss"></style>
<script>
import breadcrumb from '@/components/breadcrumb'
import API from './API'

import VueFlatPickr from 'vue-flatpickr-component';
//CSS
import '@/assets/vendor/bootstrap-table/bootstrap-table.min.css';
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.css';
//JS
import '@/assets/vendor/jquery/split'
import '@/assets/vendor/bootstrap-table/bootstrap-table.min'
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.min'
//
export default {
  components: {breadcrumb,API,VueFlatPickr },
  name: 'Units',
  mounted () {
     //this.$root.$on('bv::modal::shown', (bvEvent, modalId) => {
     
      this.formLoad();
    //})
  },
  data () {
    return {
      showPopup:false,
      dateconfig: {
        altFormat: 'd/m/Y',
        altInput: true,
        dateFormat: 'd/m/Y',
        allowInput:true,
      },
      header: {
        title: 'ĐỌC FILE KHOÁ MỞ THUÊ BAO',
        list: [
          { name: 'Lập hợp đồng', link: { name: 'Ui.cards' } },
          {
            name: 'Lắp đặt mới',
            link: { name: 'Ui.buttons' }
          }
        ]
      },
      dateConfig: {
        altInput: true,
        dateFormat: 'd/m/Y',
        altFormat: 'd/m/Y'
      },
      from_date: new Date(),
      to_date: new Date(),
      abc: 21,

      isNhapMoi:false,
      isGhiLai:false,
      isHuy:false,
      isXoa:false,


      listbox: {
        ds_donvitinh: {
          list: [],
          header: [ {fieldName: 'DVI_TINH', headerText: 'Đơn vị tính', allowFiltering: true,   width: '200px !important' ,}, 
                       ],
          value: {},
          isEnabled: true,
        },
        
      },
      success_code: "BSS-00000000",
      DonViTinh: "",
      dvitinh_id: "0",
      showResult:false,
      isUploaded:false,
      index:0,
      page:0,
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
    formLoad ()
    {
      this.LoadGrid()
      this.SetButton(1)
    },
    btnPopupClick: function (item) {
      this.$bvModal.show('PopUp');
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
        if(this.$refs.DonViTinh) this.$refs.DonViTinh.focus();
        this.Clear();
      }
      if (kieu == 1)//Them moi
      {
        this.isGhiLai = true;
        this.isHuy = true;
        if(this.$refs.DonViTinh) this.$refs.DonViTinh.focus();
        this.Clear();
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

    Clear()
    {
      this.DonViTinh = "";
      this.dvitinh_id = "0";
    },

    LoadGrid: async function () {
      var input = {};
      this.listbox.ds_donvitinh.list = this.GetData(await API.QLVT_DVI_TINH(this.axios, input));
      if (this.listbox.ds_donvitinh.list.length>0)
      {
        if(this.$refs.gridDV) this.$refs.gridDV.setCurrentSelectedRow(this.index);
      } 
    },

    gridChanged(obj)
    {
      //alert("xx");
      if(!obj) { 
        return; 
      }
      this.dvitinh_id =  obj.DVITINH_ID;
      this.DonViTinh =  obj.DVI_TINH;
      this.SetButton(3);
    },

    rowselect(idx,rdata)
    {
      this.index = idx;
    },

    btnHuy()
    {
      this.SetButton(2);
      let records = [];
      if(this.$refs.gridDV) records = this.$refs.gridDV.getSelectedRecords();
      if(records.length > 0) {
        this.dvitinh_id =  records[0].DVITINH_ID;
        this.DonViTinh =  records[0].DVI_TINH;
      }
    },

    btnNhapMoi()
    {
      this.SetButton(1);
    },
    
    btnGhiLai: async function ()
    {
      if (this.DonViTinh.trim() == "")
      {
        this.ShowAlert("Đơn vị tính không được trống!",2);
        if(this.$refs.DonViTinh) this.$refs.DonViTinh.focus();
        return;
      }
      else if (this.DonViTinh.trim().length>100)
      {
        this.ShowAlert("Đơn vị tính không được dài quá 100 ký tự!",2);
        if(this.$refs.DonViTinh) this.$refs.DonViTinh.focus();
        return;
      }

      if (this.isNhapMoi) //insert
      {
        var input1 = {};
        input1.dvi_tinh_data = "{\"DVITINH_ID\":" + this.dvitinh_id + ", \"DVI_TINH\": \"" + this.DonViTinh + "\"}";
        input1.kieu =2; //cập nhật
        console.log(input1);
        await API.fn_ghilai_dvi_tinh(this.axios, input1).then((response) => {
        if(response.data.error_code && response.data.error_code === this.success_code) {
            this.ShowAlert("Cập nhật thành công!",0);
            this.LoadGrid();
        }
        else
        {
          this.ShowAlert(response.data.message + " : " + response.data.data,2);
        }
        }
        ).catch(function(){
          console.log("Có lỗi xảy ra khi truy cập thông tin. Vui lòng thử lại hoặc liên hệ quản trị viên.");
        });
      }
      else //Nhập mới
      {
        var input = {};
        var dvi_tinh_data ="";
        input.keyname="DVITINH_ID";
        input.numblocksize = 1;
        input.numretry = 10;
        await API.get_keys(this.axios, input).then((response) => {
          if(response.data.error_code && response.data.error_code === this.success_code) {
              dvi_tinh_data = "{\"DVITINH_ID\":" + response.data.data + ", \"DVI_TINH\": \"" + this.DonViTinh + "\"}";
              var input1 = {};
              input1.dvi_tinh_data = dvi_tinh_data;
              input1.kieu =1; //Thêm mới
              console.log(input1);
              API.fn_ghilai_dvi_tinh(this.axios, input1).then((response) => {
              if(response.data.error_code && response.data.error_code === this.success_code) {
                this.ShowAlert("Thêm mới thành công!",0);
                this.LoadGrid();
              }
              else
              {
                this.ShowAlert(response.data.message + " : " + response.data.data,2);
              }
              }
              ).catch(function(){
                console.log('Có lỗi xảy ra khi truy cập thông tin. Vui lòng thử lại hoặc liên hệ quản trị viên.');
              });
          }
          else
          {
            this.ShowAlert("Lấy key không thành công !" + response.data.message,2);
          }
        }
        );
      }

    },

    btnXoa: async function ()
    {
      this.$bvModal.msgBoxConfirm('Bạn thật sự muốn xoá danh mục này không?', {
      title: '', size: 'sm', okTitle: 'Đồng ý',cancelTitle: 'Hủy',}).then(async v => {
        if(v) {
          var input = {};
          input.dvi_tinh_id=this.dvitinh_id;
          console.log(this.dvitinh_id);
          await API.fn_xoa_dvi_tinh(this.axios, input).then((response) => {
            if(response.data.error_code && response.data.error_code === this.success_code) {
                this.ShowAlert("Xóa dữ liệu đơn vị tính thành công !",0);
                this.SetButton(0);
                this.LoadGrid();
            }
            else
            {
              this.ShowAlert("Xóa dữ liệu đơn vị tính không thành công ! " + response.data.message,2);
            }
          }
          ).catch(function(){
            console.log('Có lỗi xảy ra khi truy cập thông tin. Vui lòng thử lại hoặc liên hệ quản trị viên.');
          });
          
          } 
        });
    },
  }
};
</script>
