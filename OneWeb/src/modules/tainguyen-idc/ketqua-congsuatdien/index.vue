<template src="./index.html"></template>

<script>
import api from './api.js'
export default {
  name: 'KetQuaCongSuatDien',
  components: {},
  data() {
    return {
      searchConditions: {
        donViId: null,
        nhanVienThucHienId: null,
        trangThaiId: null,
        khachHang: '',
        ngayYcStart: null,
        ngayYcEnd: null,
        loaiCvId: 0,
        congViec: null,
        idcId: null,
        ngayHoanThanhStart: null,
        ngayHoanThanhEnd: null,
        thietBi: null,
        oCamId: null
      },
      displayConditions: {
        loaiCvId: 0
      },
      dsKetQua: [], // <-- chỗ để binding vào DataGrid
      loaiCongViecList: [],
      trangThaiList: [],
      nhanVienList: [],
      idcList: [],
      isLoaicvIdFromUrl: false
    }
  },
  computed: {
    selectedLoaiCongViecText() {
      if (this.displayConditions.loaiCvId == 0 || !this.displayConditions.loaiCvId) {
        return 'Tất cả loại công việc'
      }
      const selectedItem = this.loaiCongViecList.find((item) => item.value == this.displayConditions.loaiCvId)
      return selectedItem ? selectedItem.text : 'Không xác định'
    }
  },
  mounted() {
    const loaiCvQuery = Number(this.$route.query.loaicv || 0)

    if (loaiCvQuery) {
      this.searchConditions.loaiCvId = loaiCvQuery
      this.displayConditions.loaiCvId = loaiCvQuery
      this.isLoaicvIdFromUrl = true
    }
    this.loadDanhMuc()
  },
  methods: {
    async loadDanhMuc() {
      try {
        this.$root.loading(true)
        //loai cong viec
        this.loaiCongViecList = [
          { value: 0, text: '-- Tất cả --' },
          { value: 29, text: 'Cấp phát điện' },
          { value: 43, text: 'Thu hồi điện' },
          { value: 81, text: 'Chuyển đổi điện' }
        ]
        //load nhan vien by don vi
        const donviId = this.$root.token.getDonViID()
        const resNhanVien = await api.getNhanVienByDonVi(this.$root.context, donviId)
        this.nhanVienList = [
          { value: 0, text: '-- Tất cả --' },
          ...(resNhanVien.data || []).map((item) => ({
            value: Number(item.NHANVIEN_ID),
            text: item.TEN_NV
          }))
        ]
        //load trang thai phieu thi cong
        const resTrangThai = await this.$root.context.post('/web-ecms/idc/danh-muc/common', {
          loaiDanhMuc: 'IDC-TRANGTHAI-THICONG',
          thamSo1: 'SEARCH-KETQUA',
          thamSo2: null
        })
        this.trangThaiList = [
          { value: 0, text: '-- Tất cả --' },
          ...resTrangThai.data.map((item) => ({
            value: item.ID,
            text: item.TEN
          }))
        ]
        //load idc
        const idcByNhanVienResponse = await api.getDanhMucLoaiCongViec(this.$root.context, {
          loaiDanhMuc: 'GET-IDC-BY-NHANVIENID',
          thamSo1: this.$root.token.getNhanVienID()
        })
        this.idcList = [
          { value: 0, text: '-- Tất cả --' },
          ...(idcByNhanVienResponse.data || []).map((item) => ({
            value: item.ID,
            text: item.TEN
          }))
        ]

        await this.onSearch()
      } catch (error) {
        this.$toast.error('Có lỗi xảy ra khi tải danh mục')
      } finally {
        this.$root.loading(false)
      }
    },

    async onSearch() {
      try {
        this.$root.showLoading(true)
        this.displayConditions.loaiCvId = this.searchConditions.loaiCvId
        const searchParams = { ...this.searchConditions }
        // Trim whitespace from string fields
        Object.keys(searchParams).forEach((key) => {
          if (typeof searchParams[key] === 'string') {
            searchParams[key] = searchParams[key].trim()
          }
        })
        const response = await api.getKetQuaCapPhatFiltered(this.$root.context, searchParams)
        this.dsKetQua = response.data || []
      } catch (error) {
        this.$toast.error('Có lỗi xảy ra khi tìm kiếm')
      } finally {
        this.$root.showLoading(false)
      }
    },
    validateOCam() {
      // Nếu giá trị không phải số thì set null
      if (isNaN(this.searchConditions.oCamId) || this.searchConditions.oCamId === '') {
        this.searchConditions.oCamId = null
      } else {
        this.searchConditions.oCamId = parseInt(this.searchConditions.oCamId, 10)
      }
    },
    createCongSuatDien() {
      const loaicv = this.$route.query.loaicv || this.displayConditions.loaiCvId || 0
      this.$router.push({
        path: '/tainguyen-idc/danhsach-congviec',
        query: { loaicv }
      })
    },
    actionColumn(parent) {
      return function() {
        return {
          template: {
            template: `
          <div class="action-buttons gap-1">
            <!-- Nút xem -->
            <button
              class="btn btn-sm btn-outline-primary"
              title="Xem chi tiết"
              @click="redirect('false')">
              <i class="fa fa-eye"></i>
            </button>

            <!-- Nút sửa -->
            <div :title="editTitle" style="display:inline-block">
              <button
                class="btn btn-sm btn-outline-warning"
                :disabled="isDisabled"
                @click="redirect('true')">
                <i class="fa fa-edit"></i>
              </button>
            </div>
          </div>
        `,
            computed: {
              isDisabled() {
                return this.data.idTrangThaiPTC == 24 || this.data.idTrangThaiPTC == 23
              },
              editTitle() {
                return this.data.idTrangThaiPTC == 24
                  ? 'Công việc đã hoàn thành, không thể chỉnh sửa'
                  : this.data.idTrangThaiPTC == 23
                  ? 'Công việc đã trả phiếu, không thể chỉnh sửa'
                  : 'Chỉnh sửa'
              }
            },
            methods: {
              redirect(isEditMode) {
                const phieuId = this.data.idPhieuTC
                const loaiCv = this.data.idLoaiCv
                let path = ''
                switch (loaiCv) {
                  case 29:
                    path = '/tainguyen-idc/capphat-congsuatdien'
                    break
                  case 43:
                    path = '/tainguyen-idc/thuhoi-congsuatdien'
                    break
                  case 81:
                    path = '/tainguyen-idc/chuyendoi-congsuatdien'
                    break
                }

                parent.$router.push({
                  path,
                  query: { phieutc: phieuId, isEditMode }
                })
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
  .key {
    width: 160px !important;
  }
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
