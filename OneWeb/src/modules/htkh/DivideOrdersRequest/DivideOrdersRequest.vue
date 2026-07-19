<template src="./DivideOrdersRequest.html"></template>
<style src="./style.scss"></style>

<script>
import breadcrumb from '@/components/breadcrumb'
import api from './api'
import apiHelper from './api.helper'
import { DichVuVienThong, LOAI_DV, LoaiHinhTB, KieuLapDat } from '../../../const/enums'
// import PopupGiaoViec from './popups/PopupGiaoViec';
import PopupGiaoViec from '@/modules/install//UpdateOrdersMyVNPTFreedoo/popups/PopupGiaoViec.vue'
import PopupThongTinGt from '@/modules/install//UpdateOrdersMyVNPTFreedoo/popups/PopupThongTinGt.vue'
import moment from 'moment';
import ThongTinTraCuu from './components/ThongTinTraCuu.vue';
import DanhSachDonHang from './components/DanhSachDonHang.vue'
import popupHuyDonHang from './popups/popupHuyDonHang.vue'
import popupPhanGiamSat from './popups/popupPhanGiamSat.vue'
import popupPhanDonHang from './popups/popupPhanDonHang.vue'

const MY_VNPT_UNG_DUNG_ID = 1
const PHIEU_MOI = 1
const PHIEU_TRA_LAI = 2
const CHUYEN_TC = 3

export default {
  name: 'DivideOrdersRequest',
  components: { breadcrumb, PopupThongTinGt, PopupGiaoViec , ThongTinTraCuu, DanhSachDonHang, popupHuyDonHang, popupPhanGiamSat, popupPhanDonHang},
  data () {
    return {
      header: {
        title: 'Xử lý đơn hàng',
        list: [
          { name: 'Xử lý đơn hàng', link: { name: 'Ui.cards' } },
          { name: 'Xử lý đơn hàng', link: { name: 'Ui.buttons' } }
        ]
      },
      actions: [
        { id: 'tsbtnSearch', icon: 'icon one-search', name: 'Tìm kiếm', visible: true },
        { id: 'tsbtnCapNhat', icon: 'one-save', name: 'Cập nhật', visible: true },
        { id: 'tsbtnGiaoViec', icon: 'one-file-edit2', name: 'Giao việc', visible: true },
        { id: 'tsbtnThongTinGT', icon: '-ap icon-info', name: 'Thông tin GT', visible: true },
        { id: 'tsbtnHuyDon', icon: '-ap icon-cancel-circle', name: 'Huỷ đơn', visible: true },
        { id: 'tsbtnPhanDonHang', icon: '-ap one-wallet', name: 'Phân đơn hàng', visible: true },
        { id: 'tsbtnPhanGiamSat', icon: '-ap one-file-search', name: 'Phân giám sát', visible: true }
      ],

     
      isShowLapHopDong: false,
      ungDungOptions: [],
      ketQuaOptions: [],
      donViTHOptions: [],
      donViNhanOptions: [],
      dichVuOptions: [],
      loaiHinhTBOptions: [],
      goiCuocOptions: [],
      hinhThucTTOptions: [],
      lyDoTraOptions: [],
      lyDoTTOptions: [],
      loaiSimOptions: [],
      hinhThucGiaoHangOptions: [],

      trangThaiTTOptions: [],
      B2A_MENUTAGS: [],
      B2A_MENUTAGS_BOSUNG: [],

      tuVanFilter: {
        ungDungId: '-1',
        maDonHang: '',
        ngayYC: '',
        tenKH: '',
        soDienThoai: '',
        diaChiKH: '',
        ghiChu: '',
        ketQuaId: 0,
        donViTHId: null,
        donViNhanId: null,
        noiDung: '',
        donViNhanId: 0,
        dichVuId: null,
        loaiHinhId: null,
        goiCuoc: "",
        trangThaiTTId: 0,
        hinhThucTTId: 0,
        goiDaDV: "",
        tongtien:0,
        lyDoTT: null,
        ngayTT: null,
        maTB:"",
        thoigian_camket: "",
        giaGoi: 0,
        loaiSimId: 1,
        hinhThucGiaoHang: "",
        cuoc_thoigian_camket:"",
      },
      searchInfo:{},

      dataGrid: {
        danhSachGiaoViec: {
          headers: [
            { fieldName: 'ten_nv', headerText: 'Tên nhân viên', allowFiltering: true },
            { fieldName: 'sodienthoai', headerText: 'Số điện thoại', allowFiltering: true },
            { fieldName: 'nhiemvu', headerText: 'Nhiệm vụ', allowFiltering: true },
            { fieldName: 'ngaygiao', headerText: 'Ngày giao', allowFiltering: true },
            { fieldName: 'ghichu', headerText: 'Ghi chú', allowFiltering: true }
          ],
          dataSources: []
        },
        danhSachDonHang: {
          selectedRow: {}
        }
        
      },
      checkActions: [
        { value: PHIEU_MOI, name: 'Phiếu mới', isShow: true },
        { value: PHIEU_TRA_LAI, name: 'Phiếu trả lại', isShow: true },
        { value: CHUYEN_TC, name: 'Chuyển TC', isShow: true }
      ],

      show: {
        isShowKetQua: true,
        isShowThongTinGiaoViec: true,
        isShowDonViTH: false,
        isShowNoiDung: false,
        isShowLyDoTra: false,
        isShowLyDoTT: false,
        isShowDonViNhan: false,

      },
      tag: '',
      trangThaiPhieu: CHUYEN_TC,
      position: { X: 'center', Y: 'top' },
      phieuId: 0,
      hdkhId: 0,
      kieuNhanVienMacDinh: 0,
      ngayLabel: 'Ngày YC'
    }
  },
  computed: {
    phanVungId () {
      return +this.$root.token.getPhanVungID()
    },
    donViId () {
      return +this.$root.token.getDonViID()
    },
    nhanVienId () {
      return +this.$root.token.getNhanVienID()
    },
    ngayGiao () {
      return moment(new Date()).format('DD/MM/YYYY HH:mm:ss')
    },
    cptTtphId(){
      return (this.trangThaiPhieu == PHIEU_MOI ||this.trangThaiPhieu == CHUYEN_TC) ? 1 : 3
    },
    cptTthd_id(){
      return (this.trangThaiPhieu == CHUYEN_TC) ? 5 : 3
    }
  },
  async created () {
  
   
    $('#select2-ketQuaCbo-container').addClass('text-primary')
  },
  async mounted () {
    this.loaiSimOptions =  [{id:1, text: "esim"}, {id:2, text: "Sim vật lý"}]
    this.hinhThucGiaoHangOptions =  [{id:"Hỏa tốc", text: "Hỏa tốc"}, {id:"Nhanh", text: "Nhanh"}, {id:"Tiêu chuẩn", text: "Tiêu chuẩn"}]
    
    
    $(document).on('click', '.dropdown-menu', function (e) {
      e.stopPropagation()
    })
    if ($(window).width() < 992) {
      $('.dropdown-menu a').click(function (e) {
        e.preventDefault()
        if ($(this).next('.submenu').length) {
          $(this).next('.submenu').toggle()
        }
        $('.dropdown').on('hide.bs.dropdown', function () {
          $(this).find('.submenu').hide()
        })
      })
    }
    await this.frmCapNhatTT_PTDV_Load()
  },
  methods: {
    async frmCapNhatTT_PTDV_Load () {
      this.getTag()
      this.loading(true)
      try {
        await this.getDanhSachUngDung()
        await  this.getDanhSachKetQua()
        this.onSetButtonVisible('tsbtnGiaoViec', this.tag === '0')
        await this.onChangeTrangThai(PHIEU_MOI)

        await  Promise.all([this.getCboDichVu(), this.getCboHinhThucTT(), this.getCboTrangThaiTT(), this.getCboDonViTH()])
        await this.tsbtnSearch_Click();
      } catch (e) {
      } finally {
        this.loading(false)
      }
    },
    getTag () {
      try {
        const tag = this.$route.query.tag
        if (tag && tag.length > 0) {
          const tags = tag.split('+')
          if (tags.length > 0) this.tag = tags[0]
        } else {
          this.tag = '0'
        }
      } catch (e) {
        this.$toast.error('Tham số truyền vào không hợp lệ')
      }
    },

    async getDanhSachUngDung () {
      try {
        const response = await api.getDanhSachUngDung(this.axios)
        const temp =  apiHelper.getDataFromResponseApiReturnArray(response);
        const undung = [{ungdung_id: '-1', ten_ungdung: 'Tất cả' }]
        this.ungDungOptions =  undung.concat(temp)
        this.tuVanFilter.ungDungId = MY_VNPT_UNG_DUNG_ID
      } catch (e) {
        console.log(e)
        this.$toast.error('Lấy danh sách ứng dụng không thành công')
        return []
      }
    },

    async getDanhSachKetQua () {
      try {
        const response = await api.getDanhSachKetQua(this.axios, { tag: this.tag })
        this.ketQuaOptions = apiHelper.getDataFromResponseApiReturnArray(response);
        this.ketQuaOptions.find(ketQua => ketQua.ketqua_id === 0).ten_ketqua = 'Chưa chọn'
       
      } catch (e) {
        console.log(e)
        this.$toast.error('Lấy danh sách kết quả không thành công')
  
      }
    },


    async getDanhSachLyDoTT () {
      try {
        const response = await api.getDanhSachLyDoTT(this.axios)
        this.lyDoTTOptions =  apiHelper.getDataFromResponseApiReturnArray(response).map((x) => ({id: x.lydotontc_id, text: x.lydoton_tc}));
       
      } catch (e) {
        console.log(e);
        this.$toast.error('Không thể lấy danh sách lý do TT')
     
      }
    },

    async getCboDichVu(){
      const response  = await api.getcboDichVuVT(this.axios,{})
      this.dichVuOptions = apiHelper.getDataFromResponseApiReturnArray(response).map((x) => ({id: x.DICHVUVT_ID, text: x.TEN_DVVT}));
    },
    async getCboHinhThucTT(){
      const response  = await api.getCboHinhThucTT(this.axios)
      this.hinhThucTTOptions = apiHelper.getDataFromResponseApiReturnArray(response).map((x) => ({id: x.HTTT_ID, text: x.HINHTHUC_TT}));
  
    },
    async getCboTrangThaiTT(){
      const response  = await api.getCboTTThanhToan(this.axios,)
      this.trangThaiTTOptions = apiHelper.getDataFromResponseApiReturnArray(response).map((x) => ({id: x.trangthai_id, text: x.ten_tt}));
    },

    async getCboDonViTH(){
        const response = await api.getCboDonViTH(this.axios, { })
        this.donViTHOptions = apiHelper.getDataFromResponseApiReturnArray(response).map((x) => ({id: x.donvi_id, text: x.ten_dv}));
          
    },

 
    onResetFilter () {
      this.tuVanFilter.maDonHang = ''
      this.tuVanFilter.tenKH = ''
      this.tuVanFilter.soDienThoai = ''
      this.tuVanFilter.diaChiKH = ''
      this.tuVanFilter.ngayYC = ''
      this.tuVanFilter.ghiChu = ''
      this.tuVanFilter.donViNhanId = null;
      this.tuVanFilter.donViTHId = null;
      this.tuVanFilter.lyDoTT = null;
      this.tuVanFilter.lyDoTra = null;
      this.tuVanFilter.ketQuaId = 0;
      this.tuVanFilter.noiDung = "";
      this.tuVanFilter.dichVuId = 0;
      this.tuVanFilter.loaiHinhId = 0;
      this.tuVanFilter.tongtien = 0;
      this.tuVanFilter.trangThaiTTId = 0;
      this.tuVanFilter.hinhThucTTId = 0;
      this.tuVanFilter.goiCuoc = "";
      this.tuVanFilter.ngayTT = null;
      this.tuVanFilter.goiDaDV = "";
    },

    async onSelectedRowChanged (data) {
      if (data) {
        console.log("onSelectedRowChanged", data)
        this.dataGrid.danhSachDonHang.selectedRow = { ...data }
        this.phieuId = data.phieu_id
        this.hdkhId = data.hdkh_id
        this.tuVanFilter.ungDungId = data.ungdung_id;
        this.tuVanFilter.maDonHang = data.ma_gd
        this.tuVanFilter.tenKH = data.ten_kh
        this.tuVanFilter.soDienThoai = data.so_dt
        this.tuVanFilter.diaChiKH = data.diachi_kh
        this.tuVanFilter.ghiChu = data.ghichu
        this.tuVanFilter.ngayYC = moment(new Date(data.ngay_yc)).format("DD/MM/YYYY"), 
        this.tuVanFilter.ketQuaId = 0;
        this.tuVanFilter.dichVuId = data.dichvuvt_id;
        this.tuVanFilter.loaiHinhId = data.loaitb_id;
        this.tuVanFilter.trangThaiTTId = data.trangthai || 0;
        this.tuVanFilter.hinhThucTTId = data.httt_id || 0;
     
        this.tuVanFilter.goiDaDV = data.ten_goi || "";

        this.tuVanFilter.goiCuoc =  data.magoi_neo || "";

        this.tuVanFilter.maTB = data.ma_tb
        this.tuVanFilter.hinhThucGiaoHang = data.ten_goikt || ""
        this.tuVanFilter.loaiSimId = data.loaisim || 0


        if(data.hdtb_id){
         await this.handleThongTinTienDonHang(data.hdtb_id)
        }


        this.tuVanFilter.ngayTT = data.ngay_tt? moment(new Date(data.ngay_tt)).format("DD/MM/YYYY"): null ;
        this.loading(true)
        try {
          if (this.tuVanFilter.ungDungId !== 5) {
            this.dataGrid.danhSachGiaoViec.dataSources = await this.getThongTinGiaoViec(data.phieu_id)
          }
          this.lyDoTraOptions = await this.getDanhSachLyDoTra(data.huonggiao_id)
        } catch (e) {
        } finally {
          this.loading(false)
        }
      } else {
        this.dataGrid.danhSachGiaoViec.dataSources = []
        this.onResetFilter()
      }
    },
    async getThongTinGiaoViec (phieuId) {
      try {
        const response = await api.getThongTinGiaoViec(this.axios, { phieu_id: phieuId, kieu_id: 2 })
        const data = await response.data
        return data.error_code === 'BSS-00000000' && data.error === '200' ? data.data : []
      } catch (e) {
        this.$toast.error('Không thể lấy danh sách giao việc')
        return []
      }
    },
    async getDanhSachLyDoTra (huongGiaoId) {
      try {
        const response = await api.getDanhSachLyDoTra(this.axios, { huonggiao_id: huongGiaoId })
        const data = await response.data
        return data.error_code === 'BSS-00000000' && data.error === '200' ? data.data : []
      } catch (e) {
        this.$toast.error('Không thể lấy danh sách lý do trả')
        return []
      }
    },
    async handleThongTinTienDonHang(hdtb_id){
      const respone = await api.getInfoOrderMoney(this.axios,  hdtb_id );
      const data = apiHelper.getDataFromResponseApiReturnArray(respone);
      const goiCuoc = data.find(x => x.khoanmuctt_id === 10); //Tiền gói cước
      const tienGoiCuoc = (goiCuoc?.tien || 0) + (goiCuoc?.vat || 0);
      const tongTien = data.reduce((total, item) => total + (item.tien || 0) + (item.vat || 0), 0);

      this.tuVanFilter.tongtien = tongTien;
      this.tuVanFilter.giaGoi = tienGoiCuoc;
      this.tuVanFilter.cuoc_thoigian_camket = "" //chưa có data
    },


    handleThongTinTraCuuChange(searchInfo){
      this.searchInfo = searchInfo;
      // console.log("searchInfo=>", searchInfo)
    },

    onSetButtonVisible (id, isVisible) {
      this.actions.find(item => item.id === id).visible = isVisible
    },
    async onClickAction (id) {
      switch (id) {
        case 'tsbtnSearch':
          await this.tsbtnSearch_Click()
          break
        case 'tsbtnCapNhat':
          await this.onCapNhat()
          break
        case 'tsbtnGiaoViec':
          await this.showPopupGiaoViec()
          break
        case 'tsbtnHuyDon':
          await this.showPopupHuyDon()
          break
        case 'tsbtnPhanGiamSat':
          await this.showPopupPhanGiamSat()
          break
        case 'tsbtnPhanDonHang' :
          await this.showPopupPhanDonHang()
          break
        default:
          await this.showPopupThongTinGT()
          break
      }
    },
    async tsbtnSearch_Click () {
      this.loading(true);
      const params = {
        p_ungdung_id: this.searchInfo.ungDungId || '0', 
        p_donvi_id: this.searchInfo.donViId || '0', 
        p_khdn: this.searchInfo.loaiKHId,
        p_dichvuvt_id: this.searchInfo.dichVuVtId , 
        p_loaitb_id: this.searchInfo.loaiHinhId , 
        p_ma_nv: this.searchInfo.maKenh || "", 
        p_so_dt: this.searchInfo.sdtKenhBan || "",  
        p_ma_gd: this.searchInfo.maDonHang?.trim() || "", 
        p_tthd_id: this.cptTthd_id,
        p_ttph_id: this.cptTtphId,
        p_tungay: this.searchInfo.dtpTuNgay ? moment(this.searchInfo.dtpTuNgay).format('DD/MM/YYYY') : "",
        p_denngay: this.searchInfo.dtpDenNgay ? moment(this.searchInfo.dtpDenNgay).format('DD/MM/YYYY') : ""
      }
      await this.$refs.componentDanhSachDonHang.onInitComponent(params);
      this.loading(false);
    },
    async onCapNhat () {
      console.log("this.tuVanFilter.ketQuaId", this.tuVanFilter.ketQuaId);

      let isThoaiTra = false
      if (!this.tuVanFilter.maDonHang) {
        this.$toast.warning('Không có dữ liệu')
        return false
      }
      if (this.tuVanFilter.ketQuaId === 0) {
        this.$toast.warning('Vui lòng chọn kết quả thực hiện!')
        this.$refs.ketQuaId.focus()
        return false
      }
      let message = ''
      const selectedRow = this.dataGrid.danhSachDonHang.selectedRow
      // const isWarning = await this.canhBaoCapNhat(selectedRow.hdkh_id, 1)

      if (this.tuVanFilter.ketQuaId === 1) {
        if (!this.tuVanFilter.donViTHId) {
          this.$toast.warning('Bạn chưa chọn quầy giao dịch')
          this.$refs.donViTHId.focus()
          return false
        }
        message = 'Cập nhật dữ liệu thành công!'
      } else if (this.tuVanFilter.ketQuaId === 3) {
        if (!this.tuVanFilter.donViNhanId) {
          this.$toast.warning('Bạn chưa chọn đơn vị chuyển')
          return false
        }
       
        if (selectedRow.donvi_nhan_id === this.tuVanFilter.donViNhanId) {
          this.$toast.warning('Đơn vị chuyển trùng với đơn vị hiện tại của đơn hàng')
          return false
        }
        message = 'Chuyển phiếu thành công!'
      } else if (this.tag === '0') {
        if (!this.tuVanFilter.lyDoTT) {
          this.$toast.warning('Bạn chưa nhập nguyên nhân thoái trả!')
          this.$refs.lyDoTT.focus()
          return false
        }
        isThoaiTra = true
        this.$confirm('Bạn có chắc chắn muốn thoái trả không?',
          'Thoái trả',
          { confirmButtonText: 'Có', cancelButtonText: 'Không' }
        ).then(async () => {
   
          this.loading(true)
          try {
            const isSuccess = await this.capNhat({
              phieuId: selectedRow.phieu_id,
              hdkhId: selectedRow.hdkh_id,
              ketQuaId: this.tuVanFilter.ketQuaId,
              donViTHId: this.tuVanFilter.donViTHId,
              nDGiao: this.tuVanFilter.noiDung,
              maGd: selectedRow.ma_gd,
              ungDungId: this.tuVanFilter.ungDungId,
              donViId: this.donViId,
              hdtbId: selectedRow.hdtb_id,
              donViNhanId: this.tuVanFilter.donViNhanId,
              nhanVienTHId: 0,
              lyDoTT: this.tuVanFilter.lyDoTT,
              lyDoTra: this.tuVanFilter.lyDoTra,
              tag: +this.tag,
              message: this.tuVanFilter.noiDung
            })
            
            if(!isSuccess){
              this.$toast.error('Cập nhật không thành công');
              return ;
            }
      
            this.onResetFilter()
            this.dataGrid.danhSachDonHang.selectedRow = {}
            this.dataGrid.danhSachGiaoViec.dataSources = [];
            this.$toast.success('Cập nhật thành công');
            await this.tsbtnSearch_Click()
          

          } catch (e) {
            return false
          } finally {
            this.loading(false)
          }
          return true
        }).catch((e) => {})
      } else {
        if (!this.tuVanFilter.lyDoTra) {
          this.$toast.warning('Bạn chưa chọn lý do trả!')
          this.$refs.lyDoTra.focus()
          return false
        }
        if (!this.tuVanFilter.noiDung) {
          this.$toast.warning('Hãy nhập nội dung để trả phiếu')
          this.$refs.noiDungRef.focus()
          return false
        }
        message = 'Trả phiếu thành công!'
      }
      console.log("isThoaiTra: ", isThoaiTra);


      if (!isThoaiTra) {
        
        this.loading(true)
        try {
          const isSuccess = await this.capNhat({
            phieuId: selectedRow.phieu_id,
            hdkhId: selectedRow.hdkh_id,
            ketQuaId: this.tuVanFilter.ketQuaId,
            donViTHId: +this.tuVanFilter.donViTHId,
            nDGiao: this.tuVanFilter.noiDung,
            maGd: selectedRow.ma_gd,
            ungDungId: this.tuVanFilter.ungDungId,
            donViId: this.donViId,
            hdtbId: selectedRow.hdtb_id,
            donViNhanId: this.tuVanFilter.donViNhanId,
            nhanVienTHId: 0,
            lyDoTT: this.tuVanFilter.lyDoTT,
            lyDoTra: this.tuVanFilter.lyDoTra,
            tag: +this.tag,
            message: message
          })
          if(!isSuccess){
            this.$toast.error('Cập nhật không thành công');
            return ;
          }
      
          this.onResetFilter()
          this.dataGrid.danhSachDonHang.selectedRow = {}
          this.dataGrid.danhSachGiaoViec.dataSources = [];
          this.$toast.success('Cập nhật thành công');
          await this.tsbtnSearch_Click()
        
        } catch (e) {
          console.log("Loi: ", e)
          return false
        } finally {
          this.loading(false)
        }
  
      }
    },
    async canhBaoCapNhat (param, type) {
      this.loading(true)
      try {
        const response = await api.kiemTraCanhBaoCapNhat(this.axios, { p_param: param, p_type: type })
        const data = await response.data
        return data.error_code === 'BSS-00000000' && data.error === '200' && data.data.length > 0
      } catch (e) {
        this.$toast.error('Không thể kiểm tra cảnh báo cập nhật')
        return false
      } finally {
        this.loading(false)
      }
    },
    // params: { phieuId, hdkhId, ketQuaId, donViTHId, nDGiao, maGd, ungDungId, donViId, hdtbId, donViNhanId, nhanVienTHId, lyDoTT, lyDoTra, tag, message }
    async capNhat (params) {
      try {
        const response = await api.capNhat(this.axios,{
            'phieu_id': params.phieuId,
            'hdkh_id': params.hdkhId,
            'ketqua_id': params.ketQuaId,
            'donvi_th_id': params.donViTHId,
            'nd_giao': params.nDGiao,
            'ma_gd': params.maGd,
            'ungdung_id': params.ungDungId,
            'donvi_id': params.donViId,
            'hdtb_id': params.hdtbId,
            'donvi_nhan_id': params.donViNhanId,
            'nhanvien_th_id': params.nhanVienTHId,
            'lydo_tt': params.lyDoTT,
            'lydo_tra': params.lyDoTra,
            'tag': params.tag
          }
        )
        const data =  apiHelper.getDataFromResponseApiReturnRaise(response) 
        return data.result == '1'
       
      } catch (e) {
        console.log(e)
        return false
      }
    },
    async showPopupHuyDon() {
      this.$refs.popupHuyDonHang.phieu_ids = this.$refs.componentDanhSachDonHang.getSelectedRecords();
      this.$refs.popupHuyDonHang.showDialog()
    },
    async showPopupPhanGiamSat() {
      this.$refs.popupPhanGiamSat.phieu_ids = this.$refs.componentDanhSachDonHang.getSelectedRecords();
      this.$refs.popupPhanGiamSat.showDialog()
    },
    async showPopupPhanDonHang() {
      this.$refs.popupPhanDonHang.phieu_ids = this.$refs.componentDanhSachDonHang.getSelectedRecords();
      this.$refs.popupPhanDonHang.showDialog()
    },
    async showPopupGiaoViec () {
      if (!this.tuVanFilter.maDonHang) {
        this.$toast.warning('Không có phiếu yêu cầu tiếp nhận. Bạn hãy kiểm tra lại!')
        return ;
      }
      this.loading(true)
      try {
        const [countNVMD, countNVDB] = await Promise.all([
          this.countNhanVienMacDinh(this.nhanVienId, 1),
          this.countNhanVienDiaBan(this.nhanVienId, 1)
        ])
        if (countNVMD >= 1 || countNVDB >= 1) this.kieuNhanVienMacDinh = 1
        await this.$refs.giaoViec.init(this.kieuNhanVienMacDinh)
      } catch (e) {
      } finally {
        this.loading(false)
      }
      this.$refs.popupGiaoViec.show()
    },
    async countNhanVienMacDinh (param, type) {
      try {
        const response = await api.getThongTinNVMD(this.axios, { p_param: param, p_type: type })
        const data = await response.data
        return data.error_code === 'BSS-00000000' && data.error === '200' && data.data.length > 0 ? data.data[0].count : 0
      } catch (e) {
        this.$toast.error('Không thể kiểm tra nhân viên mặc định')
        return 0
      }
    },
    async countNhanVienDiaBan (param, type) {
      try {
        const response = await api.getThongTinLnv(this.axios, { p_param: param, p_type: type })
        const data = await response.data
        return data.error_code === 'BSS-00000000' && data.error === '200' && data.data.length > 0 ? data.data[0].count : 0
      } catch (e) {
        this.$toast.error('Không thể kiểm tra nhân viên địa bàn')
        return 0
      }
    },
    onClosePopupGiaoViec () {
      this.$refs.popupGiaoViec.hide()
    },
    async onAfterClosePopupGiaoViec () {
      this.loading(true)
      try {
        this.dataGrid.danhSachGiaoViec.dataSources = await this.getThongTinGiaoViec(this.phieuId)
      } catch (e) {
      } finally {
        this.loading(false)
      }
    },

    async onKhoaMoPayTVAction (href) {
      if (this.isEmptyObject(this.dataGrid.danhSachDonHang.selectedRow)) return
      const khoaMoPayTV = await this.lapHopDong('pay_tv')
      if (khoaMoPayTV.includes('không có')) {
        this.$toast.warning(khoaMoPayTV)
        return false
      }
      const hdkh_id = this.dataGrid.danhSachDonHang.selectedRow.hdkh_id
      if (!hdkh_id) {
        this.$toast.warning('Bạn chưa chọn đúng thông tin khách hàng')
        return false
      }
      const [count, maTB] = khoaMoPayTV.split(';')
      if (this.trangThaiPhieu === PHIEU_MOI) {
        if (count === '0') {
          this.$confirm(
            'Hoàn thành phiếu tiếp nhận và tiến hành lập mới hợp đồng?',
            'Lập hợp đồng',
            { confirmButtonText: 'Có', cancelButtonText: 'Không' }
          ).then(async () => {
            await Promise.all([
              this.tuvan_ptdv_kyhd(),
              this.lapHopDong('update_pay_tv')
            ])
            const query = {
              kieuld_id: KieuLapDat.THAYDOI_TT_DICHVU_KHAC,
              vmatb: maTB,
              dichvuvt_id: DichVuVienThong.ADSL,
              loaitb_id: LoaiHinhTB.INTERNET_MYTV
            }
            const nextRoute = this.$router.resolve({ name: href, query })
            window.open(nextRoute.href, '_blank')
          }).catch((e) => {})
        } else {
          this.$toast.warning('Thông tin phiếu đã bị thay đổi. Bạn hãy lấy lại thông tin')
          return false
        }
      } else {
        const query = {
          kieuld_id: KieuLapDat.THAYDOI_TT_DICHVU_KHAC,
          vmatb: maTB,
          dichvuvt_id: DichVuVienThong.ADSL,
          loaitb_id: LoaiHinhTB.INTERNET_MYTV
        }
        const nextRoute = this.$router.resolve({ name: href, query })
        window.open(nextRoute.href, '_blank')
      }
    },
    async tuvan_ptdv_kyhd () {
      this.loading(true)
      try {
        const response = await api.tuvan_ptdv_kyhd(
          this.axios,
          {
            vphieu_id: this.dataGrid.danhSachDonHang.selectedRow.phieu_id,
            vnhanvien_id: this.nhanVienId,
            vdonvi_id: this.donViId,
            vdonvi_nhan_id: this.nhanVienId,
            vnhanvien_th_id: this.nhanVienId,
            vnd_giao: this.tuVanFilter.noiDung
          }
        )
        const data = await response.data
        if (data.error_code === 'BSS-00000000' && data.error === '200') {
          return data.data
        } else {
          this.$toast.error('Cập nhật khóa mở PayTV không thành công')
          return ''
        }
      } catch (e) {
        this.$toast.error('Không thể cập nhật khóa mở PayTV')
        return ''
      } finally {
        this.loading(false)
      }
    },

    isEmptyObject (object) {
      return object && Object.keys(object).length === 0 && Object.getPrototypeOf(object) === Object.prototype
    },


    async showPopupThongTinGT () {
      if (!this.tuVanFilter.maDonHang) {
        this.$toast.warning('Không có dữ liệu')
        return false
      }
      if (this.hdkhId) {
        await this.$refs.thongTinGT.init()
        this.$refs.popupThongTinGT.show()
      } else {
        this.$toast.warning('Không có dữ liệu HDKH')
      }
    },
    async onChangeTrangThai (value) {
      
      try {
        if(!value) return;
        this.trangThaiPhieu = value
        if (value === PHIEU_MOI) {
          this.onSetButtonVisible('tsbtnCapNhat', true)
          this.onSetButtonVisible('tsbtnGiaoViec', true)
          this.show.isShowKetQua = true
        } else if (value === PHIEU_TRA_LAI) {
          this.onSetButtonVisible('tsbtnCapNhat', true)
          this.onSetButtonVisible('tsbtnGiaoViec', true)
          this.show.isShowKetQua = true
        } else {
 
          this.onSetButtonVisible('tsbtnCapNhat', false)
          this.onSetButtonVisible('tsbtnGiaoViec', false)
          this.show.isShowKetQua = false
        }
        this.tuVanFilter.ketQuaId = null
        this.tuVanFilter.donViTHId = null
        this.tuVanFilter.noiDung = ''
        this.show.isShowDonViTH = false
        this.show.isShowNoiDung = false
        this.show.isShowLyDoTT = false
        this.show.isShowLyDoTra = false;


        const params = {
          p_ungdung_id: this.searchInfo.ungDungId || '0', 
          p_donvi_id: this.searchInfo.donViId || '0', 
          p_khdn: this.searchInfo.loaiKHId,
          p_dichvuvt_id: this.searchInfo.dichVuVtId , 
          p_loaitb_id: this.searchInfo.loaiHinhId , 
          p_ma_nv: this.searchInfo.maKenh || "", 
          p_so_dt: this.searchInfo.sdtKenhBan || "",  
          p_ma_gd: this.searchInfo.maDonHang?.trim() || "", 
          p_tthd_id: this.cptTthd_id,
          p_ttph_id: this.cptTtphId,
          p_tungay: this.searchInfo.dtpTuNgay ? moment(this.searchInfo.dtpTuNgay).format('DD/MM/YYYY') : "",
          p_denngay: this.searchInfo.dtpDenNgay ? moment(this.searchInfo.dtpDenNgay).format('DD/MM/YYYY') : ""
        }
        await this.$refs.componentDanhSachDonHang.onInitComponent(params);
      } catch (e) {
        console.log(e)
      }

    },
    async reloadDanhSachDonHang(){
      const params = {
          p_ungdung_id: this.searchInfo.ungDungId || '0', 
          p_donvi_id: this.searchInfo.donViId || '0', 
          p_khdn: this.searchInfo.loaiKHId,
          p_dichvuvt_id: this.searchInfo.dichVuVtId , 
          p_loaitb_id: this.searchInfo.loaiHinhId , 
          p_ma_nv: this.searchInfo.maKenh || "", 
          p_so_dt: this.searchInfo.sdtKenhBan || "",  
          p_ma_gd: this.searchInfo.maDonHang?.trim() || "", 
          p_tthd_id: this.cptTthd_id,
          p_ttph_id: this.cptTtphId,
          p_tungay: this.searchInfo.dtpTuNgay ? moment(this.searchInfo.dtpTuNgay).format('DD/MM/YYYY') : "",
          p_denngay: this.searchInfo.dtpDenNgay ? moment(this.searchInfo.dtpDenNgay).format('DD/MM/YYYY') : ""
        }
        await this.$refs.componentDanhSachDonHang.onInitComponent(params);
    },
    async onChangeKetQua () {
    this.show.isShowDonViNhan = false
     try {
      if (this.tuVanFilter.ketQuaId === 0) {
        this.show.isShowDonViTH = false
        this.show.isShowNoiDung = false
        this.show.isShowLyDoTT = false
        this.show.isShowLyDoTra = false
      } else if (this.tuVanFilter.ketQuaId === 1) {
        this.show.isShowDonViTH = true
        this.show.isShowNoiDung = true
        this.show.isShowLyDoTT = false
        this.show.isShowLyDoTra = false
        await this.getDanhSachDonViTH()
       
      } else if (this.tuVanFilter.ketQuaId === 3) {
        this.show.isShowDonViNhan = true
        this.show.isShowDonViTH = false
        this.show.isShowNoiDung = true
        this.show.isShowLyDoTT = false
        this.show.isShowLyDoTra = false
       await this.getDanhSachDonViNhan()
      } else if (this.tag === '0') {
        this.show.isShowDonViTH = false
        this.show.isShowLyDoTra = false
        this.show.isShowNoiDung = true
        this.show.isShowLyDoTT = true
        this.tuVanFilter.lyDoTT = null
        await this.getDanhSachLyDoTT()
        
      } else {
        this.show.isShowDonViTH = false
        this.show.isShowNoiDung = true
        this.show.isShowLyDoTT = false
        this.show.isShowLyDoTra = true
      }
      if (this.tuVanFilter.ungDungId === 5) {
        this.isShowLapHopDong = true
      } else {
        this.isShowLapHopDong = this.tuVanFilter.ketQuaId === 1 && this.tuVanFilter.ungDungId === 4
      }
     } catch (error) {
      console.log(error)
     }
      
    },


  async  getLoaiHinhThueBao(dichVuVtId){
      if(!dichVuVtId) return;
      const response = await api.getcboLoaiHinhTBTheoDV(this.axios, {params: {dichVuVtId: dichVuVtId}});
      this.loaiHinhTBOptions = apiHelper.getDataFromResponseApiReturnArray(response).map((x) => ({id: x.ID, text: x.NAME}));
      
      if(this.loaiHinhTBOptions.length > 0){
          this.tuVanFilter.loaiHinhId = this.loaiHinhTBOptions[0].id
      }      
    },

    async getCboGoiCuoc(param){
      const response = await api.getCboGoiCuoc(this.axios, param);
      this.goiCuocOptions = apiHelper.getDataFromResponseApiReturnArray(response).map((x) => ({id: x.goicuoc_id, text: x.tengoicuoc}));
    },

    async getDanhSachDonViTH () {
      try {
        const response = await api.getDanhSachDonViTH(this.axios, { vkieu: 4 })
        this.donViTHOptions = apiHelper.getDataFromResponseApiReturnArray(response).map((x) => ({id: x.donvi_id, text: x.ten_dv}));
       
      } catch (e) {
        console.log(e)
        this.$toast.error('Không thể lấy danh sách đơn vị nhận')
      }
    },

    async getDanhSachDonViNhan () {
      try {
        const response = await api.getDanhSachDonViNhan(this.axios, { loaidv: LOAI_DV.DONVIQL_TT })
        this.donViNhanOptions = apiHelper.getDataFromResponseApiReturnArray(response).map((x) => ({id: x.donvi_id, text: x.ten_dv}));
     
      } catch (e) {
        console.log(e)
        this.$toast.error('Không thể lấy danh sách đơn vị nhận')
        return []
      }
    },
  },
  


  watch:{
    'tuVanFilter.dichVuId': {
      handler: async function (val) {
        if(val)
         await this.getLoaiHinhThueBao(val)
      },
      deep: true
    },
   'tuVanFilter.loaiHinhId':{
    handler: async function (val) {
        if(val)
         await this.getCboGoiCuoc({vtinh_id: this.phanVungId, vloaitb_id: val})
      },
      deep: true
   },
  
  }
}
</script>
<style  scoped>
  

</style>
