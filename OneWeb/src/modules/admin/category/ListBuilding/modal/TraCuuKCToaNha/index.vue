<template src="./index.html"></template>
<script>
import ComboboxGrid from "../../../../../../components/Controls/ComboboxGrid"
export default {
    props: ['duan_id', 'toanha_id'],
    components: {ComboboxGrid},
    data() {
        return {
            cbo_DuAn: {
                items: [],
                selected: null
            },
            cbo_ToaNha: {
              items: [],
              selected: null
            },
            dsThongTinKC: [],
            numberOpenPopup: 0, // số lần mở popup,
        }
    },

    methods: {
        async handleShowModal() {
          this.numberOpenPopup = 0; // reset
          this.dsThongTinKC = []
          this.duan_id = this.duan_id ? this.duan_id : 0
          this.toanha_id = this.toanha_id ? this.toanha_id : 0

          await Promise.all([
            this.traCuuDuAn(),
            this.traCuuToaNha(this.duan_id)
          ]);

          this.cbo_DuAn.selected = this.duan_id
          this.cbo_ToaNha.selected = this.toanha_id
        },
        async traCuuDuAn() {
            try {
                this.loading(true)
                let response = await this.$root.context.get(`web-toanha/tracuu-tb-toanha/tracuu_duan`)
                if (response.error_code === 'BSS-00000000' && response.data != null) {
                    this.cbo_DuAn.items = response.data
                } else {
                    this.cbo_DuAn.items = []
                }
            } catch(err) {
                this.cbo_DuAn.items = []
            } finally {
                this.loading(false)
            }
        },
        async traCuuToaNha(duan_id) {
          try {
            this.loading(true)
            let response = await this.$root.context.get(`web-toanha/tracuu-tb-toanha/tracuu_toanha/${duan_id}`)
            if (response.error_code === 'BSS-00000000' && response.data != null) {
              this.cbo_ToaNha.items = response.data
              this.cbo_ToaNha.selected = this.toanha_id;
              this.onTimKiem();
            } else {
              this.cbo_ToaNha.items = []
            }
          } catch(err) {
            this.cbo_ToaNha.items = []
          } finally {
            this.loading(false)
          }
        },
        async duAnSelected(args){
          this.numberOpenPopup++;
          if (args != null && this.numberOpenPopup > 1) {
            await this.traCuuToaNha(args.duan_id)
            //this.cbo_ToaNha.selected = this.toanha_id
          }
        },
        toaNhaSelected(args) {
            if (args != null) {
                //this.cbo_ToaNha.selected = args.toanha_id
            }
        },
        async onTimKiem() {
            if (this.cbo_DuAn.selected == 0 && this.cbo_ToaNha.selected == 0) {
                this.$toast.error("Bạn chưa nhập thông tin tìm kiếm!")
                return
            }
            await this.traCuuKCToaNha()
        },
        async traCuuKCToaNha() {
          try {
            this.loading(true)
            let apiBody = {
              vduan_id: this.cbo_DuAn.selected,
              vtoanha_id: this.cbo_ToaNha.selected
            }
            console.log("body: ", apiBody)
            let url = `web-toanha/tracuu-kc-toanha/tracuu_kc_toanha`
            let response = await this.$root.context.post(url, apiBody)
            if (response.error_code === 'BSS-00000000' && response.data != null) {
              this.dsThongTinKC = response.data
            } else {
              this.dsThongTinKC = []
            }
          } catch(err) {

          } finally {
            this.loading(false)
          }
        },
        onXuatExcel() {
            if (this.dsThongTinKC.length <= 0) {
                this.$toast.error("Chưa có danh sách để xuất excel!");
            } else {
                let excelExportProperties = {
                    fileName:"Danh-sach-ket-cuoi-toa-nha.xlsx",
                    dataSource: this.dsThongTinKC
                };
                this.$refs.thongTinKCToaNhaGrid.excelExport(excelExportProperties);
            }
        },
        close() {
            console.log('close')
            this.numberOpenPopup = 0; // reset
            this.$bvModal.hide("tracuu-kc-toanha")
        }
    },
    destroyed() {

    },
}
</script>
