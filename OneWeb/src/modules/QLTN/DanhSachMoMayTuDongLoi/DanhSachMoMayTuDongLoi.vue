<template src="./DanhSachMoMayTuDongLoi.html"></template>

<script>
import Vue from 'vue'
import moment from 'moment'
import CommonsAPI from '../api/Commons'
import MoMayTuDongLoiAPI from '../api/MoMayTuDongLoi'
import KyCuoc from '@/components/KyCuoc2'
import { Query } from '@syncfusion/ej2-data'
import GiaoPhieu from './components/PopupGiaoPhieu.vue'
import {cloneArray} from "@/utils/util"
import { DateTimePickerPlugin } from "@syncfusion/ej2-vue-calendars"

Vue.use(DateTimePickerPlugin)



import {
    DichVuVienThong,
    TrangThaiHD
  } from "@/const/enums"


export default{
  components: {
    appKyCuoc: KyCuoc,
    appGiaoPhieu: GiaoPhieu
  },
  async created () {

    if(this.$route.query.dsloaihd_id  !== undefined
      && this.$route.query.dsloaihd_id !== null
      && this.$route.query.dsloaihd_id !== '')
    {
      this.params.p_dsloaihd_id = this.$route.query.dsloaihd_id.split(',')
    } else
    {
      this.params.p_dsloaihd_id = [1]
    }

    if(this.$route.query.tthd_id  !== undefined
      && this.$route.query.tthd_id !== null
      && this.$route.query.tthd_id !== '')
    {
      this.params.p_tthd_id = this.$route.query.tthd_id
    } else {
      this.params.p_tthd_id = TrangThaiHD.DANG_THI_CONG
    }


    if(this.$route.query.dsdichvuvt_id  !== undefined
      && this.$route.query.dsdichvuvt_id !== null
      && this.$route.query.dsdichvuvt_id !== '')
    {
      //this.params.p_dsdichvuvt_id = this.$route.query.dsdichvuvt_id.split(',')
      const ds = this.$route.query.dsdichvuvt_id.split(',')
      if(ds.length > 0)
        ds.forEach(x => {
          this.params.p_dsdichvuvt_id.push(parseInt(x))
        })
    } else
    {
      this.params.p_dsdichvuvt_id = [1]
    }

    const res  = await Promise.all([
      this.loadDSDichVuVT(),
      this.loadDSNguoiGV(),
      this.getThamSoNhanTinDiaBan(),
      this.getDONGBOGOIDV()
    ])
    


    //this.initUI()

    if(this.params.p_tthd_id === TrangThaiHD.DA_THI_CONG_XONG)
    {
      this.configs.f_hoanthien_hs_visible = true
      this.configs.f_hoanthien_hs_enabled = true
    }


    this.params.p_ds_khuvuc = '40,10416,10417'//this.getDataFromStorage(this.KEY_DS_KHUVUC)
  },

  computed : {
    chk_ngay_gv()
    {
      return this.params.p_chk_ngay_gv === '1'
    },
    chk_ngay_bg()
    {
      return this.params.p_chk_ngay_bg === '1'
    },
    p_ngay_gv : {
      get() {
        return this.params.p_ngay_gv
      },
      set(value) {
        return this.params.p_ngay_gv = value
      }
    },
    p_ngay_bg : {
      get() {
        return this.params.p_ngay_bg
      },
      set(value) {
        return this.params.p_ngay_bg = value
      }
    }
  },
  data() {
    return {
      KEY_DS_KHUVUC: 'KEY_DS_KHUVUC',
      configs: {
        dsNhanVien: [
          {fieldName: 'TEN_NV', headerText: 'Tên nhân viên', headerTextAlign: 'Center', allowFiltering: true, width: 120},
          {fieldName: 'SO_DT', headerText: 'Điện thoại', headerTextAlign: 'Center', allowFiltering: true, width: 120},
          {fieldName: 'NHIEMVU', headerText: 'Nhiệm vụ', headerTextAlign: 'Center', allowFiltering: true, width: 200},
          {fieldName: 'GHICHU', headerText: 'Ghi chú', headerTextAlign: 'Center', allowFiltering: true, width: 200},
        ],
        dsThueBao: [
          {fieldName: 'MA_TB', headerText: 'Số máy/Acc', headerTextAlign: 'Center', allowFiltering: true, width: 120},
          {fieldName: 'LOAIHINH_TB', headerText: 'Loại hình', headerTextAlign: 'Center', allowFiltering: true, width: 100, visible: false},
          {fieldName: 'TEN_TB', headerText: 'Tên thuê bao', headerTextAlign: 'Center', allowFiltering: true, width: 200},
          {fieldName: 'DIACHI_LD', headerText: 'Địa chỉ lắp đặt', headerTextAlign: 'Center', allowFiltering: true, width: 200},
          {fieldName: 'TEN_DV', headerText: 'Đơn vị gửi', headerTextAlign: 'Center', allowFiltering: true, width: 200},
          {fieldName: 'CHIEU_KM', headerText: 'Chiều khóa/mở', headerTextAlign: 'Center', allowFiltering: true, width: 130},
          {fieldName: 'TEN_STATUS', headerText: 'Kích hoạt Visa/Vasc', headerTextAlign: 'Center', allowFiltering: true, width: 120},
          //{fieldName: 'HOST_LD', headerText: 'Host LĐ', headerTextAlign: 'Center', allowFiltering: true, width: 120},
          {fieldName: 'CACH_KM', headerText: 'Cách khóa/mở', headerTextAlign: 'Center', allowFiltering: true, width: 140},
          {fieldName: 'NGAY_HT_GP', headerText: 'Ngày giao', headerTextAlign: 'Center', textAlign: 'Right', allowFiltering: true, width: 140},
          //{fieldName: 'DICHVUVT_ID', headerText: 'DICHVUVT_ID', headerTextAlign: 'Center', allowFiltering: true, width: 120},
          //{fieldName: 'STATUS', headerText: 'STATUS', headerTextAlign: 'Center', allowFiltering: true, width: 120},
          
          //{fieldName: 'NV_XACNHAN', headerText: 'NV xác nhận', headerTextAlign: 'Center', allowFiltering: true, width: 200},
          //{fieldName: 'KQ_XACNHAN', headerText: 'KQ xác nhận', headerTextAlign: 'Center', allowFiltering: true, width: 200},
          {fieldName: 'ND_THUCHIEN', headerText: 'ND thực hiện', headerTextAlign: 'Center', allowFiltering: true, width: 200},
          //{fieldName: 'NGAY_KYTHUAT_XM', headerText: 'Ngày XN', headerTextAlign: 'Center', textAlign: 'Right', allowFiltering: true, width: 120},
        ],
        selectionSettings: {checkboxOnly: true, type: 'Multiple'},
        fieldsDichVu: {text: 'TEN_DVVT', value: 'DICHVUVT_ID' },
        fieldsNguoiGV: {text: 'TEN_NV', value: 'NHANVIEN_ID'},
        dateConfig: {
          value: new Date(),
          format: 'dd/MM/yyyy hh:mm',
          max: new Date()
        },
        f_hoanthien_hs_visible: false,
        f_hoanthien_hs_enabled: false,
        f_hoancong_visible: false,
        f_hoanthanh_visible: false,
        f_giaophieu_visible: false,
        f_giaophieu_enabled: false,
        f_giaoviec_visible: false,
        f_kichhoat_visible: false,
        f_kichhoat_enabled: false,
        f_chapnhan_visible: false,
        f_ngay_bg_enabled: false,
        f_excel_enabled: false,
        f_xuatfile_enabled: false,
        f_xoahd_visible: false,
        f_traphieu_visible: false,
        p_dongbo_dadv: -1,
      },
      params: {
        p_kycuoc: null,
        p_hdtb_id: 0,
        p_phieu_id: 0,
        p_dvnhan_id: 0,
        p_dichvu_options: [],
        p_dichvu: null,
        p_loaitb_id: 0,
        p_chk_loi_kichhoat: '1',
        p_chk_loi_hoancong: '1',
        p_nguoi_gv_options: [],
        p_nguoi_gv: null,
        p_chk_ngay_gv: '0',
        p_chk_ngay_bg: '0',
        p_ngay_gv: new Date(),
        p_ngay_bg: new Date(),
        p_tthd_id: null,
        p_dsloaihd_id : [],
        p_dsdichvuvt_id : [],
        p_ds_khuvuc: '',
        p_tthd_id : 0,
        p_nhantin_nhanvien_diaban: 0,
        p_luong_id: 0,
        p_noidung_thuchien: null,
        p_ma_tb: null,
        p_status: -1,
        p_chk_HCTheoFile: '0',
        p_loai: '0',
        p_kieu_yc: 0, // 0: mở, 1: khóa
      },
      dsThueBao: [],
      dsThueBaoPageInfo: {
        page: 0,
        maxSize: 10,
        totalElement: 0
      },
      keyDSThueBao: 1,

      dsNhanVien: [],
      dsNhanVienPageInfo: {
        page: 0,
        maxSize: 10,
        totalElement: 0
      },
      keyDSNhanVien: 1,
      dsThaoTac: [],
    }
  },
  methods : {
    HienThiGiaoDien()
    {
      this.configs.f_hoanthien_hs_visible = false
      this.configs.f_hoanthien_hs_enabled = false
      this.configs.f_hoancong_visible = false
      this.configs.f_hoanthanh_visible = false
      this.configs.f_giaophieu_visible = false
      this.configs.f_giaoviec_visible = false
      this.configs.f_kichhoat_visible = false
      this.configs.f_kichhoat_enabled = false

      this.loading(true)
      this.getDSTheoTacTheoLuong(this.params.p_luong_id)
        .then((response) => {
          if (response.data.error_code === 'BSS-00000000'
                && response.data.data !== undefined
                && response.data.data.length > 0)
          {
            this.dsThaoTac = response.data.data
            this.dsThaoTac.forEach(item => {
              this.getDSControlTheoThaoTac(item.THAOTAC_ID)
                .then((res) => {
                  if (res.data.error_code === 'BSS-00000000'
                    && res.data.data !== undefined
                    && res.data.data.length > 0)
                  {

                  }
                })
                .catch(error => {

                })
            })
          }
        })
        .catch(error => {
          this.$toast.error(error.data.message_detail)
        })
        .finally(() => {
          this.loading(false)
        })
    },
    async getThamSoNhanTinDiaBan()
    {
      try{
        const res = await CommonsAPI.getThamSoMacDinh(this.axios, { pMaThamSo: 'LHD_NHANTIN_DIABAN'})
        const data = await res.data

        if (data.error_code === 'BSS-00000000'
          && data.data !== undefined
          && data.data.length > 0)
        {
          let ts = parseInt(data.data[0].MA_TS)
          if(ts === 1)
            this.params.p_nhantin_nhanvien_diaban = 1
        }
      }catch(error){
        console.log(error)
        if(error.data !== undefined)
          this.$toast.error(error.data.message_detail)
        else
          this.$toast.error(error)
      }finally{}
    },

    async getDONGBOGOIDV()
    {

      try {
        const res = await CommonsAPI.getThamSoMacDinh(this.axios, { pMaThamSo: 'DONGBO_GOI_DA_DV'})
        const data = await res.data

        if (data.error_code === 'BSS-00000000'
          && data.data !== undefined
          && data.data.length > 0)
        {
          this.configs.p_dongbo_dadv = parseInt(data.data[0].MA_TS)
        }
      }catch(error){
        console.log(error)
        if(error.data !== undefined)
          this.$toast.error(error.data.message_detail)
        else
          this.$toast.error(error)
      }finally{}

    },

    async getDSTheoTacTheoLuong(luong_id)
    {
      const res = await MoMayTuDongLoiAPI.getThaoTacTheoLuong(this.axios, luong_id)
      return res
    },

    async getDSControlTheoThaoTac(thaotac_id)
    {
      const res = await MoMayTuDongLoiAPI.getDSControlTheoThaoTac(this.axios, thaotac_id)
      return res
    },

    async getHuongGiaoTheoLuong(luong_id)
    {
      const res = await MoMayTuDongLoiAPI.getHuongGiaoTheoLuong(this.axios, luong_id)
      return res
    },

    KT_DieuKien_TB(kieu)
    {
      let kt = true;
      if(this.dsThueBao.length <= 0)
      {
        this.$toast.error('Không có thuê bao trong danh sách.')
        return false
      }
      let selectedRows = this.$refs.dsThueBao.getSelectedRecords()
      if(selectedRows.length <= 0)
      {
        this.$toast.error('Chưa chọn thuê bao trong danh sách.')
        return false
      }
      try
      {
          ///kieu : 1: Kiểm tra Xóa HĐ
          ///kieu : 2: Kiểm tra Kích hoạt
          ///kieu : 4: Kiểm tra Hoàn công
          if (kieu === 1) //Xóa HĐ
          {
          }
          else if (kieu === 2) //Kích hoạt
          {

            let f = selectedRows.filter(x => (x.NGAY_HT_GP === null || x.NGAY_HT_GP === ''))

            if(f.length > 0)
            {
              let ds = f.map(x => x.MA_TB)
              this.$toast.error('Thuê bao: ' + ds.join(',') + ' chưa cập nhật thông tin.\nBạn không thể Kích hoạt thuê bao này!')
              return false
            }
          }
          else if (kieu == 4) //Hoàn công
          {
            let f = selectedRows.filter(x => (x.NGAY_HT_GP === null || x.NGAY_HT_GP === ''))

            if(f.length > 0)
            {
              let ds = f.map(x => x.MA_TB)
              this.$toast.error('Thuê bao: ' + ds.join(',') + ' chưa cập nhật thông tin.\nBạn không thể Kích hoạt thuê bao này!')
              return false
            }

            f = selectedRows.filter(x => (x.STATUS !== TRANGTHAI_DONGBO.DONGBO_CM
                          && (x.NGAY_HT_GP === null || x.NGAY_HT_GP === '')
                          && (x.DICHVUVT_ID === DichVuVienThong.ADSL || x.DICHVUVT_ID === DichVuVienThong.IMS)))

            if(f.length > 0)
            {
              let ds = f.map(x => x.MA_TB)
              this.$toast.error('Thuê bao: ' + ds.join(',') + ' chưa cập nhật thông tin.\nBạn không thể Kích hoạt thuê bao này!')
              return false
            }

          }
          else if (kieu == 5) //Hoàn thiện HS
          {
            let f = selectedRows.filter(x => (x.NGAY_HT_GP === null || x.NGAY_HT_GP === ''))

            if(f.length > 0)
            {
              let ds = f.map(x => x.MA_TB)
              this.$toast.error('Thuê bao: ' + ds.join(',') + ' chưa cập nhật thông tin.\nBạn không thể Kích hoạt thuê bao này!')
              return false
            }
          }
      }
      catch (error)
      {
          return false;
      }
      return true;
    },

    async loadDSDichVuVT()
    {
      try{
        const res = await CommonsAPI.getDanhSachDichVuVienThong(this.axios)
        const data = await res.data

        if (data.error_code === 'BSS-00000000'
                && data.data !== undefined
                && data.data.length > 0)
        {
          let ds = data.data.filter(x => this.params.p_dsdichvuvt_id.includes(x.DICHVUVT_ID))
          this.params.p_dichvu_options = cloneArray(ds)
          this.params.p_dichvu = this.params.p_dichvu_options[0].DICHVUVT_ID

        }

      }catch(error){
        console.log(error)
        if(error.data !== undefined && error.data.message_detail !== undefined)
          this.$toast.error(error.data.message_detail)
        else
          this.$toast.error(error)
      }finally{} 
    },
    async loadDSNguoiGV()
    {
      try{
        const res = await MoMayTuDongLoiAPI.getDSNguoiGV(this.axios, this.$auth.getDonViID())
        const data = await res.data

        if (data.error_code === 'BSS-00000000'
                && data.data !== undefined
                && data.data.length > 0)
        {
          this.params.p_nguoi_gv_options = data.data
          this.params.p_nguoi_gv = parseInt(this.$auth.getNhanVienID())
        }
      }catch(error){
        console.log(error)
        if(error.data !== undefined && error.data.message_detail !== undefined)
          this.$toast.error(error.data.message_detail)
        else
          this.$toast.error(error)
      }finally{} 
    },

    async LAY_DS_HDTB_KHOAMOMAY_LOI()
    {
      let postData = {
        kyCuoc: this.params.p_kycuoc,
        dichVuVTId: this.params.p_dichvu,
        loaiTBId: 0,
        dsKhuVuc: this.params.p_ds_khuvuc,
      }

      const res = await MoMayTuDongLoiAPI.getDSKhoaMayLoi(this.axios, postData)
      return res
    },

    KT_DuLieuNhap(lst)
    {
        if (this.dsThueBao.length <= 0)
        {
          this.$toast.error("Không có dữ liệu để cập nhật!");
          return false;
        }
        if (this.param.p_chk_ngay_bg === '0')
        {
          this.$toast.error("Hãy chọn ngày hoàn thành !");
          //chkNgayBG.Focus();
          return false;
        }
        if(this.params.p_nguoi_gv === null)
        {
          this.$toast.error("Hãy chọn người giao việc !");
          //cboNguoiGV.Focus();
          return false;
        }
        if (this.PHAILAM("GIAOVIEC"))
        {
          if (this.dsNhanVien.length <= 0)
          {
            this.$toast.error("Hãy nhập nhân viên thực hiện  !");
            return false;
          }
        }
        //Kiểm tra ngày hoàn thành có >= ngày thanh toán , <= ngay hien tai ko
        let ngay_bg = moment(this.params.p_ngay_bg)
        if(ngay_bg.isAfter(moment()))
        {
          this.$toast.error("Bạn không được phép chọn ngày hoàn thành lớn hơn ngày hiện tại");
          return false;
        }
        //}
        //dsDanhSach
        //System.Data.DataTable dt = (System.Data.DataTable)gridDanhSach.DataSource;
        //dt.AcceptChanges();

        if (lst.length <= 0)
        {
          this.$toast.error("Bạn chưa chọn thuê bao.");
          return false;
        }
        //if (lst.Where(r => r["NGAY_HT"].ToString() != "OK").Count() > 0)
        //{
        //    Message_Box.ShowTB("Cột Trạng thái không thỏa mãn điều kiện!");
        //    return;
        //}
        //end
        return true;
    },

    async hoanthienHoSoThayDoiDV(_hdtb_id)
    {
      try
      {
        const res = await MoMayTuDongLoiAPI.hoanthienHS(this.axios, _hdtb_id)
        return true
      }catch(error)
      {
        return false
      }
    },

    /******************** */
    btnLayTTMoi_Click()
    {
      if(this.params.p_ds_khuvuc === null || this.params.p_ds_khuvuc === '')
      {
        this.$toast.error('Vui lòng thiết lập khu vực mở máy trước khi lấy dữ liệu!')
        return false
      }

      this.HienThiDanhSach()
    },

    tsbtnChapNhan_Click()
    {
      if(this.dsThueBao.length <= 0)
      {
        this.$toast.error('Không có thuê bao trên lưới để cập nhật!')
        return
      }
      let selectedRows = this.$refs.gridDSThueBao.getSelectedRecords()
      if(selectedRows.length <= 0)
      {
        this.$toast.error('Bạn chưa chọn thuê bao trên lưới để cập nhật!')
        return
      }
      let ngay_gv = moment(this.params.p_ngay_gv)
      let ngay_bg = moment(this.params.p_ngay_bg)

      if(ngay_gv.isAfter(ngay_bg))
      {
        this.$toast.error('Ngày giao việc không được lớn hơn ngày hoàn thành!')
        return
      }
      let PHAIGIAOVIEC = false;
      PHAIGIAOVIEC = this.PHAILAM("GIAOVIEC"); //Nếu PHAIGIAOVIEC = false (Tự động giao việc) thì tạo dữ liệu bảng giaophieu_nv
      if (!KT_DuLieuNhap(selectedRows))
          return;

      let postData = {
        "ds": [],
        "ngayGiao": moment(this.params.p_ngay_gv).format('DD/MM/YYYY'),
        "nhanVienGiao": this.$auth.getNhanVienID(),
        "nhanVienThucHien": this.params.p_nguoi_gv,
        "tuDong": true
      }
      selectedRows.forEach(item => {
        postData.ds.push({
          "hdtbId": this.params.p_hdtb_id,
          "phieuId": item.PHIEU_ID
        })
      })

      this.loading(false)
      MoMayTuDongLoiAPI.capnhat(this.axios, postData)
        .then(res => {
          if(res.data.error_code === 'BSS-00000000')
          {
            this.HienThiDanhSach();
            this.$toast.success("Cập nhật dữ liệu thành công !");
          }
        })
        .catch(error => {
          this.$toast.error(error.data.message_detail)
        })
        .finally(() => {
          this.loading(false)
        })

    },

    tsbtnGiaoViec_Click()
    {
      if(this.dsThueBao.length <= 0)
      {
        this.$toast.error('Không có thuê bao trên lưới để cập nhật!')
        return
      }
      if(this.params.p_nguoi_gv === null)
      {
        this.$toast.error('Hãy nhập nhân viên giao việc!')
        return
      }

      if(this.params.p_ngay_gv === null)
      {
        this.$toast.error('Hãy nhập ngày giao việc!')
        return
      }

      this.$toast.error('form frnGiaoPhieuNV  chưa gán')
      return

    },

    async tsbnKichHoat_Click()
    {
      if (!this.KT_DieuKien_TB(2)) return;

      //if (!KT_DuLieuNhap()) return;
      if (this.dsNhanVien.length <= 0)
      {
        this.$toast.error("Bạn chưa cập nhật thông tin giao việc cho nhân viên!");
        return;
      }
      if (this.dsThueBao.length <= 0)
      {
        this.$toast.error("Không có dữ liệu để kích hoạt!");
        return;
      }

      let selectedRows = this.$refs.gridDSThueBao.getSelectedRecords()
      if(selectedRows.length <= 0)
      {
        this.$toast.error('Bạn chưa chọn thuê bao trên lưới để kích hoạt !')
        return
      }

      let f = selectedRows.filter(x => x.STATUS === 5 || x.STATUS === 7)
      if(f.length > 0)
      {
        let ds = f.map(x => x.MA_TB)
        this.$toast.error('Thuê bao ' + ds.join(',') + ' đã được kích hoạt')
        return
      }

      if (this.PHAILAM("CAPNHAT_NGAYHT_KH"))
      {
      }

      selectedRows.forEach(item => {
        let ret = this.NangTocDo(item.HDTB_ID, item.THUEBAO_ID)
      })

      this.$toast.success("Kích hoạt dịch vụ thành công!");
      this.HienThiDanhSach2(); //-- minhnt sửa: 14/01/2015

    },

    tsbtnGiaoPhieu_Click()
    {
      this.loading(true)
      this.getHuongGiaoTheoLuong(this.params.p_luong_id)
        .then((res) => {
          if (res.data.error_code === 'BSS-00000000'
            && res.data.data !== undefined
            && res.data.data.length > 0)
          {
            this.params.p_huonggiao_id = res.data.data[0].HUONGGIAO_ID
            this.$bvModal.show("dlg-giao-phieu");
          } else
          {
            this.$toast.error('Chưa có hướng giao được gán !')
          }
          this.HienThiDanhSach()
        })
        .catch(error => {
          this.$toast.error(error.data.message_detail)
        })
        .finaly(() => {
          this.loading(false)
        })
    },

    tsbnXoaHD_Click()
    {
      if(this.dsThueBao.length <= 0)
      {
        this.$toast.error('Không có thuê bao cần xóa hợp đồng.')
        return false
      }
      if (!this.KT_DieuKien_TB(1)) return

      let selectedRows = this.$refs.gridDSThueBao.getSelectedRecords()
      if(selectedRows.length <= 0)
      {
        this.$toast.error('Bạn chưa chọn thuê bao.')
        return false
      }

      let f = selectedRows.filter(x => x.TTHD_ID !== 1)
      if(f.length > 0)
      {
        this.$toast.error("Trạng thái hợp đồng thuê bao " + f.map(x => x.MA_TB).join(',') + " không thỏa mãn điều kiện")
        return
      }

      f = selectedRows.filter(x => x.STATUS !== '' && x.STATUS !== 0)
      if(f.length > 0)
      {
        this.$toast.error("Thuê bao " + f.map(x => x.MA_TB).join(',') + " đã được kích hoạt. Bạn không được xóa")
        return
      }

      let ma_nd = this.$auth.getMaNhanVien()
      f = selectedRows.filter(x => x.NGUOI_CN !== ma_nd)
      if(f.length > 0)
      {
        this.$toast.error("Bạn không được phép xóa " + f.map(x => x.MA_TB).join(',') + " do user khác cập nhật.")
        return
      }

      let promises = []
      selectedRows.forEach(item => {
        promises.push(this.xoaHopDong(item.HDKH_ID, LoaiHopDong.THAY_DOI_DV))
      })

      Promise.all(promises)
        .then((r) => {
          console.log(r)
          this.$toast.success("Xóa hợp đồng trên dữ liệu thành công!");
          this.HienThiDanhSach();
        })
        .catch(error => {
          console.log(error)
        })
    },

    btnHoanCong_Click()
    {
      if (!this.KT_DieuKien_TB(4)) return;

      if (this.dsNhanVien.length <= 0)
      {
        this.$toast.error("Bạn chưa cập nhật thông tin giao việc cho nhân viên!");
        return;
      }
      if (this.PHAILAM("TUDONG_HTHS"))
      {
      }

      if (gridviewDanhSach.GetRowCellValue(gridviewDanhSach.FocusedRowHandle, "NGAY_HT_GP").ToString() == "")
      {
          Message_Box.ShowWarning("Bạn phải cập nhật dữ liệu trước khi hoàn thành !");
          return;
      }


      let postData = {
        "luongId": this.params.p_luong_id,
        "hdtbId": this.params.p_hdtb_id,
        "kieuYC": this.params.p_kieu_yc,
        "ngayGV": moment(this.params.p_ngay_gv).format('DD/MM/YYYY'),
        "ngayHT": moment(this.params.p_ngay_bg).format('DD/MM/YYYY'),
        "nhanVienId": this.$auth.getNhanVienID(),
        "donViId": this.$auth.getDonViID(),
        "maNguoiDung": this.$auth.getMaNhanVien(),
        "thuHoiThietBi": null,
        "huongGiao": this.params.p_huonggiao_id,
        "ds": [
            {

            }
        ],
        "boQuaLoiHoanCong": null
      }

      selectedRows.forEach((item) => {
        postData.ds.push({
          "dichVuVTId": item.DICHVUVT_ID,
          "status": item.STATUS,
          "giaoOMC": item.GIAO_OMC,
          "chieuKhoa": item.CHIEUKHOA,
          "maTB": item.MA_TB,
          "hdtbId": item.HDTB_ID,
          "hdkhId": item.HDKH_ID,
          "phieuId": item.PHIEU_ID,
          "thueBaoId": item.THUEBAO_ID,
          "loaiTBId": item.LOAITB_ID
        })
      })

      MoMayTuDongLoiAPI.hoancong(this.axios, postData)
        .then((res) => {
          if (res.data.error_code === 'BSS-00000000')
          {
            this.$toast.success('Cập nhật dữ liệu và hoàn thiện hồ sơ thành công !')
            this.HienThiDanhSach2()
          }
        })
        .catch(error => {
          this.$toast.error(error.data.message_detail)
          this.HienThiDanhSach2()
        })


    },

    tsbtnHoanThanh_Click()
    {
      if (this.dsNhanVien.length <= 0)
      {
        this.$toast.error("Bạn chưa cập nhật thông tin giao việc cho nhân viên!");
        return;
      }
      if (this.PHAILAM("TUDONG_HTHS"))
      {
      }

      let selectedRows = this.$refs.gridDSThueBao.getSelectedRecords()

      if(selectedRows.length <= 0)
      {
        this.$toast.error("Chưa chọn thuê bao trong danh sách!");
        return;
      }

      let postData = {
        "ds": [],
        "luongId": this.params.p_luong_id
      }

      selectedRows.forEach(item => {
        postData.ds.push({
          "hdkhId": item.HDKH_ID,
          "hdtbId": item.HDTB_ID,
          "phieuId": item.PHIEU_ID
        })
      })

      MoMayTuDongLoiAPI.hoanthanh(this.axios, postData)
        .then((res) => {
          if (res.data.error_code === 'BSS-00000000')
          {
            this.$toast.success('Cập nhật dữ liệu và hoàn thiện hồ sơ thành công !')
            this.HienThiDanhSach2()
          }
        })
        .catch(error => {
          this.$toast.error(error.data.message_detail)
          this.HienThiDanhSach2()
        })
    },

    async tsbtnChuyenDB_Click()
    {
      if (this.params.p_chk_HCTheoFile === '1')
      {
        let count = 0
        let promises = []
        this.dsThueBao.forEach(item => {
          promises.push(this.hoanthienHoSoThayDoiDV(item.HDTB_ID))
        })

        Promise.all(promises)
          .then((r) => {
            console.log(r)
            this.$toast.success("Chuyển vào danh bạ thành công");
            this.HienThiDanhSach();
          })
          .catch(error => {
            console.log(error)
          })

      }
      else
      {
        if (!this.KT_DieuKien_TB(5)) return;

        let selectedRows =this.$refs.gridDSThueBao.getSelectedRecords()
        if(selectedRows.length <= 0)
        {
          this.$toast.error('Chưa chọn thuê bao trong danh sách.')
          return false
        }

        let promises = []
        this.selectedRows.forEach(item => {
          promises.push(this.hoanthienHoSoThayDoiDV(item.HDTB_ID))
        })

        Promise.all(promises)
          .then((r) => {
            console.log(r)
            this.$toast.success("Chuyển vào danh bạ thành công");
            this.HienThiDanhSach();
          })
          .catch(error => {
            console.log(error)
          })

      }
    },
    btnTraPhieu_Click()
    {
      this.$toast.error('Chức năng này chưa hoàn thành trên DHSX')
    },

    btnXuatText_Click()
    {

    },

    async NangTocDo(_vhdtb_id, _vthuebao_id)
    {
      this.getTBHaTocDo(_vthuebao_id)
        .then((res) => {
          if (res.data.error_code === 'BSS-00000000'
            && res.data.data !== undefined
            && res.data.data.length > 0)
          {
            // xulytb.HOANTHIEN_HS_TDTD_DUYTRI(_vhdtb_id);
            this.nangTocDoThueBao(_vthuebao_id)
              .then((r) => {
                if (r.data.error_code === 'BSS-00000000')
                  return true
                else
                  return false
              })
              .catch(error => {
                return false
              })
          }
        })
        .catch(error => {
          return false
        })
    },

    async getTBHaTocDo(thuebao_id)
    {
      const res = await MoMayTuDongLoiAPI.getThueBaoHaTocDo(this.axios, thuebao_id)
      return res
    },

    async nangTocDoThueBao(thuebao_id)
    {
      const res = await MoMayTuDongLoiAPI.nangTocDoTB(this.axios, thuebao_id)
      return res
    },

    async xoaHopDong(_hdkhId, _loaiHDId)
    {
      let postData = {
        hdkhId: _hdkhId,
        loaiHDId: _loaiHDId
      }
      try{
        const res = await MoMayTuDongLoiAPI.xoaHopDongKhachHang(this.axios, postData)
        return true
      }catch(error)
      {
        return false
      }
    },

    HienThiDanhSach()
    {
      this.loading(true)

      this.LAY_DS_HDTB_KHOAMOMAY_LOI()
        .then((res) => {
          if (res.data.error_code === 'BSS-00000000'
            && res.data.data !== undefined
            && res.data.data.length > 0)
          {
            this.dsThueBao = res.data.data
            this.dsThueBaoPageInfo.totalElement = res.data.data.length
            this.keyDSThueBao++

            this.configs.f_excel_enabled = true
            this.configs.f_xuatfile_enabled = this.params.p_chk_HCTheoFile === '1'
          } else
          {
            this.params.p_ngay_bg = new Date()
            this.params.p_ngay_gv = new Date()
            this.params.p_chk_ngay_bg = '0'
            this.params.p_chk_ngay_gv = '0'

            this.dsThueBao = []
            this.dsThueBaoPageInfo.totalElement = 0
            this.dsThueBaoPageInfo.page = 0

            this.dsNhanVien = []
            this.dsNhanVienPageInfo.totalElement = 0
            this.dsNhanVienPageInfo.page = 0

            this.configs.f_excel_enabled = false
            this.configs.f_xuatfile_enabled = false
            this.configs.f_chapnhan_visible = true
            this.configs.f_xoahd_visible = false
            this.configs.f_traphieu_visible = false
          }
        })
        .catch(error => {
          this.$toast.error(error.data.message_detail)
        })
        .finally(() => { this.loading(false) })
    },

    HienThiDanhSach2()
    {
      let vloaitb_id = 0

      this.LAY_DS_HDTB_KHOAMOMAY_LOI()
        .then((res) => {
          if (res.data.error_code === 'BSS-00000000'
            && res.data.data !== undefined
            && res.data.data.length > 0)
          {
            this.dsThueBao = res.data.data
            this.dsThueBaoPageInfo.totalElement = res.data.data.length
            this.keyDSThueBao++

            this.configs.f_excel_enabled = true
            this.configs.f_xuatfile_enabled = this.params.p_chk_HCTheoFile === '1'
          } else
          {
            this.params.p_ngay_bg = new Date()
            this.params.p_ngay_gv = new Date()
            this.params.p_chk_ngay_bg = '0'
            this.params.p_chk_ngay_gv = '0'

            this.dsThueBao = []
            this.dsThueBaoPageInfo.totalElement = 0
            this.dsThueBaoPageInfo.page = 0

            this.dsNhanVien = []
            this.dsNhanVienPageInfo.totalElement = 0
            this.dsNhanVienPageInfo.page = 0

            this.configs.f_excel_enabled = false
            this.configs.f_xuatfile_enabled = false
            this.configs.f_chapnhan_visible = true
            this.configs.f_xoahd_visible = false
            this.configs.f_traphieu_visible = false
          }
        })
        .catch(error => {
          this.$toast.error(error.data.message_detail)
        })
        .finally(() => { this.loading(false) })

    },

    HienThiDSNV()
    {
      let postData = {
        phieuId: this.params.p_phieu_id,
        id: this.$auth.getNhanVienID(),
        kieuId: 2,
      }
      MoMayTuDongLoiAPI.getDSNhanVienTheoPhieu(this.axios, postData)
        .then((res) => {
          if (res.data.error_code === 'BSS-00000000'
            && res.data.data !== undefined
            && res.data.data.length > 0)
          {
            this.dsNhanVien = res.data.data
            this.dsNhanVienPageInfo.totalElement = res.data.data.length
            this.keyDSNhanVien++

          }
        })
        .catch(error => {
          this.$toast.error(error.data.message_detail)
        })
    },

    async getHuongGiaoTheoQuyTrinh(quytrinh_id, tthd_id)
    {
      try{
        let postData = {
          quyTrinhId: quytrinh_id ,
          tthdId: tthd_id,
        }

        const res = await MoMayTuDongLoiAPI.getHuongGiaoTheoQuyTrinh (this.axios, postData)
        const data = await res.data

        if (data.error_code === 'BSS-00000000'
            && data.data !== undefined
            && data.data.length > 0)
          {
            this.params.p_luong_id = data.data[0].LUONG_ID
            this.params.p_huonggiao_id = data.data[0].HUONGGIAO_ID
          } else
          {
            this.$toast.error('Không tìm thấy luồng quy trình nghiệp vụ này.\nBạn hãy liên hệ Admin kiểm tra lại')
            this.params.p_luong_id = -1
            this.HienThiDanhSach2()
          }


      }catch(error){
        if(error.data !== undefined && error.data.message_detail !== undefined)
        {
          this.$toast.error(error.data.message_detail)
        } else  
          this.$toast.error(error)
      }finally{}      

    },

    PHAILAM(CODE)
    {
      if(this.dsThaoTac.length <= 0)
        return false
      let f = this.dsThaoTac.filter(x => x.ENABLE === 1 && x.CODE === CODE)
      if(f.length <= 0)
        return false
      return true
    },

    btnXuatExcel_Click()
    {
      if(this.dsThueBao.length <= 0)
      {
        this.$toast.error('Không có dữ liệu để xuất file.')
        return
      }
      let excelExportProperties = {
        dataSource: this.dsThueBao,
        fileName: 'DanhSachThueBaoLoi.xlsx'
      };
      this.$refs.gridDSThueBao.excelExport(excelExportProperties)
    },

    doExportFile()
    {

    },

    /********************* */
    chukycuocChangeHandler(e) {
      //console.log(e)
      if(e.p_chuky_no !== null)
      {
        this.params.p_kycuoc = moment(e.p_kyhoadon).format('YYYYMM') + e.p_chuky_no
      }
      else
        this.params.p_kycuoc = null
    },
    onFilteringDichVu(e){
      var query = new Query();
      query = (e.text !== '') ? query.where('TEN_DVVT', 'contains', e.text, true) : query;
      e.updateData(this.params.p_dichvu_options, query);
    },
    onFilteringNguoiGV(e){
      var query = new Query();
      query = (e.text !== '') ? query.where('TEN_NV', 'contains', e.text, true) : query;
      e.updateData(this.params.p_nguoi_gv_options, query);
    },

    async cboDichVuVT_EditValueChanged(args)
    {
      if(args.value === null)
      {
        return
      }

      let gridObj = this.$refs.gridDSThueBao.$refs.grid.ej2Instances
      //let obj = document.getElementsByClassName("e-grid")[1].ej2_instances[0];
      let columns = gridObj.columns

      //console.log(gridObj)
      //console.log(obj)
      //console.log(columns)

      switch(args.value)
      {
        case DichVuVienThong.CO_DINH:
          document.getElementById('tsbtnKichHoat').title = "Kích hoạt tổng đài"
          columns.find(x => x.foreignKeyField === 'TEN_STATUS').headerText = "Kích hoạt tổng đài"
          columns.find(x => x.foreignKeyField === 'TEN_STATUS').visible = true

          this.params.p_quytrinh_id = 45
          break;
        case DichVuVienThong.IMS:
          document.getElementById('tsbtnKichHoat').title = "Kích hoạt VTN"
          columns.find(x => x.foreignKeyField === 'TEN_STATUS').headerText = "Kích hoạt VTN"
          columns.find(x => x.foreignKeyField === 'TEN_STATUS').visible = true

          this.params.p_quytrinh_id = 47
          break;
        case DichVuVienThong.MEGA_EYES:
          document.getElementById('tsbtnKichHoat').title = "Kích hoạt MEGAEYES"
          columns.find(x => x.foreignKeyField === 'TEN_STATUS').headerText = "Kích hoạt MEGAEYES"
          columns.find(x => x.foreignKeyField === 'TEN_STATUS').visible = true

          this.params.p_quytrinh_id = 118
          break;
        case DichVuVienThong.GPHONE:
          document.getElementById('tsbtnKichHoat').title = "Kích hoạt Ccbs"
          columns.find(x => x.foreignKeyField === 'TEN_STATUS').headerText = "Kích hoạt Ccbs"
          columns.find(x => x.foreignKeyField === 'TEN_STATUS').visible = true

          this.params.p_quytrinh_id = 46
          break;
        case DichVuVienThong.ADSL:
          document.getElementById('tsbtnKichHoat').title = "Kích hoạt Visa/Vasc"
          columns.find(x => x.foreignKeyField === 'TEN_STATUS').headerText = "Kích hoạt Visa/Vasc"
          columns.find(x => x.foreignKeyField === 'TEN_STATUS').visible = true

          this.params.p_quytrinh_id = 48
            break;
        case DichVuVienThong.TSL:
          columns.find(x => x.foreignKeyField === 'TEN_STATUS').visible = false

          this.params.p_quytrinh_id = 50
            break;
        case DichVuVienThong.MEGAWAN:
          columns.find(x => x.foreignKeyField === 'TEN_STATUS').visible = false

          this.params.p_quytrinh_id = 49
          break;
        case DichVuVienThong.DI_DONG:
            //btnXuatText.Text = "Xuất file";
          this.params.p_quytrinh_id = 516
          break;

      }

      //gridObj.headerModule.refresh()


      this.$refs.gridDSThueBao.$refs.grid.refreshColumns()

      this.loading(true)
      await this.getHuongGiaoTheoQuyTrinh(this.params.p_quytrinh_id, this.params.p_tthd_id)
      this.loading(false)
    },

    dsThueBaoRowChanged(args)
    {
      if(args === null)
      {
        return
      }

      this.params.p_hdtb_id = args.HDTB_ID
      this.params.p_phieu_id = args.PHIEU_ID
      this.params.p_dvnhan_id = args.DONVI_ID
      this.params.p_loaitb_id = args.LOAITB_ID
      this.params.p_noidung_thuchien = args.ND_THUCHIEN
      //txtMaTB.Text = gridviewDanhSach.GetRowCellValue(FocusedRowHandle, "MA_TB").ToString();
      this.params.p_ma_tb = args.MA_TB

      if (args.STATUS !== "")
        this.params.p_status = parseInt(args.STATUS)
      else
        this.params.p_status = -1;

      if (args.NGAY_HT_GP !== "")
      {
        this.params.p_chk_ngay_bg = true
        this.params.p_ngay_bg = moment(args.NGAY_HT_GP, 'DD/MM/YYYY').toDate()
      }
      else
      {
        this.params.p_chk_ngay_bg = false
        this.params.p_ngay_bg = new Date()
      }

      this.HienThiDSNV()

      this.configs.f_xoahd_visible = false
      if (this.params.p_tthd_id === TrangThaiHD.MOI && this.dsThueBao.length > 0)
      {
        this.configs.f_xoahd_visible = true
      }


      if (this.PHAILAM("KICHHOAT_TD"))
      {
        if(this.dsNhanVien.length > 0)
        {
          if(args.NGAY_HT_GP !== null && args.NGAY_HT_GP !== '')
          {
              this.params.p_chk_ngay_bg = '1'
              this.params.p_ngay_bg = moment(args.NGAY_HT_GP, 'DD/MM/YYYY').toDate()
              let trangthai_db = "";
              if (this.params.p_dichvu === DichVuVienThong.CO_DINH)
              {
                trangthai_db = TRANGTHAI_DONGBO.DONGBO_SERVICE
              }
              else
              {
                trangthai_db = TRANGTHAI_DONGBO.DONGBO_CM
              }
                if (this.params.p_status === trangthai_db)
                {
                  if (this.PHAILAM("GIAOPHIEU"))
                  {
                    this.configs.f_giaophieu_enabled = true
                    this.configs.f_kichhoat_enabled = false
                  }
                  else
                  {
                    this.configs.f_hoancong_enabled = true
                    this.configs.f_kichhoat_enabled = false
                  }

                  if (this.PHAILAM("CHECK_OMC")) // Nếu có thao tác kiểm tra OMC thì -> làm
                  {
                    var kq = args.GIAO_OMC
                    if (kq === null)
                    {
                      this.$toast.error("Không lấy được thông tin kiểm tra giao phiếu cho bộ phận tiếp theo")
                      return;
                    }
                    else if (kq === 2)
                    {
                      this.configs.f_hoancong_enabled = true
                      this.configs.f_kichhoat_enabled = false
                      this.configs.f_giaophieu_enabled = false
                      this.configs.f_giaophieu_visible = false
                    }
                    else if (kq === 1)
                    {
                      this.configs.f_hoancong_enabled = false
                      this.configs.f_kichhoat_enabled = false
                      this.configs.f_giaophieu_enabled = true
                      this.configs.f_giaophieu_visible = true
                    }
                    else
                    {
                      this.$toast.error(kq)
                      return;
                    }
                  }

                }
                else
                {
                    if (THIS.PHAILAM("CHECK_OMC")) // Nếu có thao tác kiểm tra OMC thì -> làm
                    {
                      var kq = ARGS.GIAO_OMC
                      if (kq === null)
                      {
                          this.$toast.error("Không lấy được thông tin kiểm tra giao phiếu cho bộ phận tiếp theo");
                          return;
                      }
                      else if (kq === 2)//Gpon
                      {
                        this.configs.f_hoancong_enabled = false
                        this.configs.f_kichhoat_enabled = true
                        this.configs.f_giaophieu_enabled = false
                        this.configs.f_giaophieu_visible = false
                      }
                      else if (kq === 1)//Aon
                      {
                        this.configs.f_hoancong_enabled = false
                        this.configs.f_kichhoat_enabled = false
                        this.configs.f_giaophieu_enabled = true
                        this.configs.f_giaophieu_visible = true
                      }
                      else
                      {
                          this.$toast.error(kq);
                          return;
                      }
                    }
                    else
                    {
                      this.configs.f_hoancong_enabled = false
                      this.configs.f_kichhoat_enabled = true
                      this.configs.f_giaophieu_enabled = false
                      this.configs.f_giaophieu_visible = true
                    }
                }
            }
            else
            {
              this.params.p_chk_ngay_bg = '0'
              this.params.p_ngay_bg = new Date()

              this.configs.f_hoancong_enabled = false
              this.configs.f_kichhoat_enabled = false
              this.configs.f_giaophieu_enabled = false
              this.configs.f_giaophieu_visible = true
            }
        }
        else
        {
          this.configs.f_hoancong_enabled = false
          this.configs.f_kichhoat_enabled = false
          this.configs.f_giaophieu_enabled = false
          this.configs.f_giaophieu_visible = true
        }
      }
      else // Nếu không phải kích hoạt
      {
        //Nếu phải giao phiếu
        if (this.PHAILAM("GIAOPHIEU"))
        {
          if(this.dsNhanVien.length > 0)
            {
                if (args.NGAY_HT_GP !== null && args.NGAY_HT_GP !== "")
                {
                  this.params.p_chk_ngay_bg ='1'
                  this.params.p_ngay_bg = moment(args.NGAY_HT_GP, 'DD/MM/YYYY').toDate()
                  this.configs.f_hoancong_enabled = false
                  this.configs.f_kichhoat_enabled = false
                  this.configs.f_giaophieu_enabled = true
                }
                else
                {
                  this.params.p_chk_ngay_bg ='0'
                  this.params.p_ngay_bg = new Date()
                  this.configs.f_hoancong_enabled = false
                  this.configs.f_kichhoat_enabled = false
                  this.configs.f_giaophieu_enabled = false
                }
            }
            else
            {
                this.configs.f_hoancong_enabled = false
                this.configs.f_kichhoat_enabled = false
                this.configs.f_giaophieu_enabled = false
            }

        }
        else  // Nếu không phải giao phiếu
        {
            //Nếu phải kích hoạt tổng đài
            if (this.PHAILAM("HOANCONG"))
            {
              if(this.dsNhanVien.length > 0)
                {
                    if (args.NGAY_HT_GP !== null && args.NGAY_HT_GP !== "")
                    {
                      this.params.p_chk_ngay_bg ='1'
                      this.params.p_ngay_bg = moment(args.NGAY_HT_GP, 'DD/MM/YYYY').toDate()
                      this.configs.f_hoancong_enabled = true
                    }
                    else
                    {
                      this.params.p_chk_ngay_bg ='0'
                      this.params.p_ngay_bg = new Date()
                      this.configs.f_hoancong_enabled = false
                    }
                }
                else
                {
                    this.configs.f_hoancong_enabled = false
                    this.configs.f_kichhoat_enabled = false
                    this.configs.f_giaophieu_enabled = false
                }
            }

        }
        if (this.PHAILAM("HOANTHIENHOSO"))
        {
          if(this.dsNhanVien.length > 0)
            {
                if (args.NGAY_HT_GP !== null && args.NGAY_HT_GP !== '')
                {
                  this.params.p_chk_ngay_bg ='1'
                  this.params.p_ngay_bg = moment(args.NGAY_HT_GP, 'DD/MM/YYYY').toDate()
                  this.configs.f_hoanthien_hs_enabled = true
                }
                else
                {
                  this.params.p_chk_ngay_bg ='0'
                  this.params.p_ngay_bg = new Date()
                  this.configs.f_hoanthien_hs_enabled = false
                }
            }
            else
            {
                this.configs.f_hoancong_enabled = false
                this.configs.f_kichhoat_enabled = false
                this.configs.f_giaophieu_enabled = false
            }
        }
      }
    },

    getDataFromStorage(key)
    {
      try{
        return localStorage.getItem(key)

      }catch(error)
      {
        //console.log(error)
        return null
      }

    },
    setDataToStorage(key, value)
    {
      try {
        localStorage.setItem(key, value);
        return true;
      } catch (error) {
        return false;
      }
    }

  }
}
</script>
