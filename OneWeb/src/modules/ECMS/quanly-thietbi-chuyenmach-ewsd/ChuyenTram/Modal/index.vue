<template src="./index.popup.html"></template>
<script>

    import { MultiSelectPlugin, CheckBoxSelection } from "@syncfusion/ej2-vue-dropdowns"

    export default {
        components: {},
        provide: {
            multiselect: [CheckBoxSelection]
        },

        name: 'ModalChuyenTram',
        props: ["modalId", "data"],
        // props: {
        //   // modalShow: Boolean,
        //   // dsTonghop: Object
        // },
        computed:{

        },
        data: function () {
            return {
                dataSelected:{
                    dslam_id:null,
                    ttvt:null,
                    toKT:null,
                    tramvt_id:null,
                    turack_id: null,
                    tuId: null,
                    type:null
                },
                dsTTVT:[],
                dsToKT:[],
                dsTramTB:[],
                dsTuRack:[]
            };
        },
        mounted: function () {

        },
        created: function () {
        },
        methods: {
            showModal: async function () {
                console.log(this.data)
                try{
                    this.dataSelected.dslam_id = 135//this.data.dslam_id
                    this.dataSelected.type = 'ODF_______'//this.data.type
                    await this.getTTVT()
                    if (this.dataSelected.ttvt)
                        await this.getToKyThuat()
                    if (this.dataSelected.toKT)
                        await this.getTramTB()
                    if (this.dataSelected.tramvt_id)
                        await this.getDsTuRack()
                }catch(er){}
                finally{
                }
            },

            getDsTuRack: async function() {
                try {
                    let rs = await this.$root.context.post('/web-ecms/danhmuc/turack/ds-turack-theo-donvi-va-kieutu', {
                        kieutu: this.type == 'EWSD______' ? 1 : 2,
                        donvi_id: this.dataSelected.tramvt_id
                    })
                    this.dsTuRack = rs.data
                    this.dataSelected.tuId = null
                } catch (error) {

                }
            },

            getTTVT: async function () {
                try {
                    let rs = await this.$root.context.get(
                        "/web-cabman/donvi/ds-ttvt-phanvung"
                    )
                    this.dsTTVT = rs.data
                } catch (error) {
                } finally {
                }
            },

            getToKyThuat: async function () {
                try {
                    let rs = await this.$root.context.get(
                        "/web-cabman/donvi/ds-to-vienthong/" + this.dataSelected.ttvt
                    )
                    this.dsToKT = rs.data
                } catch (error) {
                } finally {
                }
            },

            getTramTB: async function () {
                try {
                    let rs = await this.$root.context.get(
                        "/web-cabman/donvi/ds-tramtb/" + this.dataSelected.toKT
                    )
                    this.dsTramTB = rs.data
                } catch (error) {
                } finally {
                }
            },

            onChangeTTVT: async function () {
                await this.getToKyThuat()
            },

            onChangeToKT: async function () {
                await this.getTramTB()
            },

            onChangeTramTB: async function () {
                await this.getDsTuRack()
            },

            ghiLai: async function() {
                try {
                    this.$root.showLoading(true)
                    console.log(this.dataSelected.tuId)
                    if (this.dataSelected.tuId == null) {
                        this.$toast.error('Chưa chọn tủ rack.')
                        return
                    }
                    if (['FRAMETD', 'ODF', 'EWSD'].indexOf(this.dataSelected.type) >= 0) {
                        this.dataSelected.turack_id = this.dataSelected.tuId[0]
                        if (this.dataSelected.tuId.length > 1) {
                            this.$toast.error('Loại thiết bị này chỉ được chọn 1 tủ rack.')
                            return
                        }
                    }
                    let rs = await this.$root.context.post(
                        "/web-ecms/danhmuc/chuyentram/chuyentramdslam", this.dataSelected
                    )
                    if (rs.error_code === 'BSS-00000000') {
                        this.$toast.success('Cập nhật thành công.')
                    } else {
                        this.$toast.error('Cập nhật thất bại.')
                        //this.getdsDongBo()
                    }
                } catch (error) {
                    console.log(error)
                    this.$toast.error('Cập nhật thất bại.')
                } finally {
                    this.$root.showLoading(false)
                }
            }

        },
    }
</script>

