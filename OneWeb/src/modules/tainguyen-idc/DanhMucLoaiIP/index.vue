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
        id: 0,
        ten: '',
        phanLoai: '',
        ghiChu: '',
        hieuLuc: 1,
      },
      dsSelected: [],
      ipItemList: [],

      controls: {
        txtID: {
          enabled: false,
        },
        chkTen: {
          enabled: false,

        },
        txtGhiChu: {
          enabled: false,
        },
        chkHieuLuc: {
          enabled: false,
        },
        chkPhanLoai: {
          enabled: false,
        }
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
        templateFileName: 'Template_Import_LoaiIP.xlsx',
        tableColumns: [
          { key: 'ten', label: 'Tên loại IP (IP v4, IP v6)' },
          { key: 'phanLoai', label: 'Phân loại (Internet, Intranet)' },
          { key: 'ghiChu', label: 'Ghi chú' },
          { key: 'hieuLuc', label: 'Hiệu lực (1: Hiệu lực, 0: Không hiệu lực)' }
        ],
        // Header cho data grid preview (nếu không truyền thì sẽ dùng tableColumns)
        gridColumns: [
          { key: 'ten', label: 'Tên loại IP' },
          { key: 'phanLoai', label: 'Phân loại' },
          { key: 'ghiChu', label: 'Ghi chú' },
          { key: 'hieuLuc', label: 'Hiệu lực' }
        ],
        validationRules: [
          { field: 'ten', required: true, message: 'Tên loại IP không được để trống' },
          { field: 'ten', enum: ['IP v4', 'IP v6'], message: 'Tên loại IP chỉ được nhập "IP v4" hoặc "IP v6"' },
          { field: 'phanLoai', required: true, message: 'Phân loại không được để trống' },
          { field: 'phanLoai', enum: ['Internet', 'Intranet'], message: 'Phân loại chỉ được nhập "Internet" hoặc "Intranet"' },
          { field: 'ghiChu', maxLength: 500, message: 'Ghi chú không được vượt quá 500 ký tự' },
          { field: 'hieuLuc', type: 'number', message: 'Hiệu lực phải nhập số' },
          { field: 'hieuLuc', type: 'number', enum: [1, 0], message: 'Hiệu lực chỉ nhận số 0 hoặc 1 hoặc để trống' }
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
    // "currentItem.ten": function (value) {
    //   this.controls.selectTen.invalid = value == "";
    // },
  },
  created: async function () {
    try {
      this.$root.showLoading(true);
      this.ipItemList = await this.getLoaiIPItems();

      // if (this.modalItemID > 0) {
      //   let item = this.ipItemList.find(x => x.ID == this.modalItemID);
      //   if (item != null) {
      //     this.setCurrentItem(item);
      //   }
      //   this.$refs.grdItems.selectRow(item);
      // }
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
                return this.$parent.$parent.$parent.getHieuLucText(this.data.HIEU_LUC);
              },
            },
          },
        };
      };
    },

    // Phần gọi các API
    getLoaiIPItems: async function () {
      let result = [];
      try {
        let rs = await this.$root.context.post("/web-ecms/danhmuc-loai-ip/get-danhsach-ip");
        result = rs.data || [];
      } catch (e) {
        if (e.response?.data?.message_detail) {
          this.$root.toastError(e.response.data.message_detail);
        }
      }
      return result;
    },
    upsertLoaiIP: async function (args) {
      let result = false;
      try {
        let rs = await this.$root.context.post("/web-ecms/danhmuc-loai-ip/upsert", {
          id: args.id === 0 ? null : args.id,
          ten: args.ten,
          ghiChu: args.ghiChu,
          hieuLuc: args.hieuLuc,
          moTa: args.moTa,
          phanLoai: args.phanLoai
        });
        result = rs.data;
      } catch (e) {
        if (e.response?.data?.message_detail) {
          this.$root.toastError(e.response.data.message_detail);
        }
      }
      return result;
    },
    deleteLoaiIPItem: async function (id) {
      let result = false;
      try {
        let rs = await this.$root.context.post('/web-ecms/danhmuc-loai-ip/delete', { id: id });
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
      this.currentItem.id = item.ID;
      this.currentItem.ten = item.TEN;
      this.currentItem.ghiChu = item.GHI_CHU;
      this.currentItem.hieuLuc = item.HIEU_LUC;
      this.currentItem.phanLoai = item.PHANLOAI_NAME;


    },
    setCurrentItemDefault: function () {``
      this.setCurrentItem({
        ID: 0,
        TEN: 'IP v4',
        GHI_CHU: '',
        HIEU_LUC: 1,
        PHANLOAI_NAME: 'Internet',
      });
    },

    // validateControls: function () {
    //   let msg = "";
    //   if (this.controls.txtTen.invalid) {
    //     msg = "Tên không được để trống.";
    //   }
    //   if (msg != "") this.$root.toastError(msg);
    //   return msg == "";
    // },

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
      // if (this.validateControls()) {
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
            let rs = await this.upsertLoaiIP(this.currentItem);
            if (rs) {
              if (this.currentItem.id === 0) {
                this.$root.toastSuccess("Thêm IP thành công");
              }
              else {
                this.$root.toastSuccess("Cập nhật IP thành công");
              }
              this.ipItemList = await this.getLoaiIPItems();
            }
            else {
              if (this.currentItem.id === 0) {
                this.$root.toastError("Thêm IP thất bại");
              }
              else {
                this.$root.toastError("Cập nhật IP thất bại");
              }
            }
            this.enableControls(!rs);
          } finally {
            this.$root.showLoading(false);
          }
        }
      //}
    },
    btnSua_Click: async function () {
      this.enableControls(true);
    },
    btnHuy_Click: async function () {
      let items = this.grdItems.getSelectedRecords();
      let item = null;
      if (!(items == null || items.length == 0)) {
        if (this.currentItem.id == 0) {
          item = items[0];
        }
        else {
          item = items.find(x => x.ID == this.currentItem.id);
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
            promises.push(await this.deleteLoaiIPItem(item.ID))
          }

          if ((await Promise.all(promises)).some(x => x == false)) {
            this.$toast.error(`Xoá không thành công`)
            return
          } else {
            this.$toast.success(`Đã xoá thành công`)
          }
          this.ipItemList = await this.getLoaiIPItems()
          this.enableControls(false)
        } finally {
          this.$root.showLoading(false);
        }
      }
    },
    // method để gọi bulk API
    bulkCreateItems: async function (items) {
      let result = false;
      try {
        let rs = await this.$root.context.post('/web-ecms/danhmuc-loai-ip/bulk-create', items);
        result = rs.data;
        if (result.successCount > 0) {
          this.$root.toastSuccess(`Import thành công ${result.successCount} loại IP.`);
        }
        if (result.failedCount > 0) {
          this.$root.toastError(`Bỏ qua ${result.failedCount} loại IP.`);
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
    processLoaiIPExcelData(dataRows) {
      return dataRows.map((row, index) => {
        return {
          ten: row[0] || '',
          phanLoai: row[1] || '',
          ghiChu: row[2] || '',
          hieuLuc: row[3] === 1 ? 1 : 0, // Mặc định là 0 nếu không đúng định dạng
        };
      })
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
              phanLoai: item.phanLoai.trim(),
              ghiChu: item.ghiChu || null,
              hieuLuc: pSuDung,
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
        this.ipItemList = await this.getLoaiIPItems();
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
        const response = await this.$root.context.post('/web-ecms/danhmuc-loai-ip/validate-batch', validateData);
        if (response.data) {
          const result = response.data;
          if (result.valid) {
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
    formatLoaiIPCellValue(value, fieldName = null) {
      if (value === null || value === undefined || value === '') return '';

      // Chỉ format cho trường hiệu lực (hieuLuc)
      if (fieldName === 'hieuLuc' && typeof value === 'number' && (value === 1 || value === 0)) {
        return value === 1 ? 'Hiệu lực' : 'Không hiệu lực';
      }
      return value;
    },
    btnImport_Click: async function () {
      try {
        const result = await this.$refs.modalImportExcel.showModal();

        if (result.success && result.data && result.data.length > 0) {
          // Xác nhận import
          const confirmFlag = await this.$confirm(
            `Bạn có chắc muốn import ${result.data.length} loại ip từ file Excel?`,
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
    btnExport_Click: async function () {
      try {
        if (this.ipItemList == []) {
          this.$toast.warning("Dữ liệu trống!");
        } else {
          let data = []
          this.ipItemList.forEach(item => {
            data.push({
              'ID Loại IP': item.ID,
              'Tên Loại IP': item.TEN,
              'Phân Loại': item.PHANLOAI_NAME,
              'Ghi Chú': item.GHI_CHU,
              'Hiệu lực': item.HIEU_LUC == 1 ? "Hiệu lực" : "Không hiệu lực",
            })
          })

          let worksheet = XLSX.utils.json_to_sheet(data);
          worksheet['!cols'] = [
            { wch: 12 }, { wch: 25 }, { wch: 15 }, { wch: 30 }, { wch: 12 }
          ];
          let workbook = XLSX.utils.book_new()
          XLSX.utils.book_append_sheet(workbook, worksheet, "Danh sách loại IP")
          XLSX.writeFile(workbook, "Báo cáo loại IP.xlsx")
          this.$toast.success("Xuất file Excel thành công!");
        }
      } catch (ex) {
        this.$toast.error("Có lỗi trong quá trình xuất file Excel !" + ex)
      }
    },

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
