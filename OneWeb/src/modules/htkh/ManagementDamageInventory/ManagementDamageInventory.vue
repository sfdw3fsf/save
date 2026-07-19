<template src="./ManagementDamageInventory.html"></template>
<script src="@/static/vendor/jquery/split.js"></script>
<script>
import VueFlatPickr from 'vue-flatpickr-component'
import breadcrumb from '@/components/breadcrumb'
import api from '@/modules/htkh/ManagementDamageInventory/Api'
import * as donvi from './donvivnp.ts'
import PopupTestPort from '@/modules/htkh/ManagementDamageInventory/components/PopupTestPort'

export default {
  components: {
    breadcrumb,
    VueFlatPickr,
    api,
    PopupTestPort
  },
  name: 'ManagementDamageInventory',
  data: function () {
    return {
      isShowDsKhieuNai: false,
      isShowDsKhacKhieuNai: false,
      // Đơn Vị
      donvivnp: donvi,
      donviValue: [],
      listItemDonVi: [],
      listItemDonViGoc: [],
      //
      //đài htkh
      daiHTKHValue:[],
      listdaiHTKHValue: [],
      //#region Khai báo button
      btnTimKiem: {
        enabled: true,
        visible: 1
      },
      btnBoQuaPhieu: {
        enabled: false,
        visible: 1
      },
      btnGiuPhieu: {
        enabled: false,
        visible: 1
      },
      btnShowPort: {
        enabled: false,
        visible: 1
      },
      btnTestPort: {
        enabled: false,
        visible: 1
      },
      btnGiaoPhieu: {
        enabled: true,
        visible: 1
      },
      btnChiTietXly: {
        enabled: true,
        visible: 1
      },
      btnXuatFile: {
        enabled: true,
        visible: 1
      },
      btnXuatExcel: {
        enabled: true,
        visible: 1
      },
      btnLichSuBH: {
        enabled: true,
        visible: 1
      },
      btnGiaoViec: {
        enabled: true,
        visible: 1
      },
      btnChiTietThueBao: {
        enabled: true,
        visible: 1
      },
      //#endregion
      header: {
        title: 'Kiểm soát viên - xử lý báo hỏng tồn',
        list: [
          { name: 'HTKH', link: { name: 'Ui.cards' }, active: false },
          {
            name: 'Xử lý báo hỏng tồn',
            link: { name: 'Ui.buttons' },
            active: true
          }
        ]
      },
      luong_xy_ly_value: '202',
      option_luong_xl: [],
      trang_thai_phieu: {
        trang_thai_phieu_value: '',
        trang_thai_phieu_selected: null
      },
      option_trang_thai_phieu: [],
      checkPortDialog: {
        isVisiable: false,
        input: {
          vma_tb: null,
          vthuebao_id: null,
          vkieu: null
        }
      },
      listDaiHTKH: [],
      listChecboxItemsCol1: [],
      listChecboxItemsCol2: [],

      dsNhanVienGiaoViec: {
        list: [],
        header: [],
        value: {},
        isEnabled: true,
        columns: [
          { fieldName: 'ma_nd', headerText: 'Mã ND', allowFiltering: true },
          { fieldName: 'ten_nv', headerText: 'Tên nhân viên', allowFiltering: true },
          { fieldName: 'ten_dv', headerText: 'Đơn vị', allowFiltering: true }
        ]
      },
      dsTongHop: {
        list: [],
        header: [],
        value: {},
        isEnabled: true,
        columns: [
          { fieldName: 'ma_nd', headerText: 'Tỉnh', allowFiltering: true },
          { fieldName: 'ten_nv', headerText: 'Tổng tồn', allowFiltering: true },
          { fieldName: 'ten_dv', headerText: 'Đang xử lý', allowFiltering: true },
          { fieldName: 'ten_dv', headerText: 'Chưa xử lý', allowFiltering: true }
        ]
      },
      dsPhieuCanXuLy: {
        list: [],
        header: [],
        value: {},
        isEnabled: true,
        selected: {},
        columns: [
          { fieldName: 'baohong_id', headerText: 'id', allowFiltering: true, visible: false },
          { fieldName: '', headerText: 'Tỉnh', allowFiltering: true },
          { fieldName: 'ma_tb', headerText: 'Số máy/Acc', allowFiltering: true },
          { fieldName: 'nguoi_bh', headerText: 'Tên thuê bao', allowFiltering: true },
          { fieldName: 'loaitb_id', headerText: 'Loại hình', allowFiltering: true },
          { fieldName: 'donvi_id', headerText: 'Đơn vị tiếp nhận', allowFiltering: true },
          { fieldName: 'ngay_cn', headerText: 'Ngày báo hỏng', allowFiltering: true },
          { fieldName: 'nguoi_bh', headerText: 'Người lấy phiếu', allowFiltering: true },
          { fieldName: 'ngay_bh', headerText: 'Ngày lấy phiếu', allowFiltering: true },
          { fieldName: 'dienthoai_lh', headerText: 'Điện thoại KH ', allowFiltering: true },
          { fieldName: 'nguoi_cn', headerText: 'Người tạo ', allowFiltering: true },
          { fieldName: 'ghichu_hong', headerText: 'Ghi chú hỏng', allowFiltering: true },
          { fieldName: 'ma_bh', headerText: 'Mã BH', allowFiltering: true },
          { fieldName: 'ttbh_id', headerText: 'Trạng thái BH', allowFiltering: true },
          { fieldName: 'ngay_bh', headerText: 'Ngày BH ', allowFiltering: true },
          { fieldName: 'thoigian_ck', headerText: 'Thời gian CK ', allowFiltering: true },
          { fieldName: 'luot_bh', headerText: 'Lượt BH ', allowFiltering: true },
          { fieldName: 'ht_port', headerText: 'TT PORT', allowFiltering: true },
          { fieldName: 'hensuatu', headerText: 'Giờ hẹn đầu', allowFiltering: true },
          { fieldName: 'hensuaden', headerText: 'Giờ hẹn cuối', allowFiltering: true }
        ]
      },
      // gviewKhieuNai
      dsKhieuNai: {
        list: [],
        header: [],
        value: {},
        isEnabled: true,
        columns: [
          { fieldName: 'TINH', headerText: 'Tỉnh', allowFiltering: true },
          { fieldName: 'MA_KN', headerText: 'Mã khiếu nại', allowFiltering: true },
          { fieldName: 'NGAY_TN', headerText: 'Ngày tiếp nhận', allowFiltering: true },
          { fieldName: 'MA_TB', headerText: 'Số máy/Acc', allowFiltering: true },
          { fieldName: 'LOAIHINH', headerText: 'Loại hình', allowFiltering: true },
          { fieldName: 'TRANGTHAI_KN', headerText: 'Trạng thái KN', allowFiltering: true },
          { fieldName: 'NGUOI_KN', headerText: 'Người KN', allowFiltering: true },
          { fieldName: 'ND_KHIEUNAI', headerText: 'Nội dung KN', allowFiltering: true },
          { fieldName: 'CHUDE_KN', headerText: 'Chủ đề KN', allowFiltering: true },
          { fieldName: 'DONVI_NHAN', headerText: 'Đơn vị tiếp nhận ', allowFiltering: true },
          { fieldName: 'NGUOI_TAO', headerText: 'Người tạo', allowFiltering: true },
          { fieldName: 'TENPHONG', headerText: 'Đơn vị xử lý ', allowFiltering: true },
          { fieldName: 'DIENTHOAI_LH', headerText: 'Điện thoại LH', allowFiltering: true },
          { fieldName: 'NGAY_GQ', headerText: 'Ngày giải quyết', allowFiltering: true },
          { fieldName: 'NGUYENNHAN_KN', headerText: 'Nguyên nhân', allowFiltering: true },
          { fieldName: 'NOIDUNG_GQ', headerText: 'Nội dung GQ', allowFiltering: true }
        ]
      }
    }
  },
  async mounted() {
    this.Lay_Ds_NguoiDung_Tinh(this.$root.token.getNhanVienID(), 1) //đang truyền id nhân viên theo tk login + quyền =1
    this.lay_ds_nhanvien_giao_viec(this.$root.token.getDonViID()) //vdonvi_id_cha đang truyền id đơn vị theo tk login
    this.lay_ds_dai_htkh()
  },
  created() {
    this.loading(true)
    try {
      Promise.all([this.getFormLoad()])
      this.loading(false)
    } catch (ex) {
      this.loading(false)
    }
    this.loading(false)
  },
  computed: {
    cbo_luong_xy_ly: {
      get() {
        return this.option_luong_xl
      },
      set(value) {
        return (this.option_luong_xl = value)
      }
    },
    cbo_trang_thai_phieu: {
      get() {
        return this.option_trang_thai_phieu
      },
      set(value) {
        return (this.option_trang_thai_phieu = value)
      }
    }
  },
  methods: {
    getFormLoad: async function () {
      if (this.luong_xy_ly_value === '200') {
        this.isShowDsKhieuNai = true
        this.isShowDsKhacKhieuNai = false
      } else {
        this.isShowDsKhieuNai = false
        this.isShowDsKhacKhieuNai = true
      }
      (this.option_luong_xl = [
        {
          id: '202',
          text: 'Báo hỏng BRCĐ 18001166'
        },
        {
          id: '203',
          text: 'Báo hỏng CNTT DVS 18001260'
        },
        {
          id: '200',
          text: 'Khiếu nại'
        }
      ]),
        (this.option_trang_thai_phieu = [
          {
            id: '1',
            text: 'Mới tiếp nhận'
          },
          {
            id: '5',
            text: 'Chờ nghiệm thu'
          },
          {
            id: '3,8,9,15,16',
            text: 'Đang chờ VT xử lý'
          }
        ])
    },
    Showport(vkieu) {
      this.checkPortDialog.input.vkieu = vkieu
      this.checkPortOnClick()
    },
    async checkPortOnClick() {
      this.checkPortDialog.isVisiable = true
      // this.$refs.checkPort.show()
    },
    cboLuongXuly_SelectedValueChanged() {
      if (this.luong_xy_ly_value === '200') {
        this.trang_thai_phieu.trang_thai_phieu_value = '';
        this.trang_thai_phieu.trang_thai_phieu_selected= null;
        this.isShowDsKhieuNai = true
        this.isShowDsKhacKhieuNai = false
        this.option_trang_thai_phieu = [
          {
            id: '1',
            text: 'Mới tiếp nhận'
          },
          {
            id: '2',
            text: 'Đã giao xử lý'
          },
          {
            id: '3',
            text: 'Giao phòng ban xử lý'
          },
          {
            id: '4',
            text: 'Xác minh xong'
          },
          {
            id: '5',
            text: 'Đã xử lý xong'
          },
          {
            id: '6',
            text: 'Đã hoàn thành'
          },
          {
            id: '7',
            text: 'Báo tồn'
          },
          {
            id: '8',
            text: 'Khảo sát tiếp nhận khiếu nại'
          },
          {
            id: '9',
            text: 'PNV-Xử lý khiếu nại'
          },
          {
            id: '10',
            text: 'PBH-nhận trả lời từ PNV'
          },
          {
            id: '11',
            text: 'TTKT-Xử lý khiếu nại'
          },
          {
            id: '12',
            text: 'Trạm VT- Xử lý'
          },
          {
            id: '13',
            text: 'Chờ lãnh đạo PBH xác nhận'
          }
        ]
      } else {
        this.trang_thai_phieu.trang_thai_phieu_value = '';
        this.trang_thai_phieu.trang_thai_phieu_selected= null;
        this.option_trang_thai_phieu = [
          {
            id: '1',
            text: 'Mới tiếp nhận'
          },
          {
            id: '5',
            text: 'Chờ nghiệm thu'
          },
          {
            id: '3,8,9,15,16',
            text: 'Đang chờ VT xử lý'
          }
        ]
        this.isShowDsKhieuNai = false
        this.isShowDsKhacKhieuNai = true
      }
      
    },
    cboTrangThaiPhieu_SelectedValueChanged(data) {
      let data_ids = ''
      data.forEach((item) => {
        data_ids += item.id + ','
      })
      this.trang_thai_phieu_selected = data_ids.slice(0, -1)
      console.log(this.trang_thai_phieu_selected, 'trang_thai_phieu_selected')
    },
    ClearGridView() {
      this.dsKhieuNai.list = null
      // this.dsNhanVienGiaoViec.list = null
      this.dsPhieuCanXuLy.list = null
      this.dsTongHop.list = null
    },
    //#region Button click
    btnTimKiem_Click() {
      let $thisData = this

      if (!this.trang_thai_phieu_selected) {
        $thisData.$toast.warning('Bạn chưa chọn trạng thái phiếu!')
        return false
      }
      this.donviValue= [1]
      if (!this.donviValue.length > 0) {
        $thisData.$toast.warning('Bạn chưa chọn tỉnh!')
        return false
      }
      this.ClearGridView()
      this.donviValue = this.donviValue.filter((item) => item !== 'mienbac' && item !== 'miennam' && item !== 'mientrung')
      this.LoadGridviewTongHop(this.luong_xy_ly_value, '', 15, this.trang_thai_phieu_selected)
      $thisData.loading(true)
      var arrDonvi = ''
      var arrDaihtkh = ''
      switch (this.luong_xy_ly_value) {
        case '200': //Khiếu nại
          console.log(this.luong_xy_ly_value, 'luongxuly Khiếu nại')
          arrDonvi = ''
          arrDaihtkh = ''
          this.daiHTKHValue.forEach((item) => {
            arrDaihtkh += item + ','
          })
          this.donviValue.forEach((item) => {
            arrDonvi += item + ','
          })
          var pdata = {
            // vdstinh: arrDonvi.slice(0, -1), //lấy id từ tree đơn vị
            vdsdonvi: arrDaihtkh.slice(0, -1), //lấy id từ tree đơn vị
            vdstrangthai: this.trang_thai_phieu_selected
          }
          api
            .lay_du_lieu_200(this.axios, pdata)
            .then((response) => {
              if (response.data.message == 'No Content') {
                $thisData.$toast.error('Không có dữ liệu hiển thị')
              }
              if (response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000' && response.data.data) {
                this.dsKhieuNai = response.data.data
                this.SetButton('timkiem')
              } else {
                this.$toast.error('Không có dữ liệu hiển thị')
              }
              $thisData.loading(false)
            })
            .catch(function (err) {
              console.log(err, 'err')
              $thisData.$toast.error(err.data.message + '-' + err.data.message_detail)
              $thisData.loading(false)
            })
            .finally(() => {
              $thisData.loading(false)
            })
          break
        case '202': //BRCĐ & PSTN
          console.log(this.luong_xy_ly_value, 'luongxl BRCĐ & PSTN')
          arrDonvi = ''
          this.donviValue.forEach((item) => {
            arrDonvi += item + ','
          })
          var pdata = { vdstinh: arrDonvi.slice(0, -1), vdstrangthai: this.trang_thai_phieu_selected } //vdstinh lấy list id tỉnh từ tree tỉnh
          api
            .lay_du_lieu_006(this.axios, pdata)
            .then((response) => {
              if (response.data.message == 'No Content') {
                $thisData.$toast.error('Không có dữ liệu hiển thị')
              }
              if (response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000' && response.data.data) {
                this.dsPhieuCanXuLy.list = response.data.data
                this.SetButton('timkiem')
              } else {
                //code
              }
              $thisData.loading(false)
            })
            .catch(function (err) {
              console.log(err, 'err')
              $thisData.$toast.error(err.data.message + '-' + err.data.message_detail)
              $thisData.loading(false)
            })
            .finally(() => {
              $thisData.loading(false)
            })
          break
        case '203': //CNTT & DVS
          console.log(this.luong_xy_ly_value, 'luongxlCNTT & DVS')
          arrDonvi = ''
          arrDaihtkh = ''
          this.daiHTKHValue.forEach((item) => {
            arrDaihtkh += item + ','
          })
          this.donviValue.forEach((item) => {
            arrDonvi += item + ','
          })
          var pdata = { vdstinh: arrDonvi.slice(0, -1), vdstrangthai: this.trang_thai_phieu_selected, vdsdonvi: arrDaihtkh.slice(0, -1) }
          api
            .lay_du_lieu_203(this.axios, pdata)
            .then((response) => {
              if (response.data.message == 'No Content') {
                $thisData.$toast.error('Không có dữ liệu hiển thị')
              }
              if (response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000' && response.data.data) {
                this.dsPhieuCanXuLy = response.data.data
                this.SetButton('timkiem')
              } else {
                $thisData.$toast.error('Không có dữ liệu hiển thị')
              }
              $thisData.loading(false)
            })
            .catch(function (err) {
              console.log(err, 'err')
              $thisData.$toast.error(err.data.message + '-' + err.data.message_detail)
              $thisData.loading(false)
            })
            .finally(() => {
              $thisData.loading(false)
            })
          break
      }
      console.log(this.dsPhieuCanXuLy.selected.baohong_id, 'baohong_id')
      // this.SetButton(1)
    },
    btnBoQuaPhieu_Click() {},
    btnGiuPhieu_Click() {
      let $thisData = this
      console.log(this.dsPhieuCanXuLy.selected.baohong_id, 'dataRowDsPhieuCanXL')
      api
        .giuphieu_bh_nhanhkhac(this.axios, { vbaohong_id: this.dsPhieuCanXuLy.selected.baohong_id })
        .then((response) => {
          if (response.data.message == 'No Content') {
            $thisData.$toast.error('Không có dữ liệu hiển thị')
          }
          if (response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000' && response.data.data) {
            $thisData.$toast.success('Giữ phiếu thành công!') //API tra ve data null
          } else {
            $thisData.$toast.error('Lỗi ' + response.error + ' - ' + response.error_code)
          }
          $thisData.loading(false)
        })
        .catch(function (err) {
          console.log(err, 'err')
          if (err.data) {
            $thisData.$toast.error(err.data.message + '-' + err.data.message_detail)
          }
          $thisData.loading(false)
        })
        .finally(() => {
          $thisData.loading(false)
        })
    },
    btnShowPort_Click() {
      this.Showport(0)
    },
    btnTestPort_Click() {
      if (this.dsPhieuCanXuLy.selected.baohong_id) {
        this.$refs.popupTestPort.show()
      } else {
        this.$toast.error('Chưa chọn phiếu yêu cầu')
      }
    },
    btnGiaoPhieu_Click() {},
    btnChiTietXly_Click() {
      switch (this.trang_thai_phieu) {
        case 'moitiepnhan': 
          if(this.dsPhieuCanXuLy.selected.ma_tb && this.dsPhieuCanXuLy.selected.baohong_id && this.dsPhieuCanXuLy.selected.ma_tb){
            let route_moitiepnhan = this.$router.resolve({ path: "/incident/ReceiveIncident?tag=3&thuebao_id="+ this.dsPhieuCanXuLy.selected.ma_tb +"&baohong_id="+ this.dsPhieuCanXuLy.selected.baohong_id +"&ma_tb="+ this.dsPhieuCanXuLy.selected.ma_tb +"" });
            window.open(route.href);
          }
          break
        case 'dangchovtxuly': 
          if(this.dsPhieuCanXuLy.selected.ma_tb){
            let route_dangchovtxuly = this.$router.resolve({ path: "/incident/SearchIncident?ma_tb="+ this.dsPhieuCanXuLy.selected.ma_tb });
            window.open(route.href);
          }
          break
        case 'chonghiemthu': 
        if(this.dsPhieuCanXuLy.selected.ma_tb && this.dsPhieuCanXuLy.selected.baohong_id && this.dsPhieuCanXuLy.selected.ma_tb){
            let route_chonghiemthu = this.$router.resolve({ path: "/incident/ProcessAcceptanceIncidentByTag?tag=5&kieu_Load=1&matb_tk=dbntv00007554&dichvt_tk_id="+ this.dsPhieuCanXuLy.selecteddichvuvt_id +"&quytrinh_tk_id=30229" });
            window.open(route.href);
          }
          // matb_tk; quytrinh_tk_id; kieu_Load đang không biết là trường nào trong response trả về
          break
        case 'xulysucoit360': 
        let route_xulysucoit360 = this.$router.resolve({ path: "/incident/SearchIncident?ma_tb="+ this.dsPhieuCanXuLy.selected.ma_tb });
          window.open(route.href);
          break
      }
    },
    btnXuatFile_Click() {},
    btnXuatExcel_Click() {},
    btnLichSuBH_Click() {
      console.log(this.dsPhieuCanXuLy.selected)
      if(this.dsPhieuCanXuLy.selected.baohong_id){
        if( this.dsPhieuCanXuLy.selected.ma_tb){
          let route = this.$router.resolve({ path: "/incident/SearchIncident?ma_tb="+ this.dsPhieuCanXuLy.selected.ma_tb });
          window.open(route.href);
        }
      } else this.$toast.error('Chưa chọn phiếu cần xử lý')
    },
    btnGiaoViec_Click() {},
    btnChiTietThueBao_Click() {},
    //#endregion
    LoadGridviewTongHop(luongxl, tinh_id, a, trangthaiphieu) {},
    onDataSelectedRow(data) {
      this.dsPhieuCanXuLy.selected = data[0]
    },
    SetButton(kieu) {
      switch (kieu) {
        case 'timkiem': //Bat dau
          this.btnBoQuaPhieu.enabled = true
          this.btnGiuPhieu.enabled = true
          this.btnTestPort.enabled = true
          this.btnShowPort.enabled = true
          break
      }
    },
    async Lay_Ds_NguoiDung_Tinh(nhanVienID, quyen = 1) {
      console.log(nhanVienID, 'nv_id')
      try {
        let pdata = {
          vnhanvien_id: nhanVienID,
          vquyen_id: quyen
        }
        // let pdata = {
        //   vnhanvien_id: 1,
        //   vquyen_id: quyen
        // }
        // let list_tinh = [
        //   {
        //     tinh_id: 1 
        //   },
        //   {
        //     tinh_id: 23
        //   },
        //   {
        //     tinh_id: 10
        //   },
        //   {
        //     tinh_id: 34
        //   },
        //   {
        //     tinh_id: 2
        //   },
        //   {
        //     tinh_id: 3
        //   },
        //   {
        //     tinh_id: 4
        //   }
        // ]
        let list_tinh = await api.lay_ds_nguoi_dung_tinh(this.axios, pdata);
        console.log(list_tinh, 'ltinh')
        if (list_tinh.data.error_code === 'BSS-00000000' && list_tinh.data.data !== undefined && list_tinh.data.data.length > 0) {
        let arr_tinh = list_tinh.data.data.map((item) => item.tinh_id)
        //let arr_tinh = list_tinh.map((item) => item.tinh_id)
        let list_donvi = await api.lay_ds_donvi(this.axios)
        console.log(list_donvi, 'dvi')
        if (list_donvi.data.error_code === 'BSS-00000000' && list_donvi.data.data !== undefined && list_donvi.data.data.length > 0) {
          let arr_donvi = list_donvi.data.data
          arr_donvi = arr_donvi.filter((x) => arr_tinh.indexOf(x.id) != -1)

          let items = []
          let itemMienBac = []
          let itemMienTrung = []
          let itemMienNam = []

          arr_donvi.forEach(function (item) {
            if (item.vung == 1) {
              // Miền Bắc
              itemMienBac.push({
                id: item.id,
                label: item.ten + ' - ' + item.ma
              })
            }

            if (item.vung == 2) {
              // Miền Trung
              itemMienTrung.push({
                id: item.id,
                label: item.ten + ' - ' + item.ma
              })
            }

            if (item.vung == 3) {
              // Miền Nam
              itemMienNam.push({
                id: item.id,
                label: item.ten + ' - ' + item.ma
              })
            }
          })

          if (itemMienBac.length > 0) {
            items.push({
              id: 'mienbac',
              label: 'Vùng : Miền Bắc',
              children: itemMienBac
            })
          }

          if (itemMienTrung.length > 0) {
            items.push({
              id: 'mientrung',
              label: 'Vùng : Miền Trung',
              children: itemMienTrung
            })
          }

          if (itemMienNam.length > 0) {
            items.push({
              id: 'miennam',
              label: 'Vùng : Miền Nam',
              children: itemMienNam
            })
          }

          this.listItemDonViGoc = items
        }
        }
      } catch (e) {
        console.log(e)
      }
    },
    removeNoChildren(node) {
      return {
        children: node.children && node.children.length ? node.children : 0
      }
    },
    onChangeTreeSelect: function (dataitem) {
      console.log(dataitem, 'treedvi')
    },
    async lay_ds_nhanvien_giao_viec(vdonvi_id_cha) {
      console.log(vdonvi_id_cha, 'vdonvi_id_cha')
      await api.lay_ds_nguoi_dung_don_vi_con(this.axios, { vdonvi_id_cha: vdonvi_id_cha }).then((response) => {
        if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined && response.data.data.length > 0) {
          this.dsNhanVienGiaoViec.list = response.data.data
          console.log(this.dsNhanVienGiaoViec.list, 'listNVGiaoViec')
        }
      })
    },
    async lay_ds_dai_htkh() {
      await api.lay_ds_donvi_dai_htkh(this.axios, {}).then((response) => {
        if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined && response.data.data.length > 0) {
          this.listDaiHTKH = response.data.data
          let length = this.listDaiHTKH.length
          let divLength = length / 2
          let items = []
          let itemMienBac = []
          let itemMienTrung = []
          let itemMienNam = []
          this.listDaiHTKH.forEach(function (item) {
            
            if (item.donvi_cha_id == 15934) {
              // Miền trung
              itemMienTrung.push({
                id: item.donvi_id,
                label: item.ten_dv
              })
            }
            if (item.donvi_cha_id == 15964) {
              // Miền nam
              itemMienNam.push({
                id: item.donvi_id,
                label: item.ten_dv
              })
            }
            if (item.donvi_cha_id == 15904) {
              // Miền Bắc
              itemMienBac.push({
                id: item.donvi_id,
                label: item.ten_dv
              })
            }
          })
           //lấy con lvl2 miền trung
          let itemMienTrungLvl2_9992 = []
          let itemMienTrungLvl2_15935 = []
          this.listDaiHTKH.forEach(function (item) {
            if (item.donvi_cha_id == 9992) {
            // Miền trung
            itemMienTrungLvl2_9992.push({
              id: item.donvi_id,
              label: item.ten_dv
            })
          }
            if (item.donvi_cha_id == 15935) {
            // Miền trung
            itemMienTrungLvl2_15935.push({
              id: item.donvi_id,
              label: item.ten_dv
            })
          }
        })

          if (itemMienTrungLvl2_9992.length >0){
            itemMienTrung.forEach(function (item) {
              if(item.id == 9992){
                item.children = itemMienTrungLvl2_9992;
              }
            })
          }
          if (itemMienTrungLvl2_15935.length >0){
            itemMienTrung.forEach(function (item) {
              if(item.id == 15935){
                item.children = itemMienTrungLvl2_15935;
              }
            })
          }
          //15935 -Phòng CNTT & DVS - 1260 MT
          //9992 -Phòng BRCD - 1166 MT
          //end lấy con lvl2 miền trung

        //lấy con lvl2 miền bac
          let itemMienBacLvl2_9997 = []
          let itemMienBacLvl2_15905 = []
          this.listDaiHTKH.forEach(function (item) {
            if (item.donvi_cha_id == 9997) {
            // Miền bắc
            itemMienBacLvl2_9997.push({
              id: item.donvi_id,
              label: item.ten_dv
            })
          }
            if (item.donvi_cha_id == 15905) {
            // Miền bắc
            itemMienBacLvl2_15905.push({
              id: item.donvi_id,
              label: item.ten_dv
            })
          }
        })

          if (itemMienBacLvl2_9997.length >0){
            itemMienBac.forEach(function (item) {
              if(item.id == 9997){
                item.children = itemMienBacLvl2_9997;
              }
            })
          }
          if (itemMienBacLvl2_15905.length >0){
            itemMienBac.forEach(function (item) {
              if(item.id == 15905){
                item.children = itemMienBacLvl2_15905;
              }
            })
          }
          //15905 -Phòng CNTT & DVS - 1260 MB
          //9997 -Phòng BRCD - 1166 MB
          //end lấy con lvl2 miền bắc

//lấy con lvl2 miền nam
let itemMienNamLvl2_9991 = []
          let itemMienNamLvl2_15965 = []
          this.listDaiHTKH.forEach(function (item) {
            if (item.donvi_cha_id == 9991) {
            // Miền bắc
            itemMienNamLvl2_9991.push({
              id: item.donvi_id,
              label: item.ten_dv
            })
          }
            if (item.donvi_cha_id == 15965) {
            // Miền bắc
            itemMienNamLvl2_15965.push({
              id: item.donvi_id,
              label: item.ten_dv
            })
          }
        })

          if (itemMienNamLvl2_9991.length >0){
            itemMienNam.forEach(function (item) {
              if(item.id == 9991){
                item.children = itemMienNamLvl2_9991;
              }
            })
          }
          if (itemMienNamLvl2_15965.length >0){
            itemMienNam.forEach(function (item) {
              if(item.id == 15965){
                item.children = itemMienNamLvl2_15965;
              }
            })
          }
          //15965 -Phòng CNTT & DVS - 1260 MN
          //9991 -Phòng BRCD - 1166 MN
          //end lấy con lvl2 miền nam

          if (itemMienBac.length > 0) {
            items.push({
              id: 15904,
              label: 'Đài HTKH Miền Bắc',
              children: itemMienBac
            })
          }

          if (itemMienTrung.length > 0) {
            items.push({
              id: 15934,
              label: 'Đài HTKH Miền Trung',
              children: itemMienTrung
            })
          }

          if (itemMienNam.length > 0) {
            items.push({
              id: 15964,
              label: 'Đài HTKH Miền Nam',
              children: itemMienNam
            })
          }
          this.listDaiHTKH.forEach(function (item) {
            if (item.donvi_id == 15404) {
              // Phòng Tổng hợp - Trung tâm HTKH
              items.push({
                id: item.donvi_id,
                label: item.ten_dv
              })
            }
            if (item.donvi_id == 15405) {
              // Phòng Tổng hợp - Trung tâm HTKH
              items.push({
                id: item.donvi_id,
                label: item.ten_dv
              })
            }
          })
          this.listdaiHTKHValue = items;
          // this.listChecboxItemsCol1.push(items)

          // this.listDaiHTKH.forEach((item, index) => {
          //   if (index < divLength && !this.listChecboxItemsCol1.includes(item)) {
          //     this.listChecboxItemsCol1.push(item)
          //   } else if ((index = divLength && index < length && !this.listChecboxItemsCol2.includes(item))) {
          //     this.listChecboxItemsCol2.push(item)
          //   }
          // })
          console.log(this.listDaiHTKH, 'listDaiHTKH')
        }
      })
    }
  }
}
</script>
<style>
.vue-treeselect__menu-container {
  height: auto;
  position: relative;
}

.vue-treeselect__menu {
  height: auto;
  position: relative;
}
</style>
