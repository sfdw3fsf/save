<template>
  <div>
    <div class="list-actions-top d-flex justify-content-start align-items-center">
      <ul class="list">
        <li v-if="isVisibleChonLoHang" @click="onClickChonLoHang">
          <a> <span class="icon ui-1_check-circle-08 nc-icon-glyph"></span> Chọn lô hàng </a>
        </li>
        <li v-if="isVisibleXacNhan" @click="onClickXacNhan">
          <a> <span class="icon ui-1_check-circle-08 nc-icon-glyph"></span> Xác nhận </a>
        </li>
        <li v-if="isVisibleHuyBo" @click="onClickHuyBo">
          <a> <span class="icon nc-icon-glyph ui-1_circle-remov"></span> Hủy bỏ </a>
        </li>
      </ul>
    </div>
    <div class="popup-body">
      <div class="box-form">
        <div class="row">
          <div class="col-sm-6 col-12">
            <div class="info-row">
              <div class="key w90">Từ kho <span class="red">*</span></div>
              <div class="value">
                <div class="input-group">
                  <div class="input-text">
                    <div class="select-custom">
                      <SelectExt v-model.trim="txtKhoId" :dataSource="cboKho" dataTextField="tenKho" dataValueField="khoID" placeholder="Chọn vật tư" :disabled="!enabledTukho"> </SelectExt>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="col-sm-3 col-12">
            <div class="info-row">
              <div class="key w90">Đến ngày <span class="red">*</span></div>
              <div class="value">
                <div class="input-icon-right">
                  <ejs-datepicker class="form-control bg-none bor0 fw6 text h24" :format="dateFormat" :enabled="enabledDenNgay" v-model="txtDenNgay"></ejs-datepicker>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div class="box-form">
        <div class="nav tabs tab-over">
          <a href="#tab1" data-toggle="tab" :class="{ active: !isTab }" @click="changeTab(0)">Danh sách tồn kho</a>
          <a href="#tab2" data-toggle="tab" :class="{ active: isTab }" @click="changeTab(1)">Danh sách lô hàng</a>
        </div>

        <div class="tab-content">
          <div class="tab-pane active" id="tab1" v-show="!isTab">
            <div class="table-content relative">
              <DataGrid
                v-bind:columns="[
                  { fieldName: 'MA_VT', headerText: 'Mã vật tư', allowFiltering: true },
                  { fieldName: 'TEN_VT', headerText: 'Tên vật tư', allowFiltering: true },
                  { fieldName: 'DVI_TINH', headerText: 'ĐVT', allowFiltering: true },
                  { fieldName: 'SL_TON', headerText: 'SL tồn', allowFiltering: true },
                  { fieldName: 'SL_XUAT', headerText: 'SL xuất', allowFiltering: true }
                ]"
                v-bind:dataSource="grvTonKho"
                :showFilter="true"
                :allowPaging="true"
                :showColumnCheckbox="true"
                ref="selectedGrvTonKho"
              >
              </DataGrid>
            </div>
          </div>
          <div class="tab-pane active" id="tab2" v-show="isTab">
            <div class="row">
              <DataGrid
                v-bind:columns="[
                  { fieldName: 'MA_VT', headerText: 'Mã vật tư', allowFiltering: true },
                  { fieldName: 'TEN_VT', headerText: 'Tên vật tư', allowFiltering: true },
                  { fieldName: 'LOHANG', headerText: 'Lô hàng', allowFiltering: true },
                  { fieldName: 'SL_TON', headerText: 'SL tồn', allowFiltering: true },
                  { fieldName: 'SL_XUAT', headerText: 'SL xuất', allowFiltering: true }
                ]"
                v-bind:dataSource="grvVTGroup"
                :showFilter="true"
                @selectedRowChanged="onChangeVTGroup"
              >
              </DataGrid>
            </div>
            <div class="table-content relative">
              <div class="box-move-select-table">
                <div class="box-col box-form">
                  <div class="legend-title">Thông tin chi tiết lô hàng đã gán</div>
                  <DataGrid
                    :columns="[
                      { fieldName: 'MA_VT', headerText: 'Mã vật tư', allowFiltering: true },
                      { fieldName: 'TEN_VT', headerText: 'Tên vật tư', allowFiltering: true },
                      { fieldName: 'LO_HANG', headerText: 'Lô hàng', allowFiltering: true }
                    ]"
                    :dataSource="grvDaGan"
                    :showFilter="true"
                    :showColumnCheckbox="true"
                    ref="selectedDaGan"
                    :enabledSelectFirstRow="false"
                    :allowPaging="true"
                    :enablePagingServer="false"
                  >
                  </DataGrid>
                </div>
                <div class="actions">
                  <button class="btn" @click="moveRight()">
                    <span class="fa fa-angle-right"></span>
                  </button>
                  <button class="btn" @click="moveLeft()">
                    <span class="fa fa-angle-left"></span>
                  </button>
                </div>
                <div class="box-col box-form">
                  <div class="legend-title">Thông tin chi tiết lô hàng chưa gán</div>
                  <DataGrid
                    :columns="[
                      { fieldName: 'LOHANG', headerText: 'Lô hàng', allowFiltering: true },
                      { fieldName: 'MA_VT', headerText: 'Mã vật tư', allowFiltering: true },
                      { fieldName: 'TEN_VT', headerText: 'Tên vật tư', allowFiltering: true },
                      { fieldName: 'DVI_TINH', headerText: 'Đơn vị tính', allowFiltering: true },
                      { fieldName: 'SL_TAMUNG', headerText: 'Tạm ứng', allowFiltering: true },
                      { fieldName: 'SL_TON', headerText: 'SL tồn', allowFiltering: true },
                      { fieldName: 'SOLUONG', headerText: 'SL tồn thực', allowFiltering: true },
                      { fieldName: 'SL_TREO', headerText: 'SL DD', allowFiltering: true },
                      { fieldName: 'SL_XUAT', headerText: 'SL xuất', allowFiltering: true },
                      { fieldName: 'DONGIA', headerText: 'Đơn giá', allowFiltering: true },
                      { fieldName: 'THANHTIEN', headerText: 'Thành tiền', allowFiltering: true }
                    ]"
                    :dataSource="grvVatTu"
                    :showFilter="true"
                    :showColumnCheckbox="true"
                    ref="selectedVatTu"
                    dataKeyField="MA_TT"
                    :enabledSelectFirstRow="false"
                    :allowPaging="true"
                    :enablePagingServer="false"
                  >
                  </DataGrid>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import moment from 'moment'
import api from './api'

export default {
  emits: ['onBtnXacNhanClicked'],
  props: {
    vkho_id: {
      type: [String, Number],
      defaul: null,
      require: false
    },
    vngay_ct: {
      type: [String],
      defaul: null,
      require: false
    },
    vchungtu_id: {
      type: [String, Number],
      defaul: null,
      require: false
    },
  },
  components: {},
  mounted() {},
  provide: {},
  computed: {},
  data() {
    return {
      xacnhan: false,
      isVisibleChonLoHang: true,
      isVisibleXacNhan: false,
      isVisibleHuyBo: false,
      isTab: false,

      enabledTukho: true,
      enabledDenNgay: true,

      txtKhoId: 11279,
      txtDenNgay: '13/12/2021',
      cboKho: [],

      grvTonKho: [],
      selectedGrvTonKho: [],

      grvDaGan: [],
      selectedGrvDaGan: [],

      grvVatTu: [],
      selectedGrvVatTu: [],

      grvVTGroup: [],
      selectedGrvVTGroup: [],

      dsVatTu: [],
      dsGan: [],
      dsDaGan: [],
      dateFormat: 'dd/MM/yyyy',
      animationSettings: { effect: 'Zoom' },
      target: '.main-wrapper'
    }
  },
  methods: {
    async onCreate() {
      var res = await api.ds_kho_nhan_vien(this.axios, this.$auth.getNhanVienID())

      this.cboKho = res.data.data
      this.enabledTukho = false
      this.enabledDenNgay = false
      this.NAP_DS_TONKHO_GROUP()
      this.vkho_id = this.txtKhoId
    },

    async NAP_DS_TONKHO_GROUP() {
      this.grvTonKho = []
      var res = await api.ds_vattu_ton_kho_v2(this.axios, {
        khoId: this.txtKhoId,
        ngayCT: this.txtDenNgay,
        chungTuId: this.vchungtu_id
      })
      this.grvTonKho = res.data.data
      this.dsVatTu = this.grvTonKho
    },

    huyBo() {
      this.grvDaGan = []
      this.grvVTGroup = []
      this.dsGan = []
      this.dsVatTu = []
      this.isTab = false
    },

    changeTab(data) {
      if (data == 1) {
        this.isVisibleXacNhan = true
        this.isVisibleChonLoHang = false
        this.isVisibleHuyBo = true
        this.isTab = true
      } else {
        this.isVisibleXacNhan = false
        this.isVisibleChonLoHang = true
        this.isVisibleHuyBo = false
        this.isTab = false
      }
    },

    async onClickChonLoHang() {
      this.dsVatTu = []
      this.dsGan = []
      const dsTonKhoDaChon = this.$refs.selectedGrvTonKho.getSelectedRecords() || []

      if (dsTonKhoDaChon.length == 0) {
        this.$toast.error('Không có dữ liệu tồn kho không thể chọn lô')
        return
      }

      dsTonKhoDaChon.forEach((item) => {
        if (item.SL_TON < item.SL_XUAT) {
          this.$toast.error('Số lượng xuất phải nhỏ hơn số lượng tồn')
          return
        }
      })
      this.grvVTGroup = dsTonKhoDaChon
      this.changeTab(1)
    },

    async onChangeVTGroup(data) {
      if (data) {
        this.selectedGrvVTGroup = data
        this.NAP_DS_DAGAN()
        this.NAP_DS_CHITIET_TT()
      } else {
        this.grvDaGan = []
        this.grvVatTu = []
      }
    },

    NAP_DS_DAGAN() {
      this.grvDaGan = []

      if (this.dsGan.length > 0) {
        this.grvDaGan = this.dsGan.filter((x) => x.VATTU_ID.toString() == this.selectedGrvVTGroup.VATTU_ID.toString())
      }
    },

    NAP_DS_CHITIET_TT() {
      this.grvVatTu = []

      let loc = []
      if (this.dsGan.length > 0) {
        this.dsGan.forEach((element) => {
          if (element.VATTU_ID == this.selectedGrvVTGroup.VATTU_ID) {
            loc.push(element.LOHANG)
          }
        })
      }

      let ds = []
      if (loc.length > 0) {
        this.grvVatTu = this.grvTonKho.filter((x) => x.VATTU_ID == this.selectedGrvVTGroup.VATTU_ID && !loc.includes(x.LOHANG))
      } else {
        this.grvVatTu = this.grvTonKho.filter((x) => x.VATTU_ID == this.selectedGrvVTGroup.VATTU_ID)
      }
    },

    onClickHuyBo() {
      this.huyBo()
    },

    moveRight() {
      if (this.grvDaGan.length == 0) {
        this.$toast.error('Không có dữ liệu để thao tác')
        return
      }

      const dsDaGanDaChon = this.$refs.selectedDaGan.getSelectedRecords() || []

      if (dsDaGanDaChon.length == 0) {
        this.$toast.error('Bạn chưa chọn danh sách lô hàng để gỡ')
        return
      }

      let rowToDelete = []
      let rowToDeleteVatTu = []

      if (this.dsGan.length > 0) {
        rowToDelete = dsDaGanDaChon.filter((x) => x.VATTU_ID == this.selectedGrvVTGroup.VATTU_ID)
      }

      if (this.dsVatTu.length > 0) {
        this.dsVatTu.forEach((x) => {
          if (x.VATTU_ID == this.selectedGrvVTGroup.VATTU_ID) {
            rowToDelete.forEach((element) => {
              if (element.LOHANG == x.LOHANG && element.VATTU_ID == x.VATTU_ID) {
                rowToDeleteVatTu.push(x)
              }
            })
          }
        })
      }

      rowToDelete.forEach((element) => {
        this.dsGan = this.dsGan.filter((x) => x.VATTU_ID != element.VATTU_ID || element.LOHANG != x.LOHANG)
      })

      rowToDeleteVatTu.forEach((element) => {
        this.dsVatTu = this.dsVatTu.filter((x) => x.VATTU_ID != element.VATTU_ID || element.LOHANG != x.LOHANG)
      })

      this.NAP_DS_DAGAN()
      this.NAP_DS_CHITIET_TT()
    },

    moveLeft() {
      if (this.grvVatTu.length == 0) {
        this.$toast.error('Không có dữ liệu để thao tác')
        return
      }

      const dsChuaGanDaChon = this.$refs.selectedVatTu.getSelectedRecords() || []
      if (dsChuaGanDaChon.length == 0) {
        this.$toast.error('Bạn chưa chọn danh sách lô hàng để gán')
        return
      }
      this.dsGan = [...this.dsGan, ...dsChuaGanDaChon]
      this.dsVatTu = [...this.dsVatTu, ...dsChuaGanDaChon]
      this.NAP_DS_DAGAN()
      this.NAP_DS_CHITIET_TT()
    },

    onClickXacNhan() {
      if (this.dsGan.length == 0) {
        this.$toast.error('Chưa có danh sách gán không thể xác nhận')
        return
      }

      if (this.grvVTGroup.length == 0) {
        this.$toast.error('Chưa có danh sách tồn kho không thể xác nhận')
        return
      }

      let vatTuIds = []

      this.grvVTGroup.forEach((element) => {
        vatTuIds.push(element.VATTU_ID)
      })
      if (vatTuIds.length == vatTuIds.map().length) {
        this.$toast.error('Có vật tư chưa gán lô bạn hãy kiểm tra lại')
        return
      }

      this.grvVTGroup.forEach((element) => {
        let sum = 0
        this.dsGan.forEach((x) => {
          if (x.VATTU_ID == element.VATTU_ID) {
            sum += x.SOLUONG
          }
        })
        if (element.SOLUONG > sum) {
          this.$toast.error('Có vật tư chưa đủ số lượng của lô ' + element.MA_VT + ' có số lượng ' + sum + '/' + element.SOLUONG)
          return
        }
      })

      //TODO: làm tiếp nghe
      // Đức nối NhapXuatV2
      this.xacnhan = true
      this.$emit('onBtnXacNhanClicked', {xacnhan: this.xacnhan})
      // this.$refs.popupVatTuTrongKhoV2.hide()
      // this.$parent.XacNhan_frmVatTuTrongKho_V2()
    },
    frmVatTuTrongKho_V2_Load() {
      this.onCreate()
    },
    // OpenDiaLog() {
    //   this.$refs.popupVatTuTrongKhoV2.show()
    // }
  },
  created: async function() {
    // this.onCreate();
  },
  watch: {},
  destroyed() {}
}
</script>
<style></style>
