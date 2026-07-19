<template>
  <div class="modal-content popup-box">
    <div class="popup-header">
      <div class="title"><span class="icon one-notepad"></span> Địa chỉ thuê bao </div>
      <div  class="close -ap icon-close" data-dismiss="modal" @click="$bvModal.hide('popup-DiaChiThueBao')" ></div>
    </div>
    <div class="popup-body">
      <div class="box-form">
        <div class="row">
          <div class="col-sm-6 col-12">
            <div class="info-row">
              <div class="key w100">Tỉnh/Tp(*)</div>
              <div class="value">
                <div class="select-custom">
                    <Select2 v-model="tinhtp_id" :options="DmTinhTP" />
                </div>
              </div>
            </div>
            <div class="info-row">
              <div class="key w100">Quận/Huyện(*)</div>
              <div class="value">
                <div class="select-custom">
                    <Select2 v-model="quanhuyen_id" :options="DmQuanHuyen" />
                </div>
              </div>
            </div>
            <div class="info-row">
              <div class="key w100">Phường/Xã(*)</div>
              <div class="value">
                <div class="select-custom">
                    <Select2 v-model="phuongxa_id" :options="DmPhuongXa" />
                </div>
              </div>
            </div>
            <div class="info-row">
              <div class="key w100">Số nhà</div>
              <div class="value">
                <input type="text" class="form-control" />
              </div>
            </div>
          </div>
          <div class="col-sm-6 col-12">
            <div class="info-row">
              <div class="key w100">Đường/Phố</div>
              <div class="value padt7 w20">
                <div class="check-action">
                  <input type="checkbox" class="check" v-model="chkDuongPho"/>
                  <span class="name"></span>
                </div>
              </div>
              <div class="value">
                <div class="select-custom">
                    <Select2 v-model="duongpho_id" :disabled="!DuongPhoStatus" :options="DmDuongPho" />
                </div>
              </div>
            </div>
            <div class="info-row">
              <div class="key w100">Ngõ/Ấp</div>
              <div class="value padt7 w20">
                <div class="check-action">
                  <input type="checkbox" class="check" v-model="chkNgoAp"/>
                  <span class="name"></span>
                </div>
              </div>
              <div class="value">
                <div class="select-custom">
                    <Select2 v-model="ngoap_id" :disabled="!NgoApStatus" :options="DmNgoAp" />
                </div>
              </div>
            </div>
            <div class="info-row">
              <div class="key w100">Khu</div>
              <div class="value padt7 w20">
                <div class="check-action">
                  <input type="checkbox" class="check" v-model="chkKhu" />
                  <span class="name"></span>
                </div>
              </div>
              <div class="value">
                <div class="select-custom">
                    <Select2 v-model="khu_id" :disabled="!KhuStatus" :options="DmKhu" />
                </div>
              </div>
            </div>
            <div class="info-row">
              <div class="key w100">Đặc điểm</div>
              <div class="value padt7 w20">
                <div class="check-action">
                  <input type="checkbox" class="check" v-model="chkDacDiem"/>
                  <span class="name"></span>
                </div>
              </div>
              <div class="value">
                <div class="select-custom">
                    <Select2 v-model="dacdiem_id" :disabled="!DacDiemStatus" :options="DmDacDiem" />
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="box-form">
        <div class="legend-title">Thông tin thêm</div>
        <div class="row">
          <div class="col-sm-4 col-12">
            <div class="info-row">
              <div class="key w100">Block(lô/toà)</div>
              <div class="value">
                <input type="text" class="form-control" />
              </div>
            </div>
          </div>
          <div class="col-sm-3 col-12">
            <div class="info-row">
              <div class="key w70">Tầng/lầu</div>
              <div class="value">
                <input type="text" class="form-control" />
              </div>
            </div>
          </div>
          <div class="col-sm-5 col-12">
            <div class="info-row">
              <div class="key w140">Số căn hộ/sạp/phòng</div>
              <div class="value">
                <input type="text" class="form-control" />
              </div>
            </div>
          </div>
        </div>
        <div class="info-row">
          <div class="key w100">Mô tả thêm</div>
          <div class="value">
            <input type="text" class="form-control" />
          </div>
        </div>
        <div class="info-row">
          <div class="key w100">Địa chỉ</div>
          <div class="value">
            <textarea
              name=""
              class="form-control highlight"
              style="height: 100px; resize: none"
            ></textarea>
          </div>
        </div>
      </div>
      <div class="group-buttons -bottom center">
        <button class="btn btn-outline-secondary nocorner" style="width: 120px">
          <span class="icon one-map-location1"></span> Vị trí
        </button>
        <button class="btn btn-second nocorner" style="width: 120px">
          <span class="icon nc-icon-glyph ui-1_check-circle-08"></span> Chấp
          nhận
        </button>
      </div>
    </div>
  </div>
</template>
<script>
import gridView from "@/modules/prepaid/ChoosePreapaidNumber/components/gridview.vue";
import treegridView from "@/modules/contract/setup/ManageSelfcareContract/components/treegridview.vue";
import breadcrumb from "@/components/breadcrumb";
import moment from "moment";
import DatePicker from "vue2-datepicker";
import "vue2-datepicker/locale/vi.js";
import "vue2-datepicker/index.css";
import api from "../Api";
export default {
  name: "TraCuuDiaChi",
  props: ["loaiDs"],
  components: {
    breadcrumb,
    gridView,
    treegridView,
    moment,
    DatePicker,
  },
  data() {
    return {
      txtNgayYeuCau: moment().format("YYYY-MM-DD"),
      txtDenNgay: moment().format("YYYY-MM-DD"),

      chkDuongPho: false,
      DuongPhoStatus: false,
      chkNgoAp: false,
      NgoApStatus: false,
      chkKhu: false,
      KhuStatus: false,
      chkDacDiem: false,
      DacDiemStatus: false,

      tinhtp_id: 0,
      DmTinhTP: [],
      quanhuyen_id: 0,
      DmQuanHuyen: [],
      phuongxa_id: 0,
      DmPhuongXa: [],
      duongpho_id: 0,
      DmDuongPho: [],
      ngoap_id: 0,
      DmNgoAp: [],
      khu_id: 0,
      DmKhu: [],
      dacdiem_id: 0,
      DmDacDiem: []
    };
  },
  watch: {
    chkDuongPho: function (val) {
      if (val) {
        this.DuongPhoStatus = true;
      } else {
        this.DuongPhoStatus = false;
      }
    },
    chkNgoAp: function (val) {
      if (val) {
        this.NgoApStatus = true;
      } else {
        this.NgoApStatus = false;
      }
    },
    chkKhu: function (val) {
      if (val) {
        this.KhuStatus = true;
      } else {
        this.KhuStatus = false;
      }
    },
    chkDacDiem: function (val) {
      if (val) {
        this.DacDiemStatus = true;
      } else {
        this.DacDiemStatus = false;
      }
    },
  },
  created() {},
  computed: {},
  mounted() {},
  methods: {},
};
</script>