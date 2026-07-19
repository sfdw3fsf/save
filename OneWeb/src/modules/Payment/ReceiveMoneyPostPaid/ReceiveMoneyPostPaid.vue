<template src="./ReceiveMoneyPostPaid.html"></template>
<style src="./ReceiveMoneyPostPaid.scss"></style>
<script>
import breadcrumb from '@/components/breadcrumb'
import DatePicker from 'vue2-datepicker'
import VueFlatPickr from 'vue-flatpickr-component'
import API from './API'
import Vue from 'vue'
import moment from 'moment'
//CSS
import '@/assets/vendor/bootstrap-table/bootstrap-table.min.css';
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.css';
//JS
import '@/assets/vendor/jquery/split'
import '@/assets/vendor/bootstrap-table/bootstrap-table.min'
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.min'
import { getDanhSachViTriTheoNhanVien } from '../../admin/category/Staff/api'
//
export default {
  components: {breadcrumb,VueFlatPickr,API , DatePicker},
  name: 'ReceiveMoneyPostPaid',
  mounted () {
    this.getThongTinNguoiNop();
    this.getThongTinNguoiNhan();
    this.getThongTinBangke();
    this.getDonViQuanLy();
    this.getThongTinNhomNhanVien();
    this.getHopDongChuaThuTien();
    this.HienThi_BangKe();
    this.setButton(-1);

  },
  data () {
    return {
      header: {
        title: 'NHẬN TIỀN TRẢ SAU',
        list: [
          { name: 'Lập hợp đồng', link: { name: 'Ui.cards' } },
          {
            name: 'Lắp đặt mới',
            link: { name: 'Ui.buttons' }
          }
        ]
      },
      settingSelect2: {
        language: 'vi'
      },
      cboNguoiNop : {
          list : [],
          value : 0,
          disable: false,
      },
      cboNguoiNhan : {
          list : [],
          value : 0,
          disable: false,
      },
      cboLoaiBangKe : {
          list : [],
          value : 0,
          disable: false,
      },
      cboLoaiDonViQuanLy : {
          list : [],
          value : '0',
          disable: false,
      },
      cboNhomNhanVien : {
          list : [],
          value : '0',
          disable: false,
      },

      cboCongTacVien : {
          list : [],
          value : '',
          disable: false,
      },
      cboToTC : {
          list : [],
          value : '',
          disable: false,
      },
      cboNhanVienTC : {
        list : [],
        value : '',
        disable: false,
      },

      trangThaiBangKe : {
        MOI : 1
      },

      loai_nv : {
        TRAM_VT : 5
      },
      focusBangKe : {
        nguoitao_id : "",
        bang_ke_id : "",
      },
      dtPhieuD : [],
      txtMaBK : '',
      thangTao : '',
      dateFormat: 'DD/MM/YYYY',
      dateFormatMonth: 'MM/YYYY',
      typeFormat: 'date',
      typeFormatMonth: 'month',
      dateConfig: {
          dateFormat: 'd/m/Y',
          altFormat: 'd/m/Y'
      },
      nhom_lnv : {
        giaoDichVien : 2,
        congTacVien : 6,
        chamSocKhachHang : 9,
        gioiThieuHopDong : 11
      },
      listTable : {
        hop_dong_thu_tien : {
          list : [],
          header : [
            {fieldName: 'isCheck', headerText: '',type : "checkbox", allowFiltering: false, width : '50', textAlign : 'center'},
            {fieldName: 'ma_gd', headerText: 'Mã GD', allowFiltering: true, width : 100}, 
            {fieldName: 'tien' , headerText: 'Tiền hợp đồng', allowFiltering: true, width : 'auto', format : 'N0', type : "Number"}, 
            {fieldName: 'ten_kh', headerText: 'Tên khách hàng', allowFiltering: true, width : 'auto'},
            {fieldName: 'diachi_kh', headerText: 'Địa chỉ khách hàng', allowFiltering: true, width : 'auto'},
            {fieldName: 'ten_nv', headerText: 'Cộng tác viên', allowFiltering: true, width : 'auto'},
            {fieldName: 'ten_dv', headerText: 'Đơn vị', allowFiltering: true, width : 'auto'},
          ],
          value : {},
          isEnabled : true,
        },
        hop_dong_chua_tra_tien: {
          list: [],
          header: [ 
            {fieldName: 'isCheck', headerText: '',type : "checkbox", allowFiltering: false, width : '50', textAlign : 'center'}, 
            {fieldName: 'ma_gd', headerText: 'Mã GD', allowFiltering: true, width : 'auto'}, 
            {fieldName: 'tien' , headerText: 'Tiền hợp đồng', allowFiltering: true, width : 'auto', format : 'N0', type : "Number"}, 
            {fieldName: 'ten_kh', headerText: 'Tên khách hàng', allowFiltering: true, width : 'auto'},
            {fieldName: 'diachi_kh', headerText: 'Địa chỉ khách hàng', allowFiltering: true, width : 'auto'},
            {fieldName: 'ten_nv', headerText: 'Cộng tác viên', allowFiltering: true, width : 'auto'},
            {fieldName: 'ten_dv', headerText: 'Đơn vị', allowFiltering: true, width : 'auto'},
          ],
          value: {},
          isEnabled: true,
        },

        danh_sach_bang_ke : {
          list : [],
          header: [ 
            {fieldName: 'stt', headerText: 'STT', allowFiltering: true, width : 'auto'}, 
            {fieldName: 'ma_bk', headerText: 'Mã bảng kê', allowFiltering: true, width : 'auto'}, 
            {fieldName: 'bangke_id', headerText: 'Bảng kê ID', allowFiltering: true, width : 'auto'}, 
            {fieldName: 'trangthai_bk', headerText: 'Trạng thái BK', allowFiltering: true, width : 'auto'},
            {fieldName: 'nguoi_lap', headerText: 'Người lập', allowFiltering: true, width : 'auto'},
            {fieldName: 'ngay_lap', headerText: 'Ngày lập', allowFiltering: true, width : 'auto'},
            {fieldName: 'nguoi_nop', headerText: 'Người nộp', allowFiltering: true, width : 'auto'},
            {fieldName: 'tongtien', headerText: 'Tổng tiền', allowFiltering: true, width : 'auto', format : 'N0', type : "Number"},
          ],
          value: {},
          isEnabled: true,
        },
        danh_sach_thue_bao : {
          list : [],
          header: [ 
            {fieldName: 'ma_tb', headerText: 'Số máy/Account', allowFiltering: true, width : 'auto'}, 
            {fieldName: 'ma_lt', headerText: 'Số ảo', allowFiltering: true, width : 'auto'}, 
            {fieldName: 'loaihinh_tb', headerText: 'Loại hình', allowFiltering: true, width : 'auto'}, 
            {fieldName: 'tien', headerText: 'Tiền LĐ', allowFiltering: true, width : 'auto', format : 'N0', type : "Number"},
            {fieldName: 'ten_tb', headerText: 'Tên thuê bao', allowFiltering: true, width : 'auto'},
            {fieldName: 'diachi_ld', headerText: 'Địa chỉ lắp đặt', allowFiltering: true, width : 'auto'},
            {fieldName: 'trangthai_hd', headerText: 'Trạng thái HĐ', allowFiltering: true, width : 'auto'},
          ],
          value: {},
          isEnabled: true,
        }
      },

      checkNhom : false,
      checkToTC : false,
      checkThangTao : false,

      enableNhapMoi : false,
      enableGhiLai : false,
      enableXoaBK : false,
      enableHuyBo : false,
      enableNhanBk : false,
      enableIn : false,
      cTong_DaChon : 0,
      TRANGTHAI_BANGKE :{
        MOI : 1,
        DA_NHAN : 2
      },
      stt : 0,
      footerTemp: function () {
        return  { template : Vue.component('footerTemplate', {
            template: `<span>{{data.Custom}}</span>`,
            data () {return { data: {}};}
            })
          }
      },
      footerSumCheck: function () {
        return  { template : Vue.component('footerTemplate', {
            template: `<span>{{data.Custom}}</span>`,
            data () {return { data: {}};}
            })
          }
      }
    }
  },
  computed: {
    aggregates () {
      return [
        {
          field: 'tien',
          type: 'Custom',
          footerTemplate: this.footerTemp,
          customAggregateFn: this.customAggregateFn,
          // format : 'N0'
        },
        {
          field: 'isCheck',
          type: 'Custom',
          footerTemplate: this.footerSumCheck,
          customAggregateFn: this.customAggregateFnCheck,
          // format : 'N0'
        }
      ]
    },
    aggregatesHopDongThuTien : function(data) {
      return [
        {
          field: 'tien',
          type: 'Custom',
          footerTemplate: this.footerTemp,
          customAggregateFn: this.customAggregateFnThuTien,
          // format : 'N0'
        },
        {
          field: 'isCheck',
          type: 'Custom',
          footerTemplate: this.footerSumCheck,
          customAggregateFn: this.customAggregateFnCheckThuTien,
          // format : 'N0'
        }
      ]
    }
  },
  methods : {
    customAggregateFn : function (data) {
      let total = 0;
      var sum;
      var sumSelected;
      if (this.listTable.hop_dong_chua_tra_tien.list.length > 0) {
        sum = this.listTable.hop_dong_chua_tra_tien.list.reduce((pre, curr) => {
          return pre + curr.tien
        }, total)
      } else {
        sum = 0
      }
      let items = this.$refs.hopDongChuaTraTien.getSelectedRecords();

      if (items.length > 0) {
        sumSelected = items.reduce((pre, curr) => {
          return pre + curr.tien
        }, total)
      } else {
        sumSelected = 0
      }

      return this.numberWithCommas(sumSelected) + '/' + this.numberWithCommas(sum)

    },
    customAggregateFnCheck: function(data) {
      let checkLength = this.$refs.hopDongChuaTraTien.getSelectedRecords().length
      let totalCheck = this.listTable.hop_dong_chua_tra_tien.list.length
      return checkLength + '/' + totalCheck
    },
    customAggregateFnThuTien : function (data) {
      let total = 0;
      var sum;
      var sumSelected;
      if (this.listTable.hop_dong_thu_tien.list.length > 0) {
        sum = this.listTable.hop_dong_thu_tien.list.reduce((pre, curr) => {
          return pre + curr.tien
        }, total)
      } else {
        sum = 0
      }
      let items = this.$refs.hopDongThuTien.getSelectedRecords();

      if (items.length > 0) {
        sumSelected = items.reduce((pre, curr) => {
          return pre + curr.tien
        }, total)
      } else {
        sumSelected = 0
      }

      return this.numberWithCommas(sumSelected) + '/' + this.numberWithCommas(sum)

    },
    customAggregateFnCheckThuTien: function(data) {
      let checkLength = this.$refs.hopDongThuTien.getSelectedRecords().length
      let totalCheck = this.listTable.hop_dong_thu_tien.list.length
      return checkLength + '/' + totalCheck
    },
    ShowAlert(mesage)
    {
      this.$toast.error(mesage);
    },

    ShowSuccess(mesage) {
      this.$toast.success(mesage);
    },

    setButton(val) {
      this.enableNhapMoi = false;
      this.enableGhiLai = false;
      this.enableXoaBK = false;
      this.enableHuyBo = false;
      this.enableNhanBk = false;
      if (val == -1) {
        this.enableNhapMoi = true;
      }

      if (val == 0) {
        this.enableNhapMoi = true;
      }

      if (val == 1) {
        this.enableGhiLai = true;
        this.enableHuyBo = true;
      }

      if (val == 2) {
        this.enableNhapMoi = true;
        this.enableXoaBK = true;
      }

      if (val == 3) {
        this.enableNhapMoi = true;
        this.enableXoaBK = true;
        this.enableGhiLai = true;
        this.enableHuyBo = true;
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

    //get thong tin nguoi nop
    getThongTinNguoiNop: async function () {
      let input = {};
      let data = this.GetData(await API.sp_lay_ds_nv_nguoinop(this.axios, input));

      if (data.length > 0) {
        this.cboNguoiNop.list = data.map(item => {
          return {
            id : item.nhanvien_id,
            text : item.ten_ht
          }
        })
        this.cboNguoiNop.value = this.cboNguoiNop.list[0].id
      }
    },

    getThongTinNguoiNhan: async function () {
      let input = {};
      let data = this.GetData(await API.sp_lay_ds_nv_nguoinop(this.axios, input));

      if (data.length > 0) {
        this.cboNguoiNhan.list = data.map(item => {
          return {
            id : item.nhanvien_id,
            text : item.ten_ht
          }
        })
      }
    },

    // lay thong tin bang ke : combox
    getThongTinBangke: async function () {
        let input = {};
        let data = this.GetData(await API.sp_lay_ds_loai_bangke(this.axios, input));
        if (data.length > 0) {
            this.cboLoaiBangKe.list = data.map(item => {
                return {
                    id: item.ttbk_id,
                    text: item.trangthai
                }
            })
            this.cboLoaiBangKe.value = this.cboLoaiBangKe.list[0].id
        }
    },

    // lay thong tin don vi quan ly
    getDonViQuanLy : async function () {
      let input = {};
      let data = this.GetData(await API.lay_ds_donvi_ctv(this.axios, input));
        if (data.length > 0) {
            this.cboLoaiDonViQuanLy.list = data.map(item => {
                return {
                    id: item.donvi_id,
                    text: item.ten_dv
                }
            })
            this.cboLoaiDonViQuanLy.value = this.cboLoaiDonViQuanLy.list[0].id
        }
    },

    //lay thong tin nhom nhan vien
    getThongTinNhomNhanVien : async function () {
      let input = {};
      let data = this.GetData(await API.admin_loai_nv(this.axios, input));
      if (data.length > 0) {
        data.map(item => {
          if (item.NHOMLNV_ID == this.nhom_lnv.congTacVien) {
            let result = {
              id : item.LOAINV_ID,
              text : item.TEN
            }
            this.cboNhomNhanVien.list.push(result);
          }
        })
        this.cboNhomNhanVien.value = this.cboNhomNhanVien.list[0].id ? this.cboNhomNhanVien.list[0].id : '';
      }
    },

    //lay thong tin cong tac vien
    getThongTinCongTacVien: async function (donvi_id,loainv_id) {
      let input = {
        donvi_id : donvi_id,
        loainv_id : loainv_id
      }

      let data = this.GetData(await API.sp_lay_ds_nhanvien_ctv(this.axios, input));

      if (data.length > 0) {
        this.cboCongTacVien.list = data.map(item => {
          return {
            id : item.nhanvien_id,
            text : item.ten_ht
          }
        })
      }
    },

    //danh sach hop dong thu tien
    getHopDongThuTien: async function (obj) {
      this.loading(true)
      try {
        if (!obj) return;
        let bangke_id = obj.bangke_id ? obj.bangke_id : "";
        let nhanVienId = obj.nhanvien_id ? obj.nhanvien_id : "";
        let ttbk_id = obj.ttbk_id ? obj.ttbk_id : "";
        let input = {
          vbangke_id : bangke_id,
        }
        this.cboNguoiNop.value = nhanVienId;

        if (ttbk_id == this.trangThaiBangKe.MOI) {
          this.enableXoaBK = true;
          this.enableGhiLai = true;
          this.enableNhanBk = true;
          this.enableIn = false;
        } else {
          this.enableXoaBK = false;
          this.enableGhiLai = false;
          this.enableNhanBk = false;
          this.enableIn = true;
        }
        let data = this.GetData(await API.ds_hop_dong_thu_tien(this.axios, input));

        if (data && data.length > 0) {
          this.listTable.hop_dong_thu_tien.list = data
        } else {
          this.listTable.hop_dong_thu_tien.list = []
        }
      } catch(err) {
        this.ShowAlert(err)
      } finally {
        this.loading(false)
      }
    },

    async rowBangKeClicked(idx,obj) {
      if (idx < 0) return
      if (obj) {
        // this.getHopDongThuTien(obj);
        this.focusBangKe.nguoitao_id = obj.nguoitao_id ? obj.nguoitao_id : ''
        this.focusBangKe.bang_ke_id = obj.bangke_id ? obj.bangke_id : ''
        this.txtMaBK = obj.ma_bk ? obj.ma_bk : ''
        this.stt = idx
        this.cboNguoiNop.value = obj.nhanvien_id ? obj.nhanvien_id : ''
        if (obj.ttbk_id == this.TRANGTHAI_BANGKE.MOI)
        {
          this.enableXoaBK = true;
          this.enableGhiLai = true;
          this.enableNhanBk = true;
          this.enableIn = false;
        }
        else
        {
          this.enableXoaBK = false;
          this.enableGhiLai = false;
          this.enableNhanBk = false;
          this.enableIn = true;
        }
        this.dtPhieuD = [];
        await this.getHopDongThuTien(obj);
        this.dtPhieuD = this.listTable.hop_dong_thu_tien.list
        if (this.dtPhieuD.length > 0) {
          this.dtPhieuD.map(item => {
            item.CHON = false
          })
        }
      }
    },

    //lay danh sách hop dong chua thu tien
    getHopDongChuaThuTien: async function () {
      this.loading(true)
      try {
        let ctv_id = 0;
        let nhanvientc_id = 0;

        if (this.checkNhom) {
          ctv_id = this.cboCongTacVien.value ? this.cboCongTacVien.value : 0;
        }

        if (this.checkToTC) {
          nhanvientc_id = this.cboNhanVienTC.value ? this.cboNhanVienTC.value : 0;
        }

        let input = {
          vctv_id : ctv_id,
          vnhanvientc_id : nhanvientc_id
        }

        let data = this.GetData(await API.hop_dong_chua_thu_tien(this.axios, input));
        this.listTable.hop_dong_chua_tra_tien.list = data;
      } catch(e) {
        console.log(e)
      } finally {
        this.loading(false)
      }
    },

    HienThi_BangKe: async function() {
      this.loading(true)
      try {
        let nhanvienId = this.$root.token.getNhanVienID();
        let thangTaoText = "";
        if (this.checkThangTao && this.thangTao != "") {
          thangTaoText = moment(this.thangTao, 'D/M/YYYY').format('YYYYMM');
        }
        let input = {
          vthang : thangTaoText,
          vttbk_id : this.cboLoaiBangKe.value,
          vnnhanvien_id : nhanvienId
        }
        let data = this.GetData(await API.danh_sach_bang_ke(this.axios, input));

        if (data && data.length > 0) {
          this.listTable.danh_sach_bang_ke.list = data;
          this.getHopDongThuTien(data[0]);
          this.rowBangKeClicked(data[0], 0)
        } else {
          this.listTable.danh_sach_bang_ke.list = []
          this.getHopDongChuaThuTien();
        }
      } catch (err) {
        this.ShowAlert("Có lỗi : " + err);
      } finally {
        this.loading(false)
      }
    },

    // hopDongTraTienFocus(item) {
    //   console.log('item is ', item);
    // },
    numberWithCommas(n) {
      var parts = n.toString().split(".");
      return (
        parts[0].replace(/\B(?=(\d{3})+(?!\d))/g, ",") +
        (parts[1] ? "." + parts[1] : "")
      );
    },
    refeshTien() {
      let selectedRecord = this.$refs.hopDongChuaTraTien.getSelectedRecords();
      let gridObj = this.$refs.hopDongChuaTraTien.$refs.grid.ej2Instances;
      gridObj.aggregateModule.refresh(selectedRecord);
    },
    refeshHopDongThuTien() {
      let selectedRecordThuTien = this.$refs.hopDongThuTien.getSelectedRecords();
      let gridObjThuTien = this.$refs.hopDongThuTien.$refs.grid.ej2Instances;
      gridObjThuTien.aggregateModule.refresh(selectedRecordThuTien);
    },
    grid_RowSelected() {
      this.refeshTien()
    },

    grid_ItemChanged() {
      this.refeshTien()
    },
    grid_RowSelected_thuTien() {
      this.refeshHopDongThuTien()
    },
    grid_ItemChanged_thuTien() {
      this.refeshHopDongThuTien()
    },

    rowClicked : async function(idx, obj) {
      // let items = this.$refs.hopDongChuaTraTien.getSelectedRecords();
      // console.log('items in row click is ', items)
      // this.customAggregateFn()
      try {
        if (!obj) return;
        let vhdkh_id = obj.hdkh_id ? obj.hdkh_id : "";
        let input = {
          vhdkh_id : vhdkh_id
        }

        let data = this.GetData(await API.danh_sach_thue_bao(this.axios, input));
        if (data.length > 0) {
          this.listTable.danh_sach_thue_bao.list = data;
        }
      } catch(err) {
        this.ShowAlert(err);
      }

    },


    getDanhSachToTC : async function(donvi_id, loainv_id) {
      let input = {
        donvi_id : donvi_id,
        loainv_id : loainv_id
      }

      let data = this.GetData(await API.danh_sach_to_thi_cong(this.axios, input));
      if (data.length > 0) {
        this.cboToTC.list = data.map(item => {
          return {
            id : item.donvi_id,
            text : item.ten_dv
          }
        })
        this.cboToTC.value = this.cboToTC.list[0].id ? this.cboToTC.list[0].id : "";
      }

    },

    getDanhSachNhanVienTC: async function (donvi_id) {
      let input = {
        donvi_id : donvi_id
      };
      let data = this.GetData(await API.danh_sach_nhan_vien_thi_cong(this.axios, input));
      if(data.length > 0) {
        this.cboNhanVienTC.list = data.map(item => {
          return {
            id : item.nhanvien_id,
            text : item.ten_hienthi
          }
        })
        this.cboNhanVienTC.value = this.cboNhanVienTC.list[0].id ? this.cboNhanVienTC.list[0].id : "";
      }
    },


    donViChange:function(val) {
      this.getThongTinCongTacVien(val.id, this.cboNhomNhanVien.value);
      this.getDanhSachToTC(val.id, this.loai_nv.TRAM_VT);
    },
    nhomChange:function(val) {
    },
    toTCChange(val) {
      this.getDanhSachNhanVienTC(val.id);
    },
    btnLayTTMoi_Click () {
      this.HienThi_BangKe()
    },

    tsbtnNhapMoi_Click() {
      this.setButton(1);
      this.txtMaBK = ""
      // this.listTable.hop_dong_chua_tra_tien.list = [];
      this.listTable.danh_sach_thue_bao.list = [];
      this.listTable.hop_dong_thu_tien.list = [];

      this.getHopDongChuaThuTien();
    },

    tsbtnHuyBo_Click() {
      this.setButton(2);
      this.listTable.hop_dong_chua_tra_tien.list = [];
      this.listTable.danh_sach_thue_bao.list = [];
      this.listTable.hop_dong_thu_tien.list = [];

      this.getHopDongChuaThuTien();
    },

    tsbtnXoaBK_Click: async function() {
      if (this.listTable.danh_sach_bang_ke.list.length > 0) {
        if (this.focusBangKe.nguoitao_id != this.$root.token.getNhanVienID()) {
          this.ShowAlert("Bạn không được xóa bảng kê do người khác tạo !");
          return;
        }
        let input = {
          bangke_id : this.focusBangKe.bang_ke_id
        }
        this.GetData(await API.xoa_bang_ke(this.axios, input));
        this.ShowSuccess("Xóa bảng kê trên dữ liệu viễn thông tỉnh thành công !");
        this.HienThi_BangKe();
        this.getHopDongChuaThuTien();
        this.setButton(1);
      }
    },

    tsbtnGhiLai_Click : async function() {
      this.CapNhat();
    },

    CapNhat : async function() {
      if (this.cboNguoiNop.value == "") {
        this.ShowAlert('Bạn chưa chọn nhân viên nộp tiền!');
        return;
      }
      // let vds_bangke = []
      // let dtphieud = []
      if (this.enableNhapMoi == false) {
         let input = {
           'isnhapmoi' : 0,
           'vds_bangke' : await this.TaoDuLieu(true),
           'dtphieud' : this.dtPhieuD
         }
         let data =  this.GetData(await API.fn_capnhat_bangke(this.axios, input));
         if (data) {
           this.ShowSuccess('Thêm mới bảng kê vào cơ sở dữ liệu tỉnh thành công !')
           this.HienThi_BangKe()
           this.setButton(3)
         }
        
      } else {
        if (this.listTable.danh_sach_bang_ke.list.length > 0)
        {
          let input = {
           'isnhapmoi' : 1,
           'vds_bangke' : await this.TaoDuLieu(true),
           'dtphieud' : this.dtPhieuD
         }
          let data =  this.GetData(await API.fn_capnhat_bangke(this.axios, input));
          this.ShowSuccess('Cập nhật bảng kê vào cơ sở dữ liệu tỉnh thành công !')
          this.HienThi_BangKe();
          this.setButton(3);
        }
        else
        {
          this.ShowAlert('Không có bảng kê để cập nhật !')
        }
      }
      
    },

    async tsbtnNhanBK_Click() {
      let input = {
        "vbangke_id": this.focusBangKe.bang_ke_id,
        "vma_bk": this.txtMaBK,
        "nhanvien_id": this.focusBangKe.nguoitao_id,
        "donvi_id": this.$root.token.getDonViID()
      }

      let data = this.GetData(await API.fn_nhanbk_bangke(this.axios, input));

      if (data == 1) {
        this.ShowSuccess('Nhận bảng kê thành công')
      } else {
        this.ShowAlert(data)
      }
      this.HienThi_BangKe()
    },

    async tsbtnIn_Click() {
      let input = {
        "vbangke_id": this.focusBangKe.bang_ke_id
      }

      let data = this.GetData(await API.in_phieuthu_bangke(this.axios, input))

      if (data && data.length > 0) {
        console.log('run')
      }
      console.log('run')
    },

    tsbtnTimKiem_Click() {
      this.getHopDongChuaThuTien();
    },

    TaoDuLieu : async function(themmoi) {
      let maBk = ""
      let arr = []
      let row = {}
      // let stt = 0
      let bangKeId = ""
      if (themmoi) {
        let input = {
          vngaytao : moment(Date.now()).format('DD/MM/YYYY'),
          vnhanvien_id : this.$root.token.getNhanVienID()
        }
        let data = await this.GetData(await API.lay_max_stt(this.axios, input))
        if (data) {
          if (data.length == 0 || data[0]['stt'] == 0) {
            this.stt = 1;
            maBk = this.$root.token.getMaNhanVien() + moment(Date.now()).format('yyyyMMd') + "001";
          } else {
            this.stt = parseInt(data[0]['stt']) + 1;
            if (this.stt == 1) {
              maBk = this.$root.token.getMaNhanVien() + moment(Date.now()).format('yyyyMMd') + "00" + stt.toString();
            } else if (this.stt == 2){
              mabk = this.$root.token.getMaNhanVien() + moment(Date.now()).format('yyyyMMd') + "0" + stt.toString();
            } else if (this.stt == 3) {
              mabk = this.$root.token.getMaNhanVien() + moment(Date.now()).format('yyyyMMd') + stt.toString();
            }
          }
        }
        // bangke_id = getkey.GetKey("BANGKE_ID");
        row.MA_BK = maBk;
        row.STT = this.stt;
      } else {
        row.MA_BK = this.txtMaBK;
        row.STT = this.stt;
      }
      let dsChooseHopDongThuTien = this.$refs.hopDongThuTien.getSelectedRecords();
      if (dsChooseHopDongThuTien.length > 0) {
        this.cTong_DaChon =  dsChooseHopDongThuTien.reduce((pre, curr) => {
          return pre + curr.tien
        }, 0)
      }
      // row.BANGKE_ID = bangke_id;
      let tt_nd = this.$root.token.getNhanVienID()
      let inputGetkey = {
        "keyname": "BANGKE_ID"
      }
      let bangKeObjKey = await API.get_keys(this.axios, inputGetkey);
      if (bangKeObjKey.status && bangKeObjKey.status == 200) {
        bangKeId = bangKeObjKey.data.data ? bangKeObjKey.data.data : ''
      }
      row.BANGKE_ID = bangKeId
      row.NGAY_BK = moment(Date.now()).format('DD/MM/YYYY HH:mm:ss')
      row.NGUOITAO_ID = this.$root.token.getNhanVienID()
      row.NHANVIEN_ID = this.cboNguoiNop.value
      row.TONGTIEN = this.cTong_DaChon

      row.TTBK_ID = 1;
      row.NGAY_CN = moment(Date.now()).format('DD/MM/YYYY HH:mm:ss');
      row.NGUOI_CN = this.$root.token.getUserName();
      row.MAY_CN = await this.$root.token.getMachine();

      row.NGUOINHAN_ID = this.cboNguoiNop.value;
      row.NGAY_NHAN = moment(Date.now()).format('DD/MM/YYYY HH:mm:ss');
      arr.push(row)
      return arr
    },
    btnHDRight()
    {
      // var items = this.listTable.hop_dong_thu_tien.list.filter(a => a.ischecked);
      this.dtPhieuD = []
      let items = this.$refs.hopDongThuTien.getSelectedRecords();
      console.log('item is ', items);
      if (items.length > 0) {
        for (var item of items) {
          this.listTable.hop_dong_chua_tra_tien.list.push(
            {
              'diachi_kh': item.diachi_kh, 
              'hdkh_id': item.hdkh_id,
              'ma_gd' : item.ma_gd,
              'ten_dv' : item.ten_dv,
              'ten_kh' : item.ten_kh,
              'ten_nv' : item.ten_nv,
              'tien' : item.tien,
            }
          );
          this.dtPhieuD.push(
            {
              'DIACHI_KH': item.diachi_kh, 
              'HDKH_ID': item.hdkh_id,
              'MA_GD' : item.ma_gd,
              'TEN_DV' : item.ten_dv,
              'TEN_KH' : item.ten_kh,
              'TEN_NV' : item.ten_nv,
              'TIEN' : item.tien,
            }
          )
        }
      }
      var temps = this.listTable.hop_dong_thu_tien.list.filter(a => !items.includes(a));
      this.listTable.hop_dong_thu_tien.list = [];
      this.listTable.hop_dong_thu_tien.list = temps;
      let gridObj = this.$refs.hopDongChuaTraTien.$refs.grid.ej2Instances;
      gridObj.refesh()
    },

    btnHDRightAll()
    {
      this.dtPhieuD = []
      if (this.listTable.hop_dong_thu_tien.list.length > 0) {
        for (var item of this.listTable.hop_dong_thu_tien.list) {
          this.listTable.hop_dong_chua_tra_tien.list.push(
            {
              'diachi_kh': item.diachi_kh, 
              'hdkh_id': item.hdkh_id,
              'ma_gd' : item.ma_gd,
              'ten_dv' : item.ten_dv,
              'ten_kh' : item.ten_kh,
              'ten_nv' : item.ten_nv,
              'tien' : item.tien,
            }
          );
          this.dtPhieuD.push(
            {
              'DIACHI_KH': item.diachi_kh, 
              'HDKH_ID': item.hdkh_id,
              'MA_GD' : item.ma_gd,
              'TEN_DV' : item.ten_dv,
              'TEN_KH' : item.ten_kh,
              'TEN_NV' : item.ten_nv,
              'TIEN' : item.tien,
            }
          )
        }
        this.listTable.hop_dong_thu_tien.list = [];
        let gridObj = this.$refs.hopDongChuaTraTien.$refs.grid.ej2Instances;
        gridObj.refesh()
        this.refeshTien()
      }
    },

    btnHDLeft()
    {
      // var items = this.listTable.hop_dong_chua_tra_tien.list.filter(a => a.ischecked);
      this.dtPhieuD = []
      let items = this.$refs.hopDongChuaTraTien.getSelectedRecords();
      if (items.length > 0) {
        for (var item of items) {
          this.listTable.hop_dong_thu_tien.list.push(
            {
              'isCheck' : true,
              'diachi_kh': item.diachi_kh, 
              'hdkh_id': item.hdkh_id,
              'ma_gd' : item.ma_gd,
              'ten_dv' : item.ten_dv,
              'ten_kh' : item.ten_kh,
              'ten_nv' : item.ten_nv,
              'tien' : item.tien,
            }
          );
          this.dtPhieuD.push(
            {
              'DIACHI_KH': item.diachi_kh, 
              'HDKH_ID': item.hdkh_id,
              'MA_GD' : item.ma_gd,
              'TEN_DV' : item.ten_dv,
              'TEN_KH' : item.ten_kh,
              'TEN_NV' : item.ten_nv,
              'TIEN' : item.tien,
            }
          )
        }
      }
      var temps = this.listTable.hop_dong_chua_tra_tien.list.filter(a => !items.includes(a));
      this.listTable.hop_dong_chua_tra_tien.list = [];
      this.listTable.hop_dong_chua_tra_tien.list = temps;
      let gridObj = this.$refs.hopDongThuTien.$refs.grid.ej2Instances;
      gridObj.refesh()
      this.refeshTien()
    },

    btnHDLeftAll()
    {
      this.dtPhieuD = []
      if (this.listTable.hop_dong_chua_tra_tien.list.length > 0) {
        for (var item of this.listTable.hop_dong_chua_tra_tien.list) {
          this.listTable.hop_dong_thu_tien.list.push(
            {
              'diachi_kh': item.diachi_kh, 
              'hdkh_id': item.hdkh_id,
              'ma_gd' : item.ma_gd,
              'ten_dv' : item.ten_dv,
              'ten_kh' : item.ten_kh,
              'ten_nv' : item.ten_nv,
              'tien' : item.tien,
            }
          );
          this.dtPhieuD.push(
            {
              'DIACHI_KH': item.diachi_kh, 
              'HDKH_ID': item.hdkh_id,
              'MA_GD' : item.ma_gd,
              'TEN_DV' : item.ten_dv,
              'TEN_KH' : item.ten_kh,
              'TEN_NV' : item.ten_nv,
              'TIEN' : item.tien,
            }
          )
        }
        this.listTable.hop_dong_chua_tra_tien.list = [];
        let gridObj = this.$refs.hopDongThuTien.$refs.grid.ej2Instances;
        gridObj.refesh()
        this.refeshTien()
      }
    },
  }
};

</script>