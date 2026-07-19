<template src="./UploadFileHoSoDienTu.html"></template>
<style src="./UploadFileHoSoDienTu.scss"></style>
<script>
import breadcrumb from '@/components/breadcrumb'
import treemenu from './treemenu'
import gridview from './gridview'
import VueFlatPickr from 'vue-flatpickr-component';
import UploadFileHoSoDienTuAPI from '../../api/UploadFileHosoDienTuAPI'
//CSS
import '@/assets/vendor/bootstrap-table/bootstrap-table.min.css';
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.css';
//JS
import '@/assets/vendor/jquery/split'
import '@/assets/vendor/bootstrap-table/bootstrap-table.min'
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.min'
//

export default {
  components: { breadcrumb,treemenu,gridview,VueFlatPickr },
  name: 'UploadFileHoSoDienTu',
  mounted () {
    // Split boxes
    Split(['#boxLeft', '#boxMid','#boxRight'], {
        sizes: [20, 40, 40],
    })
    // END Split boxes
    this.refreshTree();
    UploadFileHoSoDienTuAPI.get_danhsach_nhom_file_hoso(this.axios, {}).then((response) => {
    if(response && response.data && response.data.data && response.data.error && response.data.error === 'BSS-00000000' && response.data.data) {
        this.listbox.danhsach_nhom_file_hoso.list = response.data.data;
        //this.filterbox.dichvu_vienthong.list = response.data.data;
        }
      }
    ); 
    UploadFileHoSoDienTuAPI.get_danhsach_loai_file_hoso(this.axios, {nhomfile_id:1}).then((response) => {
    if(response && response.data && response.data.data && response.data.error && response.data.error === 'BSS-00000000' && response.data.data) {
        this.listbox.danhsach_loai_file_hoso.list = response.data.data;
        //this.filterbox.dichvu_vienthong.list = response.data.data;
        }
      }
    );
  },
  destroyed () {},
  computed: {
    baohong: {
      get() { return this.isBaoHong; },
      set(value) { this.isBaoHong=value; }
    },
    hopdongmoi: {
      get() { return !this.isBaoHong; },
      set(value) { this.isBaoHong=!value; }
    }
  },
  data () {
    return {
      dateconfig: {
        altFormat: 'd/m/Y',
        altInput: true,
        dateFormat: 'd/m/Y',
        allowInput:true,
      },
      header: {
        title: 'QUẢN LÝ NHÂN VIÊN CHĂM SÓC KHÁCH HÀNG',
        list: [
          { name: 'Lập hợp đồng', link: { name: 'Ui.cards' } },
          {
            name: 'Lắp đặt mới',
            link: { name: 'Ui.buttons' }
          }
        ]
      },
      isBaoHong:false,
      isAddNew: false,
      isUpdate: false,
      listbox: {
        doituong_hoso:{
          list:{},
          value:"",
          isEnabled: false,
        },
        hopdong_theo_matb:{
          list:[],
          value: "",
          isEnabled: false,
        },
        danhsach_hoso_icon:{
          list: [],
          value: "",
          isEnabled: false,
        },
        lichsu_baohong_theo_matb: {
          list: [],
          value: "",
          isEnabled:false,
        },
        danhsach_loai_file_hoso: {
          list: [],
          value: "",
          isEnabled: false,
        },
        danhsach_nhom_file_hoso: {
          list: [],
          value: "",
          isEnabled: false,
        },
        danhsach_thuebao_theo_makh:{
          list: [],
          value: {},
          isEnabled: false,
        },
        chitiet_file_hs: {
          list: [],
          value: "",
          isEnabled: false,
        },
        ds_file_hs: {
          list: [],
          header:[],
          value: {},
          isEnabled: false,
        },
        ds_hd_tb_theo_hdkh_id: {
          list: [],
          value: "",
          isEnabled: false,
        },
      },
      dateConfig: {
        altInput: true,
        dateFormat: 'd/m/Y',
        altFormat: 'd/m/Y'
      },
      from_date: new Date(),
      to_date: new Date(),
    }
  },
  methods: {
    refreshTree() {
      let postData =
      {
        vtungay: this.from_date.toLocaleString("en-GB").substring(0,10),//.getDate(),
        vdenngay: this.to_date.toLocaleString("en-GB").substring(0,10),//.getDate()
      }
      UploadFileHoSoDienTuAPI.get_danhsach_doituong_hoso(this.axios, postData).then((response) => {
        if(response && response.data && response.data.data && response.data.error && response.data.error === 'BSS-00000000' && response.data.data) {
          var dsdoituong = response.data.data;
          var tree = dsdoituong.filter((a)=>a.hoso_cha_id==null);
          function addTree(item) {
            if(item.doituong==null) item.doituong='Root';
            var dsitems = dsdoituong.filter((a)=>a.hoso_cha_id===item.hoso_id);
            if(dsitems && dsitems.length>0){
              for(var i=0;i<dsitems.length;i++)
                dsitems[i] = addTree(dsitems[i]);
            item.$nodes = dsitems;
            }
            return item;
          }
          for(var j=0;j<tree.length;j++) tree[j] = addTree(tree[j]);
          this.listbox.doituong_hoso.list = tree[0];
        }
      }); 
    },
    treeClicked(val) {
      this.listbox.doituong_hoso.value = val;
      UploadFileHoSoDienTuAPI.get_ds_file_hs(this.axios,{"hoso_id": val}).then((response) => {
        if(response && response.data && response.data.data && response.data.error && response.data.error === 'BSS-00000000' && response.data.data) {
          this.listbox.ds_file_hs.list = response.data.data;
          this.listbox.ds_file_hs.header = [{name:'ma_tb',text:'Số máy/Acc'},{name:'loai_file',text:'Loại file'},{name:'ten_file',text:'Tên file'},{name:'url',text:'Xem/Tải về'},]
            //this.listbox.danhsach_loai_file_hoso.list = response.data.data;
        }
        else {
          this.listbox.ds_file_hs.list = [];
        }
      });
    },
    fileHSChanged(obj) {
      if(!obj) { 
        this.isUpdate = false;
        return; 
      }
      else this.isUpdate = true;
      var loaifile_changed = this.listbox.ds_file_hs.value.nhomfile_id!=obj.nhomfile_id;
      this.listbox.ds_file_hs.value=Object.assign({}, obj); // Clone object to avoid reverse update to grid data.
      if(loaifile_changed) this.loadLoaiFile();
      if(!this.listbox.danhsach_thuebao_theo_makh.value || !this.listbox.danhsach_thuebao_theo_makh.value.ma_tb)
      {
        this.listbox.danhsach_thuebao_theo_makh.value.ma_tb=obj.ma_tb;
        this.listbox.danhsach_thuebao_theo_makh.value.ma_kh=obj.ma_kh;
      }
    },
    dsThueBaoChanged(obj) {
      this.listbox.danhsach_thuebao_theo_makh.value=Object.assign({},obj); // Clone object to avoid problem with reverse binding
    },
    loadLoaiFile() {
      UploadFileHoSoDienTuAPI.get_danhsach_loai_file_hoso(this.axios, {nhomfile_id:this.listbox.ds_file_hs.value.nhomfile_id}).then((response) => {
        if(response && response.data && response.data.data && response.data.error && response.data.error === 'BSS-00000000' && response.data.data) {
          this.listbox.danhsach_loai_file_hoso.list = response.data.data;
          }
        }
      );
    },
    btnNewClicked() {
      this.listbox.ds_file_hs.value = {url:""};
      this.listbox.danhsach_thuebao_theo_makh.value = {};
      this.isAddNew = true;
      this.isUpdate = false;
    },
    btnUpdateClicked() {
      var vhstb_clob = [];
      var ptocheck = ['THUEBAO_ID','THANHTOAN_ID','MA_TB','HDTB_ID','MA_KH','BAOHONG_ID'];
      var dsthuebaochon = this.listbox.danhsach_thuebao_theo_makh.list.filter(a=>a.ischecked);
      for(var tb of dsthuebaochon) {
        var vstb = {};
        for(var p of ptocheck)
          if(tb[p.toLowerCase()]) vstb[p.toUpperCase()] = tb[p.toLowerCase()];
          else vstb[p.toUpperCase()] = null;
        vhstb_clob.push(vstb);
      }
      var pdata = {};
      if(this.isAddNew)
      {
        pdata.vhoso_id = this.listbox.doituong_hoso.value;
        pdata.vloaifile_id = this.listbox.ds_file_hs.value.loaifile_id;
        pdata.vurl = this.listbox.ds_file_hs.value.url;
        pdata.vghichu = this.listbox.ds_file_hs.value.ghichu;
        if(vhstb_clob && vhstb_clob.length>0)
          pdata.vhstb_clob = JSON.stringify(vhstb_clob);
        else pdata.vhstb_clob = null;
        UploadFileHoSoDienTuAPI.them_moi_file_hoso(this.axios,pdata).then((response) => {
          if(response && response.data && response.data.error && response.data.error === 'BSS-00000000' && response.data.message) {
            alert(response.data.message);
            this.isAddNew = false;
            this.isUpdate = false;
            this.listbox.ds_file_hs.value = {url:""};
            this.listbox.danhsach_thuebao_theo_makh.value = {};
            this.treeClicked(this.listbox.doituong_hoso.value);
          } else alert(response.data.message);
        }).catch(function(){
          alert('Thêm mới hồ sơ không thành công! Vui lòng thử lại hoặc liên hệ quản trị viên.');
        });
      }
      else // Update
      {
        if(!vhstb_clob || vhstb_clob.length<=0)
        {
          pdata.vhoso_id = this.listbox.doituong_hoso.value;
          pdata.vloaifile_id = this.listbox.ds_file_hs.value.loaifile_id;
          pdata.vurl = this.listbox.ds_file_hs.value.url;
          pdata.vghichu = this.listbox.ds_file_hs.value.ghichu;
          pdata.vfile_cn = this.listbox.ds_file_hs.value.file_id;
          UploadFileHoSoDienTuAPI.cap_nhat_file_hoso(this.axios,pdata).then((response) => {
            if(response && response.data && response.data.error && response.data.error === 'BSS-00000000' && response.data.message) {
              alert(response.data.message);
              this.isAddNew = false;
              this.isUpdate = false;
              this.listbox.ds_file_hs.value = {url:""};
              this.listbox.danhsach_thuebao_theo_makh.value = {};
              this.treeClicked(this.listbox.doituong_hoso.value);
            } else alert(response.data.message);
          }).catch(function(){
            alert('Cập nhật hồ sơ không thành công! Vui lòng thử lại hoặc liên hệ quản trị viên.');
          });
        }
        else {
          if(confirm('Bạn có chắc chắn muốn gán các thuê bao cho danh sách file hồ sơ đã chọn?'))
          {
            var dsHs = this.listbox.ds_file_hs.list.filter(a=>a.ischecked);
            console.log(dsHs,vhstb_clob);
            if(!dsHs || dsHs.length<=0) {
              for(var tb of vhstb_clob)
                tb.FILE_ID = this.listbox.ds_file_hs.value.file_id;
            }
            else
            {
              var vhstb_clob_arr = [];
              for(var hs of dsHs)
                for(var tb of vhstb_clob)
                {
                  tb.FILE_ID = hs.file_id;
                  vhstb_clob_arr.push(tb);
                }
              vhstb_clob = vhstb_clob_arr;
            }
            console.log(vhstb_clob);
            pdata = { vhstb_clob:JSON.stringify(vhstb_clob) };
            console.log(pdata);
            UploadFileHoSoDienTuAPI.gan_thuebao_file_hoso(this.axios,pdata).then((response) => {
              if(response && response.data && response.data.error && response.data.error === 'BSS-00000000' && response.data.message) {
                alert(response.data.message);
                this.isAddNew = false;
                this.isUpdate = false;
                this.listbox.ds_file_hs.value = {url:""};
                this.listbox.danhsach_thuebao_theo_makh.value = {};
                this.treeClicked(this.listbox.doituong_hoso.value);
              } else alert(response.data.message);
            }).catch(function(){
              alert('Gắn thuê bao với file hồ sơ không thành công! Vui lòng thử lại hoặc liên hệ quản trị viên.');
            });
          }
        }
      }
    },
    btnDeleteClicked() {
      if(confirm('Bạn có chắc chắn muốn xoá file hồ sơ này không?'))
      {
        let pdata = {};
        pdata.vloaifile_id = this.listbox.ds_file_hs.value.loaifile_id;
        pdata.vhoso_id = -1;
        pdata.vfile_cn = this.listbox.ds_file_hs.value.file_id;

        UploadFileHoSoDienTuAPI.xoa_file_hoso(this.axios,pdata).then((response) => {
          if(response && response.data && response.data.error && response.data.error === 'BSS-00000000' && response.data.message) {
            alert(response.data.message);
            this.isAddNew = false;
            this.isUpdate = false;
            this.listbox.ds_file_hs.value = {url:""};
            this.listbox.danhsach_thuebao_theo_makh.value = {};
            this.treeClicked(this.listbox.doituong_hoso.value);
          } else alert(response.data.message);
        }).catch(function(){
          alert('Xoá hồ sơ không thành công! Vui lòng thử lại hoặc liên hệ quản trị viên.');
        });
      }
    },
    btnDeleteTBClicked() {
      if(confirm('Bạn có chắc chắn muốn xoá liên kết thuê bao với hồ sơ này không?'))
      {
        let pdata = {};
        pdata.vloaifile_id = this.listbox.ds_file_hs.value.loaifile_id;
        pdata.vhoso_id = this.listbox.ds_file_hs.value.hoso_id;
        pdata.vfile_cn = this.listbox.ds_file_hs.value.file_id;

        UploadFileHoSoDienTuAPI.xoa_file_hoso(this.axios,pdata).then((response) => {
          if(response && response.data && response.data.error && response.data.error === 'BSS-00000000' && response.data.message) {
            alert(response.data.message);
            this.isAddNew = false;
            this.isUpdate = false;
          } else alert(response.data.message);
        }).catch(function(){
          alert('Xoá hồ sơ không thành công! Vui lòng thử lại hoặc liên hệ quản trị viên.');
        });
      }
    },
    filterDocFiles(){
      this.listbox.ds_file_hs.list = this.listbox.ds_file_hs.list.filter(a=> a.ma_tb==null || a.ma_tb.toLowerCase().indexOf(this.listbox.ds_file_hs.value.ma_tb.toLowerCase())>=0);
    },
    UploadFileHoSo() {
      let formData = new FormData();
      for(var file of this.$refs.files.files) {
        formData.append('files', file);
      }
      UploadFileHoSoDienTuAPI.upload_file_hoso(this.axios,formData,{ headers: { 'Content-Type': 'multipart/form-data' } }).then((response) => {
        if(response && response.data && response.data.data && response.data.error && response.data.error === 'BSS-00000000' && response.data.data) {
          this.listbox.ds_file_hs.value.url = response.data.data.join('|');
        }
      }).catch(function(){
        //console.log('File upload failed!');
      });
    },
    findAccounts() {
      // this.listbox.danhsach_thuebao_theo_makh.value.ma_tb=obj.ma_tb;
      //   this.listbox.danhsach_thuebao_theo_makh.value.ma_kh=obj.ma_kh;
      if(this.listbox.danhsach_thuebao_theo_makh.value.ma_kh)
      {
        UploadFileHoSoDienTuAPI.get_danhsach_thuebao_theo_makh(this.axios,{ "ma_kh": this.listbox.danhsach_thuebao_theo_makh.value.ma_kh }).then((response) => {
          if(response && response.data && response.data.data && response.data.error && response.data.error === 'BSS-00000000' && response.data.data) {
            this.listbox.danhsach_thuebao_theo_makh.list = response.data.data;
            this.listbox.danhsach_thuebao_theo_makh.header = [{name:'ma_tb',text:'Số máy/Acc'},{name:'loaihinh_tb',text:'Loại hình'},{name:'trangthai_tb',text:'Trạng thái'},{text:'Ngày báo hỏng'},{text:'Người báo hỏng'},{text:'Ngày nghiệm thu'},{name:'ischecked',text:'Chọn'},]
          }
          else this.listbox.danhsach_thuebao_theo_makh.list=[];
        });
      }
      else if(this.listbox.danhsach_thuebao_theo_makh.value.ma_tb)
      {
        if(!this.isBaoHong)
          UploadFileHoSoDienTuAPI.get_danhsach_hopdong_theo_matb(this.axios,{ "ma_tb": this.listbox.danhsach_thuebao_theo_makh.value.ma_tb }).then((response) => {
            if(response && response.data && response.data.data && response.data.error && response.data.error === 'BSS-00000000' && response.data.data) {
              this.listbox.danhsach_thuebao_theo_makh.list = response.data.data;
              this.listbox.danhsach_thuebao_theo_makh.header = [{name:'ma_tb',text:'Số máy/Acc'},{name:'ten_loaihd',text:'Loại hình'},{name:'trangthai_hd',text:'Trạng thái'},{text:'Ngày báo hỏng'},{text:'Người báo hỏng'},{name:'ngay_yc',text:'Ngày nghiệm thu'},{name:'ischecked',text:'Chọn'},]
            }
            else this.listbox.danhsach_thuebao_theo_makh.list=[];
          });
        else
          UploadFileHoSoDienTuAPI.get_danhsach_lichsu_baohong_theo_matb(this.axios,{ "thuebao_id": this.listbox.danhsach_thuebao_theo_makh.value.ma_tb }).then((response) => {
            if(response && response.data && response.data.data && response.data.error && response.data.error === 'BSS-00000000' && response.data.data) {
              this.listbox.danhsach_thuebao_theo_makh.list = response.data.data;
              this.listbox.danhsach_thuebao_theo_makh.header = [{name:'ma_tb',text:'Số máy/Acc'},{name:'loaihinh_tb',text:'Loại hình'},{name:'trangthai_tb',text:'Trạng thái'},{name:'ngay_bh',text:'Ngày báo hỏng'},{name:'nguoi_bh',text:'Người báo hỏng'},{name:'ngay_ht',text:'Ngày nghiệm thu'},{name:'ischecked',text:'Chọn'},]
            }
            else this.listbox.danhsach_thuebao_theo_makh.list=[];
          });
      }
      else { 
        this.listbox.danhsach_thuebao_theo_makh.list=[];
        this.listbox.danhsach_thuebao_theo_makh.value={};
      }
    }
  }
};

</script>
