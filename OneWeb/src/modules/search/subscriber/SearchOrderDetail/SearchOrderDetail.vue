<template src="./SearchOrderDetail.html"></template>
<style src="./SearchOrderDetail.scss"></style>
<script>
import SearchOrderDetailAPI from './SearchOrderDetailAPI'
import breadcrumb from '@/components/breadcrumb'
import pop_diachild from '@/modules/search/subscriber/SearchInstallDetail/popup/DiaChiLD.vue'
import pop_goidv from '@/modules/search/subscriber/SearchInstallDetail/popup/GoiDichVu.vue'
import pop_phieuton from '@/modules/search/subscriber/SearchInstallDetail/popup/TonPhieu.vue'
import pop_chitiethopdong from '@/modules/search/subscriber/SearchInstallDetail/popup/ChiTietHopDongPhuLuc.vue'
import ThongTinEmail from '@/modules/contract/setup/ChangeContractOwner/popup/ThongTinEmail.vue'
import LienHe from '@/modules/contract/setup/ChangeContractOwner/popup/ThongTinLienHe.vue'
import ThongTinHenKhachHangModal from '@/modules/contract/setup/SurveyRequest/popups/ThongTinHenKhachHang/ThongTinHenKhachHangModal.vue'
import VatTuThueBao from '@/modules/search/subscriber/SearchInstallDetail/popup/TraCuuVatTuHDTB.vue'
import Port from '@/modules/search/subscriber/SearchInstallDetail/popup/TraCuuPortHDTB.vue'
import GiaoLaiPhieu from './Popup/GiaoPhieuTC.vue'
import HuyDonHang from './Popup/HuyDonHang.vue'
import DoiGoi from './Popup/DoiGoi.vue'
import BienDong from './Popup/PopupBienDong.vue'
import XuatPhieu from '@/modules/print/InBienBan/InBienBan.vue'
import DSDonHang from './Popup/DSDonHang.vue'
import moment from 'moment'
//CSS
import '@/assets/vendor/bootstrap-table/bootstrap-table.min.css';
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.css';
import 'bootstrap/dist/css/bootstrap.css' 
import 'bootstrap-vue/dist/bootstrap-vue.css'
//JS
import '@/assets/vendor/jquery/split'
import '@/assets/vendor/bootstrap-table/bootstrap-table.min'
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.min'
import { log } from 'pdfmake/build/pdfmake'
//
export default {
    components: { breadcrumb, pop_diachild, pop_goidv, pop_phieuton, 
    pop_chitiethopdong, ThongTinEmail, LienHe, ThongTinHenKhachHangModal, VatTuThueBao, Port, GiaoLaiPhieu
    , HuyDonHang, DoiGoi, BienDong, XuatPhieu, DSDonHang},
    name: 'SearchOrderDetail', 
  mounted () {
    //load ds loại hình thuê bao
    SearchOrderDetailAPI.dich_vu(this.axios, {}).then((response) => {
    if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000' && response.data.data) {
        this.listbox.dich_vu.list = response.data.data;
        }
      }
    );
    //load ds tốc độ kênh
    SearchOrderDetailAPI.toc_do_kenh(this.axios, {}).then((response) => {
    if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000' && response.data.data) {
        this.listbox.toc_do_kenh_CIR.list = response.data.data;
        this.listbox.toc_do_kenh_PIR.list = response.data.data;
        this.listbox.toc_do_kenh_NIX.list = response.data.data;
        this.listbox.toc_do_kenh_ISP.list = response.data.data;
        }
      }
    );
  },
  data () {
    return {
      header: {
        title: 'Tra cứu chi tiết đơn hàng',
        list: [
          { 
            name: 'Lập hợp đồng', 
            link: { name: 'Ui.cards' } 
          },
          {
            name: 'Lắp đặt mới',
            link: { name: 'Ui.buttons' }
          },
        ]
      },
      CheckPIR: 0,
      CheckNIX: 0,
      CheckISP: 0,
      diachi_ld:"",
      tt_donhang:{
          list:[],
          value: "-1",
          isEnabled:false,
        },
        loai_tt:{
          list:[],
          value: "",
          isEnabled:false,
      },
      chitiethopdong:{
        thongtinchinh:"",
        thongtinthem:"",
        khachhang:{
          out_kh:[{}],
          out_mail:[{}]
        },
        thanhtoan:{
          out_tt:[{}],
          out_mail:[{}]
        },
        tienhopdong:{
         list: [],
            header:  [{fieldName:'ma_kmtt',headerText:'Mã KMTT',allowFiltering: true},
            {fieldName:'ten_kmtt',headerText:'Tên KMTT',allowFiltering: true},
            {fieldName:'tyle_vat',headerText:'Tỷ lệ VAT',allowFiltering: true},
            {fieldName:'tien',headerText:'Tiền',allowFiltering: true},
            {fieldName:'vat',headerText:'VAT',allowFiltering: true}],
        }
      },
      thongtinkhachhang: "",
      thongtinsp: "",
      dsgoi: "",
      phieu_yc:{
        hdkh_id:"",
        hdtb_id:"",
      //  khachhang_id: "",
        donhang_id: 0,
        MaGD:"",
        MaDH:"",
        LoaiHD:"",
        HuongKNCu:"",
        HuongKNMoi:"",
        NgayYC:"",
        NgayLapHD:"",
        KieuLD:"",
        SoMayMoi:"",
        SoMayCu:"",
        TenKH:"",
        DiaChi:"",
        NguoiGiao:"",
        NoiNhan:"",
        NguoiLapHD:"",
        GhiChu:"",
        SoDT:"",
        NCC: false,
        NgayYCHT:"",
        CTV:"",
        NVTiepThi:""
      },
      DsPort:{
            headers: [
                  { fieldName: 'slot', headerText: 'SlotT' },   
                { fieldName: 'port', headerText: 'Port'}, 
                { fieldName: 'vpi', headerText: 'Vpi/Svlan'}, 
                { fieldName: 'vci', headerText: 'Vci/Cvlan'},  
                { fieldName: 'rack', headerText: 'Rack'}, 
                { fieldName: 'shelf',headerText: 'Shelf'},
                { fieldName: 'system', headerText: 'System'}, 
                { fieldName: 'tendslam', headerText: 'Dslam'},    
                { fieldName: 'vitri', headerText: 'Vị trí Port'},    
                { fieldName: 'ten_card', headerText: 'Card'},    
                { fieldName: 'selfid01', headerText: 'SelfID'},    
                { fieldName: 'selfid02', headerText: 'OLT Slot'},    
                { fieldName: 'selfid03', headerText: 'OLT Port'},    
                { fieldName: 'selfid04', headerText: 'ONU ID'},    
                { fieldName: 'adsl_port', headerText: 'Adsl port'},    
                { fieldName: 'port_mdf', headerText: 'Port MDF'},    
                { fieldName: 'ketcuoi', headerText: 'Spliter'}                          
            ],
            list: []
        },
      danhsachthuebao: {
          list: [],
          header:[],
          value: {},
          isEnabled: true,
          columns: [
          { fieldName: 'ma_tb', headerText: 'Số máy/Acc', allowFiltering: true},
          { fieldName: 'matb_tn', headerText: 'Mã TN', allowFiltering: true },
          { fieldName: '', headerText: 'Số ảo', allowFiltering: true },
          { fieldName: 'ma_vnpt', headerText: 'Mã VNPT', allowFiltering: true},
          { fieldName: 'ma_tb_cha', headerText: 'Thuê bao cha', allowFiltering: true },
          { fieldName: 'ten_dvvt', headerText: 'Dịch vụ', allowFiltering: true },
          { fieldName: 'loaihinh_tb', headerText: 'Loại hình', allowFiltering: true},
          { fieldName: 'ten_tb', headerText: 'Tên thuê bao', allowFiltering: true },
          { fieldName: 'trangthai_hd', headerText: 'Trạng thái HĐ', allowFiltering: true },
          { fieldName: 'trangthai_kh', headerText: 'Trạng thái KH', allowFiltering: true},
          { fieldName: 'diachi_ld', headerText: 'Địa chỉ lắp đặt', allowFiltering: true },
          { fieldName: 'diachi_dau', headerText: 'Địa chỉ LĐ đầu', allowFiltering: true },
          { fieldName: 'diachi_cuoi', headerText: 'Địa chỉ LĐ cuối', allowFiltering: true},
          { fieldName: 'hinhthuc', headerText: 'HT chuyển đổi', allowFiltering: true },
          { fieldName: 'tocdo', headerText: 'Tốc độ', allowFiltering: true },
          { fieldName: 'ten_dt', headerText: 'Đối tượng', allowFiltering: true},
          { fieldName: 'ma_dvgt', headerText: 'Ngày hết hạn', allowFiltering: true },
          { fieldName: 'tg_thue_tu', headerText: 'TG thuê từ', allowFiltering: true },
          { fieldName: 'tg_thue_den', headerText: 'TG thuê đến', allowFiltering: true},
          { fieldName: 'ghichu', headerText: 'Ghi chú', allowFiltering: true },
          { fieldName: 'ngay_kh', headerText: 'Ngày kích hoạt', allowFiltering: true },
          { fieldName: 'ngay_ht', headerText: 'Ngày HT', allowFiltering: true},
          { fieldName: 'ngay_ins', headerText: 'Ngày nghiệm thu', allowFiltering: true },
          { fieldName: 'congnghe', headerText: 'Công nghệ', allowFiltering: true },                      
        ],
      },
      chitietthicong: {
          list: [],
          header:[],
          value: {},
          isEnabled: true,
          columns: [
          { fieldName: 'huonggiao', headerText: 'Hướng giao', allowFiltering: true, 
          allowSorting: false, isGroupedColumn: true},
          { fieldName: 'dvg', headerText: 'Đơn vị giao', allowFiltering: true},
          { fieldName: 'ngay_giao', headerText: 'Ngày giao', allowFiltering: true, },
          { fieldName: 'nv_giao', headerText: 'Người giao', allowFiltering: true },
          { fieldName: 'nd_giao', headerText: 'Nội dung giao', allowFiltering: true},
          { fieldName: 'dvn', headerText: 'Đơn vị nhận', allowFiltering: true },
          { fieldName: 'trangthai_ph', headerText: 'Trạng thái phiếu', allowFiltering: true },
          { fieldName: 'ngay_tra', headerText: 'Ngày trả', allowFiltering: true},
          { fieldName: 'nd_tra', headerText: 'Nội dung trả', allowFiltering: true },
          { fieldName: 'lydotra', headerText: 'Lý do trả', allowFiltering: true },
          { fieldName: 'ngay_th', headerText: 'Ngày CN', allowFiltering: true},
          { fieldName: 'nguoi_cn', headerText: 'Người thực hiện', allowFiltering: true },
          { fieldName: 'so_dt', headerText: 'Điện thoại liên hệ', allowFiltering: true },
          { fieldName: 'ghichu', headerText: 'Ghi chú', allowFiltering: true},
          { fieldName: 'ketqua_xl', headerText: 'Kết quả XL', allowFiltering: true },
          { fieldName: 'chuongtrinh', headerText: 'Ứng dụng', allowFiltering: true },                      
        ],
      },
      lichsudonhang: {
          list: [],
          header:[],
          value: {},
          isEnabled: true,
          columns: [
          { fieldName: 'stt', headerText: 'STT', allowFiltering: true, width: 40},
          { fieldName: 'hanhdong', headerText: 'Hành động', allowFiltering: true },
          { fieldName: 'ghichu', headerText: 'Ghi chú', allowFiltering: true },
          { fieldName: 'ngay_cn', headerText: 'Ngày cập nhật', allowFiltering: true},                  
        ],
      },
      thongtindonhang: {
          list: [],
          header:[],
          value: {},
          isEnabled: true,
          columns: [
          { fieldName: 'ma_dh', headerText: 'Mã Đơn Hàng', allowFiltering: true},
          { fieldName: 'ten_sp', headerText: 'Tên Sản Phẩm', allowFiltering: true },
          { fieldName: 'tongtien', headerText: 'Tổng Tiền', allowFiltering: true },                      
        ],
      },
      dsbiendong: {
          list: [],
          header:[],
          value: {},
          isEnabled: true,
          columns: [
          { fieldName: 'ma_dh', headerText: 'Mã ĐH', allowFiltering: true},
          { fieldName: 'ma_gd', headerText: 'Mã GD', allowFiltering: true },
          { fieldName: 'ngay_yc', headerText: 'Ngày YC', allowFiltering: true },
          { fieldName: 'trangthai_hd', headerText: 'Trạng thái', allowFiltering: true},
          { fieldName: 'ten_loaihd', headerText: 'Loại hợp đông', allowFiltering: true },
          { fieldName: 'ten_dv', headerText: 'Đơn vị lập HĐ/PL', allowFiltering: true },                      
        ],
      },
      listbox: {
        dich_vu:{
          list:[],
          value: 1,
          isEnabled:false,
        },
        toc_do_kenh_CIR:{
          list:[],
          value: "",
          isEnabled:false,
        },
        toc_do_kenh_PIR:{
          list:[],
          value: "",
          isEnabled:false,
        },
        toc_do_kenh_NIX:{
          list:[],
          value: "",
          isEnabled:false,
        },
        toc_do_kenh_ISP:{
          list:[],
          value: "",
          isEnabled:false,
        },
        ketquatimkiem2: {
          list: [],
          header:[],
          value: {},
          isEnabled: true,
        },
        ketquatimkiem3: {
          list: [],
          header:[],
          value: {},
          isEnabled: true,
        },
        ds_goidv: {
            list: [],
            header:  [{fieldName:'ma_tb',headerText:'Số máy/Account',allowFiltering: true},{fieldName:'ten_goi',headerText:'Gói đa dịch vụ',allowFiltering: true} ],
        },
         ds_phieuton: {
            list: [],
            header:  [{fieldName:'ngay_bt',headerText:'Ngày báo tồn',allowFiltering: true},{fieldName:'nguoi_bt',headerText:'Người báo tồn',allowFiltering: true},{fieldName:'donvi_bt',headerText:'Đơn vị báo tồn',allowFiltering: true},
            {fieldName:'nhom_ton',headerText:'Nhóm tồn',allowFiltering: true},{fieldName:'lydoton_tc',headerText:'Lý do tồn',allowFiltering: true},
            {fieldName:'nguoi_cn',headerText:'Người cập nhật',allowFiltering: true},{fieldName:'ghichu',headerText:'Ghi chú',allowFiltering: true} ],
        }
      },
       config: {
                dateConfig: {
                    value: new Date(),
                    format: "dd/MM/yyyy",
                    max: new Date()
                }
            },
    }
  },
  methods: {
    async HenLapDat(){
      if(this.phieu_yc.MaDH.length != 0){
      this.$refs.thongTinHenKhachHangModal.showModal();
      }
      else {
        this.$toast.error('Vui lòng nhập mã đơn hàng')
        this.$refs.madh.focus();
      }
    },

    // popup ds đơn hàng
    PopUpDSDonHang: function () {
      this.$bvModal.show('dsdonhang')
    },

    // popup email
    PopUpEmail: function () {
      if(this.phieu_yc.MaDH == ""){
        this.$toast.error('Vui lòng nhập mã đơn hàng')
        this.$refs.madh.focus();
      }
      else
      this.$bvModal.show('thongtinemail')
    },
    hideModalEmail: function () {
      this.$bvModal.hide('thongtinemail')
    },

     // popup xuất phiếu
    onShowModal: function () {
      if(this.phieu_yc.MaDH == ""){
        this.$toast.error('Không tìm thấy thông tin khách hàng. Bạn hãy kiểm tra lại!')
      }
      else
     this.$bvModal.show('popupInHopDong')
     // this.$refs.popupInHopDong.show();
    },

    // mở popup liên hệ
    PopUpLienHe: function () {
      if(this.phieu_yc.MaDH == ""){
        this.$toast.error('Vui lòng nhập mã đơn hàng')
        this.$refs.madh.focus();
      }
      else
      this.$bvModal.show('thongtinlienhe')
    },
    hideModalLienHe: function () {
      this.$bvModal.hide('thongtinlienhe')
    },

    // mở popup vật tư
    PopUpVatTu: function () {
      if(this.phieu_yc.MaDH.length != 0){
      this.$bvModal.show('vattu')
      }
      else{
        this.$toast.error('Vui lòng nhập mã đơn hàng')
        this.$refs.madh.focus();
      }
    },
    hideModalVatTu: function () {
      this.$bvModal.hide('vattu')
    },

    // mở popup port thuê bao
    PopUpport: function () {
      if(this.phieu_yc.MaDH.length != 0){
      this.$bvModal.show('port')
      }
      else {
        this.$toast.error('Vui lòng nhập mã đơn hàng')
        this.$refs.madh.focus();
      }
    },
    hideModalport: function () {
      this.$bvModal.hide('port')
    },

    // popup địa chỉ lắp đặt
    show_diachild:function(){
      if(this.phieu_yc.MaDH.length != 0){
      var data={
        "hdtb_id":this.phieu_yc.hdtb_id,
        "dichvu_id":this.listbox.dich_vu.value
        }
      // var data={
      //   "hdtb_id": 2717333,
      //   "dichvu_id":this.listbox.dich_vu.value
      //   }
      console.log(data);
      SearchOrderDetailAPI.sp_lay_tt_show_diachi_ld(this.axios,data).then(response=>{
        if(response && response.data && response.data.data){
          this.diachi_ld = response.data.data;
          console.log(this.diachi_ld);
          console.log(1);
        }
        else
          this.diachi_ld = "";
      });
      }
    },
    show_cthd:function(){
      this.loading(true);
      var data = {
        "hdtb_id": this.phieu_yc.hdtb_id
      };

      // var data = {
      //     "hdtb_id": 2717333
      // };
      SearchOrderDetailAPI.sp_lay_tt_thuebao_chinh_by_hdtb_id(this.axios,data).then(response=>{
        if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000')
          this.chitiethopdong.thongtinchinh = response.data.data[0];
        else
          this.chitiethopdong.thongtinchinh = "";

      });

      SearchOrderDetailAPI.sp_lay_tt_thuebao_them_by_hdtb_id(this.axios,data).then(response=>{
        if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000')
          this.chitiethopdong.thongtinthem = response.data.data[0];
        else
          this.chitiethopdong.thongtinthem = "";
      });

      SearchOrderDetailAPI.sp_lay_tt_khachhang_tien_by_hdtb_id(this.axios,data).then(response=>{
        if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000'){
          this.chitiethopdong.tienhopdong.list = response.data.data;
        }
        else
          this.chitiethopdong.thongtinchinh = [];

      });

      SearchOrderDetailAPI.sp_lay_tt_khachhang_by_hdtb_id(this.axios,data).then(response=>{
        if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000'){
          this.chitiethopdong.khachhang = response.data.data;
    
          if (response.data.data.out_mail == ""){
            this.chitiethopdong.khachhang.out_mail = [{"email":"","dangky":"","trangthai_sd":"","mucdich":""}];
          }
        }
        else{
          this.chitiethopdong.khachhang.out_kh = "";
          this.chitiethopdong.khachhang.out_mail = "";
        }
          

      });


      SearchOrderDetailAPI.sp_lay_tt_thanhtoan_by_hdtb_id(this.axios,data).then(response=>{
        if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000'){
          this.chitiethopdong.thanhtoan = response.data.data;
           if (response.data.data.out_mail == ""){
            this.chitiethopdong.thanhtoan.out_mail = [{"email":"","dangky":"","trangthai_sd":"","mucdich":""}];
          }
        }
        else
          this.chitiethopdong.thanhtoan = "";

      });

       this.loading(false);

    },
    show_goidv:function(){
      var data = {
                      "hdtb_id":this.phieu_yc.hdtb_id,
                      "hdkh_id":this.phieu_yc.hdkh_id
                  };
        // var data = {
        //           "hdtb_id":2981760,
        //           "hdkh_id":4504652
        //       };
      SearchOrderDetailAPI.sp_lay_ds_tb_dk_goi(this.axios,data).then(response =>{
      if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') 
          this.listbox.ds_goidv.list = response.data.data;
      else
          this.listbox.ds_goidv.list = [];
      });
    },
    show_phieuton:function(){
      var data = {
                      "hdtb_id":this.phieu_yc.hdtb_id,
                      "phieu_id":0
                  };
      //  var data = {
      //             "hdtb_id":2981760,
      //             "phieu_id":0
      //         };
      SearchOrderDetailAPI.sp_lay_ds_ton_phieu_hist(this.axios,data).then(response =>{
      if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') 
          this.listbox.ds_phieuton.list = response.data.data;
      else
          this.listbox.ds_phieuton.list = [];
      });
    },
    btnSearchClicked() {
      this.Clear();
      if(this.phieu_yc.MaDH == "" || this.phieu_yc.MaDH == null){
        this.$toast.error('Bạn chưa nhập mã đơn hàng');
        this.$refs.madh.focus();
      }
      else {
      var pdata1 = {};
      this.phieu_yc.MaDH = this.phieu_yc.MaDH.trim();
      pdata1.in_ma_tb = this.phieu_yc.MaDH;
      SearchOrderDetailAPI.thong_tin_phieu_yc(this.axios, pdata1).then((response) => {
        if(response.data.data == null || response.data.data == ""){
              this.$toast.error('Không tìm thấy thông tin về thuê bao')
          }
        else if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000' && response.data.data) {
            this.listbox.ketquatimkiem2.list = response.data.data;
            this.phieu_yc.MaDH = this.listbox.ketquatimkiem2.list[0].ma_dh;
            this.phieu_yc.MaGD = this.listbox.ketquatimkiem2.list[0].ma_gd;
            this.phieu_yc.NgayYC = moment(this.listbox.ketquatimkiem2.list[0].ngay_yc).format("DD/MM/YYYY hh:mm:ss A");
            this.phieu_yc.NgayLapHD = moment(this.listbox.ketquatimkiem2.list[0].ngaylap_hd).format("DD/MM/YYYY hh:mm:ss A");
            this.phieu_yc.LoaiHD = this.listbox.ketquatimkiem2.list[0].ten_loaihd;
            this.phieu_yc.TenKH = this.listbox.ketquatimkiem2.list[0].ten_kh;
            this.phieu_yc.SoDT = this.listbox.ketquatimkiem2.list[0].so_dt;
            this.phieu_yc.DiaChi = this.listbox.ketquatimkiem2.list[0].diachi_kh;
            this.phieu_yc.TenKH = this.listbox.ketquatimkiem2.list[0].ten_kh;
            this.phieu_yc.donhang_id = this.listbox.ketquatimkiem2.list[0].donhang_id;
           // this.phieu_yc.khachhang_id = this.listbox.ketquatimkiem2.list[0].khachhang_id;

            var pdata = {};
            pdata.vhdkh_id = this.listbox.ketquatimkiem2.list[0].hdkh_id;
            this.phieu_yc.hdkh_id = this.listbox.ketquatimkiem2.list[0].hdkh_id;
            this.GetLichSuDonHang(this.listbox.ketquatimkiem2.list[0].donhang_id);
            this.LayCTV(this.phieu_yc.hdkh_id);
            this.LayNVTuVan(this.phieu_yc.hdkh_id);
            this.LayNguoiLapHD(this.phieu_yc.hdkh_id);
            SearchOrderDetailAPI.danh_sach_thue_bao(this.axios, pdata).then((response) => {
              // if(response.data.data == null){
              //     this.$toast.error('Không tìm thấy thông tin về thuê bao')
              // }
              if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000' && response.data.data) {
                  this.danhsachthuebao.list = response.data.data;
                  console.log(this.danhsachthuebao.list);
                  if(response.data.data.length > 1){
                    this.LayDSBienDong();
                    this.$bvModal.show('biendong')
                  }
              }
            });
              }
        });
      }  
    },
    GetLichSuDonHang(donhang){
      var pdata = {};
      pdata.vdonhang_id = donhang;
      SearchOrderDetailAPI.lich_su_don_hang(this.axios, pdata).then((response) => {
        if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000' && response.data.data) {
            this.lichsudonhang.list = response.data.data;
        }
        for(var i=0; i<this.lichsudonhang.list.length; i++){
          this.lichsudonhang.list[i].stt = i+1;
        }
      });
    },
    GetChiTietThiCong(hdtb_id){
      var pdata = {};
      pdata.vhdtb_id = hdtb_id;
      SearchOrderDetailAPI.chi_tiet_thi_cong(this.axios, pdata).then((response) => {
        if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000' && response.data.data) {
            this.chitietthicong.list = response.data.data;
            for(var i=0; i<this.chitietthicong.list.length; i++){
              // this.chitietthicong.list[i].huonggiao = parseInt(this.chitietthicong.list[i].huonggiao);
             this.chitietthicong.list[i].huonggiao = this.chitietthicong.list[i].huonggiao;
              // console.log(parseInt(this.chitietthicong.list[i].huonggiao));
              // console.log(this.chitietthicong.list[i].huonggiao);
            }
            this.chitietthicong.list = response.data.data;
        }
      });
    },
    GetHuongKN(hdtb_id){
      SearchOrderDetailAPI.lay_huong_kn(this.axios, hdtb_id).then((response) => {
        if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000' && response.data.data) {
            this.phieu_yc.HuongKNCu = response.data.data[0].huong_kn_cu;
            this.phieu_yc.HuongKNMoi = response.data.data[0].huong_kn_moi;
        }
      });
    },
    LayTBCu(hdtb_id){
      var pdata = {};
      pdata.vhdtb_id = hdtb_id;
      SearchOrderDetailAPI.lay_tb_cu(this.axios, pdata).then((response) => {
        if(response && response.data && response.data.data) {
            this.phieu_yc.SoMayCu = response.data.data;
        }
      });
    },
    LayCTV(hdkh_id){
      var pdata = {};
      pdata.vhdkh_id = hdkh_id;
      SearchOrderDetailAPI.lay_ctv(this.axios, pdata).then((response) => {
        if(response && response.data && response.data.data) {
            this.phieu_yc.CTV = response.data.data;
        }
      });
    },
    LayNVTuVan(hdkh_id){
      // var pdata = {};
      // pdata.id_neo = "nhanviengt_id";
      // pdata.table = "css.hd_khachhang";
      // pdata.dk = "where hdkh_id = " + hdkh_id;
      SearchOrderDetailAPI.fn_tt_hd_khachhang(this.axios, {"param": hdkh_id,"type": 1}).then((response) => {
        if(response && response.data && response.data.data) {
            if(response.data.data == 1){
              this.phieu_yc.NVTiepThi = "";
            }
            else this.phieu_yc.NVTiepThi = response.data.data;
        }
      });
    },
    LayNguoiLapHD(hdkh_id){
      // var pdata = {};
      // pdata.id_neo = "nguoilap_hd";
      // pdata.table = "css.hd_khachhang";
      // pdata.dk = "where hdkh_id = " + hdkh_id;
      SearchOrderDetailAPI.fn_tt_hd_khachhang(this.axios, {"param": hdkh_id,"type": 1}).then((response) => {
        if(response && response.data && response.data.data) {
            if(response.data.data == 1){
              this.phieu_yc.NguoiLapHD = "";
            }
            else this.phieu_yc.NguoiLapHD = response.data.data;
        }
      });
    },
    GiaoLaiPhieu(){
      var pdata = {};
      pdata.vhdtb_id = this.phieu_yc.hdtb_id;
      var giaolai;
      for(var i=0 ; i<this.chitietthicong.list.length; i++){
        if(this.chitietthicong.list[i].trangthai_ph.toLowerCase() == "đã trả lại"){
          console.log(true);
          giaolai = 1;
        }
        else giaolai = 0;
      }
      if(giaolai==1){
      SearchOrderDetailAPI.giao_lai_phieu(this.axios, pdata).then((response) => {
        if(response && response.data && response.data.data) {
            if(response.data.data == 1){
              this.$toast.success('Giao lại phiếu thành công');
            }
        }
      });
      }
      else this.$toast.error('Phiếu trả lại không tìm thấy!');
    },
    Lay_TT_DH(){
      this.KtraNhap();
      SearchOrderDetailAPI.lay_tt_don_hang(this.axios, this.phieu_yc.donhang_id).then((response) => {
        if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000' && response.data.data) {
            this.thongtindonhang.list = response.data.data;
        }
      });
    },
    PopupDoiGoi_LayTTKhachHang(){
      SearchOrderDetailAPI.lay_tt_kh(this.axios, this.phieu_yc.donhang_id).then((response) => {
        if(response && response.data && response.data.data) {
            this.thongtinkhachhang = response.data.data;
        }
      });
      SearchOrderDetailAPI.lay_tt_sp(this.axios, this.phieu_yc.donhang_id).then((response) => {
        if(response && response.data && response.data.data) {
            this.thongtinsp = response.data.data;
        }
      });
      SearchOrderDetailAPI.lay_ds_goi(this.axios, this.phieu_yc.donhang_id).then((response) => {
        if(response && response.data && response.data.data) {
            this.dsgoi = response.data.data;
        }
      });
    },
    LayDSBienDong(){
      var pdata = {};
      pdata.in_ma_tb = this.phieu_yc.MaDH;
        SearchOrderDetailAPI.thong_tin_phieu_yc(this.axios, pdata).then((response) => {
        if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000' && response.data.data) {
            this.dsbiendong.list = response.data.data;
        }
      });
    },
    selectedRowChanged(obj){
     this.chitietthicong.list = null;
     this.listbox.dich_vu.value = obj.dichvuvt_id;
     this.phieu_yc.KieuLD = obj.ten_kieuld;
     this.phieu_yc.GhiChu = obj.ghichu;
     this.phieu_yc.SoMayMoi = obj.ma_tb
     this.phieu_yc.hdtb_id = obj.hdtb_id;
     var ncc = obj.chuyenmang;
     if(ncc == 1){
       this.phieu_yc.NCC = true;
     }
    // console.log(this.phieu_yc.hdtb_id);
     this.GetChiTietThiCong(this.phieu_yc.hdtb_id);
    //  this.GetChiTietThiCong(1474319);
     this.GetHuongKN(obj.hdtb_id);
    //  this.GetHuongKN(2083485);
     if(this.listbox.dich_vu.value == 1 || this.listbox.dich_vu.value == 11 || this.listbox.dich_vu.value == 4 || this.listbox.dich_vu.value == 8){
        this.LayTBCu(obj.hdtb_id);
     }
    },
    selectedRowChanged1(obj){
      this.phieu_yc.NguoiGiao = obj.nv_giao;
      this.phieu_yc.NoiNhan = obj.dvn;
      // this.phieu_yc.GhiChu = obj.ghichu
    },
    Clear() {
            this.chitietthicong.list = null;
            this.lichsudonhang.list = null;
            this.danhsachthuebao.list = null;
            // this.phieu_yc.hdkh_id = "";
            // this.phieu_yc.hdtb_id = "";
            this.phieu_yc.CTV = "";
            this.NVTiepThi = "";
            this.phieu_yc.MaGD = "";
         //   this.phieu_yc.MaDH = "";
            this.phieu_yc.NgayYC = "";
            this.phieu_yc.NgayLapHD = "";
            this.phieu_yc.LoaiHD = "";
            this.phieu_yc.TenKH = "";
            this.phieu_yc.SoDT = "";
            this.phieu_yc.DiaChi = "";
            this.phieu_yc.TenKH = "";
            this.phieu_yc.KieuLD = "";
            this.phieu_yc.HuongKNCu = "";
            this.phieu_yc.HuongKNMoi = "";
            this.phieu_yc.SoMayMoi = "";
            this.phieu_yc.SoMayCu = "";
            this.phieu_yc.NguoiGiao = "";
            this.phieu_yc.NoiNhan = "";
            this.phieu_yc.NCC = false;
    },
      NhapMoi() {
            this.$refs.madh.focus();
            this.chitietthicong.list = null;
            this.lichsudonhang.list = null;
            this.danhsachthuebao.list = null;
            // this.phieu_yc.hdkh_id = "";
            // this.phieu_yc.hdtb_id = "";
            this.phieu_yc.CTV = "";
            this.NVTiepThi = "";
            this.phieu_yc.MaGD = "";
            this.phieu_yc.MaDH = "";
            this.phieu_yc.NgayYC = "";
            this.phieu_yc.NgayLapHD = "";
            this.phieu_yc.LoaiHD = "";
            this.phieu_yc.TenKH = "";
            this.phieu_yc.SoDT = "";
            this.phieu_yc.DiaChi = "";
            this.phieu_yc.TenKH = "";
            this.phieu_yc.KieuLD = "";
            this.phieu_yc.HuongKNCu = "";
            this.phieu_yc.HuongKNMoi = "";
            this.phieu_yc.SoMayMoi = "";
            this.phieu_yc.SoMayCu = "";
            this.phieu_yc.NguoiGiao = "";
            this.phieu_yc.NoiNhan = "";
            this.phieu_yc.NCC = false;
    },
    KtraNhap(){
      if(this.phieu_yc.MaDH == ""){
        this.$toast.error('Bạn chưa nhập mã đơn hàng!');
        this.$refs.madh.focus();
      }
    }
  },
};
</script>