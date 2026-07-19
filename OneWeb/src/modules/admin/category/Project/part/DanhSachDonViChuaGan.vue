<template>
  <div class="box-form">
    <div class="legend-title">Danh sách đơn vị chưa gán</div>
    <div class="container-ds">
      <div class="actions">
        <div class="btn-right">
          <button class="btn" @click="removeDonVi">
            <span class="-ap icon-chevron-thin-right"></span>
          </button>
        </div>

        <button class="btn" @click="addDonVi">
          <span class="-ap icon-chevron-thin-left"></span>
        </button>
      </div>
        <div>
          <div class="info-row value" v-if="isShowCbxLoaiDonVi">
            <div class="select-custom">
              <vue-select
                :labelWidth="'90'"
                :options="LoaiDonVi.list"
                labelField="ten_loaidv"
                valueField="loaidv_id"
                label="Loại đơn vị"
                v-model="LoaiDonVi.value"
              ></vue-select>
            </div>
          </div>
          <DataGrid
            v-bind:columns="header"
            v-bind:dataSource="danhSachDonViChuaGan"
            :enable-paging-server="false"
            :allowPaging="true"
            :showFilter="true"
            :showColumnCheckbox="true"
            :selectedRow="0"
            @selectedItemsChanged="selectedItemsChanged"
          >
          </DataGrid>
      </div>
      
    </div>
  </div>
</template>

<script>
import { mapActions, mapState, mapGetters } from "vuex";
import moment from "moment";
import DatePicker from "vue2-datepicker";
import gridview from "@/components/Shared/gridview";

export default {
  name: "QuanLyDuAn",
  components: {
    DatePicker
  },

  props: {
    dataCurrent: Object
  },

  data() {
    return {
      DanhSachPhanVung: [21],
      phanvungId: 0,
      header: [
        {
          fieldName: "loaidv_id",
          headerText: "Loại đơn vị",
          allowFiltering: true,
          width: 100,
          isGroupedColumn: true
        },
        {
          fieldName: "ma_dv",
          headerText: "Mã ĐV",
          allowFiltering: true,
          width: 120
        },
        {
          fieldName: "ten_dv",
          headerText: "Đơn vị",
          allowFiltering: true,
          allowHtml: true,
          width: 120
        }
      ],

      dataRowCurrent: {
        donvi_id: 0,
        ischeck: "",
        loaidv_id: 0,
        ma_dv: "",
        ten_dv: ""
      },

      LoaiDonVi: {
        list: [
          // {id:1, text:'a'},
          // {id:2, text:'b'}
        ],
        value: 1
      },
      arrDonViDeGan: [],
      arrDonViBoGan: [],
      selectedDonVi: []
    };
  },

  async created() {
    this.phanvungId = this.$root.token.getPhanVungID();
    // console.log(this.phanvungId);
    await this.loadDataLoaiDonVi();
    await this.loadDataDsDonViChuaGan();
  },
  computed: {
    ...mapGetters("mapProject", ["danhSachDonViChuaGan", "itemsDonViDaGan"])
  },
  methods: {
    ...mapActions("mapProject", [
      "fetchDanhSachDonViChuaGan",
      "fetchDanhSachDonViDaGan",
      "ganDonVi",
      "boGanDonVi",
      "setSelecteItemsDonViDaGan",
      "fetchLoaiDonViList",
      "setLoaiDonVi                                     "
    ]),
    // xử lý cbx loại đơn vị
    isShowCbxLoaiDonVi(){
      return this.DanhSachPhanVung.includes(this.phanvungId) ? true : false;
    },
    async loadDataLoaiDonVi(){
      try {
        // await this.fetchLoaiDonViList();
        let res = await this.axios.post("web-hopdong/laydulieu/sp_tt_loai_dvi", {
          "p_type": 1
        });
        this.LoaiDonVi.list = res.data.data;
        this.LoaiDonVi.value = res.data.data[0]['loaidv_id'];
      } catch (error) {
        console.log(error);
      }
    },
    /* chọn data từ danh sách thuê bao */
    async loadDataDsDonViChuaGan() {
      // console.log('chạy', this.dataCurrent.duan_id);
      const variables = {
        duan_id: this.dataCurrent.duan_id,
        kieu: 1,
        loaidv_id: this.LoaiDonVi.value
      };
      await this.fetchDanhSachDonViChuaGan(variables);
    },

    async loadDataDsDonViDaGan() {
      // console.log('chạy', this.dataCurrent.duan_id);
      const variables = {
        duan_id: this.dataCurrent.duan_id,
        kieu: 2,
        loaidv_id: 1
      };
      await this.fetchDanhSachDonViDaGan(variables);
    },

    handleShowChild(index) {
      this.parentIndex = index;
      this.isShowChild = !this.isShowChild;
    },
    selectedItemsChanged(arr) {
      this.selectedDonVi = arr;

      // this.dataRowCurrent = item;
    },

    async addDonVi() {
      for (let index = 0; index < this.selectedDonVi.length; index++) {
        const element = this.selectedDonVi[index];
        let obj = {
          DUAN_ID: this.dataCurrent.duan_id,
          LOAIDV_ID: element.loaidv_id,
          DONVI_ID: element.donvi_id
        };
        this.arrDonViDeGan.push(obj);
      }

      const variables = {
        kieu: 1,
        ds: JSON.stringify(this.arrDonViDeGan)
      };
      await this.ganDonVi(variables);
      await this.loadDataDsDonViChuaGan();
      await this.loadDataDsDonViDaGan();
      this.arrDonViDeGan = [];
    },

    async removeDonVi() {
      for (let index = 0; index < this.itemsDonViDaGan.length; index++) {
        const element = this.itemsDonViDaGan[index];
        let obj = {
          DUAN_ID: this.dataCurrent.duan_id,
          LOAIDV_ID: element.loaidv_id,
          DONVI_ID: element.donvi_id
        };
        this.arrDonViBoGan.push(obj);
      }
      const variables = {
        kieu: 3,
        ds: JSON.stringify(this.arrDonViBoGan)
      };
      // const variables = {
      //   kieu: 3,
      //   ds: `[{"DUAN_ID": ${parseInt(
      //     this.dataCurrent.DUAN_ID
      //   )},"LOAIDV_ID": ${parseInt(
      //     this.itemsDonViDaGan.loaidv_id
      //   )},"DONVI_ID":${parseInt(this.itemsDonViDaGan.donvi_id)}}]`
      // };
      await this.boGanDonVi(variables);
      await this.loadDataDsDonViChuaGan();
      await this.loadDataDsDonViDaGan();
      this.setSelecteItemsDonViDaGan([]);
      this.arrDonViBoGan = [];
    }
  },
  watch: {
    "LoaiDonVi.value": function (newvalue, oldvalue) {
      // console.log(newvalue, oldvalue);
      this.loadDataDsDonViChuaGan();
      this.loadDataDsDonViDaGan();
    },
    dataCurrent(val) {
      // console.log('xxxxxxx',val);
      this.loadDataDsDonViChuaGan();
      this.loadDataDsDonViDaGan();
    }
  }
};
</script>
<style scoped>
.container-ds {
  display: flex;
}

.actions {
  margin-right: 10px;
}

.btn-right {
  margin-bottom: 10px;
}

button {
  background-color: rgba(186, 231, 255, 0.4);
  color: #0176ff;
}
</style>
