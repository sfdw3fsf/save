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
      type: Number
    },
    isEditMode: {
      type: Boolean,
      default: false
    }
  },
  created: async function () {
    try {
      this.$root.showLoading(true);
      this.dsKetquaBangthongCapPhat = await this.getDsBangthongCapPhat();
      this.dsBangthongCapPhat = await this.getDsBangthongCapPhat();

    } finally {
      this.$root.showLoading(false);
    }
  },
  data: function () {
    return {
      dsBangthongCapPhat: [],

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
    grd1_RowSelected: function () {
    },
    grd2_RowSelected: function () {
    },
    onNew: function(row) {
      this.$refs.ModalThongtinBangthongMang.show(row, 'ADD', this.phieutcId);
    },
    saveTempCapPhatList: function(item) {

      if (Number(item.PORT_ID_OLD) !== Number(item.PORT_ID)) {
        // Xóa port cũ
        this.dsBangthongCapPhat = this.dsBangthongCapPhat.filter(
          data => Number(data.PORT_ID) !== Number(item.PORT_ID_OLD)
        )

        // Nếu port mới đã tồn tại thì warning
        if (this.dsBangthongCapPhat.some(data => Number(data.PORT_ID) === Number(item.PORT_ID))) {
          this.$root.$toast?.warning?.('PORT đã tồn tại trong danh sách!')
        } else {
          this.dsBangthongCapPhat.push(item)
        }
      } else {
        // Nếu không đổi port thì chỉ push khi chưa tồn tại
        if (this.dsBangthongCapPhat.some(data => Number(data.PORT_ID) === Number(item.PORT_ID))) {
          this.$root.$toast?.warning?.('PORT đã tồn tại trong danh sách!')
        } else {
          this.dsBangthongCapPhat.push(item)
        }
      }
    },
    reloadKetquaCapPhatList: async function() {
      this.dsKetquaBangthongCapPhat = await this.getDsBangthongCapPhat(this.phieutcId)
      
    },
    async onAddNew() {
      // let flag = await this.$confirm(
      //   "Bạn có muốn lưu thông tin này không ?",
      //   "Xác nhận",
      //   {
      //     confirmButtonText: "Có",
      //     cancelButtonText: "Không",
      //     type: "info",
      //     dangerouslyUseHTMLString: true,
      //   }
      // )
      //   .then(async () => {
          if (this.dsBangthongCapPhat.length == -1) {
            this.$toast.warning("Không có danh sách băng thông cần cấp phát");
          } else {
            try {
              this.$root.showLoading(true);

              const payload = this.dsBangthongCapPhat.map(item => ({
                phieutcId: this.phieutcId,                  // lấy từ prop hoặc currentItem
                idcId: this.currentItem?.idcId || null,     // nếu có field idc chung
                matSanId: this.currentItem?.matSanId || null, // nếu có field matSan chung
                goiCuocId: item.GOICUOC_ID || null,
                thietbimangId: item.THIETBIMANG_ID || null,
                thietbiportId: item.PORT_ID || null,
                quocTe: item.QUOCTE || '',
                trongNuoc: item.TRONGNUOC || '',
                thicongBtId: item.THICONGBT_ID == "Tự sinh" ? 0 : item.THICONGBT_ID
              }));

              const rs = await this.$root.context.post(
                `/web-ecms/ha-tang/thongtin-u/thicong-bangthong?phieutc=${this.phieutcId}&loaiAction=CP`,
                payload
              );
              this.dsKetquaBangthongCapPhat = await this.getDsBangthongCapPhat(this.phieutcId)
              this.dsBangthongCapPhat = await this.getDsBangthongCapPhat(this.phieutcId)

              this.$toast.success("Lưu thành công");
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
    reloadCapPhatList: async function () {
      this.dsBangthong = await this.getDsBangthongCapPhat(this.phieutcId);
      if (this.trangThaiPhieu != 21) {
        this.dsBangthongKetqua  = await this.getDsBangthongCapPhat(this.phieutcId);
      } 
    },
    getDsBangthongCapPhat: async function () {
      console.log(this.isEditMode)
      console.log(this.trangThaiPhieu)
      try {
        const rs = await this.$root.context.get(`/web-ecms/ha-tang/thongtin-u/thicong-bangthong/${!this.isEditMode && this.trangThaiPhieu == 24 ? 1 : 0}?phieutcId=${this.phieutcId}`)
        return rs.data
      } catch (error) {
        this.$toast.error(error.response?.data?.message_detail || 'Không lấy được port')
        return []
      }
    },
    actionColumn(parent) {
      return function () {
        return {
          template: {
            template: `<div class="action-buttons gap-1">
                <button class="btn btn-sm btn-outline-success" :disabled="true" v-if="trangThaiPhieu != 22">
                  <span class="icon nc-icon-outline ui-1_eye-17"></span> View only
                </button>
                <button class="btn btn-sm btn-outline-danger" title="Xoá" @click="xoaItem()" v-if="isEditMode && trangThaiPhieu == 22">
                  <i class="fa fa-trash"></i>
                </button>
              </div>`,
            computed: {
              isEditMode() {
                return parent.isEditMode;
              },
              trangThaiPhieu() {
                return parent.trangThaiPhieu;
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
    xuLyCongViec(data, edit) {
      try {
        if(edit == 1) {
          this.$refs.ModalThongtinBangthongMang.show(data, 'ADD', this.phieutcId);
        } else {
          this.dsBangthongCapPhat = this.dsBangthongCapPhat.filter(item => item.PORT_ID !== data.PORT_ID);
        }

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