<template src="./index.template.html"></template>

<script>
import ThongTinChung from './components/ThongTinChung.vue';
import LichSuCapPhat from './components/LichSuCapPhat.vue';
import LichSuThayDoi from './components/LichSuThayDoi.vue';

export default {
  name: 'ThongTinChiTietIpStore',
  components: {
    ThongTinChung,
    LichSuCapPhat,
    LichSuThayDoi,
  },
  data() {
    return {
      animationSettings: {
        effect: 'Zoom',
      },
      dialogTarget: '.main-wrapper',
    };
  },
  props: {
    value: {
      type: Boolean,
      default: false,
    },
    title: {
      type: String,
      default: '',
    },
    activeTab: {
      type: Number,
      default: 0,
    },
    currentItem: {
      type: Object,
      required: true,
    },
    loaiIPList: {
      type: Array,
      default: () => [],
    },
    phanLoaiIPList: {
      type: Array,
      default: () => [],
    },
    IDCList: {
      type: Array,
      default: () => [],
    },
    VRFList: {
      type: Array,
      default: () => [],
    },
    ZoneList: {
      type: Array,
      default: () => [],
    },
    VlanList: {
      type: Array,
      default: () => [],
    },
    SubnetList: {
      type: Array,
      default: () => [],
    },
    QuyHoachSubnetList: {
      type: Array,
      default: () => [],
    },
    lichSuCapPhatList: {
      type: Array,
      default: () => [],
    },
    lichSuCapPhatColumns: {
      type: Array,
      default: () => [],
    },
    lichSuThayDoiList: {
      type: Array,
      default: () => [],
    },
    lichSuThayDoiColumns: {
      type: Array,
      default: () => [],
    },
    ipInvalid: {
      type: Boolean,
      default: false,
    },
  },
  computed: {
    tabModel: {
      get() {
        return this.activeTab;
      },
      set(val) {
        this.$emit('update:activeTab', val);
      },
    },
  },
  watch: {
    value: {
      immediate: true,
      handler(val) {
        this.toggleDialog(val);
      },
    },
  },
  methods: {
    toggleDialog(visible) {
      const applyVisibility = () => {
        const dialog = this.$refs.ipStoreDialog?.ej2Instances;
        if (!dialog) {
          return false;
        }
        if (visible) {
          dialog.show();
        } else {
          dialog.hide();
        }
        return true;
      };

      if (!applyVisibility()) {
        this.$nextTick(() => {
          applyVisibility();
        });
      }
    },
    handleDialogClose() {
      this.$emit('close');
      if (this.value) {
        this.$emit('input', false);
      }
      this.$emit('hidden');
    },
    handleDialogOpen() {
      if (!this.value) {
        this.$emit('input', true);
      }
    },
    changeTab(index) {
      this.tabModel = index;
    },
  },
};
</script>

