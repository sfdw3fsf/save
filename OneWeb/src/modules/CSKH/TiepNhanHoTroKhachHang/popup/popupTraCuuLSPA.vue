<template>
    <b-modal
      id="modal-tracuu-lspa"
      size="xl"
      hide-footer
      body-class="p-0"
      @hide="closeForm"
      no-close-on-backdrop
      scrollable
    >
      <template #modal-title>
        <div class="title">
          <span class="icon one-notepad"></span> Tra cứu lịch sử phản ánh
        </div>
      </template>
      <TraCuuLSPA :maKhachHang="maKH"/>
    </b-modal>
</template>
<script>
import TraCuuLSPA from "../../TraCuuLichSuPhanAnh";
export default {
    props: ['maKH'],
    components: {
        TraCuuLSPA,
    },
    methods: {
        closeForm() {
        this.$bvModal.hide("modal-tracuu-lspa");
        },
    },
};
</script>
