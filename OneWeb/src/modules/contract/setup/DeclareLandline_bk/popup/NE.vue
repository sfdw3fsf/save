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
          <vue-table
          style="height: 200px; overflow: scroll"
          ref="NE"
          v-bind:config="collumns"
          v-bind:options="options.ne"
          v-model="item"
        ></vue-table>
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
      item: {},
      collumns: [
        {
          key: "sone",
          label: "Số Ne",
          width: "120"
        },
        {
          key: "ne_id",
          label: "Số máy",
        },
        {
          key: "port_mdf",
          label: "Port MDF",
        },
        {
          key: "toado_mdf",
          label: "Toạ độ MDF",
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
  watch: {
    item(value) {
      let data = value
      data.tramtb_id = this.model.vetinh
      this.changeNE(value)
    },
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
      if(value) {
        this.sone = value.sone
      } else {
        this.sone = ''
      }
    },
    async getDSTrangThai() {
      let dsTrangthai = await this.axios.get('/web-quantri/danhmuc-chung/CSS_TRANGTHAI_SO', {params :{"nguoidung_id":this.$root.token.getNguoiDungID()}})
      this.options.trangthai = dsTrangthai.data.data ? dsTrangthai.data.data : []
    },
    chapnhan() {
      this.$emit('change', this.item)
    }
  },
  computed: {
  gridchange() {
    return `${this.model.trangthai}|${this.model.vetinh}`;
  },
},
};
</script>
