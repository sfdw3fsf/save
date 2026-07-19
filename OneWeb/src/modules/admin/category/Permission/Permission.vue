<template src="./Permission.html"></template>
<style scoped src="./Permission.scss"></style>
<script>
import breadcrumb from '@/components/breadcrumb'
import gridview from '@/components/Shared/gridview'
import treemenu from '@/components/Shared/treemenu'
import PermissionAPI from './PermissionAPI'
//import moment from 'moment'
//import select2 from 'select2'
export default {
  components: { breadcrumb,gridview,treemenu },
  name: 'Permission',
  //props: [ 'kieuSPId' ],
  watch: {
    'currenttab': function() {
      this.SearchChanged();
    },
  },
  async mounted () {
    this.loading(true);
    await PermissionAPI.get_loai_ct(this.axios,{}).then(
      (response) => {
          if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000' && response.data.data) {
            this.listbox.loai_ct.list = response.data.data.map(x=>{return {id:x['LOAI_CT_ID'],text:x['TENLOAI_CT']}});
            if(this.listbox.loai_ct.list && this.listbox.loai_ct.list.length>0) this.listbox.loai_ct.value = this.listbox.loai_ct.list[0].id;
          }
          else {
            this.listbox.loai_ct.list = [];
            this.listbox.loai_ct.value = 0;
          }
        }
    ).catch(function(){
      this.$toast.error('Có lỗi xảy ra khi truy cập thông tin. Vui lòng thử lại hoặc liên hệ quản trị viên.');
    });
    await PermissionAPI.get_ds_quyen(this.axios,{}).then(
      (response) => {
          if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000' && response.data.data) {
            this.listbox.ds_quyen.list = response.data.data.map(x=>{return {id:x['QUYEN_ID'],text:x['TEN_QUYEN']?x['TEN_QUYEN'].trim():""}}).sort((a,b)=>a.text.localeCompare(b.text));
            if(this.listbox.ds_quyen.list && this.listbox.ds_quyen.list.length>0) this.listbox.ds_quyen.value = this.listbox.ds_quyen.list[0].id;
          }
          else {
            this.listbox.ds_quyen.list = [];
            this.listbox.ds_quyen.value = 0;
          }
        }
    ).catch(function(){
      this.$toast.error('Có lỗi xảy ra khi truy cập thông tin. Vui lòng thử lại hoặc liên hệ quản trị viên.');
    });
    var api3 = PermissionAPI.get_ds_bang(this.axios,{}).then(
      (response) => {
          if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000' && response.data.data) {
            this.listbox.nhom_bang.list = response.data.data;//.map(x=>{return {id:x['NHOMHAM_ID'],text:x['TEN_NHOM']}});
            if(this.listbox.nhom_bang.list && this.listbox.nhom_bang.list.length>0) this.listbox.nhom_bang.value = this.listbox.nhom_bang.list[0].TENBANG;
          }
          else {
            this.listbox.nhom_bang.list = [];
            this.listbox.nhom_bang.value = 0;
          }
        }
    ).catch(function(){
      this.$toast.error('Có lỗi xảy ra khi truy cập thông tin. Vui lòng thử lại hoặc liên hệ quản trị viên.');
    });
    var api4 = PermissionAPI.get_nhom_ham(this.axios,{}).then(
      (response) => {
          if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000' && response.data.data) {
            this.listbox.nhom_ham.list = response.data.data;//.map(x=>{return {id:x['NHOMHAM_ID'],text:x['TEN_NHOM']}});
            if(this.listbox.nhom_ham.list && this.listbox.nhom_ham.list.length>0) this.listbox.nhom_ham.value = this.listbox.nhom_ham.list[0].NHOMHAM_ID;
          }
          else {
            this.listbox.nhom_ham.list = [];
            this.listbox.nhom_ham.value = 0;
          }
        }
    ).catch(function(){
      this.$toast.error('Có lỗi xảy ra khi truy cập thông tin. Vui lòng thử lại hoặc liên hệ quản trị viên.');
    });
    var api5 = PermissionAPI.get_nhom_menu(this.axios,{}).then(
      (response) => {
          if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000' && response.data.data) {
            this.listbox.nhom_menu.list = response.data.data.sort((a,b)=>a.NHOM_MENU_ID>b.NHOM_MENU_ID);//.map(x=>{return {id:x['NHOMHAM_ID'],text:x['TEN_NHOM']}});
            if(this.filtered_menu && this.filtered_menu.length>0) this.listbox.nhom_menu.value = this.filtered_menu[0].NHOM_MENU_ID;
          }
          else {
            this.listbox.nhom_menu.list = [];
            this.listbox.nhom_menu.value = 0;
          }
        }
    ).catch(function(){
      this.$toast.error('Có lỗi xảy ra khi truy cập thông tin. Vui lòng thử lại hoặc liên hệ quản trị viên.');
    });
    var api6 = PermissionAPI.get_nhom_bc(this.axios,{}).then(
      (response) => {
          if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000' && response.data.data) {
            this.listbox.nhom_bc.list = response.data.data.sort((a,b)=>a.NHOM_BC_ID>b.NHOM_BC_ID);//.map(x=>{return {id:x['NHOMHAM_ID'],text:x['TEN_NHOM']}});
            if(this.filtered_bc && this.filtered_bc.length>0) this.listbox.nhom_bc.value = this.filtered_bc[0].NHOM_BC_ID;
          }
          else {
            this.listbox.nhom_bc.list = [];
            this.listbox.nhom_bc.value = 0;
          }
        }
    ).catch(function(){
      this.$toast.error('Có lỗi xảy ra khi truy cập thông tin. Vui lòng thử lại hoặc liên hệ quản trị viên.');
    });
    Promise.all([api3,api4,api5,api6]).then().catch().finally(()=>{this.loading(false);});
  },
  destroyed () {},
  computed: {
    filtered_menu: function() {
      return this.listbox.nhom_menu.list.filter(x=>x.LOAI_CT_ID==this.listbox.loai_ct.value);
    },
    filtered_bc: function() {
      return this.listbox.nhom_bc.list.filter(x=>x.LOAI_CT_ID==this.listbox.loai_ct.value);
    },
    tree_menu_chuagan: function() {
      this.listbox.quyen_menu.chuagan_checked;
      return this.cleanTree(this.buildTree({id:0,text:'Menu chưa gán', childs:[],isSelected:false},this.listbox.quyen_menu.chuagan,'menu_id','ten_hienthi','unitlevel'));
    },
    tree_menu_dagan: function() {
      this.listbox.quyen_menu.dagan_checked;
      return this.cleanTree(this.buildTree({id:0,text:'Menu đã gán', childs:[],isSelected:false},this.listbox.quyen_menu.dagan,'menu_id','ten_hienthi','unitlevel'));
    },
    tree_ham_chuagan: function() {
      this.listbox.quyen_ham.chuagan_checked;
      return this.cleanTree(this.buildTree({id:0,text:'Hàm chưa gán', childs:[],isSelected:false},this.listbox.quyen_ham.chuagan,'ham_id','ten_ham','muc'));
    },
    tree_ham_dagan: function() {
      this.listbox.quyen_ham.dagan_checked;
      return this.cleanTree(this.buildTree({id:0,text:'Hàm đã gán', childs:[],isSelected:false},this.listbox.quyen_ham.dagan,'ham_id','ten_ham','muc'));
    },
    tree_bang_chuagan: function() {
      this.listbox.quyen_bang.chuagan_checked;
      return this.cleanTree(this.buildTree({id:0,text:'Bảng chưa gán', childs:[],isSelected:false},this.listbox.quyen_bang.chuagan,this.listbox.quyen_bang.idfield,this.listbox.quyen_bang.textfield,this.listbox.quyen_bang.levelfield));
    },
    tree_bang_dagan: function() {
      this.listbox.quyen_bang.dagan_checked;
      return this.cleanTree(this.buildTree({id:0,text:'Bảng đã gán', childs:[],isSelected:false},this.listbox.quyen_bang.dagan,this.listbox.quyen_bang.idfield,this.listbox.quyen_bang.textfield,this.listbox.quyen_bang.levelfield));
    },
  },
  data () {
    return {
      ky_hoa_don: { 
        value: new Date(),
        dateconfig: {
          altFormat: 'm/Y',
          altInput: true,
          dateFormat: 'Ym',
          allowInput:true,
          onChange: this.KyHDChanged,
        },
        isEnabled: true,
      },
      header: {
        title: 'Quản lý quyền',
        list: [
          { name: 'Trang chủ', link: { name: 'Ui.cards' } },
          { name: 'Lập hợp đồng', link: { name: 'Ui.cards' } },
          { name: 'Lắp đặt mới', link: { name: 'Ui.cards' } },
        ]
      },
      currenttab: "tab1",
      ghiChu: "",
      listbox: {
        loai_ct: {
          list:[],
          value: 0,
        },
        ds_quyen: {
          list:[],
          value: 0,
        },
        nhom_bang: {
          list:[],
          value: "",
        },
        nhom_ham: {
          list:[],
          value: 0,
        },
        nhom_menu: {
          list:[],
          value: 0,
        },
        nhom_bc: {
          list:[],
          value: 0,
        },
        quyen_menu: {
          chuagan: [],
          chuagan_checked:0,
          dagan: [],
          dagan_checked:0,
        },
        quyen_ham: {
          chuagan: [],
          chuagan_checked:0,
          dagan: [],
          dagan_checked:0,
        },
        quyen_bang: {
          chuagan: [],
          chuagan_checked:0,
          dagan: [],
          dagan_checked:0,
          levelfield: "",
          idfield: "",
          textfield: "",
        },
        quyen_baocao: {
          chuagan: [],
          chuagan_checked:[],
          headers_chuagan: [{name:'baocao_id',text:'ID'},{name:'ten_bc',text:'Tên quyền chưa gán'},],
          headers_dagan: [{name:'baocao_id',text:'ID'},{name:'ten_bc',text:'Tên quyền đã gán'},],
          cols_chuagan: [
            {fieldName:'ma_bc',headerText:'ID', allowFiltering: true, }, //Doi theo yeu cau tester
            {fieldName:'ten_bc',headerText:'Tên quyền chưa gán', allowFiltering: true, },
          ],
          cols_dagan: [
            {fieldName:'ma_bc',headerText:'ID', allowFiltering: true, }, //Doi theo yeu cau tester
            {fieldName:'ten_bc',headerText:'Tên quyền đã gán', allowFiltering: true, },
          ],
          dagan: [],
          dagan_checked:[],
        },
      },
      gridbox: {
        truocdonno: {
          list:[],
          header:[],
          value: {},
        },
        saudonno: {
          list:[],
          header:[],
          value: {},
        },
      }
    }
  },
  methods: {
    NhomMenuChanged() {
      this.loading(true);
      let pdata = {
        "p_quyen_id": this.listbox.ds_quyen.value,
        "p_nhom_menu_id": this.listbox.nhom_menu.value,
      }
      PermissionAPI.post_ds_menu_quyen(this.axios, pdata).then((response) => {
        if(response.data.error_code && response.data.error_code === 'BSS-00000000') {
          this.listbox.quyen_menu.chuagan = response.data.data.menu1;
          this.listbox.quyen_menu.dagan = response.data.data.menu;
          for(var i of this.listbox.quyen_menu.chuagan) i.isSelected=false;
          for(var i of this.listbox.quyen_menu.dagan) i.isSelected=false;
        }
      }).catch().finally(()=>{ this.loading(false); }); ; 
    },
    NhomHamChanged() {
      this.loading(true);
      let pdata = {
        "p_quyen_id": this.listbox.ds_quyen.value,
        "p_nhom_ham_id": this.listbox.nhom_ham.value,
      }
      PermissionAPI.post_ds_ham_quyen(this.axios, pdata).then((response) => {
        if(response.data.error_code && response.data.error_code === 'BSS-00000000') {
          this.listbox.quyen_ham.chuagan = response.data.data.ham1;
          this.listbox.quyen_ham.dagan = response.data.data.ham;
          for(var i of this.listbox.quyen_ham.chuagan) i.isSelected=false;
          for(var i of this.listbox.quyen_ham.dagan) i.isSelected=false;
        }
      }).catch().finally(()=>{ this.loading(false); }); ; 
    },
    NhomBangChanged() {
      this.loading(true);
      let pdata = {
        "p_quyen_id": this.listbox.ds_quyen.value,
        "p_ten_bang": this.listbox.nhom_bang.value,
      }
      PermissionAPI.post_ds_bang_quyen(this.axios, pdata).then((response) => {
        if(response.data.error_code && response.data.error_code === 'BSS-00000000') {
          this.listbox.quyen_bang.chuagan = response.data.data.bang1.data_json;
          this.listbox.quyen_bang.dagan = response.data.data.bang.data_json;
          this.listbox.quyen_bang.levelfield = response.data.data.bang.columns[0].column_name;
          this.listbox.quyen_bang.textfield = response.data.data.bang.columns[1].column_name;
          this.listbox.quyen_bang.idfield = response.data.data.bang.columns[2].column_name;
          for(var i of this.listbox.quyen_bang.chuagan) i.isSelected=false;
          for(var i of this.listbox.quyen_bang.dagan) i.isSelected=false;
        }
      }).catch().finally(()=>{ this.loading(false); }); 
    },
    NhomBCChanged() {
      this.loading(true);
      let pdata = {
        "p_quyen_id": this.listbox.ds_quyen.value,
        "p_nhom_bc_id": this.listbox.nhom_bc.value,
      }
      PermissionAPI.post_ds_baocao_quyen(this.axios, pdata).then((response) => {
        if(response.data.error_code && response.data.error_code === 'BSS-00000000') {
          this.listbox.quyen_baocao.chuagan = response.data.data.bc1;
          this.listbox.quyen_baocao.dagan = response.data.data.bc;
          for(var i of this.listbox.quyen_baocao.chuagan) i.ischecked=false;
          for(var i of this.listbox.quyen_baocao.dagan) i.ischecked=false;
        }
      }).catch().finally(()=>{ this.loading(false); }); ; 
    },
    SearchChanged() {
      switch(this.currenttab) {
        case "tab1":
          if(this.listbox.nhom_menu.value)
            this.NhomMenuChanged();
          break;
        case "tab2":
          if(this.listbox.nhom_bc.value)
            this.NhomBCChanged();
          break;
        case "tab3":
          if(this.listbox.nhom_ham.value)
            this.NhomHamChanged();
          break;
        case "tab4":
          if(this.listbox.nhom_bang.value)
            this.NhomBangChanged();
          break;
      }
    },
    buildTree(tree,ds,idfield,textfield,levelfield) {
      if(!ds || ds.length<=0) return tree;
      var i = 0;
      var level = 1;
      var lastparentitem = tree;
      while(i<ds.length) {
        if(ds[i][levelfield]==level)
        {
          lastparentitem.childs.push({id:ds[i][idfield],text:ds[i][textfield],childs:[],parent:lastparentitem,isSelected:ds[i].isSelected});
          i++;
          continue;
        }
        if(ds[i][levelfield]>level) {
          lastparentitem = lastparentitem.childs[lastparentitem.childs.length-1];
          level = ds[i][levelfield];
          continue;
        }
        if(ds[i][levelfield]<level) {
          lastparentitem = lastparentitem.parent;
          level--;
          continue;
        }
      }
      return tree;
    },
    cleanTree(item) {
      if(item.childs && item.childs.length>0)
        for(var i of item.childs)
          i = this.cleanTree(i);
      if(item.parent)
        delete item.parent;
      //item.isSelected=checked;
      return item;
    },
    treeClicked(data) {
      // console.log(data,);
    },
    tree_menu_dagan_cbClicked(id) {
      this.TreeCBUpdated(this.listbox.quyen_menu.dagan,id,'menu_id','unitlevel');
      this.listbox.quyen_menu.dagan_checked++;
    },
    tree_menu_chuagan_cbClicked(id){
      this.TreeCBUpdated(this.listbox.quyen_menu.chuagan,id,'menu_id','unitlevel');
      this.listbox.quyen_menu.chuagan_checked++;
    },
    tree_ham_dagan_cbClicked(id) {
      this.TreeCBUpdated(this.listbox.quyen_ham.dagan,id,'ham_id','muc');
      this.listbox.quyen_ham.dagan_checked++;
    },
    tree_ham_chuagan_cbClicked(id){
      this.TreeCBUpdated(this.listbox.quyen_ham.chuagan,id,'ham_id','muc');
      this.listbox.quyen_ham.chuagan_checked++;
    },
    tree_bang_dagan_cbClicked(id) {
      this.TreeCBUpdated(this.listbox.quyen_bang.dagan,id,this.listbox.quyen_bang.idfield,this.listbox.quyen_bang.levelfield);
      this.listbox.quyen_bang.dagan_checked++;
    },
    tree_bang_chuagan_cbClicked(id){
      this.TreeCBUpdated(this.listbox.quyen_bang.chuagan,id,this.listbox.quyen_bang.idfield,this.listbox.quyen_bang.levelfield);
      this.listbox.quyen_bang.chuagan_checked++;
    },
    TreeCBUpdated(list,id,idfield,levelfield) {
      for(var i=0;i<list.length;i++) {
        if(list[i][idfield]==id)
        {
          var itemlevel = list[i][levelfield];
          var sel = !list[i].isSelected;
          list[i].isSelected = sel;
          for(var j=i+1;j<list.length;j++) 
            if(list[j][levelfield]>itemlevel)
              list[j].isSelected = sel;
            else break;
          break;
        }
      }
    },
    UpdateQuyenMenu(pkieu) {
      this.loading(true);
      var pmenuarr = [];
      if(pkieu==1) {
        pmenuarr = this.listbox.quyen_menu.chuagan.filter(x=>x.isSelected).map(a=>a.menu_id);
      }
      else {
        pmenuarr = this.listbox.quyen_menu.dagan.filter(x=>x.isSelected).map(a=>a.menu_id);
      }
      if(!pmenuarr || !pmenuarr.length) { this.loading(false); return; }
      let pdata = {
        "p_kieu": pkieu, 
        "p_menu_id_arr": pmenuarr,
        "p_quyen_id": this.listbox.ds_quyen.value,
        "p_nhom_menu_id": this.listbox.nhom_menu.value,
      }
      PermissionAPI.post_sp_action_ds_quyen_menu(this.axios, pdata).then((response) => {
        this.loading(false);
        if(response.data.error_code && response.data.error_code === 'BSS-00000000') {
          this.listbox.quyen_menu.chuagan = response.data.data.menu1;
          this.listbox.quyen_menu.dagan = response.data.data.menu;
          for(var i of this.listbox.quyen_menu.chuagan) i.isSelected=false;
          for(var i of this.listbox.quyen_menu.dagan) i.isSelected=false;
          this.$toast.success('Cập nhật thành công!');
          this.listbox.quyen_menu.chuagan_checked++;
          this.listbox.quyen_menu.dagan_checked++;
        }
        // else {this.$toast.error("Lỗi khi xử lý thông tin: "+response.data.message)};
      }).catch(function(){
        this.$toast.error('Có lỗi xảy ra khi truy cập thông tin. Vui lòng thử lại hoặc liên hệ quản trị viên.');
      }); 
    },
    UpdateQuyenHam(pkieu) {
      this.loading(true);
      var parr = [];
      if(pkieu==1) {
        parr = this.listbox.quyen_ham.chuagan.filter(x=>x.isSelected).map(a=>a.ham_id);
      }
      else {
        parr = this.listbox.quyen_ham.dagan.filter(x=>x.isSelected).map(a=>a.ham_id);
      }
      if(!parr || !parr.length) { this.loading(false); return; }
      let pdata = {
        "p_kieu": pkieu, 
        "p_ham_id_arr": parr,
        "p_quyen_id": this.listbox.ds_quyen.value,
        "p_nhom_ham_id": this.listbox.nhom_ham.value,
      }
      PermissionAPI.post_sp_action_ds_quyen_ham(this.axios, pdata).then((response) => {
        this.loading(false);
        if(response.data.error_code && response.data.error_code === 'BSS-00000000') {
          this.listbox.quyen_ham.chuagan = response.data.data.ham1;
          this.listbox.quyen_ham.dagan = response.data.data.ham;
          for(var i of this.listbox.quyen_ham.chuagan) i.isSelected=false;
          for(var i of this.listbox.quyen_ham.dagan) i.isSelected=false;
          this.$toast.success('Cập nhật thành công!');
          this.listbox.quyen_ham.chuagan_checked++;
          this.listbox.quyen_ham.dagan_checked++;
        }
        // else this.$toast.error("Lỗi khi xử lý thông tin: "+response.data.message);
      }).catch(function(){
        this.$toast.error('Có lỗi xảy ra khi truy cập thông tin. Vui lòng thử lại hoặc liên hệ quản trị viên.');
      }); 
    },
    UpdateQuyenBang(pkieu) {
      this.loading(true);
      var parr = [];
      if(pkieu==1) {
        parr = this.listbox.quyen_bang.chuagan.filter(x=>x.isSelected).map(a=>a[this.listbox.quyen_bang.idfield]);
      }
      else {
        parr = this.listbox.quyen_bang.dagan.filter(x=>x.isSelected).map(a=>a[this.listbox.quyen_bang.idfield]);
      }
      if(!parr || !parr.length) { this.loading(false); return; }
      let pdata = {
        "p_kieu": pkieu, 
        "p_id_chon": parr,
        "p_quyen_id": this.listbox.ds_quyen.value,
        "p_ten_bang": this.listbox.nhom_bang.value,
      }
      PermissionAPI.post_sp_action_ds_quyen_bang(this.axios, pdata).then((response) => {
        this.loading(false);
        if(response.data.error_code && response.data.error_code === 'BSS-00000000') {
          this.listbox.quyen_bang.chuagan = response.data.data.bang1.data_json;
          this.listbox.quyen_bang.dagan = response.data.data.bang.data_json;
          this.listbox.quyen_bang.levelfield = response.data.data.bang.columns[0].column_name;
          this.listbox.quyen_bang.textfield = response.data.data.bang.columns[1].column_name;
          this.listbox.quyen_bang.idfield = response.data.data.bang.columns[2].column_name;
          for(var i of this.listbox.quyen_bang.chuagan) i.isSelected=false;
          for(var i of this.listbox.quyen_bang.dagan) i.isSelected=false;
          this.$toast.success('Cập nhật thành công!');
          this.listbox.quyen_bang.chuagan_checked++;
          this.listbox.quyen_bang.dagan_checked++;
        }
        // else this.$toast.error("Lỗi khi xử lý thông tin: "+response.data.message);
      }).catch(function(){
        this.$toast.error('Có lỗi xảy ra khi truy cập thông tin. Vui lòng thử lại hoặc liên hệ quản trị viên.');
      }); 
    },
    UpdateQuyenBC(pkieu) {
      this.loading(true);
      var parr = [];
      if(pkieu==1) {
        parr = this.listbox.quyen_baocao.chuagan_checked.map(a=>a.baocao_id);//this.listbox.quyen_baocao.chuagan.filter(x=>x.ischecked).map(a=>a.baocao_id);
      }
      else {
        parr = this.listbox.quyen_baocao.dagan_checked.map(a=>a.baocao_id);//this.listbox.quyen_baocao.dagan.filter(x=>x.ischecked).map(a=>a.baocao_id);
      }
      if(!parr || !parr.length) { this.loading(false); return; }
      let pdata = {
        "p_kieu": pkieu, 
        "p_baocao_id_arr": parr,
        "p_quyen_id": this.listbox.ds_quyen.value,
        "p_nhom_bc_id": this.listbox.nhom_bc.value,
      }
      PermissionAPI.post_sp_action_ds_quyen_baocao(this.axios, pdata).then((response) => {
        this.loading(false);
        if(response.data.error_code && response.data.error_code === 'BSS-00000000') {
          this.listbox.quyen_baocao.chuagan = response.data.data.bc1;
          this.listbox.quyen_baocao.dagan = response.data.data.bc;
          // for(var i of this.listbox.quyen_baocao.chuagan) i.ischecked=false;
          // for(var i of this.listbox.quyen_baocao.dagan) i.ischecked=false;
          this.$toast.success('Cập nhật thành công!');
          // this.listbox.quyen_baocao.chuagan_checked++;
          // this.listbox.quyen_baocao.dagan_checked++;
        }
        // else this.$toast.error("Lỗi khi xử lý thông tin: "+response.data.message);
      }).catch(function(){
        this.$toast.error('Có lỗi xảy ra khi truy cập thông tin. Vui lòng thử lại hoặc liên hệ quản trị viên.');
      }); 
    },
    grdQuyenChuaGanChanged(items) {
      this.listbox.quyen_baocao.chuagan_checked = items;
    },
    grdQuyenDaGanChanged(items) {
      this.listbox.quyen_baocao.dagan_checked = items;
    },
  }
}
</script>
