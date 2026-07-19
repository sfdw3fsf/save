<template>
  <b-modal
    id="modal-magd"
    size="lg"
    title="Danh sách hợp đồng"
    hide-footer
  >
    <div class="grid-ext">
      <ejs-grid
        ref="grid_popup"
        :dataSource='data'
        height='315px'
        @recordClick="recordClick"
      >
        <e-columns>
          <e-column
            field='maGd'
            headerText='Mã GD'
            :isPrimaryKey='true'
            width=100
          ></e-column>
          <e-column
            field='ngayYc'
            :allowEditing="false"
            headerText='Ngày yêu cầu'
            width=120
          ></e-column>
          <e-column
            field='trangThaiHd'
            :allowEditing="true"
            headerText='Trạng thái'
            width=120
            format='C2'
          ></e-column>
          <e-column
            field='tenDv'
            :allowEditing="true"
            headerText='Đơn vị lập HĐ/PL'
            width=120
            format='C2'
          ></e-column>
        </e-columns>
      </ejs-grid>
    </div>
  </b-modal>
</template>
<script>
export default {
    props: ['data'],
  methods: {
    async showModal() {
        this.$bvModal.show("modal-magd")
    },
    recordClick(data) {
        this.$emit('chooseData', data)
        this.$bvModal.hide("modal-magd")
    }
  }
}
</script>