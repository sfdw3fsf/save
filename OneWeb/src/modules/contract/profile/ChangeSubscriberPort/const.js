import XLSX from "xlsx";

export default {
  gridCols: [
    {
      fieldName: "ma_tb",
      headerText: "Mã TB",
      allowFiltering: true,
      allowSorting: false,

    },
    {
      fieldName: "system_cu",
      headerText: "System cũ",
      allowFiltering: true,
      allowSorting: false,

    },
    {
      fieldName: "system_moi",
      headerText: "System mới",
      allowFiltering: true,
      allowSorting: false,

    },
    {
      fieldName: "rack_cu",
      headerText: "Rack cũ",
      allowFiltering: true,
      allowSorting: false,

    },
    {
      fieldName: "rack_moi",
      headerText: "Rack mới",
      allowFiltering: true,
      allowSorting: false,

    },
    {
      fieldName: "shelf_cu",
      headerText: "Shelf cũ",
      allowFiltering: true,
      allowSorting: false,

    },
    {
      fieldName: "shelf_moi",
      headerText: "Shelf mới",
      allowFiltering: true,
      allowSorting: false,

    },
    {
      fieldName: "slot_cu",
      headerText: "Slot cũ",
      allowFiltering: true,
      allowSorting: false,

    },
    {
      fieldName: "slot_moi",
      headerText: "Slot mới",
      allowFiltering: true,
      allowSorting: false,

    },
    {
      fieldName: "port_cu",
      headerText: "Port cũ",
      allowFiltering: true,
      allowSorting: false,

    },
    {
      fieldName: "port_moi",
      headerText: "Port mới",
      allowFiltering: true,
      allowSorting: false,

    },
    {
      fieldName: "vpi_cu",
      headerText: "VPI cũ",
      allowFiltering: true,
      allowSorting: false,

    },
    {
      fieldName: "vci_cu",
      headerText: "VCI cũ",
      allowFiltering: true,
      allowSorting: false,

    },
    {
      fieldName: "vpi_moi",
      headerText: "VPI mới",
      allowFiltering: true,
      allowSorting: false,

    },
    {
      fieldName: "vci_moi",
      headerText: "VCI mới",
      allowFiltering: true,
      allowSorting: false,

    },
    {
      fieldName: "ma_tram_cu",
      headerText: "Mã trạm cũ",
      allowFiltering: true,
      allowSorting: false,

    },
    {
      fieldName: "ma_tram_moi",
      headerText: "Mã trạm mới",
      allowFiltering: true,
      allowSorting: false,
    },
    {
      fieldName: "selfid01_cu",
      headerText: "SELFID  cũ (9TS)",
      allowFiltering: true,
      allowSorting: false,
    },
    {
      fieldName: "selfid02_cu",
      headerText: "OLT_SLOT cũ",
      allowFiltering: true,
      allowSorting: false,
    },
    {
      fieldName: "selfid03_cu",
      headerText: "OLT_PORT cũ",
      allowFiltering: true,
      allowSorting: false,
    },
    {
      fieldName: "selfid04_cu",
      headerText: "ONUID cũ",
      allowFiltering: true,
      allowSorting: false,
    },
    {
      fieldName: "selfid01_moi",
      headerText: "SELFID  mới (9TS)",
      allowFiltering: true,
      allowSorting: false,
    },
    {
      fieldName: "selfid02_moi",
      headerText: "OLT_SLOT mới",
      allowFiltering: true,
      allowSorting: false,
    },
    {
      fieldName: "selfid03_moi",
      headerText: "OLT_PORT mới",
      allowFiltering: true,
      allowSorting: false,
    },
    {
      fieldName: "selfid04_moi",
      headerText: "ONUID mới",
      allowFiltering: true,
      allowSorting: false,
    },
    {
      fieldName: "hople",
      headerText: "Hợp lệ",
      allowFiltering: true,
      allowSorting: false,
    },
    {
      fieldName: "saiportmoi",
      headerText: "Sai port mới",
      allowFiltering: true,
      allowSorting: false,
    },
    {
      fieldName: "bras_id_moi",
      headerText: "BRAS_ID_MOI",
      allowFiltering: true,
      allowSorting: false,
    },
    {
      fieldName: "doiport_id",
      headerText: "DOIPORT_ID",
      allowFiltering: true,
      allowSorting: false,
    }
  ],

  cols: [
    'MA_TB',
    'MA_TRAM_CU',
    'SYSTEM_CU',
    'RACK_CU',
    'SHELF_CU',
    'SELFID_CU',
    'OLT_SLOT_CU',
    'OLT_PORT_CU',
    'ONUID_CU',
    'SLOT_CU',
    'PORT_CU',
    'VPI_CU',
    'VCI_CU',
    'MA_TRAM_MOI',
    'SYSTEM_MOI',
    'RACK_MOI',
    'SHELF_MOI',
    'SELFID_MOI',
    'OLT_SLOT_MOI',
    'OLT_PORT_MOI',
    'ONUID_MOI',
    'SLOT_MOI',
    'PORT_MOI',
    'VPI_MOI',
    'VCI_MOI',
  ],

  readFile(event) {
    let result = event.target.result;
    let workbook = XLSX.read(result, {
      type: 'binary'
    });

    let sheet = workbook.Sheets['Data'];
    let headers = this.getHeaderRow(sheet);
    if (headers.length <= 0) {
      throw 'Cấu trúc file excel không chính xác, vui lòng tải mẫu!';
    }

    for (let header of headers) {
      if (!this.cols.includes(header)) {
        throw 'Cấu trúc file excel không chính xác, vui lòng tải mẫu!';
      }
    }

    return XLSX.utils.sheet_to_json(sheet);
  },

  getHeaderRow(sheet) {
    let headers = [];
    let range = XLSX.utils.decode_range(sheet['!ref']);
    let C, R = range.s.r; /* start in the first row */
    /* walk every column in the range */
    for (C = range.s.c; C <= range.e.c; ++C) {
      let cell = sheet[XLSX.utils.encode_cell({c: C, r: R})] /* find the cell in the first row */

      let hdr = "UNKNOWN " + C; // <-- replace with your desired default
      if (cell && cell.t) hdr = XLSX.utils.format_cell(cell);

      headers.push(hdr);
    }
    return headers;
  }
}
