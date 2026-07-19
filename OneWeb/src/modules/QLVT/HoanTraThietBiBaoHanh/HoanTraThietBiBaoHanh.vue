<template src='./HoanTraThietBiBaoHanh.html'></template>
<style scoped src='./HoanTraThietBiBaoHanh.scss'></style>
<script>
import { mapActions, mapState, mapGetters,mapMutations } from "vuex";
import moment from 'moment';
import breadcrumb from '@/components/breadcrumb'
import { Group, Page, Filter, Sort, Resize} from "@syncfusion/ej2-vue-grids";
import HoanTraThietBiBaoHanhAPI from  '../api/HoanTraThietBiBaoHanhAPI'
import { getterName,actionName,statePropertyName,mutationName} from '../store/hoantrathietbibaohanh'

export default {
  components: {breadcrumb },
  name: "HoanTraThietBiBaoHanh",
  provide: {
    grid: [ Group, Page,Filter,Sort,Resize ]
  },
  mounted() {
  },
  computed: {
    ...mapState("hoantrathietbibaohanh", statePropertyName),
    ...mapGetters("hoantrathietbibaohanh", getterName),

    p_loaikho_id: {
      get() {
        return this.params.p_loaikho_id
      },
      set(value) {
        return this.params.p_loaikho_id = value
      }
    },
    p_nhanvien_id: {
      get() {
        return this.params.p_nhanvien_id
      },
      set(value) {
        return this.params.p_nhanvien_id = value
      }
    },
    p_tungay: {
      get() {
        return this.params.p_tungay
      },
      set(value) {
        return this.params.p_tungay = value
      }
    },
    p_denngay: {
      get() {
        return this.params.p_denngay
      },
      set(value) {
        return this.params.p_denngay = value
      }
    }
   
  },
  data() {
    return {
      header: {
        title: "HOÀN TRẢ THIẾT BỊ BẢO HÀNH",
        list: [
          { name: "Quản lý vật tư", link: { name: "Ui.cards" } },
            {
                name: "Bảo hành thiết bị",
                link: { name: "Ui.buttons" },
            },
            {
                name: "Hoàn trả thiết bị bảo hành",
                link: { name: "Ui.buttons" },
            },
            
        ],
      },
      config: {
        dateConfig: {
          default: new Date(),
          max:  new Date(),
          format: 'dd/MM/yyyy'
        }
      },
      listLoaiKho: [
        { value: 0, text: "Kho vật tư VTHP" },
        { value: 1, text: "Kho TTKD - Hàng ký gửi" },
      ],
      fieldsDsPhieuBH: [
        {
          key: "STT",
          label: "  ",
          thClass: "center",
        },
        {
          key: "SERIAL",
          label: "Serial",
          thClass: "center",
        },
        {
          key: "TRANGTHAI_BH",
          label: "Trạng thái",
          thClass: "center",
        },
        {
          key: "TRANGTHAI_VATTU",
          label: "Trạng thái vật tư",
          thClass: "center",
        },
        {
          key: "NGAY_YC",
          label: "Ngày tiếp nhận",
          thClass: "center",
          formatter: value => moment(value).format('DD/MM/YYYY hh:mm')
        },
        {
          key: "TEN_DV",
          label: "Đơn vị tiếp nhận",
          thClass: "center",
        },
        {
          key: "TEN_NV",
          label: "Người tiếp nhận",
          thClass: "center",
        },
        {
          key: "NHOM_TBI",
          label: "Nhóm thiết bị",
          thClass: "center",
        },
        {
          key: "LOAI_TBI",
          label: "Loại thiết bị",
          thClass: "center",
        },
        {
          key: "MA_TB",
          label: "Số máy/Acc",
          thClass: "center",
        },
        {
          key: "TEN_TB",
          label: "Tên TB",
          thClass: "center",
        },
        {
          key: "DIACHI_LD",
          label: "Địa chỉ lắp đặt",
          thClass: "center",
        }
      ],
      fieldsThongTinTHTB: [
        {
          key: "STT",
          label: "  ",
          thClass: "center",
        },
        {
          key: "TEN_VATTU",
          label: "Tên vật tư",
          thClass: "center",
        },
        {
          key: "SERIAL",
          label: "Serial",
          thClass: "center",
        },
        {
          key: "SOLUONG",
          label: "Số lượng",
          thClass: "center",
          tdClass: "text-right",
        },
        {
          key: "LOHANG",
          label: "Lô Hàng",
          thClass: "center",
        },
        {
          key: "LYDO_CAPTBI",
          label: "Lý do cấp thiết bị",
          thClass: "center",
        },
        {
          key: "MA_GD",
          label: "Mã GD",
          thClass: "center",
        },
      ],
      params: {
        p_loaikho_id: "",
        p_nhanvien_id: "",
        p_tungay: new Date(),
        p_denngay: new Date(),
      },
        
      p_nhanvien_options: [],
      
      arraySearchInputPhieuBH: [
        { key: "SERIAL", value: "" },
        { key: "TRANGTHAI_BH", value: "" },
        { key: "TRANGTHAI_VATTU", value: "" },
        { key: "NGAY_YC", value: "" },
        { key: "TEN_DV", value: "" },
        { key: "TEN_NV", value: "" },
        { key: "NHOM_TBI", value: "" },
        { key: "LOAI_TBI", value: "" },
        { key: "MA_TB", value: "" },
        { key: "TEN_TB", value: "" },
        { key: "DIACHI_LD", value: "" },
      ],
      arraySearchInputTHTB: [
        { key: "TEN_VATTU", value: "" },
        { key: "SERIAL", value: "" },
        { key: "SOLUONG", value: "" },
        { key: "LOHANG", value: "" },
        { key: "LYDO_CAPTBI", value: "" },
        { key: "MA_GD", value: "" },
      ],
      currentPagePhieuBH: 1,
      perPagePhieuBH: 10,
      totalItemsPhieuBH: 0,

      currentPageTHTB: 1,
      perPageTHTB: 10,
      totalItemsTHTB: 0,
      selectedRow: {},
      filterOptions: {enableCaseSensitivity: false, mode: 'Immediate', ignoreAccent: true},
      templateOptions : {
          create: function (args) {
            if(['STT'].includes(args.column.field)){
              return '<span class="fa input-icon-right"></span>'
            } 
            
            return '<div class="input-icon-right"><input id="' + args.column.field + '_filterBarcell" type="search" class="form-control"> <span class="icon nc-icon-outline ui-1_zoom"></span></div>';
          },
          write: function (args) {
          }
        },
      cRowSelected: function() {
        return {
          template : Vue.component('STTtemplate', {
            template : '<span class="fa"></span>'
          })  // end of template
        }
      },
      pageSettings: { pageSizes: ['10','20','50','100','All'], pageSize: 10 },
      danhSachPhieuBHPage : {
            page: 0,
            maxSize: 10,
            totalElement: 0,
            totalPages: 0,
            sort: null,
            propertiesSort: null,
            currentRow: 0
        },
    };
  },
  methods: {
    ...mapActions("hoantrathietbibaohanh", actionName),
    ...mapMutations("hoantrathietbibaohanh", mutationName),
    danhSachPhieuBHPageHandler(e) {
      this.danhSachPhieuBHPage.page = e.pageIndex
      this.danhSachPhieuBHPage.maxSize = e.pageSize
    },
    // search input 
    onChangeText (txt, key) {
            let data = {
                key: key || '',
                value: txt || ''
            }
            this.getObjSearch(data)
        },
     // 
    resetParams() {
      this.currentPagePhieuBH = 1;
      this.perPagePhieuBH = 10;
      this.totalItemsPhieuBH = 0;

      this.currentPageTongTra = 1;
      this.perPageTongTra = 10;
      this.totalItemsTongTra = 0;
      this.getObjSearch({});
    },
    // Load danh sách nhân viên
    async loadNhanVienTheoLoai(p_donvi_id,p_loaikho_id) {
      // pageNo: this.currentPagePhieuBH - 1,
      let dsNhanVienbody = {
        "donViId"  : p_donvi_id.toString(),
        "loaiKhoId": p_loaikho_id
      };
      await HoanTraThietBiBaoHanhAPI.getDanhSachNhanVien(this.axios, dsNhanVienbody)
      .then(
        (response) => {
          if (response.data.error_code === 'BSS-00000000') {
            let items = []
            if (response.data.data !== undefined) {
              this.p_nhanvien_options = []
              response.data.data.forEach( function (item) {
                items.push({ id : item.MA, text : item.TEN })
              })
              this.p_nhanvien_options = items
              if(this.p_nhanvien_options.length){
                this.p_nhanvien_id = this.p_nhanvien_options[0].id
              }
              else{
                this.$toast.error("Không có dữ liệu tìm kiếm")
                this.clearDanhSachPhieuBH()
              }
              
            }
          }
        }
      )
      .catch(function() {

      })
      .finally(function() {

      })
    },
    // onchange Loại kho
    async onLoaiKhoChange() {
      this.p_nhanvien_options = [];
      await this.loadNhanVienTheoLoai(this.$auth.getDonViID(),this.p_loaikho_id);
    },
    // onchange Nhân viên
    onNhanVienChange: function(){
      if (this.params.p_nhanvien_id){
        this.onClickLayTT();
        console.log(this.selectedRow);
      }
    },
    // chuyển Date thành type string
     f_DateToString: function (value,format){
      return moment(value).format(format)
    },
    // action click lấy TT
    async onClickLayTT() { 
      if (this.p_tungay > this.p_denngay ){
        this.$toast.error("Từ ngày không được lớn hơn Đến ngày")
        return
      }
      this.clearDanhSachPhieuBH()  
      this.loading(true);      
      await this.fetchData();         
    },
    // load dữ liệu ds phiếu bảo hành
    async fetchData() {
      this.loading(true)
      let dsPhieuBHParams = {
        nhanVienId: this.$auth.getNhanVienID(), 
        tuNgay: this.f_DateToString(this.p_tungay,'DD/MM/YYYY'),
        denNgay: this.f_DateToString(this.p_denngay,'DD/MM/YYYY'),
      };
      try {
        await this.getDanhSachPhieuBH(dsPhieuBHParams);
        !this.danhSachPhieuBH.length ? this.$toast.error("Không có dữ liệu tìm kiếm") : ''
        // this.$refs.DsPhieuBHTable.selectRow(0)

      } catch (error) {
        console.log(error);
      }
      this.loading(false)
    },
    // action chọn row phiếu BH
    async onRowSelected(items) {
        if (items){
          this.selectedRow = items;
          await this.layThongTin_TB(items);
        }
      },
    // lấy thông tin thuê bao
    async layThongTin_TB(items){
      this.setThongTinBaoHanhTB("")
      this.loading(true)
      let thongTinTBParams = {
        hdtbId: parseInt(this.selectedRow.HDTB_ID) || 0,
        baoHongId: parseInt(this.selectedRow.BAOHONG_ID) || 0
      };
      try {
        await this.getThongTinBaoHanhTB(thongTinTBParams);
        this.loading(false);
      } catch (error) {
        console.log(error);
      }
      this.loading(false)
    },
    async onClickHoanTra(){
      if (!this.selectedRow){
        return;
      }
      await this.hoantra(this.selectedRow);
    },
    async hoantra(){
      let res = await this.validateHoanTra();
      if (!res){
        return 
      }
      let hoanthienParams = {
        baoHanhId: parseInt(this.selectedRow.BAOHANH_ID),  
        ngayHt: this.f_DateToString(new Date(),'DD/MM/YYYY hh:mm:ss'),
        nhanVienId: this.$auth.getNhanVienID(), 
        donViId: this.$auth.getDonViID()
      }
      
      let reponse = await this.hoanThienBaoHanh(hoanthienParams);
      if (reponse.data.data == '1'){
        this.$toast.success('Cập nhật thành công!');
        this.onClickLayTT();

        return;
      }
      else {
        this.$toast.error(reponse.data.data);
        return;
      }
    },
    async validateHoanTra(){
      // if (!this.selectedRow.MA_VT){
      //   this.$toast.error("Vật tư không tồn tại trong kho, đề nghị kiểm tra lại!");
      //   return 0; 
      // }
      if (!this.selectedRow){
        this.$toast.error("Bạn chưa chọn vật tư thu hồi!");
        return 0;
      }
      let now = this.f_DateToString(new Date(),'DD/MM/YYYY HH:mm:ss');
      let ktSeri = this.kiemTraSerial(this.selectedRow.MA_TB,this.selectedRow.SERIAL);
      await this.getMaToTheoDonVi(this.$auth.getDonViID()); 
      let dtTH = {
        ma_tb: this.selectedRow.MA_TB,
        ten: this.selectedRow.TEN_TB,
        dia_chi: this.selectedRow.DIACHI_LD,
        serial: this.selectedRow.SERIAL,
        ngay_nghiemthu: now,
        tinhtrang_tbi: 'Tốt',
        loai_dvu: this.selectedRow.DICHVUVT_ID + " - " + this.selectedRow.TEN_DVVT,
        cbo_thuhoi: parseInt(this.p_nhanvien_id),
        ngay_thuhoi: now,
        trangthai: ktSeri, 
        trangthai_nguon: 'Tốt',
        ghi_chu: "Thu hồi thiết bị cho khách hàng mượn",
        ma_vtu: this.selectedRow.MA_VT,
        ten_vtu: this.selectedRow.TEN_TB,
        ma_to: this.maToObj.maKhoKT || '',
        ma_dvi: this.maToObj.tenTrungTam || '',
        lydo_thuhoi:"Thu hồi thiết bị cho khách hàng mượn",
      }
      // Kiểm tra thu hồi thuê bao
      let s = this.thuHoi_ThueBao(dtTH)
      if (s != 'OK'){
        this.$toast.error("Lỗi thu hồi thiết bị KH mượn: " + s);
        return 0;
      }
      let dtTB = {
        loai_dv : this.selectedRow.DICHVUVT_ID + " - " + this.selectedRow.TEN_DVVT,
        kieu_lapdat : this.selectedRow,
        lydo_cap: "10 - Trả thiết bị bảo hành cho khách hàng",
        ma_tb: this.selectedRow.MA_TB,
        ten: this.selectedRow.TEN_TB,
        diachi: this.selectedRow.DIACHI_LD,
        so_hd: "",
        ngayky_hopdong: now,
        ngay_nghiemthu: now,
        lydo_capdaytb: "0 - Không lựa chọn",
        lydo_captbdaucuoi: "10 - Trả thiết bị bảo hành cho khách hàng",
        ngay_nhapvtu: now,
        hinh_thuc: "TRABAOHANH"
      }
      let dtVT = {
        ma_nv : parseInt(this.p_nhanvien_id),
        id_phieu : this.selectedRow.ID_PHIEU,
        so_lohang: this.selectedRow.SO_LOHANG,
        ma_vtu: this.selectedRow.MA_VT,
        ten: this.selectedRow.TEN,
        so_luong: this.selectedRow.SO_LUONG,
        serial: this.selectedRow.SERIAL
      }
      // CapVatTuChoTB
      s = this.capVatTuChoTB(dtTB,dtVT,this.selectedRow.DIACHI_LD);
      if (s != 'OK'){
        this.$toast.error("Lỗi trả thiết bị KH: " + s);
        return 0;
      }
      return 1
    },
    // Kiểm tra Serial
    kiemTraSerial(ma_tb, SerialCu){
      return 'Tốt'
    },
    // Thu hồi thuê bao
    thuHoi_ThueBao(dtTH){
      return 'OK'
    },
    // CapVatTuChoTB
    capVatTuChoTB(dtTB,dtVT,diachi){
      return 'OK'
    },

    // lấy lịch sử vật tư
  },
  created() {
    this.clearData();
  },
  watch: {
    
  },
  destroyed () {
  }
};
</script>
