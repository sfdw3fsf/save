<script>
  // tham chieu https://cntt.vnpt.vn/browse/BSS-1230
  // [UR2.8.002] - Tra cứu danh bạ
  import XLSX from 'xlsx'
  import DatePicker from 'vue2-datepicker'
  import 'vue2-datepicker/locale/vi.js'
  import 'vue2-datepicker/index.css'
  import moment from 'moment'
  import searchAccount from '@/modules/search/subscriber/SearchAccount'
  import api2 from '@/modules/contract/profile/CompleteProfileChangeAccount/Popups/PopUpApi'
  import LOAI_DV from '@/modules/contract/profile/CompleteProfileChangeAccount/Enum/LOAI_DV.js'
  export default {
    components: {
      XLSX,
      moment,
      DatePicker,
      searchAccount,
      api2,
      LOAI_DV
    },
    name: 'ChuyenTo',
    props: {
      tag: {
        type: Number
      },
      tthdId: {
        default: -1,
        type: Number
      },
      ttbhId: {
        default: -1,
        type: Number
      },
      bhId: {
        default: 0,
        type: Number
      }
    },
    data() {
      return {
        Loading: false,
        dateBEFormat: 'YYYY-MM-DD HH:mm:ss',
        dateFormat: 'DD/MM/YYYY HH:mm:ss',
        dateFormatMonth: 'MM/YYYY',
        typeFormat: 'datetime',
        typeFormatMonth: 'month',
        dateConfig: {
          dateFormat: 'd/m/Y',
          altFormat: 'd/m/Y'
        },
        debug: true,
        target: '#app', //  .main-wrapper-payment
        targetTop: '#app', // document.querySelector('body'),.main-wrapper
        animationSettings: { effect: 'Zoom' },
        position: { X: 'center', Y: 'top' },
        showTimePanel: false,
        tabIndex: 0,
        txtSoMay: '',
        txtNgayBH: '',
        txtDichVuVT: '',
        txtLoaiTB: '',
        txtTenTB: '',
        txtDiachiLD: '',
        txtGhiChu: '',
        txtNguoiGiao: '',
        txtNguoiNhan: '',
        txtTrangThaiBH: '',
        donviQLId: '',
        txtNoiDung: '',
        DmDvQL: [],
        tramVtId: 0,
        tthd_id: 0,
        vdvtram_id: 0,
        DmDv: [],
        DmTramVT: [],
        tsbtnCapNhat: true,
        _IS_USING_LACHUONG_BH: -1,
        IS_USING_LACHUONG_BH: false,
        layout_DSLacHuong: false,
        layoutCT_DHXL: true,
        popupComponent: null,
        popupComponentData: null,
        popupComponentAttr: {},
        popupComponentEvts: {
          'form-close': this.popupClosed,
          acceptChangeCTV: this.popupClosed,
          acceptChangeNGT: this.popupClosed
        },
        popupComponentName: '',
        DhSuCo: {
          headers: [
            {
              fieldName: 'huonggiao',
              headerText: 'Hướng giao',
              allowFiltering: true,
              allowSorting: false,
              textAlign: 'left',
              isGroupedColumn: true
            },
            {
              fieldName: 'dvg',
              headerText: 'Đơn vị giao',
              allowFiltering: true,
              allowSorting: false,
              width: 90,
              textAlign: 'left'
            },
            {
              fieldName: 'nv_giao',
              headerText: 'Nhân viên giao',
              allowFiltering: true,
              allowSorting: false,
              width: 120
            },
            {
              fieldName: 'ngay_giao',
              headerText: 'Ngày giao',
              allowFiltering: true,
              allowSorting: false
            },
            {
              fieldName: 'nd_giao',
              headerText: 'Nội dung giao',
              allowFiltering: true,
              allowSorting: false
            },
            {
              fieldName: 'dvn',
              headerText: 'Đơn vị nhận',
              allowFiltering: true,
              allowSorting: false
            },
            {
              fieldName: 'ngay_tra',
              headerText: 'Ngày trả',
              allowFiltering: true,
              allowSorting: false
            },
            {
              fieldName: 'nd_tra',
              headerText: 'Nội dung trả',
              allowFiltering: true,
              allowSorting: false
            },
            {
              fieldName: 'trangthai_ph',
              headerText: 'Trạng thái phiếu',
              allowFiltering: true,
              allowSorting: false
            },
            {
              fieldName: 'nv_th',
              headerText: 'Nhân viên TH',
              allowFiltering: true,
              allowSorting: false
            },
            {
              fieldName: 'ngay_th',
              headerText: 'Ngày TH',
              allowFiltering: true,
              allowSorting: false
            },
            {
              fieldName: 'ghichu_th',
              headerText: 'Nội dung TH',
              allowFiltering: true,
              allowSorting: false
            }
          ],
          gridGroupSettings: {
            columns: ['huonggiao']
          },
          data: [],
          selectedItems: []
        },
        DsPhieuBaoHong: {
          headers: [
            {
              fieldName: 'ma_tb',
              headerText: 'Số máy/Acc',
              allowFiltering: true,
              allowSorting: false
            },
            {
              fieldName: 'ma_tb',
              headerText: 'Số ảo',
              allowFiltering: true,
              allowSorting: false
            },
            {
              fieldName: 'loaihinh_tb',
              headerText: 'Loại hình',
              allowFiltering: true,
              allowSorting: false
            },
            {
              fieldName: 'ten_tb',
              headerText: 'Tên thuê bao',
              allowFiltering: true,
              allowSorting: false
            },
            {
              fieldName: 'diachi_ld',
              headerText: 'Địa chỉ LĐ',
              allowFiltering: true,
              allowSorting: false
            },
            {
              fieldName: 'donvinhan',
              headerText: 'Đơn vị nhận',
              allowFiltering: true,
              allowSorting: false
            },
            {
              fieldName: 'diachi_dau',
              headerText: 'Địa chỉ LĐ (Điểm đầu)',
              allowFiltering: true,
              allowSorting: false
            },
            {
              fieldName: 'diachi_cuoi',
              headerText: 'Địa chỉ LĐ (Điểm cuối)',
              allowFiltering: true,
              allowSorting: false
            },
            {
              fieldName: 'ngay_bh',
              headerText: 'Ngày BH',
              allowFiltering: true,
              allowSorting: false
            },
            {
              fieldName: 'goi_kt',
              headerText: 'Gói KT',
              allowFiltering: true,
              allowSorting: false
            },
            {
              fieldName: 'kenh_tn',
              headerText: 'Kênh tiếp nhận',
              allowFiltering: true,
              allowSorting: false
            },
            {
              fieldName: 'chitieu_tg',
              headerText: 'Chỉ tiêu TG',
              allowFiltering: true,
              allowSorting: false
            },
            {
              fieldName: 'tgxl',
              headerText: 'Giờ XL',
              allowFiltering: true,
              allowSorting: false
            },
            {
              fieldName: 'gio_ck',
              headerText: 'Giờ CK',
              allowFiltering: true,
              allowSorting: false
            },
            {
              fieldName: 'gio_conlai',
              headerText: 'Giờ còn lại',
              allowFiltering: true,
              allowSorting: false
            },
            {
              fieldName: 'mucdo',
              headerText: 'Mức độ',
              allowFiltering: true,
              allowSorting: false
            },
            {
              fieldName: 'ds_nhanvien_th',
              headerText: 'Nhân viên TH',
              allowFiltering: true,
              allowSorting: false
            },
            {
              fieldName: 'tinhtrang',
              headerText: 'Tình trạng',
              allowFiltering: true,
              allowSorting: false
            },
            {
              fieldName: 'ten_plkh',
              headerText: 'Phân loại KH',
              allowFiltering: true,
              allowSorting: false
            },
            {
              fieldName: 'dienthoai_lh',
              headerText: 'Số liên hệ',
              allowFiltering: true,
              allowSorting: false
            },
            {
              fieldName: 'luot_bh',
              headerText: 'Lượt BH',
              allowFiltering: true,
              allowSorting: false
            },
            {
              fieldName: 'lan_pa',
              headerText: 'KH gọi',
              allowFiltering: true,
              allowSorting: false
            },
            {
              fieldName: 'nguyennhan',
              headerText: 'Nguyên nhân',
              allowFiltering: true,
              allowSorting: false
            },
            {
              fieldName: 'trangthai_bh',
              headerText: 'Trạng thái',
              allowFiltering: true,
              allowSorting: false
            },
            {
              fieldName: 'ngaygiao',
              headerText: 'Ngày giao',
              allowFiltering: true,
              allowSorting: false
            },
            {
              fieldName: 'nd_giao',
              headerText: 'Nội dung giao',
              allowFiltering: true,
              allowSorting: false
            },
            {
              fieldName: 'lan_ks',
              headerText: 'Lần KS',
              allowFiltering: true,
              allowSorting: false
            },
            {
              fieldName: 'ngay_sd',
              headerText: 'Ngày SD',
              allowFiltering: true,
              allowSorting: false
            },
            {
              fieldName: 'ghichu_hong',
              headerText: 'Ghi chú hỏng',
              allowFiltering: true,
              allowSorting: false
            },
            {
              fieldName: 'ghichu_th',
              headerText: 'Ghi chú TH',
              allowFiltering: true,
              allowSorting: false
            },
            {
              fieldName: 'lydoton',
              headerText: 'Lý do tồn',
              allowFiltering: true,
              allowSorting: false
            },
            {
              fieldName: 'ghichuton',
              headerText: 'Ghi chú tồn',
              allowFiltering: true,
              allowSorting: false
            },
            {
              fieldName: 'ten_dv',
              headerText: 'Đơn vị giao',
              allowFiltering: true,
              allowSorting: false
            },
            {
              fieldName: 'nhanvienql',
              headerText: 'Nhân viên QL',
              allowFiltering: true,
              allowSorting: false
            },
            {
              fieldName: 'ten_cap',
              headerText: 'Cáp gốc',
              allowFiltering: true,
              allowSorting: false
            },
            {
              fieldName: 'doi_cap',
              headerText: 'Đôi số',
              allowFiltering: true,
              allowSorting: false
            },
            {
              fieldName: 'ten_kc',
              headerText: 'Kết cuối',
              allowFiltering: true,
              allowSorting: false
            },
            {
              fieldName: 'vitri',
              headerText: 'Vị trí',
              allowFiltering: true,
              allowSorting: false
            },
            {
              fieldName: 'tientrinh',
              headerText: 'Tiến trình',
              allowFiltering: true,
              allowSorting: false
            },
            {
              fieldName: 'hensuatu',
              headerText: 'Hẹn sửa từ',
              allowFiltering: true,
              allowSorting: false
            },
            {
              fieldName: 'hensuaden',
              headerText: 'Hẹn sửa đến',
              allowFiltering: true,
              allowSorting: false
            },
            {
              fieldName: 'ten_kv',
              headerText: 'Khu vực',
              allowFiltering: true,
              allowSorting: false
            },
            {
              fieldName: 'nvkt_db',
              headerText: 'Nhân viên kỹ thuật ',
              allowFiltering: true,
              allowSorting: false
            },
            {
              fieldName: 'nvkd_db',
              headerText: 'Nhân viên kinh doanh',
              allowFiltering: true,
              allowSorting: false
            },
            {
              fieldName: 'nd_tra_con',
              headerText: 'Nội dung trả',
              allowFiltering: true,
              allowSorting: false
            },
            {
              fieldName: 'ngaytra_con',
              headerText: 'Ngày trả',
              allowFiltering: true,
              allowSorting: false
            },
            {
              fieldName: 'chuyenmang',
              headerText: 'NCC khác',
              allowFiltering: true,
              allowSorting: false
            }
          ],
          data: [],
          selectedItems: []
        },
        PhieuId: 0,
        HdTbId: 0,
        ThueBaoId: 0,
        BhId: 0,
        tag_hdbh: -1,
        Tag: '',
        labelFunctionTitle: 'THAY ĐỔI ĐƠN VỊ XỬ LÝ SỰ CỐ',
        layoutCT_DHXL_Text: 'Chi tiết điều hành xử lý sự cố',
        hdtb_id: 0,
        baohong_id: 0,
        btnLayTTMoi: {
          Visible: false
        },
        vtthd_id: -1,
        vttbh_id: -1,
        searchData: true,
        showMessage: false,
        selIndex: []
      }
    },
    created: async function () {
      console.log(2322222222222)
      try {
        this.Loading = true
        if (this.tag != null && this.tag != '') {
          this.Tag = this.tag
        }
        if (this.bhId != null && this.bhId > 0) this.BhId = this.bhId
        this._IS_USING_LACHUONG_BH = -1
  
        console.log('IS_USING_LACHUONG_BH:', this.IS_USING_LACHUONG_BH, 'Tag:', this.Tag)
        if (this.Tag != '' && this.IS_USING_LACHUONG_BH) {
          this.tag_hdbh = parseInt(this.Tag)
        }
        
        if (this.tag_hdbh != -1) {
          console.log('Vào đây')
          Promise.all([this.lay_ds_donvi_ql(LOAI_DV.DONVIQL_LD), this.NAP_DS_LACHUONG(0)])
            .then((x) => {
              console.log(x)
            })
            .finally((e) => {
              this.Loading = false
            })
          this.btnLayTTMoi.Visible = true
        } else {
          let loaidvIds = LOAI_DV.DONVIQL_LD
          if (this.baohong_id > 0 && this.IS_USING_LACHUONG_BH) {
            loaidvIds = LOAI_DV.DONVIQL_LD + ',' + LOAI_DV.BH_QLDH
          }
          await Promise.all([this.lay_ds_donvi_ql(loaidvIds), this.lay_tt_baohong_chuyento_chung()])
            .then((reponse) => {
              this.Loading = false
            })
            .finally((e) => {
              this.Loading = false
            })
        }
      } catch (err) {
        console.log('🚀 ~ file: ChuyenTo.vue ~ line 677 ~ err', err)
      }
    },
    mounted: async function () {
        let thamso = await this.lay_ds_thamso_md('BAOHONG_LH')
        if (this._IS_USING_LACHUONG_BH == -1) {
          if (thamso&& thamso.length > 0) {
            if (thamso.filter(x => x.ma_ts == 'BAOHONG_LH').length > 0) {
              this.IS_USING_LACHUONG_BH = true
            } else {
              this.IS_USING_LACHUONG_BH = false
            }
          }
        } else {
           this.IS_USING_LACHUONG_BH = false
        }
      this.loadUI()
    },
    watch: {
      donviQLId: async function (val) {
        try {
          this.loading(true)
          let loaidvId = 5 // VETINH = 5;
          if (this.baohong_id > 0 && this.tag_hdbh == -1) {
            if (val == 0) {
              loaidvId = 636 //  BH_DVXLDH = 636; //Nhóm xử lý sự cố --> minhnt -> dành riêng cho HCM
            } else {
              loaidvId = 5
            }
          } else {
            loaidvId = 5
          }
          await this.lay_ds_tramvt(val, loaidvId) // VETINH = 5;
        } catch (err) {
          // this.Loading = false
          this.ShowError(`${err.message}`)
        } finally {
          this.loading(false)
        }
      }
    },
    methods: {
      disabledRange: function (date) {
        return date > new Date()
      },
      toggleTimePanel () {
        this.showTimePanel = !this.showTimePanel
      },
      handleOpenChange () {
        this.showTimePanel = false
      },
      handleRangeClose () {
        this.showTimeRangePanel = false
      },
      dialogClose() {
        this.$emit('close', true)
        this.$emit('closeDialog', true)
      },
      thoat() {
        this.$refs.dialogObjChuyenTo.hide()
      },
      loadUI() {
        if (this.tag_hdbh != -1) {
          this.layoutCT_DHXL_Text = 'Chi tiết điều hành xử lý sự cố'
          this.labelFunctionTitle = 'THAY ĐỔI ĐƠN VỊ XỬ LÝ SỰ CỐ'
          this.layout_DSLacHuong = true
          this.layoutCT_DHXL = true
        } else {
          this.layout_DSLacHuong = false
          this.layoutCT_DHXL = true
          if (this.baohong_id > 0) {
            this.layoutCT_DHXL_Text = 'Chi tiết điều hành xử lý sự cố'
            this.labelFunctionTitle = 'THAY ĐỔI ĐƠN VỊ XỬ LÝ SỰ CỐ'
          } else if (this.hdtb_id > 0) {
            this.layoutCT_DHXL_Text = 'Chi tiết điều hành thi công'
            this.labelFunctionTitle = 'THAY ĐỔI ĐƠN VỊ THI CÔNG'
          }
        }
      },
      async loadDonViData() {
        if (this.tag_hdbh != -1) {
          await this.lay_ds_donvi_ql(LOAI_DV.DONVIQL_LD)
          this.btnLayTTMoi.Visible = true
        } else {
          
          let loaidvIds = LOAI_DV.DONVIQL_LD
          if (this.baohong_id > 0 && this.IS_USING_LACHUONG_BH) {
           
            loaidvIds = LOAI_DV.DONVIQL_LD + ',' + LOAI_DV.BH_QLDH
            await this.lay_ds_donvi_qls(loaidvIds)
            await this.lay_tt_baohong_chuyento_chung()
          }
          else{
            console.log(444444444)
             await this.lay_ds_donvi_ql(loaidvIds)
          }
        }
        await this.lay_tt_baohong_chuyento_chung()
      },
      openDialog(phieu_id, hdtb_id, baohong_id) {
        console.log('openDialog:phieu_id, hdtb_id, baohong_id', phieu_id, hdtb_id, baohong_id)
        try {
          this.CLEAR()
          this.PhieuId = phieu_id
          this.HdTbId = hdtb_id
          this.BhId = baohong_id
          this.baohong_id = baohong_id
          this.hdtb_id = hdtb_id
          this.Loading = true
          this.showMessage = false
          this.loading(true)
          // Promise.all([this.loadDonViData(), this.lay_tt_baohong_chuyento_chung()])
          this.loadDonViData()
          this.$refs.dialogObjChuyenTo.show()
          this.$refs.reftxtSoMay.focus()
          this.showMessage = false
          this.loadUI()
        } catch (err) {
          console.log("🚀 ~ file: ChuyenTo.vue ~ line 831 ~ openDialog ~ err", err)
        } finally {
          this.Loading = false
          this.loading(false)
        }
  
        // this.$refs.dialogObjChuyenTo.show()
      },
      async lay_ds_donvi_ql (loaidvId) {
        try {
          console.log(this.baohong_id)
          console.log(this.PhieuId)
          if (this.baohong_id > 0 && this.PhieuId > 0) {
            let dataDonVi = await this.lay_ds_donvi_chuyento_baohong(this.PhieuId)
            console.log('lay_ds_donvi_chuyento_baohong', dataDonVi)
            if (dataDonVi != null && dataDonVi.length > 0) {
              if (dataDonVi[0].ttbh_id != '3') {
                // TRANGTHAI_BH.DANG_DIEUHANH_XU_LY DANG_DIEUHANH_XU_LY = 3
                this.DmTramVT = dataDonVi.map((x) => ({ id: x.donvi_id, text: x.ten_dv }))
                if (this.DmTramVT != null && this.DmTramVT.length > 0) {
                  this.tramVtId = this.DmTramVT[0].id
                }
                return this.DmTramVT
              }
            }
          }
          var input = {
            loaidv_id: loaidvId
          }
          let data = this.GetData(await api2.lay_ds_donvi_ql(this.axios, input))
          this.DmDvQL = data.map((x) => ({ id: x.donvi_id, text: x.ten_ht }))
          if (this.DmDvQL != null && this.DmDvQL.length > 0) {
            this.donviQLId = this.DmDvQL[0].id
          }
          return data
        } catch (err) {
          console.log('🚀 lay_ds_donvi_ql ~ err', err)
        }
      },
      lay_ds_donvi_qls: async function (loaidvIds) {
        if (loaidvIds == null || loaidvIds == '') return []
        let arr = loaidvIds.split(',')
        if (arr != null && arr.length > 1) {
          let datas = []
          for (const loaidvId of arr) {
            var input = {
              loaidv_id: loaidvId
            }
            let data = this.GetData(await api2.lay_ds_donvi_ql(this.axios, input))
            if (data != null) {
              data.forEach((e) => {
                datas.push(e)
              })
            }
          }
          this.DmDvQL  = datas.map((x) => ({ id: x.donvi_id, text: x.ten_ht }))
          if (this.baohong_id > 0 && this.PhieuId > 0) {
            let dataDonVi = await this.lay_ds_donvi_chuyento_baohong(this.PhieuId)
            console.log('lay_ds_donvi_chuyento_baohong', dataDonVi)
            this.DmTramVT = dataDonVi.map((x) => ({ id: x.donvi_id, text: x.ten_dv }))
            if (this.DmTramVT != null && this.DmTramVT.length > 0) {
              this.tramVtId = this.DmTramVT[0].id
            }
          }
        } else {
          return await this.lay_ds_donvi_ql(loaidvIds)
        }
      },
      lay_ds_donvi_chuyento_baohong: async function (phieu_id) {
        var input = {
          phieu_id: phieu_id
        }
        let data = this.GetData(await api2.lay_ds_donvi_chuyento_baohong(this.axios, input))
        return data
      },
      lay_ds_phieu_bh_lachuong: async function (vdichvuvt_id, vthuebao_id) {
        var input = {
          vdichvuvt_id: vdichvuvt_id,
          vthuebao_id: vthuebao_id
        }
        let data = this.GetData(await api2.lay_ds_phieu_bh_lachuong(this.axios, input))
        this.DsPhieuBaoHong.data = data
        return data
      },
      lay_tt_baohong_chuyento_chung: async function () {
        var input = {
          vbaohong_id: this.BhId == null ? 0 : this.BhId,
          vhdtb_id: this.HdTbId == null ? 0 : this.HdTbId,
          vphieu_id: this.PhieuId == null ? 0 : this.PhieuId,
          vtthd_id: this.tthdId
        }
        /* test
        input.vhdtb_id = 4310449
        input.vphieu_id = 8292795
        */
        let data = this.GetData(await api2.lay_tt_baohong_chuyento_chung(this.axios, input))
        if (data == null || data.length == 0) {
          console.log('lay_tt_baohong_chuyento_chung:Không có thông tin dữ liệu')
          if (this.showMessage) this.ShowError(`Không có thông tin dữ liệu `)
        }
        await this.HienThi_Thongtin_bh(data)
        return data
      },
      lay_ds_thamso_md: async function (mathamso) {
        var input = {
          "ma_ts": mathamso
        }
        let data = this.GetData(await api2.lay_ds_thamso_md(this.axios, input))
        return data
      },
      lay_danhsach_phieuth_bh: async function (baohong_id) {
        // var input = {"baohong_id":4471646}
        var input = { baohong_id: baohong_id }
        let data = this.GetData(await api2.lay_danhsach_phieuth_bh(this.axios, input))
        return data
      },
      hienthi_ds_phieuth_chuyento: async function (vbaohong_id, vhdtb_id) {
        var input = {
          vbaohong_id: vbaohong_id == null ? 0 : vbaohong_id,
          vhdtb_id: vhdtb_id == null ? 0 : vhdtb_id
        }
        let data = this.GetData(await api2.hienthi_ds_phieuth_chuyento(this.axios, input))
        return data
      },
      lay_ds_tramvt2: async function (val) {
        try {
          this.Loading = true
          let loaidvId = 5 // VETINH = 5;
          if (this.baohong_id > 0 && this.tag_hdbh == -1) {
            if (val == 0) {
              loaidvId = 636 //  BH_DVXLDH = 636; //Nhóm xử lý sự cố --> minhnt -> dành riêng cho HCM
            } else {
              loaidvId = 5
            }
          } else {
            loaidvId = 5
          }
          await this.lay_ds_tramvt(val, loaidvId) // VETINH = 5;
        } catch (err) {
          this.Loading = false
          this.ShowError(`${err.message}`)
        } finally {
          this.Loading = false
        }
      },
      lay_ds_tramvt: async function (donvi_id, loaidv_id) {
        if (donvi_id == null || donvi_id == '') return
        var input = { donvi_id: donvi_id, loaidv_id: loaidv_id }
        let data = this.GetData(await api2.lay_ds_tramvt(this.axios, input))
        this.DmTramVT = data.map((x) => ({ id: x.donvi_id, text: x.ten_dv }))
        if (this.DmTramVT != null && this.DmTramVT.length > 0) {
          this.tramVtId = this.DmTramVT[0].id
        }
        return data
      },
      lay_ds_baohong_theo_ma_tb: async function (ma_tb, dichvuvt_id) {
        var input = { ma_tb: ma_tb, dichvuvt_id: dichvuvt_id }
        let data = this.GetData(await api2.lay_ds_baohong_theo_ma_tb(this.axios, input))
        return data
      },
      lay_dvcha_theo_dvcon: async function (donvinhanId, loaidvId) {
        try {
          var input = { vdonvi_id: donvinhanId, vloaidv_id: loaidvId }
          let data = this.GetData(await api2.lay_dvcha_theo_dvcon(this.axios, input))
          return data
        } catch (err) {
          return null
        }
      },
      HienThi_DS_PhieuTH: async function () {
        let mytable = await this.hienthi_ds_phieuth_chuyento(this.BhId, this.HdTbId)
        this.DhSuCo.data = mytable
        if (mytable != null && mytable.length > 0) {
          await this.gridChiTiet_Selected(mytable.length, mytable[mytable.length])
        }
      },
      NAP_DS_LACHUONG: async function (thuebaoId) {
        try {
          await this.lay_ds_phieu_bh_lachuong(0, thuebaoId)
        } catch (ex) {
          this.ShowError(`Có lỗi khi nạp danh sách lạc hướng  ${ex}`)
        }
      },
      HienThi_Thongtin_bh: async function (dtBH) {
        if (dtBH == null || dtBH.length == 0) return
        console.log('HienThi_Thongtin_bh', dtBH,'HdTbId:',this.HdTbId,'BhId:',this.BhId)
        let ngayBh = moment(dtBH[0].NGAY_BH, this.dateBEFormat)
        if (this.BhId > 0) {
          if (dtBH != null && dtBH.length > 0) {
            this.txtNgayBH = moment(ngayBh).format(this.dateFormat)
            if (dtBH[0].MA_TB == null && dtBH[0].MA_TB_1 != null) {
              dtBH[0].MA_TB = dtBH[0].MA_TB_1
            }
            this.txtSoMay = dtBH[0].MA_TB
  
            this.txtLoaiTB = dtBH[0].LOAIHINH_TB
            this.txtDichVuVT = dtBH[0].TEN_DVVT
            this.txtTenTB = dtBH[0].TENTB_DB
            this.txtDiachiLD = dtBH[0].DIACHILD_DB
            this.txtGhiChu = dtBH[0].GHICHU_HONG
            this.txtTrangThaiBH = dtBH[0].TRANGTHAI_BH
            // this.tthd_id = dtBH[0].TTBH_ID
            this.ttbhId = dtBH[0].TTBH_ID
            let baohong_id1 = dtBH[0].BAOHONG_ID
            this.BhId = baohong_id1
            // let vdvn_id = dtBH[0].DONVI_NHAN_ID
            // let vdonvi_ql_id = 0
            await this.HienThi_DS_PhieuTH(baohong_id1)
          }
        }
        if (this.HdTbId > 0) {
          if (dtBH != null && dtBH.length > 0) {
            console.log('HienThi_Thongtin_bh this.HdTbId > 0')
            let ngayYc = moment(dtBH[0].NGAY_YC, this.dateBEFormat)
            this.txtNgayBH = moment(ngayYc).format(this.dateFormat)
            this.txtSoMay = dtBH[0].MA_TB
            this.txtLoaiTB = dtBH[0].LOAIHINH_TB
            this.txtDichVuVT = dtBH[0].TEN_DVVT
            this.txtTenTB = dtBH[0].TEN_TB
            this.txtDiachiLD = dtBH[0].DIACHI_LD
            this.txtGhiChu = dtBH[0].GHICHU
            this.txtTrangThaiBH = dtBH[0].TRANGTHAI_HD
            // this.tthd_id = dtBH[0].TTHD_ID
            this.ttbhId = dtBH[0].TTHD_ID
            // let baohong_id = dtBH[0].BAOHONG_ID
            // this.BhId = baohong_id
  
            await this.HienThi_DS_PhieuTH(this.BhId)
            let vdvn_id = dtBH[0].DONVI_NHAN_ID
            let vdonvi_ql_id = 0
            let donvicha = await this.lay_dvcha_theo_dvcon(vdvn_id, LOAI_DV.DONVIQL_LD)
            if (donvicha != null && donvicha.length > 0) {
              vdonvi_ql_id = donvicha[0].donvi_id
            }
            if (vdonvi_ql_id == 0) {
              // this.ShowAlert('Không tìm thấy đơn vị quản lý!')
              return
            }
            console.log('this.donviQLId =vdonvi_ql_id:',vdonvi_ql_id)
            this.donviQLId = vdonvi_ql_id
            await this.lay_ds_tramvt2(vdonvi_ql_id)
            this.tramVtId = vdvn_id
          } else {
            this.hdtb_id = 0
          }
        }
      },
      NHAN_PHIEU_LACHUONG: async function () {
        if (this.txtNoiDung == '') {
          this.ShowError('Bạn chưa nhập nội dung chuyển tổ!')
          this.$refs.txtNoiDung.focus()
          return
        }
        console.log(this.tramVtId)
         console.log(this.$root.token.getMaTinh())
        if (!(this.tramVtId > 0)&&  this.$root.token.getMaTinh() != 'HCM') {
          this.ShowError('Bạn chưa chọn tổ thi công!')
          return
        }
        if (this.DsPhieuBaoHong.selectedItems == null || this.DsPhieuBaoHong.selectedItems.length == 0) {
          this.ShowError('Không có danh sách để lựa chọn ')
          return
        }
        if (this.tag_hdbh == 1) {
          this.ShowError('Thi công hiện đang trong quá trình xây dựng')
          return
        }
        if (this.tag_hdbh == 2) {
        }
      },
  
      MapCheck: async function (phieuId) {
        // let input = { id_neo: 'count(*)', in_table: 'giaophieu', in_dk: `where ttph_id =1 and  donvi_nhan_id in (select donvi_id from  admin.donvi_ldv where loaidv_id in (5,504,71)) and ttph_id = 1 and phieu_id=${phieuId} ` }
        // let data = this.GetData(await api2.map_id(this.axios, input))
        let data = this.GetData(await api2.fn_tt_giaophieu(this.axios, { param: phieuId, type: 5, param1: '' }))
        return data
      },
      gridChiTiet_Selected: async function (i, item) {
        console.log('B1')
        console.log(this.selIndex)
        if (item == undefined || item == null) return
        console.log('i, item', i, item)
        this.txtNguoiGiao = item.nv_giao
        this.txtNguoiNhan = item.dvn
        try {
          // this.tramVtId = item.donvi_nhan_id
          this.vdvtram_id = item.donvi_nhan_id  
          let phieuId = item.phieu_id
          console.log('tramVtId,phieuId', this.vdvtram_id, phieuId)
          let _check = 0
          if (this.HdTbId == 0 && this.BhId != 0) {
            _check = 1
          } else if (this.HdTbId != 0 && this.BhId == 0) {
            _check = await this.MapCheck(phieuId)
            console.log('MapCheck', phieuId, 'result:', _check)
          }
          this.tsbtnCapNhat = !(_check == 0)
        } catch (ex) {}
      },
      gridChiTiet_RowsSelected: function (items) {
        this.DhSuCo.selectedItems = items
      }, 
      gridBaoHong_RowsSelected: function (items) {
        this.DsPhieuBaoHong.selectedItems = items
      },
      async gridChiTiet_RowDataBound(args) {
        console.log('B2')
        console.log(this.selIndex)
        try {
          if (args.data['phieu_id'] == this.phieu_id) {
            this.selIndex.push(parseInt(args.row.getAttribute('aria-rowindex')))
            await this.gridChiTiet_Selected(this.selIndex, args.data)
          }
          if (this.selIndex.length > 0) {
            this.$refs.ref_gridChiTiet.grid.selectRows(this.selIndex)
            this.selIndex = []
          }
        } catch (err) {
          console.log('gridChiTiet_RowDataBound', err, args)
        }
      },
      async gridChiTiet_DataBound(args) {
        console.log('B3')
        console.log(this.selIndex)
        try {
          if (this.selIndex.length == 0 && this.DhSuCo.data != null && this.DhSuCo.data.length > 0) {
            this.selIndex.push(this.selIndex.length)
            await this.gridChiTiet_Selected(this.selIndex, args.data)
          }
          if (this.selIndex.length > 0) {
            console.log(444444444)
            console.log(this.DhSuCo.data.length)
            this.$refs.ref_gridChiTiet.grid.selectRows([this.DhSuCo.data.length -1])
            this.selIndex = []
          }
        } catch (err) {
          console.log('🚀 ~ file: ChuyenTo.vue ~ line 1055 ~ gridChiTiet_DataBound ~ err', err, args)
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
      capnhat_chuyento: async function () {
        // if(this.tsbtnCapNhat == false) return
        let nhanvienId = this.$root.token.getNhanVienID()
        let donviId = this.$root.token.getDonViID()
        let userName = this.$root.token.getUserName()
        /*
        let jsondata = JSON.stringify(this.DhSuCo.selectedItems)
        jsondata.forEach((x) => {
          x.ISCHECK = 1
        })
        */
        /*
        let jsondata = JSON.stringify(
          this.DhSuCo.selectedItems.map((x) => ({
            ISCHECK: 1,
            HUONGGIAO: x.huonggiao,
            DVG: x.dvg,
            NGAY_GIAO: x.ngay_giao,
            DONVI_NHAN_ID: x.donvi_nhan_id,
            ND_GIAO: x.nd_giao,
            DVN: x.dvn,
            NGAY_TRA: x.ngay_tra,
            ND_TRA: x.nd_tra,
            LYDOTRA: x.lydotra,
            TRANGTHAI_PH: x.trangthai_ph,
            PHIEU_ID: x.phieu_id,
            TTPH_ID: x.ttph_id,
            HUONGGIAO_ID: x.huonggiao_id,
            NV_GIAO: x.nv_giao,
            NGAY_TH: x.ngay_th,
            NV_TH: x.nv_th,
            TEN_NV_TH_ID: x.ten_nv_th_id,
            NHANVIEN_TH_ID: x.nhanvien_th_id,
            NHIEMVU: x.nhiemvu,
            DONVI_ID: x.donvi_id,
            SO_DT: x.so_dt,
            TEN_NV_GIAO_ID: x.ten_nv_giao_id,
            NV_GIAO1: x.nv_giao1,
            GHICHU: x.ghichu,
            KETQUA_XL: x.ketqua_xl,
            CHUONGTRINH: 'chuong trinh pttb',
            NGUOI_CN: userName,
            DVI_GIAO_ID: x.dvi_giao_id,
            DVI_NHAN_ID: x.dvi_nhan_id
          }))
        )
        */
        let jsondata = []
        if (this.tag_hdbh == -1) {
          jsondata = []
          // tam thoi
          // jsondata = this.DsPhieuBaoHong.selectedItems.map((x) => ({ ischeck: 1, PHIEU_ID: x.phieu_id, BAOHONG_ID: x.baohong_id }))
        } else {
          jsondata = this.DsPhieuBaoHong.selectedItems.map((x) => ({ ischeck: 1, PHIEU_ID: x.phieu_id, BAOHONG_ID: x.baohong_id }))
        }
  
        /*
        vtag_hdbh in number,
          vbaohong_id in number,
          vhdtb_id in number,
          vphieu_id in number,
          vttbh_id in number,
          vdonvinhan_id number,
          vdonviql_id number,
          vnhanvien_id number,
          vnoidung in varchar2,
          vmaycn in varchar2,
          vnguoicn in varchar2,
          vngaycn in date,
          vdanhsach_json in clob
        */
        var input = {
          vtag_hdbh: this.tag_hdbh,
          vbaohong_id: this.BhId, 
          vhdtb_id: this.HdTbId,
          vphieu_id: parseInt(this.PhieuId),
          vttbh_id: this.ttbhId,
          vdonvinhan_id: this.tramVtId?parseInt(this.tramVtId):parseInt(this.donviQLId),
          vdonviql_id: this.donviQLId?this.donviQLId:-1,//Chuyển
          vnhanvien_id: parseInt(nhanvienId),
          vnoidung: this.txtNoiDung,
          vmaycn: 'web',
          vnguoicn: userName,
          vngaycn: moment(new Date()).format('DD/MM/YYYY'),
          vdanhsach_json: jsondata
        }
        /*
        [
            {
              ISCHECK: 1,
              HUONGGIAO: '6 - Ti?p nh?n chuy?n d?i di d?ng tr? sau-> tr? tru?c',
              DVG: 'Kh�ch h�ng QLVT - Vi?n th�ng H?i Ph�ng',
              NGAY_GIAO: '25/05/2020 11:04',
              DONVI_NHAN_ID: '232',
              ND_GIAO: 'null',
              DVN: 'Giao d?ch s? 1 Ho�ng Van Th? - Ph�ng B�n h�ng KV1',
              NGAY_TRA: 'null',
              ND_TRA: 'null',
              LYDOTRA: 'null',
              TRANGTHAI_PH: 'M?i',
              PHIEU_ID: '9264850',
              TTPH_ID: '1',
              HUONGGIAO_ID: '20014',
              NV_GIAO: 'T? Th? Thu Hu?ng',
              NGAY_TH: '25/05/2020 11:04',
              NV_TH: 'ttthuong1.hpg - T? Th? Thu Hu?ng',
              TEN_NV_TH_ID: '6893 - ttthuong1.hpg - T? Th? Thu Hu?ng',
              NHANVIEN_TH_ID: '6893',
              NHIEMVU: 'null',
              DONVI_ID: '232.0',
              SO_DT: 'null',
              TEN_NV_GIAO_ID: '6893 - T? Th? Thu Hu?ng',
              NV_GIAO1: 'T? Th? Thu Hu?ng',
              GHICHU: 'null',
              KETQUA_XL: 'null',
              CHUONGTRINH: 'chuong tr?nh pttb',
              NGUOI_CN: userName,
              DVI_GIAO_ID: '1123',
              DVI_NHAN_ID: '232'
            }
          ]
        */
        let response = await api2.capnhat_chuyento(this.axios, input)
        if (response.data.error === 200 || response.data.error === '200') {
          if (response.data.error_code == 'BSS-00000000') {
            if (this.tag_hdbh == -1) {
              if (response.data.data == 'Thay đổi đơn vị thành công') {
                this.ShowSuccess(`Thay đổi đơn vị thành công!`)
                return true
              } else {
                this.ShowError(response.data.data)
                return false
              }
            } else {
              if (response.data.data == 'Ðã nhận phiếu thành công') {
                this.ShowSuccess(`Ðã nhận phiếu thành công !`)
                return true
              } else {
                this.ShowError(response.data.data)
                return false
              }
            }
          } else {
            this.ShowError(`${response.data.error_code}: ${response.data.message}`)
            return false
          }
        } else {
          console.log(response.data.error_code)
          this.ShowError(`${response.data.error_code}: ${response.data.message}`)
          return false
        }
        return false
      },
      async LAY_DANHBA_THEO_MATB1() {
        let input = {
          in_dichvuvt_id: 0,
          in_donvi_dl_id: 0,
          in_ma_tb: this.txtSoMay
        }
        let data = this.GetData(await api2.LAY_DANHBA_THEO_MATB1(this.axios, input))
        return data
      },
      async LAY_DANHBA_THEO_MATB() {
        let input = {
          vma_tb: this.txtSoMay
        }
        let data = this.GetData(await api2.LAY_DANHBA_THEO_MATB(this.axios, input))
        return data
      },
      txtMaTB_KeyPress: async function () {
        try {
          this.CLEAR1()
          let dsBaoHong = await this.LAY_DANHBA_THEO_MATB()
          if (dsBaoHong != null && dsBaoHong.length > 0) {
            if (dsBaoHong.length == 1) {
            } else {
              if (this.searchData) this.ShowSearchAccount()
            }
          } else {
            this.ShowError(`Không tìm thấy thông tin!`)
            return
          }
          this.Loading = true
          if (this.tag_hdbh != -1) {
            if (this.tag_hdbh == 2) {
              // Chua lam
              // NAP_DS_LACHUONG(Convert.ToInt64(ds.Tables[0].Rows[index]["thuebao_id"]));
              this.ThueBaoId = dsBaoHong[0].thuebao_id
              await this.NAP_DS_LACHUONG(this.ThueBaoId)
            }
          } else {
            if (this.BhId > 0) {
              let dtBH = await this.lay_tt_baohong_chuyento_chung()
              await this.HienThi_Thongtin_bh(dtBH)
            } else if (this.HdTbId > 0) {
              let dtBH = await this.lay_tt_baohong_chuyento_chung()
              await this.HienThi_Thongtin_bh(dtBH)
            }
          }
        } catch (err) {
          this.ShowError(`${err.message}`)
        } finally {
          this.Loading = false
        }
      },
      NAP_DS_LUOI: function (i, item) {
        this.txtNgayBH = item.ngay_bh
        this.txtSoMay = item.ma_tb
        // txtDichVuVT.Text = cboDichVuVT.Text;
        this.txtLoaiTB = item.loaihinh_tb
        this.txtTenTB = item.ten_tb
        this.txtDiachiLD = item.diachi_ld
        this.txtGhiChu = item.ghichu
        this.txtTrangThaiBH = item.trangthai_bh
      },
      btnLayTTMoi_Click: async function () {
        try {
          this.$refs.txtSoMay.focus()
          this.Loading = true
          await this.NAP_DS_LACHUONG(0)
          this.Loading = false
        } catch (ex) {
          this.Loading = false
        }
      },
      tsbtnCapNhat_Click: async function () {
        if (this.txtNoiDung == '') {
          this.ShowError('Bạn chưa nhập nội dung chuyển tổ!')
          this.$refs.txtNoiDung.focus()
          return
        }
        if ((this.tramVtId == null || this.tramVtId == '') &&  this.$root.token.getMaTinh() != 'HCM') {
          try {
            this.ShowError('Bạn chưa chọn tổ thi công!')
            this.$refs.reftramVtId.focus()
            return
          } catch (err) {}
        }
        if (this.vdvtram_id == this.tramVtId &&  this.$root.token.getMaTinh() != 'HCM') {
          this.ShowError('Không được chuyển tổ về chính nó!')
          // this.$refs.tramVtId.focus()
          return
        }
        if (!(this.tramVtId > 0) &&  this.$root.token.getMaTinh() != 'HCM') {
          try {
            this.ShowError('Bạn chưa chọn tổ thi công!')
            this.$refs.reftramVtId.focus()
            return
          } catch (err) {}
        }
        if (this.tag_hdbh == -1) {
          if (this.DhSuCo.data == null || this.DhSuCo.data.length == 0) {
            this.ShowError('Không có danh sách để lựa chọn')
            return
          }
          if (this.DhSuCo.selectedItems == null || this.DhSuCo.selectedItems.length == 0) {
            this.ShowError('Bạn chưa chọn danh sách để giao')
            return
          }
        }
        try {
          this.tsbtnCapNhat = false
          this.Loading = true
          let result = await this.capnhat_chuyento()
          if (result) {
            this.ChapNhan = true
            this.Loading = false
            this.tsbtnCapNhat = true
            this.$emit('chapNhan', this.ChapNhan)
            this.$refs.dialogObjChuyenTo.hide()
          }
          /* ham da gop
          if (this.tag_hdbh == -1) {
            await this.capnhat_chuyento()
          } else {
            await this.NHAN_PHIEU_LACHUONG()
          }
          */
        } catch (ex) {
          this.ChapNhan = false
          this.tsbtnCapNhat = true
          this.Loading = false
          console.log(ex)
          if (ex.message != null && ex.message != undefined) this.ShowError(`${ex.message}`)
        } finally {
          this.Loading = false
          this.tsbtnCapNhat = true
        }
      },
      SearchDanhBa: function () {
        this.ShowSearchAccount()
        // this.$refs.searchAccountDialog.show()
      },
      async searchAccountDialogClose(data) {
        try {
          console.log('searchAccountDialogClose', data)
          if (data != null) {
            this.txtSoMay = data.ma_tb
            this.searchData = false // Ko bat popup nua
            if (data.thuebao != null) {
              this.ThueBaoId = data.thuebao.thuebao_id
            }
            await this.txtMaTB_KeyPress()
          } else {
            console.log('searchAccountDialogClose is null')
          }
        } catch (err) {
          this.ShowError(`${err.message}`)
        } finally {
          this.$refs.searchAccountDialog.hide()
          this.closeDialogChuyenTo()
        }
      },
      closeDialogChuyenTo: function () {
        this.$emit('close', this.PhieuId)
        this.$emit('closeChuyenTo', this.ChapNhan)
      },
      Popup(modalId) {
        this.$bvModal.show(modalId)
      },
      ClosePopup(modalId) {
        this.$bvModal.hide(modalId)
        this.popupComponent = null
      },
      async popupClosed(val) {
        if (!val) {
          this.ClosePopup('popupComponentChuyenTos')
        }
        try {
          this.Loading = true
          switch (this.popupComponentName) {
            case 'SearchAccount':
              console.log('SearchAccount', val)
              if (val != null && val.ma_tb != '') this.txtSoMay = val.ma_tb
              if (this.txtSoMay != '' && this.txtSoMay != '') {
                this.searchData = false // Ko bat popup nua
                await this.txtMaTB_KeyPress()
              }
              break
            default:
          }
        } catch (err) {
          console.log(err)
        } finally {
          this.ClosePopup('popupComponentChuyenTos')
          this.Loading = false
        }
        // this.$emit('close', this.PhieuId)
        // this.$emit('closeChuyenTo', this.ChapNhan)
      },
      CLEAR() {
        this.txtNgayBH = moment().format('DD/MM/YYYY HH:mm:ss')
        this.txtSoMay = ''
        this.txtDichVuVT = ''
        this.txtLoaiTB = ''
        this.txtTenTB = ''
        this.txtDiachiLD = ''
        this.txtGhiChu = ''
        this.txtTrangThaiBH = ''
        this.txtNoiDung = ''
        this.tramVtId = ''
        this.donviQLId = ''
      },
      CLEAR1() {
        this.txtNgayBH = moment().format('DD/MM/YYYY HH:mm:ss')
        this.txtDichVuVT = ''
        this.txtLoaiTB = ''
        this.txtTenTB = ''
        this.txtDiachiLD = ''
        this.txtGhiChu = ''
        this.txtTrangThaiBH = ''
      },
      ShowSearchAccount() {
        this.popupComponent = () => import('@/modules/search/subscriber/SearchAccount')
        this.popupComponentName = 'SearchAccount'
        try {
          this.popupComponent.data.isPopup = true
          this.popupComponent.data.vdichvuvt_id = 0
        } catch (ex) {
          if (this.debug) console.error(ex)
        }
        this.Popup('popupComponentChuyenTos')
      },
      ShowAlert: function (msg) {
        this.$toast.warning(msg)
      },
      ShowSuccess: function (msg) {
        this.$toast.success(msg)
      },
      ShowError: function (msg) {
        this.$toast.error(msg)
      }
    }
  }
  </script>
  