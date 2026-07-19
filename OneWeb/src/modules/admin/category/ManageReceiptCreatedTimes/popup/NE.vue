<template>
  <div>
    <vue-nav>
      <ul class="list">
        <li>
          <a @click="chapnhan()">
            <span class="icon ui-1_check-circle-08 nc-icon-glyph"></span> Chấp
            nhận
          </a>
        </li>
      </ul>
    </vue-nav>
    <div class="popup-body">
      <vue-card>
        <vue-card label="Thông tin NE" :noborder="true">
          <vue-element label="Vệ tinh" class="col-md-12">
            <ejs-dropdownlist
              locale="vi-VN"
              :dataSource="optionsVetinh"
              popupWidth="auto"
              v-model="model.vetinh"
              :fields="{ value: 'donvi_id', text: 'ten_dv' }"
            />
          </vue-element>
          <vue-element label="Trạng thái" class="col-md-12">
            <ejs-dropdownlist
              locale="vi-VN"
              v-model="model.trangthai"
              :dataSource="options.trangthai"
              popupWidth="auto"
              :fields="{ value: 'TRANGTHAISO_ID', text: 'TEN_TTSO' }"
            />
          </vue-element>
          <vue-element label="Số NE" class="col-md-12">
            <ejs-textbox :value="sone"></ejs-textbox>
          </vue-element>
        </vue-card>
        <vue-card :noborder="true" label="Danh sách số NE">
          <DataGrid
          ref="NE"
          v-bind:columns="collumns"
          v-bind:dataSource="options.ne"
          :showFilter="true"
          :allowPaging="true"
          :enablePagingServer="false"
          @rowSelected="changeNE"
          panelDataHeight="170"
        ></DataGrid>
        </vue-card>
      </vue-card>
    </div>
  </div>
</template>
<script>
import VueElement from '../components/VueElement.vue'
export default {
  data() {
    return {
      animationSettings: { effect: "None" },
      isModal: true,
      sone: '',
      collumns: [
        {
          fieldName: "sone",
          headerText: "Số Ne",
          allowFiltering: true,
          allowSorting: false,
          autoFit: "true",
          width: "120"
        },
        {
          fieldName: "ne_id",
          headerText: "Số máy",
          allowFiltering: true,
          allowSorting: false,
          autoFit: "true",
        },
        {
          fieldName: "port_mdf",
          headerText: "Port MDF",
          allowFiltering: true,
          allowSorting: false,
          autoFit: "true",
          width: "120"
        },
        {
          fieldName: "toado_mdf",
          headerText: "Toạ độ MDF",
          allowFiltering: true,
          allowSorting: false,
          autoFit: "true",
          width: "120"
        }
      ],
      model: {
        vetinh: null,
        trangthai: 0
      },
      options: {
        trangthai: [],
        vetinh: [],
        ne: []
      }
    };
  },
  mounted() {
    if(this.optionsVetinh.length > 0) {
      this.model.vetinh = this.optionsVetinh[0].donvi_id
    }
    this.getDSTrangThai()    
  },
  components: {
    VueElement
  },
  props: ['optionsVetinh'],
  methods: {
    changeNE(value) {
      if(value.data) {
        this.sone = value.data.sone
      } else {
        this.sone = ''
      }
    },
    async getDSTrangThai() {
      let dsTrangthai = await this.axios.get('/web-quantri/danhmuc-chung/CSS_TRANGTHAI_SO', {params :{"nguoidung_id":this.$root.token.getNguoiDungID()}})
      this.options.trangthai = dsTrangthai.data.data ? dsTrangthai.data.data : []
    },
    chapnhan() {
      let data = {}
      let chon = this.$refs.NE.getSelectedRecords()
      if(chon.length > 0) {
        data = chon[0]
      }
      data.tramtb_id = this.model.vetinh
      this.$emit('change', data)
    }
  },
  computed: {
  gridchange() {
    return `${this.model.trangthai}|${this.model.vetinh}`;
  },
},
  watch: {
    gridchange(val) {
      if(val) {
        this.axios.post('/web-ccdv/tienhopdong/sp_danhsach_so_ne', {
          donvi_id: this.model.vetinh,
          trangthaiso_id: this.model.trangthai,
          type: 1
        }).then(response => {
          this.options.ne = response.data.data ? response.data.data : []
        })
      }
    }
  }
};
</script>
