<template>
  <b-modal
    id="popup-huongDan"
    size="xl"
    hide-footer
    hide-header
    hide-header-close
    body-class="modal-body p-0"
  >
    <div class="modal-content popup-box">
      <div class="popup-header">
        <div class="title">
          <span class="icon one-notepad"></span>Hướng dẫn
        </div>
        <div
          class="close -ap icon-close"
          data-dismiss="modal"
          @click="$bvModal.hide('popup-huongDan')"
        ></div>
      </div>
      <iframe src="http://10.70.52.164:8081/FILE//hdsd/frmTraCuuTBKS.html" style="width:100%;height:800px"></iframe>
    </div>
  </b-modal>
</template>
<script>
export default {
  props: {
    url: {
      type: String,
      default: ''
    },
  },
  created() {},
  data() {
    return {
    };
  },
  computed: {},
  methods: {},
};
</script>
