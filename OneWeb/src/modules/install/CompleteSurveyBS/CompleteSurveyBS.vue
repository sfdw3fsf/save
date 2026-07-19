<template src="./CompleteSurveyBS.html"></template>
<style src="./CompleteSurveyBS.scss"></style>
<script>
import breadcrumb from '@/components/breadcrumb'
import VueFlatPickr from 'vue-flatpickr-component';
import CompleteSurveyAPI from './CompleteSurveyBSAPI'
import gridview from '@/components/Shared/gridview'
import moment from 'moment'
//CSS
import '@/assets/vendor/bootstrap-table/bootstrap-table.min.css';
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.css';
//JS
import '@/assets/vendor/jquery/split'
import '@/assets/vendor/bootstrap-table/bootstrap-table.min'
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.min'
//
import UploadEProfile from '../../contract/profile/UploadEProfile/UploadEProfile'
import ChuyenTo from '../../contract/profile/CompleteProfileChangeAccount/Popups/ChuyenTo'
import GiaoPhieuNhanVien from '@/modules/contract/setup/DeclareLandline/popup/giaophieunhanvien'
import TonPhieu from '../../contract/setup/Payment/components/TonPhieu'
import InBienBan from '@/modules/print/InBienBan/InBienBan'
export default {
  components: {breadcrumb,VueFlatPickr,gridview,GiaoPhieuNhanVien,TonPhieu,ChuyenTo,InBienBan,UploadEProfile },
  name: 'CompleteSurvey',
  mounted () {
  
    this.listbox.ds_ketqua_ks.list = [{ketqua:"Đủ điều kiện thi công",id_ketqua: 1},{ketqua:"Không đủ điều kiện thi công",id_ketqua: 0}]; 
    this.listbox.ds_ketqua_ks.value.id_ketqua = this.listbox.ds_ketqua_ks.list[0].id_ketqua
    //Lấy ds dịch vụ viễn thông
    CompleteSurveyAPI.lay_ds_dvvt(this.axios, {}).then((response) => {
    if(response.data.error_code && response.data.error_code === this.success_code) {
        this.listbox.ds_dichvu_vt.list = response.data.data;
        this.listbox.ds_dichvu_vt.value.dichvuvt_id = response.data.data[0].dichvuvt_id
        this.getLoaiHinhTB(response.data.data[0].dichvuvt_id)
        }
      }
    ); 
   
    //Lấy danh sách loại hợp đồng 
    CompleteSurveyAPI.lay_loai_hd(this.axios, {}).then((response) => {
    if(response.data.error_code && response.data.error_code === this.success_code) {
        this.listbox.ds_loai_hd.list = response.data.data;
        this.listbox.ds_loai_hd.value.loaihd_id = response.data.data[0].loaihd_id;
        }
      }
    ); 
    //Lấy ds nhân viên khảo sát
    CompleteSurveyAPI.lay_ds_nv_theo_dv(this.axios, {}).then((response) => {
    if(response.data.error_code && response.data.error_code === this.success_code) {
        this.listbox.ds_nhanvien_ks.list = response.data.data
        this.listbox.ds_nhanvien_ks.value.nhanvien_id = response.data.data[0].nhanvien_id;
        console.log(response.data.data[0].nhanvien_id)
        }
      }
    ); 
    //Lấy danh sách hợp đồng cần khảo sát
   CompleteSurveyAPI.lay_danhsach_hd_ks_v3(this.axios, {loaihd_id:0}).then((response) => {
        if(response.data.error_code && response.data.error_code === this.success_code) {
          this.listbox.ds_phieuyc_khaosat.list = response.data.data;
          console.log(this.listbox.ds_phieuyc_khaosat.list)
        }
        else {
          this.listbox.ds_phieuyc_khaosat.list = [];
        }
      }); 
      
  },
   
  data () {
    return {
      dateconfig: {
        altFormat: 'd/m/Y',
        altInput: true,
        dateFormat: 'd/m/Y',
        allowInput:true,
      },
      ngay_ht: new Date(),
      ngay_gv: moment(new Date()).format('DD/MM/YYYY HH:mm:ss'),
      ngay_yc: new Date(),
      header: {
        title: 'CẬP NHẬT HOÀN THÀNH KHẢO SÁT',
        list: [
          { name: 'Lập hợp đồng', link: { name: 'Ui.cards' } },
          {
            name: 'Lắp đặt mới',
            link: { name: 'Ui.buttons' }
          }
        ]
      },
      isAddNew: false,
      isUpdate: false,
      isReturn: false,
      isPrint: false,
      isComplete: false,
      listbox: {
        ds_dichvu_vt: {
          list: [],
          value: { dichvuvt_id: 0 , ten_dvvt:"" },
          isEnabled: false,
        },
        ds_loai_hd: {
          list: [],
          value: { loaihd_id: 0 , ten_loaihd:"" },
          isEnabled: false,
        },
         ds_loaihinh_tb: {
          list: [],
          value: { loaitb_id: 0 , ten_loaihinh:"" },
          isEnabled: false,
        },
        ds_nhanvien_ks: {
          list: [],
          value: { nhanvien_id:"", ten_nv:"" },
          isEnabled: false,
        },
        ds_ketqua_ks: {
          list: [],
          value: { ketqua:"", id_ketqua:0 },
          isEnabled: false,
        },
        ds_phieuyc_khaosat: {
          list: [],
          header:[ {fieldName: 'ma_gd', headerText: 'Mã GD', allowFiltering: true,freeze:"left"}, 
                      {fieldName: 'ten_kh', headerText: 'Tên khách hàng', allowFiltering: true,freeze:"left"},
                      {fieldName: 'ten_kieuld', headerText: 'Kiểu YC', allowFiltering: true , allowHtml : true,freeze:"left"},
                      {fieldName: 'ma_tb', headerText: 'Số máy/Acc', allowFiltering: true , allowHtml : true,freeze:"left"},
                      {fieldName: 'diachi_ld', headerText: 'Địa chỉ lắp đặt', allowFiltering: true , allowHtml : true,freeze:"left"},                                
                      {fieldName: 'ten_dvvt', headerText: 'Dịch vụ', allowFiltering: true , allowHtml : true},
                      {fieldName: 'loaihinh_tb', headerText: 'Loại hình', allowFiltering: true , allowHtml : true}, 
                      {fieldName: 'ngay_yc', headerText: 'Ngày YC', allowFiltering: true , allowHtml : true, textAlign: 'right'},
                      {fieldName: 'ngaygiao ', headerText: 'Ngày giao', allowFiltering: true , allowHtml : true,textAlign: 'right'}, 
                      {fieldName: 'dv_hen ', headerText: 'Đơn vị hẹn', allowFiltering: true , allowHtml : true},
                      {fieldName: 'giohen_tu', headerText: 'Hẹn từ ', allowFiltering: true , allowHtml : true},
                      {fieldName: 'giohen_den', headerText: 'Hẹn đến ', allowFiltering: true , allowHtml : true}, 
                      {fieldName: 'goi_kt', headerText: 'Gói KT', allowFiltering: true , allowHtml : true},
                      {fieldName: 'chitieu_tg', headerText: 'Chỉ tiêu TG', allowFiltering: true , allowHtml : true}, 
                      {fieldName: 'thoigian_ck', headerText: 'Thời gian cam kết', allowFiltering: true , allowHtml : true},
                      {fieldName: 'gio_conlai', headerText: 'Giờ còn lại', allowFiltering: true , allowHtml : true},
                      {fieldName: 'ma_plkh', headerText: 'Mã PLKH', allowFiltering: true , allowHtml : true},
                      {fieldName: 'dv_giao', headerText: 'Đơn vị giao', allowFiltering: true , allowHtml : true},
                      {fieldName: 'lydoton_tc', headerText: 'Lý do tồn', allowFiltering: true , allowHtml : true},  
                      {fieldName: 'ghichu_ton', headerText: 'Ghi chú tồn', allowFiltering: true , allowHtml : true},  
                      {fieldName: 'ghichu', headerText: 'Ghi chú', allowFiltering: true , allowHtml : true},  
                      {fieldName: 'soluong', headerText: 'Số lượng', allowFiltering: true , allowHtml : true},                                            
                      ],
          value: {},
          isEnabled: true,
        },
         ds_nhanvien_phieu: {
          list: [],
          header:[ {fieldName: 'ten_nv', headerText: 'Tên nhân viên', allowFiltering: true}, 
                      {fieldName: 'so_dt', headerText: 'Số điện thoại', allowFiltering: true},
                      {fieldName: 'nhiemvu', headerText: 'Nhiệm vụ', allowFiltering: true , allowHtml : true},
                      {fieldName: 'ghichu', headerText: 'Ghi chú', allowFiltering: true , allowHtml : true},  
                      ],
          value: {},
          isEnabled: true,
        },
      },

      dateConfig: {
        altInput: true,
        dateFormat: 'd/m/Y',
        altFormat: 'd/m/Y'
      },
      from_date: new Date(),
      to_date: new Date(),
      success_code: "BSS-00000000",
      //parameters for update
      magd: "",
      tenkh: "",
      diachikh: "",
      diachild: "",
      kieuld: "",
      so_dt:"",
      somayacc: "",
      loaihdid: 0,
      hdtb_id:0,
      modelIn:{},
      hdkh_id: 0,
      nd_giao:"",
      trangThaiHopDongId:0,
      nd_thuchien:"",
      mday: 0,
      doicap: "",
      soluong: 0,
      hopcap: "",
      ghichu:"",
      phieuid:"",
      dichvuvtid: 0,
      chkNgayGV: false,
      chkNgayHT: false,
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
    refresh_grid (){
       CompleteSurveyAPI.lay_danhsach_hd_ks_v3(this.axios, {loaihd_id:0}).then((response) => {
        if(response.data.error_code && response.data.error_code === this.success_code) {
          this.listbox.ds_phieuyc_khaosat.list = response.data.data;
        }
        else {
          this.listbox.ds_phieuyc_khaosat.list = [];
        }
      }); 
    },
     //Lấy danh sách loại hình thuê bao
     async getLoaiHinhTB (dvvtid) {
            try 
            {
                await CompleteSurveyAPI.getLoaiHinhTB(this.axios, dvvtid).then((res) => {
                    if (
                        res.data.error_code === "BSS-00000000" &&
                        res.data.data !== undefined &&
                        res.data.data.length > 0
                    ) {
                        this.listbox.ds_loaihinh_tb.list = res.data.data;
                        this.listbox.ds_loaihinh_tb.value.loaitb_id = res.data.data[0].ID.toString();
                    }
                });
            }
            catch(err)
            {
               this.$toast.error("Lỗi lấy danh sách loại hình: "+ err.message);
            }
        },
    dichvuvtchange() {
      var temp= this.listbox.ds_dichvu_vt.list.filter(a => a.id == this.listbox.ds_dichvu_vt.value.dichvuvt_id);
      this.loading(true);
       CompleteSurveyAPI.lay_danhsach_hd_ks_v3(this.axios, {loaihd_id:0}).then((response) => {
         this.loading(false);
        if(response.data.error_code && response.data.error_code === this.success_code) {
          this.listbox.ds_phieuyc_khaosat.list = response.data.data;
          this.listbox.ds_phieuyc_khaosat.list = this.listbox.ds_phieuyc_khaosat.list.filter(a=> a.ten_dvvt==null || a.ten_dvvt.toLowerCase().indexOf(temp[0]["text"].toLowerCase())>=0);
          //this.listbox.ds_phieuyc_khaosat.header = [{name:'ma_gd',text:'Mã GD'},{name:'ten_kh',text:'Tên khách hàng'},{name:'diachi_ld',text:'Địa chỉ lắp đặt'},{name:'ten_kieuld',text:'Kiểu yêu cầu'},{name:'loaihinh_tb',text:'Loại hình TB'}]
        }
        else {
          this.listbox.ds_phieuyc_khaosat.list = [];
        }
      }); 
    },
    Update_NDTH(){
      let input =  {
          "nd_thuchien": this.nd_thuchien,
          "phieu_id": this.phieuid
        }
        CompleteSurveyAPI.update_nd_thuchien(this.axios,input).then((response) => {
            if(response.data.error_code && response.data.error_code === this.success_code) {
                this.ShowAlert("Cập nhật nội dung thực hiện thành công",0);

            }
        }); 
    },
    onCheckChangeGV(event){
        this.chkNgayGV = event.target.checked;
        if(event.target.checked == true && this.chkNgayHT == true){
           this.isComplete = true
        }else 
        {
          this.isComplete = false
        }
    },
    onCheckChangeHT(event){
        this.chkNgayHT = event.target.checked;
        if(event.target.checked == true && this.chkNgayGV == true){
           this.isComplete = true
        }else 
        {
          this.isComplete = false
        }
        
    },
    async gridChanged(obj){
      if(!obj) {
        this.isUpdate = false;
        this.isReturn = false;
        this.isPrint = false;
        this.isComplete = false;
        return; 
      }
      else 
      {
        this.isUpdate = true;
        this.isReturn = true;
        this.isPrint = true;
        if(this.chkNgayGV == false || this.chkNgayHT == false){
          this.isComplete == false
        }
      }
       //hiển thị thông tin trong Cập nhật thông tin khảo sát
      this.somayacc = obj.hdtb_id;
      this.loaihdid = obj.loaihd_id;
      this.ma_tb = obj.ma_tb;
      this.phieuid = obj.phieu_id;
      this.dichvuvtid = obj.dichvuvt_id;
      //Hiển thị thông tin thuê bao
      this.magd = obj.ma_gd;
      this.tenkh = obj.ten_kh;
      this.diachikh = obj.diachi_kh;
      this.diachild = obj.diachi_ld;
      this.kieuld = obj.ten_kieuld;
      this.soluong = obj.soluong;
      this.doicap = obj.doicap;
      this.ghichu = obj.ghichu;
      this.hdtb_id= obj.hdtb_id;
      this.mday = obj.culy;
      this.nd_giao = obj.nd_giao;
      this.nd_thuchien = obj.nd_thuchien;
      this.so_dt = obj.so_dt;
      this.ngay_yc = moment(obj.ngay_yc).format('DD/MM/YYYY');
      this.hopcap = obj.hopcap;
      if(obj.ketquaks)
        this.listbox.ds_ketqua_ks.value.id_ketqua =  obj.ketquaks;
      this.listbox.ds_dichvu_vt.value.dichvuvt_id = obj.dichvuvt_id;
      this.listbox.ds_loai_hd.value.loaihd_id = obj.loaihd_id;
      await this.getLoaiHinhTB(obj.dichvuvt_id);
      this.listbox.ds_loaihinh_tb.value.loaitb_id = parseInt(obj.loaitb_id.toString());
      let dsNhanVien = await this.lay_ds_nhanvien_theo_phieu_id();
      this.listbox.ds_nhanvien_phieu.list = dsNhanVien;
    },
    async lay_ds_nhanvien_theo_phieu_id () {
      let ds = []
      await CompleteSurveyAPI.lay_ds_nhanvien_theo_phieu_id(this.axios, {
        'phieu_id': this.phieuid,
        'nhanvien_id': Number.parseInt(this.$root.token.getNhanVienID()),
        'kieu_id': 2
      }).then(response => {
        if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data.length > 0) {
          ds = response.data.data
          this.listbox.ds_nhanvien_phieu.list = ds;
        }
      })
      return ds
    },
    async rowClicked(idx,obj)
    {
      //console.log(obj);
      this.index = idx;
      if(!obj) { 
        return; 
      }
      //hiển thị thông tin trong Cập nhật thông tin khảo sát
      this.somayacc = obj.hdtb_id;
      this.loaihdid = obj.loaihd_id;
      this.phieuid = obj.phieu_id;
      this.dichvuvtid = obj.dichvuvt_id;
      //Hiển thị thông tin thuê bao
      this.magd = obj.ma_gd;
      this.tenkh = obj.ten_kh;
      this.diachikh = obj.diachi_kh;
      this.diachild = obj.diachi_ld;
      this.kieuld = obj.ten_kieuld;
      this.soluong = obj.soluong;
      this.doicap = obj.doicap;
      this.mday = obj.culy;
      this.nd_giao = obj.nd_giao;
      this.nd_thuchien = obj.nd_thuchien;
      this.hopcap = obj.hopcap;
      this.so_dt = obj.so_dt;
      this.ghichu = obj.ghichu;
       this.ngay_yc = moment(obj.ngay_yc).format('DD/MM/YYYY');
      if(obj.ketquaks)
        this.listbox.ds_ketqua_ks.value.id_ketqua =  obj.ketquaks;
      this.listbox.ds_dichvu_vt.value.dichvuvt_id = obj.dichvuvt_id;
      this.listbox.ds_loai_hd.value.loaihd_id = obj.loaihd_id;
      await this.getLoaiHinhTB(obj.dichvuvt_id);
      this.listbox.ds_loaihinh_tb.value.loaitb_id = parseInt(obj.loaitb_id);
    },


    filterDocFiles(){
      this.listbox.ds_phieuyc_khaosat.list = this.listbox.ds_phieuyc_khaosat.list.filter(a=> a.hdtb_id==null || a.hdtb_id.toLowerCase().indexOf(this.listbox.ds_phieuyc_khaosat.value.hdtb_id.toLowerCase())>=0);
    },

    btnUpdateClicked() {
       if(this.chkNgayGV == false && this.chkNgayHT == false){
        this.ShowAlert("Chưa chọn ngày giao việc và ngày hoàn thành",1);
        return;
      }
      var pdata = {};
      pdata.culy = this.mday;
      pdata.dichvuvt_id = this.dichvuvtid;
      pdata.doicap = this.doicap;
      pdata.ghichu = this.ghichu;
      pdata.hdtb_id = this.somayacc;
      pdata.hopcap = this.hopcap;
      pdata.ketquaks = this.listbox.ds_ketqua_ks.value.id_ketqua;
      pdata.loaihd_id = this.loaihdid;
      pdata.ngay_ht = this.ngay_ht.toLocaleString('en-GB').substring(0,10) + this.ngay_ht.toLocaleString('en-GB').substring(11,20);
      pdata.nhanvien_id = this.listbox.ds_nhanvien_ks.value.nhanvien_id;
      pdata.phieu_id = this.phieuid;
      this.loading(true);
      CompleteSurveyAPI.capnhat_khaosat(this.axios,pdata).then((response) => {
        this.loading(false);
        if(response.data.error_code && response.data.error_code === this.success_code) {
            this.ShowAlert("Đã cập nhật thông tin khảo sát",0);
            this.refresh_grid();
          } else this.ShowAlert(response.data.message);
        }).catch(function(){
          this.ShowAlert('Cập nhật kết quả khảo sát không thành công! Vui lòng thử lại hoặc liên hệ quản trị viên.',2);
        }); 
    },

    btnCompleteClicked()
    {
      var pdata = {};
      pdata.hdtb_id = this.somayacc;
      pdata.ketquaks = this.listbox.ds_ketqua_ks.value.id_ketqua;
      pdata.loaihd_id = this.loaihdid;
      pdata.ngay_ht = this.ngay_ht.toLocaleString('en-GB').substring(0,10) + this.ngay_ht.toLocaleString('en-GB').substring(11,20);
      pdata.phieu_id = this.phieuid;
      this.loading(true);
      CompleteSurveyAPI.capnhat_hoanthanh_khaosat(this.axios,pdata).then((response) => {
        this.loading(false);
        if(response.data.error_code && response.data.error_code === this.success_code) {
            this.ShowAlert("Đã trả kết quả khảo sát",0);
            this.refresh_grid();
          } else this.ShowAlert(response.data.message);
        }).catch(function(){
          this.ShowAlert('Cập nhật kết quả khảo sát không thành công! Vui lòng thử lại hoặc liên hệ quản trị viên.',2);
        }); 
    },

    btnReturnClicked(){
      var pdata = {};
      pdata.phieu_id = this.phieuid;
      this.loading(true);
      CompleteSurveyAPI.traphieu_giaosai(this.axios,pdata).then((response) => {
        this.loading(false);
        if(response.data.error_code && response.data.error_code === this.success_code) {
            this.ShowAlert("Đã trả phiếu khảo sát giao sai",0);
            this.refresh_grid();
          } else this.ShowAlert(response.data.message);
        }).catch(function(){
          this.ShowAlert('Trả phiếu khảo sát không thành công! Vui lòng thử lại hoặc liên hệ quản trị viên.',2);
        }); 
    },
    giaoviec() {
        this.$refs.formGiaoPhieu.init()
        this.$refs.giaoPhieuNhanVien.show()
    },
     showPopupTonPhieu () {
      if (this.phieuid === 0) {
        this.$root.$toast.warning('Hãy chọn phiếu trước để nhập lý do tồn!')
        return
      }
      const arrPhieu = []
      if (!arrPhieu.includes(this.phieuid)) arrPhieu.push(this.phieuid)
      this.$refs.popupTonPhieu.showPhieuTon(this.phieuid, this.trangThaiHopDongId, arrPhieu)
    },
     closePopupTonPhieu () {
      this.$refs.popupTonPhieu.closePhieuTon()
    },
    chuyenTo () {
      try {
        this.$refs.popupChuyenTo.openDialog(this.phieuid, this.hdtb_id)
      } catch (error) {
        this.$root.$toast.error('Xảy ra lỗi khi thực hiện chuyển tổ')
      }
    },
    async closePopupChuyenTo () {
      this.refresh_grid();
    },
    async tsbtnInBB_Click () {
      let  hdkh = [];
      hdkh = this.GetData(await CompleteSurveyAPI.Lay_ds_hopdong_tb_theo_hdtb_id(this.axios, {
                    "in_hdtb_id": this.hdtb_id
                    }));
      console.log(hdkh)
      let nhdkhid = [hdkh[0].hdkh_id];
      let nhdtbid = [this.hdtb_id];
      let nphieuid = [this.phieuid];
     
      this.modelIn.n_phieu_id = nphieuid.join(',');
      this.modelIn.n_hdkh_id = nhdkhid.join(',');
      this.modelIn.n_hdtb_id = nhdtbid.join(',');
      console.log(this.modelIn);

      this.$refs.popupInBB.showModal();
    },
    tsbtUpHS_Click () {
        this.$refs.hosodientu.show()
    },
     //Hàm call api
        GetData: function (response) {
            if (response.data.error === 200 || response.data.error === '200') {
                return response.data.data
            } else {
                console.log(response.data.error_code)
            }
            return []
        },
    
  },
};

</script>
