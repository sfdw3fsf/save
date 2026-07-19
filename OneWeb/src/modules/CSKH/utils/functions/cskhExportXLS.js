import { Workbook } from "@syncfusion/ej2-excel-export/index";

export function exportToXls(fields, data, filename) {
    try
    {
        let columns = []
        let rows = []
        fields = fields.filter(x => x.visible != false)

        let headers = {
            index: 1,
            cells: []
        }

        fields.forEach((field, index) => {
            let width = field.width || 130
            columns.push({ index: index + 1, width: width})
            headers.cells.push({
                index: index + 1,
                value: field.headerText,
                style: {
                    borders: {
                        color: "#00000000",
                        lineStyle: "thin",
                    },
                    hAlign: "center",
                    bold: true
                }
            })
        })

        rows.push(headers)
        let rowIndex = 2

        data.forEach(dataItem => {
            let row = {
                index: rowIndex,
                cells: []
            }

            fields.forEach((field, fieldIndex) => {
                let vl = field.callback ? field.callback(dataItem[field.fieldName]) : dataItem[field.fieldName]
                row.cells.push({
                    index: fieldIndex + 1,
                    value: vl,
                    style: {
                        borders: {
                            color: "#00000000",
                            lineStyle: "thin",
                        },
                    }
                })
            })

            rows.push(row)
            rowIndex++
        })
        let worksheets = [{ columns, rows }];
        worksheets[0].name = "export"
        let workbook = new Workbook({ worksheets }, "xlsx");
        let namefile = filename || "Export.xlsx"
        workbook.save(namefile);
    }
    catch (e)
    {
        console.log('export to xls', e)
    }
}
