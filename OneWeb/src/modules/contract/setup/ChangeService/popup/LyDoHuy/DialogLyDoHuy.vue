<template>
  <!-- <b-modal
    size="xl"
    dialog-class="dialog-content"
    body-class="body-content"
    header-class="popup-header"
    content-class="popup-box"
    v-model="isShowModal"
    hide-footer
    :enableResize='true'
  > -->
  <ejs-dialog
    :enableResize="true"
    :allowDragging='true'
    :visible="false"
    ref="modal"
    :header="'Chọn lý do hủy dịch vụ'"
    showCloseIcon="true"
    :target="'.main-wrapper'"
    width="80%"
    :position="{ X: 'center', Y: '10' }"
  >
    <!-- <template #modal-header>
      <div class="title">
        <span class="icon one-notepad"></span> Chọn lý do hủy dịch vụ
      </div>
      <div class="close -ap icon-close" @click.prevent="show(false)"></div>
    </template> -->
    <div style="margin: -1rem">
      <div class="list-actions-top">
        <ul class="list">
          <li>
            <a @click="onAccept">
              <span class="icon ui-1_check-circle-08 nc-icon-glyph"></span> Chấp
              nhận (F5)
            </a>
          </li>
        </ul>
      </div>
      <div class="popup-body">
        <div class="box-form">
          <div class="info-row">
            <select3
              label="Nhóm hủy"
              :options="dsNhomHuy"
              v-model="cboNhomHuy"
              valueField="ID"
              labelField="NAME"
              @input="onSelectNhomHuy"
            />
          </div>
          <div class="table-content">
            <!-- <ejs-grid
              ref="tbLyDoHuy"
              :dataSource="dsLyDoHuy"
              :allowPaging="true"
              :pageSettings="pageSettings"
              :allowSorting="false"
              :allowFiltering="true"
              :allowTextWrap="true"
              gridLines="Both"
            >
              <e-columns>
                <e-column field="stt" headerText="Stt" width="80"></e-column>
                <e-column field="lydohuy" headerText="Lý do hủy"></e-column>
                <e-column
                  field="noidung"
                  headerText="Nội dung"
                  width="200"
                ></e-column>
                <e-column type="checkbox" width="50"></e-column>
              </e-columns>
            </ejs-grid> -->
            <DataGrid
                ref="tbLyDoHuy"
                v-bind:columns="tableCollumns"
                v-bind:dataSource="dsLyDoHuy"                            
                :showFilter="true"
                :allowPaging="true"
                :enablePagingServer="false"                
                :showColumnCheckbox="true"
                :multiple="true"    
                :show-checkbox = "true"    
                :enabled-select-first-row="false"   
                panelDataHeight="400"                   
            >
            </DataGrid>
          </div>
        </div>
      </div>
    </div>
  </ejs-dialog>
  <!-- </b-modal> -->
</template>
<script>
import { mapActions, mapGetters } from "vuex";
import select3 from "@/components/form/VueSelect.vue";
export default {
  components: {
    select3
  },
  data: () => ({
    pageSettings: { pageSizes: [10, 50, 100, 200], pageCount: 4, pageSize: 10 },
    isShowModal: false,
    dsDkLyDoHuy:[],
    hdtbId:null,
    isLapMoi:true,
    loaildId:2,
    loaitbId:null,
    kieu:1,
    cboNhomHuy: null,
    tableCollumns: [
            {
                fieldName: "stt",
                headerText: "Stt",
                allowFiltering: true,
                allowSorting: false,
                width: 80,
            },
            {
                fieldName: "lydohuy",
                headerText: "Lý do hủy",
                allowFiltering: true,
                allowSorting: false,
                width: 200,
            },
            {
                fieldName: "noidung",
                headerText: "Nội dung",
                allowFiltering: true,
                allowSorting: false,
                width: 200,
            }
        ],
  }),
  computed: {
    ...mapGetters("liquidateCommon", ["dsLyDoHuy", "dsNhomHuy"])
  },
  mounted() {

  },
  methods: {
    ...mapActions("liquidateCommon", ["layDsNhomHuy", "layDsLyDoHuy"]),
    show(visible,data) {
      if (visible) {
        this.$refs.modal.show();
      } else {
        this.$refs.modal.hide();
      }

      if (data) {
        this.dsDkLyDoHuy = data.dsDkLyDoHuy;
        this.hdtbId = data.hdtbId;
        this.isLapMoi = data.isLapMoi;
        this.loaildId = data.loaildId;
        this.loaitbId = data.loaitbId;
        this.kieu = data.kieu;
      }

      this.layDsNhomHuy({ loaildID: this.loaildId, loaitbID: this.loaitbId, kieu: this.kieu }).then(rs => {
        if (rs.length > 0) {
          this.cboNhomHuy = rs[0].ID;
        }
      });
    },
    onAccept() {
      this.$emit("onAccept", this.$refs.tbLyDoHuy.getSelectedRecords());
      this.show(false);
    },
    onSelectNhomHuy() {
      this.layDsLyDoHuy({
        loaildID: this.loaildId,
        loaitbID: this.loaitbId,
        nhomHuyID: parseInt(this.cboNhomHuy),
        kieu: this.kieu
      });
    }
  }
};
</script>
<style scoped>
.list-actions-top {
  margin-top: 18px;
  margin-left: 10px;
}
</style>
