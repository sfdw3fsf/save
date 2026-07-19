<template>
  <div id="app">
    <ejs-grid
      id="Grid"
      ref="grid"
      :dataSource="data"
      :editSettings="editSettings"
      :allowPaging="true"
      :created="created"
    >
      <e-columns>
        <e-column
          field="OrderID"
          isPrimaryKey="true"
          headerText="Order ID"
          width="90"
        ></e-column>
        <e-column
          field="CustomerID"
          headerText="Customer ID"
          format="C2"
          width="90"
        ></e-column>
        <e-column
          field="Mine"
          headerText="Check1"
          width="70"
          displayAsCheckBox="true"
          textAlign="Center"
        >
        </e-column>
        <e-column
          field="NotMine"
          headerText="Check2"
          width="70"
          displayAsCheckBox="true"
          textAlign="Center"
        >
        </e-column>
        <e-column
          field="CancerRelated"
          headerText="Check3"
          width="70"
          displayAsCheckBox="true"
          textAlign="Center"
        ></e-column>
      </e-columns>
    </ejs-grid>
  </div>
</template>

<script>
export default {
  data() {
    return {
      data: [
        {
          OrderID: "OrderID",
          CustomerID: "1",
          Mine: true,
          NotMine: false,
          CancerRelated: true
        }
      ],
      editSettings: {
        allowEditing: true
      }
    };
  },

  methods: {
    created(e) {
      // binding click event
      this.$refs.grid.ej2Instances.element.addEventListener(
        "click",
        this.click
      );
    },
    click(e) {
      // for normal ediitng
      if (
        e.target.classList.contains("e-rowcell") &&
        e.target.querySelector(".e-checkbox-wrapper")
      ) {
        if (this.$refs.grid.ej2Instances.isEdit)
          this.$refs.grid.ej2Instances.endEdit();
        var index = parseInt(e.target.getAttribute("Index"), 10); // row index
        var rowData = this.$refs.grid.ej2Instances.currentViewData[index]; // row data
        var column = this.$refs.grid.ej2Instances.columns[
          parseInt(e.target.getAttribute("aria-colindex"), 10)
        ].field; // field name
        var checkValue = !rowData[column];
        // for toggling the other columns
        rowData.Mine = false;
        rowData.NotMine = false;
        rowData.CancerRelated = false;

        rowData[column] = checkValue;

        // Update the row with the modified data
        this.$refs.grid.ej2Instances.updateRow(index, rowData);
      }
    }
  }
  // provide: {
  //   grid: [Filter, Page]
  // }
};
</script>
<style>
.e-checkbox-wrapper.e-checkbox-disabled .e-frame.e-check,
.e-css.e-checkbox-wrapper.e-checkbox-disabled .e-frame.e-check {
  background-color: #e3165b;
  border-color: transparent;
  color: #fff;
}
</style>
