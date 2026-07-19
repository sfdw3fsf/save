<template>
<div>
  <breadcrumb :header="header" v-if="!dulieu || dulieu.isPopup == false" />
  <vue-nav>
    <div class="list-actions-top">
      <ul class="list">
        <li>
          <a href="javascript: void(0)" title="Hiển thị danh sách" @click="btnTimKiem_click">
            <span class="icon one-search"></span>Tìm kiếm
          </a>
        </li>     
        <li>
          <a href="javascript: void(0)" @click.prevent="btnChapNhan_Click">
              <span class="icon nc-icon-glyph ui-1_check-circle-08"></span> Chấp nhận
          </a>
        </li>  
      </ul>
    </div>
  </vue-nav>
  <div class="page-content">
    <div class="row">      
      <div class="box-form">
        <div class="legend-title">Thông tin tìm kiếm</div>
        <div class="row">
          <div class="col-sm-4 col-12">
            <div class="info-row">
              <div class="key w110">Từ ngày</div>
              <div class="value">
                <DatePicker :format="dateFormat" :value-type="'format'" v-model="dtp_TuNgay.value"
                  :input-attr="{required: 'true', title: dtp_TuNgay.value}" @change="onChangeTuNgay">
                </DatePicker>
              </div>
            </div>
          </div>
          <div class="col-sm-4 col-12">
            <div class="info-row">
              <div class="key w110">Đến ngày</div>
              <div class="value">
                <DatePicker :format="dateFormat" :value-type="'format'" v-model="dtp_DenNgay.value"
                  :input-attr="{required: 'true', title: dtp_DenNgay.value}" @change="onChangeDenNgay">
                </DatePicker>
              </div>
            </div>
          </div>
          <div class="col-sm-4 col-12">
            <div class="info-row">
              <div class="key w110">Lọc theo <span class="red">(*)</span></div>
              <div class="value">
                <select2 :z-index="2000" class="select2" v-model="cbb_DieuKien.value"
                  :options="cbb_DieuKien.Items">
                </select2>
              </div>
            </div>
          </div>
        </div>        
      </div>
    </div>
    <div class="row">
      <div class="box-form">
        <div class="legend-title">Danh sách cơ chế</div>
        <DataGrid v-bind:columns="grid_CoChe.header"
          v-bind:dataSource="grid_CoChe.list" :showFilter="true" :allowPaging="true" ref="grid_CoChe" :showColumnCheckbox="true"
          :enablePagingServer="false" :allowExcelExport="true" :enabledSelectFirstRow="false" @selectedItemsChanged="onSelectedItems" panelDataHeight="300px" >
        </DataGrid>
      </div>
    </div>
  </div>

  <b-modal id="popupComponents" size="huge" :title="popupComponentHeader" :hide-header="popupComponentHeader?false:true"
    hide-footer body-class="modal-body p-0">
    <component :is="popupComponent" ref="generalPopup" :dulieu="popupComponentData" v-bind="popupComponentAttr"
      isPopup="true" v-on="popupComponentEvts"></component>
  </b-modal>
</div>
</template>

<script>
import {
  required,
  maxLength,
  minLength,
  numeric
} from "vuelidate/lib/validators"
import BssErrorMarker from '@/components/BssErrorMarker'
import BssRequiredMarker from '@/components/BssRequiredMarker'
import moment from 'moment'
import DatePicker from 'vue2-datepicker'
import 'vue2-datepicker/locale/vi.js'
import 'vue2-datepicker/index.css'
import breadcrumb from '@/components/breadcrumb'
import api from "../API.js";

export default {
  name: 'popup_TTHopDong',
  components: {
    breadcrumb,
    DatePicker,
    BssErrorMarker,
    BssRequiredMarker,
  },
  props: ['dulieu'],
  data() {
    return {
      dateFormat: 'DD/MM/YYYY',
      header: {
        title: "Cơ chế của kênh bán",
        list: [
          {
            name: "Cơ chế của kênh bán",
            link: {
              name: "Ui.buttons"
            },
          },
        ],
      },
      btn_Enabled: {
        nhapMoi: false,
        ghiLai: false,
        xoa: false,
        huy: false,
      },
      // popup
      popupComponent: null,
      popupComponentData: null,
      popupComponentAttr: {},
      popupComponentEvts: {
        'form-close': this.popupClosed,
      },
      popupComponentName: '',
      popupComponentHeader: '',
      // TT phieu
      tt_nd: {},
      // TT hop dong
      kenh_id: 0,
      dtp_TuNgay: { value: moment(new Date()).format('DD/MM/YYYY'), Enabled: true },
      dtp_DenNgay: { value: moment(new Date()).format('DD/MM/YYYY'), Enabled: true },
      cbb_DieuKien: { value: 0, Items: [
        {id: 0, text: 'Tất cả'},
        {id: 1, text: 'Hiện hữu'}
      ], Enabled: true },
      
      // table     
      grid_CoChe: {
        header: [
          { fieldName: 'hdkcc_id', headerText: 'ID', allowFiltering: true, allowSorting: false, width: 90 },
          { fieldName: 'hdk_id', headerText: 'HĐ kênh ID', allowFiltering: true, allowSorting: false, width: 110 },
          { fieldName: 'ngay_ky', headerText: 'Ngày ký', allowFiltering: true, allowSorting: false },
          { fieldName: 'ten_dvvt', headerText: 'Dịch vụ', allowFiltering: true, allowSorting: false },
          { fieldName: 'loaihinh_tb', headerText: 'Loại hình', allowFiltering: true, allowSorting: false },
          { fieldName: 'tyle', headerText: 'Tỷ lệ % thù lao', allowFiltering: true, allowSorting: false },
          { fieldName: 'sotien', headerText: 'Số tiền được hưởng', allowFiltering: true, allowSorting: false,
            template: () => {
              return {
                template: {
                  template: `<div> {{ cp_tien }} </div>`,
                  data: function () {
                    return {
                      data: {},
                    };
                  },
                  computed: {
                    parent() {
                      return this.$parent.$parent.$parent;
                    },
                    cp_tien: {
                      get() {
                        return this.numberWithCommas(this.data.sotien);
                      },
                      set(value) {
                        var replaced = value.replaceAll(",", "");
                        this.data.sotien = Number(replaced);
                        var index = this.$parent.$parent.$parent.grid_CoChe.list.findIndex(e => e.hdkcc_id == this.data.hdkcc_id)
                        if (index > -1) this.$parent.$parent.$parent.grid_CoChe.list[index]['sotien'] = Number(replaced)
                      },
                    },
                  },
                  methods: {
                    isNumber(evt) {
                      evt = evt ? evt : window.event;
                      var charCode = evt.which ? evt.which : evt.keyCode;
                      if (charCode > 31 && (charCode < 48 || charCode > 57)) {
                        evt.preventDefault();
                      } else {
                        return true;
                      }
                    },
                    numberWithCommas(n) {
                      var parts = n.toString().split(".");
                      return (
                        parts[0].replace(/\B(?=(\d{3})+(?!\d))/g, ",") +
                        (parts[1] ? "." + parts[1] : "")
                      );
                    },
                  },
                },
              };
            },
          },
          { fieldName: 'ten_congdoan', headerText: 'Công đoạn', allowFiltering: true, allowSorting: false },
          { fieldName: 'noidung', headerText: 'Nội dung', allowFiltering: true, allowSorting: false },          
        ],
        list: [],
        selected: {},
        selectedItems: [],
      },
    }
  },
  async created() {
    this.tt_nd = await this.$root.token.getThongTinNguoiDung()
  },
  mounted: async function () {
    await this.Load_Form()
  },
  watch: {},
  methods: {
    Load_Form: async function () {
      try {
        this.loading(true)
        this.kenh_id = (this.dulieu && this.dulieu.kenh_id) ? this.dulieu.kenh_id : (this.$route.query.kenh_id ? this.$route.query.kenh_id : 0)

        await Promise.all([          
          this.getDanhsach_CoChe()
        ])
      } catch (e) {
        console.log(e)
      } finally {
        this.loading(false)
      }
    },          
    // Lấy danh sách cơ chế của hợp đồng
    getDanhsach_CoChe: async function () {
      this.grid_CoChe.list = []
      let input = {
        kenh_id: this.kenh_id,
        loctheo: this.cbb_DieuKien.value, // loctheo: 0  -Tất cả; 1  - Hiện hữu
        tu_ngay: this.dtp_TuNgay.value,
        den_ngay: this.dtp_DenNgay.value
      }
      let data = this.GetDataList(await api.dsCoCheKenhBan(this.axios, input));
      this.grid_CoChe.list = data && data.length > 0 ? data : []
    },
    onSelectedItems: function (data) {
      this.grid_CoChe.selectedItems = data
    },
    // Change value
    onChangeTuNgay: function (value_) {
      if (!moment(value_, 'DD/MM/YYYY').isValid()) {
        this.dtp_TuNgay.value = moment(new Date()).format('DD/MM/YYYY')
      } else {
        this.dtp_TuNgay.value = value_
      }
    },
    onChangeDenNgay: function (value_) {
      if (!moment(value_, 'DD/MM/YYYY').isValid()) {
        this.dtp_DenNgay.value = moment(new Date()).format('DD/MM/YYYY')
      } else {
        this.dtp_DenNgay.value = value_
      }
    },
    GetDataList: function (response) {
      if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
        return response.data.data
      } else if (response && response.data && response.data.error_code === 'BSS-00000204') {
        return []
      } else {
        this.$root.$toast.error('Lỗi: ' + (response.data.message_detail ? response.data.message_detail : response.data.message))
        return []
      }
    },
    GetData: function (response) {
      if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
        return response.data.data
      } else if (response && response.data && response.data.error_code === 'BSS-00000204') {
        return ''
      } else {
        this.$root.$toast.error('Lỗi: ' + (response.data.message_detail ? response.data.message_detail : response.data.message))
        return ''
      }
    },
    clear: function () {
      this.dtp_TuNgay.value = moment(new Date()).format('DD/MM/YYYY')
      this.dtp_DenNgay.value = moment(new Date()).format('DD/MM/YYYY')
     
      this.grid_CoChe.list = []
      this.grid_CoChe.selectedItems = []
    },
    
    // Button click
    btnTimKiem_click: function () {
      this.Load_Form()
    },    
    btnChapNhan_Click: function () {
      this.closeForm();
    },
    closeForm() {
      this.$emit("form-close", this.grid_CoChe.selectedItems);
    },
    Popup(modalId) {
      this.$bvModal.show(modalId)
    },
    ClosePopup(modalId) {
      this.$bvModal.hide(modalId)
      this.popupComponent = null
    },
    async popupClosed(val) {
      try {
        if (!val) {
          this.ClosePopup('popupComponents')
          return
        }
        this.loading(true)

        console.log('popupClosed', this.popupComponentName, val)
        switch (this.popupComponentName) {
          case 'popup_TTThanhToan':
            break
        }
      } catch (e) {
        console.log('popupClosed', e)
      } finally {
        this.loading(false)
      }
    }
  },
}
</script>

<style scoped src="../SalesChannelContract.scss"></style>
<style>
.select-custom {
  width: 100% !important;
}

@media (min-width: 992px) {
  .modal .modal-huge {
    max-width: 95% !important;
    width: 95% !important;
  }
}

@media (min-width: 576px) {
  .modal .modal-huge {
    max-width: 95% !important;
    width: 95% !important;
  }
}
</style>