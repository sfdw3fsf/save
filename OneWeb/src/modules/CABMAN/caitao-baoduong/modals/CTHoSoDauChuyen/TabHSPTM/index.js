import uc_ptm_v3 from '../../../frmThemPhanTuMang/UC_PTM_V3'
export default function(args, parent) {
  return {
    args: {...args},
    components:{uc_ptm_v3},
    template: `
      <uc_ptm_v3 ref="uc_ptm_v3"></uc_ptm_v3>
    `,
    data() {
      return {
      }
    },
    async mounted() {
      if (parent.isNew) {
        await this.$refs.uc_ptm_v3.UC_PTM_V3_Load()
      } else {
        if (args.data) {
          await this.$refs.uc_ptm_v3.UC_PTM_V3(args.data.dt, args.data.vloaitb_id, args.data.vnhomtbi_id, args.data.vhsptm_id)
        }
      }
    },
    computed:{
      componentUC_PTM_V3() {
        return this.$refs.uc_ptm_v3
      }
    },
    methods:{
      lay_ds() {
        return this.$refs.uc_ptm_v3.lay_ds()
      },
      get_valid() {
        return this.$refs.uc_ptm_v3.get_valid()
      },
    }
  }
}
