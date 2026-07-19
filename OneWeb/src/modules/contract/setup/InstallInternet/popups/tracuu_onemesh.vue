<template>
  <b-modal
    ref="tracuu_onemesh"
    size="lg"
    hide-footer
    body-class="p-0"
    title="Tra cứu serial trên hệ thống One Mesh"
    @show="handleShowModal"
  >
    <div class="modal-content popup-box">
      <div class="list-actions-top">
        <ul class="list">
          <li>
            <a @click="tsbtnTraCuu_Click">
              <span class="icon one-file-search"></span>Tra cứu
            </a>
          </li>
        </ul>
      </div>
      <div class="popup-body">
        <div class="box-form">
          <div class="info-row">
            <div class="key w40">Serial</div>
            <div class="value">
              <input v-model="serial" type="text" class="form-control" />
            </div>
          </div>
          <textarea v-model="jsonRes" name class="form-control" style="height: 230px;resize: none;"></textarea>
        </div>
      </div>
    </div>
  </b-modal>
</template>
<script>
export default {
  data() {
    return {
      serial: "",
      jsonRes: ""
    }
  },
  methods: {
    showModal() {
      this.$refs["tracuu_onemesh"].show();
    },
    hideModal() {
      this.$refs["tracuu_onemesh"].hide();
    },
    handleShowModal() { },
    async tsbtnTraCuu_Click() {
      if (!this.serial) {
        this.jsonRes = "Chưa nhập thông tin serial thiết bị";
      }
      else {
        let data = await this.axios.get("tichhop/mesh_wifi/verifyDeviceActivation", {
          params: {
            serialNumber: this.serial
          }
        })

        this.jsonRes = data.data && data.data.data && data.data.data.activationStatus == true ? JSON.stringify(data.data.data, null, 4) : "Không tìm thấy thiết bị"
        // GNMSService mesh_wifi = new GNMSService();
        // jsonRes = mesh_wifi.checkDeviceActivation(txtSerial.Text.Trim()).Replace(",\"",",\r\t\"").Replace("{\"","{\r\t\"").Replace("\"}","\"\r}");

        //jsonRes = JsonConvert.SerializeObject(strRes, Formatting.None).ToString();
      }
    }
  },
};
</script>
