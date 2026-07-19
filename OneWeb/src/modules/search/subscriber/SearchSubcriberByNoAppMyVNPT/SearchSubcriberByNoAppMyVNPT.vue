<template>
  <div>
    <!-- breadcrumb -->
    <breadcrumb :header="header"></breadcrumb>

    <!-- menu -->
    <b-nav id="tlstpTop" pills="" class="menu">
      <b-nav-item @click="btnTimKiem"><span class="one-search" ></span> Tìm kiếm</b-nav-item>
      <b-nav-item @click="btnXuatFile"><span class="one-shape"></span> Xuất File</b-nav-item>
      <b-nav-item @click="btnXuatExcel"><span class="one-excel"></span> Xuất Excel</b-nav-item>
    </b-nav>

    <!-- form -->
    <b-container fluid="" class="p-2 page-content">
      <b-row class="m-0">
        <b-col class="box-form p-2 mr-1 overflow-hidden">
          <div class="legend-title mb-2">Thông tin tra cứu</div>
          <b-form-group
            label="Số liên hệ"
            label-cols="2"
            label-cols-lg="1"
            description="Hiển thị danh sách các thuê bao liên kết với số điện thoại liên hệ đăng nhập trên App MyVNPT"
          >
            <b-form-input id="input-1" v-model="txtSDT" trim v-on:keydown.enter="btnTimKiem" ref="inputSDT"></b-form-input>
          </b-form-group>
        </b-col>
      </b-row>
      <b-row class="m-0">
        <b-col class="box-form p-2 overflow-hidden">
          <div class="legend-title mb-2">Danh sách thuê bao</div>
          <DataGrid
            v-bind:columns="[
              {
                fieldName: 'TINH_ID',
                headerText: 'Tỉnh ID',
                allowFiltering: true,
                allowSorting: true,
              },
              {
                fieldName: 'TENTINH',
                headerText: 'Tên Tỉnh/TP',
                allowFiltering: true,
                allowSorting: true,
              },
              {
                fieldName: 'MA_TB',
                headerText: 'Số máy/Acc',
                allowFiltering: true,
                allowSorting: true,
              },
              {
                fieldName: 'TEN_TB',
                headerText: 'Tên thuê bao',
                allowFiltering: true,
                allowSorting: true,
              },
              {
                fieldName: 'TEN_KH',
                headerText: 'Tên KH',
                allowFiltering: true,
                allowSorting: true,
              },
              {
                fieldName: 'MA_HĐ',
                headerText: 'Mã HĐ',
                allowFiltering: true,
                allowSorting: true,
              },
              {
                fieldName: 'DIACHI_KH',
                headerText: 'Địa chỉ KH',
                allowFiltering: true,
                allowSorting: true,
              },
              {
                fieldName: 'LOAIHINH_TB',
                headerText: 'Loại hình',
                allowFiltering: true,
                allowSorting: true,
              },
              {
                fieldName: 'TOCDO',
                headerText: 'Tốc độ',
                allowFiltering: true,
                allowSorting: true,
              },
              {
                fieldName: 'DIACHI_LD',
                headerText: 'Địa chỉ LĐ',
                allowFiltering: true,
                allowSorting: true,
              },
              {
                fieldName: 'DIACHI_TB',
                headerText: 'Địa chỉ TB',
                allowFiltering: true,
                allowSorting: true,
              },
            ]"
            v-bind:dataSource="dgvDSTB"
            :showFilter="true"
            :showColumnCheckbox="false"
            :defaultColumnCheckboxChecked="false"
            :allowPaging="true"
            :enablePagingServer="false"
            :enabled-select-first-row="true"
            :allowExcelExport="true"
            ref='grid'
          >
          </DataGrid>
        </b-col>
      </b-row>
      <popupLayDuLieuBC ref="dialogLayDuLieuBC" ></popupLayDuLieuBC>
    </b-container>
  </div>
</template><style scope>
.menu {
  background: white;
  border-bottom: 1px solid #9090;
  box-shadow: 0 0 3px #808080;
  height: 40px;
  padding-top: 5px;
  font-weight: 600;
  line-height: 1;
}

.menu .nav-link {
  border-radius: 0;
  border-right: 1px solid #a5c1d0;
}

.menu .nav-link:active {
  color: white;
  background: #007bff;
}

.menu .nav-item:last-child .nav-link {
  border-right: 0;
}

.hide-header thead {
  display: none;
}

.form-group {
  margin-bottom: 0.333rem;
}

.e-grid.e-responsive .e-headercelldiv {
  text-overflow: clip;
}

.c-red {
  color: red;
  font-weight: bold;
}

.overflow-hidden {
  overflow: hidden;
  flex-wrap: none;
}

label,
legend {
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.col-xxx {
  width: calc(19.444444444% - 3px);
  flex: 0 0 calc(19.444444444% - 3px);
  max-width: calc(19.444444444% - 3px);
}
</style>
<script>
import breadcrumb from "@/components/breadcrumb";
import popupLayDuLieuBC from './LayDuLieuBC.vue'
export default {
  name: "FrmTcThueBaoMyVNPT",
  components: { breadcrumb,popupLayDuLieuBC },
  created: function () {
  },
  data: function () {
    return {
      header: {
        title: "Tra cứu thuê bao theo số liên hệ App MyVNPT",
        list: [
          {
            name: "Phát triển thuê bao",
            link: "/pttb",
          },
          {
            name: "Tra cứu thuê bao theo số liên hệ App MyVNPT",
            link: "/pttb/dktf",
          },
        ],
      },
      dgvDSTB: [],
      txtSDT:"",
    };
  },
  methods: {
    btnTimKiem(){
        this.timkiem();
    },
    async timkiem() {
      if(this.txtSDT.trim() == ""){
        this.$toast.error("Vui lòng nhập số điện thoại");
        this.$refs.inputSDT.focus();
        return false;
      }
      this.loading(true);
      var rs = await this.$root.context.get(
        "/tichhop/myvnpt/myvnpt_tracuu_thongtin_kh/"+this.txtSDT
      );
      //console.log(rs.data)
      if (
        rs.errorCode === 0 &&
        rs.data.length > 0
      ) {
        this.dgvDSTB = rs.data;
      } else {
        this.dgvDSTB = [];
        this.$toast.error("Không tìm thấy thông tin liên hệ");
      }
      this.loading(false);

      //console.log(rs.data);
    },
    btnXuatExcel(){
         if (this.dgvDSTB.length <= 0)
        this.$toast.error('Chưa có dữ liệu trên lưới để xuất excel!');
      else {
         let excelExportProperties = {
           fileName:"DanhSachThueBao.xlsx",
           dataSource: this.dgvDSTB
         };
         this.$refs.grid.excelExport(excelExportProperties);   
      }    
    },
    btnXuatFile(){
        try {
        if (this.dgvDSTB != null && this.dgvDSTB.length > 0) {
          this.$refs.dialogLayDuLieuBC.openDialog(this.dgvDSTB)
        } else {
          this.$toast.error('Không có dữ liệu!')
        }
      } catch (err) {}
    }
  },
};
</script>