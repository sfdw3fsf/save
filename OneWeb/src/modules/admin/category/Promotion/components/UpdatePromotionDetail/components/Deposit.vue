<template>
  <div class="tab-pane active" id="tab1">
    <p class="fw6 marb5 red">Số tháng đặt cọc</p>
    <div class="info-row">
      <div class="key w90">Nhóm Đ.Cọc</div>
      <div class="value">
        <div class="select-custom">
          <ejs-dropdownlist
            :dataSource="danhSachNhomDatCoc"
            class="combobox"
            v-model="form.nhom_datcoc_id"
            :fields="{ text: 'ten_nhom', value: 'nhom_datcoc_id' }"
            @filtering="
              onFilteringDropDownList($event, danhSachNhomKM, 'ten_nhom')
            "
            :allowFiltering="true"
          >
          </ejs-dropdownlist>
        </div>
      </div>
    </div>
    <div class="row">
      <div class="col-sm-6 col-12">
        <div class="info-row">
          <div class="key w90">Kiểu bắt đầu</div>
          <div class="value">
            <div class="select-custom">
              <ejs-dropdownlist
                ref="kieuBatDau"
                popupWidth="380px"

                class="combobox"
                v-model="form.kieu_batdau"
                :enabled="!form.nhom_datcoc_id <= 0"
                :dataSource="danhSachKieubatdau"
                :fields="{ text: 'name', value: 'id' }"
                @filtering="
                  onFilteringDropDownList($event, danhSachKieubatdau, 'name')
                "
                :allowFiltering="true"
              >
              </ejs-dropdownlist>
            </div>
          </div>
        </div>
      </div>
      <div class="col-sm-3 col-12">
        <div class="info-row">
          <div class="key w84">Tháng BDĐC</div>
          <div class="value">
            <ejs-numerictextbox
              id="thang_bddc"
              format="N"
              :enabled="!form.nhom_datcoc_id <= 0"
              :showSpinButton="false"
              v-model="form.thang_bddc"
              :value="form.thang_bddc"
              class="form-control tright pr-2"
            >
            </ejs-numerictextbox>
          </div>
        </div>
      </div>
      <div class="col-sm-3 col-12">
        <div class="info-row">
          <div class="key w60">Số tháng</div>
          <div class="value">
            <ejs-numerictextbox
              id="so_thang"
              format="N"
              :showSpinButton="false"
              :enabled="!form.nhom_datcoc_id <= 0"
              v-model="form.so_thang"
              :value="form.so_thang"
              class="form-control tright pr-2"
              @change="so_thang_changed"
            >
            </ejs-numerictextbox>
          </div>
        </div>
      </div>
    </div>
    <div class="row">
      <div class="col-sm-6 col-12">
        <div class="info-row">
          <div class="key w90">Đặt cọc</div>
          <div class="value">
            <ejs-tooltip
              class="tooltipcontainer"
              position="TopCenter"
              content="Tiền đặt cọc cước sử dụng,đã có VAT."
              target="#dat_coc"
            >
              <ejs-numerictextbox
                id="dat_coc"
                format="N"
                ref="DatCoc"
                :enabled="!form.nhom_datcoc_id <= 0"
                :showSpinButton="false"
                v-model="form.dat_coc"
                :value="form.dat_coc"
                class="form-control tright pr-2"
              >
              </ejs-numerictextbox>
            </ejs-tooltip>
          </div>
        </div>
      </div>
      <div class="col-sm-6 col-12">
        <div class="info-row">
          <div class="key w84">Tiền trừ dần</div>
          <div class="value">
            <ejs-tooltip
              class="tooltipcontainer"
              position="TopCenter"
              content="Trừ tiền đặt cọc hàng tháng cho khách hàng. Hệ thống sẽ sinh khoản mục âm tiền và không thuế."
              target="#tien_trudan"
            >
              <ejs-numerictextbox
                id="tien_trudan"
                ref="TienTruDan"
                :enabled="!form.nhom_datcoc_id <= 0"
                format="N"
                :showSpinButton="false"
                v-model="form.tien_trudan"
                :value="form.tien_trudan"
                @change="tien_trudan_changed"
                class="form-control tright pr-2"
              >
              </ejs-numerictextbox>
            </ejs-tooltip>
          </div>
          <div class="value padt7 w30 nowrap">(đ/tháng)</div>
        </div>
      </div>
    </div>
    <div class="info-row">
      <div class="key w84">
        <div class="check-action">
          <input
            type="checkbox"
            v-model="form.cho_phep_gdv"
            :disabled="!allowEditPrice"
            class="check"
          />
          <span class="name fw6">Cho phép GDV sửa tiền gói trả trước</span>
        </div>
      </div>
    </div>
    <div class="info-row">
      <div class="key w90">Nguồn KM</div>
      <div class="value">
        <div class="select-custom">
          <ejs-dropdownlist
            class="combobox"
            v-model="form.nguonkm_id"
            :dataSource="danhSachNguonKM"
            :fields="{ text: 'ten_nguon', value: 'nguonkm_id' }"
            @filtering="
              onFilteringDropDownList($event, danhSachNguonKM, 'ten_nguon')
            "
            :change="onChanged_NguonKM"
            :allowFiltering="true"
          >
          </ejs-dropdownlist>
        </div>
      </div>
    </div>
    <div class="row">
      <div class="col-sm-5 col-12">
        <div class="info-row">
          <div class="key w90">
            <div class="check-action fw6">
              <input
                type="radio"
                v-model="form.radio_ip"
                class="check"
                value="0"
              />
              <span class="name">Miễn IP</span>
            </div>
          </div>

          <div class="value">
            <ejs-tooltip
              class="tooltipcontainer"
              content="Số lượng IP tĩnh được miễn phí."
              target="#mien_ip"
            >
              <ejs-numerictextbox
                id="mien_ip"
                ref="MienIP"
                :enabled="this.controls.txtMienIP.enabled"
                format="N"
                :showSpinButton="false"
                v-model="form.mien_ip"
                :value="form.mien_ip"
                class="form-control tright pr-2"
              >
              </ejs-numerictextbox>
            </ejs-tooltip>
          </div>
        </div>
      </div>
      <div class="col-sm-7 col-12">
        <div class="info-row">
          <div class="key w84">Miễn Block</div>
          <div class="value">
            <ejs-numerictextbox
              id="mien_block"
              ref="BlockIP"
              format="N"
              :enabled="this.controls.txtMien_Block.enabled"
              :showSpinButton="false"
              v-model="form.mien_block"
              :value="form.mien_block"
              class="form-control tright pr-2"
            >
            </ejs-numerictextbox>
          </div>
          <div class="value padt7 w30 nowrap">
            <div class="check-action">
              <input
                type="radio"
                v-model="form.radio_ip"
                value="1"
                class="check"
              />
              <span class="name">Tỷ lệ giảm cước IP</span>
            </div>
          </div>
          <div class="value">
            <ejs-numerictextbox
              id="tyle_cuoc_ip"
              ref="TyleCuocIp"
              format="N"
              :enabled="this.controls.txtTyle_Cuoc_IP.enabled"
              :showSpinButton="false"
              v-model="form.tyle_cuoc_ip"
              :value="form.tyle_cuoc_ip"
              class="form-control tright pr-2"
            >
            </ejs-numerictextbox>
          </div>
        </div>
      </div>
    </div>
    <div class="row">
      <div class="col-sm-5 col-12">
        <div class="info-row">
          <div class="key w90">Tiền CK</div>
          <div class="value">
            <ejs-tooltip
              class="tooltipcontainer"
              content="Tiền cam kết hàng tháng khách hàng phải trả (Chưa VAT), nếu sử dụng < tiền cam kết khách hàng sẽ phải trả đủ = tiền CK, nếu sử dụng vượt tiền CK thì sẽ mất tiền như bình thường"
              target="#tien_ck"
            >
              <ejs-numerictextbox
                id="tien_ck"
                ref="TienCK"
                format="N"
                :showSpinButton="false"
                v-model="form.tien_ck"
                :value="form.tien_ck"
                class="form-control tright pr-2"
              >
              </ejs-numerictextbox>
            </ejs-tooltip>
          </div>
        </div>
      </div>
      <div class="col-sm-7 col-12">
        <div class="info-row">
          <div class="key w84">Kiểu cam kết</div>
          <div class="value">
            <div class="select-custom">
              <ejs-dropdownlist
                class="combobox"
                v-model="form.kieu_cam_ket"
                :dataSource="danhSachKieuCamKet"
                :fields="{ text: 'name', value: 'id' }"
                @filtering="
                  onFilteringDropDownList($event, danhSachKieuCamKet, 'name')
                "
                :allowFiltering="true"
              >
              </ejs-dropdownlist>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="row">
      <div class="col-sm-5 col-12">
        <div class="info-row">
          <div class="key w90">Tháng BĐCK</div>
          <div class="value">
            <ejs-tooltip
              class="tooltipcontainer"
              content="Tháng sẽ bắt đầu tính cước cam kết"
              target="#thang_bdck"
            >
              <ejs-numerictextbox
                id="thang_bdck"
                format="N"
                :disabled="form.kieu_cam_ket !== 0"
                :showSpinButton="false"
                v-model="form.thang_bdck"
                :value="form.thang_bdck"
                class="form-control tright pr-2"
              >
              </ejs-numerictextbox>
            </ejs-tooltip>
          </div>
        </div>
      </div>
      <div class="col-sm-7 col-12">
        <div class="info-row">
          <div class="key w84">Tháng KTCK</div>
          <div class="value">
            <ejs-tooltip
              class="tooltipcontainer"
              content="Tháng khách hàng kết thúc cam kết"
              target="#thang_ktck"
            >
              <ejs-numerictextbox
                id="thang_ktck"
                format="N"
                :showSpinButton="false"
                v-model="form.thang_ktck"
                :value="form.thang_ktck"
                class="form-control tright pr-2"
              >
              </ejs-numerictextbox>
            </ejs-tooltip>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mixin } from './mixins/index'

export default {
  mixins: [mixin],
  data: () => ({
    danhSachKieuCamKet: [
      { id: 0, name: "Bao gồm cả cước thuê bao" },
      { id: 1, name: "Không tính cước thuê bao" },
    ],
    danhSachKieubatdau: [
      { id: 1, name: "Sau tháng nghiệm thu /đặt cọc" },
      { id: 3, name: "Giao dịch tự nhập" },
    ],
    controls: {
      txtMienIP: {
        enabled: false,
      },
      txtMien_Block: {
        enabled: false,
      },
      txtTyle_Cuoc_IP: {
        enabled: false,
      },
    },
  }),
  props: ["form"],
  watch: {
    "form.kieu_cam_ket"(newValue, oldValue) {
      if (newValue !== 0) {
        this.form.thang_bdck = 0;
      }
    },
    "form.radio_ip"(newValue, oldValue) {
      this.controls.txtMienIP.enabled = newValue == 0;
      this.controls.txtMien_Block.enabled = newValue == 0;
      this.controls.txtTyle_Cuoc_IP.enabled = newValue == 1;
    },
  },
  computed: {
    danhSachNhomDatCoc() {
      return JSON.parse(
        JSON.stringify(
          this.$store.getters["promotionDetail/danhSachNhomDatCoc"]
        )
      );
    },
    danhSachNguonKM() {
      return JSON.parse(
        JSON.stringify(this.$store.getters["promotionDetail/danhSachNguonKM"])
      );
    },
    allowEditPrice() {
      return this.$store.getters["promotionDetail/allowEditPrice"]
    }
  },
  methods: {
    onChanged_NguonKM() {
      this.controls.txtMienIP.enabled = this.form.nguonkm_id > 0;
      this.controls.txtMien_Block.enabled = this.form.nguonkm_id > 0;
      this.controls.txtTyle_Cuoc_IP.enabled = this.form.nguonkm_id > 0;
    },
    tien_trudan_changed(e) {
      if (!e || !e.event) return;
      let newVal = e.value;
      this.form.tien_trudan = newVal;
      this.form.dat_coc =
        this.form.so_thang > 0
          ? this.form.tien_trudan * this.form.so_thang
          : this.form.tien_trudan;
    },
    so_thang_changed(e) {
      if (!e || !e.event) return;
      let newVal = e.value;
      this.form.so_thang = newVal;
      this.form.dat_coc =
        this.form.so_thang > 0
          ? this.form.tien_trudan * this.form.so_thang
          : this.form.tien_trudan;
    },
  },
};
</script>

<style>
</style>
