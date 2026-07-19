<template src="./UpdateAttributeType.html"></template>
<style src="./UpdateAttributeType.scss"></style>
<script>
import breadcrumb from '@/components/breadcrumb'
import VueFlatPickr from 'vue-flatpickr-component';
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
  components: {breadcrumb,VueFlatPickr,API,DataManager, Query, ODataAdaptor, Predicate },
  name: 'UpdateAttributeType',
  mounted () {
    this.LoadKieuThuocTinh();
    this.SetButton(0);

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
        title: 'KIỂU THUỘC TÍNH',
        list: [
          { name: 'Lập hợp đồng', link: { name: 'Ui.cards' } },
          {
            name: 'Lắp đặt mới',
            link: { name: 'Ui.buttons' }
          }
        ]
      },
      isNhapMoi:false,
      isGhiLai:false,
      isHuy:false,
      isXoa:false,


      listbox: {

        ds_kieuthuoctinh: {
          list: [],
          header: [ {fieldName: 'kieu_tt', headerText: 'Ký hiệu', allowFiltering: true}, 
                    {fieldName: 'caulenh', headerText: 'Câu lệnh', allowFiltering: true}, 
                    {fieldName: 'ghichu', headerText: 'Ghi chú', allowFiltering: true}, 
                       ],
          value: {},
          checked: []
        },
      },
      txtKieuThuocTinh: "",
      txtCauLenh: "",
      txtGhiChu: "",
      kieutt_id: "",
      index: 0,
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
        this.isXoa = true;
        this.isGhiLai = true;
        this.isHuy = true;
        this.$refs.txtKieuThuocTinh.focus();
        this.Clear();
      }
      if (kieu == 1)//Them moi
      {
        this.isGhiLai = true;
        this.isHuy = true;
        this.$refs.txtKieuThuocTinh.focus();
        this.Clear();
      }
      if (kieu == 2)//Huy
      {
        this.isNhapMoi = true;
        this.isXoa = true;
        this.$refs.txtKieuThuocTinh.focus();
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
      this.txtKieuThuocTinh="";
      this.txtCauLenh = "";
      this.txtGhiChu ="";
    },

    LoadKieuThuocTinh: async function () {
      var input = {};
      try {
          this.loading(true);
          this.listbox.ds_kieuthuoctinh.list = this.GetData(await API.sp_lay_ds_kieu_tt(this.axios, input));
          console.log(this.listbox.ds_kieuthuoctinh.list);
          this.loading(false);
        } catch (ex) {
          this.loading(false);
        }
    },

    gridChanged(obj)
    {
      if(!obj) { 
        return; 
      }
      this.txtKieuThuocTinh =  obj.kieu_tt;
      this.txtCauLenh = obj.caulenh;
      this.txtGhiChu =  obj.ghichu;
      this.listbox.ds_kieuthuoctinh.value.kieutt_id = obj.kieutt_id;
      this.kieutt_id = obj.kieutt_id;
    },
    rowClicked(i,obj)
    {
      if(!obj) { 
        return; 
      }
      this.index = i;
    },
    btnNhapMoi()
    {
      this.Clear();
      this.SetButton(1);

    },
    btnGhiLai: async function ()
    {
      
      if (this.txtKieuThuocTinh =="")
      {
        this.ShowAlert("Bạn chưa nhập kiểu thuộc tính",2);
        this.$refs.txtKieuThuocTinh.focus();
        return;
      }
      if (this.txtCauLenh == "")
      {
        this.ShowAlert("Bạn chưa nhập câu lệnh",2);
        this.$refs.txtCauLenh.focus();
        return;
      }
      var input = {};
      if (!this.isNhapMoi) //Nhập mới
      {
        input.kieutt_id = null;
        input.kieu = 0;
      }
      else //Cập nhật
      {
        input.kieutt_id = this.kieutt_id;
        input.kieu = 1;
      }
      input.kieu_tt = this.txtKieuThuocTinh;
      input.caulenh = this.txtCauLenh;
      input.ghichu = this.txtGhiChu;
      console.log(input);
      await this.$bvModal.msgBoxConfirm('Bạn có muốn cập nhật dữ liệu không?', {
                    title: '',
                    size: 'sm',
                    okTitle: 'Đồng ý',
                    cancelTitle: 'Hủy',
                    "centered": true,
                }).then(async v => {
                    if(v) {
                      try {
                          this.loading(true);
                          await API.fn_capnhat_kieu_tt(this.axios, input).then((response) => {
                            console.log(response.data);
                            this.loading(false);
                            if(response.data.error_code && response.data.error_code === this.success_code && response.data.data !="Kiểu thuộc tính bị trùng!") {
                              this.LoadKieuThuocTinh();
                              this.SetButton(0);
                              this.loading(false);
                              this.$refs.grid.setCurrentSelectedRow(this.index);
                              this.ShowAlert("Thêm mới thuộc tính thành công",0);
                            }
                            else
                            {
                              this.ShowAlert("Thêm mới thuộc tính không thành công: "+ response.data.data,2);
                            }
                          }
                          ); 

                          /* this.GetData(await API.fn_capnhat_kieu_tt(this.axios, input));
                          this.LoadKieuThuocTinh();
                          this.SetButton(0);
                          this.loading(false);
                          this.$refs.grid.setCurrentSelectedRow(this.index);
                          this.ShowAlert("Thêm mới thuộc tính thành công",0); */
                        } catch (ex) {
                          this.loading(false);
                          this.ShowAlert("Thêm mới thuộc tính không thành công",2);
                        }
                    }
                });

      
    },
    btnHuy()
    {
      this.SetButton(0);
    },
    btnXoa: async function ()
    {
      if (this.listbox.ds_kieuthuoctinh.value.kieutt_id =="" || this.listbox.ds_kieuthuoctinh.value.kieutt_id==null)
      {
        this.ShowAlert("Bạn chưa chọn thuộc tính để xóa",0);
        return;
      }
      this.$bvModal.msgBoxConfirm('Bạn thật sự muốn xoá thuộc tính này không?', {
      title: '', size: 'sm', okTitle: 'Đồng ý',cancelTitle: 'Hủy', "centered": true,}).then(async v => {
      if(v) {
        var input = {};
        input.kieutt_id = this.listbox.ds_kieuthuoctinh.value.kieutt_id;
        input.kieu = 2;
        console.log(input);
        try {
            this.loading(true);
            this.GetData(await API.fn_capnhat_kieu_tt(this.axios, input));
            this.LoadKieuThuocTinh();
            this.SetButton(0);
            this.loading(false);
            this.ShowAlert("Xóa thuộc tính thành công",0);
          } catch (ex) {
            this.loading(false);
            this.ShowAlert("Xóa thuộc tính không thành công",2);
          }
      }
      });
    },
    

  },
};

</script>
