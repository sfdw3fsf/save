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
              @click.prevent="createNewBras"
            >
              <span class="icon one-file-plus"></span>Nhập mới
            </a>
          </li>
          <li>
            <a
              href="#"
              :class="{ disabled: disableBtnSave }"
              @click.prevent="saveActionBras"
            >
              <span class="icon one-save"></span>Ghi lại
            </a>
          </li>
          <li>
            <a
              href="#"
              :class="{ disabled: disableBtnDelete }"
              @click.prevent="deleteItemBras"
            >
              <span class="icon one-trash"></span>Xóa
            </a>
          </li>
          <li>
            <a
              href="#"
              :class="{ disabled: disableBtnCompleted }"
              @click.prevent="completedBras"
            >
              <span class="icon one-file-check"></span>Hoàn thiện nhiều
            </a>
          </li>
          <li>
            <a
              href="#"
              :class="{ disabled: disableBtnExportExcel }"
              @click.prevent="exportExcel"
            >
              <span class="icon one-excel"></span> Xuất Excel
            </a>
          </li>
        </ul>
      </div>
    </div>
    <div class="page-content">
      <div class="information-dslam">
        <div class="box-form">
          <div class="row">
            <div class="col-sm-6 col-12">
              <div class="legend-title">Thông tin Dslam</div>
              <div class="info-row">
                <div class="value">
                  <div class="select-custom">
                    <vue-select
                      ref="refInformationBrasList"
                      :validate="['required']"
                      v-model="selectedInformationBras"
                      :labelWidth="'80'"
                      :options="informationBrasList"
                      labelField="tenbras"
                      valueField="bras_id"
                      label="Bras"
                      :required="true"
                    ></vue-select>
                  </div>
                </div>
              </div>
              <div class="info-row">
                <div class="value">
                  <div class="select-custom">
                    <!-- <SelectExt
                      ref="refInformationDslamList"
                      :dataSource="informationDslamList"
                      v-model="selectedInformationDslam"
                      dataTextField="tendslam"
                      dataValueField="dslam_id"
                      @change="handleSelectDslamVdc"
                      :required="true"
                      label="Dslam"
                      :labelWidth="80">
                      <label>Dslam</label>
                      </SelectExt> -->
                    <vue-select
                      ref="refInformationDslamList"
                      :validate="['required']"
                      v-model="selectedInformationDslam"
                      :labelWidth="'80'"
                      :options="informationDslamList"
                      labelField="tendslam"
                      valueField="dslam_id"
                      label="Dslam"
                      :required="true"
                    ></vue-select>
                  </div>
                </div>
              </div>
              <div class="info-row">
                <div class="value">
                  <vue-select
                    ref="refInformationDslamListVDC"
                    :validate="['required']"
                    v-model="selectedInformationDslamVDC"
                    :labelWidth="'80'"
                    :options="informationDslamListVDC"
                    labelField="tendslam"
                    valueField="dslam_id"
                    label="Dslam vdc"
                    :required="true"
                  ></vue-select>
                </div>
              </div>
            </div>
            <div class="col-sm-6 col-12">
              <div class="legend-title">Dslam VDC mới</div>
              <div class="info-row">
                <div class="value">
                  <div class="select-custom">
                    <vue-select
                      ref="refNewInformationBras"
                      :validate="['required']"
                      v-model="selectedNewInformationBras"
                      :labelWidth="'80'"
                      :options="informationNewBrasList"
                      labelField="tenbras"
                      valueField="bras_id"
                      label="Bras"
                      :required="true"
                    ></vue-select>
                  </div>
                </div>
              </div>
              <div class="info-row">
                <div class="value">
                  <div class="select-custom">
                    <vue-select
                      ref="refNewInformationDslamListVDC"
                      :validate="['required']"
                      v-model="selectedNewInformationDslamVDC"
                      :labelWidth="'80'"
                      :options="informationNewDslamListVDC"
                      id=""
                      labelField="tendslam"
                      valueField="dslam_id"
                      label="Dslam vdc"
                      :required="true"
                    ></vue-select>
                  </div>
                </div>
              </div>
              <div class="info-row">
                <div class="key w70"></div>
                <div class="value padt7">
                  <vue-checkbox
                    v-model="checkBoxCompletedList"
                    label="Danh sách đã hoàn thiện"
                  >
                  </vue-checkbox>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="list-subscriber-list">
        <danh-sach-thue-bao @currentRecord="currentRecord" />
      </div>
    </div>
  </div>
</template>
<script>
import { mapActions, mapState, mapGetters } from "vuex";
import { exportExcel } from "./helper/export-excel";
import {
  TRANG_THAI_DEFAULT,
  TRANG_THAI_COMPLETED,
  IS_CREATE_BRAS,
  IS_UPDATE_BRAS
} from "./constants";
import api from './api'

export default {
  name: "DSLAMTransfer",
  components: {
    DanhSachThueBao: () => import("./part/DanhSachThueBao.vue")
  },

  data() {
    return {
      // data header action
      header: {
        title: "CHUYỂN DSLAM SANG BRAS MỚI",
        list: [
          { name: "Lập hợp đồng", link: { name: "Ui.cards" } },
          {
            name: "Lắp đặt mới",
            link: { name: "Ui.buttons" }
          }
        ]
      },
      disableBtnCreate: false,
      disableBtnSave: false,
      disableBtnExportExcel: false,
      disableBtnDelete: false,
      disableBtnCompleted: false,

      // information Dslam
      selectedInformationBras: "",
      selectedInformationDslam: "",
      selectedInformationDslamVDC: "",

      // information new Dslam
      checkBoxCompletedList: false,
      selectedNewInformationBras: "",
      selectedNewInformationDslamVDC: "",


      // check create or update
      isCreate: false,
      isCompleted: false,
      currentData : null,
      selectedDatas: null,
    };
  },

  async created() {
    await this.initializeInformationBrasList();
    await this.initializeInformationNewBrasList();

     if(this.informationBrasList.length > 0)
       this.selectedInformationBras = this.informationBrasList[0]['bras_id'];
    if(this.informationNewBrasList.length > 0)
      this.selectedNewInformationBras = this.informationNewBrasList[0]['bras_id']

    await this.fetchDanhSachThueBao( this.status);
  },
  computed: {
    ...mapState("mapDslamTransfer", []),
    ...mapGetters("mapDslamTransfer", [
      "informationBrasList",
      "informationDslamList",
      "informationDslamListVDC",
      "informationNewBrasList",
      "informationNewDslamListVDC",
      "danhSachThueBao"
    ]),
    status() {
      return this.checkBoxCompletedList ? 1 : 0;
    }
  },

  methods: {
    ...mapActions("mapDslamTransfer", [
      "initializeInformationBrasList",
      "initializeInformationDslamList",
      "initializeInformationDslamListVDC",
      "initializeInformationNewBrasList",
      "initializeInformationNewDslamListVDC",
      "fetchDanhSachThueBao",
      "saveBras",
      "deleteBras"
    ]),

    async fetchInformationDslamList() {
      try {
        await this.initializeInformationDslamList(this.selectedInformationBras);
      } catch (error) {
        throw error;
      }
    },

    async fetchInformationDslamListVDC() {
      try {
        await this.initializeInformationDslamListVDC(
          this.selectedInformationBras
        );
      } catch (error) {
        throw error;
      }
    },

    async fetchInformationNewDslamListVDC() {
      try {
        await this.initializeInformationNewDslamListVDC(
          this.selectedNewInformationBras
        );
      } catch (error) {
        throw error;
      }
    },

    createNewBras() {
      this.isCreate = true;
      this.disableBtnCreate = true;
      this.disableBtnDelete = true;
      this.disableBtnCompleted = true;
      this.disableBtnExportExcel = true;
      // this.resetValueForm();
    },

    async saveActionBras() {
      if (!this.isValidForm()) {
        return;
      }
      this.$root.showLoading(true);
      // const statusCompleted = this.isCompleted
      //   ? TRANG_THAI_COMPLETED
      //   : TRANG_THAI_DEFAULT;
      const params = {
        is_insert: this.isCreate ? IS_CREATE_BRAS : IS_UPDATE_BRAS,
        bras_id: this.selectedInformationBras,
        dslam_id: this.selectedInformationDslam,
        dslam_cha_id: this.selectedInformationDslamVDC,

        bras_moi_id: this.selectedNewInformationBras,
        dslam_moi_id: this.selectedNewInformationDslamVDC,
        trangthai: 0
      };

      try {
       const response =  await this.saveBras(params);
       if(response && response.data && response.data.error_code === 'BSS-00000000'){

        this.$toast.success("Cập nhật dữ liệu thành công!");

        // this.resetValueForm();

        await this.fetchDanhSachThueBao( this.status);

        this.isCreate = false;
        this.isCompleted = false;
        this.disableBtnCreate = false;
        this.disableBtnDelete = false;
        this.disableBtnCompleted = false;
        this.disableBtnExportExcel = false;
       }else{
        this.$toast.error("Cập nhật dữ liệu thất bại!");
       }


      } catch (error) {
        this.$toast.error("Cập nhật dữ liệu thất bại!");
      }
      this.$root.showLoading(false);
    },

    //Hoan thien button click
    async completedBras() {
      if(this.selectedDatas) {
        for (const data of this.selectedDatas)
        {
          const params = {
            "p_dslam_id": data.dslam_id,
            "p_dslam_moi_id": this.selectedNewInformationDslamVDC,
            "p_bras_moi_id": this.selectedNewInformationBras
          }

          const response = await api.completedDslam(this.axios, params);
          if(response && response.data && response.data.error_code === 'BSS-00000000'){
            this.isCompleted = true;
            this.$toast.success("Hoàn thiện dữ liệu thành công!");
          }
          else this.$toast.error("Hoàn thiện dữ liệu thất bại!");
        }
        this.fetchDanhSachThueBao(this.status);
      }

      // const params = {
      //     "p_dslam_id": this.selectedInformationDslam,
      //     "p_dslam_moi_id": this.selectedNewInformationDslamVDC,
      //     "p_bras_moi_id": this.selectedNewInformationBras
      // }
      //
      // const response = await api.completedDslam(this.axios, params);
      // if(response && response.data && response.data.error_code === 'BSS-00000000'){
      //   this.isCompleted = true;
      //   this.fetchDanhSachThueBao(this.status);
      //   return  this.$toast.success("Hoàn thiện dữ liệu thành công!");
      // }
      // return  this.$toast.error("Hoàn thiện dữ liệu thất bại!");
    },

    //xoá những thuê bao chưa được kích hoạt
    async deleteItemBras() {
      const countDataSelected = this.selectedDatas.length;
      const txtComfirm = "Bạn thật sự muốn " + countDataSelected +" xoá dữ liệu không?";
      if (!confirm(txtComfirm)) return;
      try {
        const statusCompleted = this.currentData?this.currentData.trangthai: 0
        const params = [];

        if(statusCompleted === 0) {
          this.$root.showLoading(true);
          for (const data of this.selectedDatas) {
            console.log(data.dslam_id)
            params.push({ 'DSLAM_ID': data.dslam_id })
            // await this.deleteBras();
            // await this.fetchDanhSachThueBao(this.status);
            //
          }
          console.log(params);
          await this.deleteBras(params);
          await this.fetchDanhSachThueBao(this.status);
          this.$root.showLoading(false);
          this.$toast.success("Xóa dữ liệu thành công!");
        } else  {
          this.$toast.error("Thuê bao đã được kích hoat. Xóa dữ liệu lỗi!");
        }

        // if (statusCompleted === 0) {
        //   this.$root.showLoading(true);
        //   await this.deleteBras(this.selectedInformationDslam);
        //   this.$toast.success("Xóa dữ liệu thành công!");
        //   await this.fetchDanhSachThueBao(this.status);
        //   this.$root.showLoading(false);
        // } else {
        //   this.$toast.error("Thuê bao đã được kích hoat. Xóa dữ liệu lỗi!");
        // }
      } catch (error) {
        this.$toast.error("Xóa dữ liệu lỗi!");
      }
    },

    exportExcel() {
      if(!this.danhSachThueBao.length ){
        return  this.$toast.error("Danh Sách Thuê Bao Trống!");
      }

      exportExcel(this.danhSachThueBao, "DanhSachThueBao");
    },

    currentRecord(data) {
      if (data) {
        console.log("data")
        console.log(data);
        console.log("selected")
        console.log(this.selectedInformationDslam)
        // if(data.length > 1) {
        //   this.disableBtnSave = true;
        // }
        // else {
        //   this.disableBtnSave = false;
        // }
        this.selectedInformationBras = data[0].bras_id;
        this.selectedInformationDslam = data[0].dslam_id;
        this.selectedInformationDslamVDC = data[0].dslam_cha_id;
        console.log(this.selectedInformationDslamVDC)
        this.selectedNewInformationBras = data[0].bras_moi_id;
        this.selectedNewInformationDslamVDC = data[0].dslam_moi_id;
        this.selectedDatas = data;
        this.currentData = data[0]; //update;
        return;
      }

    },

    warningInput() {
      this.$refs.refInformationBrasList.checkValidate();
      this.$refs.refInformationDslamList.checkValidate();
      this.$refs.refInformationDslamListVDC.checkValidate();
      this.$refs.refNewInformationBras.checkValidate();
      this.$refs.refNewInformationDslamListVDC.checkValidate();
    },

    isValidForm() {
      this.warningInput();

      if (!this.selectedInformationBras) {
        return false;
      }

      if (!this.selectedInformationDslam) {
        return false;
      }

      if (!this.selectedInformationDslamVDC) {
        return false;
      }

      if (!this.selectedNewInformationBras) {
        return false;
      }

      if (!this.selectedNewInformationDslamVDC) {
        return false;
      }

      return true;
    },

    resetValueForm() {
      this.selectedInformationDslam = "";
      this.selectedInformationBras = "";
      this.selectedInformationDslamVDC = "";
      this.selectedNewInformationBras = "";
      this.selectedNewInformationDslamVDC = "";
      this.currentData = null;
    },
  },

  watch: {
    async selectedInformationBras(val) {
      if (val) {
        await this.fetchInformationDslamList();
        await this.fetchInformationDslamListVDC();
        if(this.informationDslamList.length > 0){
          this.selectedInformationDslam = this.informationDslamList[0]['dslam_id'];
        }
        if(this.informationDslamListVDC.length > 0){
          // this.selectedInformationDslamVDC = this.informationDslamListVDC[0]['dslam_vdc_id'];//check
          this.selectedInformationDslamVDC = this.informationDslamListVDC[0]['dslam_id'];//todo
        }

        //neu dang nhap moi thi dung input select
        if(!this.isCreate) {
          console.log('selectedInformationBras')
          console.log(this.currentData);
          this.selectedInformationDslamVDC = this.currentData.dslam_cha_id
          this.selectedInformationDslam = this.currentData.dslam_id;
          console.log("data is")
          console.log(this.selectedInformationDslamVDC)
          console.log(this.selectedInformationDslam)
        }
      }
    },

    async selectedNewInformationBras(val) {
      if (val) {
        await this.fetchInformationNewDslamListVDC();
        if(this.informationNewDslamListVDC.length > 0){
            // this.selectedNewInformationDslamVDC = this.informationNewDslamListVDC[0]['dslam_vdc_id']   //check
            this.selectedNewInformationDslamVDC = this.informationNewDslamListVDC[0]['dslam_id'] //todo

        }

      }
    },

    danhSachThueBao(val){
      if(val.length === 0 ){
        this.createNewBras();

        this.resetValueForm();
      }else{
        this.currentData = val[0];
      }
    },
    checkBoxCompletedList(val) {
      this.fetchDanhSachThueBao(this.status);
    },

  }
};
</script>
