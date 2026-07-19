<template>
  <div>
    <div class="list-actions-top">
      <ul class="list">
        <li>
          <a
            href="javascript:void(0)"
            @click="btnGiaoPhoiHop_Click"
            title="Giao phối hợp"
          >
            <span class="icon one-search"></span> Giao phối hợp
          </a>
        </li>
      </ul>
    </div>
    <b-modal
      ref="modalFormGiaoPhieuPhoiHopRef"
      title="Giao phiếu phối hợp"
      centered
      hide-footer
      size="xl"
      modal-class="bss-modal"
      @ok=""
      @shown="modalFormGiaoPhieuPhoiHopRef_Shown"
      @hidden="modalFormGiaoPhieuPhoiHopRef_Hidden"
    >
      <template #default="{ ok }">
        <formGiaoPhieuPhoiHop ref="formGiaoPhieuPhoiHopRef" @dataDeleted="formGiaoPhieuPhoiHopRef_dataDeleted" @dataChanged="formGiaoPhieuPhoiHopRef_dataChanged"></formGiaoPhieuPhoiHop>
      </template>
    </b-modal>
  </div>
</template>
<script>
import formGiaoPhieuPhoiHop from "./index.vue";
export default {
  name: "PopupGiaoPhieuPhoiHop",
  components: {
    formGiaoPhieuPhoiHop,
  },
  data: function () {
    flag: false //Cờ kiểm tra dữ liệu ở POPUP có thay đổi hay không
  },
  methods: {
    btnGiaoPhoiHop_Click: function (args) {
        //Mở popup khi có lời gọi
        this.$refs.modalFormGiaoPhieuPhoiHopRef.show();
    },
    modalFormGiaoPhieuPhoiHopRef_Shown: async function (args) {
        //Khi mở popup cần cài đặt phiếu HT ID
        await this.$refs.formGiaoPhieuPhoiHopRef.bindData(13);
    },
    modalFormGiaoPhieuPhoiHopRef_Hidden: async function (args) {
        if(this.flag){
            //Làm mới dữ liệu form chính tại đây
            console.log("Làm mới dữ liệu form chính tại đây");
        }
    },
    formGiaoPhieuPhoiHopRef_dataDeleted: async function (args) {
        //Khi phiếu bị hủy, cần đóng popup
        await this.$refs.modalFormGiaoPhieuPhoiHopRef.hide();
    },
    formGiaoPhieuPhoiHopRef_dataChanged: function (args) {
        //Khi dữ liệu thay đổi có thay đổi trên POPUP
        this.flag = true;
    },
  },
};
</script>
