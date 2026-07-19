import XLSX from "xlsx";

export function exportExcel(arrData, nameReport) {
  const data = XLSX.utils.json_to_sheet(arrData);
  const wb = XLSX.utils.book_new();
  XLSX.utils.book_append_sheet(wb, data, "data");
  var name = nameReport + ".xlsx";
  XLSX.writeFile(wb, name);
}
