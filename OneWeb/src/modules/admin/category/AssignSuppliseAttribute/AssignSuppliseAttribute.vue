<template src="./AssignSuppliseAttribute.html"></template>
<style src="./AssignSuppliseAttribute.scss"></style>
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
  name: 'AssignSuppliseAttribute',
  mounted: async function ()  {
    await this.GetThuocTinh();
    await this.GetVatTuDaGan();
    await this.refreshTree(0);
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
        ds_vtdagan: {
          list: [],
          header: [ {fieldName: 'ten_vt', headerText: 'Tên vật tư', allowFiltering: true}, 
                       ],
          value: {},
          checked: []
        },
        ds_vtchuagan:{
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
        thuoctinh: {
          list: [],
          value: {},
          },
        ds_vattudagan: {
          list: [],
          header: [ {fieldName: 'ten_dv', headerText: 'Tên vật tư', allowFiltering: true}, 
                       ],
          value: {},
          checked: []
        },
      },
    }
  },
  computed: {
   
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

    GetThuocTinh: async function ()
    {
      var input = {};
      this.loading(true);
      this.listbox.thuoctinh.list = this.GetData(await API.QLVT_THUOCTINH(this.axios, input)).filter(x=>x.THUOCTINH_ID > 0).map(x=>({id:x.THUOCTINH_ID,text:x.THUOCTINH}));
      //this.listbox.thuoctinh.list = this.GetData(await API.QLVT_NHOM_TT(this.axios, input));
      console.log(this.listbox.thuoctinh.list);
      this.loading(false);
      if (this.listbox.thuoctinh.list.length>0)
      {
        this.listbox.thuoctinh.value.THUOCTINH_ID = this.listbox.thuoctinh.list[0].id;
      }
    },
    GetVatTuDaGan: async function ()
    {
      var input = {};
      input.thuoctinh_id = this.listbox.thuoctinh.value.THUOCTINH_ID;
      console.log(input);
      this.loading(true);
      this.listbox.ds_vtdagan.list = this.GetData(await API.sp_nap_ds_vattu_dg(this.axios, input));
      console.log(this.listbox.ds_vtdagan.list);
      this.loading(false);
    },
    rowClicked()
    {

    },
    gridChanged()
    {

    },
    ThuocTinhChanged()
    {
      this.GetVatTuDaGan();
      this.refreshTree(0);
    },

    btnLamMoi()
    {
      this.GetVatTuDaGan();
      this.refreshTree(0);
    },

    ItemsVatTuDaGan_click: function (dataKeys) {
      this.listbox.ds_vtdagan.checked = dataKeys
      this.listbox.ds_vtdagan.list.forEach(function (item) {
        if (dataKeys.filter(x => x === item.vattu_id).length > 0) {
          item.ischecked = true
        } else {
          item.ischecked = false
        }
      })
    }, 

    nodeChecked: function(args) {
      var treeObj = document.getElementById('treeview').ej2_instances[0];
      this.listbox.ds_vtchuagan.checked = [];
       for (var item of treeObj.checkedNodes)
       {
         var temps = this.listbox.ds_vtchuagan.list.filter(x => x.vattu_id == item);
         if (temps.length>0)
         {
            for (var temp of temps)
            {
              this.listbox.ds_vtchuagan.checked.push(temp);
            } 
         }
       } 
    },

   refreshTree: async function (val) {
      var input = {};
      input.thuoctinh_id = this.listbox.thuoctinh.value.THUOCTINH_ID;
      
      var self = this
      if (val==0)
      {
        try {
          console.log(input);
          this.loading(true);
          this.listbox.ds_vtchuagan.list = this.GetData(await API.sp_nap_ds_vattu_cg(this.axios, input));
          this.loading(false);
        } catch (ex) {
          this.loading(false);
        }
      }
      var data = []
      self.listbox.ds_vtchuagan.list.forEach(function (item) {
        var newItem = {
          vattu_cha_id: item.vattu_cha_id == null ? null : item.vattu_cha_id,
          vattu_id: item.vattu_id,
          ten_vt: item.ten_vt,
          unitlevel: item.unitlevel
        }
        var child = self.listbox.ds_vtchuagan.list.filter(x => x.vattu_cha_id === item.vattu_id)
        let count = child.length
        if (count > 1) {
          newItem.hasChild = true
          newItem.expanded = true
          newItem.count = count
          // newItem.child = child
          if (newItem.vattu_cha_id === 0 || newItem.unitlevel <= 2) {
            newItem.expanded = true
          } else {
            newItem.expanded = false
          }
        }
        if (data.filter(x => x.vattu_id === newItem.vattu_id).length === 0) {
          data.push(newItem)
        }
      })
      this.TreeData = data.sort((a, b) => a.ten_vt.localeCompare(b.ten_vt))
      this.listbox.fields = {
        dataSource: this.TreeData,
        id: 'vattu_id',
        parentID: 'vattu_cha_id',
        text: 'ten_vt',
        hasChildren: 'hasChild'
      }
    },

    btnGoGan: async function ()
    {
      var dsVatTu = [];
      console.log(this.listbox.ds_vtdagan.checked)
      for (var temp of this.listbox.ds_vtdagan.checked)
      {
        //this.listbox.ds_vtchuagan.list.push(temp);
        dsVatTu.push(temp);
      } 
      dsVatTu = dsVatTu.map(x=>({VATTU_ID:x.vattu_id,THUOCTINH_ID:this.listbox.thuoctinh.value.THUOCTINH_ID}));
      var input = {};
      input.js_thuoctinh_vattu = JSON.stringify(dsVatTu);
      console.log(input);
      try {
        this.loading(true);
        this.GetData(await API.sp_del_thuoctinh_vattu(this.axios, input));
        
        this.ShowAlert("Gỡ gán vật tư thành công",0);
        //Chuyển item sang phải
        var temps = this.listbox.ds_vtdagan.list.filter(a => !this.listbox.ds_vtdagan.checked.includes(a));
        this.listbox.ds_vtdagan.list = [];
        this.listbox.ds_vtdagan.list = temps;
        await this.refreshTree(0);
        await this.GetVatTuDaGan();
        this.loading(false);
        
      } catch (ex) {
        this.loading(false);
        this.ShowAlert("Gỡ gán vật tư không thành công",2);
      } 
    },

    btnGan: async function ()
    {
      var dsVatTu = [];
      console.log(this.listbox.ds_vtchuagan.checked);
      for (var temp of this.listbox.ds_vtchuagan.checked)
      {
        //this.listbox.ds_vtdagan.list.push(temp);
        dsVatTu.push(temp);
      } 
      dsVatTu = dsVatTu.map(x=>({VATTU_ID:x.vattu_id,THUOCTINH_ID:this.listbox.thuoctinh.value.THUOCTINH_ID}));
      var input = {};
      input.js_thuoctinh_vattu = JSON.stringify(dsVatTu);
      console.log(input);
      try {
        this.loading(true);
        this.GetData(await API.sp_ins_thuoctinh_vattu(this.axios, input));
        this.loading(false);
        //Chuyển item sang trái
        var temps = this.listbox.ds_vtchuagan.list.filter(a => !this.listbox.ds_vtchuagan.checked.includes(a));
        this.listbox.ds_vtchuagan.list = [];
        this.listbox.ds_vtchuagan.list = temps;
        this.refreshTree(1);
        this.GetVatTuDaGan();
        this.ShowAlert("Gán vật tư thành công",0);
      } catch (ex) {
        this.loading(false);
        this.ShowAlert("Gán vật tư không thành công",2);
      } 
    },


  },
};

</script>
