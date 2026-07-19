<template>
  <b-modal id="popupTCTTTB" size="xl" hide-footer>
    <template #modal-title>
      <div class="title"><span class="icon one-notepad"></span> Tạo dải số</div>
    </template>
    <div class="modal-content popup-box">
      <div class="list-actions-top">
        <ul class="list">
          <li>
            <a @click="handleAccept"> <span class="icon one-save"></span> Chấp nhận </a>
          </li>
        </ul>
      </div>
      <div class="popup-body">
        <div class="row">
          <div class="col-sm-9 col-12">
            <ThongTinDaySo :isAccept="isAccept" :dataSelect="dataSelect"/>
            <DanhSachDaySo  @changeDataSelect="changeDataSelect"/>
          </div>
          <div class="col-sm-3 col-12">
            <DaySoHienTai  />
          </div>
        </div>
      </div>
    </div>
  </b-modal>
</template>

<script>
import { mapActions, mapState, mapGetters } from 'vuex'
import ThongTinDaySo from './part/taoDaySo/ThongTinDaySo'
import DanhSachDaySo from './part/taoDaySo/DanhSachDaySo'
import DaySoHienTai from './part/taoDaySo/DaySoHienTai'
export default {
  props: {
    isShow: Boolean
  },
  components: {
    ThongTinDaySo,
    DanhSachDaySo,
    DaySoHienTai
  },
  data () {
    return {
      isAccept: true,
      dataSelect: []
    }
  },

  computed: {
    ...mapState('prefixOfCounty', [
      'danhSachCacLoaiSo',
      'danhSachCacDonVi',
      'danhSachQuanHuyen',
      'danhSachPhuongXa',
      'danhSachTongDai',
      'danhSachDichVu',
      'dataItem',
      'isShowModal',
      'paramFixedNumber'
    ])
  },
  watch: {
    paramFixedNumber (newVal) {
      this.getDanhSachDaiCoDinh()
    }
  },
  methods: {
    ...mapActions('prefixOfCounty', ['layDanhSachPhuongXa', 'layDanhSachPrefixChuaChon', 'layDanhSachPrefixDaChon', 'layDanhSachDichVu', 'layDanhSachDaiCoDinh']),
    handleAccept () {
      this.isAccept = !this.isAccept
    },
    changeDataSelect (item) {
      this.dataSelect = item
    },
    getDanhSachDaiCoDinh () {
      let obj = {
        dichvu_vienthong_id: this.paramFixedNumber.dichVuID ? this.paramFixedNumber.dichVuID : 0,
        kieuso_id: 0,
        loaiso_id: this.paramFixedNumber.loaiSoID ? this.paramFixedNumber.loaiSoID : 0,
        nhomso_id: 0,
        prefix: this.paramFixedNumber.prefix ? this.paramFixedNumber.prefix : 0,
        so_batdau: this.paramFixedNumber.tuso ? this.paramFixedNumber.tuso : 0,
        so_ketthuc: this.paramFixedNumber.denso ? this.paramFixedNumber.denso : 0
      }
      this.layDanhSachDaiCoDinh(obj)
    }
  }
}
</script>
