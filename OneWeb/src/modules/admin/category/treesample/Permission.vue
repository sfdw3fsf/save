<template src="./Permission.html"></template>
<style scoped src="./Permission.scss"></style>
<script>
import breadcrumb from '@/components/breadcrumb'
import gridview from '@/components/Shared/gridview'
import treemenu from './treemenu'
import PermissionAPI from './PermissionAPI'
//import moment from 'moment'
//import select2 from 'select2'
export default {
  components: { breadcrumb,gridview,treemenu },
  name: 'Permission',
  //props: [ 'kieuSPId' ],
  mounted () {
    PermissionAPI.get_loai_ct(this.axios,{}).then(
      (response) => {
          if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000' && response.data.data) {
            this.listbox.loai_ct.list = response.data.data.map(x=>{return {id:x['LOAI_CT_ID'],text:x['TENLOAI_CT']}});
            }
          else alert("Lỗi khi lấy thông tin: "+response.data.message);
        }
    ).catch(function(){
      console.log('Có lỗi xảy ra khi truy cập thông tin. Vui lòng thử lại hoặc liên hệ quản trị viên.');
    });
    PermissionAPI.get_ds_quyen(this.axios,{}).then(
      (response) => {
          if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000' && response.data.data) {
            this.listbox.ds_quyen.list = response.data.data.map(x=>{return {id:x['QUYEN_ID'],text:x['TEN_QUYEN']}});
          }
          else alert("Lỗi khi lấy thông tin: "+response.data.message);
        }
    ).catch(function(){
      console.log('Có lỗi xảy ra khi truy cập thông tin. Vui lòng thử lại hoặc liên hệ quản trị viên.');
    });
    PermissionAPI.get_nhom_bang(this.axios,{}).then(
      (response) => {
          if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000' && response.data.data) {
            this.listbox.nhom_bang.list = response.data.data;//.map(x=>{return {id:x['NHOMHAM_ID'],text:x['TEN_NHOM']}});
          }
          else alert("Lỗi khi lấy thông tin: "+response.data.message);
        }
    ).catch(function(){
      console.log('Có lỗi xảy ra khi truy cập thông tin. Vui lòng thử lại hoặc liên hệ quản trị viên.');
    });
    PermissionAPI.get_nhom_ham(this.axios,{}).then(
      (response) => {
          if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000' && response.data.data) {
            this.listbox.nhom_ham.list = response.data.data;//.map(x=>{return {id:x['NHOMHAM_ID'],text:x['TEN_NHOM']}});
          }
          else alert("Lỗi khi lấy thông tin: "+response.data.message);
        }
    ).catch(function(){
      console.log('Có lỗi xảy ra khi truy cập thông tin. Vui lòng thử lại hoặc liên hệ quản trị viên.');
    });
    PermissionAPI.get_nhom_menu(this.axios,{}).then(
      (response) => {
          if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000' && response.data.data) {
            this.listbox.nhom_menu.list = response.data.data;//.map(x=>{return {id:x['NHOMHAM_ID'],text:x['TEN_NHOM']}});
          }
          else alert("Lỗi khi lấy thông tin: "+response.data.message);
        }
    ).catch(function(){
      console.log('Có lỗi xảy ra khi truy cập thông tin. Vui lòng thử lại hoặc liên hệ quản trị viên.');
    });
    PermissionAPI.get_nhom_bc(this.axios,{}).then(
      (response) => {
          if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000' && response.data.data) {
            this.listbox.nhom_bc.list = response.data.data;//.map(x=>{return {id:x['NHOMHAM_ID'],text:x['TEN_NHOM']}});
          }
          else alert("Lỗi khi lấy thông tin: "+response.data.message);
        }
    ).catch(function(){
      console.log('Có lỗi xảy ra khi truy cập thông tin. Vui lòng thử lại hoặc liên hệ quản trị viên.');
    });
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
      return this.cleanTree(this.buildTree({id:0,text:'Cột chưa gán', childs:[],isSelected:false},this.listbox.quyen_bang.chuagan,'ham_id','ten_ham','muc'));
    },
    tree_bang_dagan: function() {
      this.listbox.quyen_bang.dagan_checked;
      return this.cleanTree(this.buildTree({id:0,text:'Cột đã gán', childs:[],isSelected:false},this.listbox.quyen_bang.dagan,'ham_id','ten_ham','muc'));
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
        this.loading(false);
        if(response.data.error_code && response.data.error_code === 'BSS-00000000') {
          this.listbox.quyen_menu.chuagan = response.data.data.menu1;
          this.listbox.quyen_menu.dagan = response.data.data.menu;
          for(var i of this.listbox.quyen_menu.chuagan) i.isSelected=false;
          for(var i of this.listbox.quyen_menu.dagan) i.isSelected=false;
        }
      }); 
    },
    NhomHamChanged() {
      this.loading(true);
      let pdata = {
        "p_quyen_id": this.listbox.ds_quyen.value,
        "p_nhom_ham_id": this.listbox.nhom_ham.value,
      }
      PermissionAPI.post_ds_ham_quyen(this.axios, pdata).then((response) => {
        this.loading(false);
        if(response.data.error_code && response.data.error_code === 'BSS-00000000') {
          this.listbox.quyen_ham.chuagan = response.data.data.ham1;
          this.listbox.quyen_ham.dagan = response.data.data.ham;
          for(var i of this.listbox.quyen_ham.chuagan) i.isSelected=false;
          for(var i of this.listbox.quyen_ham.dagan) i.isSelected=false;
        }
      }); 
    },
    NhomBangChanged() {
      this.loading(true);
      let pdata = {
        "p_quyen_id": this.listbox.ds_quyen.value,
        "p_ten_bang": this.listbox.nhom_bang.value,
      }
      PermissionAPI.post_ds_bang_quyen(this.axios, pdata).then((response) => {
        this.loading(false);
        if(response.data.error_code && response.data.error_code === 'BSS-00000000') {
          this.listbox.quyen_bang.chuagan = response.data.data.ham1;
          this.listbox.quyen_bang.dagan = response.data.data.ham;
          for(var i of this.listbox.quyen_bang.chuagan) i.isSelected=false;
          for(var i of this.listbox.quyen_bang.dagan) i.isSelected=false;
        }
      }); 
    },
    buildTree(tree,ds,idfield,textfield,levelfield) {
      if(!ds || ds.length<=0) return tree;
      var i = 0;
      var level = 1;
      var lastparentitem = tree;
      for(;i<ds.length;) {
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
      console.log(data,);
    },
    tree_menu_dagan_cbClicked(id) {
      this.TreeCBUpdated(this.listbox.quyen_menu.dagan,id);
      this.listbox.quyen_menu.dagan_checked++;
    },
    tree_menu_chuagan_cbClicked(id){
      this.TreeCBUpdated(this.listbox.quyen_menu.chuagan,id);
      this.listbox.quyen_menu.chuagan_checked++;
    },
    tree_ham_dagan_cbClicked(id) {
      this.TreeCBUpdated(this.listbox.quyen_ham.dagan,id);
      this.listbox.quyen_ham.dagan_checked++;
    },
    tree_ham_chuagan_cbClicked(id){
      this.TreeCBUpdated(this.listbox.quyen_ham.chuagan,id);
      this.listbox.quyen_ham.chuagan_checked++;
    },
    TreeCBUpdated(list,id) {
      for(var i=0;i<list.length;i++) {
        if(list[i].menu_id==id)
        {
          var itemlevel = list[i].unitlevel;
          var sel = !list[i].isSelected;
          list[i].isSelected = sel;
          for(var j=i+1;j<list.length;j++) 
            if(list[j].unitlevel>itemlevel)
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
          alert('Xử lý quyền thành công!');
          this.listbox.quyen_menu.chuagan_checked++;
          this.listbox.quyen_menu.dagan_checked++;
        }
        else alert("Lỗi khi xử lý thông tin: "+response.data.message);
      }).catch(function(){
        console.log('Có lỗi xảy ra khi truy cập thông tin. Vui lòng thử lại hoặc liên hệ quản trị viên.');
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
        "p_nhom_ham_id": this.listbox.nhom_gan.value,
      }
      PermissionAPI.post_sp_action_ds_quyen_ham(this.axios, pdata).then((response) => {
        this.loading(false);
        if(response.data.error_code && response.data.error_code === 'BSS-00000000') {
          this.listbox.quyen_ham.chuagan = response.data.data.menu1;
          this.listbox.quyen_ham.dagan = response.data.data.menu;
          for(var i of this.listbox.quyen_ham.chuagan) i.isSelected=false;
          for(var i of this.listbox.quyen_ham.dagan) i.isSelected=false;
          alert('Xử lý quyền thành công!');
          this.listbox.quyen_ham.chuagan_checked++;
          this.listbox.quyen_ham.dagan_checked++;
        }
        else alert("Lỗi khi xử lý thông tin: "+response.data.message);
      }).catch(function(){
        console.log('Có lỗi xảy ra khi truy cập thông tin. Vui lòng thử lại hoặc liên hệ quản trị viên.');
      }); 
    },
  }
}
</script>
