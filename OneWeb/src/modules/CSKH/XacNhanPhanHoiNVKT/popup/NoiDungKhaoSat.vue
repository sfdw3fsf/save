<template>
  <b-modal
    id="popup-noiDungKhaoSat"
    size="xl"
    hide-footer
    hide-header
    hide-header-close
    body-class="modal-body p-0"
    show="onShow"
  >
    <div class="modal-content popup-box">
      <div class="popup-header">
        <div class="title">
          <span class="icon one-notepad"></span> Nội dung đã khảo sát tại đài hỗ trợ
        </div>
        <div class="close -ap icon-close" data-dismiss="modal" @click="$bvModal.hide('popup-noiDungKhaoSat')"></div>
      </div>
      <div class="list-actions-top">
        <ul class="list">
          <li>
            <a href="javascript: void(0)" @click="ngheGhiAm"> <span class="icon fa fa-play"></span> Nghe ghi âm </a>
          </li>
          <!-- <li>
            <a href="#">
              <span class="icon one-circle-question"></span> Trợ giúp
            </a>
          </li> -->
        </ul>
      </div>
      <div class="popup-body">
        <div class="box-form">
          <div class="legend-title">Nội dung khảo sát</div>
          <div class="info-row">
            <div class="key w60">Ghi chú</div>
            <div class="value">
              <input type="text" class="form-control" :value="ghiChu"/>
            </div>
          </div>
        </div>
         <div class="box-form" v-for="(cauHoi, index) in khaoSat.dsCauHoiChinh" :key="index">
        <div class="desc marb10">{{ cauHoi.TENCAUHOI }}</div>
        <div class="list-check-ver">
          <div
            class="item marb5"
            v-for="(cauHoiPhu, indexPhu) in cauHoi.cauHoiPhu"
            :key="cauHoiPhu.RCH_ID"
          >
            <div class="check-action -color">
              <input
                :type="cauHoi.LOAICH_ID == 1 ? 'radio' : 'checkbox'"
                class="check"
                :name="cauHoi.CAUHOI_ID"
                :value="cauHoiPhu.Checked"
                @change="onTraLoiChecked(index, indexPhu)"
              />
              <span class="name">{{ cauHoiPhu.NOIDUNG_TL }}</span>
            </div>
            <input type="text" class="form-control" v-if="cauHoiPhu.Checked && cauHoiPhu.LOAI == 1" v-model="cauHoiPhu.TxtNoiDung"/>
          </div>
        </div>
      </div>
      </div>
    </div>
  </b-modal>
</template>
<script>
import DanhSachThueBaoKhongKhaoSatApi from "../../api/DanhSachThueBaoKhongKhaoSatApi";
export default {
  name: "NoiDungKhaoSat",
  components: {},
  props: {
    ghiChu: {
      type: String,
      default: ''
    },
    tbtlId: {
      type: Number,
      default: 0
    },
    khaoSat: {
      type: Object,
      default: null
    }
  },
  data() {
    return {
      pKhaoSat: {}
    };
  },
  created() {
    this.pKhaoSat = this.khaoSat
  },
  computed: {
  },
  mounted() {},
  methods: {
    onShow() {
      this.pKhaoSat = this.khaoSat
    },
    ngheGhiAm(){
      this.$emit("ghiAm")
    }
  }
};
</script>
