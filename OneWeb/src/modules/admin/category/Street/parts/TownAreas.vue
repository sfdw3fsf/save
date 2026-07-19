<template>
    <div class="box-col box-form">
        <div class="legend-title">Danh sách Phố/Ấp/Khu</div>
        <DataGrid
            ref="getTownsByWard"
            :columns="table.config"
            :dataSource="getTownsByWard"
            :enable-paging-server="false"
            :allowPaging="true"
            :loading="true"
            :showFilter="true"
            @selectedRowChanged="onSelect"
        ></DataGrid>
    </div>
</template>

<script>
import { mapActions, mapGetters } from 'vuex'
export default {
  data () {
    return {
      table: {
        config: [
          {
            headerText: 'Phố ID',
            fieldName: 'townId',
            textAlign: 'left'
          },
          {
            headerText: 'Phố ID NEO',
            fieldName: 'townIdNeo',
            textAlign: 'left'
          },
          {
            headerText: 'Tên phố',
            fieldName: 'townName',
            textAlign: 'left'
          },
          {
            headerText: 'Nhóm phố',
            fieldName: 'townGroup',
            textAlign: 'left'
          }
        ],
        loading: false
      }
    }
  },
  computed: {
    ...mapGetters('streetCommon', [
      'getTownsByWard',
      'getTownAreas'
    ])
  },
  methods: {
    ...mapActions('streetCommon', [
      'setTownName',
      'setTownIdNeo',
      'setSelectedTownArea',
      'setTownId',
      'setNote'
    ]),
    onSelect: function (e) {
      this.setTownName(e.townName)
      this.setTownIdNeo(e.townIdNeo)
      this.setSelectedTownArea(this.getTownAreas.find(i => i.NAME === e.townGroup).ID)
      this.setTownId(e.townId)
      this.setNote(e.note)

      this.$emit('onSelect', e)
    }
  }
}
</script>
