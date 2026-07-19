<template src='./ReceivingCallsSwitchboard.html'></template>
<script>
import TraCuuKHDN from "./components/TraCuuKHDN.vue"
import TraCuuDataMig from "./components/TraCuuDataMig.vue"
import DanhSachThueBaoKhachHang from "./components/DanhSachThueBaoKhachHang.vue"
import LichSuCuocGoiKhachHang from "./components/LichSuCuocGoiKhachHang.vue"
import TraCuuDataMig1 from "./components/TraCuuDataMig.vue"
import PopupLienHeTapTrung from "./components/PopupLienHeTapTrung.vue"
import PopupChiTietCuocGoi from "./components/PopupChiTietCuocGoi.vue"
import dsTinh from "./dsTinh.json"
import api from './ReceivingCallsSwitchboardApi.js'

export default {
  components: {
    TraCuuKHDN,
    TraCuuDataMig,
    DanhSachThueBaoKhachHang,
    LichSuCuocGoiKhachHang,
    TraCuuDataMig1,
    PopupLienHeTapTrung,
    PopupChiTietCuocGoi
  },
  data: () => ({
    selectedTab: 1,
    tabList: [
      {
        name: "Tra cứu khách hàng",
        id: 1
      },
      {
        name: "Danh sách thuê bao khách hàng",
        id: 2
      },
      {
        name: "Lịch sử cuộc gọi",
        id: 3
      },
      // {
      //   name: "Chi tiết cuộc gọi",
      //   id: 4
      // },
      // {
      //   name: "Hoạt động gần đây",
      //   id: 5
      // },
      {
        name: "Tra cứu mở rộng",
        id: 6
      },
      // {
      //   name: "Tra cứu thông tin KHDN",
      //   id: 7
      // }
    ],
    khachhang_id: 0,
    tinh_id: 0,
    txt_IPCCUser: {},
    txt_CallID: {value: "", disabled: true},
    txt_HotLine: "",
    txt_Branch: "",
    dtp_CallTime: "",
    txtKhChu: "",
    txt_PhoneNo: "",
    txt_Duration: "",
    txtTenKH: "", //layItemKhGoc
    txtDiaChiKH: "",
    txt_NoiDungHoTro: "",
    grid_DanhSach: {
      config: [
        {
          fieldName: 'tinh',
          headerText: 'Tỉnh',
          allowFiltering: true,
          width: 80
        },
        {
          fieldName: 'ma_tb',
          headerText: 'Số máy/Account',
          isPrimaryKey: true,
          allowFiltering: true
        },
        {
          fieldName: 'ten_tb',
          headerText: 'Tên thuê bao',
          allowFiltering: true,
          width: 200
        },
        {
          fieldName: 'loaitb_id',
          headerText: 'Loại hình',
          allowFiltering: true
        },
        {
          fieldName: 'trangthaitb_id',
          headerText: 'Trạng thái TB',
          allowFiltering: true
        },
        {
          fieldName: 'diachi_ld',
          headerText: 'Địa chỉ lắp đặt',
          allowFiltering: true
        },
        {
          fieldName: 'diachi_ld',
          headerText: 'Địa chỉ thuê bao',
          allowFiltering: true
        },
        {
          fieldName: 'ma_lt',
          headerText: 'Số ảo',
          allowFiltering: true
        },
        {
          fieldName: 'ma_vnpt',
          headerText: 'Mã VNPT',
          allowFiltering: true
        },
        {
          fieldName: 'ma_kh',
          headerText: 'Mã KH',
          allowFiltering: true
        }
      ],
      DataSource: [],
      selected: {},
    },
    grid_History: {
      config: [
        {
          fieldName: 'id',
          headerText: 'Cuộc gọi ID',
          allowFiltering: true
        },
        {
          fieldName: 'mayNghe',
          headerText: 'Số máy',
          isPrimaryKey: true,
          allowFiltering: true
        },
        {
          fieldName: 'tenNguoiNhan',
          headerText: 'Người nhận',
          allowFiltering: true
        },
        {
          fieldName: 'gioBD',
          headerText: 'Thời gian bắt đầu',
          allowFiltering: true
        },
        {
          fieldName: 'thoiLuong',
          headerText: 'Độ dài',
          allowFiltering: true
        },
        {
          fieldName: 'kieu_CG',
          headerText: 'Kiểu CG',
          allowFiltering: true
        },
        {
          fieldName: 'chiTiet.noiDung',
          headerText: 'Nội dung hỗ trợ',
          allowFiltering: true
        },
        {
          fieldName: 'donVi',
          headerText: 'Tỉnh',
          allowFiltering: true
        }
      ],
      DataSource: null
    },
    grid_Activity: {
      config: [
        {
          fieldName: 'tinh',
          headerText: 'ID',
          allowFiltering: true
        },
        {
          fieldName: 'hdtb_id',
          headerText: 'Người yêu cầu',
          isPrimaryKey: true,
          allowFiltering: true
        },
        {
          fieldName: 'ten_tb',
          headerText: 'Ngày yêu cầu',
          allowFiltering: true
        },
        {
          fieldName: 'ten_tb',
          headerText: 'Điện thoại LH',
          allowFiltering: true
        },
        {
          fieldName: 'diachi_tb',
          headerText: 'Nội dung yêu cầu',
          allowFiltering: true
        },
        {
          fieldName: 'dichvuvt_id',
          headerText: 'Nội dung Xử lý',
          allowFiltering: true
        },
        {
          fieldName: 'trangthai_dongbo',
          headerText: 'Ngày hoàn thành',
          allowFiltering: true
        },
        {
          fieldName: 'trangthai_dongbo',
          headerText: 'Trạng thái',
          allowFiltering: true
        },
        {
          fieldName: 'trangthai_dongbo',
          headerText: 'Yêu cầu',
          allowFiltering: true
        }
      ],
      DataSource: []
    },
    dsTinh: dsTinh,
    clientInfo: {},
    DanhMuc: [],
    grid_lookup_kh: [],
    grid_lookup_mr: [],
    cuocgoi_id: null,
    lblPhanLoaiKH: {
      Text: "Khách hàng thường"
    },
    lblMaTinh: {
      Text: "Tỉnh"
    }
  }),
  computed: {
    tt_nd () {
      return {
        nhanvien_id: this.$auth.getNhanVienID(),
        donvi_id: this.$auth.getDonViID(),
        ngay_cn: this.$auth.getNgayCapNhat(),
        nguoidung_id: this.$root.token.getNguoiDungID(),
        USER_NEO: "",
        ma_nd: this.$auth.getMaNhanVien(),
        may_cn: "",
        ip: "",
        tinh_id: this.$auth.getPhanVungID()
      }
    }
  },
  watch: {
    selectedTab: function (newVal, oldval) {
      this.tab_Detail_SelectedPageChanged({Page: newVal})
    },
    "grid_Activity.DataSource": function (newVal, oldval) {
      this.grid_Activity_DataSourceChanged(newVal)
    }
  },
  async mounted () {
    this.tt_nd.chitiet = await this.$root.token.getThongTinNguoiDung()
    await this.$auth.getMachine().then(rs => { this.tt_nd.may_cn = rs })
    await this.$auth.getIP().then(rs => { this.tt_nd.ip = rs })
    await this.frmHoTro_TapTrung_Load()
    console.log(this.tt_nd)
  },
  methods: {
    LowerCasePropertyList(obj) {
      return obj.map(function(item) {
        for (var key in item) {
          var upper = key.toLowerCase()
          // check if it already wasn't uppercase
          if (upper !== key) {
            item[upper] = item[key]
            delete item[key]
          }
        }
        return item
      })
    },
    UpperCasePropertyList(obj) {
      return obj.map(function(item) {
        for (var key in item) {
          var upper = key.toUpperCase()
          // check if it already wasn't uppercase
          if (upper !== key) {
            item[upper] = item[key]
            delete item[key]
          }
        }
        return item
      })
    },
    GetDataList: function (response) {
      if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
        return response.data.data
      } else if (response && response.data && response.data.error_code === 'BSS-00000204') {
        return []
      } else {
        this.$toast.error('Lỗi: ' + (response.data.message_detail ? response.data.message_detail : response.data.message))
        return []
      }
    },
    GetData: function (response) {
      if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
        return response.data.data
      } else if (response && response.data && response.data.error_code === 'BSS-00000204') {
        return ''
      } else {
        this.$toast.error('Lỗi: ' + (response.data.message_detail ? response.data.message_detail : response.data.message))
        return ''
      }
    },
    frmHoTro_TapTrung_Load: async function () {
      try {
        // TODO
        // IPCCUser user = new IPCCUser((uint)ChucNang.Instance.Nhanvien_ID_Goc, tt_nd.ma_nd, tt_nd.ten_nd);
        // user.Code = tt_nd.ma_nd;
        // user.Status = ChatClientStatus.UnSync;
        // DataTable dtLine = new DuLieuHTKHFacade().GetLineUser(ChucNang.Instance.Nhanvien_ID_Goc);
        var dtLine = await this.GetLineUser()
        if (dtLine && dtLine.length > 0) {
          this.clientInfo.user = {
            Line: dtLine[0][0],
            account_IPCC: dtLine[0][1]
          }
          this.txt_IPCCUser.Caption = dtLine[0][1]
          if (dtLine.length > 1) {
            for (let dr of dtLine) {
              var item = {
                Caption: dr[1],
                ItemClick: this.SwitchAgent(dr[1])
              }
              this.txt_IPCCUser.push(item)
            }            
          }
        } else {
          this.clientInfo.user = {
            Line: 0,
            account_IPCC: this.tt_nd.ma_nd
          }
        }
        // if (dtLine.Rows.Count > 0)
        // {
        //     user.Line = Convert.ToInt32(dtLine.Rows[0][0]);
        //     user.account_IPCC = txt_IPCCUser.Caption = dtLine.Rows[0][1].toString();
        //     bar_Extension.EditValue = dtLine.Rows[0][0].toString();
        //     if (dtLine.Rows.Count > 1)
        //         foreach (DataRow dr in dtLine.Rows)
        //         {
        //             DevExpress.XtraBars.BarButtonItem item = new DevExpress.XtraBars.BarButtonItem();
        //             item.Caption = dr[1].toString();
        //             txt_IPCCUser.AddItem(item);
        //             item.ItemClick += SwitchAgent;
        //         }
        // }
        // else
        // {
        //     user.Line = 0;
        //     user.account_IPCC = user.Code;
        //     bar_Extension.EditValue = 0;
        // }

        // clientInfo = new ChatClientInfo(user);
        this.clientInfo.MachineName = this.tt_nd.may_cn
        // clientInfo.pID = System.Diagnostics.Process.GetCurrentProcess().Id;
        this.clientInfo.IPAddress = this.tt_nd.ip
        // new System.Threading.Thread(new System.Threading.ThreadStart(RegisterCallback)).Start();

        // TODO: CALL API
        this.DanhMuc = await this.LayDSThamSo();
      } catch (ex) {
        this.$toast.error("Lỗi: " + ex)
      }
    },
    GetLineUser: async function () {
      try {
        var input = { nhanvien_id: this.tt_nd.nhanvien_id }
        var data = this.GetDataList(await api.sp_lay_tt_line_user(this.axios, input));
        return data
      } catch (e) {

      } finally {

      }
    },
    SwitchAgent: function (Caption) {
      try {
          // if (MessageBox.Show("Thoát agent hiện tại và chuyển sang [" + e.Item.Caption + "] ?", "", MessageBoxButtons.YesNo) != DialogResult.Yes)
          //     return;
          // string msg = channel.ChangeStatus(clientInfo, ChatClientStatus.Offline, "S_Switch");
          // if (msg != "S_OK")
          // {
          //     MessageBox.Show(msg, "IPCC Respond");
          //     return;
          // }
          // (frmHoTro_TapTrung.clientInfo.User as IPCCUser).account_IPCC = e.Item.Caption;
          // txt_IPCCUser.Caption = e.Item.Caption;
          // hdl_ChangeStatus(sender, new DevExpress.XtraBars.ItemClickEventArgs(tsBtn_LoginIPCC, null));
        this.$bvModal.msgBoxConfirm("Thoát agent hiện tại và chuyển sang [" + Caption + "] ?", {
          title: '',
          size: 'sm',
          okTitle: 'Đồng ý',
          cancelTitle: 'Không đồng ý',
        }).then(async v => {
          if (v) {
          }
        });
      } catch (ex) {
        console.log('SwitchAgent', ex)
      }
    },
    LayDSThamSo: async function() {
      try {
        var input = {}
        var data = this.GetData(await api.sp_lay_ds_thamso(this.axios, input));
        var json_data = JSON.parse(data)
        if (json_data.ERROR_CODE == 1 || json_data.ERROR_CODE == '1') {
          return json_data.RESULT
        } else return {}           
      } catch (e) {
        if (e && e.data) {
          this.$toast.error('Lỗi sp_lay_ds_thamso ' + JSON.stringify(e.data.message_detail ? e.data.message_detail : e.data.message))
        } else {
          this.$toast.error('Lỗi sp_lay_ds_thamso ' + JSON.stringify(e))
        }
        return {}
      } finally {

      }
    },    
    TraCuu_FocusedRowChanged: async function (data) {
      if (data) {
        console.log('TraCuu_FocusedRowChanged',data)
        if (this.selectedTab == 1) {
          this.grid_lookup_kh = data.thongtin_TimKiem
        } else if (this.selectedTab == 6) {
          this.grid_lookup_mr = data.thongtin_TimKiem
        }

        if (data['isConfirm']) {
          this.txtTenKH = data.dtDanhSach['ten_tb']
          this.lblMaTinh.Text = this.DanhMuc['TINH'].filter(e => e.TINH_ID == data.dtDanhSach['tinh_id'])[0]['TENTINH']
          this.txtDiaChiKH = data.dtDanhSach["diachi_ld"]
          this.khachhang_id = data.dtDanhSach['khachhang_id']
          this.tinh_id = data.dtDanhSach['tinh_id']
          this.txt_PhoneNo = ''
          this.selectedTab = 2
        } 
        
        // await this.LayDSThueBaoKH()        
      }
    },
    LayDSThueBaoKH: async function () {
      try {
        this.grid_DanhSach.DataSource = []
        var input = {
          vkhachhang_id: this.khachhang_id, // input.vkhachhang_id, 
          vma_dv: this.tinh_id //input.vma_dv
        }
        var data = this.GetDataList(await api.sp_lay_ds_thuebaokh(this.axios, input));
        this.grid_DanhSach.DataSource = data && data.length > 0 ? this.LowerCasePropertyList(data) : []       
      } catch (e) {
        if (e && e.data) {
          this.$toast.error('Lỗi sp_lay_ds_thuebaokh ' + JSON.stringify(e.data.message_detail ? e.data.message_detail : e.data.message))
        } else {
          this.$toast.error('Lỗi sp_lay_ds_thuebaokh ' + JSON.stringify(e))
        }        
      } finally {

      }
    },
    gview_DanhSach_FocusedRowChanged: async function (dr) {
      // const dr = this.$refs.gview_DanhSach.$refs.grid.getSelectedRecords()
      console.log('gview_DanhSach_FocusedRowChanged',dr)
      if (dr.length == 0) {
        // TODO: fuc Clear()
        // Clear()
        this.grid_DanhSach.selected = {}
        return
      }

      try {
        this.grid_DanhSach.selected = dr[0]
        this.txtDiaChiKH = dr[0]["diachi_ld"]
        this.txtTenKH = dr[0]["ten_tb"]
        if (dr[0]["ma_tinh"]) {
          // TODO: CALL API
          this.lblMaTinh.Text = dr[0]["ma_tinh"]
        }

        let so_dt = null
        if (dr[0]["so_dt"]) {
          so_dt = dr[0]["so_dt"]
        }

        if (!so_dt) {
          so_dt = dr[0]["ma_tb"]
        }
        this.txt_PhoneNo = so_dt
        // TODO: CALL API
        // this.grid_History.DataSource = fcd.LayLichSuCuocGoi(so_dt, 100);

        // TODO: Not understand
        this.lblPhanLoaiKH.Text = dr[0]["lblPhanLoaiKH"]

        // btnTraCuuOTT.Visible = btnTraCuuMyTV.Visible = new string[]{"61", "171", "271"}.Any(x => x == dr["Loaitb_id"].ToString());
        // btnTraCuuVDC.Visible = dr["Loaitb_id"].ToString() == "58";
        // btnBaoHongOtt.Visible = (ModifierKeys & Keys.Control) == Keys.Control && dr["Loaitb_id"].ToString() == "271";
      } catch (ex) {
        console.log('gview_DanhSach_FocusedRowChanged',ex)
        this.$toast.error("Lỗi: " + ex)
      }
    },
    LayPhanLoaiKH: async function (thuebao_id, loaitb_id) {
      try {
        var input = {
          vthuebao_id: thuebao_id,
          vloaitb_id: loaitb_id
        }
        var data = this.GetData(await api.sp_lay_PhanLoai_KH(this.axios, input));
        return data
      } catch (e) {
        if (e && e.data) {
          this.$toast.error('Lỗi sp_lay_PhanLoai_KH ' + JSON.stringify(e.data.message_detail ? e.data.message_detail : e.data.message))
        } else {
          this.$toast.error('Lỗi sp_lay_PhanLoai_KH ' + JSON.stringify(e))
        }
        return null
      } finally {

      }
    },
    tsbtnThongTinLH_Click () {
      try {
        if (this.$refs.gview_DanhSach) {
          let dr = this.$refs.gview_DanhSach.$refs.grid.getSelectedRecords()
          if (dr.length > 0) {
            this.$refs.dlgLienHeTapTrung.showModal({dtrow: dr[0], so_dt: this.txt_PhoneNo, dsTinh: this.DanhMuc['TINH']})
          } else {
            this.$refs.dlgLienHeTapTrung.showModal({dtrow: {}, so_dt: this.txt_PhoneNo, dsTinh: this.DanhMuc['TINH']})
          }
        } else {
          this.$refs.dlgLienHeTapTrung.showModal({dtrow: {}, so_dt: this.txt_PhoneNo, dsTinh: this.DanhMuc['TINH']})
        }
      } catch (ex) {
        this.$toast.error("Lỗi: " + ex)
      }
    },
    dlgLienHeTapTrung_Close: async function () {
      try {
        let so_dt = ""
        let mavung = ""
        if (this.txt_PhoneNo) {
          // TODO: CALL API KiemTraSoCoDinh
          var check = this.KiemTraSoCoDinh(this.txt_PhoneNo, mavung)
          if (check.ketqua) {
          // TODO: CALL API
            this.grid_DanhSach.DataSource = await this.LayDSLienhe(check.so_dt, check.mavung)
          } else {
          // TODO: CALL API
            this.grid_DanhSach.DataSource = await this.LayDSLienhe(this.txt_PhoneNo, mavung)
          }
        }
      } catch (ex) {
        this.$toast.error("Lỗi: " + ex)
      }
    },
    KiemTraSoCoDinh: function (so_dt, ma_tinh) {
      var vma_tinh = ma_tinh
      var sodt_chuanhoa = so_dt
      var ketqua = true
      if (!so_dt || so_dt.length == 0) ketqua = false
      let codeLength = 4
      if (so_dt.substring(0, 3) == '028' || so_dt.substring(0, 3) == '024') codeLength = 3
      if (api.dctDSMaVung().hasOwnProperty(so_dt.substring(0, codeLength)) == false) {
        ketqua = false
      } else {
        vma_tinh = api.dctDSMaVung()[so_dt.substring(0, codeLength)]
        sodt_chuanhoa = sodt_chuanhoa.substring(codeLength)
        ketqua = true
      }

      return {ketqua: ketqua, so_dt: sodt_chuanhoa, mavung: vma_tinh }
    },
    LayDSLienhe: async function (so_dt, mavung) {
      try {
        var input = {
          sdt: so_dt,
          matinh: mavung,
          MIG_HNI: false
        }
        var data = this.GetDataList(await api.sp_lay_ds_lienhe(this.axios, input));
        return data
      } catch (e) {
        if (e && e.data) {
          this.$toast.error('Lỗi sp_lay_ds_lienhe ' + JSON.stringify(e.data.message_detail ? e.data.message_detail : e.data.message))
        } else {
          this.$toast.error('Lỗi sp_lay_ds_lienhe ' + JSON.stringify(e))
        }
        return []
      } finally {

      }
    },
    tab_Detail_SelectedPageChanged: async function (e) {
      // if (e.Page == 1 || e.Page == 6) {
      //   this.DanhMuc = await this.LayDSThamSo();
      // }
      if (e.Page == 2) {
        // if (this.tinh_id) await this.LayDSThueBaoKH() 
      } else if (e.Page == 3) {
        try {
          // TODO: CALL API
          // this.grid_History.DataSource = this.LayLichSuCuocGoi(ChucNang.Instance.Nhanvien_ID_Goc, 100);
          // var input = { max_row: 100, nhanvien_id: Number(this.tt_nd.nhanvien_id), sodienthoai: this.txt_PhoneNo}
          // this.grid_History.DataSource = await this.LayLichSuCuocGoi(input);
        } catch (ex) {
          this.$toast.error("Lỗi: " + ex)
        }
      }
    },
    LayLichSuCuocGoi: async function (input) {
      try {
        var data = this.GetDataList(await api.sp_lay_LichSu_CuocGoi(this.axios, input));
        return data
      } catch (e) {
        if (e && e.data) {
          this.$toast.error('Lỗi sp_lay_LichSu_CuocGoi ' + JSON.stringify(e.data.message_detail ? e.data.message_detail : e.data.message))
        } else {
          this.$toast.error('Lỗi sp_lay_LichSu_CuocGoi ' + JSON.stringify(e))
        }
        return []
      } finally {

      }
    },
    btn_ChiTiet_CG_Click () {
      // if (!this.cuocgoi_id) {
      //   return
      // }
      var dsTinh = this.DanhMuc['TINH']
      var dsLoaiHinh = this.DanhMuc['LOAIHINH']
      if (this.grid_DanhSach.selected) {
        var dulieu = {
          cuocgoi_id: this.cuocgoi_id,    
          so_dt: this.txt_PhoneNo,      
          tinh_id: this.grid_DanhSach.selected['tinh_id'],
          dsTinh: dsTinh,
          loaitb_id: this.grid_DanhSach.selected['loaitb_id'],
          dsLoaiHinh: dsLoaiHinh,
          ma_tb: this.grid_DanhSach.selected['ma_tb'],
          noidung_ht: this.txt_NoiDungHoTro,
          ten_kh : this.txtTenKH,
        }
      }
      this.$refs.dlgChiTietCuocGoi.txt_MaTB = dulieu['ma_tb']
      this.$refs.dlgChiTietCuocGoi.txt_CallID = dulieu['cuocgoi_id']
      this.$refs.dlgChiTietCuocGoi.txt_SDT_LienHe = dulieu['so_dt']
      this.$refs.dlgChiTietCuocGoi.cbo_Tinh.EditValue = dulieu['tinh_id']
      this.$refs.dlgChiTietCuocGoi.cbo_Tinh.DataSource = dulieu['dsTinh'] 
      this.$refs.dlgChiTietCuocGoi.cbo_LoaiHinhTB.EditValue = dulieu['loaitb_id']
      this.$refs.dlgChiTietCuocGoi.cbo_LoaiHinhTB.DataSource = dulieu['dsLoaiHinh']
      this.$refs.dlgChiTietCuocGoi.txt_NoiDungHT = dulieu['noidung_ht']
      this.$refs.dlgChiTietCuocGoi.txt_NoiDungPA = dulieu['noidung_ht']
      this.$refs.dlgChiTietCuocGoi.txt_KhachHang = dulieu['ten_kh']
      this.$refs.dlgChiTietCuocGoi.showModal()
    },
    gview_History_FocusedRowChanged (data) {
      console.log('gview_History_FocusedRowChanged',data)
      if (data && data.length > 0) {
        this.cuocgoi_id = data[0]?.id ? data[0]?.id : this.cuocgoi_id
      }
    },    
    // TODO : Not understand
    gview_History_CustomUnboundColumnData () {

    },
    grid_Activity_DataSourceChanged (newList) {
      const dt = this.grid_Activity.DataSource
      if (dt === null || dt.length === 0) {
        return
      }

      if (!this.grid_Activity.config.find(el => el.fieldName === "Kieu_YEUCAU")) {
        this.grid_Activity.config.push({
          fieldName: 'Kieu_YEUCAU',
          headerText: 'Kiểu yêu cầu',
          allowFiltering: true
        })
      }

      this.grid_Activity.DataSource = newList.map(el => {
        return {
          ...el,
          Kieu_YEUCAU: el.LOAI_YC.toString() === "1" ? "Hợp đồng thuê bao" : el.LOAI_YC.toString() === "2" ? "Báo hỏng" : el.LOAI_YC.toString() === "3" ? "Báo hỏng" : "Khiếu nại"
        }
      })
    },
    txt_PhoneNo_KeyPress: async function () {
      try {
        this.loading(true)
        if (this.txt_PhoneNo.length > 7) {
          // TODO: fun HienThiThongTinKhachGoiDen()
          await this.HienThiThongTinKhachGoiDen(this.txt_PhoneNo)
        }
      } catch (e) {
        
      } finally {
        this.loading(false)
      }
    },
    HienThiThongTinKhachGoiDen: async function (sochugoi) {
      try {
        // let mavung = ""
        // var check = this.KiemTraSoCoDinh(sochugoi, mavung)
        // console.log('HienThiThongTinKhachGoiDen > KiemTraSoCoDinh', check)
        // if (check.ketqua) {
        // // TODO: CALL API
        //   this.grid_DanhSach.DataSource = await this.LayDSLienhe(check.so_dt, check.mavung)
        // } else {
        // // TODO: CALL API
        //   this.grid_DanhSach.DataSource = await this.LayDSLienhe(sochugoi, mavung)
        // }
        
        // var rowLienHe = await this.lienhe_taptrung(check.so_dt, check.mavung)
        // if (rowLienHe) {
        //   this.txtTenKH = rowLienHe['ten_lh'] ? rowLienHe['ten_lh'] : this.txtTenKH
        //   this.txtDiaChiKH = rowLienHe["diachi"] ? rowLienHe["diachi"] : this.txtDiaChiKH
        //   this.txtNgaySinhKH = rowLienHe["ngaysinh"] ? rowLienHe["ngaysinh"] : this.txtNgaySinhKH          
        // }
        this.selectedTab = 2
      } catch (e) {
        console.log('HienThiThongTinKhachGoiDen',e)
      } finally {}
    },
    lienhe_taptrung: async function (sdt, matinh) {
      try {
        var input = {
          sochugoi: sdt,
          // matinh: matinh,
          MIG_HNI: false
        }
        var data = this.GetData(await api.sp_hienthi_tt_kh_goiden(this.axios, input));
        return data
      } catch (e) {
        if (e && e.data) {
          this.$toast.error('Lỗi sp_hienthi_tt_kh_goiden ' + JSON.stringify(e.data.message_detail ? e.data.message_detail : e.data.message))
        } else {
          this.$toast.error('Lỗi sp_hienthi_tt_kh_goiden ' + JSON.stringify(e))
        }
        return null
      } finally {

      }
    },
  }
}
</script>
