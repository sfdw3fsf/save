<template>
  <b-modal
    ref="popupNeo"
    size="lg"
    hide-header
    hide-header-close
    hide-footer
    body-class="p-0"
    centered
    no-close-on-backdrop
    @show="handleShowModal"
  >
    <div class="modal-content popup-box">
      <div class="popup-header">
        <div class="title">
          <span class="icon one-notepad"></span> {{ headerTitle }}
        </div>
        <div class="close -ap icon-close" @click="hideModal"></div>
      </div>
      <div class="list-actions-top">
        <ul class="list">
          <li v-for="item in actions" :key="item.id" @click="onActionClick(item)"
              :class="{disabled: !item.active}" v-show="item.visible">
            <a style="cursor: pointer;" :class="{disabled_color: !item.active}" :ref="item.id">
              <span class="icon" :class="item.icon_class"></span> {{ item.name }}
            </a>
          </li>
        </ul>
      </div>
      <div class="popup-body">
        <div class="box-form">
          <div class="legend-title">Danh sách mã thanh toán</div>
          <DataGrid v-bind:dataSource="dsMa"
                    v-bind:columns="cols"
                    :allowPaging="false"
                    :enablePagingServer="false"
                    @selectedRowChanged="selectedRowChanged"
          ></DataGrid>
        </div>
      </div>
    </div>
  </b-modal>
</template>

<script>

export default {
  name: 'FormNeo',
  props: {
    dsMa: {
      type: Array,
      default: function () {
        return []
      }
    }
  },
  data() {
    return {
      headerTitle: 'Danh sách Mã TT',
      actions: [
        {
          id: 'tsbtnChon',
          name: 'Chọn',
          active: true,
          icon_class: 'one-check',
          visible: true
        },
        {
          id: 'tsbtnThoat',
          name: 'Thoát',
          active: true,
          icon_class: 'one-cancel',
          visible: true
        }
      ],
      cols: [
        {
          fieldName: "ma_tt",
          headerText: "Mã TT",
          allowFiltering: true,
          allowSorting: false,

        },
        {
          fieldName: "ma_tt_neo",
          headerText: "Mã TT Neo",
          allowFiltering: true,
          allowSorting: false,

        },
      ],
      item: null,
    }
  },
  methods: {
    handleShowModal() {
      this.item = null;
    },

    hideModal() {
      this.$refs.popupNeo.hide();
    },

    showModal() {
      this.$refs.popupNeo.show();
    },

    onActionClick(action) {
      if (action.id === 'tsbtnChon') {
        this.$emit('chon', this.item);
      }

      this.hideModal();
    },

    selectedRowChanged(item) {
      this.item = item;
    }
  }
}
</script>

<style scoped>
</style>
