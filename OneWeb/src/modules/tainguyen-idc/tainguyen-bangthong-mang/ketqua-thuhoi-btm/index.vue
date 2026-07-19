<template src="./index.html"></template>

<script>
import DataGrid from '@/components/Controls/DataGrid/index.vue';
import TabHistory from '../../components/Tabs/TabHistory/index.vue' // import component TabHistory
import { DATE_TIME_FORMAT } from '@/constants'
import moment from 'moment'

export default {
  components: {
    DataGrid,
    TabHistory
  },
  data: function () {
    return {
      currentTab2: 1,
      danhSachCongViec: [],
      loaiCongViecList: [],
      trangThaiList: [],
      nhanVienList: [],
      phanLoaiList: [],
      donViList: [],
      idcList: [],
      dsKetquaBangthongCapPhat: [],
      dataGrid2Columns: [
        {fieldName: 'THICONGBT_ID', headerText: 'ID cấp băng thông', textAlign: 'Left', width: 50},
        {fieldName: 'GOICUOC_ID', headerText: 'Gói cước mạng', textAlign: 'Left', width: 100},
        {fieldName: 'QUOCTE', headerText: 'Quốc tế', textAlign: 'Left', width: 90},
        {fieldName: 'TRONGNUOC', headerText: 'Trong nước', textAlign: 'Left', width: 90},
        {fieldName: 'THIETBIMANG', headerText: 'Thiết bị mạng(Switch Access)', textAlign: 'Left', width: 120},
        {fieldName: 'PORT', headerText: 'ID Port', textAlign: 'Left', width: 120},
        {fieldName: 'NGAY_TH', headerText: 'Thời gian thực hiện', textAlign: 'Left', width: 140},
        {fieldName: 'NGUOI_CN', headerText: 'Người thực hiện', textAlign: 'Left', width: 120},
        {fieldName: 'TRANGTHAI', headerText: 'Trạng thái thực hiện', textAlign: 'Left', width: 120},
        {fieldName: 'TEN_KH', headerText: 'Tên khách hàng', textAlign: 'Left', width: 100},
        {fieldName: 'MA_TB', headerText: 'Mã thuê bao', textAlign: 'Left', width: 90},
        {fieldName: 'ACTIONS', headerText: 'Thao tác', allowSorting: false,
              allowFiltering: false, textAlign: 'Center', template: this.actionColumn(this), width: 80}
      ],
      searchConditions: {
        loaicvId: 44,
        donviId: null,
        khachhang: null,
        congviec: null,
        tenHeThong: null,
        ycBatdau: null,
        ycKetthuc: null,
        ychtBatdau: null,
        ychtKetthuc: null,
        nhanvienId: null,
        trangthaiId: null,
        phanloai: 1,
        idcId: null
      },
      trangThaiList: [],
      donViList: [],
      dsNhanvien: [],
      controls: {
        txtTen: {
          enabled: false,
          invalid: false,
        },
        txtGhiChu: {
          enabled: false,
        },
      },
      buttons: {
        btnAddNew: {
          enabled: true,
        },
        btnSave: {
          enabled: false,
        },
        btnCancel: {
          enabled: false,
        },
        btnEdit: {
          enabled: false,
        },
        btnDelete: {
          enabled: false,
        },
      },
      currentMode: 'ADD'
    };
  },
  computed: {
    phieutc() {
      return Number(this.$route.query.phieutc)
    }
  },
  watch: {
  },
  created: async function () {
    try {
      this.$root.showLoading(true);
      this.dsKetquaBangthongCapPhat = await this.onSearch();

      await this.loadDanhMuc();

    } finally {
      this.$root.showLoading(false);
    }
  },
  methods: {

    async loadDanhMuc() {
      try {
        // Load phân loại list
        this.phanLoaiList = [
          // { ID: -1, TEN: '-- Tất cả --' },
          { ID: 1, TEN: 'Khách hàng' },
          // { ID: PHANLOAI.NOIBO, TEN: 'Nội bộ' }
        ]

        // Load danh mục trạng thái
        const trangThaiResponse = await this.loadTrangThaiDM();
        this.trangThaiList = [{ ID: 0, TEN: '-- Tất cả --' }, ...(trangThaiResponse || [])]

        // Load IDC list cho user hiện tại (nếu cần)
        const idcByNhanVienResponse = await this.loadIdcDM();
        this.idcList = [{ ID: 0, TEN: '-- Tất cả --' }, ...(idcByNhanVienResponse || [])]

        await this.loadNhanVienByDonVi()
      } catch (error) {
        console.error('Lỗi khi tải danh mục:', error)
        this.$toast.error('Có lỗi xảy ra khi tải danh mục')
      }
    },

    async loadTrangThaiDM() {
      try {
        const rs = await this.$root.context.post(
          `/web-ecms/idc/danh-muc/common`,
          {
            loaiDanhMuc: 'IDC-TRANGTHAI-THICONG',
            thamSo1: 'SEARCH-KETQUA',
            thamSo2: null
          }
        );

        return rs.data;
      } catch (error) {
        this.$toast.error(error.response?.data?.message_detail || "Lỗi tải nhân viên");
      } finally {
        this.$root.showLoading(false);
      }
    },

    async loadNhanVienByDonVi() {
      try {
        const response = await this.loadNhanVien()
        this.nhanVienList = [{ NHANVIEN_ID: 0, TEN_NV: '-- Tất cả --' }, ...(response || [])]
      } catch (error) {
        console.error('Lỗi khi tải danh sách nhân viên:', error)
      }
    },

    async loadNhanVien() {
      try {
        const rs = await this.$root.context.post(
          `/web-ecms/idc/danh-muc/common`,
          {
            loaiDanhMuc: 'NHANVIEN-DONVI-IDC',
            thamSo1: this.$root.token.getDonViID(),
            thamSo2: null
          }
        );

        return rs.data;
      } catch (error) {
        this.$toast.error(error.response?.data?.message_detail || "Lỗi tải nhân viên");
      } finally {
        this.$root.showLoading(false);
      }
    },
    async loadIdcDM() {
      try {
        const rs = await this.$root.context.post(
          `/web-ecms/idc/danh-muc/common`,
          {
            loaiDanhMuc: 'GET-IDC-BY-NHANVIENID',
            thamSo1: this.$root.token.getNhanVienID(),
            thamSo2: null
          }
        );

        return rs.data;
      } catch (error) {
        this.$toast.error(error.response?.data?.message_detail || "Lỗi tải nhân viên");
      } finally {
        this.$root.showLoading(false);
      }
    },
    formatDateLocal(date, defaultValue, inputFormat) {
      return formatDate(date, defaultValue, inputFormat)
    },
    formatDateTimeTemplate(parent) {
      return function() {
        return {
          template: {
            template: `<span>{{ formatDateTimeValue }}</span>`,
            computed: {
              formatDateTimeValue() {
                const ngayYCHT = this.data.NGAY_YCHT?.replace('.0', '')
                return ngayYCHT ? moment(ngayYCHT).format(DATE_TIME_FORMAT) : ''
              }
            }
          }
        }
      }
    },

    khachHangTemplate(parent) {
      return function() {
        return {
          template: {
            template: `<div v-html="displayKhachHang"></div>`,
            computed: {
              displayKhachHang() {
                if (!this.data.KHACHHANG || this.data.KHACHHANG.trim() === '') {
                  return 'NỘI BỘ IDC'
                }
                return this.data.KHACHHANG
              }
            }
          }
        }
      }
    },
    buildTrangthaiOptions: async function () {
      return [
        {
          text: "Đã cấp phát",
          value: 1
        },
        {
          text: "Đã thu hồi",
          value: 0
        }
      ];
    },
    buildDonviOptions: async function () {
      return [
        {
          text: "Đơn vị 1",
          value: 1
        },
        {
          text: "Đơn vị 2",
          value: 2
        }
      ];
    },
    buildTrangthaiOptions: async function () {
      return [
        {
          text: "Đã cấp phát",
          value: 1
        },
        {
          text: "Đã thu hồi",
          value: 0
        }
      ];
    },
    enableControls: function (args) {
      for (const key in this.controls) {
        if (this.controls.hasOwnProperty(key)) {
          this.controls[key].enabled = args;
        }
      }

      this.buttons.btnSave.enabled = args;
      this.buttons.btnAddNew.enabled = !args;
      this.buttons.btnCancel.enabled = this.buttons.btnSave.enabled;
      this.buttons.btnDelete.enabled = !args;

      if (this.currentItem.id > 0)
        this.buttons.btnEdit.enabled = !this.buttons.btnSave.enabled;
    },
    setCurrentItem: function (item) {
      
    },
    setCurrentItemDefault: function () {
      this.setCurrentItem({
        id: 0,
        ten: '',
        ghiChu: '',
        hieuLuc: 1,
      });
    },
    validateControls: function () {
      let msg = "";
      if (this.currentItem.ten == null || this.currentItem.ten == "") {
        msg = "Tên không được để trống.";
      }
      if (msg != "") { 
        this.$root.toastError(msg);
        return false;
      }
      return true;
    },
    actionColumn(parent) {
      return function () {
        return {
          template: {
            template: `<div class="action-buttons gap-1">
              <button class="btn btn-sm btn-outline-primary" title="Xem thi công" @click="xemChiTiet()">
                  <i class="fa fa-eye"></i>
                </button>
              </div>`,
            computed: {},
            methods: {
              xuLyItem() {
                parent.xuLyCongViec(this.data, true)
              },
              xemChiTiet() {
                console.log(this.data)
                parent.xuLyCongViec(this.data, false)
              }
            }
          }
        }
      }
    },

    getDsBangthongCapPhat: async function () {
      try {
        const rs = await this.$root.context.get(`web-ecms/ha-tang/thongtin-u/thicong-bangthong/dsKetqua?loaiKq=TH`)
        return rs.data
      } catch (error) {
        this.$toast.error(error.response?.data?.message_detail || 'Không lấy được thông tin')
        return []
      }
    },
    async onSearch() {
      try {
        this.$root.showLoading(true)

        const response = await this.$root.context.post(
          `/web-ecms/ha-tang/thongtin-u/search`,
          this.searchConditions
        )

        // Gán kết quả API về danh sách hiển thị
        this.dsKetquaBangthongCapPhat = response.data || []
        return response.data || [];

      } catch (error) {
        this.handleError(error)
      } finally {
        this.$root.showLoading(false)
      }
    },
    onNew: function() {
      const routeParams = {
        loaicv: 44
      };
      // routeParams[] = data[fieldName];
      this.$router.push({
        path: "danhsach-congviec",
        query: routeParams
      });
    },
    xuLyCongViec(data, view) {
      try {
        this.$router.push({
          path: "idc-bangthong-mang",
          query: view ? {
            phieutc: data.PHIEUTC_ID,
            isEditMode: true
          } : {
            phieutc: data.PHIEUTC_ID
          }
        })

      } catch (error) {
        console.error('Lỗi khi xử lý công việc:', error)
        this.$toast.error('Có lỗi xảy ra khi xử lý công việc')
      }
    }
  },
};
</script>

<style>
.check-action {
  top: 3px;
}

.e-rowcell .check-action input[type="checkbox"]:checked:disabled+.name:before {
  background: #fff;
  border-color: #0176ff;
  color: #0176ff;
}

.e-rowcell .check-action input[type="checkbox"]:disabled+.name:before {
  background: #fff;
  border-color: #0176ff;
}

.form-section {
  margin: 20px 0;
}

.datagrid-section {
  margin: 20px 0;
}
.phoi-hop-thuc-hien {
      white-space: pre-line !important;
      word-wrap: break-word !important;
      overflow-wrap: break-word !important;
    }
</style>