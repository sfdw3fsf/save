<template>
  <ejs-dialog :allowDragging="true" :animationSettings="animationSettings" :enableResize="false"
    :position="{ X: 'center', Y: 'top' }" :visible="false" ref="popupNhaCC" :showCloseIcon="false" width="70%"
    minHeight="350" :isModal="true" :target="target">
    <div class="modal-content popup-box">
      <div class="popup-header">
        <div class="title">
          <span class="icon one-file-plus"></span>Nhà mạng khác
        </div>
        <div class="close -ap icon-close" style="cursor: pointer" @click="hideModal"></div>
      </div>
      <ActionTop :actions="actions" @onActionClick="onActionClick" />
      <div class="popup-body">
        <div class="box-form">
          <div class="legend-title">Thông tin</div>
          <div class="row">
            <div class="col-sm-4">
              <div class="info-row">
                <div class="key w120">Nhà mạng</div>
                <div class="value">
                  <SelectExt :options="lsNhaMang" v-model="nhamang"></SelectExt>
                </div>
              </div>
            </div>
            <div class="col-sm-4">
              <div class="info-row">
                <div class="key w120">Fiber PSC</div>
                <div class="value">
                  <vue-numeric class="form-control" v-model="fiber" output-type="Number">
                  </vue-numeric>
                </div>
              </div>
            </div>
            <div class="col-sm-4">
              <div class="info-row">
                <div class="key w120">APRU</div>
                <div class="value">
                  <vue-numeric class="form-control" v-model="apru" output-type="Number">
                  </vue-numeric>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="box-form">
          <div class="legend-title">Danh sách nhà mạng khác</div>
          <div class="info-row">
            <DataGrid v-bind:columns="dsNhaCC.columns" v-bind:dataSource="dsNhaCC.data" :showColumnCheckbox="false"
              :enable-paging-server="false" :allowPaging="true" :showFilter="true" :showSTT="true" ref="refDSTinh"
              panelDataHeight="300" :enabledSelectFirstRow="false" @selectedRowChanged="gridChanged"></DataGrid>
          </div>
        </div>
      </div>
    </div>
  </ejs-dialog>
</template>

<script>
import breadcrumb from "@/components/kylq_components/breadcrumb.vue";
import ActionTop from "@/components/kylq_components/ActionTop.vue";
import moment from "moment";
import api from "../API";
export default {
  name: "nhacc",
  components: {
    api,
    ActionTop,
    moment,
    breadcrumb,
  },
  data() {
    return {
      actions: [
        {
          id: "capnhat",
          name: "Ghi lại",
          active: true,
          icon_class: "icon fa fa-edit",
        },
        {
          id: "xoa",
          name: "Xoá",
          active: true,
          icon_class: "icon one-trash",
        },
        {
          id: "thoat",
          name: "Thoát",
          active: true,
          icon_class: "",
        },
      ],
      dsNhaCC:
      {
        columns: [
          {
            fieldName: "ten_nhacc", headerText: "Nhà mạng khác", allowFiltering: true
          },
          {
            fieldName: "fiber_psc", headerText: "Fiber PSC", type: 'Number',
            format: 'N0', allowFiltering: true
          },
          {
            fieldName: "apru", headerText: "APRU", type: 'Number',
            format: 'N0', allowFiltering: true
          },
        ],
        data: [],
      },
      nhamang: "",
      fiber: "",
      apru: "",
      nhaccID: "",
      lsNhaMang: [],
      toanha_id: ""
    };
  },
  methods: {
    onActionClick(action) {
      switch (action.id) {
        case "capnhat":
          this.CapNhat(1)
          break;
        case "xoa":
          this.CapNhat(0)
          break;
        case "thoat":
          this.hideModal()
          break;
        default:
          break;
      }
    },
    CapNhat(value) {
      var toanha_id = {
        "NHACC_ID": this.nhamang,
        "TOANHA_ID": this.toanha_id,
        "FIBER_PSC": this.fiber,
        "APRU": this.apru,
        "NGUOI_CN": this.$root.token.getUserName()
      }
      var input = {
        "vkieu": value,
        "vds": JSON.stringify(toanha_id)
      }
      api.fn_capnhat_ct_nhacc_toanha(this.axios, input).then((res) => {
        if (res.data.error === '200') {
          this.$toast.success("Thành công")
        }
      });

    },
    getDsNhaCC() {
      var toanha_id = {
        "TOANHA_ID": this.toanha_id
      }
      var input = {
        "nghiepvu": "DANHMUC_CT_NHACC_TOANHA",
        "ds_para": JSON.stringify(toanha_id)
      }
      api.lay_loai_tt_duan(this.axios, input).then((res) => {
        if (res.data.data.length > 0) {
          this.dsNhaCC.data = res.data.data
        }
      });
    },
    GetData: function (response) {
      if (response.data.error === 200 || response.data.error === '200') {
        return response.data.data
      } else {
        console.log(response.data.error_code)
      }
      return []
    },
    async showModal() {
      this.getDsNhaCC();
      var input = {
        "nghiepvu": "DANHMUC_NHA_CC",
        "ds_para": ""
      }
      this.lsNhaMang = this.GetData(await api.lay_loai_tt_duan(this.axios, input)).map(x => ({
        id: x.nhacc_id,
        text: x.ten_nhacc
      }))
      this.$refs["popupNhaCC"].show();

    },
    hideModal() {
      this.$refs["popupNhaCC"].hide();
    },
    gridChanged(item) {
      this.nhamang = item.nhacc_id
      this.fiber = item.fiber_psc
      this.apru = item.apru
    }
  },
};
</script>