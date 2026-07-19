<template>
  <div>
    <b-modal
      id="LichSuHen"
      modal-class="popup-box"
      header-class="popup-header"
      body-class="none-padding"
      size="xl"
      hide-footer
    >
      <template #modal-header="{ close }">
        <div class="title">Lịch sử hẹn PHKH</div>
        <div
          @click="close"
          class="close -ap icon-close"
          data-dismiss="modal"
        ></div>
      </template>
      <div class="popup-body">
        <vue-card>
            <vue-table :config="config"
            ></vue-table>
        </vue-card>
      </div>
    </b-modal>
  </div>
</template>
<script>
export default {
    props: {
        open: Boolean
    },
    data() {
        return {
            config: [
                {
                    label: "Ngày hẹn từ",
                    filterAble: true,
                },
                {
                    label: "Ngày hẹn đến",
                    filterAble: true,
                },
                {
                    label: "Ngày CN",
                    filterAble: true,
                },
                {
                    label: "Người CN",
                    filterAble: true,
                },
                {
                    label: "Nội dung",
                    filterAble: true,
                },
                {
                    label: "Lý do hẹn",
                    filterAble: true,
                }
                ]
        }
    },
    watch: {
        open() {
            this.$bvModal.show("LichSuHen");
        }
    }
};
</script>