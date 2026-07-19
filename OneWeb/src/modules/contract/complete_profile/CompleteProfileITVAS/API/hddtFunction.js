import moment from "moment";
import api from './CompleteProfileAPI'
import {YEUCAU_LAY_DULIEU_TOKEN, YEUCAU_DUYET_CMS, CA_CHOXULY, GUI_FILE_HOSO_CMS} from './ca_objects'
import {LOAI_HOPDONG} from '@/constants'
import axios from 'axios'
export default {
  async DongBoIdHeThongHDDT($this, data)
  {
    try {
      let kq = await api.laythongtin_hddt(axios, data.mst);
    }
    catch (ex)
    {
      throw ex;
    }
  }
}
