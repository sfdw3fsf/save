<template>
  <div class="main-wrapper">
    <div class="breadcrumb-top">
      <div class="main-title">Gán lại nhân viên quản lý thuê bao</div>
      <ul class="breadcrumb">
        <li class="breadcrumb-item"><a href="#"><span class="nc-icon-outline ui-1_home-simple"></span> Trang chủ </a></li>
        <li class="breadcrumb-item"><a href="#">Lập hợp đồng</a></li>
        <li class="breadcrumb-item active">Lắp đặt mới</li>
      </ul>
    </div>
    <div class="list-actions-top">
      <ul class="list">
        <li @click="getDsThuebao">
          <a>
            <span class="icon nc-icon-glyph files_single-content-03"></span> Lấy TT
          </a>
        </li>
        <li @click="update">
          <a>
            <span class="icon one-reload"></span> Cập nhật
          </a>
        </li>
      </ul>
    </div>
    <div class="page-content">
      <div class="row">
        <div class="col-sm-6 col-12">
          <div class="box-form">
            <div class="legend-title">Thông tin tìm kiếm</div>
            <div class="info-row">
              <div class="key w110">Loại nhân viên</div>
              <div class="value">
                <div class="select-custom">
                  <SelectExt
                    v-model="cboLoaiNV.value"
                    :allowFiltering="true"
                    dataTextField="TEN"
                    dataValueField="LOAINV_ID"
                    :dataSource="cboLoaiNV.DataSource"
                  ></SelectExt>
                </div>
              </div>
            </div>
            <div class="title-bg-main">
              <span class="title">Danh sách TB theo nhân viên</span>
            </div>
            <div class="info-row">
              <div class="key w110">Đơn vị</div>
              <div class="value">
                <div class="select-custom">
                  <SelectExt
                    v-model="cboDonviNguon.value"
                    :allowFiltering="true"
                    dataTextField="ten_dv"
                    dataValueField="donvi_id"
                    :dataSource="cboDonviNguon.DataSource"
                  ></SelectExt>
                </div>
              </div>
            </div>
            <div class="info-row">
              <div class="key w110">Nhân viên nguồn</div>
              <div class="value">
                <div class="select-custom">
                  <SelectExt
                    v-model="cboNhanvienNguon.value"
                    :allowFiltering="true"
                    dataTextField="ten_ht"
                    dataValueField="nhanvien_id"
                    :dataSource="cboNhanvienNguon.DataSource"
                  ></SelectExt>
                </div>
              </div>
            </div>
            <div class="table-content">
              <DataGrid
                :showColumnCheckbox="true"
                ref="gridFrom"
                :columns="gridFrom.columns"
                :dataSource="gridFrom.DataSource"
                :enable-paging-server="false"
                :allowPaging="true"
                :showFilter="true"
              ></DataGrid>
            </div>
          </div>
        </div>
        <div class="col-sm-6 col-12">
          <div class="box-form">
            <div class="legend-title">Thông tin tìm kiếm</div>
            <div class="info-row">
              <div class="key w110">Loại hợp đồng</div>
              <div class="value">
                <div class="select-custom">
                  <vue-select
                    v-model="cboLHD.value"
                    labelField="TEN_LHD"
                    valueField="ID_LHD"
                    labelWidth="0"
                    :options="cboLHD.DataSource"
                  ></vue-select>
                </div>
              </div>
            </div>
            <div class="title-bg-main">
              <span class="title">Danh sách TB theo nhân viên</span>
            </div>
            <div class="info-row">
              <div class="key w110">Đơn vị</div>
              <div class="value">
                <div class="select-custom">
                  <SelectExt
                    v-model="cboDonviDich.value"
                    :allowFiltering="true"
                    dataTextField="ten_dv"
                    dataValueField="donvi_id"
                    :dataSource="cboDonviDich.DataSource"
                  ></SelectExt>
                </div>
              </div>
            </div>
            <div class="info-row">
              <div class="key w110">Nhân viên đích</div>
              <div class="value">
                <div class="select-custom">
                  <SelectExt
                    v-model="cboNhanvienDich.value"
                    :allowFiltering="true"
                    dataTextField="ten_ht"
                    dataValueField="nhanvien_id"
                    :dataSource="cboNhanvienDich.DataSource"
                  ></SelectExt>
                </div>
              </div>
            </div>
            <div class="table-content">
              <DataGrid
                ref="gridTo"
                :columns="gridTo.columns"
                :dataSource="gridTo.DataSource"
                :enable-paging-server="false"
                :allowPaging="true"
                :showFilter="true"
              ></DataGrid>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import moment from "moment"
export default
  {
    data() {
      return {
        cboLHD: {
          DataSource: [
            { 'ID_LHD': '-1', 'TEN_LHD': '--Tất cả--' },
            { 'ID_LHD': '2', 'TEN_LHD': 'Duy trì' },
            { 'ID_LHD': '3', 'TEN_LHD': 'Hỗ trợ' }
          ],
          value: '-1'
        },
        cboLoaiNV: {
          DataSource: [],
          value: null
        },
        cboDonviNguon: {
          DataSource: [],
          value: null
        },
        cboDonviDich: {
          DataSource: [],
          value: null
        },
        cboNhanvienNguon: {
          DataSource: [],
          value: null
        },
        cboNhanvienDich: {
          DataSource: [],
          value: null
        },
        gridFrom: {
          DataSource: [],
          columns: [
            {
              fieldName: "ma_tb",
              headerText: "Số máy/Acc",
              allowFiltering: true,
              allowSorting: true,
              width: '100'
            },
            {
              fieldName: "thuebao_id",
              headerText: "Mã TN",
              allowFiltering: true,
              allowSorting: true,
              width: '100'
            },
            {
              fieldName: "ten_dvvt",
              headerText: "Dịch vụ",
              allowFiltering: true,
              allowSorting: true,
              width: '100'
            },
            {
              fieldName: "loaihinh_tb",
              headerText: "Loại hình",
              allowFiltering: true,
              allowSorting: true,
              width: '100'
            },
            {
              fieldName: "ten_tb",
              headerText: "Tên thuê bao",
              allowFiltering: true,
              allowSorting: true,
              width: '100'
            }
          ]
        },
        gridTo: {
          DataSource: [],
          columns: [
            {
              fieldName: "ma_tb",
              headerText: "Số máy/Acc",
              allowFiltering: true,
              allowSorting: true,
              width: '100'
            },
            {
              fieldName: "thuebao_id",
              headerText: "Mã TN",
              allowFiltering: true,
              allowSorting: true,
              width: '100'
            },
            {
              fieldName: "ten_dvvt",
              headerText: "Dịch vụ",
              allowFiltering: true,
              allowSorting: true,
              width: '100'
            },
            {
              fieldName: "loaihinh_tb",
              headerText: "Loại hình",
              allowFiltering: true,
              allowSorting: true,
              width: '100'
            },
            {
              fieldName: "ten_tb",
              headerText: "Tên thuê bao",
              allowFiltering: true,
              allowSorting: true,
              width: '100'
            }
          ]
        }
      }
    },
    watch: {
      "cboLoaiNV.value"(value) {
        if (value) {
          if (this.cboDonviNguon.value)
            this.getDsNhanvienNguon()
          if (this.cboDonviDich.value)
            this.getDsNhanvienDich()
        }
      },
      "cboDonviNguon.value"(val) {
        if (val)
          this.getDsNhanvienNguon()
      },
      "cboDonviDich.value"(val) {
        if (val)
          this.getDsNhanvienDich()
      }
    },
    mounted() {
      this.getDsLoaiNV()
      this.getDsDonvi()
    },
    methods: {
      async getDsLoaiNV() {
        try {
          let data = await this.axios.get("web-quantri/danhmuc-chung/ADMIN_LOAI_NV")
          this.cboLoaiNV.DataSource = data.data.data.filter(item => [454,453,452,451,450].includes(item.LOAINV_ID))
          this.cboLoaiNV.value = this.cboLoaiNV.DataSource[0]['LOAINV_ID']
        } catch (err) {

        }
      },
      async getDsDonvi() {
        try {
          let data = await this.axios.post("web-danhba/gan_nvchamsoc_khdn/lay_ds_donvi_nguon_dich")
          this.cboDonviNguon.DataSource = data.data.data
          this.cboDonviDich.DataSource = data.data.data
          if(this.cboDonviNguon.DataSource.length > 0) {
            this.cboDonviNguon.value =  this.cboDonviNguon.DataSource[0].donvi_id
            this.cboDonviDich.value  =  this.cboDonviDich.DataSource[0].donvi_id
          }
        } catch (err) {

        }
      },
      async getDsNhanvienNguon() {
        try {
          let data = await this.axios.post("web-danhba/gan_nvchamsoc_khdn/lay_nv_ql_theo_loainv", {
            "donvi_id": this.cboDonviNguon.value,
            "loainv_id": this.cboLoaiNV.value
          })
          this.cboNhanvienNguon.DataSource = data.data.data
          if(this.cboNhanvienNguon.DataSource.length > 0) {
            this.cboNhanvienNguon.value = this.cboNhanvienNguon.DataSource[0].nhanvien_id
          }
        } catch (err) {

        }
      },
      async getDsNhanvienDich() {
        try {
          let data = await this.axios.post("web-danhba/gan_nvchamsoc_khdn/lay_nv_ql_theo_loainv", {
            "donvi_id": this.cboDonviDich.value,
            "loainv_id": this.cboLoaiNV.value
          })
          this.cboNhanvienDich.DataSource = data.data.data
          if(this.cboNhanvienDich.DataSource.length > 0) {
            this.cboNhanvienDich.value = this.cboNhanvienDich.DataSource[0].nhanvien_id
          }
        } catch (err) {

        }
      },
      async getDsThuebao() {
        try {
          let data = await this.axios.post("web-danhba/gan_nvchamsoc_khdn/lay_ds_tb_khdn_by_nv", {
            "loainv_id": this.cboLoaiNV.value,
            "nhanvien_from_id": this.cboNhanvienNguon.value,
            "nhanvien_to_id": this.cboNhanvienDich.value,
            "loaihd_id": this.cboLHD.value
          })
          this.gridFrom.DataSource = data.data.data.nv_from
          this.gridTo.DataSource = data.data.data.nv_to

          if(this.gridFrom.DataSource.length <= 0) {
            this.$toast.error("Không tìm thấy thông tin")
          }
        } catch (err) {
          this.$toast.error("Không tìm thấy thông tin")
        }
      },
      async update() {
        try {

          let selected = this.$refs.gridFrom.getSelectedRecords()
          if (this.cboNhanvienNguon.value == this.cboNhanvienDich.value) {
            this.$toast.error("Nhân viên nguồn và nhân viên đích không thể giống nhau.");
            return;
          }

          if (selected.length <= 0) {
            this.$toast.error("Không có thuê bao cũ.");
            return;
          }
          this.loading(true)
          let data = []
          selected.forEach(item => {
            data.push(item.thuebao_id)
          })
          this.axios.post("web-danhba/gan_nvchamsoc_khdn/update_nhanvien_cs_tb_khdn_all_func", {
            "json_tb": data,
            "vdonvi_dich": this.cboDonviDich.value,
            "vdonvi_nguon": this.cboDonviNguon.value,
            "vnhanvien_cu_id": this.cboNhanvienNguon.value,
            "vnhanvien_moi_id": this.cboNhanvienDich.value,
            "vloai_hd": this.cboLHD.value,
            "vloainv_id": this.cboLoaiNV.value,
            "vip_cn": "Test"
          })

          this.$toast.success("Cập nhật thành công!")
          this.getDsThuebao()
        } catch (e) {
          console.log(e)
          this.$toast.success("Cập nhật lỗi!")
          this.loading(false)
        }
        this.loading(false)
      }
    }
  }
</script>