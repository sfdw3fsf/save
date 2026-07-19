import { customPaging } from '../../Staff/plugins/customPaging'
import { Filter, Page } from '@syncfusion/ej2-vue-grids'
import { Query } from '@syncfusion/ej2-data'

export const pagingAndFilterMixin = {
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
      pagerTemplate: customPaging,
      filterOptions: {
        operator: 'contains'
      }
    }
  },
  methods: {
    onfilteringDropDownList (event, dataSource, name) {
      let query = new Query()
      query =
        event.text !== ''
          ? query.where(name, 'contains', event.text, true)
          : query
      event.updateData(dataSource, query)
    }
  },
  provide: {
    grid: [Filter, Page]
  }
}
