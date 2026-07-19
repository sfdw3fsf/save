// Khai bao store của module Diem chia trung gian

import { interDividePointInstallTSLCommon } from './commons/index'
import { tocDoKenhCommon } from './TocDoKenh/commons/index'
import { loaiHDCommon } from './LoaiHD/commons/index'
import { lyDoTraCommon } from './LyDoTra/commons/index'
import { donViCommon } from './DonVi/commons/index'
import { nhanVienTheoDonViCommon } from './NhanVienTheoDonVi/commons/index'

export const interDividePointInstallTSL = {
  modules: {
    interDividePointInstallTSLCommon,
    tocDoKenhCommon,
    loaiHDCommon,
    lyDoTraCommon,
    donViCommon,
    nhanVienTheoDonViCommon,
  }
}
