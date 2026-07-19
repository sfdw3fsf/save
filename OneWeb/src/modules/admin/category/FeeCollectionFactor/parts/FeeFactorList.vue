<template>
    <div class="box-form">
        <div class="legend-title">
            Danh sách hệ số thu cước róc
        </div>
        <DataGrid
            :columns="table.columns"
            :dataSource="dataSource"
            :enable-paging-server="false"
            :allowPaging="true"
            :loading="table.isLoading"
            :showFilter="true"
            :enabledSelectFirstRow="true"
            @selectedRowChanged="onSelect"
        ></DataGrid>
    </div>
</template>

<script>
import { mapActions } from 'vuex'
export default {
  data () {
    return {
      table: {
        isLoading: true,
        columns: [{
          headerText: 'STT',
          fieldName: 'stt',
          textAlign: 'center',
          width: 50
        }, {
          fieldName: 'thoidiem_tps',
          headerText: 'Thời điểm thu cước phát sinh',
          textAlign: 'left'
        }, {
          fieldName: 'heso_tcr',
          headerText: 'Hệ số',
          textAlign: 'center',
          width: 50
        }, {
          fieldName: 'kieu_tcr',
          headerText: 'Kiểu TCR',
          textAlign: 'left'
        }]
      }
    }
  },
  props: {
    dataSource: Array
  },
  methods: {
    ...mapActions('feeCollectionFactorCommon', [
      'loadEmployeeTypesNotAdd',
      'loadEmployeeTypesAdded',
      'loadInvoicePeriodsNotAdd',
      'loadInvoicePeriodsAdded',
      'setSelectedFeeFactor'
    ]),
    onSelect: async function (e) {
      this.$root.showLoading(true)
      try {
        await this.loadEmployeeTypesNotAdd({
          phanvung_id: this.$root.token.getPhanVungID(),
          heso_tcr_id: e.heso_tcr_id,
          loai: 0
        })
        await this.loadEmployeeTypesAdded({
          phanvung_id: this.$root.token.getPhanVungID(),
          heso_tcr_id: e.heso_tcr_id,
          loai: 1
        })
        await this.loadInvoicePeriodsNotAdd({
          phanvung_id: this.$root.token.getPhanVungID(),
          heso_tcr_id: e.heso_tcr_id,
          loai: 0
        })
        await this.loadInvoicePeriodsAdded({
          phanvung_id: this.$root.token.getPhanVungID(),
          heso_tcr_id: e.heso_tcr_id,
          loai: 1
        })

        if (e.kieu_tcr_id) {
          this.setSelectedFeeFactor(e)
          this.$emit('onSelectedFactor', e)
        }
      } catch (error) {
        console.log(error)
        this.$toast.error(error.toString())
      }
      this.$root.showLoading(false)
    }
  }
}
</script>
