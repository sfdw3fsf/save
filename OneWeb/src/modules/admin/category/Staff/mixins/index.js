import { customPaging } from '../plugins/customPaging'
import { Filter, Page } from '@syncfusion/ej2-vue-grids'

export const mixin = {
  data: function () {
    return {
      pageSettings: { pageSize: 10 },
      selectionOptions: {
        enableToggle: false,
        type: 'Multiple'
      },
      pagerTemplate: customPaging,
      filterOptions: {
        operator: 'contains'
      }
    }
  },
  methods: {},
  provide: {
    grid: [Filter, Page]
  }
}
