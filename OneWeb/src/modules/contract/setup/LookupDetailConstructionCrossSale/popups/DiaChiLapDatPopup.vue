<template>
  <div class="modal-content popup-box">
    <div class="popup-header">
      <div class="title"><span class="icon one-notepad"></span>Địa chỉ lắp đặt</div>
      <div
        class="close -ap icon-close"
        data-dismiss="modal"
        @click="$bvModal.hide('popup-diachilapdat')"
      ></div>
    </div>
    <div class="popup-body">
      <div class="row">
        <div class="col-sm-6 col-12">
          <div class="info-row">
            <div class="key w120">Mã GD</div>
            <div class="value">
              <input type="text" class="form-control" readonly v-model="ma_gd">
            </div>
          </div>
        </div>
        <div class="col-sm-6 col-12">
          <div class="info-row">
            <div class="key w120">Loại HĐ/PL</div>
            <div class="value">
              <input type="text" class="form-control" readonly v-model="ten_loaihd">
            </div>
          </div>
        </div>
      </div>
      <div class="row">
        <div class="col-sm-6 col-12">
          <div class="info-row">
            <div class="key w120">Dịch vụ</div>
            <div class="value">
              <input type="text" class="form-control" readonly v-model="ten_dvvt">
            </div>
          </div>
        </div>
        <div class="col-sm-6 col-12">
          <div class="info-row">
            <div class="key w120">Số máy/Acc</div>
            <div class="value">
              <input type="text" class="form-control" readonly v-model="ma_tb">
            </div>
          </div>
        </div>
      </div>
      <div class="row">
        <div class="col-sm-3 col-12">
          <div class="info-row">
            <div class="key w120">Điện thoại LH đầu</div>
            <div class="value">
              <input type="text" class="form-control" readonly v-model="sdt_dau">
            </div>
          </div>
        </div>
        <div class="col-sm-3 col-12">
          <div class="info-row">
            <div class="key w120">Điện thoại LH cuối</div>
            <div class="value">
              <input type="text" class="form-control" readonly v-model="sdt_cuoi">
            </div>
          </div>
        </div>
        <div class="col-sm-6 col-12">
          <div class="info-row">
            <div class="key w120">Tốc độ</div>
            <div class="value">
              <input type="text" class="form-control" readonly v-model="tocdo">
            </div>
          </div>
        </div>
      </div>
      <div class="row">
        <div class="col-sm-6 col-12">
          <div class="info-row">
            <div class="key w120">Địa chỉ cũ</div>
            <div class="value">
              <input type="text" class="form-control" readonly v-model="dc_cu">
            </div>
          </div>
        </div>
        <div class="col-sm-6 col-12">
          <div class="info-row">
            <div class="key w120">Tổ thi công</div>
            <div class="value">
              <input type="text" class="form-control" readonly v-model="totc_cu">
            </div>
          </div>
        </div>
      </div>
      <div class="row">
        <div class="col-sm-6 col-12">
          <div class="info-row">
            <div class="key w120">Địa chỉ mới</div>
            <div class="value">
              <input type="text" class="form-control" readonly v-model="dc_moi">
            </div>
          </div>
        </div>
        <div class="col-sm-6 col-12">
          <div class="info-row">
            <div class="key w120">Tổ thi công</div>
            <div class="value">
              <input type="text" class="form-control" readonly v-model="totc_moi">
            </div>
          </div>
        </div>
      </div>
      <div class="row">
        <div class="col-sm-6 col-12">
          <div class="info-row">
            <div class="key w120">Đ/c điểm đầu cũ</div>
            <div class="value">
              <input type="text" class="form-control" readonly v-model="dc_dau_cu">
            </div>
          </div>
        </div>
        <div class="col-sm-6 col-12">
          <div class="info-row">
            <div class="key w120">Tổ thi công</div>
            <div class="value">
              <input type="text" class="form-control" readonly v-model="totc_dau_cu">
            </div>
          </div>
        </div>
      </div>
      <div class="row">
        <div class="col-sm-6 col-12">
          <div class="info-row">
            <div class="key w120">Đ/c điểm đầu mới</div>
            <div class="value">
              <input type="text" class="form-control" readonly v-model="dc_dau_moi">
            </div>
          </div>
        </div>
        <div class="col-sm-6 col-12">
          <div class="info-row">
            <div class="key w120">Tổ thi công</div>
            <div class="value">
              <input type="text" class="form-control" readonly v-model="totc_dau_moi">
            </div>
          </div>
        </div>
      </div>
      <div class="row">
        <div class="col-sm-6 col-12">
          <div class="info-row">
            <div class="key w120">Đ/c điểm cuối cũ</div>
            <div class="value">
              <input type="text" class="form-control" readonly v-model="dc_cuoi_cu">
            </div>
          </div>
        </div>
        <div class="col-sm-6 col-12">
          <div class="info-row">
            <div class="key w120">Tổ thi công</div>
            <div class="value">
              <input type="text" class="form-control" readonly v-model="totc_cuoi_cu">
            </div>
          </div>
        </div>
      </div>
      <div class="row">
        <div class="col-sm-6 col-12">
          <div class="info-row">
            <div class="key w120">Đ/c điểm cuối mới</div>
            <div class="value">
              <input type="text" class="form-control" readonly v-model="dc_cuoi_moi">
            </div>
          </div>
        </div>
        <div class="col-sm-6 col-12">
          <div class="info-row">
            <div class="key w120">Tổ thi công</div>
            <div class="value">
              <input type="text" class="form-control" readonly v-model="totc_cuoi_moi">
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import api from "../api";

// Tham chiếu UR2.8.004 mục Địa chỉ lắp đặt
export default {
  name: "DiaChiLapDatPopup",
  components: {

  },

  props: ['hdtb_id', 'ma_tb', 'ten_loaihd', 'ma_gd', 'dichvuvt_id', 'ten_dvvt'],

  mounted () {
    this.loadDiaChiLD();
  },

  data() {
    return {
      sdt_dau: "",
      sdt_cuoi: "",
      tocdo: "",
      dc_cu: "",
      totc_cu: "",
      dc_moi: "",
      totc_moi: "",
      dc_dau_cu: "",
      totc_dau_cu: "",
      dc_dau_moi: "",
      totc_dau_moi: "",
      dc_cuoi_cu: "",
      totc_cuoi_cu: "",
      dc_cuoi_moi: "",
      totc_cuoi_moi: "",
    };
  },

  methods: {
    async loadDiaChiLD() {
      try {
        this.loading(true);
        const ret = (await api.sp_lay_tt_show_diachi_ld(this.axios, {
          hdtb_id: this.hdtb_id,
          dichvu_id: this.dichvuvt_id
        })).data.data;

        this.sdt_dau = ret.sdt_dau;
        this.sdt_cuoi = ret.sdt_cuoi;
        this.tocdo = ret.tocdo;
        this.dc_cu = ret.dc_cu;
        this.totc_cu = ret.totc_cu;
        this.dc_moi = ret.dc_moi;
        this.totc_moi = ret.totc_moi;
        this.dc_dau_cu = ret.dc_dau_cu;
        this.totc_dau_cu = ret.totc_dau_cu;
        this.dc_dau_moi = ret.dc_dau_moi;
        this.totc_dau_moi = ret.totc_dau_moi;
        this.dc_cuoi_cu = ret.dc_cuoi_cu;
        this.totc_cuoi_cu = ret.totc_cuoi_cu;
        this.dc_cuoi_moi = ret.dc_cuoi_moi;
        this.totc_cuoi_moi = ret.totc_cuoi_moi;

      } catch (e) {
        this.$toast("Có lỗi khi lấy địa chỉ lắp đặt");
      } finally {
        this.loading(false);
      }
    }
  },
}
</script>
