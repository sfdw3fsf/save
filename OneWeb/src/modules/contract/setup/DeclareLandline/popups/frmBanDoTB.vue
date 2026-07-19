<template>
    <ModalChonToaDo
      idModalChonToaDo="ModalChonToaDo"
      ref="ModalChonToaDo"
      @XacNhanToaDo="XacNhanToaDo"
      :position="map_position"
      :diachi="map_info.dia_chi"
      :ten_kc="map_info.title"
      :mode="mode"
    ></ModalChonToaDo>
</template>
<script>
import ModalChonToaDo from '@/modules/CABMAN/XetDuyetPhanTuMangHoDanThuThap/ChonToaDo'
export default {
    components: {
        ModalChonToaDo
    },
    data () {
        return {
            map_position : {},
            map_info: {
                dia_chi: "",
                title: ""
            },
            mode: 1
        }
    },
    methods: {
        XacNhanToaDo(data) {
            this.$emit("XacNhanToaDo", data)
        },
        show() {
            this.$bvModal.show('ModalChonToaDo')
            if(this.mode == 2) {
                this.$nextTick(() => {
                    setTimeout(() => {
                        this.$refs.ModalChonToaDo.$refs.BanDo.searchLocation(this.map_info.dia_chi)
                    }, 500)
                })
            }
        }
    }
}
</script>