<template>
  <div>
      <ejs-dialog :enableResize='true' :visible="false" ref="frmTraCuu_ThueBao" :header='"Danh sách thuê bao"'
    showCloseIcon=true target=".main-wrapper" width='60%' height="auto">
        <div class="list-actions-top">
          <ul class="list">
              <li>
                  <a @click="btnChapnhan_Click">
                      <span class="icon one-file-attach"></span> Chấp nhận
                  </a>
              </li>
          </ul>
        </div>
      <div class="modal-content">
        <div class="popup-body">
          <DataGridCustom ref="dgvThueBao" v-bind:columns="colums"></DataGridCustom>
        </div>
      </div>
      </ejs-dialog>
  </div>
</template>
<script>
import DataGridCustom from "@/modules/contract/setup/DeclareLandline/components/DataGridCustom";
export default {
    components: {
        DataGridCustom
    },
  data() {
    return {
      colums: [
        {
          headerText: "Số máy/Acc",
          autoFit: true,
          allowSorting: false,
          fieldName: "ma_tb",
        },
        {
          headerText: "Dịch vụ",
          autoFit: true,
          allowSorting: false,
          fieldName: "ma_kn",
        },
        {
          headerText: "Loại hình",
          autoFit: true,
          allowSorting: false,
          fieldName: "loaihinh_tb",
        },
        {
          headerText: "Tên thuê bao",
          autoFit: true,
          allowSorting: false,
          fieldName: "ten_tb",
        },
        {
          headerText: "Mã TB",
          autoFit: true,
          allowSorting: false,
          fieldName: "ma_tb",
        },
        {
          headerText: "Trạng thái",
          autoFit: true,
          allowSorting: false,
          fieldName: "trangthai_kn",
        },
        {
          headerText: "Mã thanh toán",
          autoFit: true,
          allowSorting: false,
          fieldName: "ma_tt",
        },
      ],
    }
  },
  methods: {
      show() {
          this.$refs.frmTraCuu_ThueBao.show()
      },
      btnChapnhan_Click() {
        let selected = this.$refs.dgvThueBao.getSelectedRecords()
        this.$emit("frmTraCuu_ThueBao_Close", selected.length > 0 ? selected[0] : {})
        this.$refs.frmTraCuu_ThueBao.hide()
      },
      frmTraCuu_ThueBao(ds) {
          console.log(ds)
          this.$refs.dgvThueBao.dataSource = ds
      }
  }
};
</script>