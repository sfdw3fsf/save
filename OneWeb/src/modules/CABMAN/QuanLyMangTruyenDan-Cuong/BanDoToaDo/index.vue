<template src="./index.template.html"></template>
<script>
  import ModalChonToaDoExt from './ChonToaDo'
export default {
  components: {ModalChonToaDoExt},
  data: function () {
    return {
      modalShow: false,
      thuebao: {}
    };
  },
  created: function () {
    this.thuebao.TOADO_LD = "20.839756339478544;106.60154969240723"
  },
  methods: {
    async chonToaDo () {
      let p = { lat: parseFloat(this.thuebao.TOADO_LD.split(';')[0]), lng: parseFloat(this.thuebao.TOADO_LD.split(';')[1]) }
      this.$refs.ModalChonToaDoExt.show(p).then((result) => {
        if (result.ok) {
          
          this.thuebao.TOADO_LD = result.data.lat + ';' + result.data.lng
          this.luuToaDo()
          console.log('chonToaDo',this.thuebao.TOADO_LD)
        }
      })
    },
    luuToaDo () {
      console.log('luuToaDo', this.thuebao.TOADO_LD)
      let toado = this.thuebao.TOADO_LD
      this.$confirm(`Cập nhật tọa độ ?`,
        'Cập nhật tọa độ',
        {
          confirmButtonText: 'Có',
          cancelButtonText: 'Không'
        }).then(() => {
        try {
          this.thuebao.TOADO_LD = toado
        } catch (error) {
          this.$root.$toast.error('Cấu trúc tọa độ không đúng định dạng.')
          return false
        }
      }).catch(() => {})
    },
    getDiachi () { 
      return this.thuebao.DIACHI_LD
    },
    getPosition () {
      if (Object.keys(this.thuebao).length > 0 && this.thuebao.TOADO_LD !== null) {
        let p = { lat: parseFloat(this.thuebao.TOADO_LD.split(';')[0]), lng: parseFloat(this.thuebao.TOADO_LD.split(';')[1]) }
        return p
      } else {
        return { lat: 0, lng: 0 }
      }
    },
    getToaDo () {
      console.log('getToaDo ',this.thuebao.TOADO_LD)
      return this.thuebao.TOADO_LD
    },
  },
};
</script>
