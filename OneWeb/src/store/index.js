import Vue from 'vue'
import Vuex from 'vuex'

// plugin local storage
import VuexPersistence from 'vuex-persist'
import logger from '@/plugins/logger'

import { user } from './user'
import { qltn } from '@/modules/QLTN/store'

import { qllv } from './qllv'
import { interDividePointInstallTSL } from './InterDividePointInstallTSL'
import { admin } from './admin'

import { cskh } from '@/modules/CSKH/store'
import { qlvt } from '@/modules/QLVT/store'
import { ecms } from '../modules/ECMS/store/'
import { qlsc } from '../modules/QLSC/store/'
import { cabman } from '../modules/CABMAN/store/'
import { mapStaffAndCareer } from './mapStaffAndCareer'
import { mapDslamTransfer } from './DslamTransfer'
import { changeFreeIPInfo } from './ChangeFreeIPInfo/index'
import { classifyService } from './ClassifyService'
import { dvvt } from './dvvt'
import { lhtb } from './lhtb'
import { receiveProfile } from './receiveProfile'
import { khieunai } from '../modules/EXTS/KHIEUNAI/store/'
import { liquidate } from './liquidate'
import { restoreLiquidate } from './RestoreLiquidate'
import { deviceRecallCommission } from './DeviveRecallCommission'
import { promotion } from './Promotion'
import { commissionRate } from './CommissionRate'
import { processServiceByTeleStation } from './ProcessServiceByTeleStation'
import { street } from './Street/index'
import { prefixOfCounty } from './PrefixOfCounty'
import { mapPreferentialSubscriber } from './PreferentialSubscriber/index'
import { mapStateLocation } from './State/index'
import { contractorRole } from './ContractorRole'
import { complaintTopic } from './ComplaintTopic'
import { changeModem } from './ChangeModem'
import { storeCat } from './storeCat/index'
import { mapChangeSubsTypeByInstallOperation } from './ChangeSubsTypeByInstallOperation'
import { feeCollectionFactor } from './FeeCollectionFactor/index'
import { changeNEParameter } from './changeNEParameter/index'
import { mapProject } from './Project'
import { siteArea } from '../modules/admin/category/SiteArea/store/index'
import { portParameters } from './UpdatePortParameters/index'
import { mapChangeService } from './ChangeService'
import { listSubcribersNotReceiveDamage } from './ListSubcribersNotReceiveDamage/index'
import { handoverIncident } from './HandoverIncident/index'
import { installReplaceRecallWifi } from './InstallReplaceRecallWifi/index'
import { UpdateHouseholdInformation } from "./UpdateHouseholdInformation/index";
import { updateDeviceType } from './UpdateDeviceType'
import { SearchMaintenanceSlipNotDone } from "./SearchMaintenanceSlipNotDone/index";
import {KySmartCA} from './KySmartCA/index';
import { CompleteProfileITVAS } from './CompleteProfileITVAS/index'
import { ipcc } from './ipcc/index'
import {DuyetHangLoatV2} from './DuyetHangLoatV2/index'

Vue.use(Vuex);

const vuexPersist = new VuexPersistence({
  key: 'my-app',
  //storage: window.localStorage

  // add config to which modules need to keep state
  modules: ['user', 'admin']
})

const debug = process.env.NODE_ENV !== 'production'

export const store = new Vuex.Store({
  modules: {
    user,
    KySmartCA,
    CompleteProfileITVAS,
//    qllv,
    admin,
    qltn,
    qlvt,
    cskh,
    ecms,
    qlsc,
    cabman,
    mapStaffAndCareer,
    mapDslamTransfer,
    changeFreeIPInfo,
    classifyService,
    interDividePointInstallTSL,
    dvvt,
    lhtb,
    receiveProfile,
    khieunai,
    liquidate,
    restoreLiquidate,
    deviceRecallCommission,
    promotion,
    commissionRate,
    processServiceByTeleStation,
    mapPreferentialSubscriber,
    prefixOfCounty,
    street,
    mapStateLocation,
    contractorRole,
    complaintTopic,
    changeModem,
    storeCat,
    mapChangeSubsTypeByInstallOperation,
    feeCollectionFactor,
    changeNEParameter,
    mapProject,
    siteArea,
    portParameters,
    mapChangeService,
    listSubcribersNotReceiveDamage,
    handoverIncident,
    installReplaceRecallWifi,
    UpdateHouseholdInformation,
    updateDeviceType,
    SearchMaintenanceSlipNotDone,
    ipcc,
    DuyetHangLoatV2
  },
  strict: debug,
  plugins: debug ? [logger(), vuexPersist.plugin] : [vuexPersist.plugin],
  state: {
    header: {
      title: '',
      list: []
    }
  },
  mutations: {
    setHeader(state, header) {
      state.header = header
    }
  },
  actions: {
    setHeader(context, header) {
      context.commit('setHeader', header)
    }
  }
})
