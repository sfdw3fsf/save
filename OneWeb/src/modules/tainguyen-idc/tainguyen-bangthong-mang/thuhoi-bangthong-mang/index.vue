<template src="./index.html"></template>

<script>
import DataGrid from '@/components/Controls/DataGrid/index.vue';
import ModalThongtinBangthongMang from '../popup-thongtin-bangthong-mang/index.vue';


export default {
  components: {
    DataGrid,
    ModalThongtinBangthongMang
  },
  props: {
    currentMode: {
      type: String,
      default: 'VIEW'
    },
    phieutcId: {
      type: Number,
      default: 0
    },
    trangThaiPhieu: {
      type: Number,
      default: 0
    },
    isEditMode: {
      type: Boolean,
      default: false
    }
  },
  created: async function () {
    try {
      this.$root.showLoading(true);
      this.nguoiCn = this.$root.context.user.getProperty('ten_nv')
      this.dsBangthong = await this.getDsBangthongCapPhat(this.phieutcId);
      this.dsKetquaBangthongCapPhat = await this.getDsBangthongCapPhat(this.phieutcId);

    } finally {
      this.$root.showLoading(false);
    }
  },
  data: function () {
    return {
      dsBangthong: [],
      dsBangthongThuhoi: [],
      nguoiCn: '',

      dataGrid1Columns: [
        {fieldName: 'THICONGBT_ID', headerText: 'ID cấp băng thông', textAlign: 'Left', width: 80},
        {fieldName: 'GOICUOC_ID', headerText: 'Gói cước mạng', textAlign: 'Left', width: 100},
        {fieldName: 'QUOCTE', headerText: 'Quốc tế', textAlign: 'Left', width: 90},
        {fieldName: 'TRONGNUOC', headerText: 'Trong nước', textAlign: 'Left', width: 110},
        {fieldName: 'THIETBIMANG', headerText: 'Thiết bị mạng(Switch Access)', textAlign: 'Left', width: 110},
        {fieldName: 'PORT', headerText: 'ID Port', textAlign: 'Left', width: 120},
        {fieldName: 'TRANGTHAI', headerText: 'Trạng thái CCDV', textAlign: 'Left', width: 170},
        {fieldName: 'ACTIONS', headerText: 'Thao tác', allowSorting: false,
              allowFiltering: false, textAlign: 'Center', template: this.actionColumn(this), width: 80}
      ],      
      dsKetquaBangthongCapPhat: [],
      dataGrid2Columns: [
        {fieldName: 'THICONGBT_ID', headerText: 'ID cấp băng thông', textAlign: 'Left', width: 50},
        {fieldName: 'GOICUOC_ID', headerText: 'Gói cước mạng', textAlign: 'Left', width: 100},
        {fieldName: 'QUOCTE', headerText: 'Quốc tế', textAlign: 'Left', width: 90},
        {fieldName: 'TRONGNUOC', headerText: 'Trong nước', textAlign: 'Left', width: 90},
        {fieldName: 'THIETBIMANG', headerText: 'Thiết bị mạng(Switch Access)', textAlign: 'Left', width: 120},
        {fieldName: 'PORT', headerText: 'ID Port', textAlign: 'Left', width: 120},
        {fieldName: 'TRANGTHAI', headerText: 'Trạng thái thực hiện', textAlign: 'Left', width: 120}
      ],
    };
  },
  computed: {
    grd1: function () {
      return this.$refs.grd1;
    },
    grd2: function () {
      return this.$refs.grd2;
    },
  },
  methods: {
    grd1_RowSelected: function (args) {
      console.log('DataGrid 2 row selected:', args.data);

    },
    grd1_RowDeselected: function (args) {

    },
    grd2_RowSelected: function (args) {
      console.log('DataGrid 2 row selected:', args.data);
    },
    onNew: function(row) {
      
    },
    capnhatThuHoiList: function(item) {   
      item.map(x => { 
        // tìm trong danh sách item xem có phần tử nào trùng THICONGBT_ID không
        const match = this.dsBangthong.find(y => y.THICONGBT_ID === x.THICONGBT_ID);

        if (match) {

        } else {
          this.dsBangthong.push(x);
        }
      });

    },
    reloadCapPhatList: async function () {
      this.dsBangthong = await this.getDsBangthongCapPhat(this.phieutcId);
    },
    getDsBangthongCapPhat: async function () {
      try {
        const rs = await this.$root.context.get(`/web-ecms/ha-tang/thongtin-u/thicong-bangthong/${!this.isEditMode && this.trangThaiPhieu == 24 ? 1 : 0}?phieutcId=${this.phieutcId}`)
        return rs.data
      } catch (error) {
        this.$toast.error(error.response?.data?.message_detail || 'Không lấy được thông tin')
        return []
      }
    },
    onNew: function(row) {
      this.$refs.ModalThongtinBangthongMang.show(row, 'MINUS', this.phieutcId);
    },
    async onAddNew() {
      this.dsBangthongThuhoi = this.dsBangthong.map(row => ({
        phieutcBtId: row.THICONGBT_ID,
        phieutcId: this.phieutcId,
        nguoiCn: this.nguoiCn
      }));

      // let flag = await this.$confirm(
      //   "Bạn có muốn lưu thông tin này không ?",
      //   "Xác nhận",
      //   {
      //     confirmButtonText: "Có",
      //     cancelButtonText: "Không",
      //     type: "info",
      //     dangerouslyUseHTMLString: true,
      //   }
      // ).then(async () => {
          if (this.dsBangthongThuhoi.length == -1) {
            this.$toast.warning("Không có danh sách băng thông cần cấp phát");
          } else {
            try {
              this.$root.showLoading(true);

              const payload = this.dsBangthongThuhoi;

              const rs = await this.$root.context.post(
                `/web-ecms/ha-tang/thongtin-u/thicong-bangthong?phieutc=${this.phieutcId}&loaiAction=TH`,
                payload
              );
              this.$toast.success("Lưu thành công");
              this.dsBangthong = await this.getDsBangthongCapPhat(this.phieutcId);
              this.dsKetquaBangthongCapPhat  = await this.getDsBangthongCapPhat(this.phieutcId);

            } catch (error) {
              this.$toast.error(error.response?.data?.message_detail || "Lưu thất bại");
            } finally {
              this.$root.showLoading(false);
            }
          }
      //   })
      //   .catch((e) => {
      //     return false;
      //   });
      // if (flag) {
      //   try {
      //     this.$root.showLoading(true);
      //     this.$root.toastSuccess("Lưu thành công");
      //   } finally {
      //     this.$root.showLoading(false);
      //   }
      // }
    },
    actionColumn(parent) {
      return function () {
        return {
          template: {
            template: `<div class="action-buttons gap-1">
                <button class="btn btn-sm btn-outline-success" :disabled="true" v-if="!isEditMode">
                  <span class="icon nc-icon-outline ui-1_eye-17"></span> View only
                </button>
                <button class="btn btn-sm btn-outline-danger" title="Xoá" @click="xoaItem()" v-if="isEditMode">
                  <i class="fa fa-trash"></i>
                </button>
              </div>`,
            computed: {
              isEditMode() {
                return parent.isEditMode;
              }
            },
            methods: {
              xoaItem() {
                parent.xuLyCongViec(this.data)
              }
            }
          }
        }
      }
    },
    xuLyCongViec(data) {
      try {
        this.dsBangthong = this.dsBangthong.filter(item => item.PORT_ID !== data.PORT_ID);
      } catch (error) {
        console.error('Lỗi khi xử lý công việc:', error)
        this.$toast.error('Có lỗi xảy ra khi xử lý công việc')
      }
    }
  }
};
</script>

<style scoped>
.form-section {
  margin: 20px 0;
}

.datagrid-section {
  margin: 20px 0;
}
</style>