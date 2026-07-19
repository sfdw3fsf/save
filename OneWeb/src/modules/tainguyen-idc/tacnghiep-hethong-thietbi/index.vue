<template src="./index.template.html"> </template>
<script>
import Vue from 'vue';
import moment from "moment";
import ModalChiTietPhieuTacNghiep from './chitiet-phieu-tacnghiep/index.vue';

export default {
  name: 'TacNghiepHeThongThietBi',
  components: {
    ModalChiTietPhieuTacNghiep
  },
  data() {
    return {
      loaiTacNghiepList: [],
      IDCList: [],
      nhiemVuData: [
        {
          MA_CV: '12345',
          PHIEU_YC: 'ABC',
          HANG_MUC_TRIEN_KHAI: 'Lắp đặt mới',
          LOAI_CONG_VIEC: 'Khai báo giám Rack',
          DON_VI_THUC_HIEN: 'IDC Miền Bắc',
          CA_NHAN_THUC_HIEN: 'Trần Đăng Phúc',
          PHOI_HOP_THUC_HIEN: 'Nguyễn Văn Hạnh',
          THOI_HAN_THUC_HIEN: '20/05/2024 9h00 AM',
          TRANG_THAI_THUC_HIEN: 'Tiếp nhận công việc',
          LY_DO_TRA: null,
          NGAY_HOAN_THANH: null
        },
        {
          MA_CV: '12346',
          PHIEU_YC: 'DEF',
          HANG_MUC_TRIEN_KHAI: 'Bảo trì định kỳ',
          LOAI_CONG_VIEC: 'Kiểm tra hệ thống',
          DON_VI_THUC_HIEN: 'IDC Miền Nam',
          CA_NHAN_THUC_HIEN: 'Lê Văn An',
          PHOI_HOP_THUC_HIEN: 'Phạm Thị Bình',
          THOI_HAN_THUC_HIEN: '21/05/2024 8h00 AM',
          TRANG_THAI_THUC_HIEN: 'Đang thực hiện',
          LY_DO_TRA: null,
          NGAY_HOAN_THANH: null
        },
      ]
    }
  },

  computed: {
    grdNhiemVu: function () {
      return this.$refs.grdNhiemVu;
    }
  },

  watch: {

  },

  created: async function () {
  },

  mounted: async function () {
    this.loaiTacNghiepList = await this.fetchLoaiTacNghiepList();
    this.IDCList = await this.fetchIDCList();
  },
  methods: {
    actionColumn(parent) {
      return function () {
        return {
          template: {
            template: `<div class="action-buttons gap-1">
                <button class="btn btn-sm btn-outline-primary" title="Xem chi tiết" @click="viewItem()" :disabled="!canView">
                  <i class="fa fa-eye"></i>
                </button>
                <button class="btn btn-sm btn-outline-success" title="Xử lý" @click="xuLyItem()" :disabled="!canProcess">
                  <i class="fa fa-cogs"></i>
                </button>
              </div>`,
            computed: {
              canView() {
                return true;
              },
              canProcess() {
                return this.data.TRANG_THAI_THUC_HIEN === 'Tiếp nhận công việc' ||
                  this.data.TRANG_THAI_THUC_HIEN === 'Chờ phê duyệt';
              },
            },
            methods: {
              viewItem() {
                parent.viewNhiemVu(this.data);
              },
              xuLyItem() {
                parent.xuLyNhiemVu(this.data);
              },
            },
          },
        };
      };
    },

    onDetailDataBound(e) {
      //
    },

    viewNhiemVu(data) {
      this.$refs.ModalChiTietPhieuTacNghiep.show(data, 'VIEW');
    },

    xuLyNhiemVu(data) {
      this.$refs.ModalChiTietPhieuTacNghiep.show(data, 'EDIT');
    },

    onRefreshData() {
      console.log('Data refreshed');
    },

    fetchLoaiTacNghiepList() {
      setTimeout(() => {
        this.loaiTacNghiepList = [
          { value: 'LDTB', text: 'Lắp đặt thiết bị' },
          { value: 'TDTB', text: 'Tháo dỡ thiết bị' },
          { value: 'TDTTTB', text: 'Thay đổi thông tin thiết bị' }
        ];
      }, 1000);
    },
    fetchIDCList() {
      setTimeout(() => {
        this.IDCList = [
          { value: 'IDC_1', text: 'IDC Miền Bắc' },
          { value: 'IDC_2', text: 'IDC Miền Nam' },
          { value: 'IDC_3', text: 'IDC Miền Trung' }
        ];
      }, 1000);
    }
  }
}
</script>
<style scoped></style>
