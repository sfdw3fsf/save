<template src="./index.html"></template>
<style src="./styles.scss" scoped></style>

<script>
import api from './api';
import * as XLSX from 'xlsx';
import ModalImportExcel from '../components/Modals/ModalImportExcel';
import ModalOCamInfo from './components/ModalOCamInfo';

export default {
  components: {
    ModalImportExcel,
    ModalOCamInfo
  },
  data: function () {
    return {
      dataItems: [],
      dsSelected: [],
      currentItem: {
        thanhNguonId: null,
        loaiTnId: null,
        rackId: null,
        ten: "",
        moTa: "",
        trangThaiId: null,
        viTriLd: null,
        kieuLd: null,
        ghiChu: null,
        suDung: 1,
      },
      controls: {
        txtThanhNguonId: {
          enabled: false,
          invalid: false,
        },
        selectLoaiTn: {
          enabled: false,
          invalid: false,
        },
        selectRack: {
          enabled: false,
          invalid: false,
        },
        txtTen: {
          enabled: false,
          invalid: false,
        },
        txtMoTa: {
          enabled: false,
        },
        selectTrangThai: {
          enabled: false,
          invalid: false,
        },
        selectViTriLd: {
          enabled: false,
          invalid: false,
        },
        selectKieuLd: {
          enabled: false,
          invalid: false,
        },
        txtGhiChu: {
          enabled: false,
        },
        checkBoxSuDung: {
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
        btnHuyBo: {
          enabled: false,
        },
        btnSua: {
          enabled: false,
        },
        btnXoa: {
          enabled: false,
        },
      },
      loaiThanhNguonOptions: [], 
      kieuLapDatOptions: [
        { value: 1, text: "Dọc trái" },
        { value: 2, text: "Dọc phải" },
        { value: 3, text: "Ngang trên" },
        { value: 4, text: "Ngang dưới" }
      ],
      trangThaiOptions: [
        { value: 1, text: "On" },
        { value: 2, text: "Off" }
      ],
      rackOptions: [], 
      viTriUOptions: [],
      importConfig: {
        title: 'Import dữ liệu',
        templateFileName: 'Template_Import_ThanhNguonTrenRack.xlsx',
        tableColumns: [
          { key: 'loaiTnId', label: 'Loại thanh nguồn' },
          { key: 'ten', label: 'Tên thanh nguồn' },
          { key: 'rackId', label: 'ID Rack' },
          { key: 'viTriLd', label: 'Vị trí U lắp thanh nguồn ngang' },
          { key: 'kieuLd', label: 'Kiểu lắp đặt (1: Dọc trái, 2: Dọc phải, 3: Ngang trên, 4: Ngang dưới)' },
          { key: 'trangThaiId', label: 'Trạng thái (1: On, 2: Off)' },
          { key: 'suDung', label: 'Hiệu lực (1: Hiệu lực, 0: Không hiệu lực)' },
          { key: 'ghiChu', label: 'Ghi chú' }
        ],
        gridColumns: [
          { key: 'loaiTnId', label: 'Loại thanh nguồn' },
          { key: 'ten', label: 'Tên thanh nguồn' },
          { key: 'rackId', label: 'ID Rack' },
          { key: 'viTriLd', label: 'Vị trí U' },
          { key: 'kieuLd', label: 'Kiểu lắp đặt' },
          { key: 'trangThaiId', label: 'Trạng thái' },
          { key: 'suDung', label: 'Hiệu lực' },
          { key: 'ghiChu', label: 'Ghi chú' }
        ],
        validationRules: [
          { field: 'loaiTnId', required: true, message: 'Loại thanh nguồn không được để trống' },
          { field: 'ten', required: true, message: 'Tên thanh nguồn không được để trống' },
          { field: 'ten', maxLength: 100, message: 'Tên thanh nguồn không được vượt quá 100 ký tự' },
          { field: 'rackId', required: true, message: 'ID Rack không được để trống' },
          { field: 'viTriLd', required: true, message: 'Vị trí U không được để trống' },
          { field: 'viTriLd', type: 'number', message: 'Vị trí U phải nhập số' },
          { field: 'kieuLd', required: true, message: 'Kiểu lắp đặt không được để trống' },
          { field: 'kieuLd', type: 'number', enum: [1, 2, 3, 4], message: 'Kiểu lắp đặt chỉ nhận số 1, 2, 3 hoặc 4' },
          { field: 'trangThaiId', required: true, message: 'Trạng thái không được để trống' },
          { field: 'trangThaiId', type: 'number', enum: [1, 2], message: 'Trạng thái chỉ nhận số 1 hoặc 2' },
          { field: 'suDung', type: 'number', enum: [1, 0], message: 'Hiệu lực chỉ nhận số 0 hoặc 1 hoặc để trống' },
          { field: 'ghiChu', maxLength: 200, message: 'Ghi chú không được vượt quá 200 ký tự' }
        ],
        checkDataResult: true,
      }
    };
  },
  computed: {
    gridItems: function () {
      return this.$refs.gridItems;
    },
    loaiThanhNguonOptionsComputed: function() {
      return this.loaiThanhNguonOptions.map(item => ({
        id: item.value,
        text: item.text
      }));
    },
    kieuLapDatOptionsComputed: function() {
      return this.kieuLapDatOptions.map(item => ({
        id: item.value,
        text: item.text
      }));
    },
    trangThaiOptionsComputed: function() {
      return this.trangThaiOptions.map(item => ({
        id: item.value,
        text: item.text
      }));
    },
    rackOptionsComputed: function() {
      return this.rackOptions.map(item => ({
        id: item.value,
        text: item.text
      }));
    },
    viTriUOptionsComputed: function() {
      return this.viTriUOptions.map(item => ({
        id: item.value,
        text: item.text
      }));
    }
  },
  watch: {
    "currentItem.thanhNguonId": function (value) {
      this.buttons.btnSua.enabled = value > 0;
      this.buttons.btnXoa.enabled = value > 0;
      
      if (value > 0 && this.buttons.btnGhiLai.enabled) {
        this.checkEditLoaiTNPermissionAndUpdate(value);
      }
    },
    "currentItem.loaiTnId": function (value) {
      this.controls.selectLoaiTn.invalid = !value;
    },
    "currentItem.rackId": async function (value) {
      this.controls.selectRack.invalid = !value;
      
      if (value) {
        await this.loadViTriUOptionsByRackId(value);
        if (this.viTriUOptions.length === 0) {
          this.currentItem.viTriLd = null;
        }
      } else {
        this.viTriUOptions = [];
        this.currentItem.viTriLd = null;
      }
    },
    "currentItem.ten": function (value) {
      this.controls.txtTen.invalid = value == "";
    },
    "currentItem.trangThaiId": function (value) {
      this.controls.selectTrangThai.invalid = !value;
    },
    "currentItem.viTriLd": function (value) {
      this.controls.selectViTriLd.invalid = !value;
    },
    "currentItem.kieuLd": function (value) {
      this.controls.selectKieuLd.invalid = !value;
    },
  },
  created: async function () {
    try {
      this.$root.showLoading(true);
      const [dataItems, loaiThanhNguonData, rackData] = await Promise.all([
        this.getItems(),
        this.getLoaiThanhNguonOptions(),
        this.getRackOptions() 
      ]);
      this.dataItems = dataItems;
      this.loaiThanhNguonOptions = loaiThanhNguonData;
      this.rackOptions = rackData;
    } finally {
      this.$root.showLoading(false);
    }
  },
  methods: {
    getColumnHoatDongTemplate: function () {
      return function () {
        return {
          template: {
            template: `<button style="color: #0176FF; font-weight: 600; background: unset; border: unset;" @click="showOCamInfo()"><span class="icon one-file-plus"></span> Danh sách ổ cắm</button>`,
            methods: {
              showOCamInfo: function () {
                const thanhNguonId = this.data.THANHNGUON_ID;
                const thanhNguonTen = this.data.TEN;
                this.$parent.$parent.$parent.showOCamInfoModal(thanhNguonId, thanhNguonTen);
              },
            },
          },
        };
      };
    },
    getItems: async function () {
      let result = [];
      try {
        let rs = await api.getDanhSachThanhNguonTrenRack(this.$root.context);
        result = rs.data;
        if(result){
          this.setCurrentItemDefault();
        }
      } catch (error) {
        console.error('Error getting danh sach thanh nguon tren rack:', error);
      }
      return result;
    },
    updateItem: async function (args) {
      let result = false;
      try {
        let rs = await api.capNhatThanhNguonTrenRack(this.$root.context, args);
        result = rs.data.item === 1 ? true : false;
      } catch(e) {
        if(!(e.response == null || e.response == undefined)){
          if(!(e.response.data == null || e.response.data == undefined)){
            this.$root.toastError(e.response.data.message_detail);
          }
        }
      }
      return result;
    },
    onSelectRowCheckbox(itemsCheck) {
      this.dsSelected = itemsCheck
    },
    deleteItem: async function (id) {
      let result = false;
      try {
        let rs = await api.xoaThanhNguonTrenRack(this.$root.context, id);
        result = rs.data.item === 1 ? true : false;
      } catch(e) {
        if(!(e.response == null || e.response == undefined)){
          if(!(e.response.data == null || e.response.data == undefined)){
            this.$root.toastError(e.response.data.message_detail);
          }
        }
      }
      return result;
    },
    checkEditLoaiTNPermission: async function (thanhNguonId) {
      let result = false;
      try {
        let rs = await api.checkEditLoaiTN(this.$root.context, thanhNguonId);
        result = rs.data.item === 1 ? true : false;
      } catch(e) {
        if(!(e.response == null || e.response == undefined)){
          if(!(e.response.data == null || e.response.data == undefined)){
            this.$root.toastError(e.response.data.message_detail);
          }
        }
      }
      return result;
    },
    enableControls: function (args, disableLoaiTn = false) {
      this.controls.selectLoaiTn.enabled = args && !disableLoaiTn;
      this.controls.selectRack.enabled = args;
      this.controls.txtTen.enabled = args;
      this.controls.txtMoTa.enabled = args;
      this.controls.selectTrangThai.enabled = args;
      this.controls.selectViTriLd.enabled = args;
      this.controls.selectKieuLd.enabled = args;
      this.controls.txtGhiChu.enabled = args;
      this.controls.checkBoxSuDung.enabled = args;

      this.buttons.btnGhiLai.enabled = args;
      this.buttons.btnNhapMoi.enabled = !args;
      this.buttons.btnHuyBo.enabled = this.buttons.btnGhiLai.enabled;
      if (this.currentItem.thanhNguonId > 0)
        this.buttons.btnSua.enabled = !this.buttons.btnGhiLai.enabled;
    },
    setCurrentItem: async function (item) {
      this.currentItem.thanhNguonId = item.THANHNGUON_ID;
      this.currentItem.loaiTnId = item.LOAITN_ID;
      this.currentItem.rackId = item.RACK_ID;
      this.currentItem.ten = item.TEN;
      this.currentItem.moTa = item.MOTA || "";
      this.currentItem.trangThaiId = item.TRANGTHAI_ID;
      this.currentItem.viTriLd = item.VITRI_LD;
      this.currentItem.kieuLd = item.KIEU_LD;
      this.currentItem.ghiChu = item.GHICHU || "";
      this.currentItem.suDung = (item.SUDUNG !== undefined && item.SUDUNG !== null) ? item.SUDUNG : 
                                (item.SU_DUNG !== undefined && item.SU_DUNG !== null) ? item.SU_DUNG : 1;
      
      if (item.RACK_ID) {
        await this.loadViTriUOptionsByRackId(item.RACK_ID);
      }
    },
    setCurrentItemDefault: function () {
      this.setCurrentItem({
        THANHNGUON_ID: null,
        LOAITN_ID: null,
        RACK_ID: null,
        TEN: "",
        MOTA: "",
        TRANGTHAI_ID: null,
        VITRI_LD: null,
        KIEU_LD: null,
        GHICHU: "",
        SUDUNG: 1,
      });
    },
    validateControls: function () {
      let msg = "";
      if (this.controls.selectLoaiTn.invalid) {
        msg = "Loại thanh nguồn không được để trống.";
      }
      if (this.controls.selectRack.invalid) {
        msg = "ID Rack không được để trống.";
      }
      if (this.controls.txtTen.invalid) {
        msg = "Tên thanh nguồn không được để trống.";
      }
      if (this.controls.selectTrangThai.invalid) {
        msg = "Trạng thái không được để trống.";
      }
      if (this.controls.selectViTriLd.invalid) {
        msg = "Vị trí U không được để trống.";
      }
      if (this.controls.selectKieuLd.invalid) {
        msg = "Kiểu lắp đặt không được để trống.";
      }
      if (this.currentItem.rackId && this.viTriUOptions.length > 0 && !this.currentItem.viTriLd) {
        msg = "Vị trí U không được để trống.";
      }
      if (msg != "") this.$root.toastError(msg);
      return msg == "";
    },
    gridItems_RowSelected: async function (args) {
      this.setCurrentItem(args.data);
    },
    gridItems_RowDeselected: async function (args) {
      let items = this.gridItems.getSelectedRecords();
      if (!(items == null || items.length == 0)) {
        this.setCurrentItem(items[0]);
      } else {
        this.setCurrentItemDefault();
      }
    },
    btnGhiLai_Click: async function (args) {
      if (this.validateControls()) {
        let flag = await this.$confirm(
          "Có chắc bạn muốn lưu lại thanh nguồn trên rack đang chọn?",
          "Xác nhận thực hiện",
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
            let rs = await this.updateItem(this.currentItem);
            if (rs) {             
              if (this.currentItem.thanhNguonId === 0) {
                this.$root.toastSuccess("Thêm thanh nguồn trên rack thành công");
              }
              else{
                this.$root.toastSuccess("Cập nhật thanh nguồn trên rack thành công");
              }
              
              this.dataItems = await this.getItems();
              
              if (this.currentItem.thanhNguonId === 0) {
                if (this.dataItems && this.dataItems.length > 0) {
                  const latestItem = this.dataItems.reduce((latest, current) => {
                    return (current.THANHNGUON_ID > latest.THANHNGUON_ID) ? current : latest;
                  });
                  this.setCurrentItem(latestItem);
                }
              } else {
                const updatedItem = this.dataItems.find(item => item.THANHNGUON_ID === this.currentItem.thanhNguonId);
                if (updatedItem) {
                  this.setCurrentItem(updatedItem);
                }
              }
              
            }
            else{
              if (this.currentItem.thanhNguonId === 0) {
                this.$root.toastError("Thêm thanh nguồn trên rack thất bại");
              }
              else{
                this.$root.toastError("Cập nhật thanh nguồn trên rack thất bại");
              }
            }
            this.enableControls(!rs);
          } finally {
            this.$root.showLoading(false);
          }
        }
      }
    },
    btnNhapMoi_Click: async function (args) {
      this.enableControls(true);
      this.setCurrentItemDefault();
    },
    btnHuyBo_Click: async function (args) {
      let item = null;
      if (this.currentItem.thanhNguonId && this.currentItem.thanhNguonId > 0) {
        item = this.dataItems.find(x => x.THANHNGUON_ID == this.currentItem.thanhNguonId);
      } else {
        let items = this.gridItems.getSelectedRecords();
        if (items && items.length > 0) {
          item = items[0];
        }
      }
      if (item != null) {
        await this.setCurrentItem(item);
      } else {
        this.setCurrentItemDefault();
      }
      this.enableControls(false);
    },
    btnSua_Click: async function (args) {
      try {
        this.$root.showLoading(true);
        const canEditLoaiTn = await this.checkEditLoaiTNPermission(this.currentItem.thanhNguonId);
        
        this.enableControls(true, !canEditLoaiTn);
        if (!canEditLoaiTn) {
          this.$root.toastWarning("Thanh nguồn này có các ổ cắm đã được sử dụng nên bạn không chỉnh sửa được loại thanh nguồn.");
        }
      } catch (error) {
        console.error(error);
        this.$root.toastError('Có lỗi khi kiểm tra quyền chỉnh sửa.');
      } finally {
        this.$root.showLoading(false);
      }
    },
    btnXoa_Click: async function (args) {
      if (this.dsSelected.length <= 0) {
        this.$toast.error('Chưa chọn danh mục cần xoá.')
        return
      }
      let flag = await this.$confirm(
        "Có chắc bạn muốn xóa thanh nguồn trên rack đang chọn?",
        "Xác nhận thực hiện",
        {
          confirmButtonText: "Có",
          cancelButtonText: "Không",
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
            promises.push(await this.deleteItem(item.THANHNGUON_ID))
          }
          let res = await Promise.all(promises)
          let ok = res.filter((x) => x === true)

          if (ok.length > 0) {
            this.$toast.success(`Đã xoá thành công ${ok.length} / ${res.length} mục`)
          }
          this.dataItems = await this.getItems()
          this.enableControls(false)
        } finally {
          this.$root.showLoading(false);
        }
      }
    },
    btnXuatExcel_Click() {
      this.$refs.gridItems.excelExport({
        fileName: 'DanhMucThanhNguonTrenRack.xlsx'
      })
    },
    processThanhNguonTrenRackExcelData(dataRows) {
      return dataRows.map((row, index) => {
        return {
          loaiTnId: row[0],
          ten: row[1],
          rackId: row[2],
          viTriLd: row[3],
          kieuLd: row[4],
          trangThaiId: row[5],
          suDung: row[6],
          ghiChu: row[7] || '',
          moTa: ''
        };
      })
    },
    formatThanhNguonTrenRackCellValue(value, fieldName = null) {
      if (value === null || value === undefined || value === '') return '';
      if (fieldName === 'suDung' && typeof value === 'number' && (value === 1 || value === 0)) {
        return value === 1 ? 'Hiệu lực' : 'Không hiệu lực';
      }
      
      if (fieldName === 'trangThaiId' && typeof value === 'number' && (value === 1 || value === 2)) {
        return value === 1 ? 'On' : 'Off';
      }
      
      if (fieldName === 'kieuLd' && typeof value === 'number' && [1, 2, 3, 4].includes(value)) {
        const options = { 1: 'Dọc trái', 2: 'Dọc phải', 3: 'Ngang trên', 4: 'Ngang dưới' };
        return options[value];
      }
      
      if (fieldName === 'ghiChu') {
        return value || '';
      }
      
      return value;
    },
    btnImportExcel_Click: async function () {
      try {
        const result = await this.$refs.modalImportExcel.showModal();
        
        if (result.success && result.data && result.data.length > 0) {
          const confirmFlag = await this.$confirm(
            `Bạn có chắc muốn import ${result.data.length} thanh nguồn trên rack từ file Excel?`,
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
    bulkCreateItems: async function (items) {
      let result = false;
      try {
        let rs = await api.bulkCreateThanhNguonTrenRack(this.$root.context, items);
        result = rs.data;
        if (result.successCount > 0) {
          this.$root.toastSuccess(`Import thành công ${result.successCount} thanh nguồn trên rack.`);
        }
        if (result.failedCount > 0) {
          this.$root.toastError(`Bỏ qua ${result.failedCount} thanh nguồn trên rack.`);
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
    processImportItemData(item) {
      let pSuDung = 1;
      if (item.suDung !== undefined && item.suDung !== null) {
        if (typeof item.suDung === 'string') {
          pSuDung = item.suDung.toLowerCase() === 'hiệu lực' ? 1 : 0;
        } else {
          pSuDung = item.suDung ? 1 : 0;
        }
      }
      let processedLoaiTnId = item.loaiTnId;
      if (typeof item.loaiTnId === 'string' && isNaN(item.loaiTnId)) {
        const trimmedText = item.loaiTnId.trim().toUpperCase();
        const foundOption = this.loaiThanhNguonOptions.find(option => 
          option.text && option.text.trim().toUpperCase() === trimmedText
        );
        if (foundOption) {
          processedLoaiTnId = foundOption.value;
        } else {
          processedLoaiTnId = 0;
        }
      }

      return {
        thanhNguonId: null,
        loaiTnId: processedLoaiTnId,
        rackId: item.rackId,
        ten: item.ten || '',
        moTa: item.moTa || null,
        trangThaiId: item.trangThaiId,
        viTriLd: item.viTriLd,
        kieuLd: item.kieuLd,
        ghiChu: item.ghiChu || null,
        suDung: pSuDung,
      };
    },

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
            const processedItem = this.processImportItemData(item);
            validItems.push(processedItem);
          } catch (error) {
            errorCount++;
            errors.push(`Dòng ${i + 1}: ${error.message}`);
          }
        }

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

        this.dataItems = await this.getItems();
      } finally {
        this.$root.showLoading(false);
      }
    },

    async handleCheckData(checkDataInfo) {
      try {
        const validateData = checkDataInfo.data.map(item => this.processImportItemData(item));
        const response = await api.validateBatchThanhNguonTrenRack(this.$root.context, validateData);
        if (response.data) {
          const result = response.data;
          
          if (result.isValid) {
            // Không có lỗi -> enable button "Ghi lại"
            this.importConfig.checkDataResult = false;
            this.$root.toastSuccess('Dữ liệu hợp lệ!');
          } else {
            // Có lỗi -> disable button "Ghi lại"
            this.importConfig.checkDataResult = true;
            const errorMessage = 'Có tổng ' + result.errorCount + ' dòng dữ liệu lỗi. \n' + result.errorMessage;
            this.$root.toastError(errorMessage);
            // emit cho modalImportExcel để hiển thị lỗi lên grid
            this.$refs.modalImportExcel.handleCheckDataResult({
              isValid: false,
              errors: result.errorMessage,
            })  
          }
        } else {
          this.importConfig.checkDataResult = true;
          this.$root.toastError('Lỗi khi nhận phản hồi từ server');
        }
      } catch (error) {
        this.importConfig.checkDataResult = true;
        console.error('Lỗi khi validate batch:', error);
        
        if (error.response && error.response.data && error.response.data.message_detail) {
          this.$root.toastError('Lỗi khi kiểm tra dữ liệu: ' + error.response.data.message_detail);
        } else {
          this.$root.toastError('Lỗi khi kiểm tra dữ liệu: ' + error.message);
        }
      }
    },
    showOCamInfoModal: async function (thanhNguonId, thanhNguonTen) {
      try {
        await this.$refs.modalOCamInfo.showModal(thanhNguonId, thanhNguonTen);
      } catch (error) {
        console.error('Lỗi khi hiển thị modal ổ cắm:', error);
        this.$root.toastError('Có lỗi khi hiển thị thông tin ổ cắm');
      }
    },
    getLoaiThanhNguonOptions: async function () {
      let result = [];
      try {
        let rs = await api.getDanhSachLoaiThanhNguon(this.$root.context);
        if (rs.data && Array.isArray(rs.data)) {
          const filteredData = rs.data.filter(item => item.SUDUNG === 1);
          result = filteredData.map(item => ({
            value: item.ID || item.id,
            text: item.TEN || item.ten
          }));
        }
      } catch (error) {
        console.error('Error getting danh sach loai thanh nguon:', error);
      }
      return result;
    },
    getRackOptions: async function () {
      let result = [];
      try {
        let rs = await api.getDanhSachRack(this.$root.context);
        console.log('rs nè he: ', rs);
        if (rs.data && Array.isArray(rs.data)) {
          const filteredData = rs.data.filter(item => item.SUDUNG === 1);
          result = filteredData.map(item => ({
            value: item.RACK_ID,
            text: item.RACK_ID
          }));
        }
      } catch (error) {
        console.error('Error getting danh sach rack:', error);
      }
      return result;
    },
    loadViTriUOptionsByRackId: async function (rackId) {
      try {
        let rs = await api.getDanhMucU(this.$root.context, {
          loaiDanhMuc: "THONGTIN-U",
          thamSo1: rackId
        });
        
        if (rs.data && Array.isArray(rs.data)) {
          const filteredData = rs.data.filter(item => item.SUDUNG === 1);
          this.viTriUOptions = filteredData.map(item => ({
            value: item.VITRI,
            text: item.VITRI
          }));
        } else {
          this.viTriUOptions = [];
        }
      } catch (error) {
        console.error('Error getting danh muc U theo rack:', error);
        this.viTriUOptions = [];
      }
    },
    checkEditLoaiTNPermissionAndUpdate: async function (thanhNguonId) {
      try {
        const canEditLoaiTn = await this.checkEditLoaiTNPermission(thanhNguonId);
        this.controls.selectLoaiTn.enabled = canEditLoaiTn;
        
        if (!canEditLoaiTn) {
          this.$root.toastWarning("Thanh nguồn này có các ổ cắm đã được sử dụng nên bạn không chỉnh sửa được loại thanh nguồn.");
        }
      } catch (error) {
        console.error(error);
        this.controls.selectLoaiTn.enabled = true;
      }
    },
  },
};
</script>
<style>
.check-action {
  top: 3px;
}

.e-rowcell .check-action input[type="checkbox"]:checked:disabled + .name:before {
  background: #fff;
  border-color: #0176ff;
  color: #0176ff;
}

.e-rowcell .check-action input[type="checkbox"]:disabled + .name:before {
  background: #fff;
  border-color: #0176ff;
}

/* CSS cho validation error */
.error-input {
  border: 1px solid #f57f6c !important;
  animation-name: bounce;
  animation-duration: 0.5s;
  animation-delay: 0.25s;
}

@keyframes bounce {
  0% {
    transform: translateX(0px);
    timing-function: ease-in;
  }
  37% {
    transform: translateX(5px);
    timing-function: ease-out;
  }
  55% {
    transform: translateX(-5px);
    timing-function: ease-in;
  }
  73% {
    transform: translateX(4px);
    timing-function: ease-out;
  }
  82% {
    transform: translateX(-4px);
    timing-function: ease-in;
  }
  91% {
    transform: translateX(2px);
    timing-function: ease-out;
  }
  96% {
    transform: translateX(-2px);
    timing-function: ease-in;
  }
  100% {
    transform: translateX(0px);
    timing-function: ease-in;
  }
}
</style>
