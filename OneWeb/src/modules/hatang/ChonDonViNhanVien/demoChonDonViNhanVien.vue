<template src="./demoChonDonViNhanVien.html">
</template>

<script>
import ChonDonViNhanVien from "./index";

export default {
    data() {
        return {
           phanvung_id: parseInt(this.$auth.getPhanVungID()),
           donViId: 0,
           tenDonVi: "",
           chonNhanVien: false,
           nhanVienId: 0,
           tenNhanVien: "",
           dsOptForm: [
           { ID: 1, TEN: "Chọn đơn vị" },
           { ID: 2, TEN: "Chọn nhân viên"}
           ],
           optFormId: 1,

        }
    },
    components: {
        ChonDonViNhanVien
    },
    methods: {
        Test(){
          this.chonNhanVien=false;
          if (this.optFormId==2)
            this.chonNhanVien=true;
          this.$refs.refChonDonViNhanVien.dialogOpen();


        },
        xacNhanChonDVNV(data){
            console.log(data , " = xacNhanChonDVNV")
            this.donViId=data.DONVI_ID;
            this.tenDonVi=data.TEN_DV;
            this.nhanVienId=data.NHANVIEN_ID;
            this.tenNhanVien=data.TEN_NV;
        }

    },
    created: async function (){



    }
}
</script>
