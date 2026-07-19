<template src="./index.html"> </template>
<script>
import Vue from 'vue'
import moment from 'moment'
import DataGrid from '@/components/Controls/DataGrid/index.vue';
import SelectExt from '@/components/Controls/SelectExt/index.vue';
import axios from 'axios';
export default {
  name: 'DsKetQuaCapPhatRack',
  components: {
    DataGrid,
    SelectExt,
  },
  data() {
    return {
      searchConditions: {
        donviId: 0,
        tenCv: null,
        loaiCvId: 0,
        ngayYcStart: null,
        ngayYcEnd: null,
        ngayYchtStart: null,
        ngayYchtEnd: null,
        nhanvienThId: null,
        tenRack: null,
        loaiRack: null,
        khachHang: null,
        congViec: null,
        trangthaiId: null,
        idcId: null,
        phanLoai: 0,
      },
      danhSachKetQua: [],
      donViList: [],
      trangThaiList: [],
      nhanVienList: [],
      phanLoaiList: [],
      idcByNhanVienList: [],
    }
  },
  computed: {

  },
  watch: {},
  created: async function() {
    await this.loadLoaiCongViec();
  },
  mounted: async function() {
    this.loadDanhMuc()
  },
  methods: {
    async loadLoaiCongViec() {
      const response = await this.$root.context.post('/web-ecms/idc/danh-muc/common', {
        loaiDanhMuc: 'IDC-LOAICV',
        thamSo1: null,
        thamSo2: null
      });
      if (response && response.data && response.data.length > 0) {
        this.searchConditions.tenCv = response.data.find(item => item.LOAICV_ID == 63)?.TEN || '';
      }
    },
    async loadDanhMuc() {
      try {
        const idcByNhanVienResponse = await this.$root.context.post('/web-ecms/idc/danh-muc/common', {
          loaiDanhMuc: 'GET-IDC-BY-NHANVIENID',
          thamSo1: this.$root.token.getNhanVienID(),
          thamSo2: null
        })
        this.idcByNhanVienList = [
          { value: 0, text: '-- Tất cả --' },
          ...(idcByNhanVienResponse.data || []).map((item) => ({
            value: item.ID,
            text: item.TEN
          }))
        ]

        // Load danh mục trạng thái
        const trangThaiResponse = await this.$root.context.post('/web-ecms/idc/danh-muc/common', {
          loaiDanhMuc: 'IDC-TRANGTHAI-THICONG',
          thamSo1: 'SEARCH-KETQUA',
          thamSo2: null
        })
        this.trangThaiList = [
          { value: 0, text: '-- Tất cả --' },
          ...(trangThaiResponse.data || []).map((item) => ({
            value: item.ID,
            text: item.TEN
          }))
        ]

        // Load danh mục nhân viên
        const nhanVienResponse = await this.$root.context.post('/web-ecms/idc/danh-muc/common', {
          loaiDanhMuc: 'NHANVIEN-DONVI-IDC',
          thamSo1: this.$root.token.getDonViID(),
          thamSo2: null
        })
        this.nhanVienList = [
          { value: 0, text: '-- Tất cả --' },
          ...(nhanVienResponse.data || []).map((item) => ({
            value: item.NHANVIEN_ID,
            text: item.TEN_NV
          }))
        ]
        this.phanLoaiList = [
          { value: 0, text: '-- Tất cả --' },
          { value: 1, text: 'Khách hàng' },
          { value: 2, text: 'Nội bộ' }
        ]
        await this.onSearch()
      } catch (error) {
        console.error('Lỗi khi tải danh mục:', error)
        this.$toast.error('Có lỗi xảy ra khi tải danh mục')
      }
    },

    async getDsData(data) {
      const response = await this.$root.context.post('/web-ecms/thicong-rack/ds-ketqua-thicong-rack', {
        loaiCvId: 63,
        donviId: data.donviId == 0 ? null : data.donviId,
        trangthaiId: data.trangthaiId == 0 ? null : data.trangthaiId,
        nhanvienThId: data.nhanvienThId == 0 ? null : data.nhanvienThId,
        ngayYcStart: data.ngayYcStart ?? null,
        ngayYcEnd: data.ngayYcEnd ?? null,
        ngayYchtStart: data.ngayYchtStart ?? null,
        ngayYchtEnd: data.ngayYchtEnd ?? null,
        phanLoai: data.phanLoai == 0 ? null : data.phanLoai,
        khachHang: data.khachHang ? data.khachHang.trim() : null,
        congViec: data.congViec ? data.congViec.trim() : null,
        idcId: data.idcId == 0 ? null : data.idcId,
        tenRack: data.tenRack ? data.tenRack.trim() : null,
        loaiRack: data.loaiRack ? data.loaiRack.trim() : null,
      });
      return response;
    },

    async onSearch() {
      try {
        this.$root.showLoading(true)
        const searchParams = { ...this.searchConditions }
        const response = await this.getDsData(searchParams)
        this.danhSachKetQua = response.data || []
      } catch (error) {
        console.error('Lỗi khi tìm kiếm kết quả thay đổi rack:', error)
        this.$toast.error('Có lỗi xảy ra khi tải dữ liệu')
      } finally {
        this.$root.showLoading(false)
      }
    },

    dsThayDoiRack() {
      this.$router.push({ path: `/tainguyen-idc/danhsach-congviec?loaicv=63` })
    },

    viewDetails(data) {
      this.$router.push({
        path: '/tainguyen-idc/thaydoi-rack-form',
        query: { phieutc: data.PHIEUTC_ID }
      })
    },
    editDetails(data) {
      this.$router.push({
        path: '/tainguyen-idc/thaydoi-rack-form',
        query: { phieutc: data.PHIEUTC_ID, isEditMode: true }
      })
    },

    actionColumn(parent) {
      return function () {
        return {
          template: {
            template: `<div class="action-buttons">
              <button  v-if="!isEditable" class="btn btn-sm btn-outline-primary ml-1" title="Xem chi tiết" @click="viewDetail()">
                <i class="fa fa-eye"></i>
              </button>
              <button v-if="isEditable" class="btn btn-sm btn-outline-primary ml-1" title="Sửa" @click="editDetail()">
                <i class="fa fa-edit"></i>
              </button>
            </div>`,
            computed: {
              isEditable() {
                const currentNhanVienId = this.$root.token.getNhanVienID();
                const editable =
                  (this.data.TRANGTHAI_ID === 22) &&
                  Number(currentNhanVienId) === this.data.NHANVIEN_TH_ID;
                return editable;
              }
            },
            methods: {
              viewDetail() {
                parent.viewDetails(this.data)
              },
              editDetail() {
                parent.editDetails(this.data)
              }
            }
          }
        }
      }
    }
  }
}
</script>
<style scoped>
.info-row {
  .value {
    flex: 1;
  }
  .separator {
    width: 30px;
    display: flex;
    justify-content: center;
    align-items: center;
  }
}
</style>
