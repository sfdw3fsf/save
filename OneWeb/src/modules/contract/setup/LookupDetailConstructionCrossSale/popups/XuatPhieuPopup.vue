<template>
  <div class="modal-content popup-box">
    <div class="popup-header">
      <div class="title"><span class="icon one-notepad"></span>In biên bản</div>
      <div
        class="close -ap icon-close"
        data-dismiss="modal"
        @click="$bvModal.hide('popup-xuatphieu')"
      ></div>
    </div>
    <div class="list-actions-top">
      <ul class="list">
        <li>
          <a href="#" @click.prevent="">
            <span class="icon one-file-plus"></span> Biên bản
          </a>
        </li>
      </ul>
    </div>
    <div class="popup-body">
      <div class="box-form">
        <div class="legend-title">
          Danh sách các mẫu in
        </div>
        <div class="info-row">
          <div class="value w20 padt7">
            <div class="check-action">
              <input type="checkbox" class="check" v-model="checkLuuIn">
              <span class="name"></span>
            </div>
          </div>
          <div class="key w80">
            Lưu in
          </div>
          <div class="value">
            <div class="select-custom">
              <ejs-dropdownlist
                :allowFiltering="true"
                :enabled="checkLuuIn"
                v-model="selectBienBan"
                :dataSource="cboBienBan"
              />
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
// Tham chiếu đến UR2.8.004 mục Xuất phiếu
export default {
  name: "XuatPhieuPopup",
  components: {

  },

  mounted () {

  },
  data() {
    return {
      checkLuuIn: true,
      cboBienBan: [],
      selectBienBan: null
    };
  },

  methods: {

  },
}
</script>
