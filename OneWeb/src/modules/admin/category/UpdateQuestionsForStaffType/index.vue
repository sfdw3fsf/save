<template>
  <div>
    <div class="breadcrumb-top">
      <div class="main-title">gán câu hỏi cho loại nhân viên</div>
      <ul class="breadcrumb">
        <li class="breadcrumb-item">
          <a href="#"
            ><span class="nc-icon-outline ui-1_home-simple"></span> Trang chủ
          </a>
        </li>
        <li class="breadcrumb-item"><a href="#">Lập hợp đồng</a></li>
        <li class="breadcrumb-item active">Lắp đặt mới</li>
      </ul>
    </div>
    <div class="list-actions-top">
      <ul class="list">
        <li @click="ghilai">
          <a> <span class="icon one-save"></span>Ghi lại </a>
        </li>
      </ul>
    </div>
    <div class="page-content">
      <div class="box-form">
        <div class="legend-title">Thông tin câu hỏi, loại nhân viên</div>
        <div class="info-row">
          <div class="key w80">Loại NV</div>
          <div class="value">
            <div class="select-custom">
              <SelectExt v-model="form.loai_nv" :allowFiltering="true" dataTextField="TEN"
                      dataValueField="LOAINV_ID" :dataSource="cboLoaiNhanvien"></SelectExt>
            </div>
          </div>
        </div>
        <div class="info-row">
          <div class="key w80">Hình thức</div>
          <div class="value">
            <div class="select-custom">
              <SelectExt v-model="form.hinhthuc" :allowFiltering="true" dataTextField="ten"
                      dataValueField="hinhthuc" :dataSource="cboHinhthuc"></SelectExt>
            </div>
          </div>
        </div>
      </div>
      <div class="box-move-select-table">
        <div class="box-col box-form">
          <div class="legend-title">Câu hỏi chưa gán</div>
          <div class="table-content" style="height: 480px">
            <DataGrid :columns="columns_nv" ref="chuagan" :dataSource="gridDanhSachChuagan" :enable-paging-server="false"
              :allowPaging="true" :showFilter="true">
            </DataGrid>
          </div>
        </div>
        <div class="actions">
          <button @click="gan" class="btn">
            <span class="fa fa-angle-right"></span>
          </button>

          <button @click="bogan" class="btn">
            <span class="fa fa-angle-left"></span>
          </button>
        </div>
        <div class="box-col box-form">
          <div class="legend-title">Câu hỏi đã gán</div>

          <div class="table-content" style="height: 480px">
            <DataGrid :columns="columns_nv" ref="dagan" :dataSource="gridDanhSachDagan" :enable-paging-server="false"
              :allowPaging="true" :showFilter="true">
            </DataGrid>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
function compare( a, b ) {
  if ( a.cauhoi_id < b.cauhoi_id ){
    return -1;
  }
  if ( a.cauhoi_id > b.cauhoi_id ){
    return 1;
  }
  return 0;
}
import state from './define.js'
export default {
  data() {
    return {
      ...state
    }
  },
  created() {
    this.getComboLoaiNhanvien()
    this.getCboHinhthuc()
  },
  watch: {
    changeDanhsach() {
      this.layDanhsach(0)
      this.layDanhsach(1)
    }
  },
  computed: {
    changeDanhsach() {
      return this.form.loai_nv + this.form.hinhthuc
    }
  },
  methods: {
    async ghilai() {
      try {
        this.loading(true)
        let data = []
        this.gridDanhSachDagan.forEach((item, index) => {
          data.push({
            CAUHOI_ID: item.cauhoi_id,
            LOAINV_ID: this.form.loai_nv,
            THUTU: index
          })
        })
        let res = await this.axios.post("web-quantri/cauhoi-loainv/sp_cauhoi_lnv_update", {
          "hinhthuc": this.form.hinhthuc,
          "loainv_id": this.form.loai_nv,
          "js_data": JSON.stringify(data)
        })
        if(res.data.data == 1) {
          this.$toast.success("Cập nhật thành công!")
        } else {
          this.$toast.error(res.data.message)
        }
        this.layDanhsach(0)
        this.layDanhsach(1)
        this.loading(false)
      } catch(err) {
        console.log(err)
        this.loading(false)
      }
    },
    async getComboLoaiNhanvien() {
      try {
        let data = await this.axios.get("web-quantri/danhmuc-chung/ADMIN_LOAI_NV")
        this.cboLoaiNhanvien = data.data.data
        if(this.cboLoaiNhanvien.length > 0) {
          this.form.loai_nv = this.cboLoaiNhanvien[0].LOAINV_ID
        }
      } catch(err) {
        this.$toast.error("Lỗi!")
      }
    },
    sortArray() {
      this.gridDanhSachChuagan.sort(compare)
      this.gridDanhSachDagan.sort(compare)
    },
    gan() {
      let chuagan = this.$refs.chuagan.getSelectedRecords()
      if(chuagan.length > 0) {
        this.gridDanhSachDagan.push(chuagan[0])
        let index = this.gridDanhSachChuagan.indexOf(chuagan[0])
        if (index > -1) {
          this.gridDanhSachChuagan.splice(index, 1);
        }
      }

      this.sortArray()
    },
    bogan() {
      let dagan = this.$refs.dagan.getSelectedRecords()
      if(dagan.length > 0) {
        let index = this.gridDanhSachDagan.indexOf(dagan[0])
        if (index > -1) {
          this.gridDanhSachDagan.splice(index, 1);
        }
        this.gridDanhSachChuagan.push(dagan[0])
      }

      this.sortArray()

    },
    async getCboHinhthuc() {
      try {
        let data = await this.axios.get("web-quantri/cauhoi-loainv/sp_lay_hinhthuc_ks")
        this.cboHinhthuc = data.data.data.map(x=>{
          x.ten= `${x.hinhthuc} - ${x.tenhinhthuc}`
          return x
        })
        if(this.cboHinhthuc.length > 0) {
          this.form.hinhthuc = this.cboHinhthuc[0].hinhthuc
        }
      } catch(err) {
        this.$toast.error("Lỗi!")
      }
    },
    async layDanhsach(loai) {
      try {
        this.loading(true)
        let data = await this.axios.post("web-quantri/cauhoi-loainv/sp_ds_cauhoi_loainv", {
          "hinhthuc": this.form.hinhthuc,
          "loainv_id": this.form.loai_nv,
          "loai": loai
        })
        if(loai == 0) {
          this.gridDanhSachChuagan = data.data.data
          this.gridDanhSachChuagan = [...this.gridDanhSachChuagan]
        }
        if(loai == 1) {
          this.gridDanhSachDagan = data.data.data
          this.gridDanhSachDagan = [...this.gridDanhSachDagan]
        }

        this.loading(false)
      } catch(err) {
        this.loading(false)
        this.$toast.error("Lỗi!")
      }
    }
  },
}
</script>
