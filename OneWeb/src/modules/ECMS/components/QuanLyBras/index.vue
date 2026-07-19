<template src="./index.html"></template>
<script>
    import { actionName, statePropertyName } from '../../store/QuanLyBras'
    import { mapActions, mapState } from 'vuex'
    import Modal from '../../../ECMS/components/QuanLyBras/modal'

    export default {
      components: {Modal },
      name: 'QuanLyBas',
        computed: {
             ...mapState('ecms/quanLyBras', statePropertyName)
        },
        destroyed () {
        },
        methods: {
             ...mapActions('ecms/quanLyBras', actionName)
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
