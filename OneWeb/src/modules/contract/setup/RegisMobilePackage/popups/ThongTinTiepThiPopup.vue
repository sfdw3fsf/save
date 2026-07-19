<template>
  <form id="body-ThongTinTiepThiPopup" class="popup-body" @submit.prevent="submitForm">
    <div class="form-row">
      <div class="col-6 info-row">
        <div class="key label-left">Số TB</div>
        <div class="value">
          <input type="text" readonly class="form-control" :value="soThueBao"/>
        </div>
      </div>
      <div class="col-6 info-row">
        <div class="key label-right">Tên thuê bao</div>
        <div class="value">
          <input type="text" readonly class="form-control" :value="tenThueBao"/>
        </div>
      </div>
    </div>
    <div class="form-row">
      <div class="col-6 info-row">
        <div class="key label-left">Loại thuê bao</div>
        <div class="value">
          <input type="text" readonly class="form-control" :value="loaiThueBao"/>
        </div>
      </div>
      <div class="col-6 info-row">
        <div class="key label-right">Địa chỉ</div>
        <div class="value">
          <input type="text" readonly class="form-control" :value="diaChi"/>
        </div>
      </div>
    </div>
    <div class="py-1">
      <label class="label-left">Dịch vụ đang dùng</label>
      {{ dichVuDangDungText }}
    </div>
    <div class="info-row">
      <div class="key label-left">TB trả trước được tham gia khuyến mại</div>
      <div class="value">
        <input type="text" readonly class="form-control" v-model="traTruocKhuyenMaiText"/>
      </div>
    </div>
    <div class="info-row">
      <div class="key label-left">Gói cước trả trước ưu tiên mới ĐK</div>
      <div class="value">
        <input type="text" readonly class="form-control" v-model="traTruocKhuyenMaiText"/>
      </div>
    </div>
    <div class="form-row">
      <div class="col-6 info-row">
        <div class="key label-left">Dịch vụ tư vấn</div>
        <div class="value">
<!--          <ejs-dropdownlist
            :allowFiltering="true" :filtering="filterDichVuTuVan"
            v-model="dichVuTuVanId" :dataSource="dichVuTuVanOptions"
            :fields="{ value: 'mobile_package_id', text: 'package_name' }"
          />-->
          <SelectExt :allowFiltering="true" :dataSource="dsGoiCuoc"
                     v-model="goiCuocId" @select="dichVuTuVanSelected"
                     dataTextField="package_name" dataValueField="mobile_package_id"></SelectExt>
        </div>
      </div>
      <div class="col-6 info-row">
        <div class="key label-right">Loại máy sử dụng</div>
        <div class="value">
          <input type="text" class="form-control" required v-model="loaiMaySuDung"/>
        </div>
      </div>
    </div>
    <div class="info-row">
      <div class="key label-left">Địa điểm sử dụng</div>
      <div class="value">
        <input type="text" class="form-control" required v-model="diaDiemSuDung"/>
      </div>
    </div>
    <div class="form-row">
      <div class="col-6 info-row">
        <div class="key label-left">Khách hàng hẹn gọi lại</div>
        <div class="value">
          <k-date-picker format="DD/MM/YYYY" v-model="ngayHenGoiLai" />
        </div>
      </div>
      <div class="col-6 info-row">
        <div class="key label-right">Email</div>
        <div class="value">
          <input type="email" class="form-control" :required="emailRequired" v-model="emailLienHe"/>
        </div>
      </div>
    </div>
    <div class="form-row">
      <div class="col-6 info-row">
        <div class="key label-left">Đánh giá tiếp thị</div>
        <div class="value">
          <b-form-select :options="danhGiaTiepThiOptions" required v-model="danhGiaTiepThiId"></b-form-select>
        </div>
      </div>
      <div class="col-6 info-row">
        <div class="key label-right">Độ hài lòng</div>
        <div class="value">
          <b-form-select :options="doHaiLongOptions" required v-model="doHaiLongId"></b-form-select>
        </div>
      </div>
    </div>
    <div class="info-row">
      <div class="key label-left">Ý kiến khách hàng</div>
      <div class="value">
        <input type="text" class="form-control" :required="yKienKhachHangRequired" v-model="yKienKhachHang"/>
      </div>
    </div>
    <div class="text-center py-3">
      <b-button type="submit" variant="outline-secondary" class="mr-3" @click="onClickLuuThongTinTiepThi">Lưu thông tin tiếp thị</b-button>
      <b-button type="submit" variant="outline-primary" :disabled="moDichVuDisabled" @click="onClickMoDichVu">Mở dịch vụ</b-button>
    </div>
  </form>
</template>

<style>
#body-ThongTinTiepThiPopup {
  font-size: 14px;
}
#body-ThongTinTiepThiPopup .label-left {
  width: 270px;
  text-align: right;
  padding-right: 16px;
}
#body-ThongTinTiepThiPopup .label-right {
  width: 150px;
  text-align: right;
  padding-right: 16px;
}
</style>

<script>
import api from "../api";
import KDatePicker from '@/components/kylq_components/KDatePicker.vue';

export default {
  name: "ThongTinTiepThiPopup",
  components: {
    KDatePicker
  },

  props: {
    modalId: { type: String },
    soThueBao: { type: String },
    tenThueBao: { type: String },
    loaiThueBao: { type: String },
    diaChi: { type: String },
    khuyenMaiTT: { type: String },
    goiTtUuTienDk: { type: String },
    didong_goi: { }
  },

  mounted() {
    this.loadOptions();
  },

  data() {
    return {
      traTruocKhuyenMaiText: "",
      traTruocUuTienMoiDkText: "",

      dsGoiCuoc: [],
      goiCuocId: null,
      packageName: "",

      loaiMaySuDung: "",
      diaDiemSuDung: "",
      ngayHenGoiLai: "",
      emailLienHe: "",

      danhGiaTiepThiOptions: [
        { value: 1, text: "Đồng ý TV nhưng không SDDV" },
        { value: 2, text: "Đồng ý tư vấn và mở qua PTTB" },
        { value: 3, text: "Đồng ý tư vấn và mở qua HT khác" },
        { value: 4, text: "Không đồng ý" },
        { value: 5, text: "Hẹn gọi lại" },
        { value: 6, text: "Gửi Email" },
      ],
      danhGiaTiepThiId: null,

      doHaiLongOptions: [
        { value: 1, text: "Hài lòng" },
        { value: 2, text: "Không hài lòng" },
        { value: 3, text: "Ý kiến" },
      ],
      doHaiLongId: null,

      yKienKhachHang: "",

      submitBtn: "",
    };
  },

  computed: {
    dichVuDangDungText() {
      return this.didong_goi.map(it => it.SERVICE_CODE).join(", ");
    },

    yKienKhachHangRequired() {
      // Chọn “Đồng ý tư vấn…” và “Không đồng ý” nhập trường “Ý kiến khách hàng”
      return [1, 2, 3, 4].includes(this.danhGiaTiepThiId);
    },

    ngayHenGoiLaiRequired() {
      // Chọn “Hẹn gọi lại” nhập trường “Khách hàng hẹn gọi lại”
      return this.danhGiaTiepThiId === 5;
    },

    emailRequired() {
      // Chọn “Gửi email” nhập trường “email”
      return this.danhGiaTiepThiId === 6;
    },

    moDichVuDisabled() {
      // Chỉ ấn button “Mở dịch vụ” khi chọn mục “Đánh giá tiếp thị” là “Đồng ý tư vấn và mở qua PTTB” và đã nhập mục “Ý kiến khách hàng”
      return !(this.danhGiaTiepThiId === 2 && this.yKienKhachHang.trim());
    }
  },

  methods: {
    async loadOptions() {
      this.dsGoiCuoc = (await api.dsGoiCuoc(this.axios, 0)).data.data;
    },

    dichVuTuVanSelected({id, text}){
      console.log("ThongTinTiepThiPopup dichVuTuVanSelected", {id, text});
      this.packageName = text;
    },

    trimTextInputs() {
      const fieldNames = ["loaiMaySuDung", "diaDiemSuDung", "emailLienHe", "yKienKhachHang"];
      for (const fieldName of fieldNames) {
        this[fieldName] = this[fieldName].trim();
      }
    },

    onClickLuuThongTinTiepThi() {
      console.log("ThongTinTiepThiPopup onClickDone");
      this.submitBtn = "LuuThongTinTiepThi";
      this.trimTextInputs();
    },

    onClickMoDichVu() {
      this.submitBtn = "MoDichVu";
      this.trimTextInputs();
    },

    async submitForm() {
      console.log("ThongTinTiepThiPopup submitForm");

      if (this.submitBtn === "LuuThongTinTiepThi") {
        await api.fn_themmoi_tt_tiepthi_dd(this.axios, {
          // cdate: "string",
          // cuser: "string",
          danh_gia: this.danhGiaTiepThiId.toString(),
          dia_diem: this.diaDiemSuDung,
          email: this.emailLienHe,
          hai_long: this.doHaiLongId.toString(),
          hen_goi_lai: this.ngayHenGoiLai,
          loai_may: this.loaiMaySuDung,
          mobile_package_id: this.goiCuocId,
          package_name: this.packageName.toString(),
          phanvung_id: +this.$root.token.getPhanVungID(),
          sothuebao: this.soThueBao,
          tt_tiepthi_id: 0,
          y_kien: this.yKienKhachHang
        });
        this.$toast("Lưu thông tin tiếp thị thành công");
      }
    }
  }
};
</script>
