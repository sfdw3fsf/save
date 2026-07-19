/* eslint-disable eqeqeq */
<template src='./ChoosePreapaidNumber.html'></template>
<style  src='./ChoosePreapaidNumber.scss'></style>
<script>
// import gridView from '@/modules/complaint/ResovleComplaintAfterSales/components/gridview.vue'
// import gridView from '@/modules/prepaid/ChoosePreapaidNumber/components/gridview.vue'
// import treegridView from '@/modules/complaint/ResovleComplaintAfterSales/components/treegridview.vue'
import nhapDSSoMay from '@/modules/prepaid/ChoosePreapaidNumber/components/NhapDSSoMay/NhapDSSoMay.vue'
import breadcrumb from '@/components/breadcrumb'
import { required, minLength } from 'vuelidate/lib/validators'
import 'flatpickr/dist/plugins/monthSelect/style.css'
import bssFlatPicker from '@/components/BssDatePicker'
import { LoaiHinhTB } from '@/const/enums/index.js'
import moment from 'moment'
import api from './Api'

export default {
  components: {
    breadcrumb,
    // gridView,
    // treegridView,
    bssFlatPicker,
    moment,
    nhapDSSoMay,
    required,
    minLength
  },
  name: 'ChoosePreapaidNumber',
  data() {
    return {
      popupComponent: null,
      header: {
        title: 'Chọn số trả trước',
        list: [
          { name: ' Phát triển thuê bao', link: { name: 'Ui.cards' } },
          {
            name: ' Chọn số trả trước',
            link: { name: 'Ui.buttons' }
          }
        ]
      },
      Loading: false,
      KhoiTaoThueBaoTag: '',
      // frmKhoiTaoTB
      kieu: 0,
      chonso: false, // vuth update 0210
      sotb_goc: '',
      khoid: null,
      xacnhan: false,
      loaitb: '',
      pmeg: '',
      kho_id: '',
      labelControl3: false,
      txtMaXT: true,
      // end frmKhoiTaoTB
      updateui: false,
      target: '#app .main-wrapper', //  .main-wrapper-payment
      targetTop: '#app', // document.querySelector('body'),.main-wrapper
      MaXacThucFunc: '',
      animationSettings: { effect: 'Zoom' },
      position: { X: 'center', Y: 'top' },
      txtMaCaptcha: '',
      vkieuform: -1,
      xTabKhoTinh: true,
      xTabKhoTQ: true,
      xTabKhoTQ_TraSau: false,
      xtraTabPage2: true,
      xTabKhoTQ_Text: 'Danh sách kho số toàn quốc',
      xTabKhoTinh_Text: 'Danh sách kho số',
      CboKieuSo_ReadOnly: false,
      cboTrangThai_ReadOnly: false,
      txtMaXacThuc_ReadOnly: true,
      so_tb_kt: '',
      KHO_TINH_ID: '14',
      KHO_TQ_ID: '1',
      so_tb_chon: '',
      so_sim_chon: '',
      goicuoc_id_chon: '',
      khoso_dangchon: 0, // 0 kho toanquoc, 1 kho tinh vuth update fix 1609
      SearchResultChanged: false,
      TabKhoSoIndex: 0,
      KhoSoDangChon: 0,
      Dathuchien: false,
      pageIndexToDisable: 1,
      cboPrefix: '',
      CboKieuSo: 0,
      CboKieuSo_Enabled: true,
      cboKhoSo: 1,
      cboKhoSo_Enabled: true,
      colBtnChonSo: true,
      cboTrangThai: 0,
      tsbtnChonSo: true,
      btnRefresh: true,
      btnLayTT: true,
      btnNhapMoi: true,
      btnXoaTB: true,
      btnCapNhat: true,
      txtPrefix: '',
      txtMaXacThuc: '',
      txtNhapSo: '',
      DmDauSo: [],
      DmKieuSo: [],
      DmKhoSo: [],
      DmTrangThaiKhoSo: [],
      pageNumber: 1,
      columns: '',
      KhoSoVttTitle: 'Danh sách kho số trả trước viễn thông tỉnh',
      grcDSKhoSo: {
        headers: [
          { fieldName: 'row_stt', headerText: 'STT', width: '70', textAlign: 'Center' },
          { fieldName: 'so_tb', headerText: 'Số máy', cssClass: 'soTbCol', width: 120 },
          { fieldName: 'status', headerText: 'Tình trạng', cssClass: 'statusCol', width: 90 },
          { fieldName: 'ten_kieuso', headerText: 'Kiểu số' },
          { fieldName: 'cuocthang_toithieu', headerText: 'Cước tháng cam kết', cssClass: 'cuocThangCol', textAlign: 'Right' },
          { fieldName: 'thoigian_sudung', headerText: 'Thời gian cam kết', textAlign: 'Right' },
          { fieldName: 'datcoc', headerText: 'Tiền hoàn trả HĐ tháng', textAlign: 'Right' },
          { fieldName: 'status_tt_img', headerText: 'Trạng thái', textAlign: 'Center', allowHtml: true, width: 100, cssClass: 'trangthaiCol' }
        ],
        data: [],
        selectedItems: [],
        currentRow: {}
      },
      page_num: 0,
      page_sz: 10,
      rowIndex: 0,
      totalRecords: 100,
      isPageNumChanging: false,
      gcKHOTQTraSau: {
        headers: [
          { fieldName: 'row_stt', headerText: 'STT', width: '70', textAlign: 'Center' },
          { fieldName: 'so_tb', headerText: 'Số máy', cssClass: 'soTbCol', width: 120 },
          { fieldName: 'status', headerText: 'Tình trạng', cssClass: 'statusCol' },
          { fieldName: 'ten_kieuso', headerText: 'Kiểu số' },
          { fieldName: 'cuocthang_toithieu', headerText: 'Cước tháng cam kết', cssClass: 'cuocThangCol', textAlign: 'Right' },
          { fieldName: 'thoigian_sudung', headerText: 'Thời gian cam kết', textAlign: 'Right' },
          { fieldName: 'datcoc', headerText: 'Tiền hoàn trả HĐ tháng', textAlign: 'Right' },
          { fieldName: 'status_tt_img', headerText: 'Trạng thái', textAlign: 'Center', allowHtml: true, width: 100, cssClass: 'trangthaiCol' }
        ],
        data: [],
        selectedItems: [],
        currentRow: {}
      },
      grcDSAllKho: {
        headers: [
          { fieldName: 'row_stt', headerText: 'STT', width: '70', textAlign: 'Center' },
          { fieldName: 'MSISDN', headerText: 'Số máy', width: 120 },
          { fieldName: 'CUOC_CK', headerText: 'Cước CK', textAlign: 'Right' },
          { fieldName: 'THOIGIAN_CK', headerText: 'Thời gian CK', textAlign: 'Right' },
          { fieldName: 'MSI_KHO', headerText: 'Kho', cssClass: 'khoCol' }
        ],
        data: [],
        selectedItems: [],
        currentRow: {}
      },
      KhoSoToanQuoc: [],
      KhoSoVTT: [],
      grcDS_ChuaDK: {
        headers: [
          { fieldName: 'so_tb', headerText: 'Số máy' },
          { fieldName: 'ten_kieuso', headerText: 'Kiểu số' },
          { fieldName: 'cuocthang_toithieu', headerText: 'Cước tháng cam kết' }
        ],
        data: [],
        selectedItems: [],
        currentRow: {}
      },
      commandColumn: {
        show: true,
        headerText: 'Thao tác',
        width: 100
      },
      commandColumn1: {
        show: true,
        headerText: ' ',
        width: 50,
        firstColumn: true
      },
      commandColumn2: {
        show: true,
        headerText: ' ',
        width: 50,
        firstColumn: true
      },
      commandsKT: [
        {
          headerText: 'Thao tác',
          cssClass: 'btnChonSo', // btnDowLoad
          name: 'chonso',
          text: '<a href="javascript:void(0);" class="act f20 text-main nc-icon-glyph  arrows-1_small-triangle-down" ></a>',
          title: 'Chọn số'
        }
      ],
      commands1: [
        {
          headerText: 'Thao tác',
          cssClass: 'btnChonSo', // btnDowLoad
          name: 'chonso',
          text: '<a href="javascript:void(0);" class="act f20 text-main nc-icon-glyph  arrows-1_small-triangle-down" ></a>',
          title: 'Chọn số'
        }
      ],
      commands2: [
        {
          headerText: 'Thao tác',
          cssClass: 'btnChonSo', // btnKhoiTao_TS
          name: 'chonso',
          text: '<a href="javascript:void(0);" class="act f20 text-main nc-icon-glyph ui-1_circle-add" ></a>',
          title: 'Chọn số'
        }
      ],
      commands3: [
        {
          headerText: 'Thao tác',
          cssClass: 'btnChonSo', // repositoryItemButtonEdit3
          name: 'chonso',
          text: '<a href="javascript:void(0);" class="act f20 text-main nc-icon-glyph  arrows-1_small-triangle-down" ></a>',
          title: 'Chọn số'
        }
      ],
      commands: [
        {
          headerText: 'Thao tác',
          cssClass: 'btnKhoiTao',
          name: 'edit',
          text: '<a href="javascript:void(0);" class="act f20 text-main nc-icon-glyph ui-1_circle-add" ></a>',
          title: 'Khởi tạo thuê bao'
        }
      ],
      grcDS_DaDK: {
        headers: [
          { fieldName: 'STT', headerText: 'STT', width: 90, textAlign: 'Center' },
          { fieldName: 'SO_TB', headerText: 'Số máy', width: 120 },
          { fieldName: 'NGAYTH', headerText: 'Thời gian ĐK' },
          { fieldName: 'THOIGIAN_CON', headerText: 'Hiệu lực (phút)', width: 160 },
          { fieldName: 'GHI_CHU', headerText: 'Ghi chú' }
        ],
        data: [],
        currentRow: {},
        fcol: true,
        lastcoltemplate: 3
      },
      timeDifference: 0,
      KhoiTao: {
        SoMay: '',
        MSIN: '',
        LoaiMSIN: 128,
        LoaiTB: '',
        GhiChu: '',
        MaXacThuc: '',//'00000000'
      },
      KhoiTaoLoaiTB: '',
      DsLoaiTB: [],
      grcDSKhoiTao: {
        headers: [
          { fieldName: 'STT', headerText: 'STT', width: 90, textAlign: 'Center' },
          { fieldName: 'SOMAY', headerText: 'Số máy', width: 120 },
          { fieldName: 'SIMCARD', headerText: 'Số Sim', width: 150 },
          { fieldName: 'NGAY_CN', headerText: 'Ngày khởi tạo' },
          { fieldName: 'GHICHU', headerText: 'Ghi chú' }
        ],
        data: [],
        selectedItems: [],
        currentRow: {},
        fcol: false
      },
      KhoSo: [
        { id: 1, text: 'Trả trước' },
        { id: 2, text: 'Trả sau' }
      ],
      DS_KHOITAO_CHONSO_TS: [
        {
          id: '-1',
          text: 'Số thuê bao và số SIM không nằm cùng trên tổng đài nên không khởi tạo được. Bạn hãy chọn lại'
        },
        { id: '4', text: 'Số thuê bao này đang chờ khởi tạo. Hãy chọn thuê bao khác' },
        { id: '5', text: 'Số thuê bào này đang chờ khởi tạo hãy chọn thuê bao khác' },
        { id: '6', text: 'Số MSIN  đã gán cho thuê bao khác' },
        { id: '7', text: 'Không có thông tin người dùng' },
        { id: '8', text: 'Mã xác thực không đúng' },
        { id: '9', text: 'Không có thông tin mã tỉnh người dùng' },
        { id: '10', text: 'Chỉ khởi tạo được với loại là Post' },
        { id: '11', text: 'Thuê bao nằm trong dải số chỉ được khởi tạo là Mobibroadband trả sau' },
        { id: '12', text: 'User chưa được phân quyền khởi tạo dải số này. Yêu cầu liên hệ với ADMIN tỉnh.' },
        { id: '19', text: 'Loại thuê bao không được phép khởi tạo' },
        { id: '20', text: 'Lỗi kích hoạt thuê bao trả trước qua Webservice' }
      ],
      DS_KHOITAO_CHONSO_TT: [
        { id: '4', text: 'Số thuê bao này đang chờ khởi tạo. Hãy chọn thuê bao khác' },
        { id: '6', text: 'Số MSIN  đã gán cho thuê bao khác' },
        { id: '5', text: 'Số thuê bào này đang chờ khởi tạo hãy chọn thuê bao khác' },
        { id: '8', text: 'Mã xác thực không đúng' },
        { id: '20', text: 'User khởi tạo không đúng' },
        { id: '21', text: 'số thuê bao không được hòa mạng trả trước' },
        { id: '10', text: 'Bạn chỉ khởi tạo được với loại là Post' },
        {
          id: '11',
          text: 'Có lỗi khi khởi tạo thuê bao, bạn vui lòng liên hệ với bộ phận hỗ trợ kỹ thuật VNP về vấn đề này'
        },
        {
          id: '-1',
          text: 'Số thuê bao và số SIM không nằm cùng trên tổng đài nên không khởi tạo được. Bạn hãy chọn lại'
        },
        { id: '2003', text: ' Số sim không đựoc phép khởi tạo với loại sim tương ứng. Bạn hãy chọn lại' }
      ]
    }
  },
  computed: {
    LoaihinhTB() {
      if (this.vkieuform == 0) {
        return LoaiHinhTB.TRA_TRUOC
      }
      return LoaiHinhTB.TRA_SAU
    },
    vprefix_all() {
      if (this.cboPrefix == 0 || this.cboPrefix == '' || this.cboPrefix == null) {
        return -1
      }
      return '84' + this.trim(this.cboPrefix.trimStart(), '0')
    },
    vprefix() {
      if (this.cboPrefix == '0' || this.cboPrefix == '' || this.cboPrefix == null) {
        return -1
      }
      return this.cboPrefix
    },
    vkho_id() {
      if (this.cboKhoSo == '' || this.cboKhoSo == null) {
        return -1
      }
      return this.cboKhoSo
    },
    vkieusso_id() {
      console.log('vkieusso_id', this.CboKieuSo)
      if (this.DmKieuSo == null || this.DmKieuSo.length <= 0) {
        return '-1'
      }
      if (this.CboKieuSo == 0) return ''
      return this.CboKieuSo
    },
    vma_tb() {
      return this.txtPrefix
    },
    vma_xacthuc() {
      return this.txtMaXacThuc
    },
    vstatus() {
      if (this.cboTrangThai == '' || this.cboTrangThai == '0' || this.cboTrangThai == null) {
        return -1
      }
      return this.cboTrangThai
    },
    vsomay_khotinh() {
      if (this.grcDSAllKho.currentRow != null) {
        return this.grcDSAllKho.currentRow.msisdn
      }
      return ''
    },
    vsomay_khochung_tinh() {
      if (this.grcDSKhoSo.currentRow != null) {
        return this.grcDSKhoSo.currentRow.so_tb
      }
      return ''
    },
    vsomay_toanquoc_trasau() {
      if (this.gcKHOTQTraSau.currentRow != null) {
        return this.gcKHOTQTraSau.currentRow.so_tb
      }
      return ''
    },
    vkhoid_dadangky() {
      if (this.grcDS_DaDK.currentRow != null) {
        return this.grcDS_DaDK.currentRow['kho_id']
      }
      return ''
    },
    vthuebao_dakhoitao() {
      if (this.grcDSKhoiTao.currentRow != null) {
        return this.grcDSKhoiTao.currentRow['SOMAY']
      }
      return '-1'
    },
    vthuebao_chon() {
      if (this.grcDS_DaDK.currentRow != null) {
        return this.grcDS_DaDK.currentRow['SO_TB']
      }
      return ''
    },
    vgoicuoc_id() {
      if (this.grcDSKhoiTao.currentRow != null) {
        return this.grcDSKhoiTao.currentRow['GOICUOC_ID']
      }
      return '-1'
    },
    vsimcard() {
      if (this.grcDSKhoiTao.currentRow != null) {
        return this.grcDSKhoiTao.currentRow['SIMCARD']
      }
      return '-1'
    }
  },
  watch: {
    cboPrefix: async function (val) {
      if (val == '-1') return
      if (this.vkieuform == 0) {
        let dt = await this.tt_1_kieuso(this.vprefix_all)
        if (dt == null || dt.length == 0) {
          this.ShowError('Không lấy được dữ liệu kiểu số từ VNP')
          return
        }
        dt.unshift({ TEN_KIEUSO: 'Không chọn', KIEUSO_ID: 0 })
        this.DmKieuSo = dt.map((x) => ({ id: x.KIEUSO_ID, text: x.TEN_KIEUSO }))
        this.CboKieuSo = 0
        /*
                dt.AcceptChanges();

                dv.Sort = "KIEUSO_ID";
                dv.Table.SetColumnsOrder("KIEUSO_ID", "TEN_KIEUSO");
                bts.HT_DS_GRIDLOOKUP(CboKieuSo, dv.Table, "TEN_KIEUSO", "KIEUSO_ID", true, 2, 400);
                */
      }
      if (this.vkieuform == 1) {
        let dt = await this.tt_1_kieuso(this.vprefix_all)
        if (dt == null || dt.length == 0) {
          this.ShowError('Không lấy được dữ liệu kiểu số từ VNP')
          return
        }
        dt.unshift({ TEN_KIEUSO: 'Không chọn', KIEUSO_ID: 0 })
        this.DmKieuSo = dt.map((x) => ({ id: x.KIEUSO_ID, text: x.TEN_KIEUSO }))
        this.CboKieuSo = 0
        /*
                dt.AcceptChanges();
                DataView dv = dt.DefaultView;
                dv.Sort = "KIEUSO_ID";
                dv.Table.SetColumnsOrder("KIEUSO_ID", "TEN_KIEUSO");
                bts.HT_DS_GRIDLOOKUP(CboKieuSo, dv.Table, "TEN_KIEUSO", "KIEUSO_ID", true, 2, 400);
                */
      }
    },
    updateui: function (val, oldVal) {
      this.$forceUpdate()
    },
    Loading: function (val, old) {
      this.$root.showLoading(val)
    },
    cboKhoSo: async function (val) {
      try {
        let _vkho_id = val
        this.txtMaXacThuc = ''
        if (_vkho_id == 2) {
          this.txtMaXacThuc_ReadOnly = false
          // txtMaXacThuc.BackColor = Color.LightYellow;
        } else {
          this.txtMaXacThuc_ReadOnly = true
          // txtMaXacThuc.ResetBackColor();
          // txtMaXacThuc.Properties.ReadOnly = true;
        }
        // grcDSKhoSo.DataSource = null;
        // grcDSAllKho.DataSource = null;
        // this.grcDSKhoSo.data = []
        this.grcDSKhoSo.data = []
        this.grcDSAllKho.data = []
        if (_vkho_id == this.KHO_TINH_ID) {
          this.CboKieuSo_ReadOnly = false
          this.cboTrangThai_ReadOnly = true
          // splitContainerControl1.PanelVisibility = SplitPanelVisibility.Both; // vuth update 0710
          await this.NAP_DS_SODANGKY()
          this.xTabKhoTQ = true
          if (this.vkieuform == 1) {
            // vuth update fix 1609 : neu goi tu tra sau thi giu nguyen logic,tra truoc cho hien thi het;
            this.xTabKhoTinh = true // vuth update ts 0210
            this.xTabKhoTQ = true
            this.xTabKhoTQ_TraSau = true
            /* var col = tableLayoutPanel2.ColumnStyles;
                        col[0].Width = 0;
                        col[1].SizeType = SizeType.Percent;
                        col[1].Width = 100; */
            // xTabKhoSo.SelectedTabPageIndex = 0;
            this.TabKhoSoIndex = 0
          } else {
            this.xTabKhoTinh = true
          }
        } else {
          if (this.vkieuform == 1) {
            await this.NAP_DS_SODANGKY()
            if (_vkho_id != this.KHO_TQ_ID) {
              // ko phai kho toan quoc
              this.xTabKhoTinh = true // vuth update ts 0210
              this.xTabKhoTQ = true
              this.xTabKhoTQ_TraSau = true
              // splitContainerControl1.PanelVisibility = SplitPanelVisibility.Panel1;
              // vuth update ts 0210
              this.CboKieuSo_ReadOnly = true
              // this.xTabKhoSo.SelectedTabPageIndex = 1;
              this.TabKhoSoIndex = 1
              this.cboTrangThai_ReadOnly = true
            }
            if (_vkho_id == this.KHO_TQ_ID) {
              // kho toan quoc
              this.xTabKhoTinh = true // vuth update ts 0210
              this.xTabKhoTQ = true
              this.xTabKhoTQ_TraSau = true
              this.TabKhoSoIndex = 2
              this.CboKieuSo_ReadOnly = false
              this.cboTrangThai_ReadOnly = false
            }
            // splitContainerControl1.PanelVisibility = SplitPanelVisibility.Both; // vuth update fix 0710
          } else {
            this.xTabKhoTinh = true
            this.xTabKhoTQ = true
            // splitContainerControl1.PanelVisibility = SplitPanelVisibility.Both;
            if (parseInt(_vkho_id) == 1) {
              // kho toàn quốc vuth update fix 1709
              // xTabKhoSo.SelectedTabPageIndex = 0;
              this.TabKhoSoIndex = 0
              this.cboTrangThai_ReadOnly = false
            }
            if (parseInt(_vkho_id) == 4) {
              // kho tỉnh
              // xTabKhoSo.SelectedTabPageIndex = 1;
              this.TabKhoSoIndex = 1
              this.cboTrangThai_ReadOnly = true
            }
          }
        }
      } catch (err) {
        console.log(err)
      }
    },
    TabKhoSoIndex: function (val) {
      this.xTabKhoSo_SelectedPageChanged(val)
    }
  },
  created() {
    this.$root.$on('bv::modal::shown', (bvEvent, modalId) => {
      document.getElementsByClassName('modal-content').forEach(function (v) {
        v.removeAttribute('tabindex')
      })
      if (modalId == 'my-modal-khoi-tao') {
        try {
          this.$refs.refKhoiTaoMSIN.focus()
        } catch (err) {}
      }
    })
    this.$root.$on('bv::modal::hidden', (bvEvent, modalId) => {
      //this.ValidateModal(bvEvent, modalId)
      if (modalId == 'my-modal-khoi-tao') {
        this.KhoiTao.MSIN = ''
      }
    })
    if (this.$route.name.toLowerCase() == 'choosepreapaidnumber'.toLowerCase()) {
      this.Tag = '0' // Tra truoc
    } else if (this.$route.name.toLowerCase() == 'chosenumberinprovincewarehouse'.toLowerCase()) {
      this.Tag = '1' // Tra sau
    } else if (this.$route.name.toLowerCase() == '') {
      // alert(this.$route.name)
      //this.$router.push('choosepreapaidnumber')
      this.Tag = '0' //  Tra truoc
    } else {
      //alert(this.$route.name)
    }
    if (this.$route.query.tag != null && this.$route.params.tag != '' && this.$route.query.tag.length > 0) {
      this.Tag = this.$route.query.tag
    }

    this.vkieuform = parseInt(this.Tag)
    if (this.vkieuform == 1) {
      this.xTabKhoTQ_Text = 'Danh sách kho số chung viễn thông tỉnh'
      this.xTabKhoTinh_Text = 'Danh sách kho số khác'
      this.xTabKhoTQ = true
      this.commandColumn1.show = true
    } else {
      this.xTabKhoTinh_Text = ' Danh sách kho số trả trước viễn thông tỉnh'
      this.xTabKhoTinh = true
      this.commandColumn1.show = false
    }
    this.Loading = true
    // this.getDmDauSo(),  this.getDmTrangThaiKhoSo(), this.getDmKieuSo(this.cboPrefix), this.DanhSachKhoSoKhoiTaoUser(), this.DanhSachKhoSoUser()
    Promise.all([this.THIETLAP()])
      .then((responses) => {
        this.Loading = false
      })
      .catch((err) => {
        this.Loading = false
        console.log('Promise.all', err)
      })
      .finally(() => {
        this.Loading = false
      })
  },
  async mounted() {
    this.check_ccbs()
  },
  validations: {
    KhoiTao: {
      MSIN: {
        required,
        minLength: minLength(10)
      }
    }
  },
  methods: {
    cTemplate: function (data) {
      return 'abc'
    },
    commandClicked1: async function (cmdName, data) {
      // console.log('commandClicked1', 'cmdName:', cmdName, 'data:', data)
      if (this.TabKhoSoIndex == 0) {
        //await this.LAPHD_CHONSO()
        await this.btnDowLoad_ButtonClick()
      } else if (this.TabKhoSoIndex == 1) {
        await this.btnKhoiTao_TS_ButtonClick(data)
      } else if (this.TabKhoSoIndex == 2) {
        //await this.KHOTQ_CHONSO_TS()
        await this.repositoryItemButtonEdit3_ButtonClick()
      } else {
        console.log('next')
      }
      return false
    },
    async repositoryItemButtonEdit3_ButtonClick() {
      await this.KHOTQ_CHONSO_TS()
      return false
    },
    async btnChonSo_KT_ButtonClick() {
      console.log('call btnChonSo_KT_ButtonClick')
      if (this.vthuebao_dakhoitao != null && this.vthuebao_dakhoitao.length == 11) {
        // vuth update fix 1609
        if (this.vthuebao_dakhoitao.startsWith('84')) {
          this.so_tb_chon = this.vthuebao_dakhoitao.substring(2)
        }
        // so_tb_chon = Regex.Replace(vthuebao_dakhoitao, "^84", "");
      } else {
        this.so_tb_chon = this.vthuebao_dakhoitao
      }
      this.so_sim_chon = this.vsimcard
      this.goicuoc_id_chon = this.vgoicuoc_id
      if (this.so_tb_chon != '-1' && this.so_sim_chon != '-1') {
        this.xacnhan = true
        //this.Close()
      } else {
        if (this.so_tb_chon != '-1' || this.so_tb_chon != '') {
          this.ShowError('Không lấy được số máy đã chọn')
          return
        }
        if (this.so_sim_chon != '-1' || this.so_sim_chon != '') {
          this.ShowError('Không lấy được sim card của số máy')
        }
      }
    },
    async btnChon_ButtonClick() {
      console.log('call btnChon_ButtonClick')
      return false
    },
    commandClicked2: async function (cmdName, data) {
      await this.btnChonSo_KT_ButtonClick()
      return false
    },
    commandClicked: async function (cmdName, data) {
      // console.log('commandClicked', 'cmdName:', cmdName, 'data:', data)
      try {
        this.so_tb_kt = data.SO_TB
        this.chonso = false
        data.SO_TB = data.SO_TB
        if (data.SO_TB == null || data.SO_TB == '') {
          this.xacnhan = false
          this.ShowError('Không lấy được số máy đã chọn để khởi tạo')
        }
        this.kieu = this.vkieuform
        if (this.vkieuform == 0) {
          this.chonso = true
        }
        this.sotb_goc = data.SO_TB
        this.KhoiTao.SoMay = this.sotb_goc.substring(2)
        if (this.vkieuform == 1) {
          this.khoid = this.vkho_id
        }
        await this.NAP_THONGTIN()
      } catch (err) {
        console.log('commandClicked', err)
      } finally {
        this.KhoiTaoThueBaoTag = 'btnChon_ButtonClick'
        this.ShowKhoiTaoThueBao(data)
      }
      return false
    },
    grcDS_ChuaDK_selectedItems: function (items) {
      this.grcDS_ChuaDK.selectedItems = items
    },
    grcDSKhoSo_selectedItems: function (items) {
      this.grcDSKhoSo.selectedItems = items
    },
    prefix_chonso_kh_dd: async function () {
      let loaihinh = -1
      if (this.vkieuform == 1) loaihinh = LoaiHinhTB.DIDONGTRASAU
      if (this.vkieuform == 0) loaihinh = LoaiHinhTB.DIDONGTRATRUOC
      let input = { LOAITB_ID: loaihinh }
      let data = this.GetData(await api.prefix_chonso_kh_dd(this.axios, input))
      console.log('prefix_chonso_kh_dd', input, data)
      return data
    },
    getDmDauSo: async function () {
      let loaihinh = -1
      if (this.vkieuform == '1') loaihinh = LoaiHinhTB.DIDONGTRASAU
      if (this.vkieuform == '0') loaihinh = LoaiHinhTB.DIDONGTRATRUOC
      let input = { LOAITB_ID: loaihinh }
      let data = this.GetData(await api.prefix_chonso_kh_dd(this.axios, input))
      this.DmDauSo = data.map((x) => ({ id: x.VALUE, text: x.VALUE_ == null ? '' : x.VALUE_ }))
      this.cboPrefix = this.DmDauSo[0].id
    },
    getDmKieuSo: async function (dauso) {
      var input = {
        dauso: dauso
      }
      let data = this.GetData(await api.getDmKieuSo(this.axios, input))
      this.DmKieuSo = data.map((x) => ({ id: x.kieuso_id, text: x.ten_kieuso == null ? '' : x.ten_kieuso }))
    },
    getDsKhoSo: async function () {
      return
      var input = {
        tra_sau: this.vkieuform
      }
      this.DmKhoSo = this.GetData(await api.getDsKhoSo(this.axios, input))
      if (this.DmKhoSo.length > 0) {
        // fix theo nghiep vu trong winform
        if (this.vkieuform == 1) {
          this.DmKhoSo = this.DmKhoSo.filter((x) => x.kho_id != 4)
        } else {
          this.DmKhoSo = this.DmKhoSo.filter((x) => x.kho_id == 1 || x.kho_id == 14)
        }
      } else {
        this.ShowAlert(`Không lấy được ds kho số !`)
      }
    },
    getDmTrangThaiKhoSo: async function () {
      let data = [
        { ID: 0, NAME: 'Không chọn' },
        { ID: 1, NAME: 'Đã đăng ký' },
        { ID: 2, NAME: 'Chưa đăng ký' }
      ]
      try {
        let data = await this.tt_1_trangthai()
        if (data != null) {
          data.unshift({ ID: 0, NAME: 'Không chọn' })
          this.DmTrangThaiKhoSo = data
            .map((x) => ({
              id: x.ID,
              text: x.NAME == null ? '' : x.NAME
            }))
            .sort((a, b) => parseInt(a.id) - parseInt(b.id))
          this.cboTrangThai = '0'
        } else {
          this.DmTrangThaiKhoSo = data
        }
      } catch (err) {
        this.DmTrangThaiKhoSo = data
      }

      /*
      var input = {}
      let data = this.GetData(await api.getDmTrangThaiKhoSo(this.axios, input))
      this.DmTrangThaiKhoSo = data.map((x) => ({ id: x.id, text: x.name == null ? '' : x.name })) // this.GetData(await api.getDmTrangThaiKhoSo(this.axios, input))
      */
    },
    GetData: function (response) {
      if (response.data.error === 200 || response.data.error === '200') {
        if (response.data.error_code == 'BSS-00000000') {
          return response.data.data
        } else {
          console.log(response.data.message)
          // this.ShowError(response.data.message)
          return []
        }
      } else {
        console.log(response.data.error_code)
        // this.ShowError(response.data.error_code)
      }
      return []
    },
    GetDataCCBS: function (response) {
      if (response.data.error === 200 || response.data.error === '200') {
        if (response.data.error_code == 'BSS-00000000') {
          return response.data.data
        } else {
          return response.data.message
        }
      } else {
        console.log(response.data.error_code)
        this.ShowError(response.data.error_code)
      }
      return null
    },
    tt_1_khoitao_chonso_tratruoc: async function () {
      console.log('call tt_1_khoitao_chonso_tratruoc')
      try {
        let ma_nhanvien_ccbs = this.$root.token.getMaCCBS()
        let ma_tinh = this.$root.token.getMaTinh()
        let userId = this.$root.token.getNhanVienID()
        let ip = this.$root.token.getIP()
        var input = {
          ghichu: this.KhoiTao.GhiChu,
          imsi: this.KhoiTao.MSIN,
          kho: this.vkho_id,
          ma_xac_thuc: this.KhoiTao.MaXacThuc,
          msisdn: '84' + this.KhoiTao.SoMay,
          ploai: this.KhoiTao.LoaiTB
        }
        let response = await api.tt_1_khoitao_chonso_tratruoc(this.axios, input)
        if (response.data.error === 200 || response.data.error === '200') {
          if (response.data.error_code == 'BSS-00000000') {
            if(!response.data.data && response.data.message==3)
              return "3";
            else
              return response.data.data
          } else {
            console.log(response.data.message)
            return response.data.message
          }
        } else {
          console.log(response.data.error_code)
          this.ShowError(response.data.error_code)
        }
      } catch (err) {
        return null
      }
    },
    tt_1_khoitao_chonso_trasau: async function () {
      try {
        let ma_nhanvien_ccbs = this.$root.token.getMaCCBS()
        let ma_tinh = this.$root.token.getMaTinh()
        let userId = this.$root.token.getNhanVienID()
        let ip = this.$root.token.getIP()
        var input = {
          ghichu: this.KhoiTao.GhiChu,
          imsi: this.KhoiTao.MSIN,
          kho: this.vkho_id,
          ma_xac_thuc: this.KhoiTao.MaXacThuc,
          msisdn: '84' + this.KhoiTao.SoMay,
          ploai: this.KhoiTao.LoaiTB
        }
        let response = await api.tt_1_khoitao_chonso_trasau(this.axios, input)
        if (response.data.error === 200 || response.data.error === '200') {
          if (response.data.error_code == 'BSS-00000000') {
            return response.data.message
          } else {
            console.log(response.data.message)
            return response.data.message
          }
        } else {
          console.log(response.data.error_code)
          this.ShowError(response.data.error_code)
          return response.data.message
        }
      } catch (err) {
        return null
      }
    },
    shouldDisablePageNavigation() {
      return (pageIndex) => pageIndex > this.page_num;
    },
    async pageChanged (value) {
      this.loading(true);
     
      setTimeout( async () => {
              
              const newData = await this.tt_1_tim_kiem_so();
              console.log(value)
              this.grcDSKhoSo.data = newData;
              this.page_num = value.pageIndex 
              this.loading(false);
      }, 11000);
              
      
    }, 
    ts_laphd_chonso_khotinh_layds: async function () {
      let ma_nhanvien_ccbs = this.$root.token.getMaCCBS() 
      let ma_tinh = this.$root.token.getMaTinh()
      let userId = this.$root.token.getNhanVienID()
      let ip = '127.0.0.1' // this.$root.token.getIP()
      var input = {
        prefix: this.vprefix_all,
        so_trang: 1,
        ip: ip,
        so_tb: this.vma_tb,
        kieuso_id: this.vkieusso_id,
        userid: ma_nhanvien_ccbs
      }
      let data = this.GetDataCCBS(await api.ts_laphd_chonso_khotinh_layds(this.axios, input))
      return data
    },
    ts_khoitao_ds_loaitb: async function (khoId) {
      let matinh = this.$root.token.getMaTinh()
      var input = {
        ma_tinh: matinh,
        skho: khoId
      }
      let data = this.GetData(await api.ts_khoitao_ds_loaitb(this.axios, input))
      return data
    },
    ts_khoitao_tratruoc_ds_loaitb: async function () {
      let ma_tinh = this.$root.token.getMaTinh()
      var input = {
        ma_tinh: ma_tinh
      }
      let data = this.GetData(await api.ts_khoitao_tratruoc_ds_loaitb(this.axios, input))
      return data
    },
    ts_danhsach_khoso_khoitao_tb: async function () {
      let ma_nhanvien_ccbs = this.$root.token.getMaCCBS()
      let ma_tinh = this.$root.token.getMaTinh()
      let userId = this.$root.token.getNhanVienID()
      var input = {
        kho: this.vkho_id,
        ma_tinh: ma_tinh,
        prefix: this.vprefix_all,
        so_tb: this.vma_tb,
        xac_thuc: this.txtMaXacThuc,
        userid: ma_nhanvien_ccbs,
        seq: 1
      }
      let data = this.GetData(await api.ts_danhsach_khoso_khoitao_tb(this.axios, input))
      console.log('ts_danhsach_khoso_khoitao_tb', data)
      return data
    },
    // ham nay ko tim thay
    tt_1_tim_kiem_so: async function () {
      // let ma_nhanvien_ccbs = this.$root.token.getMaCCBS()
      var input = {
        kieuso_id: this.vkieusso_id,
        page_index: this.page_num,
        prefix: this.vprefix_all,
        so_tb: this.vma_tb,
        status: this.vstatus
      }

      // input = {
      //   kieuso_id: '',
      //   page_index: 1,
      //   prefix: '8488',
      //   so_tb: '*09900',
      //   status: 1
      // }
      console.log('tt_1_tim_kiem_so', input)
      let data = this.GetData(await api.tt_1_tim_kiem_so(this.axios, input))
      return data
    },
    tt_1_chonso: async function (sotb, ghichu) {
      try {
        var input = {
          ghi_chu: ghichu,
          so_tb: sotb
        }
        let response = await api.tt_1_chonso(this.axios, input)
        if (response.data.error === 200 || response.data.error === '200') {
          if (response.data.error_code == 'BSS-00000000') {
            //return response.data.data
            return '1'
          } else {
            console.log(response.data.message)
            return response.data.message
          }
        } else {
          console.log(response.data.error_code)
          // this.ShowError(response.data.error_code)
          return response.data.message
        }
      } catch (err) {
        return null
      }
    },
    tt_1_chonso_ds: async function () {
      // checked
      let ma_nhanvien_ccbs = this.$root.token.getMaCCBS()
      var input = { userid: ma_nhanvien_ccbs }
      let data = this.GetData(await api.tt_1_chonso_ds(this.axios, input))
      return this.UpperCasePropertyList(data)//data
    },
    tt_1_trangthai: async function () {
      let ma_nhanvien_ccbs = this.$root.token.getMaCCBS()
      var input = {}
      let data = this.GetData(await api.tt_1_trangthai(this.axios, input))

      return data
    },
    check_ccbs() {
      let ma_nhanvien_ccbs = this.$root.token.getMaCCBS()
      if (ma_nhanvien_ccbs == null || ma_nhanvien_ccbs == '') {
        this.ShowError(`Chưa liên kết với tài khoản CCBS !`)
        return false
      }
      return true
    },
    ts_laphd_chonso_khotinh_phieudk: async function () {
      // checked
      let ma_nhanvien_ccbs = this.$root.token.getMaCCBS()
      var input = { userid: ma_nhanvien_ccbs }
      let data = this.GetData(await api.ts_laphd_chonso_khotinh_phieudk(this.axios, input))
      return data
    },
    /* ts_laphd_chonso_khotinh_layds: async function () {
      let ma_nhanvien_ccbs = this.$root.token.getMaCCBS()
      var input = { USER_NEO: ma_nhanvien_ccbs }
      let data = this.GetData(await api.ts_laphd_chonso_khotinh_layds(this.axios, input))
      return data
    }, */
    ts_laphd_chonso_khotinh: async function (somay) {
      try {
        let ma_nhanvien_ccbs = this.$root.token.getMaCCBS()
        let ma_tinh = this.$root.token.getMaTinh()
        var input = {
          ip: '127.0.0.1',
          so_tb: somay,
          ma_tinh_nguoi_dung: ma_tinh,
          userid: ma_nhanvien_ccbs
        }
        let response = await api.ts_laphd_chonso_khotinh(this.axios, input)
        if (response.data.error === 200 || response.data.error === '200') {
          if (response.data.error_code == 'BSS-00000000') {
            return response.data.data
          } else {
            console.log(response.data.message)
            return response.data.message
          }
        } else {
          console.log(response.data.error_code)
          this.ShowError(response.data.error_code)
        }
      } catch (err) {
        this.ShowError(err.message)
        return null
      }
    },
    tt_1_kieuso: async function () {
      var input = { prefix: this.vprefix_all }
      let data = this.GetData(await api.tt_1_kieuso(this.axios, input))
      return data
    },
    ts_thongtin_khoso_khoitao: async function () {
      let ma_nhanvien_ccbs = this.$root.token.getMaCCBS()
      let ip = '127.0.0.1' // this.$root.token.getIP()
      let ma_tinh = this.$root.token.getMaTinh()
      let input = { ma_tinh: ma_tinh, ip: ip, userid: ma_nhanvien_ccbs }
      let data = this.GetData(await api.ts_thongtin_khoso_khoitao(this.axios, input))
      return data
    },
    SearchKhoSo1: function () {
      alert('1313')
    },
    btnLayTT_Click: async function () {
      try {
        this.Loading = true
        await this.TRACUU_KHOSO_TQ()
      } catch (err) {
        this.Loading = false
        this.ShowError(`Có lỗi xảy ra : ${err.message}`)
      } finally {
        this.Loading = false
      }
    },
    // LayTT
    async btnRefresh_Click() {
      try {
        this.Loading = true
        await this.NAP_DS_SODANGKY()
        await this.NAP_DS_KHOITAO()
      } catch (err) {
        this.ShowError(`Có lỗi xảy ra : ${err.message}`)
      } finally {
        this.Loading = false
      }
    },
    async btnNhapMoi_Click() {
      try {
        this.Loading = true
        await this.NHAP_SO()
      } catch (err) {
        this.ShowError(`Có lỗi xảy ra : ${err.message}`)
      } finally {
        this.Loading = false
      }
    },
    async NHAP_SO() {
      try {
        // grvDS_ChuaDK.CloseEditor();
        if (this.grcDS_ChuaDK.data == null) {
          this.ShowError('Không lấy được dữ liệu danh sách chưa đăng ký')
          return
        }
        // ds_cdk.AcceptChanges();
        this.$bvModal.show('my-modal')
        /*
                var f = new frmNhapText("Nhập danh sách số máy ");
                f.Text = "Nhập danh sách số máy";
                if (f.ShowDialog() == DialogResult.OK) // nếu thay đổi
                {
                    var s = f.Output.TrimEnd(", ".ToCharArray()); ;
                    string[] ds_tb = s.Split(',');

                    foreach (var r in ds_tb)
                    {
                        var banghi = ds_cdk.Select("SO_TB='84" + r + "'" +
                                                 " OR SO_TB='" + r + "'").Length;
                        if (banghi > 0)
                        {
                            continue;
                        }
                        var row = ds_cdk.NewRow();
                        row["ISCHECK"] = "0";
                        row["TEN_KIEUSO"] = "Chọn số thủ công";
                        row["CUOCTHANG_TOITHIEU"] = "Chọn số thủ công";
                        if (r.StartsWith("84"))
                        {
                            row["SO_TB"] = "0" + r.Remove(0, 2);
                        }
                        else
                        {
                            row["SO_TB"] = r;
                        }
                        ds_cdk.Rows.Add(row);
                    }
                    ds_cdk.AcceptChanges();

                    #region Distinct lại dữ liệu
                    var view = new DataView(ds_cdk);
                    var distinctValues = view.ToTable(true, "SO_TB", "TEN_KIEUSO", "CUOCTHANG_TOITHIEU");
                    var check = new DataColumn("ISCHECK", typeof(String));
                    check.DefaultValue = "0";
                    distinctValues.Columns.Add(check);
                    grcDS_ChuaDK.DataSource = distinctValues;
                    grvDS_ChuaDK.BestFitColumns();
                    if (colKieuSo.Width > 250)
                        colKieuSo.Width = 250;
                    #endregion

                }
                */
      } catch (err) {
        this.ShowError('Có lỗi khi nhập số máy: ' + err.message)
      }
    },
    async XOA_DS_CHUA_DK() {
      try {
        if (this.grcDS_ChuaDK.data == null) {
          this.ShowError('Không lấy được dữ liệu danh sách chưa đăng ký')
          return
        }
        if (this.grcDS_ChuaDK.data.length == 0) {
          this.ShowError('Không có dữ liệu số máy để xóa')
          return
        }
        if (this.grcDS_ChuaDK.selectedItems.length == 0) {
          this.ShowError('Chưa có số máy nào được chọn')
          return
        }
        let selectedItems = this.grcDS_ChuaDK.selectedItems
        this.grcDS_ChuaDK.data = this.grcDS_ChuaDK.data.filter((x) => selectedItems.filter((y) => (y.so_tb = x.so_tb)).length == 0)
        await this.CAP_NHAT_TRANGTHAI()
      } catch (err) {
        console.log(err)
        this.ShowError('Có lỗi khi xóa danh sách chưa đăng ký ')
      }
    },
    // XoaSo
    async btnXoaTB_Click() {
      try {
        this.Loading = true
        await this.XOA_DS_CHUA_DK()
      } catch (err) {
        this.ShowError(err.message)
      } finally {
        this.Loading = false
      }
    },
    async btnCapNhat_Click() {
      try {
        this.Loading = true
        await this.DANGKY()
      } catch (err) {
        this.ShowError(err.message)
      } finally {
        this.Loading = false
      }
    },
    async NAP_COMBO() {
      await this.getDmDauSo()
      await this.getDmTrangThaiKhoSo()
      if (this.vkieuform == 0) {
        // trả truoc
        this.columns = 'STATUS'
        // // #region Nạp dữ liệu bảng chưa đăng ký
        let dt_cdk = []
        let a = { ISCHECK: 0, SO_TB: '', TEN_KIEUSO: '', CUOCTHANG_TOITHIEU: '' }
        // dt_cdk.push(a)
        // var dt_cdk = tdan.GET_DATA_SQL("select '0' ISCHECK ,'' SO_TB,'' TEN_KIEUSO,'' CUOCTHANG_TOITHIEU from dual")
        // dt_cdk.Clear()
        this.grcDS_ChuaDK.data = dt_cdk

        // #endregion
        // // #region COMBO kho số tỉnh vuth update 1709
        // var dt = tdan.GET_DATA_SQL('' + string.Format(" select '{0}' TEN_KHO ,'1' KHO_ID from dual ", 'Kho số toàn quốc') + ' union all ' + string.Format(" select '{0}' TEN_KHO ,'4' KHO_ID from dual ", 'Kho số trả trước viễn thông tỉnh'))
        let dt = [
          { ten_kho: 'Kho số toàn quốc', kho_id: 1 },
          { ten_kho: 'Kho số trả trước viễn thông tỉnh', kho_id: 4 }
        ]
        this.DmKhoSo = dt
        // bts.HT_DS_LOOKUPEDIT(cboKhoSo, dv.ToTable(), 'TEN_KHO', 'KHO_ID')
        // cboKhoSo.Properties.DropDownRows = dv.Count

        // #endregion
      }

      if (this.vkieuform == 1) {
        // trả sau
        this.columns = 'TRANGTHAI'
        // // #region COMBO kho số
        let dt_kho = await this.ts_thongtin_khoso_khoitao()
        /*
                    var lingq_result = from row in dt_kho.AsEnumerable()
                                       where row.Field<String>("KHO_ID") != "4"
                                       select row;
                    */
        let lingq_result = dt_kho.filter((x) => x.KHO_ID != '4')
        if (lingq_result.length > 0) {
          let dt_kho_trasau = lingq_result
          this.DmKhoSo = dt_kho_trasau.map((x) => ({ kho_id: x.KHO_ID, ten_kho: x.TEN_KHO }))
          /*
                        // bts.HT_DS_LOOKUPEDIT(cboPrefix, ccbs.tt_1_prefix(), "value", "value");
                        bts.HT_DS_LOOKUPEDIT(cboKhoSo, dt_kho_trasau, "TEN_KHO", "KHO_ID");
                        cboKhoSo.Properties.DropDownRows = dt_kho_trasau.length;
                        */
        } else {
          this.ShowError('Không lấy được ds kho số')
        }
        // #endregion
      }
    },
    async THIETLAP() {
      if (this.vkieuform == 1) {
        // trả sau
        this.xTabKhoTQ_Text = ' Danh sách kho số chung viễn thông tỉnh'
        this.colChKB = false
        this.btnCapNhat = false
        this.btnRefresh = false
        this.tsbtnChonSo = false
        this.btnXoaTB = false
        this.btnNhapMoi = false
        this.xTabKhoTinh_Text = 'Danh sách kho số khác' // vuth update ts 0210
        // splitContainerControl1.PanelVisibility = SplitPanelVisibility.Both; // vuth update ts 0710
        this.xTabKhoTQ = true
        this.xtraTabPage2 = true
        this.cboTrangThai_ReadOnly = false
        // tableCombo.RowStyles[1].Height = 0;
        await Promise.all([this.NAP_COMBO(), this.NAP_DS_SODANGKY(), this.NAP_DS_KHOITAO()]).catch((err) => {
          console.log('THIETLAP: Promise.all', err)
        })
        // await this.NAP_COMBO()
        // await this.NAP_DS_SODANGKY()
        // await this.NAP_DS_KHOITAO() // vuth update 04/12
        // xTabKhoSo.SelectedTabPageIndex = 2; // vuth update 0710 mac dinh kho so toan quoc
        this.TabKhoSoIndex = 2
        // groupControl1.Visible = false; // vuth update 0710 an ds chua dk
        // tableLayoutPanel2.ColumnStyles[0].SizeType = SizeType.Percent;
        // tableLayoutPanel2.ColumnStyles[1].SizeType = SizeType.Percent;
        // tableLayoutPanel2.ColumnStyles[0].Width = 0;
        // tableLayoutPanel2.ColumnStyles[1].Width = 100;
        // xtraTabControl1.Dock = DockStyle.Fill;
      }
      if (this.vkieuform == 0) {
        // Trả trước
        this.colBtnChonSo = false

        /* vuth update fix 1509
                var rowStyles = tableCombo.RowStyles;
                rowStyles[0].SizeType = SizeType.Percent;
                rowStyles[1].SizeType = SizeType.Percent;
                rowStyles[0].Height = 0;
                rowStyles[1].Height = 100;

                var rowStyles_all = tableLayoutPanel3.RowStyles;
                rowStyles_all[1].SizeType = SizeType.Absolute;
                rowStyles_all[1].Height = 35;

                var col = tableCombo.ColumnStyles;
                col[1].Width = 30;
                col[3].Width = 30;
                col[2].SizeType = SizeType.Percent;
                col[2].Width = 50;
                 */
        this.xTabKhoTinh_Text = ' Danh sách kho số trả trước viễn thông tỉnh'
        this.xTabKhoTinh = true // vuth update fix 1409
        // emptyEditor = new RepositoryItem();
        // grcDSKhoSo.RepositoryItems.Add(emptyEditor);
        await Promise.all([this.NAP_COMBO(), this.NAP_DS_SODANGKY(), this.NAP_DS_KHOITAO()]).catch((err) => {
          console.log('THIETLAP: Promise.all', err)
        })
        // await this.NAP_COMBO()
        // await this.NAP_DS_SODANGKY()
        // await this.NAP_DS_KHOITAO()
        this.xtraTabPage2 = true
        // splitContainerControl1.PanelVisibility = SplitPanelVisibility.Both;
        // // #region Thiết lập lưới
        // grcDSKhoSo.setAllGridView_SpaceToggleCheck(false, true, false);
        // grcDS_ChuaDK.setAllGridView_SpaceToggleCheck(false, true, false);
        // #endregion
      }
    },
    async NAP_DS_KHOSO() {
      let nguoidungId = this.$root.token.getNguoiDungID()
      let loaitb_id = this.vkieuform == 0 ? '21' : '20'
      var input = { NGUOIDUNG_ID: nguoidungId, LOAITB_ID: loaitb_id }
      let data = this.GetData(await api.NAP_DS_KHOSO(this.axios, input))
      if (data != null) {
        let i = 0
        data.forEach(function (item) {
          i++
          item.STT = i
        })
      }
      return data
      // this.grcDS_DaDK.data = data
    },
    async sp_chonso_kh_danhmuc(ds_para, nghiepvu) {
      let phanvungId = this.$root.token.getPhanVungID()
      var input = {
        ds_para: ds_para,
        nghiepvu: nghiepvu,
        phanvung_id: phanvungId
      }
      let data = this.GetData(await api.sp_chonso_kh_danhmuc(this.axios, input))
      return data
    },
    async sp_ht_prefix_lhtb(loaitb_id) {
      let phanvungId = this.$root.token.getPhanVungID()
      var input = {
        loaitb_id: loaitb_id,
        phanvung_id: phanvungId
      }
      let data = this.GetData(await api.sp_ht_prefix_lhtb(this.axios, input))
      return data
    },
    async fn_khoitao_tb_didong(kieu, loai) {
      try {
        // let loai = this.vkieuform == 0 ? 1 : 2
        let nguoidungId = this.$root.token.getNguoiDungID()
        let donviId = this.$root.token.getDonViID()
        let ma_ccbs = this.$root.token.getMaCCBS()
        let may_cn = 'web' // this.$root.token.getMachine()
        let username = this.$root.token.getUserName()
        let ip = '127.0.0.1' // this.$root.token.getIP()
        let ds_param = { VTT: this.KhoiTao.LoaiTB, NGUOIDUNG_ID: nguoidungId, SOMAY: this.KhoiTao.SoMay, SOSIM: this.KhoiTao.MSIN, USER_NEO: ma_ccbs, GHICHU: this.KhoiTao.GhiChu, DONVI_ID: donviId, NGUOI_CN: username, MAY_CN: may_cn, IP_CN: ip }
        var input = {
          kieu: kieu,
          loai: loai,
          ds_para: JSON.stringify(ds_param)
        }
        let data = this.GetData(await api.fn_khoitao_tb_didong(this.axios, input))
        return JSON.parse(data)
      } catch (err) {
        return { KETQUA: 0, THONGBAO: err.message }
      }
    },
    async DANHMUC_GOICUOC_DD_TT() {
      let phanvungId = this.$root.token.getPhanVungID()
      var input = {
        P_PHANVUNG_ID: phanvungId,
        P_NGHIEPVU: 'DANHMUC_GOICUOC_DD_TS',
        P_DS_PARA: ''
      }
      let data = this.GetData(await api.DANHMUC_GOICUOC_DD_TT(this.axios, input))
      return data
    },
    async DANHMUC_GOICUOC_DD_TS() {
      var input = {}
      let data = this.GetData(await api.DANHMUC_GOICUOC_DD_TS(this.axios, input))
      return data
    },
    async NAP_DS_KHOITAO() {
      try {
        let data = await this.NAP_DS_KHOSO()
        this.grcDSKhoiTao.data = this.UpperCasePropertyList(data)
      } catch (err) {
        console.log(err)
        this.ShowError(`Có lỗi khi nạp danh sách số đã đăng ký ! , ${err.message}`)
      }
    },
    async NAP_DS_SODANGKY() {
      try {
        let khotq_id = this.KHO_TQ_ID
        if (this.vkieuform == 0) {
          let ds = await this.tt_1_chonso_ds()
          // region vuth update fix 1609
          // vuth update 1709 : loc theo user_neo vi ccbs tra ve ds dang ky theo user_neo
          let dtDangkyChuaKhoiTao = []
          if (ds != null && ds.length > 0) {
            /*
            DataTable dt_dakhoitao = tdan.GET_DATA_SQL(@"
                        SELECT somay, simcard, donvi_id, trangthaiso_id, ghichu,goicuoc_id,
                               ngay_cn, nguoidung_id, nguoi_cn, may_cn, ip_cn
                          FROM {?DB2}.khoso_dd where user_neo=:vuser_neo and  trangthaiso_id not in (4,5)", "vuser_neo", ttnd.USER_NEO);
            */
            let dt_dakhoitao = await this.DS_KHOSO_KHOITAO_USER()
            if (dt_dakhoitao != null) {
              var rowsDKchuaKhoitao = ds.filter((x) => dt_dakhoitao.filter((y) => y.SOMAY == x.SO_TB.substring(2) || y.SOMAY == x.SO_TB).length == 0)
              /*
                            var rowsDKchuaKhoitao = from row_dadk in ds.AsEnumerable()
                                                   join row_dakhoitao in dt_dakhoitao.AsEnumerable()
                                                   on row_dadk.Field<string>("SO_TB").Substring(2) equals row_dakhoitao.Field<string>("SOMAY")
                                                   into childTable
                                                   from child in childTable.DefaultIfEmpty(dt_dakhoitao.NewRow())
                                                   where child.Field<string>("SOMAY") == null
                                                   select row_dadk;
                          */
              if (rowsDKchuaKhoitao.length > 0) dtDangkyChuaKhoiTao = rowsDKchuaKhoitao
            } else {
              dtDangkyChuaKhoiTao = ds
            }
          }
          // #endregion
          // this.grcDS_DaDK.data = []
          let i = 1
          if (dtDangkyChuaKhoiTao != null)
            dtDangkyChuaKhoiTao.forEach(function (item) {
              item.STT = i
              i++
            })
          let ii = 1
          dtDangkyChuaKhoiTao.forEach(function (item) {
            item.STT = ii
            ii++
          })
          this.grcDS_DaDK.data = dtDangkyChuaKhoiTao
          // this.grvDS_DaDK.BestFitColumns();
        }
        if (this.vkieuform == 1) {
          /*  colThoiGianDK.FieldName = "NGAY_TH";
                    colHieuLUc.FieldName = "NGAY_HH";
                    colHieuLUc.Caption = "Ngày hết hạn";
                    */
          // colKhoId.Visible = true;

          let ds = await this.tt_1_chonso_ds()
          let dtDangkyChuaKhoiTao = [] // new DataTable() // vuth update 0710
          if (ds != null && ds.length > 0) {
            let dt_dakhoitao = await this.DS_KHOSO_KHOITAO_USER()
            if (dt_dakhoitao != null) {
              // var rowsDKchuaKhoitao = ds.filter((x) => dt_dakhoitao.filter((y) => y.SOMAY == x.SO_TB.substring(2)).length > 0)
              var rowsDKchuaKhoitao = ds.filter((x) => dt_dakhoitao.filter((y) => y.SOMAY == x.SO_TB || y.SOMAY == x.SO_TB.substring(2)).length == 0)
              /*
                            var rowsDKchuaKhoitao = from row_dadk in ds.AsEnumerable()
                                                    join row_dakhoitao in dt_dakhoitao.AsEnumerable()
                                                    on row_dadk.Field<string>("SO_TB").Substring(2) equals row_dakhoitao.Field<string>("SOMAY")
                                                    into childTable
                                                    from child in childTable.DefaultIfEmpty(dt_dakhoitao.NewRow())
                                                    where child.Field<string>("SOMAY") == null
                                                    select row_dadk;
                            */
              if (rowsDKchuaKhoitao.length > 0) dtDangkyChuaKhoiTao = rowsDKchuaKhoitao
            } else {
              dtDangkyChuaKhoiTao = ds
            }
          }
          let ds_chonso_khotinh_ts = await this.ts_laphd_chonso_khotinh_phieudk()
          // let dtDaDK = [] // createDataTable_DaDK();
          let dtDaDK = dtDangkyChuaKhoiTao.map((item) => ({
            SO_TB: item.SO_TB,
            NGAYTH: item.NGAYTH,
            THOIGIAN_CON: item.THOIGIAN_CON,
            KHO_ID: khotq_id
          }))
          /*
                    foreach (DataRow dr in dtDangkyChuaKhoiTao)
                    {
                        DataRow tmp = dtDaDK.NewRow();
                        tmp["SO_TB"] = dr["SO_TB"];
                        tmp["NGAYTH"] = dr["NGAYTH"];
                        tmp["THOIGIAN_CON"] = dr["THOIGIAN_CON"];
                        tmp["KHO_ID"] = KHO_TQ_ID;
                        dtDaDK.Rows.Add(tmp);
                    }
                    */
          if (ds_chonso_khotinh_ts != null) {
            // vuth update 0710
            ds_chonso_khotinh_ts.forEach(function (item) {
              let ngay_hh = moment(item.NGAY_HH, 'DD/MM/YYYY HH:mm:ss')
              console.log('ngay_hh', ngay_hh)
              let date = new Date()
              // let thoi_gian_con=moment(item.NGAY_HH,'DD/MM/YYYY HH:mm:ss').getTime() -new Date().getTime()
              let thoi_gian_con = moment(ngay_hh).diff(date, 'minutes')
              dtDaDK.push({
                SO_TB: item.SO_TB,
                NGAYTH: item.NGAY_TH,
                THOIGIAN_CON: thoi_gian_con,
                KHO_ID: khotq_id,
                NGAY_HH: item.NGAY_HH
              })
            })
            /*
                        foreach (DataRow dr in ds_chonso_khotinh_ts.Rows)
                        {
                            DataRow tmp = dtDaDK.NewRow();
                            tmp["SO_TB"] = dr["SO_TB"];
                            tmp["NGAYTH"] = dr["NGAY_TH"];
                            TimeSpan thoigian_con = Convert.ToDateTime(dr["NGAY_HH"]) - DateTime.Now;
                            tmp["THOIGIAN_CON"] = Convert.ToInt32(thoigian_con.TotalMinutes);
                            tmp["KHO_ID"] = KHO_TINH_ID;
                            dtDaDK.Rows.Add(tmp);
                        }
                      */
          }

          // this.grcDS_DaDK.data = []
          let j = 1
          if (dtDaDK != null)
            dtDaDK.forEach(function (item) {
              item.STT = j
              j++
            })
          this.grcDS_DaDK.data = dtDaDK

          // grvDS_DaDK.BestFitColumns();
        }
      } catch (errr) {
        console.log(errr)
        this.ShowError(`Có lỗi khi nạp danh sách số đã đăng ký: ${errr.message}`)
      }
    },
    async TRACUU_KHOSO_TQ2() {
      console.log(11111111)
      // region xu ly tra truoc
      let dt = []
      if (this.vkieuform == 0) {
        if (this.khoso_dangchon == 0) {
          // nếu trả trước và ở tab kho số toàn quốc -- vuth update fix 1609
          // region  Nạp dữ liệu  kho số toàn quốc

          // var dt = ccbs.tt_1_tim_kiem_so(parameters[0].ToString(), parameters[1].ToString(),
          //                                parameters[2].ToString(),
          //                                parameters[3].ToString(), parameters[4].ToString(), parameters[5].ToString(), ttnd.ip, ttnd.tentat_ccbs);
          dt = await this.tt_1_tim_kiem_so()
          // Điền thêm page_index = 0 để build không lỗi
          // 0 ma tb
          // 1 kieuso id
          // 2 prefix
          // 3 trangthai
          // 4 user id
          // 5 so trang
          if (dt.length > 1) {
            // var check = new DataColumn("ISCHECK", typeof(String));
            // check.DefaultValue = "0";
            // dt.Columns.Add(check);
            // var status_tt = new DataColumn("status_tt", typeof(String));
            // status_tt.DefaultValue = "0";
            // dt.Columns.Add(status_tt);
          }
          // sender.SetOutput(dt);
          // #endregion
        } // vuth update fix 1609
        else {
          if (this.vkho_id != this.KHO_TINH_ID) {
            // // #region  Nạp dữ liệu  kho all trừ kho chung tỉnh

            dt = await this.ts_danhsach_khoso_khoitao_tb()
            // Điền thêm page_index = 0 để build không lỗi
            // 0 ma tb
            // 1 kh_id id
            // 2 prefix
            // 3 xac thuc
            // 4 user id
            if (dt != null) {
              // var STT = new DataColumn("STT", typeof(Int32));
              //  STT.AutoIncrement = true;
              // STT.AutoIncrementSeed = 1;
              // STT.AutoIncrementStep = 1;
              // dt.Columns.Add(STT);
              // for (int i = 0; i < dt.length; i++)
              // {
              //    dt.Rows[i]["STT"] = i + 1;
              // }
              // dt.AcceptChanges();
            }
            // sender.SetOutput(dt);

            // #endregion
          } else {
            // // #region  Nạp dữ liệu  kho chung viễn thông tỉnh

            dt = await this.ts_laphd_chonso_khotinh_layds()
            // Điền thêm page_index = 0 để build không lỗi
            // 0 ma tb
            // 1 kho_id id
            // 2 prefix
            // 3 kieuso
            // 4 user id
            // if (dt != null)
            // {
            //     if (dt.Columns.Count > 1)
            //     {

            //         var status_tt = new DataColumn("status_tt", typeof(String));
            //         status_tt.DefaultValue = "0";
            //         dt.Columns.Add(status_tt);
            //     }
            //     dt.AcceptChanges();
            // }
            // sender.SetOutput(dt);

            // #endregion
          }
        }
      }
      //  #endregion xong xu ly tra truoc
      if (this.vkieuform == 1) {
        if (this.cboKhoSo == this.KHO_TQ_ID) {
          // region  Nạp dữ liệu  kho số toàn quốc

          dt = await this.tt_1_tim_kiem_so()
          // Điền thêm page_index = 0 để build không lỗi
          // 0 ma tb
          // 1 kieuso id
          // 2 prefix
          // 3 trangthai
          // 4 user id
          // 5 so trang
          // if (dt.Columns.Count > 1)
          // {
          //     var check = new DataColumn("ISCHECK", typeof(String));
          //     check.DefaultValue = "0";
          //     dt.Columns.Add(check);

          //     var status_tt = new DataColumn("status_tt", typeof(String));
          //     status_tt.DefaultValue = "0";
          //     dt.Columns.Add(status_tt);
          // }
          // sender.SetOutput(dt);
          // #endregion
        } else if (this.vkho_id != this.KHO_TINH_ID) {
          /// / #region  Nạp dữ liệu  kho all trừ kho chung tỉnh

          dt = this.ts_danhsach_khoso_khoitao_tb()
          // Điền thêm page_index = 0 để build không lỗi
          // 0 ma tb
          // 1 kh_id id
          // 2 prefix
          // 3 xac thuc
          // 4 user id
          // if (dt != null)
          // {

          //     var STT = new DataColumn("STT", typeof(Int32));
          //     //  STT.AutoIncrement = true;
          //     // STT.AutoIncrementSeed = 1;
          //     // STT.AutoIncrementStep = 1;
          //     dt.Columns.Add(STT);
          //     for (int i = 0; i < dt.length; i++)
          //     {
          //         dt.Rows[i]["STT"] = i + 1;
          //     }
          //     dt.AcceptChanges();
          // }
          // sender.SetOutput(dt);

          // #endregion
        } else {
          /// / #region  Nạp dữ liệu  kho chung viễn thông tỉnh

          dt = this.ts_laphd_chonso_khotinh_layds()
          // Điền thêm page_index = 0 để build không lỗi
          // 0 ma tb
          // 1 kho_id id
          // 2 prefix
          // 3 kieuso
          // 4 user id
          // if (dt != null)
          // {
          //     if (dt.Columns.Count > 1)
          //     {

          //         var status_tt = new DataColumn("status_tt", typeof(String));
          //         status_tt.DefaultValue = "0";
          //         dt.Columns.Add(status_tt);
          //     }
          //     dt.AcceptChanges();
          // }
          // sender.SetOutput(dt);

          // #endregion
        }
      }
      return dt
    },
    async CAP_NHAT_TRANGTHAI() {
      try {
        if (this.grcDSKhoSo.data == null) {
          return
        }
        if (this.grcDSKhoSo.data.length == 0) {
          return
        }
        if (this.grcDS_ChuaDK.data == null) {
          this.grcDSKhoSo.data.forEach(function (dr) {
            dr['status_tt'] = dr['trangthai']
            if (dr['trangthai'] == 0) {
              dr['status_tt_img'] = '<a href="javascript:void(0);" class="text-warning aChonSo act"><span class="nc-icon-glyph ui-1_home-simple"></span>  </a>'
            } else {
              dr['status_tt_img'] = ''
            }
          })
          this.grcDSKhoSo.data = this.grcDSKhoSo.data.slice(0)
        } else {
          if (this.grcDS_ChuaDK.data.length == 0) {
            this.grcDSKhoSo.data.forEach(function (dr) {
              dr['status_tt'] = dr['trangthai']
              if (dr['trangthai'] == 0) {
                dr['status_tt_img'] = '<a href="javascript:void(0);" class="text-warning aChonSo act"><span class="nc-icon-glyph ui-1_home-simple"></span>  </a>'
              } else {
                dr['status_tt_img'] = ''
              }
            })
            this.grcDSKhoSo.data = this.grcDSKhoSo.data.slice(0)
          } else {
            let self = this
            this.grcDSKhoSo.data.forEach(function (dr) {
              if (self.grcDS_ChuaDK.data.filter((x) => x.so_tb == dr['so_tb']).length > 0) {
                dr['status_tt'] = '2'
                dr['status_tt_img'] = '<a href="javascript:void(0);" class="text-success  aChonSo act"><span class="nc-icon-glyph ui-1_check-circle-08"></span> </a>'
              } else {
                dr['status_tt'] = dr['trangthai']
                if (dr['trangthai'] == 0) {
                  dr['status_tt_img'] = '<a href="javascript:void(0);" class="text-warning aChonSo act"><span class="nc-icon-glyph ui-1_home-simple"></span>  </a>'
                } else {
                  dr['status_tt_img'] = ''
                }
              }
            })
            this.grcDSKhoSo.data = this.grcDSKhoSo.data.slice(0)
          }
        }
      } catch (err) {
        console.log('CAP_NHAT_TRANGTHAI', err)
        this.ShowError(`Có lỗi khi cập nhật trạng thái ${err.message}`)
      }
    },
    xTabKhoSo_SelectedPageChanged(tabindex) {
      if (this.vkieuform == 0) {
        if (tabindex == 1) {
          // xTabKhoTinh
          this.cboKhoSo_Enabled = true
          this.CboKieuSo_Enabled = false
          this.khoso_dangchon = 1
          this.tsbtnChonSo = false
          this.btnXoaTB = false
          this.btnCapNhat = false
          this.btnNhapMoi = false
          this.cboKhoSo = '4'
        }
        if (tabindex == 0) {
          // xTabKhoTQ
          this.cboKhoSo_Enabled = true // vuth update fix 1709
          this.CboKieuSo_Enabled = true
          this.khoso_dangchon = 0
          this.tsbtnChonSo = true
          this.btnXoaTB = true
          this.btnCapNhat = true
          this.btnNhapMoi = false
          this.cboKhoSo = '1'
        }
      } else {
        if (tabindex == 0) {
          // kho chung tinh
          this.cboKhoSo = this.KHO_TINH_ID
        } else if (tabindex == 2) {
          // kho toan quoc
          this.cboKhoSo = this.KHO_TQ_ID
        }
      }
    },
    TRACUU_KHOSO_TQ: async function () {
      try {
        if (this.cboPrefix == undefined || this.cboPrefix.length <= 0) {
          this.ShowAlert('Bạn phải chọn đầu số  !')
          return
        }
        if (this.CboKieuSo === undefined || this.CboKieuSo.length <= 0) {
          this.ShowAlert('Bạn phải chọn kiểu số  !')
          this.$ref.refCboKieuSo.focus()
          return
        }
        this.Loading = true
        let ds = await this.TRACUU_KHOSO_TQ2()
        /// / #region Gán dữ liệu
        if (this.vkieuform == 0) {
          if (this.khoso_dangchon == 0) {
            // vuth update fixed 1609
            // #region Trả trước tab kho toàn quốc
            this.grcDSKhoSo.data = ds
            if (ds && ds[0] && Object.keys(ds[0]).length == 1) {
              let keys = Object.keys(ds[0])
              this.ShowError('Dữ liệu tìm kiếm bị lỗi: ' + ds[0][keys[0]])
            }
            // if (frm.Output != DBNull.Value && frm.Output != null)
            // {
            //     var ds = frm.Output as DataTable;
            //     if (ds != null && ds.Columns.Count == 1)
            //     {

            //          this.ShowError("Dữ liệu tìm kiếm bị lỗi: " + ds.Rows[0][0]);
            //         return;
            //     }
            //     grcDSKhoSo.DataSource = ds;
            //     grvDSKhoSo.BestFitColumns();
            // }
            await this.CAP_NHAT_TRANGTHAI()
            // #endregion
          } // nếu kho tỉnh
          else {
            console.log('TRACUU_KHOSO_TQ1')
            if (this.cboKhoSo.length > 0) {
              this.xTabKhoTinh_Text = '' + this.DmKhoSo.filter((x) => x.kho_id == this.cboKhoSo)[0].ten_kho
            }
            // colTrangThai.FieldName = columns;
            if (this.vkho_id == this.KHO_TINH_ID) {
              console.log('TRACUU_KHOSO_TQ2')
              // neu la kho chung
              // colstatus_tt.Visible = false;
              this.xTabKhoTQ_Text = ' Danh sách kho số chung viễn thông tỉnh'
              this.grcDSKhoSo.data = null
              // if (frm.Output != DBNull.Value && frm.Output != null)
              // {

              //     var ds = frm.Output as DataTable;
              //     if (ds != null && ds.length == 1)
              //     {

              //         if (ds.Rows[0]["SO_TB"] == DBNull.Value || ds.Rows[0]["SO_TB"] == null)
              //         {

              //             return;
              //         }
              //     }

              //     grcDSKhoSo.DataSource = ds;
              //     grvDSKhoSo.BestFitColumns();
              // }
              this.grcDSKhoSo.data = ds
              // CAP_NHAT_TRANGTHAI();
            } else {
              console.log('TRACUU_KHOSO_TQ3')
              this.grcDSAllKho.data = []
              if (ds != null && ds.length > 0) {
                let dt_khosoconlai = null // vuth update fix 1709 : lọc bỏ những số đã khởi tạo/đã dùng
                // var ds = frm.Output as DataTable;
                if (ds != null && ds.length == 1) {
                  if (ds[0]['ID'] == null || ds[0]['ID'] == null) {
                    this.ShowAlert('' + ds[0]['MSI_KHO'])
                    return
                  }
                }
                if (ds != null) {
                  // let dt_khosodadung = tdan.GET_DATA_SQL(@"
                  //             SELECT somay, simcard, donvi_id, trangthaiso_id, ghichu,goicuoc_id,
                  //              ngay_cn, nguoidung_id, nguoi_cn, may_cn, ip_cn
                  //              FROM {?DB2}.khoso_dd where user_neo=:vuser_neo and  trangthaiso_id not in (4,5)", "vuser_neo", ttnd.USER_NEO);
                  //  var linqResult = from row in ds.AsEnumerable()
                  //                                  join row_dadung in dt_khosodadung.AsEnumerable()
                  //                                  on row.Field<string>("MSISDN").Substring(2) equals row_dadung.Field<string>("SOMAY")
                  //                                  into childTable
                  //                             from child in childTable.DefaultIfEmpty(dt_khosodadung.NewRow())
                  //                                  where child.Field<string>("SOMAY") == null
                  //                                  select row;
                  //  if (linqResult.Any())
                  //      dt_khosoconlai = linqResult.CopyToDataTable();
                  let dt_khosodadung = await this.DS_KHOSO_KHOITAO_USER()
                  let linqResult = ds.filter((x) => dt_khosodadung.filter((y) => y.SOMAY == x.MSISDN.substring(2)).length == 0)
                  if (linqResult != null && linqResult.length > 0) dt_khosoconlai = linqResult
                  console.log('linqResult', linqResult)
                  console.log('dt_khosoconlai', dt_khosoconlai)
                }
                this.grcDSAllKho.data = dt_khosoconlai
                // grvDSAllKho.BestFitColumns();
              }
            }
          }
        }
        if (this.vkieuform == 1) {
          // #region Trả sau
          if (this.cboKhoSo > 0 && this.vkho_id != this.KHO_TINH_ID && this.vkho_id != this.KHO_TQ_ID) {
            this.xTabKhoTinh_Text = '' + this.DmKhoSo.filter((x) => x.kho_id == this.cboKhoSo)[0].ten_kho
          }
          // colTrangThai.FieldName = columns;
          if (this.vkho_id == this.KHO_TINH_ID) {
            // neu la kho chung
            // colstatus_tt.Visible = false;
            this.xTabKhoTQ_Text = ' Danh sách kho số chung viễn thông tỉnh'
            // grcDSKhoSo.DataSource = null;
            if (ds != null) {
              // var ds = frm.Output as DataTable;
              if (ds != null && ds.length == 1) {
                if (ds[0]['ten_kieuso'] == 'Yeu cau dang nhap he thong truoc khi tim kiem') {
                  this.ShowError('Kiểm tra lại phân quyền của tài khoản người dùng!')
                  return
                } else if (ds[0]['ten_kieuso'] == 'Ban phai nhap gia tri can tim ket hop voi ky tu * de tim gan chinh xac') {
                  this.ShowError('Nhập thêm giá trị kết hợp với ký tự * để tìm kiếm!')
                  return
                } else if (ds[0]['so_tb'] == null || ds[0]['so_tb'] == null) {
                  return
                }
              }

              this.grcDSKhoSo.data = ds
              // grvDSKhoSo.BestFitColumns();
            }
            // xTabKhoSo.SelectedTabPageIndex = 0; // vuth update fix ts 0210
            this.TabKhoSoIndex = 0
            // CAP_NHAT_TRANGTHAI();
          } else if (this.vkho_id == this.KHO_TQ_ID) {
            // #region Trả sau tab kho gv chon so toàn quốc
            this.gcKHOTQTraSau.data = null
            if (ds != null) {
              // var ds = frm.Output as DataTable;
              if (ds != null && Object.keys(ds[0]).length == 1) {
                let key = Object.keys(ds[0])[0]
                this.ShowError('Dữ liệu tìm kiếm bị lỗi: ' + ds[0][key])
                return
              }
              this.gcKHOTQTraSau.data = ds
              this.FormatStatus(this.gcKHOTQTraSau.data, 'status_tt', 'status_tt_img')
              this.TabKhoSoIndex = 2
              // gvKhoTQTraSau.BestFitColumns();
            }
          } else {
            this.grcDSAllKho.data = null
            if (ds != null) {
              // var ds = frm.Output as DataTable;
              if (ds != null && ds.length == 1) {
                if (ds[0]['ID'] == null || ds[0]['ID'] == null) {
                  this.ShowWarning('' + ds[0]['MSI_KHO'])
                  return
                }
              }
              this.grcDSAllKho.data = ds
              this.TabKhoSoIndex = 1
              // grvDSAllKho.BestFitColumns();
            }
          }
          
          // #endregion
        }
        //  #endregion
      } catch (err) {
        console.error(err)
        this.Loading = false
        // this.ShowError(`Có lỗi khi tìm kiếm số ${err.message}`)
      } finally {
        this.Loading = false
      }
    },
    async btnDowLoad_ButtonClick() {
      this.KhoiTaoThueBaoTag = 'btnDowLoad_ButtonClick'
      await this.LAPHD_CHONSO()
    },
    async btnKhoiTao_TS_ButtonClick(data) {
      console.log('btnKhoiTao_TS_ButtonClick', data)
      //this.so_tb_kt = this.vsomay_khotinh
      this.so_tb_kt=data.MSISDN?data.MSISDN.toString().substring(2):''
      console.log('so_tb_kt', this.so_tb_kt)
      this.chonso = false
      this.KhoiTaoThueBaoTag = 'btnKhoiTao_TS_ButtonClick'
      if (this.so_tb_kt == '-1') {
        this.xacnhan = false
        this.ShowError('Không lấy được số máy đã chọn để khởi tạo')
      } else {
        this.sotb_goc = this.so_tb_kt
        
        // var frm = new frmKhoiTaoTB();
        this.kieu = this.vkieuform // vuth update fix 1609
        if (this.vkieuform == 0) {
          this.chonso = false
        } else {
          this.khoid = this.vkho_id
        } // vuth update 03/12/2019
        await this.NAP_THONGTIN()
        console.log('sotb_goc', this.sotb_goc)
        this.KhoiTao.SoMay=this.sotb_goc
        this.$bvModal.show('my-modal-khoi-tao')

        //frm.ShowDialog();
        // if (this.xacnhan) {
        //   await this.NAP_DS_KHOITAO()
        // }
      }
    },
    async tsbtnChonSo_Click() {
      console.log('🚀 ~ file: ChoosePreapaidNumber.vue ~ line 1936 ~ tsbtnChonSo_Click ~ tsbtnChonSo_Click')
      try {
        await this.CHONSO_DK()
      } catch (err) {
        console.log('🚀 ~ file: ChoosePreapaidNumber.vue ~ line 1940 ~ tsbtnChonSo_Click ~ err', err)
        this.Loading = false
        this.ShowError(`${err.message}`)
      } finally {
        this.Loading = false
      }
    },
    CHONSO_DK: async function () {
      console.log('🚀 ~ file: ChoosePreapaidNumber.vue ~ line 1946 ~ CHONSO_DK')
      try {
        // #region Kiểm tra

        //var dt = this.grcDSKhoSo.data
        if (this.grcDSKhoSo.data == null) {
          this.ShowError('Không lấy được dữ liệu danh sách kho số')
          return
        }
        if (this.grcDSKhoSo.data.length == 0) {
          this.ShowError('Chưa có dữ liệu kho số để chọn')
          return
        }
        var banghi = this.grcDSKhoSo.selectedItems.length
        if (banghi == 0) {
          this.ShowError('Chưa có số máy nào được chọn')
          return
        }
        // var ds_cdk = this.grcDS_ChuaDK.data
        if (this.grcDS_ChuaDK.data == null) {
          this.ShowError('Không lấy được dữ liệu danh sách chưa đăng ký')
          return
        }
        // #endregion
        // #region Gán dữ liệu
        for (let dr of this.grcDSKhoSo.selectedItems) {
          if (this.vkieuform == 0 && parseInt(dr['thoigian_sudung'].replace('Tháng', '')) > 0) {
            // vuth update fix 1409
            this.ShowError('Bạn đã chọn nhầm số cam kết trả sau , xin vui lòng chọn lại !!!')
            return
          }
          dr['ischeck'] = '0'
          if (dr['trangthai'] == '1') continue
          let row = {}
          row['so_tb'] = dr['so_tb']
          row['ischeck'] = '0'
          row['ten_kieuso'] = dr['ten_kieuso']
          row['cuocthang_toithieu'] = dr['cuocthang_toithieu']
          if (this.grcDS_ChuaDK.data.filter((x) => x.so_tb == dr['so_tb']).length == 0) {
            this.grcDS_ChuaDK.data.push(row)
          } else {
            this.ShowError(`Bạn đã chọn số tb này : ${dr['so_tb']}`)
          }
        }
        await this.CAP_NHAT_TRANGTHAI()
      } catch (err) {
        this.ShowError('Có lỗi khi chọn số ' + err.message)
      }
    },
    CHONSO_DK_OLD: async function () {
      var self = this
      if (this.cboKhoSo !== 14) {
        // console.log('Kho số chung ')
        if (this.grcDSKhoSo.data === null || this.grcDSKhoSo.data.length <= 0) {
          this.ShowAlert('Kho số không có dữ liệu')
          return
        }
        let selectedItems = this.grcDSKhoSo.selectedItems
        if (selectedItems.length <= 0) {
          this.ShowAlert('Bạn chưa chọn số trong kho số  !')
          return
        }
        selectedItems.forEach(function (item) {
          item.CHON = true
          if (self.grcDS_ChuaDK.data.filter((x) => x.so_tb === item.so_tb).length == 0) {
            self.grcDS_ChuaDK.data.push(item)
          } else {
            self.ShowAlert(`Số thuê bao ${item.so_tb} đã chọn !`)
          }
        })
        this.Selected()
      } else {
        // console.log('Kho số VTT ')
        if (this.grcDSAllKho.data === null || this.grcDSAllKho.data.length <= 0) {
          this.ShowAlert('Kho số không có dữ liệu')
          return
        }
        let selectedItems = this.grcDSAllKho.selectedItems
        if (selectedItems.length <= 0) {
          this.ShowAlert('Bạn chưa chọn số trong kho số VTT !')
          return
        }
        selectedItems.forEach(function (item) {
          item.CHON = true
          if (self.grcDS_ChuaDK.data.filter((x) => x.so_tb === item.so_tb).length === 0) {
            self.grcDS_ChuaDK.data.push(item)
          } else {
            self.ShowAlert(`Số thuê bao ${item.so_tb} đã chọn !`)
          }
        })
        this.Selected2()
      }
      this.updateui = !this.updateui
    },
    Selected: function () {
      var self = this
      this.grcDSKhoSo.data.forEach(function (item) {
        if (self.grcDS_ChuaDK.data.filter((x) => x.so_tb === item.so_tb).length > 0) {
          item.CHON = true
        } else {
          item.CHON = false
        }
      })
    },
    Selected2: function () {
      var self = this
      this.grcDSAllKho.data.forEach(function (item) {
        if (self.grcDS_ChuaDK.data.filter((x) => x.so_tb === item.so_tb).length > 0) {
          item.CHON = true
        } else {
          item.CHON = false
        }
      })
    },
    ShowNhapSo: function () {
      this.$bvModal.show('my-modal')
    },
    CloseNhapSo: function () {
      this.$bvModal.hide('my-modal')
    },
    NhapSo: function () {
      try {
        // grvDS_ChuaDK.CloseEditor();
        /*
        if (this.grcDS_ChuaDK.data == null || this.grcDS_ChuaDK.data.length == 0) {
          this.ShowError('Không lấy được dữ liệu danh sách chưa đăng ký')
          return
        }
        */
        // ds_cdk.AcceptChanges();
        if (true) {
          // nếu thay đổi
          var s = this.txtNhapSo
          let ds_tb = s.split(',')
          for (let r of ds_tb) {
            let banghi = this.grcDS_ChuaDK.data.filter((x) => x.so_tb == '84' + r || x.so_tb == r).length
            if (banghi > 0) {
              continue
            }
            var row = {}
            row['iskieu'] = '0'
            row['ten_kieuso'] = 'Chọn số thủ công'
            row['cuocthang_toithieu'] = 'Chọn số thủ công'
            if (r.startsWith('84')) {
              row['so_tb'] = '0' + r.Remove(0, 2)
            } else {
              row['so_tb'] = r
            }
            this.grcDS_ChuaDK.data.push(row)
          }

          // ds_cdk.AcceptChanges();

          // #region Distinct lại dữ liệu
          // var view = new DataView(ds_cdk);
          // var distinctValues = view.ToTable(true, "so_tb", "TEN_KIEUSO", "CUOCTHANG_TOITHIEU");
          // var check = new DataColumn("ISCHECK", typeof(String));
          // check.DefaultValue = "0";
          // distinctValues.Columns.Add(check);
          this.grcDS_ChuaDK.data = this.grcDS_ChuaDK.data.slice(0)
          // grvDS_ChuaDK.BestFitColumns();
          // if (colKieuSo.Width > 250)
          //     colKieuSo.Width = 250;
          // #endregion
          if (this.txtNhapSo.length > 0) {
            this.$bvModal.hide('my-modal')
          }
        }
      } catch (err) {
        this.ShowError(`Có lỗi khi nhập số máy ${err.message}`)
      }
      /*
      var so = this.grcDSKhoSo.data.filter((s) => s.so_tb === this.txtNhapSo)
      if (so.length === 1) {
        let item = so[0]
        if (this.grcDS_ChuaDK.data.filter((x) => x.so_tb === this.txtNhapSo).length === 0) {
          this.grcDS_ChuaDK.data.push(item)
        } else {
          this.ShowAlert(`Số thuê bao ${this.txtNhapSo} đã được chọn !`)
        }
      } else {
        this.ShowAlert('Không tìm thấy số kho số tìm kiếm ')
      }
      */
    },
    XoaSo: function () {
      /*
      var xoaChonSo = this.grcDS_ChuaDK.data.filter(x => x.ischecked)
      if (xoaChonSo.length > 0) {
        this.grcDS_ChuaDK.data = this.grcDS_ChuaDK.data.filter(x => !x.ischecked)
      }
      */
      // this.grcDS_ChuaDK.data = []
      this.Selected()
      this.updateui = !this.updateui
    },
    DANGKY: async function () {
      const self = this
      if (this.grcDS_ChuaDK.data == null) {
        this.ShowError('Không lấy được dữ liệu danh sách số đang chờ đăng ký')
        return
      }
      if (this.grcDS_ChuaDK.data == 0) {
        this.ShowError('Chưa có danh sách số máy để đăng ký')
        return
      }
      if (this.grcDS_ChuaDK.selectedItems.length == 0) {
        this.ShowError('Chưa có số máy nào được chọn')
        return
      }
      let dsSoDaChon = this.grcDS_ChuaDK.selectedItems
      this.$bvModal
        .msgBoxConfirm('Bạn có chắc chắn muốn đăng ký danh sách các số này không??', {
          title: '',
          size: 'sm',
          okTitle: 'Đồng ý',
          cancelTitle: 'Hủy'
        })
        .then(async (v) => {
          if (!v) {
            return
          }
          // Xử lý code ở đây
          // Xac nhan capcha
          let ds_thuebao = dsSoDaChon.map((x) => '84' + ((x.so_tb && x.so_tb.indexOf('0')==0 && x.so_tb.length>=10)?x.so_tb.substring(1):x.so_tb)).join(',')//dsSoDaChon.map((x) => '84' + self.trim(x.so_tb, '0')).join(',')
          var kq = await this.tt_1_chonso(ds_thuebao, 'DHSX')
          if (kq == null || kq == '' || kq.length == 0) {
            // vuth update 14/12
            this.ShowError('Không có dữ liệu trả về')
            return
          }
          if (kq == 1) {
            this.ShowSuccess('Đăng ký thành công ')
          } else {
            this.ShowError(kq)
          }
          await this.NAP_DS_SODANGKY()
          var ds_dagan = this.grcDS_DaDK.data
          if (ds_dagan == null) {
            return
          }
          if (ds_dagan.length == 0) return
          console.info('x',JSON.stringify(this.grcDS_ChuaDK.data),'y',JSON.stringify(ds_dagan));
          var i = 1;
          this.grcDS_ChuaDK.data = this.grcDS_ChuaDK.data.filter((x) => ds_dagan.filter((y) => x.so_tb.substring(x.so_tb.length-9)==y.SO_TB.substring(y.SO_TB.length-9)).length == 0)//this.grcDS_ChuaDK.data.filter((x) => ds_dagan.filter((y) => x.so_tb == '84' + y.SO_TB || x.so_tb == '84' + y.SO_TB.substring(1) || '84'+x.so_tb.substring(1)==y.SO_TB || x.so_tb == this.trim(y.SO_TB, '0')).length == 0)
          this.grcDS_ChuaDK.data.forEach(function (item) {
            item.STT = i
            i++
          })
          /*
           for (var dr of soDaChon) {
            // vuth update fix 0210
            // var banghi = ds_dagan.Select("so_tb='84" + dr["so_tb"].ToString().TrimStart('0') + "'" +
            //                              " OR so_tb='" + dr["so_tb"].ToString().TrimStart('0') + "'").Length;
            var banghi = ds_dagan.filter((x) => x.so_tb == '84' + dr['so_tb'] || xso_tb == this.trimStart(dr['so_tb'],'0')).length
            if (banghi > 0) {
              //dt.Rows.Remove(dr);

            }
          }
          */
          await this.TRACUU_KHOSO_TQ()
        })
    },
    async NAP_THONGTIN() {
      // console.log('NAP_THONGTIN')
      try {
        if (this.sotb_goc == '') return
        let tb = this.sotb_goc
        if (this.khoid == '') return
        this.kho_id = this.khoid
        if (this.kho_id == '14') {
          this.labelControl3 = true
          this.txtMaXT = true
          this.txtMaXT_Enable = true
        }

        // this.KhoiTao.SoMay = tb.substring(2)
        if (this.kieu == 0 && this.chonso == true) {
          // trả trước chon so 0210
          // #region COMBO loại hình
          // bts.HT_DS_LOOKUPEDIT(cboLoaiTB, ccbs.tt_2_khoitao_tratruoc_product1(), "ID", "value");
          let tmp01 = await this.ts_khoitao_tratruoc_ds_loaitb()
          /*
                    string sql02 = "select tentat_neo value,tengoicuoc ID from " + DatabaseConstants.DB2 + ".goicuoc_dd where loaitb_id=21 and tentat_neo is not null ";
                    DataTable tmp02 = tdan.GET_DATA_SQL(sql02);
                    var linq_resutl = from row2 in tmp02.AsEnumerable()
                                      join row1 in tmp01.AsEnumerable()
                                           on row2.Field<string>("value") equals row1.Field<string>("LOAITB_ID")
                                      select row2;
                    */
          // let tmp02 = await this.DANHMUC_GOICUOC_DD_TT()
          let tmp02 = await this.sp_chonso_kh_danhmuc('', 'DANHMUC_GOICUOC_DD_TT')

          let linq_resutl = tmp02.filter((x) => tmp01.filter((y) => y.LOAITB_ID == x.value).length > 0)
          let dt_goicuoc = []
          if (linq_resutl != null && linq_resutl.length > 0) dt_goicuoc = linq_resutl
          // bts.HT_DS_LOOKUPEDIT(cboLoaiTB, dt_goicuoc, "ID", "value");
          this.DsLoaiTB = dt_goicuoc.map((x) => ({ id: x.value, text: x.id }))
          // #endregion
        }
        if (this.kieu == 0 && this.chonso == false) {
          // vuth update fix 0210
          // bts.HT_DS_LOOKUPEDIT(cboLoaiTB, ccbs.tt_2_khoitao_tratruoc_product1(), "ID", "value");
          // DataTable tmp01 = ccbs.ts_khoitao_ds_loaitb(DatabaseConstants.DB2.Substring(4).ToUpper(), KHO_TT_TINH_ID);
          let tmp01 = await this.ts_khoitao_ds_loaitb(4)
          // string sql02 = "select tentat_neo value,tengoicuoc ID from " + DatabaseConstants.DB2 + ".goicuoc_dd where loaitb_id=21 and tentat_neo is not null ";
          // DataTable tmp02 = tdan.GET_DATA_SQL(sql02);
          // var linq_resutl = from row2 in tmp02.AsEnumerable()
          //                   join row1 in tmp01.AsEnumerable()
          //                        on row2.Field<string>("value") equals row1.Field<string>("LOAITB_ID")
          //                   select row2;
          // let tmp02 = await this.DANHMUC_GOICUOC_DD_TT()
          let tmp02 = await this.sp_chonso_kh_danhmuc('', 'DANHMUC_GOICUOC_DD_TT')
          let linq_resutl = tmp02.filter((x) => tmp01.filter((y) => y.LOAITB_ID == x.value).length > 0)
          console.log('tmp02', tmp02, 'tmp01', tmp01, 'linq_resutl', linq_resutl)
          let dt_goicuoc = []
          if (linq_resutl != null && linq_resutl.length > 0) {
            dt_goicuoc = linq_resutl
            // bts.HT_DS_LOOKUPEDIT(cboLoaiTB, dt_goicuoc, "ID", "value");
            this.DsLoaiTB = dt_goicuoc.map((x) => ({ id: x.value, text: x.id }))
          } else {
            this.ShowError('Không lấy được giá trị loại thuê bao! Hãy thử lại!')
            this.Close()
            return
          }
        }
        if (this.kieu == 1) {
          console.log('NAP_THONGTIN:', 'kieu', this.kieu, 'khoid', this.vkho_id)
          // trả sau
          // #region COMBO loại hình
          // DataTable tmp01 = ccbs.ts_khoitao_ds_loaitb(DatabaseConstants.DB2.Substring(4).ToUpper(), khoid);
          let tmp01 = await this.ts_khoitao_ds_loaitb(this.vkho_id)
          // let tmp02 = await this.DANHMUC_GOICUOC_DD_TT()
          // let tmp02 = await this.sp_ht_prefix_lhtb(20)
          let tmp02 = await this.sp_chonso_kh_danhmuc('', 'DANHMUC_GOICUOC_DD_TS')
          // string sql02 = "select tentat_neo value,tengoicuoc ID from " + DatabaseConstants.DB2 + ".goicuoc_dd where loaitb_id=20 and tentat_neo is not null ";
          // DataTable tmp02 = tdan.GET_DATA_SQL(sql02);
          // var linq_resutl = from row2 in tmp02.AsEnumerable()
          //                   join row1 in tmp01.AsEnumerable()
          //                        on row2.Field<string>("value") equals row1.Field<string>("LOAITB_ID")
          //                   select row2;
          let linq_resutl = tmp02.filter((x) => tmp01.filter((y) => y.LOAITB_ID == x.value).length > 0)
          //console.log('Danh sách loại thuê bao trả sau:', linq_resutl)
          let dt_goicuoc = []
          if (linq_resutl != null && linq_resutl.length > 0) {
            dt_goicuoc = linq_resutl
            // bts.HT_DS_LOOKUPEDIT(cboLoaiTB, dt_goicuoc, "ID", "value");
            this.DsLoaiTB = dt_goicuoc.map((x) => ({ id: x.value, text: x.id }))
          } else {
            this.ShowError(`Không lấy được giá trị loại thuê bao! Hãy thử lại! ${this.kieu}`)
            this.Close()
            return
          }
          // #endregion
        }
      } catch (err) {
        console.log('NAP_THONGTIN-ERROR:', err)
        this.ShowError('Có lỗi khi lấy thông tin ' + err.message)
      }
    },
    trimStart: function (str, c) {
      if (str.length == 0) return str
      c = c || ' '
      var i = 0
      for (; i < str.length && str.charAt(i) == c; i++) {
        return str.substring(i)
      }
    },
    ShowKhoiTaoThueBao: function (item) {
      this.$bvModal.show('my-modal-khoi-tao')
    },
    async count_goicuoc_dd() {
      let phanvungId = this.$root.token.getPhanVungID()
      // let input = {
      //   id_neo: 'count(*)',
      //   in_table: 'css.goicuoc_dd',
      //   in_dk: ` phanvungId=${phanvungId}  &&  tentat_neo==${this.KhoiTao.LoaiTB}`
      // }
      // let data = this.GetData(await api.map_id(this.axios, input))
      let data = this.GetData(await api.fn_tt_goicuoc_dd(this.axios, { param: this.KhoiTao.LoaiTB, type: 3 }))
      return data
    },
    async count_khoso_dd() {
      let phanvungId = this.$root.token.getPhanVungID()
      let nguoidungId = this.$root.token.getNguoiDungID()
      // let input = {
      //   id_neo: 'count(*)',
      //   in_table: 'css.goicuoc_dd',
      //   in_dk: `nguoidung_id=${nguoidungId} and somay=${this.KhoiTao.SoMay} and simcard=${this.KhoiTao.MSIN}`
      // }
      // let data = this.GetData(await api.map_id(this.axios, input))
      let data = this.GetData(await api.fn_tt_goicuoc_dd(this.axios, { param: nguoidungId, param1: this.KhoiTao.SoMay, param2: this.KhoiTao.MSIN, type: 4 }))
      return data
    },

    UpperCasePropertyList(obj) {
      return obj.map(function(item) {
        for (let key in item) {
          let upper = key.toUpperCase()
          // check if it already wasn't uppercase
          if (upper !== key) {
            item[upper] = item[key]
            delete item[key]
          }
        }
        return item
      })
    },
    async KHOITAO_TT() {
      try {
        let key = '0'
        this.xacnhan = false
        if (this.KhoiTao.LoaiTB == null || this.KhoiTao.LoaiTB == '') {
          this.ShowError('Chưa có dữ liệu loại thuê bao')
          return
        }
        // #region vuth update fix 1609 : bỏ zem để check db số máy đã khởi tạo chưa cho nhanh.
        /*  var d = tdan.GET_DATA_SQL('select count(*) from {?DB2}.goicuoc_dd where tentat_neo=:vtt', 'vtt',
           this.KhoiTao.LoaiTB) */
        /*
        let d = await this.count_goicuoc_dd()
        if (d == 0) {
          let text = this.DsLoaiTB.filter((x) => x.id == this.KhoiTao.LoaiTB)[0].value
          this.ShowError('Loại thuê bao ' + text + ' (' + this.KhoiTao.LoaiTB + ') chưa được khai báo dưới tỉnh')
          return
        }
        if (this.KhoiTao.MSIN == '') {
          this.ShowError('Bạn chưa nhập số SIM')
          return
        }

        /*  var ds = tdan.GET_DATA_SQL('select count(*) from {?DB2}.khoso_dd where nguoidung_id=:vnguoidung_id and somay=:vsomay and simcard=:vsosim',
           'vnguoidung_id', ttnd.nguoidung_id, 'vsomay', txtSoMay.Text, 'vsosim', txtSoMsin.Text) */
        /*
        let ds = await this.count_khoso_dd()
        key = Object.keys(ds[0])
        if (ds != '0') {
          this.ShowError('Số máy này đã được khởi tạo. Bạn hãy kiểm tra lại')
          return
        }
        */
        let d = await this.fn_khoitao_tb_didong(0, 1)
        if (d.KETQUA == 0) {
          this.ShowError(d.THONGBAO)
          return
        }
        if (d.KETQUA != 1) return
        // #endregion
        /*   if (DialogResult.No == Message_Box.ShowQuestion('Bạn có muốn khởi tạo số 84' + txtSoMay.Text + ' với loại thuê bao ' + cboLoaiTB.Text + ' không?')) {
            return
          } */
        let cboLoaiTbText = this.DsLoaiTB.filter((x) => x.id == this.KhoiTao.LoaiTB)[0].text
        this.$bvModal
          .msgBoxConfirm(`Bạn có muốn khởi tạo số 84${this.KhoiTao.SoMay}  với loại thuê bao ${cboLoaiTbText} không ?`, {
            title: '',
            size: 'sm',
            okTitle: 'Đồng ý',
            cancelTitle: 'Hủy'
          })
          .then(async (v) => {
            if (!v) {
              return
            }
            // Xử lý code ở đây
            // Lamnt: Bổ sung thêm trwongf ma_tinh - 15/03/2018
            // var kq = ccbs.tt_2_khoitao_tratruoc("84" + txtSoMay.Text, cboLoaiTB, txtSoMsin.Text, DatabaseConstants.DB2.Substring(4).ToUpper()); Đổi hàm theo yêu cầu của Luật
            this.loaitb = this.KhoiTao.LoaiTB
            switch (this.loaitb) {
              case 'MEG':
                this.pmeg = '1'
                break
              case 'STK':
                this.pmeg = '2'
                break
              case 'VCC':
                this.pmeg = '3'
                break
              case 'KARO':
                this.pmeg = '4'
                break
              default:
                this.pmeg = '0'
                break
            }

            // var kq = ccbs.tt_1_khoitao_chonso_trasau("", ttnd.USER_NEO, DatabaseConstants.DB2.Substring(4).ToUpper(),
            //                        txtSoMsin.Text.Trim(), loaitb, KHO_TT_TINH_ID, "84" + txtSoMay.Text.Trim(), pmeg, txtGhiChu.Text.Trim());
            this.loading(true)
            let kq = await this.tt_1_khoitao_chonso_trasau()
            this.loading(false)
            if (kq == null) {
              this.ShowError('Không có dữ liệu trả về')
              return
            }
            // key = Object.keys(kq[0])[0]
            if (kq == '3') {
              // vuth bỏ zem để update số khởi tạo thành công vào khoso_dd  --update 1709 : insert them user_neo
              let z = await this.fn_khoitao_tb_didong(1, 1)
              /*
              var z = tdan.EXECUTE_SQL(@"
                       Insert into {?DB2}.khoso_dd (somay, simcard, donvi_id, trangthaiso_id, ghichu,
                                       ngay_cn, nguoidung_id, nguoi_cn, may_cn, ip_cn,user_neo, goicuoc_id)
                      select :vsomay,:vsimcard,:vdonvi_id,1,:vghichu,sysdate,:vnguoidung_id,:vnguoi_cn,:vmay_cn,:vip_cn,:vuser_neo,
                       (select goicuoc_id from {?DB2}.goicuoc_dd where tentat_neo=:vtentat_neo and loaitb_id=21) from dual",
                 "vsomay", txtSoMay.Text, "vsimcard", txtSoMsin.Text,
                 "vdonvi_id", ttnd.donvi_id, "vghichu", txtGhiChu.Text, "vnguoidung_id", ttnd.nguoidung_id, "vnguoi_cn", ttnd.ma_nd,
                 "vmay_cn", ttnd.may_cn, "vip_cn", ttnd.ip, "vuser_neo", ttnd.USER_NEO, "vtentat_neo", cboLoaiTB);
             */
              if (z.KETQUA == 1) {
                this.$toast.success('Khởi tạo thành công')
                //this.ShowError('Khởi tạo thành công')
                this.xacnhan = true
                this.Close()
              } else {
                this.ShowError('Đã khởi tạo trên CCBS thành công. Lưu dữ liệu về tỉnh có lỗi')
              }
            } else {
              // let error = kq[key]
              // if (this.DS_KHOITAO_CHONSO_TS.filter((x) => x.id == error).length > 0) {
              //   this.ShowError(this.DS_KHOITAO_CHONSO_TS.filter((x) => x.id == error)[0].text)
              // } else {
              //   this.ShowError(error)
              // }
              this.ShowError(kq)
            }
          })
      } catch (err) {
        this.ShowError(`Có lỗi khi khởi tạo ${err.message}`)
      }
    },
    Close() {
      this.$bvModal.hide('my-modal')
      if (this.xacnhan) {
        if (this.KhoiTaoThueBaoTag == 'btnKhoiTao_TS_ButtonClick') {
          this.KhoiTaoThueBaoTag = ''
          Promise.all([this.NAP_DS_KHOITAO()])
            .then((response) => {
              console.log(response)
            })
            .finally(() => {
              this.Loading = false
            })
        } else if (this.KhoiTaoThueBaoTag == 'btnChon_ButtonClick') {
          Promise.all([this.NAP_DS_KHOITAO(), this.NAP_DS_SODANGKY()])
            .then((response) => {
              console.log(response)
            })
            .finally(() => {
              this.Loading = false
            })
        } else if (this.KhoiTaoThueBaoTag == 'btnDowLoad_ButtonClick') {
          // Promise.all([this.TRACUU_KHOSO_TQ(), this.NAP_DS_SODANGKY()])
          //   .then((response) => {
          //     console.log(response)
          //   })
          //   .finally(() => {
          //     this.Loading = false
          //   })
        }
      }
    },
    async KHOITAO_TS() {
      try {
        this.xacnhan = false
        if (this.KhoiTao.LoaiTB == null || this.KhoiTao.LoaiTB == '') {
          this.ShowError(`Chưa có dữ liệu loại thuê bao`)
          // this.KhoiTao.LoaiTB = 1
          return
        }
        // #region vuth update fix 1609 : bỏ zem để check db số máy đã khởi tạo chưa cho nhanh.
        /*  var d = tdan.GET_DATA_SQL("select count(*) from {?DB2}.goicuoc_dd where tentat_neo=:vtt", "vtt",
         cboLoaiTB.EditValue.ToString()); */
        /*
        let d = await this.count_goicuoc_dd()
        if (d == '0') {
          this.ShowError('Loại thuê bao ' + cboLoaiTB.Text + ' (' + cboLoaiTB.EditValue + ') chưa được khai báo dưới tỉnh')
          return
        }
        if (this.KhoiTao.MSIN == '') {
          this.ShowError('Bạn chưa nhập số SIM')
          return
        }
        */

        /*  var ds = tdan.GET_DATA_SQL("select count(*) from {?DB2}.khoso_dd where nguoidung_id=:vnguoidung_id and somay=:vsomay and simcard=:vsosim",
           "vnguoidung_id", ttnd.nguoidung_id, "vsomay", txtSoMay.Text, "vsosim", txtSoMsin.Text); */
        /*
        let ds = await this.count_khoso_dd()
        if (ds != '0') {
          this.ShowError('Số máy này đã được khởi tạo. Bạn hãy kiểm tra lại')
          return
        }
        */
        // #endregion

        /*   if (DialogResult.No == Message_Box.ShowQuestion('Bạn có muốn khởi tạo số 84' + txtSoMay.Text + ' với loại thuê bao ' + cboLoaiTB.Text + ' không?')) {
            return
          } */
        let d = await this.fn_khoitao_tb_didong(0, 2)
        if (d == null || d == '') {
          this.ShowError(`Lỗi kết nối !`)
          return
        }

        if (d.KETQUA == 0) {
          this.ShowError(d.THONGBAO)
          return
        } /*
        let loaitb= this.KhoiTao.LoaiTB
        let a = this.DsLoaiTB.filter((x) => x.id ==loaitb)
        console.log('cboLoaiTbText', a)
        let cboLoaiTbText = (a == null || a.length==0) ? loaitb : a[0].text
        */
        let cboLoaiTbText = this.DsLoaiTB.filter((x) => x.id == this.KhoiTao.LoaiTB)[0].text
        this.$bvModal
          .msgBoxConfirm(`Bạn có muốn khởi tạo số 84${this.KhoiTao.SoMay} với loại thuê bao ${cboLoaiTbText} không ?`, {
            title: '',
            size: 'sm',
            okTitle: 'Đồng ý',
            cancelTitle: 'Hủy'
          })
          .then(async (v) => {
            if (!v) {
              return
            }
            // Xử lý code ở đây
            // Lamnt: Bổ sung thêm trwongf ma_tinh - 15/03/2018
            let loaitb = this.KhoiTao.LoaiTB
            switch (loaitb) {
              case 'MEG':
                this.pmeg = '1'
                break
              case 'STK':
                this.pmeg = '2'
                break
              case 'VCC':
                this.pmeg = '3'
                break
              case 'KARO':
                this.pmeg = '4'
                break
              default:
                this.pmeg = '0'
                break
            }
            let pxacthuc = '00000000'
            if (this.kho_id == '1') pxacthuc = ''
            if (this.kho_id == '14') pxacthuc = this.KhoiTao.MaXacThuc // update otp
            /* var kq = ccbs.tt_1_khoitao_chonso_trasau(pxacthuc, ttnd.USER_NEO, DatabaseConstants.DB2.Substring(4).ToUpper(),
            txtSoMsin.Text.Trim(), loaitb, kho_id, "84" + txtSoMay.Text.Trim(), pmeg, txtGhiChu.Text.Trim()); */
            let kq = await this.tt_1_khoitao_chonso_trasau()
            if (kq == null || kq == '') {
              this.ShowError('Không có dữ liệu trả về')
              return
            }
            if (kq == '3') {
              // vuth bỏ zem để update số khởi tạo thành công vào khoso_dd  --update 1709 : insert them user_neo
              let z = await this.fn_khoitao_tb_didong(1, 2)

              /*  var z = tdan.EXECUTE_SQL(@"
             Insert into {?DB2}.khoso_dd (somay, simcard, donvi_id, trangthaiso_id, ghichu,
             ngay_cn, nguoidung_id, nguoi_cn, may_cn, ip_cn,user_neo, goicuoc_id)
             select :vsomay,:vsimcard,:vdonvi_id,1,:vghichu,sysdate,:vnguoidung_id,:vnguoi_cn,:vmay_cn,:vip_cn,:vuser_neo,
             (select goicuoc_id from {?DB2}.goicuoc_dd where tentat_neo=:vtentat_neo and loaitb_id=20) from dual",
             "vsomay", txtSoMay.Text, "vsimcard", txtSoMsin.Text,
               "vdonvi_id", ttnd.donvi_id, "vghichu", txtGhiChu.Text, "vnguoidung_id", ttnd.nguoidung_id, "vnguoi_cn", ttnd.ma_nd,
               "vmay_cn", ttnd.may_cn, "vip_cn", ttnd.ip, "vuser_neo", ttnd.USER_NEO, "vtentat_neo", cboLoaiTB.EditValue.ToString()); */
              if (z.KETQUA == 1) {
                this.ShowSuccess('Khởi tạo thành công')
                this.xacnhan = true
                this.Close()
              } else {
                this.ShowError('Đã khởi tạo trên CCBS thành công. Lưu dữ liệu về tỉnh có lỗi')
              }
            } else {
              let error = kq
              if (this.DS_KHOITAO_CHONSO_TS.filter((x) => x.id == error).length > 0) {
                this.ShowError(this.DS_KHOITAO_CHONSO_TS.filter((x) => x.id == error)[0].text)
              } else {
                this.ShowError(error)
              }
            }
          })
      } catch (err) {
        this.ShowError('Có lỗi khi khởi tạo ' + err.message)
      }
    },
    async KHOITAO_CHONSO_TT() {
      // vuth update them 0210
      try {
        this.xacnhan = false
        if (this.KhoiTao.LoaiTB == null || this.KhoiTao.LoaiTB == '') {
          this.ShowError('Chưa có dữ liệu loại thuê bao')
          return
        }
        // #region vuth update fix 1609 : bỏ zem để check db số máy đã khởi tạo chưa cho nhanh.
        /* var d = tdan.GET_DATA_SQL('select count(*) from {?DB2}.goicuoc_dd where tentat_neo=:vtt', 'vtt', this.KhoiTao.LoaiTB) */
        // let d = await this.count_goicuoc_dd()

        let d = await this.fn_khoitao_tb_didong(0, 1)
        if (d.KETQUA == 0) {
          this.ShowError(d.THONGBAO)
          return
        }
        /*
        let text = this.DsLoaiTB.filter((x) => x.id == this.KhoiTao.LoaiTB)[0].value
        if (d == '0') {
          this.ShowError('Loại thuê bao ' + this.KhoiTao.LoaiTB + ' (' + text + ') chưa được khai báo dưới tỉnh')
          return
        }
        if (this.KhoiTao.MSIN == '') {
          this.ShowError('Bạn chưa nhập số SIM')
          return
        }
        // var ds = tdan.GET_DATA_SQL("select count(*) from {?DB2}.khoso_dd where nguoidung_id=:vnguoidung_id and somay=:vsomay and simcard=:vsosim",
        //         "vnguoidung_id", ttnd.nguoidung_id, "vsomay", txtSoMay.Text, "vsosim", txtSoMsin.Text);
        let ds = await this.count_khoso_dd()
        if (ds != '0') {
          this.ShowError('Số máy này đã được khởi tạo. Bạn hãy kiểm tra lại')
          return
        }
        */
        // #endregion
        // if (DialogResult.No == Message_Box.ShowQuestion("Bạn có muốn khởi tạo số 84" + txtSoMay.Text + " với loại thuê bao " + cboLoaiTB.Text + " không?"))
        //     return;
        let loaitb_text = this.DsLoaiTB.filter((x) => x.id == this.KhoiTao.LoaiTB)[0].text
        this.$bvModal
          .msgBoxConfirm(`Bạn có muốn khởi tạo số 84${this.KhoiTao.SoMay}  với loại thuê bao ${loaitb_text} + " không?`, {
            title: '',
            size: 'sm',
            okTitle: 'Đồng ý',
            cancelTitle: 'Hủy'
          })
          .then(async (v) => {
            if (!v) {
              return
            }
            //
            let tt_nd = this.$root.token.getThongTinNguoiDung()
            // let loaiSim = this.KhoiTao.LoaiMSIN == 0 ? '64' : '128' // vuth update fix 3009
            let loaiSim = this.KhoiTao.LoaiMSIN
            /* var kq = ccbs.tt_1_khoitao_chonso_tratruoc('', ttnd.USER_NEO, DatabaseConstants.DB2.Substring(4).ToUpper(), txtSoMsin.Text.Trim(), cboLoaiTB, '84' + txtSoMay.Text.Trim(), loaiSim, txtGhiChu.Text.Trim(), ttnd.ip) */
            let kq = await this.tt_1_khoitao_chonso_tratruoc()
            if (kq == null || kq == '') {
              this.ShowError('Không có dữ liệu trả về')
              return
            }
            if (kq == '3') {
              // let z = 1
              let z = await this.fn_khoitao_tb_didong(1, 1)
              // vuth bỏ zem để update số khởi tạo thành công vào khoso_dd  --update 1709 : insert them user_neo
              // var z = tdan.EXECUTE_SQL(@"
              //          Insert into {?DB2}.khoso_dd (somay, simcard, donvi_id, trangthaiso_id, ghichu,
              //                          ngay_cn, nguoidung_id, nguoi_cn, may_cn, ip_cn,user_neo, goicuoc_id)
              //         select :vsomay,:vsimcard,:vdonvi_id,1,:vghichu,sysdate,:vnguoidung_id,:vnguoi_cn,:vmay_cn,:vip_cn,:vuser_neo,
              //          (select goicuoc_id from {?DB2}.goicuoc_dd where tentat_neo=:vtentat_neo and loaitb_id=21) from dual",
              //    "vsomay", txtSoMay.Text, "vsimcard", txtSoMsin.Text,
              //    "vdonvi_id", ttnd.donvi_id, "vghichu", txtGhiChu.Text, "vnguoidung_id", ttnd.nguoidung_id, "vnguoi_cn", ttnd.ma_nd,
              //    "vmay_cn", ttnd.may_cn, "vip_cn", ttnd.ip, "vuser_neo", ttnd.USER_NEO, "vtentat_neo", cboLoaiTB);
              if (z.KETQUA == 1) {
                this.ShowSuccess('Khởi tạo thành công')
                this.xacnhan = true
                this.Close()
              } else {
                this.ShowError('Đã khởi tạo trên CCBS thành công. Lưu dữ liệu về tỉnh có lỗi')
              }
            } else {
              let error = kq
              if (this.DS_KHOITAO_CHONSO_TT.filter((x) => x.id == error).length > 0) {
                this.ShowError(this.DS_KHOITAO_CHONSO_TT.filter((x) => x.id == error)[0].text)
              } else {
                this.ShowError(error)
              }
            }
          })
      } catch (err) {
        this.ShowError('Có lỗi khi khởi tạo ' + err.message)
      }
    },
    // frmKhoiTaoTB:btnCapNhat_Click()
    async frmKhoiTaoTB_btnCapNhat_Click() {
      try {
        this.Loading = true
        if (this.KhoiTao.LoaiTB == null || this.KhoiTao.LoaiTB == '') {
          this.ShowError('Chưa có dữ liệu loại thuê bao')
          return
        }
        if (this.KhoiTao.MSIN == null || this.KhoiTao.MSIN == '') {
          this.ShowError('Bạn chưa nhập số SIM')
          return
        }
        if (this.kieu == 0 && this.chonso) {
          // vuth update 0210
          console.log('KHOITAO_CHONSO_TT')
          await this.KHOITAO_CHONSO_TT()
        }
        if (this.kieu == 0 && !this.chonso) {
          // vuth update 0210
          console.log('KHOITAO_TT')
          await this.KHOITAO_TT()
        }
        if (this.kieu == 1) {
          console.log('KHOITAO_TS')
          await this.KHOITAO_TS()
        }
        //THEM 20230820 - ThangDM1 reload du lieu sau dang ky
        await Promise.all([this.NAP_DS_SODANGKY(), this.NAP_DS_KHOITAO()]).catch((err) => {
          console.log('RELOAD: Promise.all', err)
        })
        this.Loading = false
      } catch (err) {
        this.Loading = false
      } finally {
        this.Loading = false
      }
    },
    KhoiTaoThueBao: async function () {
      let ma_nhanvien_ccbs = this.$root.token.getMaCCBS()
      let ma_tinh = this.$root.token.getMaTinh()
      var input = {
        ghichu: this.KhoiTao.GhiChu,
        imsi: this.KhoiTao.MSIN,
        kho: this.cboKhoSo,
        ma_xac_thuc: this.txtMaXacThuc,
        msisdn: this.KhoiTao.MSIN,
        ploai: this.KhoiTao.LoaiMSIN,
        tratruoc_tq: 1,
        userid: ma_nhanvien_ccbs,
        ma_tinh: ma_tinh
      }
      if (this.KhoiTao.SoMay === undefined || this.KhoiTao.SoMay.length <= 9) {
        this.ShowAlert('Thông tin số máy bắt buộc ')
        return
      }
      if (this.KhoiTao.MSIN === undefined || this.KhoiTao.MSIN.length <= 9) {
        this.ShowAlert('Thông tin MSIN bắt buộc ')
        return
      }
      this.$bvModal
        .msgBoxConfirm(`Bạn muốn khởi tạo số ${this.KhoiTao.SoMay} với loại thuê bao CARD không ?`, {
          title: '',
          size: 'sm',
          okTitle: 'Đồng ý',
          cancelTitle: 'Hủy'
        })
        .then(async (v) => {
          if (!v) {
            return
          }
          // Xử lý code ở đây
          let response = await api.KhoiTaoThueBao(this.axios, input)
          if (response.data.error_code === 'BSS-00000000') {
            let a = await this.NAP_DS_KHOITAO()
            // let b = this.DS_KHOSO_KHOITAO_USER()
            Promise.all([a])
            this.$bvModal.hide('my-modal-khoi-tao')
          } else {
            this.ShowAlert(` Thông báo lỗi  ${response.data.message} ,mã lỗi:${response.data.error_code}`)
          }
        })

      /*
      if (confirm(`Bạn muốn khởi tạo số ${this.KhoiTao.SoMay} với loại thuê bao CARD không ?`)) {

      }
      */
    },
    // frmKhoiTaoTB_btnLayTT_Click
    LamMoiKhoiTaoThueBao: function () {
      console.log('LamMoiKhoiTaoThueBao', this)
      this.KhoiTao.SoMay = this.sotb_goc.substring(2)
      this.KhoiTao.MSIN = ''
      this.KhoiTao.GhiChu = ''
      this.$refs.refKhoiTaoMSIN.focus()
    },
    async LAPHD_CHONSO() {
      try {
        this.$bvModal
          .msgBoxConfirm(`Bạn có chắc chắn muốn chọn số  ${this.vsomay_khochung_tinh} không ?`, {
            title: '',
            size: 'sm',
            okTitle: 'Đồng ý',
            cancelTitle: 'Hủy'
          })
          .then(async (v) => {
            if (!v) {
              return
            }
            // Xử lý code ở đây
            var kq = await this.ts_laphd_chonso_khotinh(this.vsomay_khochung_tinh)
            if (kq == '1') {
              this.ShowSuccess('Chọn số thành công')
              await this.TRACUU_KHOSO_TQ()
              await this.NAP_DS_SODANGKY()
            } else {
              this.ShowError('Chọn số thất bại : ' + kq)
            }
          })
      } catch (ex) {
        this.ShowError('Có lỗi khi chọn số kho tỉnh ' + ex.message)
      }
    },
    async KHOTQ_CHONSO_TS() {
      try {
        let somay = this.vsomay_toanquoc_trasau
        this.$bvModal
          .msgBoxConfirm(`Bạn có chắc chắn muốn chọn số ${somay} không ?`, {
            title: '',
            size: 'sm',
            okTitle: 'Đồng ý',
            cancelTitle: 'Hủy'
          })
          .then(async (v) => {
            if (!v) {
              return
            }
            this.MaXacThucFunc = 'KHOTQ_CHONSO_TS'
            // this.OpenMaXacThuc()
            // Xử lý code ở đây
            var kq = await this.tt_1_chonso(somay, 'DHSX')
            if (kq == null || kq == 0 || kq == '') {
              // vuth update 14/12
              this.ShowError('Không có dữ liệu trả về')
              return
            } else if (kq == '1') {
              this.ShowError('Chọn số thành công')
              await this.TRACUU_KHOSO_TQ()
              await this.NAP_DS_SODANGKY()
            } else {
              this.ShowError('Chọn số thất bại : ' + kq)
            }
          })
      } catch (ex) {
        this.ShowError('Có lỗi khi chọn số kho tỉnh ' + ex.message)
      }
    },
    DS_KHOSO_KHOITAO_USER: async function () {
      let ma_nhanvien_ccbs = this.$root.token.getMaCCBS()
      var input = { USER_NEO: ma_nhanvien_ccbs }
      var data = this.GetData(await api.DS_KHOSO_KHOITAO_USER(this.axios, input))
      var i = 0
      data.forEach(function (item) {
        i++
        item.STT = i
      })
      return data
    },
    DanhSachKhoSoUser: async function () {
      let ma_nhanvien_ccbs = this.$root.token.getMaCCBS()
      // var input = { USER_NEO: 'vtngoc_hpg' }
      var input = { USER_NEO: ma_nhanvien_ccbs }
      var data = this.GetData(await api.DanhSachKhoSoKhoiDangKy(this.axios, input))
      var i = 0
      data.forEach(function (item) {
        i++
        item.STT = i
      })
      this.grcDS_DaDK.data = this.UpperCasePropertyList(data)
    },
    LayTT: async function () {
      this.Loading = true
      await this.DanhSachKhoSoUser()
      this.Loading = false
    },
    async ValidateModal(bvEvent, modalId) {},
    Popup(modalId) {
      this.$bvModal.show(modalId)
    },
    ClosePopup(modalId) {
      this.$bvModal.hide(modalId)
    },
    ShowAlert: function (message) {
      this.$toast.error(message)
    },
    ShowError: function (message) {
      this.$toast.error(message)
    },
    ShowWarning: function (message) {
      this.$toast.error(message)
    },
    ShowSuccess: function (message) {
      this.$toast.success(message)
    },
    trim(str, ch) {
      let start = 0
      let end = str.length
      while (start < end && str[start] === ch) ++start
      while (end > start && str[end - 1] === ch) --end
      return start > 0 || end < str.length ? str.substring(start, end) : str
    },
    gcKHOTQTraSau_rowClicked: function (i, item) {
      this.gcKHOTQTraSau.currentRow = item
    },
    grcDSAllKho_rowClicked: function (i, item) {
      this.grcDSAllKho.currentRow = item
    },
    grcDSKhoSo_rowClicked: function (i, item) {
      this.grcDSKhoSo.currentRow = item
    },
    grcDS_DaDK_rowClicked: function (i, item) {
      this.grcDS_DaDK.currentRow = item
    },
    grcDSKhoiTao_rowClicked: function (i, item) {
      this.grcDSKhoiTao.currentRow = item
    },
    grcDSKhoSo_rowDataBound_Old(args) {
      if (args.data['status_tt'] == 0) {
        args.data['status_tt_img'] = '<a href="javascript:void(0);" class="aChonSo text-warning act"><span class="nc-icon-glyph ui-1_home-simple"></span>  </a>'
      } else if (args.data['status_tt'] == 1) {
        args.data['status_tt_img'] = ''
        // args.data['status_tt_img'] = '<a href="javascript:void(0);" class="aChonSo text-warning act"><span class="nc-icon-glyph ui-1_home-simple"></span>  </a>'
      } else if (args.data['status_tt'] == 2) {
        args.data['status_tt_img'] = ''
        // args.data['status_tt_img'] = '<a href="javascript:void(0);" class="aChonSo text-success act"><span class="nc-icon-glyph ui-1_check-circle-08"></span> </a>'
      } else {
        args.data['status_tt_img'] = ''
        // args.data['status_tt_img'] = '<a href="javascript:void(0);" class="aChonSo text-warning act"><span class="nc-icon-glyph ui-1_home-simple"></span>  </a>'
      }
    },
    grcDSKhoSo_rowDataBound(args) {
      if (args.data['trangthai'] == 0) {
        args.data['status_tt_img'] = '<a href="javascript:void(0);" class="aChonSo aChonSo2 text-warning act"><span class="nc-icon-glyph ui-1_home-simple"></span>  </a>'
      } else if (args.data['trangthai'] == 1) {
        args.data['status_tt_img'] = ''
        // args.data['status_tt_img'] = '<a href="javascript:void(0);" class="aChonSo text-warning act"><span class="nc-icon-glyph ui-1_home-simple"></span>  </a>'
      } else if (args.data['trangthai'] == 2) {
        args.data['status_tt_img'] = ''
        // args.data['status_tt_img'] = '<a href="javascript:void(0);" class="aChonSo text-success act"><span class="nc-icon-glyph ui-1_check-circle-08"></span> </a>'
      } else {
        args.data['status_tt_img'] = ''
        // args.data['status_tt_img'] = '<a href="javascript:void(0);" class="aChonSo text-warning act"><span class="nc-icon-glyph ui-1_home-simple"></span>  </a>'
      }
    },
    ThoatMaXacThuc: function () {
      this.$refs.dialogObjCapCha.hide()
    },
    OpenMaXacThuc: function () {
      this.$refs.dialogObjCapCha.show()
    },
    // frmCaptcha
    async XacNhanMaXacThuc() {
      let ok = false
      if (ok) {
        if (this.MaXacThucFunc == 'KHOTQ_CHONSO_TS') {
        } else {
        }
      }
    },
    OpenKhoiTaoThueBao: function () {
      this.$refs.dialogObjKhoiTaoThuBao.show()
    },
    ThoatKhoiTaoThueBao: function () {
      this.$refs.dialogObjKhoiTaoThuBao.hide()
    },
    FormatStatus(data, status_tt, status_tt_img) {
      if (data != null && data.length > 0) {
        if (Array.isArray(data)) {
          data.forEach(function (item) {
            if (item[status_tt] == 0) {
              item[status_tt_img] = '<a href="javascript:void(0);" class="text-warning aChonSo act"><span class="nc-icon-glyph ui-1_home-simple"></span></a>'
            } else if (item[status_tt] == 1) {
              item[status_tt_img] = '<a href="javascript:void(0);" class="text-warning aChonSo act"><span class="nc-icon-glyph ui-1_home-simple"></span> </a>'
            } else if (item[status_tt] == 2) {
              item[status_tt_img] = '<a href="javascript:void(0);" class="text-success aChonSo act"><span class="nc-icon-glyph ui-1_check-circle-08"></span> </a>'
            } else {
              item[status_tt_img] = '<a href="javascript:void(0);" class="text-warning aChonSo act"><span class="nc-icon-glyph ui-1_home-simple"></span></a>'
            }
          })
        } else {
          if (data[status_tt] == 0) {
            data[status_tt_img] = '<a href="javascript:void(0);" class="text-warning  aChonSo act"><span class="nc-icon-glyph ui-1_home-simple"></span></a>'
          } else if (data[status_tt] == 1) {
            data[status_tt_img] = '<a href="javascript:void(0);" class="text-warning aChonSo act"><span class="nc-icon-glyph ui-1_home-simple"></span> </a>'
          } else if (data[status_tt] == 2) {
            data[status_tt_img] = '<a href="javascript:void(0);" class="text-success aChonSo act"><span class="nc-icon-glyph ui-1_check-circle-08"></span> </a>'
          } else {
            data[status_tt_img] = '<a href="javascript:void(0);" class="text-warning aChonSo act"><span class="nc-icon-glyph ui-1_home-simple"></span></a>'
          }
        }
      }
    }
  }
}
</script>
