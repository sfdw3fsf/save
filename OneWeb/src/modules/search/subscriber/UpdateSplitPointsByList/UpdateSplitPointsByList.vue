<template src="./UpdateSplitPointsByList.html"></template>

<script>
import breadcrumb from '@/components/breadcrumb'
import api from './api'
import { DichVuVienThong, LOAI_DV } from '../../../../const/enums'
import ViewHistoryPopup from './popups/ViewHistoryPopup'
import moment from 'moment'

export default {
  name: 'UpdateSplitPointsByList',
  components: { breadcrumb, ViewHistoryPopup },
  data () {
    return {
      header: {
        title: 'Sửa đổi trạm thi công theo điểm chia',
        list: [
          { name: 'Lập hợp đồng', link: { name: 'Ui.cards' } },
          { name: 'Lắp đặt mới', link: { name: 'Ui.buttons' } }
        ]
      },
      actions: [
        { id: 'btnLayTTMoi', icon: 'one-reload1', name: 'Lấy TT', visible: true },
        { id: 'tsbtnGhiLai', icon: 'one-save', name: 'Ghi lại', visible: true },
        { id: 'tsbtnViewHistory', icon: 'one-search', name: 'Tra cứu biến động', visible: true }
      ],
      diemChiaCuFilter: {
        donViQLId: null,
        diemChiaId: null,
        dichVuVTId: null,
        cboDaiVT: [],
        cboVetinhAdsl: [],
        cboDichVuVT: []
      },
      diemChiaMoiFilter: {
        donViQLId: null,
        diemChiaId: null,
        cboDaiVTMoi: [],
        cboVeTinhMoi: []
      },
      danhSachThueBao: {
        headers: [
          { fieldName: 'ma_tb', headerText: 'Số máy/Acc', allowFiltering: true, visible: true },
          { fieldName: 'ten_tb', headerText: 'Tên thuê bao', allowFiltering: true, visible: true },
          { fieldName: 'diachi_ld', headerText: 'Địa chỉ lắp đặt', allowFiltering: true, visible: true },
          { fieldName: 'ngay_sd', headerText: 'Ngày sử dụng', allowFiltering: true, visible: true },
          { fieldName: 'gcol_Diem', headerText: 'Điểm', allowFiltering: true, visible: true }
        ],
        dataSources: [],
        selectedItems: []
      },
      machine: '',
      position: { X: 'center', Y: 'top' }
    }
  },
  computed: {
    nhanVienId () {
      return +this.$root.token.getNhanVienID()
    },
    nguoiDungId () {
      return +this.$root.token.getNguoiDungID()
    }
  },
  async created () {
    const [machineResponse, loadResponse] = await Promise.all([
      this.$root.token.getMachine(),
      await this.frmCapNhatTramHCTheoFile_Load()
    ])
    this.machine = machineResponse
  },
  methods: {
    async frmCapNhatTramHCTheoFile_Load () {
      this.loading(true)
      try {
        const [dichvuVTCbo, daiVTCbo] = await Promise.all([
          this.getDanhSachDichVuVT(),
          this.getDanhSachDaiVT(LOAI_DV.DONVIQL_LD)
        ])
        this.diemChiaCuFilter.cboDichVuVT = dichvuVTCbo
        if (dichvuVTCbo.length > 0) this.diemChiaCuFilter.dichVuVTId = dichvuVTCbo[0].dichvuvt_id
        this.diemChiaCuFilter.cboDaiVT = daiVTCbo
        this.diemChiaMoiFilter.cboDaiVTMoi = daiVTCbo
        if (daiVTCbo.length > 0) {
          const donViId = daiVTCbo[0].donvi_id
          this.diemChiaCuFilter.donViQLId = donViId
          this.diemChiaMoiFilter.donViQLId = donViId
          const diemChiaCbo = await this.getDanhSachDiemChia(donViId)
          this.diemChiaCuFilter.cboVetinhAdsl = diemChiaCbo
          this.diemChiaMoiFilter.cboVeTinhMoi = diemChiaCbo
          if (diemChiaCbo.length > 0) {
            this.diemChiaCuFilter.diemChiaId = diemChiaCbo[0].donvi_id
            this.diemChiaMoiFilter.diemChiaId = diemChiaCbo[0].donvi_id
          }
        }
      } catch (e) {
        this.diemChiaCuFilter.cboDichVuVT = []
        this.diemChiaCuFilter.cboDaiVT = []
        this.diemChiaMoiFilter.cboDaiVTMoi = []
      } finally {
        this.loading(false)
      }
    },
    async getDanhSachDichVuVT () {
      try {
        const response = await api.getDanhSachDichVuVT(this.axios, { dichvu_vt: '1,4,7,8,9,10,11' })
        const data = await response.data
        return data.error_code === 'BSS-00000000' && data.error === '200' ? data.data : []
      } catch (e) {
        this.$root.$toast.error('Không thể lấy danh sách dịch vụ viễn thông')
        return []
      }
    },
    async getDanhSachDaiVT (loaiDVId) {
      try {
        const response = await api.getDanhSachDaiVT(this.axios, loaiDVId)
        const data = await response.data
        return data.error_code === 'BSS-00000000' && data.error === '200' ? data.data : []
      } catch (e) {
        this.$root.$toast.error('Không thể lấy danh sách dịch vụ viễn thông')
        return []
      }
    },
    async onClickAction (id) {
      switch (id) {
        case 'btnLayTTMoi':
          await this.btnLayTTMoi_Click()
          break
        case 'tsbtnGhiLai':
          await this.capNhat()
          break
        default:
          await this.tsbtnViewHistory_Click()
          break
      }
    },
    async btnLayTTMoi_Click () {
      if (this.diemChiaCuFilter.cboVetinhAdsl.length <= 0 || this.diemChiaMoiFilter.cboVeTinhMoi.length <= 0) {
        this.$root.$toast.warning('Bạn chưa được gán quyền quản lý điểm chia nào trong đơn vị này !')
        return false
      }
      await this.HienThiDanhSach()
    },
    async HienThiDanhSach () {
      this.loading(true)
      try {
        this.danhSachThueBao.dataSources = await this.getDanhSachThueBao(
          this.diemChiaCuFilter.diemChiaId,
          this.diemChiaCuFilter.dichVuVTId
        )
        if (this.danhSachThueBao.dataSources.length <= 0) {
          this.$root.$toast.warning('Không có dữ liệu !')
        }
        this.gvDanhSach_CustomUnboundColumnData()
      } catch (e) {
        this.danhSachThueBao.dataSources = []
      } finally {
        this.loading(false)
      }
    },
    async getDanhSachThueBao (donViId, dichVuVTId) {
      try {
        const response = await api.getDanhSachThueBao(
          this.axios,
          { donvi_id: donViId, dichvuvt_id: dichVuVTId }
        )
        const data = await response.data
        return data.error_code === 'BSS-00000000' && data.error === '200' ? data.data : []
      } catch (e) {
        this.$root.$toast.error('Không thể lấy danh sách thuê bao')
        return []
      }
    },
    gvDanhSach_CustomUnboundColumnData () {
      const dataSource = this.danhSachThueBao.dataSources
      if (dataSource.length <= 0) return false
      const newDataSources = dataSource.map(elm => ({ ...elm, gcol_Diem: elm.kieudv_id === 1 ? 'Điểm đầu' : 'Điểm cuối' }))
      this.danhSachThueBao.dataSources = [...newDataSources]
    },
    async capNhat () {
      this.loading(true)
      try {
        const thueBaoIds = this.danhSachThueBao
          .selectedItems
          .map(elm => ({ THUEBAO_ID: elm.thuebao_id }))
        await this.capNhatLogDbtbDv(
          thueBaoIds,
          this.diemChiaCuFilter.diemChiaId,
          this.diemChiaMoiFilter.diemChiaId
        )
        if (this.danhSachThueBao.selectedItems.length > 0) {
          const maTBError = []
          for (const item of this.danhSachThueBao.selectedItems) {
            let dstdttDBCD = []
            let dstdttDBAdsl = []
            let dstdttDBMgwan = []
            let dstdttDBIMS = []
            let dsTDTTTSL = []
            let dbtbDV = [{
              TRAMVT_ID_NEW: this.diemChiaMoiFilter.diemChiaId,
              THUEBAO_ID: item.thuebao_id,
              LOAIDV_ID: LOAI_DV.TRAM_VT,
              KIEUDV_ID: item.kieudv_id,
              DONVI_QL: this.diemChiaCuFilter.donViQLId
            }]
            if (item.dichvuvt_id === DichVuVienThong.CO_DINH) {
              dstdttDBCD = this.TaoDuLieu_CD(item.thuebao_id, item.ma_tb)
            }
            if (item.dichvuvt_id === DichVuVienThong.ADSL) {
              dstdttDBAdsl = await this.TaoDuLieu_ADSL(item.thuebao_id, item.ma_tb)
            }
            if (item.dichvuvt_id === DichVuVienThong.MEGAWAN || item.dichvuvt_id === DichVuVienThong.METRONET) {
              dstdttDBMgwan = await this.TaoDuLieu_MGWAN_METRO(item.thuebao_id, item.ma_tb)
            }
            if (item.dichvuvt_id === DichVuVienThong.IMS) {
              dstdttDBIMS = await this.TaoDuLieu_IMS(item.thuebao_id, item.ma_tb)
            }
            if (item.dichvuvt_id === DichVuVienThong.TSL) {
              dsTDTTTSL = await this.TaoDuLieu_TSL(item.thuebao_id, item.ma_tb, item.kieudv_id)
            }
            const response = await this.capNhatDiemChia({
              js_tdtt_db_cd: JSON.stringify(dstdttDBCD),
              js_tdtt_db_adsl: JSON.stringify(dstdttDBAdsl),
              js_tdtt_db_mgwan: JSON.stringify(dstdttDBMgwan),
              js_tdtt_db_ims: JSON.stringify(dstdttDBIMS),
              js_tdtt_db_tsl: JSON.stringify(dsTDTTTSL),
              js_dbtb_dv: JSON.stringify(dbtbDV)
            })
            if (response.KETQUA !== 1) maTBError.push(item.ma_tb)
          }
          maTBError.length === 0
            ? this.$root.$toast.success('Cập nhật điểm chia thành công')
            : this.$root.$toast.error(`Cập nhật điểm chia không thành công ở các số máy/acc: ${maTBError.join()}`)
          await this.HienThiDanhSach()
        }
      } catch (e) {
        this.$root.$toast.error('Không thể cập nhật')
      } finally {
        this.loading(false)
      }
    },
    TaoDuLieu_CD (THUEBAO_ID, MA_TB) {
      const dstdttDBCD = []
      const ngay = moment(new Date()).format('DD/MM/YYYY HH:mm:ss')
      const row = {
        THUEBAO_ID,
        MA_TB,
        NE: '',
        NE_ID: 0,
        HOST_ID: 0,
        NE_CU: '',
        NE_ID_CU: 0,
        TUYENCAP: '',
        LIENTU: '',
        DOICAP_P: 0,
        HOPCAP_P: '',
        TUCAP_P: '',
        LOAICAP_ID: 0,
        CAP_GOC: '',
        CAP_P: '',
        DOICAP_GOC: 0,
        TUCAP_GOC: '',
        CULY: 0,
        TUYENCAP_CU: '',
        LIENTU_CU: '',
        DOICAP_P_CU: 0,
        HOPCAP_P_CU: '',
        TUCAP_P_CU: '',
        LOAICAP_ID_CU: 0,
        CAP_GOC_CU: '',
        CAP_P_CU: '',
        DOICAP_GOC_CU: 0,
        TUCAP_GOC_CU: '',
        KIEU: 0,
        NGUON_YC: 0,
        CULY_CU: 0,
        TRAMTB_ID_MOI: 0,
        HOSTLD_ID_CU: 0,
        HOSTLD_ID_MOI: 0,
        TRAMTB_ID_CU: 0,
        CHUQUAN_ID: 0,
        CHUQUANLD_ID: 0,
        CHUQUAN_ID_CU: 0,
        CHUQUANLD_ID_CU: 0,
        TRANGTHAI_ID: 1,
        NHANVIEN_ID: this.nhanVienId,
        DONVI_ID: this.diemChiaCuFilter.diemChiaId,
        NGAY_DK: ngay,
        NGAY_HT: ngay,
        MAY_CN: this.machine,
        NGAY_CN: ngay,
        NGUOI_CN: this.nguoiDungId,
        GHICHU: 'Thay đổi trạm thi công danh bạ theo file'
      }
      dstdttDBCD.push(row)
      return dstdttDBCD
    },
    async TaoDuLieu_ADSL (THUEBAO_ID, MA_TB) {
      const dstdttDBAdsl = []
      const TDTT_DB_ID = await this.getKeys('TDTT_DB_ID')
      const ngay = moment(new Date()).format('DD/MM/YYYY HH:mm:ss')
      const row = {
        TDTT_DB_ID,
        HDTB_ID: 0,
        THUEBAO_ID,
        MA_TB,
        TRANGTHAI_ID: 1,
        NHANVIEN_ID: this.nhanVienId,
        DONVI_ID: this.diemChiaCuFilter.diemChiaId,
        NGAY_DK: ngay,
        NGAY_HT: ngay,
        MAY_CN: this.machine,
        NGAY_CN: ngay,
        NGUOI_CN: this.nguoiDungId,
        GHICHU: 'Thay đổi trạm thi công danh bạ theo file',
        PORT_ID: 0,
        PORT_ID_CU: 0,
        VCI_VPI_ID: 0,
        VCI_VPI_ID_CU: 0,
        BRAS_ID: 0,
        BRAS_ID_CU: 0,
        DSLAM_ID: 0,
        MAC_ADDR: '',
        MAC_ADDR_CU: '',
        DSLAM_ID_CU: 0,
        MA_LT: '',
        MA_LT_CU: '',
        PASSWORD: '',
        PASSWORD_CU: '',
        IP: '',
        IP_CU: '',
        TUYENCAP: '',
        LIENTU: '',
        DOICAP_P: 0,
        HOPCAP_P: '',
        TUCAP_P: '',
        LOAICAP_ID: 0,
        CAP_GOC: '',
        CAP_P: '',
        DOICAP_GOC: 0,
        TUCAP_GOC: '',
        CULY: 0,
        TUYENCAP_CU: '',
        LIENTU_CU: '',
        DOICAP_P_CU: 0,
        HOPCAP_P_CU: '',
        TUCAP_P_CU: '',
        LOAICAP_ID_CU: 0,
        CAP_GOC_CU: '',
        CAP_P_CU: '',
        DOICAP_GOC_CU: 0,
        TUCAP_GOC_CU: '',
        CULY_CU: 0,
        SLID_CU: '',
        SERI_MD_CU: '',
        SLID_MOI: '',
        SERI_MD_MOI: '',
        THONGTIN_TC: '',
        SODT_CU: '',
        SODT_MOI: '',
        KETCUOI_ID_CU: '',
        KETCUOI_ID: '',
        VITRI_CU: '',
        VITRI: '',
        VITRI_2_CU: '',
        VITRI_2: '',
        DOICAP_CU: '',
        DOICAP: '',
        DOICAP_2_CU: '',
        KIEU: 0,
        NGUON_YC: 0,
        DOICAP_2: '',
        CHUQUANLD_ID: 0,
        CHUQUAN_ID: 0,
        CHUQUAN_ID_CU: 0,
        CHUQUANLD_ID_CU: 0
      }
      dstdttDBAdsl.push(row)
      return dstdttDBAdsl
    },
    async TaoDuLieu_MGWAN_METRO (THUEBAO_ID, MA_TB) {
      const dstdttDBMgwan = []
      const TDTT_DB_ID = await this.getKeys('TDTT_DB_ID')
      const ngay = moment(new Date()).format('DD/MM/YYYY HH:mm:ss')
      const row = {
        TDTT_DB_ID,
        THUEBAO_ID,
        MA_TB,
        TRANGTHAI_ID: 1,
        NHANVIEN_ID: this.nhanVienId,
        DONVI_ID: this.diemChiaCuFilter.diemChiaId,
        NGAY_DK: ngay,
        NGAY_HT: ngay,
        MAY_CN: this.machine,
        NGAY_CN: ngay,
        NGUOI_CN: this.nguoiDungId,
        GHICHU: 'Thay đổi trạm thi công danh bạ theo file',
        VCI_VPI_ID: 0,
        PE_ID: 0,
        PORT_PE: '',
        SLOT_PE: '',
        WAN_IP: '',
        LAN_IP: '',
        MA_LT: '',
        CUOC_TC: 0,
        CUOC_TK: 0,
        CUOC_TBI: 0,
        CUOC_HT: 0,
        TUYENCAP: '',
        LIENTU: '',
        DOICAP_P: 0,
        HOPCAP_P: '',
        DOICAP_GOC: 0,
        TUCAP_GOC: '',
        TUCAP_P: '',
        LOAICAP_ID: 0,
        CAP_GOC: '',
        CAP_P: '',
        VRF: '',
        LINK: '',
        SERIAL: '',
        ROUTE: '',
        SVLAN: '',
        CVLAN: '',
        CUOC_TKDT: 0,
        CUOC_TCDT: 0,
        CAP_ID: 0,
        DOICAP: '',
        CULY: 0,
        PORT_ID_CU: 0,
        VCI_VPI_ID_CU: 0,
        PE_ID_CU: 0,
        PORT_PE_CU: '',
        WAN_IP_CU: '',
        LAN_IP_CU: '',
        CUOC_TC_CU: 0,
        CUOC_TK_CU: 0,
        CUOC_TBI_CU: 0,
        CUOC_HT_CU: 0,
        TUYENCAP_CU: '',
        LIENTU_CU: '',
        DOICAP_P_CU: 0,
        HOPCAP_P_CU: '',
        DOICAP_GOC_CU: 0,
        TUCAP_GOC_CU: '',
        TUCAP_P_CU: '',
        LOAICAP_ID_CU: 0,
        CAP_GOC_CU: '',
        CAP_P_CU: '',
        VRF_CU: '',
        LINK_CU: '',
        SERIAL_CU: '',
        ROUTE_CU: '',
        SVLAN_CU: '',
        CVLAN_CU: '',
        CUOC_TKDT_CU: 0,
        CUOC_TCDT_CU: 0,
        CAP_ID_CU: 0,
        DOICAP_CU: '',
        CULY_CU: 0,
        SLOT_PE_CU: '',
        DSLAM: '',
        DSLAM_CU: '',
        TEN_TBI: '',
        TEN_TBI_CU: '',
        HDTB_ID: 0,
        HDTB_CHA_ID: 0,
        THUEBAO_CHA_ID: 0,
        HDTB_CHA_ID_CU: 0,
        THUEBAO_CHA_ID_CU: 0,
        HUONG_KN: '',
        HUONG_KN_CU: '',
        THIETBIDC_ID: 0,
        THIETBIDC_ID_CU: 0,
        MA_PL: '',
        MA_PL_CU: '',
        NHAN_HD: '',
        SITE_ID_CU: '',
        SITE_ID_MOI: '',
        LOAINODE_ID: 0,
        LOAINODE_ID_CU: 0,
        LOAIKENH_ID: 0,
        LOAIKENH_ID_CU: 0,
        TINH_KN_ID: 0,
        TINH_KN_ID_CU: 0,
        KETCUOI_ID_CU: '',
        KETCUOI_ID: '',
        VITRI_CU: '',
        KIEU: 0,
        NGUON_YC: 0,
        VITRI: '',
        CHUQUAN_ID: 0,
        CHUQUANLD_ID: 0,
        CHUQUANLD_ID_CU: 0,
        CHUQUAN_ID_CU: 0,
        TEN_KH: '',
        TEN_KH_CU: '',
        DIACHI_ID: 0,
        DIACHI_ID_CU: 0,
        MA_CRM: '',
        MA_CRM_CU: '',
        MA_VTT_CU: '',
        MA_VTT: '',
        ID_ERP: '',
        ID_ERP_CU: '',
        TOCDO_ID: 0,
        TOCDO_ID_CU: 0,
        VTT_ID_CU: 0,
        VTT_ID: 0,
        DIACHI_KN: '',
        DIACHI_KN_CU: '',
        MA_HD_CU: '',
        MA_HD: ''
      }
      dstdttDBMgwan.push(row)
      return dstdttDBMgwan
    },
    async TaoDuLieu_IMS (THUEBAO_ID, MA_TB) {
      const dstdttDBIMS = []
      const TDTT_DB_ID = await this.getKeys('TDTT_DB_ID')
      const ngay = moment(new Date()).format('DD/MM/YYYY HH:mm:ss')
      const row = {
        TDTT_DB_ID,
        THUEBAO_ID,
        MA_TB,
        TRANGTHAI_ID: 1,
        NHANVIEN_ID: this.nhanVienId,
        DONVI_ID: this.diemChiaCuFilter.diemChiaId,
        NGAY_DK: ngay,
        NGAY_HT: ngay,
        MAY_CN: this.machine,
        NGAY_CN: ngay,
        NGUOI_CN: this.nguoiDungId,
        GHICHU: 'Thay đổi trạm thi công danh bạ theo file',
        HDTB_ID: 0,
        PORT_ID: 0,
        PORT_ID_CU: 0,
        VCI_VPI_ID: 0,
        VCI_VPI_ID_CU: 0,
        BRAS_ID: 0,
        BRAS_ID_CU: 0,
        DSLAM_ID: 0,
        DSLAM_ID_CU: 0,
        MA_LT: '',
        MA_LT_CU: '',
        PASSWORD: '',
        PASSWORD_CU: '',
        TUYENCAP: '',
        LIENTU: '',
        DOICAP_P: 0,
        HOPCAP_P: '',
        TUCAP_P: '',
        LOAICAP_ID: 0,
        CAP_GOC: '',
        CAP_P: '',
        DOICAP_GOC: 0,
        TUCAP_GOC: '',
        CULY: 0,
        TUYENCAP_CU: '',
        LIENTU_CU: '',
        DOICAP_P_CU: 0,
        HOPCAP_P_CU: '',
        TUCAP_P_CU: '',
        LOAICAP_ID_CU: 0,
        CAP_GOC_CU: '',
        CAP_P_CU: '',
        DOICAP_GOC_CU: 0,
        TUCAP_GOC_CU: '',
        CULY_CU: 0,
        KETCUOI_ID_CU: '',
        KETCUOI_ID: '',
        VITRI_CU: '',
        VITRI: '',
        DOICAP_CU: '',
        KIEU: 0,
        NGUON_YC: 0,
        DOICAP: '',
        CHUQUAN_ID: 0,
        CHUQUANLD_ID: 0,
        CHUQUAN_ID_CU: 0,
        CHUQUANLD_ID_CU: 0
      }
      dstdttDBIMS.push(row)
      return dstdttDBIMS
    },
    async TaoDuLieu_TSL (THUEBAO_ID, MA_TB, DAUCUOI_ID) {
      const dsTDTTTSL = []
      const TDTT_DB_ID = await this.getKeys('TDTT_DB_ID')
      const ngay = moment(new Date()).format('DD/MM/YYYY HH:mm:ss')
      const row = {
        TDTT_DB_ID,
        THUEBAO_ID,
        MA_TB,
        DAUCUOI_ID,
        TRANGTHAI_ID: 1,
        NHANVIEN_ID: this.nhanVienId,
        DONVI_ID: this.diemChiaCuFilter.diemChiaId,
        NGAY_DK: ngay,
        NGAY_HT: ngay,
        MAY_CN: this.machine,
        NGAY_CN: ngay,
        NGUOI_CN: this.nguoiDungId,
        GHICHU: 'Thay đổi trạm thi công danh bạ theo file',
        CUOC_TC: 0,
        CUOC_TK: 0,
        CUOC_TBI: 0,
        CUOC_HT: 0,
        CUOC_TKDT: 0,
        CUOC_TCDT: 0,
        TEN_TBI: '',
        BRAS_ID: 0,
        DSLAM_ID: 0,
        PORT_ID: 0,
        VCI_VPI_ID: 0,
        SLOT: '',
        PORT: '',
        TIMESLOT: '',
        LINK: '',
        IP_WAN: '',
        VRF: '',
        TUYENCAP: '',
        LIENTU: '',
        LOAICAP_ID: 0,
        TUCAP_GOC: '',
        DOICAP_GOC: 0,
        CAP_GOC: '',
        TUCAP_P: '',
        HOPCAP_P: '',
        DOICAP_P: 0,
        CAP_P: '',
        CAP_ID: 0,
        DOICAP: 0,
        SERIAL: '',
        CULY: 0,
        CUOC_TC_CU: 0,
        CUOC_TK_CU: 0,
        CUOC_TBI_CU: 0,
        CUOC_HT_CU: 0,
        CUOC_TKDT_CU: 0,
        CUOC_TCDT_CU: 0,
        TEN_TBI_CU: '',
        BRAS_ID_CU: 0,
        DSLAM_ID_CU: 0,
        PORT_ID_CU: 0,
        VCI_VPI_ID_CU: 0,
        SLOT_CU: '',
        PORT_CU: '',
        TIMESLOT_CU: '',
        LINK_CU: '',
        IP_WAN_CU: '',
        VRF_CU: '',
        TUYENCAP_CU: '',
        LIENTU_CU: '',
        LOAICAP_ID_CU: 0,
        TUCAP_GOC_CU: '',
        DOICAP_GOC_CU: 0,
        CAP_GOC_CU: '',
        TUCAP_P_CU: '',
        HOPCAP_P_CU: '',
        DOICAP_P_CU: 0,
        CAP_P_CU: '',
        CAP_ID_CU: 0,
        DOICAP_CU: 0,
        SERIAL_CU: '',
        CULY_CU: 0,
        TBIDC_DAU_ID: 0,
        TBIDC_DAU_ID_CU: 0,
        TBIDC_CUOI_ID: 0,
        TBIDC_CUOI_ID_CU: 0,
        HDTB_ID: 0,
        MA_PL: '',
        MA_PL_CU: '',
        NHAN_HD: '',
        KETCUOI_ID_CU: '',
        KETCUOI_ID: '',
        VITRI_CU: '',
        VITRI: '',
        KIEU: 0,
        NGUON_YC: 0,
        CHUQUANLD_ID: 0,
        CHUQUAN_ID: 0,
        CHUQUAN_ID_CU: 0,
        CHUQUANLD_ID_CU: 0,
        TEN_KH_CU: '',
        TEN_KH: '',
        DIACHI_ID_CU: 0,
        DIACHI_ID: 0,
        TOCDO_ID_CU: 0,
        TOCDO_ID: 0,
        MA_VTT_CU: '',
        MA_VTT: '',
        VTT_ID: 0,
        VTT_ID_CU: 0,
        MA_HD: '',
        MA_HD_CU: ''
      }
      dsTDTTTSL.push(row)
      return dsTDTTTSL
    },
    async getKeys (keyname) {
      try {
        const response = await api.getKeys(this.axios, { keyname, numblocksize: 0, numretry: 0 })
        const data = await response.data
        return data.error_code === 'BSS-00000000' && data.error === '200' ? data.data : ''
      } catch (e) {
        this.$root.$toast.error('Không thể lấy key')
        return ''
      }
    },
    async capNhatLogDbtbDv (thuebaoIds, donViCuId, donViMoiId) {
      try {
        const response = await api.capNhatLogDbtbDv(
          this.axios,
          { vthuebao_id_list: JSON.stringify(thuebaoIds), vdonvi_id_cu: donViCuId, vdonvi_id_moi: donViMoiId }
        )
        const data = await response.data
        if (!(data.error_code === 'BSS-00000000' && data.error === '200')) {
          this.$root.$toast.error('Cập nhật log điểm chia không thành công')
        }
      } catch (e) {
        this.$root.$toast.error('Không thể cập nhật log điểm chia')
      }
    },
    async capNhatDiemChia (
      { js_tdtt_db_cd, js_tdtt_db_adsl, js_tdtt_db_mgwan, js_tdtt_db_ims, js_tdtt_db_tsl, js_dbtb_dv }
    ) {
      try {
        const response = await api.capNhatDiemChia(
          this.axios,
          {
            js_tdtt_db_cd,
            js_tdtt_db_adsl,
            js_tdtt_db_mgwan,
            js_tdtt_db_ims,
            js_tdtt_db_tsl,
            js_dbtb_dv
          }
        )
        const data = await response.data
        return JSON.parse(data.data)
      } catch (e) {
        this.$root.$toast.error('Không thể cập nhật điểm chia')
        return null
      }
    },
    async tsbtnViewHistory_Click () {
      if (this.danhSachThueBao.dataSources.length === 0) {
        this.$refs.viewHistoryRef.initData([])
        this.$refs.viewHistoryPopupRef.show()
      } else {
        let historyDataSources = []
        this.loading(true)
        try {
          const thueBaoIds = this.danhSachThueBao
            .selectedItems
            .map(elm => ({ THUEBAO_ID: elm.thuebao_id }))
          const data = await this.getLogCapNhatDiemChia(thueBaoIds)
          if (data.length > 0) {
            data.forEach(elm => {
              elm.ngay_cn = moment(elm.ngay_cn).format('DD/MM/YYYY HH:mm:ss')
              return elm
            })
          }
          historyDataSources = [...data]
        } catch (e) {
          historyDataSources = []
        } finally {
          this.loading(false)
          this.$refs.viewHistoryRef.initData(historyDataSources)
          this.$refs.viewHistoryPopupRef.show()
        }
      }
    },
    onSelectedItemsChanged (items) {
      this.danhSachThueBao.selectedItems = [...items]
    },
    async getLogCapNhatDiemChia (thuebaoIds) {
      try {
        const response = await api.getLogCapNhatDiemChia(this.axios, { thuebao_id_list: JSON.stringify(thuebaoIds) })
        const data = await response.data
        return data.error_code === 'BSS-00000000' && data.error === '200' ? data.data : []
      } catch (e) {
        this.$root.$toast.error('Không thể lấy thông tin lịch sử')
        return []
      }
    },
    async cboDaiVT_SelectedValueChanged () {
      if (this.diemChiaCuFilter.cboDaiVT.length > 0) {
        if (this.diemChiaCuFilter.donViQLId) {
          this.loading(true)
          try {
            this.diemChiaCuFilter.cboVetinhAdsl = await this.getDanhSachDiemChia(this.diemChiaCuFilter.donViQLId)
          } catch (e) {
            this.diemChiaCuFilter.cboVetinhAdsl = []
          } finally {
            this.loading(false)
          }
        } else {
          this.diemChiaCuFilter.cboVetinhAdsl = []
        }
      } else {
        this.diemChiaCuFilter.cboVetinhAdsl = []
      }
    },
    async getDanhSachDiemChia (donViId) {
      try {
        const response = await api.getDanhSachDiemChia(
          this.axios,
          { p_donvi_id: donViId, p_loaidv_id: LOAI_DV.TRAM_VT }
        )
        const data = await response.data
        return data.error_code === 'BSS-00000000' && data.error === '200' ? data.data : []
      } catch (e) {
        this.$root.$toast.error('Lỗi khi load chức năng !')
        return []
      }
    },
    async cboDaiVTMoi_SelectedValueChanged () {
      if (this.diemChiaMoiFilter.cboDaiVTMoi.length > 0) {
        if (this.diemChiaMoiFilter.donViQLId) {
          this.loading(true)
          try {
            this.diemChiaMoiFilter.cboVeTinhMoi = await this.getDanhSachDiemChia(this.diemChiaMoiFilter.donViQLId)
          } catch (e) {
            this.diemChiaMoiFilter.cboVeTinhMoi = []
          } finally {
            this.loading(false)
          }
        } else {
          this.diemChiaMoiFilter.cboVeTinhMoi = []
        }
      } else {
        this.diemChiaMoiFilter.cboVeTinhMoi = []
      }
    },
    cboDichVuVT_SelectedValueChanged () {
      if (this.diemChiaCuFilter.dichVuVTId) {
        this.danhSachThueBao
          .headers
          .find(elm => elm.fieldName === 'gcol_Diem')
          .visible = this.diemChiaCuFilter.dichVuVTId === DichVuVienThong.TSL
      }
    }
  }
}
</script>
