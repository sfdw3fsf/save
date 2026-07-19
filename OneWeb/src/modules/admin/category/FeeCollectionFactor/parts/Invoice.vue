<template>
    <div class="box-move-select-table">
        <div class="box-col box-form">
            <div class="legend-title">Kỳ hóa đơn đã gán</div>
            <DataGrid ref="invoicesAdded"
                :columns="tableAdded.columns"
                :dataSource="tableAdded.options"
                :enable-paging-server="false"
                :allowPaging="true"
                :loading="true"
                :showFilter="true"
                :showColumnCheckbox="true"
                :enabledSelectFirstRow="false"
                :selectionSettings="{ type: 'Multiple'}"
                @selectedItemsChanged="selectedItemsAddedChanged"
            ></DataGrid>
        </div>
        <div class="actions">
            <button class="btn" @click="removeInvoicesFromList">
                <span class="fa fa-angle-right"></span>
            </button>
            <button class="btn" @click="removeAllInvoicesFromList">
                <span class="fa fa-angle-double-right"></span>
            </button>
            <button class="btn" @click="addInvoicesToList">
                <span class="fa fa-angle-left"></span>
            </button>
            <button class="btn" @click="addAllInvoicesToList">
                <span class="fa fa-angle-double-left"></span>
            </button>
        </div>
        <div class="box-col box-form">
            <div class="legend-title">Kỳ hóa đơn chưa gán</div>
            <DataGrid ref="invoicesNotAdded"
                :columns="tableNotAdded.columns"
                :dataSource="tableNotAdded.options"
                :enable-paging-server="false"
                :allowPaging="true"
                :loading="true"
                :showFilter="true"
                :showColumnCheckbox="true"
                :enabledSelectFirstRow="false"
                :selectionSettings="{ type: 'Multiple'}"
                @selectedItemsChanged="selectedItemsNotAddedChanged"
            ></DataGrid>
        </div>
    </div>
</template>

<script>
import gridView from '@/components/Shared/gridview.vue'
import { mapActions } from 'vuex'
export default {
  components: {
    gridView
  },
  data () {
    return {
      perpage: Number.MAX_SAFE_INTEGER,
      tableAdded: {
        selected: null,
        columns: [{
          fieldName: 'chukyno',
          headerText: 'Kỳ hóa đơn'
        }],
        options: []
      },
      tableNotAdded: {
        selected: null,
        columns: [{
          fieldName: 'chukyno',
          headerText: 'Kỳ hóa đơn'
        }],
        options: []
      },
      checkedElementsNotAdded: [],
      checkedElementsAdded: []
    }
  },
  props: {
    invoicePeriodsNotAdd: Array,
    invoicePeriodsAdded: Array
  },
  watch: {
    invoicePeriodsNotAdd: function () {
      this.tableNotAdded.options = this.invoicePeriodsNotAdd
    },
    invoicePeriodsAdded: function () {
      this.tableAdded.options = this.invoicePeriodsAdded
    }
  },
  methods: {
    ...mapActions('feeCollectionFactorCommon', [
      'addInvoicePeriodsToAddedList',
      'removeInvoicePeriodsFromList'
    ]),
    addAllInvoicesToList: function (e) {
      this.addInvoicePeriodsToAddedList(this.tableNotAdded.options)
    },
    addInvoicesToList: function () {
      this.addInvoicePeriodsToAddedList(this.checkedElementsNotAdded)
      this.checkedElementsNotAdded = []
    },
    removeAllInvoicesFromList: function () {
      this.removeInvoicePeriodsFromList(this.tableAdded.options)
    },
    removeInvoicesFromList: function () {
      this.removeInvoicePeriodsFromList(this.checkedElementsAdded)
      this.checkedElementsAdded = []
    },
    selectedItemsNotAddedChanged: function (e) {
      this.checkedElementsNotAdded = e
    },
    selectedItemsAddedChanged: function (e) {
      this.checkedElementsAdded = e
    }
  }
}
</script>
