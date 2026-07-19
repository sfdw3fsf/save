<template>
    <div class="box-move-select-table">
        <div class="box-col box-form">
            <div class="legend-title">Danh sách phường đã gán</div>
            <DataGrid
                ref="wardsAdded"
                :columns="tableAdded.config"
                :dataSource="tableAdded.options"
                :enable-paging-server="false"
                :allowPaging="true"
                :loading="true"
                :showFilter="true"
                :showColumnCheckbox="true"
                @selectedItemsChanged="selectedItemsChangedWardsAdded"
            ></DataGrid>
        </div>
        <div class="actions">
            <button class="btn" @click="removeWardsCheckedFromList">
                <span class="fa fa-angle-right"></span>
            </button>
            <button class="btn" @click="removeAllWardsFromList">
                <span class="fa fa-angle-double-right"></span>
            </button>
            <button class="btn" @click="moveWardsCheckedToList">
                <span class="fa fa-angle-left"></span>
            </button>
            <button class="btn" @click="moveAllWardsToList">
                <span class="fa fa-angle-double-left"></span>
            </button>
        </div>
        <div class="box-col box-form">
            <div class="legend-title">Danh sách phường chưa gán</div>
            <DataGrid
                ref="wardsNotAdded"
                :columns="tableNotAdded.config"
                :dataSource="tableNotAdded.options"
                :enable-paging-server="false"
                :allowPaging="true"
                :loading="true"
                :showFilter="true"
                :showColumnCheckbox="true"
                 @selectedItemsChanged="selectedItemsChangedWardsNotAdded"
            ></DataGrid>
        </div>
    </div>
</template>

<script>
import { mapActions, mapGetters } from 'vuex'
import gridView from '@/components/Shared/gridview.vue'

export default {
  components: {
    gridView
  },
  data () {
    return {
      tableAdded: {
        selected: null,
        config: [{
          headerText: 'Tên phường',
          fieldName: 'ward'
        }, {
          headerText: 'Tên quận',
          fieldName: 'district'
        }
        ],
        loading: false,
        options: []
      },
      tableNotAdded: {
        selected: null,
        config: [
          {
            headerText: 'Tên phường',
            fieldName: 'ward'
          }
        ],
        loading: false,
        options: []
      },
      selectedWardsNotAdded: [],
      selectedWardsAdded: []
    }
  },
  computed: {
    ...mapGetters('streetCommon', [
      'getWardsAdded',
      'getWardsNotAdded',
      'getSelectedDistrictId',
      'getTownId'
    ])
  },
  methods: {
    ...mapActions('streetCommon', [
      'setWardsAdded',
      'addWardsToWardsAdded',
      'removeWardsFromWardsNotAdded',
      'addWardsToWardsNotAdded',
      'removeWardsFromWardsAdded',
      'setWardsNotAdded'
    ]),
    moveWardsCheckedToList: function () {
      if (this.selectedWardsNotAdded.length > 0) {
        this.addWardsToWardsAdded(this.selectedWardsNotAdded)
        this.removeWardsFromWardsNotAdded(this.selectedWardsNotAdded)
        this.selectedWardsNotAdded = []
      }
    },
    moveAllWardsToList: function () {
      this.addWardsToWardsAdded(this.getWardsNotAdded)
      this.removeWardsFromWardsNotAdded(this.getWardsNotAdded)
    },
    removeWardsCheckedFromList: function () {
      if (this.selectedWardsAdded.length > 0) {
        this.addWardsToWardsNotAdded(this.selectedWardsAdded)
        this.removeWardsFromWardsAdded(this.selectedWardsAdded)
        this.selectedWardsAdded = []
      }
    },
    removeAllWardsFromList: function () {
      this.addWardsToWardsNotAdded(this.getWardsAdded)
      this.removeWardsFromWardsAdded(this.getWardsAdded)
    },
    selectedItemsChangedWardsNotAdded: function (e) {
      this.selectedWardsNotAdded = e
    },
    selectedItemsChangedWardsAdded: function (e) {
      this.selectedWardsAdded = e
    }
  },
  watch: {
    getTownId: async function (newValue, oldValue) {
      this.$root.showLoading(true)
      await this.setWardsAdded({
        pho_id: newValue
      })
      await this.setWardsNotAdded({
        pho_id: newValue,
        quan_id: this.getSelectedDistrictId
      })
      this.$root.showLoading(false)
    },
    getWardsAdded: function () {
      this.tableAdded.options = this.getWardsAdded
    },
    getWardsNotAdded: function (newValue, oldValue) {
      this.tableNotAdded.options = newValue
    }
  }
}
</script>
