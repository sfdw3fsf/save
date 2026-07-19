<template src="./index.html"></template>
<style src="./styles.scss" scoped></style>

<script>
export default {
  data() {
    return {
      isDisableSave: true,
      isDisableNew: false,
      isDisableUpdate: false,
      isDisableDelete: false,
      isDisableItems:true,
      dsSelected: [],
      dsThietBiTrungGian: [],
      form: {
        pId: 0,
        pTen: "",        
        pGhiChu: "",
        pHieuLuc: 1
      },
    }
  },
  mounted: async function () {
    await this.getDanhSach();
  },

  methods: {
    getDanhSach: async function () {
      try {
        const rs = await this.$root.context.get(
          `/web-ecms/danhmuc/thietbi-trunggian/danhsach`
        );
        if (rs.data)
          this.dsThietBiTrungGian= rs.data;
      } catch (error) { }
    },
    setDanhSach: async function () {
      try {
        if (!this.isDisableSave || !this.isDisableUpdate) {
          const rs = await this.$root.context.post(
            `/web-ecms/danhmuc/thietbi-trunggian/capnhat`, this.form
          );
          if (rs.error == "200")
            this.$toast.success("Cập nhật thành công")
          await this.getDanhSach();
        }
      } catch (error) { this.$toast.error("Có lỗi xảy ra") }
    },
async  onDelete(){
  if (this.isDisableDelete) return;
      this.$confirm(
            'Bạn có muốn xoá các danh mục đã chọn ?',
            "Danh mục thiết bị",
            {
              confirmButtonText: "Đồng ý",
              cancelButtonText: "Không",
            }
          )
            .then(async () => {
              await this.deleteDanhSach();
            })

    },
    deleteDanhSach: async function () {
                try {
                      if (!this.isDisableDelete) {
                        let count = 0;

                        for (let item of this.dsSelected) {
                          const rs = await this.$root.context.post(
                            `/web-ecms/danhmuc/thietbi-trunggian/xoa/` + item
                          );
                          if (rs.error == "200")
                            count++;
                        }

                        this.$toast.success("Xóa phiếu thành công " + count + " phiếu")

                      }
                    } catch (error) { this.$toast.error("Có lỗi xảy ra") }
                    finally {
                      await this.getDanhSach();
                    }
  
    },
    onSelectRowCheckbox(itemsCheck) {
      console.log('check', itemsCheck);
      this.dsSelected = itemsCheck;
    },
    onNew() {
      if (!this.isDisableNew) {
        this.form.pId = 0,
          this.form.pTen = "",          
          this.form.pGhiChu = "",
          this.form.pHieuLuc = 1
        this.isDisableNew = true;
        this.isDisableDelete = true;
        this.isDisableSave = false;
        this.isDisableUpdate = true;
        this.isDisableItems=false;
      }
    },
    onUpdate() {
      if (!this.isDisableUpdate) {        
        this.isDisableNew = true;
        this.isDisableDelete = true;
        this.isDisableSave = false;
        this.isDisableUpdate = true;
        this.isDisableItems=false;
      }
    },
    onSelectRowChanged(selected) {
      this.isDisableSave = true,
        this.isDisableNew = false,
        this.isDisableUpdate = false,
        this.isDisableDelete = false,

        this.form.pId = selected.data.ID;
      this.form.pTen = selected.data.TEN;
      this.form.pMaHSX = selected.data.MA_HSX;
      this.form.pGhiChu = selected.data.GHI_CHU;
      this.form.pHieuLuc = selected.data.HIEU_LUC;
      this.isDisableItems=true;

    }

  },
  watch: {
  }

};

</script>