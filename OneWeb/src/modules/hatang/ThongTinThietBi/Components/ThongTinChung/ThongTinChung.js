import { CUSTOMER_TYPES, DEFAULT_OBJ, LOAI_THIET_BI, PORT_TYPE } from '../../test'
import moment from 'moment'

export default {
  name: 'ThongTinChung',
  props: {
    currentItem: {
      type: Object,
      default: () => {}
    }
  },
  data() {
    return {
      deviceTypeList: [],
      customerTypeList: [],
      equipmentCategoryList: [],
      IPAddressList: [],
      parentDeviceList: [],
      discTypeList: [],
      thongTinChungControls: {
        txt_ten: {
          enabled: false,
          invalid: false
        },
        txt_kyhieu: {
          enabled: false
        },
        txt_mathietbi_infra: {
          enabled: false
        },
        select_loaithietbi_id: {
          enabled: false,
          invalid: false
        },
        select_loaikhachhang: {
          enabled: false
        },
        txt_serial_number: {
          enabled: false
        },
        checkbox_hsm: {
          enabled: false
        },
        checkbox_hieu_luc: {
          enabled: false
        },
        txt_ghi_chu: {
          enabled: false
        },
        select_loaitudia_id: {
          enabled: false,
          invalid: true
        },
        select_chungloaithietbi_id: {
          enabled: false,
          invalid: true
        },
        select_ip_ids: {
          enabled: false
        },
        txt_part_number: {
          enabled: false
        },
        txt_model: {
          enabled: false
        },
        select_thietbicha_id: {
          enabled: false
        }
      },
      normalizer(node) {
        return {
          id: node.ID,
          label: node.IP
        }
      }
    }
  },
  computed: {},
  watch: {},
  async created() {},
  methods: {}
}
