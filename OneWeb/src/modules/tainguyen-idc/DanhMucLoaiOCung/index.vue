<template src="./index.template.html"></template>

<script>
import axios from 'axios';
import XLSX from 'xlsx';
import ModalImportExcel from '../components/Modals/ModalImportExcel';

export default {
  props: {
    modalItemID: 0
  },
  components: {
    ModalImportExcel
  },
  data: function () {
    return {
      currentItem: {
        id: null,
        ten: '',
        ghiChu: '',
        suDung: 1,
      },
      dsSelected: [],
      loaiOCungItemList: [],

      controls: {
        txtID: {
          enabled: false,
        },
        txtTen: {
          enabled: false,
          invalid: false
        },
        txtGhiChu: {
          enabled: false,
        },
        chkHieuLuc: {
          enabled: false,
        },
      },
      buttons: {
        btnNhapMoi: {
          enabled: true,
        },
        btnGhiLai: {
          enabled: false,
        },
        btnHuy: {
          enabled: false,
        },
        btnSua: {
          enabled: false,
        },
        btnXoa: {
          enabled: false,
        },
        btnImport: {
          enabled: true,
        },
        btnExport: {
          enabled: true,
        },
      },

      // Cấu hình cho modal import
      importConfig: {
        title: 'Import dữ liệu',
        templateFileName: 'Template_Import_LoaiOCung.xlsx',
        tableColumns: [
          { key: 'ten', label: 'Tên Loại ổ cứng' },
          { key: 'ghiChu', label: 'Ghi chú' },
          { key: 'suDung', label: 'Hiệu lực (1: Hiệu lực, 0: Không hiệu lực)' }
        ],
        // Header cho data grid preview (nếu không truyền thì sẽ dùng tableColumns)
        gridColumns: [
          { key: 'ten', label: 'Tên loại ổ cứng' },
          { key: 'ghiChu', label: 'Ghi chú' },
          { key: 'suDungText', label: 'Hiệu lực' }
        ],
        validationRules: [
          { field: 'ten', required: true, message: 'Tên loại ổ cứng không được để trống' },
          { field: 'ten', maxLength: 100, message: 'Tên loại ổ cứng không được vượt quá 100 ký tự' },
          { field: 'ghiChu', maxLength: 500, message: 'Ghi chú không được vượt quá 500 ký tự' },
          { field: 'suDung', type: 'number', message: 'Hiệu lực phải nhập số' },
          { field: 'suDung', type: 'number', enum: [1, 0], message: 'Hiệu lực chỉ nhận số 0 hoặc 1 hoặc để trống' }
        ],
        checkDataResult: true,
      }
    };
  },
  computed: {
    grdItems: function () {
      return this.$refs.grdItems;
    },
  },
  watch: {
    "currentItem.id": function (value) {
      this.buttons.btnSua.enabled = value > 0;
      this.buttons.btnXoa.enabled = value > 0;
    },
    "currentItem.ten": function (value) {
      this.controls.txtTen.invalid = value == "";
    },
  },
  created: async function () {
    try {
      this.$root.showLoading(true);

      await this.refreshListWithText();

    } finally {
      this.$root.showLoading(false);
    }
  },
  methods: {
    getHieuLucText: function (value) {
      return value == 1 ? "Hiệu lực" : "Không hiệu lực";
    },
    getColumnHieuLucTemplate: function () {
      return function () {
        return {
          template: {
            template: `<span>{{ getText() }}</span>`,
            methods: {
              getText: function () {
                return this.$parent.$parent.$parent.getHieuLucText(this.data.SUDUNG);
              },
            },
          },
        };
      };
    },
    refreshListWithText: async function() {
      let list = await this.getLoaiOCungItems();
      this.loaiOCungItemList = list.map(item => ({
        ...item,
        SUDUNG_TEXT: item.SUDUNG == 1 ? 'Hiệu lực' : 'Không hiệu lực'
      }));
    },
    // Phần gọi các API
    getLoaiOCungItems: async function () {
      let result = [];
      try {
        let rs = await this.$root.context.post("/web-ecms/danhmuc-loai-ocung/get-danhsach-loai-ocung");
        result = rs.data || [];
      } catch (e) {
        if (e.response?.data?.message_detail) {
          this.$root.toastError(e.response.data.message_detail);
        }
      }
      return result;
    },

    upsertLoaiOCung: async function (args) {
      let result = false;
      try {
        let rs = await this.$root.context.post("/web-ecms/danhmuc-loai-ocung/upsert", {
          id: args.id === 0 ? null : args.id,
          ten: args.ten,
          ghiChu: args.ghiChu,
          suDung: args.suDung
        });
        result = rs.data;
      } catch (e) {
        if (e.response?.data?.message_detail) {
          this.$root.toastError(e.response.data.message_detail);
        }
      }
      return result;
    },
    deleteLoaiOCungItem: async function (id) {
      let result = false;
      try {
        let rs = await this.$root.context.post('/web-ecms/danhmuc-loai-ocung/delete', { id: id });
        result = rs.data;
      } catch (e) {
        if (e.response?.data?.message_detail) {
          this.$root.toastError(e.response.data.message_detail);
        }
      }
      return result;
    },

    enableControls: function (args) {
      for (const key in this.controls) {
        if (this.controls.hasOwnProperty(key)) {
          this.controls[key].enabled = args;
        }
      }

      this.buttons.btnNhapMoi.enabled = !args;
      this.buttons.btnGhiLai.enabled = args;
      this.buttons.btnHuy.enabled = this.buttons.btnGhiLai.enabled;
      this.buttons.btnXoa.enabled = !args;
      this.buttons.btnImport.enabled = !args;
      this.buttons.btnExport.enabled = !args;

      if (this.currentItem.id > 0)
        this.buttons.btnSua.enabled = !this.buttons.btnGhiLai.enabled;
    },
    setCurrentItem: async function (item) {
      this.currentItem.id = item.LOAIOCUNG_ID;
      this.currentItem.ten = item.TEN;
      this.currentItem.ghiChu = item.GHICHU;
      this.currentItem.suDung = item.SUDUNG;
    },
    setCurrentItemDefault: function () {
      this.setCurrentItem({
        LOAIOCUNG_ID: 0,
        TEN: '',
        GHICHU: '',
        SUDUNG: 1,
      });
    },
    validateControls: function () {
      let msg = "";
      if (this.controls.txtTen.invalid) {
        msg = "Tên không được để trống.";
      }
      if (msg != "") this.$root.toastError(msg);
      return msg == "";
    },

    // Phần sự kiện Row Clicked
    grdItems_RowSelected: async function (args) {
      try {
        this.$root.showLoading(true);
        this.setCurrentItem(args.data);
      } finally {
        this.$root.showLoading(false);
      }

    },
    grdItems_RowDeselected: async function () {
      let items = this.grdItems.getSelectedRecords();
      if (!(items == null || items.length == 0)) {
        try {
          this.$root.showLoading(true);
          this.setCurrentItem(items[0]);
        } finally {
          this.$root.showLoading(false);
        }
      } else {
        this.setCurrentItemDefault();
      }
    },
    onSelectRowCheckbox(itemsCheck) {
      this.dsSelected = itemsCheck
    },

    // Phần sự kiện Btn
    btnNhapMoi_Click: async function () {
      this.setCurrentItemDefault();
      this.enableControls(true);
    },
    btnGhiLai_Click: async function () {
      if (this.validateControls()) {
        let flag = await this.$confirm(
          "Bạn có muốn lưu thông tin này không ?",
          "Xác nhận",
          {
            confirmButtonText: "Có",
            cancelButtonText: "Không",
            type: "info",
            dangerouslyUseHTMLString: true,
          }
        )
          .then((s) => true)
          .catch((e) => {
            return false;
          });
        if (flag) {
          try {
            this.$root.showLoading(true);
            let rs = await this.upsertLoaiOCung(this.currentItem);
            if (rs) {
              if (this.currentItem.id === 0) {
                this.$root.toastSuccess("Thêm Loại ổ cứng thành công");
              }
              else {
                this.$root.toastSuccess("Cập nhật Loại ổ cứng thành công");
              }
            }
            await this.refreshListWithText();
            this.enableControls(!rs);
          } finally {
            this.$root.showLoading(false);
          }
        }
      }
    },
    btnSua_Click: async function () {
      this.enableControls(true);
    },
    btnXoa_Click: async function () {
      if (this.dsSelected.length <= 0) {
        this.$toast.error('Chưa chọn danh mục cần xoá.')
        return
      }
      let items = this.grdItems.getSelectedRecords();
      let msg = `Bạn có muốn xóa dữ liệu không?`
      let flag = await this.$confirm(msg, "Xác nhận",
        {
          confirmButtonText: "Đồng ý",
          cancelButtonText: "Không đồng ý",
          type: "warning",
          dangerouslyUseHTMLString: true,
        }
      )
        .then((s) => true)
        .catch((e) => {
          return false;
        });
      if (flag) {
        try {
          this.$root.showLoading(true)
          let promises = []
          for (let item of this.dsSelected) {
            promises.push(await this.deleteLoaiOCungItem(item.LOAIOCUNG_ID))
          }

          if ((await Promise.all(promises)).some(x => x == false)) {
            this.$toast.error(`Xoá không thành công`)
            return
          } else {
            this.$toast.success(`Đã xoá thành công`)
          }
          this.loaiOCungItemList = await this.getLoaiOCungItems()
          this.enableControls(false)
        } finally {
          this.$root.showLoading(false);
        }
      }
    },
    btnHuy_Click: async function () {
      let items = this.grdItems.getSelectedRecords();
      let item = null;
      if (!(items == null || items.length == 0)) {
        if (this.currentItem.id == 0) {
          item = items[0];
        }
        else {
          item = items.find(x => x.LOAIOCUNG_ID == this.currentItem.id);
        }
      }
      if (item != null) {
        console.log('item', item);
        this.setCurrentItem(item);
      } else {
        this.setCurrentItemDefault();
      }
      this.enableControls(false);
    },
    btnImport_Click: async function() {
      try {
        const result = await this.$refs.modalImportExcel.showModal();

        if (result.success && result.data && result.data.length > 0) {
          // Xác nhận import
          const confirmFlag = await this.$confirm(
            `Bạn có chắc muốn import ${result.data.length} loại ổ cứng từ file Excel?`,
            "Xác nhận import",
            {
              confirmButtonText: "Có",
              cancelButtonText: "Không",
              type: "warning",
              dangerouslyUseHTMLString: true,
            }
          ).then((s) => true).catch((e) => false);

          if (confirmFlag) {
            await this.processImportData(result.data);
          }
        }
      } catch (error) {
        console.error('Lỗi khi import Excel:', error);
        this.$root.toastError('Có lỗi khi import Excel: ' + error.message);
      }
    },


    // method để gọi bulk API
    bulkCreateItems: async function (items) {
      let result = false;
      try {
        let rs = await this.$root.context.post('/web-ecms/danhmuc-loai-ocung/bulk-create', items);
        result = rs.data;
        if (result.successCount > 0) {
          this.$root.toastSuccess(`Import thành công ${result.successCount} loại ổ cứng.`);
        }
        if (result.failedCount > 0) {
          this.$root.toastError(`Bỏ qua ${result.failedCount} loại ổ cứng.`);
        }
      } catch(e) {
        if(!(e.response == null || e.response == undefined)){
          if(!(e.response.data == null || e.response.data == undefined)){
            this.$root.toastError(e.response.data.message_detail);
          }
        }
      }
      return result;
    },
    // processLopKNExcelData(dataRows) {
    //   return dataRows.map((row, index) => {
    //     return {
    //       ten: row[0] || '',
    //       ghiChu: row[1] || '',
    //       suDung: row[2] === 1 ? 1 : 0, // Mặc định là 0 nếu không đúng định dạng
    //     };
    //   })
    // },
    processLopKNExcelData(dataRows) {
      if (!dataRows || dataRows.length === 0) return [];

      console.log('=== DEBUG IMPORT EXCEL ===');
      console.log('Tổng dòng:', dataRows.length);
      dataRows.forEach((r, i) => console.log(`Raw dòng ${i + 1}:`, r));

      let startIndex = 0;

      // Tự động phát hiện có header hay không
      const firstRow = dataRows[0];
      const firstCell = firstRow && firstRow[0] ? firstRow[0].toString().trim() : '';

      // Nếu dòng đầu có chứa từ khóa điển hình của header → bỏ qua nó
      const hasHeader = /tên.*loại.*ổ.*cứng|tên.*loại|loại.*ổ.*cứng|hiệu.*lực.*\(.*\)/i.test(firstCell);

      if (hasHeader) {
        console.log('Phát hiện có header → bỏ qua dòng 1');
        startIndex = 1;
      } else {
        console.log('Không có header → lấy từ dòng đầu tiên');
        startIndex = 0;
      }

      return dataRows
        .slice(startIndex)
        .map((row, idx) => {
          if (!row || row.length < 1 || !row[0]) return null;

          const ten = (row[0] || '').toString().trim();
          if (ten === '') return null;

          const ghiChu = row[1] !== undefined ? (row[1] || '').toString().trim() : '';
          const suDungRaw = row[2];

          let suDung = 1;
          if (suDungRaw !== undefined && suDungRaw !== null && suDungRaw !== '') {
            if (typeof suDungRaw === 'string') {
              const normalized = suDungRaw.toLowerCase()
                .normalize('NFD')
                .replace(/[\u0300-\u036f]/g, '')
                .trim();
              suDung = (normalized.includes('khong') || normalized.includes('ko') || normalized.includes('0')) ? 0 : 1;
            } else {
              suDung = Number(suDungRaw) === 1 ? 1 : 0;
            }
          }

          const result = {
            ten,
            ghiChu: ghiChu || null,
            suDung,
            suDungText: suDung === 1 ? 'Hiệu lực' : 'Không hiệu lực'
          };

          console.log(`Record ${idx + 1} (sau khi bỏ header):`, result);
          return result;
        })
        .filter(item => item !== null);
    },
    // Hàm xử lý dữ liệu Excel
     processImportData: async function (importData) {
      try {
        this.$root.showLoading(true);
        let successCount = 0;
        let errorCount = 0;
        const errors = [];
        const validItems = [];

        for (let i = 0; i < importData.length; i++) {
          const item = importData[i];

          try {
            let pSuDung = 1; // Default value
            if (item.suDung !== undefined && item.suDung !== null) {
              // Nếu là string, convert sang number
              if (typeof item.suDung === 'string') {
                pSuDung = item.suDung.toLowerCase() === 'hiệu lực' ? 1 : 0;
              } else {
                // Nếu là number, sử dụng trực tiếp
                pSuDung = item.suDung ? 1 : 0;
              }
            }

            // Chuẩn bị dữ liệu theo format API bulk-create
            const importItem = {
              ten: item.ten.trim(),
              ghiChu: item.ghiChu || null,
              suDung: pSuDung,
            };

            validItems.push(importItem);

          } catch (error) {
            errorCount++;
            errors.push(`Dòng ${i + 1}: ${error.message}`);
          }
        }

        // Nếu có dữ liệu hợp lệ, gọi bulk API
        if (validItems.length > 0) {
          try {
            const result = await this.bulkCreateItems(validItems);
            if (result) {
              successCount = validItems.length;
            } else {
              errorCount += validItems.length;
              errors.push(`Lỗi khi lưu dữ liệu hàng loạt`);
            }
          } catch (error) {
            errorCount += validItems.length;
            errors.push(`Lỗi khi gọi API bulk-create: ${error.message}`);
          }
        }

        // Reload data sau khi import
        await this.refreshListWithText();
      } finally {
        this.$root.showLoading(false);
      }
    },
    async handleCheckData(checkDataInfo) {
      try {
        // Chuẩn bị dữ liệu theo format API validate-batch
        const validateData = checkDataInfo.data.map(item => ({
          ten: item.ten.trim(),
          ghiChu: item.ghiChu || null,
          suDung: item.suDung || 1,
        }));

        // Gọi API validate-batch
        const response = await this.$root.context.post('/web-ecms/danhmuc-loai-ocung/validate-batch', validateData);
        if (response.data) {
          const result = response.data;

          if (result.isValid) {
            this.importConfig.checkDataResult = false; // Không có lỗi -> enable button "Ghi lại"
            this.$root.toastSuccess('Dữ liệu hợp lệ!');
          } else {
            this.importConfig.checkDataResult = true; // Có lỗi -> disable button "Ghi lại"
            const errorMessage = 'Có tổng ' + result.errorCount + ' dòng dữ liệu lỗi. \n' + result.errorMessage;
            this.$root.toastError(errorMessage);
            // emit cho modalImportExcel để hiển thị lỗi lên grid
            this.$refs.modalImportExcel.handleCheckDataResult({
              isValid: false,
              errors: result.errorMessage,
            })
          }
        } else {
          this.importConfig.checkDataResult = true; // Có lỗi -> disable button "Ghi lại"
          this.$root.toastError('Lỗi khi nhận phản hồi từ server');
        }
      } catch (error) {
        this.importConfig.checkDataResult = true; // Có lỗi -> disable button "Ghi lại"
        console.error('Lỗi khi validate batch:', error);

        if (error.response && error.response.data && error.response.data.message_detail) {
          this.$root.toastError('Lỗi khi kiểm tra dữ liệu: ' + error.response.data.message_detail);
        } else {
          this.$root.toastError('Lỗi khi kiểm tra dữ liệu: ' + error.message);
        }
      }
    },
    formatLopKNCellValue(value, fieldName = null) {
      if (value === null || value === undefined || value === '') return '';

      // Chỉ format cho trường hiệu lực (suDung)
      if (fieldName === 'suDung' && typeof value === 'number' && (value === 1 || value === 0)) {
        return value === 1 ? 'Hiệu lực' : 'Không hiệu lực';
      }
      return value;
    },

    btnExport_Click: async function () {
      // this.$refs.gridItems.excelExport({
      //   fileName: 'DanhMucLopKetNoi.xlsx'
      // })
      try {
        if (this.loaiOCungItemList.length === 0) {
          this.$toast.warning("Dữ liệu trống!");
          return;
        }

        let data = this.loaiOCungItemList.map(item => ({
          'ID Loại Ổ cứng': item.LOAIOCUNG_ID,
          'Tên Loại Ổ cứng': item.TEN,
          'Ghi chú': item.GHICHU,
          'Hiệu Lực': item.SUDUNG === 1 ? 'Hiệu lực' : 'Không hiệu lực',
        }));

        let worksheet = XLSX.utils.json_to_sheet(data);
        worksheet['!cols'] = [
          { wch: 18 }, { wch: 25 }, { wch: 30 }, { wch: 12 }
        ];

        let workbook = XLSX.utils.book_new();
        XLSX.utils.book_append_sheet(workbook, worksheet, "Danh sách loại ổ cứng");
        XLSX.writeFile(workbook, `Bao_cao_loai_ocung_${new Date().toISOString().slice(0,10)}.xlsx`);

        this.$toast.success("Xuất file Excel thành công!");
      } catch (ex) {
        this.$toast.error("Có lỗi: " + ex.message);
      }
    }

  },
};
</script>
<style>
.check-action {
  top: 3px;
}

.e-rowcell .check-action input[type="checkbox"]:checked:disabled+.name:before {
  background: #fff;
  border-color: #0176ff;
  color: #0176ff;
}

.e-rowcell .check-action input[type="checkbox"]:disabled+.name:before {
  background: #fff;
  border-color: #0176ff;
}
</style>
