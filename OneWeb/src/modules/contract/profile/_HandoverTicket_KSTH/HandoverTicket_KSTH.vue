<template src="./HandoverTicket_KSTH.html"></template>
<style scoped src="./HandoverTicket_KSTH.scss"></style>
<script>
import breadcrumb from '@/components/breadcrumb'
import gridview from '@/components/Shared/gridview'
import '@/assets/vendor/jquery/split'
import HandoverTicket_KSTHAPI from './HandoverTicket_KSTHAPI'
import moment from 'moment'
export default {
  components: { breadcrumb,gridview },
  name: 'HandoverTicket_KSTH',
  props: {
      isPopup: {
        type: Boolean
      }
  },
  //props: [ 'kieuSPId' ],
  mounted () {
    Split(['#boxLeft','#boxRight'], {
        sizes: [70, 30],
        gutterSize: 16,
        onDragEnd: function (sizes) {

        },
    });
    HandoverTicket_KSTHAPI.get_kieu_giao_hoso(this.axios,{}).then(
      (response) => {
          if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000' && response.data.data) {
            this.listbox.kieugiao_hs.list = response.data.data;
          }
        }
    );
    HandoverTicket_KSTHAPI.get_donvi_giao_hoso(this.axios,{}).then(
      (response) => {
          if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000' && response.data.data) {
            this.listbox.donvigiao.list = response.data.data;
          }
        }
    );
    HandoverTicket_KSTHAPI.get_trangthai_ph(this.axios,{}).then(
      (response) => {
          if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000' && response.data.data) {
            this.listbox.trangthai_ph.list = response.data.data;
          }
        }
    );
    HandoverTicket_KSTHAPI.get_nhom_file(this.axios,{}).then(
      (response) => {
          if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000' && response.data.data) {
            this.listbox.nhomfile.list = response.data.data;
          }
        }
    );
    HandoverTicket_KSTHAPI.get_loai_file(this.axios,{}).then(
      (response) => {
          if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000' && response.data.data) {
            this.listbox.loaifile.list = response.data.data;
          }
        }
    );
    
  },
  destroyed () {},
  computed: {
    cant_delete:{
      get() { 
        var dsfile = this.gridbox.filehoso.list.filter(x=>x.ischecked);
        return (!dsfile || dsfile.length<=0) 
      },
      
    }
  },
  data () {
    return {
      kieu_sp_id: 1,
      header: {
        title: 'Quản lý giao nhận hồ sơ: Giao cho kiểm soát tổng hợp',
        list: [
          { name: 'Trang chủ', link: { name: 'Ui.cards' } },
          { name: 'Lập hợp đồng', link: { name: 'Ui.cards' } },
          { name: 'Lắp đặt mới', link: { name: 'Ui.cards' } },
        ]
      },
      isThemFile: false,
      fileHoSoURL: "",
      ghiChu: "",
      listbox: {
        kieugiao_hs: {
          list:[],
          value: 0,
        },
        donvigiao: {
          list:[],
          value: 0,
        },
        trangthai_ph: {
          list:[],
          value: 0,
        },
        lo_hs: {
          list:[],
          value: 0,
        },
        nhomfile: {
          list:[],
          value: 0,
        },
        loaifile: {
          list:[],
          value: 0,
        },
      },
      searchDate: {
        isEnabled: false,
        fromDate: new Date(),
        toDate: new Date(),
        dateconfig: {
          altFormat: 'd/m/Y',
          altInput: true,
          dateFormat: 'd/m/Y',
          allowInput:true,
        },
      },
      searchBox: {
        ma_gd: "",

      },
      dateconfig: {
        altFormat: 'd/m/Y H:i:S',
        altInput: true,
        dateFormat: 'd/m/Y H:i:S',
        allowInput:true,
        enableTime: true,
      },
      gridbox: {
        bohoso: {
          list: [],
          header: [],
          value: {},
        },
        filehoso: {
          list: [],
          header: [],
          value: {},
        },
        hdthuebao: {
          list: [],
          header: [],
          value: {},
        },
      }
    }
  },
  methods: {
    TimTTHS() {
      let pdata = {
        "huonggiao_id": 0, //fix
        "kieugiao": this.listbox.kieugiao_hs.value,
        "ma_gd": this.searchBox.ma_gd,
        "phieu_id": 0, //fix
        "quytrinh_id": 550, //fix
        "timngay": this.searchDate.isEnabled?1:0,
        "tths_id": 1, //fix
        "ttph_id": this.listbox.trangthai_ph.value,
        "tungay":this.searchDate.isEnabled?moment(this.searchDate.fromDate).format("DD/MM/YYYY"):null,
        "denngay":this.searchDate.isEnabled?moment(this.searchDate.toDate).format("DD/MM/YYYY"):null,
      }

      HandoverTicket_KSTHAPI.post_layds_bohoso_chuagiao_theo_makh_magd(this.axios, pdata).then(
      (response) => {
          if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000' && response.data.data) {
            this.gridbox.bohoso.list = response.data.data;
            this.gridbox.bohoso.header = [{name:'stt',text:'STT'},{name:'ma_gd',text:'Mã GD'},{name:'ma_kh',text:'Mã KH'},{name:'ten_kh',text:'Tên khách hàng'},{name:'tuyenthu',text:'Tuyến thu'},{name:'trangthai_up_file',text:'UpFile'},{name:'ckb_xacnhan',text:'Đã xác nhận'},]
            // Load danh sach ma lo ho so:
            if(this.listbox.lo_hs.list && this.listbox.lo_hs.list.count>0)
              this.listbox.lo_hs.list = [...new Set(this.gridbox.bohoso.list.map(item => ({ "ID":item.bohs_id,"NAME":item.malo_hs })))]; // Can xem lai
          }
        }
      );
    },
    grdBoHSChanged(obj) {
      this.gridbox.bohoso.value=Object.assign({}, obj);
      this.listbox.lo_hs.value = obj.malo_hs;
      LoadFile_HS();
      if(this.isThemFile) NAP_DS_HOPDONGTB();
    },
    LoadFile_HS() {
      HandoverTicket_KSTHAPI.post_layds_file_trong_hoso(this.axios, {hoso_id:this.gridbox.bohoso.value.bohs_id}).then(
      (response) => {
          if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000' && response.data.data) {
            this.gridbox.filehoso.list = response.data.data;
            for(fhs of this.gridbox.filehoso.list)
              fhs.ischecked = false;
            this.gridbox.filehoso.header = [{name:'ma_tb',text:'Số máy/Acc'},{name:'ma_gd',text:'Mã GD'},{name:'loai_file',text:'Loại file'},{name:'ten_file',text:'Tên file'},{name:'url',text:'Xem/Tải về'},]
          }
        }
      );
    },
    RefreshFileHS(){
      grdFileHSChanged(this.gridbox.filehoso.value);
    },
    grdFileHSChanged(obj) {
      this.gridbox.filehoso.value = Object.assign({}, obj);
      this.ghiChu = obj.ghichu;
      this.fileHoSoURL = obj.url;
      this.listbox.loaifile.value = obj.loaifile_id;
      this.listbox.nhomfile.value = obj.nhomfile_id;
    },
    NAP_DS_HOPDONGTB(){
      HandoverTicket_KSTHAPI.post_lay_ds_hd_tb_theo_hdkh_id(this.axios, {vhdkh_id:this.gridbox.bohoso.value.hdkh_id}).then(
      (response) => {
          if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000' && response.data.data) {
            this.gridbox.hdthuebao.list = response.data.data;
            this.gridbox.hdthuebao.header = [{name:'ma_tb',text:'Số máy/Acc'},{name:'loaihinh_tb',text:'Loại hình'},{name:'trangthai_tb',text:'Trạng thái'},{name:'',text:'Ngày báo hỏng'},{name:'',text:'Người báo hỏng'},{name:'ngay_ht',text:'Ngày nghiệm thu'},] //thiếu ngay_bh và nguoi_bh
          }
        }
      );
    },
    ThemFile() {
      if(!this.gridbox.bohoso.value || !this.gridbox.bohoso.value.hdkh_id)
        alert('Phải chọn hồ sơ để thêm file!');
      else
      {
        this.isThemFile = true;
        NAP_DS_HOPDONGTB();
        //Reset form
        this.ghiChu = "";
        this.fileHoSoURL = "";
        this.listbox.loaifile.value = 0;
        this.listbox.nhomfile.value = 0;
      }
    },
    async XoaFile() {
      var dsfile = this.gridbox.filehoso.list.filter(x=>x.ischecked);
      if(!dsfile || dsfile.length<=0) {
        alert('Bạn phải chọn ít nhất một file để xóa!'); return;
      }
      console.log(dsfile);
      var error_msg = "";
      //Thiếu chức năng xoá file chưa có API, lấy ở UR2.9.004_008
      for(var f of dsfile)
        await HandoverTicket_KSTHAPI.xoa_file_bsh_theo_fileid(this.axios,{"ID":f.file_id}).then((response) => {
          if(response && response.data && response.data && response.data.error && response.data.error === 'BSS-00000000' && response.data.message) {
            //alert(response.data.message);
            return;
          }
          //else alert(response.data.message);
          error_msg+=response.data.message+"\r\n";
        }).catch(function(){
          console.log('Có lỗi xảy ra khi cập nhật file hồ sơ. Vui lòng thử lại hoặc liên hệ quản trị viên.');
          error_msg+=+"Lỗi xử lý API\r\n";
        });
      if(!error_msg) alert('Xoá '+dsfile.length+' file hồ sơ thành công!');
      else alert('Có lỗi khi xoá file hồ sơ: \r\n'+error_msg);
    },
    CAPNHAT_FILE() {
      if(!this.gridbox.bohoso.value) {
        alert('Không tìm thấy bộ hồ sơ để thêm file.');
        return;
      }
      if(!this.listbox.loaifile.value) {
        alert('Bạn chưa chọn loại file.');
        return;
      }
      if(!this.fileHoSoURL) {
        alert('Bạn chưa nhập đường dẫn file');
        return;
      }
      /*
      var kq = tdan.GET_VALUE_FUNC("{?DB1}.CAPNHAT_FILE_HS", "vkieu", 6, "vhoso_id", 0,
                                         "vloaifile_id", vloaifile_id, "url", vurl, "ghichu", txtGhiChu.Text, "vmay_cn",
                                         ttnd.may_cn, "vnguoi_cn", ttnd.ma_nd, "vip_cn", ttnd.ip, "vfile_cn", 0, "vfile_clob", "0", "vbohs_hd", vbohs_id);
                                         */
      var pdata = {
        "bohoso_id": this.gridbox.bohoso.value.bohs_id, //truyen vao
        "file_id": 0,
        "file_url": this.fileHoSoURL,
        "files": [],
        "ghichu": this.ghiChu, //truyen vao
        "hoso_id": 0, //truyen vao // Can xem lai vi cai nay trung voi bohoso_id & theo code mau thi no mac dinh = 0 WinUIProjects/WinUI/WinUIQLHoSo/frmGiaoPhieu_BHS.cs:3484
        "kieu": 6, //fix
        "loaifile_id": this.listbox.loaifile.value //truyen vao
      }
      HandoverTicket_KSTHAPI.post_capnhat_file_hoso(this.axios,pdata).then((response) => {
        if(response && response.data && response.data && response.data.error && response.data.error === 'BSS-00000000' && response.data.message) {
          alert(response.data.message);
        }
        else alert(response.data.message);
      }).catch(function(){
        console.log('Có lỗi xảy ra khi cập nhật file hồ sơ. Vui lòng thử lại hoặc liên hệ quản trị viên.');
      });
    },
    UploadFileHoSo() {
      let formData = new FormData();
      for(var file of this.$refs.files.files) {
        formData.append('files', file);
      }
      HandoverTicket_KSTHAPI.upload_file_hoso(this.axios,formData,{ headers: { 'Content-Type': 'multipart/form-data' } }).then((response) => {
        if(response && response.data && response.data.data && response.data.error && response.data.error === 'BSS-00000000' && response.data.data) {
          this.fileHoSoURL = response.data.data.join('|');
        }
      }).catch(function(){
        //console.log('File upload failed!');
      });
    },
    grdHDTBChanged(obj){
      //this.ghiChu = obj.
      this.gridbox.hdthuebao.value = Object.assign({}, obj);
    },
  }
}
</script>
