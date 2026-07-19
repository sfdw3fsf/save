import moment from "moment";
import XLSX from "xlsx";

export function exportExcel(arrData, nameReport) {
  const stdEx = moment().format('yyyymmddHHss')
  const data = XLSX.utils.json_to_sheet(arrData);
  const wb = XLSX.utils.book_new();
  XLSX.utils.book_append_sheet(wb, data, `Data`);
  var name = nameReport +   `_${stdEx}` + ".xlsx";
  XLSX.writeFile(wb, name);
}
