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
          <vue-element labelWidth="90" label="Vệ tinh" class="col-md-12">
            <SelectExt
            locale="vi-VN"
              :dataSource="options.vetinh"
              v-model="model.vetinh"
              popupWidth="auto"
              dataTextField="ten_dv"
              dataValueField="donvi_id"
            >
          </SelectExt>
          </vue-element>
          <vue-element labelWidth="90" label="Trạng thái" class="col-md-12">
            <SelectExt
              locale="vi-VN"
              v-model="model.trangthai"
              :dataSource="options.trangthai"
              popupWidth="auto"
              dataTextField="TEN_TTSO"
              dataValueField="TRANGTHAISO_ID"
            ></SelectExt>
          </vue-element>
          <vue-element labelWidth="90" label="Số NE" class="col-md-12">
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
          width: "120",
          filterAble: true
        },
        {
          key: "ds_matb",
          label: "Số máy",
          filterAble: true
        },
        {
          key: "port_mdf",
          label: "Port MDF",
          filterAble: true
        },
        {
          key: "toado_mdf",
          label: "Toạ độ MDF",
          filterAble: true
        }
      ],
      model: {
        vetinh: null,
        trangthai: 1
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
  props: ['tramvt_id', 'host_id'],
  async mounted() {
    let rs = await this.axios.post("web-quantri/thongsone/sp_ht_donvi_ne", {
                "donvi_id": this.tramvt_id ? this.tramvt_id : this.host_id,
                "loaidv_id": 25
            })
            this.options.vetinh = rs.data ? rs.data.data : []
    if(this.options.vetinh.length > 0) {
      this.model.vetinh = this.options.vetinh[0].donvi_id
    }
    
    this.getDSTrangThai()    
  },
  components: {
    VueElement
  },
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
      this.model.trangthai = 1
    },
    chapnhan() {
      this.item.tramtb_id = this.model.vetinh
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
