<template src="./index.html"></template>
<script>
    import { actionName, statePropertyName } from '../../../ECMS/store/QuanLyModule'
    import { mapActions, mapState } from 'vuex'
    import Modal from '../../../ECMS/components/QuanLyModule/Modal'

    export default {
      components: {Modal },
      name: 'QuanLyModule',
        computed: {
             ...mapState('ecms/quanLyModule', statePropertyName)
        },
        destroyed () {
        },
        methods: {
             ...mapActions('ecms/quanLyModule', actionName)
        },
        created () {
        this.clearState()
        this.createApiManager()
        },
        async mounted () {
        },
        data () {
            return {
            }
        }
    }
</script>
