<template>
  <b-modal size="xl" hide-header hide-header-close hide-footer body-class="p-0" no-close-on-esc no-close-on-backdrop
    ref="refSearchPhieuKenhBan" style="max-height: 80%;">
    <div class="modal-content popup-box">
      <div class="popup-header">
        <div class="title">
          <span class="img"><span class="icon one-notepad"></span> Tra cứu phiếu SO </span>
        </div>
        <div class="close -ap icon-close" data-dismiss="modal" @click="hideModal"></div>
      </div>
      <div class="list-actions-top">
        <ul class="list">
          <li>
            <a @click="onTimKiem"> <span class="icon one-search"></span> Tìm kiếm </a>
          </li>
          <li>
            <a @click="onChapNhan"> <span class="icon ui-1_check-circle-08 nc-icon-glyph"></span> Chấp nhận </a>
          </li>
        </ul>
      </div>
      <div class="popup-body">
        <div class="box-form">
          <div class="legend-title">Thông tin tra cứu</div>
          <div class="row">
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key w120">Khách hàng</div>
                <div class="value">
                  <input type="text" class="form-control" v-model="params.pTenKh"
                    placeholder="Mã TB, Mã GD, Số HĐ, Tên TB" />
                </div>
              </div>
            </div>
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key w120">Công việc</div>
                <div class="value">
                  <input type="text" class="form-control" v-model="params.pMaCv"
                    placeholder="Mã công việc, Tên công việc, Nội dung" />
                </div>
              </div>
            </div>
          </div>
          <div class="row">
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key w120">Loại công việc</div>
                <div class="value">
                  <SelectExt v-model="params.pLoaicvId" placeholder="-- Chọn loại công việc --" dataTextField="text"
                    dataValueField="value" :dataSource="[...loaiCongViecList]" />
                </div>
              </div>
            </div>
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key w120">IDC</div>
                <div class="value">
                  <SelectExt v-model="params.pIdcId"  placeholder="-- Chọn IDC --"  dataTextField="TEN" dataValueField="ID" :dataSource="[...idcList]" />
                </div>
              </div>
            </div>
          </div>
          <div class="legend-title mart20">Danh sách phiếu SO</div>
          <div class="row">
            <div class="col-12">
              <DataGrid ref="grdDSPhieuSO" :allowPaging="true" :enablePagingServer="false" :showColumnCheckbox="false"
                :dataSource="danhSachPhieuSO" v-bind:columns="[
                  { fieldName: 'MA_GD', headerText: 'Mã giao dịch', textAlign: 'Left', width: 150 },
                  { fieldName: 'MA_TB', headerText: 'Mã thuê bao', textAlign: 'Left', width: 150 },
                  { fieldName: 'TEN_TB', headerText: 'Tên khách hàng', textAlign: 'Left', width: 180 },
                  { fieldName: 'MA_HD', headerText: 'Số hợp đồng', textAlign: 'Left' },
                  { fieldName: 'GOICUOC', headerText: 'Gói cước', textAlign: 'Left' },
                  { fieldName: 'IDC', headerText: 'IDC', textAlign: 'Left' },
                  { fieldName: 'MA_CONGVIEC', headerText: 'Mã công việc', textAlign: 'Left' },
                  { fieldName: 'TEN_CONGVIEC', headerText: 'Tên công việc', textAlign: 'Left' },
                  // { fieldName: 'GOICUOC', headerText: 'Gói cước', textAlign: 'Left' },
                  { fieldName: 'LOAI_CV', headerText: 'Loại tác nghiệp', textAlign: 'Left' },
                  { fieldName: 'NHANVIEN_GIAO', headerText: 'Nhân viên giao', textAlign: 'Left' },
                  { fieldName: 'DONVI', headerText: 'Đơn vị thực hiện', textAlign: 'Left' },
                  { fieldName: 'NHANVIEN_TH', headerText: 'Nhân viên thực hiện', textAlign: 'Left' },

                  { fieldName: 'SOLUONG', headerText: 'Giá trị khai báo', textAlign: 'Center' },
                  { fieldName: 'NOIDUNG', headerText: 'Nội dung', textAlign: 'Left' },
                  // { fieldName: 'KHONGGIAN', headerText: 'Không gian', textAlign: 'Left' },
                  // { fieldName: 'LOAIPH', headerText: 'Loại phiếu', textAlign: 'Left' },
                  // { fieldName: 'TEN_KIEULD', headerText: 'Kiểu lắp đặt', textAlign: 'Left' },
                  { fieldName: 'NGAY_TAO', headerText: 'Ngày giao', textAlign: 'Left', width: 180 },
                  { fieldName: 'NGAY_YCHT', headerText: 'Ngày yêu cầu HT', textAlign: 'Left', width: 180 }
                ]" @recordDoubleClick="onRecordDoubleClick" @selectedRowChanged="onSelectedRowChanged"
                :enabledSelectFirstRow="true">
              </DataGrid>
            </div>
          </div>
        </div>
      </div>
    </div>
  </b-modal>
</template>

<script>
import API from '../api'

export default {
  data() {
    return {
      target: '.main-wrapper',
      animationSettings: { effect: 'Zoom' },
      dialogPosition: { X: 'center', Y: 'center' },
      params: {
        pTenKh: null,
        pMaCv: null,
        pLoaicvId: null,
        pIdcId: null
      },
      danhSachPhieuSO: [],
      currentItem: null,
      loaiCongViecList: [],
      idcList: []
    }
  },
  created() {
    this.getDanhMuc()
  },
  methods: {
    async apiSearchDSPhieuSO(params) {
      this.loading(true)
      let dsResult = []
      try {
        // const response = await API.getDSPhieuSO(this.axios, params)
        // const response = await API.getDSPhieuSOV3(this.axios, params)
        const response = await API.getDsCongViec(this.axios, params)
        if (response.data.error_code === 'BSS-00000000') {
          dsResult = response.data.data
        } else {
          dsResult = []
        }
      } catch (error) {
        console.log('apiGetDSGiamTruCuoc error: ', error)
        dsResult = []
      } finally {
        this.loading(false)
      }
      return dsResult
    },
    show() {
      this.$refs.refSearchPhieuKenhBan.show()
      this.refSearchPhieuKenhBanLoad()
      this.onTimKiem()
    },
    hideModal() {
      this.$refs.refSearchPhieuKenhBan.hide()
    },
    refSearchPhieuKenhBanLoad() {
      this.params = {
        pMaTB: '',
        pMaGD: '',
        pTenKH: '',
        pSoHD: ''
      }
      this.danhSachPhieuSO = []
      this.currentItem = null
    },
    onChapNhan() {
      console.log('onChapNhan', this.currentItem)
      if (!this.currentItem) return this.$root.toastError('Bạn cần chọn phiếu SO trước!')
      this.$emit('beforClosePopup', this.currentItem)
      this.$refs.refSearchPhieuKenhBan.hide()
    },
    async onTimKiem() {
      const params = {
        pLoaicvId: this.params.pLoaicvId || null,
        pIdcId: this.params.pIdcId != 0 ? this.params.pIdcId : null,
        pTenKh: this.params.pTenKh || null,
        pMaCv: this.params.pMaCv || null
      }
      this.danhSachPhieuSO = await this.apiSearchDSPhieuSO(params)
    },
    onRecordDoubleClick() {
      console.log('onRecordDoubleClick')
      this.onChapNhan()
    },
    onSelectedRowChanged(data) {
      console.log('onSelectedRowChanged', data)
      if (data) {
        this.currentItem = data
      }
    },
    async getDanhMuc() {
      const loaiCongViecResponse = await API.getDanhMucLoaiCongViec(this.$root.context, {
        loaiDanhMuc: 'IDC-LOAICV'
      })
      console.log('idcResponse', loaiCongViecResponse)
      this.loaiCongViecList = [
        { value: 0, text: '-- Tất cả --' },
        ...(loaiCongViecResponse.data || []).map((item) => ({
          value: item.LOAICV_ID,
          text: item.TEN
        }))
      ]
      const idcResponse = await API.getDanhMucIDC(this.axios, this.$root.token)
      console.log('idcResponse', idcResponse)
      this.idcList = [
        { ID: 0, TEN: '-- Tất cả --' },
        ...idcResponse
      ]
    }
  }
}
</script>

<style scoped>
.e-dialog {
  position: fixed !important;
  top: 50% !important;
  left: 50% !important;
  transform: translate(-50%, -50%) !important;
}

.custom-dialog .e-dlg-content {
  overflow: visible !important;
  overflow-x: visible !important;
}
</style>
