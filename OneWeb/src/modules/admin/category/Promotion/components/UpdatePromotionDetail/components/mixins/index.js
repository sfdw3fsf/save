import { customPaging } from '../plugins/customPaging'
import { Filter, Page } from '@syncfusion/ej2-vue-grids'
import { Query } from '@syncfusion/ej2-data'

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
  provide: {
    grid: [Filter, Page]
  },
  methods: {

    onFilteringDropDownList(e, dataSource, name) {
      var query = new Query();
      query =
        e.text !== "" ? query.where(name, "contains", e.text, true) : query;
      e.updateData(dataSource, query);
    },
  }
}
