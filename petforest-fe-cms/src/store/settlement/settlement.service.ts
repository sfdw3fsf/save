import { DATE_FORMAT } from '@/utils/constants/date.constant';
import { COUPON_STATUS } from '@/utils/enums/common.enum';
import { ENUM_STATUS } from '@/utils/enums/status.enum';
import { numberOrZero, orNull, orUndefined, parseStringToNumber } from '@/utils/parser/io.parser';
import dayjs from 'dayjs';
import {
  SettlementAdjustmentAdditionalCostListDTO,
  SettlementAdjustmentListDTO,
  SettlementAdjustmentSummaryDTO,
  SettlementCouponListDTO,
} from './dto/settlement.dto';
import { SettlementAdjustmentListFilter, SettlementCouponListFilter } from './request/settlement.request';
import {
  SettlementAdjustmentAdditionalCostListRESP,
  SettlementAdjustmentListRESP,
  SettlementAdjustmentSummaryRESP,
  SettlementCouponListRESP,
} from './response/settlement.response';

export const settlementAdjustmentListParamsToFilter = (searchParams: URLSearchParams): SettlementAdjustmentListFilter => {
  const DEFAULT_DATE_RANGE: [number, number] = [dayjs().startOf('day').valueOf(), dayjs().endOf('day').valueOf()];

  const filter = JSON.stringify({
    'reservation.funeralInformation.funeralDate.gte': DEFAULT_DATE_RANGE[0],
    'reservation.funeralInformation.funeralDate.lte': DEFAULT_DATE_RANGE[1],
  });

  return {
    filter: filter,
    search: orUndefined(searchParams.get('search')),
    sortOrder: orUndefined(searchParams.get('sortOrder')),
    sortBy: orUndefined(searchParams.get('sortBy')),
  };
};

export const settlementAdjustmentListRespToDto = (resp: SettlementAdjustmentListRESP): SettlementAdjustmentListDTO => {
  const dto: SettlementAdjustmentListDTO = {
    ...resp,
    key: resp.id,
    funeralDate: resp.funeralDate ? dayjs(resp.funeralDate).format(DATE_FORMAT.DATE.HYPHEN) : null,
    status: resp.status as ENUM_STATUS,
    discountAmount: orNull(resp.discountAmount),
    usageCoupon: orNull(resp.usageCoupon),
    remainingAmount: orNull(resp.remainingAmount),
  };
  return dto;
};

export const settlementAdjustmentSummaryRespToDto = (resp: SettlementAdjustmentSummaryRESP): SettlementAdjustmentSummaryDTO => {
  const dto: SettlementAdjustmentSummaryDTO = {
    ...resp,
    totalSettlements: resp.totalSettlements,
    totalAmount: resp.totalAmount,
    totalRemainingAmount: resp.totalRemainingAmount,
  };
  return dto;
};

export const settlementAdjustmentListSummary = (dto: readonly SettlementAdjustmentListDTO[] = []) => {
  return dto.reduce(
    (acc, cur) => ({
      basicAmount: acc.basicAmount + numberOrZero(cur.basicAmount),
      productAmount: acc.productAmount + numberOrZero(cur.productAmount),
      additionalCost: {
        transport: acc.additionalCost.transport + numberOrZero(cur.additionalCost.transport),
        overweight: acc.additionalCost.overweight + numberOrZero(cur.additionalCost.overweight),
        columbarium: acc.additionalCost.columbarium + numberOrZero(cur.additionalCost.columbarium),
        temporaryPlacement: acc.additionalCost.temporaryPlacement + numberOrZero(cur.additionalCost.temporaryPlacement),
        carbonPlate: acc.additionalCost.carbonPlate + numberOrZero(cur.additionalCost.carbonPlate),
        otherAdditionalCost: acc.additionalCost.otherAdditionalCost + numberOrZero(cur.additionalCost.otherAdditionalCost),
      },
      settlementMethod: {
        card: acc.settlementMethod.card + numberOrZero(cur.settlementMethod.card),
        cash: acc.settlementMethod.cash + numberOrZero(cur.settlementMethod.cash),
        accountAmount: acc.settlementMethod.accountAmount + numberOrZero(cur.settlementMethod.accountAmount),
        prePaid: acc.settlementMethod.prePaid + numberOrZero(cur.settlementMethod.prePaid),
      },
      discountAmount: acc.discountAmount + numberOrZero(cur.discountAmount),
      usageCoupon: acc.usageCoupon + numberOrZero(cur.usageCoupon),
      remainingAmount: acc.remainingAmount + numberOrZero(cur.remainingAmount),
      totalAmount: acc.totalAmount + numberOrZero(cur.totalAmount),
    }),
    {
      basicAmount: 0,
      productAmount: 0,
      additionalCost: {
        transport: 0,
        overweight: 0,
        columbarium: 0,
        temporaryPlacement: 0,
        carbonPlate: 0,
        otherAdditionalCost: 0,
      },
      settlementMethod: {
        card: 0,
        cash: 0,
        accountAmount: 0,
        prePaid: 0,
      },
      discountAmount: 0,
      usageCoupon: 0,
      remainingAmount: 0,
      totalAmount: 0,
    },
  );
};

// Additional cost

export const settlementAdjustmentAdditionalCostListRespToDto = (
  resp: SettlementAdjustmentAdditionalCostListRESP,
): SettlementAdjustmentAdditionalCostListDTO => {
  const dto: SettlementAdjustmentAdditionalCostListDTO = {
    ...resp,
    key: resp.id,
    funeralDate: resp.funeralDate ? dayjs(resp.funeralDate).format(DATE_FORMAT.DATE.HYPHEN) : '',
    status: resp.status as ENUM_STATUS,
  };

  return dto;
};

export const settlementAdjustmentAdditionalCostListSummary = (dto: readonly SettlementAdjustmentAdditionalCostListDTO[] = []) => {
  return dto.reduce(
    (acc, cur) => ({
      basicAmount: acc.basicAmount + numberOrZero(cur.basicAmount),
      transport: acc.transport + numberOrZero(cur.transport),
      overweight: acc.overweight + numberOrZero(cur.overweight),
      columbarium: acc.columbarium + numberOrZero(cur.columbarium),
      temporaryPlacement: acc.temporaryPlacement + numberOrZero(cur.temporaryPlacement),
      carbonPlate: acc.carbonPlate + numberOrZero(cur.carbonPlate),
      otherAdditionalCost: acc.otherAdditionalCost + numberOrZero(cur.otherAdditionalCost),
    }),
    {
      basicAmount: 0,
      transport: 0,
      overweight: 0,
      columbarium: 0,
      temporaryPlacement: 0,
      carbonPlate: 0,
      otherAdditionalCost: 0,
    },
  );
};

// Coupon

export const settlementCouponListRespToDto = (resp: SettlementCouponListRESP): SettlementCouponListDTO => {
  let dto: SettlementCouponListDTO = {
    id: resp.id,
    key: resp.id,
    code: orUndefined(resp.code),
    status: resp.status as COUPON_STATUS,
    paymentCode: orUndefined(resp.paymentCode),
    amount: orUndefined(resp.amount),
    note: orUndefined(resp.note),
    purchaser: orUndefined(resp.purchaser),
    purchaserContact: orUndefined(resp.purchaserContact),
    purchaseDate: orUndefined(resp.purchaseDate),
    useDate: orUndefined(resp.useDate),
  };

  const { reservation } = resp;

  if (reservation) {
    const { guardianInformation, funeralInformation } = reservation;
    dto = {
      ...dto,
      reservationId: reservation.id,
      reservationCode: orUndefined(reservation.code),
      funeralDate: orUndefined(funeralInformation?.funeralDate),
      funeralTime: orUndefined(funeralInformation?.funeralTime),
      guardianName: orUndefined(guardianInformation?.name),
      guardianPhone: orUndefined(guardianInformation?.phoneNumber),
    };
  }

  return dto;
};

export const settlementCouponListParamsToFilter = (
  searchParams: URLSearchParams,
  defaultFilter?: SettlementCouponListFilter,
): SettlementCouponListFilter => {
  const { start, end } = {
    start: parseStringToNumber(searchParams.get('start')),
    end: parseStringToNumber(searchParams.get('end')),
  };

  let filter;
  if (start && end) {
    filter = JSON.stringify({
      'reservation.funeralInformation.funeralDate.gte': start,
      'reservation.funeralInformation.funeralDate.lte': end,
    });
  }

  return {
    filter: orUndefined(filter || defaultFilter?.filter),
    search: orUndefined(searchParams.get('search') || defaultFilter?.search),
    sortOrder: orUndefined(searchParams.get('sortOrder') || defaultFilter?.sortOrder),
    sortBy: orUndefined(searchParams.get('sortBy') || defaultFilter?.sortBy),
  };
};
