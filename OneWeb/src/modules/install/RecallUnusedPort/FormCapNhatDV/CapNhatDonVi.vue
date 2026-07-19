<template>
  <b-modal
    ref="popupCapNhatDV"
    size="lg"
    hide-header
    hide-header-close
    hide-footer
    body-class="p-0"
    centered
    no-close-on-backdrop
    @show="handleShowModal"
  >
    <div class="modal-content popup-box">
      <div class="popup-header">
        <div class="title">
          <span class="icon one-notepad"></span> {{ headerTitle }}
        </div>
        <div class="close -ap icon-close" @click="hideModal"></div>
      </div>
      <div class="list-actions-top">
        <ul class="list">
          <li v-for="item in actions" :key="item.id" @click="onActionClick(item)"
              :class="{disabled: !item.active}" v-show="item.visible">
            <a style="cursor: pointer;" :class="{disabled_color: !item.active}" :ref="item.id">
              <span class="icon" :class="item.icon_class"></span> {{ item.name }}
            </a>
          </li>
        </ul>
      </div>
      <div class="popup-body">
        <div class="box-form">
          <div class="legend-title">Thông tin phiếu yêu cầu</div>
          <div class="row">
            <div class="col-12">
              <div class="info-row">
                <div class="key w120">Đơn vị QL</div>
                <div class="value">
                  <div class="select-custom">
                    <SelectExt v-model="cboDonViQL.value"
                               :allowFiltering="true"
                               :dataSource="cboDonViQL.list"
                               dataValueField="ID"
                               dataTextField="NAME"/>
                  </div>
                </div>
              </div>
              <div class="info-row">
                <div class="key w120">Trạm VT</div>
                <div class="value">
                  <div class="select-custom">
                    <SelectExt v-model="cboTramVT.value"
                               :allowFiltering="true"
                               :dataSource="cboTramVT.list"
                               dataValueField="donvi_id"
                               dataTextField="ten_dv"/>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </b-modal>
</template>

<script>
import API from "./CapNhatDonVi";

export default {
  name: 'CapNhatDonVi',
  props: {
    inputData: {
      type: Object,
      default: function () {
        return {}
      }
    }
  },
  data() {
    return {
      headerTitle: 'Cập nhật đơn vị thu hồi cổng',
      actions: [
        {
          id: 'tsbtnCapNhat',
          name: 'Cập nhật (F9)',
          active: true,
          icon_class: 'one-save',
          visible: true
        },
        {
          id: 'tsbtnThoat',
          name: 'Thoát',
          active: true,
          icon_class: 'one-cancel',
          visible: true
        }
      ],
      phieu_id: 0,
      cboDonViQL: {
        list: [],
        value: 0
      },
      cboTramVT: {
        list: [],
        value: 0
      }
    }
  },
  watch: {
    'cboDonViQL.value': function (newVal) {
      this.cboDonViQL_Changed()
    },
  },
  methods: {
    handleShowModal() {
      this.cboDonViQL.list = [];
      this.cboTramVT.list = [];
    },

    hideModal() {
      this.$refs.popupCapNhatDV.hide()
    },

    initData() {
      try {
        this.loading(true);
        this.loadDataFromProps();
        this.loadCboDonViQL();
      } catch (error) {
        this.$root.$toast.error('Có lỗi: ' + error)
      } finally {
        this.loading(false)
      }
    },

    loadDataFromProps() {
      if (this.inputData != null) {
        this.phieu_id = this.inputData.phieu_id ? this.inputData.phieu_id : 0
      }
    },

    showModal() {
      this.initData()
      this.$refs.popupCapNhatDV.show()
    },

    onActionClick(action) {
      if (action.id === 'tsbtnCapNhat') {
        this.CapNhat();
      } else if (action.id === 'tsbtnThoat') {
        this.hideModal();
      }
    },

    async loadCboDonViQL() {
      try {
        let rs = await API.lay_dvql(this.axios);
        let {data} = rs.data;
        this.cboDonViQL.list = data != null ? data : [];
        return data;
      } catch (e) {

      }
    },

    cboDonViQL_Changed() {
      let {list, value} = this.cboDonViQL;
      if (list.length > 0) {
        if (value == null) {
          return;
        }

        this.loadCboTramVT(value);
      }
    },

    async loadCboTramVT(dvql_id) {
      try {
        let rs = await API.lay_tramvt(this.axios, {
          "vdonvi_cha_id": dvql_id,
          "vloaidv_id": 5
        });
        let {data} = rs.data;
        this.cboTramVT.list = data != null ? data : [];
        return data;
      } catch (e) {

      }
    },

    async CapNhat() {
      try {
        let rs = await API.capnhat_dvn_giaophieu(this.axios, {
          "vdonvi_id": this.cboTramVT.value,
          "vphieu_id": this.phieu_id
        });

        if (rs.data != null && rs.data.error === '200') {
          this.$root.$toast.success('Cập nhật đơn vị thành công');
          this.$emit('capnhat_success');
        } else {
          this.$root.$toast.error('Cập nhật đơn vị thất bại');
        }
      } catch (e) {
        this.$root.$toast.error('Có lỗi Cập nhật ' + e.message);
      }
    }
  }
}
</script>

<style scoped>
.disabled_color {
  color: gray !important;
}
</style>
