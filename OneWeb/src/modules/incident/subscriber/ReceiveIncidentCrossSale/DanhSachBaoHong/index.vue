<template src="./index.html"></template>

<script>
import Const from './const';

export default {
  name: "DanhSachBaoHong",
  data() {
    return {
      menuItems: Const.contextMenuItems,
      dsIdChon: [],
    }
  },
  props: {
    dsBaoHong: {
      type: Array,
      required: true
    },
    options: {
      type: Object,
      required: true
    },
    value: {
      type: Object,
      required: true
    },
    colNguoiGiu_Visible: {
      type: Boolean,
      default: false,
    },
    showGiuPhieu: {
      type: Boolean,
      default: false
    },
    showHuyGiuPhieu: {
      type: Boolean,
      default: false
    },
  },
  computed: {
    dsbh: {
      get() {
        return this.value;
      },
      set(dsbh) {
        this.$emit('input', dsbh);
      }
    },

    menuContextItems() {
      let menus = [...this.menuItems];
      let data = [];
      for (let menu of menus) {
        if (!this.showGiuPhieu && menu.id === 'dsbhGiuPhieu') {
          continue;
        }

        if (!this.showHuyGiuPhieu && menu.id === 'dsbhHuyGiuPhieu') {
          continue;
        }

        data.push(menu);
      }

      return data;
    }
  },
  methods: {
    dataBound() {
      this.$refs.dsBaoHong.grid.autoFitColumns();
    },

    selectedRowChanged(item) {
      this.$emit('selectedRowChanged', item);
    },

    getSelectedRecords() {
      if (this.dsIdChon.length > 0) {
        return this.dsBaoHong.filter(item => this.dsIdChon.includes(item.baohong_id));
      }

      return [];
      // return this.$refs.dsBaoHong.getSelectedRecords();
    },

    getSelectedRowIndexes() {
      let indexes = [];
      if (this.dsIdChon.length > 0) {
        this.dsBaoHong.forEach((item, index) => {
          if (this.dsIdChon.includes(item.baohong_id)) {
            indexes.push(index);
          }
        });
      }

      return indexes;
      // return this.$refs.dsBaoHong.grid.getSelectedRowIndexes();
    },

    setSelectedRows(indexes) {
      this.$refs.dsBaoHong.selectRows(indexes);
      if (this.dsBaoHong.length > 0) {
        this.dsBaoHong.forEach((item, index) => {
          if (indexes.includes(index)) {
            this.dsIdChon.push(item.baohong_id);
          }
        });
      } else {
        this.dsIdChon = [];
      }
    },

    contextMenuClick(args) {
      let data = args.rowInfo.rowData;
      this.$emit(args.item.id, data);
    },

    getColumnCheckbox(parent) {
      return function () {
        return {
          template: {
            template: `
              <div class="check-action">
              <input
                type="checkbox"
                class="check"
                v-model="parent.dsIdChon"
                :value="data.baohong_id"/>
              <span class="name"></span>
              </div>
            `,
            data() {
              return {
                parent: parent,
                data: {}
              }
            }
          }
        }
      }
    },
  }
}
</script>

<style scoped>
</style>
