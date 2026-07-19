<template>
  <div class="modal-content popup-box">
    <div class="popup-header">
      <div class="title"><span class="icon one-notepad"></span>Biên tập</div>
      <div class="close -ap icon-close" data-dismiss="modal" @click="$bvModal.hide(modalId)"></div>
    </div>
    <form id="body-SuaGoiCuocPopup" class="popup-body" @submit.prevent="submitForm">
      <div class="legend-title">Sửa gói cước di động {{ packageId }}</div>
      <div class="form-row">
        <div class="col-6">
          <div class="info-row">
            <div class="key">Loại gói cước</div>
            <div class="value">
              <b-select class="mr-3" value-field="package_type_id" text-field="description"
                        disabled :options="dsLoaiGoiCuoc" v-model="packageDetails.package_type"></b-select>
            </div>
          </div>
        </div>
        <div class="col-6">
          <div class="info-row">
            <div class="key">Tên gói</div>
            <div class="value">
              <input type="text" readonly class="form-control" v-model="packageDetails.package_name" />
            </div>
          </div>
        </div>
      </div>
      <div class="form-row">
        <div class="col-6">
          <div class="info-row">
            <div class="key">Mã gói</div>
            <div class="value">
              <input type="text" readonly class="form-control" v-model="packageDetails.package_code" />
            </div>
          </div>
        </div>
        <div class="col-6">
          <div class="info-row">
            <div class="key">Service (dùng cho gói VAS)</div>
            <div class="value">
              <input type="text" readonly class="form-control" v-model="packageDetails.service_code" />
            </div>
          </div>
        </div>
      </div>
      <div class="form-row">
        <div class="col-6">
          <div class="info-row">
            <div class="key">Dispatch_ID (gói Trả sau)</div>
            <div class="value">
              <input type="text" readonly class="form-control" v-model="packageDetails.post_dispatch_id" />
            </div>
          </div>
        </div>
        <div class="col-6">
          <div class="info-row">
            <div class="key">Package_ID (gói Trả sau)</div>
            <div class="value">
              <input type="text" readonly class="form-control" v-model="packageDetails.post_package_id" />
            </div>
          </div>
        </div>
      </div>
      <div class="form-row">
        <div class="col-6">
          <div class="info-row">
            <div class="key">Giá</div>
            <div class="value">
              <input type="text" readonly class="form-control" v-model="packageDetails.price" />
            </div>
          </div>
        </div>
        <div class="col-6">
          <div class="info-row">
            <div class="key">Chu kỳ</div>
            <div class="value">
              <b-select class="mr-3" value-field="cycle_id" text-field="description"
                        disabled :options="dsChuKy" v-model="packageDetails.cycle"></b-select>
            </div>
          </div>
        </div>
      </div>
      <div class="form-row">
        <div class="col-6">
          <div class="info-row">
            <div class="key">Trạng thái</div>
            <div class="value">
              <b-select class="mr-3" disabled :options="dsTrangThai" v-model="packageDetails.status"></b-select>
            </div>
          </div>
        </div>
        <div class="col-6">
          <div class="info-row">
            <div class="key">Độ ưu tiên</div>
            <div class="value">
              <input type="text" readonly class="form-control" v-model="packageDetails.priority" />
            </div>
          </div>
        </div>
      </div>
      <div class="form-row">
        <div class="col-6">
          <div class="info-row">
            <div class="key">Thoại nội mạng</div>
            <div class="value">
              <input type="text" readonly class="form-control" v-model="packageDetails.internal_voice_minute" />
            </div>
          </div>
        </div>
        <div class="col-6">
          <div class="info-row">
            <div class="key">Thoại ngoại mạng</div>
            <div class="value">
              <input type="text" readonly class="form-control" v-model="packageDetails.external_voice_minute" />
            </div>
          </div>
        </div>
      </div>
      <div class="form-row">
        <div class="col-6">
          <div class="info-row">
            <div class="key">SMS nội mạng</div>
            <div class="value">
              <input type="text" readonly class="form-control" v-model="packageDetails.internal_sms_minute" />
            </div>
          </div>
        </div>
        <div class="col-6">
          <div class="info-row">
            <div class="key">SMS ngoại mạng</div>
            <div class="value">
              <input type="text" readonly class="form-control" v-model="packageDetails.external_sms_minute" />
            </div>
          </div>
        </div>
      </div>
      <div class="form-row">
        <div class="col-6">
          <div class="info-row">
            <div class="key">Data</div>
            <div class="value">
              <input type="text" readonly class="form-control" v-model="packageDetails.data_amount" />
            </div>
          </div>
        </div>
      </div>
      <div class="info-row">
        <div class="key">Mô tả ngắn</div>
        <div class="value">
          <textarea readonly class="form-control" v-model="packageDetails.short_desc" />
        </div>
      </div>
      <div class="info-row">
        <div class="key">Mô tả đầy đủ</div>
        <div class="value">
          <textarea readonly class="form-control" v-model="packageDetails.full_desc" />
        </div>
      </div>
      <div class="info-row">
        <div class="key">Mô tả ĐHXS</div>
        <div class="value">
          <textarea class="form-control" v-model="packageDetails.dhsx_desc" />
        </div>
      </div>
      <div class="text-center py-3">
        <b-button type="submit" variant="outline-primary">Sửa gói cước</b-button>
      </div>
    </form>
  </div>
</template>

<style>
#body-SuaGoiCuocPopup .info-row .key {
  width: 185px;
}

#body-SuaGoiCuocPopup .form-row > :nth-child(2) {
  padding-left: 16px;
}
</style>

<script>
import api from "../api";
import _ from "lodash";

export default {
  name: "SuaGoiCuocPopup",
  components: {},

  props: ["modalId", "packageId", "dsLoaiGoiCuoc"],

  mounted() {
    this.loadGoiCuoc();
  },

  data() {
    return {
      dsChuKy: [],
      dsTrangThai: [
        { text: 'Hiệu lực', value: 1 },
        { text: 'Không hiệu lực', value: 0 },
      ],

      /*
      {
        "full_desc": "Goi EZ50 cua MB tra truoc",
        "agent_name": null,
        "external_sms_minute": null,
        "data_amount": null,
        "post_dispatch_id": null,
        "date_to": null,
        "package_min": null,
        "package_type": 3,
        "priority": null,
        "cycle": 0,
        "internal_voice_minute": null,
        "package_code": "SPS_PRODUCT_EZ_EZ50",
        "mobile_package_id": 27049,
        "post_package_id": null,
        "price": "50000",
        "dhsx_desc": null,
        "external_voice_minute": null,
        "package_name": "EZ_EZ50",
        "service_code": "EZ_EZ50",
        "package_max": null,
        "short_desc": "Goi EZ50 cua MB tra truoc",
        "internal_sms_minute": null,
        "date_from": null,
        "status": 1
      }
       */
      packageDetails: {
        package_type: null,
        package_name: null,
        package_code: null,
        service_code: null,
        post_dispatch_id: null,
        post_package_id: null,
        price: null,
        cycle: null,
        status: null,
        priority: null,
        internal_voice_minute: null,
        external_voice_minute: null,
        internal_sms_minute: null,
        external_sms_minute: null,
        data_amount: null,
        short_desc: null,
        full_desc: null,
        dhsx_desc: null
      }
    };
  },

  methods: {
    async loadGoiCuoc() {
      try {
        this.loading(true);

        this.dsChuKy = (await api.dsChuKy(this.axios)).data.data;

        const ret = (await api.chiTietGoiCuoc(this.axios, this.packageId)).data.data[0];
        Object.assign(this.packageDetails, ret);

      } catch (e) {
        this.$toast("Có lỗi khi lấy chi tiết gói cước");

      } finally {
        this.loading(false);
      }
    },

    async submitForm() {
      try {
        this.loading(true);
        await api.fn_capnhat_goicuoc_dd(this.axios, {
          cycle: this.packageDetails.cycle,
          data_amount: this.packageDetails.data_amount,
          dhsx_desc: this.packageDetails.dhsx_desc,
          external_sms_minute: this.packageDetails.external_sms_minute,
          external_voice_minute: this.packageDetails.external_voice_minute,
          full_desc: this.packageDetails.full_desc,
          internal_sms_minute: this.packageDetails.internal_sms_minute,
          internal_voice_minute: this.packageDetails.internal_voice_minute,
          mobile_package_id: this.packageDetails.mobile_package_id,
          package_code: this.packageDetails.package_code,
          package_name: this.packageDetails.package_name,
          package_type: this.packageDetails.package_type,
          post_dispatch_id: this.packageDetails.post_dispatch_id,
          post_package_id: this.packageDetails.post_package_id,
          price: this.packageDetails.price,
          priority: this.packageDetails.priority,
          service_code: this.packageDetails.service_code,
          short_desc: this.packageDetails.short_desc,
          status: this.packageDetails.status,
        });
        this.$bvModal.hide(this.modalId);
        this.$toast(`Cập nhật gói cước ${this.packageId} thành công`);

      } catch (e) {
        this.$toast("Có lỗi xảy ra khi cập nhật gói cước");

      } finally {
        this.loading(false);
      }
    }
  },
};
</script>
