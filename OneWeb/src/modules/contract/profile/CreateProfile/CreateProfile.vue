<template src="./CreateProfile.html"></template>
<style src="./CreateProfile.scss"></style>
<script>
import breadcrumb from '@/components/breadcrumb'
import VueFlatPickr from 'vue-flatpickr-component';
import CreateProfileAPI from './CreateProfileAPI'
import gridview from '@/components/Shared/gridview'
//CSS
import '@/assets/vendor/bootstrap-table/bootstrap-table.min.css';
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.css';
//JS
import '@/assets/vendor/jquery/split';
import '@/assets/vendor/bootstrap-table/bootstrap-table.min';
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.min';

import moment from "moment"
//
export default {
  components: {breadcrumb,VueFlatPickr,gridview,CreateProfileAPI},
  name: 'CreateProfile',
  mounted () {
    Split(['#boxLeft', '#boxMid','#boxRight'], {
        sizes: [30, 30, 40],
    })

    this.tu_ngay = moment(this.den_ngay).add(-30, 'd').format("DD/MM/YYYY");
    this.loading(true);
    CreateProfileAPI.trangthai_bo_hoso(this.axios, {}).then((response) => {
    if(response.data.error_code && response.data.error_code === this.success_code) {
        this.listbox.trangthaihoso.list = response.data.data;
        if (this.listbox.trangthaihoso.list.length>0)
        {
          this.listbox.trangthaihoso.value.id = this.listbox.trangthaihoso.list[0].ID;
        }
        }
      }
    );
    CreateProfileAPI.kho_hoso(this.axios, {}).then((response) => {
    if(response.data.error_code && response.data.error_code === this.success_code) {
        this.listbox.khohoso.list = response.data.data;
        if (this.listbox.khohoso.list.length>0)
        {
          this.listbox.khohoso.value.id = this.listbox.khohoso.list[1].ID;
        }
        }
      }
    );
    CreateProfileAPI.nhom_file(this.axios, {}).then((response) => {
    if(response.data.error_code && response.data.error_code === this.success_code) {
        this.listbox.nhomfile.list = response.data.data;
        if (this.listbox.nhomfile.list.length>0)
        {
          this.listbox.nhomfile.value.id = this.listbox.nhomfile.list[1].ID;
        }
        }
      }
    );
    CreateProfileAPI.loai_file(this.axios, {}).then((response) => {
    if(response.data.error_code && response.data.error_code === this.success_code) {
        this.listbox.loaifile.list = response.data.data;
        if (this.listbox.loaifile.list.length>0)
        {
          this.listbox.loaifile.value.id = this.listbox.loaifile.list[1].loaifile_id;
        }
        }
      }
    );






    this.loading(false);
    this.listbox.ds_bohoso.list = [];
    this.listbox.chitiet_hoso.list = [];
    this.listbox.donvinhan_hoso.list = [];
    this.listbox.ds_hopdong_chuacohoso.list = [];
    this.listbox.dsthuebao.list = [];


    this.findClicked()

  },
  data() {
    return {
      dateconfig: {
        altFormat: 'd/m/Y',
        altInput: true,
        dateFormat: 'd/m/Y',
        allowInput: true,
      },
      ngay_ht: new Date(),
      header: {
        title: 'QUẢN LÝ BỘ HỒ SƠ',
        list: [{
            name: 'Lập hợp đồng',
            link: {
              name: 'Ui.cards'
            }
          },
          {
            name: 'Lắp đặt mới',
            link: {
              name: 'Ui.buttons'
            }
          }
        ]
      },
      isNhapMoi: false,
      isCapNhat: false,
      isThemFile: false,
      isXoaFile: false,
      isXoaPhieu: false,
      isLayToanBo:false,
      listbox: {
        trangthaihoso: {
          list: [],
          value: {id: "", name: ""},
        },
        khohoso: {
          list: [],
          value: {id: "",name: ""},
        },
        nhomfile: {
          list: [],
          value: {id: "",name: ""},
        },
        loaifile: {
          list: [],
          value: {loaifile_id: "",loai_file: ""},
        },
        ds_thuebao: {
          list: [],
          header: [],
          value: {},
          isEnabled: true,
        },
        ds_bohoso: {
          list: [],
          header: [ {fieldName: 'hoso_id', headerText: 'Mã bô HS', allowFiltering: true, width:90},
                       {fieldName: 'ma_gd', headerText: 'Mã GD', allowFiltering: true , width:90},
                       {fieldName: 'ma_kh', headerText: 'Mã KH', allowFiltering: true , allowHtml : true , width:90},
                       {fieldName: 'ngaytao', headerText: 'Ngày tạo', allowFiltering: true , allowHtml : true , width:90},
                       {fieldName: 'diachi_kh', headerText: 'Địa chỉ KH', allowFiltering: true , allowHtml : true , width:90},
                       {fieldName: 'ten_dv', headerText: 'Đơn vị tạo', allowFiltering: true , allowHtml : true , width:90},
                       {fieldName: 'ten_nv', headerText: 'Nhân viên tạo', allowFiltering: true , allowHtml : true , width:90},
                       {fieldName: 'ngay_ht', headerText: 'Ngày lưu kho', allowFiltering: true , allowHtml : true , width:90},
                       {fieldName: 'trangthai', headerText: 'Trạng thái', allowFiltering: true , allowHtml : true , width:90},
                       ],
          value: {},
          isEnabled: true,
          checked:[],
        },
        chitiet_hoso: {
          list: [],
          header: [ {fieldName: 'hoso_id', headerText: 'Số máy/Acc', allowFiltering: true, width:90},
                       {fieldName: 'ma_gd', headerText: 'Mã GD', allowFiltering: true , width:90},
                       {fieldName: 'loai_file', headerText: 'Loại file', allowFiltering: true , allowHtml : true , width:90},
                       {fieldName: 'ten_file', headerText: 'Tên file', allowFiltering: true , allowHtml : true , width:90},
                       {fieldName: 'loai_hd', headerText: 'Loại HĐ/PL', allowFiltering: true , allowHtml : true , width:90},
                       {fieldName: 'ngay_yc', headerText: 'Ngày yêu cầu', allowFiltering: true , allowHtml : true , width:90},
                       {fieldName: '', headerText: 'Xem/Tải về', allowFiltering: true , allowHtml : true , width:90},
                       {fieldName: 'nhom_file', headerText: 'Nhóm file', allowFiltering: true , allowHtml : true, isGroupedColumn:true},

                       ],
          value: {},
          isEnabled: true,
          checked: [],
        },
        donvinhan_hoso: {
          list: [],
          header: [ {fieldName: 'hoso_id', headerText: 'Đơn vị nhận', allowFiltering: true, width:90},
                       {fieldName: 'ma_gd', headerText: 'Loại đơn vị', allowFiltering: true , width:90},
                       ],
          value: {},
          isEnabled: true,
        },
        dsthuebao: {
          list: [],
          header: [ {fieldName: 'MA_TB', headerText: 'Số máy/Acc', allowFiltering: true, width:90},
                       {fieldName: 'LOAIHINH_TB', headerText: 'Loại hình', allowFiltering: true , width:90},
                       {fieldName: 'TRANGTHAI_TB', headerText: 'Trạng thái', allowFiltering: true , allowHtml : true , width:90},
                       {fieldName: '', headerText: 'Ngày báo hỏng', allowFiltering: true , allowHtml : true , width:90},
                       {fieldName: 'TEN_TB', headerText: 'Người báo hỏng', allowFiltering: true , allowHtml : true , width:90},
                       {fieldName: 'NGAY_HT', headerText: 'Ngày nghiệm thu', allowFiltering: true , allowHtml : true , width:90},
                       {fieldName: 'LOAI_HD', headerText: 'Loại HĐ', allowFiltering: true , allowHtml : true, isGroupedColumn:true},
                       ],
          value: {},
          isEnabled: true,
        },
        ds_hopdong_chuacohoso: {
          list: [],
          header: [ {fieldName: 'ma_gd', headerText: 'Mã GD', allowFiltering: true, width:90},
                       {fieldName: 'ngay_yc', headerText: 'Ngày YC', allowFiltering: true , width:90},
                       {fieldName: 'ngay_ht', headerText: 'Ngày HT', allowFiltering: true , allowHtml : true , width:90},
                       {fieldName: 'ma_kh', headerText: 'Mã KH', allowFiltering: true , allowHtml : true , width:90},
                       {fieldName: 'ten_kh', headerText: 'Tên KH', allowFiltering: true , allowHtml : true , width:90},
                       ],
          value: {},
          isEnabled: true,
        },
        ds_file_hs: {
          list: [],
          header: [],
          value: {
            url: ""
          },
          isEnabled: false,
        },
      },
      dateConfig: {
        altInput: true,
        dateFormat: 'd/m/Y',
        altFormat: 'd/m/Y'
      },
      success_code: "BSS-00000000",
      ngay_tao: new Date(),
      ngay_ht: new Date(),
      tu_ngay: new Date(),
      den_ngay: new Date(),
      thoihancon_lonhon: 0,
      thoihancon_nhohon: 0,
      magd: '',
      mabohs: '',
      makh: '',
      tenkh: '',
      ghichu: '',
      ghichufile: '',
      hosoid: '',
      bohosoid: '',
      loaifileid: '',
      hdkhid:'',
      dsQuyen:[],
      ckbLayAll_DS:true,
      tths_id:1,
      quytrinh_id:550
    }
  },
  computed: {
    nhohon: {
      get() { return this.isNhoHon; },
      set(value) { this.isNhoHon=value;
      this.thoihancon_nhohon="0";
      }
    },
    lonhon: {
      get() { return this.isLonHon; },
      set(value1) { this.isLonHon=value1;
      this.thoihancon_lonhon="0";
      }
    },
    laytoanbo: {
      get() { return this.isLayToanBo; },
      set(value) { this.isLayToanBo=value;
      },
    }
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
    selectedItems_click: function (dataKeys) {
      this.listbox.chitiet_hoso.checked = dataKeys
      this.listbox.chitiet_hoso.list.forEach(function (item) {
        if (dataKeys.filter(x => x === item.file_id).length > 0) {
          item.ischecked = true
        } else {
          item.ischecked = false
        }
      })
    },
    selectedBoHoSo_click: function (dataKeys) {
      this.listbox.ds_bohoso.checked = dataKeys
      this.listbox.ds_bohoso.list.forEach(function (item) {
        if (dataKeys.filter(x => x === item.bohs_id).length > 0) {
          item.ischecked = true
        } else {
          item.ischecked = false
        }
      })
    },
    btnAddNew(){
      this.isNhapMoi=true;
      this.magd = '';
      this.mabohs = '';
      this.makh = '';
      this.tenkh = '';
      this.ghichu= '';
      this.listbox.trangthaihoso.value.id = 1;
      this.$refs.magd.focus();
    },
    btnCancel(){
      this.isNhapMoi=false;
      this.isCapNhat = false;
      this.magd = '';
      this.mabohs = '';
      this.makh = '';
      this.tenkh = '';
      this.ghichu= '';
    },

    //Xử lý khi click vào nút Lấy TT
    btnGetTT() {
      var pdata = {};
      pdata.ma_thuebao =this.magd;
      this.loading(true);
      console.log(pdata);
      CreateProfileAPI.layds_theo_matb(this.axios, pdata).then((response) => {
        if(response.data.error_code && response.data.error_code === this.success_code) {
          this.listbox.ds_thuebao.list = response.data.data;
          this.listbox.ds_thuebao.header = [{name:'ma_gd',text:'Mã GD'},{name:'ngay_yc',text:'Ngày YC'},{name:'ten_loaihd',text:'Loại HĐ'},{name:'ten_dv',text:'Đơn vị lập HĐ/PL'}];
          console.log('this.listbox.ds_thuebao.list.length',this.listbox.ds_thuebao.list.length);
          if (this.listbox.ds_thuebao.list.length==1)
          {
            this.magd = this.listbox.ds_thuebao.list[0].ma_gd;
            this.mabohs = this.listbox.ds_thuebao.list[0].bohs;
            this.makh = this.listbox.ds_thuebao.list[0].ma_kh;
            this.tenkh = this.listbox.ds_thuebao.list[0].ten_kh;
            this.hdkhid=this.listbox.ds_thuebao.list[0].hdkh_id;
            this.ghichu = '';
            //Lấy danh sách thuê bao
            var pdata = {};
            pdata.vhdkh_id =this.hdkhid;
            this.loading(true);
            console.log(pdata);
            CreateProfileAPI.lay_ds_hd_tb_theo_hdkh_id(this.axios, pdata).then((response) => {
              if(response.data.error_code && response.data.error_code === this.success_code) {
                  this.listbox.dsthuebao.list = response.data.data;
                }
              else {
                  this.listbox.dsthuebao.list = [];
                }
            }).finally(()=>{ this.loading(false); });
          }
        }
        else {
          this.listbox.ds_thuebao.list = [];
        }
      }).finally(()=>{ this.loading(false);});
    },
    //Xử lý khi nhập mã bộ hs enter
    onMaBoHSEnter()
    {
      var pdata = {};
      pdata.hoso_id =this.mabohs;
      pdata.tungay =this.ngay_tao.toLocaleString('en-GB').substring(0,10);
      pdata.denngay =this.ngay_ht.toLocaleString('en-GB').substring(0,10);
      pdata.tim_all =0;
      pdata.tths_id =this.listbox.trangthaihoso.value.id;
      console.log(pdata);
      this.loading(true);
      CreateProfileAPI.layds_bohoso_trong_kho(this.axios, pdata).then((response) => {
        if(response.data.error_code && response.data.error_code === this.success_code) {
          this.listbox.ds_bohoso.list = response.data.data;
          if (this.listbox.ds_bohoso.list.length>0)
          {
            this.makh = this.listbox.ds_bohoso.list[0].ma_kh;
            this.tenkh = this.listbox.ds_bohoso.list[0].ten_kh;
            this.magd = this.listbox.ds_bohoso.list[0].ma_gd;
            this.ngay_tao = this.listbox.ds_bohoso.list[0].ngaytao;
            this.listbox.trangthaihoso.value.id = this.listbox.ds_bohoso.list[0].trangthai_id;
          }
        }
        else {
          this.listbox.ds_bohoso.list = [];
          this.magd = "";
          this.mabohs = "";
          this.makh = "";
          this.tenkh = "";
          this.hdkhid="";
          this.ghichu = "";
          this.listbox.chitiet_hoso.list=[];
        }
      }).finally(()=>{ this.loading(false);});
    },
    //Xử lý khi click vào nút Cập nhật
    btnUpdate(){
      this.isCapNhat = true;
      //Tạo bộ hồ sơ
      var input = [];
      input.push({'hopdong_khachhang_id': this.hdkhid});
      this.loading(true);
      CreateProfileAPI.capnhat_bohoso(this.axios, input).then((response) => {
        if(response.data.error_code && response.data.error_code === this.success_code) {
          this.ShowAlert("Tạo mới hồ sơ thành công!",0)
          //load danh sách bộ hồ sơ
          var pdata = {};
          pdata.hoso_id =this.mabohs;
          pdata.tungay = this.tu_ngay.toLocaleString('en-GB').substring(0,10);
          pdata.denngay = this.den_ngay.toLocaleString('en-GB').substring(0,10);
          if (this.isLayToanBo)
            pdata.tim_all =1;
          else
            pdata.tim_all=0;
          pdata.tths_id =1;
          this.loading(true);
          console.log(pdata);
          CreateProfileAPI.layds_bohoso_trong_kho(this.axios, pdata).then((response) => {
            if(response.data.error_code && response.data.error_code === this.success_code) {
                this.listbox.ds_bohoso.list = response.data.data;
              }
            else {
                this.listbox.ds_bohoso.list = [];
                this.magd = "";
                this.mabohs = "";
                this.makh = "";
                this.tenkh = "";
                this.hdkhid="";
                this.ghichu = "";
                this.listbox.chitiet_hoso.list=[];
              }
          }).finally(()=>{ this.loading(false);});
        }
        else
        {
          this.ShowAlert("Có lỗi trong khi thêm mới hồ sơ",2);
        }
        }).catch(function () {
          this.ShowAlert('Tạo bô hồ sơ không thành công!',2);
          return;
      }).finally(()=>{ this.loading(false); });
    },

    btnAddFile(){
      this.isThemFile=true;
      //Lấy danh sách thuê bao
      var pdata = {};
      pdata.vhdkh_id =this.hdkhid;
      this.loading(true);
      CreateProfileAPI.lay_ds_hd_tb_theo_hdkh_id(this.axios, pdata).then((response) => {
        if(response.data.error_code && response.data.error_code === this.success_code) {
            this.listbox.dsthuebao.list = response.data.data;
          }
        else {
            this.listbox.dsthuebao.list = [];
          }
      }).finally(()=>{ this.loading(false); });
    },

    gridtbaoChanged(obj){
      if(!obj) {
        return;
      }
      this.magd = obj.ma_gd;
      this.mabohs = obj.bohs;
      this.makh = obj.ma_kh;
      this.tenkh = obj.ten_kh;
      this.hdkhid=obj.hdkh_id;
      this.ghichu = '';
      //Lấy danh sách thuê bao
      var pdata = {};
      pdata.vhdkh_id =this.hdkhid;
      this.loading(true);
      console.log(pdata);
      CreateProfileAPI.lay_ds_hd_tb_theo_hdkh_id(this.axios, pdata).then((response) => {
        if(response.data.error_code && response.data.error_code === this.success_code) {
            this.listbox.dsthuebao.list = response.data.data;
          }
        else {
            this.listbox.dsthuebao.list = [];
          }
      }).finally(()=>{ this.loading(false); });
    },

    gridHoSoChanged(obj){
      if(!obj) {
        return;
      }
      this.isCapNhat = true;
      this.isXoaPhieu = true;

      this.magd = obj.ma_gd;
      this.mabohs = obj.ma_bhs;
      this.makh = obj.ma_kh;
      this.tenkh = obj.ten_kh;
      this.hdkhid=obj.hdkh_id;
      this.listbox.trangthaihoso.value.id = obj.trangthai_id;
      this.ngay_tao = (obj.ngaytao == null? new Date(): obj.ngaytao).toLocaleString('en-GB').substring(0,10);
      this.ngay_ht = (obj.ngay_ht == null? new Date(): obj.ngay_ht).toLocaleString('en-GB').substring(0,10);
      this.ghichu = obj.ghichu;

      this.hosoid =obj.hoso_id;
      this.bohosoid =  obj.bohs_id;
      //load chi tiết hồ sơ
      var pdata = {};
      pdata.hoso_id =obj.hoso_id;
      this.loading(true);
      CreateProfileAPI.layds_file_trong_hoso(this.axios, pdata).then((response) => {
        if(response.data.error_code && response.data.error_code === this.success_code) {
            this.listbox.chitiet_hoso.list = response.data.data;
          }
        else {
            this.listbox.chitiet_hoso.list = [];
          }
      }).finally(()=>{ this.loading(false); });
      //load đơn vị nhận hồ sơ
      var input = {};
      input.hoso_id = obj.hoso_id;
      console.log(input);
      this.loading(true);
      CreateProfileAPI.layds_donvi_nhan_hoso(this.axios, input).then((response) => {
        if(response.data.error_code && response.data.error_code === this.success_code) {
            this.listbox.donvinhan_hoso.list = response.data.data;
          }
        else {
            this.listbox.donvinhan_hoso.list = [];
          }
      }).finally(()=>{ this.loading(false); });
       //Lấy danh sách thuê bao
      var pdata = {};
      pdata.vhdkh_id =this.hdkhid;
      this.loading(true);
      console.log(pdata);
      CreateProfileAPI.lay_ds_hd_tb_theo_hdkh_id(this.axios, pdata).then((response) => {
        if(response.data.error_code && response.data.error_code === this.success_code) {
          this.listbox.dsthuebao.list = response.data.data;
        }
        else {
          this.listbox.dsthuebao.list = [];
        }
        }).finally(()=>{ this.loading(false); });
    },

    //xử lý sự kiện khi click vào nút Tìm kiếm
    findClicked(){
      var pdata = {};
      if(this.ckbLayAll_DS== true)
      {
        pdata.loai_id = this.$root.token.getNguoiDungID()
      }
      else
      {
        pdata.loai_id = 0
      }

      pdata.tungay = this.tu_ngay.toLocaleString('en-GB').substring(0,10);
      pdata.denngay = this.den_ngay.toLocaleString('en-GB').substring(0,10);

      console.log('pdata',pdata)

      this.loading(true);
      CreateProfileAPI.layds_hopdong_chuaco_hoso(this.axios, pdata).then((response) => {
        if(response.data.error_code && response.data.error_code === this.success_code) {
          this.listbox.ds_hopdong_chuacohoso.list = response.data.data;
        }
        else {
          this.listbox.ds_hopdong_chuacohoso.list = [];
        }
      }).finally(()=>{ this.loading(false); });
    },

    resetClicked() {
        this.listbox.nhomfile.value.id = "";
        this.listbox.loaifile.value.id = "";
        this.ghichufile = "";
        this.listbox.ds_file_hs.value.url="";
      },
    //Xử lý sự kiện upload file
    UploadFileHoSo() {
        let formData = new FormData();
        for (var file of this.$refs.files.files) {
          formData.append('files', file);
        }
        this.loading(true);
        CreateProfileAPI.upload_file_hoso(this.axios, formData, {
          headers: {
            'Content-Type': 'multipart/form-data'
          }
        }).then((response) => {
          if (response.data.error_code && response.data.error_code === this.success_code) {
            this.listbox.ds_file_hs.value.url = response.data.data.join('|');
            console.log(this.listbox.ds_file_hs.value.url);
          }
        }).catch(function () {
          console.log('Upload file hồ sơ không thành công!');
        }).finally(()=>{ this.loading(false); });
    },


    saveClicked() {
        //Trong phần khảo sát code không thấy có đoạn thêm file này (người khảo sát Nguyễn Văn Tiến)
        if (this.listbox.ds_bohoso.list.length == 0) {
          this.ShowAlert("Không tìm thấy bộ hồ sơ để thêm file.",1);
          return;
        }
        if (this.listbox.loaifile.value.id == -1) {
          this.ShowAlert("Bạn chưa chọn loại file.",1);
          return;
        }
        if (this.listbox.ds_file_hs.value.url == "") {
          this.ShowAlert("Bạn chưa nhập đường dẫn file",1);
          return;
        }
        if (this.hosoid == "") {
          this.ShowAlert("Không tìm thấy bộ hồ sơ để thêm file.",1);
          return;
        }
        var pdata = {};
        pdata.bohoso_id = this.bohosoid;
        pdata.file_id = 0;
        pdata.file_url = this.listbox.ds_file_hs.value.url;
        pdata.ghichu = this.ghichufile;
        pdata.hoso_id = this.hosoid;
        pdata.kieu = 0;
        pdata.loaifile_id = this.listbox.loaifile.value.id;

        var file = [];
        var ptocheck = ['FILE_ID', 'HDTB_ID', 'THUEBAO_ID', 'TRANGTHAI'];
        var vstb = {};
        for (var p of ptocheck)
          vstb[p.toUpperCase()] = 0;
        file.push(vstb);
        if (file && file.length > 0)
          pdata.files = JSON.stringify(file);
        else pdata.files = null;
        console.log(pdata);
        this.loading(true);
        CreateProfileAPI.capnhat_file_hoso(this.axios, pdata).then((response) => {
        }).catch(function () {
          this.ShowAlert('Lưu file hồ sơ không thành công!',2);
        }).finally(()=>{ this.loading(false); });

      },
      //Xử lý khi click nút Xóa phiếu
      btnSetDelete: async function ()
      {
        this.$bvModal.msgBoxConfirm('Bạn có thực sự muốn xóa hồ sơ không?', {
      title: '', size: 'sm', okTitle: 'Đồng ý',cancelTitle: 'Hủy',}).then(async v => {
      if(v) {
        if (this.listbox.ds_bohoso.checked.length>0) {
          var pdata = [];
          for (var hs of this.listbox.ds_bohoso.checked) {
            pdata.push({'bohoso_id': hs.bohs_id});
          }
          console.log(pdata);
          this.loading(true);
          await CreateProfileAPI.xoa_bohoso(this.axios, pdata).then((response) => {
            if(response.data.error_code && response.data.error_code === this.success_code) {
                this.ShowAlert("Xóa hồ sơ thành công!",0)
                //load danh sách bộ hồ sơ
                var pdata = {};
                pdata.hoso_id =this.mabohs;
                pdata.tungay = this.tu_ngay.toLocaleString('en-GB').substring(0,10);
                pdata.denngay = this.den_ngay.toLocaleString('en-GB').substring(0,10);
                if (this.isLayToanBo)
                  pdata.tim_all =1;
                else
                  pdata.tim_all=0;
                pdata.tths_id =1;
                console.log(pdata);
                this.loading(true);
                CreateProfileAPI.layds_bohoso_trong_kho(this.axios, pdata).then((response) => {
                  if(response.data.error_code && response.data.error_code === this.success_code) {
                      this.listbox.ds_bohoso.list = response.data.data;
                    }
                  else {
                      this.listbox.ds_bohoso.list = [];
                      this.magd = "";
                      this.mabohs = "";
                      this.makh = "";
                      this.tenkh = "";
                      this.hdkhid="";
                      this.ghichu = "";
                      this.listbox.chitiet_hoso.list=[];
                    }
                }).finally(()=>{ this.loading(false);});
              }
              else
              {
                this.ShowAlert("Có lỗi trong khi xóa hồ sơ",2);
              }
            }).catch(function () {
              this.ShowAlert('Xóa bộ hồ sơ không thành công!',2);
              return;
          }).finally(()=>{ this.loading(false); });
          //this.btnUpdate();
        }
      }
      });
      },
    //Xử lý sự kiện khi click vào nút Xóa file
    btnFileDelete: async function ()
    {
      this.$bvModal.msgBoxConfirm('Bạn có thực sự muốn xóa file trong hồ sơ không?', {
      title: '', size: 'sm', okTitle: 'Đồng ý',cancelTitle: 'Hủy',}).then(async v => {
      if(v) {
        if(this.listbox.chitiet_hoso.checked.length>0) {
          var pdata = [];
          for(var file of this.listbox.chitiet_hoso.checked)
          {
            //console.log(pdata.file_id);
            pdata.push({'file_id': file.file_id});
          }
          console.log(pdata);
          this.loading(true);
          await CreateProfileAPI.xoa_file_hoso(this.axios,pdata).then((response) => {
            if(response.data.error_code && response.data.error_code === this.success_code)
            {
              this.ShowAlert("Xóa file trong hồ sơ thành công!",0)
              //load lại dữ liệu file hồ sơ sau khi xóa
              var pdata = {};
              pdata.hoso_id = this.hosoid;
              console.log(pdata.hoso_id);
              this.loading(true);
              CreateProfileAPI.layds_file_trong_hoso(this.axios, pdata).then((response) => {
                if(response.data.error_code && response.data.error_code === this.success_code) {
                  this.listbox.chitiet_hoso.list = response.data.data;
                }
                else {
                  this.listbox.chitiet_hoso.list = [];
                }
              }).finally(()=>{ this.loading(false); });
            }
            else
            {
                this.ShowAlert("Có lỗi trong khi xóa file trong hồ sơ",2);
            }
            }).catch(function(){
              this.ShowAlert('Xóa file hồ sơ không thành công!',2);
              return;
          }).finally(()=>{ this.loading(false); });

        }
      }
      });
    },
    //Xử lý khi click chọn tạo bộ hồ sơ
    CreateSetClicked() {
        var dsHopDong = this.listbox.ds_hopdong_chuacohoso.list.filter(a => a.ischecked);
        if (dsHopDong.length > 0) {
          var pdata = [];
          for (var hopdong of dsHopDong) {
            pdata.push({'hopdong_khachhang_id': hopdong.hdkh_id});
          }
          this.loading(true);
          CreateProfileAPI.capnhat_bohoso(this.axios, pdata).then((response) => {
            if(response.data.error_code && response.data.error_code === this.success_code)
            {
              this.ShowAlert("Tạo bộ hồ sơ thành công!",0)
              //Load lại danh sách bộ hồ sơ
              this.btnUpdate();
              //load lại hợp đồng chưa có hồ sơ
              this.findClicked();
            }
            else
            {
              this.ShowAlert('Tạo bộ hồ sơ không thành công!',2);
            }
            }).catch(function () {
              this.ShowAlert('Tạo bô hồ sơ không thành công!',2);
              return;
          }).finally(()=>{ this.loading(false); });
        }
        this.loading(fasle);
      },



//Load Quyền
async Load_quyen()
{
  const res = await  CreateProfileAPI.ds_quyen_nguoidung(this.axios, {
      })
  this.dsQuyen = res.data.data ? res.data.data : []

},



      //Hàm export để thực hiện sau
      btnExportFileClicked() {

      },
    },
};

</script>
