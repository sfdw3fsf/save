<template src="./index.html"></template>

<script>
export default {
  name: 'KhachHangIDC',
  props: {
    currentItem: {
      type: Object,
      required: true
    },
  },
  data: function () {
    return {
      khachHangList: [],
      columns: [            
                  {fieldName: 'STT', headerText: 'STT', textAlign: 'Center', width: 150},
                  {fieldName: 'MA_KH', headerText: 'Mã khách hàng', textAlign: 'Left', width: 150},
                  {fieldName: 'TEN_KH', headerText: 'Tên khách hàng', textAlign: 'Left', width: 150},
                  {fieldName: 'MA_HD', headerText: 'Mã HD', textAlign: 'Left', width: 200},
                  {fieldName: 'THOIHAN_HD', headerText: 'Thời gian hợp đồng', textAlign: 'Left', width: 150},
                  {fieldName: 'SLA', headerText: 'Cam kết SLA', textAlign: 'Left', width: 150},
                  {fieldName: 'THAO_TAC', headerText: 'Thao tác', textAlign: 'Center', width: 150, template: this.acctionColumn(this)
                  }
              ]
    };
  },
  components: {
  },
  computed: {
   
  },
  watch: {
    'currentItem.id': async function(newVal) {
      if (newVal && newVal !== 0) {
        await this.getKhachHangList()
      } else {
        this.khachHangList = []
      }
    }
  },
  created: async function () {
    await this.getKhachHangList()
  },
  mounted: async function () {    
  },
  methods: {
    async getKhachHangList() {
      try {
        const response = await this.$root.context.post('/web-ecms/idc/hatang-cntt/thiet-bi/danh-sach-khach-hang', {id: this.currentItem.id});
        this.khachHangList = response.data || [];
        this.khachHangList.forEach((item, index) => {
          item.STT = index + 1; // Thêm trường STT
        });
      } catch (error) {
        console.error('Error fetching nghiệp vụ list:', error);
      }
    },
    acctionColumn(parent) {
      return function() {
        return {
          template: {
            template: `<div class="action-buttons">
                <button class="btn btn-sm btn-primary" @click="view()">
                  Xem chi tiết hợp đồng
                </button>
              </div>`,
            methods: {
              view() {
                parent.viewItem(this.data)
              }
            }
          }
        }
      }
    },

    viewItem(item) {
      this.$root.context.showModal({
        title: 'Chi tiết hợp đồng khách hàng',
        width: '800px',
        component: 'ChitietThietbiIdcKhachhang',
        params: {
          currentItem: item
        }
      });
    },
    async refreshData() {
      await this.getKhachHangList();
    },

  },
};
</script>
<style>

</style>
