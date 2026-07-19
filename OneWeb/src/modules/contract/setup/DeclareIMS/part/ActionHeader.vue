<template>
  <div class="list-actions-top">
    <ul class="list">
      <li>
        <a href="#" @click.prevent="layThongTin"> <span class="icon one-file-attach"></span> Lấy TT</a>
      </li>
      <li>
        <a href="#" @click.prevent="update"> <span class="icon one-reload1"></span> Cập nhật </a>
      </li>
      <li v-if="checkVisibleButton('GIAOVIEC')">
        <a href="#" @click.prevent="giaoViec"> <span class="dropdown-icon one-file-edit2"></span> Giao việc </a>
      </li>

      <li v-if="checkVisibleButton('GIAOPHIEU')">
        <a :class="{ disabled: !statusButton.isEnableButtonGiaoPhieu }" href="#" @click.prevent="giaoPhieu"> <span class="icon one-file-arrow-right1"></span> Giao phiếu </a>
      </li>
      <li>
        <a href="#" @click.prevent="giaoHostCu"> <span class="icon one-file-arrow-right1"></span> Giao host cũ </a>
      </li>
      <li>
        <a href="#" @click.prevent="khoiPhucSo"> <span class="icon nc-icon-glyph arrows-1_refresh-68"></span> Khôi phục số </a>
      </li>

      <li v-if="checkVisibleButton('KICHHOAT_TD')">
        <a :class="{ disabled: !statusButton.isEnableButtonKichHoat }" @click.prevent="kichHoat" href="#"> <span class="icon one-file-arrow-up"></span> Kích hoạt </a>
      </li>
      <li v-if="checkVisibleButton('TSBTNHUYKH')">
        <a :class="{ disabled: !statusButton.isEnableButtonHuyKichHoat }" @click.prevent="huyKichHoat" href="#"> <span class="icon nc-icon-glyph ui-1_circle-remove"></span> Hủy kích hoạt </a>
      </li>
      <li v-show="$route.query.enableNutHuy">
        <a @click.prevent="huyKichHoat" href="#"> <span class="icon nc-icon-glyph ui-1_circle-remove"></span> Hủy kích hoạt </a>
      </li>
      <!-- v-if="checkVisibleButton('CAPCAP_TB')" -->
      <li>
        <a href="#" @click.prevent="dauNoi"> <span class="icon one-connect"></span>Đấu nối </a>
      </li>
      <li v-if="checkVisibleButton('HOANCONG')">
        <a :class="{ disabled: !statusButton.isEnableButtonHoanCong }" href="#" @click.prevent="hoanCong"> <span class="dropdown-icon one-hoancong"></span> Hoàn công </a>
      </li>

      <li>
        <a href="#" @click.prevent="inPhieu"> <span class="icon one-xlsx-import"></span>In phiếu </a>
      </li>
      <li v-if="checkVisibleButton('TSBTNCHUYENTO')">
        <a href="#" @click.prevent="chuyenTo"> <span class="icon one-change"></span>Chuyển tổ </a>
      </li>
      <li>
        <a href="#" @click.prevent="traPhieu"> <span class="icon one-file-back"></span>Trả phiếu </a>
      </li>
    </ul>
    <div class="more dropdown">
      <a data-toggle="dropdown" href="#">
        <span class="icon -ap icon-dots-three-vertical"></span>
      </a>
      <div class="dropdown-menu ovauto" style="max-height: 300px">
        <a href="#" @click.prevent="doiCong"> <span class="dropdown-icon one-sync"></span> Đổi cổng </a>
        <a v-if="checkVisibleButton('VAT_TU_MOI')" href="#" @click.prevent="vatTu"> <span class="dropdown-icon one-vt"></span> Vật tư </a>
        <a href="#" @click.prevent="xuatFile"> <span class="dropdown-icon one-download"></span> Xuất file </a>
        <a href="#" @click.prevent="henLapDat"> <span class="dropdown-icon one-henld"></span> Hẹn LĐ </a>
        <a href="#" @click.prevent="thongSoKyThuat"> <span class="dropdown-icon nc-icon-glyph ui-1_settings"></span> Thông số KT </a>
        <a href="#" @click.prevent="ganKhuVuc"> <span class="dropdown-icon -ap icon-pin"></span> Gán KV </a>
        <a href="#" @click.prevent="duAn"> <span class="dropdown-icon one-clipart-edit"></span> Dự án </a>
        <a href="#" @click.prevent="tonPhieu"> <span class="dropdown-icon one-calendar-number"></span> Tồn phiếu </a>
        <a href="#" @click.prevent="doKiem"> <span class="dropdown-icon nc-icon-glyph design_design"></span> Đo kiểm </a>
        <a v-if="checkVisibleButton('HOANTHIENHOSO')" href="#" @click.prevent="hoanThien"> <span class="dropdown-icon one-file-check"></span> Hoàn thiện </a>
        <a v-if="statusButton.isShowButtonLienHe" href="#" @click.prevent="lienHe"> <span class="dropdown-icon one-calendar-number"></span> Liên Hệ </a>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'ActionHeader',
  components: {},
  data: () => ({}),
  computed: {},
  props: {
    statusButton: Object,
    danhSachThaoTac: {
      type: Array,
      default: () => []
    }
  },
  methods: {
    checkVisibleButton(code) {
      if (!this.danhSachThaoTac || this.danhSachThaoTac.length === 0) {
        return true
      }
      const thaoTac = this.danhSachThaoTac.find((element) => element.code === code)
      if (!thaoTac) {
        return false
      }
      return Number(thaoTac.enable) === 1
    },

    giaoViec() {
      this.$emit('giaoViec')
    },
    update() {
      this.$emit('update')
    },
    giaoHostCu() {
      this.$emit('giaoHostCu')
    },
    khoiPhucSo() {
      this.$emit('khoiPhucSo')
    },
    giaoPhieu() {
      this.$emit('giaoPhieu')
    },
    tonPhieu() {
      this.$emit('tonPhieu')
    },
    xuatFile() {
      this.$emit('xuatFile')
    },
    dauNoi() {
      this.$emit('dauNoi')
    },
    chuyenTo() {
      this.$emit('chuyenTo')
    },
    traPhieu() {
      this.$emit('traPhieu')
    },
    doiCong() {
      this.$emit('doiCong')
    },
    duAn() {
      this.$emit('duAn')
    },
    thongSoKyThuat() {
      this.$emit('thongSoKyThuat')
    },
    ganKhuVuc() {
      this.$emit('ganKhuVuc')
    },
    henLapDat() {
      this.$emit('henLapDat')
    },
    lienHe() {
      this.$emit('lienHe')
    },
    hoanThien() {
      this.$emit('hoanThien')
    },
    hoanCong() {
      this.$emit('hoanCong')
    },
    inPhieu() {
      this.$emit('inPhieu')
    },
    layThongTin() {
      this.$emit('layThongTin')
    },
    doKiem() {
      this.$emit('doKiem')
    },
    vatTu() {
      this.$emit('vatTu')
    },
    huyKichHoat() {
      this.$emit('huyKichHoat')
    },
    kichHoat() {
      this.$emit('kichHoat')
    }
  }
}
</script>
