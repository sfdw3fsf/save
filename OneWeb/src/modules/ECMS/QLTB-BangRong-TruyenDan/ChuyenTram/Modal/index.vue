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
                    this.dataSelected.dslam_id = this.data.dslam_id
                    this.dataSelected.type = this.data.type
                    this.dataSelected.ttvt = null
                    this.dataSelected.toKT = null
                    this.dataSelected.tramvt_id = null
                    await this.getTTVT()
                }catch(er){}
                finally{
                }
            },

            getDsTuRack: async function() {
                try {
                    let rs = await this.$root.context.post('/web-ecms/danhmuc/turack/ds-turack-theo-donvi-va-kieutu', {
                        kieutu: this.type == 'EWSD' ? 1 : 2,
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
                    if (['FRAME', 'ODF', 'EWSD'].indexOf(this.dataSelected.type) >= 0) {
                        this.dataSelected.turack_id = this.dataSelected.tuId[0]
                        if (this.dataSelected.tuId.length > 1) {
                            this.$toast.error('Loại thiết bị này chỉ được chọn 1 tủ rack.')
                            return
                        }
                    }
                    else {
                        this.dataSelected.list_tu_id = this.dataSelected.tuId
                        this.dataSelected.turack_id = -1
                    }
                    let rs = await this.$root.context.post(
                        "/web-ecms/danhmuc/chuyentram/chuyentramdslam", this.dataSelected
                    )
                    if (rs.error_code === 'BSS-00000000') {
                        this.$toast.success('Cập nhật thành công.')
                        let obj = {
                            turack_ids: this.dataSelected.tuId,
                            dslam_id: this.dataSelected.dslam_id,
                            type: this.dataSelected.type,
                            ttvt: this.dataSelected.ttvt,
                            toKT: this.dataSelected.toKT,
                            tramvt_id: this.dataSelected.tramvt_id
                        }
                        this.$emit('loadTree', obj)
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

