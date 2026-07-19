<template src="./index.html"></template>

<script>
import CapPhatRack from './capphat-rack/index.vue';
import ThuHoiRack from './thuhoi-rack/index.vue';
import CapPhatU from './capphat-u/index.vue';
import ThuHoiU from './thuhoi-u/index.vue';
import DataGrid from '@/components/Controls/DataGrid/index.vue';

export default {
  components: {
    CapPhatRack,
    ThuHoiRack,
    CapPhatU,
    ThuHoiU,
    DataGrid,
  },
  data: function () {
    return {
      currentItem: {
        id: 0,
        ten: '',
        ghiChu: '',
        hieuLuc: 1,
      },
      selectedLayout: 1,
      layoutOptions: [],
      idcOptions: [],
      formData: {
        field1: '',
        field2: '',
        field3: '',
        field4: '',
        field5: '',
        field6: '',
        field7: '',
        field8: '',
        field9: '',
        field10: '',
        field11: '',
        field12: ''
      },
      dataGrid1Items: [
        { id: 1, col1: 'Data 1-1', col2: 'Data 1-2', col3: 'Data 1-3', col4: 'Data 1-4', col5: 'Data 1-5', col6: 'Data 1-6', col7: 'Data 1-7', col8: 'Data 1-8', col9: 'Data 1-9' },
        { id: 2, col1: 'Data 2-1', col2: 'Data 2-2', col3: 'Data 2-3', col4: 'Data 2-4', col5: 'Data 2-5', col6: 'Data 2-6', col7: 'Data 2-7', col8: 'Data 2-8', col9: 'Data 2-9' },
      ],
      dataGrid1Columns: [
        {fieldName: 'col1', headerText: 'Mã CV', textAlign: 'Left', width: 100},
        {fieldName: 'col2', headerText: 'Phiếu YC', textAlign: 'Left', width: 100},
        {fieldName: 'col3', headerText: 'Hạng mục triển khai', textAlign: 'Left', width: 100},
        {fieldName: 'col4', headerText: 'Loại công việc', textAlign: 'Left', width: 100},
        {fieldName: 'col5', headerText: 'Đơn vị thực hiện', textAlign: 'Left', width: 100},
        {fieldName: 'col6', headerText: 'Cá nhân thực hiện', textAlign: 'Left', width: 100},
        {fieldName: 'col7', headerText: 'Phối hợp thực hiện', textAlign: 'Left', width: 100},
        {fieldName: 'col8', headerText: 'Thời gian thực hiện', textAlign: 'Left', width: 100},
        {fieldName: 'col9', headerText: 'Trạng thái thực hiện', textAlign: 'Left', width: 100}
      ],
      controls: {
        txtTen: {
          enabled: false,
          invalid: false,
        },
        txtGhiChu: {
          enabled: false,
        },
      },
      buttons: {
        btnAddNew: {
          enabled: true,
        },
        btnSave: {
          enabled: false,
        },
        btnCancel: {
          enabled: false,
        },
        btnEdit: {
          enabled: false,
        },
        btnDelete: {
          enabled: false,
        },
      },
      currentMode: 'ADD'
    };
  },
  computed: {
    showLayout1: function() {
      return this.selectedLayout === 1;
    },
    grdListOfWorks: function () {
      return this.$refs.grdListOfWorks;
    },
  },
  watch: {
    "currentItem.id": function (value) {
      this.buttons.btnEdit.enabled = value > 0;
      this.buttons.btnDelete.enabled = value > 0;
    },
    "currentItem.ten": function (value) {
      this.controls.txtTen.invalid = value == "";
    },
  },
  created: async function () {
    try {
      this.$root.showLoading(true);
      this.setCurrentItemDefault();
      this.enableControls(true);
      this.layoutOptions = await this.buildLayoutOptions();
      this.idcOptions = await this.buildIDCOptions();
    } finally {
      this.$root.showLoading(false);
    }
  },
  methods: {
    buildLayoutOptions: async function () {
      return [
        { ID: 1, TEN: 'Cấp phát RACK' },
        { ID: 2, TEN: 'Thu hồi RACK' },
        { ID: 3, TEN: 'Cấp phát U' },
        { ID: 4, TEN: 'Thu hồi U' },
      ];
    },

    enableControls: function (args) {
      for (const key in this.controls) {
        if (this.controls.hasOwnProperty(key)) {
          this.controls[key].enabled = args;
        }
      }

      this.buttons.btnSave.enabled = args;
      this.buttons.btnAddNew.enabled = !args;
      this.buttons.btnCancel.enabled = this.buttons.btnSave.enabled;
      this.buttons.btnDelete.enabled = !args;

      if (this.currentItem.id > 0)
        this.buttons.btnEdit.enabled = !this.buttons.btnSave.enabled;
    },
    setCurrentItem: function (item) {
      this.currentItem.id = item.id || 0;
      this.currentItem.ten = item.ten || '';
      this.currentItem.ghiChu = item.ghiChu || '';
      this.currentItem.hieuLuc = item.hieuLuc || 1;
    },
    setCurrentItemDefault: function () {
      this.setCurrentItem({
        id: 0,
        ten: '',
        ghiChu: '',
        hieuLuc: 1,
      });
    },
    validateControls: function () {
      let msg = "";
      if (this.currentItem.ten == null || this.currentItem.ten == "") {
        msg = "T�n kh�ng ��c � tr�ng.";
      }
      if (msg != "") { 
        this.$root.toastError(msg);
        return false;
      }
      return true;
    },
    onAddNew: async function () {
      
    },
    onSave: async function () {
      if (this.validateControls()) {
        let flag = await this.$confirm(
          "B�n c� mu�n l�u th�ng tin n�y kh�ng ?",
          "X�c nh�n",
          {
            confirmButtonText: "C�",
            cancelButtonText: "Kh�ng",
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
            this.$root.toastSuccess("L�u th�nh c�ng");
          } finally {
            this.$root.showLoading(false);
          }
        }
      }
    },
    onCancel: async function () {
      this.setCurrentItemDefault();
      this.enableControls(false);
    },
    onEdit: async function () {
      this.currentMode = 'EDIT';
    },
    onDelete: async function () {
      let msg = `B�n c� mu�n x�a d� li�u kh�ng?`
      let flag = await this.$confirm(msg, "X�c nh�n",
        {
          confirmButtonText: "�ng �",
          cancelButtonText: "Kh�ng �ng �",
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
          this.$root.showLoading(true);
          this.setCurrentItemDefault();
          this.enableControls(false);
          this.$root.toastSuccess("X�a th�nh c�ng");
        } finally {
          this.$root.showLoading(false);
        }
      }
    },
    buildIDCOptions: async function () {
      return [
        { id: 1, ten: 'IDC Hà Nội' },
        { id: 2, ten: 'IDC TP.HCM' },
        { id: 3, ten: 'IDC Đà Nẵng' },
      ];
    },
    onLayoutSelectionChange: function() {
    },
    onIDCSelectionChange: function() {
    },
    grdListOfWorks_RowSelected: function (args) {
      console.log('DataGrid 1 row selected:', args.data);
    },
    onFormSubmit: function() {
      console.log('Form data:', this.formData);
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

.form-section {
  margin: 20px 0;
}

.datagrid-section {
  margin: 20px 0;
}
</style>