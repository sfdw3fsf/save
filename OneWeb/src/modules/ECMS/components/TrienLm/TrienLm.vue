<template src="./TrienLm.html"></template>
<style scoped src="./TrienLm.scss"></style>
<script>
    import breadcrumb from '@/components/breadcrumb'
    import Modal from './Modal'
    import { getterName, actionName } from '../../store/LoaiTu'
    import { mapActions, mapState, mapGetters } from 'vuex'
    import { dateFormat } from '@/utils/format'

    export default {
        components: { breadcrumb, Modal },
        name: 'TrienLm',
        computed: {
            ...mapState('ecms', ['loaiTu']),
            ...mapGetters('ecms/loaiTu', getterName)
        },
        methods: {
            ...mapActions('ecms/loaiTu', actionName),
            showLoading () {
                this.loading(true)
            },
            showNotification () {
                this.$toast.success('Success')
                this.$toast.warning('Warning')
                this.$toast.error('Error')
                this.$toast.info('Info')
            },
            chuyenTrang () {
                this.$router.push({name: 'QuanLyPrefixIPCuaBras'})
            },
            async loadDsLoaiTu () {
                try {
                    this.loading(true)
                    await this.lietKeLoaiTu()
                } catch(error) {
                    console.log(error)
                } finally {
                    this.loading(false)
                }
            },
            clearDsLoaiTu() {
                this.setDsLoaiTu([])
            },
            onPageChange(pageIndex) {
                this.pageIndex = pageIndex
            },
            onPageChangeDsLoaiTu(pageData) {
                this.dsLoaiTuPaging = pageData
            }
        },
        async mounted () {

            this.$store.dispatch('setHeader', {
                title: 'TrienLM demo',
                list: [
                    { name: 'TrienLM', link: { name: 'Ui.cards' } },
                    {
                        name: 'Demo',
                        link: { name: 'Ui.buttons' }
                    }
                ]
            })

            await this.loadDsLoaiTu()
        },
        data () {
            return {
                header: {
                    title: 'TrienLM demo',
                    list: [
                        { name: 'TrienLM', link: { name: 'Ui.cards' } },
                        {
                            name: 'Demo',
                            link: { name: 'Ui.buttons' }
                        }
                    ]
                },
                totalRow: 50,
                dsLoaiTuPaging: [],
                pageIndex: 0,
                dateValue: '16/06/2021',
                dateValue1: '18/06/2021',
                dateConfig: {
                    altInput: true,
                    dateFormat: dateFormat,
                    altFormat: dateFormat       
                },
                comboValue: 2,
                comboDataSource: [{ value: 1, text: "a" }, { value: 2, text: "b" }, { value: 3, text: "c" }],
                checkBoxValue: true,
                multiCheckBoxValue: [],
                tableDataSource: [{name: 'a', email: 'abc@vnpt.vn', address: '344 2/9 đn'}, {name: 'b', email: 'abc@vnpt.vn', address: '344 2/9 đn'}],
                selectTreeValue: null,
                options: [ 
                    {
                        id: 'a',
                        label: 'a',
                        children: [ 
                            {
                                id: 'aa',
                                label: 'aa',
                            }, 
                            {
                                id: 'ab',
                                label: 'ab',
                            } 
                        ],
                    }, 
                    {
                        id: 'b',
                        label: 'b',
                    }, 
                    {
                        id: 'c',
                        label: 'c',
                    } 
                ],
            }
        }
    }
</script>