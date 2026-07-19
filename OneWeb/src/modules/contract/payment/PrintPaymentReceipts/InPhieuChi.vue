<template>
  <b-modal
    id="in-phieu-chi"
    modal-class="popup-box"
    header-class="popup-header"
    body-class="none-padding"
    size="xl"
    hide-footer
  >
    <template #modal-header="{ close }">
      <div class="title">
        <span class="icon nc-icon-outline ui-1_settings"></span> In phiếu chi
      </div>
      <div
        @click="close"
        class="close -ap icon-close"
        data-dismiss="modal"
      ></div>
    </template>

    <div class="list-actions-top">
      <ul class="list">
        <li>
          <a @click="search"> <span class="icon one-search"></span> Tìm kiếm </a>
        </li>
        <li>
          <a> <span class="icon one-print"></span> In phiếu chi </a>
        </li>
      </ul>
    </div>
    <div class="popup-body mart10">
      <div class="box-form">
        <b-row>
          <b-col sm="5">
            <ThongTinTimKiem v-model="thongtintimkiem" />
          </b-col>
          <b-col sm="7">
            <ThongTinHoaDon />
          </b-col>
          <b-col sm="12">
            <DanhSachPhieu ref="danhsachphieu" :thongtintimkiem="thongtintimkiem" />
          </b-col>
        </b-row>
      </div>
    </div>
  </b-modal>
</template>
<script>
import ThongTinTimKiem from './part/ThongTinTimKiem'
import ThongTinHoaDon from './part/ThongTinHoaDon'
import DanhSachPhieu from './part/DanhSachPhieu'
export default {
  data() {
    return {
      thongtintimkiem: {
        ma_gd: '',
        ngay_bd: '',
        ngay_kt: ''
      }
    }
  },
  props: {
    open: Boolean,
  },
  components: {
      ThongTinTimKiem,
      ThongTinHoaDon,
      DanhSachPhieu
  },
  watch: {
    open(val) {
      if (val && val === true) {
        this.$bvModal.show("in-phieu-chi");
      } else {
        this.$bvModal.hide("in-phieu-chi");
      }
    },
  },
  methods: {
    search() {
      //console.log(this.$refs.danhsachphieu)
      if(this.$refs.danhsachphieu)
        this.$refs.danhsachphieu.getDanhSachThoaiTra()
    }
  }
};
</script>
<style>
    .none-padding {
        padding: 0 !important;
    }
    .modal-dialog {
        border-radius: 0px !important;
        width: 100% !important;
        max-width: 1220px !important;
    }
</style>