<template>
  <div class="">
    <div class="list-action-top">
      <vue-bread-crumb :header="header" />
      <div class="list-actions-top">
        <ul class="list">
          <li>
            <a
              href="#"
              :class="{ disabled: disableBtnCreate }"
              @click.prevent="createNewState"
            >
              <span class="icon one-file-plus"></span>Nhập mới
            </a>
          </li>

          <li>
            <a
              href="#"
              :class="{ disabled: disableBtnSave }"
              @click.prevent="saveInfoState"
            >
              <span class="icon one-save"></span>Ghi lại
            </a>
          </li>
          <li>
            <a
              href="#"
              :class="{ disabled: disableBtnCancel }"
              @click.prevent="cancelAction"
            >
              <span class="icon nc-icon-glyph ui-1_circle-remove"></span>Hủy
            </a>
          </li>
          <li>
            <a
              href="#"
              :class="{ disabled: disableBtnDelete }"
              @click.prevent="deleteRecordState"
            >
              <span class="icon one-trash"></span>Xóa
            </a>
          </li>
        </ul>
      </div>
    </div>
    <div class="page-content">
      <div class="information-state">
        <div class="box-form">
          <div class="row">
            <div class="legend-title">Cập nhật thông tin quận huyện</div>
            <div class="row">
              <div class="col-sm-8">
                <div class="row">
                  <div class="col-sm-6">
                    <div class="info-row">
                      <div class="value">
                        <vue-input
                          v-model="maQuanHuyen"
                          ref="refMaQuanHuyen"
                          :validate="['required']"
                          :labelWidth="'130'"
                          label="Mã quận/huyện"
                          :required="true"
                        ></vue-input>
                      </div>
                    </div>
                  </div>
                  <div class="col-sm-6">
                    <div class="info-row">
                      <div class="value">
                        <vue-input
                          v-model="quanIDNeo"
                          :highlight="true"
                          :labelWidth="'130'"
                          label="Quận ID Neo"
                        ></vue-input>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="row">
                  <div class="info-row">
                    <div class="value">
                      <vue-input
                        v-model="tenQuanHuyen"
                        :highlight="true"
                        ref="refTenQuanHuyen"
                        :validate="['required']"
                        :labelWidth="'130'"
                        label="Tên quận/huyện"
                        :required="true"
                      ></vue-input>
                    </div>
                  </div>
                </div>
              </div>
              <div class="col-sm-4">
                <div class="info-row">
                  <div class="value">
                    <div class="select-custom">
                      <vue-select
                        v-model="tinhTp"
                        ref="refTinhTP"
                        :validate="['required']"
                        :labelWidth="'80'"
                        :options="cityList"
                        labelField="TENTINH"
                        valueField="TINH_ID"
                        label="Tỉnh/Tp"
                        :required="true"
                      ></vue-select>
                    </div>
                  </div>
                </div>
                <div class="info-row">
                  <div class="value">
                    <vue-input
                      v-model="tienTo"
                      ref="refTienTo"
                      :labelWidth="'80'"
                      label="Tiền tố"
                    ></vue-input>
                  </div>
                </div>
              </div>
            </div>
            <div class="row">
              <div class="info-row">
                <div class="value">
                  <vue-input
                    v-model="ghiChu"
                    ref="refGhiChu"
                    :labelWidth="'130'"
                    labelField="tendslam"
                    valueField="dslam_id"
                    label="Ghi chú"
                  ></vue-input>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="list-state">
        <vue-card label="Danh sách quận">
          <vue-table
            :config="config"
            :options="districts"
            v-model="currentRecord"
            :unique="'index'"
            :loading="isLoading"
          />
        </vue-card>
      </div>
    </div>
  </div>
</template>
<script>
import { mapActions, mapState, mapGetters } from "vuex";
import {
  STATUS_CREATE,
  TYPE_CREATE,
  STATUS_EXIST_MA_QUAN_HUYEN,
  STATUS_EXIST_TEN_QUAN_HUYEN,
  ERROR_EXIST_MA_QUAN_HUYEN,
  ERROR_EXIST_TEN_QUAN_HUYEN,
  ERROR_VALIDATE_MA_QUAN_HUYEN,
  ERROR_VALIDATE_TEN_QUAN_HUYEN,
  ERROR_VALIDATE_GHI_CHU,
  ERROR_VALIDATE_TIEN_TO,
  ERROR_VALIDATE_QUAN_ID_NEO,
  ERROR_VALIDATE_TYPE_QUAN_ID_NEO,
  ERROR_EXIST_TEN_VA_MA_QUAN_HUYEN
} from "./constants";

export default {
  name: "State",
  components: {},

  data() {
    return {
      // data header action
      header: {
        title: "QUẢN LÝ QUẬN/HUYỆN",
        list: [
          { name: "Lập hợp đồng", link: { name: "Ui.cards" } },
          {
            name: "Lắp đặt mới",
            link: { name: "Ui.buttons" }
          }
        ]
      },

      currentRecord: null,
      isCreate: false,
      disableQuanIDUpdate: true,

      //info
      maQuanHuyen: 0,
      tenQuanHuyen: "",
      quanID: 0,
      ghiChu: "",
      quanIDNeo: 0,
      tinhTp: 0,
      tienTo: "",

      //disable button
      disableBtnCreate: false,
      disableBtnSave: false,
      disableBtnCancel: false,
      disableBtnDelete: false,

      config: [
        {
          label: "STT",
          filterAble: true,
          key: "stt"
        },
        {
          label: "Mã quận huyện",
          filterAble: true,
          key: "MA_QUAN"
        },
        {
          label: "Quận Neo",
          filterAble: true,
          key: "QUAN_ID_NEO"
        },
        {
          label: "Tên quận/huyện",
          filterAble: true,
          key: "TEN_QUAN"
        }
      ]
    };
  },

  async created() {
    await this.initializeCityList();
    await this.fetchDistrictList();
    this.currentRecord = this.districts ? this.districts[0] : null;
  },
  computed: {
    ...mapState("mapStateLocation", [""]),
    ...mapGetters("mapStateLocation", [
      "cityList",
      "districtList",
      "statusCheckExistState",
      "isLoading",
      "errorExist"
    ]),
    districts() {
      const data = this.districtList.map((item, index) => {
        const list = {
          stt: index + 1,
          MA_QUAN: item.MA_QUAN,
          QUAN_ID_NEO: item.QUAN_ID_NEO,
          TEN_QUAN: item.TEN_QUAN,
          GHICHU: item.GHICHU,
          TINH_ID: item.TINH_ID,
          TIENTO: item.TIENTO,
          QUAN_ID: item.QUAN_ID
        };
        return list;
      });
      return data;
    }
  },

  methods: {
    ...mapActions("mapStateLocation", [
      "initializeCityList",
      "fetchDistrictList",
      "checkState",
      "createState",
      "updateState",
      "deleteState"
    ]),

    resetInformationState() {
      this.maQuanHuyen = "";
      this.tenQuanHuyen = "";
      this.ghiChu = "";
      this.quanIDNeo = "";
      this.tinhTp = "";
      this.tienTo = "";
      this.quanID = "";
    },

    mapDataCurrentRecor(data) {
      console.log('mapDataCurrentRecor', data)
      this.maQuanHuyen = data.MA_QUAN;
      this.tenQuanHuyen = data.TEN_QUAN;
      this.ghiChu = data.GHICHU;
      this.quanIDNeo = data.QUAN_ID_NEO;
      this.tinhTp = data.TINH_ID;
      this.tienTo = data.TIENTO;
      this.quanID = data.QUAN_ID;
    },

    warningInput() {
      this.$refs.refMaQuanHuyen.checkValidate();
      this.$refs.refTenQuanHuyen.checkValidate();
      this.$refs.refTinhTP.checkValidate();
    },

    isValidForm() {
      this.warningInput();

      if (!this.maQuanHuyen) {
        return false;
      }

      if (!this.tenQuanHuyen) {
        return false;
      }

      if (!this.tinhTp) {
        return false;
      }

      return true;
    },

    async checkStateExist() {
      const variables = {
        p_phanvung_id: this.tinhTp,
        p_quan_id: this.quanID,
        p_kieu: TYPE_CREATE,
        p_ma_quan: this.maQuanHuyen,
        p_ten_quan: this.tenQuanHuyen,
        p_quan_id: this.quanID
      };
      await this.checkState(variables);
    },

    createNewState() {
      this.isCreate = true;
      this.disableBtnCreate = true;
      this.disableBtnDelete = true;
      this.disableQuanIDUpdate = false;
      this.resetInformationState();
    },

    validationInformation(
      maQuanHuyen,
      tenQuanHuyen,
      ghiChu,
      quanIDNeo,
      tienTo
    ) {
      if (maQuanHuyen && maQuanHuyen.trim().length > 20) {
        this.$toast.error(`${ERROR_VALIDATE_MA_QUAN_HUYEN}`);
        return false;
      }

      if (tenQuanHuyen && tenQuanHuyen.trim().length > 200) {
        this.$toast.error(`${ERROR_VALIDATE_TEN_QUAN_HUYEN}`);
        return false;
      }

      if (
        !Number(quanIDNeo) &&
        quanIDNeo !== 0 &&
        quanIDNeo !== null &&
        quanIDNeo !== ""
      ) {
        this.$toast.error(`${ERROR_VALIDATE_TYPE_QUAN_ID_NEO}`);
        return false;
      }

      if (quanIDNeo && String(quanIDNeo).trim().length > 10) {
        this.$toast.error(`${ERROR_VALIDATE_QUAN_ID_NEO}`);
        return false;
      }

      if (tienTo && tienTo.trim().length > 6) {
        this.$toast.error(`${ERROR_VALIDATE_TIEN_TO}`);
        return false;
      }

      if (ghiChu && ghiChu.trim().length > 200) {
        this.$toast.error(`${ERROR_VALIDATE_GHI_CHU}`);
        return false;
      }

      return true;
    },

    async saveInfoState() {
      if (!this.isValidForm()) {
        return;
      }

      if (
        !this.validationInformation(
          this.maQuanHuyen,
          this.tenQuanHuyen,
          this.ghiChu,
          this.quanIDNeo,
          this.tienTo
        )
      ) {
        return;
      }

      this.$root.showLoading(true);

      if (this.isCreate) {
        try {
          const variables = {
            QUAN_ID: Number(this.quanID),
            MA_QUAN: this.maQuanHuyen.trim(),
            TEN_QUAN: this.tenQuanHuyen.trim(),
            GHICHU: this.ghiChu ? this.ghiChu.trim() : null,
            QUAN_ID_NEO: Number(this.quanIDNeo),
            TIENTO: this.tienTo ? this.tienTo.trim() : null,
            TINH_ID: Number(this.tinhTp)
          };

          await this.createState(variables);
          this.$toast.success("Thêm mới dữ liệu thành công!");
          await this.fetchDistrictList();

          this.currentRecord = this.districts ? this.districts[0] : null;
          this.disableBtnCreate = false;
          this.disableBtnDelete = false;
          this.isCreate = false;
          this.disableQuanIDUpdate = true;
        } catch (error) {
          if (
            this.errorExist.includes(STATUS_EXIST_MA_QUAN_HUYEN) &&
            this.errorExist.includes(STATUS_EXIST_TEN_QUAN_HUYEN)
          ) {
            this.$toast.error(`${ERROR_EXIST_TEN_VA_MA_QUAN_HUYEN}`);
            this.$root.showLoading(false);
            return;
          }
          if (this.errorExist.includes(STATUS_EXIST_MA_QUAN_HUYEN)) {
            this.$toast.error(`${ERROR_EXIST_MA_QUAN_HUYEN}`);
            this.$root.showLoading(false);
            return;
          }
          if (this.errorExist.includes(STATUS_EXIST_TEN_QUAN_HUYEN)) {
            this.$toast.error(`${ERROR_EXIST_TEN_QUAN_HUYEN}`);
            this.$root.showLoading(false);
            return;
          }
        }
      } else {
        try {
          const variables = {
            QUAN_ID: Number(this.quanID),
            MA_QUAN: this.maQuanHuyen.trim(),
            TEN_QUAN: this.tenQuanHuyen.trim(),
            GHICHU: this.ghiChu ? this.ghiChu.trim() : "",
            QUAN_ID_NEO: Number(this.quanIDNeo),
            TIENTO: this.tienTo ? this.tienTo.trim() : "",
            TINH_ID: Number(this.tinhTp)
          };
          await this.updateState(variables);
          this.$toast.success("Cập nhật dữ liệu thành công!");
          await this.fetchDistrictList();
          this.currentRecord = this.districts.find(
            item => item.QUAN_ID === this.quanID
          );
        } catch (error) {
          if (
            this.errorExist.includes(STATUS_EXIST_MA_QUAN_HUYEN) &&
            this.errorExist.includes(STATUS_EXIST_TEN_QUAN_HUYEN)
          ) {
            this.$toast.error(`${ERROR_EXIST_TEN_VA_MA_QUAN_HUYEN}`);
            this.$root.showLoading(false);
            return;
          }
          if (this.errorExist.includes(STATUS_EXIST_MA_QUAN_HUYEN)) {
            this.$toast.error(`${ERROR_EXIST_MA_QUAN_HUYEN}`);
            this.$root.showLoading(false);
            return;
          }
          if (this.errorExist.includes(STATUS_EXIST_TEN_QUAN_HUYEN)) {
            this.$toast.error(`${ERROR_EXIST_TEN_QUAN_HUYEN}`);
            this.$root.showLoading(false);
            return;
          }
        }
      }

      this.$root.showLoading(false);
    },

    cancelAction() {
      this.disableBtnCreate = false;
      this.disableBtnDelete = false;
      this.isCreate = false;
      this.mapDataCurrentRecor(this.currentRecord);
    },

    async deleteRecordState() {
      const isConfirm = await this.$bvModal.msgBoxConfirm(
        `Bạn có thực sự muốn xóa dữ liệu không?`,
        {
          title: "",
          size: "sm",
          okTitle: "Đồng ý",
          cancelTitle: "Hủy"
        }
      );

      if (isConfirm) {
        this.$root.showLoading(true);

        try {
          await this.deleteState({ p_quan_id: this.quanID });
          this.$toast.success("Xóa dữ liệu thành công!");
          await this.fetchDistrictList();
          this.currentRecord = this.districts ? this.districts[0] : null;
        } catch (error) {
          this.$toast.error("Xóa dữ liệu lỗi, dữ liệu đã có liên kết!");
        }
      }

      this.$root.showLoading(false);
    }
  },

  watch: {
    currentRecord(val) {
      this.mapDataCurrentRecor(val);
    }
  }
};
</script>
