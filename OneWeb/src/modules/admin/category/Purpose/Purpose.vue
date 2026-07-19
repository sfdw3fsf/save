<template src="./Purpose.html"></template>
<style src="./Purpose.scss"></style>
<script>
import breadcrumb from '@/components/breadcrumb'
import API from './API'
import gridview from '@/components/Shared/gridview'
import VueFlatPickr from 'vue-flatpickr-component';
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
  components: {breadcrumb,API,VueFlatPickr,gridview,DataManager, Query, ODataAdaptor, Predicate },
  name: 'Purpose',
  mounted: async function () {
    await this.LoadCbo();
    await this.loaict_change();
    await this.refreshTree();
    await this.SetButton(0);
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
        title: 'ĐỒNG BỘ VẬT TƯ',
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
        NhomMD: {
          list: [],
          value: {},
        },
        LoaiCT: {
          list: [],
          value: {},
        },
        MucDichCha: {
          list: [],
          value: {},
        },

        MucDich: {
          list: [],
          value: {},
        },

        kieuphieu: {
          list: [],
          value: {},
        },

        ds_donvitinh: {
          list: [],
          header: [],
          value: {},
          isEnabled: true,
        },

        ds_mucdich:{
           list:[],
           value:"",
           isEnabled: false,
           checked: []
        },

        fields: {
          dataSource: null,
          id: 'mucdich_id',
          parentID: 'mucdich_cha_id',
          text: 'mucdich',
          hasChildren: 'hasChild'
        },
        
      },
      success_code: "BSS-00000000",
      animationSettings: { effect: 'Zoom' },
      DonViTinh: "",
      dvitinh_id: "0",
      showResult:false,
      isUploaded:false,

      txtMaMD:"",
      txtMucDich: "",
      mucdich_id: 0,

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
    btnPopupClick: function (item) {
      //this.$bvModal.show('PopUp');
      this.$refs.dlgPurpose.show();
    },
    // show: function () {
    //   //this.$bvModal.show('PopUp');
    //   this.$refs.dlgPurpose.show();
    // },
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
        this.$refs.txtMaMD.focus();
        this.Clear();
      }
      if (kieu == 1)//Them moi
      {
        this.isGhiLai = true;
        this.isHuy = true;
        this.$refs.txtMaMD.focus();
        this.Clear();
      }
      if (kieu == 2)//Huy
      {
        this.isNhapMoi = true;
        this.isXoa = true;
        this.$refs.txtMaMD.focus();
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

    LoadCbo: async function () {
      //Load nhóm mục đích
      var input = {};
      this.listbox.NhomMD.list = this.GetData(await API.QLVT_NHOM_MD(this.axios, input)).filter(x=>x.NHOMMD_ID > 0).map(x=>({id:x.NHOMMD_ID,text:x.NHOM_MD}));
      if (this.listbox.NhomMD.list.length>0)
      {
        this.listbox.NhomMD.value.NHOMMD_ID = 1; //this.listbox.NhomMD.list[0].id;
      }
      //Load loại chứng từ
      var input1 = {};
      this.listbox.LoaiCT.list = this.GetData(await API.sp_lay_ds_loaict(this.axios, input1)).filter(x=>x.loaict_id > 0).map(x=>({id:x.loaict_id,text:x.loai_ct}));
      if (this.listbox.LoaiCT.list.length>0)
      {
        this.listbox.LoaiCT.value.loaict_id = this.listbox.LoaiCT.list[0].id;
      }
      //Load mục đích cha
      var input2 = {};
      input2.mucdich_id = -1;
      this.listbox.MucDichCha.list = this.GetData(await API.sp_lay_mucdich_cha(this.axios, input2)).filter(x=>x.mucdich_id > 0).map(x=>({id:x.mucdich_id,text:x.mucdich_id + " - " + x.mucdich}));
      if (this.listbox.MucDichCha.list.length>0)
      {
        this.listbox.MucDichCha.value.mucdich_id = this.listbox.MucDichCha.list[0].id;
      }
      //Load mục đích 
      this.listbox.MucDich.list = this.listbox.MucDichCha.list;
      this.listbox.MucDich.list.splice(0, 0, {id:0, text: "--"});
      console.log(this.listbox.MucDich.list);

      // if (this.listbox.MucDich.list.length>0)
      // {
      //   this.listbox.MucDich.value.mucdich_id = this.listbox.MucDich.list[0].id;
      // }
    },

    refreshTree: async function () {
      var input = {};
      var self = this
      this.loading(true);
      try {
          this.listbox.ds_mucdich.list = this.GetData(await API.sp_lay_danhsach_mucdich(this.axios, input));
          // console.log(this.listbox.ds_mucdich.list);
          this.loading(false);
        } catch (ex) {
          this.loading(false);
        }
      var data = []
      self.listbox.ds_mucdich.list.forEach(function (item) {
        var newItem = {
          mucdich_cha_id: item.mucdich_cha_id == '' ? null : item.mucdich_cha_id,
          mucdich_id: item.mucdich_id,
          mucdich: item.mucdich,
          unitlevel: item.unitlevel
        }
        var child = self.listbox.ds_mucdich.list.filter(x => x.mucdich_cha_id === item.mucdich_id)
        let count = child.length
        if (count > 1) {
          newItem.hasChild = true
          newItem.expanded = true
          newItem.count = count
          // newItem.child = child
          if (newItem.mucdich_cha_id === 0 || newItem.unitlevel <= 2) {
            newItem.expanded = true
          } else {
            newItem.expanded = false
          }
        }
        if (data.filter(x => x.mucdich_id === newItem.mucdich_id).length === 0) {
          data.push(newItem)
        }
      })
      this.TreeData = data.sort((a, b) => a.mucdich.localeCompare(b.mucdich))
      this.listbox.fields = {
        dataSource: this.TreeData,
        id: 'mucdich_id',
        parentID: 'mucdich_cha_id',
        text: 'mucdich',
        hasChildren: 'hasChild'
      }
    },

    nodeChecked: function(args) {
       var treeObj = document.getElementById('treeview').ej2_instances[0];
      this.listbox.ds_mucdich.checked = [];
       for (var item of treeObj.checkedNodes)
       {
         var temps = this.listbox.ds_mucdich.list.filter(x => x.mucdich_id == item);
         if (temps.length>0)
         {
           for (var temp of temps)
          {
            this.listbox.ds_mucdich.checked.push(temp);
          } 
         }
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
      this.listbox.ds_donvitinh.header = [{name:'DVI_TINH',text:'Đơn vị tính'}];
    },

    loaict_change: async function()
    {
      //Load kiểu phiếu
      var input = {};
      input.loaichungtu_id = this.listbox.LoaiCT.value.loaict_id;
      this.listbox.kieuphieu.list = this.GetData(await API.sp_lay_kieuphieu_mucdich(this.axios, input)).filter(x=>x.mucdich_id > 0).map(x=>({id:x.mucdich_id,text:x.mucdich}));
      if (this.listbox.kieuphieu.list.length>0)
      {
        this.listbox.kieuphieu.value.mucdich_id = this.listbox.kieuphieu.list[0].id;
      }
    },

    clear()
    {
      this.txtMaMD ="";
      this.txtMucDich = "";
    },

    btnNhapMoi()
    {
      this.clear();
      this.SetButton(1);
    },

    btnHuy()
    {
      this.clear();
      this.SetButton(0);
    },

    treeClicked: async function (node)
    {
      var treeObj = node.nodeData;
      //Lấy chi tiết mục đích
      var input = {};
      input.mucdich_id = treeObj.id
     
      this.loading(true);
      try {
        console.log(input);
          var temp = this.GetData(await API.sp_lay_mucdich_theo_id(this.axios, input));
          console.log(temp);
          this.loading(false);
          if (temp.length>0)
          {
            this.txtMaMD = temp[0].ma_md;
            this.txtMucDich = temp[0].mucdich;
            this.mucdich_id = temp[0].mucdich_id;
            this.listbox.NhomMD.value.NHOMMD_ID = temp[0].nhommd_id;
            this.listbox.MucDichCha.value.mucdich_id = temp[0].mucdich_cha_id;
            this.listbox.LoaiCT.value.loaict_id = temp[0].loaict_id;
            this.listbox.kieuphieu.value.mucdich_id = temp[0].loaiphieu_id;

          }
        } catch (ex) {
          this.loading(false);
        }

      this.SetButton(3);
    },

    btnGhiLai: async function ()
    {
      //chặn quyền theo KHDN-14349
      if (!this.isNhapMoi) // Nhập mới
      {
        this.ShowAlert("Đây là danh mục dùng chung muốn điều chỉnh hay thêm mới mục đích vui lòng gửi IT360",2);        
        return;
      }
      else{ //sửa
        this.ShowAlert("Cập nhật mục đích không thành công",2);        
        return;
      }
      if (this.txtMaMD =="")
      {
        this.ShowAlert("Mã mục đích không được để trống",2);
        this.$refs.txtMaMD.focus();
        return;
      }
      
      if (this.txtMucDich =="")
      {
        this.ShowAlert("Mục đích không được để trống",2);
        this.$refs.txtMucDich.focus();
        return;
      }
      if (!this.isNhapMoi) // Nhập mới
      {
        var input = {};
        input.mucdich = this.txtMucDich;
        input.mucdichcha_id = this.listbox.MucDichCha.value.mucdich_id;
        input.nhom_mucdich_id = this.listbox.NhomMD.value.NHOMMD_ID;
        input.ma_mucdich = this.txtMaMD;
        input.loaiphieu_id = this.listbox.kieuphieu.value.mucdich_id;
        input.noidung = "";
        input.loaict_id = this.listbox.LoaiCT.value.loaict_id;
        input.tentat = "";
        console.log(input);
        this.loading(true);
        try {
            await API.fn_insert_mucdich(this.axios, input).then((response) => {
              console.log(response.data);
              this.loading(false);
              if(response.data.error_code && response.data.error_code === this.success_code) {
                this.clear();
                this.refreshTree();
                this.loading(false);
                this.ShowAlert("Thêm mới mục đích thành công",0);
              }
              else
              {
                this.ShowAlert("Thêm mới mục đích không thành công: " + response.data.data ,2);
              }
            }
            ); 
          } catch (ex) {
            this.loading(false);
            this.ShowAlert("Thêm mới mục đích không thành công",2);
          }
      }
      else //cập nhật
      {
        var input = {};
        input.mucdich_id = this.mucdich_id;
        input.mucdich = this.txtMucDich;
        input.mucdichcha_id = this.listbox.MucDichCha.value.mucdich_id;
        input.nhom_mucdich_id = this.listbox.NhomMD.value.NHOMMD_ID;
        input.ma_mucdich = this.txtMaMD;
        input.loaiphieu_id = this.listbox.kieuphieu.value.mucdich_id;
        input.noidung = null;
        input.loaict_id = this.listbox.LoaiCT.value.loaict_id;
        input.tentat = "";
        console.log(input);
        this.loading(true);
        try {
          await API.fn_update_mucdich(this.axios, input).then((response) => {
              console.log(response.data);
              this.loading(false);
              if(response.data.error_code && response.data.error_code === this.success_code) {
                this.clear();
                this.refreshTree();
                this.loading(false);
                this.ShowAlert("Cập nhật mục đích thành công",0);
              }
              else
              {
                this.ShowAlert("Cập nhật mục đích không thành công: " + response.data.data ,2);
              }
            }
            ); 
          } catch (ex) {
            this.loading(false);
            this.ShowAlert("Cập nhật mục đích không thành công",2);
          }
      }
    },

    btnXoa: async function ()
    {
      //chặn quyền theo KHDN-14349
      this.ShowAlert("Xóa mục đích không thành công",2);
      return
      this.$bvModal.msgBoxConfirm('Bạn thật sự muốn xoá mục đích này không?', {
      title: '', size: 'sm', okTitle: 'Đồng ý',cancelTitle: 'Hủy',}).then(async v => {
      if(v) {
        var input = {};
        input.mucdich_id = this.mucdich_id;
        console.log(input);
        this.loading(true);
        try {
          
            this.GetData(await API.fn_delete_mucdich(this.axios, input));
            this.clear();
            this.refreshTree();
            this.loading(false);
            this.ShowAlert("Xóa mục đích thành công",0);
          } catch (ex) {
            this.loading(false);
            this.ShowAlert("Xóa mục đích không thành công",2);
          }
      }
      });
    },

    MucDichChange(item)
    {
      this.searchNodes(item);
    },

     //Search node tree
    searchNodes: function (args) {
      if (this.listbox.MucDich.value.mucdich_id==0)
      {
        this.refreshTree();
      }
      else
      {
        var _text = "";
        var temp = this.listbox.MucDich.list.filter(x => x.id == this.listbox.MucDich.value.mucdich_id);
        if (temp.length>0)
        {
          _text = temp[0].text;
        }
        console.log(args);
        var seft = this
        // eslint-disable-next-line one-var
        var predicats = [], _array = [], _filter = []
        
        let treeObj = document.getElementById('treeview').ej2_instances[0];

        if (_text == '') {
          this.changeDataSource(seft.TreeData)
        } else {
          var predicate = new Predicate('mucdich', 'contains', _text, true)
          var filteredList = new DataManager(seft.TreeData).executeLocal(new Query().where(predicate))
          console.log(`filteredList:${filteredList.length}`)
          console.log(filteredList)
          for (var j = 0; j < filteredList.length; j++) {
            _filter.push(filteredList[j]['mucdich_id'])
            var filters = this.getFilterItems(filteredList[j], seft.TreeData)

            for (var i = 0; i < filters.length; i++) {
              if (_array.indexOf(filters[i]) == -1 && filters[i] != null) {
                _array.push(filters[i])
                predicats.push(new Predicate('mucdich_id', 'equal', filters[i], false))
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
      }
      
    },

    getFilterItems: function (fList, list) {
      var nodes = []
      nodes.push(fList['mucdich_id'])
      var query2 = new Query().where('mucdich_id', 'equal', fList['mucdich_cha_id'], false)
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
        dataSource: data, id: 'mucdich_id', text: 'mucdich', parentID: 'mucdich_cha_id', hasChildren: 'hasChild'
      }
    },


  }
};
</script>
