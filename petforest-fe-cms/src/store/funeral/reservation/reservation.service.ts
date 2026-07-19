import { MaterialNameRESP } from '@/store/material/response/material.response';
import { DATE_FORMAT } from '@/utils/constants/date.constant';
import { mappingNewAdditionService } from '@/utils/constants/funeral/reservation-detail.constant';
import {
  DETAIL_FUNERAL_RESERVATION_OPTIONS,
  DETAIL_FUNERAL_STATUS_OPTIONS,
  DETAIL_GUARDIAN_BOOKING_OPTIONS,
  DETAIL_GUARDIAN_VISIT_OPTIONS,
  DETAIL_PET_GENDER_OPTIONS,
  DETAIL_PET_REGISTATION_OPTIONS,
  DETAIL_SERVICE_ATTENDANCE_OPTIONS,
  DETAIL_SERVICE_LUCETTE_JEWELRY_OPTIONS,
  DETAIL_SERVICE_NEW_AUDITION_COLOR_OPTIONS,
  DETAIL_SERVICE_TRANSPORT_ACCOMPANIED_STATUS_OPTIONS,
} from '@/utils/enums/funeral/reservation-mngt.enums';
import { getTodayRange, getYearFromTodayRange } from '@/utils/helpers/date-time.helper';
import { parseTimeStampToDayjs, parseTimestampToTime } from '@/utils/parser/date-time.parser';
import { numberOrUndefined, numberOrZero, orString, orUndefined } from '@/utils/parser/io.parser';
import { isEmpty } from 'lodash';
import {
  AdditionalCostDTO,
  ColumbariumDTO,
  FuneralInfoDTO,
  FuneralMNGTReservationListDTO,
  GuardianInfoDTO,
  HospitalInfoDTO,
  LucetteDTO,
  NewAdditionDTO,
  PartnerCompanyDTO,
  PaymentMethodDTO,
  PetInfoDTO,
  ReservationDetailDTO,
  SettlementDetailDTO,
  SuppliesDTO,
} from './dto/reservation.dto';

import { LUCETTE_CATEGORY } from '@/utils/enums/funeral/lucette-mngt.enums';
import dayjs from 'dayjs';
import {
  ReservationListFilter,
  ReservationUpdateColumbariumREQ,
  ReservationUpdateDetailREQ,
  ReservationUpdateFunaralInformationREQ,
  ReservationUpdateGuardianInformationREQ,
  ReservationUpdateHospitalInformationREQ,
  ReservationUpdateInventoriesREQ,
  ReservationUpdateLucetteInformationREQ,
  ReservationUpdatePartnerInformationREQ,
  ReservationUpdatePetInformationREQ,
  ReservationUpdateREQ,
  ReservationUpdateSettlementDetailREQ,
  ReservationUpdateSettlementREQ,
  ReservationUpdateStockOrdersREQ,
} from './request/reservation.request';
import { ReservationInventoriesRESP, ReservationListRESP } from './response/reservation.response';

export const reservationDetailFormInitialValues = {
  manager: undefined,
  branch: '',
  petInfo: {
    registration: { type: DETAIL_PET_REGISTATION_OPTIONS.UNREGISTERED, id: '' },
    petName: '',
    type: undefined,
    breed: undefined,
    weight: undefined,
    gender: DETAIL_PET_GENDER_OPTIONS.MALE,
    age: undefined,
    dateBirth: undefined,
    dateDeath: undefined,
  },
  guardianInfo: {
    booking: undefined,
    visit: undefined,
    guardianName: '',
    phoneNumber: '',
    breed: '',
    weight: undefined,
    address: {
      general: '',
      detail: '',
    },
    email: '',
    request: '',
  },
  funeralInfo: {
    reservation: undefined,
    status: undefined,
    funeralDate: undefined,
    progress: undefined,
    subProgress: undefined,
    waitingRoom: undefined,
    progressStatusNote: '',
    email: '',
    request: '',
    isActiveTransport: false,
    transport: {
      accompanimentStatus: DETAIL_SERVICE_TRANSPORT_ACCOMPANIED_STATUS_OPTIONS.ACCOMPANY,
      numberPeople: undefined,
      manager: undefined,
      transportTime: undefined,
      deliveryServiceAddress: '',
      deliveryServiceDetailAddress: '',
      funeral: undefined,
      attendance: DETAIL_SERVICE_ATTENDANCE_OPTIONS.ATTENDANCE,
    },
  },
  serviceInfo: {
    supplies: [{ type: '', kind: 0, quantity: 1, amount: 0 }],
    newAdditon: {
      isActive: false,
      color: DETAIL_SERVICE_NEW_AUDITION_COLOR_OPTIONS.GOLD,
      typeMemorialTablet: [],
    },
    lucette: {
      isActive: false,
      category: LUCETTE_CATEGORY.SCHEDULE_PENDING,
      process_date: undefined,
      process_time: '',
      jewelry: DETAIL_SERVICE_LUCETTE_JEWELRY_OPTIONS.NOT_APPLIED,
      value_121: 0,
      value_238: 0,
    },
    columbarium: {
      isActive: false,
      category: undefined,
      roomNumber: undefined,
      date: [undefined, undefined],
      emergencyContact: '',
    },
  },
  announcementsCouponInfo: {
    announcement_web_link: '',
    coupon_history: [],
  },
  hospital: {
    hospital_name: undefined,
    settlement_method: undefined,
    amount: undefined,
  },
  partnerCompany: {
    companyName: undefined,
    remarks: '',
  },
  additionalCost: {
    transportService: 0,
    overweight: 0,
    columbarium: 0,
    temporaryStorage: 0,
    carbonPlate: 0,
    miscellaneous: 0,
  },
  paymentMethod: {
    baseAmount: undefined,
    itemAmount: undefined,
    additionalDiscountReason: '',
    additionalDiscountAmount: undefined,
    totalAmount: undefined,
  },
  settlementDetail: [
    {
      id: undefined,
      date: dayjs().valueOf(),
      settlementMethod: undefined,
      amount: 0,
      note: '',
    },
  ],
  reservationPhoto: {
    attachments: [],
  },
};

export const reservationListParamsToFilter = (searchParams: URLSearchParams): ReservationListFilter => {
  const [startOfDay, endOfDay] = getTodayRange();
  const filter = JSON.stringify({
    'funeralInformation.funeralDate.gte': startOfDay,
    'funeralInformation.funeralDate.lte': endOfDay,
  });
  return { filter: orUndefined(filter), search: orUndefined(searchParams.get('search')) };
};

export const reservationNonAccompanyListParamsToFilter = (searchParams: URLSearchParams): ReservationListFilter => {
  const [startOfDay, endOfDay] = getTodayRange();
  const filter = JSON.stringify({
    'funeralInformation.funeralDate.gte': startOfDay,
    'funeralInformation.funeralDate.lte': endOfDay,
    'funeralInformation.guardianAttendanceType.in': ['NOT_ACCOMPANY'],
  });
  return { filter: orUndefined(filter), search: orUndefined(searchParams.get('search')) };
};

export const reservationRespToDto = (r: ReservationListRESP): FuneralMNGTReservationListDTO => {
  const progress = {
    progress: orString(r.funeralInformation.progress?.name),
    subProgresses: orString(r.funeralInformation.subProgresses?.name),
    waitingRoom: orString(r.funeralInformation.waitingRoom?.name),
  };

  return {
    key: r.id,
    id: r.id,
    code: r.code,
    notificationStatus: r.notificationStatus,
    color: orString(r.funeralInformation.funeralService?.color),
    funeralDate: numberOrZero(r.funeralInformation.funeralDate),
    deliveryDate: parseTimeStampToDayjs(r.funeralInformation.deliveryDate),
    funeralTime: orString(r.funeralInformation.funeralTime),
    'funeralInformation.processStatus': r.funeralInformation.processStatus as DETAIL_FUNERAL_STATUS_OPTIONS,
    'funeralInformation.reservationStatus': r.funeralInformation.reservationStatus as DETAIL_FUNERAL_RESERVATION_OPTIONS,
    petWeight: numberOrZero(r.petInformation.weight),
    petName: orString(r.petInformation.name),
    petType: orString(r.petInformation.petType?.id),
    progress: Object.values(progress).every((value) => isEmpty(value))
      ? ''
      : `${orString(progress.progress)}/${orString(progress.subProgresses)}/${orString(progress.waitingRoom)}`,
    progressNote: orString(r.funeralInformation.progressNote),
    funeralService: `${orString(r.funeralInformation.funeralService?.type)} ${r.funeralInformation.funeralService?.name ? `[${orString(r.funeralInformation.funeralService?.name)}]` : ''} `,
    additionalServices: orString(r.note),
    isRegisterPhoto: !!(r.reservationPhoto && r.reservationPhoto?.files.length > 0),
    paymentMethod: {
      baseAmount: r.settlement?.basicAmount,
      itemAmount: r.settlement?.productAmount,
      additionalDiscountReason: orString(r.settlement?.reasonOfDiscount),
      additionalDiscountAmount: numberOrZero(r.settlement?.discountAmount),
      coupon: numberOrZero(r.settlement?.usageCoupon),
      totalAmount: r.settlement?.totalAmount,
    },
    additionalCost: {
      transportService: numberOrZero(r.settlement?.additionalCost.transport),
      overweight: numberOrZero(r.settlement?.additionalCost.overweight),
      columbarium: numberOrZero(r.settlement?.additionalCost.columbarium),
      temporaryStorage: numberOrZero(r.settlement?.additionalCost.temporaryPlacement),
      carbonPlate: numberOrZero(r.settlement?.additionalCost.carbonPlate),
      miscellaneous: numberOrZero(r.settlement?.additionalCost.otherAdditionalCost),
    },
    carService: {
      guardianAttendanceType: r.funeralInformation.guardianAttendanceType,
      managerName: orString(r.funeralInformation.manager?.fullName),
      vehicleTime: orString(r.funeralInformation.vehicleTime),
    },
  };
};

export const reservationDetailRespToFormDto = (
  r: ReservationListRESP,
  materialNameData: MaterialNameRESP[] = [],
): ReservationDetailDTO => {
  const stockOrder = r.stockOrder || {};
  const [startOfDay, endOfDay] = getYearFromTodayRange();
  return {
    manager: orUndefined(r.leader?.id),
    branch: orString(r.branch.name),
    id: r.id,
    code: r.code,
    reservationPhoto: {
      attachments:
        r.reservationPhoto?.files.map((item) => ({
          uid: item.id.toString(),
          name: item.fileName,
          status: 'done',
          url: process.env.NEXT_PUBLIC_S3_PREFIX + item.key,
        })) || [],
    },
    petInfo: {
      registration: { type: r.petInformation.status, id: orUndefined(r.petInformation.registrationNumber) },
      petName: orString(r.petInformation.name),
      type: orUndefined(r.petInformation.petType?.id),
      breed: orUndefined(r.petInformation.petBreed?.id),
      weight: numberOrZero(r.petInformation.weight),
      gender: r.petInformation.gender,
      age: numberOrZero(r.petInformation.age),
      dateBirth: numberOrUndefined(r.petInformation.dateOfBirth),
      dateDeath: numberOrUndefined(r.petInformation.endDate),
    },
    guardianInfo: {
      booking: orUndefined(r.guardianInformation?.reservationSource),
      visit: orUndefined(r.guardianInformation?.referralSource),
      guardianName: orString(r.guardianInformation?.name),
      phoneNumber: orString(r.guardianInformation?.phoneNumber),
      addressGeneral: orString(r.guardianInformation?.address),
      addressDetail: orString(r.guardianInformation?.addressDetail),
      email: orString(r.guardianInformation?.email),
      request: orString(r.guardianInformation?.request),
    },
    funeralInfo: {
      time: orString(r.funeralInformation.funeralTime),
      reservation: orUndefined(r.funeralInformation.reservationStatus),
      status: orUndefined(r.funeralInformation?.processStatus),
      funeralDate: numberOrZero(r.funeralInformation?.funeralDate),
      progress: orUndefined(r.funeralInformation?.progress?.id),
      subProgress: orUndefined(r.funeralInformation?.subProgresses?.id),
      waitingRoom: orUndefined(r.funeralInformation?.waitingRoom?.id),
      progressStatusNote: orUndefined(r.funeralInformation?.progressNote),
      request: orString(r.funeralInformation.note),
      attendance: orUndefined(r.funeralInformation?.observationType),
      funeral: orUndefined(r.funeralInformation?.funeralService?.id),
      isActiveTransport: r.funeralInformation.isMarked,
      numberOfVisitors: orUndefined(r.funeralInformation.numberOfVisitors),
      transport: orUndefined({
        accompanimentStatus: orUndefined(r.funeralInformation?.guardianAttendanceType),
        numberPeople: orUndefined(r.funeralInformation?.numberOfAttendances),
        manager: orUndefined(r.funeralInformation?.manager?.id),
        transportTime: r.funeralInformation?.vehicleTime ? orString(r.funeralInformation?.vehicleTime) : undefined,
        deliveryServiceAddress: orString(r.funeralInformation?.address),
        deliveryServiceDetailAddress: orString(r.funeralInformation?.addressDetail),
      }),
    },
    serviceInfo: {
      supplies: r.inventories.map((item) => {
        return {
          inventoryId: item.id,
          type: orString(item.material.type),
          kind: item.material.id,
          quantity: numberOrZero(Math.abs(item.quantity)),
          price: 0,
          amount: numberOrZero(item.amount),
          isDefault: item.isDefault,
        };
      }),
      newAdditon: {
        isActive: !isEmpty(stockOrder),
        color: orUndefined(r.stockOrder?.material),
        typeMemorialTablet: Object.entries(stockOrder)
          .filter(([key, value]) => key in mappingNewAdditionService && typeof value === 'number' && value > 0)
          .map(([key, value]) => ({
            type: mappingNewAdditionService[key as keyof typeof mappingNewAdditionService],
            quantity: value as number,
          })),
      },
      lucette: {
        isActive: !isEmpty(r.lucetteInformation),
        category: orUndefined(r.lucetteInformation?.process),
        processDate: numberOrUndefined(r.lucetteInformation?.processDate),
        processTime: orString(r.lucetteInformation?.processTime),
        status: orUndefined(r.lucetteInformation?.jewelry),
        materials:
          materialNameData?.map((item) => ({
            materialId: item.id,
            name: item.name,
            quantity: r.lucetteInformation?.lucetteOrders.find((order) => order.lucetteMaterial.id === item.id)?.quantity ?? 0,
          })) ?? [],
      },
      columbarium: {
        isActive: !isEmpty(r.columbariumInformation),
        category: orString(r.columbariumInformation?.columbariumPlace.columbariumRoom.name),
        roomNumber: orUndefined(r.columbariumInformation?.columbariumPlace.id),
        date: r.columbariumInformation?.registrationDate
          ? [
              numberOrUndefined(r.columbariumInformation?.registrationDate),
              numberOrUndefined(r.columbariumInformation?.expirationDate),
            ]
          : [startOfDay, dayjs(endOfDay).subtract(1, 'day').startOf('day').valueOf()],
        emergencyContact: orString(r.columbariumInformation?.emergencyContact),
        roomName: orUndefined(r.columbariumInformation?.columbariumPlace.number),
      },
    },
    announcementsCouponInfo: {
      announcement_web_link: `${process.env.NEXT_PUBLIC_ECM_URL}obituary/card-owner/create?id=${orString(r.obituaryInformation?.link)}`,
      coupon_history: orUndefined(
        r.obituaryInformation?.coupons.map((item) => {
          return {
            couponContactSender: `${item.code} (${item.purchaser} - ${item.purchaserContact})`,
            paymentAmount: item.amount,
          };
        }),
      ),
    },
    hospital: {
      hospital_name: orUndefined(r.hospitalInformation?.hospital?.id),
      settlement_method: orUndefined(r.hospitalInformation?.hospitalEntitlement?.id),
      amount: numberOrZero(r.hospitalInformation?.amount),
    },
    partnerCompany: {
      companyName: orUndefined(r.partnerInformation?.partner?.id),
      remarks: orString(r.partnerInformation?.note),
    },
    settlementId: numberOrZero(r.settlement?.id),
    additionalCost: {
      transportService: numberOrZero(r.settlement?.additionalCost.transport),
      overweight: numberOrZero(r.settlement?.additionalCost.overweight),
      columbarium: numberOrZero(r.settlement?.additionalCost.columbarium),
      temporaryStorage: numberOrZero(r.settlement?.additionalCost.temporaryPlacement),
      carbonPlate: numberOrZero(r.settlement?.additionalCost.carbonPlate),
      miscellaneous: numberOrZero(r.settlement?.additionalCost.otherAdditionalCost),
    },
    paymentMethod: {
      baseAmount: numberOrZero(r.settlement?.basicAmount),
      itemAmount: numberOrZero(r.settlement?.productAmount),
      additionalDiscountReason: orString(r.settlement?.reasonOfDiscount),
      additionalDiscountAmount: numberOrZero(r.settlement?.discountAmount),
      coupon: numberOrZero(r.settlement?.usageCoupon),
      totalAmount: numberOrZero(r.settlement?.totalAmount),
    },
    settlementDetail: r.reservationSettlements.map((item) => ({
      id: item.id,
      date: item.settlementDate,
      settlementMethod: item.reservationMethod,
      amount: item.settlementAmount,
      note: item.memo,
    })),
  };
};

export const reservationInventoriesRespToFormDto = (r: ReservationInventoriesRESP[]): SuppliesDTO[] => {
  return r.map((item) => ({
    inventoryId: item.id,
    type: orString(item.material.type),
    kind: item.material.id,
    quantity: item.quantity,
    amount: item.amount,
  }));
};

export const petInformationDtoToReq = (r: PetInfoDTO): ReservationUpdatePetInformationREQ => {
  return {
    petName: r.petName,
    registrationNumber: r.registration?.id,
    petTypeId: r.type,
    petBreedId: r.breed,
    weight: r.weight,
    dateOfBirth: r.dateBirth?.valueOf(),
    endDate: r.dateDeath?.valueOf(),
    age: r.age,
    gender: r.gender,
    status: r.registration.type,
  };
};

export const funeralInformationDtoToReq = (r: FuneralInfoDTO): ReservationUpdateFunaralInformationREQ => {
  return {
    funeralTime: isEmpty(r.time) ? undefined : r.time,
    isMarked: r.isActiveTransport,
    vehicleTime: isEmpty(r.transport.transportTime)
      ? undefined
      : typeof r.transport.transportTime === 'string'
        ? r.transport.transportTime
        : parseTimestampToTime(r.transport.transportTime?.valueOf().toString(), DATE_FORMAT.TIME.PICKER_HOUR_MIN),
    funeralDate: r.funeralDate.valueOf(),
    progressNote: r.progressStatusNote,
    reservationStatus: r.reservation,
    numberOfVisitors: r.numberOfVisitors,
    processStatus: r.status,
    guardianAttendanceType: r.transport.accompanimentStatus,
    numberOfAttendances: r.transport.numberPeople,
    observationType: r.attendance,
    funeralNote: r.request,
    address: r.transport.deliveryServiceAddress,
    addressDetail: r.transport.deliveryServiceDetailAddress,
    progressId: r.progress,
    subProgressId: r.subProgress,
    waitingRoomId: r.waitingRoom,
    managerId: r.transport.manager,
  };
};

export const guardianInformationDtoToReq = (r: GuardianInfoDTO): ReservationUpdateGuardianInformationREQ => {
  return {
    guardianName: r.guardianName,
    phoneNumber: r.phoneNumber,
    email: isEmpty(r.email) ? undefined : r.email,
    address: r.addressGeneral,
    addressDetail: r.addressDetail,
    request: r.request,
    referralSource: r.visit as DETAIL_GUARDIAN_VISIT_OPTIONS,
    reservationSource: r.booking as DETAIL_GUARDIAN_BOOKING_OPTIONS,
    provinceName: r.provinceName,
    districtName: r.districtName,
  };
};

export const reservationInformationDtoToReq = (r: ReservationDetailDTO): ReservationUpdateREQ => {
  return {
    leaderId: r.manager,
  };
};

export const columbariumInformationDtoToReq = (r: ColumbariumDTO): ReservationUpdateColumbariumREQ => {
  return {
    emergencyContact: r.emergencyContact,
    columbariumPlaceId: r.roomNumber ?? undefined,
    registrationDate: r.date[0],
    expirationDate: r.date[1],
    isDelete: r.isActive ? false : true,
  };
};

export const hospitalInformationDtoToReq = (r: HospitalInfoDTO): ReservationUpdateHospitalInformationREQ => {
  return {
    amount: r.amount,
    hospitalEntitlementId: r.settlement_method,
    hospitalId: r.hospital_name,
  };
};

export const partnerInformationDtoToReq = (r: PartnerCompanyDTO): ReservationUpdatePartnerInformationREQ => {
  return {
    partnerNote: r.remarks,
    partnerId: orUndefined(r.companyName),
  };
};

export const paymentInformationDtoToReq = (r: PaymentMethodDTO & AdditionalCostDTO): ReservationUpdateSettlementREQ => {
  return {
    productAmount: numberOrZero(r.itemAmount),
    transport: numberOrZero(r.transportService),
    basicAmount: numberOrZero(r.baseAmount),
    overweight: numberOrZero(r.overweight),
    columbarium: numberOrZero(r.columbarium),
    temporaryPlacement: numberOrZero(r.temporaryStorage),
    carbonPlate: numberOrZero(r.carbonPlate),
    otherAdditionalCost: numberOrZero(r.miscellaneous),
    reasonOfDiscount: orString(r.additionalDiscountReason),
    discountAmount: numberOrZero(r.additionalDiscountAmount),
  };
};

export const newAdditionInformationDtoToReq = (r: NewAdditionDTO): ReservationUpdateStockOrdersREQ => {
  const rematchedStockOrder = Object.keys(mappingNewAdditionService).reduce(
    (acc, key) => {
      const mappedKey = key as keyof typeof mappingNewAdditionService;
      const matchedItems = r.typeMemorialTablet.filter((item) => item.type === mappingNewAdditionService[mappedKey]);
      const totalQuantity = matchedItems.reduce((sum, item) => sum + item.quantity, 0);
      acc[mappedKey] = totalQuantity > 0 ? totalQuantity : 0;
      return acc;
    },
    { columbarium: 0, home: 0, monite: 0, emotional: 0, sophisticated: 0, frame: 0 },
  );

  return {
    ...rematchedStockOrder,
    material: r.color,
    isDelete: r.isActive ? false : true,
  };
};

export const lucetteInformationDtoToReq = (r: LucetteDTO): ReservationUpdateLucetteInformationREQ => {
  const materials = r.materials.filter((item) => item.quantity > 0).map(({ materialId, quantity }) => ({ materialId, quantity }));

  return {
    processDate: r.processDate?.valueOf(),
    processTime: isEmpty(r.processTime)
      ? undefined
      : typeof r.processTime === 'string'
        ? r.processTime
        : parseTimestampToTime(r.processTime?.valueOf().toString(), DATE_FORMAT.TIME.PICKER_HOUR_MIN),
    materials: materials,
    isDelete: r.isActive ? false : true,
    process: r.category,
  };
};

export const inventoriesInformationDtoToReq = (r: SuppliesDTO[], funeralServiceId: number): ReservationUpdateInventoriesREQ => {
  const materials = r
    .filter(({ kind }) => kind !== undefined)
    .map(({ kind, quantity, amount, isDefault }) => ({
      materialId: kind as number,
      quantity,
      amount: amount,
      isDefault: !!isDefault,
    }));

  return { materials, funeralServiceId };
};

export const settlementDetailDtoToReq = (r: SettlementDetailDTO[]): ReservationUpdateSettlementDetailREQ => {
  const reservationSettlements = r.map(({ id, date, settlementMethod, amount, note }) => ({
    id: id,
    settlementAmount: amount,
    reservationMethod: settlementMethod,
    settlementDate: date?.valueOf(),
    memo: note,
  }));

  return { reservationSettlements: reservationSettlements };
};

// export const paymentInformationDtoToReq = (p: PaymentMethodDTO, a: AdditionalCostDTO): ReservationUpdateSettlementREQ => {
//   return {
//     basicAmount: numberOrZero(p.baseAmount),
//     productAmount: numberOrZero(p.itemAmount),
//     transport: a.transportService,
//     overweight: a.overweight,
//     columbarium: a.columbarium,
//     temporaryPlacement: a.temporaryStorage,
//     carbonPlate: a.carbonPlate,
//     otherAdditionalCost: a.miscellaneous,
//     reasonOfDiscount: p.additionalDiscountReason,
//     discountAmount: p.additionalDiscountAmount,
//   };
// };

export const reservationDetailDtoToReq = (
  r: ReservationDetailDTO,
  preData?: ReservationDetailDTO,
  attachmentIds?: number[],
): ReservationUpdateDetailREQ => {
  return {
    reservation: reservationInformationDtoToReq(r),
    columbariumInformation:
      !preData?.serviceInfo.columbarium.isActive && !r.serviceInfo.columbarium.isActive
        ? undefined
        : columbariumInformationDtoToReq(r.serviceInfo.columbarium),
    funeralInformation: funeralInformationDtoToReq(r.funeralInfo),
    guardianInformation: guardianInformationDtoToReq(r.guardianInfo),
    hospitalInformation: hospitalInformationDtoToReq(r.hospital),
    lucetteInformation:
      !preData?.serviceInfo.lucette.isActive && !r.serviceInfo.lucette.isActive
        ? undefined
        : lucetteInformationDtoToReq(r.serviceInfo.lucette),
    partnerInformation: partnerInformationDtoToReq(r.partnerCompany),
    petInformation: petInformationDtoToReq(r.petInfo),
    reservationInventory: inventoriesInformationDtoToReq(r.serviceInfo.supplies, r.funeralInfo.funeral),
    reservationPhoto: { photoIds: attachmentIds },
    reservationSettlement: settlementDetailDtoToReq(r.settlementDetail),
    stockOrder:
      !preData?.serviceInfo.newAdditon.isActive && !r.serviceInfo.newAdditon.isActive
        ? undefined
        : newAdditionInformationDtoToReq(r.serviceInfo.newAdditon),
    settlement: paymentInformationDtoToReq({ ...r.paymentMethod, ...r.additionalCost }),
  };
};
