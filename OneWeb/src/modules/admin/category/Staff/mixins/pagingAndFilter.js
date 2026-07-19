import { customPaging } from '../plugins/customPaging'
import { Filter, Page, Resize } from '@syncfusion/ej2-vue-grids'
import { Query } from '@syncfusion/ej2-data'
import Vue from 'vue'

export const pagingAndFilter = {
  data: function () {
    return {
      pageSettings: { pageSize: 10 },
      selectionOptionsMultiple: {
        enableToggle: false,
        type: 'Multiple'
      },
      selectionOptionsSingle: {
        enableToggle: false,
        type: 'Single'
      },
      resizeSettingsAuto: { mode: 'Auto' },
      resizeSettingsNormal: { mode: 'Normal' },
      pagerTemplate: customPaging,
      filterOptions: {
        operator: 'contains'
      },
      columnIndexTemplate: function () {
        return {
          template: Vue.component('columnIndexTemplate', {
            template: `<span>{{ Number(data.index) + 1 }}</span>`,
            data: function (e) {
              return {
                data: {}
              }
            }
          })
        }
      }
    }
  },
  methods: {
    onFilteringDropDownList (event, dataSource, name) {
      let query = new Query()
      query =
        event.text !== ''
          ? query.where(name, 'contains', event.text, true)
          : query
      event.updateData(dataSource, query)
    }
  },
  provide: {
    grid: [Filter, Page, Resize]
  }
}
