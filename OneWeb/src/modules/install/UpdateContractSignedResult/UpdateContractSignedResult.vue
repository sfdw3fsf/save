<template src="./UpdateContractSignedResult.html"></template>
<style scoped src="./UpdateContractSignedResult.scss"></style>
<script>
import breadcrumb from '@/components/breadcrumb'
import gridview from '@/components/Shared/gridview'
import UpdateContractSignedResultAPI from './UpdateContractSignedResultAPI'
import DatePicker from 'vue2-datepicker'
import 'vue2-datepicker/locale/vi.js'
import 'vue2-datepicker/index.css'
import moment from 'moment'
import frnGiaoPhieuNV from "@/modules/contract/setup/DeclareLandline/popups/frnGiaoPhieuNV.vue";

export default {
  components: { breadcrumb,gridview,DatePicker,frnGiaoPhieuNV },
  name: 'UpdateContractSignedResult',
  props: [ 'kieuSPId' ],
  mounted () {
    UpdateContractSignedResultAPI.get_sp_layds_loaihd_theoid(this.axios,this.loai_hd_id,{}).then(
      (response) => {
          if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000' && response.data.data) {
            this.listbox.loai_hd.value = this.loai_hd_id;
            this.listbox.loai_hd.list = response.data.data;
            // for(var item of this.listbox.list)
            //   item.tien = this.formatNumber(item.tien);
            // this.listbox.header = [{name:'sanpham_id',text:'ID'},{name:'ten_sp',text:'Tên sản phẩm'},{name:'tien',text:'Số tiền',format:"number"},{name:'chitiet_sp',text:'Chi tiết sản phẩm'},]
          }
        }
    );
    UpdateContractSignedResultAPI.get_lay_loai_ketqua_ky_hd(this.axios,{}).then(
      (response) => {
          if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000' && response.data.data) {
            //this.listbox.loai_kq_ky_hd.value = this.loai_hd_id;
            this.listbox.loai_kq_ky_hd.list = response.data.data;
          }
        }
    );
    UpdateContractSignedResultAPI.get_lay_ds_nhanvien_thicong(this.axios,0,{}).then(
      (response) => {
          if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000' && response.data.data) {
            //this.listbox.loai_kq_ky_hd.value = this.loai_hd_id;
            this.listbox.nhanvien_thicong.list = response.data.data;
            this.listbox.nhanvien_thicong.value = response.data.data[0].ID;
          }
        }
    );
  },
  destroyed () {},
  computed: {
    isGiaoViec: {
      get() {
        if(!this.listbox.dsthuebao.value || !this.listbox.dsthuebao.value.phieu_id) {
          return false;
        }
        return true;
      },
    },
    isCapNhat: {
      get() {
        if(!this.listbox.dsthuebao.value || !this.listbox.dsthuebao.value.phieu_id) {
          return false;
        }
        return true;
      }
    }
  },
  data () {
    return {
      header: {
        title: 'cập nhật kết quả yêu cầu ký hợp đồng tại địa chỉ kh',
        list: [
          { name: 'Trang chủ', link: { name: 'Ui.cards' } },
          { name: 'Lập hợp đồng', link: { name: 'Ui.cards' } },
          { name: 'Lắp đặt mới', link: { name: 'Ui.cards' } },
        ]
      },
      ten_tb: '',
      diachi_ld: '',
      ghichu_tb: '',
      nhanvien_yc: '',
      dateFormat: "DD/MM/YYYY",
      datetimeFormat: "DD/MM/YYYY HH:mm:ss",
      dateconfig: {
        altFormat: 'd/m/Y H:i:S',
        altInput: true,
        dateFormat: 'd/m/Y H:i:S',
        allowInput:true,
        enableTime: true,
      },
      loai_hd_id: 36,
      kieu_id: 2, // Mac dinh la 2, thay tam 1 de test
      magd: "",
      listbox: {
        loai_hd: {
          list: [],
          value: 0,
        },
        loai_kq_ky_hd: {
          list: [],
          value: 0,
        },
        nhanvien_thicong: {
          list: [],
          value: 0,
        },
        dsthuebao: {
          list: [],
          value: {phieu_id:11819836}, //gán fix tạm để test
          header: [{name:'ma_gd',text:'Mã GD'},{name:'ten_tb',text:'Tên TB'},{name:'mst',text:'Mã số thuế'},{name:'so_dt',text:'Điện thoại LH'},{name:'thuonghieu',text:'Loại hình'},{name:'diachi_ld',text:'Địa chỉ LĐ'},{name:'ngay_yc',text:'Ngày YC'},{name:'ghichu',text:'Ghi chú'},],
          cols:[
            {fieldName: 'chongd', type: 'checkbox', headerText: 'Chọn GD', allowFiltering: true, allowSorting: true},
            {fieldName: 'ma_gd', headerText: 'Mã GD', allowFiltering: true, allowSorting: true},
            {fieldName: 'ten_tb', headerText: 'Tên TB', allowFiltering: true, allowSorting: true},
            {fieldName: 'mst', headerText: 'Mã số thuế', allowFiltering: true, allowSorting: true},
            {fieldName: 'so_dt', headerText: 'Điện thoại LH', allowFiltering: true, allowSorting: true},
            {fieldName: 'thuonghieu', headerText: 'Loại hình', allowFiltering: true, allowSorting: true},
            {fieldName: 'diachi_ld', headerText: 'Địa chỉ LĐ', allowFiltering: true, allowSorting: true},
            {fieldName: 'ngay_yc', headerText: 'Ngày YC', allowFiltering: true, allowSorting: true},
            {fieldName: 'ghichu', headerText: 'Ghi chú', allowFiltering: true, allowSorting: true},
          ]
        },
        dsnhanvien: {
          list: [],
          value: {},
          header: [{name:'ten_nv',text:'Tên nhân viên'},{name:'so_dt',text:'Số điện thoại'},{name:'nhiemvu',text:'Nhiệm vụ'},{name:'ghichu',text:'Ghi chú'},],
        },
      },
      filterbox: {
        NgayGiaoViec: {
          isEnabled: false,
          value: moment(new Date()).format("DD/MM/YYYY HH:mm:ss"),//new Date(),
        },
        NgayHT: {
          isEnabled: false,
          value: moment(new Date()).format("DD/MM/YYYY HH:mm:ss"),//new Date(),
        },
      },
    }
  },
  methods: {
    LayTT() {
      let pdata = {
        "huonggiao_id": 0,
        "loaihd_id": this.listbox.loai_hd.value,
        "ma_gd": "0",
        "tthd_id": 0
      };
      // if(this.magd) pdata.ma_gd = this.magd; // Trong code WF không thấy sử dụng để tìm kiếm???
      this.loading(true);
      UpdateContractSignedResultAPI.post_lay_ds_phieu_giao_kyhd(this.axios,pdata).then((response) => {
          this.loading(false);
        if(response && response.data && response.data.error_code && response.data.error_code === 'BSS-00000000' && response.data.data) {
          this.listbox.dsthuebao.list = response.data.data;
          for(var item of this.listbox.dsthuebao.list) {
            item.chongd = false;
          }
          //this.listbox.dsthuebao.header = [{name:'ma_gd',text:'Mã GD'},{name:'ten_tb',text:'Tên TB'},{name:'mst',text:'Mã số thuế'},{name:'so_dt',text:'Điện thoại LH'},{name:'thuonghieu',text:'Loại hình'},{name:'diachi_ld',text:'Địa chỉ LĐ'},{name:'ngay_yc',text:'Ngày YC'},{name:'ghichu',text:'Ghi chú'},];
          //,format:"number"
        } //else this.$toast.error(response.data.message);
        else this.listbox.dsthuebao.list = [];
      }).catch(function(){
        this.loading(false);
        this.$toast.error('Có lỗi xảy ra trong quá trình truy vấn thông tin. Vui lòng thử lại hoặc liên hệ người quản trị.');
      });
    },
    CapNhatPhieu() {
      if(!this.filterbox.NgayGiaoViec.isEnabled) {
        this.$toast.error('Hãy nhập ngày giao việc!')
        return;
      }
      if(!this.listbox.nhanvien_thicong.value || this.listbox.nhanvien_thicong.value<=0){
        this.$toast.error('Hãy nhập nhân viên giao việc!')
        return;
      }
      if(!this.listbox.dsthuebao.value || !this.listbox.dsthuebao.value.phieu_id) {
        this.$toast.error("Chưa có danh thuê bao. Bạn hãy kiểm tra lại! ");
        return;
      }
      if(!this.filterbox.NgayHT.isEnabled) {
        this.$toast.error('Hãy nhập ngày hoàn thành!')
        return;
      }
      if(!this.listbox.dsnhanvien.value || !this.listbox.dsnhanvien.value.nhanvien_th_id)
      {
        this.$toast.error('Chưa chọn nhân viên thực hiện!');
        return;
      }
      this.loading(true);
      let pdata = {
          phieu_id:this.listbox.dsthuebao.value.phieu_id,
          nhanvien_gv_id:this.listbox.nhanvien_thicong.value,
          nhanvien_th_id:this.listbox.dsnhanvien.value.nhanvien_th_id,
          ngay_th:this.filterbox.NgayHT.value,
          ngay_gv:this.filterbox.NgayGiaoViec.value,
          ghichu: this.ghichu_tb,
          hdtb_id: (this.listbox.dsthuebao.value && this.listbox.dsthuebao.value.hdtb_id?this.listbox.dsthuebao.value.hdtb_id:0),
          ketquaks: this.listbox.loai_kq_ky_hd.value,
          lydotontc_id: 0,
      };
      UpdateContractSignedResultAPI.post_capnhat_thongtin_phieu(this.axios,pdata).then((response) => {
        if(response && response.data && response.data.error_code && response.data.error_code === 'BSS-00000000' && response.data.message) {
          this.$toast.success('Phiếu cập nhật thành công!');
        } //else this.$toast.error(response.data.message);
      }).catch(function(){
        this.$toast.error('Có lỗi xảy ra trong quá trình lưu thông tin phiếu. Vui lòng thử lại hoặc liên hệ người quản trị.');
      }).finally(()=>{
        this.loading(false);
        this.LayTT();
      });
    },
    async grdDsThueBaoChanged(idx,obj) {
      this.listbox.dsthuebao.value=obj;
      for(var item of this.listbox.dsthuebao.list)
        item.chongd = (item.phieu_id==obj.phieu_id);
      this.magd = obj.ma_gd;
      this.ten_tb = obj.ten_tb;
      this.diachi_ld = obj.diachi_ld;
      this.ghichu_tb = obj.ghichu;
      var rs = await this.$root.context.post('/web-ccdv/khaibaotsl/fn_lay_thongtin_nhanvien',{"nhanvien_id":obj.nhanvien_id});
      if(rs.data) this.nhanvien_yc = rs.data;
      else this.nhanvien_yc = obj.nhanvien_id;
      this.HienThiDSNV();
    },
    grdDsNhanVienChanged(obj) {
      this.listbox.dsnhanvien.value=obj;
    },
    GiaoViec() {
      if(!this.filterbox.NgayGiaoViec.isEnabled || !this.filterbox.NgayGiaoViec.value) {
        this.$toast.error('Hãy nhập ngày giao việc!')
        return;
      }
      if(!this.listbox.nhanvien_thicong.value || this.listbox.nhanvien_thicong.value<=0){
        this.$toast.error('Hãy nhập nhân viên giao việc!')
        return;
      }
      if(!this.listbox.dsthuebao.value || !this.listbox.dsthuebao.value.phieu_id) {
        this.$toast.error("Chưa có danh sách thuê bao. Bạn hãy kiểm tra lại! ");
        return;
      }
      let selected = [this.listbox.dsthuebao.value];
      let dsphieuGV = [];
      let dsma_tb = [];
      let dshdtb_id = [];
      if (selected.length > 0) {
        selected.forEach((item) => {
          dsphieuGV.push(item.phieu_id);
          dsma_tb.push(item.ma_tb);
          dshdtb_id.push(item.hdtb_id);
        });
      }
      let vnhanvien_giao_id = Number(this.listbox.nhanvien_thicong.value);
      let vngaygiao = this.filterbox.NgayGiaoViec.value;
      this.$refs.frnGiaoPhieuNV.frnGiaoPhieuNV(
        dsphieuGV,
        this.$root.token.getNhanVienID(),
        vnhanvien_giao_id,
        vngaygiao
      );
      this.$refs.frnGiaoPhieuNV.vma_tb = this.listbox.dsthuebao.value.ma_tb;
      this.$refs.frnGiaoPhieuNV.vhdtb_id = this.listbox.dsthuebao.value.hdtb_id;
      this.$refs.frnGiaoPhieuNV.dsma_tb = dsma_tb;
      this.$refs.frnGiaoPhieuNV.dshdtb_id = dshdtb_id;
      this.$refs.frnGiaoPhieuNV.huonggiao_id = 0;
      this.$nextTick(() => {
        this.$refs.frnGiaoPhieuNV.load();
        this.$refs.popupfrnGiaoPhieuNV.show();
      });

      // Xu ly form popup
      // this.$bvModal.show('popupTCTTTB');
      // $('#popupTCTTTB').modal();
      // //appending modal background inside the bigform-content
      // $('.modal-backdrop').appendTo('.main-wrapper');
      // //removing body classes to enable click events
      // $('body > .modal-backdrop').remove();
      // Het xu ly form popup
      // this.HienThiDSNV();
    },
    HienThiDSNV() {
      if(!this.listbox.dsthuebao.value || !this.listbox.dsthuebao.value.phieu_id || !this.kieu_id) return;
      this.loading(true);
      let pdata ={
        "kieu_id": this.kieu_id,
        //"nhanvien_id": 0,
        "phieu_id": this.listbox.dsthuebao.value.phieu_id
      }
      UpdateContractSignedResultAPI.post_ds_nhanvien_theo_phieu(this.axios,pdata).then((response) => {
        this.loading(false);
        if(response && response.data && response.data.error_code && response.data.error_code === 'BSS-00000000' && response.data.data) {
          this.listbox.dsnhanvien.list = response.data.data;
          this.listbox.dsnhanvien.header = [{name:'ten_nv',text:'Tên nhân viên'},{name:'so_dt',text:'Số điện thoại'},{name:'nhiemvu',text:'Nhiệm vụ'},{name:'ghichu',text:'Ghi chú'},];
          if(this.listbox.dsnhanvien.list && this.listbox.dsnhanvien.list.length>0) this.listbox.dsnhanvien.value = this.listbox.dsnhanvien.list[0];
          //,format:"number"
        }
        else if(response.data.error==204) {
          this.listbox.dsnhanvien.list= [];
          this.listbox.dsnhanvien.value = {};
        }
        else {
          this.listbox.dsnhanvien.list= [];
          this.listbox.dsnhanvien.value = {};
          this.$toast.error(response.data.message);
        }
      }).catch(function(){
        this.loading(false);
        //this.$toast.error('Có lỗi xảy ra trong quá trình lưu sản phẩm. Vui lòng thử lại hoặc liên hệ người quản trị.');
      });
    },
    btnTraPhieuGiaoSai_Click() {
      //Khong co code, dang hoi LAM
    }
  }
}
</script>
