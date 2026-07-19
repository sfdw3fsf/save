<template>
  <div class="main-wrapper">
    <div class="breadcrumb-top">
      <div class="main-title">Đầu số phường xã</div>
      <ul class="breadcrumb">
        <li class="breadcrumb-item">
          <a href="#"
            ><span class="nc-icon-outline ui-1_home-simple"></span> Trang chủ
          </a>
        </li>
        <li class="breadcrumb-item"><a href="#">Lập hợp đồng</a></li>
        <li class="breadcrumb-item active">Lắp đặt mới</li>
      </ul>
    </div>
    <div class="list-actions-top">
      <ul class="list">
        <li>
          <a  @click="handleSave"> <span class="icon one-save"></span> Ghi lại </a>
        </li>
        <li>
          <a @click="handleShowModal" > <span class="icon one-file-plus-1"></span>Tạo dải số </a>
        </li>
      </ul>
    </div>
    <div class="page-content">
      <ThongTinDauSo :saveForm="saveForm" @tuso="getTuSo" @denso="getDenSo" :dataSelect="dataSelect"/>
      <div class="box-move-select-table">
        <PrefixDaChon  @changeSelectedPrefix="handlePrefix" @changPrefixNotPSelect="handleNotPrefix" />
         <Action @changPrefixSelect="changPrefixSelect" @changPrefixNotPSelect ="changPrefixNotPSelect" @changAllNotSelect="changAllNotSelect" @changAllSelect="changAllSelect"/>
        <PrefixChuaChon @changeSelectedNotPrefix="handleNotPrefix"/>
      </div>
    </div>
    <TaoDaySo :isShow="isShow"/>
  </div>
</template>
<script>
import { mapActions, mapState, mapGetters } from 'vuex'
import ThongTinDauSo from './part/dauSoPhuongXa/ThongTinDauSo'
import PrefixDaChon from './part/dauSoPhuongXa/PrefixDaChon'
import PrefixChuaChon from './part/dauSoPhuongXa/PrefixChuaChon'
import Action from './part/dauSoPhuongXa/Action'
import TaoDaySo from './TaoDaySo'
export default {
  components: {
    ThongTinDauSo,
    PrefixDaChon,
    PrefixChuaChon,
    Action,
    TaoDaySo

  },
  data () {
    return {
      saveForm: false,
      dataNotSelect: [],
      dataSelect: [],
      isShow: false,
      tuSo : '',
      denSo : ''
    }
  },
  computed: {
    ...mapState('prefixOfCounty', ['danhSachPrefixChuaChon', 'isLoading', 'danhSachPrefixDaChon', 'isShowModal'])
  },
  created () {
    this.layDanhSachLoaiSo()
    this.layDanhSachQuanHuyen()
    this.layDanhSachCacDonVi({loai_donvi_id: 3})
    this.layDanhSachTongDai({loai_donvi_id: 16})
    this.layDanhSachPrefixChuaChon({
      'dichvuvt_id': null,
      'donvi_ql_id': null,
      'host_id': null,
      'loaiso_id': null,
      'phuong_id': null,
      'quan_id': null,
      'trangthai_chon': 'chua-chon'
    })
    this.layDanhSachPrefixDaChon({
      'dichvuvt_id': null,
      'donvi_ql_id': null,
      'host_id': null,
      'loaiso_id': null,
      'phuong_id': null,
      'quan_id': null,
      'trangthai_chon': ' da-chon'

    })
  },

  methods: {
    ...mapActions('prefixOfCounty', ['layDanhSachLoaiSo', 'layDanhSachQuanHuyen', 'layDanhSachCacDonVi', 'layDanhSachTongDai', 'layDanhSachPrefixChuaChon', 'layDanhSachPrefixDaChon']),
    handleShowModal () {
      this.$bvModal.show('popupTCTTTB')
      this.$store.commit('prefixOfCounty/SET_SHOW_MODAL', !this.isShowModal)
    },
    handleSave () {
      this.saveForm = !this.saveForm
    },
    getTuSo(value) {
      this.tuSo = value
    },
    getDenSo(value) {
      this.denSo = value
    },
    handleNotPrefix (data) {
      this.dataNotSelect = data
    },
    handlePrefix (data) {
      this.dataSelect = data
    },
    changAllNotSelect () {
      if (this.danhSachPrefixChuaChon.length > 0) {
        this.danhSachPrefixChuaChon.map(item => {
          item.tuso = this.tuSo,
          item.denso = this.denSo
        })
        const data = [...this.danhSachPrefixChuaChon, ...this.danhSachPrefixDaChon]
        this.$store.commit('prefixOfCounty/SET_DANH_SACH_PREFIX_DA_CHON', data)
        this.$store.commit('prefixOfCounty/SET_DANH_SACH_PREFIX_CHUA_CHON', [])
      }
    },
    changAllSelect () {
      if (this.danhSachPrefixDaChon.length > 0) {
        const data = [...this.danhSachPrefixDaChon, ...this.danhSachPrefixChuaChon]
        this.$store.commit('prefixOfCounty/SET_DANH_SACH_PREFIX_DA_CHON', [])
        this.$store.commit('prefixOfCounty/SET_DANH_SACH_PREFIX_CHUA_CHON', data)
      }
    },
    changPrefixNotPSelect () {
      if (this.danhSachPrefixDaChon.length > 0) {
        const data = [...this.dataSelect, ...this.danhSachPrefixChuaChon]
        const dataPrefixDaChon = this.danhSachPrefixDaChon.filter(item => !this.dataSelect.includes(item))
        this.$store.commit('prefixOfCounty/SET_DANH_SACH_PREFIX_CHUA_CHON', data)
        this.$store.commit('prefixOfCounty/SET_DANH_SACH_PREFIX_DA_CHON', dataPrefixDaChon)
      }
    },
    changPrefixSelect () {
      if (this.danhSachPrefixChuaChon.length > 0) {
        if (this.dataNotSelect.length > 0) {
          this.dataNotSelect.map(item => {
            item.tuso = this.tuSo,
            item.denso = this.denSo
          })
        }
        const data = [...this.dataNotSelect, ...this.danhSachPrefixDaChon]
        console.log('data is ', data)
        const dataPrefixChuaChon = this.danhSachPrefixChuaChon.filter(item => !this.dataNotSelect.includes(item))
        console.log('dataPrefixChuaChon', dataPrefixChuaChon)
        this.$store.commit('prefixOfCounty/SET_DANH_SACH_PREFIX_DA_CHON', data)
        this.$store.commit('prefixOfCounty/SET_DANH_SACH_PREFIX_CHUA_CHON', dataPrefixChuaChon)
      }
      // this.$store.commit('prefixOfCounty/SET_DANH_SACH_PREFIX_DA_CHON', newVal)
    }
  }
}
</script>
