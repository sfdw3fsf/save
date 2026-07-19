<template src="./AssignSuppliesToTitleDepartment.html"></template>
<style src="./AssignSuppliesToTitleDepartment.scss"></style>
<script>
import breadcrumb from '@/components/breadcrumb'
import VueFlatPickr from 'vue-flatpickr-component';
import API from './API'
import gridview from '@/components/Shared/gridview'
import treemenu from './treemenu'
import { DataManager, Query, ODataAdaptor, Predicate } from '@syncfusion/ej2-data'
import { GridPlugin, Edit  } from "@syncfusion/ej2-vue-grids";
import DataGridCustom from "./components/DataGrid";
//CSS
import '@/assets/vendor/bootstrap-table/bootstrap-table.min.css';
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.css';
//JS
import '@/assets/vendor/jquery/split'
import '@/assets/vendor/bootstrap-table/bootstrap-table.min'
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.min'
//
export default {
  components: {breadcrumb,VueFlatPickr,gridview,treemenu,API,DataManager, Query, ODataAdaptor, Predicate,DataGridCustom },
  name: 'AssignSuppliesToTitleDepartment',
  mounted () {
    this.LoadNhomVT();
    this.LoadDonVi();
    this.init();

  },
  data () {
    return {
      editSettings: { allowEditing: true, allowAdding: false, allowDeleting: false, mode: 'Batch'  },
      checkedThuocTinhDG :[],
      checkedThuocTinhCG :[],
      dsThuocTinhDaGan: [],
      isPageChange: false,
      model: {},
      dateconfig: {
        altFormat: 'd/m/Y H:i:S',
        altInput: true,
        dateFormat: 'd/m/Y H:i:S',
        allowInput:true,
      },
      ngay_ht: new Date(),
      header: {
        title: 'GÁN LOẠI VẬT TƯ - CHỨC DANH, ĐƠN VỊ',
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

      isCapNhat:false,
      isCachTim: false,

      listbox: {
        NhomVT: {
          list: [],
          value: {NHOMVT_ID: "",NHOM_VT: ""},
        },
        NhomLVT: {
          list: [],
          value: {loaivt_id: "",loai_vt: ""},
        },
        LVT: {
          list: [],
          value: {loaivt_id: "",loai_vt: ""},
        },
        DonVi: {
          list: [],
          value: {DONVI_ID: "",TEN_DV: ""},
        },
        DonVi1: {
          list: [],
          value: {DONVI_ID: "",TEN_DV: ""},
        },

        ds_donvidagan: {
          list: [],
          header: [ {fieldName: 'ten_dv', headerText: 'Đơn vị', allowFiltering: true},
                    {fieldName: 'dinhmuc', headerText: 'Định mức', allowFiltering: true ,allowEditing:true},
                       ],
          value: {},
          checked: []
        },

        ds_donvichuagan:{
           list:[],
           value:"",
           isEnabled: false,
           checked: []
        },
        fields: {
          dataSource: null,
          id: 'donvi_id',
          parentID: 'donvi_cha_id',
          text: 'ten_dv',
          hasChildren: 'hasChild'
        },
        DsKhuVuc: [],
        dsdoituong_all: [],
        ds_nvdagan: {
          list: [],
          header: [ {fieldName: 'ten_nv', headerText: 'Nhân viên', allowFiltering: true },
                    {fieldName: 'ten_dv', headerText: 'Đơn vị', allowFiltering: true},
                    {fieldName: 'dinhmuc', headerText: 'Định mức', allowFiltering: true ,customAttributes : {class: 'red'},allowEditing:true},

                       ],
          value: {},
          checked: []
        },
        ds_nvchuagan: {
          list: [],
          header: [ {fieldName: 'ten_nv', headerText: 'Nhân viên', allowFiltering: true},
                    {fieldName: 'ten_dv', headerText: 'Đơn vị', allowFiltering: true},
                    {fieldName: 'dinhmuc', headerText: 'Định mức', allowFiltering: true,customAttributes : {class: 'red'}},
                       ],
          value: {},
          checked: []
        },

        ds_chucdanhdagan: {
          list: [],
          header: [ {fieldName: 'ten_vt', headerText: 'Chức danh',allowSorting : true,allowFiltering: true},
                    {fieldName: 'dinhmuc', headerText: 'Định mức',allowSorting : true,allowFiltering: true, allowEditing:true
                    ,customAttributes : {class: 'red'}},
                    {
            fieldName: "batbuoc",
            headerText: "Bắt buộc",
            width: '100',
            allowSorting : false,
            allowFiltering: false,
            textAlign : "Center",
            //headerTemplate : this.getCheckboxHeaderTemplate(this),
            filterTemplate : this.getCheckboxHeaderTemplate(this),            
            template: this.getCheckboxTemplate(this, 'batbuoc', 'thuocTinhDaGan_CD'),
           
          },
                       ],
          value: {},
          checked: []
        },
        ds_chucdanhchuagan: {
          list: [],
          header: [ {fieldName: 'ten_vt', headerText: 'Chức danh', allowFiltering: true},
                    {fieldName: 'dinhmuc', headerText: 'Định mức', allowFiltering: true,customAttributes : {class: 'red'}},
                    {
            fieldName: "batbuoc",
            headerText: "Bắt buộc",
            width: '100',   
            allowSorting : false,
            allowFiltering: false,
            textAlign : "Center",
            filterTemplate : this.getCheckboxHeaderTemplateCG(this),
            //headerTemplate : this.getCheckboxHeaderTemplateCG(this),         
            template: this.getCheckboxTemplate(this, 'batbuoc', 'thuocTinhDaGan_CC'),
          },
                       ],
          value: {},
          checked: []
        },
      },
      dateConfig: {
        altInput: true,
        dateFormat: 'd/m/Y',
        altFormat: 'd/m/Y'
      },
      tungay: new Date(),
      denngay: new Date(),
      DK_tungay: new Date(),
      DK_denngay: new Date(),
      ngaytaogoi: new Date(),
      denngay_cu: new Date(),

      txtTenVatTu:"",

      success_code: "BSS-00000000",
      ThoiDiemTT: "",
      NgayTTTu: "0",
      NgayTTDen: "0",
      TyLe: "0",
      TYLE_NQH_ID: "",
      TabIndex: 0,

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
    customiseCell: function(args) {
      console.log(args) 
        if (args.column.field === 'dinhmuc') {
          console.log(args)            
                args.cell.classList.add('txtcolor');            
            }       
    },  
    async init() {
      try {
        $("#chb_chonnhanh").prop('checked', false);
        $("#chb_chonnhanh_cd").prop('checked', false);
        $("#chb_chonnhanh_cc").prop('checked', false);
        $("#chb_chonnhanh_nd").prop('checked', false);
        $("#chb_chonnhanh_nc").prop('checked', false);
      } catch (e) {
      }
    },
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
    ItemsDonViDaGan_click: function (dataKeys) {
      console.log(dataKeys)
      this.listbox.ds_donvidagan.checked = dataKeys
      this.listbox.ds_donvidagan.list.forEach(function (item) {
        if (dataKeys.filter(x => x === item.donvi_id).length > 0) {
          item.ischecked = true
        } else {
          item.ischecked = false
        }
      })
    },

    ItemsViTriCVDaGan_click: function (dataKeys) {
      this.listbox.ds_chucdanhdagan.checked = dataKeys
      this.listbox.ds_chucdanhdagan.list.forEach(function (item) {
        if (dataKeys.filter(x => x === item.vitri_id).length > 0) {
          item.ischecked = true
        } else {
          item.ischecked = false
        }
      })
    },
    ItemsViTriCVChuaGan_click: function (dataKeys) {
      this.listbox.ds_chucdanhchuagan.checked = dataKeys
      this.listbox.ds_chucdanhchuagan.list.forEach(function (item) {
        if (dataKeys.filter(x => x === item.vitri_id).length > 0) {
          item.ischecked = true
        } else {
          item.ischecked = false
        }
      })
    },

    ItemsNVDaGan_click: function (dataKeys) {
      this.listbox.ds_nvdagan.checked = dataKeys
      this.listbox.ds_nvdagan.list.forEach(function (item) {
        if (dataKeys.filter(x => x === item.nhanvien_id).length > 0) {
          item.ischecked = true
        } else {
          item.ischecked = false
        }
      })
    },

    ItemsNVChuaGan_click: function (dataKeys) {
      this.listbox.ds_nvchuagan.checked = dataKeys
      this.listbox.ds_nvchuagan.list.forEach(function (item) {
        if (dataKeys.filter(x => x === item.nhanvien_id).length > 0) {
          item.ischecked = true
        } else {
          item.ischecked = false
        }
      })
    },

    GetData: function (response) {
      if (response.data.error === 200 || response.data.error === '200') {
        return response.data.data
      } else {
        console.log(response.data.error_code)
      }
      return []
    },

    LoadDonVi: async function ()
    {
      this.checkedThuocTinhCG = [];
      this.checkedThuocTinhDG = [];
      var input = {};
      this.loading(true);
      this.listbox.DonVi.list = this.GetData(await API.ADMIN_DONVI(this.axios, input)).filter(x=>x.DONVI_ID > 0).map(x=>({id:x.DONVI_ID,text:x.TEN_DV}));
      this.loading(false);
      this.listbox.DonVi1.list = this.listbox.DonVi.list;
      if (this.listbox.DonVi.list.length>0)
      {
        this.listbox.DonVi.value.DONVI_ID = this.listbox.DonVi.list[0].id;
        this.listbox.DonVi1.value.DONVI_ID = this.listbox.DonVi1.list[0].id;

      }
    },

    LoadNhomVT: async function ()
    {
      var input = {};
      this.checkedThuocTinhCG = [];
      this.checkedThuocTinhDG = [];
      this.loading(true);
      this.listbox.NhomVT.list = this.GetData(await API.QLVT_NHOM_VT(this.axios, input)).filter(x=>x.NHOMVT_ID > 0).map(x=>({id:x.NHOMVT_ID,text:x.NHOM_VT}));
      this.loading(false);
      if (this.listbox.NhomVT.list.length>0)
      {
        this.listbox.NhomVT.value.NHOMVT_ID = this.listbox.NhomVT.list[0].id;
        this.NhomVTChanged();
      }
    },

    NhomVTChanged: async function ()
    {
      var input = {};
      this.checkedThuocTinhCG = [];
      this.checkedThuocTinhDG = [];
      input.nhomvt_id = this.listbox.NhomVT.value.NHOMVT_ID;
      this.loading(true);
      this.listbox.NhomLVT.list = this.GetData(await API.sp_lay_ds_nhom_lvt(this.axios, input)).filter(x=>x.loaivt_id > 0).map(x=>({id:x.loaivt_id,text:x.loai_vt}));
      this.loading(false);
      if (this.listbox.NhomLVT.list.length>0)
      {
        this.listbox.NhomLVT.value.loaivt_id = this.listbox.NhomLVT.list[0].id;
        this.NhomLVTChanged();
      }
    },

    NhomLVTChanged: async function ()
    {
      var input = {};
      this.checkedThuocTinhCG = [];
      this.checkedThuocTinhDG = [];
      input.loaivt_cha_id = this.listbox.NhomLVT.value.loaivt_id;
      input.nhomvt_id = this.listbox.NhomVT.value.NHOMVT_ID;
      this.loading(true);
      this.listbox.LVT.list = this.GetData(await API.sp_lay_ds_loai_vt(this.axios, input)).filter(x=>x.loaivt_id > 0).map(x=>({id:x.loaivt_id,text:x.loai_vt}));
      this.loading(false);
      if (this.listbox.LVT.list.length>0)
      {
        this.listbox.LVT.value.loaivt_id = this.listbox.LVT.list[0].id;
        this.LVTChanged();
      }
    },

    LoadNVDaGan: async function ()
    {
      this.checkedThuocTinhCG = [];
      this.checkedThuocTinhDG = [];
      var input = {};
      if (this.listbox.DonVi.value.DONVI_ID != "")
      {
        input.donvi_id = this.listbox.DonVi.value.DONVI_ID;
        input.kieu = 5;
        input.loaivt_id = this.listbox.LVT.value.loaivt_id;
        console.log(input);
        this.loading(true);
        this.listbox.ds_nvdagan.list = this.GetData(await API.sp_lay_ds_ccdv(this.axios, input));
        this.loading(false);
        if (this.listbox.ds_nvdagan.list.length>0)
        {
          this.listbox.ds_nvdagan.value.DONVI_ID = this.listbox.ds_nvdagan.list[0].id;
        }
      }
    },

    LoadNVChuaGan: async function ()
    {
      if (this.listbox.DonVi1.value.DONVI_ID != "")
      {
        this.checkedThuocTinhCG = [];
      this.checkedThuocTinhDG = [];
        var input = {};
        input.donvi_id = this.listbox.DonVi1.value.DONVI_ID;
        input.kieu = 4;
        input.loaivt_id = this.listbox.LVT.value.loaivt_id;
        this.loading(true);
        this.listbox.ds_nvchuagan.list = this.GetData(await API.sp_lay_ds_ccdv(this.axios, input));
        this.loading(false);
      }
    },

    DVDaGanChanged: async function ()
    {
      var input = {};
      if (this.listbox.DonVi.value.DONVI_ID != "")
      {
        this.checkedThuocTinhCG = [];
      this.checkedThuocTinhDG = [];
        input.donvi_id = this.listbox.DonVi.value.DONVI_ID;
        input.kieu = 5;
        input.loaivt_id = this.listbox.LVT.value.loaivt_id;
        this.loading(true);
        this.listbox.ds_nvdagan.list = this.GetData(await API.sp_lay_ds_ccdv(this.axios, input));
        this.init();
        this.loading(false);
      }
    },

    DVChuaGanChanged: async function ()
    {
      this.checkedThuocTinhCG = [];
      this.checkedThuocTinhDG = [];
      if (this.listbox.DonVi1.value.DONVI_ID != "")
      {
        var input = {};
        input.donvi_id = this.listbox.DonVi1.value.DONVI_ID;
        input.kieu = 4;
        input.loaivt_id = this.listbox.LVT.value.loaivt_id;
        this.loading(true);
        this.listbox.ds_nvchuagan.list = this.GetData(await API.sp_lay_ds_ccdv(this.axios, input));
        this.init();
        this.loading(false);
      }
    },

    LoadChucDanhDaGan: async function ()
    {
      this.checkedThuocTinhCG = [];
      this.checkedThuocTinhDG = [];
      var input = {};
      input.donvi_id = 0;
      input.kieu = 3;
      input.loaivt_id = this.listbox.LVT.value.loaivt_id;
      //console.log(input);
      this.loading(true);
      this.listbox.ds_chucdanhdagan.list = this.GetData(await API.sp_lay_ds_ccdv(this.axios, input))
      //this.listbox.ds_chucdanhdagan.list = this.listbox.ds_chucdanhdagan.list.map(x=>({batbuoc:1}));
      //console.log(this.listbox.ds_chucdanhdagan.list);
      this.loading(false);
    },

    LoadChucDanhChuaGan: async function ()
    {
      this.checkedThuocTinhCG = [];
      this.checkedThuocTinhDG = [];
      var input = {};
      input.donvi_id = 0;
      input.kieu = 2;
      input.loaivt_id = this.listbox.LVT.value.loaivt_id;
      this.loading(true);
      this.listbox.ds_chucdanhchuagan.list = this.GetData(await API.sp_lay_ds_ccdv(this.axios, input));
      this.loading(false);
      //console.log(this.listbox.ds_chucdanhchuagan.list)
    },

    LVTChanged: async function ()
    {
      if (this.TabIndex==0) //Đơn vị
      {
        this.onDVChanged();
      }
      else if (this.TabIndex==1) //vị trí công việc
      {
        this.onVTCVChange();
      }
      else if (this.TabIndex==2) //nhân viên
      {
        this.onNVChange();
      }
      this.checkedThuocTinhCG = [];
      this.checkedThuocTinhDG = [];
    },

    onDVChanged: async function ()
    {
      this.checkedThuocTinhCG = [];
      this.checkedThuocTinhDG = [];
      //Lấy ds đơn vị đã gán
      await this.LoadDVDaGan();
      //Lấy ds đơn vị chưa gán
      await this.refreshTree(0);
    },

    onVTCVChange: async function ()
    {
      this.checkedThuocTinhCG = [];
      this.checkedThuocTinhDG = [];
      //lấy danh sách chức danh đã gán vào gridview
      await this.LoadChucDanhDaGan();
      //lấy danh sách chức danh chưa gán vào gridview
      await this.LoadChucDanhChuaGan();
    },
    onNVChange: async function ()
    {
      this.checkedThuocTinhCG = [];
      this.checkedThuocTinhDG = [];
      //lấy ds nhân viên đã gán vào gridview
      await this.LoadNVDaGan();
       //lấy ds nhân viên chưa gán vào gridview
      await this.LoadNVChuaGan();
    },

    LoadDVDaGan: async function ()
    {
      this.checkedThuocTinhCG = [];
      this.checkedThuocTinhDG = [];
      var input = {};
      input.donvi_id = 0;
      input.kieu = 1;
      input.loaivt_id = this.listbox.LVT.value.loaivt_id;
      //console.log(input);
      var self = this
      try {
        this.loading(true);
        this.listbox.ds_donvidagan.list = this.GetData(await API.sp_lay_ds_ccdv(this.axios, input));
        this.init();
        this.loading(false);
      } catch (ex) {
      }
    },

    refreshTree: async function (val) {
      //var input = {kieugoiId: this.KieuGoiId}
      var input = {};
      input.donvi_id = 0;
      input.kieu = 0;
      input.loaivt_id = this.listbox.LVT.value.loaivt_id;
      //console.log(input);
      var self = this
      if (val==0)
      {
        try {
          console.log(input);
          this.loading(true);
          this.listbox.ds_donvichuagan.list = this.GetData(await API.sp_lay_ds_ccdv(this.axios, input));
          this.loading(false);
        } catch (ex) {
          this.loading(false);
        }
      }
      var data = []
      self.listbox.ds_donvichuagan.list.forEach(function (item) {
        var newItem = {
          donvi_cha_id: item.donvi_cha_id == '900' ? null : item.donvi_cha_id,
          donvi_id: item.donvi_id,
          ten_dv: item.ten_dv,
          unitlevel: item.unitlevel
        }
        var child = self.listbox.ds_donvichuagan.list.filter(x => x.donvi_cha_id === item.donvi_id)
        let count = child.length
        if (count > 1) {
          newItem.hasChild = true
          newItem.expanded = true
          newItem.count = count
          // newItem.child = child
          if (newItem.donvi_cha_id === 0 || newItem.unitlevel <= 2) {
            newItem.expanded = true
          } else {
            newItem.expanded = false
          }
        }
        if (data.filter(x => x.donvi_id === newItem.donvi_id).length === 0) {
          data.push(newItem)
        }
      })
      this.TreeData = data.sort((a, b) => a.ten_dv.localeCompare(b.ten_dv))
      this.listbox.fields = {
        dataSource: this.TreeData,
        id: 'donvi_id',
        parentID: 'donvi_cha_id',
        text: 'ten_dv',
        hasChildren: 'hasChild'
      }
    },

    nodeChecked: function(args) {
       var treeObj = document.getElementById('treeview').ej2_instances[0];
      this.listbox.ds_donvichuagan.checked = [];
       for (var item of treeObj.checkedNodes)
       {
         var temps = this.listbox.ds_donvichuagan.list.filter(x => x.donvi_id == item);
         if (temps.length>0)
         {
            for (var temp of temps)
            {
              this.listbox.ds_donvichuagan.checked.push(temp);
            }
         }
       }
    },
    async btnNhapMoi()
    {
      this.listbox.ds_donvidagan.list = [];
      this.listbox.ds_donvichuagan.list = [];
      this.listbox.ds_nvdagan.list = [];
      this.listbox.ds_nvchuagan.list = [];
      this.listbox.ds_chucdanhdagan.list = [];
      this.listbox.ds_chucdanhchuagan.list = [];
      this.listbox.LVT.list = [];
      this.listbox.LVT.value = [];
      this.listbox.NhomVT.list = [];
      this.listbox.NhomLVT.list = [];
      this.listbox.DonVi.list = [];
      this.changeDataSource([]);
      this.listbox.ds_donvichuagan.checked = [];
      this.init();
      await this.LoadDonVi();
      // if (this.TabIndex==0) //Đơn vị
      // {
      //   this.onDVChanged();
      // }
      //else
      if (this.TabIndex==1) //vị trí công việc
      {
        this.onVTCVChange();
      }
      else if (this.TabIndex==2) //nhân viên
      {
        this.onNVChange();
      }
      this.LoadNhomVT();
      //this.LoadDonVi();
      //this.LVTChanged();
    },

    btnGoGan: async function ()
    {
      if (this.checkedThuocTinhDG.length==0)
      {
        this.ShowAlert("Bạn chưa chọn đơn vị để gỡ gán",2);
        return;
      }
      var dsChucDanh = [];
      for (var temp of this.checkedThuocTinhDG)
      {
        this.listbox.ds_donvichuagan.list.push(temp);
        dsChucDanh.push(temp);
      }
      dsChucDanh = dsChucDanh.map(x=>({DVID:x.donvi_id,LVL:x.ten_dv}));
      var input = {};
      input.vloaivt_id = this.listbox.LVT.value.loaivt_id;
      input.vkieu = 9;
      input.vdata = JSON.stringify(dsChucDanh);
      console.log(input);
      try {
        this.loading(true);
        //this.GetData(await API.ganall_ccdc(this.axios, input));
        this.loading(false);
        this.ShowAlert("Gỡ gán đơn vị thành công",0);
        //Chuyển item sang phải
        var temps = this.listbox.ds_donvidagan.list.filter(a => !this.checkedThuocTinhDG.includes(a));
        this.listbox.ds_donvidagan.list = [];
        this.listbox.ds_donvidagan.list = temps;
        this.checkedThuocTinhDG = [];
        this.refreshTree(1);
      } catch (ex) {
        this.loading(false);
        this.ShowAlert("Gỡ gán đơn vị không thành công",2);
      }
    },

    btnGan: async function ()
    {
      if (this.listbox.ds_donvichuagan.checked.length==0)
      {
        this.ShowAlert("Bạn chưa chọn đơn vị để gán",2);
        return;
      }
      var dsChucDanh = [];
      for (var temp of this.listbox.ds_donvichuagan.checked)
      {
        temp.dinhmuc = 1;
        this.listbox.ds_donvidagan.list.push(temp);
        dsChucDanh.push(temp);
      }
      dsChucDanh = dsChucDanh.map(x=>({DVID:x.donvi_id,LVL:x.ten_dv}));
      var input = {};
      input.vloaivt_id = this.listbox.LVT.value.loaivt_id;
      input.vkieu = 7;
      input.vdata = JSON.stringify(dsChucDanh);
      //console.log(input);
      try {
        this.loading(true);
        //this.GetData(await API.ganall_ccdc(this.axios, input));
        this.loading(false);
        //Chuyển item sang trái
        var temps = this.listbox.ds_donvichuagan.list.filter(a => !this.listbox.ds_donvichuagan.checked.includes(a));
        this.listbox.ds_donvichuagan.list = [];
        this.listbox.ds_donvichuagan.list = temps;
        this.refreshTree(1);
        this.ShowAlert("Gán đơn vị thành công",0);
      } catch (ex) {
        this.loading(false);
        this.ShowAlert("Gán đơn vị không thành công",2);
      }
    },

    btnGoGan_CD: async function ()
    {
      if (this.checkedThuocTinhDG.length==0)
      {
        this.ShowAlert("Bạn chưa chọn vị trí công việc để gỡ gán",2);
        return;
      }
      var dsChucDanh = [];
      for (var temp of this.checkedThuocTinhDG)
      {
        this.listbox.ds_chucdanhchuagan.list.push(temp);
        dsChucDanh.push(temp);
      }
      dsChucDanh = dsChucDanh.map(x=>({VTID:x.vitri_id,LVL:x.ten_vt}));
      var input = {};
      input.vloaivt_id = this.listbox.LVT.value.loaivt_id;
      input.vkieu = 3;
      input.vdata = JSON.stringify(dsChucDanh);
      console.log(input);
      try {
        this.loading(true);
        //this.GetData(await API.ganall_ccdc(this.axios, input));
        this.loading(false);
        this.ShowAlert("Gỡ gán vị trí công việc thành công",0);
        //Chuyển item sang phải
        var temps = this.listbox.ds_chucdanhdagan.list.filter(a => !this.checkedThuocTinhDG.includes(a));
        this.listbox.ds_chucdanhdagan.list = [];
        this.listbox.ds_chucdanhdagan.list = temps;
        this.checkedThuocTinhDG = [];
      } catch (ex) {
        this.loading(false);
        this.ShowAlert("Gỡ gán vị trí công việc không thành công",2);
      }
    },
    btnGan_CD: async function ()
    {
      if (this.checkedThuocTinhCG.length==0)
      {
        this.ShowAlert("Bạn chưa chọn vị trí công việc để gán",2);
        return;
      }
      var dsChucDanh = [];
      for (var temp of this.checkedThuocTinhCG)
      {
        this.listbox.ds_chucdanhdagan.list.push(temp);
        dsChucDanh.push(temp);
      }
      dsChucDanh = dsChucDanh.map(x=>({VTID:x.vitri_id,LVL:x.ten_vt,BATBUOC:x.batbuoc}));
      var input = {};
      input.vloaivt_id = this.listbox.LVT.value.loaivt_id;
      input.vkieu = 1;
      input.vdata = JSON.stringify(dsChucDanh);
      console.log(input);
      try {
        this.loading(true);
        //this.GetData(await API.ganall_ccdc(this.axios, input));
        this.loading(false);
        this.ShowAlert("Gán vị trí công việc thành công",0);
        //Chuyển item sang trái
        var temps = this.listbox.ds_chucdanhchuagan.list.filter(a => !this.checkedThuocTinhCG.includes(a));
        this.listbox.ds_chucdanhchuagan.list = [];
        this.listbox.ds_chucdanhchuagan.list = temps;
        this.checkedThuocTinhCG = [];
      } catch (ex) {
        this.loading(false);
        this.ShowAlert("Gán vị trí công việc không thành công",2);
      }
    },

    btnGoGan_NV: async function ()
    {
      let changes = this.$refs.thuocTinhDaGan_ND.getBatchChanges().changedRecords
      if(changes.length>0){
        for(let i=0;i<changes.length;i++){
            let change=changes[i]
            const index= this.checkedThuocTinhDG.findIndex(x=>x.nhanvien_id==change.nhanvien_id)
            if(index>-1){
              if(change.dinhmuc > 1000){
                this.listbox.ds_nvdagan.list[index].dinhmuc='999'
              }else if(change.dinhmuc < 1){
                this.listbox.ds_nvdagan.list[index].dinhmuc='1'
              }else{
                this.listbox.ds_nvdagan.list[index].dinhmuc=change.dinhmuc
              }
                
            }
        }
      }
      if (this.checkedThuocTinhDG.length==0)
      {
        this.ShowAlert("Bạn chưa chọn nhân viên để gỡ gán",2);
        return;
      }
      var dsChucDanh = [];
      for (var temp of this.checkedThuocTinhDG)
      {
        this.listbox.ds_nvchuagan.list.push(temp);
        dsChucDanh.push(temp);
      }
      dsChucDanh = dsChucDanh.map(x=>({NVID:x.nhanvien_id,LVL:x.dinhmuc}));
      var input = {};
      input.vloaivt_id = this.listbox.LVT.value.loaivt_id;
      input.vkieu = 6;
      input.vdata = JSON.stringify(dsChucDanh);
      console.log(input);
      try {
        this.loading(true);
        //this.GetData(await API.ganall_ccdc(this.axios, input));
        this.loading(false);
        this.ShowAlert("Gỡ gán nhân viên thành công",0);
        //Chuyển item sang trái
        var temps = this.listbox.ds_nvdagan.list.filter(a => !this.checkedThuocTinhDG.includes(a));
        this.listbox.ds_nvdagan.list = [];
        this.listbox.ds_nvdagan.list = temps;
        this.checkedThuocTinhDG = [];
      } catch (ex) {
        this.loading(false);
        this.ShowAlert("Gỡ gán nhân viên không thành công",2);
      }
    },
    btnGan_NV: async function ()
    {
      if (this.checkedThuocTinhCG.length==0)
      {
        this.ShowAlert("Bạn chưa chọn nhân viên để gán",2);
        return;
      }
      var dsChucDanh = [];
      for (var temp of this.checkedThuocTinhCG)
      {
        this.listbox.ds_nvdagan.list.push(temp);
        dsChucDanh.push(temp);
      }
      dsChucDanh = dsChucDanh.map(x=>({NVID:x.nhanvien_id,LVL:x.dinhmuc}));
      var input = {};
      input.vloaivt_id = this.listbox.LVT.value.loaivt_id;
      input.vkieu = 4;
      input.vdata = JSON.stringify(dsChucDanh);
      console.log(input);
      try {
        this.loading(true);
        //this.GetData(await API.ganall_ccdc(this.axios, input));
        this.loading(false);
        this.ShowAlert("Gán nhân viên thành công",0);
        //Chuyển item sang trái
        var temps = this.listbox.ds_nvchuagan.list.filter(a => !this.checkedThuocTinhCG.includes(a));
        this.listbox.ds_nvchuagan.list = [];
        this.listbox.ds_nvchuagan.list = temps;
        this.checkedThuocTinhCG = [];
      } catch (ex) {
        this.loading(false);
        this.ShowAlert("Gán nhân viên không thành công",2);
      }
    },

    btnGhiLai()
    {
      if (this.TabIndex==0) //Đơn vị
      {
        if (this.listbox.ds_donvichuagan.list.length>0 || this.listbox.ds_donvidagan.list.length>0)
        {
          this.$bvModal.msgBoxConfirm('Bạn có muốn cập nhật lại thông tin loại vật tư đơn vị không?', {
                title: 'Xác nhận cập nhật', size: 'md', okTitle: 'Đồng ý', centered: true, cancelTitle: 'Không đồng ý',}).then(async v => {
                if(v) {
                  let changes = this.$refs.thuocTinhDaGan.getBatchChanges().changedRecords
                  if(changes.length>0){
                    for(let i=0;i<changes.length;i++){
                        let change=changes[i]
                        const index=this.listbox.ds_donvidagan.list.findIndex(x=>x.donvi_id==change.donvi_id)
                        if(index>-1){
                          if(change.dinhmuc > 1000){
                            this.listbox.ds_donvidagan.list[index].dinhmuc='999'
                          }else if(change.dinhmuc < 1){
                            this.listbox.ds_donvidagan.list[index].dinhmuc='1'
                          }else{
                            this.listbox.ds_donvidagan.list[index].dinhmuc=change.dinhmuc
                          }
                        }
                    }
                  }
                  var dsChucDanh = [];
                  var dsChucDanhDG = [];
                  dsChucDanhDG = this.listbox.ds_donvidagan.list.map(x=>({DVID:x.donvi_id,LVL:x.dinhmuc}));
                  dsChucDanh = this.listbox.ds_donvichuagan.list.map(x=>({DVID:x.donvi_id,LVL:x.dinhmuc}));
                  var input = {};
                  var inputDG = {};
                  var inputDG2 = {};
                  inputDG2.vloaivt_id = this.listbox.LVT.value.loaivt_id;
                  inputDG2.vkieu = 7;
                  inputDG2.vdata = JSON.stringify(dsChucDanhDG);
                  inputDG.vloaivt_id = this.listbox.LVT.value.loaivt_id;
                  inputDG.vkieu = 8;
                  inputDG.vdata = JSON.stringify(dsChucDanhDG);
                  console.log(inputDG,inputDG.vdata);
                  input.vloaivt_id = this.listbox.LVT.value.loaivt_id;
                  input.vkieu = 9;
                  input.vdata = JSON.stringify(dsChucDanh);
                  console.log(input,input.vdata);
                  try {
                    this.loading(true);
                    this.GetData(await API.ganall_ccdc(this.axios, inputDG2));
                    this.GetData(await API.ganall_ccdc(this.axios, input));
                    this.GetData(await API.ganall_ccdc(this.axios, inputDG));
                    this.loading(false);
                    //Lấy ds đơn vị đã gán
                    await this.LoadDVDaGan();
                    //Lấy ds đơn vị chưa gán
                    await this.refreshTree(0);
                    this.ShowAlert("Cập nhât đơn vị thành công",0);
                  } catch (ex) {
                    this.loading(false);
                    this.ShowAlert("Cập nhật đơn vị không thành công",2);
                  }
                }
          });
        }
      }
      else if (this.TabIndex==1) //Vị trí công việc
      {
        if (this.listbox.ds_chucdanhdagan.list.length>0 || this.listbox.ds_chucdanhchuagan.list.length>0)
        {
          this.$bvModal.msgBoxConfirm('Bạn có muốn cập nhật lại thông tin loại vật tư chức danh không?', {
                title: 'Xác nhận cập nhật', size: 'md',centered: true, okTitle: 'Đồng ý',cancelTitle: 'Không đồng ý',}).then(async v => {
                if(v) {
                  let changes = this.$refs.thuocTinhDaGan_CD.getBatchChanges().changedRecords
                  if(changes.length>0){
                    for(let i=0;i<changes.length;i++){
                        let change=changes[i]
                        const index = this.listbox.ds_chucdanhdagan.list.findIndex(x=>x.vitri_id==change.vitri_id)
                        if(index>-1){
                          if(change.dinhmuc > 999){
                            this.listbox.ds_chucdanhdagan.list[index].dinhmuc='999'
                          }else if(change.dinhmuc < 1){
                            this.listbox.ds_chucdanhdagan.list[index].dinhmuc='1'
                          }else{
                            this.listbox.ds_chucdanhdagan.list[index].dinhmuc=change.dinhmuc
                          }
                        }
                    }
                  }
                  for(let i =0 ; i< this.listbox.ds_chucdanhdagan.list.length ;i++){
                    this.listbox.ds_chucdanhdagan.list[i].batbuoc = this.listbox.ds_chucdanhdagan.list[i].batbuoc == "1" ? "s1" : "s0";
                  }
                  for(let j =0 ; j< this.listbox.ds_chucdanhchuagan.list.length ;j++){
                    this.listbox.ds_chucdanhchuagan.list[j].batbuoc = this.listbox.ds_chucdanhchuagan.list[j].batbuoc == "1" ? "s1" : "s0";
                  }
                  var dsChucDanh = [];
                  var dsChucDanhCG = [];
                  console.log(this.listbox.ds_chucdanhdagan.list);
                  dsChucDanhCG = this.listbox.ds_chucdanhchuagan.list.map(x=>({VTID:x.vitri_id,LVL:x.dinhmuc,BATBUOC:x.batbuoc}));
                  dsChucDanh = this.listbox.ds_chucdanhdagan.list.map(x=>({VTID:x.vitri_id,LVL:x.dinhmuc,BATBUOC:x.batbuoc}));
                  var input = {};
                  var inputDG = {};
                  var inputCG = {};
                  inputDG.vloaivt_id = this.listbox.LVT.value.loaivt_id;
                  inputDG.vkieu = 1;
                  inputDG.vdata = JSON.stringify(dsChucDanh);
                  input.vloaivt_id = this.listbox.LVT.value.loaivt_id;
                  input.vkieu = 1;
                  input.vdata = JSON.stringify(dsChucDanh);
                  inputCG.vloaivt_id = this.listbox.LVT.value.loaivt_id;
                  inputCG.vkieu = 3;
                  inputCG.vdata = JSON.stringify(dsChucDanhCG);
                  console.log(input);
                  try {
                    this.loading(true);
                    this.GetData(await API.ganall_ccdc(this.axios, inputDG));
                    this.GetData(await API.ganall_ccdc(this.axios, input));
                    this.GetData(await API.ganall_ccdc(this.axios, inputCG));
                    this.loading(false);
                    //lấy danh sách chức danh đã gán vào gridview
                    await this.LoadChucDanhDaGan();
                    //lấy danh sách chức danh chưa gán vào gridview
                    await this.LoadChucDanhChuaGan();
                    this.ShowAlert("Cập nhât vật tư chức danh thành công",0);
                  } catch (ex) {
                    this.loading(false);
                    this.ShowAlert("Cập nhật vật tư chức danh không thành công",2);
                  }
          }
          });
        }
      }
      else if (this.TabIndex==2) //Nhân viên
      {
        if (this.listbox.ds_nvdagan.list.length>0 || this.listbox.ds_nvchuagan.list.length>0)
        {
          this.$bvModal.msgBoxConfirm('Bạn có muốn cập nhật lại thông tin loại vật tư nhân viên không?', {
                title: 'Xác nhận cập nhật', size: 'md',centered: true, okTitle: 'Đồng ý',cancelTitle: 'Không đồng ý',}).then(async v => {
                if(v) {
                  let changes = this.$refs.thuocTinhDaGan_ND.getBatchChanges().changedRecords
                  if(changes.length>0){
                    for(let i=0;i<changes.length;i++){
                        let change=changes[i]
                        const index= this.listbox.ds_nvdagan.list.findIndex(x=>x.nhanvien_id==change.nhanvien_id)
                        if(index>-1){
                          if(change.dinhmuc > 1000){
                            this.listbox.ds_nvdagan.list[index].dinhmuc='999'
                          }else if(change.dinhmuc < 1){
                            this.listbox.ds_nvdagan.list[index].dinhmuc='1'
                          }else{
                            this.listbox.ds_nvdagan.list[index].dinhmuc=change.dinhmuc
                          }
                            
                        }
                    }
                  }
                  var dsChucDanh = [];
                  var dsChucDanhCG = [];
                  dsChucDanh = this.listbox.ds_nvdagan.list.map(x=>({NVID:x.nhanvien_id,LVL:x.dinhmuc}));
                  dsChucDanhCG = this.listbox.ds_nvchuagan.list.map(x=>({NVID:x.nhanvien_id,LVL:x.dinhmuc}));
                  var input = {};
                  var inputDG = {};
                  var inputCG = {};
                  inputDG.vloaivt_id = this.listbox.LVT.value.loaivt_id;
                  inputDG.vkieu = 4;
                  inputDG.vdata = JSON.stringify(dsChucDanh);
                  input.vloaivt_id = this.listbox.LVT.value.loaivt_id;
                  input.vkieu = 4;
                  input.vdata = JSON.stringify(dsChucDanh);
                  console.log(input);
                  inputCG.vloaivt_id = this.listbox.LVT.value.loaivt_id;
                  inputCG.vkieu = 6;
                  inputCG.vdata = JSON.stringify(dsChucDanhCG);
                  try {
                    this.loading(true);
                    this.GetData(await API.ganall_ccdc(this.axios, inputDG));
                    this.GetData(await API.ganall_ccdc(this.axios, input));
                    this.GetData(await API.ganall_ccdc(this.axios, inputCG));
                    this.loading(false);
                    //lấy ds nhân viên đã gán vào gridview
                    await this.LoadNVDaGan();
                    //lấy ds nhân viên chưa gán vào gridview
                    await this.LoadNVChuaGan();
                    this.ShowAlert("Cập nhât loại vật tư nhân viên thành công",0);
                  } catch (ex) {
                    this.loading(false);
                    this.ShowAlert("Cập nhật loại vật tư nhân viên không thành công",2);
                  }
          }
          });
        }
      }
    },

    //Search node tree
    searchNodes: function (args) {
      var _text = this.txtTenVatTu
      var seft = this
      // eslint-disable-next-line one-var
      var predicats = [], _array = [], _filter = []
      let treeObj = document.getElementById('treeview').ej2_instances[0]
      if (_text == '') {
        this.changeDataSource(seft.TreeData)
      } else {
        var predicate = new Predicate('ten_dv', 'contains', _text, true)
        var filteredList = new DataManager(seft.TreeData).executeLocal(new Query().where(predicate))
        console.log(`filteredList:${filteredList.length}`)
        console.log(filteredList)
        for (var j = 0; j < filteredList.length; j++) {
          _filter.push(filteredList[j]['donvi_id'])
          var filters = this.getFilterItems(filteredList[j], seft.TreeData)

          for (var i = 0; i < filters.length; i++) {
            if (_array.indexOf(filters[i]) == -1 && filters[i] != null) {
              _array.push(filters[i])
              predicats.push(new Predicate('donvi_id', 'equal', filters[i], false))
            }
          }
        }
        if (predicats.length == 0) {
          this.changeDataSource([])
        } else {
          var query = new Query().where(new Predicate.or(predicats))
          var newList = new DataManager(this.TreeData).executeLocal(query)
          seft.changeDataSource(newList)
          setTimeout(function () {
            treeObj.expandAll()
          }, 100)
        }
      }
    },

    getFilterItems: function (fList, list) {
      var nodes = []
      nodes.push(fList['donvi_id'])
      var query2 = new Query().where('donvi_id', 'equal', fList['donvi_cha_id'], false)
      var fList1 = new DataManager(list).executeLocal(query2)
      if (fList1.length != 0) {
        var pNode = this.getFilterItems(fList1[0], list)
        for (var i = 0; i < pNode.length; i++) {
          if (nodes.indexOf(pNode[i]) == -1 && pNode[i] != null) { nodes.push(pNode[i]) }
        }
        return nodes
      }
      return nodes
    },

    changeDataSource: function (data) {
      let treeObj = document.getElementById('treeview').ej2_instances[0]
      treeObj.fields = {
        dataSource: data, id: 'donvi_id', text: 'ten_dv', parentID: 'donvi_cha_id', hasChildren: 'hasChild'
      }
    },

    txtTenVatTuEnter()
    {
      this.searchNodes();
    },
    selectAll(){
      var treeObj = document.getElementById('treeview').ej2_instances[0];
      console.log(treeObj)
      this.listbox.ds_donvichuagan.checked = [];
       for (var item of treeObj.checkedNodes)
       {
         var temps = this.listbox.ds_donvichuagan.list;
         if (temps.length>0)
         {
            for (var temp of temps)
            {
              this.listbox.ds_donvichuagan.checked.push(temp);
            }
         }
       }
    },
    changeCheckBatbuoc(data){
            console.log('checkBatbuoc', data)
            const index=this.listbox.ds_chucdanhdagan.list.findIndex(x=>x.vitri_id==data.vitri_id)
            if(index>-1){
                this.listbox.ds_chucdanhdagan.list[index].batbuoc=data.batbuoc
            }
            console.log('ds_chucdanhdagan', this.listbox.ds_chucdanhdagan.list)
    },

    changeCheckBatbuocCG(data){
            console.log('checkBatbuoc', data)
            const index=this.listbox.ds_chucdanhchuagan.list.findIndex(x=>x.vitri_id==data.vitri_id)
            if(index>-1){
                this.listbox.ds_chucdanhchuagan.list[index].batbuoc=data.batbuoc
            }
            console.log('ds_chucdanhdagan', this.listbox.ds_chucdanhchuagan.list)
    },
    checkBoxnvdagan(parent,batbuoc){
    return function() {
      return {
        template : {
          template : `
              <div class="check-action">
                <ejs-checkbox :z-index="2000" v-model="checkBatbuoc" @change="changeCheck"></ejs-checkbox>
              </div>
            `,
          data(){
            return {
              parent : parent,
              checkBatbuoc :false,
              data : {},
            }
          },
          read : () =>{
          },
          mounted (){ 
            this.checkBatbuoc = batbuoc;      
            console.log(this.checkBatbuoc)     
          },
          methods :{
            changeCheck(){
                this.data.batbuoc = this.checkBatbuoc ? "1": "0"
                this.parent.changeCheckBatbuoc(this.data)
            }
          }
        }
      }
    }
  },
  checkBoxnvchuagan(parent,batbuoc){
    return function() {
      return {
        template : {
          template : `
              <div class="check-action">
                <ejs-checkbox :z-index="2000" v-model="checkBatbuoc" @change="changeCheck"></ejs-checkbox>
              </div>
            `,
          data(){
            return {
              parent : parent,
              checkBatbuoc :false,
              data : {},
            }
          },
          read : () =>{
          },
          mounted (){ 
            // this.checkBatbuoc = batbuoc;      
            // console.log(batbuoc)     
          },
          methods :{
            changeCheck(){
                this.data.batbuoc = this.checkBatbuoc ? "1": "0"
                this.parent.changeCheckBatbuocCG(this.data)
            }
          }
        }
      }
    }
  },
  DMdagan(parent){
    return function() {
      return {
        template : {
          template : `
          <span style='text-color:red'>
            {{this.parent()}}
          </span>
          `,
          data(){
            return {
              data : {},
            }
          },
          read : () =>{
          },
          mounted (){
          },
          methods :{            
          }
        }
      }
    }
  },
  GetDMCV(){
    let DM = this.$refs.gvDanhSachTTCT.getSelectedRecords()
    return DM.dinhmuc;
  },
  getCheckboxTemplate(parent, field, grid) {
      return function () {
        return {
          template: {
            template: `<div class="template_checkbox"> <input type="checkbox" 
            @change="change" :checked="data[field] == 1"> </div>`,
            data() {
              return {
                field: field
              };
            },
            methods: {
              change(event) {
                parent.$refs[grid].dataSource[this.data.index][field] = event.target.checked ? "1" : "0"
              }
            }
          },
        };
      };
    },
    getCheckboxHeaderTemplate(parent) {
      return function () {
        return {
          template: {
            template: `<div class="template_checkbox" style="width:15px;margin-left:44%">
                <input type="checkbox" @change="change($event)" v-model="rolesSelected" :indeterminate.prop="dataProperty">
              </div>`,
            data() {
              return {     
                parent : parent,
                check : 0 ,
                rolesSelected: '',
                dataProperty : true
              };
            },
           mounted() {    
            },
            methods: {
              change(event) {    
                if(this.check == 0 && this.rolesSelected == ""){
                  this.parent.HideBatBuoc();
                  this.dataProperty =  false; 
                  this.check = 1;
                }else if(this.check == 0 && this.rolesSelected != ""){
                  this.parent.Hide0BatBuoc();
                  this.dataProperty =  false;
                  this.check = 1;
                }else if(this.check == 1 && this.rolesSelected != ""){
                  this.parent.Hide0BatBuoc();
                  this.dataProperty =  false;
                  this.check = 2;
                }else if(this.check == 1 && this.rolesSelected == ""){
                  this.parent.HideBatBuoc();
                  this.dataProperty =  false;
                  this.check = 2;
                }else if(this.check == 2 ){
                  this.dataProperty =  true; 
                  this.parent.LoadChucDanhDaGan(); 
                  this.check = 0;     
                }
              }
            }
          },
        };
      };
    },
    async Hide0BatBuoc(){
      await this.LoadChucDanhDaGan();
      for(let i = 0 ;i < this.listbox.ds_chucdanhdagan.list.length ; i++){
        if(this.listbox.ds_chucdanhdagan.list[i].batbuoc == 0){
          this.listbox.ds_chucdanhdagan.list.splice(i, 1);          
          i--;
        }
      }      
    },
    async HideBatBuoc(){
      await this.LoadChucDanhDaGan();
      for(let j = 0 ;j < this.listbox.ds_chucdanhdagan.list.length ; j++){
        if(this.listbox.ds_chucdanhdagan.list[j].batbuoc == 1){
          this.listbox.ds_chucdanhdagan.list.splice(j, 1); 
          j--;
        }
      }      
    },
    getCheckboxHeaderTemplateCG(parent) {
      return function () {
        return {
          template: {
            template: `<div class="template_checkbox" style="width:15px;margin-left:44%">
                <input type="checkbox" @change="changeCG($event)" v-model="rolesSelectedCG" :indeterminate.prop="dataPropertyCG">
              </div>`,
            data() {
              return {     
                parent : parent,
                checkCG : 0 ,
                rolesSelectedCG: '',
                dataPropertyCG : true
              };
            },
           mounted() {    
            },
            methods: {
              async changeCG(event) {  
                console.log(this.rolesSelectedCG)  
                if(this.checkCG ==1 && this.rolesSelectedCG == ""){
                  this.parent.Hide0BatBuocCG();
                  this.dataPropertyCG =  false; 
                  this.checkCG = 2;
                }else if(this.checkCG ==0 && this.rolesSelectedCG != ""){
                  await this.parent.HideBatBuocCG();
                  this.dataPropertyCG =  false;
                  this.checkCG = 1;
                // }else if(this.checkCG == 1 && this.rolesSelectedCG != ""){
                //   await this.parent.Hide0BatBuocCG();
                //   this.dataPropertyCG =  false;
                //   this.checkCG = 2;
                // }else if(this.checkCG == 1 && this.rolesSelectedCG == ""){
                //   this.parent.HideBatBuocCG();
                //   this.dataPropertyCG =  false;
                //   this.checkCG = 2;
                // }else if(this.checkCG == 2 ){
                //   this.dataPropertyCG =  true; 
                //   this.parent.LoadChucDanhChuaGan(); 
                //   this.checkCG = 0;     
                }else if(this.checkCG == 2  && this.rolesSelectedCG != ""){
                  this.parent.Hide0BatBuocCG();
                  this.dataPropertyCG =  true; 
                  this.checkCG = 0;
                }

              }
            }
          },
        };
      };
    },
    async Hide0BatBuocCG(){
      this.LoadChucDanhChuaGan();
      // for(let i = 0 ;i < this.listbox.ds_chucdanhchuagan.list.length ; i++){
      //   if(this.listbox.ds_chucdanhchuagan.list[i].batbuoc == 0){
      //     this.listbox.ds_chucdanhchuagan.list.splice(i, 1);
      //     i--;
      //   }
      // }
         
    },
    async HideBatBuocCG(){
      this.listbox.ds_chucdanhchuagan.list.splice(0, this.listbox.ds_chucdanhchuagan.list.length);
      // await this.LoadChucDanhChuaGan();
      // for(let j = 0 ;j < this.listbox.ds_chucdanhchuagan.list.length ; j++){
      //   if(this.listbox.ds_chucdanhchuagan.list[j].batbuoc == 1){
      //     this.listbox.ds_chucdanhchuagan.list.splice(j, 1); 
      //     j--;
      //   }
      // }     
    },

  //
  rowSelected(data) {
      try {
        if (data.data.length == undefined) {
            this.checkedThuocTinhDG.push(data.data)
        } else {
            let check = document.getElementById("chb_chonnhanh")
            if (check.checked) {
                this.checkedThuocTinhDG = this.listbox.ds_donvidagan.list.slice()
            } else {
                for (let i=0; i<data.data.length; i++) {
                    let isExists = false
                    for (let ix=0; ix<this.checkedThuocTinhDG.length; ix++) {
                        if (data.data[i].donvi_id == this.checkedThuocTinhDG[ix].donvi_id) {
                            isExists = true;
                            break
                        }
                    }
                    if (!isExists) {
                        this.checkedThuocTinhDG.push(data.data[i])
                    }
                }
            }
        }
      } catch (err) {
        console.log(err);
      }
    },
    rowDeselected(data) {
        if (this.isPageChange) return
        if (data.data.length == undefined) {
            $("#chb_chonnhanh").prop('checked', false);
            let iRemove
            for (let i=0; i<this.checkedThuocTinhDG.length; i++) {
              if (this.checkedThuocTinhDG[i].donvi_id == data.data.donvi_id) {
                iRemove = i;
                break;
              }
            }
            this.checkedThuocTinhDG.splice(iRemove, 1)
        } else {
            if (data.data.length != this.checkedThuocTinhDG.length) {
                $("#chb_chonnhanh").prop('checked', false);
                this.onCheckbox()
                return
            }

            let check = document.getElementById("chb_chonnhanh")
            if (check.checked) {
                this.checkedThuocTinhDG = this.listbox.ds_donvidagan.list.slice()
            } else {
                for (let ix=0; ix<data.data.length; ix++) {
                    let iRemove = -1
                    for (let i=0; i<this.checkedThuocTinhDG.length; i++) {
                        if (this.checkedThuocTinhDG[i].donvi_id == data.data[ix].donvi_id) {
                            iRemove = i;
                            break;
                        }
                    }
                    this.checkedThuocTinhDG.splice(iRemove, 1)
                }
            }
        }
    },
    pageChanged() {
        this.isPageChange = true;
        let check = document.getElementById("chb_chonnhanh")
        if (check.checked) {
            this.checkedThuocTinhDG = this.listbox.ds_donvidagan.list.slice()
        }
        this.listbox.ds_donvidagan.list = [...this.listbox.ds_donvidagan.list]
    },
    actionComplete() {
        let lstSelect = []
        let pIndex = this.$refs.thuocTinhDaGan.$refs.pagination.pageIndexData
        let pSize = this.$refs.thuocTinhDaGan.$refs.pagination.pageSizeData
        for (let i=0; i<this.listbox.ds_donvidagan.list.length; i++) {
            for (let iS=0; iS<this.checkedThuocTinhDG.length; iS++) {
                if (this.listbox.ds_donvidagan.list[i].donvi_id == this.checkedThuocTinhDG[iS].donvi_id) {
                    if (i>=pIndex*pSize && i<(pIndex+1)*pSize) {
                        lstSelect.push(i - pIndex*pSize)
                    }
                    break;
                }
            }
        }
        this.$refs.thuocTinhDaGan.$refs.grid.selectRows(lstSelect)
        this.isPageChange = false;
    },
  onCheckbox() {
        let changes = this.$refs.thuocTinhDaGan.getBatchChanges().changedRecords
          if(changes.length>0){
            for(let i=0;i<changes.length;i++){
                let change=changes[i]
                const index=this.listbox.ds_donvidagan.list.findIndex(x=>x.donvi_id==change.donvi_id)
                if(index>-1){
                    this.listbox.ds_donvidagan.list[index].dinhmuc=change.dinhmuc
                }
            }
        }
        this.isPageChange = true
        let check = document.getElementById("chb_chonnhanh")
        if (check.checked) {
              this.checkedThuocTinhDG = this.listbox.ds_donvidagan.list.slice()
        } else {
            this.checkedThuocTinhDG = [];
        }
        this.listbox.ds_donvidagan.list = [...this.listbox.ds_donvidagan.list]
    },
    ///

    rowSelected_CD(data) {
      try {
        if (data.data.length == undefined) {
            this.checkedThuocTinhDG.push(data.data)
        } else {
            let check = document.getElementById("chb_chonnhanhcd")
            if (check.checked) {
                this.checkedThuocTinhDG = this.listbox.ds_chucdanhdagan.list.slice()
            } else {
                for (let i=0; i<data.data.length; i++) {
                    let isExists = false
                    for (let ix=0; ix<this.checkedThuocTinhDG.length; ix++) {
                        if (data.data[i].vitri_id == this.checkedThuocTinhDG[ix].vitri_id) {
                            isExists = true;
                            break
                        }
                    }
                    if (!isExists) {
                        this.checkedThuocTinhDG.push(data.data[i])
                    }
                }
            }
        }
        console.log('rowSelected');
      } catch (err) {
        console.log(err);
      }
    },
    rowDeselected_CD(data) {
        if (this.isPageChange) return
        if (data.data.length == undefined) {
            $("#chb_chonnhanhcd").prop('checked', false);
            let iRemove
            for (let i=0; i<this.checkedThuocTinhDG.length; i++) {
              if (this.checkedThuocTinhDG[i].vitri_id == data.data.vitri_id) {
                iRemove = i;
                break;
              }
            }
            this.checkedThuocTinhDG.splice(iRemove, 1)
        } else {
            if (data.data.length != this.checkedThuocTinhDG.length) {
                $("#chb_chonnhanhcd").prop('checked', false);
                this.onCheckbox_CD()
                return
            }

            let check = document.getElementById("chb_chonnhanhcd")
            if (check.checked) {
                this.checkedThuocTinhDG = this.listbox.ds_chucdanhdagan.list.slice()
            } else {
                for (let ix=0; ix<data.data.length; ix++) {
                    let iRemove = -1
                    for (let i=0; i<this.checkedThuocTinhDG.length; i++) {
                        if (this.checkedThuocTinhDG[i].loaivt_id == data.data[ix].loaivt_id) {
                            iRemove = i;
                            break;
                        }
                    }
                    this.checkedThuocTinhDG.splice(iRemove, 1)
                }
            }
        }
        console.log('rowDeselected');
    },
    pageChanged_CD() {
        this.isPageChange = true;
        let check = document.getElementById("chb_chonnhanhcd")
        if (check.checked) {
            this.checkedThuocTinhDG = this.listbox.ds_chucdanhdagan.list.slice()
        }
        this.listbox.ds_chucdanhdagan.list = [...this.listbox.ds_chucdanhdagan.list];
    },
    actionComplete_CD() {
        let lstSelect = []
        let pIndex = this.$refs.thuocTinhDaGan_CD.$refs.pagination.pageIndexData
        let pSize = this.$refs.thuocTinhDaGan_CD.$refs.pagination.pageSizeData
        for (let i=0; i<this.listbox.ds_chucdanhdagan.list.length; i++) {
            for (let iS=0; iS<this.checkedThuocTinhDG.length; iS++) {
                if (this.listbox.ds_chucdanhdagan.list[i].vitri_id == this.checkedThuocTinhDG[iS].vitri_id) {
                    if (i>=pIndex*pSize && i<(pIndex+1)*pSize) {
                        lstSelect.push(i - pIndex*pSize)
                    }
                    break;
                }
            }
        }
        this.$refs.thuocTinhDaGan_CD.$refs.grid.selectRows(lstSelect)
        this.isPageChange = false;
    },
  onCheckbox_CD() {
        let changes = this.$refs.thuocTinhDaGan_CD.getBatchChanges().changedRecords
        if(changes.length>0){
          for(let i=0;i<changes.length;i++){
              let change=changes[i]
              const index = this.listbox.ds_chucdanhdagan.list.findIndex(x=>x.vitri_id==change.vitri_id)
              if(index>-1){
                  this.listbox.ds_chucdanhdagan.list[index].dinhmuc=change.dinhmuc
              }
          }
        }
        this.isPageChange = true
        let check = document.getElementById("chb_chonnhanhcd")
        if (check.checked) {
              this.checkedThuocTinhDG = this.listbox.ds_chucdanhdagan.list.slice()
        } else {
            this.checkedThuocTinhDG = [];
        }
        this.listbox.ds_chucdanhdagan.list = [...this.listbox.ds_chucdanhdagan.list]
    },

  /// Chức vụ chưa gán

  rowSelected_CC(data) {
      try {
        if (data.data.length == undefined) {
            this.checkedThuocTinhCG.push(data.data)
        } else {
            let check = document.getElementById("chb_chonnhanhcc")
            if (check.checked) {
                this.checkedThuocTinhCG = this.listbox.ds_chucdanhchuagan.list.slice()
            } else {
                for (let i=0; i<data.data.length; i++) {
                    let isExists = false
                    for (let ix=0; ix<this.checkedThuocTinhCG.length; ix++) {
                        if (data.data[i].vitri_id == this.checkedThuocTinhCG[ix].vitri_id) {
                            isExists = true;
                            break
                        }
                    }
                    if (!isExists) {
                        this.checkedThuocTinhCG.push(data.data[i])
                    }
                }
            }
        }
        console.log('rowSelected');
      } catch (err) {
        console.log(err);
      }
    },
    rowDeselected_CC(data) {
        if (this.isPageChange) return
        if (data.data.length == undefined) {
            $("#chb_chonnhanhcc").prop('checked', false);
            let iRemove
            for (let i=0; i<this.checkedThuocTinhCG.length; i++) {
              if (this.checkedThuocTinhCG[i].vitri_id == data.data.vitri_id) {
                iRemove = i;
                break;
              }
            }
            this.checkedThuocTinhCG.splice(iRemove, 1)
        } else {
            if (data.data.length != this.checkedThuocTinhCG.length) {
                $("#chb_chonnhanhcc").prop('checked', false);
                this.onCheckbox_CC()
                return
            }

            let check = document.getElementById("chb_chonnhanhcc")
            if (check.checked) {
                this.checkedThuocTinhCG = this.listbox.ds_chucdanhchuagan.list.slice()
            } else {
                for (let ix=0; ix<data.data.length; ix++) {
                    let iRemove = -1
                    for (let i=0; i<this.checkedThuocTinhCG.length; i++) {
                        if (this.checkedThuocTinhCG[i].loaivt_id == data.data[ix].loaivt_id) {
                            iRemove = i;
                            break;
                        }
                    }
                    this.checkedThuocTinhCG.splice(iRemove, 1)
                }
            }
        }
        console.log('rowDeselected');
    },
    pageChanged_CC() {
        this.isPageChange = true;
        let check = document.getElementById("chb_chonnhanhcc")
        if (check.checked) {
            this.checkedThuocTinhCG = this.listbox.ds_chucdanhchuagan.list.slice()
        }
        this.listbox.ds_chucdanhchuagan.list = [...this.listbox.ds_chucdanhchuagan.list]
    },
    actionComplete_CC() {
        let lstSelect = []
        let pIndex = this.$refs.thuocTinhDaGan_CC.$refs.pagination.pageIndexData
        let pSize = this.$refs.thuocTinhDaGan_CC.$refs.pagination.pageSizeData
        for (let i=0; i<this.listbox.ds_chucdanhchuagan.list.length; i++) {
            for (let iS=0; iS<this.checkedThuocTinhCG.length; iS++) {
                if (this.listbox.ds_chucdanhchuagan.list[i].vitri_id == this.checkedThuocTinhCG[iS].vitri_id) {
                    if (i>=pIndex*pSize && i<(pIndex+1)*pSize) {
                        lstSelect.push(i - pIndex*pSize)
                    }
                    break;
                }
            }
        }
        this.$refs.thuocTinhDaGan_CC.$refs.grid.selectRows(lstSelect)
        this.isPageChange = false;
    },
  onCheckbox_CC() {
        this.isPageChange = true
        let check = document.getElementById("chb_chonnhanhcc")
        if (check.checked) {
              this.checkedThuocTinhCG = this.listbox.ds_chucdanhchuagan.list.slice()
        } else {
            this.checkedThuocTinhCG = [];
        }
        this.listbox.ds_chucdanhchuagan.list = [...this.listbox.ds_chucdanhchuagan.list]
    },

    /// Nhân viên đã gán

  rowSelected_ND(data) {
      try {
        if (data.data.length == undefined) {
            this.checkedThuocTinhDG.push(data.data)
        } else {
            let check = document.getElementById("chb_chonnhanhnd")
            if (check.checked) {
                this.checkedThuocTinhDG = this.listbox.ds_nvdagan.list.slice()
            } else {
                for (let i=0; i<data.data.length; i++) {
                    let isExists = false
                    for (let ix=0; ix<this.checkedThuocTinhDG.length; ix++) {
                        if (data.data[i].nhanvien_id == this.checkedThuocTinhDG[ix].nhanvien_id) {
                            isExists = true;
                            break
                        }
                    }
                    if (!isExists) {
                        this.checkedThuocTinhDG.push(data.data[i])
                    }
                }
            }
        }
        console.log('rowSelected');
      } catch (err) {
        console.log(err);
      }
    },
    rowDeselected_ND(data) {
        if (this.isPageChange) return
        if (data.data.length == undefined) {
            $("#chb_chonnhanhnd").prop('checked', false);
            let iRemove
            for (let i=0; i<this.checkedThuocTinhDG.length; i++) {
              if (this.checkedThuocTinhDG[i].nhanvien_id == data.data.nhanvien_id) {
                iRemove = i;
                break;
              }
            }
            this.checkedThuocTinhDG.splice(iRemove, 1)
        } else {
            if (data.data.length != this.checkedThuocTinhDG.length) {
                $("#chb_chonnhanhnd").prop('checked', false);
                this.onCheckbox_ND()
                return
            }

            let check = document.getElementById("chb_chonnhanhnd")
            if (check.checked) {
                this.checkedThuocTinhDG = this.listbox.ds_nvdagan.list.slice()
            } else {
                for (let ix=0; ix<data.data.length; ix++) {
                    let iRemove = -1
                    for (let i=0; i<this.checkedThuocTinhDG.length; i++) {
                        if (this.checkedThuocTinhDG[i].nhanvien_id == data.data[ix].nhanvien_id) {
                            iRemove = i;
                            break;
                        }
                    }
                    this.checkedThuocTinhDG.splice(iRemove, 1)
                }
            }
        }
        console.log('rowDeselected');
    },
    pageChanged_ND() {
        this.isPageChange = true;
        let check = document.getElementById("chb_chonnhanhnd")
        if (check.checked) {
            this.checkedThuocTinhDG = this.listbox.ds_nvdagan.list.slice()
        }
        this.listbox.ds_nvdagan.list = [...this.listbox.ds_nvdagan.list]
    },
    actionComplete_ND() {
        let lstSelect = []
        let pIndex = this.$refs.thuocTinhDaGan_ND.$refs.pagination.pageIndexData
        let pSize = this.$refs.thuocTinhDaGan_ND.$refs.pagination.pageSizeData
        for (let i=0; i<this.listbox.ds_nvdagan.list.length; i++) {
            for (let iS=0; iS<this.checkedThuocTinhDG.length; iS++) {
                if (this.listbox.ds_nvdagan.list[i].nhanvien_id == this.checkedThuocTinhDG[iS].nhanvien_id) {
                    if (i>=pIndex*pSize && i<(pIndex+1)*pSize) {
                        lstSelect.push(i - pIndex*pSize)
                    }
                    break;
                }
            }
        }
        this.$refs.thuocTinhDaGan_ND.$refs.grid.selectRows(lstSelect)
        this.isPageChange = false;
    },
  onCheckbox_ND() {
        let changes = this.$refs.thuocTinhDaGan_ND.getBatchChanges().changedRecords
        if(changes.length>0){
          for(let i=0;i<changes.length;i++){
              let change=changes[i]
              const index= this.listbox.ds_nvdagan.list.findIndex(x=>x.nhanvien_id==change.nhanvien_id)
              if(index>-1){
                  this.listbox.ds_nvdagan.list[index].dinhmuc=change.dinhmuc
              }
          }
        }
        this.isPageChange = true
        let check = document.getElementById("chb_chonnhanhnd")
        if (check.checked) {
              this.checkedThuocTinhDG = this.listbox.ds_nvdagan.list.slice()
        } else {
            this.checkedThuocTinhDG = [];
        }
        this.listbox.ds_nvdagan.list = [...this.listbox.ds_nvdagan.list]
    },
  /// Nhân viên chưa gán

rowSelected_NC(data) {
      try {
        if (data.data.length == undefined) {
            this.checkedThuocTinhCG.push(data.data)
        } else {
            let check = document.getElementById("chb_chonnhanhnc")
            if (check.checked) {
                this.checkedThuocTinhCG = this.listbox.ds_nvchuagan.list.slice()
            } else {
                for (let i=0; i<data.data.length; i++) {
                    let isExists = false
                    for (let ix=0; ix<this.checkedThuocTinhCG.length; ix++) {
                        if (data.data[i].nhanvien_id == this.checkedThuocTinhCG[ix].nhanvien_id) {
                            isExists = true;
                            break
                        }
                    }
                    if (!isExists) {
                        this.checkedThuocTinhCG.push(data.data[i])
                    }
                }
            }
        }
      } catch (err) {
        console.log(err);
      }
    },
    rowDeselected_NC(data) {
        if (this.isPageChange) return
        if (data.data.length == undefined) {
            $("#chb_chonnhanhnc").prop('checked', false);
            let iRemove
            for (let i=0; i<this.checkedThuocTinhCG.length; i++) {
              if (this.checkedThuocTinhCG[i].nhanvien_id == data.data.nhanvien_id) {
                iRemove = i;
                break;
              }
            }
            this.checkedThuocTinhCG.splice(iRemove, 1)
        } else {
            if (data.data.length != this.checkedThuocTinhCG.length) {
                $("#chb_chonnhanhnc").prop('checked', false);
                this.onCheckbox_NC()
                return
            }

            let check = document.getElementById("chb_chonnhanhnc")
            if (check.checked) {
                this.checkedThuocTinhCG = this.listbox.ds_nvchuagan.list.slice()
            } else {
                for (let ix=0; ix<data.data.length; ix++) {
                    let iRemove = -1
                    for (let i=0; i<this.checkedThuocTinhCG.length; i++) {
                        if (this.checkedThuocTinhCG[i].nhanvien_id == data.data[ix].nhanvien_id) {
                            iRemove = i;
                            break;
                        }
                    }
                    this.checkedThuocTinhCG.splice(iRemove, 1)
                }
            }
        }

    },
    pageChanged_NC() {
        this.isPageChange = true;
        let check = document.getElementById("chb_chonnhanhnc")
        if (check.checked) {
            this.checkedThuocTinhCG = this.listbox.ds_nvchuagan.list.slice()
        }
        this.listbox.ds_nvchuagan.list = [...this.listbox.ds_nvchuagan.list]
    },
    actionComplete_NC() {
        let lstSelect = []
        let pIndex = this.$refs.thuocTinhDaGan_NC.$refs.pagination.pageIndexData
        let pSize = this.$refs.thuocTinhDaGan_NC.$refs.pagination.pageSizeData
        for (let i=0; i<this.listbox.ds_nvchuagan.list.length; i++) {
            for (let iS=0; iS<this.checkedThuocTinhCG.length; iS++) {
                if (this.listbox.ds_nvchuagan.list[i].nhanvien_id == this.checkedThuocTinhCG[iS].nhanvien_id) {
                    if (i>=pIndex*pSize && i<(pIndex+1)*pSize) {
                        lstSelect.push(i - pIndex*pSize)
                    }
                    break;
                }
            }
        }
        this.$refs.thuocTinhDaGan_NC.$refs.grid.selectRows(lstSelect)
        this.isPageChange = false;
    },
  onCheckbox_NC() {
        this.isPageChange = true
        let check = document.getElementById("chb_chonnhanhnc")
        if (check.checked) {
              this.checkedThuocTinhCG = this.listbox.ds_nvchuagan.list.slice()
        } else {
            this.checkedThuocTinhCG = [];
        }
        this.listbox.ds_nvchuagan.list = [...this.listbox.ds_nvchuagan.list]
    }


  },
   provide: {
    grid: [Edit]
  }





};

</script>
<style>
  .txtcolor{
    color: red;
    font-weight: bold;
}
</style>
