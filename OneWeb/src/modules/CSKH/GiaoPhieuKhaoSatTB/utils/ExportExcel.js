import { Workbook } from "@syncfusion/ej2-excel-export/index";
export default (columnGrid, data, fileName, sheetName) => {
    let borders = {
        color: "#00000000",
        // lineStyle: "thin",
        bold: true
    };

    let funcColumn = data => {
        let arr = [];
        data.forEach((element, key) => {
            arr.push({
                index: key + 1,
                width: element.width ? element.width : 130
            });
        });
        return arr;
    };
    let funcColumnHeader = data => {
        let arr = [];
        data.forEach((element, key) => {
            arr.push({
                index: key + 1,
                value: element.headerText,
                style: {
                    bold: true
                    // borders: borders
                }
            });
        });
        return arr;
    };
    let columns = funcColumn(columnGrid);
    let columnsHeader = funcColumnHeader(columnGrid);
    let headerRow_line = {
        index: 1,
        cells: columnsHeader
    };
    let rows = [];
    rows.push(headerRow_line);
    let index = 2;
    data.forEach(record => {
        let dataCells = [];
        columnGrid.forEach((element, key) => {
            dataCells.push({
                index: key + 1,
                value: record[element.fieldName],
                style: { bold: false }
            });
        });
        let row = {
            index: index++,
            cells: dataCells
        };
        rows.push(row);
    });
    let worksheets = [{ columns, rows }];
    worksheets[0].name = `${sheetName}`;
    let workbook = new Workbook({ worksheets }, "xlsx");
    workbook.save(`${fileName}.xlsx`);
};
