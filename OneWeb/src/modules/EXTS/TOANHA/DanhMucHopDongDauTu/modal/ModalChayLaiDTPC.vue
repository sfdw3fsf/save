<template>
  <b-modal ref="dlgTChayLaiDTPC" size="lg" body-class="p-0" scrollable title="Cập nhật lại doanh thu phân chia" hide-footer @hide="hideModal">
    <div class="list-actions-top">
      <ul class="list">
        <li>
          <a href="javascript: void(0)" @click="onKhoiTao" title="Khởi tạo"> <span class="icon one-reload1"></span> Khởi tạo</a>
        </li>
        <li>
          <a href="javascript: void(0)" @click="onCapNhat" title="Cập nhật" :class="{ disabled: !getEnableBtnUpdate }"> <span class="icon one-save"></span>Cập nhật </a>
        </li>
        <li>
          <a href="javascript: void(0)" @click="hideHuy" title="Huỷ"> <span class="icon one-export"></span> Huỷ </a>
        </li>
      </ul>
    </div>
    <div class="page-content">
      <div class="box-form d-flex flex-column gap-2">
        <div class="info-row">
          <div class="key w120">Mã dự án</div>
          <div class="value">
            <input type="text" class="form-control" disabled v-model="this.maDuAn" />
          </div>
        </div>
        <div class="info-row">
          <div class="key w120">
            <span class="name">Từ kỳ</span>
          </div>
          <div class="value">
            <ejs-datepicker class="form-control bg-none bor0 fw6 text" v-model="from" :placeholder="waterMark" start="Year" depth="Year" :format="dateFormat" :showClearButton="false" :allowEdit="true"> </ejs-datepicker>
          </div>
        </div>
        <div class="info-row">
          <div class="key w120">
            <span class="name">Đến kỳ</span>
          </div>
          <div class="value">
            <ejs-datepicker class="form-control bg-none bor0 fw6 text" v-model="to" :placeholder="waterMark" start="Year" depth="Year" :format="dateFormat" :showClearButton="false" :allowEdit="true"> </ejs-datepicker>
          </div>
        </div>
        <div class="info-row">
          <div class="key w120">
            <span class="name"></span>
          </div>
          <div class="value d-flex gap-1 align-items-center">
            <input type="checkbox" name="checkOnline" checked />
            <label for="checkOnline" id="checkOnline" class="m-0">Cập nhật tỷ lệ PCDT theo hợp đồng online</label>
          </div>
        </div>
      </div>
      <div class="box-for">
        <DataGrid
          v-bind:columns="[
            { fieldName: 'chuKy', headerText: 'Chu kỳ', allowFiltering: true, allowSorting: false, width: 60 },
            { fieldName: 'maDuAn', headerText: 'Mã dự án', allowFiltering: true, allowSorting: false, width: 110 },
            { fieldName: 'trangThai', headerText: 'Trạng thái', allowFiltering: true, allowSorting: false, width: 110, textAlign: 'center' },
            { fieldName: 'ghiChu', headerText: 'Ghi chú', allowFiltering: true, allowSorting: false, width: 160, textAlign: 'center' }
          ]"
          v-bind:dataSource="ds"
          :enabledSelectFirstRow="false"
          :showFilter="true"
          :allowPaging="true"
          ref="hopDongGrid"
          :enablePagingServer="false"
          :allowExcelExport="true"
        >
        </DataGrid>
      </div>
    </div>
  </b-modal>
</template>
<script>
export default {
  name: 'PopupChayLaiDTPC',
  components: {},
  props: {
    maDuAn: {
      type: String,
      default: ''
    },
    hddtuId: {
      type: Number,
      default: 0
    },
    checkDTPC: {
      type: Boolean,
      default: false
    },
    txt_TyLePCDT: {
      type: Number,
      default: ''
    },
    chb_GoiCuocTichHop: {
      type: Boolean,
      default: false
    }
  },
  computed: {
    getEnableBtnUpdate() {
      return this.ds.length > 0 && this.ds.every((item) => item.kq == 1)
    }
  },
  data() {
    return {
      ds: [],
      waterMark: 'Chọn chu kỳ',
      dateFormat: 'MM/yyyy',
      from: '',
      to: ''
    }
  },
  methods: {
    showModal() {
      this.$refs['dlgTChayLaiDTPC'].show()
    },
    hideModal() {
      this.ds = []
      this.from = ''
      this.to = ''
      this.$refs['dlgTChayLaiDTPC'].hide()
    },
    async onKhoiTao() {
      console.log(this.from, this.to)
      if (this.from == '' || this.to == '' || this.from == null || this.to == null) {
        this.$toast.error('Vui lòng chọn kỳ')
        return
      }
      if (this.from > this.to) {
        this.$toast.error('Kỳ bắt đầu phải nhỏ hơn kỳ kết thúc')
        return
      }
      this.ds = []
      let soThang = (this.to.getFullYear() - this.from.getFullYear()) * 12 + (this.to.getMonth() - this.from.getMonth()) + 1
      if (this.checkDTPC) {
        for (let i = 0; i < soThang; i++) {
          let calculatedMonth = this.from.getMonth() + i + 1
          let yearOffset = Math.floor((calculatedMonth - 1) / 12)
          let correctMonth = calculatedMonth % 12 === 0 ? 12 : calculatedMonth % 12
          let correctYear = this.from.getFullYear() + yearOffset
          let item = {
            chuKy: this.formatMonth(correctMonth) + '/' + correctYear,
            maDuAn: this.maDuAn,
            trangThai: '',
            ghiChu: ''
          }
          const rs = await this.$root.context.post('/web-toanha/danhmuc-hopdong-dautu/doanhthu_phanchia_kiemtra', {
            maDuAn: this.maDuAn,
            chuky: parseInt(correctYear + '' + this.formatMonth(correctMonth)),
            hddtu_id: this.hddtuId
          })
          item.trangThai = rs.data[0].kq == 0 ? '⚠️' : ''
          item.ghiChu = rs.data[0].kq == 0 ? rs.data[0].thongbao : ''
          item.kq = rs.data[0].kq
          this.ds.push(item)
        }
      } else {
        let chuKyFrom = new Date(this.from)
        chuKyFrom.setMonth(chuKyFrom.getMonth() - 1)
        for (let i = 0; i < soThang + 1; i++) {
          let calculatedMonth = chuKyFrom.getMonth() + i + 1
          let yearOffset = Math.floor((calculatedMonth - 1) / 12)
          let correctMonth = calculatedMonth % 12 === 0 ? 12 : calculatedMonth % 12
          let correctYear = chuKyFrom.getFullYear() + yearOffset
          let item = {
            chuKy: this.formatMonth(correctMonth) + '/' + correctYear,
            maDuAn: this.maDuAn,
            trangThai: '',
            ghiChu: ''
          }
          const rs = await this.$root.context.post('/web-toanha/danhmuc-hopdong-dautu/doanhthu_phanchia_kiemtra', {
            maDuAn: this.maDuAn,
            chuky: parseInt(correctYear + '' + this.formatMonth(correctMonth)),
            hddtu_id: this.hddtuId
          })
          item.trangThai = rs.data[0].kq == 0 ? '⚠️' : ''
          item.ghiChu = rs.data[0].kq == 0 ? rs.data[0].thongbao : ''
          item.kq = rs.data[0].kq
          this.ds.push(item)
        }
      }
    },
    formatMonth(month) {
      return month < 10 ? '0' + month : '' + month
    },
    async onCapNhat() {
      try {
        this.$root.showLoading(true)
        for (let i = 0; i < this.ds.length; i++) {
          let item = this.ds[i]
          const rs = await this.$root.context.post('/web-toanha/danhmuc-hopdong-dautu/doanhthu_toanha_thaydoi', {
            maDuAn: this.maDuAn,
            chuky: parseInt(item.chuKy.split('/')[1] + '' + this.formatMonth(parseInt(item.chuKy.split('/')[0]))),
            hddtu_id: this.hddtuId,
            tyle_pcdt: this.txt_TyLePCDT,
            goicuoc_tichhop: this.chb_GoiCuocTichHop ? 1 : 0
          })
          if (rs.data[0].kq == 1) {
            this.$set(this.ds, i, { ...item, trangThai: '✅', kqcn: 1, ghiChu: rs.data[0].thongbao })
          } else {
            item.trangThai = ''
            this.$toast.error(rs.data[0].thongbao)
            this.$root.showLoading(false)
            break
          }
        }
        if (this.ds.every((item) => item.kqcn == 1)) {
          this.$toast.success('Cập nhật thành công')
        }
        this.$root.showLoading(false)
      } catch (error) {
        this.$root.showLoading(false)
      }
    },
    hideHuy() {
      this.hideModal()
    }
  }
}
</script>
<style>
.page-content {
  top: 40px !important;
  bottom: 0 !important;
}
.modal-content{
  max-height: 650px !important;
}
</style>
