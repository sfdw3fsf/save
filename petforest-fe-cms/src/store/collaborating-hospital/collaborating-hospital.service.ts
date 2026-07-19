import { CALENDAR_STATUS_ENUMS } from '@/utils/enums/common.enums';
import { SALES_CALENDAR_EVENT_ID, SALES_CALENDAR_EVENT_INACTIVE } from '@/utils/enums/home/calendar.enum';
import { ENUM_STATUS } from '@/utils/enums/status.enum';
import { getCurrentMonthRange, getTodayRange } from '@/utils/helpers/date-time.helper';
import { parseTimestampToDate, parseTimeStampToDayjs } from '@/utils/parser/date-time.parser';
import { orNull, orString, orUndefined } from '@/utils/parser/io.parser';
import { CollaboratingRegion } from '@/utils/types/collaborating/partner-hospital.type';
import dayjs, { Dayjs } from 'dayjs';
import { TFunction } from 'i18next';
import { isArray } from 'lodash';
import {
  HospitalDeliveryDTO,
  HospitalDeliverySummaryDTO,
  HospitalDeliverySummaryListDTO,
  HospitalDeliveryTotal,
  HospitalDetailDTO,
  HospitalDetailSalesDTO,
  HospitalDetailSettlementDTO,
  HospitalDetailSettlementTotalDTO,
  HospitalDTO,
  HospitalRegionDTO,
  HospitalSaleCalendarDTO,
  HospitalSaleListDTO,
  HospitalSaleTotal,
  HospitalSettlementDTO,
  HospitalSettlementMethodSummaryDTO,
  HospitalSettlementSummaryDTO,
} from './dto/collaborating-hospital.dto';
import {
  HospitalDeliveryListFilter,
  HospitalListFilter,
  HospitalSettlementListFilter,
  HospitalUpdateREQ,
} from './request/collaborating-hospital.request';
import {
  HospitalDeliveryRESP,
  HospitalDeliverySummaryListRESP,
  HospitalDeliverySummaryRESP,
  HospitalDetailRESP,
  HospitalDetailSalesRESP,
  HospitalDetailSettlementRESP,
  HospitalDetailSettlementTotalRESP,
  HospitalRegionRESP,
  HospitalRESP,
  HospitalSaleCalendarRESP,
  HospitalSaleListRESP,
  HospitalSettlementMethodSummaryRESP,
  HospitalSettlementRESP,
  HospitalSettlementSummaryRESP,
} from './response/collaborating-hospital.response';

export const hospitalInformationInitialValues: HospitalDetailDTO = {
  name: '',
  address: '',
  detailAddress: '',
  director: '',
  region: '',
  hospitalSize: '',
  parkingStatus: '',
  businessRegistrationNumber: '',
  phoneNumber: '',
  email: '',
  note: '',
  isMarked: false,
  bankHolder: '',
  depositor: '',
  accountNumber: '',
  createdAt: '',
  branchIds: [],
  provinceName: '',
  districtName: '',
};

export const hospitalRegionRespToDto = (r: HospitalRegionRESP): HospitalRegionDTO => {
  const label = r.krName === '전체' ? `${r.krName} (${r.total} / ${r.totalFuneral})` : `${r.krName} (${r.total})`;
  return {
    value: r.id,
    label: label,
    children: r.districts?.map((item) => ({
      label: `${item.krName} (${item.total})`,
      value: item.id,
    })),
  };
};

export const hospitalRegionRespToDtoType2 = (r: HospitalRegionRESP): HospitalRegionDTO => {
  const label = `${r.krName} (${r.total})`;
  return {
    value: r.id,
    label: label,
    children: r.districts?.map((item) => ({
      label: `${item.krName} (${item.total})`,
      value: item.id,
    })),
  };
};

export function hospitalListParamsToFilter(searchParams: URLSearchParams): HospitalListFilter {
  return {
    search: orUndefined(searchParams.get('search')),
    sortOrder: orUndefined(searchParams.get('sortOrder')),
    sortBy: orUndefined(searchParams.get('sortBy')),
    regions: orUndefined(searchParams.get('regions')),
  } as HospitalListFilter;
}

export const hospitalRespToDto = (r: HospitalRESP): HospitalDTO => {
  return {
    ...r,
    key: r.id,
    createdAt: parseTimestampToDate(r.createdAt),
    entitlementMethod: r.entitlementMethod?.id,
    totalFuneral: r.totalFuneral.toLocaleString(),
    totalSales: r.totalSales.toLocaleString(),
    hospitalBranches: r.hospitalBranches?.map((item) => item.id) || [],
    isNewHospital: dayjs(Number(r.createdAt)).isAfter(dayjs().subtract(6, 'months')),
  };
};

export const regionFilterToReq = (regions: CollaboratingRegion[] | undefined): string | undefined => {
  return regions ? regions.map((item) => `${item.province}.${item.municipality}`).join(',') : undefined;
};

export const hospitalDetailRespToDto = (r: HospitalDetailRESP): HospitalDetailDTO => {
  return {
    ...r,
    entitlementMethod: r.entitlementMethod?.id,
    branchIds: r.hospitalBranches?.map((item) => item.id) || [],
  };
};

export const hospitalDetailSalesRespToDto = (r: HospitalDetailSalesRESP): HospitalDetailSalesDTO => {
  return {
    ...r,
    key: r.id,
    visitedAt: r.visitedAt ? parseTimestampToDate(r.visitedAt) : '',
  } as HospitalDetailSalesDTO;
};

//Delivery
export function hospitalDeliveryListParamsToFilter(searchParams: URLSearchParams): HospitalDeliveryListFilter {
  const [startDate, endDate] = getCurrentMonthRange();
  const startDateFromPrevious = searchParams.get('startDate');
  const endDateFromPrevious = searchParams.get('endDate');

  const filter = JSON.stringify({
    'requestedAt.gte': startDateFromPrevious || startDate,
    'requestedAt.lte': endDateFromPrevious || endDate,
  });
  return {
    search: orUndefined(searchParams.get('search')),
    filter: orUndefined(filter),
    sortOrder: orUndefined(searchParams.get('sortOrder')),
    sortBy: orUndefined(searchParams.get('sortBy')),
  } as HospitalDeliveryListFilter;
}

export const hospitalDeliveryRespToDto = (r: HospitalDeliveryRESP): HospitalDeliveryDTO => {
  return {
    ...r,
    key: r.id,
    managerId: r.manager?.managerId,
    requestedAt: parseTimeStampToDayjs(r.requestedAt),
    deliveredAt: parseTimeStampToDayjs(r.deliveredAt),
  };
};

export const hospitalDeliverySummaryRespToDto = (r: HospitalDeliverySummaryRESP): HospitalDeliverySummaryDTO => {
  return {
    total: r.totalFuneral.toLocaleString(),
    request: {
      brochure: r.totalRequestedBrochure.toLocaleString(),
      transportCaseLarge: r.totalRequestedBigTransportCase.toLocaleString(),
      transportCaseSmall: r.totalRequestedSmallTransportCase.toLocaleString(),
    },
    delivery: {
      brochure: r.totalDeliveredBrochure.toLocaleString(),
      transportCaseLarge: r.totalDeliveredBigTransportCase.toLocaleString(),
      transportCaseSmall: r.totalDeliveredSmallTransportCase.toLocaleString(),
    },
  };
};

export const hospitalDeliverySummaryListRespToDto = (r: HospitalDeliverySummaryListRESP): HospitalDeliverySummaryListDTO => {
  return {
    ...r,
    key: r.hospitalId,
  };
};

export const hospitalDeliveryDtoToSummary = (data: readonly HospitalDeliverySummaryListDTO[]): HospitalDeliverySummaryListDTO => {
  return data.reduce(
    (summary, current) => {
      return {
        key: 0,
        hospitalId: 0,
        hospitalName: 'Total',
        numOfFunerals: summary.numOfFunerals + current.numOfFunerals,
        requestedBrochure: summary.requestedBrochure + current.requestedBrochure,
        requestedBigTransportCase: summary.requestedBigTransportCase + current.requestedBigTransportCase,
        requestedSmallTransportCase: summary.requestedSmallTransportCase + current.requestedSmallTransportCase,
        deliveredBrochure: summary.deliveredBrochure + current.deliveredBrochure,
        deliveredBigTransportCase: summary.deliveredBigTransportCase + current.deliveredBigTransportCase,
        deliveredSmallTransportCase: summary.deliveredSmallTransportCase + current.deliveredSmallTransportCase,
      };
    },
    {
      key: 0,
      hospitalId: 0,
      hospitalName: 'Total',
      numOfFunerals: 0,
      requestedBrochure: 0,
      requestedBigTransportCase: 0,
      requestedSmallTransportCase: 0,
      deliveredBrochure: 0,
      deliveredBigTransportCase: 0,
      deliveredSmallTransportCase: 0,
    },
  );
};

export const hospitalDeliveryListDataDtoToTotal = (data?: HospitalDeliveryDTO[]): HospitalDeliveryTotal => {
  return data
    ? data.reduce(
        (summary, current) => {
          return {
            numOfFunerals: summary.numOfFunerals + current.numOfFunerals,
            deliveredBrochure: summary.deliveredBrochure + current.deliveredBrochure,
            deliveredTransportCase:
              summary.deliveredTransportCase + current.deliveredBigTransportCase + current.deliveredSmallTransportCase,
          };
        },
        {
          numOfFunerals: 0,
          deliveredBrochure: 0,
          deliveredTransportCase: 0,
        },
      )
    : {
        numOfFunerals: 0,
        deliveredBrochure: 0,
        deliveredTransportCase: 0,
      };
};

export const hospitalInformationUpdateDtoToReq = (d: HospitalDetailDTO): HospitalUpdateREQ => {
  return {
    ...d,
    settlementMethodId: d.entitlementMethod,
  };
};

// Sales
export const hospitalSaleRespToDto = (resp: HospitalSaleListRESP): HospitalSaleListDTO => {
  const dto: HospitalSaleListDTO = {
    ...resp,
    key: resp.id,
    hospitalName: resp.hospitalId,
    visitStatus: resp.visitStatus as ENUM_STATUS,
    visitedAt: resp.visitedAt ? dayjs(+resp.visitedAt) : null,
    hospitalId: orUndefined(resp.hospitalId),
    manager: orNull(resp.manager.managerId),
  };
  return dto;
};

export const getCalendarTitle = (data: HospitalSaleCalendarRESP, tEnum: TFunction<'enum', undefined>) => {
  const type = {
    [SALES_CALENDAR_EVENT_ID.NEW_SALES]: `${data.managerName},  ${data.hospitalName}, ${data.region}`,
    [SALES_CALENDAR_EVENT_ID.REQUEST]: `${data.hospitalName}, ${tEnum('promotional_material.brochure')} ${data.requestedBrochure}, ${tEnum('promotional_material.large_transport_box')} ${data.requestedBigTransportCase}, ${tEnum('promotional_material.small_transport_box')} ${data.requestedSmallTransportCase}`,
    [SALES_CALENDAR_EVENT_ID.TRANSMISSION]: `${data.hospitalName}, ${tEnum('promotional_material.brochure')} ${data.deliveredBrochure}, ${tEnum('promotional_material.large_transport_box')} ${data.deliveredBigTransportCase}, ${tEnum('promotional_material.small_transport_box')} ${data.deliveredSmallTransportCase}`,
  };

  return type[data.saleType];
};

export const hospitalSaleCalendarRespToDto = (
  r: HospitalSaleCalendarRESP,
  index: number,
  tEnum: TFunction<'enum', undefined>,
  month: Dayjs[],
): HospitalSaleCalendarDTO => {
  const type =
    dayjs(+r.markedAt).isAfter(month[0]) && dayjs(Number(+r.markedAt)).isBefore(month[1])
      ? r.saleType
      : SALES_CALENDAR_EVENT_INACTIVE[r.saleType];
  return {
    id: index.toString(),
    calendarId: type,
    title: getCalendarTitle(r, tEnum),
    category: 'allday',
    state: r.saleType,
    start: parseTimestampToDate(r.markedAt),
    end: parseTimestampToDate(r.markedAt),
    isReadOnly: true,
    status: CALENDAR_STATUS_ENUMS.ACTIVE,
  };
};

export const hospitalSaleListParamsToFilter = (searchParams: URLSearchParams): HospitalListFilter => {
  // return {
  //   search: orUndefined(searchParams.get('search')),
  //   sortOrder: orUndefined(searchParams.get('sortOrder')),
  //   sortBy: orUndefined(searchParams.get('sortBy')),
  // };
  const [startOfDay, endOfDay] = getTodayRange();
  const filter = JSON.stringify({
    'visitedAt.gte': startOfDay,
    'visitedAt.lte': endOfDay,
  });
  return { filter: orUndefined(filter), search: orUndefined(searchParams.get('search')) };
};

export const hospitalSalesListDataDtoToTotal = (data?: HospitalDetailSalesDTO[], total?: number): HospitalSaleTotal => {
  return data
    ? data.reduce(
        (summary, current) => {
          return {
            numOfFunerals: summary.numOfFunerals + 1,
            numerOfSalesVisit: total,
          } as HospitalSaleTotal;
        },
        {
          numOfFunerals: 0,
          numerOfSalesVisit: 0,
        },
      )
    : {
        numOfFunerals: 0,
        numerOfSalesVisit: 0,
      };
};

//Settlement
export function hospitalSettlementParamsToFilter(searchParams: URLSearchParams): HospitalSettlementListFilter {
  const [startDate, endDate] = getCurrentMonthRange();
  const filter = JSON.stringify({
    'reservation.funeralInformation.funeralDate.gte': startDate,
    'reservation.funeralInformation.funeralDate.lte': endDate,
  });

  return {
    search: orUndefined(searchParams.get('search')),
    filter: orUndefined(filter),
    sortOrder: orUndefined(searchParams.get('sortOrder')),
    sortBy: orUndefined(searchParams.get('sortBy')),
  } as HospitalSettlementListFilter;
}

export const hospitalSettlementRespToDto = (r: HospitalSettlementRESP): HospitalSettlementDTO => {
  return {
    ...r,
    key: r.hospitalSettlementId,
    reservationId: orUndefined(r.reservationId),
    settlementDate: parseTimestampToDate(r.settlementDate),
    funeralDate: parseTimestampToDate(r.funeralDate),
    'hospitalEntitlement.id': r.hospitalEntitlement.name,
    latestEmailDate: parseTimestampToDate(r.latestEmailDate),
    amount: r.amount.toLocaleString(),
    memo: orString(r.memo),
  };
};

export const hospitalSettlementMethodSummaryRespToDto = (
  r: HospitalSettlementMethodSummaryRESP[],
  tCommon: TFunction<'common', undefined>,
): HospitalSettlementMethodSummaryDTO => {
  return r.map((item) => ({
    title: item.entitlementName,
    value: [
      { text: tCommon('won'), count: item.totalSettlementAmount },
      { text: tCommon('cases'), count: item.numberOfSettlement || 0 },
    ],
  }));
};

export const hospitalSettlementSummaryRespToDto = (r: HospitalSettlementSummaryRESP): HospitalSettlementSummaryDTO[] => {
  const result = Object.entries(r.hospitals).map(([key, value]) => {
    const values: Record<string | number, string | number[] | number> = {};
    values['hospital'] = key;
    values['hospitalId'] = value.hospitalId;
    value.data.forEach((method) => {
      if (method.entitlementId) {
        values[method.entitlementId] = [method.numberOfSettlement, method.totalSettlementAmount];
      }
    });

    return values;
  });

  const summary = result.reduce((acc, item) => {
    Object.entries(item).forEach(([key, value]) => {
      if (!isNaN(Number(key)) && isArray(value)) {
        if (!acc[key]) {
          acc[key] = [...value];
        } else {
          acc[key] = (acc[key] as number[]).map((val, index) => val + value[index]);
        }
      }
    });
    return acc;
  }, {});

  return result as HospitalSettlementSummaryDTO[];
};

export const hospitalSettlementSummaryToTotalRow = (result: HospitalSettlementSummaryDTO[]) => {
  const summary = result.reduce((acc: any, item) => {
    Object.entries(item).forEach(([key, value]) => {
      if (!isNaN(Number(key)) && isArray(value)) {
        if (!acc[key as keyof typeof acc]) {
          acc[key] = [...value];
        } else {
          acc[key] = (acc[key] as number[]).map((val, index) => val + value[index]);
        }
      }
    });
    return acc;
  }, {}) as Record<number, number[]>;

  return summary;
};

export const hospitalDetailSettlementRespToDto = (r: HospitalDetailSettlementRESP): HospitalDetailSettlementDTO => {
  return {
    ...r,
    key: r.hospitalSettlementId,
    settlementDate: parseTimeStampToDayjs(r.settlementDate),
    funeralDate: parseTimestampToDate(r.funeralDate),
    amount: r.amount.toLocaleString(),
    hospitalEntitlementId: r.hospitalEntitlement?.id,
  };
};

export const hospitalDetailSettlementTotalRespToDto = (
  r: HospitalDetailSettlementTotalRESP[],
  tPartnerHospital: TFunction<'collaborating_partner_hospital', undefined>,
): HospitalDetailSettlementTotalDTO[] => {
  return r?.map((item) => ({
    title: item.entitlementName,
    // value: `${item.totalSettlement}${tPartnerHospital('detail.case')}/${item.totalAmount}${tPartnerHospital('detail.won')}`,
    value: [
      { text: tPartnerHospital('detail.case'), count: item.totalSettlement },
      { text: tPartnerHospital('detail.won'), count: item.totalAmount },
    ],
  }));
};
