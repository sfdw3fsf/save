<template>
  <!-- popupCSHT -->
  <div>
    <div class="modal-content popup-box">
      <div class="popup-header">
        <div class="title">Danh sách CSHT</div>
        <div class="close -ap icon-close" @click="$bvModal.hide('popupCSHT')" data-dismiss="modal"></div>
      </div>
      <div class="list-actions-top" style="padding-right: 0px !important; padding-top: 15px !important;">
        <div class="col-sm-4 col-12"></div>
        <div class="col-sm-4 col-12 actions row" style="text-align: center;">
          <div class="col-sm-3 col-12">
            <button class="btn btn-main" @click="revious_CHST">
              <span class="act -ap icon-chevron-thin-left"></span>
            </button>
          </div>
          <div class="col-sm-6 col-12" style="text-align: center;">
            <input type="number" class="form-control" v-model="params.pageNumberTQ" @change="onChangePageNumberTQ" />
          </div>
          <div class="col-sm-3 col-12">
            <button class="btn btn-main" @click="next_CHST">
              <span class="act -ap icon-chevron-thin-right"></span>
            </button>
          </div>
        </div>
        <div class="col-sm-4 col-12">
          <div class="pull-right">
            <button class="btn btn-second pad5" @click="pictureEdit1_Click">
              <span class="one-reload f16"></span>
            </button>
          </div>
        </div>
      </div>
      <div class="popup-body">
        <div class="row">
          <div class="col-sm-6 col-12">
            <div class="info-row">
              <div class="key w90">
                Tìm mã CSHT
              </div>
              <div class="value">
                <div class="select-custom">
                  <ejs-dropdownlist class="form-control" ref="cboTinh" locale="vi" v-model="params.p_Tinh" :dataSource="cboTinh" :fields="{value:'tentat_ccbs', text: 'tentinh'}"> </ejs-dropdownlist>
                </div>
              </div>
            </div>
          </div>
          <div class="col-sm-6 col-12">
            <input type="text" class="form-control" v-model="params.txtTK_MaCSHT" @keyup.enter="pictureEdit1_Click" />
          </div>
        </div>
        <div class="row">
          <div class="col-sm-12 col-12">
            <div class="info-row">
              <div class="key w90">
                Tìm tên CSHT
              </div>
              <div class="value">
                <input type="text" class="form-control" v-model="params.txtTK_TenCSHT" @keyup.enter="pictureEdit1_Click" />
              </div>
            </div>
          </div>
        </div>
        <div class="table-content col-sm-12 col-12">
          <DataGrid
            :columns="[
                            {fieldName: 'stt', headerText: 'STT', allowFiltering: true},                              
                            {fieldName: 'ma_csht', headerText: 'Mã CSHT', allowFiltering: true},
                            {fieldName: 'ten_csht', headerText: 'Tên CSHT', allowFiltering: true},
                            {fieldName: 'muc_dich_su_dung', headerText: 'MĐ Sử dụng', allowFiltering: true},
                            {fieldName: 'stt',  visible:false, isPrimaryKey:true}     
                            ]"
            ref="gridDSMa_PDA"
            :showFilter="true"
            :allowPaging="true"
            :enablePagingServer="false"
            dataKeyField="stt"
            :showColumnCheckbox="false"
            :enabledSelectFirstRow="false"
            v-bind:dataSource="grcMa_CSHT"
            @selectedRowChanged="gridDSMa_PDAFocus"
          />
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  components: {},
  props: {},
  data() {
    return {}
  },
  computed: {},
  async mounted() {},
  watch: {},
  methods: {},
  created() {
    window.addEventListener('keydown', this.keyDownHandler)
  },
  destroyed() {
    window.removeEventListener('keydown', this.keyDownHandler)
  }
}
</script>
<style scoped></style>
