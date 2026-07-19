<template>
  <KDataGrid
    id="gview_DanhSach"
    ref="gview_DanhSach"
    v-bind:columns="grid_DanhSach.config"
    v-bind:dataSource="grid_DanhSach.DataSource"
    :enable-paging-server="false"
    :allowPaging="true"
    :showFilter="true"
    :showColumnCheckbox="false"
    :selectionSettings="{ enableToggle: false }"
    @rowSelected="gview_DanhSach_FocusedRowChanged"
    :contextMenuItems="grid_DanhSach.contextMenuItems"
    @contextMenuClick="contextMenu_Click"
  >
  </KDataGrid>
</template>
<script>
import KDataGrid from '@/components/kylq_components/KDataGrid'
import api from "../ReceivingCallsSwitchboardApi.js"
import EventBus from "@/utils/eventBus";

export default {
  name: "DanhSachThueBaoKhachHang",
  components: { KDataGrid },
  props: ['dulieu'],
  data: () => ({
    cuocgoi_id: 0,
    khachhang_id: 0,
    tinh_id: 0,
    ma_tb: null,
    so_tb: '',
    tt_nd: {},
    grid_DanhSach: {
      contextMenuItems: [
        // {
        //   id: 'frmTiepNhanYCKhaoSat',
        //   tag: '38',
        //   text: 'Tiếp nhận yêu cầu',
        // },
        {
          id: 'frmLapHopDong',
          tag: '',
          text: 'Lập hợp đồng',
          items: [
            {
              id: 'frmDatMoi',
              tag: '',
              text: 'Lắp mới',
              path: 'https://onebss.vnpt.vn/#/contract/InstallNewSubs?tag=0%2B2&makh=input_makh',
            },
            {
              id: 'frmThayDoiTTKhac',
              tag: '',
              text: 'Thay đổi thông tin khách hàng',
              path: 'https://onebss.vnpt.vn/#/ext-hopdong/thaydoi-thongtin-khachhang?ma_tb=input_matb&taikhoan=input_matb',
            },
            {
              id: 'frmChuyenDich',
              tag: '',
              text: 'Chuyển dịch',
              path: 'https://onebss.vnpt.vn/#/ext-hopdong/dichchuyen?ma_tb=input_matb&taikhoan=input_matb',
            },
            {
              id: 'frmChamDutSD',
              tag: '',
              text: 'Thanh lý',
              path: 'https://onebss.vnpt.vn/#/contract/liquidate?ma_tb=input_matb&taikhoan=input_matb',
            },
            {
              id: 'frmKhoiPhuc_ThanhLy',
              tag: '',
              text: 'Khôi phục',
              path: 'https://onebss.vnpt.vn/#/contract/RestoreLiquidationSubs?ma_tb=input_matb&taikhoan=input_matb',
            },
            {
              id: 'frmThayDoiTocDo',
              tag: '',
              text: 'Thay đổi tốc độ',
              items: [
                {
                  id: 'frmThayDoiTocDoADSL',
                  tag: '0',
                  text: 'Thay đổi tốc độ Internet',
                  path: 'https://onebss.vnpt.vn/#/contract/ChangeInternetSpeed?tag=0%3Fma_tb%3Dinput_matb&taikhoan=input_matb',
                },
                {
                  id: 'frmThayDoiThongSoWan',
                  tag: '',
                  text: 'Thay đổi tốc độ MegaWan',
                  path: 'https://onebss.vnpt.vn/#/contract/ChangeMegaWanConfigCrossSell?ma_tb=input_matb&taikhoan=input_matb',
                },
                {
                  id: 'frmThayDoiThongSoMyTV',
                  tag: '',
                  text: 'Thay đổi thông số MyTV',
                  path: 'https://onebss.vnpt.vn/#/contract/changeMyTVParameter?ma_tb=input_matb&taikhoan=input_matb',
                },
                {
                  id: 'frmThayDoiTDTSL',
                  tag: '',
                  text: 'Thay đổi tốc độ TSL',
                  path: 'https://onebss.vnpt.vn/#/contract/SetupChangeTSLSpeed?ma_tb=input_matb&taikhoan=input_matb',
                },
              ]
            },
            {
              id: 'frmThayDoiLHTB',
              tag: '',
              text: 'Chuyển đổi loại hình',
              items: [
                {
                  id: 'frmThayDoiLHTB_CDIMS',
                  tag: '0',
                  text: 'Chuyển đổi CĐ<->IMS',
                  path: 'https://onebss.vnpt.vn/#/contract/ChangeSubsTypeCrossSale?ma_tb=input_matb&taikhoan=input_matb',
                },
                {
                  id: 'frmThayDoiLHTB_KHAC',
                  tag: '0',
                  text: 'Chuyển đổi loại hình khác',
                  path: 'https://onebss.vnpt.vn/#/contract/ChangeSubsType?tag=0&ma_tb=input_matb&taikhoan=input_matb',
                },
              ]
            },
            {
              id: 'frmThayDoiDV',
              tag: '',
              text: 'Thay đổi DVGT',
              path: 'https://onebss.vnpt.vn/#/contract/ChangeService?ma_tb=input_matb&taikhoan=input_matb',
            },
            {
              id: 'frmChuyenQuyenSD',
              tag: '',
              text: 'Chuyển quyền',
              path: 'https://onebss.vnpt.vn/#/contract/ChangeContractOwner?ma_tb=input_matb&taikhoan=input_matb',
            },
            {
              id: 'frmTachNhapTB',
              tag: '',
              text: 'Tách nhập',
              path: 'https://onebss.vnpt.vn/#/contract/SeparationSubscriber?ma_tb=input_matb&taikhoan=input_matb',
            },
          ]
        },
        {
          id: 'frmBienDong',
          tag: '',
          text: 'Biến động',
          items: [
            {
              id: 'frmTraCuuLichSuTB',
              tag: '',
              text: 'Biến động thuê bao',
              path: 'https://onebss.vnpt.vn/#/search/SearchSubscriberInformation?ma_tb=input_matb&taikhoan=input_matb',
            },
          ]
        },
        {
          id: 'frmTraCuu',
          tag: '',
          text: 'Tra cứu',
          items: [
            {
              id: 'frmTraCuuCTDanhBa',
              tag: '',
              text: 'Tra cứu tổng hợp',
              path: 'https://onebss.vnpt.vn/#/search/SearchGeneral?ma_tb=input_matb&taikhoan=input_matb',
            },
            {
              id: 'frmTraCuuPhieuTC',
              tag: '',
              text: 'Tra cứu chi tiết thi công',
              path: 'https://onebss.vnpt.vn/#/search/SearchInstallDetail?ma_tb=input_matb&taikhoan=input_matb',
            },
            {
              id: 'frmTraCuuVASC',
              tag: '',
              text: 'Tra cứu chi tiết MyTV',
              path: 'https://onebss.vnpt.vn/#/search/SearchSubcriberMyTV?ma_tb=input_matb&taikhoan=input_matb',
            },
            {
              id: 'frmTraCuuVDC',
              tag: '',
              text: 'Tra cứu chi tiết Fiber',
              path: 'https://onebss.vnpt.vn/#/search/SearchSubsInfo?ma_tb=input_matb&taikhoan=input_matb',
            },
            {
              id: 'frmTraCuuChiTietMyTvOtt',
              tag: '',
              text: 'Tra cứu thuê bao MyTV OTT',
              path: 'https://onebss.vnpt.vn/#/search/SearchSubcriberMyTV?ma_tb=input_matb&taikhoan=input_matb',
            },
            {
              id: 'frmTraCuuChiTietNoTongHop',
              tag: '',
              text: 'Tra cứu chi tiết nợ tổng hợp',
              path: 'https://onebss.vnpt.vn/#/qltn/TraCuuNoTongHop?ma_tb=input_matb&taikhoan=input_matb',
            },
          ]
        },
        {
          id: 'frmBaoHong',
          tag: '',
          text: 'Báo Hỏng',
          items: [
            {
              id: 'frmTiepNhanBH',
              tag: '1+1',
              text: 'Tiếp nhận báo hỏng',
              path: 'https://onebss.vnpt.vn/#/incident/ReceiveIncident?ma_tb=input_matb&taikhoan=input_matb&sdt=input_sdt&tinh=input_tinh&matinh=input_matinh&dichvuvt_id=dichvuvtid&loaitb_id=loaitbid',
            },
            {
              id: 'frmTraCuuPhieuBH',
              tag: '',
              text: 'Lịch sử báo hỏng',
              path: 'https://onebss.vnpt.vn/#/incident/SearchIncidentUpdateAppointment?ma_tb=input_matb&taikhoan=input_matb',
            },
            {
              id: 'frmTiepNhanBHBC',
              tag: '',
              text: 'Tiếp nhận báo hỏng bán chéo',
              path: 'https://onebss.vnpt.vn/#/incident/ReceiveIncidentCrossSale?ma_tb=input_matb&taikhoan=input_matb',
            },
            // {
            //   id: 'frmBaoHongMyTvOtt',
            //   tag: '',
            //   text: 'Báo hỏng thuê bao MyTV OTT',
            // },
            // {
            //   id: 'frmTiepNhanBH',
            //   tag: '4',
            //   text: 'Xử lý báo hỏng tồn chủ động',
            // },
          ]
        },
        {
          id: 'frmKhieuNai',
          tag: '',
          text: 'Khiếu nại',
          // target: '.e-content',
          items: [
            {
              id: 'frmTiepNhanKN',
              tag: '9+255',
              text: 'Tiếp nhận khiếu nại',
              path: 'https://onebss.vnpt.vn/#/ext-khieunai/tiep-nhan-khieu-nai?ma_tb=input_matb&taikhoan=input_matb',
            },
            {
              id: 'frmTraCuuPhieuKN',
              tag: '',
              text: 'Lịch sử xử lý khiếu nại',
              path: 'https://onebss.vnpt.vn/#/complaint/SearchComplainDetail?ma_tb=input_matb&taikhoan=input_matb',
            },
            {
              id: 'frmTraCuuLSPhanAnh',
              tag: '',
              text: 'Lịch sử phản ánh',
              path: 'https://onebss.vnpt.vn/#/cskh/TraCuuLichSuPhanAnh?ma_kh=input_makh',
            },
          ]
        },
        {
          id: 'frmTiepNhanYC_18001166',
          tag: '',
          text: 'Tiếp nhận đơn hàng',
          path: 'https://onebss.vnpt.vn/#/htkh/Order?ma_tb=input_matb&taikhoan=input_matb',
        },
        // {
        //   id: 'frmTiepNhanHoTroCOVID',
        //   tag: '',
        //   text: 'Tiếp nhận hỗ trợ COVID',
        // },
      ],
      config: [
        {
          fieldName: 'tentinh',
          headerText: 'Tỉnh',
          allowFiltering: true,
          width: 150
        },
        {
          fieldName: 'ma_tb',
          headerText: 'Số máy/Account',
          isPrimaryKey: true,
          width: 180,
          allowFiltering: true
        },
        {
          fieldName: 'ma_kh',
          headerText: 'Mã KH',
          width: 150,
          allowFiltering: true
        },
        {
          fieldName: 'ten_tb',
          headerText: 'Tên thuê bao',
          width: 180,
          allowFiltering: true,
        },
        {
          fieldName: 'ten_dv',
          headerText: 'Dịch vụ',
          width: 180,
          allowFiltering: true
        },
        {
          fieldName: 'ten_loaihinh',
          headerText: 'Loại hình',
          width: 180,
          allowFiltering: true
        },
        {
          fieldName: 'diachi_ld',
          headerText: 'Địa chỉ lắp đặt',
          allowFiltering: true
        },
        {
          fieldName: 'so_dt',
          headerText: 'Số điện thoại',
          width: 130,
          allowFiltering: true
        },
        {
          fieldName: 'so_gt',
          headerText: 'Số giấy tờ',
          width: 120,
          allowFiltering: true
        },
        {
          fieldName: 'mst',
          headerText: 'Mã số thuế',
          allowFiltering: true
        },
        {
          fieldName: 'ten_loaikh',
          headerText: 'Loại KH',
          allowFiltering: true
        },
        {
          fieldName: 'ma_plkh',
          headerText: 'Mã PLKH',
          allowFiltering: true
        },
        {
          fieldName: 'ten_trangthai',
          headerText: 'Trạng thái TB',
          allowFiltering: true
        },
      ],
      DataSource: [],
      selected: {},
    },
  }),
  mounted: async function () {
    if (this.dulieu) {
      if (this.dulieu.so_tb) this.so_tb = this.dulieu.so_tb
      if (this.dulieu.khachhang_id) this.khachhang_id = this.dulieu.khachhang_id
      if (this.dulieu.tinh_id) this.tinh_id = this.dulieu.tinh_id
      // if (this.so_tb || (this.khachhang_id && this.tinh_id)) await this.frm_Load(this.so_tb, this.khachhang_id, this.tinh_id)
    }
  },
  watch: {
    'dulieu.so_tb'(val) {
      this.so_tb = val
    },
    'dulieu.khachhang_id'(val) {
      this.khachhang_id = val
    },
    'dulieu.tinh_id'(val) {
      this.tinh_id = val
    },
    'dulieu.ma_tb'(val) {
      this.ma_tb = val
    },
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
        this.$root.$toast.error('Lỗi: ' + (response.data.message_detail ? response.data.message_detail : response.data.message))
        return []
      }
    },
    GetData: function (response) {
      if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
        return response.data.data
      } else if (response && response.data && response.data.error_code === 'BSS-00000204') {
        return ''
      } else {
        this.$root.$toast.error('Lỗi: ' + (response.data.message_detail ? response.data.message_detail : response.data.message))
        return ''
      }
    },
    frm_Load: async function (so_tb, khachhang_id, tinh_id, ma_tb) {
      try {
        this.loading(true)
        if (ma_tb) this.ma_tb = ma_tb
        else this.ma_tb = null
        if (so_tb) {
          let mavung = ""
          this.so_tb = so_tb
          var check = this.KiemTraSoCoDinh(so_tb, mavung)
          console.log('frm_Load > KiemTraSoCoDinh', check)
          if (check.ketqua) {
            // TODO: CALL API
            var input = {
              sdt: check.so_dt,
              matinh: check.mavung,
              MIG_HNI: false
            }
            await this.LayDSLienhe(input)
            return
          } else {
            // TODO: CALL API
            var input = {
              sdt: so_tb,
              matinh: mavung,
              MIG_HNI: false
            }
            await this.LayDSLienhe(input)
            return
          }
        } else if (khachhang_id && tinh_id) {
          var input = {
            vkhachhang_id: khachhang_id, // input.vkhachhang_id,
            vma_dv: tinh_id //input.vma_dv
          }
          await this.LayDSThueBaoKH(input);
          return
        }
      } catch (e) {
        console.log('DanhSachThueBaoKhachHang > frm_Load',e)
      } finally { this.loading(false) }
    },
    LayDSThueBaoKH: async function (input) {
      try {
        var data = this.GetDataList(await api.sp_lay_ds_thuebaokh(this.axios, input));
        this.grid_DanhSach.DataSource = data && data.length > 0 ? this.LowerCasePropertyList(data) : []
        if (this.grid_DanhSach.DataSource && this.grid_DanhSach.DataSource.length > 0) {
          for (let item of this.grid_DanhSach.DataSource) {
            item.stt = this.ma_tb && item['ma_tb'] == this.ma_tb ? 1 : Math.floor(Math.random() * 10^6) + 2
            if (item['tinh_id'] && !item['tentinh']) item.tentinh = this.dulieu.DanhMuc['TINH'].filter(e => e.TINH_ID == item['tinh_id'])[0]['TENTINH']
            if (item['dichvuvt_id']) item.ten_dv = this.dulieu.DanhMuc['LOAIHINH'].filter(e => e.DICHVUVT_ID == item['dichvuvt_id'])[0]['TEN_DVVT']
            if (item['loaitb_id']) item.ten_loaihinh = this.dulieu.DanhMuc['LOAIHINH'].filter(e => e.LOAITB_ID == item['loaitb_id'])[0]['LOAIHINH_TB']
            if (item['trangthaitb_id']) item.ten_trangthai = this.dulieu.DanhMuc['TRANGTHAITB'].filter(e => e.TRANGTHAITB_ID == item['trangthaitb_id'])[0]['TRANGTHAI_TB']
          }
          this.grid_DanhSach.DataSource = this.grid_DanhSach.DataSource.sort((a, b) => Number(a.stt) - Number(b.stt))
        }
      } catch (e) {
        console.log('sp_lay_ds_thuebaokh',e)
        if (e && e.data) {
          this.$toast.error('Lỗi sp_lay_ds_thuebaokh ' + JSON.stringify(e.data.message_detail ? e.data.message_detail : e.data.message))
        } else {
          this.$toast.error('Lỗi sp_lay_ds_thuebaokh ' + JSON.stringify(e))
        }
      } finally {

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
    LayDSLienhe: async function (input) {
      try {
        this.grid_DanhSach.DataSource = this.GetDataList(await api.sp_lay_ds_lienhe(this.axios, input));
        if (this.grid_DanhSach.DataSource && this.grid_DanhSach.DataSource.length > 0) {
          for (let item of this.grid_DanhSach.DataSource) {
            item.stt = this.ma_tb && item['ma_tb'] == this.ma_tb ? 1 : Math.floor(Math.random() * 10^6) + 2
            if (item['tinh_id'] && !item['tentinh']) item.tentinh = this.dulieu.DanhMuc['TINH'].filter(e => e.TINH_ID == item['tinh_id'])[0]['TENTINH']
            if (item['dichvuvt_id']) item.ten_dv = this.dulieu.DanhMuc['LOAIHINH'].filter(e => e.DICHVUVT_ID == item['dichvuvt_id'])[0]['TEN_DVVT']
            if (item['loaitb_id']) item.ten_loaihinh = this.dulieu.DanhMuc['LOAIHINH'].filter(e => e.LOAITB_ID == item['loaitb_id'])[0]['LOAIHINH_TB']
            if (item['trangthaitb_id']) item.ten_trangthai = this.dulieu.DanhMuc['TRANGTHAITB'].filter(e => e.TRANGTHAITB_ID == item['trangthaitb_id'])[0]['TRANGTHAI_TB']
          }
          this.grid_DanhSach.DataSource = this.grid_DanhSach.DataSource.sort((a, b) => Number(a.stt) - Number(b.stt))
        }
      } catch (e) {
        if (e && e.data) {
          this.$toast.error('Lỗi sp_lay_ds_lienhe ' + JSON.stringify(e.data.message_detail ? e.data.message_detail : e.data.message))
        } else {
          this.$toast.error('Lỗi sp_lay_ds_lienhe ' + JSON.stringify(e))
        }
      } finally {

      }
    },
    gview_DanhSach_FocusedRowChanged: async function (args) {
      try {
        this.loading(true)
        const dr = this.$refs.gview_DanhSach.$refs.grid.getSelectedRecords()
        document.getElementById('gview_DanhSach').scrollIntoView(true);
        // if (dr && dr.length > 0) {
        //   var thuebao_id = dr[0]["thuebao_id"]
        //   var loaitb_id = dr[0]["loaitb_id"]
        //   var isVIP = await this.LayPhanLoaiKH(thuebao_id, loaitb_id)
        //   if (isVIP) {
        //     dr[0].lblPhanLoaiKH = "Khách hàng VIP";
        //   } else {
        //     dr[0].lblPhanLoaiKH = "Khách hàng thường";
        //   }
        // }

        this.$emit("rowSelected", dr);
      } catch (e) {} finally { this.loading(false) }
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
    ChuyenPhanVung: async function(val) {
      console.log(val);
      let rs = await this.$root.context.post("/quantri/oauth/token",{
        "grant_type": "password",
        "client_id": "clientapp",
        "client_secret": "password",
        "phanvung_id":val
      });

      try {
        if(rs && rs.access_token) {
          this.$root.context.authenticate(rs);
          //this.$toast.success(`Chuyển tỉnh thành công sang ${this.lstChuyenTinh.find(x=>x.id==val).text}`);
          //this.$emit('tokenchanged',{});
          //location.reload();
          //this.$router.go(0);
        }
        else {
          this.$toast.error("Có lỗi xảy ra trong quá trình thực hiện chuyển tỉnh!");
        }
      } catch(ex) {
        console.log(ex)
        this.$toast.error("Có lỗi xảy ra trong quá trình thực hiện chuyển tỉnh!");
      }
    },
    async contextMenu_Click(args) {
      let selected = this.$refs.gview_DanhSach.$refs.grid.getSelectedRecords()
      console.log(selected)
      let matb = ''
      let makh = ''
      let sdt = ''
      let tinh_id = ''
      let ma_tinh = ''
      let path = args.item.path
      if (selected && selected.length > 0) {
        tinh_id = selected[0]['tinh_id']
        EventBus.$emit('emitChuyenTinh', {id: String(tinh_id), reload : false});

        matb = selected[0]['ma_tb']
        if (matb) path = path.replaceAll('input_matb', matb)
        else path = path.replaceAll('input_matb','')

        sdt = this.so_tb ? this.so_tb : (selected[0]['so_dt'] ? selected[0]['so_dt'] : selected[0]['ma_tb'])
        if (sdt) path = path.replaceAll('input_sdt', sdt)
        else path = path.replaceAll('input_sdt','')

        makh = selected[0]['ma_kh']
        if (makh) path = path.replaceAll('input_makh', makh)
        else path = path.replaceAll('input_makh','')

        let dichvuvtid = selected[0]['dichvuvt_id']
        if (dichvuvtid) path = path.replaceAll('dichvuvtid', dichvuvtid)
        else path = path.replaceAll('dichvuvtid','')

        let loaitbid = selected[0]['loaitb_id']
        if (loaitbid) path = path.replaceAll('loaitbid', loaitbid)
        else path = path.replaceAll('loaitbid','')

        if (tinh_id) path = path.replaceAll('input_tinh', tinh_id)
        else path = path.replaceAll('input_tinh','')

        ma_tinh = this.dulieu.DanhMuc['TINH'].filter(e => e.TINH_ID == tinh_id)[0]['TENTAT']
        if (ma_tinh) path = path.replaceAll('input_matinh', ma_tinh)
        else path = path.replaceAll('input_matinh','')
      } else {
        if (path.includes('input_matb')) path = path.replaceAll('input_matb','')
        else if (path.includes('input_makh')) path = path.replaceAll('input_makh','')
        else if (path.includes('input_sdt')) path = path.replaceAll('input_sdt','')
        else if (path.includes('input_tinh')) path = path.replaceAll('input_tinh','')
        else if (path.includes('input_matinh')) path = path.replaceAll('input_matinh','')
      }
      if (process.env.NODE_ENV == 'testing') {
        path = path.replace('https://onebss.vnpt.vn', 'https://dev-onebss.vnpt.vn')
      }
      window.open(path, '_blank')
    },
  }
}
</script>
