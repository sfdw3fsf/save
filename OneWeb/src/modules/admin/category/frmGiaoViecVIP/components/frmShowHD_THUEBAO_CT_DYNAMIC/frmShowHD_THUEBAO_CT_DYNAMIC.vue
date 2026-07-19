<script>
import { defineAsyncComponent } from '@vue/composition-api'
import api from '../../api'

export default {
  components: {
    frmDynamic_Hd_thuebao_ct: defineAsyncComponent(() => import('@/modules/contract/setup/InstallNewSubs/Popups/frmDynamic_Hd_thuebao_ct/frmDynamic_Hd_thuebao_ct.vue'))
  },
  props: {
    dulieu: {
      type: [Object],
      require: true
    }
  },

  data() {
    return {
      thongtin_hienthi_header_ct: [
        // {
        //   ten_hienthi: 'Cấu hình Worker',
        //   field_name: 'THONGTIN_XT'
        // },
        // {
        //   ten_hienthi: 'Cấu hình namespace',
        //   field_name: 'THONGTIN_CT'
        // },
      ],
      list_dulieu: [
        // {
        //   "vloaitb_id": 399,
        //   "hdtb_id": 566240,
        //   "vappend_name": "THONGTIN_XT",
        //   "enableEdit": false,
        //   "vfield_detail": {
        //     "vloaitb_id": 399,
        //     "vopt_list": "00000",
        //     "vfield_name_cha": "THONGTIN_XT"
        //   },
        //   "vtinh_id": 4
        // },
        // {
        //   "vloaitb_id": 399,
        //   "hdtb_id": 566240,
        //   "vappend_name": "THONGTIN_CT",
        //   "enableEdit": false,
        //   "vfield_detail": {
        //     "vloaitb_id": 399,
        //     "vopt_list": "00000",
        //     "vfield_name_cha": "THONGTIN_CT"
        //   },
        //   "vtinh_id": 4
        // }
      ],

      isShow: false
    }
  },

  methods: {
    async loadData() {
      this.list_dulieu = []
      this.thongtin_hienthi_header_ct.forEach(e => {
        let dulieu_ct = JSON.parse(JSON.stringify(this.dulieu)); // Tạo bản sao sâu
        dulieu_ct.vappend_name = e.field_name;
        dulieu_ct.vfield_detail.vfield_name_cha = e.field_name;
        console.log('dulieu_ct')
        console.log(dulieu_ct.vfield_detail.vfield_name_cha)
        this.list_dulieu.push(dulieu_ct);
      });
    },

    async sp_lay_tt_hd_thuebao_ct_dynamic() {
      try {
        // this.loading = true
        let obj = {
          "vphanvung_id": this.dulieu.vtinh_id,
          "loaitb_id": this.dulieu.vloaitb_id
        }
        let rs =  await api.sp_lay_tt_hd_thuebao_ct_dynamic(this.axios, obj)
        // console.log(rs.data.error_code == 'BSS-00000000')
        if(rs.data.error_code == 'BSS-00000000') {
          this.thongtin_hienthi_header_ct = rs.data.data
          console.log('this.thongtin_hienthi_header_ct')
          console.log(this.thongtin_hienthi_header_ct)
          return
        }

        return this.$toast.error('Không có dữ liệu');
      }
      catch (error) {
        console.error('Error:', error)
      }
      finally {
        // this.loading = false
      }
    },

    test() {
      this.loadData()
      // console.log(123123)
      console.log(this.dulieu)
      console.log(this.list_dulieu)
    }
  },

  async mounted() {
    debugger
    await this.sp_lay_tt_hd_thuebao_ct_dynamic()
    await this.loadData()
    this.isShow = true
  },
  beforeDestroy() {
    console.log("beforeDestroy: Component is about to be destroyed.");
    this.isShow = false;
  }

}
</script>

<template src="./frmShowHD_THUEBAO_CT_DYNAMIC.html">

</template>

<style scoped>

</style>
