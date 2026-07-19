<template src="./index.html"></template>

<script>
export default {
  name: 'TabHistory',
  props: {
    tenBang: {
      type: String,
      default: null,
    },
    tenCot: {
      type: String,
      default: null,
    },
    khoaDuLieu: {
      type: Number,
      default: null,
    },
  },
  data: function () {
    return {
      historyList: [
        
      ]
    };
  },
  components: {
  },
  computed: {
   
  },
  watch: {
    'khoaDuLieu': {
      handler: async function (newVal, oldVal) {
        if (newVal !== oldVal) {
          await this.getHistoryList();
        }
      },
      immediate: false,
    },
  },
  created: async function () {
  },
  mounted: async function () {
    await this.getHistoryList();
  },
  methods: {
    async getHistoryList() {
      try {
        const response = await this.$root.context.post('/web-ecms/idc/lichsu-tacdong/danhsach', {
          tenBang: this.tenBang,
          tenCot: this.tenCot,
          khoaDuLieu: this.khoaDuLieu
        });
        this.historyList = response.data || [];
        this.historyList.forEach((item, index) => {
          item.STT = index + 1; // Thêm trường STT
        });
      } catch (error) {
        console.error('Error fetching history list:', error);
      }
    },
    async refreshData() {
      await this.getHistoryList();
    },
  },
};
</script>
<style>

</style>
